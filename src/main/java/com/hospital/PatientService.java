package com.hospital;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class PatientService{

    // * registration for the patient
    public void registerPatient(Scanner input) throws Exception{
        System.out.println("Patient Registration");

        System.out.println("Enter username : ");
        String username=input.nextLine();

        System.out.println("Enter password : ");
        String password=input.nextLine();

        System.out.println("Enter Full name : ");
        String name=input.nextLine();

        System.out.print("Email: ");
        String email = input.nextLine();

        System.out.print("Enter phone: ");
        String phone = input.nextLine();

        System.out.print("Date of birth (YYYY-MM-DD): ");
        String dateOfBirth = input.nextLine();

        System.out.print("Gender: ");
        String gender = input.nextLine();

        System.out.print("Address: ");
        String address = input.nextLine();


        Connection connection = DBConnection.getConnection();

        String sql1= "INSERT INTO users (username,password,role)  VALUES (?,?,'PATIENT') RETURNING ID ";

        PreparedStatement ps1=connection.prepareStatement(sql1);

        ps1.setString(1, username);
        ps1.setString(2, password);
        
        ResultSet rs=ps1.executeQuery();
        rs.next();

        int usedId=rs.getInt("id");

        String sql2="INSERT INTO patients"+"(user_id,name,email,phone,date_of_birth,gender,address)"+"VALUES(?,?,?,?,?,?,?)";

        PreparedStatement ps2=connection.prepareStatement(sql2);

        ps2.setInt(1, usedId);
        ps2.setString(2, name);
        ps2.setString(3, email);
        ps2.setString(4, phone);
        ps2.setDate(5, Date.valueOf(dateOfBirth));
        ps2.setString(6, gender);
        ps2.setString(7, address);


        ps2.executeUpdate();

        System.out.println("Patient registered Successfully");
        
        rs.close();
        ps1.close();
        ps2.close();
        connection.close();

    }       



    // * Login for the patient

    public boolean loginPatient(String username,String password) throws Exception{
        Connection connection=DBConnection.getConnection();
        String sql="SELECT id FROM users "+"WHERE username= ? AND password= ? "+"AND role='PATIENT'";
        PreparedStatement ps=connection.prepareStatement(sql);
        ps.setString(1, username);
        ps.setString(2, password);
        ResultSet rs=ps.executeQuery();

        boolean loginSuccess=rs.next();

        rs.close();
        ps.close();
        connection.close();

        return  loginSuccess;
    }


}