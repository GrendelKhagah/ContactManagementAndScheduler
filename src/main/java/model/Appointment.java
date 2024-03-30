package model;

import javafx.scene.control.Alert;
import java.time.ZonedDateTime;
import java.time.ZoneId;
import java.time.LocalTime;
/**
 * Appointments Java Class for package model, used in appointment app.
 *  
 * @author Taylor
 */
public class Appointment extends Trackable{
    
    private int appointmentID;      // Appointment_ID INT(10)
    private String title;           // Title VARCHAR(50)
    private String description;     // Description VARCHAR(50)
    private String location;        // Location VARCHAR(50)
    private String type;            // Type VARCHAR(50)
    private ZonedDateTime start;    // Start DATETIME
    private ZonedDateTime end;      // End DATETIME
    
    private int customerID;         // Customer_ID INT(10)
    private int userID;             // User_ID INT(10)
    private int contactID;          // Contact_ID INT(10)
    
    private static final ZoneId BUSINESS_ZONE_ID = ZoneId.of("America/New_York"); // Sets business TimeZone as EST
    private static final LocalTime BUSINESS_START = LocalTime.of(8, 0); // business open 
    private static final LocalTime BUSINESS_END = LocalTime.of(22, 0);  // and closing hours

    // Constructors
    
    /**
     * Complete Constructor for an Appointment, Takes all Parameters
     * 
     * @param appointmentID
     * @param title
     * @param description
     * @param location
     * @param type
     * @param start
     * @param end
     * @param createDate        Date Country object Created
     * @param createdBy         Who Created Country Object
     * @param lastUpdate        When last Updated
     * @param updatedBy         Who last Updated
     * @param customerID
     * @param userID
     * @param contactID 
     */
    public Appointment(
            int appointmentID,
            String title,
            String description,
            String location,
            String type,
            ZonedDateTime start,
            ZonedDateTime end,
            ZonedDateTime createDate,
            String createdBy,
            ZonedDateTime lastUpdate,
            String updatedBy,
            int customerID,
            int userID,
            int contactID) {
        super(createDate, createdBy, lastUpdate, updatedBy); //Call to superclass constructor
        this.appointmentID = appointmentID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;
    }
    
    /**
     * Complete Constructor for an Appointment, Takes all Parameters
     * Assumes Creation Date and Last Edit are same(freshly made)
     * 
     * @param appointmentID
     * @param title
     * @param description
     * @param location
     * @param type
     * @param start
     * @param end
     * @param createDate        Date Country object Created
     * @param createdBy         Who Created Country Object
     * @param customerID
     * @param userID
     * @param contactID 
     */
    public Appointment(
            int appointmentID,
            String title,
            String description,
            String location,
            String type,
            ZonedDateTime start,
            ZonedDateTime end,
            ZonedDateTime createDate,
            String createdBy,
            int customerID,
            int userID,
            int contactID) {
        super(createDate, createdBy); //Call to superclass constructor
        this.appointmentID = appointmentID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;
    }

    // Getters and Setters
    
    /**
     * gets appointmentID
     * 
     * @return appointmentID
     */
    public int getAppointmentID() {return appointmentID;}
    
    /**
     * sets appointment ID
     * 
     * @param appointmentID 
     */
    public void setAppointmentID(int appointmentID) {this.appointmentID = appointmentID;}
    
    /**
     * returns appointment's Title
     * 
     * @return appointment title
     */
    public String getAppointmentTitle() {return title;}
    
    /**
     * sets the appointment title
     * 
     * @param newTitle  takes in String newTitle, to set title
     */
    public void setAppointmentTitle(String newTitle){
        this.title = newTitle;
    }
    
    /**
     * gets the appointment description
     * 
     * @return returns description as a string
     */
    public String getAppointmentDescription(){return description;}
    
    /**
     * sets appointment description to input String
     * 
     * @param newDescription  String to update Description
     */
    public void setAppointmentDescription(String newDescription){
        this.description = newDescription;
    }
    
    /**
     * gets the appointment locations
     * @return String Location of appointment
     */
    public String getAppointmentLocation(){return location;}
    
    
    /**
     * sets appointment location
     * @param newLocation String to be set as new location
     */
    public void setAppointmentLocation(String newLocation){
        this.location = newLocation;
    }

    /**
     * gets the appointment type
     * 
     * @return String of type
     */
    public String getAppointmentType(){return type;}
    
    /**
     * Sets the appointment Type
     * 
     * @param newType 
     */
    public void setAppointmentType(String newType){
        this.type = newType;
    }
    
    /**
     * Appointment Start Time
     * @return ZonedDateTime of start time
     */
    public ZonedDateTime getAppointmentStart(){return start;}
    
    /**
     * Sets Appointment Start Time
     * 
     * @param newStart ZonedDateTime
     */
    public void setAppointmentStart(ZonedDateTime newStart){
        this.start = newStart;
    }
    
    /**
     * Appointment End Time
     * @return ZonedDateTime of end time
     */
    public ZonedDateTime getAppointmentEnd(){return end;}
    
    /**
     * Sets Appointment End Time
     * 
     * @param newEnd ZonedDateTime
     */
    public void setAppointmentEnd(ZonedDateTime newEnd){
        this.end = newEnd;
    }
    
    /**
     * returns customerID , Foreign Key
     * 
     * @return int of customerID
     */
    public int getAppointmentCustomerID(){return customerID;}
    
    /**
     * Sets customerID , Foreign Key
     * 
     * @param newCustomerID ID of customer
     */
    public void setAppointmentCustomerID(int newCustomerID){
        this.customerID = newCustomerID;
    }
    
    /**
     * returns userID , Foreign Key
     * 
     * @return int of userID
     */
    public int getAppointmentUserID(){return userID;}
    
    /**
     * Sets userID , Foreign Key
     * 
     * @param newUserID ID of User
     */
    public void setAppointmentUserID(int newUserID){
        this.userID = newUserID;
    }
    
    /**
     * returns contactID , Foreign Key
     * 
     * @return int of contact's ID
     */
    public int getAppointmentContactID(){return contactID;}
    
    /**
     * Sets contactID , Foreign Key
     * 
     * @param newContactID ID of contact
     */
    public void setAppointmentContactID(int newContactID){
        this.contactID = newContactID;
    }
    
    /**
     * Enables Alerts for other methods
     * 
     * @param title    alert Title
     * @param content  alert Content
     */
    private static void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

}
