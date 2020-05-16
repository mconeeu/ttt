package eu.mcone.ttt.state;

import eu.mcone.gameapi.api.event.gamestate.GameStateStartEvent;
import eu.mcone.gameapi.api.event.gamestate.GameStateStopEvent;
import eu.mcone.ttt.scoreboard.InGameObjective;
import eu.mcone.ttt.scoreboard.Tablist;

public class InGameState extends eu.mcone.gameapi.api.gamestate.common.InGameState {

    static {
        setScoreboard(Tablist.class);
        setObjective(InGameObjective.class);
    }

    public InGameState() {
        super(60 * 45);
    }

    @Override
    public void onStart(GameStateStartEvent event) {
        super.onStart(event);
    }

    @Override
    public void onStop(GameStateStopEvent event) {
    }

}
