package ro.festival.dao;

import ro.festival.database.DBConnection;
import ro.festival.model.*;
import ro.festival.model.eventtypes.*;

import java.sql.*;
import java.time.LocalTime;
import java.util.Optional;

public class EventDAO {
    private static EventDAO instance;

    public static EventDAO getInstance() {
        if (instance == null) {
            instance = new EventDAO();
        }
        return instance;
    }

    public Optional<Event> read(int id) {
        String sql = "SELECT * FROM Event WHERE id_event = ?";
        try (Connection conn = DBConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String name = rs.getString("event_name");
                LocalTime start = rs.getTime("start_time").toLocalTime();
                LocalTime end = rs.getTime("end_time").toLocalTime();
                String dayName = rs.getString("dayName");
                FestivalDay day = FestivalDay.valueOf(dayName);

                // Aici trebuie să-ți identifici tipul real al evenimentului (ex: Concert, DJ etc.)
                // Momentan întoarcem un simplu Event
                Event e = new Event(name, start, end, day);
                e.setIdEvent(id);

                return Optional.of(e);
            }

        } catch (SQLException e) {
            System.err.println("Error reading event: " + e.getMessage());
        }

        return Optional.empty();
    }
}
