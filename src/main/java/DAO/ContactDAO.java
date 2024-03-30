package DAO;

import helper.JDBC;
import java.sql.Connection;
import model.Contact;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *  Contact DAO, responsible for adding contacts, getting all contacts, getting a contact
 *  deleting contacts and updating contacts.
 *  
 * @author Taylor Ketterling
 */
public class ContactDAO {
    
    /**
     * add a contact to the database
     * 
     * @param contact contact to add to DB
     * @throws SQLException 
     */
    public static void addContact(Contact contact) throws SQLException {
        String query = "INSERT INTO Contacts (Contact_Name, Email) "
                + "VALUES (?, ?)";

        try (Connection connection = JDBC.getConnection();
            PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, contact.getContactName());
            ps.setString(2, contact.getContactEmail());
            ps.executeUpdate();
        }
    }
    
    /**
     * Gets all contacts from database
     * 
     * @return ObservableList of all contacts
     */
    public static ObservableList<Contact> getAllContacts(){
        ObservableList<Contact> contactList = FXCollections.observableArrayList();
        String query = "SELECT * FROM Contacts";
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Contact contact = new Contact(
                    rs.getInt("Contact_ID"),
                    rs.getString("Contact_Name"),
                    rs.getString("Email"));
                contactList.add(contact);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contactList;
    }
    
    /**
     * Gets all contacts from database
     * 
     * @return ObservableList of all contacts
     */
    public static ObservableList<String> getAllContactsNamelist(){
        ObservableList<String> contactList = FXCollections.observableArrayList();
        String query = "SELECT * FROM Contacts";
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                contactList.add(rs.getString("Contact_Name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Collections.sort(contactList);
        return contactList;
    }
    
    /**
     * Gets a single contact by ID
     * 
     * @param contactID Id of contact to fetch
     * @return  Contact by ID
     */
    public static Contact getContact(int contactID){
        String query = "SELECT * FROM Contacts WHERE Contact_ID = ?";
        try (PreparedStatement ps = JDBC.getConnection().prepareStatement(query)) {
            ps.setString(1, Integer.toString(contactID));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Contact contact = new Contact(
            rs.getInt("Contact_ID"),
           rs.getString("Contact_Name"),
               rs.getString("Email"));
                return contact;
            } else {
                return null;
            }
            
            
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * Gets multiple contacts by string(name)
     * 
     * @param name Name of contact
     * @return ObservableList of Contacts matching name
     */
    public static ObservableList<Contact> getContacts(String name){
        ObservableList<Contact> contactList = FXCollections.observableArrayList();
        String query = "SELECT * FROM Contacts WHERE Contact_Name = ?";
        try (PreparedStatement ps = JDBC.getConnection().prepareStatement(query)) {
            ps.setString(1, "%" + name + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Contact contact = new Contact(
            rs.getInt("Contact_ID"),
           rs.getString("Contact_Name"),
               rs.getString("Email"));
                contactList.add(contact);
            } 
        } catch (SQLException e){
            e.printStackTrace();
        }
        return contactList;
    }
    
    public static int getcontactID(String name){
        ObservableList<Contact> contactList = FXCollections.observableArrayList();
        String query = "SELECT * FROM Contacts WHERE Contact_Name = ?";
        try (PreparedStatement ps = JDBC.getConnection().prepareStatement(query)) {
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getInt("Contact_ID");
            } 
        } catch (SQLException e){
            e.printStackTrace();
        }
        return -1; // query failed, return junk data
        
    }
    
}
