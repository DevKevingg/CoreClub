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

public class UnbanCommand implements CommandExecutor {

    @Getter
    private PunishmentManager manager;
    @Getter
    private CorePlugin plugin;

    public UnbanCommand(final PunishmentManager manager) {
        this.plugin = CorePlugin.getPlugin ();
        this.manager = manager;
        this.plugin.getCommand ("unban").setExecutor ((CommandExecutor)this);
    }

    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (!(sender instanceof Player)) {
            if (args.length < 1) {
                Messager.sendMessage(sender, CC.RED +  "Usage: /unban <target> [reason] [-s] ", CC.YELLOW + " Remove a player's ban.\nAdd a \"-s\" at the end to silently ban a player.", "");
            }
            else {
                final OfflinePlayer target = Bukkit.getOfflinePlayer (args[0]);
                final PunishmentManager.Punishment punishment = this.manager.hasActivePunishment (target.getUniqueId (), PunishmentType.BANNED);
                if (punishment == null) {
                    Messager.sendMessage (sender, CC.RED + target.getName () + " target is not banned.");
                    return true;
                }
                punishment.removePunishment ();
                final boolean silent = Arrays.asList (args).contains ("-s");
                if (!silent) {
                    Bukkit.broadcastMessage (CC.GREEN + target.getName () + " was unbanned by " + sender.getName () + ".");
                }
                else {
                    Messager.sendMessage ( sender, CC.RED + "ONLY STAFF: " + target.getName () + " was unbanned by " + sender.getName () + ".");
                }
            }
            return true;
        }
        final Player player = (Player)sender;
        final Profile profile = new Profile(player.getUniqueId ());
        if (!profile.getRank ().isAboveOrEqual (Rank.MOD)) {
            Messager.sendMessage(sender, CC.RED + "You don't have permission to use this command." );
            return true;
        }
        if (args.length < 1) {
            Messager.sendMessage(sender, CC.RED + "Usage: /unban <target> [reason] [-s] ", CC.YELLOW + " Remove a player's ban.\nAdd a \"-s\" at the end to silently ban a player.", "");
        }
        else {
            final OfflinePlayer target = Bukkit.getOfflinePlayer (args[0]);
            final PunishmentManager.Punishment punishment = this.manager.hasActivePunishment (target.getUniqueId (), PunishmentType.BANNED);
            if (punishment == null) {
                Messager.sendMessage (sender, CC.RED + target.getName () + " target is not banned.");
                return true;
            }
            punishment.removePunishment ();
            final boolean silent = Arrays.asList (args).contains ("-s");
            if (!silent) {
                Bukkit.broadcastMessage (CC.GREEN + target.getName () + " was unbanned by " + sender.getName () + ".");
            }
            else {
                Messager.sendMessage ( sender, CC.RED + "ONLY STAFF: " + target.getName () + " was unbanned by " + sender.getName () + ".");
            }
        }
        return true;
    }
}
