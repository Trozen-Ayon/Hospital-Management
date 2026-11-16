package com.hospital;

import java.sql.*;
import java.util.Scanner;
// * department service for admin
public class DepartmentService {

    public void addDepartment(Scanner input) throws Exception {

        System.out.print("Enter Department Name: ");
        String name = input.nextLine();

        System.out.print("Enter Description: ");
        String description = input.nextLine();

        Connection connection = DBConnection.getConnection();

        String sql = "INSERT INTO departments (name, description) VALUES (?, ?)";

        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setString(1, name);
        ps.setString(2, description);

        ps.executeUpdate();

        System.out.println("Department Added Successfully!");

        ps.close();
        connection.close();
    }


    public void viewAllDepartments() throws Exception {

    Connection connection = DBConnection.getConnection();

    String sql = "SELECT * FROM departments ORDER BY id";

    PreparedStatement ps = connection.prepareStatement(sql);

    ResultSet rs = ps.executeQuery();

    while (rs.next()) {

        System.out.println("--------------------");

        System.out.println("Department ID: " + rs.getInt("id"));
        System.out.println("Name: " + rs.getString("name"));
        System.out.println("Description: " + rs.getString("description"));
    }

    rs.close();
    ps.close();
    connection.close();
}


}