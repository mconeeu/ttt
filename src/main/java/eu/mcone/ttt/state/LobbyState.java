package eu.mcone.ttt.state;

import eu.mcone.coresystem.api.bukkit.CoreSystem;
import eu.mcone.coresystem.api.bukkit.item.ItemBuilder;
import eu.mcone.coresystem.api.bukkit.world.CoreLocation;
import eu.mcone.gameapi.api.GameAPI;
import eu.mcone.gameapi.api.event.gamestate.GameStateStopEvent;
import eu.mcone.gameapi.api.gamestate.common.LobbyGameState;
import eu.mcone.gameapi.api.player.GamePlayer;
import eu.mcone.gameapi.api.team.Team;
import eu.mcone.ttt.TTT;
import eu.mcone.ttt.listener.InventoryTriggerListener;
import eu.mcone.ttt.player.TTTPass;
import eu.mcone.ttt.player.TTTPlayer;
import eu.mcone.ttt.roles.Role;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.*;

public class LobbyState extends LobbyGameState {

    private static final double DETECTIVES_PROBABILITY = 0.2, TRAITOR_PROBABILITY = 0.3;
    private static final Random ROLE_RANDOM = new Random();
    private static final List<Location> SPAWNS = new ArrayList<>();
    private List<Location> inUse = new ArrayList<>();

    static {
        for (Map.Entry<String, CoreLocation> location : TTT.getInstance().getGameWorld().getLocations().entrySet()) {
            if (location.getKey().startsWith("ttt-spawn-")) {
                SPAWNS.add(location.getValue().bukkit());
            }
        }
    }

    @Override
    public void onStop(GameStateStopEvent event) {
        int playing = TTT.getInstance().getPlayerManager().getPlaying().size();
        int detectives = (int) (playing * DETECTIVES_PROBABILITY) > 0 ? (int) (playing * DETECTIVES_PROBABILITY) : 1;
        int traitors = (int) (playing * TRAITOR_PROBABILITY) > 0 ? (int) (playing * TRAITOR_PROBABILITY) : 1;

        int detectiveSize = 0;
        int traitorSize = 0;

        List<Player> players = new ArrayList<>(TTT.getInstance().getPlayerManager().getPlaying());
        Map<GamePlayer, Role> playerRoles = new HashMap<>();

        List<TTTPlayer> redeemedPassPlayers = new ArrayList<>();
        for (TTTPlayer ttp : TTT.getInstance().getOnlineTTTPlayers()) {
            if (ttp.hasRedeemedPass()) {
                redeemedPassPlayers.add(ttp);
            }
        }

        for (int i = redeemedPassPlayers.size(); i >= 1; i--) {
            TTTPlayer ttp = redeemedPassPlayers.get(ROLE_RANDOM.nextInt(i));
            Role role = ttp.getRedeemedPass().equals(TTTPass.TRAITOR) ? Role.TRAITOR : Role.DETECTIVE;

            if ((role.equals(Role.TRAITOR) ? traitors : detectives) > 0) {
                playerRoles.put(TTT.getInstance().getGamePlayer(ttp.bukkit()), role);
                players.remove(ttp.bukkit());
                redeemedPassPlayers.remove(ttp);
                ttp.setPassRedeemed();

                if (ttp.getRedeemedPass().equals(TTTPass.TRAITOR)) {
                    traitors--;
                } else if (ttp.getRedeemedPass().equals(TTTPass.DETECTIVE)) {
                    detectives--;
                }
            } else {
                TTT.getInstance().getMessenger().send(ttp.bukkit(), "§4Dein §c" + ttp.getRedeemedPass() + " §4konnte nicht eingelöst werden, es sind zu wenig Spieler online!");
            }
        }

        for (int i = playing; i >= 1; i--) {
            if (detectives > 0) {
                Player p = players.get(ROLE_RANDOM.nextInt(i));
                playerRoles.put(TTT.getInstance().getGamePlayer(p), Role.DETECTIVE);

                players.remove(p);
                detectives--;
                continue;
            }
            if (traitors > 0) {
                Player p = players.get(ROLE_RANDOM.nextInt(i));
                playerRoles.put(TTT.getInstance().getGamePlayer(p), Role.TRAITOR);

                players.remove(p);
                traitors--;
            }
        }

        for (Player p : players) {
            playerRoles.put(TTT.getInstance().getGamePlayer(p), Role.INNOCENT);
        }

        Team detectiveTeam = TTT.getInstance().getTeamManager().getTeam(Role.DETECTIVE.getName());
        Team traitorTeam = TTT.getInstance().getTeamManager().getTeam(Role.TRAITOR.getName());
        Team innocentTeam = TTT.getInstance().getTeamManager().getTeam(Role.INNOCENT.getName());

        detectiveTeam.setSize(detectiveSize);
        traitorTeam.setSize(traitorSize);
        innocentTeam.setSize(playerRoles.size() - (detectiveSize + traitorSize));

        for (Map.Entry<GamePlayer, Role> role : playerRoles.entrySet()) {
            switch (role.getValue()) {
                case DETECTIVE:
                    role.getKey().setTeam(detectiveTeam);
                    break;
                case TRAITOR:
                    role.getKey().setTeam(traitorTeam);
                    break;
                case INNOCENT:
                    role.getKey().setTeam(innocentTeam);
                    break;

            }

            GamePlayer gp = role.getKey();
            Player p = gp.bukkit();

            p.getInventory().clear();
            p.getInventory().setItem(8, InventoryTriggerListener.IDENTIFY_STICK);
            if (gp.getTeam().getName().equalsIgnoreCase(Role.DETECTIVE.getName())) {
                p.getInventory().setChestplate(ItemBuilder.createLeatherArmorItem(Material.LEATHER_CHESTPLATE, Color.BLUE).unbreakable(true).create());
            } else {
                p.getInventory().setChestplate(ItemBuilder.createLeatherArmorItem(Material.LEATHER_CHESTPLATE, Color.GREEN).unbreakable(true).create());
            }


            CoreSystem.getInstance().createTitle().title("§fDeine §lRolle§f:").subTitle(gp.getTeam().getName()).stay(4).fadeIn(1).fadeOut(2).send(p);
            p.playSound(p.getLocation(), Sound.AMBIENCE_THUNDER, 1, 1);
            p.playSound(p.getLocation(), Sound.NOTE_PLING, 1, 1);

            TTT.getInstance().getMessenger().send(p, "§fDu bist in der Rolle " + gp.getTeam().getName());

            Location location = null;
            do {
                location = SPAWNS.get(ROLE_RANDOM.nextInt(SPAWNS.size() - 1));
                inUse.add(location);
                p.teleport(location);
            } while (location != null && !inUse.contains(location));
        }
    }
}
