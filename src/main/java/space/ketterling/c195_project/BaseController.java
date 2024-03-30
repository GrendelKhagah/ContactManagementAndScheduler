package space.ketterling.c195_project;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import model.SessionManager;

/**
 * FXML Controller abstract class
 * adding a base controller to consolidate lots of re-used code
 * 
 * leaves other classes to implement initialize method
 * 
 * @author Taylor
 */
public abstract class BaseController implements Initializable{
    
    /**
     * Switches to various scenes, handles nationalization resource bundle
     * Checks to ensure there is a user logged in, otherwise redirects to login page
     * 
     * @param event            event being passed
     * @param fxmlFile         String of fxmlFile Destination without the .fxml
     */
    protected void switchScene(ActionEvent event, String fxmlFile) {
        if (null == SessionManager.getInstance().getActiveUser()){ //checks to ensure a user is logged in
            SessionManager.getInstance().logOut(); //double log out in case no user is logged in
            fxmlFile = "loginform";   // send user to login page to log in
        }
        try {
            ResourceBundle bundle = ResourceBundle.getBundle(fxmlFile, new Locale(SessionManager.getInstance().getCurrentLanguage()));
            Parent parent = FXMLLoader.load(getClass().getResource(fxmlFile+".fxml"), bundle);
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {                
            displayAlert(6);    // Handle IOException 
            e.printStackTrace();        
        } catch (Exception e) {
            displayAlert(7);    // Handle other unexpected exceptions such as bad address
        }
    }
    
    /**
     * Takes an alert case # and spits out a pop up alert
     * Handles Alerts and Error alerts
     * 
     * @param alertCase int alertCase
     */
    protected void displayAlert(int alertCase) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION); // soft alerts
        Alert alertError = new Alert(Alert.AlertType.ERROR);  // error alerts

        if("fr".equals(SessionManager.getInstance().getCurrentLanguage())){
            switch (alertCase) {
            case 1:
                alert.setTitle("Information");
                alert.setHeaderText("Données d'entrée manquantes, veuillez compléter le formulaire");
                alert.showAndWait();
                break;
            case 2:
                alert.setTitle("Information");
                alert.setHeaderText("Le nom d'utilisateur ou le mot de passe est incorrect");
                alert.showAndWait();
                break;
            case 3:
                alert.setTitle("Succès");
                alert.setHeaderText("Client ajouté à la base de données");
                alert.showAndWait();
                break;
            case 4:
                alert.setTitle("Problème");
                alert.setHeaderText("Problème lors de l'ajout du client à la base de données");
                alert.showAndWait();
                break;
            case 5:
                alertError.setTitle("Problème de connexion");
                alertError.setHeaderText("problème, exception IO");
                alertError.showAndWait();
                break;
            case 6:
                alertError.setTitle("IOException");
                alertError.setHeaderText("Problème avec IO");
                alertError.showAndWait();
                break;
            case 7:
                alertError.setTitle("Exception");
                alertError.setHeaderText("adresse probablement mauvaise");
                alertError.showAndWait();
                break;
            case 8:
                alert.setTitle("Problème");
                alert.setHeaderText("Champ vide dans la soumission");
                alert.showAndWait();
                break;
            case 9:
                alert.setTitle("Succès");
                alert.setHeaderText("Client mis à jour dans la base de données");
                alert.showAndWait();
                break;
            case 10:
                alert.setTitle("Problème");
                alert.setHeaderText("Problème de mise à jour du client dans la base de données");
                alert.showAndWait();
                break;
            case 11:
                alert.setTitle("Alerte");
                alert.setHeaderText("Client supprimé avec succès");
                alert.showAndWait();
                break;
            case 12:
                alertError.setTitle("Erreur");
                alertError.setHeaderText("Erreur lors de la suppression du client");
                alertError.showAndWait();
                break;
            case 13:
                alertError.setTitle("Erreur");
                alertError.setHeaderText("Impossible de charger les clients");
                alertError.showAndWait();
                break;
            case 14:
                alert.setTitle("Problème");
                alert.setHeaderText("Aucun client sélectionné");
                alert.showAndWait();
                break;
            case 15:
                alert.setTitle("Suppression impossible");
                alert.setHeaderText("Le client a des rendez-vous associés, suppression impossible");
                alert.showAndWait();
                break;
            case 16:
                alert.setTitle("Problème");
                alert.setHeaderText("Aucun rendez-vous sélectionné");
                alert.showAndWait();
                break;
            case 17:
                alert.setTitle("Problème");
                alert.setHeaderText("Problème avec les heures sélectionnées et soit les heures d'ouverture ou les conflits de disponibilité.");
                alert.showAndWait();
                break;
            case 18:
                alert.setTitle("Problème");
                alert.setHeaderText("Problème de chevauchement de disponibilité avec le client, impossible de planifier cette réunion.");
                alert.showAndWait();
                break;
            case 19:
                alertError.setTitle("Erreur");
                alertError.setHeaderText("Erreur lors de la vérification de la disponibilité du client pour la réunion");
                alertError.showAndWait();
                break;
            case 20:
                alertError.setTitle("Erreur");
                alertError.setHeaderText("Erreur de chargement des clients");
                alertError.showAndWait();
                break;
            case 21:
                alert.setTitle("Succès");
                alert.setHeaderText("Rendez-vous pris");
                alert.showAndWait();
                break;
            case 22:
                alert.setTitle("Succès");
                alert.setHeaderText("Rendez-vous supprimé avec succès");
                alert.showAndWait();
                break;
            case 23:
                alert.setTitle("Erreur");
                alert.setHeaderText("Problème de suppression du rendez-vous");
                alert.showAndWait();
                break;
            case 24:
                alert.setTitle("Problème");
                alert.setHeaderText("Problème d'ajout du rendez-vous à la base de données");
                alert.showAndWait();
                break;
            case 25:
                alertError.setTitle("Erreur");
                alertError.setHeaderText("Erreur de mise à jour de l'affichage de la table");
                alertError.showAndWait();
                break;
            case 26:
                alertError.setTitle("Exception SQL");
                alertError.setHeaderText("Problème avec SQL");
                alertError.showAndWait();
                break;
            case 27:
                alertError.setTitle("Erreur");
                alertError.setHeaderText("Erreur de chargement des contacts");
                alertError.showAndWait();
                break;
            case 28:
                alertError.setTitle("Suppression du client impossible");
                alertError.setHeaderText("Le client a des rendez-vous");
                alertError.showAndWait();
                break;
            case 29:
                alert.setTitle("Succès");
                alert.setHeaderText("Carte de contact créée");
                alert.showAndWait();
                break;
            case 30:
                alertError.setTitle("erreur");
                alertError.setHeaderText("Erreur de chargement des rendez-vous");
                alertError.showAndWait();
                break;
            case 31:
                alert.setTitle("Succès");
                alert.setHeaderText("Rendez-vous mis à jour");
                alert.showAndWait();
                break;
            case 32:
                alert.setTitle("Problème");
                alert.setHeaderText("Chevauchement de rendez-vous détecté, impossible de procéder");
                alert.showAndWait();
                break;
            default:
                alertError.setTitle("Erreur");
                alertError.setHeaderText("Une erreur inconnue s'est produite");
                alertError.showAndWait();
            }
        } else {
            switch (alertCase) {
            case 1:
                alert.setTitle("Information");
                alert.setHeaderText("Missing input data, please complete form");
                alert.showAndWait();
                break;
            case 2:
                alert.setTitle("Information");
                alert.setHeaderText("Username or Password was incorrect");
                alert.showAndWait();
                break;
            case 3:
                alert.setTitle("Success");
                alert.setHeaderText("Customer Added to Database");
                alert.showAndWait();
                break;
            case 4:
                alert.setTitle("Issue");
                alert.setHeaderText("Issue adding Customer to Database");
                alert.showAndWait();
                break;
            case 5:
                alertError.setTitle("Issue logging in ");
                alertError.setHeaderText("issue, IO exception");
                alertError.showAndWait();
                break;
            case 6:
                alertError.setTitle("IOException");
                alertError.setHeaderText("Issue with IO");
                alertError.showAndWait();
                break;
            case 7:
                alertError.setTitle("Exception");
                alertError.setHeaderText("possible bad address");
                alertError.showAndWait();
                break;
            case 8:
                alert.setTitle("Issue");
                alert.setHeaderText("Empty Field in submission");
                alert.showAndWait();
                break;
            case 9:
                alert.setTitle("Success");
                alert.setHeaderText("Customer updated to Database");
                alert.showAndWait();
                break;
            case 10:
                alert.setTitle("Issue");
                alert.setHeaderText("Issue updating Customer to Database");
                alert.showAndWait();
                break;
            case 11:
                alert.setTitle("Alert");
                alert.setHeaderText("Customer Deleted Successfully");
                alert.showAndWait();
                break;
            case 12:
                alertError.setTitle("Error");
                alertError.setHeaderText("Error While Deleting Customer");
                alertError.showAndWait();
                break;
            case 13:
                alertError.setTitle("Error");
                alertError.setHeaderText("Customers Unable to load");
                alertError.showAndWait();
                break;
            case 14:
                alert.setTitle("Issue");
                alert.setHeaderText("Customer not selected");
                alert.showAndWait();
                break;
            case 15:
                alert.setTitle("Cannot Delete");
                alert.setHeaderText("Customer has associated Appointments, Cannot delete");
                alert.showAndWait();
                break;
            case 16:
                alert.setTitle("Issue");
                alert.setHeaderText("Appointment not selected");
                alert.showAndWait();
                break;
            case 17:
                alert.setTitle("Issue");
                alert.setHeaderText("Issue with selected Times and either business hour or availability conficts.");
                alert.showAndWait();
                break;
            case 18:
                alert.setTitle("Issue");
                alert.setHeaderText("Availability overlap issue with customer, cannot schedule this meeting.");
                alert.showAndWait();
                break;
            case 19:
                alertError.setTitle("Error");
                alertError.setHeaderText("Error while checking customer availability for meeting");
                alertError.showAndWait();
                break;
            case 20:
                alertError.setTitle("Error");
                alertError.setHeaderText("Error loading customers");
                alertError.showAndWait();
                break;
            case 21:
                alert.setTitle("Success");
                alert.setHeaderText("Appointment Made");
                alert.showAndWait();
                break;
            case 22:
                alert.setTitle("success");
                alert.setHeaderText("Appointment Deleted Successfully");
                alert.showAndWait();
                break;
            case 23:
                alert.setTitle("Error");
                alert.setHeaderText("Issue Deleting Appointment");
                alert.showAndWait();
                break;
            case 24:
                alert.setTitle("Issue");
                alert.setHeaderText("Issue adding Appointment to Database");
                alert.showAndWait();
                break;
            case 25:
                alertError.setTitle("Error");
                alertError.setHeaderText("Error updating Table View");
                alertError.showAndWait();
                break;
            case 26:
                alertError.setTitle("SQL Exception");
                alertError.setHeaderText("issue with SQL");
                alertError.showAndWait();
                break;
            case 27:
                alertError.setTitle("Error");
                alertError.setHeaderText("Error loading contacts");
                alertError.showAndWait();
                break;
            case 28:
                alertError.setTitle("Cannot delete Customer");
                alertError.setHeaderText("Customer has appointments");
                alertError.showAndWait();
                break;
            case 29:
                alert.setTitle("Success");
                alert.setHeaderText("Contact Card Created");
                alert.showAndWait();
                break;
            case 30:
                alertError.setTitle("error");
                alertError.setHeaderText("error loading Appointments");
                alertError.showAndWait();
                break;
            case 31:
                alert.setTitle("success");
                alert.setHeaderText("Appointment Updated");
                alert.showAndWait();
                break;
            case 32:
                alert.setTitle("Issue");
                alert.setHeaderText("Appointment Overlap Detected, cannot proceed");
                alert.showAndWait();
                break;
            default:
                alertError.setTitle("Error");
                alertError.setHeaderText("Unknown Error has occured");
                alertError.showAndWait();
            }
        }
    }
}
