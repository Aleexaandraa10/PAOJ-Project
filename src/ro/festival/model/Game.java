package ro.festival.model;

public class Game {
    private final String gameName;
    private final boolean isOpenAllNight;
    private final boolean hasPrize;
    private final int maxCapacity;

    public Game(String gameName, boolean isOpenAllNight, boolean hasPrize, int maxCapacity) {
        this.gameName = gameName;
        this.isOpenAllNight = isOpenAllNight;
        this.hasPrize = hasPrize;
        this.maxCapacity = maxCapacity;
    }

    public boolean isOpenAllNight() { return isOpenAllNight; }

    @Override
    public String toString() {
        return gameName +
                (isOpenAllNight ? " is open all night" : " isn't open all night") +
                (hasPrize ? ", has prize available " : ", has no prize available ") +
                        "and has " + maxCapacity + " available seats.";
    }
}
