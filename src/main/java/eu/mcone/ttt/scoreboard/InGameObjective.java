package eu.mcone.ttt.scoreboard;

import eu.mcone.coresystem.api.bukkit.player.CorePlayer;
import eu.mcone.gameapi.api.player.GamePlayer;
import eu.mcone.ttt.TTT;

public class InGameObjective extends eu.mcone.gameapi.api.scoreboard.InGameObjective {

    public InGameObjective() {
        super("TTT");
    }

    @Override
    protected void onRegister(CorePlayer corePlayer) {
        super.onRegister(corePlayer);
        GamePlayer gamePlayer = TTT.getInstance().getGamePlayer(corePlayer.getUuid());

        setDisplayName("§7§l⚔ §c§l§nTTT");
        setScore(9, "");
        setScore(8, "§8» §7Rolle:");
        setScore(7, "   §f§l" + gamePlayer.getTeam().getPrefix());
        setScore(6, "");
        setScore(5, "§8» §7Kills:");
        setScore(3, "");
        setScore(2, "§8» §7Lebene Spieler:");
        onReload(corePlayer);

    }

    @Override
    protected void onReload(CorePlayer corePlayer) {
        super.onReload(corePlayer);

        GamePlayer gamePlayer = TTT.getInstance().getGamePlayer(corePlayer.getUuid());
        setScore(4, "   §f§l" + gamePlayer.getRoundKills());
        setScore(1, "   §f§l" + TTT.getInstance().getPlayerManager().getPlaying().size());
    }
}