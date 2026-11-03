package com.hospital;
import java.sql.Time;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class PatientService{

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




public int loginPatient(String username, String password) throws Exception {

    Connection connection = DBConnection.getConnection();

    String sql = "SELECT patients.id FROM users " +
                 "JOIN patients ON users.id = patients.user_id " +
                 "WHERE users.username = ? AND users.password = ? " +
                 "AND users.role = 'PATIENT'";

    PreparedStatement ps = connection.prepareStatement(sql);

    ps.setString(1, username);
    ps.setString(2, password);

    ResultSet rs = ps.executeQuery();

    int patientId = -1;

    if (rs.next()) {
        patientId = rs.getInt("id");
    }

    rs.close();
    ps.close();
    connection.close();

    return patientId;
}

public void bookAppointment(int patientId, Scanner input) throws Exception {

    Connection connection = DBConnection.getConnection();

    // Show all available doctors
    String sql1 = "SELECT id, name, specialization FROM doctors";

    PreparedStatement ps1 = connection.prepareStatement(sql1);

    ResultSet rs = ps1.executeQuery();

    System.out.println("\nAvailable Doctors");
    System.out.println("----------------------");

    while (rs.next()) {
        System.out.println("Doctor ID: " + rs.getInt("id"));
        System.out.println("Name: " + rs.getString("name"));
        System.out.println("Specialization: " + rs.getString("specialization"));
        System.out.println("----------------------");
    }

    rs.close();
    ps1.close();

    // Take appointment information
    System.out.print("Enter Doctor ID: ");
    int doctorId = input.nextInt();
    input.nextLine();

    System.out.print("Enter Appointment Date (YYYY-MM-DD): ");
    String date = input.nextLine();

    System.out.print("Enter Appointment Time (HH:MM:SS): ");
    String time = input.nextLine();

    System.out.print("Enter Reason: ");
    String reason = input.nextLine();

    // Insert appointment into database
    String sql2 = "INSERT INTO appointments " +
                  "(patient_id, doctor_id, appointment_date, appointment_time, reason, status) " +
                  "VALUES (?, ?, ?, ?, ?, 'PENDING')";

    PreparedStatement ps2 = connection.prepareStatement(sql2);

    ps2.setInt(1, patientId);
    ps2.setInt(2, doctorId);
    ps2.setDate(3, Date.valueOf(date));
    ps2.setTime(4, Time.valueOf(time));
    ps2.setString(5, reason);

    ps2.executeUpdate();

    System.out.println("\nAppointment Booked Successfully!");
    System.out.println("Status: PENDING");

    ps2.close();
    connection.close();
}


public void viewMyAppointments(int patientId) throws Exception {

    Connection connection = DBConnection.getConnection();

    String sql = "SELECT appointments.id, doctors.name, " +
                 "doctors.specialization, appointments.appointment_date, " +
                 "appointments.appointment_time, appointments.reason, " +
                 "appointments.status " +
                 "FROM appointments " +
                 "JOIN doctors ON appointments.doctor_id = doctors.id " +
                 "WHERE appointments.patient_id = ? " +
                 "ORDER BY appointments.appointment_date";

    PreparedStatement ps = connection.prepareStatement(sql);

    ps.setInt(1, patientId);

    ResultSet rs = ps.executeQuery();

    boolean found = false;

    System.out.println("\nMy Appointments");
    System.out.println("----------------------");

    while (rs.next()) {

        found = true;

        System.out.println("Appointment ID: " + rs.getInt("id"));
        System.out.println("Doctor Name: " + rs.getString("name"));
        System.out.println("Specialization: " + rs.getString("specialization"));
        System.out.println("Date: " + rs.getDate("appointment_date"));
        System.out.println("Time: " + rs.getTime("appointment_time"));
        System.out.println("Reason: " + rs.getString("reason"));
        System.out.println("Status: " + rs.getString("status"));

        System.out.println("----------------------");
    }

    if (!found) {
        System.out.println("You have no appointments.");
    }

    rs.close();
    ps.close();
    connection.close();
}



public void cancelAppointment(int patientId, Scanner input) throws Exception {

    System.out.print("Enter Appointment ID: ");
    int appointmentId = input.nextInt();
    input.nextLine();

    Connection connection = DBConnection.getConnection();

    String sql = "UPDATE appointments " +
                 "SET status = 'CANCELLED' " +
                 "WHERE id = ? " +
                 "AND patient_id = ? " +
                 "AND status = 'PENDING'";

    PreparedStatement ps = connection.prepareStatement(sql);

    ps.setInt(1, appointmentId);
    ps.setInt(2, patientId);

    int result = ps.executeUpdate();

    if (result > 0) {
        System.out.println("Appointment Cancelled Successfully!");
    } else {
        System.out.println("Unable to cancel appointment.");
        System.out.println("Appointment not found or cannot be cancelled.");
    }

    ps.close();
    connection.close();
}

public void viewPrescriptions(int patientId) throws Exception {

    Connection connection = DBConnection.getConnection();

    String sql = "SELECT appointments.id, doctors.name, " +
                 "prescriptions.diagnosis, prescriptions.treatment, " +
                 "prescriptions.medicines, prescriptions.instructions, " +
                 "prescriptions.created_at " +
                 "FROM prescriptions " +
                 "JOIN appointments ON prescriptions.appointment_id = appointments.id " +
                 "JOIN doctors ON appointments.doctor_id = doctors.id " +
                 "WHERE appointments.patient_id = ? " +
                 "ORDER BY prescriptions.created_at DESC";

    PreparedStatement ps = connection.prepareStatement(sql);

    ps.setInt(1, patientId);

    ResultSet rs = ps.executeQuery();

    boolean found = false;

    System.out.println("\nMy Prescriptions");
    System.out.println("----------------------");

    while (rs.next()) {

        found = true;

        System.out.println("Appointment ID: " + rs.getInt("id"));
        System.out.println("Doctor Name: " + rs.getString("name"));
        System.out.println("Diagnosis: " + rs.getString("diagnosis"));
        System.out.println("Treatment: " + rs.getString("treatment"));
        System.out.println("Medicines: " + rs.getString("medicines"));
        System.out.println("Instructions: " + rs.getString("instructions"));
        System.out.println("Created At: " + rs.getTimestamp("created_at"));

        System.out.println("----------------------");
    }

    if (!found) {
        System.out.println("No prescriptions found.");
    }

    rs.close();
    ps.close();
    connection.close();
}

public void giveReview(int patientId, Scanner input) throws Exception {

    System.out.print("Enter Appointment ID: ");
    int appointmentId = input.nextInt();

    System.out.print("Give Rating (1-5): ");
    int rating = input.nextInt();
    input.nextLine();

    if (rating < 1 || rating > 5) {
        System.out.println("Rating must be between 1 and 5!");
        return;
    }

    System.out.print("Enter Comment: ");
    String comment = input.nextLine();

    Connection connection = DBConnection.getConnection();

    String sql = "INSERT INTO reviews " +
                 "(appointment_id, patient_id, doctor_id, rating, comment) " +
                 "SELECT id, patient_id, doctor_id, ?, ? " +
                 "FROM appointments " +
                 "WHERE id = ? AND patient_id = ? " +
                 "AND status = 'COMPLETED' " +
                 "AND NOT EXISTS (" +
                 "SELECT 1 FROM reviews WHERE appointment_id = ?" +
                 ")";

    PreparedStatement ps = connection.prepareStatement(sql);

    ps.setInt(1, rating);
    ps.setString(2, comment);
    ps.setInt(3, appointmentId);
    ps.setInt(4, patientId);
    ps.setInt(5, appointmentId);

    int result = ps.executeUpdate();

    if (result > 0) {
        System.out.println("Review Submitted Successfully!");
    } else {
        System.out.println("Unable to submit review.");
        System.out.println("Appointment may not be completed, may not belong to you, or already has a review.");
    }

    ps.close();
    connection.close();
}

}