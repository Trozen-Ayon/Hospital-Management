package com.hospital;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class CreateAdmin {

    public static void main(String[] args) throws Exception {

        Scanner input = new Scanner(System.in);

        System.out.print("Enter Admin Username: ");
        String username = input.nextLine();

        System.out.print("Enter Admin Password: ");
        String password = input.nextLine();

        String hashedPassword = PasswordUtil.hashPassword(password);

        Connection connection = DBConnection.getConnection();

        String sql = "INSERT INTO users (username, password, role) " +
                     "VALUES (?, ?, 'ADMIN')";

        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setString(1, username);
        ps.setString(2, hashedPassword);

        ps.executeUpdate();

        System.out.println("Admin Created Successfully!");

        ps.close();
        connection.close();
        input.close();
    }
}