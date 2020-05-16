package eu.mcone.ttt.listener;

import eu.mcone.coresystem.api.bukkit.item.ItemBuilder;
import eu.mcone.coresystem.api.bukkit.util.Messenger;
import eu.mcone.gameapi.api.backpack.defaults.DefaultCategory;
import eu.mcone.gameapi.api.player.GamePlayer;
import eu.mcone.ttt.TTT;
import eu.mcone.ttt.inventorys.RollInventar;
import eu.mcone.ttt.player.TTTItem;
import eu.mcone.ttt.state.EndState;
import eu.mcone.ttt.state.InGameState;
import eu.mcone.ttt.state.LobbyState;
import eu.mcone.ttt.state.MiddleState;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class InventoryTriggerListener implements Listener {

    public static final ItemStack ROLL_ITEM = new ItemBuilder(Material.EMERALD, 1, 0).displayName("§f§lRolle wählen §8» §7§oWähle deine Rolle aus.").create();

    public static final ItemStack IDENTIFY_STICK = new ItemBuilder(Material.STICK, 1, 0).displayName("§fIdentifizierer").create();

    private static boolean tester_trap = false;
    public static boolean insideTester = false;
    public static ArrayList<Player> playerTester = new ArrayList<>();

    public static final ItemStack IRON_SWORD_ITEM = new ItemBuilder(Material.IRON_SWORD, 1, 0).displayName("§fEisenschwert").create();
    public static final ItemStack STONE_SWORD_ITEM = new ItemBuilder(Material.STONE_SWORD, 1, 0).displayName("§fSteinschwert").create();
    public static final ItemStack WOOD_SWORD_ITEM = new ItemBuilder(Material.WOOD_SWORD, 1, 0).displayName("§fHolzschwert").create();
    public static final ItemStack BOW_ITEM = new ItemBuilder(Material.BOW, 1, 0).displayName("§fBogen").create();
    public static final ItemStack ARROWS_ITEM = new ItemBuilder(Material.ARROW, 32, 0).displayName("§fPfeile").create();

    @EventHandler
    public void on(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            ItemStack itemStack = e.getItem();
            if (itemStack != null) {
                if (TTT.getInstance().getGameStateManager().getRunning() instanceof LobbyState
                        || TTT.getInstance().getGameStateManager().getRunning() instanceof EndState) {
                    if (itemStack.getType().equals(Material.IRON_DOOR)) {
                        p.performCommand("hub");
                    } else if (itemStack.getType().equals(Material.EMERALD)) {
                        new RollInventar(p);
                    } else if (itemStack.hasItemMeta()) {
                        if (e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§3§lRucksack §8» §7§oZeige deine gesammelten Items an")) {
                            e.setCancelled(true);
                            TTT.getInstance().getBackpackManager().openBackpackInventory(DefaultCategory.GADGET.name(), p);
                        }
                        e.setCancelled(true);
                    }
                }
            }
            if (TTT.getInstance().getGameStateManager().getRunning() instanceof eu.mcone.ttt.state.InGameState || TTT.getInstance().getGameStateManager().getRunning() instanceof MiddleState) {
                if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK) && e.getClickedBlock() != null) {
                    Material clicked = e.getClickedBlock().getType();
                    switch (clicked) {
                        case TRAPPED_CHEST:
                        case CHEST: {
                            TTTItem item = TTT.getInstance().getTTTPlayer(p).getAndSaveNewTTTItem();

                            if (item != null) {
                                e.getClickedBlock().setType(Material.AIR);
                                p.playSound(p.getLocation(), Sound.ANVIL_LAND, 1, 1);
                                p.getInventory().addItem(item.getItems());
                            } else {
                                e.setCancelled(true);
                                TTT.getInstance().getMessenger().send(p, "§4Du hast bereits alle Items gesammelt!");
                                p.playSound(p.getLocation(), Sound.NOTE_BASS, 1, 1);
                            }

                            break;
                        }
                        case ENDER_CHEST: {
                            if (TTT.getInstance().getGameStateManager().getRunning() instanceof eu.mcone.ttt.state.InGameState) {
                                e.getClickedBlock().setType(Material.AIR);
                                p.getInventory().addItem(TTTItem.IRON_SWORD.getItems());
                                p.playSound(p.getLocation(), Sound.ANVIL_LAND, 1, 1);
                            } else {
                                e.setCancelled(true);
                                TTT.getInstance().getMessenger().send(p, "§4Du kannst diese Kiste erst nach der §cVorbereitungsphase§4 öffnen!");
                            }

                            break;
                        }
                        case STONE_BUTTON: {
                            GamePlayer gamePlayer = TTT.getInstance().getGamePlayer(p);
                            if (TTT.getInstance().getGameStateManager().getRunning() instanceof InGameState) {
                                if (!gamePlayer.getTeam().equals(TTT.getInstance().getDetectiveTeam())) {
                                    if (TTT.getInstance().getGameWorld().getBlockLocation("tester-button").equals(e.getClickedBlock().getLocation())) {
                                        if (!insideTester) {
                                            TTT.getInstance().getMessenger().broadcast(Messenger.Broadcast.BroadcastMessageTyp.INFO_MESSAGE, "§7Der Spieler §f" + p.getName() + "§7 hat den §fTraitor Tester §7betreten!");
                                            TTT.getInstance().getGameWorld().teleportSilently(p, "tester-inside-spawn");
                                            insideTester = true;
                                            playerTester.add(p);


                                            TTT.getInstance().getGameWorld().getBlockLocation("tester-lamp-1").getBlock().setType(Material.GLASS);
                                            TTT.getInstance().getGameWorld().getBlockLocation("tester-lamp-2").getBlock().setType(Material.GLASS);

                                            TTT.getInstance().getGameWorld().getBlockLocation("tester-redstone-1").getBlock().setType(Material.REDSTONE_BLOCK);
                                            TTT.getInstance().getGameWorld().getBlockLocation("tester-redstone-2").getBlock().setType(Material.REDSTONE_BLOCK);
                                            TTT.getInstance().getGameWorld().getBlockLocation("tester-redstone-3").getBlock().setType(Material.REDSTONE_BLOCK);

                                            Bukkit.getScheduler().runTaskLater(TTT.getGamePlugin(), () -> {
                                                p.playEffect(p.getLocation(), Effect.INSTANT_SPELL, 1);
                                                p.playSound(p.getLocation(), Sound.NOTE_STICKS, 1, 1);

                                                if (tester_trap) {
                                                    Bukkit.getScheduler().runTaskLater(TTT.getGamePlugin(), () -> {
                                                        TTT.getInstance().getGameWorld().getBlockLocation("tester-block-1").getBlock().setType(Material.AIR);
                                                        TTT.getInstance().getGameWorld().getBlockLocation("tester-block-2").getBlock().setType(Material.AIR);
                                                        TTT.getInstance().getGameWorld().getBlockLocation("tester-block-3").getBlock().setType(Material.AIR);
                                                        TTT.getInstance().getGameWorld().getBlockLocation("tester-block-4").getBlock().setType(Material.AIR);
                                                        TTT.getInstance().getGameWorld().getBlockLocation("tester-block-5").getBlock().setType(Material.AIR);
                                                        TTT.getInstance().getGameWorld().getBlockLocation("tester-block-6").getBlock().setType(Material.AIR);
                                                        TTT.getInstance().getGameWorld().getBlockLocation("tester-block-7").getBlock().setType(Material.AIR);
                                                        TTT.getInstance().getGameWorld().getBlockLocation("tester-block-8").getBlock().setType(Material.AIR);
                                                        TTT.getInstance().getGameWorld().getBlockLocation("tester-block-9").getBlock().setType(Material.AIR);
                                                        Bukkit.getScheduler().runTaskLater(TTT.getGamePlugin(), () -> {
                                                            tester_trap = false;
                                                            TTT.getInstance().getGameWorld().getBlockLocation("tester-block-1").getBlock().setType(Material.IRON_BLOCK);
                                                            TTT.getInstance().getGameWorld().getBlockLocation("tester-block-2").getBlock().setType(Material.IRON_BLOCK);
                                                            TTT.getInstance().getGameWorld().getBlockLocation("tester-block-2").getBlock().setType(Material.IRON_BLOCK);
                                                            TTT.getInstance().getGameWorld().getBlockLocation("tester-block-3").getBlock().setType(Material.IRON_BLOCK);
                                                            TTT.getInstance().getGameWorld().getBlockLocation("tester-block-4").getBlock().setType(Material.IRON_BLOCK);
                                                            TTT.getInstance().getGameWorld().getBlockLocation("tester-block-5").getBlock().setType(Material.IRON_BLOCK);
                                                            TTT.getInstance().getGameWorld().getBlockLocation("tester-block-6").getBlock().setType(Material.IRON_BLOCK);
                                                            TTT.getInstance().getGameWorld().getBlockLocation("tester-block-7").getBlock().setType(Material.IRON_BLOCK);
                                                            TTT.getInstance().getGameWorld().getBlockLocation("tester-block-8").getBlock().setType(Material.IRON_BLOCK);
                                                            TTT.getInstance().getGameWorld().getBlockLocation("tester-block-9").getBlock().setType(Material.IRON_BLOCK);
                                                        }, 17L);
                                                    }, 12L);
                                                }
                                                Bukkit.getScheduler().runTaskLater(TTT.getGamePlugin(), () -> {

                                                    if (gamePlayer.getTeam().equals(TTT.getInstance().getTraitorTeam())) {
                                                        TTT.getInstance().getGameWorld().getBlockLocation("tester-lamp-1").getBlock().setType(Material.REDSTONE_BLOCK);
                                                        TTT.getInstance().getGameWorld().getBlockLocation("tester-lamp-2").getBlock().setType(Material.REDSTONE_BLOCK);
                                                        p.playSound(p.getLocation(), Sound.WITHER_DEATH, 2, 2);
                                                    } else {
                                                        Block b1 = TTT.getInstance().getGameWorld().getBlockLocation("tester-lamp-1").getBlock();
                                                        b1.setType(gamePlayer.getTeam().getItem().getType());
                                                        b1.setData((byte) gamePlayer.getTeam().getItem().getDurability());

                                                        Block b2 = TTT.getInstance().getGameWorld().getBlockLocation("tester-lamp-2").getBlock();
                                                        b2.setType(gamePlayer.getTeam().getItem().getType());
                                                        b2.setData((byte) gamePlayer.getTeam().getItem().getDurability());

                                                        p.playSound(p.getLocation(), Sound.NOTE_PIANO, 2, 2);
                                                    }
                                                    p.playSound(p.getLocation(), Sound.NOTE_PLING, 1, 1);

                                                    Bukkit.getScheduler().runTaskLater(TTT.getGamePlugin(), () -> {

                                                        TTT.getInstance().getGameWorld().getBlockLocation("tester-redstone-1").getBlock().setType(Material.AIR);
                                                        TTT.getInstance().getGameWorld().getBlockLocation("tester-redstone-2").getBlock().setType(Material.AIR);
                                                        TTT.getInstance().getGameWorld().getBlockLocation("tester-redstone-3").getBlock().setType(Material.AIR);

                                                        TTT.getInstance().getGameWorld().getBlockLocation("tester-lamp-1").getBlock().setType(Material.REDSTONE_LAMP_OFF);
                                                        TTT.getInstance().getGameWorld().getBlockLocation("tester-lamp-2").getBlock().setType(Material.REDSTONE_LAMP_OFF);
                                                        Bukkit.getScheduler().runTaskLater(TTT.getGamePlugin(), () -> {
                                                            insideTester = false;
                                                            playerTester.remove(p);
                                                        }, 28L);
                                                    }, 25L);

                                                }, 55L);
                                            }, 2L);

                                        } else {
                                            TTT.getInstance().getMessenger().send(p, "§4Der Traitor-Tester wird bereits benutzt, bitte warte!");
                                        }

                                    } else if (TTT.getInstance().getGameWorld().getBlockLocation("tester-trap-button").equals(e.getClickedBlock().getLocation())) {
                                        if (gamePlayer.getTeam().equals(TTT.getInstance().getTraitorTeam())) {
                                            if (!tester_trap) {
                                                tester_trap = true;
                                                TTT.getInstance().getMessenger().broadcast(Messenger.Broadcast.BroadcastMessageTyp.INFO_MESSAGE, "§fDie Trator-Falle wurde ausgeführt!");
                                            } else {
                                                e.setCancelled(true);
                                                TTT.getInstance().getMessenger().send(p, "§4Die §c§lTraitor-Falle §4wurde bereits ausgeführt");
                                            }
                                        } else {
                                            TTT.getInstance().getMessenger().send(p, "§4Du musst ein Traitor dafür sein!");
                                            e.setCancelled(true);
                                        }
                                        break;
                                    }
                                } else {
                                    e.setCancelled(true);
                                    TTT.getInstance().getMessenger().send(p, "§4Als Detective kannst du den §cTraitor-Tester§4 nicht betreten!");
                                }
                            } else {
                                e.setCancelled(true);
                                TTT.getInstance().getMessenger().send(p, "§4Warte die Vorbereitungsphase ab!");
                            }
                        }
                    }
                }
            }
        }
    }
}
