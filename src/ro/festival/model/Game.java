package ro.festival.model;

public class Game {
    private String gameName;
    private boolean isOpenAllNight;
    private boolean hasPrize;
    private int maxCapacity;

    public Game(String gameName, boolean isOpenAllNight, boolean hasPrize, int maxCapacity) {
        this.gameName = gameName;
        this.isOpenAllNight = isOpenAllNight;
        this.hasPrize = hasPrize;
        this.maxCapacity = maxCapacity;
    }

    public String getGameName() { return gameName; }
    public boolean isOpenAllNight() { return isOpenAllNight; }
    public boolean hasPrice() { return hasPrize; }
    public int getMaxCapacity() { return maxCapacity; }

    @Override
    public String toString() {
        return gameName +
                (isOpenAllNight ? " is open all night" : " isn't open all night") +
                (hasPrize ? ", has prize available " : ", has no prize available ") +
                        " and has " + maxCapacity + " available seats.";
    }
}
