package com.hospital;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL ="jdbc:postgresql://localhost:5432/hospital_management";

    private static final String USER = "postgres";
    private static final String PASSWORD = "12345";
    // * password of postgres

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
