package com.hospital;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AdminService {

public boolean loginAdmin(String username, String password) throws Exception {

    Connection connection = DBConnection.getConnection();

    String sql = "SELECT id, password FROM users " +
                 "WHERE username = ? " +
                 "AND role = 'ADMIN'";

    PreparedStatement ps = connection.prepareStatement(sql);

    ps.setString(1, username);

    ResultSet rs = ps.executeQuery();

    boolean loginSuccess = false;

    if (rs.next()) {
        String storedHash = rs.getString("password");

        if (PasswordUtil.checkPassword(password, storedHash)) {
            loginSuccess = true;
        }
    }

    rs.close();
    ps.close();
    connection.close();

    return loginSuccess;
}


}