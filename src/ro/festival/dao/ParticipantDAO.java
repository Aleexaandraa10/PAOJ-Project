package ro.festival.dao;

import ro.festival.database.DBConnection;
import ro.festival.model.Participant;
import ro.festival.model.Ticket;
import ro.festival.service.TicketService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ParticipantDAO extends BaseDAO<Participant, Integer> {

    private static ParticipantDAO instance;

    public static ParticipantDAO getInstance() {
        if (instance == null) {
            instance = new ParticipantDAO();
        }
        return instance;
    }

    @Override
    public void create(Participant participant) {
        String sql = "INSERT INTO Participant (name, age, ticket_code) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, participant.getParticipantName());
            stmt.setInt(2, participant.getAge());
            stmt.setString(3, participant.getTicket().getCode());
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error inserting participant:");
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Participant> read(Integer id) {
        String sql = "SELECT id, name, age, ticket_code FROM Participant WHERE id = ?";
        try (Connection conn = DBConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String name = rs.getString("name");
                int age = rs.getInt("age");
                String ticketCode = rs.getString("ticket_code");
                Ticket ticket = TicketService.getInstance().getTicketByCode(ticketCode);
                Participant p = new Participant(name, age, ticket);
                return Optional.of(p);
            }

        } catch (SQLException e) {
            System.err.println("Error reading participant:");
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public void update(Participant participant) {
        String sql = "UPDATE Participant SET name = ?, age = ?, ticket_code = ? WHERE id = ?";
        try (Connection conn = DBConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, participant.getParticipantName());
            stmt.setInt(2, participant.getAge());
            stmt.setString(3, participant.getTicket().getCode());
            stmt.setInt(4, participant.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error updating participant:");
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM Participant WHERE id = ?";
        try (Connection conn = DBConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error deleting participant:");
            e.printStackTrace();
        }
    }

    public List<Participant> readAll() {
        List<Participant> participants = new ArrayList<>();
        String sql = "SELECT id, name, age, ticket_code FROM Participant";

        try (Connection conn = DBConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String name = rs.getString("name");
                int age = rs.getInt("age");
                String ticketCode = rs.getString("ticket_code");
                Ticket ticket = TicketService.getInstance().getTicketByCode(ticketCode);

                Participant p = new Participant(name, age, ticket);
                p.setId(rs.getInt("id"));
                participants.add(p);
            }

        } catch (SQLException e) {
            System.err.println("Error reading all participants:");
            e.printStackTrace();
        }

        return participants;
    }

    public Participant findByNameAndAge(String name, int age) {
        String sql = "SELECT * FROM Participant WHERE name = ? AND age = ?";
        try (Connection conn = DBConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setInt(2, age);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Participant p = new Participant(
                        rs.getString("name"),
                        rs.getInt("age"),
                        TicketService.getInstance().getTicketByCode(rs.getString("ticket_code"))
                );
                p.setId(rs.getInt("id_participant"));
                return p;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
