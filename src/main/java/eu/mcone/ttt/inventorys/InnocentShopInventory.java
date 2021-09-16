package eu.mcone.ttt.inventorys;

import eu.mcone.coresystem.api.bukkit.inventory.CoreInventory;
import eu.mcone.coresystem.api.bukkit.inventory.InventoryOption;
import eu.mcone.coresystem.api.bukkit.inventory.InventorySlot;
import eu.mcone.ttt.TTT;
import eu.mcone.ttt.gadgets.InnocentGadget;
import org.bukkit.entity.Player;

public class InnocentShopInventory extends CoreInventory {

    private int i = 10;

    public InnocentShopInventory(Player player) {
        super("§aInnocent Shop", player, InventorySlot.ROW_3, InventoryOption.FILL_EMPTY_SLOTS);
        for (InnocentGadget gadgets : InnocentGadget.values()) {
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