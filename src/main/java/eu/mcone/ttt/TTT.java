package eu.mcone.ttt;

import eu.mcone.coresystem.api.bukkit.CoreSystem;
import eu.mcone.coresystem.api.bukkit.item.ItemBuilder;
import eu.mcone.coresystem.api.bukkit.player.CorePlayer;
import eu.mcone.coresystem.api.bukkit.world.CoreWorld;
import eu.mcone.gameapi.api.GamePlugin;
import eu.mcone.gameapi.api.Option;
import eu.mcone.gameapi.api.team.Team;
import eu.mcone.ttt.commands.PassesCMD;
import eu.mcone.ttt.commands.TTTCMD;
import eu.mcone.ttt.commands.ShopCMD;
import eu.mcone.ttt.gadgets.HealBlockGadget;
import eu.mcone.ttt.listener.*;
import eu.mcone.ttt.player.TTTPlayer;
import eu.mcone.ttt.roles.Role;
import eu.mcone.ttt.scoreboard.Tablist;
import eu.mcone.ttt.state.EndState;
import eu.mcone.ttt.state.InGameState;
import eu.mcone.ttt.state.LobbyState;
import eu.mcone.ttt.state.MiddleState;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class TTT extends GamePlugin {

    public TTT() {
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

    @Getter
    private List<TTTPlayer> players;

    @Override
    public void onGameEnable() {
        instance = this;
        players = new ArrayList<>();
        gameWorld = CoreSystem.getInstance().getWorldManager().getWorld(getGameConfig().parseConfig().getGameWorld());

        sendConsoleMessage("§aInitializing new GameState Handler...");
        getGameStateManager().addGameStateFirst(new LobbyState()).addGameState(new MiddleState()).addGameState(new InGameState()).addGameState(new EndState()).startGame();
        getPlayerManager();
        getDamageLogger();
        getTeamManager().addTeamChat(new PlayerChatListener());

        sendConsoleMessage("§aRegistering custom Teams");
        getTeamManager().addCustomTeam(new Team(Role.TRAITOR.getName(), 1, "§cTraitor", ChatColor.RED, Color.RED, new ItemBuilder(Material.WOOL, 1, 12).create()));
        getTeamManager().addCustomTeam(new Team(Role.DETECTIVE.getName(), 2, "§1Detective", ChatColor.BLUE, Color.BLUE, new ItemBuilder(Material.WOOL, 1, 11).create()));
        getTeamManager().addCustomTeam(new Team(Role.INNOCENT.getName(), 3, "§aInnocent", ChatColor.GREEN, Color.GREEN, new ItemBuilder(Material.WOOL, 1, 13).create()));
        getBackpackManager().setItemSlot(1);
        getBackpackManager().setFallbackSlot(1);

        getTeamManager().addTeamTablist(Tablist.class);

        sendConsoleMessage("§aRegistering Commands and Listeners...");
        registerCommands(
                new ShopCMD(),
                new TTTCMD(),
                new PassesCMD()

        );

        registerEvents(
                new GeneralPlayerListener(),
                new InventoryTriggerListener(),
                new PlayerDeathListener(),
                new PlayerJoinListener(),
                new PlayerQuitListener(),
                new TeamWonListener(),
                new EntityDamageListener(),
                new EntityDamageByEntityListener(),
                new GameDrawListener(),
                new PlayerMoveListener(),
                new NpcInteractListener(),
                new HealBlockGadget()
        );


        sendConsoleMessage("§aVersion §f" + this.getDescription().getVersion() + "§a enabled...");


    }

    @Override
    public void onGameDisable() {

        sendConsoleMessage("§cPlugin disabled!");

    }

    public TTTPlayer getTTTPlayer(CorePlayer cp) {
        return getTTTPlayer(cp.getUuid());
    }

    public TTTPlayer getTTTPlayer(Player p) {
        return getTTTPlayer(p.getUniqueId());
    }

    public TTTPlayer getTTTPlayer(UUID uuid) {
        for (TTTPlayer ttp : players) {
            if (ttp.getCorePlayer().getUuid().equals(uuid)) {
                return ttp;
            }
        }
        return null;
    }

    public TTTPlayer getTTTPlayer(String name) {
        for (TTTPlayer ttp : players) {
            if (ttp.getCorePlayer().getName().equals(name)) {
                return ttp;
            }
        }
        return null;
    }

    public Collection<TTTPlayer> getOnlineTTTPlayers() {
        return new ArrayList<>(players);
    }

    public void registerTTTPlayer(TTTPlayer ttp) {
        players.add(ttp);
    }

    public void unregisterTTTPlayer(TTTPlayer ttp) {
        players.remove(ttp);
    }

}
