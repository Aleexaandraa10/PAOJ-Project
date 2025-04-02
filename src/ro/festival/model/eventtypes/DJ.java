package ro.festival.model.eventtypes;

import ro.festival.model.FestivalDay;
import ro.festival.model.Event;

import java.time.LocalTime;

public class DJ extends Event {
    private final String djName;
    private final boolean isMainStage;

    public DJ(String eventName, LocalTime startTime, LocalTime endTime, FestivalDay day, String djName, boolean isMainStage) {
        super(eventName, startTime, endTime, day);
        this.djName = djName;
        this.isMainStage = isMainStage;
    }

    public boolean getIsMainStage() {return isMainStage;}

    @Override
    public String toString() {
        return super.toString() + "\n    The DJ features " + djName + ". You can find the DJ on " + (isMainStage ? "the main stage." : "the secondary stage.");
    }
}
