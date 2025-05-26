package ro.festival.service;
import ro.festival.InitHelper;
import ro.festival.dao.EventDAO;
import ro.festival.dao.GlobalTalkSeatDAO;
import ro.festival.dao.ParticipantDAO;
import ro.festival.dao.ParticipantEventDAO;
import ro.festival.dao.eventtypes.*;
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


    // pt fct 9, sa adun punctele participantului si daca a participat la un turneu din joc in Fun Zone
    private static Participant lastTournamentWinner = null;


    public void initDemoData() {
        if (InitHelper.isInitialized()) {
            System.out.println("The data already exists. InitDemoData won't run.");
            return;
        }

        // ==================================
        //             TICKETS
        // ==================================
        List<Ticket> demoTickets = List.of(
                new Ticket("T001", 250.0),
                new Ticket("T002", 300.0),
                new Ticket("T003", 275.5),
                new Ticket("T004", 310.75),
                new Ticket("T005", 199.99),
                new TicketUnder25("T006", 200.0, 10.0),
                new TicketUnder25("T007", 180.0, 15.0),
                new TicketUnder25("T008", 220.0, 12.5)
        );

        for (Ticket t : demoTickets)
            TicketService.getInstance().addTicket(t);

        // le luăm din DB după cod pentru a asocia corect
        Ticket t1 = TicketService.getInstance().getTicketByCode("T001");
        Ticket t2 = TicketService.getInstance().getTicketByCode("T002");
        Ticket t3 = TicketService.getInstance().getTicketByCode("T003");
        Ticket t4 = TicketService.getInstance().getTicketByCode("T004");
        Ticket t5 = TicketService.getInstance().getTicketByCode("T005");
        Ticket t6 = TicketService.getInstance().getTicketByCode("T006");
        Ticket t7 = TicketService.getInstance().getTicketByCode("T007");
        Ticket t8 = TicketService.getInstance().getTicketByCode("T008");

        // ==================================
        //           PARTICIPANTS
        // ==================================
        Participant p1 = new Participant("Ioana", 28, t1);
        Participant p2 = new Participant("Mihai", 32, t2);
        Participant p3 = new Participant("Elena", 25, t3);
        Participant p4 = new Participant("Vlad", 29, t4);
        Participant p5 = new Participant("Radu", 23, t5);
        Participant p6 = new Participant("Ana", 22, t6);
        Participant p7 = new Participant("Daria", 20, t7);
        Participant p8 = new Participant("Alex", 19, t8);

        for (Participant p : List.of(p1, p2, p3, p4, p5, p6, p7, p8))
            ParticipantService.getInstance().addParticipant(p);

        // ==================================
        //     EVENTS + ORGANIZERS
        // ==================================

        // Organizator 1: Concerte și DJ
        Organizer org1 = new Organizer("Stage Masters", "Cristian D.", List.of(
                new Concert("Imagine Dragons Live", LocalTime.of(20, 0), LocalTime.of(22, 30), FestivalDay.DAY1, "Imagine Dragons", "Alternative Rock"),
                new Concert("Coldplay Vibes", LocalTime.of(22, 30), LocalTime.of(0, 0), FestivalDay.DAY1, "Coldplay", "Pop Rock"),
                new Concert("EDM Arena", LocalTime.of(21, 0), LocalTime.of(23, 0), FestivalDay.DAY2, "Martin Garrix", "EDM"),
                new DJ("Night Vibes", LocalTime.of(23, 0), LocalTime.of(1, 0), FestivalDay.DAY2, "Armin Van Buuren", true),
                new DJ("Electro Pulse", LocalTime.of(1, 0), LocalTime.of(3, 0), FestivalDay.DAY3, "David Guetta", true),
                new DJ("Chill Grooves", LocalTime.of(20, 0), LocalTime.of(22, 0), FestivalDay.DAY3, "Kygo", false)
        ));

        // Organizator 2: Food
        Organizer org2 = new Organizer("Taste & Joy", "Alina P.", List.of(
                new CampEats("Salty Delights", LocalTime.of(17, 0), LocalTime.of(2, 0), FestivalDay.DAY1, "The Pretzel Cart", List.of("pretzels", "nachos", "salted peanuts"), true),
                new CampEats("Savoury Stop", LocalTime.of(16, 0), LocalTime.of(23, 30), FestivalDay.DAY2, "Savory & Co", List.of("cheese sticks", "mini-pies", "salty muffins"), false),
                new CampEats("Sweet Corner", LocalTime.of(12, 0), LocalTime.of(22, 0), FestivalDay.DAY3, "Candy House", List.of("cotton candy", "lollipops", "ice cream"), false),
                new CampEats("Fast & Yummy", LocalTime.of(19, 0), LocalTime.of(3, 30), FestivalDay.DAY1, "Grill Bros", List.of("burgers", "fries", "cola"), true)
        ));

        // Organizator 3: FunZone + Talks
        Organizer org3 = new Organizer("Experience Lab", "Mihai R.", List.of(
                new FunZone("FunZone Madness", LocalTime.of(16, 0), LocalTime.of(22, 0), FestivalDay.DAY1,
                        List.of(
                                new Game("Escape Room", true, true, 8),
                                new Game("Arcade Zone", false, false, 20)
                        )
                ),
                new FunZone("GameZone Fiesta", LocalTime.of(15, 0), LocalTime.of(23, 0), FestivalDay.DAY3,
                        List.of(
                                new Game("Virtual Reality Arena", true, false, 10),
                                new Game("Foosball Tournament", false, true, 12)
                        )
                ),
                new GlobalTalks("Future of Music", LocalTime.of(14, 0), LocalTime.of(15, 30), FestivalDay.DAY2, "Elena Popescu", "AI in Music", 8),
                new GlobalTalks("Festival Sustainability", LocalTime.of(10, 0), LocalTime.of(11, 30), FestivalDay.DAY3, "Andrei Ionescu", "Green Festivals", 5),
                new GlobalTalks("Marketing 4 Festivals", LocalTime.of(12, 0), LocalTime.of(13, 30), FestivalDay.DAY3, "Ioana Georgescu", "Social Media Magic", 4)
        ));

        List<Organizer> organizers = List.of(org1, org2, org3);


        // 1. Inserăm toți organizatorii în baza de date
        for (Organizer org : organizers) {
            OrganizerService.getInstance().addOrganizer(org);
        }

        // 2. Inserăm evenimentele asociate fiecărui organizator, cu ID-ul corect
        for (Organizer org : organizers) {
            for (Event event : org.getEvents()) {
                event.setId_organizer(org.getId());
                if (event instanceof Concert){
                    ConcertDAO.getInstance().create((Concert) event);
                }
                if (event instanceof DJ){
                    DJDAO.getInstance().create((DJ) event);
                }
                if (event instanceof CampEats){
                    CampEatsDAO.getInstance().create((CampEats) event);
                }
                if (event instanceof GlobalTalks){
                    GlobalTalksDAO.getInstance().create((GlobalTalks) event);
                }
                if (event instanceof FunZone){
                    FunZoneDAO.getInstance().create((FunZone) event);
                }
                EventService.getInstance().addEvent(event);
            }
        }

        // ===============================
        //     PARTICIPANT EVENT LINKING
        // ===============================
        ParticipantEventDAO participantEventDAO = ParticipantEventDAO.getInstance();

        // Refolosim obiectele deja instanțiate
        List<Event> allEvents = EventService.getInstance().getAllEvents();

        Event sweetCorner = allEvents.stream().filter(e -> e.getEventName().equals("Sweet Corner")).findFirst().orElse(null);
        Event fastFood = allEvents.stream().filter(e -> e.getEventName().equals("Fast & Yummy")).findFirst().orElse(null);
        Event salty1 = allEvents.stream().filter(e -> e.getEventName().equals("Salty Delights")).findFirst().orElse(null);
        Event salty2 = allEvents.stream().filter(e -> e.getEventName().equals("Savoury Stop")).findFirst().orElse(null);
        Event fz1 = allEvents.stream().filter(e -> e.getEventName().equals("FunZone Madness")).findFirst().orElse(null);
        Event fz2 = allEvents.stream().filter(e -> e.getEventName().equals("GameZone Fiesta")).findFirst().orElse(null);
        Event gt1 = allEvents.stream().filter(e -> e.getEventName().equals("Future of Music")).findFirst().orElse(null);
        Event gt2 = allEvents.stream().filter(e -> e.getEventName().equals("Festival Sustainability")).findFirst().orElse(null);
        Event gt3 = allEvents.stream().filter(e -> e.getEventName().equals("Marketing 4 Festivals")).findFirst().orElse(null);

        if (sweetCorner != null) {
            participantEventDAO.addParticipantToEvent(p1.getId(), sweetCorner.getIdEvent());
            participantEventDAO.addParticipantToEvent(p2.getId(), sweetCorner.getIdEvent());
        }
        if (fastFood != null) {
            participantEventDAO.addParticipantToEvent(p1.getId(), fastFood.getIdEvent());
            participantEventDAO.addParticipantToEvent(p3.getId(), fastFood.getIdEvent());
        }
        if (salty2 != null) {
            participantEventDAO.addParticipantToEvent(p4.getId(), salty2.getIdEvent());
            participantEventDAO.addParticipantToEvent(p5.getId(), salty2.getIdEvent());
        }
        if (salty1 != null) {
            participantEventDAO.addParticipantToEvent(p5.getId(), salty1.getIdEvent());
            participantEventDAO.addParticipantToEvent(p6.getId(), salty1.getIdEvent());
        }
        if (fz1 != null) {
            for (Participant p : List.of(p7, p8, p6, p1))
                participantEventDAO.addParticipantToEvent(p.getId(), fz1.getIdEvent());
        }
        if (fz2 != null) {
            for (Participant p : List.of(p5, p4, p3))
                participantEventDAO.addParticipantToEvent(p.getId(), fz2.getIdEvent());
        }
        if (gt1 != null) participantEventDAO.addParticipantToEvent(p1.getId(), gt1.getIdEvent());
        if (gt2 != null) {
            participantEventDAO.addParticipantToEvent(p4.getId(), gt2.getIdEvent());
            participantEventDAO.addParticipantToEvent(p3.getId(), gt2.getIdEvent());
        }
        if (gt3 != null) {
            participantEventDAO.addParticipantToEvent(p7.getId(), gt3.getIdEvent());
            participantEventDAO.addParticipantToEvent(p8.getId(), gt3.getIdEvent());
        }

        // ===============================
        //  GLOBAL TALK SEAT RESERVATIONS
        // ===============================
        GlobalTalkSeatDAO seatDAO = GlobalTalkSeatDAO.getInstance();
        if (gt1 != null) {
            for (Participant p : List.of(p1, p2, p3, p4, p5, p6, p7, p8))
                seatDAO.reserveSeat(p.getId(), gt1.getIdEvent());
        }
        if (gt2 != null) {
            for (Participant p : List.of(p1, p2, p3, p4))
                seatDAO.reserveSeat(p.getId(), gt2.getIdEvent());
        }
        if (gt3 != null)
            seatDAO.reserveSeat(p8.getId(), gt3.getIdEvent());

        // ===============================
        //       FINALIZARE INIT
        // ===============================
        InitHelper.setInitialized(true);
        System.out.println("Demo data has been initialized successfully.");
    }

    // =================================================================================================================
    //                                           APPLICATION FUNCTIONALITIES - PARTICIPANTS
    // =================================================================================================================
    // === 1. View all tickets ===
    public void printAllTickets(){
        List <Ticket> tickets = TicketService.getInstance().getAllTickets();

        if(tickets.isEmpty()){
            System.out.println("No tickets found");
            return;
        }
        tickets.forEach(System.out::println);
    }

    // === 2. View participants under 25 ===
    public void printParticipantsUnder25(){
        List<Participant> participants = ParticipantService.getInstance().getAllParticipants();

        participants.stream()
                .filter(p-> p.getAge()<25)
                .forEach(p->System.out.println(p.getParticipantName() + " (" + p.getAge() + " years old)"));
    }

    // === 3. View participation stats (top participants & event types) ===
    public void viewParticipationInsights() {
        System.out.println("----- Festival Participation Insights -----");

        List<Event> events = EventService.getInstance().getAllEvents();

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
        Map<Integer, Long> participationCounts = ParticipantEventDAO.getInstance().getParticipationCounts();

        System.out.println("\nTop 3 participants by number of attended events:");

        participationCounts.entrySet().stream()
                .sorted(Map.Entry.<Integer, Long>comparingByValue().reversed())
                .limit(3)
                .forEach(entry -> {
                    Participant p = ParticipantDAO.getInstance().read(entry.getKey()).orElse(null);
                    if (p != null) {
                        System.out.println("• " + p.getParticipantName() + ": " + entry.getValue() + " events");
                    }
                });
    }

    // === 4. Show all DJ sets on the main stage ===
    public void printDJOnMainStage(){
        List<DJ> djs = DJDAO.getInstance().getAllDJs();
        djs.stream()
                .filter(DJ::getIsMainStage)
                .forEach(System.out::println);

    }

    // === 5. Show all night-games in FunZone ===
    public void printAllNightGames() {
        List<FunZone> funZones = FunZoneDAO.getInstance().getAllFunZones();

        funZones.stream()
                .flatMap(fz -> fz.getGames().stream())
                .filter(Game::isOpenAllNight)
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

        int count = TicketService.getInstance().getAllTickets().size();
        String code = "T" + String.format("%03d", count + 1);

        Ticket ticket = isUnder25
                ? new TicketUnder25(code, basePrice, discount)
                : new Ticket(code, basePrice);

        TicketService.getInstance().addTicket(ticket);

        System.out.print("What's your first name? ");
        String name = scanner.nextLine();

        //unul sau mai multe nume precedate de spatiu, prima lit mare, restul mici, din cel putin 2 litere
        if (!name.matches("^([A-Z][a-z]+)( [A-Z][a-z]+)*$")) {
            System.out.println("Invalid name. It should start with a capital letter and have more than one letter.");
            return;
        }

        Participant participant = new Participant(name, age, ticket);
        ParticipantService.getInstance().addParticipant(participant);


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
    public void reserveSeat_GlobalTalk(Scanner scanner) {
        List<GlobalTalks> talks = GlobalTalksDAO.getInstance().getAllGlobalTalks();

        if (talks.isEmpty()) {
            System.out.println("No global talks found");
            return;
        }

        System.out.println("Available global talks events: ");
        for (int i = 0; i < talks.size(); i++) {
            System.out.println((i + 1) + ". " + talks.get(i));
        }

        System.out.println("Choose the event number: ");
        int index = Integer.parseInt(scanner.nextLine()) - 1;

        if (index < 0 || index >= talks.size()) {
            System.out.println("Please enter a valid index.");
            return;
        }

        GlobalTalks selected = talks.get(index);
        int talkId = selected.getIdEvent();

        List<Participant> reserved = GlobalTalkSeatDAO.getInstance().getParticipantsForTalk(talkId);

        if (reserved.size() >= selected.getSeats()) {
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

        Participant participant = ParticipantDAO.getInstance().findByNameAndAge(name, age);

        if (participant == null) {
            System.out.println("Participant not found in system. Please register first.");
            return;
        }

        GlobalTalkSeatDAO.getInstance().reserveSeat(participant.getId(), talkId);

        System.out.println("Reservation confirmed for " + selected.getEventName() + "!");

        System.out.println("\nThe participants for this event are:");
        reserved = GlobalTalkSeatDAO.getInstance().getParticipantsForTalk(talkId);
        for (Participant p : reserved) {
            System.out.println(" - " + p.getParticipantName());
        }

        System.out.println("Available seats: " + (selected.getSeats() - reserved.size()));
    }


    // === 8. Find events that start after a specific time ===
    public void printEventsByStartTime(LocalTime time){
        List<Event> events = EventService.getInstance().getAllEvents();
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

        List<Participant> participants = ParticipantService.getInstance().getAllParticipants();

        Participant currentParticipant = participants.stream()
                .filter(p -> p.getTicket() != null && p.getTicket().getCode().equalsIgnoreCase(code))
                .findFirst()
                .orElse(null);

        if (currentParticipant == null) {
            System.out.println("Invalid ticket.");
            return;
        }

        // evenimentul la care a participat currentParticipant
        List<Integer> attendedEventIds = ParticipantEventDAO.getInstance()
                .getEventIdsForParticipant(currentParticipant.getId());

        List<Event> attendedEvents = attendedEventIds.stream()
                .map(id -> EventDAO.getInstance().read(id).orElse(null))
                .filter(Objects::nonNull)
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
        int ticketPoints = (int) (currentParticipant.getTicket().getPrice() * 0.10);
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

        List<Participant> participants = ParticipantService.getInstance().getAllParticipants();

        Participant userParticipant = participants.stream()
                .filter(p -> p.getTicket() != null && p.getTicket().getCode().equalsIgnoreCase(code))
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
        tournamentPlayers.forEach(p -> System.out.println("• " + p.getParticipantName()));

        Participant winner = runTournamentRoundFlexible(tournamentPlayers, "First Round");
        System.out.println("\n🏆 The Mini-Tournament Winner is: " + winner.getParticipantName() + "! 🏆");

        lastTournamentWinner = winner;
        System.out.println(winner.getParticipantName() +" earned 50 points!");
        System.out.println("\nWinner's ticket code: " + winner.getTicket().getCode());
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
                        p1.getParticipantName(), s1,
                        p2.getParticipantName(), s2,
                        p3.getParticipantName(), s3,
                        best.getParticipantName());

                winners.add(best);
                i += 3;
            } else {
                int score1 = ThreadLocalRandom.current().nextInt(0, 100);
                int score2 = ThreadLocalRandom.current().nextInt(0, 100);

                Participant winner = score1 >= score2 ? p1 : p2;
                winners.add(winner);

                System.out.printf("%s (%d) vs %s (%d) → Winner: %s\n",
                        p1.getParticipantName(), score1,
                        p2.getParticipantName(), score2,
                        winner.getParticipantName());
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

        List<Event> dayEvents = EventService.getInstance().getEventsByDay(selectedDay);

        if (dayEvents.isEmpty()) {
            System.out.println("No events scheduled for " + selectedDay);
            return;
        }

        System.out.println("Events scheduled for " + selectedDay + ":");
        dayEvents.forEach(System.out::println);
    }


    // === 2. View all organizers and their events ===
    public void printOrganizersAndEvents() {
        List<Organizer> organizers = OrganizerService.getInstance().getAllOrganizers();
        List<Event> allEvents = EventService.getInstance().getAllEvents();

        for (Organizer organizer : organizers) {
            System.out.println("Organizer: " + organizer.getOrganizerName());

            List<Event> eventsForOrganizer = allEvents.stream()
                    .filter(e -> e.getId_organizer() == organizer.getId())
                    .toList();

            if (eventsForOrganizer.isEmpty()) {
                System.out.println("    (No events)");
            } else {
                eventsForOrganizer.forEach(e -> System.out.println("    - " + e));
            }
        }
    }


    // === 3. Group events by type ===
    public void groupEventsByType() {
        Map<String, List<Event>> grouped = new LinkedHashMap<>();

        grouped.put("Concert", new ArrayList<>(ConcertDAO.getInstance().getAllConcerts()));
        grouped.put("DJ", new ArrayList<>(DJDAO.getInstance().getAllDJs()));
        grouped.put("GlobalTalks", new ArrayList<>(GlobalTalksDAO.getInstance().getAllGlobalTalks()));
        grouped.put("CampEats", new ArrayList<>(CampEatsDAO.getInstance().getAllCampEats()));
        grouped.put("FunZone", new ArrayList<>(FunZoneDAO.getInstance().getAllFunZones()));

        grouped.forEach((type, eventList) -> {
            System.out.println("\n=== " + type + " ===");
            eventList.forEach(System.out::println);
        });
    }


    // === 4. Order all events by start time ===
    public void printEventsOrderedByStartTime() {
        List<Event> events = EventService.getInstance().getAllEvents();
        events.stream()
                .sorted(Comparator.comparing(Event::getStartTime))
                .forEach(System.out::println);
    }



    // === 5. Move an event to another day ===
    public void moveEvent(Scanner scanner) {
        System.out.print("Please choose the day (1-3): ");
        int dayEvent = Integer.parseInt(scanner.nextLine());

        if (dayEvent < 1 || dayEvent > 3) {
            System.out.println("Invalid day.");
            return;
        }

        FestivalDay selectedDay = FestivalDay.valueOf("DAY" + dayEvent);
        List<Event> dayEvents = EventService.getInstance().getEventsByDay(selectedDay);

        if (dayEvents.isEmpty()) {
            System.out.println("No events on this day.");
            return;
        }

        System.out.println("Events for DAY" + dayEvent + ":");
        for (int i = 0; i < dayEvents.size(); i++) {
            System.out.println((i + 1) + ". " + dayEvents.get(i));
        }

        System.out.print("Please choose the number of the event you would like to move: ");
        int eventIndex = Integer.parseInt(scanner.nextLine()) - 1;

        if (eventIndex < 0 || eventIndex >= dayEvents.size()) {
            System.out.println("Invalid selection.");
            return;
        }

        Event selectedEvent = dayEvents.get(eventIndex);

        System.out.print("Enter the new day (1-3): ");
        int newDay = Integer.parseInt(scanner.nextLine());

        if (newDay < 1 || newDay > 3) {
            System.out.println("Invalid new day.");
            return;
        }

        if (newDay == dayEvent) {
            System.out.println("Event is already on this day.");
            return;
        }

        selectedEvent.setDay(FestivalDay.valueOf("DAY" + newDay));
        EventService.getInstance().updateEvent(selectedEvent);

        System.out.println("✅ Event '" + selectedEvent.getEventName() + "' has been moved to DAY" + newDay + ".");
    }



}
