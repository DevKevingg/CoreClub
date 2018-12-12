package me.devkevin.core.Profile;

import lombok.Getter;
import me.devkevin.core.CorePlugin;
import me.devkevin.core.database.Database;
import me.devkevin.core.punishments.PunishmentManager;
import me.devkevin.core.ranks.Rank;
import me.devkevin.core.utils.CC;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.permissions.PermissionAttachment;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.bukkit.plugin.*;

import java.util.*;

public class Profile {

    @Getter
    private UUID identifier;
    @Getter
    private Rank rank;
    @Getter
    private static Map<UUID, Profile> profiles = new HashMap<> ();

    private static HashMap<UUID, PermissionAttachment> localPermissions = new HashMap<UUID, PermissionAttachment>();
    @Getter
    private List<PunishmentManager> punishments;

    public Profile(UUID identifier) {
        this.identifier = identifier;
        this.rank = Rank.NORMAL;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();

        Profile profile = Profile.getByUuidIfAvailable(event.getPlayer().getUniqueId());

        final PermissionAttachment attachment = this.getPlayer().addAttachment((Plugin)CorePlugin.getInstance());
        if (this.hasRank(Rank.ADMIN)) {
            attachment.setPermission("bukkit.command.*", true);
            attachment.setPermission ("report.staff", true);
            attachment.setPermission ("core.broadcast", true);
            attachment.setPermission ("BungeeStaffChat.Use", true);
            attachment.setPermission ("siri.admin", true);
        }
        else if (this.hasRank(Rank.MOD)) {
            attachment.setPermission("bukkit.command.clear", true);
            attachment.setPermission("bukkit.command.effect", true);
            attachment.setPermission("bukkit.command.enchant", true);
            attachment.setPermission("bukkit.command.gamemode", true);
            attachment.setPermission ("report.staff", true);
            attachment.setPermission ("BungeeStaffChat.Use", true);
            attachment.setPermission ("siri.admin", true);
        }
        else if (this.hasRank(Rank.TRAINEE)) {
            attachment.setPermission("bukkit.command.clear", true);
            attachment.setPermission("bukkit.command.effect", true);
            attachment.setPermission("bukkit.command.enchant", true);
            attachment.setPermission("bukkit.command.gamemode", true);
            attachment.setPermission ("report.staff", true);
            attachment.setPermission ("siri.admin", true);
        }
        else if (this.hasRank(Rank.NORMAL)) {
            attachment.setPermission("bukkit.command.me", false);
            attachment.setPermission("minecraft.command.me", false);
        }
        if (profile != null && profile.getRank() != null) {
            Profile.updateTabList(player, profile.getRank());
        }
    }

    
    public boolean hasMessages() {
        try {
            final PreparedStatement statement = Database.getConnection().prepareStatement("SELECT * FROM playerData WHERE UUID=?");
            statement.setString(1, this.identifier.toString());
            final ResultSet results = statement.executeQuery();
            results.next();
            return results.getBoolean("TogglePM");
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isCreated() {
        try {
            final ResultSet results = Database.getStatement().executeQuery("SELECT * FROM playerData WHERE UUID='" + this.identifier + "'");
            if (results.next()) {
                return true;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public boolean isExists() {
        try {
            ResultSet results = Database.getStatement().executeQuery("SELECT * FROM rankdata WHERE uuid='" + this.identifier.toString() + "'");
            if (results.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void create() {
        try {
            PreparedStatement statement = Database.getConnection().prepareStatement("INSERT INTO rankdata(uuid,name,rank) VALUES (?,?,?)");
            statement.setString(1, this.identifier.toString());
            statement.setString(2, Bukkit.getPlayer(this.identifier).getName());
            statement.setString(3, Rank.NORMAL.getName());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setRank(Rank rank) {
        try {
            PreparedStatement statement = Database.getConnection().prepareStatement("UPDATE rankdata SET rank = ? WHERE uuid = ?");
            statement.setString(1, rank.getName());
            statement.setString(2, this.identifier.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean hasRank(final Rank rank) {
        return this.rank.hasRank(rank);
    }

    public Rank getRank() {
        try {
            PreparedStatement statement = Database.getConnection().prepareStatement("SELECT * FROM rankdata WHERE uuid=?");
            statement.setString(1, this.identifier.toString());
            ResultSet results = statement.executeQuery();
            results.next();
            return Rank.getByName (results.getString("rank"));
        } catch (SQLException e) {
            Rank rank = Rank.NORMAL;
            return rank;
        }
    }

    public UUID getIdentifier() {
        return identifier;
    }


    public Player getPlayer() {
        return CorePlugin.getInstance().getServer().getPlayer(this.identifier);
    }


    public static void updateTabList(Player player, Rank rank) {
        player.setPlayerListName (rank.getColor() + player.getName());
    }

    public void updateTabList(Rank rank) {
        this.getPlayer().setPlayerListName(rank.getColor() + this.getPlayer().getName());
    }

    public static Profile getByUuidIfAvailable(UUID uuid) {
        return profiles.get(uuid);
    }

    public static Profile getByUuid(UUID uuid) {
        Profile profile = profiles.get(uuid);

        if (profile == null) {
            return new Profile(uuid);
        } else {
            return profile;
        }
    }

    public static String getRankColor(UUID uuid) {
        Profile profile = Profile.getByUuidIfAvailable(uuid);

        if (profile == null) {
            return CC.WHITE;
        } else {
            return profile.getRank().getColor();
        }
    }
}
