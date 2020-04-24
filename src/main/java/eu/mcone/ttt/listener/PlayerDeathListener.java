package eu.mcone.ttt.listener;

import eu.mcone.coresystem.api.bukkit.CoreSystem;
import eu.mcone.coresystem.api.bukkit.player.CorePlayer;
import eu.mcone.coresystem.api.bukkit.util.Messager;
import eu.mcone.gameapi.api.gamestate.common.EndGameState;
import eu.mcone.gameapi.api.player.GamePlayer;
import eu.mcone.gameapi.api.player.PlayerManager;
import eu.mcone.ttt.TTT;
import eu.mcone.ttt.state.LobbyState;
import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.DisplaySlot;

public class PlayerDeathListener implements Listener {

    @EventHandler
    public void on(PlayerDeathEvent e) {

        e.setDeathMessage(null);
        Player player = e.getEntity().getPlayer();
        Player killer = (player.getKiller() != null ? player.getKiller() : TTT.getInstance().getDamageLogger().getKiller(player));

        player.getInventory().clear();
        player.getInventory().setArmorContents(new ItemStack[0]);

        if (TTT.getInstance().getGameStateManager().getRunning() instanceof LobbyState
                || TTT.getInstance().getGameStateManager().getRunning() instanceof EndGameState) {
            CoreSystem.getInstance().getWorldManager().getWorld(TTT.getInstance().getGameConfig().parseConfig().getLobby()).teleport(player, "spawn");
        } else {
            GamePlayer gamePlayer = TTT.getInstance().getGamePlayer(player);
            gamePlayer.addDeaths(1);

            if (killer != null) {
                GamePlayer gameKiller = TTT.getInstance().getGamePlayer(killer);
                gameKiller.addKills(1);
                TTT.getInstance().getMessager().broadcast(Messager.Broadcast.BroadcastMessageTyp.KILL_MESSAGE, "§7Der Spieler §f" + killer.getName() + "§7 hat §f" + player.getName() + "§7 getötet!");
                killer.addPotionEffect(PotionEffectType.REGENERATION.createEffect(55, 3));
                player.playSound(player.getLocation(), Sound.NOTE_BASS, 1, 1);
                player.playEffect(player.getLocation(), Effect.INSTANT_SPELL, 1);
            } else {
                TTT.getInstance().getMessager().broadcast(Messager.Broadcast.BroadcastMessageTyp.DEATH_MESSAGE, "§7Der Spieler §f" + player.getName() + "§7 ist gestorben");
                player.playSound(player.getLocation(), Sound.NOTE_BASS, 1, 1);
            }

            CoreSystem.getInstance().getWorldManager().getWorld(TTT.getInstance().getGameConfig().parseConfig().getLobby()).teleport(player, "game.spectator");

            player.getInventory().setArmorContents(null);
            player.getInventory().clear();

            player.getInventory().setItem(7, PlayerManager.SPECTATOR);
            player.getInventory().setItem(8, InventoryTriggerListener.QUIT_ITEM);

            CoreSystem.getInstance().createActionBar()
                    .message("§c§oDu bist gestorben")
                    .send(player);

            for (CorePlayer all : CoreSystem.getInstance().getOnlineCorePlayers()) {
                all.getScoreboard().getObjective(DisplaySlot.SIDEBAR).reload();
            }
        }

        // player.spigot().respawn();
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
