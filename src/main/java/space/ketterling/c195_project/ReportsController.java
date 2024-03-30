package space.ketterling.c195_project;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.SessionManager;
import model.AppointmentSummary;
import model.ContactSchedule;
import DAO.ContactDAO;
import DAO.AppointmentDAO;
import DAO.UserDAO;
import DAO.CustomerDAO;
import java.sql.SQLException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

/**
 * FXML Controller class for the Reports.fxml page
 * 
 * Generates the Reports and displays them on the page
 *
 * @author Taylor Ketterling
 */
public class ReportsController extends BaseController {
    
    @FXML
    private TableView<AppointmentSummary> appointmentsSummaryTable;
    @FXML
    private TableColumn<AppointmentSummary, String> yearColumn;
    @FXML
    private TableColumn<AppointmentSummary, String> monthColumn;
    @FXML
    private TableColumn<AppointmentSummary, String> typeColumn;
    @FXML
    private TableColumn<AppointmentSummary, Number> totalColumn;
    
    @FXML
    private ComboBox<String> contactsComboBox;
    @FXML
    private TableView<ContactSchedule> contactSchedulesTable;
    @FXML
    private TableColumn<ContactSchedule, Integer> csAppointmentIdColumn;
    @FXML
    private TableColumn<ContactSchedule, String> csTitleColumn;
    @FXML
    private TableColumn<ContactSchedule, String> csTypeColumn;
    @FXML
    private TableColumn<ContactSchedule, String> csDescriptionColumn;
    @FXML
    private TableColumn<ContactSchedule, String> csStartColumn;
    @FXML
    private TableColumn<ContactSchedule, String> csEndColumn;
    @FXML
    private TableColumn<ContactSchedule, Integer> csCustomerIDColumn;
    
    @FXML
    private Label totalUsers;
    @FXML
    private Label totalCustomers;
    @FXML
    private Label totalAppointments;
    
    /**
     * Handles switching scene to customers page
     * @param event 
     */
    @FXML
    void CustomersButtonAction(ActionEvent event) {
        switchScene(event, "Customers");
        
    }
    
    /**
     * Logs the user out and returns them to the loginform
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
     * Method is attached to country combo box, on action will update divisions available for selected country
     * 
     */
    @FXML
    private void onContactSelected() {
        String selectedContactName = contactsComboBox.getValue();
        int contactID = ContactDAO.getcontactID(selectedContactName);

        try {
            ObservableList<ContactSchedule> schedules = AppointmentDAO.getContactSchedules(contactID);
            contactSchedulesTable.setItems(schedules);
            setupDateTimeColumns();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Sets up the Date columns to be formatted into a string and displayed
     * Uses Lambda to set the value as the output of the attached method.
     */
    private void setupDateTimeColumns() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // Converting the object's ZonedDateTime to users local time as a String
        // Use of Lambda , this time to set the formatted zonedDateTimes into each cell
        csStartColumn.setCellValueFactory(cellData -> {
            ZonedDateTime startZdt = cellData.getValue().getStartDate();
            return new ReadOnlyStringWrapper(startZdt.withZoneSameInstant(ZoneId.systemDefault()).format(formatter));
        });

        csEndColumn.setCellValueFactory(cellData -> {
            ZonedDateTime endZdt = cellData.getValue().getEndDate();
            return new ReadOnlyStringWrapper(endZdt.withZoneSameInstant(ZoneId.systemDefault()).format(formatter));
        });
    }
    
    
    /**
     * Initializes the controller class.
     * 
     * Generates a Appointment summary and displays that data to the appointments summary table
     * 
     * Sets up a contact schedule view, user must choose contact for data to populate
     * 
     * Counts the total users, customers, and appointments and displays it on the reports page
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        //   the total number of customer appointments by type and month and by year
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
        monthColumn.setCellValueFactory(new PropertyValueFactory<>("month"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        totalColumn.setCellValueFactory(new PropertyValueFactory<>("totalAppointments"));
        try {
            ObservableList<AppointmentSummary> summaries = AppointmentDAO.getAppointmentSummaries();
            appointmentsSummaryTable.setItems(summaries);
        } catch (SQLException e){
            displayAlert(33);
            e.printStackTrace();
        }
        
        // Contact Schedule TableView setup
        csAppointmentIdColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        csTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        csTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        csDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        csStartColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        csEndColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        csCustomerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        contactsComboBox.setItems(ContactDAO.getAllContactsNamelist());
        
        //Contact Schedule table updates after Person is selected.
        
        // Try and fetch the 3 total counts and populate the data on the reports page
        try {
            int usersCount = UserDAO.getTotalUsers();
            int customersCount = CustomerDAO.getTotalCustomers();
            int appointmentsCount = AppointmentDAO.getTotalAppointments();

            totalUsers.setText(String.valueOf(usersCount));
            totalCustomers.setText(String.valueOf(customersCount));
            totalAppointments.setText(String.valueOf(appointmentsCount));
        } catch (SQLException e) {
            // Handle exceptions, maybe log an error or display a message to the user
            e.printStackTrace();
        }
    }
}
