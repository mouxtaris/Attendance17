package org.openjfx.attendance17;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;

import java.util.ArrayList;
import java.util.List;

public class AttendanceView {

    @FXML
    private TextField amkaTextField;

    @FXML
    private TextField birthdayTextField;

    @FXML
    private TextArea diagnosisTextArea;

    @FXML
    private TextField lastNameTextField;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField payTextField;

    public void displayStudentDetails(Students selectedStudent) {
        if (selectedStudent != null) {
            // Set the name
            nameTextField.setText(selectedStudent.getName());

            // Set the last name
            lastNameTextField.setText(selectedStudent.getLastName());

            // Set the diagnosis
            diagnosisTextArea.setText(selectedStudent.getDiagnosis());

            // Set the AMKA
            amkaTextField.setText(selectedStudent.getAmka());

            birthdayTextField.setText(selectedStudent.getBirthday());

            payTextField.setText(selectedStudent.getPayment());

            nameTextField.setEditable(false);
            lastNameTextField.setEditable(false);
            diagnosisTextArea.setEditable(true);
            amkaTextField.setEditable(false);
            birthdayTextField.setEditable(false);
            payTextField.setEditable(true);

        }
    }


    @FXML
    private TableView<StudentSession> attendanceViewTable;

    @FXML
    private TableColumn<StudentSession, String> ergColumn;

    @FXML
    private TableColumn<StudentSession, String> logColumn;

    @FXML
    private TableColumn<StudentSession, String> psyColumn;

    @FXML
    public void initialize(String amka) {
        loadAttendanceIntoTableView(amka);
    }

    private void loadAttendanceIntoTableView(String amka) {
        AttendanceFileManager fileManager = new AttendanceFileManager(amka);
        List<StudentSession> sessionList = fileManager.readAdminsFromFile(amka + ".dat");

        if (sessionList != null && !sessionList.isEmpty()) {

            ObservableList<StudentSession> observableList = FXCollections.observableArrayList(sessionList);

            // Make each TableColumn editable
            logColumn.setCellFactory(TextFieldTableCell.forTableColumn());
            ergColumn.setCellFactory(TextFieldTableCell.forTableColumn());
            psyColumn.setCellFactory(TextFieldTableCell.forTableColumn());

            logColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLogotherapy()));
            ergColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getErgotherapy()));
            psyColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPsychotherapy()));

            logColumn.setOnEditCommit((TableColumn.CellEditEvent<StudentSession, String> t) -> {
                // Update the value in the data model
                t.getTableView().getItems().get(t.getTablePosition().getRow()).setLogotherapy(t.getNewValue());
                attendanceViewTable.refresh(); // Refresh the table to reflect changes

                // Get the row and column indexes of the current cell
                int rowIndex = t.getTablePosition().getRow();
                int colIndex = t.getTablePosition().getColumn();

                // Move to the next row in the same column
                if (rowIndex + 1 < attendanceViewTable.getItems().size()) {
                    // Increment row index to move to the next row
                    rowIndex++;
                    // Select the next cell in the same column
                    attendanceViewTable.getSelectionModel().clearAndSelect(rowIndex, logColumn);
                    // Start editing the selected cell
                    attendanceViewTable.edit(rowIndex, logColumn);
                }
            });

            ergColumn.setOnEditCommit((TableColumn.CellEditEvent<StudentSession, String> t) -> {
                // Update the value in the data model
                ((StudentSession) t.getTableView().getItems().get(t.getTablePosition().getRow())).setErgotherapy(t.getNewValue());
                attendanceViewTable.refresh(); // Refresh the table to reflect changes

                // Get the row and column indexes of the current cell
                int rowIndex = t.getTablePosition().getRow();
                int colIndex = t.getTablePosition().getColumn();

                // Move to the next row in the same column
                if (rowIndex + 1 < attendanceViewTable.getItems().size()) {
                    // Increment row index to move to the next row
                    rowIndex++;
                    // Select the next cell in the same column
                    attendanceViewTable.getSelectionModel().clearAndSelect(rowIndex, ergColumn);
                    // Start editing the selected cell
                    attendanceViewTable.edit(rowIndex, ergColumn);
                }
            });

            psyColumn.setOnEditCommit((TableColumn.CellEditEvent<StudentSession, String> t) -> {
                // Update the value in the data model
                ((StudentSession) t.getTableView().getItems().get(t.getTablePosition().getRow())).setPsychotherapy(t.getNewValue());
                attendanceViewTable.refresh(); // Refresh the table to reflect changes

                // Get the row and column indexes of the current cell
                int rowIndex = t.getTablePosition().getRow();
                int colIndex = t.getTablePosition().getColumn();

                // Move to the next row in the same column
                if (rowIndex + 1 < attendanceViewTable.getItems().size()) {
                    // Increment row index to move to the next row
                    rowIndex++;
                    // Select the next cell in the same column
                    attendanceViewTable.getSelectionModel().clearAndSelect(rowIndex, psyColumn);
                    // Start editing the selected cell
                    attendanceViewTable.edit(rowIndex, psyColumn);
                }
            });

            attendanceViewTable.setItems(observableList);

        } else {
            // Display an alert or message indicating that no data is available
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("No data available.");
            alert.showAndWait();
        }
    }

    @FXML
    public void saveData() {

        AttendanceFileManager filemanager = new AttendanceFileManager();

        ObservableList<StudentSession> dataList = attendanceViewTable.getItems();

        ArrayList<StudentSession> sessionList = new ArrayList<>(dataList);

        filemanager.emptyFile(amkaTextField.getText());
        filemanager.appendListToFile(dataList, amkaTextField.getText());
        loadAttendanceIntoTableView(amkaTextField.getText());

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText("Οι παρουσίες αποθηκεύτηκαν");
        alert.showAndWait();

    }

    @FXML
    public void updateDiagnosis(){

        FileManager fileManager = new FileManager();
        fileManager.updateDiagnosis(amkaTextField.getText(), diagnosisTextArea.getText());
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText("Η διάγνωση αποθηκεύτηκε!");
        alert.showAndWait();

    }

    @FXML
    public void updatePayment(){
        FileManager fileManager = new FileManager();
        fileManager.updatePayment(amkaTextField.getText(), payTextField.getText());
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText("Η πληρωμή αποθηκεύτηκε!");
        alert.showAndWait();
    }

}

