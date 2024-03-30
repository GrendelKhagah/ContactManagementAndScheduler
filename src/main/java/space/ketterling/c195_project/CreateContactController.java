package space.ketterling.c195_project;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import model.Customer;
import model.Contact;
import DAO.ContactDAO;

/**
 * FXML Controller class for CreateContact 
 * 
 * Allows contacts to be made
 *
 * @author Taylor Ketterling
 */
public class CreateContactController extends BaseController {
    
    @FXML
    private TextField contactNameField;
    @FXML
    private TextField emailField;
    
    
    /**
     * Handles switching scene to the Customers Page
     * 
     * @param event Sends user Back
     */
    @FXML
    void backButtonAction(ActionEvent event) {
        switchScene(event, "Customers");
        
    }
    
    /**
     * button listener, takes field data and attempts to add a contact
     * 
     * @param event 
     */
    @FXML
    void addContactButtonAction(ActionEvent event) {
        String contactName = contactNameField.getText();
        String contactEmail = emailField.getText();
        
        // Check for empty fields
        if (contactName.isEmpty() ||
                contactEmail.isEmpty()) 
        {
            displayAlert(8); // Show an alert for empty fields
            return;         // Stops execution if any field is empty
        }
        
        Contact contact = new Contact(
                0,
                contactName,
                contactEmail);
        try {
            ContactDAO.addContact(contact);
            displayAlert(29);
            switchScene(event, "Customers");
        } catch (SQLException e) {
            displayAlert(4);
            e.printStackTrace();
        }
    }
    
    
    /**
     * Initializes the controller class.
     * 
     * fills conactName with selected customer's name
     * not a requirement to keep that name
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Customer selectedCustomer = CustomersController.getCustomerToModify(); 
        contactNameField.setText(selectedCustomer.getCustomerName());
    }    
    
}
