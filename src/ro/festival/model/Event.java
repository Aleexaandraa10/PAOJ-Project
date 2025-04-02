package ro.festival.model;

import java.time.LocalTime;

public abstract class Event {
    private final String eventName;
    private final LocalTime startTime;
    private final LocalTime endTime;
    private final FestivalDay day;

    public Event(String eventName, LocalTime startTime, LocalTime endTime, FestivalDay day) {
        this.eventName = eventName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.day = day;
    }
    public String getEventName() { return eventName; }
    public LocalTime getStartTime() { return startTime; }
    public FestivalDay getDay() { return day; }

    @Override
    public String toString() {
        return eventName + " starts at " + startTime.toString() + " and ends at " + endTime.toString() + " on " + day + ".";
    }
}
