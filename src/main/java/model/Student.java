package model;

import java.util.ArrayList;
import java.util.List;

public class Student extends model.Person {
    private long studentID;
    private int totalCredits;
    private List<Long> enrolledCourses;
    /**
     * //wir erstellen ein neues Objekt von Typ "Student"
     * @param person ein Objekt von Typ "Person"
     * @param studentID ein "Long"-Zahl
     */
    public Student(Person person, long studentID) {
        super(person.getPersonID(), person.getVorname(), person.getNachname());
        this.studentID = studentID;
        this.totalCredits = 0;
        this.enrolledCourses = new ArrayList<>();
    }

    public Student(Person person, long studentID, int totalCredits, List<Long> enrolledCourses) {
        super(person.getPersonID(), person.getVorname(), person.getNachname());
        this.studentID = studentID;
        this.totalCredits = totalCredits;
        this.enrolledCourses = enrolledCourses;
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentID=" + studentID +
                ", totalCredits=" + totalCredits +
                ", enrolledCourses=" + enrolledCourses +
                "} " + super.toString();
    }

    /* Getters */
    @Override
    public Long getPersonID() {
        return super.getPersonID();
    }

    @Override
    public String getVorname() {
        return super.getVorname();
    }

    @Override
    public String getNachname() {
        return super.getNachname();
    }

    public long getStudentID() {
        return studentID;
    }

    public int getTotalCredits() {
        return totalCredits;
    }

    public List<Long> getEnrolledCourses() {
        return enrolledCourses;
    }

    /* Setters */
    @Override
    public void setPersonID(Long personID) {
        super.setPersonID(personID);
    }

    @Override
    public void setVorname(String vorname) {
        super.setVorname(vorname);
    }

    @Override
    public void setNachname(String nachname) {
        super.setNachname(nachname);
    }

    public void setStudentID(long studentID) {
        this.studentID = studentID;
    }

    public void setTotalCredits(int totalCredits) {
        this.totalCredits = totalCredits;
    }

    public void setEnrolledCourses(List<Long> enrolledCourses) {
        this.enrolledCourses = enrolledCourses;
    }
}
