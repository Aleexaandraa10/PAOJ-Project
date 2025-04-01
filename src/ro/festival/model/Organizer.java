package ro.festival.model;

import java.util.List;

// un record este un tip de clasa folosit pt reprezentarea DATELOR IMUTABILE
// fara sa scrii manual constructori, getteri, equals(), hashCode() sau toString()
// limitari ale recordurilor: --> nu poti avea settere
//                            --> nu poti extinde o alta clasa
//                            --> campurile sunt automat final
public record Organizer(String companyName, String organizerName, List<Event> events) {

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Organizer company: ").append(companyName).append("\n");
        sb.append("Representitive: ").append(organizerName).append("\n");
        sb.append("Events organized in this festival:\n");

        if (events.isEmpty()) {
            sb.append("No events assigned yet.");
        } else {
            for (Event e : events) {
                sb.append("\n   - ").append(e.getEventName()).append(" (").append(e.getDay()).append(")");
            }
        }
        return sb.toString();
    }

}


// EXPLICATIE EQUALS
//  String a = new String("Ioana");
//  String b = new String("Ioana");
//
//   System.out.println(a == b);       // false – referințe diferite
//   System.out.println(a.equals(b));  // true  – conținut egal