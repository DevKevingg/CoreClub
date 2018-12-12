package me.devkevin.core.punishments.command;

import org.bukkit.command.CommandExecutor;
import me.devkevin.core.punishments.*;
import me.devkevin.core.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import me.devkevin.core.utils.*;
import java.util.*;
import me.devkevin.core.Profile.*;
import me.devkevin.core.ranks.*;
import org.bukkit.*;

public class BlacklistCommand implements CommandExecutor
{
    private PunishmentManager manager;
    private CorePlugin plugin;

    public BlacklistCommand(final PunishmentManager manager) {
        this.plugin = CorePlugin.getPlugin();
        this.manager = manager;
        this.plugin.getCommand("blacklist").setExecutor((CommandExecutor)this);
    }

    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (!(sender instanceof Player)) {
            if (args.length < 1) {
                Messager.sendMessage(sender, CC.RED + "Usage: /blacklist <target> [reason] [-s] ", CC.YELLOW + " Permanently blacklist a player from the network.\nAdd a \"-s\" at the end to silently blacklist a player.", "");
            }
            else {
                final OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
                if (!target.hasPlayedBefore() && !target.isOnline()) {
                    Messager.sendMessage(sender, CC.RED + args[0] + " has never played before.");
                    return true;
                }
                String reason = "";
                for (int i = 2; i < args.length; ++i) {
                    reason = reason + args[i] + " ";
                }
                if (reason.equalsIgnoreCase("-s")) {
                    Messager.sendMessage(sender, CC.RED + "Please provide a valid reason.");
                    return true;
                }
                reason = reason.replace("-s", "");
                final boolean silent = Arrays.asList(args.length).contains("-s");
                if (target.isOnline()) {
                    target.getPlayer().kickPlayer(CC.RED + "You are blacklisted from uDrop Club.\n" + CC.GRAY + "You may not appeal this type of ban.\n" + CC.DARK_RED + "You may also not purchase an unban for this type of ban.");
                }
                if (!silent) {
                    Messager.sendMessage ( sender, CC.GREEN + target.getName() + " was blacklisted by " + sender.getName() + " for " + reason + ".");
                }
                else {
                    Messager.sendMessage ( sender, CC.RED + "ONLY STAFF: " + target.getName() + " was blacklisted by " + sender.getName() + ".");
                }
                Bukkit.getServer().dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "ban " + target.getName() + ' ' + reason + " -s");
            }
            return true;
        }
        final Player player = (Player)sender;
        final Profile profile = new Profile(player.getUniqueId());
        if (!profile.getRank().isAboveOrEqual(Rank.ADMIN)) {
            Messager.sendMessage(sender, CC.RED + "You don't have permission to use this command.");
            return true;
        }
        if (args.length < 1) {
            Messager.sendMessage(sender, CC.RED + "Usage: /blacklist <target> [reason] [-s] ", CC.YELLOW + " Permanently blacklist a player from the network.\nAdd a \"-s\" at the end to silently blacklist a player.", "");
        }
        else {
            final OfflinePlayer target2 = Bukkit.getOfflinePlayer(args[0]);
            if (!target2.hasPlayedBefore() && !target2.isOnline()) {
                Messager.sendMessage(sender, CC.RED + args[0] + " has never played before.");
                return true;
            }
            String reason2 = "";
            for (int j = 2; j < args.length; ++j) {
                reason2 = reason2 + args[j] + " ";
            }
            if (reason2.equalsIgnoreCase("-s")) {
                Messager.sendMessage(sender, CC.RED + "Please provide a valid reason.");
                return true;
            }
            reason2 = reason2.replace("-s", "");
            final boolean silent2 = Arrays.asList(args.length).contains("-s");
            if (target2.isOnline()) {
                target2.getPlayer().kickPlayer(CC.RED + "You are blacklisted from uDrop Club.\n" + CC.GRAY + "You may not appeal this type of ban.\n" + CC.DARK_RED + "You may also not purchase an unban for this type of ban.");
            }
            if (!silent2) {
                Bukkit.broadcastMessage(CC.GREEN + target2.getName() + " was blacklisted by " + sender.getName() + ".");
            }
            else {
                Messager.sendMessage ( sender, CC.RED + "ONLY STAFF: " + target2.getName() + " was blacklisted by " + sender.getName() + ".");
            }
            Bukkit.getServer().dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "ban " + target2.getName() + ' ' + reason2 + " -s");
        }
        return true;
    }

    public PunishmentManager getManager() {
        return this.manager;
    }

    public CorePlugin getPlugin() {
        return this.plugin;
    }
}

