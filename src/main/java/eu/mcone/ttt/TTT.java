package eu.mcone.ttt;

import eu.mcone.coresystem.api.bukkit.CorePlugin;
import eu.mcone.coresystem.api.bukkit.CoreSystem;
import eu.mcone.coresystem.api.bukkit.world.CoreWorld;
import eu.mcone.gameapi.api.GamePlugin;
import eu.mcone.gameapi.api.Option;
import eu.mcone.gameapi.api.gamestate.common.InGameState;
import eu.mcone.ttt.state.EndState;
import eu.mcone.ttt.state.LobbyState;
import lombok.Getter;
import org.bukkit.ChatColor;

public class TTT extends GamePlugin {

    protected TTT() {
        super("TTT", ChatColor.RED, "ttt.prefix",
                Option.BACKPACK_MANAGER_REGISTER_GADGET_CATEGORY,
                Option.BACKPACK_MANAGER_REGISTER_OUTFIT_CATEGORY,
                Option.BACKPACK_MANAGER_REGISTER_HAT_CATEGORY,
                Option.BACKPACK_MANAGER_REGISTER_TRAIL_CATEGORY,
                Option.BACKPACK_MANAGER_REGISTER_EXCLUSIVE_CATEGORY,
                Option.BACKPACK_MANAGER_USE_RANK_BOOTS);
    }

    @Getter
    private static TTT instance;
    @Getter
    private CoreWorld gameWorld;


    @Override
    public void onGameEnable() {
        instance = this;
        sendConsoleMessage("§aInitializing new GameState Handler...");
        getGameStateManager().addGameStateFirst(new LobbyState()).addGameState(new InGameState(60 * 45)).addGameState(new EndState()).startGame();
        getPlayerManager();
        getDamageLogger();
        getBackpackManager().setItemSlot(0);
        getBackpackManager().setFallbackSlot(0);

        sendConsoleMessage("§aRegistering Commands and Listeners...");

        gameWorld = CoreSystem.getInstance().getWorldManager().getWorld(getGameConfig().parseConfig().getGameWorld());



        sendConsoleMessage("§aVersion §f" + this.getDescription().getVersion() + "§a enabled...");


    }

    @Override
    public void onGameDisable() {

        sendConsoleMessage("§cPlugin disabled!");

    }
}
