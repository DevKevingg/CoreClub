package me.devkevin.core.database;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.bukkit.Bukkit;

public class Database {

    private static Connection connection;
    private static PreparedStatement statement;

    public static void openConnection() {
        try {
            if (Database.connection != null && !Database.connection.isClosed()) {
                return;
            }
            Class.forName("com.mysql.jdbc.Driver");
            Database.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/core", "root", "yourpassword");
            Bukkit.getLogger().info("[Core] Database has been connected");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void closeConnection() {
        try {
            if (Database.connection == null && Database.connection.isClosed()) {
                return;
            }
            Database.connection.close();
            Bukkit.getLogger().info("[Core] Database has been disconnected");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createTables() {
        try {
            final String sql = "CREATE TABLE IF NOT EXISTS rankdata(uuid varchar(64), name varchar(64), rank varchar(20))";
            (Database.statement = Database.connection.prepareStatement(sql)).executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return Database.connection;
    }

    public static PreparedStatement getStatement() {
        return Database.statement;
    }
}
