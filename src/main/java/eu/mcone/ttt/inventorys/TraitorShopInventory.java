package eu.mcone.ttt.inventorys;

import eu.mcone.coresystem.api.bukkit.inventory.CoreInventory;
import eu.mcone.coresystem.api.bukkit.inventory.InventoryOption;
import eu.mcone.coresystem.api.bukkit.inventory.InventorySlot;
import eu.mcone.ttt.TTT;
import eu.mcone.ttt.gadgets.DetectiveGadgets;
import eu.mcone.ttt.gadgets.TraitorGadgets;
import org.bukkit.entity.Player;

public class TraitorShopInventory extends CoreInventory {

    private int i = 11;
    public TraitorShopInventory(Player player) {
        super("§cTraitor Shop", player, InventorySlot.ROW_3, InventoryOption.FILL_EMPTY_SLOTS);

        for (TraitorGadgets gadgets : TraitorGadgets.values()) {
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
