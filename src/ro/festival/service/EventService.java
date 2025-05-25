package ro.festival.service;

import ro.festival.dao.EventDAO;
import ro.festival.model.Event;

public class EventService {
    private static EventService instance;
    private final EventDAO eventDAO;

    private EventService() {
        this.eventDAO = EventDAO.getInstance();
    }

    public static EventService getInstance() {
        if (instance == null) {
            instance = new EventService();
        }
        return instance;
    }

    public void addEvent(Event event) {
        // verifică dacă există deja un event identic (după nume și interval orar)
        boolean exists = eventDAO.existsByNameAndTime(event.getEventName(), event.getStartTime(), event.getEndTime());
        if (!exists) {
            eventDAO.create(event);
        }
    }
}
