package ro.festival.model;

public class Participant {
    private final int id;
    private final String participantName;
    private final int age;
    private final Ticket ticket;

    public Participant(int id, String participantName, int age, Ticket ticket) {
        this.id = id;
        this.participantName = participantName;
        this.age = age;
        this.ticket = ticket;
    }

    public Participant(String participantName, int age, Ticket ticket) {
        this(0, participantName, age, ticket);
    }

    public int getId() {
        return id;
    }

    public String getParticipantName() {
        return participantName;
    }

    public int getAge() {
        return age;
    }

    public Ticket getTicket() {
        return ticket;
    }

    @Override
    public String toString() {
        return "Participant: " + participantName +
                "\nAge: " + age +
                (ticket != null ? "\n" + ticket : "\nNo ticket assigned yet.");
    }
}
