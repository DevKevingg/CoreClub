package me.devkevin.core.staff.freeze.Listener.listeners;

import me.devkevin.core.staff.freeze.Listener.ListenerHandler;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener
{
    private ListenerHandler handler;
    
    public PlayerListener(final ListenerHandler handler) {
        this.handler = handler;
    }
    
    @EventHandler
    public void onQuit(final PlayerQuitEvent e) {
        final Player player = e.getPlayer();
        if (this.handler.getPlugin().getManagerHandler().getFrozenManager().isFrozen(player.getUniqueId())) {
            this.handler.getPlugin().getServer().broadcast(ChatColor.GOLD + player.getName() + " logged out while frozen.", "core.staff");
            this.handler.getPlugin().getManagerHandler().getFrozenManager().unfreezeUUID(player.getUniqueId());
            this.handler.getPlugin().getManagerHandler().getPlayerSnapshotManager().restorePlayer(player);
        }
    }
    
    @EventHandler
    public void onItemDrop(final PlayerDropItemEvent e) {
        if (this.handler.getPlugin().getManagerHandler().getFrozenManager().isFrozen(e.getPlayer().getUniqueId())) {
            e.setCancelled(true);
        }
    }
}
