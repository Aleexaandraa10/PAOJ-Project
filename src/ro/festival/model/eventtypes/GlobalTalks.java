package ro.festival.model.eventtypes;

import ro.festival.model.FestivalDay;
import ro.festival.model.Event;

import java.time.LocalTime;

public class GlobalTalks extends Event{
    private final String speakerName;
    private final String topic;
    private final int seats;

    public GlobalTalks(String eventName, LocalTime startTime, LocalTime endTime, FestivalDay day, String speakerName, String topic, int seats) {
        super(eventName, startTime, endTime, day);
        this.speakerName = speakerName;
        this.topic = topic;
        this.seats = seats;
    }

    public String getSpeakerName() { return speakerName; }
    public String getTopic() { return topic; }
    public int getSeats() { return seats; }

    @Override
    public String toString(){
        return super.toString() + "\n    The speaker is " + speakerName
                + ", discussing the topic " + topic + ", with " + seats + " available seats.";
    }
}
