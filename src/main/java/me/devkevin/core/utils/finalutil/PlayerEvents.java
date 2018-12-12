package me.devkevin.core.utils.finalutil;

import me.devkevin.core.Profile.Profile;
import me.devkevin.core.commands.staff.MuteChatCommand;
import me.devkevin.core.ranks.Rank;
import me.devkevin.core.staff.StaffChatHandler;
import me.devkevin.core.utils.CC;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;

public class PlayerEvents implements Listener {

    @EventHandler
    public void onJoin(final PlayerJoinEvent event) {
        event.setJoinMessage ( null );
    }

    @EventHandler
    public void onLeave(final PlayerQuitEvent event) {
        event.setQuitMessage ( null );
    }

    @EventHandler
    public void onPlayerChat(final AsyncPlayerChatEvent event) {
        final Player p = event.getPlayer ();
        final Profile profile = new Profile ( p.getUniqueId () );
        if (MuteChatCommand.muteToggled) {
            if (!profile.getRank ().isAboveOrEqual ( Rank.TRAINEE )) {
                event.setCancelled ( true );
                p.sendMessage ( ChatColor.translateAlternateColorCodes ( '&' , "&cChat is currently muted." ) );
            } else if (!profile.getRank ().isAboveOrEqual ( Rank.TRAINEE )) {
                event.setCancelled ( false );
            } else if (MuteChatCommand.muteToggled) {
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onStaffChat(final AsyncPlayerChatEvent event) {
        final Player player = event.getPlayer();
        if (StaffChatHandler.isStaffChat(player)) {
            event.setCancelled(true);
            for (final Player online : Bukkit.getServer().getOnlinePlayers()) {
                if (new Profile (online.getUniqueId()).getRank().isAboveOrEqual(Rank.TRAINEE)) {
                    online.sendMessage(CC.AQUA + "[S] " + new Profile (player.getUniqueId()).getRank().getColor() + player.getName() + CC.WHITE + ": " + CC.WHITE + event.getMessage());
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onCommandProcess(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();

        if (event.getMessage().toLowerCase().startsWith("//calc") || event.getMessage().toLowerCase().startsWith("//eval") || event.getMessage().toLowerCase().startsWith("//solve") || event.getMessage().toLowerCase().startsWith("/bukkit:") || event.getMessage().toLowerCase().startsWith("/me") || event.getMessage().toLowerCase().startsWith("/bukkit:me") || event.getMessage().toLowerCase().startsWith("/minecraft:") || event.getMessage().toLowerCase().startsWith("/minecraft:me")) {
            player.sendMessage(ChatColor.RED + "You cannot perform this command.");
            event.setCancelled(true);
        }
    }
}
