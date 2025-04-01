package ro.festival;

import ro.festival.service.FestivalService;

import java.time.LocalTime;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // !!!!!!!!! VEZI CA AI PUTEA FOLOSI JUNIT !!!!!!!!!!!!!!!
        // mai intai implementeaza tu niste functionalitati si mai vezi dupa
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

            // Ticket related
            System.out.println("1.  Buy a ticket");
            System.out.println("2.  View participants under 25");
            System.out.println("3.  View all tickets (including discounts)");

            // Event & schedule
            System.out.println("4.  View full schedule for a specific day ordered by start time");
            System.out.println("5.  Reserve a seat for a limited-capacity event");


            System.out.println("6.  Order all events by start time");
            System.out.println("7.  Group events by type");
            System.out.println("8.  Find events that start after a specific time");

            // Event types
            System.out.println("9.  Show all DJ sets on the main stage");
            System.out.println("10. Show all-night games in FunZone");
            System.out.println("11. Join a competition in the FunZone");

            // Organizers
            System.out.println("12. View all organizers and their events");
            System.out.println("13. Move an event to another day");
            System.out.println("14. View festival statistics");

            // Exit
            System.out.println("0.  Exit the application");
            System.out.println("=================================================");

            System.out.print("Enter your choice: ");
            String input = scanner.nextLine();
            System.out.println();

            switch (input) {
                case "1" -> festivalService.buyTicketInteractively(scanner);
                case "2" -> {
                    System.out.println("Participants under 25:");
                    festivalService.printParticipantsUnder25();
                }
                case "3" -> {
                    System.out.println("All tickets issued:");
                    festivalService.printAllTickets();
                }
                case "4" -> {
                    System.out.println("What day would you like to view?");
                    System.out.println("1 - Day 1");
                    System.out.println("2 - Day 2");
                    System.out.println("3 - Day 3");
                    System.out.print("Your choice: ");

                    try {
                        int day = Integer.parseInt(scanner.nextLine());

                        if (day >= 1 && day <= 3) {
                            System.out.println("Schedule for Day " + day + ":\n");
                            festivalService.printScheduleForDay(day);
                        } else {
                            System.out.println("Invalid day selected.\n");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Please enter a valid number (1, 2, or 3).");
                    }
                }

                case "5" -> {
                    festivalService.reserveSeat();
                }
                case "6" -> {
                    System.out.println("Events ordered by start time:");
                    festivalService.printEventsOrderedByStartTime();
                }
                case "7" -> {
                    System.out.println("Events grouped by type:");
                    festivalService.groupEventsByType();
                }
                case "8" -> {
                    System.out.print("Enter time (HH:mm): ");
                    LocalTime time = LocalTime.parse(scanner.nextLine());
                    System.out.println("Events starting after " + time + ":");
                    festivalService.printEventsByStartTime(time);
                }
                case "9"  -> {
                    System.out.println("DJ sets on the main stage:");
                    festivalService.printDJOnMainStage();
                }
                case "10" -> {
                    System.out.println("Games in FunZone open all night:");
                    festivalService.printAllNightGames();
                }
                case "11" -> {
                    festivalService.joinCompetition();
                }
                case "12" -> {
                    System.out.println("Organizers and their events:");
                    festivalService.printOrganizersAndEvents();
                }
                case "13" ->{
                    festivalService.moveEvent();
                }
                case "14" -> {
                    festivalService.viewStatistics();
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
