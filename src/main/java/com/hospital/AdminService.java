package com.hospital;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AdminService {

    public boolean loginAdmin(String username, String password) throws Exception {

        Connection connection = DBConnection.getConnection();

        String sql = "SELECT id FROM users " +
                     "WHERE username = ? AND password = ? " +
                     "AND role = 'ADMIN'";

        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setString(1, username);
        ps.setString(2, password);

        ResultSet rs = ps.executeQuery();

        boolean loginSuccess = rs.next();

        rs.close();
        ps.close();
        connection.close();

        return loginSuccess;
    }
}