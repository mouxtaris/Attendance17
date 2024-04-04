package org.openjfx.attendance17;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.IOException;

public class ControllerMain {
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void switchToAttendance(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Attendance.fxml"));
        stage =(Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setTitle("Προσθήκη Παρουσίας");
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToAddStudent(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("AddStudent.fxml"));
        stage =(Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setTitle("Προσθήκη Μαθητή");
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToDeleteStudent(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("DeleteStudent.fxml"));
        stage =(Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setTitle("Διαγραφή Μαθητή");
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
