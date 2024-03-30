package space.ketterling.c195_project;

import DAO.AppointmentDAO;
import DAO.ContactDAO;
import DAO.CustomerDAO;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Appointment;
import model.Contact;
import model.Customer;
import model.SessionManager;
import helper.util;
import java.util.Collections;

/**
 * FXML Controller class for UpdateAppointment.fxml page
 * 
 * 
 *
 * @author Taylor
 */
public class UpdateAppointmentController extends BaseController {
    @FXML
    private TextField titleField;
    @FXML
    private TextField descriptionField;
    @FXML
    private TextField locationField;
    @FXML
    private TextField typeField;
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private DatePicker endDatePicker;
    @FXML
    private ComboBox<String> customersComboBox;
    @FXML
    private ChoiceBox<Integer> startChoiceBox;
    @FXML
    private ChoiceBox<Integer> endChoiceBox;
    @FXML
    private ChoiceBox<Integer> startMinChoiceBox;
    @FXML
    private ChoiceBox<Integer> endMinChoiceBox;
    @FXML
    private ComboBox<String> contactsComboBox;
    @FXML
    private Label idLabel;
    
    //Map allows selections to be mapped to IDs
    private Map<String, Integer> customerNameMap = new HashMap<>();
    private Map<Integer, String> customerIDMap = new HashMap<>();
    private Map<String, Integer> contactNameMap = new HashMap<>();
    private Map<Integer, String> contactIDMap = new HashMap<>();
    
    /**
     * Handles switching scene to the Customers Appointments Page
     * 
     * @param event Sends user Back
     */
    @FXML
    void BackButtonAction(ActionEvent event) {
        switchScene(event, "Appointments");
        
    }
    
    /**
     * Updates an Appointment with new information
     * 
     * @param event 
     */
    @FXML
    void updateAppointmentButtonAction(ActionEvent event){
        int AppointmentID = AppointmentsController.getAppointmentToModify().getAppointmentID() ;
        String Title = titleField.getText();
        String Description = descriptionField.getText();
        String Location = locationField.getText();
        String Type = typeField.getText();
        LocalDate localstartDate = startDatePicker.getValue();
        LocalDate localendDate = startDatePicker.getValue();
        String customer = customersComboBox.getValue();
        
        // Check for empty fields
        if (Title.isEmpty() ||
            Description.isEmpty() ||
            Location.isEmpty() ||
            Type.isEmpty() ||
            localstartDate == null ||
            localendDate == null ||
            customer == null) 
        {
            displayAlert(8); // Show an alert for empty fields
            return;         // Stops execution if any field is empty
        }
       
        //ZonedDateTime processing happens down here to avoid processing a null value
        ZonedDateTime startDateTime = getZonedDateTimeFromPickerAndChoices(startDatePicker, startChoiceBox, startMinChoiceBox);
        ZonedDateTime endDateTime = getZonedDateTimeFromPickerAndChoices(endDatePicker, endChoiceBox, endMinChoiceBox);
        
        String updatedBy = SessionManager.getInstance().getActiveUser().getUserName();
        int customerID = customerNameMap.get(customer);
        int userID = SessionManager.getInstance().getActiveUser().getUserID();
        int contactID = contactNameMap.get(contactsComboBox.getValue());
        
        //checks if given start and end times are within customer availability and business hours
        if (!businessLogicChecksPassed(startDateTime, endDateTime, customerID)) {
            displayAlert(17);
            return;
        }
        
        Appointment appointment = new Appointment(
                AppointmentID,
                Title,
                Description,
                Location,
                Type,
                startDateTime,
                endDateTime,
                ZonedDateTime.now(),
                updatedBy, // will create object with last user as updatedBy, CreateBy will get ignored in DAO
                customerID,
                userID,
                contactID);

        try {
            AppointmentDAO.updateAppointment(appointment);
            displayAlert(31); // success alert
            switchScene(event, "Appointments");
          
        } catch (SQLException e) {
            displayAlert(24);
            e.printStackTrace();
        }
    }
    
    /**
     * Converts Local Times to ZonedDateTimes
     * 
     * returns null if date hour and minute not provided
     * 
     * @param datePicker       fx datepocler
     * @param hourChoiceBox    fx hour choicebox
     * @param minuteChoiceBox  fx minute choicebox
     * @return 
     */
    private ZonedDateTime getZonedDateTimeFromPickerAndChoices(DatePicker datePicker, ChoiceBox<Integer> hourChoiceBox, ChoiceBox<Integer> minuteChoiceBox) {
        LocalDate date = datePicker.getValue();
        Integer hour = hourChoiceBox.getValue();
        Integer minute = minuteChoiceBox.getValue();

        if (date != null && hour != null && minute != null) {
            LocalTime time = LocalTime.of(hour, minute);
            return ZonedDateTime.of(date, time, ZoneId.systemDefault());
        } else {
            return null;
        }
    }
    
    /**
     * Checks to ensure 
     * 
     * @param startDate
     * @param endDate
     * @param customerID
     * @return 
     */
    private boolean businessLogicChecksPassed(ZonedDateTime startDate, ZonedDateTime endDate, int customerID){
        if(!util.isWithinBusinessHours(startDate, endDate)){return false;}
        else if(checkForOverlap(startDate, endDate, customerID)) {return false;}
        
        return true;
    }
    
    /**
     * Checks for overlaps in customers schedule with proposed timeFrame
     * returns true when overlap is detected.
     * 
     * @param startDate  ZonedDateTime StartDate
     * @param endDate    ZonedDateTime EndDate
     * @param customerID customer's ID
     * @return 
     */
    private boolean checkForOverlap(ZonedDateTime startDate, ZonedDateTime endDate, int customerID) {
        try {
            ObservableList<Appointment> allAppointments = AppointmentDAO.getAppointmentsForCustomer(customerID);
            for (Appointment existingAppointment : allAppointments) {
                ZonedDateTime existingStart = existingAppointment.getAppointmentStart();
                ZonedDateTime existingEnd = existingAppointment.getAppointmentEnd();
                // checks to see if the appointment being checked is the appointment being modified, if so ignore.
                if(existingAppointment.getAppointmentID() != AppointmentsController.getAppointmentToModify().getAppointmentID()){
                    if (startDate.isBefore(existingEnd) && endDate.isAfter(existingStart)) {
                        displayAlert(32);
                        return true; // Overlap was detected, return true
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            displayAlert(19);
        }
        return false; // No overlap detected, return false
    }
    
    /**
     * Initializes the controller class.
     * 
     * Fills the Form with selectedAppointent data
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Appointment selectedAppointment = AppointmentsController.getAppointmentToModify();
        if(selectedAppointment != null){            
            idLabel.setText("Appointment ID: " + Integer.toString(selectedAppointment.getAppointmentID()));
            titleField.setText(selectedAppointment.getAppointmentTitle());
            descriptionField.setText(selectedAppointment.getAppointmentDescription());
            locationField.setText(selectedAppointment.getAppointmentLocation());
            typeField.setText(selectedAppointment.getAppointmentType());
            
            
            startDatePicker.setValue(selectedAppointment.getAppointmentStart().toLocalDate());
            endDatePicker.setValue(selectedAppointment.getAppointmentEnd().toLocalDate());
            
            startChoiceBox.getItems().addAll(IntStream.rangeClosed(0, 23).boxed().collect(Collectors.toList()));
            startMinChoiceBox.getItems().addAll(IntStream.rangeClosed(0, 59).boxed().collect(Collectors.toList()));
            endChoiceBox.getItems().addAll(IntStream.rangeClosed(0, 23).boxed().collect(Collectors.toList()));
            endMinChoiceBox.getItems().addAll(IntStream.rangeClosed(0, 59).boxed().collect(Collectors.toList()));
            
            startChoiceBox.setValue(selectedAppointment.getAppointmentStart().getHour());
            startMinChoiceBox.setValue(selectedAppointment.getAppointmentStart().getMinute());
            endChoiceBox.setValue(selectedAppointment.getAppointmentEnd().getHour());
            endMinChoiceBox.setValue(selectedAppointment.getAppointmentEnd().getMinute());
            
            try {
            ObservableList<Customer> customers = CustomerDAO.getAllCustomers();
            ObservableList<String> customerNames = FXCollections.observableArrayList();
            for (Customer customer : customers) {
                customerNames.add(customer.getCustomerName());
                customerNameMap.put(customer.getCustomerName(), customer.getCustomerID());
                customerIDMap.put(customer.getCustomerID(), customer.getCustomerName());
            }
            Collections.sort(customerNames);
            customersComboBox.getItems().addAll(customerNames);
            customersComboBox.setValue(customerIDMap.get(selectedAppointment.getAppointmentCustomerID()));
            } catch(SQLException e) {
                displayAlert(20);
            }

            ObservableList<Contact> contacts = ContactDAO.getAllContacts();
            ObservableList<String> contactNames = FXCollections.observableArrayList();
            for (Contact contact : contacts) {
                contactNames.add(contact.getContactName());
                contactNameMap.put(contact.getContactName(), contact.getContactID());
                contactIDMap.put(contact.getContactID(), contact.getContactName());
            }
            Collections.sort(contactNames);
            contactsComboBox.getItems().addAll(contactNames);
            if(selectedAppointment.getAppointmentContactID() != 0){
                contactsComboBox.setValue(contactIDMap.get(selectedAppointment.getAppointmentContactID()));
            }
        }
    }
}
