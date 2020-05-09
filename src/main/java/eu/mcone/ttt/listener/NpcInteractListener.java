package eu.mcone.ttt.listener;

import com.mojang.authlib.properties.Property;
import eu.mcone.coresystem.api.bukkit.event.npc.NpcInteractEvent;
import eu.mcone.coresystem.api.bukkit.npc.NpcData;
import eu.mcone.coresystem.api.bukkit.npc.data.PlayerNpcData;
import eu.mcone.coresystem.api.bukkit.npc.entity.PlayerNpc;
import eu.mcone.coresystem.api.bukkit.util.Messenger;
import eu.mcone.coresystem.api.bukkit.world.CoreLocation;
import eu.mcone.coresystem.api.core.player.SkinInfo;
import eu.mcone.gameapi.api.GameAPI;
import eu.mcone.gameapi.api.player.GamePlayer;
import eu.mcone.ttt.TTT;
import eu.mcone.ttt.roles.Role;
import net.minecraft.server.v1_8_R3.PacketPlayInUseEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public class NpcInteractListener implements Listener {

    @EventHandler
    public void on(NpcInteractEvent e) {
        Player player = e.getPlayer();
        if (e.getNpc().getData().getType().equals(EntityType.PLAYER) && e.getAction().equals(PacketPlayInUseEntity.EnumEntityUseAction.INTERACT)) {
            GamePlayer gamePlayer = TTT.getInstance().getGamePlayer(player);
            GamePlayer deadPlayer = GameAPI.getInstance().getGamePlayer(e.getNpc().getData().getName());

            if (player.getItemInHand().getType().equals(InventoryTriggerListener.IDENTIFY_STICK.getType())) {
                if (gamePlayer.getTeam().getName().equalsIgnoreCase(Role.DETECTIVE.getName())) {
                    TTT.getInstance().getMessenger().broadcast(Messenger.Broadcast.BroadcastMessageTyp.INFO_MESSAGE, "§7Die Leiche von §f" + e.getNpc().getData().getName() + "§7 wurde Identifiziert er war ein " + deadPlayer.getTeam().getName());

                    Property textures = ((CraftPlayer) deadPlayer.bukkit()).getHandle().getProfile().getProperties().get("textures").iterator().next();
                    ((PlayerNpc) e.getNpc()).setSkin(new SkinInfo(
                            e.getNpc().getData().getName(),
                            textures.getValue(),
                            textures.getSignature(),
                            SkinInfo.SkinType.PLAYER
                    ));
                    e.getNpc().changeDisplayname(deadPlayer.getTeam().getChatColor() + e.getNpc().getData().getName());
                } else {
                    TTT.getInstance().getMessenger().send(player, "§7Nur der §fDetector §7kann die Leiche §fidentifizieren§7!");
                }
            } else {
                TTT.getInstance().getMessenger().send(player, "§7Nehme dein §fIdentifizierer §7in die Hand!");
            }
        }
    }
}
