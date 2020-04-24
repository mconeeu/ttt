package eu.mcone.ttt.listener;

import eu.mcone.coresystem.api.bukkit.item.ItemBuilder;
import eu.mcone.coresystem.api.bukkit.util.Messenger;
import eu.mcone.gameapi.api.backpack.defaults.DefaultCategory;
import eu.mcone.gameapi.api.gamestate.common.EndGameState;
import eu.mcone.gameapi.api.gamestate.common.InGameState;
import eu.mcone.gameapi.api.player.GamePlayer;
import eu.mcone.ttt.TTT;
import eu.mcone.ttt.roles.Role;
import eu.mcone.ttt.state.LobbyState;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class InventoryTriggerListener implements Listener {


    public static final ItemStack QUIT_ITEM = new ItemBuilder(Material.IRON_DOOR, 1, 0).displayName("§4§lVerlassem §8» §7§overlasse die Spielrunde.").create();
    public static final ItemStack IDENTIFY_STICK = new ItemBuilder(Material.STICK, 1, 0).displayName("§fIdentifizierer").create();

    private final ItemStack IRON_SWORD = new ItemBuilder(Material.IRON_SWORD, 1, 0).displayName("§fEisenschwert").create();
    private final ItemStack STONE_SWORD = new ItemBuilder(Material.IRON_DOOR, 1, 0).displayName("§fSteinschwert").create();
    private final ItemStack WOOD_SWORD = new ItemBuilder(Material.IRON_DOOR, 1, 0).displayName("§fHolzschwert").create();
    private final ItemStack BOW = new ItemBuilder(Material.BOW, 1, 0).displayName("§fBogen").create();
    private final ItemStack ARROWS = new ItemBuilder(Material.ARROW, 32, 0).displayName("§fPfeile").create();

    @EventHandler
    public void on(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            ItemStack itemStack = e.getItem();
            if (itemStack != null) {
                if (TTT.getInstance().getGameStateManager().getRunning() instanceof LobbyState
                        || TTT.getInstance().getGameStateManager().getRunning() instanceof EndGameState) {
                    if (itemStack.getType().equals(Material.IRON_DOOR)) {
                        player.performCommand("hub");
                    } else if (itemStack.hasItemMeta()) {
                        if (e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§3§lRucksack §8» §7§oZeige deine gesammelten Items an")) {
                            e.setCancelled(true);
                            TTT.getInstance().getBackpackManager().openBackpackInventory(DefaultCategory.GADGET.name(), player);
                        }
                        e.setCancelled(true);
                    }
                } else if (TTT.getInstance().getGameStateManager().getRunning() instanceof InGameState) {
                    if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK) && e.getClickedBlock() != null) {
                        Material clicked = e.getClickedBlock().getType();

                        switch (clicked) {
                            case CHEST: {

                                e.getClickedBlock().setType(Material.AIR);
                                final int rdm = new Random().nextInt(4);
                                switch (rdm) {
                                    case 0: {
                                        player.getInventory().addItem(STONE_SWORD);
                                        break;
                                    }
                                    case 1:
                                    case 2: {
                                        player.getInventory().addItem(WOOD_SWORD);
                                        break;
                                    }
                                    case 3: {
                                        player.getInventory().addItem(BOW);
                                        player.getInventory().addItem(ARROWS);
                                        break;
                                    }
                                }
                                player.playSound(player.getLocation(), Sound.ANVIL_USE, 3.0f, 3.0f);

                                break;
                            }
                            case ENDER_CHEST: {
                                e.getClickedBlock().setType(Material.AIR);
                                player.getInventory().addItem(IRON_SWORD);
                                break;
                            }
                            case STONE_BUTTON: {
                                if (TTT.getInstance().getGameWorld().getBlockLocation("tester-button").equals(e.getClickedBlock().getLocation())) {
                                    TTT.getInstance().getMessenger().broadcast(Messenger.Broadcast.BroadcastMessageTyp.INFO_MESSAGE, "Der Spieler §f" + player.getName() + "§7hat den Traitor Tester betreten!");
                                    TTT.getInstance().getGameWorld().teleportSilently(player, "tester-inside-spawn");


                                    TTT.getInstance().getGameWorld().getBlockLocation("tester-lamp-1").getBlock().setType(Material.GLASS);
                                    TTT.getInstance().getGameWorld().getBlockLocation("tester-lamp-2").getBlock().setType(Material.GLASS);

                                    TTT.getInstance().getGameWorld().getBlockLocation("tester-redstone-1").getBlock().setType(Material.REDSTONE_BLOCK);
                                    TTT.getInstance().getGameWorld().getBlockLocation("tester-redstone-2").getBlock().setType(Material.REDSTONE_BLOCK);
                                    TTT.getInstance().getGameWorld().getBlockLocation("tester-redstone-3").getBlock().setType(Material.REDSTONE_BLOCK);

                                    Bukkit.getScheduler().runTaskLater(TTT.getGamePlugin(), () -> {
                                        player.playEffect(player.getLocation(), Effect.INSTANT_SPELL, 1);
                                        player.playSound(player.getLocation(), Sound.NOTE_STICKS, 1, 1);
                                        Bukkit.getScheduler().runTaskLater(TTT.getGamePlugin(), () -> {

                                            GamePlayer gamePlayer = TTT.getInstance().getGamePlayer(player);

                                            if (gamePlayer.getTeam().getName().equalsIgnoreCase(Role.TRAITOR.getName())) {
                                                TTT.getInstance().getGameWorld().getBlockLocation("tester-lamp-1").getBlock().setType(Material.REDSTONE_BLOCK);
                                                TTT.getInstance().getGameWorld().getBlockLocation("tester-lamp-2").getBlock().setType(Material.REDSTONE_BLOCK);
                                            } else {
                                                TTT.getInstance().getGameWorld().getBlockLocation("tester-lamp-1").getBlock().setType(Material.WOOL);
                                                TTT.getInstance().getGameWorld().getBlockLocation("tester-lamp-2").getBlock().setType(Material.WOOL);
                                            }
                                            TTT.getInstance().getGameWorld().getBlockLocation("tester-redstone-1").getBlock().setType(Material.AIR);
                                            TTT.getInstance().getGameWorld().getBlockLocation("tester-redstone-2").getBlock().setType(Material.AIR);
                                            TTT.getInstance().getGameWorld().getBlockLocation("tester-redstone-3").getBlock().setType(Material.AIR);
                                            player.playSound(player.getLocation(), Sound.NOTE_PLING, 1, 1);

                                            Bukkit.getScheduler().runTaskLater(TTT.getGamePlugin(), () -> {
                                                TTT.getInstance().getGameWorld().getBlockLocation("tester-lamp-1").getBlock().setType(Material.GLASS);
                                                TTT.getInstance().getGameWorld().getBlockLocation("tester-lamp-2").getBlock().setType(Material.GLASS);
                                            },10L);

                                        }, 25L);
                                    }, 2L);


                                }
                                break;
                            }
                        }
                    }
                }
            }
        }
    }
}
