package org.openjfx.attendance17;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;


import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public class DeleteStudent implements Serializable {
    @FXML

    private Stage stage;
    private Scene scene;
    private Parent root;


    public void openMain(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
        stage =(Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Παρουσίες");
        scene = new Scene(root);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();

    }

    public void openAttendance(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Attendance.fxml"));
        stage =(Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setTitle("Προσθήκη Μαθητή");
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void openAdd(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("AddStudent.fxml"));
        stage =(Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setTitle("Διαγραφή Μαθητή");
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private TableView<Students> attendanceTable;

    @FXML
    private TableColumn<Students, String> nameCol;

    @FXML
    private TableColumn<Students, String> lastNameCol;

    @FXML
    private TableColumn<Students, String> birthdayCol;

    @FXML
    private TableColumn<Students, String> amkaCol;

    @FXML
    private void initialize() {
        // Call a method to load data into the TableView
        loadDataIntoTableView();
    }

    // Method to load data into the TableView
    private void loadDataIntoTableView() {
        FileManager fileManager = new FileManager();
        List<Students> studentsList = fileManager.readAdminsFromFile(fileManager.getStudentsFile());

        if (studentsList != null && !studentsList.isEmpty()) {
            // Convert the list to an ObservableList
            ObservableList<Students> observableList = FXCollections.observableArrayList(studentsList);

            // Bind the ObservableList to the TableView
            attendanceTable.setItems(observableList);

            // Configure the columns to display the appropriate data
            nameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
            lastNameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLastName()));
            birthdayCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBirthday()));
            amkaCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAmka()));
        }
        else {
            // Display an alert or message indicating that no data is available
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("No data available.");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleDeleteButton() {
        // Get the selected student
        Students selectedStudent = attendanceTable.getSelectionModel().getSelectedItem();
        String amka = selectedStudent.getAmka();

        if (selectedStudent != null) {
            // Display a confirmation dialog
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Επιβεβαίωση");
            alert.setHeaderText("Διαγραφή Μαθητή");
            alert.setContentText("Τα δεδομένα θα διαγραφούν μόνιμα από τον υπολογιστή");

            Optional<ButtonType> result = alert.showAndWait();

            // If the user confirms deletion, proceed
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // Delete the selected student from the file
                FileManager fileManager = new FileManager();
                fileManager.deleteStudentFromFile(selectedStudent);

                AttendanceFileManager attendanceFile = new AttendanceFileManager(amka);
                attendanceFile.deleteAmkaFile(amka);

                // Reload data into the TableView after deletion
                loadDataIntoTableView();
            }
        } else {
            // If no student is selected, display an alert
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Student Selected");
            alert.setContentText("Please select a student to delete.");
            alert.showAndWait();
        }
    }

}
