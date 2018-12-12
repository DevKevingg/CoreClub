package me.devkevin.core.staff.freeze.Listener.listeners;

import me.devkevin.core.staff.freeze.Listener.ListenerHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class InventoryListener implements Listener
{
    private ListenerHandler handler;
    
    public InventoryListener(final ListenerHandler handler) {
        this.handler = handler;
    }
    
    @EventHandler
    public void onInvClose(final InventoryCloseEvent e) {
        final Player player = (Player)e.getPlayer();
        if (this.handler.getPlugin().getManagerHandler().getFrozenManager().isFrozen(player.getUniqueId())) {
            new BukkitRunnable() {
                public void run() {
                    player.openInventory(InventoryListener.this.handler.getPlugin().getManagerHandler().getInventoryManager().getFrozenInv());
                }
            }.runTaskLater((Plugin)this.handler.getPlugin(), 1L);
        }
    }
    
    @EventHandler
    public void onInvClick(final InventoryClickEvent e) {
        final Player player = (Player)e.getWhoClicked();
        if (this.handler.getPlugin().getManagerHandler().getFrozenManager().isFrozen(player.getUniqueId())) {
            e.setCancelled(true);
        }
    }
}
