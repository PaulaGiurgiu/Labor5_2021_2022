package repository;

import exception.DeleteVorlesungFromLehrerException;
import exception.ExistException;
import exception.RegisterException;
import model.Lehrer;
import model.Person;
import model.Student;
import model.Vorlesung;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class RegistrationSystem {
    private final VorlesungRepository vorlesungRepository;
    private final PersonRepository personRepository;
    private final StudentRepository studentRepository;
    private final LehrerRepository lehrerRepository;
    private final EnrolledRepository enrolledRepository;

    /**
     * wir erstellen ein neues Objekt von Typ RegistrationSystem
     * @throws SQLException falls man Repositories nicht öffnen kann
     */
    public RegistrationSystem() throws SQLException {
        this.vorlesungRepository = new VorlesungRepository();
        this.personRepository = new PersonRepository();
        this.studentRepository = new StudentRepository();
        this.lehrerRepository = new LehrerRepository();
        this.enrolledRepository = new EnrolledRepository();
    }

    /**
     * @return alle Elementen aus der "vorlesungRepository"
     */
    public VorlesungRepository getVorlesungRepository(){
        return this.vorlesungRepository;
    }

    /**
     * @return alle Elementen aus der "personRepository"
     */
    public PersonRepository getPersonRepository() {
        return personRepository;
    }

    /**
     * @return alle Elementen aus der "studentRepository"
     */
    public StudentRepository getStudentRepository(){
        return this.studentRepository;
    }

    /**
     * @return alle Elementen aus der "lehrerRepository"
     */
    public LehrerRepository getLehrerRepository(){
        return this.lehrerRepository;
    }

    /**
     * @return alle Elementen aus der "enrolledRepository"
     */
    public EnrolledRepository getEnrolledRepository() {
        return enrolledRepository;
    }

    /**
     *
     * @param VorlesungID eine "Long" Zahl, die ein "Vorlesung" Id entspricht
     * @param StudentID eine "Long" Zahl, die ein "Student" Id entspricht
     * @return der Student meldet sich fur die gegebene Vorlesung an
     * @throws RegisterException falls der Student oder die Vorlesung nicht in der Repository Liste sind
     *                     falls es gar kein verfugbar Platz dur der Vorlesung gibt
     *                     falls der Anzahl der Credits des Studenten grosser als 30
     * @throws SQLException falls man nicht Daten in der Datenbank ändern kann
     *
     * wir aktualisieren die Listen aus der Student-, Vorlesung- und LehrerRepository
     */
    public boolean register(long VorlesungID, long StudentID) throws RegisterException, SQLException {
        String message = "Unerfüllte Bedingungen: ";
        Vorlesung vorlesung = this.vorlesungRepository.findOne(VorlesungID);
        Student student = this.studentRepository.findOne(StudentID);

        if (vorlesung == null || student == null)
            throw new RegisterException(message + "Der Student und/oder die Vorlesung sind/ist nicht in der Liste.");

        if (this.vorlesungRepository.enrolledStudents(vorlesung).size() > vorlesung.getMaxEnrollment())
            throw new RegisterException(message + "Keine freie Plätze.");
        else if (this.studentRepository.credits(student) + vorlesung.getCredits() > 30)
            throw new RegisterException(message + "Anzahl von Credits übersprungen.");
        else if (this.enrolledRepository.findOne(vorlesung.getVorlesungID(), student.getStudentID()))
            throw new RegisterException(message + "Der Student nimmt an dieser Vorlesung teil.");
        else
            this.enrolledRepository.save(VorlesungID, StudentID);

        return true;
    }

    /**
     *
     * @param VorlesungID eine "Long" Zahl, die ein "Vorlesung" Id entspricht
     * @param StudentID eine "Long" Zahl, die ein "Student" Id entspricht
     *
     * wir loschen die Vorlesung aus der "Vorlesung" Liste des Studenten
     * wir loschen der Student aus der "Studenten" Liste der Vorlesung
     * wir aktualisieren die Listen aus der Student- und VorlesungRepository
     *
     * @throws RegisterException falls Der Student und/oder die Vorlesung sind/ist nicht in der Liste
     * @throws SQLException falls man nicht Daten in der Datenbank ändern kann
     */
    public void unregister(long VorlesungID, long StudentID) throws RegisterException, SQLException {
        String message = "Unerfüllte Bedingungen: ";
        Vorlesung vorlesung = this.vorlesungRepository.findOne(VorlesungID);
        Student student = this.studentRepository.findOne(StudentID);

        if (vorlesung == null || student == null)
            throw new RegisterException(message + "Der Student und/oder die Vorlesung sind/ist nicht in der Liste.");

        this.enrolledRepository.delete(VorlesungID, StudentID);
    }

    /**
     * @return ein HashMap mit der Vorlesungen, die freie Platze haben und deren Anzahl
     * @throws SQLException falls man nicht Daten aus der Datenbank lesen kann
     */
    public HashMap<Integer, Long> retrieveCoursesWithFreePlaces() throws SQLException {
        HashMap<Integer, Long> map = new HashMap<Integer, Long>();
        for (Vorlesung vorlesung: this.vorlesungRepository.findAll()){
            map.put(vorlesung.getMaxEnrollment() - this.vorlesungRepository.enrolledStudents(vorlesung).size(), vorlesung.getVorlesungID());
        }

        return map;
    }

    /**
     * @param VorlesungID eine "Long" Zahl, die ein "Vorlesung" Id entspricht
     * @return eine Liste von Studenten, die an der gegebenen Vorlesung teilnehmen
     * @throws ExistException falls die Vorlesung nicht in der Liste ist
     * @throws SQLException falls man nicht Daten aus der Datenbank lesen kann
     */
    public List<Long> retrieveStudentsEnrolledForACourse(long VorlesungID) throws ExistException, SQLException {
        Vorlesung vorlesung = this.vorlesungRepository.findOne(VorlesungID);
        if (vorlesung == null){
            throw new ExistException("Die Vorlesung ist nicht in der Liste.");
        }

        return this.vorlesungRepository.enrolledStudents(vorlesung);
    }

    /**
     * @return alle Elemente aus der "vorlesungRepository"
     * @throws SQLException falls man nicht Daten aus der Datenbank lesen kann
     */
    public List<Vorlesung> getAllCourses() throws SQLException {
        List<Vorlesung> vorlesungList = new ArrayList<>();
        vorlesungRepository.findAll()
                .forEach(vorlesung -> {
                    try {
                        vorlesungList
                                .add(new Vorlesung(vorlesung.getName(),
                                        vorlesung.getLehrer(),
                                        vorlesung.getVorlesungID(),
                                        vorlesung.getMaxEnrollment(),
                                        vorlesungRepository.enrolledStudents(vorlesung),
                                        vorlesungRepository.findOne(vorlesung.getVorlesungID()).getCredits()
                                ));
                    } catch (SQLException e) {
                        System.out.println(e.getMessage());
                    }
                });

        return vorlesungList;
    }

    /**
     * @return alle Elemente aus der "personRepository"
     * @throws SQLException falls man nicht Daten aus der Datenbank lesen kann
     */
    public List<Person> getAllPersons() throws SQLException {
        List<Person> personList = new ArrayList<>();
        this.personRepository.findAll()
                .forEach(person -> personList
                        .add(new Person(person.getPersonID(),
                                person.getVorname(),
                                person.getNachname())));
        return personList;
    }

    /**
     * @return alle Elemente aus der "studentRepository"
     * @throws SQLException falls man nicht Daten aus der Datenbank lesen kann
     */
    public List<Student> getAllStudents() throws SQLException {
        List<Student> studentList = new ArrayList<>();
        this.studentRepository.findAll()
                .forEach(student ->
                {
                    try {
                        studentList
                                .add(new Student(new Person(student.getPersonID(), student.getVorname(), student.getNachname()),
                                        student.getStudentID(),
                                        this.studentRepository.credits(student),
                                        this.studentRepository.enrolledCourses(student)));
                    } catch (SQLException e) {
                        System.out.println(e.getMessage());
                    }
                });

        return studentList;
    }

    /**
     * @return alle Elemente aus der "lehrerRepository"
     * @throws SQLException falls man nicht Daten aus der Datenbank lesen kann
     */
    public List<Lehrer> getAllLehrer() throws SQLException {
        List<Lehrer> lehrerList = new ArrayList<>();
        this.lehrerRepository.findAll()
                .forEach(lehrer ->
                {
                    try {
                        lehrerList
                                .add(new Lehrer(new Person(lehrer.getPersonID(), lehrer.getVorname(), lehrer.getNachname()),
                                        lehrer.getLehrerID(),
                                        this.lehrerRepository.enrolledCourses(lehrer)));
                    } catch (SQLException e) {
                        System.out.println(e.getMessage());
                    }
                });
        return lehrerList;
    }

    /**
     *
     * @param VorlesungID eine "Long" Zahl, die ein "Vorlesung" Id entspricht
     * @param newCredit die neue Anzahl von Credits
     * @return wir andern die Anzahl der Credits der Vorlesung
     *         wir "unregister" alle Studenten aus der Vorlesung
     *         wir aktualisieren die Vorlesung
     *         wir "register" die alte Studenten zu der Vorlesung
     * @throws RegisterException falls die alte Studenten nicht mehr an der Vorlesung teilnehmen konnten
     * @throws ExistException falls die Vorlesung existiert nicht
     * @throws SQLException falls man nicht Daten in der Datenbank ändern kann
     */
    public boolean changeCreditFromACourse(long VorlesungID, int newCredit) throws RegisterException, ExistException, SQLException {
        String message = "Unerfüllte Bedingungen: ";
        Vorlesung vorlesung = this.vorlesungRepository.findOne(VorlesungID);
        if (vorlesung == null)
            throw new RegisterException(message + "Die Vorlesung ist nicht in der Liste.");

        Vorlesung newVorlesung = new Vorlesung(vorlesung.getName(),
                vorlesung.getLehrer(),
                vorlesung.getVorlesungID(),
                vorlesung.getMaxEnrollment(),
                newCredit);
        this.vorlesungRepository.update(newVorlesung);

        Student student;
        for (Long studentID: this.vorlesungRepository.enrolledStudents(vorlesung)) {
            student = this.studentRepository.findOne(studentID);
            if (this.studentRepository.credits(student) > 30)
                unregister(VorlesungID, studentID);
            
        }

        throw new ExistException("Die Anzahl der Credits wurden geändert.");
    }

    /**
     * @param LehrerID eine "Long" Zahl, die ein "Lehrer" Id entspricht
     * @param VorlesungID eine "Long" Zahl, die ein "Vorlesung" Id entspricht
     *         die Loschung der Vorlesung von dem Lehrer
     *         wir "unregister" alle Studenten aus der Vorlesung
     *         wir aktualisieren die "Vorlesung" Liste des Lehrers
     * @throws DeleteVorlesungFromLehrerException falls der Lehrer nich die Vorlesung löschen kann
     * @throws RegisterException falls die Vorlesung existiert nicht
     * @throws SQLException falls man nicht Daten in der Datenbank ändern kann
     */
    public void deleteVorlesungFromLehrer(long LehrerID, long VorlesungID) throws DeleteVorlesungFromLehrerException, RegisterException, SQLException {
        String message = "Unerfüllte Bedingungen: ";
        Lehrer lehrer = this.lehrerRepository.findOne(LehrerID);
        Vorlesung vorlesung = this.vorlesungRepository.findOne(VorlesungID);

        if (lehrer == null || vorlesung == null)
            throw new DeleteVorlesungFromLehrerException(message + "Der Lehrer und/oder die Vorlesung sind/ist nicht in der Liste.");

        if (vorlesung.getLehrer() != lehrer.getLehrerID())
            throw new DeleteVorlesungFromLehrerException(message + "Der Lehrer unterrichtet nicht diese Vorlesung.");

        this.vorlesungRepository.enrolledStudents(vorlesung)
                .forEach(student -> {
                    try {
                        unregister(vorlesung.getVorlesungID(), student);
                    } catch (RegisterException | SQLException e) {
                        System.out.println(message + e.getMessage());
                    }
                });

        this.vorlesungRepository.delete(vorlesung);
    }
}
