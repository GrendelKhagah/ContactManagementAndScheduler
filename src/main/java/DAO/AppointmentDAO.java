package DAO;

import helper.*;
import model.Appointment;
import model.AppointmentSummary;
import model.ContactSchedule;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import model.SessionManager;
 
/**
 * Appointment DAO, Responsible for adding, deleting, updating and fetching appointments
 * 
 * @author Taylor Ketterling
 */
public class AppointmentDAO {
    
    
    /**
     * Adds an appointment to the database
     * DB will generate Appointment ID 
     * 
     * @param appointment appointment to be added
     * @throws SQLException 
     */
    public static void addAppointment(Appointment appointment) throws SQLException{
        String query = "INSERT INTO Appointments (Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) " +
                "VALUES (?, ?, ?, ?, ?, ?, NOW(), ?, NOW(), ?, ?, ?, ?)";
        
        try (Connection connection = JDBC.getConnection();
            PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, appointment.getAppointmentTitle());     
            ps.setString(2, appointment.getAppointmentDescription());
            ps.setString(3, appointment.getAppointmentLocation());
            ps.setString(4, appointment.getAppointmentType());
            // Converting ZonedDateTime to Timestamp for Database Storage
            ps.setTimestamp(5, Timestamp.from(appointment.getAppointmentStart().toInstant()));
            
            // Converting ZonedDateTime to Timestamp for Database Storage
            ps.setTimestamp(6, Timestamp.from(appointment.getAppointmentEnd().toInstant()));
            ps.setString(7, appointment.getCreatedBy());
            ps.setString(8, appointment.getUpdatedBy());
            ps.setInt(9, appointment.getAppointmentCustomerID());
            ps.setInt(10, appointment.getAppointmentUserID());
            ps.setInt(11, appointment.getAppointmentContactID());
            ps.executeUpdate();
        }
    }
    
    /**
     * Searches through all appointment titles by given sting
     * 
     * @param name   Name of title to be searched for
     * @return       ObservableList of appointments matching query
     * @throws SQLException 
     */
    public static ObservableList<Appointment> searchAppointments(String name) throws SQLException{
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
        String query = "SELECT * FROM Appointments WHERE Title LIKE ?";
        try (Connection connection = JDBC.getConnection();
            PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, "%" + name + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                appointmentList.add(new Appointment(
                    rs.getInt("Appointment_ID"),
                    rs.getString("Title"),
                    rs.getString("Description"),
                    rs.getString("Location"),
                    rs.getString("Type"),
                    rs.getTimestamp("Start").toInstant().atZone(ZoneId.systemDefault()),
                    rs.getTimestamp("End").toInstant().atZone(ZoneId.systemDefault()),
                    rs.getTimestamp("Create_Date").toInstant().atZone(ZoneId.systemDefault()),
                    rs.getString("Created_By"),
                    rs.getTimestamp("Last_Update").toInstant().atZone(ZoneId.systemDefault()),
                    rs.getString("Last_Updated_By"),
                    rs.getInt("Customer_ID"),
                    rs.getInt("User_ID"),
                    rs.getInt("Contact_ID")));
            }
        }
        return appointmentList;
        
    }
    
    /**
     * Returns an ObservableList list of appoints a customer has
     * 
     * @param customerID   Id of customer to check appointments
     * @return              ObservableList of customer appointments
     * @throws SQLException 
     */
    public static ObservableList<Appointment> getAppointmentsForCustomer(int customerID)throws SQLException{
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
        String query = "SELECT * FROM Appointments WHERE Customer_ID = ?";
        try (Connection connection = JDBC.getConnection();
            PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, customerID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                appointmentList.add(new Appointment(
                    rs.getInt("Appointment_ID"),
                    rs.getString("Title"),
                    rs.getString("Description"),
                    rs.getString("Location"),
                    rs.getString("Type"),
                    rs.getTimestamp("Start").toInstant().atZone(ZoneId.systemDefault()),
                    rs.getTimestamp("End").toInstant().atZone(ZoneId.systemDefault()),
                    rs.getTimestamp("Create_Date").toInstant().atZone(ZoneId.systemDefault()),
                    rs.getString("Created_By"),
                    rs.getTimestamp("Last_Update").toInstant().atZone(ZoneId.systemDefault()),
                    rs.getString("Last_Updated_By"),
                    rs.getInt("Customer_ID"),
                    rs.getInt("User_ID"),
                    rs.getInt("Contact_ID")));
            }
        }
        return appointmentList;
    }
    
    /**
     * Deletes an appointment by ID
     * returns true if successful 
     * @param appointmentID ID of appointment to be deleted
     * @return      true if successful
     * @throws SQLException 
     */
    public static boolean deleteAppointment(int appointmentID) throws SQLException {
        String deleteQuery = "DELETE FROM Appointments WHERE Appointment_ID = ?";
        try (Connection connection = JDBC.getConnection();
            PreparedStatement ps = connection.prepareStatement(deleteQuery)) {
            ps.setInt(1, appointmentID);
            ps.executeUpdate();
            return true; // Appointment deleted successfully return true
        }
    }
    
    /**
     * Takes an appointment and updates the DB to reflect it.
     * 
     * @param appointment   Updated appointment
     * @return              True if updated successfully 
     * @throws SQLException 
     */
    public static boolean updateAppointment(Appointment appointment) throws SQLException {
        String updateQuery = "UPDATE Appointments SET Title = ?, Description = ? , Location = ?, Type = ?, Start = ?, End = ?, Last_Update = NOW(), Last_Updated_By = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";

        try (Connection connection = JDBC.getConnection();
            PreparedStatement ps = connection.prepareStatement(updateQuery)) {
            ps.setString(1, appointment.getAppointmentTitle());     
            ps.setString(2, appointment.getAppointmentDescription());
            ps.setString(3, appointment.getAppointmentLocation());
            ps.setString(4, appointment.getAppointmentType());
            // Converting ZonedDateTime to Timestamp for Database Storage
            ps.setTimestamp(5, Timestamp.from(appointment.getAppointmentStart().toInstant()));
            // Converting ZonedDateTime to Timestamp for Database Storage
            ps.setTimestamp(6, Timestamp.from(appointment.getAppointmentEnd().toInstant()));
            ps.setString(7, appointment.getUpdatedBy());
            ps.setInt(8, appointment.getAppointmentCustomerID());
            ps.setInt(9, appointment.getAppointmentUserID());
            ps.setInt(10, appointment.getAppointmentContactID());
            ps.setInt(11, appointment.getAppointmentID());
            
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;              // Returns true if the update was successful
        }
        
    }
    
    /**
     * Returns all Appointments in DB
     * 
     * @return          All Appointments
     * @throws SQLException 
     */
    public static ObservableList<Appointment> getAllAppointments() throws SQLException {
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
        String query = "SELECT * FROM Appointments";
        try (Connection connection = JDBC.getConnection();
            PreparedStatement ps = connection.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                appointmentList.add(new Appointment(
                    rs.getInt("Appointment_ID"),
                    rs.getString("Title"),
                    rs.getString("Description"),
                    rs.getString("Location"),
                    rs.getString("Type"),
                    rs.getTimestamp("Start").toInstant().atZone(ZoneId.systemDefault()),
                    rs.getTimestamp("End").toInstant().atZone(ZoneId.systemDefault()),
                    rs.getTimestamp("Create_Date").toInstant().atZone(ZoneId.systemDefault()),
                    rs.getString("Created_By"),
                    rs.getTimestamp("Last_Update").toInstant().atZone(ZoneId.systemDefault()),
                    rs.getString("Last_Updated_By"),
                    rs.getInt("Customer_ID"),
                    rs.getInt("User_ID"),
                    rs.getInt("Contact_ID")));
            }
        }
        return appointmentList;
    }
    
    /**
     * Runs a Query that generates Appointment summaries
     * returns a list of all appointmentSummary
     * 
     * @return    Observable Appointment Summary list
     * @throws SQLException 
     */
    public static ObservableList<AppointmentSummary> getAppointmentSummaries() throws SQLException {
        ObservableList<AppointmentSummary> summaries = FXCollections.observableArrayList();
        String query = " SELECT YEAR(Start) AS year, MONTH(Start) AS month, Type, COUNT(*) AS total " +
            "FROM Appointments " +
            "GROUP BY YEAR(Start), MONTH(Start), Type " +
            "ORDER BY YEAR(Start), MONTH(Start), Type;";

        try (Connection connection = JDBC.getConnection();
             PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String year = String.valueOf(rs.getInt("year"));
                String month = rs.getString("month"); // Convert this to month name if necessary
                String type = rs.getString("Type");
                Integer total = rs.getInt("total");

                summaries.add(new AppointmentSummary(year, util.getMonthName(month), type, total));
            }
        }
        return summaries;
    }
    
    /**
     * Returns the contactSchedules of a single contact
     * 
     * @param contactID Contact to find schedules for
     * @return          ObservableList of ContactSchedule
     * @throws SQLException 
     */
    public static ObservableList<ContactSchedule> getContactSchedules(int contactID) throws SQLException {
        ObservableList<ContactSchedule> contactSchedules = FXCollections.observableArrayList();
        String query = "SELECT Appointment_ID, Title, Type, Description, Start, End, Customer_ID FROM Appointments WHERE Contact_ID = ?";

        try (Connection connection = JDBC.getConnection();
            PreparedStatement ps = connection.prepareStatement(query)) {
            
            ps.setInt(1, contactID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                contactSchedules.add(new ContactSchedule(
                    rs.getInt("Appointment_ID"),
                    rs.getString("Title"),
                    rs.getString("Type"),
                    rs.getString("Description"),
                    rs.getTimestamp("Start").toInstant().atZone(ZoneId.systemDefault()),
                    rs.getTimestamp("End").toInstant().atZone(ZoneId.systemDefault()),
                    rs.getInt("Customer_ID")
                ));
            }
        }
        return contactSchedules;
    }
    
    /**
     * Gets the total appointments in DB
     * 
     * @return  int of total appointments
     * @throws SQLException 
     */
    public static int getTotalAppointments() throws SQLException {
        String query = "SELECT COUNT(*) AS total FROM Appointments";
        try (Connection connection = JDBC.getConnection();
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("total");
            }
        }
        return 0;
    }
    
    /**
     * gets appointments within 15 minutes for the active user
     * 
     * @return
     * @throws SQLException 
     */
    public static List<Appointment> getAppointmentsWithin15Minutes() throws SQLException {
        int userID = SessionManager.getInstance().getActiveUser().getUserID();
        List<Appointment> appointments = getAllAppointments();
        List<Appointment> upcomingAppointments = new ArrayList<>();
        ZonedDateTime now = ZonedDateTime.now(ZoneId.systemDefault());

         for (Appointment appointment : appointments) {
            ZonedDateTime appointmentStart = appointment.getAppointmentStart();
            ZonedDateTime inFifteenMinutes = now.plusMinutes(15);

            if (appointmentStart.isAfter(now)
                    && appointmentStart.isBefore(inFifteenMinutes)
                    && appointment.getAppointmentUserID() == userID)
            {
                upcomingAppointments.add(appointment);
            }
        }

        return upcomingAppointments;
    }
}
