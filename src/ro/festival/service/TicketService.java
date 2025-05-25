package ro.festival.service;

import ro.festival.dao.TicketDAO;
import ro.festival.model.Ticket;

import java.util.List;

public class TicketService {
    private static TicketService instance;

    private TicketService() {}

    public static TicketService getInstance() {
        if (instance == null) {
            instance = new TicketService();
        }
        return instance;
    }

    public void addTicket(Ticket t) {
        TicketDAO.getInstance().create(t);
    }

    public Ticket getTicketByCode(String code) {
        return TicketDAO.getInstance().read(code).orElse(null);
    }

    public List<Ticket> getAllTickets() {
        return TicketDAO.getInstance().readAll();
    }

    public void updateTicket(Ticket t) {
        TicketDAO.getInstance().update(t);
    }

    public void deleteTicket(String code) {
        TicketDAO.getInstance().delete(code);
    }
}
