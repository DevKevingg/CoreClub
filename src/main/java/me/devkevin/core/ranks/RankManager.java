package me.devkevin.core.ranks;

import me.devkevin.core.database.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class RankManager
{
    public static String getRankColor(final UUID uuid) {
        return getRank(uuid).getColor();
    }

    public static Rank getRank(final UUID uuid) {
        try {
            final PreparedStatement statement = Database.getConnection().prepareStatement("SELECT * FROM playerData WHERE UUID=?");
            statement.setString(1, uuid.toString());
            final ResultSet results = statement.executeQuery();
            results.next();
            return Rank.getRankOrDefault(results.getInt("Rank"));
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            return Rank.NORMAL;
        }
    }
}


