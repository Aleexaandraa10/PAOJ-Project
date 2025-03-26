package ro.festival.model;

import java.util.ArrayList;
import java.util.List;

public class EventSchedule {
    private FestivalDay day;
    private List<Event> events;

    public EventSchedule(FestivalDay day) {
        this.day = day;
        this.events = new ArrayList<>();
    }
    public FestivalDay getDay() { return day; }
    public List<Event> getEvents() { return events; }

}
