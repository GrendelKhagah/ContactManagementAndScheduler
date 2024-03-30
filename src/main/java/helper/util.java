package helper;

import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * utility class for program utilities
 * includes mapping month names to month's number
 * checking within business hours
 * 
 * @author Taylor Ketterling
 */
public class util {
    // monthsNames map for the appointmentSummaries
    private static final Map<String, String> monthNames = new HashMap<>();
    static {
        monthNames.put("1", "January");
        monthNames.put("2", "February");
        monthNames.put("3", "March");
        monthNames.put("4", "April");
        monthNames.put("5", "May");
        monthNames.put("6", "June");
        monthNames.put("7", "July");
        monthNames.put("8", "August");
        monthNames.put("9", "September");
        monthNames.put("10", "October");
        monthNames.put("11", "November");
        monthNames.put("12", "December");
    }
    
    /**
     * Checks to see if the appointment is within the defined business hours.
     * Returns true if within business hours
     * 
     * Zone of business is set to America/New_York for EST
     * 
     * @param startDate appointment start time
     * @param endDate   appointment end time
     * @return         true if within business hours, else false
     */
    public static boolean isWithinBusinessHours(ZonedDateTime startDate, ZonedDateTime endDate) {
        ZoneId businessZoneId = ZoneId.of("America/New_York"); // EST zone

        ZonedDateTime businessStartTime = startDate.withZoneSameInstant(businessZoneId).with(LocalTime.of(8, 0));
        ZonedDateTime businessEndTime = startDate.withZoneSameInstant(businessZoneId).with(LocalTime.of(22, 0));
        System.out.println(businessStartTime);
        System.out.println(businessEndTime);
        
        return !startDate.isBefore(businessStartTime) && !endDate.isAfter(businessEndTime);
    }
    
    /**
     * Returns month name given the month number
     * checks the map for month names by monthNumber, defaults to unknown
     * 
     * @param monthNumber  String # of month
     * @return             String month name
     */
    public static String getMonthName(String monthNumber) {
        return monthNames.getOrDefault(monthNumber, "Unknown");
    }
    
    
}
