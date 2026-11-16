package com.hospital;

import java.sql.*;

public class AppointmentService {

    public void viewAllAppointments() throws Exception {

        Connection connection = DBConnection.getConnection();

        String sql = "SELECT a.id, p.name AS patient_name, " +
                     "d.name AS doctor_name, a.appointment_date, " +
                     "a.status, a.reason " +
                     "FROM appointments a " +
                     "JOIN patients p ON a.patient_id = p.id " +
                     "JOIN doctors d ON a.doctor_id = d.id " +
                     "ORDER BY a.id";

        PreparedStatement ps = connection.prepareStatement(sql);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {

            System.out.println("--------------------------");

            System.out.println("Appointment ID: " + rs.getInt("id"));
            System.out.println("Patient: " + rs.getString("patient_name"));
            System.out.println("Doctor: " + rs.getString("doctor_name"));
            System.out.println("Date: " + rs.getTimestamp("appointment_date"));
            System.out.println("Status: " + rs.getString("status"));
            System.out.println("Reason: " + rs.getString("reason"));
        }

        rs.close();
        ps.close();
        connection.close();
    }
}