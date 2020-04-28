package eu.mcone.ttt.listener;

import eu.mcone.coresystem.api.bukkit.CoreSystem;
import eu.mcone.coresystem.api.bukkit.player.CorePlayer;
import eu.mcone.gameapi.api.player.GamePlayer;
import eu.mcone.gameapi.api.team.PlayingChat;
import eu.mcone.ttt.TTT;
import eu.mcone.ttt.roles.Role;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChatListener extends PlayingChat {

    @Override
    public void onPlayingChat(String s, Player p, GamePlayer gp) {
        for (Player t : TTT.getInstance().getPlayerManager().getPlaying()) {
            GamePlayer gt = TTT.getInstance().getGamePlayer(t);

            if (
                    (gt.getTeam().getName().equals(Role.INNOCENT.getName())
                            || gt.getTeam().getName().equals(Role.DETECTIVE.getName())
                    ) && gp.getTeam().getName().equals(Role.TRAITOR.getName())
            ) {
                t.sendMessage(
                        "§" + TTT.getInstance().getTeamManager().getTeam(Role.INNOCENT.getName()).getChatColor().getChar() + p.getName() + "§8:§7 " + s
                );
            } else {
                t.sendMessage(
                        "§" + gp.getTeam().getChatColor().getChar() + p.getName() + "§8:§7 " + s
                );
            }
        }
    }
}
