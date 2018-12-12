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

import java.util.UUID;

public class MuteCommand implements CommandExecutor {

    @Getter
    private PunishmentManager manager;
    @Getter
    private CorePlugin plugin;

    public MuteCommand(final PunishmentManager manager) {
        this.plugin = CorePlugin.getPlugin ();
        this.manager = manager;
        this.plugin.getCommand ( "mute" ).setExecutor ( ( CommandExecutor ) this );
    }

    public boolean onCommand(final CommandSender sender , final Command command , final String label , final String[] args) {
        if (!(sender instanceof Player)) {
            if (args.length < 2) {
                Messager.sendMessage (sender, CC.RED + "Usage: /mute <target> <time> <reason> [-s] ", CC.YELLOW + " Temporarily mute a player from chatting.\nAdd a \"-s\" at the end to silently mute a player.", "");
            }
            else {
                final OfflinePlayer target = Bukkit.getOfflinePlayer ( args[0] );
                if (!target.hasPlayedBefore () && !target.isOnline ()) {
                    Messager.sendMessage ( sender , CC.RED + args[0] + " has never played before." );
                    return true;
                }
                if (this.manager.hasActivePunishment (target.getUniqueId (), PunishmentType.MUTE) != null) {
                    Messager.sendMessage (sender, CC.RED + target.getName () + " is already muted.");
                    return true;
                }
                String reason = "";
                for (int i = 1; i < args.length; ++i) {
                    reason = reason + args[i] + " ";
                }
                if (reason.equalsIgnoreCase ("-s")) {
                    Messager.sendMessage (sender, CC.RED + target.getName () + " was muted permanently by " + sender.getName () + ".");
                }
                else {
                    command.broadcastCommandMessage (sender, CC.RED + "STAFF ONLY: " + target.getName () + " was muted permanently by " + sender.getName () + ".");
                }
                if (target.isOnline ()) {
                    target.getPlayer ().sendMessage ("");
                    target.getPlayer ().sendMessage (CC.GREEN + "You has been muted by " + sender.getName () + " for " + reason + ".");
                    target.getPlayer ().sendMessage ("");
                }
                final PunishmentManager manager = this.manager;
                final PunishmentType mute = PunishmentType.MUTE;
                final UUID uniqueId = target.getUniqueId();
                final PunishmentManager manager2 = this.manager;
                manager.addPunishment(mute, uniqueId, null, PunishmentManager.PUNISHMENT_EXPIRE_PERMANENTLY, reason);
            }
            return true;
        }
        final Player player = (Player)sender;
        final Profile profile = new Profile (player.getUniqueId ());
        if (!profile.getRank ().isAboveOrEqual ( Rank.TRAINEE)) {
            Messager.sendMessage(sender, CC.RED + "You don't have permission to use this command." );
            return true;
        }
        if (args.length < 2) {
            Messager.sendMessage (sender, CC.RED + "Usage: /mute <target> <reason> [-s] ", CC.YELLOW + " Temporarily mute a player from chatting.\nAdd a \"-s\" at the end to silently mute a player.", "");
        }
        else {
            final OfflinePlayer target = Bukkit.getOfflinePlayer ( args[0] );
            if (!target.hasPlayedBefore () && !target.isOnline ()) {
                Messager.sendMessage ( sender , CC.RED + args[0] + " has never played before." );
                return true;
            }
            if (this.manager.hasActivePunishment (target.getUniqueId (), PunishmentType.MUTE) != null) {
                Messager.sendMessage (sender, CC.RED + target.getName () + " is already muted.");
                return true;
            }
            String reason = "";
            for (int i = 1; i < args.length; ++i) {
                reason = reason + args[i] + " ";
            }
            if (reason.equalsIgnoreCase ("-s")) {
                Messager.sendMessage (sender, CC.RED + target.getName () + " was temporarily muted by " + sender.getName () + ".");
            }
            else {
                Messager.sendMessage ( sender, CC.RED + "STAFF ONLY: " + target.getName () + " was muted permanently by " + sender.getName () + ".");
            }
            if (target.isOnline ()) {
                target.getPlayer ().sendMessage ("");
                target.getPlayer ().sendMessage (CC.GREEN + "You has been muted by " + sender.getName () + " for " + reason + ".");
                target.getPlayer ().sendMessage ("");
            }
            final PunishmentManager manager = this.manager;
            final PunishmentType mute = PunishmentType.MUTE;
            final UUID uniqueId = target.getUniqueId();
            final PunishmentManager manager2 = this.manager;
            manager.addPunishment(mute, uniqueId, null, PunishmentManager.PUNISHMENT_EXPIRE_PERMANENTLY, reason);
        }
        return true;
    }
}
