module org.openjfx.attendance {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.openjfx.attendance17 to javafx.fxml;
    exports org.openjfx.attendance17;
}