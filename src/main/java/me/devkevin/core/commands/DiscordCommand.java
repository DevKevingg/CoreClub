package me.devkevin.core.commands;

import me.devkevin.core.utils.CC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class DiscordCommand implements CommandExecutor {

    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] arguments) {
        if (command.getName ().equalsIgnoreCase ("discord")) {
            sender.sendMessage ( CC.LIGHT_PURPLE + "Join our Discord: " + CC.WHITE + "https://discord.gg/eyNVhg7");
        }
        return true;
    }
}
