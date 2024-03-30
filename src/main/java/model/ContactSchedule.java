package model;

import java.time.ZonedDateTime;
/**
 * ContactSchedule class for reporting on contacts schedule's
 * 
 * @author Taylor Ketterling
 */
public class ContactSchedule {
    private int appointmentID;
    private String title;
    private String type;
    private String description;
    private ZonedDateTime startDateTime;
    private ZonedDateTime endDateTime;
    private int customerID;
    
    //Constructors
    
    public ContactSchedule(
            int appointmentID,
            String title,
            String type,
            String description,
            ZonedDateTime startDateTime,
            ZonedDateTime endDateTime,
            int customerID) {
        this.appointmentID = appointmentID;
        this.title = title;
        this.type = type;
        this.description = description;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.customerID = customerID;
    }

    // Getters and setters
    /**
     * Sets ContactSchedule's Appointment it
     * 
     * @param id int ID of appointment
     */
    public void setAppointmentID(int id){
        this.appointmentID = id;
    }
    
    /**
     * Gets the appointment ID of contact schedule
     * 
     * @return int ID
     */
    public int getAppointmentID(){return this.appointmentID;}
    
    /**
     * Sets the appointment Title of contact Schedule
     * 
     * @param title 
     */
    public void setTitle(String title){
        this.title = title;
    }
    
    /**
     * gets the appointment Title of contact Schedule
     * 
     * @return String Title
     */
    public String getTitle(){return this.title;}
    
    /**
     * Sets the type of the appointment
     * 
     * @param type String appointment type
     */
    public void setType(String type){
        this.type = type;
    }
    
    /**
     * Gets the appointment type
     * 
     * @return String, Appointment type
     */
    public String getType(){return this.type;}
    
    /**
     * Sets the description of the schedule's appointment
     * 
     * @param description String description of appointment
     */
    public void setDescription(String description){
        this.description = description;
    }
    
    /**
     * Gets the description
     * 
     * @return String description of appointment
     */
    public String getDescription(){return this.description;}
    
    /**
     * Sets the startdate of the schedule
     * 
     * @param startDateTime ZonedDateTime
     */
    public void setStartDate(ZonedDateTime startDateTime){
        this.startDateTime = startDateTime;
    }
    
    /**
     * gets the Startdate of the schedule
     * 
     * @return ZonedDateTime startdate
     */
    public ZonedDateTime getStartDate(){return startDateTime;}
    
    /**
     * Sets the end date of the schedule
     * 
     * @param endDateTime ZonedDateTime
     */
    public void setEndDate(ZonedDateTime endDateTime){
        this.endDateTime = endDateTime;
    }
    
    /**
     * Gets the end date of the schedule
     * 
     * @return ZonedDateTime endDateTime
     */
    public ZonedDateTime getEndDate(){return endDateTime;}
    
    /**
     * Sets the customer ID of schedule
     * 
     * @param id 
     */
    public void setCustomerID(int id){
        this.customerID = id;
    }
    
    /**
     * Gets the customer ID of the schedule
     * 
     * @return 
     */
    public int getCustomerID(){return customerID;}
}
