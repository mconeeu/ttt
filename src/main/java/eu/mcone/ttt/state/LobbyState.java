package eu.mcone.ttt.state;

import eu.mcone.coresystem.api.bukkit.CorePlugin;
import eu.mcone.coresystem.api.bukkit.CoreSystem;
import eu.mcone.coresystem.api.bukkit.item.ItemBuilder;
import eu.mcone.coresystem.api.bukkit.player.CorePlayer;
import eu.mcone.coresystem.api.bukkit.util.Messenger;
import eu.mcone.coresystem.api.bukkit.world.CoreLocation;
import eu.mcone.gameapi.api.event.gamestate.GameStateStopEvent;
import eu.mcone.gameapi.api.gamestate.common.LobbyGameState;
import eu.mcone.gameapi.api.player.GamePlayer;
import eu.mcone.gameapi.api.team.Team;
import eu.mcone.ttt.TTT;
import eu.mcone.ttt.listener.InventoryTriggerListener;
import eu.mcone.ttt.roles.Role;
import eu.mcone.ttt.scoreboard.Tablist;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.*;

public class LobbyState extends LobbyGameState {

    private static final double DETECTIVES_PROBABILITY = 0.2, TRAITOR_PROBABILITY = 0.3;
    private static final Random ROLE_RANDOM = new Random();
    private static final List<Location> SPAWNS = new ArrayList<>();

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
        int detectives = (int) (playing / DETECTIVES_PROBABILITY);
        int traitors = (int) (playing / TRAITOR_PROBABILITY);

        List<Player> players = new ArrayList<>(TTT.getInstance().getPlayerManager().getPlaying());
        Map<GamePlayer, Role> playerRoles = new HashMap<>();
        for (int i = playing-1; i >= 0; i--) {
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

        int innocents = players.size();
        for (Player p : players) {
            playerRoles.put(TTT.getInstance().getGamePlayer(p), Role.INNOCENT);
        }

        Team detectiveTeam = TTT.getInstance().getTeamManager().getTeam(Role.DETECTIVE.getName());
        Team traitorTeam = TTT.getInstance().getTeamManager().getTeam(Role.TRAITOR.getName());
        Team innocentTeam = TTT.getInstance().getTeamManager().getTeam(Role.INNOCENT.getName());

        detectiveTeam.setSize(detectives);
        traitorTeam.setSize(traitors);
        innocentTeam.setSize(innocents);

        int i = 0;
        for (Map.Entry<GamePlayer, Role> role : playerRoles.entrySet()) {
            switch (role.getValue()) {
                case DETECTIVE: role.getKey().setTeam(detectiveTeam); break;
                case TRAITOR: role.getKey().setTeam(traitorTeam); break;
                case INNOCENT: role.getKey().setTeam(innocentTeam); break;
            }

            GamePlayer gp = role.getKey();
            Player p = gp.bukkit();

            p.getInventory().clear();
            p.getInventory().setItem(8, InventoryTriggerListener.IDENTIFY_STICK);
            p.getInventory().setChestplate(ItemBuilder.createLeatherArmorItem(Material.LEATHER_CHESTPLATE, Color.GRAY).create());

            p.teleport(SPAWNS.get(i));
        }
    }

    @Override
    public void onCountdownSecond(CorePlugin plugin, int second) {
        super.onCountdownSecond(plugin, second);

        if (second == 5) {
            TTT.getInstance().getTeamManager().setupTeamWherePriority();
            TTT.getInstance().getMessenger().broadcast(Messenger.Broadcast.BroadcastMessageTyp.INFO_MESSAGE, "§7Die §fRollen §7stehen nun fest.");
        }
    }
}
