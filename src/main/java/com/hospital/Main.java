package com.hospital;

import java.util.Scanner;


public  class Main{
    public static void main(String[] args) {
        Scanner input=new Scanner(System.in);
        // * taking input from the user

        System.out.println("Welcome to hospital Management System");
        System.out.println("1. Patient Login");
        System.out.println("2. Doctor Login");
        System.out.println("3. Admin Login");
        System.out.println("4. Exit ");
        System.out.println("5. Patient Registration");

        System.out.println("Enter your choice : ");
        int choice=input.nextInt();
        input.nextLine();

  if (choice == 1) {
    System.out.println("<---------------------->");

    System.out.println("Enter username: ");
    String username = input.nextLine();

    System.out.println("Enter password: ");
    String password = input.nextLine();

    PatientService patientService = new PatientService();

    try {
        if (patientService.loginPatient(username, password)) {

            System.out.println("<---------------------->");
            System.out.println("Patient Login Successful");
            System.out.println("Patient Dashboard");
            System.out.println("1. View Doctors");
            System.out.println("2. Book Appointment");
            System.out.println("3. View My Appointments");
            System.out.println("4. Logout");

            System.out.println("Enter your choice: ");
            int patient_choice = input.nextInt();
            input.nextLine();

            if (patient_choice == 1) {
                DoctorService doctorService = new DoctorService();

                try {
                    doctorService.viewDoctor();
                } catch (Exception e) {
                    System.out.println("Unable to load doctors.");
                    System.out.println(e.getMessage());
                }

            } else if (patient_choice == 2) {
                System.out.println("Book Appointment Selected");

            } else if (patient_choice == 3) {
                System.out.println("View My Appointments Selected");

            } else if (patient_choice == 4) {
                System.out.println("Logout Selected");

            } else {
                System.out.println("Invalid Choice");
            }

        } else {
            System.out.println("Invalid username or password");
        }

    } catch (Exception e) {
        System.out.println("Login Failed!");
        System.out.println(e.getMessage());
    }

} else if (choice == 2) {
    System.out.println("Doctor Login Selected");

} else if (choice == 3) {
    System.out.println("Admin Login Selected");

} else if (choice == 4) {
    System.out.println("Goodbye!");

} else if (choice == 5) {
    PatientService patientService = new PatientService();

    try {
        patientService.registerPatient(input);
    } catch (Exception e) {
        System.out.println("Registration Failed!");
        System.out.println(e.getMessage());
    }

} else {
    System.out.println("Invalid choice!");
}

    }
}