package space.ketterling.c195_project;

import DAO.AppointmentDAO;
import DAO.CustomerDAO;
import DAO.ContactDAO;
import model.Contact;
import model.Appointment;
import model.Customer;
import helper.util;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.DatePicker;
import model.SessionManager;
import java.time.ZoneId;
import java.time.LocalTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import java.util.stream.IntStream;
import java.util.stream.Collectors;

/**
 * FXML Controller class
 *
 * @author Taylor Ketterling
 */
public class AddAppointmentController extends BaseController {

    
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
    
    //Map allows selections to be mapped to IDs
    private Map<String, Integer> customerNameMap = new HashMap<>();
    private Map<String, Integer> contactNameMap = new HashMap<>();
    
    /**
     * Handles switching scene to the Appointments Page
     * 
     * @param event Sends user Back
     */
    @FXML
    void BackButtonAction(ActionEvent event) {
        switchScene(event, "Appointments"); 
    }
    
    /**
     * action event for when add customer button is pressed
     * will take contents of fields and attempt to add a customer
     * 
     * 
     * @param event 
     */
    @FXML
    void addAppointmentButtonAction(ActionEvent event) {
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
            return;           // Stops execution if any field is empty
        }
       
        //ZonedDateTime processing happens down here to avoid processing a null value
        ZonedDateTime startDateTime = getZonedDateTimeFromPickerAndChoices(startDatePicker, startChoiceBox, startMinChoiceBox);
        ZonedDateTime endDateTime = getZonedDateTimeFromPickerAndChoices(endDatePicker, endChoiceBox, endMinChoiceBox);
        
        String createdBy = SessionManager.getInstance().getActiveUser().getUserName();
        int customerID = customerNameMap.get(customer);
        int userID = SessionManager.getInstance().getActiveUser().getUserID();
        int contactID = contactNameMap.get(contactsComboBox.getValue());

        //Ensure StartDate and endDate fall within accepable business hours and customer availability
        if (!businessLogicChecksPassed(startDateTime, endDateTime, customerID)) {
            displayAlert(17);
            return;
        }
        
        Appointment appointment = new Appointment(
                0,
                Title,
                Description,
                Location,
                Type,
                startDateTime,
                endDateTime,
                ZonedDateTime.now(),
                createdBy,
                customerID,
                userID,
                contactID);

        try {
            AppointmentDAO.addAppointment(appointment);
            displayAlert(21); // success alert
            switchScene(event, "Appointments");
          
        } catch (SQLException e) {
            displayAlert(24);
            e.printStackTrace();
        }
    }
    
    /**
     * Checks to ensure startdate and enddate are within business hours and customer availability
     * 
     * returns true if pass
     * 
     * @param startDate ZonedDateTime of start date
     * @param endDate   ZonedDateTime of end date
     * @param customerID ID of customer to be checked
     * @return  true if pass conditions
     */
    private boolean businessLogicChecksPassed(ZonedDateTime startDate, ZonedDateTime endDate, int customerID){
        if(!util.isWithinBusinessHours(startDate, endDate)){return false;}
        else if(checkForOverlap(startDate, endDate, customerID)) {return false;}
        
        return true;
    }
    
    /**
     * Checks for overlaps in customers schedule with proposed timeframe
     * returns true when overlap is detected.
     * Will Display an Alert That Overlap was detected
     * 
     * This add method and the update method are slightly different so not consolidating
     * (Update ignore's appointment being updated as conflict)
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
                if (startDate.isBefore(existingEnd) && endDate.isAfter(existingStart)) {
                    displayAlert(32);
                    return true; // Overlap was detected, return true
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            displayAlert(19);
        }
        return false; // No overlap detected, return false
    }
    
    /**
     * Converts Local Times to ZonedDateTimes
     * 
     * returns null if date hour and minute not provided
     * 
     * @param datePicker
     * @param hourChoiceBox
     * @param minuteChoiceBox
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
     * Initializes the controller class.
     * 
     * Gets the time and date now, and fills in date and time fields with current time
     * Fills end date and time fields with time now +15 minutes
     * 
     * Fetches customer list and contacts list
     * endTime gets + 15 minutes from now
     * 
     * times are localized to user
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        //set the date picker to today for convience
        startDatePicker.setValue(LocalDate.now());
        endDatePicker.setValue(LocalDate.now());
        
        startChoiceBox.getItems().addAll(IntStream.rangeClosed(0, 23).boxed().collect(Collectors.toList()));
        startMinChoiceBox.getItems().addAll(IntStream.rangeClosed(0, 59).boxed().collect(Collectors.toList()));
        
        endChoiceBox.getItems().addAll(IntStream.rangeClosed(0, 23).boxed().collect(Collectors.toList()));
        endMinChoiceBox.getItems().addAll(IntStream.rangeClosed(0, 59).boxed().collect(Collectors.toList()));
        
        // Extract hours and minutes from current time
        LocalTime now = LocalTime.now();
        startChoiceBox.setValue(now.getHour());
        startMinChoiceBox.setValue(now.getMinute());
        
        // Adjust the end time to be now +15 minutes for convienence
        endChoiceBox.setValue(now.plusMinutes(15).getHour());
        endMinChoiceBox.setValue(now.plusMinutes(15).getMinute());

        try {
            ObservableList<Customer> customers = CustomerDAO.getAllCustomers();
            ObservableList<String> customerNames = FXCollections.observableArrayList();
            for (Customer customer : customers) {
                customerNames.add(customer.getCustomerName());
                customerNameMap.put(customer.getCustomerName(), customer.getCustomerID());
            }
            Collections.sort(customerNames);
            customersComboBox.getItems().addAll(customerNames);
            //System.out.println(customerNameMap); //debugging 
        } catch(SQLException e) {
            displayAlert(20);
        }
        
        ObservableList<Contact> contacts = ContactDAO.getAllContacts();
        ObservableList<String> contactNames = FXCollections.observableArrayList();
        for (Contact contact : contacts) {
            contactNames.add(contact.getContactName());
            contactNameMap.put(contact.getContactName(), contact.getContactID());
        }
        Collections.sort(contactNames);
        contactsComboBox.getItems().addAll(contactNames);
    }    
}
