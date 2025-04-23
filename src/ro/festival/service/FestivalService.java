package ro.festival.service;
import ro.festival.model.*;
import ro.festival.model.eventtypes.*;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

// idee de baza: daca un atribut nu tb sa se modifice dupa instantiere, il fac final
// e ca si cum as spune: acest camp face parte din identitatea obiectului si nu se va schimba

public class FestivalService {
    // final pt un atribut = nu se mai poate schimba referinta listei
    // dar pot modifica continutul listei --> .add(), .clear()

    // Interrogations
    private final List<Ticket> tickets;
    private final Set<Participant> participants;
    private final List<Event> events;
    private final Map<Organizer, List<Event>> organizerEvents;

    // Actions
    private final Map<Integer, List<Event>> scheduleByDay; //programul evenimentelor in fiecare zi
    private final Map<GlobalTalks, List<Participant>> reservedSeats;
    private final Map<Event, List<Participant>> eventAttended;

    // pt fct 9, sa adun punctele participantului si daca a participat la un turneu din joc in Fun Zone
    private static Participant lastTournamentWinner = null;

    public FestivalService() {
        this.tickets = new ArrayList<>();
        this.participants = new HashSet<>();
        this.events = new ArrayList<>();
        this.scheduleByDay = new HashMap<>();
        this.organizerEvents = new HashMap<>();
        this.reservedSeats = new HashMap<>();
        this.eventAttended = new HashMap<>();
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


        // =============================================================================================================
        //                    EVENTS (every event will be added in scheduleByDay + eventAttended )
        // =============================================================================================================

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

        eventAttended.computeIfAbsent(sweetCorner, k -> new ArrayList<>(List.of(p1, p2)));
        eventAttended.computeIfAbsent(fastFood, k -> new ArrayList<>(List.of(p1, p3)));
        eventAttended.computeIfAbsent(salty2, k -> new ArrayList<>(List.of(p4, p5)));
        eventAttended.computeIfAbsent(salty1, k -> new ArrayList<>(List.of(p5, p6)));


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

        eventAttended.computeIfAbsent(fz1, k -> new ArrayList<>(List.of(p7, p8, p6, p1)));
        eventAttended.computeIfAbsent(fz1, k -> new ArrayList<>(List.of(p5, p4, p3)));

        scheduleByDay.computeIfAbsent(1, k -> new ArrayList<>()).add(fz1);
        scheduleByDay.computeIfAbsent(3, k -> new ArrayList<>()).add(fz2);

        // ==================================================
        //             GLOBAL TALKS ( + reserved seats)
        // ==================================================
        GlobalTalks gt1 = new GlobalTalks("Future of Music", LocalTime.of(14, 0),
                LocalTime.of(15, 30), FestivalDay.DAY2, "Elena Popescu", "AI in Music", 8);
        GlobalTalks gt2 = new GlobalTalks("Festival Sustainability", LocalTime.of(10, 0),
                LocalTime.of(11, 30), FestivalDay.DAY3, "Andrei Ionescu", "Green Festivals", 5);
        GlobalTalks gt3 = new GlobalTalks("Marketing 4 Festivals", LocalTime.of(12, 0),
                LocalTime.of(13, 30), FestivalDay.DAY3, "Ioana Georgescu", "Social Media Magic", 4);
        events.addAll(List.of(gt1, gt2, gt3));

        eventAttended.computeIfAbsent(gt1, k -> new ArrayList<>(List.of(p1)));
        eventAttended.computeIfAbsent(gt2, k -> new ArrayList<>(List.of(p4, p3)));
        eventAttended.computeIfAbsent(gt3, k -> new ArrayList<>(List.of(p7, p8)));

        scheduleByDay.computeIfAbsent(2, k -> new ArrayList<>()).add(gt1);
        scheduleByDay.computeIfAbsent(3, k -> new ArrayList<>()).addAll(List.of(gt2, gt3));

        reservedSeats.put(gt1, new ArrayList<>(List.of(p1, p2, p3, p4, p5, p6, p7, p8)));
        reservedSeats.put(gt2, new ArrayList<>(List.of(p1, p2, p3, p4)));
        reservedSeats.put(gt3, new ArrayList<>(List.of(p8))); //nu am putut face doar List.of(p8) pt ca a
                            // s fi creat o lista imutabila(read-only) pe care nu as mai fi putut s-o modific dupa
                            // asa cum fac la fct de la 5


        // ======================================================================
        //             ORGANIZERS ( adding in organizerEvents )
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


    // =================================================================================================================
    //                                           APPLICATION FUNCTIONALITIES - PARTICIPANTS
    // =================================================================================================================
    // === 1. View all tickets ===
    public void printAllTickets(){
        if(tickets.isEmpty()){
            System.out.println("No tickets found");
            return;
        }
        tickets.forEach(System.out::println);
    }

    // === 2. View participants under 25 ===
    public void printParticipantsUnder25(){
        participants.stream()
                .filter(p-> p.age()<25)
                .forEach(p->System.out.println(p.participantName() + " (" + p.age() + " years old)"));
    }

    // === 3. View participation stats (top participants & event types) ===
    public void viewParticipationInsights() {
        System.out.println("----- Festival Participation Insights -----");

        String mostCommonType = events.stream()
                .collect(Collectors.groupingBy(
                        e -> e.getClass().getSimpleName(),     // grupăm după tipul clasei (ex: "Concert")
                        Collectors.counting()                        //  numărăm câte apariții are fiecare tip
                )) //pana aici am facut Map<String, Long> unde String este tipul ev si Long este nr de aparitii
                .entrySet().stream()                                 //  convertim mapa într-un stream de entry-uri
                .max(Map.Entry.comparingByValue())        //  căutăm entry-ul cu cea mai mare valoare (adică cel mai frecvent)
                .map(Map.Entry::getKey)                   //  extragem cheia (adică tipul clasei)
                .orElse("N/A");                     //  dacă nu există niciun element, returnăm "N/A"


        System.out.println("\nMost frequent event type: " + mostCommonType);

        // === Participanții cu cele mai multe participări ===
        Map<Participant, Long> participations = eventAttended.values().stream()//.values() ia doar List<Participant> din perechea (cheie, val)
                .flatMap(List::stream) //aplatizam toate listele de participanti intr-un flux continuu
                .collect(Collectors.groupingBy(p -> p, Collectors.counting())); // obtinem un Map<Participant, Long>, unde Long e de cate ori a participat

        System.out.println("\nTop 3 participants by number of attended events:");
        participations.entrySet().stream()
                .sorted(Map.Entry.<Participant, Long>comparingByValue().reversed())
                .limit(3)
                .forEach(entry -> System.out.println("• " + entry.getKey().participantName() + ": " + entry.getValue() + " events"));
    }

    // === 4. Show all DJ sets on the main stage ===
    public void printDJOnMainStage(){
        events.stream()
                .filter(e-> e instanceof DJ) //true doar daca e este un DJ
                .map( e -> (DJ) e) //transforma fiecare Event din stream intr-un ob de tip DJ, folosind un cast
                .filter(DJ::getIsMainStage)
                .forEach(System.out::println);
    }

    // === 5. Show all night-games in FunZone ===
    public void printAllNightGames(){
        events.stream()
                .filter( e -> e instanceof FunZone)
                .map( e -> (FunZone) e) //necesar sa facem ca sa putem accesa metode ale clasei FunZone
                .flatMap(fz -> fz.getGames().stream()) //"desface" toate listele de jocuri intr-un
                // singur flux continuu
                .filter(Game::isOpenAllNight) //pastreaza doar jocurile deschise toata noaptea
                .forEach(System.out::println);
    }

    // === 6. Buy a ticket + add participant ===
    public void buyTicketInteractively(Scanner scanner) {
        System.out.println("Let's buy you a ticket.");

        System.out.print("How old are you? ");
        String input = scanner.nextLine().trim();

        if (!input.matches("^(1[4-9]|[2-5][0-9]|60)$")) {
            System.out.println("Invalid age. Must be between 14 and 60.");
            return;
        }

        int age = Integer.parseInt(input);
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

        //unul sau mai multe nume precedate de spatiu, prima lit mare, restul mici, din cel putin 2 litere
        if (!name.matches("^([A-Z][a-z]+)( [A-Z][a-z]+)*$")) {
            System.out.println("Invalid name. It should start with a capital letter and have more than one letter.");
            return;
        }

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

    // === 7. Reserve a seat for a limited-capacity event ===
    public void reserveSeat_GlobalTalk(Scanner scanner){
        List<GlobalTalks> talks = events.stream()
                .filter(e -> e instanceof GlobalTalks)
                .map( e -> (GlobalTalks) e)
                .toList();
        if(talks.isEmpty()){
            System.out.println("No global talks found");
            return;
        }

        System.out.println("Available global talks events: ");
        for(int i=0; i<talks.size(); i++){
            System.out.println( (i+1) + ". " + talks.get(i).toString());
        }

        System.out.println("Choose the event number: ");
        int index = Integer.parseInt(scanner.nextLine()) - 1;

        if(index < 0 || index >= talks.size()){
            System.out.println("Please enter a valid index.");
            return;
        }

        GlobalTalks selected = talks.get(index);
        // computeIfAbsent = daca nu exista il creez si il adaug, daca exista il folosesc
        List<Participant> reserved = reservedSeats.computeIfAbsent(selected, k -> new ArrayList<>());

        if(reserved.size() >= selected.getSeats()){
            System.out.println("Sorry, this talk is fully booked!");
            return;
        }
        System.out.print("Your name: ");
        String name = scanner.nextLine();

        if (!name.matches("^([A-Z][a-z]+)( [A-Z][a-z]+)*$")) {
            System.out.println("Invalid name. It should start with a capital letter and have more than one letter.");
            return;
        }

        System.out.print("Your age? ");
        int age = Integer.parseInt(scanner.nextLine());

        Participant participant = new Participant(name, age, null);

        reserved.add(participant);
        System.out.println("Reservation confirmed for " + selected.getEventName() + "!");
        System.out.println("\nThe participants for this event are: ");
        reservedSeats.forEach((key, participantsList) -> {
            if(key.equals(selected)){
                for(Participant p: participantsList) {
                    System.out.println(" - " + p.participantName());
                }
            }
        });
        System.out.println("Available seats: " + (selected.getSeats()-reserved.size()) );

    }

    // === 8. Find events that start after a specific time ===
    public void printEventsByStartTime(LocalTime time){
        events.stream()
                .filter(e -> e.getStartTime().isAfter(time))
                .forEach(System.out::println);
    }

    // === 9. Festival Points: Earn & Spend ===
    private void spendPoints(int eventPoints, int cost) {
        if (eventPoints < cost) {
            System.out.println("You don't have enough points to spend this points. You need " + (cost - eventPoints) + " more.");
            return;
        }
        System.out.println("You spent " + cost + " points. Remaining: " + (eventPoints - cost));
    }

    public void calculatePoints(Scanner scanner){
        System.out.print("Enter your ticket code: ");
        String code = scanner.nextLine();

        Participant currentParticipant = participants.stream()
                .filter(p -> p.ticket() != null && p.ticket().getCode().equalsIgnoreCase(code))
                .findFirst()
                .orElse(null);

        if (currentParticipant == null) {
            System.out.println("Invalid ticket.");
            return;
        }

        // evenimentul la care a participat currentParticipant
        // Map.Entry = interfata interna (nested interface) din cadrul interfetei Map
        //             --> un "tip de obiect" care reprezinta o pereche cheie-valoare dintr-o mapa
        //eu ajung la Map.Entry folosind .entrySet() care ret. toate perechile din mapa
        List<Event> attendedEvents = eventAttended.entrySet().stream() //.entrySet() obtine toate perechile (Event, List<Participant>) din mapa
                .filter(entry -> entry.getValue().contains(currentParticipant)) //pastram doar acele intrari (event + participant) unde lista participantilor contine currentParticipant
                .map(Map.Entry::getKey) //din fiecare intrare care a trecut de filtru, extragem cheia, adica ob. Event
                .toList();

        if (attendedEvents.isEmpty()) {
            System.out.println("You haven't participated in any events yet.");
            return;
        }

        System.out.println("\nYou’ve participated in:");
        attendedEvents.forEach(e -> System.out.println("• " + e.getEventName()));
        if (lastTournamentWinner != null && lastTournamentWinner.equals(currentParticipant)){
            System.out.println("• MiniTournament (winner)");
        }

        int eventPoints = 0;
        int ticketPoints = (int) (currentParticipant.ticket().getPrice() * 0.10);
        eventPoints += ticketPoints;

        for (Event event : attendedEvents) {
            if (event instanceof FunZone) {
                eventPoints += 15;
            } else if (event instanceof GlobalTalks) {
                eventPoints += 20;
            } else if (event instanceof CampEats) {
                eventPoints += 10;
            }
        }

        System.out.println("Points from ticket and events: " + eventPoints);
        if (lastTournamentWinner != null && lastTournamentWinner.equals(currentParticipant)) {
            System.out.println("Bonus from tournament: 50");
            eventPoints += 50;
            System.out.println("Total points: " + (eventPoints));
        }

        Map<String, Integer> prizes = Map.of(
                "Festival Badge", 50,
                "Tote Bag", 75,
                "VIP Lounge Access", 120
        );

        System.out.println("\nAvailable Prizes:");
        prizes.forEach((prize, cost) -> System.out.println("• " + prize + " — " + cost + " points"));

        System.out.print("\nWant to redeem a prize? Enter its name or press Enter to skip: ");
        String chosenPrize = scanner.nextLine();

        if (!chosenPrize.isBlank()) {
            if (!prizes.containsKey(chosenPrize)) {
                System.out.println("Prize not found.");
            } else {
                int cost = prizes.get(chosenPrize);
                spendPoints(eventPoints, cost);
            }
        }
    }

    // === 10. Join the FunZone Mini-Tournament ===
    public void registerAndStartMiniTournament(Scanner scanner) {
        System.out.print("Enter your ticket code to join the tournament: ");
        String code = scanner.nextLine();

        Participant userParticipant = participants.stream()
                .filter(p -> p.ticket() != null && p.ticket().getCode().equalsIgnoreCase(code))
                .findFirst()
                .orElse(null);

        if (userParticipant == null) {
            System.out.println("Invalid ticket code. Registration failed.");
            return;
        }

        System.out.print("How many total participants (including you) do you want in the tournament? (min 3): ");
        int total;
        try {
            total = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid number.");
            return;
        }

        if (total < 3) {
            System.out.println("Minimum number of participants is 3.");
            return;
        }

        List<Participant> others = participants.stream()
                .filter(p -> !p.equals(userParticipant))
                .collect(Collectors.toList());

        if (others.size() < total - 1) {
            System.out.println("Not enough participants in the system to form a tournament of that size.");
            return;
        }

        Collections.shuffle(others);
        List<Participant> selectedOpponents = others.subList(0, total - 1);

        List<Participant> tournamentPlayers = new ArrayList<>();
        tournamentPlayers.add(userParticipant);
        tournamentPlayers.addAll(selectedOpponents);

        System.out.println("\nYou have successfully joined the FunZone Mini-Tournament!");
        System.out.println("Here are the participants:");
        tournamentPlayers.forEach(p -> System.out.println("• " + p.participantName()));

        Participant winner = runTournamentRoundFlexible(tournamentPlayers, "First Round");
        System.out.println("\n🏆 The Mini-Tournament Winner is: " + winner.participantName() + "! 🏆");

        lastTournamentWinner = winner;
        System.out.println(winner.participantName() +" earned 50 points!");
        System.out.println("\nWinner's ticket code: " + winner.ticket().getCode());
    }

    private Participant runTournamentRoundFlexible(List<Participant> players, String roundName) {
        if (players.size() == 1) return players.getFirst();

        System.out.println("\n-- " + roundName + " --");
        List<Participant> winners = new ArrayList<>();

        int i = 0;
        while (i < players.size()) {
            int remaining = players.size() - i;

            Participant p1 = players.get(i);
            Participant p2 = players.get(i + 1);
            if (remaining == 3) {
                Participant p3 = players.get(i + 2);

                int s1 = ThreadLocalRandom.current().nextInt(0, 100);
                int s2 = ThreadLocalRandom.current().nextInt(0, 100);
                int s3 = ThreadLocalRandom.current().nextInt(0, 100);

                Map<Participant, Integer> scores = Map.of(p1, s1, p2, s2, p3, s3);
                Participant best = scores.entrySet().stream()
                        .max(Map.Entry.comparingByValue())
                        .get().getKey();

                System.out.printf("3-player round: %s (%d), %s (%d), %s (%d) → Winner: %s\n",
                        p1.participantName(), s1,
                        p2.participantName(), s2,
                        p3.participantName(), s3,
                        best.participantName());

                winners.add(best);
                i += 3;
            } else {
                int score1 = ThreadLocalRandom.current().nextInt(0, 100);
                int score2 = ThreadLocalRandom.current().nextInt(0, 100);

                Participant winner = score1 >= score2 ? p1 : p2;
                winners.add(winner);

                System.out.printf("%s (%d) vs %s (%d) → Winner: %s\n",
                        p1.participantName(), score1,
                        p2.participantName(), score2,
                        winner.participantName());
                i += 2;
            }
        }

        String nextRound = switch (winners.size()) {
            case 4 -> "Semifinals";
            case 3 -> "3-Player Round";
            case 2 -> "Final";
            default -> "Next Round";
        };

        return runTournamentRoundFlexible(winners, nextRound);
    }

    // =================================================================================================================
    //                                           APPLICATION FUNCTIONALITIES - ORGANIZERS
    // =================================================================================================================

    // === 1. View full schedule for a specific day ===
    public void printScheduleForDay(int day) {
        FestivalDay selectedDay = FestivalDay.valueOf("DAY" + day);

        List<Event> dayEvents = scheduleByDay.getOrDefault(day, new ArrayList<>());

        if (dayEvents.isEmpty()) {
            System.out.println("No events scheduled for " + selectedDay);
            return;
        }

        System.out.println("Events scheduled for " + selectedDay + ":");
        dayEvents.stream()
                .sorted(Comparator.comparing(Event::getStartTime))
                .forEach(System.out::println);
    }

    // === 2. View all organizers and their events ===
    public void printOrganizersAndEvents(){
        organizerEvents.forEach((organizer, eventList) -> {
            System.out.println("Organizers: " + organizer.organizerName());
            eventList.forEach(e->System.out.println("    - " + e));
        });
    }

    // === 3. Group events by type ===
    public void groupEventsByType(){
        Map<String, List<Event>> grouped = events.stream()
                .collect(Collectors.groupingBy(e->e.getClass().getSimpleName()));

        // pentru fiecare el. e vreau sa fie grupat dupa e.getClass().getSimpleName(), deci se creeaza automat map
        // String din cheie este rezultatul e.getClass().getSimpleName()
        //.collect() este o op terminala intr-un Stream, care transforma fluxul de date intr-o colectie noua
        // .collect() = ok, am parcurs stream-ul, acum vreau sa strang totul intr-un rezultat final

        grouped.forEach((type, eventList) -> {
            System.out.println("\n=== " + type + " ===");
            eventList.forEach(System.out::println);
        });
        // .getClass() --> Concert.class
        // .getSimpleName() --> Concert
    }

    // === 4. Order all events by start time ===
    public void printEventsOrderedByStartTime() {
        events.stream()
                .sorted(Comparator.comparing(Event::getStartTime))
                .forEach(System.out::println);
    }



    // === 5. Move an event to another day ===
    public void moveEvent(Scanner scanner){
        if (scheduleByDay.isEmpty()) {
            System.out.println("No events scheduled.");
            return;
        }

        System.out.println("Available events:");
        scheduleByDay.forEach((day, eventList) -> {
            System.out.println("=============");
            System.out.println("Day: " + day);
            System.out.println("=============");
            System.out.println("Events: ");
            for(int i=0; i<eventList.size(); i++){
                System.out.println((i+1) + ". " + eventList.get(i).toString());
            }
            System.out.println();
        });
        System.out.println("Please choose the day: ");
        int dayEvent = Integer.parseInt(scanner.nextLine());
        System.out.println("Please choose the number of the event you would like to move: ");
        int eventIndex = Integer.parseInt(scanner.nextLine()) - 1;

        if (eventIndex < 0 || eventIndex >= scheduleByDay.get(dayEvent).size()){
            System.out.println("Invalid selection.");
            return;
        }

        Event selectedEvent = scheduleByDay.get(dayEvent).get(eventIndex);

        System.out.print("Enter the new day (1-3): ");
        int newDay = Integer.parseInt(scanner.nextLine());

        if (newDay < 1 || newDay > 3) {
            System.out.println("Invalid day.");
            return;
        }

        if (newDay == dayEvent) {
            System.out.println("The event is already on that day.");
            return;
        }

        scheduleByDay.get(dayEvent).remove(selectedEvent);
        // fac asa pt ca daca puneam scheduleByDay.get(newDay).add(selectedEvent) mi-ar fi putut arunca NullPointerException
        // in cazul in care ziua 1,2 sau 3 nu exista niciun event
        // cu computeIfAbsent daca in ziua 3 de ex nu e niciun event, se init o noua lista si se adauga eventul
        scheduleByDay.computeIfAbsent(newDay, k -> new ArrayList<>()).add(selectedEvent);

        System.out.println("Event '" + selectedEvent.getEventName() + "' has been moved from Day " + dayEvent + " to Day " + newDay + ".");
        System.out.println("If you want to see your event, please select 1 in the menu!");
    }

}
