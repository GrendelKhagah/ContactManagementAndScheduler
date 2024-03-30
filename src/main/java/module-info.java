/**
 * FXML project with SQL DAO interface
 * 
 * 
 */
module space.ketterling.c195_project {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires java.sql;

    opens model to javafx.base, javafx.fxml;
    opens space.ketterling.c195_project to javafx.fxml;
    exports space.ketterling.c195_project;
}
