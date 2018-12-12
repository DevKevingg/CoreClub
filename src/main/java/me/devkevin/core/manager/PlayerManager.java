package me.devkevin.core.manager;

import lombok.Getter;
import me.devkevin.core.CorePlugin;
import me.devkevin.core.Profile.Profile;
import me.devkevin.core.database.Database;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class PlayerManager {

    @Getter
    private final Map<UUID, Profile> userMap;
    @Getter
    private final UUID uuid;
    @Getter
    private List<UUID> logging;
    @Getter
    private HashMap<UUID, ItemStack[]> contents;

    public PlayerManager(UUID uuid , List <UUID> logging , UUID identificator) {
        this.uuid = uuid;
        this.logging = logging;
        this.userMap = new HashMap<UUID, Profile> ();
    }

    public void addUser(final Profile user) {
        this.userMap.put (user.getIdentifier (), user);
    }

    public Map<UUID, Profile> getUserMap() {
        return this.userMap;
    }

    public Profile getPlayer(UUID uniqueId) {
        return ( Profile ) CorePlugin.getInstance ().getServer ().getPlayer (this.uuid);
    }

    public boolean isLogging(final UUID uuid) {
        return this.logging.contains(uuid);
    }

    public boolean isRegister(UUID uuid) {
        try {
            final PreparedStatement statement = Database.getConnection().prepareStatement("SELECT * FROM playerData WHERE UUID=?");
            statement.setString(1, this.uuid.toString());
            final ResultSet results = statement.executeQuery();
            results.next();
            return results.getBoolean("Register");
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void setRegister(UUID uuid , final boolean s) {
        try {
            Database.getStatement().executeUpdate("UPDATE playerData SET Register=" + s + " WHERE UUID='" + this.uuid + "'");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getPassword(final UUID uuid) {
        try {
            final PreparedStatement statement = Database.getConnection().prepareStatement("SELECT * FROM playerData WHERE UUID=?");
            statement.setString(1, uuid.toString());
            final ResultSet results = statement.executeQuery();
            results.next();
            return results.getInt("Password");
        }
        catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public void setPassword(UUID uuid , final int password) {
        try {
            Database.getStatement().executeUpdate("UPDATE playerData SET Password=" + password + " WHERE UUID='" + this.uuid + "'");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isLogging() {
        return this.logging.contains(this.uuid);
    }

    public void setLogged(UUID uuid , final boolean s) {
        if (s) {
            this.logging.remove(this.uuid);
            if (Bukkit.getOfflinePlayer(this.uuid).isOnline()) {
                Bukkit.getPlayer(this.uuid).removePotionEffect(PotionEffectType.BLINDNESS);
                if (this.contents.containsKey(this.uuid)) {
                    Bukkit.getPlayer(this.uuid).getInventory().setContents((ItemStack[])this.contents.get(this.uuid));
                    this.contents.remove(this.uuid);
                }
            }
        }
        else {
            this.logging.add(this.uuid);
            Bukkit.getPlayer(this.uuid).addPotionEffect(new PotionEffect (PotionEffectType.BLINDNESS, Integer.MAX_VALUE, 10));
            this.contents.put(this.uuid, Bukkit.getPlayer(this.uuid).getInventory().getContents());
            Bukkit.getPlayer(this.uuid).getInventory().clear();
        }
    }
}

