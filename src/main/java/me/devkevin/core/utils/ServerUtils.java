package me.devkevin.core.utils;

import net.md_5.bungee.api.connection.*;

public class ServerUtils
{
    public static String getServer(final ProxiedPlayer player) {
        return player.getServer().getInfo().getName();
    }
}
