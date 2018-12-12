package me.devkevin.core.commands.staff;

import me.devkevin.core.Profile.Profile;
import me.devkevin.core.ranks.Rank;
import me.devkevin.core.utils.CC;
import me.devkevin.core.utils.Messager;
import me.devkevin.core.utils.finalutil.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SetRankCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] arguments) {
        if (!(sender instanceof Player)) {
            if(arguments.length != 2) {
                Messager.sendMessage(sender, ChatColor.RED + "Usage: /setrank [target] [rank]", ChatColor.YELLOW + "Give a player a rank.", "");
                return true;
            }
            OfflinePlayer target = Bukkit.getOfflinePlayer(arguments[0]);
            if (!target.hasPlayedBefore() && !target.isOnline()) {
                sender.sendMessage(Messager.translate("&c" + arguments[0] + " has never played before."));
                return true;
            }
            Profile profiletarget = new Profile(target.getUniqueId());
            if (!profiletarget.isExists()) {
                sender.sendMessage(Messager.translate("&cPlayer not found in database."));
                return true;
            }
            Rank playerrank = null;
            try {
                if (arguments[0].equalsIgnoreCase( "normal")) {
                    playerrank = Rank.NORMAL;
                }
                if (arguments[0].equalsIgnoreCase( "verif")) {
                    playerrank = Rank.VERIF;
                }
                if (arguments[0].equalsIgnoreCase( "cheater")) {
                    playerrank = Rank.CHEATER;
                }
                if (arguments[0].equalsIgnoreCase( "member")) {
                    playerrank = Rank.MEMBER;
                }
                if (arguments[0].equalsIgnoreCase( "clubber")) {
                    playerrank = Rank.CLUBBER;
                }
                if (arguments[0].equalsIgnoreCase( "dropper")) {
                    playerrank = Rank.DROPPER;
                }
                if (arguments[0].equalsIgnoreCase( "builder")) {
                    playerrank = Rank.BUILDER;
                }
                if (arguments[0].equalsIgnoreCase( "youtuber")) {
                    playerrank = Rank.YOUTUBER;
                }
                if (arguments[0].equalsIgnoreCase( "famous")) {
                    playerrank = Rank.FAMOUS;
                }
                else if (arguments[0].equalsIgnoreCase("trainee")) {
                    playerrank = Rank.TRAINEE;
                }
                else if (arguments[0].equalsIgnoreCase("mod")) {
                    playerrank = Rank.MOD;
                }
                else if (arguments[0].equalsIgnoreCase("admin")) {
                    playerrank = Rank.ADMIN;
                }
                if (arguments[0].equalsIgnoreCase( "coowner")) {
                    playerrank = Rank.MEDIA_OWNER;
                }
                if (arguments[0].equalsIgnoreCase( "PlatformAdmin")) {
                    playerrank = Rank.PLATFORM_ADMINISTRATOR;
                }
                if (arguments[0].equalsIgnoreCase( "manager")) {
                    playerrank = Rank.MANAGER;
                }
                else if (arguments[0].equalsIgnoreCase("owner")) {
                    playerrank = Rank.OWNER;
                }
                else if (arguments[0].equalsIgnoreCase("developer")) {
                    playerrank = Rank.DEVELOPER;
                } else {
                    playerrank = Rank.valueOf(arguments[1].toUpperCase());
                }
            } catch (Exception e) {
                Messager.sendMessage(sender, "&cCould not parse that rank.");
                return true;
            }
            if (playerrank != null) {
                profiletarget.setRank(playerrank);
                if (target.isOnline()) {
                    target.getPlayer().sendMessage(Messager.translate("&aYour have given " + profiletarget.getRank().getColor() + profiletarget.getRank().getName()));
                    return false;
                }
                sender.sendMessage(Messager.translate("&aUpdated " + target.getName() + "'s rank to " + profiletarget.getRank().getName() + "."));
                return true;
            }
            return true;
        }
        Player player = (Player)sender;
        Profile profile = new Profile(player.getUniqueId());
        if (!profile.getRank().isAboveOrEqual(Rank.PLATFORM_ADMINISTRATOR)) {
            Messager.sendMessage(sender, CC.RED + "You don't have permission to use this command." );
            return true;
        }
        if(arguments.length != 2) {
            Messager.sendMessage(sender, ChatColor.RED + "Usage: /setrank [target] [rank]", ChatColor.YELLOW + "Give a player a rank.", "");
            return true;
        }
        OfflinePlayer target = Bukkit.getOfflinePlayer(arguments[0]);
        if (!target.hasPlayedBefore() && !target.isOnline()) {
            player.sendMessage(Messager.translate("&c" + arguments[0] + " has never played before."));
            return true;
        }
        Profile profiletarget = new Profile(target.getUniqueId());
        if (!profiletarget.isExists()) {
            player.sendMessage(Messager.translate("&cPlayer not found in database."));
            return true;
        }
        Rank playerrank = null;
        try {
            if (arguments[0].equalsIgnoreCase( "normal")) {
                playerrank = Rank.NORMAL;
            }
            if (arguments[0].equalsIgnoreCase( "verif")) {
                playerrank = Rank.VERIF;
            }
            if (arguments[0].equalsIgnoreCase( "cheater")) {
                playerrank = Rank.CHEATER;
            }
            if (arguments[0].equalsIgnoreCase( "member")) {
                playerrank = Rank.MEMBER;
            }
            if (arguments[0].equalsIgnoreCase( "clubber")) {
                playerrank = Rank.CLUBBER;
            }
            if (arguments[0].equalsIgnoreCase( "dropper")) {
                playerrank = Rank.DROPPER;
            }
            if (arguments[0].equalsIgnoreCase( "builder")) {
                playerrank = Rank.BUILDER;
            }
            if (arguments[0].equalsIgnoreCase( "youtuber")) {
                playerrank = Rank.YOUTUBER;
            }
            if (arguments[0].equalsIgnoreCase( "famous")) {
                playerrank = Rank.FAMOUS;
            }
            else if (arguments[0].equalsIgnoreCase("trainee")) {
                playerrank = Rank.TRAINEE;
            }
            else if (arguments[0].equalsIgnoreCase("mod")) {
                playerrank = Rank.MOD;
            }
            else if (arguments[0].equalsIgnoreCase("admin")) {
                playerrank = Rank.ADMIN;
            }
            if (arguments[0].equalsIgnoreCase( "coowner")) {
                playerrank = Rank.MEDIA_OWNER;
            }
            if (arguments[0].equalsIgnoreCase( "Platform-Admin")) {
                playerrank = Rank.PLATFORM_ADMINISTRATOR;
            }
            if (arguments[0].equalsIgnoreCase( "manager")) {
                playerrank = Rank.MANAGER;
            }
            else if (arguments[0].equalsIgnoreCase("owner")) {
                playerrank = Rank.OWNER;
            }
            else if (arguments[0].equalsIgnoreCase("developer")) {
                playerrank = Rank.DEVELOPER;
            } else {
                playerrank = Rank.valueOf(arguments[1].toUpperCase());
            }
        } catch (Exception e) {
            Messager.sendMessage(sender, "&cCould not parse that rank.");
            return true;
        }
        if (playerrank != null) {
            profiletarget.setRank(playerrank);
            if (target.isOnline()) {
                target.getPlayer().sendMessage(Messager.translate("&aYour have given " + profiletarget.getRank().getColor() + profiletarget.getRank().getName()));
                return false;
            }
            player.sendMessage(Messager.translate("&aUpdated " + target.getName() + "'s rank to " + profiletarget.getRank().getName() + "."));
            return true;
        }
        return true;
    }
}