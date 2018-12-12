package me.devkevin.core.utils.finalutil;

import me.devkevin.core.utils.CC;
import org.bukkit.ChatColor;

import java.util.Arrays;
import java.util.regex.Pattern;

public class StringUtil {

    public static final String NO_PERMISSION;
    public static final String COMMAND_COOLDOWN;
    public static final String PLAYER_ONLY;
    public static final String CHAT_COOLDOWN;
    public static final String PLAYER_NOT_FOUND;
    public static final String LOAD_ERROR;
    public static final String SPLIT_PATTERN;
    public static String PERMISSION;

    private StringUtil() {
        throw new RuntimeException ("cannot instantiate a utility class.");
    }

    public static String buildMessage(final String[] args, final int start) {
        if (start >= args.length) {
            return "";
        }
        return ChatColor.stripColor(String.join(" ", (CharSequence[])Arrays.copyOfRange(args, start, args.length)));
    }

    public static String getFirstSplit(final String s) {
        return s.split(StringUtil.SPLIT_PATTERN)[0];
    }

    static {
        NO_PERMISSION = CC.RED + "You don't have permission to use this command.";
        COMMAND_COOLDOWN = CC.RED + "You can't use command that fast.";
        PLAYER_ONLY = CC.RED + "Only players can use this command.";
        CHAT_COOLDOWN = CC.RED + "You can't chat that fast.";
        PLAYER_NOT_FOUND = CC.RED + "%s not found.";
        LOAD_ERROR = CC.RED + "An error occurred while loading your player data. Try again later.";
        SPLIT_PATTERN = Pattern.compile("\\s").pattern();
    }
}
