package ro.festival;

import ro.festival.database.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InitHelper {
    public static boolean isInitialized() {
        try (Connection conn = DBConnection.connect()) {
            return tableHasData(conn, "Ticket")
                    || tableHasData(conn, "TicketUnder25")
                    || tableHasData(conn, "Participant")
                    || tableHasData(conn, "Event")
                    || tableHasData(conn, "Concert")
                    || tableHasData(conn, "DJ")
                    || tableHasData(conn, "GlobalTalks")
                    || tableHasData(conn, "FunZone")
                    || tableHasData(conn, "Game")
                    || tableHasData(conn, "CampEats")
                    || tableHasData(conn, "ParticipantEvent")
                    || tableHasData(conn, "GlobalTalkSeat")
                    || tableHasData(conn, "Organizer");
        } catch (SQLException e) {
            e.printStackTrace();
            return true;
        }
    }

    private static boolean tableHasData(Connection conn, String tableName) {
        String sql = "SELECT 1 FROM " + tableName + " LIMIT 1";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return true;
        }
    }

    public static void setInitialized(boolean value) {
    }

}
