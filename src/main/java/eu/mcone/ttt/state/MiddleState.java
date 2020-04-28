package eu.mcone.ttt.state;

import eu.mcone.coresystem.api.bukkit.CorePlugin;
import eu.mcone.coresystem.api.bukkit.CoreSystem;
import eu.mcone.coresystem.api.bukkit.player.CorePlayer;
import eu.mcone.coresystem.api.bukkit.util.Messenger;
import eu.mcone.gameapi.api.event.gamestate.GameStateCountdownEndEvent;
import eu.mcone.gameapi.api.event.gamestate.GameStateCountdownStartEvent;
import eu.mcone.gameapi.api.event.gamestate.GameStateStartEvent;
import eu.mcone.gameapi.api.gamestate.GameState;
import eu.mcone.ttt.TTT;
import eu.mcone.ttt.scoreboard.InGameObjective;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class MiddleState extends GameState {

    public MiddleState() {
        super("Middle State", 25);
    }

    @Override
    public void onStart(GameStateStartEvent event) {
        super.onStart(event);
        TTT.getInstance().getGameStateManager().startCountdown();

        for (Player player : TTT.getInstance().getPlayerManager().getPlaying()) {
            CoreSystem.getInstance().getCorePlayer(player).getScoreboard().setNewObjective(new InGameObjective());
            player.setLevel(1);
            player.setExp(0);
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
        TTT.getInstance().getMessenger().broadcast(Messenger.Broadcast.BroadcastMessageTyp.INFO_MESSAGE, "§cDas Grundlöse töten von Spielern ist verboten und wird mit einem §lBan bestraft!");
        for (Player all : Bukkit.getOnlinePlayers()) {
            all.setLevel(0);

        }


    }
}
