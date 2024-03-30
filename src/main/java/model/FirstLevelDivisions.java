package model;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * FirstLevelDivisions Class, has its constructor and getters
 * While setters exists; All FLD data is input directly to DB
 * 
 * @author Taylor Ketterling
 */
public class FirstLevelDivisions extends Trackable {
    
    private int divisionID;
    private String division;
    private int countryID;
    
    //Constructors
    /**
     * Complete Constructor for a First Level Division with modification tracking
     * 
     * @param divisionID        ID of division
     * @param division          Name of division
     * @param createDate        Date Country object Created
     * @param createdBy         Who Created Country Object
     * @param lastUpdate        When last Updated
     * @param updatedBy         Who last Updated
     * @param countryID         country ID ( Foreign Key)
     */
    public FirstLevelDivisions (
            int divisionID,
            String division,
            ZonedDateTime createDate,
            String createdBy,
            ZonedDateTime lastUpdate,
            String updatedBy,
            int countryID){
        super(createDate, createdBy, lastUpdate, updatedBy); //Call to superclass constructor
        this.divisionID = divisionID;
        this.division = division;
        this.countryID = countryID;
    }
    
    /**
     * Complete Constructor for a First Level Division with modification tracking
     * Assumes Creation Date and Last Edit are same(freshly made)
     * 
     * @param divisionID        ID of division
     * @param division          Name of division
     * @param createDate        Date Country object Created
     * @param createdBy         Who Created Country Object
     * @param countryID         country ID ( Foreign Key)
     */
    public FirstLevelDivisions (
            int divisionID,
            String division,
            ZonedDateTime createDate,
            String createdBy,
            int countryID){
        super(createDate, createdBy); //Call to superclass short constructor
        this.divisionID = divisionID;
        this.division = division;
        this.countryID = countryID;
    }
   
    
    //Setters and Getters
    /**
     * Gets First level division ID
     * 
     * @return int ID
     */
    public int getFLDID(){return divisionID;}
    
    /**
     * Sets first level division ID
     * 
     * @param ID int
     */
    public void setFLDID(int ID){
        this.divisionID = ID;
    }
    
    /**
     * Gets First Level Division Name
     * 
     * @return String name of Division
     */
    public String getFLDName(){return division;}
    
    /**
     * Sets First level division name
     * 
     * @param name String of Division name
     */
    public void setFLDName(String name){
        this.division = name;
    }
    
    /**
     * Gets the First level division country ID
     * 
     * @return int ID of country
     */
    public int getFLDCountryID(){return countryID;}
    
    /**
     * Sets the First level division country ID
     * 
     * @param ID int of Country ID
     */
    public void setFLDCountryID(int ID){
        this.countryID = ID;
    }
    
    /**
     * overridden toString(), outputs all division contents.
     * 
     * @return 
     */
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss VV");
        return "FirstLevelDivisions{" +
                "divisionID= " + divisionID +
                ", division= " + division + '\'' +
                ", Create_Date= " + (createDate != null ? createDate.format(formatter) : "null") +
                ", createdBy= " + createdBy + '\'' +
                ", Last_Update= " + (lastUpdate != null ? lastUpdate.format(formatter) : "null") +
                ", updatedBy= " + updatedBy + '\'' +
                ", countryID= " + countryID +'}';
    }

    
}
