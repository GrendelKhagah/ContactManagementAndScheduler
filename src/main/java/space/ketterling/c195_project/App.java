package space.ketterling.c195_project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.ResourceBundle;
import java.io.IOException;
import java.util.Locale;
import helper.JDBC;
import java.sql.SQLException;
/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("loginform"));
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }
    
    /**
     * Loads an FXML file and its associated ResourceBundle for internationalization.
     * This method is utilized to load FXML files for different stages or scenes, 
     * ensuring that each UI component is localized according to the user's language settings.
     *
     * @param fxml The base name of the FXML file to load, without the '.fxml' extension.
     *             This name also corresponds to the ResourceBundle's base name.
     * @return The Parent object loaded from the specified FXML file, with localized text.
     * @throws IOException If the FXML file or ResourceBundle cannot be found, or if an I/O error occurs.
     */
    private static Parent loadFXML(String fxml) throws IOException {
        ResourceBundle bundle = ResourceBundle.getBundle(fxml, Locale.getDefault());
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"), bundle);
        
        
        return fxmlLoader.load();
    }

    public static void main(String[] args) throws SQLException {
        launch();
        JDBC.closeConnection();
    }

}