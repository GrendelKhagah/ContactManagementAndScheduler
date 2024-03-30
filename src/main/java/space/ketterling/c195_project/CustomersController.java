package space.ketterling.c195_project;

import DAO.AppointmentDAO;
import DAO.CustomerDAO;
import model.Customer;
import model.SessionManager;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import java.sql.SQLException;
import java.util.List;
import javafx.scene.control.Label;
import model.Appointment;
import java.time.format.DateTimeFormatter;

/**
 * FXML Controller class for Customers.fxml
 *
 * @author Taylor
 */
public class CustomersController extends BaseController {
    
    // Table View
    @FXML
    private TableView<Customer> customerTableView;
    @FXML
    private TableColumn<Customer, Integer> customerID;
    @FXML
    private TableColumn<Customer, String> customerName;
    @FXML
    private TableColumn<Customer, String> address;
    @FXML
    private TableColumn<Customer, String> firstDivision;
    @FXML
    private TableColumn<Customer, String> country;
    @FXML
    private TableColumn<Customer, String> postal;
    @FXML
    private TableColumn<Customer, String> phoneNumber;
    @FXML
    private TextField searchBar;
    @FXML
    private Label messageLabel;
    
    
    // Selected Customer on TableView
    private static Customer selectedCustomer;
    
    // Allows other controllers to grab who was selected
    public static Customer getCustomerToModify(){
        return selectedCustomer;
    }

    /**
     * Handles switching scene to Add Customers Form
     * 
     * @param event 
     */
    @FXML
    void addCustomerButtonAction(ActionEvent event) {
        switchScene(event, "addCustomer");
    }
    
    /**
     * Handles switching scene to Customer Update Form
     * 
     * @param event 
     */
    @FXML
    void updateCustomerButtonAction(ActionEvent event) {
        selectedCustomer = customerTableView.getSelectionModel().getSelectedItem();
        if (selectedCustomer == null) {
            displayAlert(14);
        } else {
            switchScene(event, "updateCustomer");
        }
    }
    
    /**
     * Handles switching scene to Contact Create form
     * 
     * @param event 
     */
    @FXML
    void createContactButtonAction(ActionEvent event) {
        selectedCustomer = customerTableView.getSelectionModel().getSelectedItem();
        if (selectedCustomer == null) {
            displayAlert(14);
        } else {
            switchScene(event, "createContact");
        }
    }
    
    /**
     * Handles Deleting Selected Customer
     * 
     * @param event 
     */
    @FXML
    void deleteCustomerButtonAction(ActionEvent event) {
        Customer selectedCustomer = customerTableView.getSelectionModel().getSelectedItem();
        if (selectedCustomer == null) {
            displayAlert(14);
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alert");
            alert.setContentText("Do you want to DELETE the selected Customer?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {

                try {
                    if(CustomerDAO.deleteCustomer(selectedCustomer.getCustomerID())){
                        displayAlert(11);
                        
                    } else {
                        displayAlert(28);
                    }
                } catch(SQLException e) {
                   e.printStackTrace();
                   displayAlert(12);
                
                }
            }
            populateTableView();
        }
    }
    
    /**
     * Logs user out and sends them to the loginform
     * 
     * @param event 
     */
    @FXML
    void logoutButtonAction(ActionEvent event){
        SessionManager.getInstance().logOut();
        switchScene(event, "loginform");
    }
    
    /**
     * Handles switching scene to the Appointments Page
     * 
     * @param event 
     */
    @FXML
    void AppointmentsButtonAction(ActionEvent event) {
        switchScene(event, "Appointments");
    }
    
    /**
     * Handles switching scene to the Reports Page
     * 
     * @param event 
     */
    @FXML
    void ReportsButtonAction(ActionEvent event) {
        switchScene(event, "Reports");
    }
    
    /**
     * updates TableView with Customer Data
     * 
     */
    private void populateTableView() {
        try {
            ObservableList<Customer> customers = CustomerDAO.getAllCustomers();
            customerTableView.setItems(customers);
            customerTableView.refresh();
        } catch (SQLException e) {
            e.printStackTrace();
            displayAlert(25);
        }
    }
    
    /**
     * Check for appointments, if any within 15 minutes will warn user
     * 
     */
    private void checkForUpcomingAppointments() {
        try {
            List<Appointment> upcomingAppointments = AppointmentDAO.getAppointmentsWithin15Minutes();
            if (!upcomingAppointments.isEmpty()) {
                Appointment nextAppointment = upcomingAppointments.get(0);
                messageLabel.setText("Upcoming Meeting, appt ID: "
                        + nextAppointment.getAppointmentID()
                        + ", Start Time: "
                        + nextAppointment.getAppointmentStart().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm z"))
                        + ", End Time: "
                        + nextAppointment.getAppointmentEnd().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm z"))+".");
            } else {
                messageLabel.setText("No upcoming appointments within the next 15 minutes.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Initializes the controller class. 
     * Sets up the customer table view with column value factories and populates
     * the table view with customer data. Also implements a dynamic search feature 
     * for the customer table using a lambda expression.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        customerID.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("customerID"));
        customerName.setCellValueFactory(new PropertyValueFactory<Customer, String>("customerName"));
        address.setCellValueFactory(new PropertyValueFactory<Customer, String>("customerAddress"));
        firstDivision.setCellValueFactory(new PropertyValueFactory<Customer, String>("customerRegion"));
        country.setCellValueFactory(new PropertyValueFactory<Customer, String>("customerCountry"));
        postal.setCellValueFactory(new PropertyValueFactory<Customer, String>("customerPostalCode"));
        phoneNumber.setCellValueFactory(new PropertyValueFactory<Customer, String>("customerPhone"));
        
        populateTableView();
                                                            // Lambda Expression
        searchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                customerTableView.setItems(CustomerDAO.searchCustomer(newValue));
                customerTableView.refresh();
            } catch (SQLException e) {
                e.printStackTrace();
                displayAlert(13); //customers unable to load alert
            }
        });
        
        checkForUpcomingAppointments();
    }    
    
}
