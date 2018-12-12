package me.devkevin.core.commands.staff;

import lombok.Getter;
import me.devkevin.core.CorePlugin;
import me.devkevin.core.Profile.Profile;
import me.devkevin.core.ranks.Rank;
import me.devkevin.core.utils.CC;
import me.devkevin.core.utils.Messager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ClearChatCommand implements CommandExecutor {

    @Getter
    private final CorePlugin plugin;
    @Getter
    private final String message;

    public ClearChatCommand(final  CorePlugin plugin) {
        this.plugin = CorePlugin.getPlugin ();
        final StringBuilder builder = new StringBuilder ();
        for (int i = 0; i < 100; i++) {

            builder.append ("§8 §8 §1 §3 §3 §7 §8 §r \n");
        }
        this.message = builder.toString ();
    }

    @Override
    public boolean onCommand(final CommandSender sender ,final Command command , final String s , final String[] strings) {
        final Player player = (Player)sender;
        final Profile profile = new Profile(player.getUniqueId());
        if (!profile.getRank().isAboveOrEqual(Rank.PLATFORM_ADMINISTRATOR)) {
            Messager.sendMessage ( sender , CC.RED + "You don't have permission to use this command." );
            return true;
        }
        this.plugin.getServer ().broadcastMessage (this.message);
        return true;
    }
}
