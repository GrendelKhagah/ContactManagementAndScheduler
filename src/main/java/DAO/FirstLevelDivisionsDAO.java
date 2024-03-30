package DAO;

import helper.JDBC;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DOA for fetching First level Divisions and getting divisions b
 * 
 * @author Taylor Ketterling
 */
public class FirstLevelDivisionsDAO {

    
    public static int getDivisionID(String divisionName){
        String query = "SELECT Division_ID FROM FirstLevelDivision WHERE Division_Name = ?"; 
        int divisionId = -1; 
        try (Connection connection = JDBC.getConnection();
            PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, divisionName);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    divisionId = rs.getInt("Division_ID");
                }
            }
        } catch (SQLException e){
            System.out.println("issue @ getDivisionID");
        }
        return divisionId;
    }
    
    /**
     * Gets all Divisions in database
     * 
     * @return
     * @throws SQLException 
     */
    public static List<String> getAllDivisions() throws SQLException {
        List<String> divisions = new ArrayList<>();
        String query = "SELECT * FROM FirstLevelDivision";

        try (Connection connection = JDBC.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    divisions.add(rs.getString("Division_Name"));
                }
            }
        }
        return divisions;
    }
    
    /**
     * Gets all division for a country ID
     * 
     * @param countryId  Country ID to be filtered by
     * @return           List of FirstDivisions in country_ID
     * @throws SQLException 
     */
    public static List<String> getDivisionsByCountryId(int countryId) throws SQLException {
        List<String> divisions = new ArrayList<>();
        String query = "SELECT Division_Name FROM FirstLevelDivision WHERE Country_ID = ?";

        try (Connection connection = JDBC.getConnection();
            PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, countryId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    divisions.add(rs.getString("Division_Name"));
                }
            }
        }
        return divisions;
    }
}
