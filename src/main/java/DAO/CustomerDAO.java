package DAO;

import helper.JDBC;
import model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;

/**
 * Customer DAO, Adds, deletes, and updates customer along with pulls customer data
 * 
 * @author Taylor Ketterling
 */
public class CustomerDAO {
    /**
     * Adds a Customer to the Database
     * 
     * @param customer      Customer Object
     * @throws SQLException 
     */
    public static void addCustomer(Customer customer) throws SQLException {
        String query = "INSERT INTO Customers (Customer_Name, Address, Postal_Code, Phone, Create_Date, "
                + "Created_By, Last_Updated, Last_Updated_By, Division_ID, Country, Region) " +
                "VALUES (?, ?, ?, ?, NOW(), ?, NOW(), ?, ?, ?, ?)";

        try (Connection connection = JDBC.getConnection();
            PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, customer.getCustomerName());
            ps.setString(2, customer.getCustomerAddress());
            ps.setString(3, customer.getCustomerPostalCode());
            ps.setString(4, customer.getCustomerPhone());
            ps.setString(5, customer.getCreatedBy());
            ps.setString(6, customer.getUpdatedBy());
            ps.setInt(7, customer.getCustomerDivisionID());
            ps.setString(8, customer.getCustomerCountry());
            ps.setString(9, customer.getCustomerRegion());
            ps.executeUpdate();
        }
    }
    
    /**
     * Searches for a customer by ID
     * 
     * @param customerId   Id to search
     * @return            list of customers 
     * @throws SQLException 
     */
    public static ObservableList<Customer> searchCustomer(int customerId) throws SQLException {  // Overloaded Method
        ObservableList<Customer> customerList = FXCollections.observableArrayList();
        String query = "SELECT * FROM Customers WHERE Customer_ID = ?";
        try (Connection connection = JDBC.getConnection();
            PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, customerId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                customerList.add(new Customer(
                    rs.getInt("Customer_ID"),
                    rs.getString("Customer_Name"),
                    rs.getString("Address"),
                    rs.getString("Postal_Code"),
                    rs.getString("Region"),
                    rs.getString("Country"),
                    rs.getString("Phone"),
                    rs.getTimestamp("Create_Date").toInstant().atZone(ZoneId.systemDefault()),
                    rs.getString("Created_By"),
                    rs.getTimestamp("Last_Updated").toInstant().atZone(ZoneId.systemDefault()),
                    rs.getString("Last_Updated_By"),
                    rs.getInt("Division_ID")));
            }
        }
        return customerList;
    }
    
    /**
     * Searches for customers by name
     * 
     * @param name    name of customer to search
     * @return        list of customers
     * @throws SQLException 
     */
    public static ObservableList<Customer> searchCustomer(String name) throws SQLException {   // Overloaded Method
        ObservableList<Customer> customerList = FXCollections.observableArrayList();
        String query = "SELECT * FROM Customers WHERE Customer_Name LIKE ?";
        try (Connection connection = JDBC.getConnection();
            PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, "%" + name + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                customerList.add(new Customer(
                    rs.getInt("Customer_ID"),
                    rs.getString("Customer_Name"),
                    rs.getString("Address"),
                    rs.getString("Postal_Code"),
                    rs.getString("Region"),
                    rs.getString("Country"),
                    rs.getString("Phone"),
                    rs.getTimestamp("Create_Date").toInstant().atZone(ZoneId.systemDefault()),
                    rs.getString("Created_By"),
                    rs.getTimestamp("Last_Updated").toInstant().atZone(ZoneId.systemDefault()),
                    rs.getString("Last_Updated_By"),
                    rs.getInt("Division_ID")));
            }
        }
        return customerList;
    }


    /**
     * Deletes a Customer From the databases, checks if they have any appointments first
     * 
     * @param customerId    ID of customer to be deleted
     * @return      true if deleted, false if not
     * @throws SQLException 
     */
    public static boolean deleteCustomer(int customerId) throws SQLException {
        // Check if customer has appointments
        String query = "SELECT COUNT(*) AS appointmentCount FROM Appointments WHERE Customer_ID = ?";
        try (Connection connection = JDBC.getConnection();
            PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, customerId);
            ResultSet rs = ps.executeQuery();
            if (rs.next() && rs.getInt("appointmentCount") > 0) {
                return false; // Customer has appointments, cannot delete return false
            }
        }

        // No appointments, proceed with the deletion
        String deleteQuery = "DELETE FROM Customers WHERE Customer_ID = ?";
        try (Connection connection = JDBC.getConnection();
            PreparedStatement ps = connection.prepareStatement(deleteQuery)) {
            ps.setInt(1, customerId);
            ps.executeUpdate();
            return true; // Customer deleted successfully return true
        }
    }
    
    /**
     * Takes a customer object and updates the DB with that customer
     * updated customer must have same ID to update old customer
     * 
     * @param customer  new customer to be updated
     * @return          true if successfully updated
     * @throws SQLException 
     */
    public static boolean updateCustomer(Customer customer) throws SQLException {
        String updateQuery = "UPDATE Customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Last_Updated = NOW(), Last_Updated_By = ?, Division_ID = ?, Country = ?, Region = ? WHERE Customer_ID = ?";

        try (Connection connection = JDBC.getConnection();
            PreparedStatement ps = connection.prepareStatement(updateQuery)) {
            ps.setString(1, customer.getCustomerName());
            ps.setString(2, customer.getCustomerAddress());
            ps.setString(3, customer.getCustomerPostalCode());
            ps.setString(4, customer.getCustomerPhone());
            ps.setString(5, customer.getUpdatedBy());
            ps.setInt(6, customer.getCustomerDivisionID());
            ps.setString(7, customer.getCustomerCountry());
            ps.setString(8, customer.getCustomerRegion());
            ps.setInt(9, customer.getCustomerID());
            
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;              // Returns true(1) if the update was successful
        }
        
    }
    
    /**
     * Returns all Customers in DB
     * 
     * @return          All customers
     * @throws SQLException 
     */
    public static ObservableList<Customer> getAllCustomers() throws SQLException {
        ObservableList<Customer> customerList = FXCollections.observableArrayList();
        String query = "SELECT * FROM Customers";
        try (Connection connection = JDBC.getConnection();
            PreparedStatement ps = connection.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                customerList.add(new Customer(
                    rs.getInt("Customer_ID"),
                    rs.getString("Customer_Name"),
                    rs.getString("Address"),
                    rs.getString("Postal_Code"),
                    rs.getString("Region"),
                    rs.getString("Country"),
                    rs.getString("Phone"),
                    rs.getTimestamp("Create_Date").toInstant().atZone(ZoneId.systemDefault()),
                    rs.getString("Created_By"),
                    rs.getTimestamp("Last_Updated").toInstant().atZone(ZoneId.systemDefault()),
                    rs.getString("Last_Updated_By"),
                    rs.getInt("Division_ID")));
            }
        }
        return customerList;
    }
    
    /**
     * gets total amount of customers in database
     * 
     * @return int total ammount of customers
     * @throws SQLException 
     */
    public static int getTotalCustomers() throws SQLException {
        String query = "SELECT COUNT(*) AS total FROM Customers";
        try (Connection connection = JDBC.getConnection();
             PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("total");
            }
        }
        return 0;
    }
}
