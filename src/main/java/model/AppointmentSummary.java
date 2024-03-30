package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Used to handle appointment summaries for Reports
 * 
 * @author Taylor Ketterling
 */
public class AppointmentSummary {
    
    private StringProperty year;
    private StringProperty month;
    private StringProperty type;
    private IntegerProperty totalAppointments;

    /**
     * Creates a appointment summary report object
     * 
     * @param year     year of summary
     * @param month    month of summary
     * @param type    type of meeting   (grouped by type)
     * @param totalAppointments    total appointments by type in that month of that year
     */
    public AppointmentSummary(String year, String month, String type, Integer totalAppointments) {
        this.year = new SimpleStringProperty(year);
        this.month = new SimpleStringProperty(month);
        this.type = new SimpleStringProperty(type);
        this.totalAppointments = new SimpleIntegerProperty(totalAppointments);
    }

    // Getters
    public String getYear() { return year.get(); }
    public String getMonth() { return month.get(); }
    public String getType() { return type.get(); }
    public Integer getTotalAppointments() { return totalAppointments.get(); }

    // Property getters
    public StringProperty yearProperty() { return year; }
    public StringProperty monthProperty() { return month; }
    public StringProperty typeProperty() { return type; }
    public IntegerProperty totalAppointmentsProperty() { return totalAppointments; }
    
}
