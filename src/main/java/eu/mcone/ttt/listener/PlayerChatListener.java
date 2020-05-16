package eu.mcone.ttt.listener;

import eu.mcone.gameapi.api.player.GamePlayer;
import eu.mcone.gameapi.api.player.GamePlayerState;
import eu.mcone.gameapi.api.team.TeamChatListener;
import eu.mcone.ttt.TTT;
import org.bukkit.entity.Player;

public class PlayerChatListener extends TeamChatListener {

    @Override
    public void onPlayingChat(String s, Player p, GamePlayer gp) {
        for (Player t : TTT.getInstance().getPlayerManager().getPlayers(GamePlayerState.PLAYING)) {
            GamePlayer gt = TTT.getInstance().getGamePlayer(t);

            if (
                    (gt.getTeam().equals(TTT.getInstance().getInnocentTeam())
                            || gt.getTeam().equals(TTT.getInstance().getDetectiveTeam())
                    ) && gp.getTeam().equals(TTT.getInstance().getTraitorTeam())
            ) {
                t.sendMessage(
                        TTT.getInstance().getInnocentTeam().getColor() + p.getName() + "§8 »§7 " + s
                );
            } else {
                t.sendMessage(
                        gp.getTeam().getColor() + p.getName() + "§8 »§7 " + s
                );
            }
        }
    }

}
