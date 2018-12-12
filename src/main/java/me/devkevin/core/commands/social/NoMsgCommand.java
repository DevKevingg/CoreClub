package me.devkevin.core.commands.social;

import lombok.Getter;
import me.devkevin.core.CorePlugin;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NoMsgCommand implements CommandExecutor {

    @Getter
    private CorePlugin plugin;

    public NoMsgCommand(final CorePlugin instance) {
        this.plugin = instance;
    }

    public boolean onCommand(final CommandSender sender, final Command cmd, final String CommandLabel, final String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "You aren't a player");
            return true;
        }
        final Player player = (Player)sender;
        if (this.plugin.nomsg.contains(player)) {
            this.plugin.nomsg.remove(player);
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aou can now see private messages."));
        }
        else {
            this.plugin.nomsg.add(player);
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou can no longer see private messages."));
        }
        return false;
    }
}
