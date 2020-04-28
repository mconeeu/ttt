package eu.mcone.ttt.commands;

import eu.mcone.coresystem.api.bukkit.command.CoreCommand;
import eu.mcone.ttt.TTT;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TTTCMD extends CoreCommand {


    public TTTCMD() {
        super("ttt");
    }

    @Override
    public boolean onCommand(CommandSender commandSender, String[] strings) {
        Player player = (Player) commandSender;

        TTT.getInstance().getMessenger().send(player, "§8§m---------- §r§c§lMCONE-TTT §8§m----------");
        TTT.getInstance().getMessenger().send(player, "§7Entwickelt von §fMarvio, §fDieserDominik");
        TTT.getInstance().getMessenger().send(player, "§r");
        TTT.getInstance().getMessenger().send(player, "§7§oWir bemühen uns darum alle Systeme und Spielmodi so effizient wie möglich zu gestalten.");
        TTT.getInstance().getMessenger().send(player, "§7§oDeshalb sind auch alle von uns verwendeten Plugins ausschließlich selbst entwickelt!");
        TTT.getInstance().getMessenger().send(player, "§8§m---------- §r§c§lMCONE-TTT §8§m----------");
        player.playSound(player.getLocation(), Sound.NOTE_PIANO, 1, 1);


        return false;
    }
}
