package ro.festival.model.eventtypes;

import ro.festival.model.FestivalDay;
import ro.festival.model.Event;
import ro.festival.model.Game;
import java.time.LocalTime;
import java.util.List;

public class FunZone extends Event{
    private List<Game> games;

    public FunZone(String eventName, LocalTime startTime, LocalTime endTime, FestivalDay day, List<Game> games) {
        super(eventName, startTime, endTime, day);
        this.games = games;
    }

    public List<Game> getGames() { return games; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append("\n   All the games available:");

        for (Game game : games) {
            sb.append("\n   - ").append(game.toString());
        }

        return sb.toString();
    }

}
