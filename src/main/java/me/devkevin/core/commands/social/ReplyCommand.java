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

public class ReplyCommand implements CommandExecutor {

    @Getter
    private CorePlugin plugin;

    public ReplyCommand(final CorePlugin instance) {
        this.plugin = instance;
    }

    public boolean onCommand(final CommandSender sender, final Command cmd, final String CommandLabel, final String[] args) {
        if (sender instanceof Player) {
            final Player player = (Player)sender;
            if (args.length > 0) {
                if (args.length >= 1) {
                    if (this.plugin.reply.get(player.getName()) != null) {
                        final Player target = Bukkit.getPlayer((String)this.plugin.reply.get(player.getName()));
                        if (target != null) {
                            final StringBuilder sb = new StringBuilder();
                            for (int i = 0; i < args.length; ++i) {
                                sb.append(String.valueOf(args[i]) + " ");
                            }
                            final String message = sb.toString();
                            target.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7(From " + new Profile (player.getUniqueId()).getRank().getColor() + player.getName() + "&7) " + message));
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7(To " + new Profile(target.getUniqueId()).getRank().getColor() + target.getName() + "&7) " + message));
                            this.plugin.reply.put(target.getName(), player.getName());
                        }
                        else {
                            player.sendMessage(ChatColor.RED + "No player has written to you");
                        }
                    }
                    else {
                        player.sendMessage(ChatColor.RED + "No player has written to you");
                    }
                }
                else {
                    player.sendMessage(ChatColor.RED + "Usage: /r <Message>");
                }
            }
            else {
                player.sendMessage(ChatColor.RED + "Usage: /r <player> <Message>");
            }
        }
        else {
            sender.sendMessage(ChatColor.RED + "You are not a player");
        }
        return false;
    }
}
