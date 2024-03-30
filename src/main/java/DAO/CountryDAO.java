package DAO;

import helper.JDBC;
import model.Country;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 *
 * @author Taylor
 */
public class CountryDAO {

    /**
     * Retrieves all countries from the database.
     * 
     * @return A List of Country names.
     */
    public static List<String> getAllCountries() {
        List<String> countries = new ArrayList<>();
        String query = "SELECT Country_Name FROM Country";

        try (Connection connection = JDBC.getConnection();
             PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                countries.add(rs.getString("Country_Name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return countries;
    }
    
     /**
     * Retrieves a country by its name.
     * 
     * @param countryName The name of the country.
     * @return A Country object if found, null otherwise.
     */
    public static Country getCountryByName(String countryName) {
        String query = "SELECT * FROM Country WHERE Country_Name = ?";
        Country country = null;

        try (Connection connection = JDBC.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
             
            ps.setString(1, countryName);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int countryId = rs.getInt("Country_ID");
                    String name = rs.getString("Country_Name");
                    ZonedDateTime createDate = rs.getTimestamp("Create_Date").toInstant().atZone(ZoneId.systemDefault());
                    String createdBy = rs.getString("Create_By");
                    ZonedDateTime lastUpdate = rs.getTimestamp("Last_Update").toInstant().atZone(ZoneId.systemDefault());
                    String updatedBy = rs.getString("Last_Updated_By");
                    
                    country = new Country(countryId, name, createDate, createdBy, lastUpdate, updatedBy);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return country;
    }
}