package eu.mcone.ttt.inventorys;

import eu.mcone.coresystem.api.bukkit.inventory.CoreInventory;
import eu.mcone.coresystem.api.bukkit.inventory.InventoryOption;
import eu.mcone.coresystem.api.bukkit.inventory.InventorySlot;
import eu.mcone.coresystem.api.bukkit.item.ItemBuilder;
import eu.mcone.ttt.TTT;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class RollInventar extends CoreInventory {
    public RollInventar(Player player) {
        super("", player, InventorySlot.ROW_3, InventoryOption.FILL_EMPTY_SLOTS);

        setItem(InventorySlot.ROW_2_SLOT_4, new ItemBuilder(Material.WOOL, 1, 14)
                        .displayName("§4Traitor-Pass §8| §fLobbygame")
                        .lore("§7§oWenn du ein Ticket einlöst", "§7§owirst du Traitor sein!", "", "§8» §f§nLinksklick§8 | §7§oNutzen")
                        .create(),

                e -> {
                    player.closeInventory();
                    TTT.getInstance().getMessager().send(player,"§7Du wirst §4Traitor §7sein!");

                });

        setItem(InventorySlot.ROW_2_SLOT_6, new ItemBuilder(Material.WOOL, 1, 5)
                        .displayName("§1Detective-Pass §8| §fLobbygame")
                        .lore("§7§oWenn du ein Ticket einlöst", "§7§owirst du Detective sein!", "", "§8» §f§nLinksklick§8 | §7§oNutzen")
                        .create(),

                e -> {
                    player.closeInventory();
                    TTT.getInstance().getMessager().send(player,"§7Du wirst §1Detective §7sein!");
                });



    }
}
