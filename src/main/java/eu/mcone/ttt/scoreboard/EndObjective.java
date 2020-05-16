package eu.mcone.ttt.scoreboard;

import eu.mcone.coresystem.api.bukkit.player.CorePlayer;
import eu.mcone.gameapi.api.player.GamePlayer;
import eu.mcone.ttt.TTT;

public class EndObjective extends LobbyObjective {

    public EndObjective() {
        super();
    }

    @Override
    protected void onRegister(CorePlayer corePlayer) {
        super.onRegister(corePlayer);

        GamePlayer gamePlayer = TTT.getInstance().getGamePlayer(corePlayer.getUuid());
        setDisplayName("§7§l⚔ §c§l§nTTT");
        setScore(6, "");
        setScore(5, "§8» §7Karma:");
        setScore(4, "    §f§l" + gamePlayer.getStats().getGoal());
        setScore(3, "");
        setScore(2, "§8» §7Kills:");
        onReload(corePlayer);
    }

    @Override
    protected void onReload(CorePlayer corePlayer) {
        GamePlayer gamePlayer = TTT.getInstance().getGamePlayer(corePlayer.getUuid());
        setScore(1, "    §f§l" + gamePlayer.getRoundKills());
    }
}
