package ro.festival.model;

public enum FestivalDay{
    DAY1, DAY2, DAY3;

    @Override
    public String toString() {
        return name().replace("DAY", "Day " );
    }
}
