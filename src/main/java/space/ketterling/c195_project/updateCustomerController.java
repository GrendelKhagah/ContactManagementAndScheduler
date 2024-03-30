package space.ketterling.c195_project;

import DAO.CountryDAO;
import DAO.CustomerDAO;
import DAO.FirstLevelDivisionsDAO;
import java.net.URL;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import model.Country;
import model.Customer;
import model.SessionManager;

/**
 * FXML Controller class for updatingCustomer.fxml
 *
 * @author Taylor Ketterling
 */
public class updateCustomerController extends BaseController {
    
    @FXML
    private TextField customerIDField;
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
     * Handles switching scene to the Customers tableview Page
     * 
     * @param event Sends user Back
     */
    @FXML
    void BackButtonAction(ActionEvent event) {
        switchScene(event, "Customers");
        
    }
    
    /**
     * Handles Updating Customer Data and switching back to Customers PAge
     * 
     * Will check for empty fields
     * 
     * @param event Sends user Back
     */
    @FXML
    void updateButtonAction(ActionEvent event) {
        int customerID = Integer.parseInt(customerIDField.getText());
        String customerName = customerNameField.getText();
        String address = addressField.getText();
        String postalCode = postalCodeField.getText();
        String country = countryComboBox.getValue();
        String firstDivision = firstDivisionComboBox.getValue();
        String phone = phoneNumberField.getText();
        
        String updatedBy = SessionManager.getInstance().getActiveUser().getUserName();
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
            return; // Stops execution if any field is empty
        }
        
        Customer updatedCustomer = new Customer(
                customerID,
                customerName,
                address,
                postalCode,
                firstDivision,
                country,
                phone,
                ZonedDateTime.now(),
                updatedBy,
                ZonedDateTime.now(),
                updatedBy,
                divisionID);
        try {
            CustomerDAO.updateCustomer(updatedCustomer);
            displayAlert(9); // success alert
            switchScene(event, "Customers");
          
        } catch (SQLException e) {
            displayAlert(10);
            e.printStackTrace();
        }
    }
    
    /**
     * Method is attached to country combo box, on action will update divisions made available
     * 
     */
    @FXML
    private void onCountrySelected() {
        String selectedCountryName = countryComboBox.getValue();
        Country country = CountryDAO.getCountryByName(selectedCountryName); 

        try {
            List<String> divisions = FirstLevelDivisionsDAO.getDivisionsByCountryId(country.getCountryID());
            firstDivisionComboBox.setItems(FXCollections.observableArrayList(divisions));
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
  
    /**
     * Initializes the controller class.
     * 
     * populates all fields with Customer data
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Customer selectedCustomer = CustomersController.getCustomerToModify();
        if(selectedCustomer != null){
            //System.out.println(selectedCustomer); //debugg testing
            
            customerIDField.setText(Integer.toString(selectedCustomer.getCustomerID()));
            customerNameField.setText(selectedCustomer.getCustomerName());
            addressField.setText(selectedCustomer.getCustomerAddress());
            postalCodeField.setText(selectedCustomer.getCustomerPostalCode());
            phoneNumberField.setText(selectedCustomer.getCustomerPhone());
            
            List<String> countries = CountryDAO.getAllCountries();
            countryComboBox.getItems().addAll(countries);
            countryComboBox.getSelectionModel().select(selectedCustomer.getCustomerCountry());
            onCountrySelected();
            firstDivisionComboBox.getSelectionModel().select(selectedCustomer.getCustomerRegion());
        }
    }
}
