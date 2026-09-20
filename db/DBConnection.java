package db;
import java.sql.*;

public class DBConnection {
    public static Connection getConnection() throws SQLException{
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/java_mini_project","krishshah","krish2705");
    }
}
