module com.mycompany.electronic_kostprogram {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.mycompany.electronic_kostprogram to javafx.fxml;
    exports com.mycompany.electronic_kostprogram;
}
