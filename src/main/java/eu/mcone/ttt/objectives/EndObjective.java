package eu.mcone.ttt.objectives;

import eu.mcone.coresystem.api.bukkit.player.CorePlayer;
import eu.mcone.gameapi.api.player.GamePlayer;
import eu.mcone.gameapi.api.scoreboard.EndGameObjective;
import eu.mcone.ttt.TTT;

public class EndObjective extends EndGameObjective {

    public EndObjective() {
        super("TTT");
    }

    @Override
    protected void onRegister(CorePlayer corePlayer) {
        super.onRegister(corePlayer);

        GamePlayer gamePlayer = TTT.getInstance().getGamePlayer(corePlayer.getUuid());
        setDisplayName("§7§l⚔ §c§l§nTTT");
        setScore(6, "");
        setScore(5, "§8» §7Team:");
        setScore(4, "    §f§l" + gamePlayer.getTeam().getTeam().getPrefix());
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
