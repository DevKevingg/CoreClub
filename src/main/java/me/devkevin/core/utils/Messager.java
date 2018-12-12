package me.devkevin.core.utils;

import java.util.Arrays;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import net.md_5.bungee.api.ChatColor;

public class Messager {

    public static String translate(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    public static List<String> translate(List<String> list) {
        for (int i = 0; i < list.size(); ++i) {
            list.set(i, translate(list.get(i)));
        }
        return list;
    }

    public static String[] translate(String[] array) {
        return translate(Arrays.asList(array)).stream().toArray(String[]::new);
    }

    public static void sendMessage(Player player, String... strings) {
        for (String string : strings) {
            player.sendMessage(translate(string));
        }
    }

    public static void sendMessage(CommandSender sender, String... strings) {
        for (String string : strings) {
            sender.sendMessage(translate(string));
        }
    }

    public static void broadcastMessage(String... strings) {
        for (String string : strings) {
            Bukkit.broadcastMessage(translate(string));
        }
    }

    public static void sendConsoleMessage(String... strings) {
        for (String string : strings) {
            Bukkit.getConsoleSender().sendMessage(translate(string));
        }
    }

    public static String formatLocation(Location location) {
        String world = location.getWorld().getName();
        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();
        return world + ", " + x + ", " + y + ", " + z;
    }

}

