package ro.festival.model.eventtypes;

import ro.festival.model.FestivalDay;
import ro.festival.model.Event;

import java.time.LocalTime;
import java.util.List;

public class CampEats extends Event {
    private String vendorName;
    private List<String> foodType;
    private boolean openUntilLate;

    public CampEats(String eventName, LocalTime startTime, LocalTime endTime, FestivalDay day, String vendorName, List <String> foodType, boolean openUntilLate) {
        super(eventName, startTime, endTime, day);
        this.vendorName = vendorName;
        this.foodType = foodType;
        this.openUntilLate = openUntilLate;
    }

    public String getVendorName() {
        return vendorName;
    }

    public List<String> getFoodType() {
        return foodType;
    }

    public boolean isOpenUntilLate() {
        return openUntilLate;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append("\n Vendor: ").append(vendorName);
        sb.append("\n Food options: ").append(String.join(", ", foodType));
        sb.append("\n Open until late: ").append(openUntilLate ? "Yes" : "No");
        return sb.toString();
    }

}


