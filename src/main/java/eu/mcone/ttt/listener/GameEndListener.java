package eu.mcone.ttt.listener;

import eu.mcone.coresystem.api.bukkit.CoreSystem;
import eu.mcone.coresystem.api.bukkit.item.ItemBuilder;
import eu.mcone.coresystem.api.bukkit.player.CorePlayer;
import eu.mcone.coresystem.api.bukkit.scoreboard.MainScoreboard;
import eu.mcone.gameapi.api.GamePlugin;
import eu.mcone.gameapi.api.event.game.GameDrawEvent;
import eu.mcone.gameapi.api.event.team.TeamWonEvent;
import eu.mcone.gameapi.api.team.Team;
import eu.mcone.ttt.TTT;
import eu.mcone.ttt.roles.Role;
import eu.mcone.ttt.scoreboard.EndObjective;
import eu.mcone.ttt.state.EndState;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public class GameEndListener implements Listener {

    @EventHandler
    public void onTeamWon(TeamWonEvent e) {
        preparePlayersForEnd(e.getTeam());
    }

    @EventHandler
    public void onDraw(GameDrawEvent e) {
        preparePlayersForEnd(TTT.getInstance().getTeamManager().getTeam(Role.INNOCENT.getName()));
    }

    private static void preparePlayersForEnd(Team team) {
        TTT.getInstance().getGameStateManager().setGameState(TTT.getInstance().getGameStateManager().getNextGameState(), true);

        for (Player player : Bukkit.getOnlinePlayers()) {
            GamePlugin.getGamePlugin().getPlayerManager().setPlaying(player, false);
            GamePlugin.getGamePlugin().getPlayerManager().setSpectating(player, false);

            CorePlayer corePlayer = CoreSystem.getInstance().getCorePlayer(player);
            corePlayer.setScoreboard(new MainScoreboard());
            corePlayer.getScoreboard().setNewObjective(new EndObjective());

            player.getInventory().clear();
            player.getInventory().setArmorContents(new ItemStack[0]);
            player.getActivePotionEffects().clear();
            player.playSound(player.getLocation(), Sound.FIREWORK_BLAST, 1, 1);
            TTT.getInstance().getMessenger().send(player, "§7Team " + team.getPrefix() + " §7hat das Spiel gewonnen!");
            CoreSystem.getInstance().getWorldManager().getWorld(TTT.getInstance().getGameConfig().parseConfig().getLobby()).teleport(player, "spawn");
            player.playSound(player.getLocation(), Sound.ENDERDRAGON_DEATH, 1, 1);
            player.setHealth(20);
            player.setFoodLevel(20);
            player.setLevel(0);
            player.setExp(0);

            player.getInventory().setArmorContents(null);
            player.setGameMode(GameMode.SURVIVAL);

            player.getInventory().setItem(8, InventoryTriggerListener.QUIT_ITEM);
            player.getInventory().setItem(7, new ItemBuilder(Material.STORAGE_MINECART, 1, 0).displayName("§3§lRucksack §8» §7§oZeige deine gesammelten Items an").create());
        }
    }

}
