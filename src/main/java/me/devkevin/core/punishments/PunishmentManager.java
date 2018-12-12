package me.devkevin.core.punishments;

import me.devkevin.core.CorePlugin;
import me.devkevin.core.database.Database;
import me.devkevin.core.utils.Duration;
import org.bukkit.*;
import org.bukkit.plugin.*;
import java.sql.*;
import java.util.*;
import org.apache.commons.lang.time.*;

public class PunishmentManager
{
    public static int PUNISHMENT_EXPIRE_PERMANENTLY;
    public static int PUNISHMENT_EXPIRED;
    private int nextId;
    private Map<UUID, List<Punishment>> punishments;
    private String sqlQueryAll;
    private String sqlQueryBanned;
    private String sqlInsert;
    private String sqlUpdate;
    private Map<UUID, List<Punishment>> updatePunishments;
    private PreparedStatement update;
    private int threadId;

    public PunishmentManager(final CorePlugin plugin) {
        this.nextId = 1;
        this.punishments = new HashMap<UUID, List<Punishment>>();
        this.updatePunishments = new HashMap<UUID, List<Punishment>>();
        final String sql = "CREATE TABLE IF NOT EXISTS punishmentData(id INT NOT NULL AUTO_INCREMENT, type varchar(45) NOT NULL, banned varchar(45) NOT NULL, punisher varchar(45) NULL, expires BIGINT, reason varchar(45) NULL, PRIMARY KEY(id))";
        final String sqlGetNextID = "SELECT MAX(id) AS max FROM punishmentData";
        try {
            PreparedStatement statement = Database.getConnection().prepareStatement(sql);
            statement.executeUpdate();
            statement = Database.getConnection().prepareStatement(sqlGetNextID);
            final ResultSet results = statement.executeQuery();
            while (results.next()) {
                this.nextId = results.getInt("max") + 1;
            }
            this.sqlQueryAll = "SELECT * FROM punishmentData WHERE expires <> 0 AND expires > UNIX_TIMESTAMP()";
            this.sqlQueryBanned = "SELECT * FROM punishmentData WHERE banned=?";
            this.sqlInsert = "INSERT INTO punishmentData(type,banned,punisher,expires,reason) VALUES (?,?,?,?,?)";
            this.sqlUpdate = "UPDATE punishmentData SET expires = ? WHERE id = ?";
            this.update = Database.getConnection().prepareStatement(this.sqlInsert);
        }
        catch (SQLException e) {
            System.out.println("[Core] " + e.getMessage());
        }
        this.loadActivePunishments();
        this.threadId = Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin)plugin, (Runnable)new Runnable() {
            @Override
            public void run() {
                try {
                    PunishmentManager.this.update.executeBatch();
                    PunishmentManager.this.updatePunishments = (Map<UUID, List<Punishment>>)new HashMap();
                }
                catch (SQLException e) {
                    System.out.println("[Core] " + e.getMessage());
                }
            }
        }, 60L, 60L);
    }

    public void loadActivePunishments() {
        this.punishments = new HashMap<UUID, List<Punishment>>();
        try {
            final PreparedStatement statement = Database.getConnection().prepareStatement(this.sqlQueryAll);
            final List<Punishment> list = this.resultSetToPunishments(statement.executeQuery());
            for (final Punishment punishment : list) {
                this.addPunishmentLocal(punishment, this.punishments);
            }
        }
        catch (SQLException e) {
            System.out.println("[Core] " + e.getMessage());
        }
    }

    public void loadActivePunishmentsFor(final UUID id) {
        List<Punishment> list;
        if (this.updatePunishments.containsKey(id)) {
            list = this.updatePunishments.get(id);
        }
        else {
            list = new ArrayList<Punishment>();
        }
        list.addAll(this.getPunishments(id));
        this.punishments.put(id, list);
    }

    public List<Punishment> getPunishments(final UUID id) {
        try {
            final PreparedStatement statement = Database.getConnection().prepareStatement(this.sqlQueryBanned);
            statement.setString(1, id.toString());
            return this.resultSetToPunishments(statement.executeQuery());
        }
        catch (SQLException e) {
            System.out.println("[Core] " + e.getMessage());
            return null;
        }
    }

    public List<Punishment> getActivePunishements(final UUID id) {
        return this.punishments.containsKey(id) ? this.punishments.get(id) : new ArrayList<Punishment>();
    }

    public List<Punishment> getAllPunishments(final UUID id) {
        final List<Punishment> punishments = this.getPunishments(id);
        if (this.updatePunishments.containsKey(id)) {
            punishments.addAll(this.updatePunishments.get(id));
        }
        return punishments;
    }

    public Punishment hasActivePunishment(final UUID id, final PunishmentType type) {
        for (final Punishment punishment : this.getActivePunishements(id)) {
            if (punishment.getType() == type && !punishment.hasExpired()) {
                return punishment;
            }
        }
        return null;
    }

    public Punishment addPunishment(final PunishmentType type, final UUID banned, final UUID punisher, final long expires, final String reason) {
        final Punishment punishment = new Punishment(this.nextId, type, banned, punisher, expires, reason);
        ++this.nextId;
        this.addPunishmentLocal(punishment, this.punishments);
        this.addPunishmentLocal(punishment, this.updatePunishments);
        try {
            this.update.setString(1, type.toString());
            this.update.setString(2, banned.toString());
            this.update.setString(3, (punisher != null) ? punisher.toString() : null);
            this.update.setLong(4, expires);
            this.update.setString(5, reason);
            this.update.addBatch();
            ++this.nextId;
        }
        catch (SQLException e) {
            System.out.println("[Core] " + e.getMessage());
        }
        return punishment;
    }

    private void addPunishmentLocal(final Punishment punishment, final Map<UUID, List<Punishment>> map) {
        if (map.containsKey(punishment.getBanned())) {
            map.get(punishment.getBanned()).add(punishment);
        }
        else {
            final List<Punishment> temp = new ArrayList<Punishment>();
            temp.add(punishment);
            map.put(punishment.getBanned(), temp);
        }
    }

    public void updatePunishment(final Punishment punishment, final long expires) {
        try {
            final PreparedStatement statement = Database.getConnection().prepareStatement(this.sqlUpdate);
            statement.setLong(1, expires);
            statement.setInt(2, punishment.getId());
            statement.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println("[Core] " + e.getMessage());
        }
    }

    private List<Punishment> resultSetToPunishments(final ResultSet results) {
        final List<Punishment> list = new ArrayList<Punishment>();
        try {
            while (results.next()) {
                final int id = results.getInt("id");
                final PunishmentType type = PunishmentType.valueOf(results.getString("type"));
                final UUID target = UUID.fromString(results.getString("banned"));
                final UUID admin = (results.getString("punisher") != null) ? UUID.fromString(results.getString("punisher")) : null;
                final long expires = results.getLong("expires");
                final String reason = results.getString("reason");
                list.add(new Punishment(id, type, target, admin, expires, reason));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void save() {
        try {
            this.update.executeBatch();
            this.updatePunishments = new HashMap<UUID, List<Punishment>>();
        }
        catch (SQLException e) {
            System.out.println("[Core] " + e.getMessage());
        }
    }

    public void cancel() {
        Bukkit.getServer().getScheduler().cancelTask(this.threadId);
    }

    static {
        PunishmentManager.PUNISHMENT_EXPIRE_PERMANENTLY = -1;
        PunishmentManager.PUNISHMENT_EXPIRED = 0;
    }

    public class Punishment
    {
        private int id;
        private PunishmentType type;
        private UUID banned;
        private UUID punisher;
        private long expires;
        private String reason;

        public Punishment(final int id, final PunishmentType type, final UUID banned, final UUID punisher, final long expires, final String reason) {
            this.id = id;
            this.type = type;
            this.banned = banned;
            this.punisher = punisher;
            this.expires = expires;
            this.reason = reason;
        }

        public int getId() {
            return this.id;
        }

        public long getExpires() {
            return this.expires - System.currentTimeMillis();
        }

        public boolean hasExpired() {
            return this.expires == PunishmentManager.PUNISHMENT_EXPIRED || (this.expires != -1L && this.expires <= System.currentTimeMillis());
        }

        public void removePunishment() {
            this.setExpires(PunishmentManager.PUNISHMENT_EXPIRED);
        }

        public void setExpires(final long time) {
            PunishmentManager.this.updatePunishment(this, time);
            this.expires = time;
        }

        public UUID getBanned() {
            return this.banned;
        }

        public UUID getPunisher() {
            return this.punisher;
        }

        public String getReason() {
            return this.reason;
        }

        public PunishmentType getType() {
            return this.type;
        }

        public String getMessage() {
            final StringBuilder builder = new StringBuilder();
            builder.append(this.getType().getContext());
            if (this.getExpires() != PunishmentManager.PUNISHMENT_EXPIRE_PERMANENTLY) {
                builder.append(" ");
                builder.append(DurationFormatUtils.formatDurationWords(this.getExpires(), true, true));
            }
            if (this.getReason() != null) {
                builder.append(" for ");
                builder.append(this.getReason());
            }
            return builder.toString();
        }

        public String getBanTimeLeft() {
            if (this.getExpires() <= PunishmentManager.PUNISHMENT_EXPIRE_PERMANENTLY) {
                return "Permanently";
            }
            return "Remaining: " + Duration.getRemaining(this.getExpires(), true, true);
        }

        public String getMuteTimeLeft() {
            if (this.getExpires() <= PunishmentManager.PUNISHMENT_EXPIRE_PERMANENTLY) {
                return "permanently";
            }
            return "for another " + Duration.getRemaining(this.getExpires(), true, true);
        }
    }
}
