package ro.festival;

import ro.festival.service.FestivalService;

import java.time.LocalTime;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        FestivalService festivalService = new FestivalService();
        festivalService.initDemoData();

        System.out.println("Welcome to the Festival App!");
        System.out.println("We’re happy to see you!\n");

        boolean running = true;

        while (running) {
            System.out.println("=================================================");
            System.out.println("What would you like to do?");
            System.out.println("-------------------------------------------------");

            // Interrogations
            System.out.println("1.  View all tickets (including discounts)");
            System.out.println("2.  View participants under 25");
            System.out.println("3.  View participation statistics (top participants & event types)");
            System.out.println("4.  View full schedule for a specific day ordered by start time");
            System.out.println("5.  Show all DJ sets on the main stage");
            System.out.println("6.  Show all-night games in FunZone");
            System.out.println("7.  View all organizers and their events");

            // Actions
            System.out.println("8.  Buy a ticket");
            System.out.println("9.  Group events by type");
            System.out.println("10. Order all events by start time");
            System.out.println("11. Find events that start after a specific time");
            System.out.println("12. Reserve a seat for a limited-capacity event");
            System.out.println("13. Move an event to another day");
            System.out.println("14. Festival Points: Earn & Spend");

            // Exit
            System.out.println("0. Exit the application");
            System.out.println("=================================================");

            System.out.print("Enter your choice: ");
            String input = scanner.nextLine();
            System.out.println();

            switch (input) {
                case "1" -> {
                    System.out.println("All tickets issued:");
                    festivalService.printAllTickets();
                }
                case "2" -> {
                    System.out.println("Participants under 25:");
                    festivalService.printParticipantsUnder25();
                }
                case "3" -> festivalService.viewParticipationInsights();
                case "4" -> {
                    System.out.println("What day would you like to view?");
                    System.out.println("1 - Day 1");
                    System.out.println("2 - Day 2");
                    System.out.println("3 - Day 3");
                    System.out.print("Your choice: ");

                    try {
                        int day = Integer.parseInt(scanner.nextLine());

                        if (day >= 1 && day <= 3) {
                            festivalService.printScheduleForDay(day);
                        } else {
                            System.out.println("Invalid day selected.\n");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Please enter a valid number (1, 2, or 3).");
                    }
                }
                case "5"  -> {
                    System.out.println("DJ sets on the main stage:");
                    festivalService.printDJOnMainStage();
                }
                case "6" -> {
                    System.out.println("Games in FunZone open all night:");
                    festivalService.printAllNightGames();
                }
                case "7" -> {
                    System.out.println("Organizers and their events:");
                    festivalService.printOrganizersAndEvents();
                }
                case "8" -> festivalService.buyTicketInteractively(scanner);
                case "9" -> {
                    System.out.println("Events grouped by type:");
                    festivalService.groupEventsByType();
                }
                case "10" -> {
                    System.out.println("Events ordered by start time:");
                    festivalService.printEventsOrderedByStartTime();
                }
                case "11" -> {
                    System.out.print("Enter time (HH:mm): ");
                    LocalTime time = LocalTime.parse(scanner.nextLine());
                    System.out.println("Events starting after " + time + ":");
                    festivalService.printEventsByStartTime(time);
                }
                case "12" -> {
                    System.out.println("Reserve a seat for a limited-capacity event:");
                    festivalService. reserveSeat_GlobalTalk(scanner);
                }
                case "13" -> {
                    System.out.println("This option is only for organizers!");
                    festivalService.moveEvent(scanner);
                }
                case "14" -> {
                    System.out.println("Festival Points System:");
                    System.out.println("- Earn points by purchasing tickets (10% of ticket value)");
                    System.out.println("- Gain extra points by attending CampEats, GlobalTalks, or FunZone events");
                    System.out.println("- Spend points on exclusive prizes!");
                    festivalService.calculatePoints(scanner);
                }
                case "0"  -> {
                    System.out.println("Thank you for visiting the Festival App. See you soon!");
                    running = false;
                }
                default -> System.out.println("Invalid option. Please try again.\n");
            }
            System.out.println();
        }
        scanner.close();
    }
}
