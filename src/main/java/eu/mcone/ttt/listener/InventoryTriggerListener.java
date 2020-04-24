package eu.mcone.ttt.listener;

import eu.mcone.coresystem.api.bukkit.item.ItemBuilder;
import eu.mcone.gameapi.api.backpack.defaults.DefaultCategory;
import eu.mcone.gameapi.api.gamestate.common.EndGameState;
import eu.mcone.gameapi.api.gamestate.common.InGameState;
import eu.mcone.ttt.TTT;
import eu.mcone.ttt.state.LobbyState;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class InventoryTriggerListener implements Listener {

    public static final ItemStack QUIT_ITEM = new ItemBuilder(Material.IRON_DOOR, 1, 0).displayName("§4§lVerlassem §8» §7§overlasse die Spielrunde.").create();
    
    @EventHandler
    public void on(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            ItemStack itemStack = e.getItem();
            if (itemStack != null) {
                if (TTT.getInstance().getGameStateManager().getRunning() instanceof LobbyState
                        || TTT.getInstance().getGameStateManager().getRunning() instanceof EndGameState) {
                    if (itemStack.getType().equals(Material.IRON_DOOR)) {
                        player.performCommand("hub");
                    } else if (itemStack.hasItemMeta()) {
                        if (e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§3§lRucksack §8» §7§oZeige deine gesammelten Items an")) {
                            e.setCancelled(true);
                            TTT.getInstance().getBackpackManager().openBackpackInventory(DefaultCategory.GADGET.name(), player);
                        }
                        e.setCancelled(true);
                    }
                } else if (TTT.getInstance().getGameStateManager().getRunning() instanceof InGameState) {


                }
            }
        }
    }
}
