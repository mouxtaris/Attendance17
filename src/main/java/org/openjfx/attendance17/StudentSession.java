package org.openjfx.attendance17;

import java.io.Serializable;
import java.util.Objects;

public class StudentSession extends AddStudent implements Serializable {

    private String logotherapy;
    private String ergotherapy;
    private String psychotherapy;

    public StudentSession(String logotherapy, String ergotherapy, String psychotherapy){
        this.logotherapy = logotherapy;
        this.ergotherapy = ergotherapy;
        this.psychotherapy = psychotherapy;
    }

    public String getLogotherapy(){return logotherapy;}

    public String getErgotherapy(){return ergotherapy;}

    public String getPsychotherapy(){return psychotherapy;}

    public void setLogotherapy(String logotherapy) {
        this.logotherapy = logotherapy;
    }

    public void setErgotherapy(String ergotherapy) {
        this.ergotherapy = ergotherapy;
    }

    public void setPsychotherapy(String psychotherapy) {
        this.psychotherapy = psychotherapy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentSession students = (StudentSession) o;
        return Objects.equals(logotherapy, students.logotherapy) &&
                Objects.equals(ergotherapy, students.ergotherapy) &&
                Objects.equals(psychotherapy, students.psychotherapy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(logotherapy, ergotherapy, psychotherapy);
    }

}

