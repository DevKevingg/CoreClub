package me.devkevin.core.staff.freeze.Listener.listeners;

import me.devkevin.core.staff.freeze.Listener.ListenerHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityListener implements Listener
{
    private ListenerHandler handler;
    
    public EntityListener(final ListenerHandler handler) {
        this.handler = handler;
    }
    
    @EventHandler
    public void onEntityDamage(final EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            final Player player = (Player)e.getEntity();
            if (this.handler.getPlugin().getManagerHandler().getFrozenManager().isFrozen(player.getUniqueId())) {
                e.setCancelled(true);
            }
        }
    }
    
    @EventHandler
    public void onEntityDamageByEntity(final EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player) {
            final Player player = (Player)e.getDamager();
            if (this.handler.getPlugin().getManagerHandler().getFrozenManager().isFrozen(player.getUniqueId())) {
                e.setCancelled(true);
            }
        }
    }
}
