package repository;

import connection.ConnectionMySQL;
import model.Person;
import model.Student;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentRepository implements CrudRepository<Student>{
    static final String QUERY_SELECT = "SELECT * FROM STUDENT INNER JOIN PERSON ON STUDENT.personID = PERSON.personID";
    static final String QUERY_FIND = "SELECT * FROM STUDENT INNER JOIN PERSON ON STUDENT.personID = PERSON.personID WHERE studentID = ?";
    static final String QUERY_INSERT = "INSERT INTO STUDENT VALUES(?, ?, ?)";
    static final String QUERY_DELETE = "DELETE FROM STUDENT WHERE studentID = ?";
    static final String QUERY_UPDATE = "UPDATE STUDENT SET personID = ?, totalCredits = ? where studentID = ?";
    static final String QUERY_CREDITS = "SELECT SUM(credits) AS 'TOTAL' FROM STUDENT " +
                                        "INNER JOIN ENROLLED ON STUDENT.studentID = ENROLLED.studentID " +
                                        "INNER JOIN VORLESUNG ON VORLESUNG.vorlesungID = ENROLLED.vorlesungID " +
                                        "WHERE STUDENT.studentID = ?";
    static final String QUERY_COURSES = "SELECT * FROM STUDENT " +
                                        "INNER JOIN ENROLLED ON STUDENT.studentID = ENROLLED.studentID " +
                                        "WHERE STUDENT.studentID = ?";

    private java.sql.Connection connection;
    private java.sql.Statement statement;
    private java.sql.ResultSet resultSet;

    public StudentRepository() throws SQLException {
        super();
        this.connection = new ConnectionMySQL().getConnection();
        this.statement = connection.createStatement();
    }

    @Override
    public Student findOne(long id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(QUERY_FIND);
        preparedStatement.setLong(1, id);
        this.resultSet = preparedStatement.executeQuery();

        Student student = null;
        while (resultSet.next()){
            student = new Student(new Person(resultSet.getLong("personID"), resultSet.getString("vorname"), resultSet.getString("nachname")), resultSet.getLong("studentID"));
        }

        return student;
    }

    @Override
    public List<Student> findAll() throws SQLException {
        List<Student> studentList = new ArrayList<>();
        this.resultSet = statement.executeQuery(QUERY_SELECT);

        Student student;
        while (resultSet.next()){
            student = new Student(new Person(resultSet.getLong("personID"), resultSet.getString("vorname"), resultSet.getString("nachname")), resultSet.getLong("studentID"));
            studentList.add(student);
        }
        return studentList;
    }

    @Override
    public Student save(Student entity) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(QUERY_INSERT);
        preparedStatement.setLong(1, entity.getPersonID());
        preparedStatement.setLong(2, entity.getStudentID());
        preparedStatement.setInt(3, entity.getTotalCredits());

        preparedStatement.executeUpdate();

        return entity;
    }

    @Override
    public void delete(Student entity) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(QUERY_DELETE);
        preparedStatement.setLong(1, entity.getStudentID());

        preparedStatement.executeUpdate();
    }

    @Override
    public Student update(Student entity) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(QUERY_UPDATE);
        preparedStatement.setLong(1, entity.getPersonID());
        preparedStatement.setInt(2, entity.getTotalCredits());
        preparedStatement.setLong(3, entity.getStudentID());

        preparedStatement.executeUpdate();

        return entity;
    }

    public int credits(Student entity) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement(QUERY_CREDITS);
        preparedStatement.setLong(1, entity.getStudentID());
        this.resultSet = preparedStatement.executeQuery();

        int count = 0;
        while (this.resultSet.next()){
            count = resultSet.getInt("TOTAL");
        }

        return count;
    }

    public List<Long> enrolledCourses(Student entity) throws SQLException{
        List<Long> list = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement(QUERY_COURSES);
        preparedStatement.setLong(1, entity.getStudentID());
        this.resultSet = preparedStatement.executeQuery();

        while (resultSet.next()){
            list.add(resultSet.getLong("vorlesungID"));
        }

        return list;
    }


}
