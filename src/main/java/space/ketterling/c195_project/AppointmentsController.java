package space.ketterling.c195_project;

import DAO.AppointmentDAO;
import DAO.ContactDAO;
import model.Appointment;
import model.Contact;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.beans.property.ReadOnlyStringWrapper;
import java.time.format.DateTimeFormatter;
import java.time.ZonedDateTime;
import java.time.ZoneId;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

import model.SessionManager;
/**
 * FXML Controller class for the appointments page
 *
 * @author Taylor
 */
public class AppointmentsController extends BaseController {

    // Table View
    @FXML
    private TableView<Appointment> appointmentTableView;
    @FXML
    private TableColumn<Appointment, Integer> appointmentID;
    @FXML
    private TableColumn<Appointment, String> appointmentTitle;
    @FXML
    private TableColumn<Appointment, String> appointmentDescription;
    @FXML
    private TableColumn<Appointment, String> appointmentLocation;
    @FXML
    private TableColumn<Appointment, String> appointmentContact;
    @FXML
    private TableColumn<Appointment, String> startDT;
    @FXML
    private TableColumn<Appointment, String> endDT;
    @FXML
    private TableColumn<Appointment, Integer> Customer_ID;
    @FXML
    private TableColumn<Appointment, Integer> User_ID;
    @FXML
    private TextField searchBar;
    @FXML
    private Label messageLabel;
    @FXML
    private DatePicker filterDatePicker;
    // Selected Appointment on TableView
    private static Appointment selectedAppointment;
    
    // Allows other controllers to grab what was selected
    public static Appointment getAppointmentToModify(){
        return selectedAppointment;
    }
    
    /**
     * Handles when the date picker receives a date to filter
     * 
     */
    @FXML
    void filterTableByDate(){
        LocalDate selectedDate = filterDatePicker.getValue();
        if (selectedDate != null) {
            populateTableViewWithDateFilter(selectedDate);
        } else {
            populateTableView();
            // if empty just loads table
        }
        
    }
    
    /**
     * Handles switching scene to the Customer's Page
     * 
     * @param event 
     */
    @FXML
    void CustomersButtonAction(ActionEvent event) {
        switchScene(event, "Customers");
        
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
     * Handles switching scene to Add Appointment Form
     * 
     * @param event 
     */
    @FXML
    void addAppointmentButtonAction(ActionEvent event) {
        switchScene(event, "AddAppointment");
    }
    
    /**
     * Handles switching scene to Appointment Update Form
     * 
     * @param event 
     */
    @FXML
    void updateAppointmentButtonAction(ActionEvent event) {
        selectedAppointment = appointmentTableView.getSelectionModel().getSelectedItem();
        if (selectedAppointment == null) {
            displayAlert(16);
        } else {
            switchScene(event, "UpdateAppointment");
        }
    }
    
    /**
     * Helper button, to show users how to use the Date Picker Filter
     * 
     * @param event 
     */
    @FXML
    void helperInfo(ActionEvent event) {
        displayAlert(33);
    }
    
    /**
     * Handles Deleting Selected Appointment
     * must have an appointment selected
     * @param event 
     */
    @FXML
    void deleteAppointmentButtonAction(ActionEvent event) {
        Appointment selectedAppointment = appointmentTableView.getSelectionModel().getSelectedItem();
        if (selectedAppointment == null) {
            displayAlert(16);
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            if("fr".equals(SessionManager.getInstance().getCurrentLanguage())){
                alert.setTitle("Confirmation de suppression de rendez-vous");
                alert.setContentText("Voulez-vous SUPPRIMER le rendez-vous sélectionné ?\n\n" +
                                    "Titre du rendez-vous : " + selectedAppointment.getAppointmentTitle() + "\n" +
                                    "ID du rendez-vous : " + selectedAppointment.getAppointmentID() + "\n" +
                                    "Type de rendez-vous : " + selectedAppointment.getAppointmentType());
            } else {
                alert.setTitle("Appointment Deletion Confirmation");
                alert.setContentText("Do you want to DELETE the selected Appointment?\n\n" +
                                "Appointment Title: " + selectedAppointment.getAppointmentTitle() + "\n" +
                                "Appointment ID: " + selectedAppointment.getAppointmentID() + "\n" +
                                "Appointment Type: " + selectedAppointment.getAppointmentType());
            }
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {

                try {
                    if(AppointmentDAO.deleteAppointment(selectedAppointment.getAppointmentID())){
                        displayAlert(22);
                        //displays success if true
                    } else {
                        displayAlert(23);
                        //displays failure if false
                    }
                } catch(SQLException e) {
                   e.printStackTrace();
                   displayAlert(26);
                
                }
                populateTableView(); //Updates the table view post deletion
            }
        }
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
     * updates TableView with Customer Data
     * 
     */
    private void populateTableView() {
        try {
            ObservableList<Appointment> appointments = AppointmentDAO.getAllAppointments();
            System.out.println("test: "+appointments);
            appointmentTableView.setItems(appointments);
            appointmentTableView.refresh();
        } catch (SQLException e) {
            e.printStackTrace();
            displayAlert(25);
        }
    }
    
    /**
     * Works much like populateTableView() except this on takes in a date in the future and finds all
     * the appointments between now and then
     * 
     * @param endDate End of search time
     */
    private void populateTableViewWithDateFilter(LocalDate selectedDate) {
        try {
            ObservableList<Appointment> allAppointments = AppointmentDAO.getAllAppointments();
            ObservableList<Appointment> filteredAppointments = FXCollections.observableArrayList();

            // Setup to work either in the past or future. if old date is provided, will filter towards the past
            // if future date is provided, will filter to the future
            LocalDate today = LocalDate.now(); // todays date
            LocalDate startDate = selectedDate.isBefore(today) ? selectedDate : today;
            LocalDate endDate = selectedDate.isAfter(today) ? selectedDate : today;

            for (Appointment appointment : allAppointments) {
                LocalDate appointmentDate = appointment.getAppointmentStart().toLocalDate();
                if (!appointmentDate.isBefore(startDate) && !appointmentDate.isAfter(endDate)) {
                    filteredAppointments.add(appointment);
                }
            }

            appointmentTableView.setItems(filteredAppointments);
            appointmentTableView.refresh();
        } catch (SQLException e) {
            e.printStackTrace();
            displayAlert(25);
        }
    }
    
    /**
     * Sets up the Date columns to be formatted into a string and displayed
     * 
     */
    private void setupDateTimeColumns() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm z");
        // im using lower case z but GMT printed out like upper case Z .. date time formatter

        // Converting the object's ZonedDateTime to users local time as a String
        // Second use of Lambda , this time to set the formatted zonedDateTimes into each cell
        startDT.setCellValueFactory(cellData -> {
            ZonedDateTime startZdt = cellData.getValue().getAppointmentStart();
            return new ReadOnlyStringWrapper(startZdt.format(formatter));
        });

        endDT.setCellValueFactory(cellData -> {
            ZonedDateTime endZdt = cellData.getValue().getAppointmentEnd();
            //System.out.println("Original End: " + endZdt + ", Local End: " + localEnd);
            return new ReadOnlyStringWrapper(endZdt.format(formatter));
        });
        
        

    }
    
    /**
     * Sets up the contact column to show the contacts name. Takes the contact ID and finds the name
     * 
     */
    private void setupContactColumn() {
        // Another use of lambda, this time for assigning a string to the cell factory by using contactID 
        // to fetch the contact's name
        appointmentContact.setCellValueFactory(cellData -> {
            int contactId = cellData.getValue().getAppointmentContactID();
            Contact contact = ContactDAO.getContact(contactId);
            String contactName = (contact != null) ? contact.getContactName() : "Unknown";
            return new ReadOnlyStringWrapper(contactName);
        });
    }
    
    /**
     * Check for appointments, if any within 15 minutes will warn user
     * 
     */
    private void checkForUpcomingAppointments() {
        String languageCode = SessionManager.getInstance().getCurrentLanguage();
        try {
            List<Appointment> upcomingAppointments = AppointmentDAO.getAppointmentsWithin15Minutes();
            if (!upcomingAppointments.isEmpty()) {
                Appointment nextAppointment = upcomingAppointments.get(0);
                if("fr".equals(languageCode)){
                    messageLabel.setText("Prochaine réunion, ID de rdv : " + nextAppointment.getAppointmentID()
                        + "\nHeure de début : " + nextAppointment.getAppointmentStart().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm z"))
                        + ",  Heure de fin : " + nextAppointment.getAppointmentEnd().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm z")) + ".");

                } else {
                    messageLabel.setText("Upcoming Meeting, appt ID: " + nextAppointment.getAppointmentID()
                        + "\nStart Time: " + nextAppointment.getAppointmentStart().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm z"))
                        + ",  End Time: " + nextAppointment.getAppointmentEnd().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm z"))+".");
                }
            } else {
                if("fr".equals(languageCode)){
                    messageLabel.setText("Aucun rendez-vous prévu dans les 15 prochaines minutes.");

                } else {
                    messageLabel.setText("No upcoming appointments within the next 15 minutes.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Initializes the controller class.
     * 
     * Lambda used for search bar functionality
     * adding a method to a Listener
     * updates table when ran
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        appointmentID.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("AppointmentID"));
        appointmentTitle.setCellValueFactory(new PropertyValueFactory<Appointment, String>("appointmentTitle"));
        appointmentDescription.setCellValueFactory(new PropertyValueFactory<Appointment, String>("appointmentDescription"));
        appointmentLocation.setCellValueFactory(new PropertyValueFactory<Appointment, String>("appointmentLocation"));
        Customer_ID.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("appointmentCustomerID"));
        User_ID.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("appointmentUserID"));
        setupDateTimeColumns();
        setupContactColumn();
        populateTableView();
        
                                                            // Lambda Expression
        searchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                appointmentTableView.setItems(AppointmentDAO.searchAppointments(newValue));
                appointmentTableView.refresh();
            } catch (SQLException e) {
                e.printStackTrace();
                displayAlert(30); //Appointments unable to load alert
            }
        });
        
        checkForUpcomingAppointments();
    }
}
