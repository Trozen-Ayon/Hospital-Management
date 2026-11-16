package com.hospital;

import java.sql.*;

public class ReviewService {

    public void viewAllReviews() throws Exception {

        Connection connection = DBConnection.getConnection();

        String sql = "SELECT r.id, p.name AS patient_name, " +
                     "d.name AS doctor_name, r.rating, " +
                     "r.comment, r.created_at " +
                     "FROM reviews r " +
                     "JOIN patients p ON r.patient_id = p.id " +
                     "JOIN doctors d ON r.doctor_id = d.id " +
                     "ORDER BY r.id";

        PreparedStatement ps = connection.prepareStatement(sql);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {

            System.out.println("--------------------------");

            System.out.println("Review ID: " + rs.getInt("id"));
            System.out.println("Patient: " + rs.getString("patient_name"));
            System.out.println("Doctor: " + rs.getString("doctor_name"));
            System.out.println("Rating: " + rs.getInt("rating") + "/5");
            System.out.println("Comment: " + rs.getString("comment"));
            System.out.println("Date: " + rs.getTimestamp("created_at"));
        }

        rs.close();
        ps.close();
        connection.close();
    }
}