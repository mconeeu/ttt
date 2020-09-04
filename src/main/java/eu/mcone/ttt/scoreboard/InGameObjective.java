package eu.mcone.ttt.scoreboard;

import eu.mcone.coresystem.api.bukkit.player.CorePlayer;
import eu.mcone.coresystem.api.bukkit.scoreboard.CoreSidebarObjectiveEntry;
import eu.mcone.gameapi.api.player.GamePlayer;
import eu.mcone.gameapi.api.player.GamePlayerState;
import eu.mcone.ttt.TTT;

public class InGameObjective extends eu.mcone.gameapi.api.scoreboard.InGameObjective {

    public InGameObjective() {
        super();
    }

    @Override
    protected void onInGameRegister(CorePlayer corePlayer, CoreSidebarObjectiveEntry entry) {
        super.onRegister(corePlayer, entry);
        GamePlayer gamePlayer = TTT.getInstance().getGamePlayer(corePlayer.getUuid());

        entry.setTitle("§7§l⚔ §c§l§nTTT");
        entry.setScore(12, "");
        entry.setScore(11, "§8» §7Karma:");
        entry.setScore(10, "   §f§l" + gamePlayer.getStats().getGoals());
        entry.setScore(9, "");
        entry.setScore(8, "§8» §7Rolle:");
        entry.setScore(7, "   §f§l" + gamePlayer.getTeam().getLabel());
        entry.setScore(6, "");
        entry.setScore(5, "§8» §7Rollen-Punkte:");
        entry.setScore(4, "  §f§l" + gamePlayer.bukkit().getLevel());
        entry.setScore(3, "");
        entry.setScore(2, "§8» §7Lebene Spieler:");
        onReload(corePlayer, entry);
    }

    @Override
    protected void onInGameReload(CorePlayer corePlayer, CoreSidebarObjectiveEntry entry) {
        super.onReload(corePlayer, entry);

        GamePlayer gamePlayer = TTT.getInstance().getGamePlayer(corePlayer.getUuid());
        entry.setScore(10, "   §f§l" + gamePlayer.getStats().getGoals());
        entry.setScore(4, "    §f§l" + gamePlayer.bukkit().getLevel());
        entry.setScore(1, "    §f§l" + TTT.getInstance().getPlayerManager().getPlayers(GamePlayerState.PLAYING).size());
    }
}