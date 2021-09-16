package eu.mcone.ttt.listener;

import eu.mcone.gameapi.api.gamestate.common.InGameState;
import eu.mcone.ttt.TTT;
import eu.mcone.ttt.state.MiddleState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EntityDamageByEntityListener implements Listener {

    @EventHandler
    public void on(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
            if (TTT.getInstance().getGameStateManager().getRunning() instanceof MiddleState || TTT.getInstance().getGameStateManager().getRunning() instanceof InGameState) {
                if (TTT.getInstance().getGameStateManager().isCountdownRunning()) {
                    e.setCancelled(true);
                }
            } else {
                e.setCancelled(true);
            }
        }
    }

}
