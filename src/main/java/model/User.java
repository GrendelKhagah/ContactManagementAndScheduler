package model;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * User class with its constructor, getters and setters.
 * 
 * @author Taylor Ketterling
 */
public class User extends Trackable{
    
    private int userID;
    private String userName;
    private String password;
    
    //Constructors
    /**
     * Constructor for a User
     * 
     * Calls superclass Trackable for creation and update logging
     * @param userID        ID of user
     * @param userName      Name of User
     * @param password      Password of User
     * @param createDate    Date User was Created
     * @param createdBy     Person who created User
     * @param lastUpdate    Date user was last updated
     * @param updatedBy     Person who last updated User
     */
    public User (int userID,
            String userName,
            String password,
            ZonedDateTime createDate,
            String createdBy,
            ZonedDateTime lastUpdate,
            String updatedBy){
        super(createDate, createdBy, lastUpdate, updatedBy); //Call to superclass constructor
        this.userID = userID;
        this.userName = userName;
        this.password = password;
        
    }
    
    /**
     * Constructor for a User only takes creation date and creator. 
     * assumes creator and last updated are equal
     * 
     * Calls superclass Trackable for creation and update logging
     * @param userID        ID of user
     * @param userName      Name of User
     * @param password      Password of User
     * @param createDate    Date User was Created
     * @param createdBy     Person who created User
     */
    public User (int userID,
            String userName,
            String password,
            ZonedDateTime createDate,
            String createdBy){
        super(createDate, createdBy); //Call to superclass short constructor
        this.userID = userID;
        this.userName = userName;
        this.password = password;
        
    }
    
    
    //Getters and Setters
    
    /**
     * Gets the User's ID
     * 
     * @return int user ID
     */
    public int getUserID(){return userID;}
    
    /**
     * Sets the User's ID
     * 
     * @param userID int ID of user
     */
    public void setUserID(int userID){
        this.userID = userID;
    }
    
    /**
     * Returns the user's Name
     * 
     * @return String Name of user
     */
    public String getUserName(){return userName;}
    
    /**
     * Sets user's Name
     * 
     * @param userName The Name of user
     */
    public void setUserName(String userName){
        this.userName = userName;
    }
    
    /**
     * Gets the user's Password
     * 
     * @return String of user password
     */
    public String getUserPassword(){return password;}
    
    /**
     * sets user password
     * 
     * @param password The new User Password
     */
    public void setUserPassword(String password){
        this.password = password;
    }
    
    /**
     * overridden toString(), Returns all User Contents
     * 
     * @return 
     */
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z");
        return "User{" +
                "userID= " + userID +
                ", userName= " + userName + '\'' +
                ", password= " + "********" + '\'' + 
                ", create_Date= " + (createDate != null ? createDate.format(formatter) : "null") +
                ", createdBy= " + createdBy + '\'' +
                ", last_Update= " + (lastUpdate != null ? lastUpdate.format(formatter) : "null") +
                ", updatedBy= " + updatedBy + '\'' + '}';
    }
    
    
}
