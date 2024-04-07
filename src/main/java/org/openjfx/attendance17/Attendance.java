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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

public class Attendance implements Serializable {
    @FXML
    static Stage stage;
    private Scene scene;
    private Parent root;

    public void openMain(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Παρουσίες");
        scene = new Scene(root);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();

    }

    public void openAdd(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("AddStudent.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Προσθήκη Μαθητή");
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void openDelete(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("DeleteStudent.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Διαγραφή Μαθητή");
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    private TableView<Students> attendanceTable;

    @FXML
    private TableColumn<Students, String> birthdayCol;

    @FXML
    private TableColumn<Students, String> lastNameCol;

    @FXML
    private TableColumn<Students, String> nameCol;

    @FXML
    private TableColumn<Students, String> amkaCol;

    @FXML
    private TableColumn<Students, String> sessionCol;

    @FXML
    private TableColumn<Students, String> expDateCol;

    @FXML
    private TableColumn<Students, String> paymentCol;

    // Load data into the TableView during initialization or when needed
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

            // Configure the existing columns to display the appropriate data
            nameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
            lastNameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLastName()));
            birthdayCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBirthday()));
            amkaCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAmka()));
            sessionCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSession()));
            expDateCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getExpDate()));
            paymentCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPayment()));

        } else {
            // Display an alert or message indicating that no data is available
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("No data available.");
            alert.showAndWait();
        }

        attendanceTable.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                Students selectedStudent = attendanceTable.getSelectionModel().getSelectedItem();
                if (selectedStudent != null){
                    try {
                        openAttendanceView();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

    }

    public void openAttendanceView() throws IOException {

        Students selectedStudent = attendanceTable.getSelectionModel().getSelectedItem();

        if (selectedStudent != null){
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AttendanceView.fxml"));
            Parent root = loader.load();

            // Get the controller instance from the FXMLLoader
            AttendanceView controller = loader.getController();

            // Display the student details using the controller
            controller.displayStudentDetails(selectedStudent);
            controller.initialize(selectedStudent.getAmka());


            // Create a new stage
            Stage newStage = new Stage();
            newStage.setTitle("Καρτέλα Μαθητή");
            newStage.setResizable(false);

            // Set modality to keep new window above the main window
            newStage.initModality(Modality.WINDOW_MODAL);
            newStage.initOwner(stage);

            // Set the scene with the loaded FXML content
            Scene scene = new Scene(root);
            newStage.setScene(scene);

            // Show the new stage
            newStage.show();

        }

    }
}
