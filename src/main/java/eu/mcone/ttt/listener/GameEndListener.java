package eu.mcone.ttt.listener;

import eu.mcone.gameapi.api.event.game.GameDrawEvent;
import eu.mcone.gameapi.api.event.team.TeamWonEvent;
import eu.mcone.gameapi.api.team.Team;
import eu.mcone.ttt.TTT;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class GameEndListener implements Listener {

    @EventHandler
    public void onTeamWon(TeamWonEvent e) {
        preparePlayersForEnd(e.getTeam());
    }

    @EventHandler
    public void onDraw(GameDrawEvent e) {
        preparePlayersForEnd(TTT.getInstance().getInnocentTeam());
    }

    private static void preparePlayersForEnd(Team team) {
        //TTT.getInstance().getGameStateManager().setGameState(TTT.getInstance().getGameStateManager().getNextGameState(), true);

        for (Player player : Bukkit.getOnlinePlayers()) {
            TTT.getInstance().getMessenger().send(player, "§7Team " + team.getLabel() + " §7hat das Spiel gewonnen!");
        }
    }

}
