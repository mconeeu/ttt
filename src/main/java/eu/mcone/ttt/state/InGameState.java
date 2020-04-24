package eu.mcone.ttt.state;

import eu.mcone.coresystem.api.bukkit.CoreSystem;
import eu.mcone.coresystem.api.bukkit.player.CorePlayer;
import eu.mcone.gameapi.api.event.gamestate.GameStateStartEvent;
import eu.mcone.gameapi.api.event.gamestate.GameStateStopEvent;
import eu.mcone.ttt.TTT;
import eu.mcone.ttt.objectives.InGameObjective;
import org.bukkit.entity.Player;

public class InGameState extends eu.mcone.gameapi.api.gamestate.common.InGameState {

    public InGameState(int timeout) {
        super(timeout);
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
    public void onStop(GameStateStopEvent event) {
    }

}
