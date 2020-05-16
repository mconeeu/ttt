package eu.mcone.ttt.scoreboard;

import eu.mcone.coresystem.api.bukkit.player.CorePlayer;
import org.bukkit.Bukkit;

public class LobbyObjective extends eu.mcone.gameapi.api.scoreboard.LobbyObjective {

    @Override
    protected void onRegister(CorePlayer corePlayer) {
        super.onRegister(corePlayer);
        setDisplayName("§7§l⚔ §c§l§nTTT");

        setScore(3, "");
        setScore(2, "§8» §7Wartende Spieler:");
        onReload(corePlayer);
    }

    @Override
    protected void onReload(CorePlayer corePlayer) {
        super.onReload(corePlayer);
        setScore(1, "§f  " + Bukkit.getOnlinePlayers().size());
    }

}
