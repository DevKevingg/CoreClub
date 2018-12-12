package me.devkevin.core.commands.social;

import lombok.Getter;
import me.devkevin.core.CorePlugin;
import me.devkevin.core.Profile.Profile;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MessageCommand implements CommandExecutor {

    @Getter
    private CorePlugin plugin;

    public MessageCommand(final CorePlugin instance) {
        this.plugin = instance;
    }

    public boolean onCommand(final CommandSender sender, final Command cmd, final String CommandLabel, final String[] args) {
        if (sender instanceof Player) {
            if (args.length <= 1) {
                sender.sendMessage(ChatColor.RED + "Usage: /msg <player> <message>");
                return false;
            }
            final Player player = (Player)sender;
            final Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cPlayer not found"));
            }
            else {
                final StringBuilder sb = new StringBuilder();
                for (int i = 1; i < args.length; ++i) {
                    sb.append(String.valueOf(args[i]) + " ");
                }
                final String message = sb.toString();
                if (this.plugin.nomsg.contains(target)) {
                    sender.sendMessage(ChatColor.RED + "The player is unable to receive messages");
                    return true;
                }
                target.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7(From " + new Profile (player.getUniqueId()).getRank().getColor() + player.getName() + "&7) " + message));
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7(To " + new Profile(target.getUniqueId()).getRank().getColor() + target.getName() + "&7) " + message));
                this.plugin.reply.put(target.getName(), player.getName());
            }
        }
        else {
            final Player target2 = Bukkit.getPlayer(args[0]);
            if (target2 == null) {
                sender.sendMessage(ChatColor.RED + "Player not found");
            }
            else {
                final StringBuilder sb2 = new StringBuilder();
                for (int j = 1; j < args.length; ++j) {
                    sb2.append(String.valueOf(args[j]) + " ");
                }
                final String message2 = sb2.toString();
                if (this.plugin.nomsg.contains(target2)) {
                    sender.sendMessage(ChatColor.RED + "The player is unable to receive messages");
                    return true;
                }
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7(To &aConsole&7)" + message2));
                target2.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7(From &dConsole&7)" + message2));
            }
        }
        return false;
    }
}
