package helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Java DataBase Connector , Set to connect to Schedule's Database which contains multiple tables for scheduling app
 * 
 * @author Taylor Ketterling
 */
public class JDBC {
    // Database IP address: 191.96.56.103
    // Port: 3306
    // Databse: u117460936_Schedules
    // ?useSSL=false set for stability issues(DB would not connect without) needs looking into
    // User = u117460936_test  , password = WGUtest!1
    private static final String DATABASE_URL = "jdbc:mysql://191.96.56.103:3306/u117460936_Schedules?useSSL=false";
    private static final String DATABASE_USERNAME = "u117460936_test";
    private static final String DATABASE_PASSWORD = "WGUtest!1";

    private static Connection connection = null;
    
    /**
     * Opens a connection with the Database using credentials
     * 
     * @throws SQLException 
     */
    public static void openConnection() throws SQLException {
        connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
    }
    
    /**
     * Closes a connection if not already closed
     * 
     * @throws SQLException 
     */
    public static void closeConnection() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    /**
     * Gets a connection, if not opened, opens the connection
     * 
     * @return  the connection
     * @throws SQLException 
     */
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed() || !connection.isValid(1)) {
            openConnection();
        }
        return connection;
    }
}
