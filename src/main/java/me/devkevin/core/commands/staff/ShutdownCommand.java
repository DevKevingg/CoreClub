package me.devkevin.core.commands.staff;

import lombok.Getter;
import me.devkevin.core.CorePlugin;
import me.devkevin.core.Profile.Profile;
import me.devkevin.core.ranks.Rank;
import me.devkevin.core.task.ShutdownTask;
import me.devkevin.core.utils.CC;
import me.devkevin.core.utils.Messager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class ShutdownCommand  implements CommandExecutor {

    @Getter
    private final CorePlugin plugin;
    @Getter
    private ShutdownTask shutdownTask;

    public ShutdownCommand(final CorePlugin plugin) {
        this.plugin = CorePlugin.getPlugin ();
        this.plugin.getCommand ("shutdown").setExecutor ((CommandExecutor)this);
    }

    @Override
    public boolean onCommand(CommandSender sender , Command command ,final  String s , final String[] args) {
        Player player = (Player)sender;
        Profile profile = new Profile(player.getUniqueId());
        if (!profile.getRank().isAboveOrEqual(Rank.PLATFORM_ADMINISTRATOR)) {
            Messager.sendMessage(sender, CC.RED + "You don't have permission to use this command." );
            return true;
        }
        if (args.length != 1) {
            sender.sendMessage(CC.RED + "Please use /shutdown <seconds | time | cancel>");
            return true;
        }
        if (args[0].equalsIgnoreCase("time")) {
            if (this.shutdownTask == null) {
                sender.sendMessage(CC.RED + "The server is not scheduled to shut down.");
            }
            else {
                sender.sendMessage(CC.GREEN + "The server will shutdown in " + this.shutdownTask.getSecondsUntilShutdown() + " seconds.");
            }
            return true;
        }
        if (args[0].equalsIgnoreCase("cancel")) {
            if (this.shutdownTask == null) {
                sender.sendMessage(CC.RED + "The server is not scheduled to shut down.");
            }
            else {
                this.shutdownTask.cancel();
                this.shutdownTask = null;
                sender.sendMessage(CC.RED + "The server shutdown has been canceled.");
            }
        }
        else {
            int seconds;
            try {
                seconds = Integer.parseInt(args[0]);
            }
            catch (NumberFormatException e) {
                sender.sendMessage(CC.RED + "You must input a valid number!");
                return true;
            }
            if (seconds <= 0) {
                sender.sendMessage(CC.RED + "You must input a number greater than 0!");
                return true;
            }
            if (this.shutdownTask == null) {
                (this.shutdownTask = new ShutdownTask(this.plugin, seconds)).runTaskTimer((Plugin ) this.plugin, 20L, 20L);
            }
            else {
                this.shutdownTask.setSecondsUntilShutdown(seconds);
            }
            sender.sendMessage(CC.GREEN + "The server will shutdown in " + seconds + " seconds.");
        }
        return true;
    }
}
