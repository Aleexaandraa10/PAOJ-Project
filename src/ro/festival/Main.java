package ro.festival;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // !!!!!!!!! VEZI CA AI PUTEA FOLOSI JUNIT !!!!!!!!!!!!!!!
        // mai intai implementeaza tu niste functionalitati si mai vezi dupa
        Scanner scanner = new Scanner(System.in);

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
            System.out.println("4.  View full schedule for a specific day");
            System.out.println("5.  View top 3 longest events");
            System.out.println("6.  Order all events by start time");
            System.out.println("7.  Group events by type");
            System.out.println("8.  Find events that start at a specific time");

            // Event types
            System.out.println("9.  Show all DJ sets on the main stage");
            System.out.println("10. Show all-night games in FunZone");

            // Food
            System.out.println("11. Show food vendors open all night");

            // Organizers
            System.out.println("12. View all organizers and their events");

            // Exit
            System.out.println("0.  Exit the application");
            System.out.println("=================================================");
            //      -p-lpk,
            System.out.print("Enter your choice: ");
            String input = scanner.nextLine();
            System.out.println();

            switch (input) {
                case "1" -> System.out.println("Let's buy you a ticket.");
                case "2" -> System.out.println("Participants under 25:");
                case "3" -> System.out.println("All tickets issued:");
                case "4" -> {
                    System.out.println("What day would you like to view?");
                    System.out.println("1 - Day 1");
                    System.out.println("2 - Day 2");
                    System.out.println("3 - Day 3");
                    System.out.print("Your choice: ");
                    String dayChoice = scanner.nextLine();
                    switch (dayChoice) {
                        case "1" -> System.out.println("Schedule for Day 1:\n");
                        case "2" -> System.out.println("Schedule for Day 2:\n");
                        case "3" -> System.out.println("Schedule for Day 3:\n");
                        default -> System.out.println("Invalid day selected.\n");
                    }
                }
                case "5" -> System.out.println("Top 3 longest events:");
                case "6" -> System.out.println("Events ordered by start time:");
                case "7" -> System.out.println("Events grouped by type:");
                case "8" -> {
                    System.out.print("Enter time (HH:mm): ");
                    String time = scanner.nextLine();
                    System.out.println("Events starting at " + time + ":");
                }
                case "9"  -> System.out.println("DJ sets on the main stage:");
                case "10" -> System.out.println("Games in FunZone open all night:");
                case "11" -> System.out.println("Food vendors open all night:");
                case "12" -> System.out.println("Organizers and their events:");
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
