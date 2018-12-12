package me.devkevin.core.utils.finalutil;

import com.google.common.base.Preconditions;
import me.devkevin.core.CorePlugin;
import me.devkevin.core.Profile.Profile;
import me.devkevin.core.ranks.Rank;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public final class PlayerUtil
{
    public static final Comparator<Player> VISIBLE_RANK_ORDER;

    public PlayerUtil() {
        throw new RuntimeException("Cannot instantiate a utility class.");
    }

    public static void messageStaff(final String message) {
        messageStaff(message, Rank.TRAINEE);
    }

    public static void messageStaff(final String message, final Rank rank) {
        Bukkit.getOnlinePlayers().stream().filter( player -> CorePlugin.getInstance().getPlayerManager().getPlayer(player.getUniqueId()).hasRank(rank)).forEach( player -> player.sendMessage(message));
    }

    public static boolean testPermission(final CommandSender sender, final Rank requiredRank) {
        if (sender instanceof Player) {
            final Profile profile = CorePlugin.getInstance().getPlayerManager().getPlayer(((Player)sender).getUniqueId());
            if (!profile.hasRank(requiredRank)) {
                sender.sendMessage(StringUtil.NO_PERMISSION);
                return false;
            }
        }
        return true;
    }
    static {
        final Profile[] profileA = new Profile[1];
        final Profile[] profileB = new Profile[1];
        VISIBLE_RANK_ORDER = ((a, b) -> {
            profileA[0] = CorePlugin.getInstance().getPlayerManager().getPlayer(a.getUniqueId());
            profileB[0] = CorePlugin.getInstance().getPlayerManager().getPlayer(b.getUniqueId());
            return -profileA[0].getRank().compareTo( profileB[0].getRank());
        });
    }

    public static boolean isOnline(final CommandSender sender, final Player player) {
        return player != null && (!(sender instanceof Player) || ((Player)sender).canSee(player));
    }

    public static List<String> getCompletions(final String[] args, final List<String> input) {
        return getCompletions(args, input, 80);
    }

    public static List<String> getCompletions(final String[] args, final List<String> input, final int limit) {
        Preconditions.checkNotNull((Object)args);
        Preconditions.checkArgument(args.length != 0);
        final String argument = args[args.length - 1];
        final String s = null;
        return input.stream().filter(string -> string.regionMatches(true, 0, s, 0, s.length())).limit(limit).collect(Collectors.toList());
    }
}

