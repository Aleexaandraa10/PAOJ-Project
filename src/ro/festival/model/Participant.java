package ro.festival.model;

public class Participant {
    private String participantName;
    private int age;
    private Ticket ticket;

    public Participant(String participantName, int age, Ticket ticket) {
        this.participantName = participantName;
        this.age = age;
        this.ticket = ticket;
    }

    public String getParticipantName() {
        return participantName;
    }
    public int getAge() { return age;}
    public Ticket getTicket() { return ticket;}

    @Override
    public String toString() {
        return " Participant: " + participantName +
                "\nAge: " + age +
                (ticket != null ? "\n" + ticket.toString() : "\nNo ticket assigned yet.");
    }

}
