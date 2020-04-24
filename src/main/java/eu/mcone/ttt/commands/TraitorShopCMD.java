package eu.mcone.ttt.commands;

import eu.mcone.coresystem.api.bukkit.command.CorePlayerCommand;
import eu.mcone.gameapi.api.player.GamePlayer;
import eu.mcone.ttt.TTT;
import eu.mcone.ttt.roles.Role;
import eu.mcone.ttt.state.InGameState;
import org.bukkit.entity.Player;

public class TraitorShopCMD extends CorePlayerCommand {
    public TraitorShopCMD() {
        super("traitorshop");
    }

    @Override
    public boolean onPlayerCommand(Player p, String[] args) {
        if (TTT.getInstance().getGameStateManager() instanceof InGameState) {
            GamePlayer gamePlayer = TTT.getInstance().getGamePlayer(p);
            if (gamePlayer.getTeam().getName().equalsIgnoreCase(Role.TRAITOR.getName())) {

            } else {
                TTT.getInstance().getMessenger().send(p, "§4Du musst Traitor für diesen Befehl sein!");
            }
        } else {
            TTT.getInstance().getMessenger().send(p, "§4Du darfst diesen Befehl nur in der Ingame Phase benutzen!");
        }



        return false;
    }
}
