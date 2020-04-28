package eu.mcone.ttt.listener;

import eu.mcone.coresystem.api.bukkit.CoreSystem;
import eu.mcone.coresystem.api.bukkit.item.ItemBuilder;
import eu.mcone.coresystem.api.bukkit.player.CorePlayer;
import eu.mcone.coresystem.api.bukkit.scoreboard.MainScoreboard;
import eu.mcone.gameapi.api.event.game.GameDrawEvent;
import eu.mcone.ttt.TTT;
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

public class GameDrawListener implements Listener {

    @EventHandler
    public void on(GameDrawEvent e) {
        TTT.getInstance().getGameStateManager().setGameState(new EndState(), true);
        TTT.getInstance().getGameStateManager().startCountdown();

        for (Player all : Bukkit.getOnlinePlayers()) {
            CorePlayer corePlayer = CoreSystem.getInstance().getCorePlayer(all);
            corePlayer.setScoreboard(new MainScoreboard());
            corePlayer.getScoreboard().setNewObjective(new EndObjective());

            all.getInventory().clear();
            all.getInventory().setArmorContents(new ItemStack[0]);
            all.getActivePotionEffects().clear();
            all.playSound(all.getLocation(), Sound.FIREWORK_BLAST, 1, 1);
            TTT.getInstance().getMessenger().send(all, "§7Team " + e.getTeam().getPrefix() + " §7hat das Spiel gewonnen!");
            CoreSystem.getInstance().getWorldManager().getWorld(TTT.getInstance().getGameConfig().parseConfig().getLobby()).teleport(all, "spawn");
            all.playSound(all.getLocation(), Sound.ENDERDRAGON_DEATH, 1, 1);
            all.setHealth(20);
            all.setFoodLevel(20);
            all.setLevel(0);
            all.setExp(0);

            all.getInventory().setArmorContents(null);
            all.setGameMode(GameMode.SURVIVAL);

            all.getInventory().setItem(8, InventoryTriggerListener.QUIT_ITEM);
            all.getInventory().setItem(7, new ItemBuilder(Material.STORAGE_MINECART, 1, 0).displayName("§3§lRucksack §8» §7§oZeige deine gesammelten Items an").create());

        }
    }
}
