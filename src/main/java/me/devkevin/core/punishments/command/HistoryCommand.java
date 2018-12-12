package me.devkevin.core.punishments.command;

import lombok.Getter;
import me.devkevin.core.CorePlugin;
import me.devkevin.core.Profile.Profile;
import me.devkevin.core.punishments.PunishmentManager;
import me.devkevin.core.ranks.Rank;
import me.devkevin.core.utils.CC;
import me.devkevin.core.utils.Messager;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HistoryCommand implements CommandExecutor {

    @Getter
    private PunishmentManager manager;
    @Getter
    private CorePlugin plugin;

    public HistoryCommand(final PunishmentManager manager) {
        this.plugin = CorePlugin.getPlugin();
        this.manager = manager;
        this.plugin.getCommand("history").setExecutor((CommandExecutor)this);
    }

    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (!(sender instanceof Player)) {
            if (args.length < 1) {
                Messager.sendMessage ( sender , CC.RED + "Usage: /history <target>" );
            }
            else {
                final OfflinePlayer target = Bukkit.getOfflinePlayer (args[0]);
                if (!target.hasPlayedBefore () && !target.isOnline ()) {
                    Messager.sendMessage (sender, CC.RED + args + " has never played before.");
                    return true;
                }
                Messager.sendMessage (sender, CC.GRAY + "--------------------------------------");
                Messager.sendMessage (sender, CC.GOLD + "History for " + CC.GRAY + target.getName ());
                Messager.sendMessage (sender, "");
                int i = 0;
                for (final PunishmentManager.Punishment punishment : this.manager.getAllPunishments (target.getUniqueId ())) {
                    ++i;
                    Messager.sendMessage (sender, CC.GOLD + "#" + punishment.getId () + CC.WHITE + " / " + CC.YELLOW + WordUtils.capitalizeFully (punishment.getType ().name ()));
                    Messager.sendMessage (sender,CC.GRAY + "»" + CC.YELLOW + "Punisher: " + ((punishment.getPunisher () == null) ? "Console" : Bukkit.getOfflinePlayer (punishment.getPunisher ()).getName ()));
                    Messager.sendMessage (sender,CC.GRAY + "»" + CC.YELLOW + "Reason: " + punishment.getReason ());
                    Messager.sendMessage (sender, "");
                }
                if (i < 1) {
                    Messager.sendMessage (sender, CC.RED + "No history for " + target.getName () + ".");
                }
                Messager.sendMessage (sender, CC.GRAY + "--------------------------------------");
            }
            return true;
        }
        final Player player = (Player)sender;
        final Profile profile = new Profile ( player.getUniqueId ());
        if (!profile.getRank ().isAboveOrEqual ( Rank.TRAINEE )) {
            Messager.sendMessage ( sender , CC.RED + "You don't have permission to use this command." );
            return true;
        }
        if (args.length < 1) {
            Messager.sendMessage ( sender , CC.RED + "Usage: /history <target>" );
        }
        else {
            final OfflinePlayer target = Bukkit.getOfflinePlayer (args[0]);
            if (!target.hasPlayedBefore () && !target.isOnline ()) {
                Messager.sendMessage (sender, CC.RED + args + " has never played before.");
                return true;
            }
            Messager.sendMessage (sender, CC.GRAY + "--------------------------------------");
            Messager.sendMessage (sender, CC.GOLD + "History for " + CC.GRAY + target.getName ());
            Messager.sendMessage (sender, "");
            int i = 0;
            for (final PunishmentManager.Punishment punishment : this.manager.getAllPunishments (target.getUniqueId ())) {
                ++i;
                Messager.sendMessage (sender, CC.GOLD + "#" + punishment.getId () + CC.WHITE + " / " + CC.YELLOW + WordUtils.capitalizeFully (punishment.getType ().name ()));
                Messager.sendMessage (sender,CC.GRAY + "»" + CC.YELLOW + "Punisher: " + ((punishment.getPunisher () == null) ? "Console" : Bukkit.getOfflinePlayer (punishment.getPunisher ()).getName ()));
                Messager.sendMessage (sender,CC.GRAY + "»" + CC.YELLOW + "Reason: " + punishment.getReason ());
                Messager.sendMessage (sender, "");
            }
            if (i < 1) {
                Messager.sendMessage (sender, CC.RED + "No history for " + target.getName () + ".");
            }
            Messager.sendMessage (sender, CC.GRAY + "--------------------------------------");
        }
        return true;
    }
}
