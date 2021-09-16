package eu.mcone.ttt.scoreboard;

import eu.mcone.coresystem.api.bukkit.player.CorePlayer;
import eu.mcone.coresystem.api.bukkit.scoreboard.CoreSidebarObjectiveEntry;
import eu.mcone.gameapi.api.player.GamePlayer;
import eu.mcone.gameapi.api.scoreboard.GameObjective;
import eu.mcone.ttt.TTT;

public class TTTEndObjective extends GameObjective {

    @Override
    protected void onGameObjectiveRegister(CorePlayer corePlayer, CoreSidebarObjectiveEntry entry) {
        GamePlayer gamePlayer = TTT.getInstance().getGamePlayer(corePlayer.getUuid());
        entry.setScore(5, "");
        entry.setScore(4, "§8» §7Karma:");
        entry.setScore(3, "    §f§l" + gamePlayer.getStats().getGoals());
        entry.setScore(2, "");
        entry.setScore(1, "§8» §7Kills:");
        onReload(corePlayer, entry);
    }

    @Override
    protected void onGameObjectiveReload(CorePlayer corePlayer, CoreSidebarObjectiveEntry entry) {
        GamePlayer gamePlayer = TTT.getInstance().getGamePlayer(corePlayer.getUuid());
        entry.setScore(0, "    §f§l" + gamePlayer.getRoundKills());
    }

}
