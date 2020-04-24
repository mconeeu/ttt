package eu.mcone.ttt;

import eu.mcone.coresystem.api.bukkit.CoreSystem;
import eu.mcone.coresystem.api.bukkit.item.ItemBuilder;
import eu.mcone.coresystem.api.bukkit.world.CoreWorld;
import eu.mcone.gameapi.api.GamePlugin;
import eu.mcone.gameapi.api.Option;
import eu.mcone.gameapi.api.team.Team;
import eu.mcone.ttt.commands.DetectiveShopCMD;
import eu.mcone.ttt.commands.TTTCMD;
import eu.mcone.ttt.commands.TraitorShopCMD;
import eu.mcone.ttt.listener.GeneralPlayerListener;
import eu.mcone.ttt.listener.InventoryTriggerListener;
import eu.mcone.ttt.listener.PlayerDeathListener;
import eu.mcone.ttt.listener.PlayerJoinListener;
import eu.mcone.ttt.roles.Role;
import eu.mcone.ttt.scoreboard.Tablist;
import eu.mcone.ttt.state.EndState;
import eu.mcone.ttt.state.InGameState;
import eu.mcone.ttt.state.LobbyState;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;

public class TTT extends GamePlugin {

    protected TTT() {
        super("TTT", ChatColor.RED, "ttt.prefix",
                Option.BACKPACK_MANAGER_REGISTER_GADGET_CATEGORY,
                Option.BACKPACK_MANAGER_REGISTER_OUTFIT_CATEGORY,
                Option.BACKPACK_MANAGER_REGISTER_HAT_CATEGORY,
                Option.BACKPACK_MANAGER_REGISTER_TRAIL_CATEGORY,
                Option.BACKPACK_MANAGER_REGISTER_EXCLUSIVE_CATEGORY,
                Option.BACKPACK_MANAGER_USE_RANK_BOOTS,
                Option.USE_CUSTOM_TEAMS);
    }

    @Getter
    private static TTT instance;
    @Getter
    private CoreWorld gameWorld;


    @Override
    public void onGameEnable() {
        instance = this;
        sendConsoleMessage("§aInitializing new GameState Handler...");
        getGameStateManager().addGameStateFirst(new LobbyState()).addGameState(new InGameState()).addGameState(new EndState()).startGame();
        getPlayerManager();
        getDamageLogger();

        sendConsoleMessage("§aRegistering custom Teams");
        getTeamManager().addCustomTeam(new Team(Role.TRAITOR.getName(), 1, "§c|", ChatColor.RED, Color.RED, new ItemBuilder(Material.WOOL, 1, 12).create()));
        getTeamManager().addCustomTeam(new Team(Role.DETECTIVE.getName(), 2, "§1|", ChatColor.BLUE, Color.BLUE, new ItemBuilder(Material.WOOL, 1, 12).create()));
        getTeamManager().addCustomTeam(new Team(Role.INNOCENT.getName(), 3, "§a|", ChatColor.GREEN, Color.GREEN, new ItemBuilder(Material.WOOL, 1, 12).create()));
        getBackpackManager().setItemSlot(0);
        getBackpackManager().setFallbackSlot(0);

        getTeamManager().addTeamTablist(new Tablist());

        gameWorld = CoreSystem.getInstance().getWorldManager().getWorld(getGameConfig().parseConfig().getGameWorld());

        sendConsoleMessage("§aRegistering Commands and Listeners...");
        registerCommands(
                new DetectiveShopCMD(),
                new TraitorShopCMD(),
                new TTTCMD()

        );

        registerEvents(
                new GeneralPlayerListener(),
                new InventoryTriggerListener(),
                new PlayerDeathListener(),
                new PlayerJoinListener()
        );


        sendConsoleMessage("§aVersion §f" + this.getDescription().getVersion() + "§a enabled...");


    }

    @Override
    public void onGameDisable() {

        sendConsoleMessage("§cPlugin disabled!");

    }
}
