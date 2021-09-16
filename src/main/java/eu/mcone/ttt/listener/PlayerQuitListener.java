package eu.mcone.ttt.listener;

import eu.mcone.coresystem.api.bukkit.CoreSystem;
import eu.mcone.coresystem.api.bukkit.player.CorePlayer;
import eu.mcone.ttt.TTT;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.DisplaySlot;

public class PlayerQuitListener implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        for (CorePlayer all : CoreSystem.getInstance().getOnlineCorePlayers()) {
            all.getScoreboard().getObjective(DisplaySlot.SIDEBAR).reload();
        }

        TTT.getInstance().unregisterTTTPlayer(TTT.getInstance().getTTTPlayer(e.getPlayer()));
    }
}
