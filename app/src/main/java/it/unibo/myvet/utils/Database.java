package it.unibo.myvet.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Database implements AutoCloseable {
    private Connection connection;

    // Private constructor to enforce use of the factory method
    private Database(Connection connection) {
        this.connection = connection;
    }

    // Static method to create an instance of Database
    public static Database connect(String url, String user, String password) throws SQLException {
        Connection connection = DriverManager.getConnection(url, user, password);
        return new Database(connection);
    }

    // Method to prepare a PreparedStatement
    public PreparedStatement prepareStatement(String sql) throws SQLException {
        return connection.prepareStatement(sql);
    }

    // Override the close method from AutoCloseable to close the connection
    @Override
    public void close() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
