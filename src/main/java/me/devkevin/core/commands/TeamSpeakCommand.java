package me.devkevin.core.commands;

import me.devkevin.core.utils.CC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class TeamSpeakCommand implements CommandExecutor {

    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] arguments) {
        if (command.getName ().equalsIgnoreCase ("teamspeak")) {
            sender.sendMessage ( CC.LIGHT_PURPLE + "Join our TeamSpeak: " + CC.WHITE + "ts.udrop.club");
        }
        return true;
    }
}
