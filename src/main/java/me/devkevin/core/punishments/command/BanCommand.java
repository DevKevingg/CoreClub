package me.devkevin.core.punishments.command;

import lombok.Getter;
import me.devkevin.core.CorePlugin;
import me.devkevin.core.Profile.Profile;
import me.devkevin.core.punishments.PunishmentManager;
import me.devkevin.core.punishments.PunishmentType;
import me.devkevin.core.ranks.Rank;
import me.devkevin.core.utils.CC;
import me.devkevin.core.utils.Messager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.UUID;

public class BanCommand implements CommandExecutor {

    @Getter
    private PunishmentManager manager;
    @Getter
    private CorePlugin plugin;

    public BanCommand(final PunishmentManager manager) {
        this.plugin = CorePlugin.getPlugin ();
        this.manager = manager;
        this.plugin.getCommand ("ban").setExecutor ((CommandExecutor)this);
    }


    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (!(sender instanceof Player)) {
            if (args.length < 2) {
                Messager.sendMessage(sender, CC.RED + "Usage: /ban <target> [reason] [-s] " + CC.YELLOW + " Permanently ban a player from the network. \nAdd a \"-s\" at the end to silently ban a player." );
                return true;
            } else {
                final OfflinePlayer target = Bukkit.getOfflinePlayer ( args[0] );
                if (!target.hasPlayedBefore () && !target.isOnline ()) {
                    Messager.sendMessage(sender, CC.RED + target.getName () + " has never played before, but will be banned." );
                }
                String reason = "";
                for (int i = 1; i < args.length; ++i) {
                    reason = reason + args[i] + " ";
                }
                if (reason.equalsIgnoreCase ( "-s" )) {
                    Messager.sendMessage(sender, CC.RED + "Please provide a valid reason." );
                    return true;
                }
                reason = reason.replace ( "-s" , "" );
                final boolean silent = Arrays.asList ( args ).contains ( "-s" );
                if (target.isOnline ()) {
                    target.getPlayer ().kickPlayer ( CC.RED + "You are permanently banned from uDrop Club.\n" + CC.GRAY + "If you feel this ban is unjustified, email appeal@udrop.club.\n" + CC.GOLD + "You may also purchase an unban at store.udrop.club." );
                }
                if (!silent) {
                    Bukkit.broadcastMessage ( CC.GREEN + target.getName () + " was banned permanently by " + sender.getName () + " for " + reason + '.' );
                } else {
                    Messager.sendMessage (sender, CC.RED + "ONLY STAFF: " + target.getName () + " was banned permanently by " + sender.getName () + " for " + reason + '.' );
                }
                final PunishmentManager manager = this.manager;
                final PunishmentType banned = PunishmentType.BANNED;
                final UUID uniqueId = target.getUniqueId ();
                final PunishmentManager manager2 = this.manager;
                manager.addPunishment ( banned , uniqueId , null , PunishmentManager.PUNISHMENT_EXPIRE_PERMANENTLY , reason );
            }
            return true;
        }
        final Player player = ( Player ) sender;
        final Profile profile = new Profile ( player.getUniqueId ());
        if (!profile.getRank ().isAboveOrEqual ( Rank.MOD )) {
            Messager.sendMessage(sender, CC.RED + "You don't have permission to use this command." );
            return true;
        }
        if (args.length < 2) {
            Messager.sendMessage(sender, CC.RED + "Usage: /ban <target> [reason] [-s] " + CC.YELLOW + " Permanently ban a player from the network.\nAdd a \"-s\" at the end to silently ban a player." );
            return true;
        } else {
            final OfflinePlayer target = Bukkit.getOfflinePlayer ( args[0] );
            if (!target.hasPlayedBefore () && !target.isOnline ()) {
                Messager.sendMessage(sender, CC.RED + target.getName () + " has never played before, but will be banned." );
                return true;
            }
            String reason = "";
            for (int i = 1; i < args.length; ++i) {
                reason = reason + args[i] + " ";
            }
            if (reason.equalsIgnoreCase ( "-s" )) {
                Messager.sendMessage(sender, CC.RED + "Please provide a valid reason." );
                return true;
            }
            reason = reason.replace ( "-s" , "" );
            final boolean silent = Arrays.asList ( args ).contains ( "-s" );
            if (target.isOnline ()) {
                target.getPlayer ().kickPlayer ( CC.RED + "You are permanently banned from uDrop Club.\n" + CC.GRAY + "If you feel this ban is unjustified, email appeal@udrop.club.\n" + CC.GOLD + "You may also purchase an unban at store.udrop.club." );
            }
            if (!silent) {
                Bukkit.broadcastMessage ( CC.GREEN + target.getName () + " was banned permanently by " + sender.getName () + " for " + reason + '.' );
            } else {
                Messager.sendMessage ( sender , CC.RED + "ONLY STAFF: " + target.getName () + " was banned permanently by " + sender.getName () + " for " + reason + '.' );
            }
            final PunishmentManager manager = this.manager;
            final PunishmentType banned = PunishmentType.BANNED;
            final UUID uniqueId = target.getUniqueId ();
            final UUID punisher = player.getUniqueId();
            final PunishmentManager manager2 = this.manager;
            manager.addPunishment ( banned , uniqueId , punisher , PunishmentManager.PUNISHMENT_EXPIRE_PERMANENTLY , reason );
        }
        return true;
    }
}