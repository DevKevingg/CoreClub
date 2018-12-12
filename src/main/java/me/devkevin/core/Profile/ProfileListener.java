package me.devkevin.core.Profile;

import lombok.Getter;
import me.devkevin.core.CorePlugin;
import me.devkevin.core.ranks.Rank;
import me.devkevin.core.utils.CC;
import me.devkevin.core.utils.Messager;
import org.bukkit.event.Listener;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

public class ProfileListener implements Listener {

    @Getter
    private CorePlugin plugin;


    @EventHandler
    public void onConnect(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Profile profile = new Profile(player.getUniqueId());
        if(!profile.isExists()) {
            profile.create();
        }
        Rank rank = profile.getRank();
        String userTag = rank.getPrefix() + rank.getColor() + player.getName();
        if (!player.getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', userTag))) {
            player.setDisplayName(ChatColor.translateAlternateColorCodes('&', userTag));
        }
    }


    @EventHandler
    public void onChatAt(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        Profile profile = new Profile(player.getUniqueId());
        event.setFormat("%1$s" + ChatColor.WHITE + ": %2$s");
        player.setDisplayName(player.getName());
        Rank rank = profile.getRank();
        String userTag = rank.getPrefix() + rank.getColor() + player.getName();
        if (!player.getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', userTag))) {
            player.setDisplayName(ChatColor.translateAlternateColorCodes('&', userTag));
        }
    }
}
