package space.ketterling.c195_project;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import DAO.UserDAO;
import helper.JDBC;
import helper.LoginActivityLogger;
import java.util.Locale;
import javafx.collections.FXCollections;
import javafx.scene.control.ChoiceBox;
import model.SessionManager;

/**
 * FXML Controller class for the login form, handles credential validation and session setup
 *
 * @author Taylor Ketterling
 */
public class loginformController extends BaseController {

    @FXML
    private TextField userNameField;
    @FXML
    private TextField passwordField;
    @FXML
    private Label errorLabel;
    @FXML
    private Label localLabel;
    @FXML
    private ChoiceBox<String> languageComboBox;
    
    /**
     * checks if data is valid, then checks if credentials are valid
     * 
     * If data is invalid, prompts user
     * if Credentials are invalid, prompts user and clears text fields
     * 
     * If successful, will send user to Customers page and start their session
     *
     * @param event         logs user in
     */
    @FXML
    void loginButtonAction(ActionEvent event) {
        try {
            String Name = userNameField.getText();
            String Password = passwordField.getText();
            
            if (ValidData(Name, Password)) {
                if(UserDAO.validateLogin(Name, Password)){  //attempt login 
                    errorLabel.setText("Login successful.");
                    LoginActivityLogger.logLoginAttempt(Name, true);
                    SessionManager.getInstance().setCurrentLanguage(languageComboBox.getValue());
                    switchScene(event, "Customers");
                } else {
                    //displayAlert(2); //Decided to go with on screen errors instead of popups
                    LoginActivityLogger.logLoginAttempt(Name, false);
                    errorLabel.setText("Login failed. Please try again.");
                    passwordField.setText("");
                    userNameField.setText("");
                }  
            } else {
                    //displayAlert(1); //Decided to go with on screen errors instead of popups
                    LoginActivityLogger.logLoginAttempt(Name, false);
                    errorLabel.setText("Missing Data in Field");
            }
            
        } catch(NumberFormatException e) {
            displayAlert(5); // Pop up error for any IO Exceptions
        }
        
    }
    
    /**
     * Validates Data received from the user
     * Checks that Name and Password are not null
     * and that Name and Password and not empty ""
     * 
     * 
     * @param Name           must not be null
     * @param Password       must not be null
     * @return               true of not null
     */
    private boolean ValidData(String Name, String Password) {
        
        if((Name != null && Password != null)&&(!"".equals(Name) && !"".equals(Password))){
            return true;
        } else {
            return false;
        }

    }
    
    /**
     * Initializes the controller class. Checks the systems local language code.
     * If language is available, sets system to that language. defaults to english
     * 
     * will warn if DB can not connect
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Locale userLocale = Locale.getDefault();
        String languageCode = userLocale.getLanguage();
        languageComboBox.setItems(FXCollections.observableArrayList("English", "French"));
        localLabel.setText("System Language Code =  "+languageCode);
        if ("fr".equals(languageCode)) {
            languageComboBox.setValue("French");
        } else {
            languageComboBox.setValue("English");
        }
        
        try{
            JDBC.openConnection();
            errorLabel.setText("");
        } catch (Exception e) {
            errorLabel.setText("Database could not connect");
        }
    }    
    
}
