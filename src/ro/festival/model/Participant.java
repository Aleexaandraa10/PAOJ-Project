package ro.festival.model;

public record Participant(String participantName, int age, Ticket ticket) {

    @Override
    public String toString() {
        return " Participant: " + participantName +
                "\nAge: " + age +
                (ticket != null ? "\n" + ticket : "\nNo ticket assigned yet.");
    }

}
