package org.openjfx.attendance17;

import java.io.Serializable;
import java.util.Objects;

public class Students extends AddStudent implements Serializable {

    private String name;
    private String lastName;
    private String birthday;
    private String diagnosis;
    private String amka;
    private String session;
    private String expDate;
    private String payment;



    public Students(String name, String lastName, String birthday, String amka, String session, String expDate, String payment, String diagnosis) {
        this.name = name;
        this.lastName = lastName;
        this.birthday = birthday;
        this.amka = amka;
        this.diagnosis = diagnosis;
        this.session = session;
        this.expDate = expDate;
        this.payment = payment;
    }

    public Students() {
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getAmka(){return amka;}

    public String getSession(){return session;}

    public void setSession(String session){this.session = session;}

    public String getExpDate() {return expDate;}

    public String getPayment() {return payment;}

    public void setPayment(String payment){this.payment = payment;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Students students = (Students) o;
        return Objects.equals(name, students.name) &&
                Objects.equals(lastName, students.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, lastName);
    }

}

