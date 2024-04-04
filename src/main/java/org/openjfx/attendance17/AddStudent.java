package org.openjfx.attendance17;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class AddStudent implements Serializable {

    @FXML
    //declare the textFields
    TextField nameTextField;
    @FXML
    TextField lastNameTextField;
    @FXML
    TextField dateBirthTextField;
    @FXML
    TextArea diagnosisTextArea;
    @FXML
    private TextField amkaTextField;

    private Stage stage;
    private Scene scene;
    private Parent root;


    public void openMain(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Main.fxml")));
        stage =(Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setTitle("Παρουσίες");
        scene = new Scene(root);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();

    }

    public void openAttendance(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Attendance.fxml")));
        stage =(Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setTitle("Προσθήκη Μαθητή");
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void openDelete(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("DeleteStudent.fxml"));
        stage =(Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setTitle("Διαγραφή Μαθητή");
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void addStudent(ActionEvent e) throws IOException {

        String name = nameTextField.getText();
        String lastname = lastNameTextField.getText();
        String birthday = dateBirthTextField.getText();
        String diagnosis = diagnosisTextArea.getText();
        String amka = amkaTextField.getText();

        Students student = new Students(name, lastname, birthday, amka,diagnosis);

        ArrayList<Students> studentsList = new ArrayList<>();
        studentsList.add(student);

        FileManager fileManager = new FileManager();

        fileManager.appendListToFile(studentsList, fileManager.getStudentsFile());

        AttendanceFileManager attendanceManager = new AttendanceFileManager();
        ArrayList<StudentSession> studentSessions = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            studentSessions.add(new StudentSession("-", "-", "-"));
        }

        attendanceManager.writeListToFile(studentSessions, amka+".dat");

        nameTextField.clear();
        lastNameTextField.clear();
        dateBirthTextField.clear();
        diagnosisTextArea.clear();
        amkaTextField.clear();

    }
}

