package model;

/**
 * Contact Class with its constructor and getters/setters
 * 
 * @author Taylor Ketterling
 */
public class Contact {
    
    private int contactID;
    private String contactName;
    private String email;
    
    
    //contructors
    /**
     * Constructor for Contact, takes id, name and email
     * 
     * @param contactID   Id of Contact
     * @param contactName name of Contact
     * @param email       email of contact
     */
    public Contact (
            int contactID,
            String contactName,
            String email){
        this.contactID = contactID;
        this.contactName = contactName;
        this.email = email;
        
    }
    
    //Getters and setters
    
    /**
     * Get Contact's ID
     * 
     * @return int, Contact ID
     */
    public int getContactID(){return contactID;}
    
    /**
     * Sets Contact ID
     * 
     * @param ID type int, ID of Contact
     */
    public void setContactID(int ID){
        this.contactID = ID;
    }
    
    /**
     * Gets Contact Name
     * 
     * @return Name of Contact
     */
    public String getContactName(){return contactName;}
    
    /**
     * Sets contact name
     * 
     * @param name String of Contact Name
     */
    public void setContactName(String name){
        this.contactName = name;
    }    
    
    /**
     * Gets contact email
     * 
     * @return email as a String
     */
    public String getContactEmail(){return email;}
    
    /**
     * sets contact email
     * 
     * @param email the new Contact email
     */
    public void setContactEmail(String email){
        this.email = email;
    }
    
    /**
     * Overridden toString(), outputs contact contents
     * 
     * @return 
     */
    @Override
    public String toString() {
        return "Contact{" +
                "contactID= " + contactID +
                ", contactName= " + contactName + '\'' +
                ", email= " + email + '\'' + '}';
    }
}
