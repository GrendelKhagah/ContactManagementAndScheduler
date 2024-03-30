package DAO;

import helper.JDBC;
import java.sql.Connection;
import model.User;
import model.SessionManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.time.ZoneId;

/**
 *  Validates User Credentials for login, adds users to DB, fetches all users from DB
 *
 * @author Taylor Ketterling
 */
public class UserDAO {
    
    /**
     * Adds Users, sets creation date to now, and last edit date to now
     * Allows for user account creation
     * 
     * Not currently in use in application (users manually added in DB)
     * 
     * @param userName  User's name
     * @param password  User's Password
     * @param createdBy Who is creating this user
     * @throws SQLException 
     */
    public static void addUser(String userName, String password, String createdBy) throws SQLException {
        String query = "INSERT INTO Users (User_Name, User_Password, Create_Date, Created_By, Last_Updated, Last_Updated_By) " +
                "VALUES (?, ?, NOW(), ?, NOW(), ?)";

        try (PreparedStatement ps = JDBC.getConnection().prepareStatement(query)) {
            ps.setString(1, userName);
            ps.setString(2, password);
            ps.setString(3, createdBy);
            ps.setString(4, createdBy);
            ps.executeUpdate();
        }
    }
    
     /**
     * Retrieves all users from the database and adds them to an observable list.
     *
     * @return An ObservableList of User objects.
     */
    public static ObservableList<User> getAllUsers() {
        ObservableList<User> userList = FXCollections.observableArrayList();
        String query = "SELECT * FROM Users";
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                User user = new User(
                    rs.getInt("User_ID"),
                    rs.getString("User_Name"),
                    rs.getString("Password"),
                    rs.getTimestamp("Create_Date").toLocalDateTime().atZone(ZoneId.systemDefault()),
                    rs.getString("Created_By"),
                    rs.getTimestamp("Last_Update").toLocalDateTime().atZone(ZoneId.systemDefault()),
                    rs.getString("Last_Updated_By"));
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    /**
     * Validates the user login based on username and password.
     *
     * @param userName The username.
     * @param password The password.
     * @return true if the username and password are valid and match a user in the database, otherwise false.
     */
    public static boolean validateLogin(String userName, String password) {
        String query = "SELECT * FROM Users WHERE User_Name = ? AND User_Password = ?";
        try (PreparedStatement ps = JDBC.getConnection().prepareStatement(query)) {
            ps.setString(1, userName);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            ZoneId zoneId = ZoneId.systemDefault();

            if (rs.next()) {
                User loggedInUser = new User(
                      rs.getInt("User_ID"),
                     rs.getString("User_Name"),
                     rs.getString("User_Password"),
                   rs.getTimestamp("Create_Date").toLocalDateTime().atZone(zoneId),
                    rs.getString("Created_By"),
                    rs.getTimestamp("Last_Updated").toLocalDateTime().atZone(zoneId),
                    rs.getString("Last_Updated_By"));
                    SessionManager.getInstance().setActiveUser(loggedInUser);  // Sets the active user in SessionManager
                    return true;
            }
           
            return false;  
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * gets the total amount of users in the database
     * returns 0 
     * 
     * @return int total users
     * @throws SQLException 
     */
    public static int getTotalUsers() throws SQLException {
    String query = "SELECT COUNT(*) AS total FROM Users";
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
