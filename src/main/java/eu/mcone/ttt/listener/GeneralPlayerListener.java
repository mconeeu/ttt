package eu.mcone.ttt.listener;

import eu.mcone.gameapi.api.gamestate.common.EndGameState;
import eu.mcone.ttt.TTT;
import eu.mcone.ttt.state.LobbyState;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerAchievementAwardedEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

public class GeneralPlayerListener implements Listener {

    @EventHandler
    public void onTrigger(InventoryClickEvent e) {
        if ((TTT.getInstance().getGameStateManager().getRunning() instanceof LobbyState
                || TTT.getInstance().getGameStateManager().getRunning() instanceof EndGameState)
                && !e.getWhoClicked().getGameMode().equals(GameMode.CREATIVE)) {
            e.setCancelled(true);
        } else {
            e.setCancelled(false);
        }
    }


    @EventHandler
    public void onWeatherChange(WeatherChangeEvent e) {
        if (e.toWeatherState()) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onAchievementAward(PlayerAchievementAwardedEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onItemPickUp(PlayerPickupItemEvent e) {
        if (TTT.getInstance().getGameStateManager().getRunning() instanceof LobbyState
                || TTT.getInstance().getGameStateManager().getRunning() instanceof EndGameState) {
            e.setCancelled(true);
        } else {
            e.setCancelled(false);
        }
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void on(BlockBreakEvent e) {
        e.setCancelled(true);
    }


    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        if (TTT.getInstance().getGameStateManager().getRunning() instanceof LobbyState
                || TTT.getInstance().getGameStateManager().getRunning() instanceof EndGameState) {
            e.setCancelled(true);
        } else {
            e.setCancelled(false);
        }
    }
}
