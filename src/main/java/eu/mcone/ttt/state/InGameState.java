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
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;

public class InGameState extends eu.mcone.gameapi.api.gamestate.common.InGameState {

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
