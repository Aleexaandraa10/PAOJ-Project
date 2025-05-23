package ro.festival.database;

import ro.festival.database.DBConnection;

import java.sql.Connection;

public class MainConnection {
    public static void main(String[] args) {
        System.out.println("Starting database connection test...");

        Connection conn = DBConnection.connect();

        if (conn != null) {
            System.out.println("Successfully connected to the database!");
            DBConnection.disconnect(conn);
        } else {
            System.out.println("Failed to connect to the database.");
        }
    }
}
