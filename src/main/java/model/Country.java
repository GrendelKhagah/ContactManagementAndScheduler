package model;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Country Class with its constructors, getters and setters
 *  
 * @author Taylor Ketterlings
 */
public class Country extends Trackable{
    private int countryID;
    private String countryName;
    
    
    //Constructors
    /**
     * Complete Constructor for Country
     * 
     * @param countryID         Country's ID
     * @param countryName       Country's Name
     * @param createDate       Date Country object Created
     * @param createdBy         Who Created Country Object
     * @param lastUpdate       When last Updated
     * @param updatedBy         Who last Updated
     */
    public Country(
            int countryID,
            String countryName,
            ZonedDateTime createDate,
            String createdBy,
            ZonedDateTime lastUpdate,
            String updatedBy){
        super(createDate, createdBy, lastUpdate, updatedBy); //Call to superclass constructor
        this.countryID = countryID;
        this.countryName = countryName;
        
    }
    
    /**
     * Constructor for Country, Assumes Creation Date and Last Edit are same(freshly made)
     * 
     * @param countryID         Country's ID
     * @param countryName       Country's Name
     * @param createDate        Date Country object Created
     * @param createdBy         Who Created Country Object
     */
    public Country(
            int countryID,
            String countryName,
            ZonedDateTime createDate,
            String createdBy){
        super(createDate, createdBy); //Call to superclass constructor
        this.countryID = countryID;
        this.countryName = countryName;
        
    }
    
    
    //Getters and Setters
    
    /**
     *  get country ID
     * 
     * @return country's ID
     */
    public int getCountryID(){return countryID;}
    
    /**
     * Sets country ID
     * 
     * @param countryID int of country ID to set
     */
    public void setCountryID(int countryID){
        this.countryID = countryID;
    }
    
    /**
     * gets country's Name
     * 
     * @return String of Country Name
     */
    public String getCountryName(){return countryName;}
    
    /**
     * Sets country's name
     * 
     * @param Name String of country Name
     */
    public void setCountryName(String Name){
        this.countryName = Name;
    }
    
    /**
     * Overridden toString(), outputs country's contents
     * 
     * @return 
     */
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss VV");
        return "Country{" +
                "countryID= " + countryID +
                ", countryName= " + countryName + '\'' +
                ", Create_Date= " + (createDate != null ? createDate.format(formatter) : "null") +
                ", createdBy= " + createdBy + '\'' +
                ", Last_Update= " + (lastUpdate != null ? lastUpdate.format(formatter) : "null") +
                ", updatedBy= " + updatedBy + '\'' + '}';
    }
}
