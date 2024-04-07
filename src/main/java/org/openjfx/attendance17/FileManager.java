package org.openjfx.attendance17;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileManager {

    private ArrayList<String> files;
    private final String studentsFile;

    public FileManager(){

        files = new ArrayList<>();
        studentsFile = "students.dat";
        files.add(studentsFile);

    }

    public FileManager(String studentsFile){
        this.studentsFile = studentsFile;
    }

    public String getStudentsFile(){
        return studentsFile;
    }

    //Checks if the filename exists
    public boolean filenameExists(String filename) {
        for (String file : files) {
            if (file.equals(filename)) {
                return true;
            }
        }
        return false;
    }

    //Writes the given list to the file
    public void writeListToFile(List list, String filename) {
        if (filenameExists(filename)) {
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
                oos.writeObject(list);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void appendListToFile(List list, String filename) {
        List existingList = readListFromFile(filename);

        if (existingList != null) {
            existingList.addAll(list);

            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
                oos.writeObject(existingList);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private List readListFromFile(String filename) {
        if (filenameExists(filename)) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
                return (List) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.out.println(e.getMessage());
            }
        }
        return new ArrayList();  // Return an empty list if the file doesn't exist or an error occurs
    }

    public ArrayList<Students> readAdminsFromFile(String filename) {
        if (filenameExists(filename)) {
            try (ObjectInputStream oos = new ObjectInputStream(new FileInputStream(filename))) {
                return (ArrayList<Students>) oos.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.out.println(e.getMessage());;
            }
        }
        else
            System.out.println("No such filename");
        return null;
    }

    //save data of the students
    public void saveData(ArrayList<Students> students){
        writeListToFile(students, studentsFile);
    }

    public void deleteStudentFromFile(Students student) {
        List<Students> studentsList = readStudentsFromFile();

        if (studentsList != null) {
            System.out.println("Students list read from file: " + studentsList); // Debugging message
            if (studentsList.remove(student)) {
                try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(studentsFile))) {
                    oos.writeObject(studentsList);
                    System.out.println("Student removed: " + student.getName()); // Debugging message
                } catch (IOException e) {
                    System.err.println("Error writing to file: " + e.getMessage());
                }
            } else {
                System.err.println("Student not found in the list: " + student.getName()); // Debugging message
            }
        } else {
            System.err.println("Error reading students list from file"); // Debugging message
        }
    }

    private List<Students> readStudentsFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(studentsFile))) {
            return (ArrayList<Students>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error reading from file: " + e.getMessage());
        }
        return null;
    }

    public void updateDiagnosis(String amka, String newDiagnosis) {
        // Read the list of students from file
        List<Students> studentsList = readStudentsFromFile();

        if (studentsList != null) {
            // Find the student with the specified AMKA
            for (Students student : studentsList) {
                if (student.getAmka().equals(amka)) {
                    // Update the diagnosis
                    student.setDiagnosis(newDiagnosis);
                    // Save the updated list back to the file
                    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(studentsFile))) {
                        oos.writeObject(studentsList);
                        System.out.println("Diagnosis updated for student with AMKA: " + amka);
                        return; // Exit the method after updating the diagnosis
                    } catch (IOException e) {
                        System.err.println("Error writing to file: " + e.getMessage());
                        return; // Exit the method if an error occurs
                    }
                }
            }
            System.err.println("Student with AMKA " + amka + " not found."); // Debugging message
        } else {
            System.err.println("Error reading students list from file"); // Debugging message
        }
    }

    public void updatePayment(String amka, String newPayment) {
        // Read the list of students from file
        List<Students> studentsList = readStudentsFromFile();

        if (studentsList != null) {
            // Find the student with the specified AMKA
            for (Students student : studentsList) {
                if (student.getAmka().equals(amka)) {
                    // Update the diagnosis
                    student.setPayment(newPayment);
                    // Save the updated list back to the file
                    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(studentsFile))) {
                        oos.writeObject(studentsList);
                        System.out.println("Diagnosis updated for student with AMKA: " + amka);
                        return; // Exit the method after updating the diagnosis
                    } catch (IOException e) {
                        System.err.println("Error writing to file: " + e.getMessage());
                        return; // Exit the method if an error occurs
                    }
                }
            }
            System.err.println("Student with AMKA " + amka + " not found."); // Debugging message
        } else {
            System.err.println("Error reading students list from file"); // Debugging message
        }
    }

}





