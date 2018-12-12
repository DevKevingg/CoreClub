package me.devkevin.core.punishments.listener;

import lombok.Getter;
import me.devkevin.core.CorePlugin;
import me.devkevin.core.punishments.PunishmentManager;
import me.devkevin.core.punishments.PunishmentType;
import me.devkevin.core.utils.CC;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.Plugin;

import java.util.UUID;

public class PunishmentListener implements Listener {

    @Getter
    private PunishmentManager manager;
    @Getter
    private CorePlugin plugin;

    public PunishmentListener(final PunishmentManager manager) {
        this.plugin = CorePlugin.getPlugin ();
        this.manager = manager;
        this.plugin.getServer ().getPluginManager ().registerEvents ((Listener)this, (Plugin)this.plugin);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onPlayerLogin(final AsyncPlayerChatEvent event) {
        final UUID uuid = event.getPlayer ().getUniqueId ();
        final PunishmentManager.Punishment punishment = this.manager.hasActivePunishment (uuid, PunishmentType.MUTE);
        if (punishment != null) {
            event.setCancelled (true);
            event.getPlayer ().sendMessage ( CC.RED + "Your account is current muted " + punishment.getMuteTimeLeft () + ".");
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onPlayerBanLogin(final PlayerLoginEvent event) {
        final UUID uuid = event.getPlayer ().getUniqueId ();
        final PunishmentManager.Punishment punishment = this.manager.hasActivePunishment (uuid, PunishmentType.BANNED);
        if (punishment != null) {
            event.disallow(PlayerLoginEvent.Result.KICK_FULL, CC.RED + "You are permanently banned from uDrop Club.\n" + CC.GRAY + "If you feel this ban is unjustified, email appeal@udrop.club.\n" + CC.GOLD + "You may also purchase an unban at store.udrop.club." );
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onPlayerBlacklistLogin(final PlayerLoginEvent event) {
        final UUID uuid = event.getPlayer ().getUniqueId ();
        final PunishmentManager.Punishment punishment = this.manager.hasActivePunishment (uuid, PunishmentType.BLACKLISTED);
        if (punishment != null) {
            event.disallow(PlayerLoginEvent.Result.KICK_OTHER, CC.RED + "You are blacklisted from uDrop Club.\n" + CC.GRAY + "You may not appeal this type of ban.\n" + CC.DARK_RED + "You may also not purchase an unban for this type of ban.");
        }
    }
}
