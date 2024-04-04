package org.openjfx.attendance17;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class AttendanceFileManager{

    private ArrayList<String> files;
    private String studentSessionFile;

    public AttendanceFileManager(String selectedStudent){
        files = new ArrayList<>();
        studentSessionFile =  selectedStudent + ".dat";
        files.add(studentSessionFile);
    }

    public AttendanceFileManager(){
        this.studentSessionFile = studentSessionFile;
        this.files = new ArrayList<>();
    }

    public String getStudentSessionFile(){return studentSessionFile;}

    //check if filename exists
    public boolean filenameExists(String filename) {
        for (String file : files) {
            if (file.equals(filename)) {
                return true;
            }
        }
        return false;
    }

    public void writeListToFile(List list, String filename){

        if (!filenameExists(filename)){
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
                oos.writeObject(list);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void appendListToFile(List list, String filename) {
        List existingList = readListFromFile(filename + ".dat");

        if (existingList != null) {
            existingList.addAll(list);

            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename + ".dat"))) {
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

    public ArrayList<StudentSession> readAdminsFromFile(String filename) {
        if (filenameExists(filename)) {
            try (ObjectInputStream oos = new ObjectInputStream(new FileInputStream(filename))) {
                return (ArrayList<StudentSession>) oos.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.out.println(e.getMessage());;
            }
        }
        else
            System.out.println("No such filename");
        return null;
    }

    public void saveData(ArrayList<StudentSession> students,String filename){
        appendListToFile(students, studentSessionFile);
    }

    // Method to empty the data in the file
    public void emptyFile(String filename) {
        try {
            Files.write(Paths.get(filename+ ".dat"), new byte[0]); // Write an empty byte array to the file
            System.out.println("File emptied successfully: " + filename + ".dat");
        } catch (IOException e) {
            System.out.println("Failed to empty the file: " + e.getMessage());
        }
    }

    public void deleteAmkaFile(String filename){

        File file = new File(filename + ".dat");

        // Check if the file exists before attempting to delete
        if (file.exists()) {
            try {
                // Attempt to delete the file
                boolean isDeleted = file.delete();

                if (isDeleted) {
                    System.out.println("File deleted successfully.");
                } else {
                    System.out.println("Failed to delete the file.");
                }
            } catch (SecurityException e) {
                System.out.println("Security Exception: Unable to delete the file due to insufficient permissions.");
            } catch (Exception e) {
                System.out.println("An error occurred while deleting the file: " + e.getMessage());
            }
        } else {
            System.out.println("File does not exist.");
        }
    }

}
