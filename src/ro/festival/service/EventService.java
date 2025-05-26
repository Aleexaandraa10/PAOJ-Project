package ro.festival.service;

import ro.festival.dao.EventDAO;
import ro.festival.dao.eventtypes.*;
import ro.festival.model.Event;
import ro.festival.model.FestivalDay;

import java.util.List;
import java.util.Optional;

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
    public List<Event> getAllEvents() {
        return eventDAO.readAll();
    }

    public Optional<Event> getEventById(int id) {
        return eventDAO.read(id);
    }

    public void updateEvent(Event event) {
        eventDAO.update(event);
    }
    public List<Event> getEventsByDay(FestivalDay day) {
        return eventDAO.getEventsByDay(day);
    }

    public Event getTypedEventById(int id) {
        Event base = EventDAO.getInstance().read(id).orElse(null);
        if (base == null) return null;

        Event typed = null;

        typed = ConcertDAO.getInstance().read(id).orElse(null);
        if (typed != null) return typed;

        typed = DJDAO.getInstance().read(id).orElse(null);
        if (typed != null) return typed;

        typed = GlobalTalksDAO.getInstance().read(id).orElse(null);
        if (typed != null) return typed;

        typed = CampEatsDAO.getInstance().read(id).orElse(null);
        if (typed != null) return typed;

        typed = FunZoneDAO.getInstance().read(id).orElse(null);
        if (typed != null) return typed;

        return base; // fallback, dar nu ar trebui să ajungă aici
    }


}
