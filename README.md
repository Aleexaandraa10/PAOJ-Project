# 🎪 Festival Management Platform

A comprehensive **Java-based festival management system** built as part of a university PAO course project. This application simulates the full ecosystem of a music and cultural festival like **Untold** or **Neversea**, integrating participant registration, event scheduling, organizer coordination, and audit logging.

---

## Key Features

### 📟 Core Functionality

* **CRUD operations** for Participants, Events, Organizers, and Tickets
* **Multiple event types** implemented via inheritance:

  * `Concert`, `DJ`, `CampEats`, `GlobalTalks`, `FunZone`
* **Event participation system** with join operations and validation
* **Ticket types**: Standard & Under-25 (with discount)
* **GlobalTalk seat reservation** system (limited-capacity)
* **FunZone mini-tournament** with random winner and point bonuses
* **Festival Points System** to reward engagement and redeem prizes
* **Under-25 age validation** with correction and penalty
* **Organizer removal with dynamic event reassignment**

### 📄 Database Integration

* **MySQL relational database** with normalized structure
* DAO classes for each entity type, following **Single Responsibility** and **Open/Closed** principles
* Modular CRUD implementations based on an abstract `BaseDAO<T, K>`
* Foreign key relationships managed explicitly (e.g., `ParticipantEvent`, `GlobalTalkSeat`)

### 🔐 Audit Logging

* CSV-based **audit log** that records every significant user action
* Each entry includes `action_name` and `timestamp`
* Singleton `AuditService` class for centralized logging

### 📋 Menu-driven CLI Interface

* Separate interactive menus for Participants & Organizers
* Four service layers handle distinct business logic: `ParticipantService`, `EventService`, `TicketService`, `OrganizerService`
* Central **`FestivalService`** coordinates all operations, integrating the entire business logic
* Input validation and graceful error handling

---

## 🧱 Project Structure

```
Proiect_PAO/
├── src/
│   ├── ro.festival/
│   │   ├── dao/                 # All DAO classes
│   │   ├── database/            # DB connection helper
│   │   ├── model/               # Domain models
│   │   ├── service/             # Services incl. FestivalService & AuditService
│   │   └── InitHelper.java      # Demo data loader
├── audit_log.csv                # Action log file
├── README.md                    # Project overview (this file)
├── docs/                        # Documentation & ER diagrams
│   └── database_diagram.png     # ER Diagram
```

---

## 🖼️ ER Diagram

> You can find the full Entity-Relationship diagram inside the `docs/` folder: [`database_diagram.png`](docs/database_diagram.png)

---

## 🛠️ Technologies Used

* Java 17
* IntelliJ IDEA
* MySQL
* JDBC

---

## 📦 Sample Use Cases

* A 22-year-old user buys a ticket → receives a discount → added as Participant
* A participant is caught misrepresenting age under 25 → corrected and ticket recalculated
* An Organizer is removed → events redistributed to others
* A participant reserves a seat at a limited GlobalTalk → seats decrease in real-time
* A user checks points earned from attended events and redeems a `VIP Lounge Access`
