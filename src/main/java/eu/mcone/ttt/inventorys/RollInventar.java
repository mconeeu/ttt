package eu.mcone.ttt.inventorys;

import eu.mcone.coresystem.api.bukkit.inventory.CoreInventory;
import eu.mcone.coresystem.api.bukkit.inventory.InventoryOption;
import eu.mcone.coresystem.api.bukkit.inventory.InventorySlot;
import eu.mcone.coresystem.api.bukkit.item.ItemBuilder;
import eu.mcone.ttt.TTT;
import eu.mcone.ttt.player.TTTPass;
import eu.mcone.ttt.player.TTTPlayer;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class RollInventar extends CoreInventory {

    public RollInventar(Player player) {
        super("§fWähle deine Rolle", player, InventorySlot.ROW_3, InventoryOption.FILL_EMPTY_SLOTS);

        TTTPlayer tttPlayer = TTT.getInstance().getTTTPlayer(player);

        setItem(InventorySlot.ROW_2_SLOT_3, new ItemBuilder(Material.WOOL, 1, 14)
                        .displayName("§4Traitor-Pass")
                        .lore("§7§oWenn du ein Ticket einlöst", "§7§owirst du Traitor sein!", "", "§7Du hast noch §f" + tttPlayer.getTraitorPasses() + " Tickets", "", "§8» §f§nLinksklick§8 | §7§oNutzen")
                        .create(),

                e -> {
                    try {
                        tttPlayer.redeemPass(TTTPass.TRAITOR);
                        TTT.getInstance().getMessenger().send(player, "§7Du wirst ein §4Traitor §7werden!");
                    } catch (IllegalStateException ignored) {
                        TTT.getInstance().getMessenger().send(player, "§4Du hast keine Traitor-Pässe!");
                    } finally {
                        player.closeInventory();
                    }
                });

        setItem(InventorySlot.ROW_2_SLOT_7, new ItemBuilder(Material.WOOL, 1, 11)
                        .displayName("§1Detective-Pass")
                        .lore("§7§oWenn du ein Ticket einlöst", "§7§owirst du Detective sein!", "", "§7Du hast noch §f" + tttPlayer.getDetectivePasses() + " Tickets", "", "§8» §f§nLinksklick§8 | §7§oNutzen")
                        .create(),

                e -> {
                    try {
                        tttPlayer.redeemPass(TTTPass.DETECTIVE);
                        TTT.getInstance().getMessenger().send(player, "§7Du wirst ein §1Detective §7werden!");
                    } catch (IllegalStateException ignored) {
                        TTT.getInstance().getMessenger().send(player, "§4Du hast keine Detective-Pässe!");
                    } finally {
                        player.closeInventory();
                    }
                });


        openInventory();

    }
}
