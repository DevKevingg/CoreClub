package me.devkevin.core.punishments.command;

import lombok.Getter;
import me.devkevin.core.CorePlugin;
import me.devkevin.core.Profile.Profile;
import me.devkevin.core.punishments.PunishmentManager;
import me.devkevin.core.punishments.PunishmentType;
import me.devkevin.core.ranks.Rank;
import me.devkevin.core.utils.CC;
import me.devkevin.core.utils.Duration;
import me.devkevin.core.utils.Messager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class TempMuteCommand implements CommandExecutor {

    @Getter
    private PunishmentManager manager;
    @Getter
    private CorePlugin plugin;

    public TempMuteCommand(final PunishmentManager manager) {
        this.plugin = CorePlugin.getPlugin ();
        this.manager = manager;
        this.plugin.getCommand ("tempmute").setExecutor ((CommandExecutor)this);
    }

    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (!(sender instanceof Player)) {
            if (args.length < 3) {
                Messager.sendMessage (sender, CC.RED + "Usage: /mute <target> <time> <reason> [-s] ", CC.YELLOW + " Temporarily mute a player from chatting.\nAdd a \"-s\" at the end to silently mute a player.", "");
            }
            else {
                final OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
                if (!target.hasPlayedBefore() && !target.isOnline()) {
                    Messager.sendMessage (sender, CC.RED + target.getName () + " has never played before, but will be muted.");
                }
                final long time = Duration.parse (args[1]);
                if (time < 0L) {
                    Messager.sendMessage (sender, CC.RED + args[1] + " is not a valid duration.");
                    return true;
                }
                if (time < 0L) {
                    Messager.sendMessage (sender, CC.RED + "Invalid duration: 3m3s");
                    return true;
                }
                if (this.manager.hasActivePunishment(target.getUniqueId(), PunishmentType.MUTE) != null) {
                    Messager.sendMessage (sender, CC.RED + target.getName () + " is not already muted.");
                    return true;
                }
                String reason = "";
                for (int i = 2; i < args.length; ++i) {
                    reason = reason + args[i] + " ";
                }
                if (reason.equalsIgnoreCase("-s")) {
                    Messager.sendMessage (sender, CC.RED + "Please provide a valid reason.");
                    return true;
                }
                reason = reason.replace("-s", "");
                final boolean silent = Arrays.asList(args).contains("-s");
                if (!silent) {
                    Messager.sendMessage (sender, CC.GREEN + target.getName() + " was temporarily muted by " + sender.getName () + " for " + reason + ".");
                }
                else {
                    Messager.sendMessage ( sender, CC.RED + "STAFF ONLY: " + target.getName () + " was temporarily muted by " + sender.getName () + " for " + reason + ".");
                }
                if (target.isOnline ()) {
                    target.getPlayer ().sendMessage ("");
                    target.getPlayer ().sendMessage (CC.GREEN + "You has been temporarily muted by " + sender.getName () + " for " + reason + ".");
                    target.getPlayer ().sendMessage ("");
                }
                this.manager.addPunishment(PunishmentType.MUTE, target.getUniqueId(), null, System.currentTimeMillis() + time, reason);
            }
            return true;
        }
        final Player player = (Player)sender;
        final Profile profile = new Profile(player.getUniqueId ());
        if (!profile.getRank ().isAboveOrEqual (Rank.TRAINEE)) {
            Messager.sendMessage ( sender , CC.RED + "You don't have permission to use this command." );
            return true;
        }
        if (args.length < 3) {
            Messager.sendMessage (sender, CC.RED + "Usage: /mute <target> <time> <reason> [-s] ", CC.YELLOW + " Temporarily mute a player from chatting.\nAdd a \"-s\" at the end to silently mute a player.", "");
        }
        else {
            final OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
            if (!target.hasPlayedBefore() && !target.isOnline()) {
                Messager.sendMessage (sender, CC.RED + target.getName () + " has never played before, but will be muted.");
            }
            final long time = Duration.parse (args[1]);
            if (time < 0L) {
                Messager.sendMessage (sender, CC.RED + args[1] + " is not a valid duration.");
                return true;
            }
            if (time < 0L) {
                Messager.sendMessage (sender, CC.RED + "Invalid duration: 3m3s");
                return true;
            }
            if (this.manager.hasActivePunishment(target.getUniqueId(), PunishmentType.MUTE) != null) {
                Messager.sendMessage (sender, CC.RED + target.getName () + " is not already muted.");
                return true;
            }
            String reason = "";
            for (int i = 2; i < args.length; ++i) {
                reason = reason + args[i] + " ";
            }
            if (reason.equalsIgnoreCase("-s")) {
                Messager.sendMessage (sender, CC.RED + "Please provide a valid reason.");
                return true;
            }
            reason = reason.replace("-s", "");
            final boolean silent = Arrays.asList(args).contains("-s");
            if (!silent) {
                Messager.sendMessage (sender, CC.GREEN + target.getName() + " was temporarily muted by " + sender.getName () + " for " + reason + ".");
            }
            else {
                Messager.sendMessage ( sender, CC.RED + "STAFF ONLY: " + target.getName () + " was temporarily muted by " + sender.getName () + " for " + reason + ".");
            }
            if (target.isOnline ()) {
                target.getPlayer ().sendMessage ("");
                target.getPlayer ().sendMessage (CC.GREEN + "You has been temporarily muted by " + sender.getName () + " for " + reason + ".");
                target.getPlayer ().sendMessage ("");
            }
            this.manager.addPunishment(PunishmentType.MUTE, target.getUniqueId(), null, System.currentTimeMillis() + time, reason);
        }
        return true;
    }
}
