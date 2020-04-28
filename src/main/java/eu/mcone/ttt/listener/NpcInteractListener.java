package eu.mcone.ttt.listener;

import eu.mcone.coresystem.api.bukkit.event.npc.NpcInteractEvent;
import eu.mcone.coresystem.api.bukkit.npc.NpcData;
import eu.mcone.coresystem.api.bukkit.npc.data.PlayerNpcData;
import eu.mcone.coresystem.api.bukkit.util.Messenger;
import eu.mcone.coresystem.api.bukkit.world.CoreLocation;
import eu.mcone.coresystem.api.core.player.SkinInfo;
import eu.mcone.gameapi.api.player.GamePlayer;
import eu.mcone.ttt.TTT;
import net.minecraft.server.v1_8_R3.PacketPlayInUseEntity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class NpcInteractListener implements Listener {

    @EventHandler
    public void on(NpcInteractEvent e) {
        Player player = e.getPlayer();
        if (e.getNpc().getData().getType().equals(EntityType.PLAYER) && e.getAction().equals(PacketPlayInUseEntity.EnumEntityUseAction.INTERACT)) {
            GamePlayer gamePlayer = TTT.getInstance().getGamePlayer(player);

            TTT.getInstance().getMessenger().broadcast(Messenger.Broadcast.BroadcastMessageTyp.INFO_MESSAGE, "§7Die Leiche von §f" + player.getName() + "§7 wurde Identifiziert er war ein " + gamePlayer.getTeam().getName());

            e.getNpc().changeDisplayname(gamePlayer.getTeam().getColor() + e.getNpc().getData().getName());
            e.getNpc().update(
                    new NpcData
                            (
                                    EntityType.PLAYER,
                                    gamePlayer.getTeam().getColor() + e.getNpc().getData().getName(),
                                    player.getName(),
                                    new CoreLocation(player.getLocation()),
                                    new PlayerNpcData(
                                            player.getName(),
                                            "",
                                            SkinInfo.SkinType.PLAYER,
                                            false,
                                            false,
                                            false,
                                            null
                                    )
                            )
            );
        }
    }
}
