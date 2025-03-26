package ro.festival.model;

import java.util.List;

public class Organizer {
    private String companyName;
    private String organizerName;
    private List<Event> events;

    public Organizer(String companyName, String organizerName, List<Event> events) {
        this.companyName = companyName;
        this.organizerName = organizerName;
        this.events = events;
    }
    public String getCompanyName() { return companyName; }
    public String getOrganizerName() { return organizerName; }
    public List<Event> getEvents() { return events; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Organizer company: " + companyName + "\n");
        sb.append("Representitive: " + organizerName + "\n");
        sb.append("Events organized in this festival:\n");

        if (events.isEmpty()) {
            sb.append("No events assigned yet.");
        } else{
            for (Event e : events) {
                sb.append("\n   - ").append(e.getEventName()).append(" (").append(e.getDay()).append(")");
            }
        }
        return sb.toString();
    }

}
