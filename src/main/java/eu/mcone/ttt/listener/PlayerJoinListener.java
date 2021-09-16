package eu.mcone.ttt.listener;

import eu.mcone.coresystem.api.bukkit.CoreSystem;
import eu.mcone.coresystem.api.bukkit.item.ItemBuilder;
import eu.mcone.coresystem.api.bukkit.player.CorePlayer;
import eu.mcone.gameapi.api.HotbarItem;
import eu.mcone.gameapi.api.event.player.GamePlayerLoadedEvent;
import eu.mcone.gameapi.api.gamestate.common.EndGameState;
import eu.mcone.gameapi.api.gamestate.common.InGameState;
import eu.mcone.gameapi.api.player.GamePlayer;
import eu.mcone.gameapi.api.player.PlayerManager;
import eu.mcone.ttt.TTT;
import eu.mcone.ttt.player.TTTPlayer;
import eu.mcone.ttt.state.LobbyState;
import eu.mcone.ttt.state.MiddleState;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scoreboard.DisplaySlot;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void on(GamePlayerLoadedEvent e) {
        Player player = e.getBukkitPlayer();
        GamePlayer gamePlayer = TTT.getInstance().getGamePlayer(player);

        player.setGameMode(GameMode.SURVIVAL);

        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        player.setHealth(20);
        player.setFoodLevel(20);
        player.setExp(0);
        player.setLevel(0);

        new TTTPlayer(e.getCorePlayer());
        player.getInventory().setItem(8, HotbarItem.QUIT);

        if (TTT.getInstance().getGameStateManager().getRunning() instanceof LobbyState) {
            for (CorePlayer cp : CoreSystem.getInstance().getOnlineCorePlayers()) {
                if (cp.getScoreboard() != null) {
                    if (cp.getScoreboard().getObjective(DisplaySlot.SIDEBAR) != null) {
                        cp.getScoreboard().getObjective(DisplaySlot.SIDEBAR).reload();
                    }
                }
            }

//            gamePlayer.setLastUsedBackPackItemInventar();
            player.getInventory().setItem(7, new ItemBuilder(Material.STORAGE_MINECART, 1, 0).displayName("§3§lRucksack §8» §7§oZeige deine gesammelten Items an").create());
            player.getInventory().setItem(0, InventoryTriggerListener.ROLL_ITEM);

            CoreSystem.getInstance().getWorldManager().getWorld(TTT.getInstance().getGameConfig().parseConfig().getLobby()).teleport(player, "spawn");
        } else if (TTT.getInstance().getGameStateManager().getRunning() instanceof InGameState || TTT.getInstance().getGameStateManager().getRunning() instanceof MiddleState) {
            player.getInventory().setArmorContents(null);
            player.getInventory().setChestplate(null);
            player.getInventory().clear();

            player.getInventory().setItem(7, PlayerManager.SPECTATOR);
            player.getInventory().setItem(8, HotbarItem.QUIT);
            CoreSystem.getInstance().getWorldManager().getWorld(TTT.getInstance().getGameConfig().parseConfig().getLobby()).teleport(player, "game.spectator");
        } else if (TTT.getInstance().getGameStateManager().getRunning() instanceof EndGameState) {
            CoreSystem.getInstance().getWorldManager().getWorld(TTT.getInstance().getGameConfig().parseConfig().getLobby()).teleport(player, "spawn");

            player.getInventory().setItem(8, HotbarItem.QUIT);
            player.getInventory().setItem(7, new ItemBuilder(Material.STORAGE_MINECART, 1, 0).displayName("§3§lRucksack §8» §7§oZeige deine gesammelten Items an").create());

            for (CorePlayer all : CoreSystem.getInstance().getOnlineCorePlayers()) {
                all.getScoreboard().getObjective(DisplaySlot.SIDEBAR).reload();
            }
        }
    }
}
