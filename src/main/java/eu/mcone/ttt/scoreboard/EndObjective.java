package eu.mcone.ttt.scoreboard;

import eu.mcone.coresystem.api.bukkit.player.CorePlayer;
import eu.mcone.coresystem.api.bukkit.scoreboard.CoreSidebarObjectiveEntry;
import eu.mcone.gameapi.api.player.GamePlayer;
import eu.mcone.gameapi.api.scoreboard.LobbyObjective;
import eu.mcone.ttt.TTT;

public class EndObjective extends LobbyObjective {

    public EndObjective() {
        super();
    }

    @Override
    protected void onLobbyRegister(CorePlayer corePlayer, CoreSidebarObjectiveEntry entry) {
        super.onRegister(corePlayer, entry);

        GamePlayer gamePlayer = TTT.getInstance().getGamePlayer(corePlayer.getUuid());
        entry.setTitle("§7§l⚔ §c§l§nTTT");
        entry.setScore(6, "");
        entry.setScore(5, "§8» §7Karma:");
        entry.setScore(4, "    §f§l" + gamePlayer.getStats().getGoals());
        entry.setScore(3, "");
        entry.setScore(2, "§8» §7Kills:");
        onReload(corePlayer, entry);
    }

    @Override
    protected void onLobbyReload(CorePlayer corePlayer, CoreSidebarObjectiveEntry entry) {
        GamePlayer gamePlayer = TTT.getInstance().getGamePlayer(corePlayer.getUuid());
        entry.setScore(1, "    §f§l" + gamePlayer.getRoundKills());
    }
}
