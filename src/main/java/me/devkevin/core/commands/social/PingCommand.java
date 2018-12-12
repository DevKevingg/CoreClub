package me.devkevin.core.commands.social;

import lombok.Getter;
import me.devkevin.core.CorePlugin;
import me.devkevin.core.utils.CC;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class PingCommand implements CommandExecutor {

    @Getter
    private CorePlugin plugin;

    public PingCommand() {
        this.plugin = CorePlugin.getPlugin ();
    }

    public boolean onCommand(final CommandSender sender, final Command command, final String s, final String[] args) {
        Player toCheck;
        if (args.length == 0) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(CC.RED + "Usage: /ping <player>");
                return true;
            }
            toCheck = (Player)sender;
        }
        else {
            toCheck = Bukkit.getPlayer(StringUtils.join((Object[])args));
        }
        if (toCheck == null) {
            sender.sendMessage(CC.RED + "No player named '" + StringUtils.join((Object[])args) + "' found online.");
            return true;
        }
        sender.sendMessage(CC.LIGHT_PURPLE + toCheck.getName() + (toCheck.getName().endsWith("s") ? "'" : "'s") + CC.YELLOW + " ping: " + CC.YELLOW + this.getPing(toCheck) + "ms");
        if (sender instanceof Player && !toCheck.getName().equals(sender.getName())) {
            final Player senderPlayer = (Player)sender;
            sender.sendMessage(CC.LIGHT_PURPLE + "Ping difference: " + CC.YELLOW + (Math.max(this.getPing(senderPlayer), this.getPing(toCheck)) - Math.min(this.getPing(senderPlayer), this.getPing(toCheck))) + "ms" + CC.YELLOW + ".");
        }
        return true;
    }

    private int getPing(final Player player) {
        final int ping = ((CraftPlayer )player).getHandle().ping;
        return ping;
    }
}

