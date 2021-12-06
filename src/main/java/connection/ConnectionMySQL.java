package connection;
import java.sql.*;


public class ConnectionMySQL {
    static  final String DB_URL = "jdbc:mysql://root@localhost:3306/Labor5_2021_2022";
    static final String USER = "root";
    static final String PASS = "ParolaMySQL";

    private java.sql.Connection connection;

    public ConnectionMySQL() {
        try (java.sql.Connection connection = DriverManager.getConnection(DB_URL, USER,  PASS)) {
            this.connection = connection;
        } catch ( SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public java.sql.Connection getConnection() throws SQLException {
        connection = DriverManager.getConnection(DB_URL, USER,  PASS);
        return connection;
    }
}
