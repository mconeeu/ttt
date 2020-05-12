package eu.mcone.ttt.listener;

import eu.mcone.coresystem.api.bukkit.CoreSystem;
import eu.mcone.gameapi.api.gamestate.common.EndGameState;
import eu.mcone.ttt.TTT;
import eu.mcone.ttt.state.EndState;
import eu.mcone.ttt.state.InGameState;
import eu.mcone.ttt.state.LobbyState;
import eu.mcone.ttt.state.MiddleState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamageListener implements Listener {

    @EventHandler
    public void on(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            Player player = (Player) e.getEntity();

            if (TTT.getInstance().getGameStateManager().getRunning() instanceof InGameState) {
                EntityDamageEvent.DamageCause damageCause = e.getCause();

                if (damageCause.equals(EntityDamageEvent.DamageCause.LAVA)
                        || damageCause.equals(EntityDamageEvent.DamageCause.FIRE)
                        || damageCause.equals(EntityDamageEvent.DamageCause.FIRE_TICK)
                        || damageCause.equals(EntityDamageEvent.DamageCause.FALL)) {
                    e.setCancelled(false);
                }
            } else {
                e.setCancelled(true);
                
                if (e.getCause().equals(EntityDamageEvent.DamageCause.VOID)) {
                    CoreSystem.getInstance().getWorldManager().getWorld(TTT.getInstance().getGameConfig().parseConfig().getLobby()).teleport(player, "spawn");
                }
            }
        }

    }
}
