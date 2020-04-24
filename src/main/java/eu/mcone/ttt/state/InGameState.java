package eu.mcone.ttt.state;

import eu.mcone.coresystem.api.bukkit.CorePlugin;
import eu.mcone.coresystem.api.bukkit.CoreSystem;
import eu.mcone.coresystem.api.bukkit.player.CorePlayer;
import eu.mcone.coresystem.api.bukkit.util.Messenger;
import eu.mcone.gameapi.api.event.gamestate.GameStateCountdownEndEvent;
import eu.mcone.gameapi.api.event.gamestate.GameStateStartEvent;
import eu.mcone.gameapi.api.event.gamestate.GameStateStopEvent;
import eu.mcone.ttt.TTT;
import eu.mcone.ttt.scoreboard.InGameObjective;
import org.bukkit.entity.Player;

public class InGameState extends eu.mcone.gameapi.api.gamestate.common.InGameState {

    public InGameState() {
        super(25, 60 * 45);
    }

    @Override
    public void onStart(GameStateStartEvent event) {
        super.onStart(event);

        for (Player player : TTT.getInstance().getPlayerManager().getPlaying()) {
            CorePlayer corePlayer = CoreSystem.getInstance().getCorePlayer(player);
            corePlayer.getScoreboard().setNewObjective(new InGameObjective());
        }
    }

    @Override
    public void onCountdownSecond(CorePlugin plugin, int second) {
        super.onCountdownSecond(plugin, second);

        if (second == 20) {
            TTT.getInstance().getMessenger().broadcast(Messenger.Broadcast.BroadcastMessageTyp.INFO_MESSAGE, "§7Die Vorbereitungsphase endet in §f20 Sekunden!");
        } else if (second == 10) {
            TTT.getInstance().getMessenger().broadcast(Messenger.Broadcast.BroadcastMessageTyp.INFO_MESSAGE, "§7Die Vorbereitungsphase endet in §f10 Sekunden!");
        } else if (second == 3) {
            TTT.getInstance().getMessenger().broadcast(Messenger.Broadcast.BroadcastMessageTyp.INFO_MESSAGE, "§7Die Vorbereitungsphase endet in §f3 Sekunden!");
        } else if (second == 2) {
            TTT.getInstance().getMessenger().broadcast(Messenger.Broadcast.BroadcastMessageTyp.INFO_MESSAGE, "§7Die Vorbereitungsphase endet in §f2 Sekunden!");
        } else if (second == 1) {
            TTT.getInstance().getMessenger().broadcast(Messenger.Broadcast.BroadcastMessageTyp.INFO_MESSAGE, "§7Die Vorbereitungsphase endet in §f1 Sekunden!");
        }
    }

    @Override
    public void onCountdownEnd(GameStateCountdownEndEvent event) {
        super.onCountdownEnd(event);

        TTT.getInstance().getMessenger().broadcast(Messenger.Broadcast.BroadcastMessageTyp.INFO_MESSAGE, "§7Die Vorbereitungsphase ist nun §fbeendet!");

    }

    @Override
    public void onStop(GameStateStopEvent event) {
    }

}
