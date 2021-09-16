package eu.mcone.ttt.scoreboard;

import eu.mcone.coresystem.api.bukkit.player.CorePlayer;
import eu.mcone.coresystem.api.bukkit.scoreboard.CoreSidebarObjectiveEntry;
import eu.mcone.gameapi.api.player.GamePlayer;
import eu.mcone.gameapi.api.player.GamePlayerState;
import eu.mcone.gameapi.api.scoreboard.InGameObjective;
import eu.mcone.ttt.TTT;

public class TTTInGameObjective extends InGameObjective {

    @Override
    protected void onInGameRegister(CorePlayer corePlayer, CoreSidebarObjectiveEntry entry) {
        entry.setScore(10, "§8» §7Karma:");
        entry.setScore(8, "");
        entry.setScore(7, "§8» §7Rolle:");
        entry.setScore(5, "");
        entry.setScore(4, "§8» §7Rollen-Punkte:");
        entry.setScore(2, "");
        entry.setScore(1, "§8» §7Lebene Spieler:");
        onReload(corePlayer, entry);
    }

    @Override
    protected void onInGameReload(CorePlayer corePlayer, CoreSidebarObjectiveEntry entry) {
        GamePlayer gamePlayer = TTT.getInstance().getGamePlayer(corePlayer.getUuid());
        entry.setScore(9, "   §f§l" + gamePlayer.getStats().getGoals());
        entry.setScore(3, "    §f§l" + gamePlayer.bukkit().getLevel());
        entry.setScore(0, "    §f§l" + TTT.getInstance().getPlayerManager().getPlayers(GamePlayerState.PLAYING).size());
    }

}