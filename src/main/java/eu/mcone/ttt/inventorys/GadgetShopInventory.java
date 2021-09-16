package eu.mcone.ttt.inventorys;

import eu.mcone.coresystem.api.bukkit.inventory.CoreInventory;
import eu.mcone.coresystem.api.bukkit.inventory.InventoryOption;
import eu.mcone.coresystem.api.bukkit.inventory.InventorySlot;
import eu.mcone.ttt.TTT;
import eu.mcone.ttt.gadgets.Gadget;
import org.bukkit.entity.Player;

public class GadgetShopInventory extends CoreInventory {

    public GadgetShopInventory(Player player, Gadget... gadgets) {
        super("§1Detective Shop", player, InventorySlot.ROW_3, InventoryOption.FILL_EMPTY_SLOTS);

        int i = InventorySlot.ROW_2_SLOT_2;
        for (Gadget gadget : gadgets) {
            setItem(i, gadget.getItem(), e -> {
                player.closeInventory();

                if (player.getLevel() >= gadget.getLevel()) {
                    player.setLevel(player.getLevel() - gadget.getLevel());

                    TTT.getInstance().getMessenger().send(player, "§aDu hast das Item §f" + gadget.getName() + " §a erfolgreich gekauft!");
                    player.getInventory().addItem(gadget.getItem());

                } else {
                    TTT.getInstance().getMessenger().send(player, "§4Du hast nicht genügend Level!");
                }
            });
            i++;
        }

        openInventory();
    }
}
