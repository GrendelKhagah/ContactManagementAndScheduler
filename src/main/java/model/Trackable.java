package model;

import java.time.ZonedDateTime;
/**
 * Abstract class Trackable
 * 
 * @author Taylor
 */
public abstract class Trackable {
    
    protected ZonedDateTime createDate;
    protected String createdBy;
    protected ZonedDateTime lastUpdate;
    protected String updatedBy;
    
    // Constructors 
    /**
     * Constructor for a Trackable object
     * Assume trackable object is freshly created and last to update is creator
     * 
     * @param createDate Date Created
     * @param createdBy  Who Created it
     */
    public Trackable(
            ZonedDateTime createDate,
            String createdBy){
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = createDate;
        this.updatedBy = createdBy;
    }
    
    /**
     * Complete Constructor for a Trackable object
     * 
     * @param createDate Date Created
     * @param createdBy  Who Created it
     * @param lastUpdate When was it last updated
     * @param updatedBy  Who last updated it
     */
    public Trackable(
            ZonedDateTime createDate,
            String createdBy,
            ZonedDateTime lastUpdate,
            String updatedBy){
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.updatedBy = updatedBy;
    }
    
    //Setters and Getters
    
    /**
     * Gets Trackable objects Creation Date
     * 
     * @return Date Item Created
     */
    public ZonedDateTime getCreateDate() {return createDate;}

    /**
     * Sets Create Date of Trackable object
     * 
     * @param createDate ZonedDateTime of createDate
     */
    public void setCreateDate(ZonedDateTime createDate) {
        this.createDate = createDate;
    }

    /**
     * Gets Who Created the Item
     * 
     * @return String of item creator
     */
    public String getCreatedBy() {return createdBy;}

    /**
     * Sets who created the item
     * 
     * @param createdBy String Creator's Name
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * gets when last update occurred
     * 
     * @return ZonedDateTime of last update time
     */
    public ZonedDateTime getLastUpdate() {return lastUpdate;}

    /**
     * sets when last update performed
     * 
     * @param last_Update ZonedDateTime of last update 
     */
    public void setLastUpdate(ZonedDateTime last_Update) {
        this.lastUpdate = last_Update;
    }

    /**
     * Gets who performed last update
     * 
     * @return String name of person to perform last update
     */
    public String getUpdatedBy() {return updatedBy;}

    /**
     * Sets who performed last update
     * 
     * @param updatedBy String of name to be set as last person to perform update
     */
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }
    
}
