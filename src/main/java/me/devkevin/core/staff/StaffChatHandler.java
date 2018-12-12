package me.devkevin.core.staff;

import me.devkevin.core.utils.ColorText;
import org.bukkit.entity.*;
import java.util.*;

public class StaffChatHandler {

    private static List<UUID> staffchatList;

    public static boolean isStaffChat(final Player player) {
        return StaffChatHandler.staffchatList.contains(player.getUniqueId());
    }

    public static void setStaffChat(final Player player, final boolean b) {
        if (b) {
            StaffChatHandler.staffchatList.add(player.getUniqueId());
            player.sendMessage(ColorText.translate("&aYou have entered staff chat."));
        }
        else {
            StaffChatHandler.staffchatList.remove(player.getUniqueId());
            player.sendMessage(ColorText.translate("&cYou have left staff chat."));
        }
    }

    static {
        StaffChatHandler.staffchatList = new ArrayList<UUID>();
    }
}
