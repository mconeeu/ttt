package eu.mcone.ttt.commands;

import eu.mcone.coresystem.api.bukkit.command.CorePlayerCommand;
import eu.mcone.coresystem.api.bukkit.item.ItemBuilder;
import eu.mcone.gameapi.api.player.GamePlayer;
import eu.mcone.ttt.TTT;
import eu.mcone.ttt.inventorys.DetectiveShopInventory;
import eu.mcone.ttt.inventorys.InnocentShopInventory;
import eu.mcone.ttt.inventorys.TraitorShopInventory;
import eu.mcone.ttt.roles.Role;
import eu.mcone.ttt.state.InGameState;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ShopCMD extends CorePlayerCommand {
    public ShopCMD() {
        super("shop");
    }



    @Override
    public boolean onPlayerCommand(Player p, String[] args) {
        if (TTT.getInstance().getGameStateManager().getRunning() instanceof InGameState) {
            GamePlayer gamePlayer = TTT.getInstance().getGamePlayer(p);
            p.playSound(p.getLocation(), Sound.CHEST_OPEN, 1, 1);
            if (gamePlayer.getTeam().getName().equalsIgnoreCase(Role.TRAITOR.getName())) {
                new TraitorShopInventory(p);
            } else if (gamePlayer.getTeam().getName().equalsIgnoreCase(Role.DETECTIVE.getName())) {
                new DetectiveShopInventory(p);
            } else if (gamePlayer.getTeam().getName().equalsIgnoreCase(Role.INNOCENT.getName())) {
                new InnocentShopInventory(p);
            }
        } else {
            TTT.getInstance().getMessenger().send(p, "§4Du darfst diesen Befehl nur in der Ingame Phase benutzen!");
        }


        return false;
    }
}
