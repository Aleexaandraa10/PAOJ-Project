package ro.festival.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// creeaza efectiv conexiunea cu baza de date
public class DBConnection {
    public static Connection connect() {
        try {
            return DriverManager.getConnection(
                    Config.getDbUrl(),
                    Config.getDbUser(),
                    Config.getDbPassword()
            );
        } catch (SQLException e) {
            System.err.println("Error while connecting to the database:");
            e.printStackTrace();
            return null;
        }
    }

    public static void disconnect(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Database connection closed.");
            } catch (SQLException e) {
                System.err.println("Error while closing the database connection:");
                e.printStackTrace();
            }
        }
    }
}
