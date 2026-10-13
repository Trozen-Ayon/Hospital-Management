package com.hospital;
// * classes
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

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
}
