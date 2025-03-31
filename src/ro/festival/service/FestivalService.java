//// Ticket related
//            System.out.println("1.  Buy a ticket");
//            System.out.println("2.  View participants under 25");
//            System.out.println("3.  View all tickets (including discounts)");
//
//// Event & schedule
//            System.out.println("4.  View full schedule for a specific day");
//            System.out.println("5.  View top 3 longest events");
//            System.out.println("6.  Order all events by start time");
//            System.out.println("7.  Group events by type");
//            System.out.println("8.  Find events that start at a specific time");

package ro.festival.service;
import ro.festival.model.*;


import java.time.Duration;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

public class FestivalService {
    private List<Ticket> tickets = new ArrayList<>();
    private Set<Participant> participants = new HashSet<>();
    private List<Event> events = new ArrayList<>();
    private Map<Integer, List<Event>> scheduleByDay = new HashMap<>();


    public FestivalService() {
        //aici va fi demo
    }

    // === 1. Buy a ticket ===
//    public void buyTicket(Participant p, double price) {
//        Ticket ticket = new Ticket(p, price);
//    }

    // === 2. View participants under 25 ===
    public void printParticipantsUnder25(){
        participants.stream()
                .filter(p-> p.getAge()<25)
                .forEach(p->System.out.println(p.getParticipantName() + "(" + p.getAge() + "years old)"));

    }

    // === 3. View all tickets ===
    public void printAllTickets(){
        if(tickets.isEmpty()){
            System.out.println("No tickets found");
            return;
        }
        tickets.forEach(System.out::println);
    }

    // === 4. View full schedule for a specific day ===
    public void printScheduleForDay(int day){
        //se returneaza lista de evenimente pt ziua day
        List<Event> dayEvents = scheduleByDay.getOrDefault(day, new ArrayList<>());

        if(dayEvents.isEmpty()){
            System.out.println("No events scheduled for Day " + day);
            return;
        }
        dayEvents.forEach(System.out::println);
    }

    // === 5. View top 3 longest events ===
    public void printTop3LongestEvents() {
        events.stream()
                .sorted(Comparator.comparing(e ->
                                Duration.between(((Event) e).getStartTime(), ((Event) e).getEndTime()))
                        .reversed()
                )
                .limit(3)
                .forEach(System.out::println);
    }

    // === 6. Order all events by start time ===
    public void printEventsOrderedByStartTime() {
        events.stream()
                .sorted(Comparator.comparing(Event::getStartTime))
                .forEach(System.out::println);
    }

    // === 7. Group events by type ===
    public void groupEventsByType(){
        Map<String, List<Event>> grouped = events.stream()
                .collect(Collectors.groupingBy(e->e.getClass().getSimpleName()));

        // pentru fiecare el. e vreau sa fie grupat dupa e.getClass().getSimpleName(), deci se creeaza automat map
        // String din cheie este rezultatul e.getClass().getSimpleName()
        //.collect() este o op terminala intr-un Stream, care transforma fluxul de date intr-o colectie noua
        // .collect() = ok, am parcurs stream-ul, acum vreau sa strang totul intr-un rezultat final

        grouped.forEach((type, eventList) -> {
            System.out.println("=== " + type + " ===");
            eventList.forEach(System.out::println);
        });
        // .getClass() --> Concert.class
        // .getSimpleName() --> Concert
    }

    // === 8. Find events that start at a specific time ===
    public void printEventsByStartTime(LocalTime time){
        events.stream()
                .filter(e -> e.getStartTime().equals(time))
                .forEach(System.out::println);
    }



}
