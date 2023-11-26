module com.example.barcode {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires layout;
    requires kernel;
    requires itextpdf;
    requires io;
    requires java.desktop;


    opens com.example.barcode to javafx.fxml;
    exports com.example.barcode;
}