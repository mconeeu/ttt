package eu.mcone.ttt.scoreboard;

import eu.mcone.coresystem.api.bukkit.player.CorePlayer;
import eu.mcone.coresystem.api.bukkit.scoreboard.CoreSidebarObjectiveEntry;
import eu.mcone.gameapi.api.scoreboard.LobbyObjective;
import eu.mcone.ttt.TTT;

public class TTTLobbyObjective extends LobbyObjective {

    @Override
    protected void onLobbyRegister(CorePlayer corePlayer, CoreSidebarObjectiveEntry entry) {
        entry.setScore(3, "");
        entry.setScore(1, "§8» §7Karma:");
        entry.setScore(0, "    §f§l" + TTT.getInstance().getGamePlayer(corePlayer.getUuid()).getStats().getGoals());
    }

}
