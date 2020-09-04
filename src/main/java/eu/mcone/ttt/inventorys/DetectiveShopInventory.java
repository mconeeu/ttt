package eu.mcone.ttt.inventorys;

import eu.mcone.coresystem.api.bukkit.inventory.CoreInventory;
import eu.mcone.coresystem.api.bukkit.inventory.InventoryOption;
import eu.mcone.coresystem.api.bukkit.inventory.InventorySlot;
import eu.mcone.ttt.TTT;
import eu.mcone.ttt.gadgets.DetectiveGadgets;
import org.bukkit.entity.Player;

public class DetectiveShopInventory extends CoreInventory {

    private int i = 10;

    public DetectiveShopInventory(Player player) {
        super("§1Detective Shop", player, InventorySlot.ROW_3, InventoryOption.FILL_EMPTY_SLOTS);
        for (DetectiveGadgets gadgets : DetectiveGadgets.values()) {
            setItem(i, gadgets.getItem(), e -> {
                        player.closeInventory();

                        if (player.getLevel() >= gadgets.getLevel()) {
                            player.setLevel(player.getLevel() - gadgets.getLevel());

                            TTT.getInstance().getMessenger().send(player, "§aDu hast das Item §f" + gadgets.getName() + " §a erfolgreich gekauft!");
                            player.getInventory().addItem(gadgets.getItem());

                        } else {
                            TTT.getInstance().getMessenger().send(player, "§4Du hast nicht genügend Level!");
                        }
                    }
            );
            i++;
        }


        openInventory();
    }
}
