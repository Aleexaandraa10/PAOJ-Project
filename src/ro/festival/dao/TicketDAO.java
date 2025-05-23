package ro.festival.dao;

import ro.festival.database.DBConnection;
import ro.festival.model.Ticket;
import ro.festival.model.TicketUnder25;

import java.sql.*;
import java.util.Optional;

public class TicketDAO extends BaseDAO<Ticket, String> {

    private static TicketDAO instance;

    public static TicketDAO getInstance() {
        if (instance == null) {
            instance = new TicketDAO();
        }
        return instance;
    }

    @Override
    public void create(Ticket ticket) {
        String sql = "INSERT INTO Ticket (code, price) VALUES (?, ?)";
        try (Connection conn = DBConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, ticket.getCode());
            stmt.setDouble(2, ticket.getPrice());
            stmt.executeUpdate();

            // dacă e TicketUnder25, inserează și în tabela secundară
            if (ticket instanceof TicketUnder25) {
                String sqlU25 = "INSERT INTO TicketUnder25 (code, discountPercentage) VALUES (?, ?)";
                try (PreparedStatement stmtU25 = conn.prepareStatement(sqlU25)) {
                    stmtU25.setString(1, ticket.getCode());
                    stmtU25.setDouble(2, ((TicketUnder25) ticket).getDiscountPercentage());
                    stmtU25.executeUpdate();
                }
            }

        } catch (SQLException e) {
            System.err.println("Error inserting ticket:");
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Ticket> read(String code) {
        String sql = """
            SELECT t.code, t.price, tu.discountPercentage
            FROM Ticket t
            LEFT JOIN TicketUnder25 tu ON t.code = tu.code
            WHERE t.code = ?
        """;

        try (Connection conn = DBConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, code);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                double price = rs.getDouble("price");
                double discount = rs.getDouble("discountPercentage");
                boolean isUnder25 = !rs.wasNull();

                Ticket ticket = isUnder25
                        ? new TicketUnder25(code, price, discount)
                        : new Ticket(code, price);

                return Optional.of(ticket);
            }

        } catch (SQLException e) {
            System.err.println("Error reading ticket:");
            e.printStackTrace();
        }

        return Optional.empty();
    }

    @Override
    public void update(Ticket ticket) {
        String sql = "UPDATE Ticket SET price = ? WHERE code = ?";
        try (Connection conn = DBConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, ticket.getPrice());
            stmt.setString(2, ticket.getCode());
            stmt.executeUpdate();

            if (ticket instanceof TicketUnder25) {
                String sqlU25 = "UPDATE TicketUnder25 SET discountPercentage = ? WHERE code = ?";
                try (PreparedStatement stmtU25 = conn.prepareStatement(sqlU25)) {
                    stmtU25.setDouble(1, ((TicketUnder25) ticket).getDiscountPercentage());
                    stmtU25.setString(2, ticket.getCode());
                    stmtU25.executeUpdate();
                }
            }

        } catch (SQLException e) {
            System.err.println("Error updating ticket:");
            e.printStackTrace();
        }
    }

    @Override
    public void delete(String code) {
        try (Connection conn = DBConnection.connect()) {
            // șterge din TicketUnder25 dacă există
            try (PreparedStatement stmtU25 = conn.prepareStatement("DELETE FROM TicketUnder25 WHERE code = ?")) {
                stmtU25.setString(1, code);
                stmtU25.executeUpdate();
            }

            // șterge din Ticket
            try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM Ticket WHERE code = ?")) {
                stmt.setString(1, code);
                stmt.executeUpdate();
            }

        } catch (SQLException e) {
            System.err.println("Error deleting ticket:");
            e.printStackTrace();
        }
    }
}
