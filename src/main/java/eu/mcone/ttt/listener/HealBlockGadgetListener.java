package eu.mcone.ttt.listener;

import eu.mcone.ttt.TTT;
import eu.mcone.ttt.gadgets.DetectiveGadget;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;

public class HealBlockGadgetListener implements Listener {

    public static ArrayList<Location> HealblockLocation = new ArrayList<>();
    private int Gadgetcount = 0;

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        Player player = e.getPlayer();
        Block block = e.getBlock();

        if (block.getType().equals(DetectiveGadget.HEAL_STATION.getItem().getType())) {
            HealblockLocation.add(block.getLocation());
            player.playSound(player.getLocation(), Sound.FIREWORK_TWINKLE, 1, 1);
            e.setCancelled(false);
        }
    }

    @EventHandler
    public void on(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK) && e.getClickedBlock() != null) {
                Block block = e.getClickedBlock();
                if (HealblockLocation.contains(block.getLocation())) {

                    e.setCancelled(true);

                    player.playSound(player.getLocation(), Sound.DRINK, 1, 1);
                    player.addPotionEffect(PotionEffectType.HEALTH_BOOST.createEffect(60, 2));
                    TTT.getInstance().getMessenger().send(player, "§fDu hast Regeneration von der Heilstation erhalten!");


                    if (Gadgetcount == 1) {
                        block.setType(Material.AIR);
                        Gadgetcount = 0;
                    } else {
                        Gadgetcount = 1;
                    }
                }
            }
        }
    }

}
