package me.devkevin.core;

import lombok.Getter;
import lombok.Setter;
import me.devkevin.core.Profile.ProfileListener;
import me.devkevin.core.commands.DiscordCommand;
import me.devkevin.core.commands.TeamSpeakCommand;
import me.devkevin.core.commands.social.MessageCommand;
import me.devkevin.core.commands.social.NoMsgCommand;
import me.devkevin.core.commands.social.PingCommand;
import me.devkevin.core.commands.social.ReplyCommand;
import me.devkevin.core.commands.staff.*;
import me.devkevin.core.database.Database;
import me.devkevin.core.manager.PlayerManager;
import me.devkevin.core.punishments.PunishmentManager;
import me.devkevin.core.punishments.command.*;
import me.devkevin.core.punishments.listener.PunishmentListener;
import me.devkevin.core.staff.freeze.Listener.ListenerHandler;
import me.devkevin.core.staff.freeze.ManagerHandler;
import me.devkevin.core.tab.TabManager;
import me.devkevin.core.tab.packet.PacketBasedVersion;
import me.devkevin.core.tab.packet.PacketUtil;
import me.devkevin.core.task.ShutdownTask;
import me.devkevin.core.utils.finalutil.PlayerEvents;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.*;

public class CorePlugin extends JavaPlugin implements Listener {

    private Database Database;
    @Getter
    private PlayerManager playerManager;
    @Getter
    private static CorePlugin instance;
    @Setter
    private Location spawnLocation;
    @Getter
    private PunishmentManager punishmentManager;
    @Setter
    private ShutdownTask shutdownTask = null;
    @Getter
    public ArrayList<Player> staffchat;
    @Getter
    public List<String> messages;
    @Getter
    private ManagerHandler managerHandler;
    @Getter
    public Random rn;
    @Getter
    public List<Player> nomsg;
    @Getter
    public HashMap<String, String> reply;
    @Getter
    public HashMap<Player, Integer> chatdelay;
    @Getter
    private PacketUtil packetUtil;
    @Getter
    private TabManager tabManager;


    public static CorePlugin getInstance() {
        return CorePlugin.instance;
    }


    @Override
    public void onEnable() {

        //THIS IS WHAT MAKES THE PLUGIN WORK NIGGER
        instance = this;
        super.onEnable ();

        //database
        this.database ();

        //managers
        this.managers();

        //listeners
        this.listeners ();

        //Commands
        this.commands ();

        //events
        this.registerEvents ();

        //more shit NIGGER
        this.staffchat = new ArrayList<Player>();
        this.messages = new ArrayList<String>();
        this.managerHandler = new ManagerHandler(this);
        this.rn = new Random ();
        this.nomsg = new ArrayList<Player>();
        this.reply = new HashMap<String, String>();
        this.chatdelay = new HashMap<Player, Integer>();
        new ListenerHandler (this);
        this.tablist();
    }

    private void database() {
        Database.openConnection ();
        Database.createTables ();
    }


    public void registerEvents() {
        final PluginManager pm = Bukkit.getPluginManager ();
        pm.registerEvents (new PlayerEvents (), this);
    }

    private void managers(){
        this.punishmentManager = new PunishmentManager(this);
    }

    private void listeners() {
        this.getServer ().getPluginManager ().registerEvents ( new ProfileListener () , this );
        this.getServer ().getPluginManager ().registerEvents (new PunishmentListener(this.punishmentManager), this );
    }

    private void registerCommand(final Command cmd) {
        this.registerCommand(cmd, this.getName());
    }

    public void registerCommand(final Command cmd, final String fallbackPrefix) {
        MinecraftServer.getServer().server.getCommandMap().register(cmd.getName(), fallbackPrefix, cmd);
    }

    private void commands() {
        this.getCommand ("setrank").setExecutor(new SetRankCommand ());
        this.getCommand ("ping").setExecutor(new PingCommand());
        this.getCommand("clearchat").setExecutor(new ClearChatCommand (this));
        this.getCommand("teamspeak").setExecutor(new TeamSpeakCommand ());
        this.getCommand ("discord").setExecutor (new DiscordCommand ());
        this.getCommand ("mutechat").setExecutor (new MuteChatCommand ());
        this.getCommand ("staffchat").setExecutor (new StaffChatCommand ());
        this.getCommand("broadcast").setExecutor(new BroadcastCommand ());
        this.getCommand("report").setExecutor(new ReportCommand ());
        this.getCommand("freeze").setExecutor(new FreezeCommand (this));
        this.getCommand("msg").setExecutor(new MessageCommand (this));
        this.getCommand("r").setExecutor(new ReplyCommand (this));
        this.getCommand("tpm").setExecutor(new NoMsgCommand (this));

        new BanCommand (this.punishmentManager);
        new BlacklistCommand (this.punishmentManager);
        new UnbanCommand (this.punishmentManager);
        new HistoryCommand (this.punishmentManager);
        new KickCommand (this.punishmentManager);
        new MuteCommand (this.punishmentManager);
        new UnmuteCommand (this.punishmentManager);
        new TempBanCommand (this.punishmentManager);
        new TempMuteCommand (this.punishmentManager);
        new ShutdownCommand (this);
    }

    @Override
    public void onDisable() {
        Database.closeConnection();
        this.punishmentManager.cancel ();
        this.punishmentManager.save ();
    }

    private void tablist() {
        this.packetUtil = new PacketBasedVersion ();
        this.tabManager = new TabManager();
    }

    public static CorePlugin getPlugin() {
        return (CorePlugin) JavaPlugin.getPlugin(CorePlugin.class);
    }

    public PlayerManager getPlayerManager()
    {
        return this.playerManager;
    }

    public PlayerManager getClubDatabase()
    {
        return this.playerManager;
    }

    public PacketUtil getPacketUtil() {
        return this.packetUtil;
    }

    public TabManager getTabManager() {
        return this.tabManager;
    }

    public ManagerHandler getManagerHandler() {
        return this.managerHandler;
    }
}

