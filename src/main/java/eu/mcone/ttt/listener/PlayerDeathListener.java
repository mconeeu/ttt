package eu.mcone.ttt.listener;

import com.sun.xml.internal.ws.policy.EffectiveAlternativeSelector;
import eu.mcone.coresystem.api.bukkit.CoreSystem;
import eu.mcone.coresystem.api.bukkit.command.CorePlayerCommand;
import eu.mcone.coresystem.api.bukkit.npc.NpcData;
import eu.mcone.coresystem.api.bukkit.npc.data.PlayerNpcData;
import eu.mcone.coresystem.api.bukkit.player.CorePlayer;
import eu.mcone.coresystem.api.bukkit.util.Messenger;
import eu.mcone.coresystem.api.bukkit.world.CoreLocation;
import eu.mcone.coresystem.api.core.player.SkinInfo;
import eu.mcone.gameapi.api.GamePlugin;
import eu.mcone.gameapi.api.event.team.TeamWonEvent;
import eu.mcone.gameapi.api.gamestate.common.EndGameState;
import eu.mcone.gameapi.api.player.GamePlayer;
import eu.mcone.gameapi.api.player.PlayerManager;
import eu.mcone.gameapi.api.team.Team;
import eu.mcone.ttt.TTT;
import eu.mcone.ttt.roles.Role;
import eu.mcone.ttt.state.LobbyState;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.DisplaySlot;

public class PlayerDeathListener implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void on(PlayerDeathEvent e) {
        e.getDrops().clear();

        e.setDeathMessage(null);
        Player player = e.getEntity().getPlayer();
        GamePlayer gp = TTT.getInstance().getGamePlayer(player);
        Player killer = (player.getKiller() != null ? player.getKiller() : TTT.getInstance().getDamageLogger().getKiller(player));

        player.getInventory().clear();
        player.getInventory().setArmorContents(new ItemStack[0]);

        if (gp.getTeam() != null) {
            gp.removeFromGame();
        }

        Team traitors = TTT.getInstance().getTeamManager().getTeam(Role.TRAITOR.getName());
        Team detectives = TTT.getInstance().getTeamManager().getTeam(Role.DETECTIVE.getName());
        Team innocents = TTT.getInstance().getTeamManager().getTeam(Role.INNOCENT.getName());

        System.out.println(TTT.getInstance().getTeamManager().getTeams());

        if (traitors.getPlayers().size() > 0 && detectives.getPlayers().size() == 0 && innocents.getPlayers().size() == 0) {
            TTT.getInstance().getMessenger().broadcast(Messenger.Broadcast.BroadcastMessageTyp.INFO_MESSAGE, "§7Die §c§lTraitor §7haben das Spiel gewonnen!");
            Bukkit.getPluginManager().callEvent(new TeamWonEvent(traitors));
        } else if (traitors.getPlayers().size() == 0 && (detectives.getPlayers().size() > 0 || innocents.getPlayers().size() > 0)) {
            TTT.getInstance().getMessenger().broadcast(Messenger.Broadcast.BroadcastMessageTyp.INFO_MESSAGE, "§7Die §a§lInnocents §7haben das Spiel gewonnen!");
            Bukkit.getPluginManager().callEvent(new TeamWonEvent(innocents));
        }


        if (TTT.getInstance().getGameStateManager().getRunning() instanceof LobbyState
                || TTT.getInstance().getGameStateManager().getRunning() instanceof EndGameState) {
            CoreSystem.getInstance().getWorldManager().getWorld(TTT.getInstance().getGameConfig().parseConfig().getLobby()).teleport(player, "spawn");
        } else {
            GamePlayer gamePlayer = TTT.getInstance().getGamePlayer(player);
            gamePlayer.addDeaths(1);

            if (killer != null) {
                GamePlayer gameKiller = TTT.getInstance().getGamePlayer(killer);
                gameKiller.addKills(1);
                ;

                TTT.getInstance().getMessenger().send(player, "§7Der Spieler §f" + killer.getName() + "§7 hat dich getötet!");
                TTT.getInstance().getMessenger().send(killer, "§7Du hast §f" + player.getName() + "§7 getötet");


                if (gamePlayer.getTeam().getName().equalsIgnoreCase(Role.TRAITOR.getName())) {
                    if (gameKiller.getTeam().getName().equalsIgnoreCase(Role.DETECTIVE.getName()) || gameKiller.getTeam().getName().equalsIgnoreCase(Role.INNOCENT.getName())) {
                        killer.setLevel(killer.getLevel() + 2);
                        TTT.getInstance().getMessenger().send(player, "§8[§a+20 Karma§8]");
                        gamePlayer.addGoals(20);
                    }
                    player.setLevel(2);
                } else if (gamePlayer.getTeam().getName().equalsIgnoreCase(Role.DETECTIVE.getName())) {
                    if (gameKiller.getTeam().getName().equalsIgnoreCase(Role.TRAITOR.getName())) {
                        killer.setLevel(killer.getLevel() + 3);
                    } else {
                        TTT.getInstance().getMessenger().send(killer, "§7Du hast einen §1Detective §7als §aInnocent§7 getötet §8[§a-40 Karma§8]");
                        if (gamePlayer.getCorePlayer().getStats().getGoal() >= 40) {
                            gamePlayer.getCorePlayer().getStats().removeGoals(40);
                        }
                    }
                } else if (gamePlayer.getTeam().getName().equalsIgnoreCase(Role.INNOCENT.getName())) {
                    if (gameKiller.getTeam().getName().equalsIgnoreCase(Role.TRAITOR.getName())) {
                        killer.setLevel(killer.getLevel() + 2);
                    } else {
                        TTT.getInstance().getMessenger().send(killer, "§7Du hast einen Innocent getötet §8[§a-20 Karma§8]");
                        if (gamePlayer.getCorePlayer().getStats().getGoal() >= 20) {
                            gamePlayer.getCorePlayer().getStats().removeGoals(20);
                        }
                    }
                }

                player.playSound(player.getLocation(), Sound.NOTE_BASS, 1, 1);
                player.playEffect(player.getLocation(), Effect.INSTANT_SPELL, 1);
            } else {
                player.playSound(player.getLocation(), Sound.NOTE_BASS, 1, 1);
            }

            CoreSystem.getInstance().getWorldManager().getWorld(TTT.getInstance().getGameConfig().parseConfig().getLobby()).teleport(player, "game.spectator");
            NpcData npcData = new NpcData
                    (
                            EntityType.PLAYER,
                            player.getName(),
                            "§fLeiche",
                            new CoreLocation(player.getLocation()),
                            new PlayerNpcData(
                                    "zombie",
                                    "",
                                    SkinInfo.SkinType.PLAYER,
                                    false,
                                    false,
                                    false,
                                    null
                            )
                    );

            CoreSystem.getInstance().getNpcManager().addNPC(npcData);


            player.getInventory().setArmorContents(null);
            player.getInventory().clear();

            player.getInventory().setItem(7, PlayerManager.SPECTATOR);
            player.getInventory().setItem(8, InventoryTriggerListener.QUIT_ITEM);

            CoreSystem.getInstance().createActionBar()
                    .message("§c§oDu bist gestorben")
                    .send(player);

            for (CorePlayer cp : CoreSystem.getInstance().getOnlineCorePlayers()) {
                if (cp.getScoreboard() != null) {
                    if (cp.getScoreboard().getObjective(DisplaySlot.SIDEBAR) != null) {
                        cp.getScoreboard().getObjective(DisplaySlot.SIDEBAR).reload();
                    }
                }
            }
        }

         player.spigot().respawn();
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e) {
        Player player = e.getPlayer();
        if (TTT.getInstance().getGameStateManager().getRunning() instanceof LobbyState
                || TTT.getInstance().getGameStateManager().getRunning() instanceof EndGameState) {
            e.setRespawnLocation(CoreSystem.getInstance().getWorldManager().getWorld(TTT.getInstance().getGameConfig().parseConfig().getLobby()).getLocation("spawn"));
        } else {
            e.setRespawnLocation(CoreSystem.getInstance().getWorldManager().getWorld(TTT.getInstance().getGameConfig().parseConfig().getGameWorld()).getLocation("game.spectator"));
        }
    }
}
