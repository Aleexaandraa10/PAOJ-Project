package ro.festival.model;

public class Ticket {
    private String code;
    protected double price;

    public Ticket(String code, double price) {
        this.code = code;
        this.price = price;

    }
    public String getCode() { return code; }


    @Override
    public String toString() {
        return "Ticket: " + code + " | Price: " + price + " RON.";
    }
}
