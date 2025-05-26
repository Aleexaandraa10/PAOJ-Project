package ro.festival;

import ro.festival.database.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InitHelper {
    private static boolean initialized = false;

    public static boolean isInitialized() {
        // Nu setăm `initialized` decât dacă toate tabelele sunt populate
        try (Connection conn = DBConnection.connect()) {
            boolean allPopulated =
                    tableHasData(conn, "Ticket") &&
                            tableHasData(conn, "TicketUnder25") &&
                            tableHasData(conn, "Participant") &&
                            tableHasData(conn, "Event") &&
                            tableHasData(conn, "Concert") &&
                            tableHasData(conn, "DJ") &&
                            tableHasData(conn, "GlobalTalks") &&
                            tableHasData(conn, "FunZone") &&
                            tableHasData(conn, "Game") &&
                            tableHasData(conn, "CampEats") &&
                            tableHasData(conn, "ParticipantEvent") &&
                            tableHasData(conn, "GlobalTalkSeat") &&
                            tableHasData(conn, "Organizer");

            initialized = allPopulated;
            return allPopulated;

        } catch (SQLException e) {
            e.printStackTrace();
            return false; // presupunem că baza nu e inițializată dacă apare o eroare
        }
    }

    private static boolean tableHasData(Connection conn, String tableName) {
        String sql = "SELECT 1 FROM " + tableName + " LIMIT 1";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            return rs.next();
        } catch (SQLException e) {
            System.err.println("Eroare la verificarea tabelei: " + tableName);
            e.printStackTrace();
            return false;
        }
    }

    public static void setInitialized(boolean value) {
        initialized = value;
    }
}
