package eu.mcone.ttt.state;

import eu.mcone.gameapi.api.event.gamestate.GameStateStopEvent;
import eu.mcone.gameapi.api.gamestate.common.EndGameState;
import eu.mcone.ttt.TTT;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class EndState extends EndGameState {
    public EndState() {
    }

    @Override
    public void onStop(GameStateStopEvent event) {
        super.onStop(event);
        for (Player player : Bukkit.getOnlinePlayers()) {
            TTT.getInstance().getMessenger().send(player, "§7Der Server startet nun neu...");
        }

        Bukkit.getServer().shutdown();
    }
}
