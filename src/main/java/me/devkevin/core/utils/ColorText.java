package me.devkevin.core.utils;

import net.md_5.bungee.api.*;

public class ColorText
{
    public static String translate(final String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}

