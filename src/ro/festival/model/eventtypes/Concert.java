package ro.festival.model.eventtypes;

import ro.festival.model.Event;
import ro.festival.model.FestivalDay;
import java.time.LocalTime;

public class Concert extends Event {
    private final String artist;
    private final String genre;

    public Concert(String eventName, LocalTime startTime, LocalTime endTime, FestivalDay day, String artist, String genre) {
        super(eventName, startTime, endTime, day);
        this.artist = artist;
        this.genre = genre;
    }

    public String getArtist() { return artist; }
    public String getGenre() { return genre; }

    @Override
    public String toString() {
        return super.toString() + "\n    The concert features " + artist + " performing " + genre + " music.";
    }

}
