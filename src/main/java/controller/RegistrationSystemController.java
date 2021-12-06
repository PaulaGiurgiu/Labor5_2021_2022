package controller;

import exception.DeleteVorlesungFromLehrerException;
import exception.ExistException;
import exception.RegisterException;
import model.Lehrer;
import model.Person;
import model.Student;
import model.Vorlesung;
import repository.RegistrationSystem;

import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class RegistrationSystemController {
    private final RegistrationSystem registrationSystem;

    public RegistrationSystemController() throws SQLException {
        this.registrationSystem = new RegistrationSystem();
    }

    /**
     * @param VorlesungID eine "long" Zahl
     * @param StudentID eine "long" Zahl
     * @throws RegisterException falls der Student mit der Id "StudentID" nicht an der Vorlesung mit der Id "VorlesungID" registriert wurde
     * @throws SQLException falls man nicht Daten in der Datenbank ändern kann
     */
    public void controller_register(long VorlesungID, long StudentID) throws RegisterException, SQLException {
        registrationSystem.register(VorlesungID, StudentID);
    }

    /**
     * @param VorlesungID eine "long" Zahl
     * @param StudentID eine "long" Zahl
     * @throws RegisterException falls der Student mit der Id "StudentID" nicht aus der Vorlesung mit der Id "VorlesungID" "unregistriert" wurde
     * @throws SQLException falls man nicht Daten in der Datenbank ändern kann
     */
    public void controller_unregister(long VorlesungID, long StudentID) throws RegisterException, SQLException {
        registrationSystem.unregister(VorlesungID, StudentID);
    }

    /**
     * @return die Liste aller Vorlesungen
     * @throws SQLException falls man nicht Daten aus der Datenbank lesen kann
     */
    public List<Vorlesung> controller_getAllCourses() throws SQLException {
        return registrationSystem.getAllCourses();
    }

    /**
     * @return die Liste aller Personen
     * @throws SQLException falls man nicht Daten aus der Datenbank lesen kann
     */
    public List<Person> controller_getAllPersons() throws SQLException {
        return registrationSystem.getAllPersons();
    }

    /**
     * @return die Liste aller Studenten
     * @throws SQLException falls man nicht Daten aus der Datenbank lesen kann
     */
    public List<Student> controller_getAllStudents() throws SQLException {
        return registrationSystem.getAllStudents();
    }

    /**
     * @return die Liste aller Lehrer
     * @throws SQLException falls man nicht Daten aus der Datenbank lesen kann
     */
    public List<Lehrer> controller_getAllLehrer() throws SQLException {
        return registrationSystem.getAllLehrer();
    }

    /**
     * @return ein HashMap mit der Vorlesungen, die freie Platze haben und deren Anzahl
     * @throws SQLException falls man nicht Daten aus der Datenbank lesen kann
     */
    public HashMap<Integer, Long> controller_retrieveCoursesWithFreePlaces() throws SQLException {
        return registrationSystem.retrieveCoursesWithFreePlaces();
    }

    /**
     * @param VorlesungID eine "Long" Zahl, die ein "Vorlesung" Id entspricht
     * @return eine Liste von Studenten, die an der gegebenen Vorlesung teilnehmen
     * @throws ExistException falls die Vorlesung nicht in der Liste ist
     * @throws SQLException falls man nicht Daten aus der Datenbank lesen kann
     */
    public List<Long> controller_retrieveStudentsEnrolledForACourse(long VorlesungID) throws ExistException, SQLException {
        return registrationSystem.retrieveStudentsEnrolledForACourse(VorlesungID);
    }

    /**
     * @param VorlesungID eine "Long" Zahl, die ein "Vorlesung" Id entspricht
     * @param newCredit die neue Anzahl von Credits
     * @throws RegisterException falls die alte Studenten nicht mehr an der Vorlesung teilnehmen konnten
     * @throws ExistException falls die Vorlesung existiert nicht
     * @throws SQLException falls man nicht Daten in der Datenbank ändern kann
     */
    public void controller_changeCreditFromACourse(long VorlesungID, int newCredit) throws RegisterException, ExistException, SQLException {
        registrationSystem.changeCreditFromACourse(VorlesungID, newCredit);
    }

    /**
     * @param LehrerID eine "Long" Zahl, die ein "Lehrer" Id entspricht
     * @param VorlesungID eine "Long" Zahl, die ein "Vorlesung" Id entspricht
     * @throws DeleteVorlesungFromLehrerException falls der Lehrer nich die Vorlesung löschen kann
     * @throws RegisterException falls die Vorlesung existiert nicht
     * @throws SQLException falls man nicht Daten in der Datenbank ändern kann
     */
    public void controller_deleteVorlesungFromLehrer(long LehrerID, long VorlesungID) throws DeleteVorlesungFromLehrerException, RegisterException, SQLException {
        registrationSystem.deleteVorlesungFromLehrer(LehrerID, VorlesungID);
    }

    /**
     * @return die sortierte Liste der Studenten
     * @throws SQLException falls man nicht Daten aus der Datenbank lesen kann
     */
    public List<Student> controller_sortListeStudenten() throws SQLException {
        Collections.sort(registrationSystem.getAllStudents(), new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                return o1.getPersonID().compareTo(o2.getPersonID());
            }
        });

        return registrationSystem.getAllStudents();
    }

    /**
     * @return die sortierte Liste der Vorlesungen
     * @throws SQLException falls man nicht Daten aus der Datenbank lesen kann
     */
    public List<Vorlesung> controller_sortListeVorlesungen() throws SQLException {
        Collections.sort(registrationSystem.getAllCourses(), new Comparator<Vorlesung>() {
            @Override
            public int compare(Vorlesung o1, Vorlesung o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });

        return registrationSystem.getAllCourses();
    }

    /**
     * @return die filtrierte Liste der Studenten
     * @throws SQLException falls man nicht Daten aus der Datenbank lesen kann
     */
    public List<Student> controller_filterListeStudenten() throws SQLException {
        List<Student> studentList = this.controller_getAllStudents().stream()
                .filter(student -> student.getEnrolledCourses().size() >=2).collect(Collectors.toList());

        return studentList;
    }

    /**
     * @return die filtrierte Liste der Vorlesungen
     * @throws SQLException falls man nicht Daten aus der Datenbank lesen kann
     */
    public List<Vorlesung> controller_filterListeVorlesungen() throws SQLException {
        List<Vorlesung> vorlesungList = this.controller_getAllCourses().stream()
                .filter(vorlesung -> vorlesung.getMaxEnrollment() > 30).collect(Collectors.toList());

        return vorlesungList;
    }

    /**
     * @param personID eine "Long"-Zahl
     * @param Vorname ein String
     * @param Nachname ein String
     * @throws SQLException falls man nicht Daten in der Datenbank einfügen kann
     */
    public void controller_addPerson(Long personID, String Vorname, String Nachname) throws SQLException {
        this.registrationSystem.getPersonRepository()
                .save(new Person(personID, Vorname, Nachname));
    }

    /**
     * @param PersonID eine "Long"-Zahl
     * @param StudentID eine "Long"-Zahl
     * @throws SQLException falls man nicht Daten in der Datenbank einfügen kann
     */
    public void controller_addStudent(Long PersonID, Long StudentID) throws SQLException {
        if (registrationSystem.getPersonRepository().findOne(PersonID) != null) {
            registrationSystem.getStudentRepository()
                    .save(new Student(registrationSystem.getPersonRepository().findOne(PersonID), StudentID));
        }
    }

    /**
     * @param PersonID eine "Long"-Zahl
     * @param LehrerID eine "Long"-Zahl
     * @throws SQLException falls man nicht Daten in der Datenbank einfügen kann
     */
    public void controller_addLehrer(Long PersonID, Long LehrerID) throws SQLException {
        if (registrationSystem.getLehrerRepository().findOne(PersonID) != null){
            registrationSystem.getLehrerRepository()
                    .save(new Lehrer(registrationSystem.getPersonRepository().findOne(PersonID), LehrerID));
        }

    }

    /**
     * @param Name ein String
     * @param IdLehrer eine "Long"-Zahl
     * @param IdVorlesung eine "Long"-Zahl
     * @param MaxEnrollment eine Zahl
     * @param Credits eine Zahl
     * @throws SQLException falls man nicht Daten in der Datenbank einfügen kann
     */
    public void controller_addVorlesung(String Name, Long IdLehrer, Long IdVorlesung, int MaxEnrollment, int Credits) throws SQLException {
        if (registrationSystem.getLehrerRepository().findOne(IdLehrer) != null){
            registrationSystem.getVorlesungRepository()
                    .save(new Vorlesung(Name, IdLehrer, IdVorlesung, MaxEnrollment, Credits));
        }
    }
}
