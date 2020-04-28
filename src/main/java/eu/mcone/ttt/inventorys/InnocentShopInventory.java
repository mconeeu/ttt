package eu.mcone.ttt.inventorys;

import eu.mcone.coresystem.api.bukkit.inventory.CoreInventory;
import eu.mcone.coresystem.api.bukkit.inventory.InventoryOption;
import eu.mcone.coresystem.api.bukkit.inventory.InventorySlot;
import org.bukkit.entity.Player;

public class InnocentShopInventory extends CoreInventory {

    public InnocentShopInventory(Player player) {
        super("§aInnocent Shop", player, InventorySlot.ROW_3, InventoryOption.FILL_EMPTY_SLOTS);


        openInventory();
    }
}