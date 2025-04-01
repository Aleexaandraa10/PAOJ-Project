package ro.festival.service;
import ro.festival.model.*;
import ro.festival.model.eventtypes.*;
import java.time.Duration;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

// idee de baza: daca un atribut nu tb sa se modifice dupa instantiere, il fac final
// e ca si cum as spune: acest camp face parte din identitatea obiectului si nu se va schimba

public class FestivalService {
    // final pt un atribut = nu se mai poate schimba referinta listei
    // dar pot modifica continutul listei --> .add(), .clear()

    private final List<Ticket> tickets;
    private final Set<Participant> participants;
    private final List<Event> events;
    private final Map<Integer, List<Event>> scheduleByDay; //programul evenimentelor in fiecare zi
    private final Map<Organizer, List<Event>> organizerEvents;


    public FestivalService() {
        this.tickets = new ArrayList<>();
        this.participants = new HashSet<>();
        this.events = new ArrayList<>();
        this.scheduleByDay = new HashMap<>();
        this.organizerEvents = new HashMap<>();
    }


    public void initDemoData(){
        // ==================================
        //              TICKETS
        // ==================================
        Ticket t1 = new Ticket("T001", 250.0);
        Ticket t2 = new Ticket("T002", 300.0);
        Ticket t3 = new Ticket("T003", 275.5);
        Ticket t4 = new Ticket("T004", 310.75);
        Ticket t5 = new Ticket("T005", 199.99);
        TicketUnder25 t6 = new TicketUnder25("T006", 200.0, 10.0);
        TicketUnder25 t7 = new TicketUnder25("T007", 180.0, 15.0);
        TicketUnder25 t8 = new TicketUnder25("T008", 220.0, 12.5);

        tickets.addAll(List.of(t1, t2, t3, t4, t5, t6, t7, t8));

        // ==================================
        //              PARTICIPANTS
        // ==================================
        Participant p1 = new Participant("Ioana", 28, t1);
        Participant p2 = new Participant("Mihai", 32, t2);
        Participant p3 = new Participant("Elena", 25, t3);
        Participant p4 = new Participant("Vlad", 29, t4);
        Participant p5 = new Participant("Radu", 23, t5); // sub 25, dar are bilet normal
        Participant p6 = new Participant("Ana", 22, t6); // TicketUnder25
        Participant p7 = new Participant("Daria", 20, t7); // TicketUnder25
        Participant p8 = new Participant("Alex", 19, t8); // TicketUnder25

        participants.addAll(List.of(p1, p2, p3, p4, p5, p6, p7, p8));


        // ====================================================================================
        //                    EVENTS (every event will be added in scheduleByDay)
        // ====================================================================================

        // ==================================
        //              CAMP EATS
        // ==================================

        //  Salty Corner 1 – Ziua 1
        CampEats salty1 = new CampEats(
                "Salty Delights", LocalTime.of(17, 0), LocalTime.of(2, 0), FestivalDay.DAY1,
                "The Pretzel Cart", List.of("pretzels", "nachos", "salted peanuts"), true
        );

        //  Salty Corner 2 – Ziua 2
        CampEats salty2 = new CampEats(
                "Savoury Stop", LocalTime.of(16, 0), LocalTime.of(23, 30), FestivalDay.DAY2,
                "Savory & Co", List.of("cheese sticks", "mini-pies", "salty muffins"), false
        );

        //  Sweet Corner – Ziua 3
        CampEats sweetCorner = new CampEats(
                "Sweet Corner", LocalTime.of(12, 0), LocalTime.of(22, 0), FestivalDay.DAY3,
                "Candy House", List.of("cotton candy", "lollipops", "ice cream"), false
        );

        //  Fast Food – Ziua 1
        CampEats fastFood = new CampEats(
                "Fast & Yummy", LocalTime.of(19, 0), LocalTime.of(3, 30), FestivalDay.DAY1,
                "Grill Bros", List.of("burgers", "fries", "cola"), true
        );
        events.addAll(List.of(salty1, salty2, sweetCorner, fastFood));


        //computeIfAbsent(...)
        //      --> verifica daca o cheie exista deja intr-un Map
        //      --> daca nu exista, creeaza o valoarea noua, o adauga in Map si o returneaza
        //      --> daca exista, doar o returneaza
        scheduleByDay.computeIfAbsent(1, k -> new ArrayList<>()).addAll(List.of(salty1, fastFood));
        scheduleByDay.computeIfAbsent(2, k -> new ArrayList<>()).add(salty2);
        scheduleByDay.computeIfAbsent(3, k -> new ArrayList<>()).add(sweetCorner);

        // ==================================
        //              CONCERT
        // ==================================
        Concert c1 = new Concert("Imagine Dragons Live", LocalTime.of(20, 0),
                LocalTime.of(22, 30), FestivalDay.DAY1, "Imagine Dragons", "Alternative Rock");
        Concert c2 = new Concert("Coldplay Vibes", LocalTime.of(22, 30),
                LocalTime.of(0, 0), FestivalDay.DAY1, "Coldplay", "Pop Rock");
        Concert c3 = new Concert("EDM Arena", LocalTime.of(21, 0),
                LocalTime.of(23, 0), FestivalDay.DAY2, "Martin Garrix", "EDM");
        events.addAll(List.of(c1, c2, c3));

        scheduleByDay.computeIfAbsent(1, k -> new ArrayList<>()).addAll(List.of(c1, c2));
        scheduleByDay.computeIfAbsent(2, k -> new ArrayList<>()).add(c3);
        //nu se creeaza o lista noua, doar returneaza lista existenta si o foloseste

        // ==================================
        //              DJ
        // ==================================
        DJ dj1 = new DJ("Night Vibes", LocalTime.of(23, 0),
                LocalTime.of(1, 0), FestivalDay.DAY2, "Armin Van Buuren", true);
        DJ dj2 = new DJ("Electro Pulse", LocalTime.of(1, 0),
                LocalTime.of(3, 0), FestivalDay.DAY3, "David Guetta", true);
        DJ dj3 = new DJ("Chill Grooves", LocalTime.of(20, 0),
                LocalTime.of(22, 0), FestivalDay.DAY3, "Kygo", false);
        events.addAll(List.of(dj1, dj2, dj3));

        scheduleByDay.computeIfAbsent(2, k -> new ArrayList<>()).add(dj1);
        scheduleByDay.computeIfAbsent(3, k -> new ArrayList<>()).addAll(List.of(dj2, dj3));

        // ==================================
        //             FUN ZONE
        // ==================================
        // Games
        Game g1 = new Game("Escape Room", true, true, 8);
        Game g2 = new Game("Arcade Zone", false, false, 20);
        Game g3 = new Game("Virtual Reality Arena", true, false, 10);
        Game g4 = new Game("Foosball Tournament", false, true, 12);

        FunZone fz1 = new FunZone("FunZone Madness", LocalTime.of(16, 0),
                LocalTime.of(22, 0), FestivalDay.DAY1, List.of(g1, g2));
        FunZone fz2 = new FunZone("GameZone Fiesta",
                LocalTime.of(15, 0), LocalTime.of(23, 0), FestivalDay.DAY3, List.of(g3, g4));
        events.addAll(List.of(fz1, fz2));

        scheduleByDay.computeIfAbsent(1, k -> new ArrayList<>()).add(fz1);
        scheduleByDay.computeIfAbsent(3, k -> new ArrayList<>()).add(fz2);

        // ==================================
        //             FUN ZONE
        // ==================================
        GlobalTalks gt1 = new GlobalTalks("Future of Music", LocalTime.of(14, 0),
                LocalTime.of(15, 30), FestivalDay.DAY2, "Elena Popescu", "AI in Music", 100);
        GlobalTalks gt2 = new GlobalTalks("Festival Sustainability", LocalTime.of(10, 0),
                LocalTime.of(11, 30), FestivalDay.DAY3, "Andrei Ionescu", "Green Festivals", 80);
        GlobalTalks gt3 = new GlobalTalks("Marketing 4 Festivals", LocalTime.of(12, 0),
                LocalTime.of(13, 30), FestivalDay.DAY3, "Ioana Georgescu", "Social Media Magic", 120);
        events.addAll(List.of(gt1, gt2, gt3));

        scheduleByDay.computeIfAbsent(2, k -> new ArrayList<>()).add(gt1);
        scheduleByDay.computeIfAbsent(3, k -> new ArrayList<>()).addAll(List.of(gt2, gt3));

        // ======================================================================
        //             ORGANIZERS ( adding in organizerEvents)
        // =======================================================================
        Organizer org1 = new Organizer("Stage Masters", "Cristian D.",
                List.of(c1, c2, c3, dj1, dj2, dj3));
        Organizer org2 = new Organizer("Taste & Joy", "Alina P.",
                List.of(salty1, salty2, sweetCorner, fastFood));
        Organizer org3 = new Organizer("Experience Lab", "Mihai R.",
                List.of(fz1, fz2, gt1, gt2, gt3));

        // aici s-a folosit put pt ca deja avem lista de evenimente si vrem sa o atribuim unui organizator(cheia in acest caz)
        // computeIfAbsent am folosit pt ca nu stiam sigur daca cheia exista, poate deja am adaugat ev in ziua 1 poate nu
            //computeIfAbsent se asigura ca lista deja exista ca sa pot da .add pe ea

        organizerEvents.put(org1, org1.events());
        organizerEvents.put(org2, org2.events());
        organizerEvents.put(org3, org3.events());
    }

    // === 1. Buy a ticket + add participant ===
    public void buyTicketInteractively(Scanner scanner) {
        System.out.println("Let's buy you a ticket.");

        System.out.print("How old are you? ");
        int age = Integer.parseInt(scanner.nextLine());
        boolean isUnder25 = age <= 25;

        double basePrice = Math.round((200 + Math.random() * 200) * 100.0) / 100.0;

        int discount = 0;
        if (isUnder25) {
            discount = 5 + new Random().nextInt(16);
            System.out.println("You'll receive a discount of " + discount + "%!");
        }

        String code = "T" + String.format("%03d", tickets.size() + 1);
        Ticket ticket = isUnder25
                ? new TicketUnder25(code, basePrice, discount)
                : new Ticket(code, basePrice);

        tickets.add(ticket);

        System.out.print("What's your first name? ");
        String name = scanner.nextLine();



        participants.add(new Participant(name, age, ticket));

        double finalPrice = isUnder25
                ? Math.round(basePrice * (1 - discount / 100.0) * 100.0) / 100.0
                : basePrice;

        System.out.println("\nTicket created successfully!");
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
        System.out.println("Ticket code: " + code);
        System.out.println("Initial price: " + basePrice + " RON");

        if (isUnder25) {
            System.out.println("Discount: " + discount + "%");
            System.out.println("Final price after discount: " + finalPrice + " RON");
        } else {
            System.out.println("No discount applied.");
        }
        System.out.println("Ticket details:\n" + ticket);
    }


    // === 2. View participants under 25 ===
    public void printParticipantsUnder25(){
        participants.stream()
                .filter(p-> p.age()<25)
                .forEach(p->System.out.println(p.participantName() + " (" + p.age() + "years old)"));
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
        dayEvents.stream()
                .sorted(Comparator.comparing(Event::getStartTime))
                .forEach(System.out::println);
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

    // === 8. Find events that start after a specific time ===
    public void printEventsByStartTime(LocalTime time){
        events.stream()
                .filter(e -> e.getStartTime().isAfter(time))
                .forEach(System.out::println);
    }

    // === 9. Show all DJ sets on the main stage ===
    public void printDJOnMainStage(){
        events.stream()
                .filter(e-> e instanceof DJ) //true doar daca e este un DJ
                .map( e -> (DJ) e) //transforma fiecare Event din stream intr-un ob de tip DJ, folosind un cast
                .filter(DJ::getIsMainStage)
                .forEach(System.out::println);
    }

    // === 10. Show all night-games in FunZone ===
    public void printAllNightGames(){
        events.stream()
                .filter( e -> e instanceof FunZone)
                .map( e -> (FunZone) e) //necesar sa facem ca sa putem accesa metode ale clasei FunZone
                .flatMap(fz -> fz.getGames().stream()) //"desface" toate listele de jocuri intr-un
                                                                // singur flux continuu
                .filter(Game::isOpenAllNight) //pastreaza doar jocurile deschise toata noaptea
                .forEach(System.out::println);
    }

    // === 11. Show food vendors open all night ===
    public void printAllNightFood(){
        events.stream()
                .filter(e-> e instanceof CampEats)
                .map( e -> (CampEats) e)
                .filter(CampEats::isOpenUntilLate)
                .forEach(System.out::println);
    }

    // === 12. View all organizers and their events ===
    public void printOrganizersAndEvents(){
        organizerEvents.forEach((organizer, eventList) -> {
            System.out.println("Organizers: " + organizer.organizerName());
            eventList.forEach(e->System.out.println("    - " + e));
        });
    }


}
