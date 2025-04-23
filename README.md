
# 🎉 Festival App

This Java application simulates a management platform for a large-scale music festival like UNTOLD or Neversea. It offers interactive features for both participants and organizers, with a gamified and dynamic console-based interface.

## 🎯 Purpose of the Application

The Festival App enables:

- management of festival events and schedules;
- interaction with the FunZone area;
- ticket purchasing and seat reservation;
- earning and spending points through a gamified reward system;
- simulation of a mini-tournament between participants.

The application supports two types of users: **Participants** and **Organizers**, each with a dedicated menu and specific options.

---

## 🎟️ Participant Menu

1. **View all tickets (including discounts)**  
   Displays all available tickets and applies discounts for users under the age of 25.

2. **Participants under 25**  
   Lists participants who are eligible for special discounted tickets.

3. **Participation Statistics**  
   Shows the top 3 most active participants and the most attended event type.

4. **DJ Sets on the Main Stage**  
   Displays a list of DJs performing on the main stage.

5. **All-night Games in the FunZone**  
   Filters and displays games that are open all night.

6. **Purchase a Ticket**  
   Allows the user to buy a ticket and input personal information.

7. **Reserve a Seat for Limited-Capacity Events (GlobalTalks)**  
   Participants can reserve seats for talks, limited by capacity.

8. **Search Events Starting After a Certain Time**  
   Filters events based on a user-defined starting time.

9. **Points System – Earn & Spend**
    - Earn points automatically when buying tickets (10% of ticket price).
    - Bonus points are awarded for attending CampEats, FunZone, and GlobalTalks events.
    - Points can be redeemed for rewards (e.g., badges, VIP access).

10. **Join the Mini-Tournament in FunZone**
    - Join using your ticket code.
    - Opponents are randomly generated.
    - Simulates match rounds (1v1 or 3-player matches).
    - The final winner earns 50 bonus points.

---

## 🛠️ Organizer Menu

1. **View Full Schedule for a Specific Day**  
   Displays all events scheduled for a selected day in chronological order.

2. **View Organizers and Their Events**  
   Lists each organizer along with the events they manage.

3. **Group Events by Type**  
   Groups events into categories: `Concert`, `DJ`, `CampEats`, `FunZone`, `GlobalTalks`.

4. **Sort Events by Start Time**  
   Lists all festival events in chronological order.

5. **Reschedule an Event**  
   Allows organizers to change the day of an event; the schedule updates accordingly.

---

## 🧑‍💻 Technologies Used

- Java 17
- Object-Oriented Programming (OOP)
- Generic Collections (`List`, `Set`, `Map`)
- Modular design: separation of `Main` and `FestivalService`
- Points and gamification system
- Role-based interactive menu


--- 

## 📌 Notes

- The app is fully functional via the command line.
- All demo data is generated inside the `initDemoData()` method from `FestivalService.java`.
