package eu.mcone.ttt.commands;

import eu.mcone.coresystem.api.bukkit.command.CorePlayerCommand;
import eu.mcone.ttt.TTT;
import eu.mcone.ttt.player.TTTPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PassesCMD extends CorePlayerCommand {


    public PassesCMD() {
        super("pass");
    }

    //TODO ADD PASSES

    @Override
    public boolean onPlayerCommand(Player p, String[] args) {
        if (args.length == 4) {
            if (args[0].equalsIgnoreCase("add")) {
                if (args[1].equalsIgnoreCase("traitorpass")) {
                    Player target = Bukkit.getPlayer(args[2]);
                    if (target != null) {
                        TTTPlayer tttPlayer = TTT.getInstance().getTTTPlayer(target);
                        int amount = Integer.parseInt(args[3]);

                        tttPlayer.addTraitorPass(amount);

                        TTT.getInstance().getMessenger().send(p, "§7Du hast dem Spieler §f" + target.getName() + " " + amount + "§7 Traitor Pässe hinzugefügt!");
                        TTT.getInstance().getMessenger().send(target, "§7Du hast von §f" + p.getName() + " " + amount + "§7 Traitor Pässe erhalten!");

                    } else {
                        TTT.getInstance().getMessenger().send(p, "§4Der Spieler ist Offline");
                    }
                } else if (args[1].equalsIgnoreCase("detectivepass")) {
                    Player target = Bukkit.getPlayer(args[2]);
                    if (target != null) {
                        TTTPlayer tttPlayer = TTT.getInstance().getTTTPlayer(target);
                        int amount = Integer.parseInt(args[3]);

                        tttPlayer.addDetectivePass(amount);

                        TTT.getInstance().getMessenger().send(p, "§7Du hast dem Spieler §f" + target.getName() + " " + amount + "§7 Detective Pässe hinzugefügt!");
                        TTT.getInstance().getMessenger().send(target, "§7Du hast von §f" + p.getName() + " " + amount + "§7 Detective Pässe erhalten!");

                    } else {
                        TTT.getInstance().getMessenger().send(p, "§4Der Spieler ist Offline");
                    }
                } else {
                    TTT.getInstance().getMessenger().send(p, "§cBitte benutze: §4/pass <add|remove> <traitorpass|detectivepass> <[Spieler]> <Anzahl>!");
                }
            } else if (args[0].equalsIgnoreCase("remove")) {
                if (args[1].equalsIgnoreCase("traitorpass")) {
                    Player target = Bukkit.getPlayer(args[2]);
                    if (target != null) {
                        TTTPlayer tttPlayer = TTT.getInstance().getTTTPlayer(target);
                        int amount = Integer.parseInt(args[3]);

                        tttPlayer.removeTraitorPass(amount);

                        TTT.getInstance().getMessenger().send(p, "§7Du hast dem Spieler §f" + target.getName() + " " + amount + "§7 Traitor Pässe entfernt!");
                        TTT.getInstance().getMessenger().send(target, "§f" + p.getName() + "§7 hat dir §f" + amount + "§7 Traitor Pässe entfernt!");

                    } else {
                        TTT.getInstance().getMessenger().send(p, "§4Der Spieler ist Offline");
                    }
                } else if (args[1].equalsIgnoreCase("detectivepass")) {
                    Player target = Bukkit.getPlayer(args[2]);
                    if (target != null) {
                        TTTPlayer tttPlayer = TTT.getInstance().getTTTPlayer(target);
                        int amount = Integer.parseInt(args[3]);

                        tttPlayer.removeDetectivePass(amount);


                        TTT.getInstance().getMessenger().send(p, "§7Du hast dem Spieler §f" + target.getName() + " " + amount + "§7 Detectiv Pässe entfernt!");
                        TTT.getInstance().getMessenger().send(target, "§f" + p.getName() + "§7 hat dir §f" + amount + "§7 Detectiv Pässe entfernt!");


                    } else {
                        TTT.getInstance().getMessenger().send(p, "§4Der Spieler ist Offline");
                    }
                } else {
                    TTT.getInstance().getMessenger().send(p, "§cBitte benutze: §4/pass <add|remove> <traitorpass|detectivepass> <[Spieler]> <Anzahl>!");
                }
            }
        } else {
            TTT.getInstance().getMessenger().send(p, "§cBitte benutze: §4/pass <add|remove> <traitorpass|detectivepass> <[Spieler]> <Anzahl>!");
        }


        return false;
    }
}
