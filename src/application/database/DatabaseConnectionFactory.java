package application.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionFactory {
    // Database URL
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/frisbeegolfapp";
    // Database credentials
    private static final String DATABASE_USER = "root";
    private static final String DATABASE_PASSWORD = "Elyas";

    // JDBC driver name
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";

    /**
     * Establishes a connection to the database.
     *
     * @return a Connection object
     * @throws SQLException if a database access error occurs
     */
    public static Connection getConnection() throws SQLException {
        try {
            // Ensure the JDBC driver is loaded
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC Driver not found.", e);
        }

        return DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
    }
    
    // Optionally, you could include a method to close the connection
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                // Log the exception or handle it as per your application's need
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
    }
}


