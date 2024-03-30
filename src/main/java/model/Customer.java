package model;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Customer Class with its constructor, getter and setters.
 * 
 * @author Taylor Ketterling
 */
public class Customer extends Trackable{
    
    private int Customer_ID;
    private String Customer_Name;
    private String Address;
    private String Postal_Code;
    private String Region;
    private String Country;
    private String Phone;
    private int Division_ID;
    
    // Constructors

    /**
     * Complete Constructor for Creating a Customer Object:
     * 
     * @param Customer_ID   Unique Identifier for the Customer.
     * @param Customer_Name Name of the Customer.
     * @param Address       Customer's Address.
     * @param Region        Region of Customer.
     * @param Country       Customer's Country.
     * @param Postal_Code   Customer's Postal Code.
     * @param Phone         Customer's Phone Number
     * @param createDate   Date and Time the customer was Created.
     * @param createdBy    Who Created the Customer
     * @param lastUpdate  Date and Time the customer was last Updated.
     * @param updatedBy   Who Updated the Customer
     * @param Division_ID   Division the customer Belongs to.
     */
    public Customer(int Customer_ID,
            String Customer_Name,
            String Address,
            String Postal_Code,
            String Region,
            String Country,
            String Phone,
            ZonedDateTime createDate,
            String createdBy,
            ZonedDateTime lastUpdate,
            String updatedBy,
            int Division_ID
            ){
        super(createDate, createdBy, lastUpdate, updatedBy); //Call to superclass constructor
        this.Customer_ID = Customer_ID;
        this.Customer_Name = Customer_Name;
        this.Address = Address;
        this.Postal_Code = Postal_Code;
        this.Region = Region;
        this.Country = Country;
        this.Phone = Phone;
        this.Division_ID = Division_ID;
    }
    
    /**
     * Constructor for Creating a Customer Object:
     * Assumes Creation Date and Last Edit are same(freshly made)
     * 
     * @param Customer_ID   Unique Identifier for the Customer.
     * @param Customer_Name Name of the Customer.
     * @param Address       Customer's Address.
     * @param Region        Region of Customer.
     * @param Country       Customer's Country.
     * @param Postal_Code   Customer's Postal Code.
     * @param Phone         Customer's Phone Number
     * @param createDate   Date and Time the customer was Created.
     * @param createdBy    Who Created the Customer
     * @param Division_ID   Division the customer Belongs to.
     */
    public Customer(int Customer_ID,
            String Customer_Name,
            String Address,
            String Postal_Code,
            String Region,
            String Country,
            String Phone,
            ZonedDateTime createDate,
            String createdBy,
            int Division_ID
            ){
        super(createDate, createdBy); //Call to superclass short constructor
        this.Customer_ID = Customer_ID;
        this.Customer_Name = Customer_Name;
        this.Address = Address;
        this.Postal_Code = Postal_Code;
        this.Region = Region;
        this.Country = Country;
        this.Phone = Phone;
        this.Division_ID = Division_ID;
    }
    
    // Setters and Getters
    /**
     * Gets the Customer's ID
     * 
     * @return the Customer ID
     */
    public int getCustomerID(){return Customer_ID;}
    
    /**
     * Sets the Customer's ID
     * 
     * @param ID The new Customer ID
     */
    public void setCustomerID(int ID) {
        this.Customer_ID = ID;
    }
    
    /**
     * Gets the Customer's Name
     * 
     * @return The Customer Name
     */
    public String getCustomerName(){return Customer_Name;}
    
    /**
     * Sets the Customer's Name
     * 
     * @param Name The new Customer Name
     */
    public void setCustomerName(String Name){
        this.Customer_Name = Name;
    }
    
    /**
     * Gets the Customer's Address
     * 
     * @return the Customer Address
     */
    public String getCustomerAddress(){return Address;}
    
    /**
     * Sets the Customer's Address
     * 
     * @param Address The new Customer Address
     */
    public void setCustomerAddress(String Address){
        this.Address = Address;
    }
    
    /**
     * Gets the Customer's Postal Code
     * 
     * @return Postal Code of Customer
     */
    public String getCustomerPostalCode(){return Postal_Code;}
    
    /**
     * Sets the Customer's Postal Code
     * 
     * @param Postal_Code The new Postal Code of Customer
     */
    public void setCustomerPostalCode(String Postal_Code){
        this.Postal_Code = Postal_Code;
    }
    
    /**
     * Gets the Customer's Region
     * 
     * @return Region of Customer
     */
    public String getCustomerRegion(){return Region;}
    
    /**
     * Sets the Customer's Region
     * 
     * @param Region The new Region of Customer
     */
    public void setCustomerRegion(String Region){
        this.Region = Region;
    }
    
    /**
     * Gets the Customer's Country
     * 
     * @return Country of Customer
     */
    public String getCustomerCountry(){return Country;}
    
    /**
     * Sets the Customer's Country
     * 
     * @param Country The new Country of Customer
     */
    public void setCustomerCountry(String Country){
        this.Country = Country;
    }
    
    /**
     * Gets the Customer's Phone # (String)
     * 
     * @return Phone # of Customer
     */
    public String getCustomerPhone(){return Phone;}
    
    /**
     * Sets the Customer's Phone # (String)
     * 
     * @param Phone The new Phone # of Customer(String)
     */
    public void setCustomerPhone(String Phone){             // method overloading
        this.Phone = Phone;
        //improvement : Needs Data Validation
    }
    
    /**
     * Sets the Customer's Phone # (integer)
     * Converts integer to String
     * 
     * @param Phone the new phone # of Customer(int)
     */
    public void setCustomerPhone(int Phone){                // method overloading 
        this.Phone = Integer.toString(Phone);
        //improvement : Needs data validation
    }
  
    /**
     * Returns Customer's Division ID
     * 
     * @return int division ID
     */
    public int getCustomerDivisionID(){return Division_ID;}
    
    /**
     * Sets customer's Division ID
     * 
     * @param Division_ID int ID of division customer belongs to
     */
    public void setCustomerDivisionID(int Division_ID){
        this.Division_ID = Division_ID;
    }
    
    /**
     * Overridden toString(), outputs all Customer Contents.
     * 
     * @return 
     */
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z");
            return "Customer{" +
                    "customerID= " + Customer_ID +
                    ", customerName= " + Customer_Name + '\'' +
                    ", address= " + Address + '\'' +
                    ", postalCode= " + Postal_Code + '\'' +
                    ", phone= " + Phone + '\'' +
                    ", createDate= " + (createDate != null ? createDate.format(formatter) : "null") +
                    ", createdBy= " + createdBy + '\'' +
                    ", lastUpdated= " + (lastUpdate != null ? lastUpdate.format(formatter) : "null") +
                    ", lastUpdatedBy= " + updatedBy + '\'' +
                    ", divisionID= " + Division_ID + '}';
    }
    
}
