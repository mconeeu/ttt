package eu.mcone.ttt.listener;

import eu.mcone.ttt.TTT;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveListener implements Listener {

    @EventHandler
    public void on(PlayerMoveEvent e) {
        Player player = e.getPlayer();

        if (InventoryTriggerListener.insideTester) {
            if (!InventoryTriggerListener.playerTester.contains(player)) {
                Location tester_1 = TTT.getInstance().getGameWorld().getBlockLocation("tester-block-1");
                Location tester_2 = TTT.getInstance().getGameWorld().getBlockLocation("tester-block-2");
                Location tester_3 = TTT.getInstance().getGameWorld().getBlockLocation("tester-block-3");
                Location tester_4 = TTT.getInstance().getGameWorld().getBlockLocation("tester-block-4");
                Location tester_5 = TTT.getInstance().getGameWorld().getBlockLocation("tester-block-5");
                Location tester_6 = TTT.getInstance().getGameWorld().getBlockLocation("tester-block-6");
                Location tester_7 = TTT.getInstance().getGameWorld().getBlockLocation("tester-block-7");
                Location tester_8 = TTT.getInstance().getGameWorld().getBlockLocation("tester-block-8");
                Location tester_9 = TTT.getInstance().getGameWorld().getBlockLocation("tester-block-9");

                if (player.getWorld().equals(tester_1.getWorld()) && player.getLocation().distance(tester_1) <= 2 ||
                        player.getWorld().equals(tester_2.getWorld()) && player.getLocation().distance(tester_2) <= 2 ||
                        player.getWorld().equals(tester_3.getWorld()) && player.getLocation().distance(tester_3) <= 2 ||
                        player.getWorld().equals(tester_4.getWorld()) && player.getLocation().distance(tester_4) <= 2 ||
                        player.getWorld().equals(tester_5.getWorld()) && player.getLocation().distance(tester_5) <= 2 ||
                        player.getWorld().equals(tester_6.getWorld()) && player.getLocation().distance(tester_6) <= 2 ||
                        player.getWorld().equals(tester_7.getWorld()) && player.getLocation().distance(tester_7) <= 2 ||
                        player.getWorld().equals(tester_8.getWorld()) && player.getLocation().distance(tester_8) <= 2 ||
                        player.getWorld().equals(tester_9.getWorld()) && player.getLocation().distance(tester_9) <= 2) {
                    TTT.getInstance().getGameWorld().teleportSilently(player, "outTester");
                }

            }
        }
    }
}
