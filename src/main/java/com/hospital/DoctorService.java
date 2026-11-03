package com.hospital;

// * classes
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class DoctorService {
    public void viewDoctor() throws Exception{

        Connection connection=DBConnection.getConnection();
        Statement statement=connection.createStatement();

        ResultSet result=statement.executeQuery("SELECT doctors.name, doctors.email,doctors.phone,doctors.specialization,departments.name AS department FROM doctors LEFT JOIN departments ON doctors.department_id=departments.id");
        while(result.next()){
            System.out.println("<----------->");
            System.out.println("Name : "+result.getString("name"));
            System.out.println("Email : "+result.getString("email"));
            System.out.println("Phone : "+result.getString("phone"));
            System.out.println("Specialization: "+result.getString("specialization"));
            System.out.println("Department : "+result.getString("department"));
        }
        connection.close();
    }


    public void registerDoctor(Scanner input) throws Exception {

    System.out.println("\nDoctor Registration");

    System.out.print("Enter username: ");
    String username = input.nextLine();

    System.out.print("Enter password: ");
    String password = input.nextLine();

    System.out.print("Enter doctor name: ");
    String name = input.nextLine();

    System.out.print("Enter email: ");
    String email = input.nextLine();

    System.out.print("Enter phone: ");
    String phone = input.nextLine();

    System.out.print("Enter specialization: ");
    String specialization = input.nextLine();

    Connection connection = DBConnection.getConnection();

    try {
        // First, create user account
        String sql1 = "INSERT INTO users (username, password, role) " +
                      "VALUES (?, ?, 'DOCTOR') RETURNING id";

        PreparedStatement ps1 = connection.prepareStatement(sql1);

        ps1.setString(1, username);
        ps1.setString(2, password);

        ResultSet rs = ps1.executeQuery();

        rs.next();
        int userId = rs.getInt("id");

        rs.close();
        ps1.close();

        // Show available departments
        String sql2 = "SELECT id, name FROM departments";

        PreparedStatement ps2 = connection.prepareStatement(sql2);

        ResultSet rs2 = ps2.executeQuery();

        System.out.println("\nAvailable Departments");

        while (rs2.next()) {
            System.out.println(
                rs2.getInt("id") + ". " + rs2.getString("name")
            );
        }

        rs2.close();
        ps2.close();

        System.out.print("Enter Department ID: ");
        int departmentId = input.nextInt();
        input.nextLine();

        // Insert doctor information
        String sql3 = "INSERT INTO doctors " +
                      "(user_id, name, email, phone, specialization, department_id) " +
                      "VALUES (?, ?, ?, ?, ?, ?)";

        PreparedStatement ps3 = connection.prepareStatement(sql3);

        ps3.setInt(1, userId);
        ps3.setString(2, name);
        ps3.setString(3, email);
        ps3.setString(4, phone);
        ps3.setString(5, specialization);
        ps3.setInt(6, departmentId);

        ps3.executeUpdate();

        System.out.println("\nDoctor Registered Successfully!");

        ps3.close();

    } catch (Exception e) {
        System.out.println("Doctor Registration Failed!");
        throw e;

    } finally {
        connection.close();
    }
}







    public int loginDoctor(String username, String password) throws Exception {

    Connection connection = DBConnection.getConnection();

    String sql = "SELECT doctors.id FROM users " +
                 "JOIN doctors ON users.id = doctors.user_id " +
                 "WHERE users.username = ? AND users.password = ? " +
                 "AND users.role = 'DOCTOR'";

    PreparedStatement ps = connection.prepareStatement(sql);

    ps.setString(1, username);
    ps.setString(2, password);

    ResultSet rs = ps.executeQuery();

    int doctorId = -1;

    if (rs.next()) {
        doctorId = rs.getInt("id");
    }

    rs.close();
    ps.close();
    connection.close();

    return doctorId;
}



public void viewMyAppointments(int doctorId) throws Exception {

    Connection connection = DBConnection.getConnection();

    String sql = "SELECT appointments.id, patients.name, " +
                 "patients.phone, appointments.appointment_date, " +
                 "appointments.appointment_time, appointments.reason, " +
                 "appointments.status " +
                 "FROM appointments " +
                 "JOIN patients ON appointments.patient_id = patients.id " +
                 "WHERE appointments.doctor_id = ? " +
                 "ORDER BY appointments.appointment_date";

    PreparedStatement ps = connection.prepareStatement(sql);

    ps.setInt(1, doctorId);

    ResultSet rs = ps.executeQuery();

    boolean found = false;

    System.out.println("\nMy Appointments");
    System.out.println("----------------------");

    while (rs.next()) {

        found = true;

        System.out.println("Appointment ID: " + rs.getInt("id"));
        System.out.println("Patient Name: " + rs.getString("name"));
        System.out.println("Patient Phone: " + rs.getString("phone"));
        System.out.println("Date: " + rs.getDate("appointment_date"));
        System.out.println("Time: " + rs.getTime("appointment_time"));
        System.out.println("Reason: " + rs.getString("reason"));
        System.out.println("Status: " + rs.getString("status"));

        System.out.println("----------------------");
    }

    if (!found) {
        System.out.println("No appointments found.");
    }

    rs.close();
    ps.close();
    connection.close();
}

public void acceptAppointment(int doctorId, Scanner input) throws Exception {

    System.out.print("Enter Appointment ID: ");
    int appointmentId = input.nextInt();
    input.nextLine();

    Connection connection = DBConnection.getConnection();

    String sql = "UPDATE appointments " +
                 "SET status = 'ACCEPTED' " +
                 "WHERE id = ? " +
                 "AND doctor_id = ? " +
                 "AND status = 'PENDING'";

    PreparedStatement ps = connection.prepareStatement(sql);

    ps.setInt(1, appointmentId);
    ps.setInt(2, doctorId);

    int result = ps.executeUpdate();

    if (result > 0) {
        System.out.println("Appointment Accepted Successfully!");
    } else {
        System.out.println("Unable to accept appointment.");
        System.out.println("Appointment not found or already processed.");
    }

    ps.close();
    connection.close();
}


public void completeAppointment(int doctorId, Scanner input) throws Exception {

    System.out.print("Enter Appointment ID: ");
    int appointmentId = input.nextInt();
    input.nextLine();

    System.out.print("Enter Diagnosis: ");
    String diagnosis = input.nextLine();

    System.out.print("Enter Treatment Notes: ");
    String treatment = input.nextLine();

    System.out.print("Enter Medicines: ");
    String medicines = input.nextLine();

    System.out.print("Enter Instructions: ");
    String instructions = input.nextLine();

    Connection connection = DBConnection.getConnection();

    try {

        // Check whether this appointment belongs to this doctor
        String checkSql = "SELECT id FROM appointments " +
                          "WHERE id = ? AND doctor_id = ? " +
                          "AND status = 'ACCEPTED'";

        PreparedStatement checkPs = connection.prepareStatement(checkSql);

        checkPs.setInt(1, appointmentId);
        checkPs.setInt(2, doctorId);

        ResultSet rs = checkPs.executeQuery();

        if (!rs.next()) {
            System.out.println("Appointment not found or not accepted!");
            rs.close();
            checkPs.close();
            return;
        }

        rs.close();
        checkPs.close();

  
        String sql1 = "INSERT INTO prescriptions " +
                      "(appointment_id, diagnosis, treatment, medicines, instructions) " +
                      "VALUES (?, ?, ?, ?, ?)";

        PreparedStatement ps1 = connection.prepareStatement(sql1);

        ps1.setInt(1, appointmentId);
        ps1.setString(2, diagnosis);
        ps1.setString(3, treatment);
        ps1.setString(4, medicines);
        ps1.setString(5, instructions);

        ps1.executeUpdate();

        ps1.close();

        // Update appointment status
        String sql2 = "UPDATE appointments " +
                      "SET status = 'COMPLETED' " +
                      "WHERE id = ? AND doctor_id = ?";

        PreparedStatement ps2 = connection.prepareStatement(sql2);

        ps2.setInt(1, appointmentId);
        ps2.setInt(2, doctorId);

        ps2.executeUpdate();

        ps2.close();

        System.out.println("\nAppointment Completed Successfully!");
        System.out.println("Diagnosis and Treatment Saved!");

    } finally {
        connection.close();
    }
}


public void viewMyReviews(int doctorId) throws Exception {

    Connection connection = DBConnection.getConnection();

    // Calculate average rating
    String sql1 = "SELECT AVG(rating) AS average_rating, " +
                  "COUNT(*) AS total_reviews " +
                  "FROM reviews " +
                  "WHERE doctor_id = ?";

    PreparedStatement ps1 = connection.prepareStatement(sql1);

    ps1.setInt(1, doctorId);

    ResultSet rs1 = ps1.executeQuery();

    if (rs1.next()) {

        double averageRating = rs1.getDouble("average_rating");
        int totalReviews = rs1.getInt("total_reviews");

        System.out.println("\nMy Rating");
        System.out.println("----------------------");
        System.out.println("Average Rating: " + averageRating + " / 5");
        System.out.println("Total Reviews: " + totalReviews);
    }

    rs1.close();
    ps1.close();

    // Show patient comments
    String sql2 = "SELECT patients.name, reviews.rating, " +
                  "reviews.comment, reviews.created_at " +
                  "FROM reviews " +
                  "JOIN patients ON reviews.patient_id = patients.id " +
                  "WHERE reviews.doctor_id = ? " +
                  "ORDER BY reviews.created_at DESC";

    PreparedStatement ps2 = connection.prepareStatement(sql2);

    ps2.setInt(1, doctorId);

    ResultSet rs2 = ps2.executeQuery();

    System.out.println("\nPatient Reviews");
    System.out.println("----------------------");

    boolean found = false;

    while (rs2.next()) {

        found = true;

        System.out.println("Patient Name: " + rs2.getString("name"));
        System.out.println("Rating: " + rs2.getInt("rating") + " / 5");
        System.out.println("Comment: " + rs2.getString("comment"));
        System.out.println("Date: " + rs2.getTimestamp("created_at"));

        System.out.println("----------------------");
    }

    if (!found) {
        System.out.println("No reviews available.");
    }

    rs2.close();
    ps2.close();
    connection.close();
}


}
