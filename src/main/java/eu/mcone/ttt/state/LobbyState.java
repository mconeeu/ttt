package eu.mcone.ttt.state;

import eu.mcone.coresystem.api.bukkit.CorePlugin;
import eu.mcone.coresystem.api.bukkit.CoreSystem;
import eu.mcone.coresystem.api.bukkit.item.ItemBuilder;
import eu.mcone.coresystem.api.bukkit.player.CorePlayer;
import eu.mcone.coresystem.api.bukkit.util.Messager;
import eu.mcone.gameapi.api.event.gamestate.GameStateStopEvent;
import eu.mcone.gameapi.api.gamestate.common.LobbyGameState;
import eu.mcone.gameapi.api.player.GamePlayer;
import eu.mcone.gameapi.api.team.TeamTablist;
import eu.mcone.ttt.TTT;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

public class LobbyState extends LobbyGameState {

    @Override
    public void onStop(GameStateStopEvent event) {
        for (Player player : TTT.getInstance().getPlayerManager().getPlaying()) {
            GamePlayer gamePlayer = TTT.getInstance().getGamePlayer(player);
            CorePlayer corePlayer = CoreSystem.getInstance().getCorePlayer(player.getUniqueId());
            corePlayer.setScoreboard(new TeamTablist());
            player.teleport(TTT.getInstance().getGameWorld().getLocation(gamePlayer.getTeam().getSpawnLocation()));
            player.getInventory().clear();

        }
    }

    @Override
    public void onCountdownSecond(CorePlugin plugin, int second) {
        super.onCountdownSecond(plugin, second);

        if (second == 5) {

            TTT.getInstance().getMessager().broadcast(Messager.Broadcast.BroadcastMessageTyp.INFO_MESSAGE, "§7Die §fRollen §7stehen nun fest.");
        }
    }
}
