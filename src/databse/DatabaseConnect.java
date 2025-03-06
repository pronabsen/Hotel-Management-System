package databse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class DatabaseConnect {
    public Connection connection = null;
    public Statement statement = null;

    public DatabaseConnect() {
        try {

            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:hotel_management_system.db");
            statement = connection.createStatement();

            System.out.println("Connected to SQLite database.");

        /*
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel_managment", "root", "");
            statement = connection.createStatement();
        */

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public void closeConnection() {
        try {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
            System.out.println("Database connection closed.");
        } catch (SQLException e) {
            System.out.println("Error closing database: " + e.getMessage());
        }
    }
}
