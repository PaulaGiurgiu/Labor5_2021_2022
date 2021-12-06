package UI;

import controller.RegistrationSystemController;
import exception.DeleteVorlesungFromLehrerException;
import exception.ExistException;
import exception.RegisterException;

import java.sql.SQLException;
import java.util.Map;
import java.util.Scanner;

public class Konsole {
    private RegistrationSystemController registrationSystem;


    public Konsole() {
        try {
            this.registrationSystem = new RegistrationSystemController();
        } catch (SQLException e) {
            System.out.println("Error mit der Offnung der Konsole");
        }
        System.out.println("Willkommen! \nTaste 1:\tRegister " +
                                        "\nTaste 2:\tUnegister " +
                                        "\nTaste 3:\tPrint Liste Vorlesungen " +
                                        "\nTaste 4:\tPrint Liste Personen " +
                                        "\nTaste 5:\tPrint Liste Studenten " +
                                        "\nTaste 6:\tPrint Liste Lehrer " +
                                        "\nTaste 7:\tVorlesungen mit freie Plätze " +
                                        "\nTaste 8:\tPrint Liste Studenten von einer Vorlesung " +
                                        "\nTaste 9:\tCredit Anzahl ändern " +
                                        "\nTaste 10:\tVorlesung löschen von Lehrer" +
                                        "\nTaste 11:\tSort Liste Studenten " +
                                        "\nTaste 12:\tSort Liste Vorlesung " +
                                        "\nTaste 13:\tFilter Liste Studenten " +
                                        "\nTaste 14:\tFilter Liste Vorlesung " +
                                        "\nTaste 15:\tAdd Vorlesung " +
                                        "\nTaste 16:\tAdd Person " +
                                        "\nTaste 17:\tAdd Student " +
                                        "\nTaste 18:\tAdd Lehrer" +
                                        "\nTaste 0:\tExit\n");

        Scanner command = new Scanner(System.in);
        int commandInput;
        do {
            System.out.println("\nCommand: ");
            switch (commandInput = command.nextInt()) {
                case 0:
                    System.out.println("Bis bald.");
                    break;
                case 1:
                    System.out.println("Register");
                    ui_getAllCourses();
                    ui_getAllStudents();
                    ui_register();
                    break;
                case 2:
                    System.out.println("Unregister");
                    ui_getAllCourses();
                    ui_getAllStudents();
                    ui_unregister();
                    break;
                case 3:
                    System.out.println("Print Liste Vorlesungen");
                    ui_getAllCourses();
                    break;
                case 4:
                    System.out.println("Print Liste Personen");
                    ui_getAllPersons();
                    break;
                case 5:
                    System.out.println("Print Liste Studenten");
                    ui_getAllStudents();
                    break;
                case 6:
                    System.out.println("Print Liste Lehrer");
                    ui_getAllLehrer();
                    break;
                case 7:
                    System.out.println("Vorlesungen mit freie Plätze");
                    ui_retrieveCoursesWithFreePlaces();
                    break;
                case 8:
                    System.out.println("Print Liste Studenten von einer Vorlesung");
                    ui_getAllCourses();
                    ui_retrieveStudentsEnrolledForACourse();
                    break;
                case 9:
                    System.out.println("Credit Anzahl ändern");
                    ui_getAllCourses();
                    ui_changeCreditFromACourse();
                    break;
                case 10:
                    System.out.println("Vorlesung löschen");
                    ui_getAllCourses();
                    ui_deleteVorlesungFromLehrer();
                    break;
                case 11:
                    System.out.println("Sort Liste Studenten nach 'Person ID'");
                    ui_sortListeStudenten();
                    break;
                case 12:
                    System.out.println("Sort Liste Vorlesung nach 'Name'");
                    ui_sortListeVorlesungen();
                    break;
                case 13:
                    System.out.println("Filter Liste Studenten nach Studenten mit Anzahl der Vorlesungen >= 2");
                    ui_filterListeStudenten();
                    break;
                case 14:
                    System.out.println("Filter Liste Vorlesung nach Vorlesungen mit maximal Enrollment > 30");
                    ui_filterListeVorlesungen();
                    break;
                case 15:
                    System.out.println("Add Vorlesung");
                    ui_addVorlesung();
                    break;
                case 16:
                    System.out.println("Add Person");
                    ui_addPerson();
                    break;
                case 17:
                    System.out.println("Add Student");
                    ui_addStudent();
                    break;
                case 18:
                    System.out.println("Add Lehrer");
                    ui_addLehrer();
                    break;
            }

        } while (commandInput != 0);
    }


    /**
     * die "ui_register"-Methode meldet ein bestimmter Student an eine Vorlesung an
     */
    private void ui_register(){
        System.out.println("Register");
        Scanner idVorlesung = new Scanner(System.in);
        Scanner idStudent = new Scanner(System.in);
        long VorlesungID, StudentID;

        System.out.println("Vorlesung ID: ");
        VorlesungID = idVorlesung.nextLong();
        System.out.println("Student ID: ");
        StudentID = idStudent.nextLong();
        try {
            registrationSystem.controller_register(VorlesungID, StudentID);
        } catch (RegisterException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * die "ui_unregister"-Methode löscht der Student aus der Vorlesung
     */
    private void ui_unregister(){
        System.out.println("Unregister");
        Scanner idVorlesung = new Scanner(System.in);
        Scanner idStudent = new Scanner(System.in);
        long VorlesungID, StudentID;

        System.out.println("Vorlesung ID: ");
        VorlesungID = idVorlesung.nextLong();
        System.out.println("Student ID: ");
        StudentID = idStudent.nextLong();
        try {
            registrationSystem.controller_unregister(VorlesungID, StudentID);
        } catch (RegisterException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * die "ui_getAllCourses"-Methode gibt alle Vorlesungen zurück
     */
    private void ui_getAllCourses() {
        System.out.println("Print Liste Vorlesungen");
        try {
            this.registrationSystem.controller_getAllCourses()
                    .forEach(System.out::println);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * die "ui_getAllPersons"-Methode gibt alle Personen zurück
     */
    private void ui_getAllPersons(){
        System.out.println("Print Liste Personen");
        try {
            this.registrationSystem.controller_getAllPersons()
                    .forEach(System.out::println);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * die "ui_getAllStudents"-Methode gibt alle Studenten zurück
     */
    private void ui_getAllStudents(){
        System.out.println("Print Liste Studenten");
        try {
            this.registrationSystem.controller_getAllStudents()
                    .forEach(System.out::println);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * die "ui_getAllLehrer"-Methode gibt alle Lehrer zurück
     */
    private void ui_getAllLehrer(){
        System.out.println("Print Liste Lehrer");
        try {
            this.registrationSystem.controller_getAllLehrer()
                    .forEach(System.out::println);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    /**
     * die "ui_retrieveCoursesWithFreePlaces"-Methode zeigt alle Vorlesungen mit freie Plätze
     */
    private void ui_retrieveCoursesWithFreePlaces(){
        System.out.println("Vorlesungen mit freie Plätze");
        try {
            for (Map.Entry map: registrationSystem.controller_retrieveCoursesWithFreePlaces().entrySet()) {
                System.out.println(map.getKey() + " " + map.getValue());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * die "ui_retrieveStudentsEnrolledForACourse"-Methode zeigt die "Student"-Liste von einer Vorlesung
     */
    private void ui_retrieveStudentsEnrolledForACourse(){
        System.out.println("Print Liste Studenten von einer Vorlesung");
        Scanner idVorlesung = new Scanner(System.in);
        long VorlesungId;

        System.out.println("Vorlesung ID: ");
        VorlesungId = idVorlesung.nextLong();
        try {
            for (Long student:registrationSystem.controller_retrieveStudentsEnrolledForACourse(VorlesungId)) {
                System.out.println(student);
            }
        } catch (ExistException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * die "ui_changeCreditFromACourse"-Methode ändert die Anzahl der Credits
     */
    private void ui_changeCreditFromACourse(){
        System.out.println("Credit Anzahl ändern");
        Scanner idVorlesung = new Scanner(System.in);
        Scanner credits = new Scanner(System.in);
        long VorlesungId;
        int newCredit;

        System.out.println("Vorlesung ID: ");
        VorlesungId = idVorlesung.nextLong();
        System.out.println("New Credits: ");
        newCredit = credits.nextInt();
        try {
            registrationSystem.controller_changeCreditFromACourse(VorlesungId, newCredit);
        } catch (RegisterException | ExistException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * die "ui_deleteVorlesungFromLehrer"-Methode löscht die Vorlesung von Lehrer
     */
    private void ui_deleteVorlesungFromLehrer(){
        System.out.println("Vorlesung löschen");
        Scanner idVorlesung = new Scanner(System.in);
        Scanner idLehrer = new Scanner(System.in);
        long VorlesungId, LehrerId;

        System.out.println("Vorlesung ID: ");
        VorlesungId = idVorlesung.nextLong();
        System.out.println("Lehrer ID: ");
        LehrerId = idLehrer.nextLong();
        try {
            registrationSystem.controller_deleteVorlesungFromLehrer(LehrerId, VorlesungId);
        } catch (DeleteVorlesungFromLehrerException | RegisterException | SQLException e) {
            System.out.println(e.getMessage());
        }

        ui_getAllCourses();
    }

    /**
     * die "ui_sortListeStudenten"-Methode sort die "Student"-Liste nach 'Vorname'
     */
    private void ui_sortListeStudenten(){
        System.out.println("Sort Liste Studenten");
        try {
            this.registrationSystem.controller_sortListeStudenten()
                    .forEach(System.out::println);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * die "ui_sortListeVorlesungen"-Methode sort die "Vorlesung"-Liste nach 'Name'
     */
    private void ui_sortListeVorlesungen(){
        System.out.println("Sort Liste Vorlesung");
        try {
            this.registrationSystem.controller_sortListeVorlesungen()
                    .forEach(System.out::println);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * die "ui_filterListeStudenten"-Methode filter die "Student"-Liste nach Studenten mit Anzahl der Vorlesungen >= 2
     */
    private void ui_filterListeStudenten(){
        System.out.println("Filter Liste Studenten");
        try {
            this.registrationSystem.controller_filterListeStudenten()
                    .forEach(System.out::println);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * die "ui_filterListeVorlesungen"-Methode filter die "Vorlesung"-Liste nach Vorlesungen mit maximal Enrollment > 30
     */
    private void ui_filterListeVorlesungen(){
        System.out.println("Filter Liste Vorlesung");
        try {
            this.registrationSystem.controller_filterListeVorlesungen()
                    .forEach(System.out::println);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * die "ui_addPerson"-Methode fügt eine neue Person
     */
    private void ui_addPerson(){
        System.out.println("Add Person");
        Scanner vorname = new Scanner(System.in);
        Scanner nachname = new Scanner(System.in);
        Scanner id = new Scanner(System.in);
        String Vorname, Nachname;
        long Id;

        System.out.println("ID: ");
        Id = id.nextLong();
        System.out.println("Vorname: ");
        Vorname = vorname.nextLine();
        System.out.println("Nachname: ");
        Nachname = nachname.nextLine();
        try {
            registrationSystem.controller_addPerson(Id, Vorname, Nachname);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    /**
     * die "ui_addStudent"-Methode fügt einen neuen Student
     */
    private void ui_addStudent(){
        System.out.println("Add Student");
        Scanner studentID = new Scanner(System.in);
        Scanner personID = new Scanner(System.in);
        long StudentID, PersonID;

        System.out.println("Person ID: ");
        PersonID = personID.nextLong();
        System.out.println("Student ID: ");
        StudentID = studentID.nextLong();

        try {
            registrationSystem.controller_addStudent(PersonID, StudentID);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * die "ui_addLehrer"-Methode fügt einen neuen Lehrer
     */
    private void ui_addLehrer(){
        System.out.println("Add Lehrer");
        Scanner lehrerID = new Scanner(System.in);
        Scanner personID = new Scanner(System.in);
        long LehrerID, PersonID;

        System.out.println("Person ID: ");
        PersonID = personID.nextLong();
        System.out.println("Student ID: ");
        LehrerID = lehrerID.nextLong();

        try {
            registrationSystem.controller_addLehrer(PersonID, LehrerID);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * die "ui_addVorlesung"-Methode fügt eine neue Vorlesung
     */
    private void ui_addVorlesung(){
        System.out.println("Add Vorlesung");
        Scanner name = new Scanner(System.in);
        Scanner idLehrer = new Scanner(System.in);
        Scanner idVorlesung = new Scanner(System.in);
        Scanner maxEnrollment = new Scanner(System.in);
        Scanner credits = new Scanner(System.in);
        String Name;
        long IdLehrer, IdVorlesung;
        int MaxEnrollment, Credits;

        System.out.println("Name der Vorlesung: ");
        Name = name.nextLine();
        System.out.println("Lehrer ID: ");
        IdLehrer = idLehrer.nextLong();
        System.out.println("Vorlesung ID: ");
        IdVorlesung = idVorlesung.nextLong();
        System.out.println("Maximal Teilnehner: ");
        MaxEnrollment = maxEnrollment.nextInt();
        System.out.println("Anzahl Credit: ");
        Credits = credits.nextInt();
        try {
            registrationSystem.controller_addVorlesung(Name, IdLehrer, IdVorlesung, MaxEnrollment, Credits);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

}
