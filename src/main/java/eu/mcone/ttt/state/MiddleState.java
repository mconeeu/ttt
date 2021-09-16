package eu.mcone.ttt.state;

import eu.mcone.coresystem.api.bukkit.CorePlugin;
import eu.mcone.coresystem.api.bukkit.broadcast.SimpleBroadcast;
import eu.mcone.gameapi.api.event.gamestate.GameStateCountdownEndEvent;
import eu.mcone.gameapi.api.event.gamestate.GameStateStartEvent;
import eu.mcone.gameapi.api.gamestate.GameState;
import eu.mcone.gameapi.api.player.GamePlayerState;
import eu.mcone.ttt.TTT;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class MiddleState extends GameState {

    public MiddleState() {
        super("Middle State", 25);
    }

    @Override
    public void onStart(GameStateStartEvent event) {
        TTT.getInstance().getGameStateManager().startCountdown();

        for (Player player : TTT.getInstance().getPlayerManager().getPlayers(GamePlayerState.PLAYING)) {
            player.setLevel(1);
            player.setExp(0);
        }
    }

    @Override
    public void onCountdownSecond(CorePlugin plugin, int second) {
        switch (second) {
            case 20:
            case 10:
            case 3:
            case 2:
            case 1: {
                TTT.getInstance().getMessenger().broadcast(new SimpleBroadcast("§7Die Vorbereitungsphase endet in §f"+second+" Sekunden§7!"));
            }
        }
    }

    @Override
    public void onCountdownEnd(GameStateCountdownEndEvent event) {
        TTT.getInstance().getMessenger().broadcast(
                new SimpleBroadcast("§7Die Vorbereitungsphase ist nun §fbeendet!")
        );
        TTT.getInstance().getMessenger().broadcast(
                new SimpleBroadcast("§cDas Grundlöse töten von Spielern ist verboten und wird mit einem §lBan bestraft!")
        );

        for (Player all : Bukkit.getOnlinePlayers()) {
            all.setLevel(0);
        }
    }

}
