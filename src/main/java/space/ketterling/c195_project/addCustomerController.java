package space.ketterling.c195_project;

import DAO.CustomerDAO;
import DAO.CountryDAO;
import model.Country;
import model.SessionManager;
import DAO.FirstLevelDivisionsDAO;
import model.Customer;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import javafx.collections.FXCollections;


/**
 * FXML Controller class for the adding customer form
 *
 * @author Taylor Ketterling
 */
public class addCustomerController extends BaseController {

    @FXML
    private TextField customerNameField;
    @FXML
    private TextField addressField;
    @FXML
    private TextField postalCodeField;
    @FXML
    private TextField phoneNumberField;
    @FXML
    private ComboBox<String> countryComboBox;
    @FXML
    private ComboBox<String> firstDivisionComboBox;
    
    /**
     * Handles switching scene to the Customers Page
     * 
     * @param event Sends user Back
     */
    @FXML
    void BackButtonAction(ActionEvent event) {
        switchScene(event, "Customers");
        
    }
    
    /**
     * action event for when add customer button is pressed
     * will take contents of fields and attempt to add a customer
     * 
     * 
     * @param event 
     */
    @FXML
    void addCustomerButtonAction(ActionEvent event) {
        String customerName = customerNameField.getText();
        String address = addressField.getText();
        String postalCode = postalCodeField.getText();
        String country = countryComboBox.getValue();
        String firstDivision = firstDivisionComboBox.getValue();
        String phone = phoneNumberField.getText();
        String createdBy = SessionManager.getInstance().getActiveUser().getUserName();
        int divisionID = FirstLevelDivisionsDAO.getDivisionID(firstDivision); //returns -1 if errors in DAO 
        
        // Check for empty fields
        if (customerName.isEmpty() ||
                address.isEmpty() ||
                postalCode.isEmpty() ||
                country == null ||
                firstDivision == null ||
                phone.isEmpty()) 
        {
            displayAlert(8); // Show an alert for empty fields
            return;         // Stops execution if any field is empty
        }
        
        Customer customer = new Customer(
                0,
                customerName,
                address,
                postalCode,
                firstDivision,
                country,
                phone,
                ZonedDateTime.now(),
                createdBy,
                divisionID);

        try {
            CustomerDAO.addCustomer(customer);
            displayAlert(3); // success alert
            switchScene(event, "Customers");
          
        } catch (SQLException e) {
            displayAlert(4);
            e.printStackTrace();
        }
    }
    
    /**
     * Method is attached to country combo box, on action will update divisions available for selected country
     * 
     */
    @FXML
    private void onCountrySelected() {
        String selectedCountryName = countryComboBox.getValue();
        Country country = CountryDAO.getCountryByName(selectedCountryName); 
        
        try {
            List<String> divisions = FirstLevelDivisionsDAO.getDivisionsByCountryId(country.getCountryID());
            Collections.sort(divisions);//sorts the divisions alphabetically before loading them into combobox
            firstDivisionComboBox.setItems(FXCollections.observableArrayList(divisions));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    /**
     * Initializes the controller class.
     * Loads countries with available selectable countries
     * once a country is selected, available first divisions will be made selectable
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        List<String> countries = CountryDAO.getAllCountries();
        Collections.sort(countries);//sorts the counrties alphabetically before loading them into combobox
        countryComboBox.getItems().addAll(countries);
    }    
    
}
