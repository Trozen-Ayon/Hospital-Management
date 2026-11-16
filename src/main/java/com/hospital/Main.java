package com.hospital;
import java.io.Console;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        while (true) {

            System.out.println("\n===== HOSPITAL MANAGEMENT SYSTEM =====");
            System.out.println("1. Patient Login");
            System.out.println("2. Doctor Login");
            System.out.println("3. Admin Login");
            System.out.println("4. Exit");
            System.out.println("5. Patient Registration");
            System.out.println("6. Doctor Registration");

            System.out.print("Enter your choice: ");

            int choice = input.nextInt();
            input.nextLine();

            try {
                if (choice == 1) {
                    patientLogin(input);
                }
                else if (choice == 2) {
                    doctorLogin(input);
                }
                else if (choice == 3) {
                    adminLogin(input);
                }
                else if (choice == 4) {
                    System.out.println("Goodbye!");
                    break;
                }
                else if (choice == 5) {
                    PatientService patientService = new PatientService();
                    patientService.registerPatient(input);
                }
                else if (choice == 6) {
                    DoctorService doctorService = new DoctorService();
                    doctorService.registerDoctor(input);
                }
                else {
                    System.out.println("Invalid Choice!");
                }
            }
            catch (Exception e) {
                System.out.println("Operation Failed!");
                System.out.println(e.getMessage());
            }
        }

        input.close();
    }



    public static void patientLogin(Scanner input) throws Exception {

        System.out.println("\n===== PATIENT LOGIN =====");

        System.out.print("Enter username: ");
        String username = input.nextLine();

        String password = readPassword("Enter Password: ");

        PatientService patientService = new PatientService();

        int patientId = patientService.loginPatient(username, password);

        if (patientId == -1) {
            System.out.println("Invalid username or password!");
            return;
        }

        System.out.println("Patient Login Successful!");

        while (true) {

            System.out.println("\n===== PATIENT DASHBOARD =====");
            System.out.println("1. View Doctors");
            System.out.println("2. Book Appointment");
            System.out.println("3. View My Appointments");
            System.out.println("4. Cancel My Appointments");
            System.out.println("5. View My Prescriptions");
            System.out.println("6. Give Review & Rating");
            System.out.println("7. Logout");

            System.out.print("Enter your choice: ");

            int patientChoice = input.nextInt();
            input.nextLine();

            try {
                if (patientChoice == 1) {
                    DoctorService doctorService = new DoctorService();
                    doctorService.viewDoctor();
                }
                else if (patientChoice == 2) {
                    patientService.bookAppointment(patientId, input);
                }
                else if (patientChoice == 3) {
                    patientService.viewMyAppointments(patientId);
                }
                else if (patientChoice == 4) {
                    patientService.cancelAppointment(patientId, input);
                }
                else if (patientChoice == 5) {
                    patientService.viewPrescriptions(patientId);
                }
                else if (patientChoice == 6) {
                    patientService.giveReview(patientId, input);
                }
                else if (patientChoice == 7) {
                    System.out.println("Patient Logout Successful!");
                    break;
                }
                else {
                    System.out.println("Invalid Choice!");
                }
            }
            catch (Exception e) {
                System.out.println("Operation Failed!");
                System.out.println(e.getMessage());
            }
        }
    }



    public static void doctorLogin(Scanner input) throws Exception {

        System.out.println("\n===== DOCTOR LOGIN =====");

        System.out.print("Enter username: ");
        String username = input.nextLine();

        String password = readPassword("Enter Password: ");

        DoctorService doctorService = new DoctorService();

        int doctorId = doctorService.loginDoctor(username, password);

        if (doctorId == -1) {
            System.out.println("Invalid username or password!");
            return;
        }

        System.out.println("Doctor Login Successful!");

        while (true) {

            System.out.println("\n===== DOCTOR DASHBOARD =====");
            System.out.println("1. View My Appointments");
            System.out.println("2. Accept Appointment");
            System.out.println("3. Complete Appointment");
            System.out.println("4. View My Reviews");
            System.out.println("5. Logout");

            System.out.print("Enter your choice: ");

            int doctorChoice = input.nextInt();
            input.nextLine();

            try {
                if (doctorChoice == 1) {
                    doctorService.viewMyAppointments(doctorId);
                }
                else if (doctorChoice == 2) {
                    doctorService.acceptAppointment(doctorId, input);
                }
                else if (doctorChoice == 3) {
                    doctorService.completeAppointment(doctorId, input);
                }
                else if (doctorChoice == 4) {
                    doctorService.viewMyReviews(doctorId);
                }
                else if (doctorChoice == 5) {
                    System.out.println("Doctor Logout Successful!");
                    break;
                }
                else {
                    System.out.println("Invalid Choice!");
                }
            }
            catch (Exception e) {
                System.out.println("Operation Failed!");
                System.out.println(e.getMessage());
            }
        }
    }


    public static void adminLogin(Scanner input) throws Exception {

        System.out.println("\n===== ADMIN LOGIN =====");

        System.out.print("Enter username: ");
        String username = input.nextLine();

        // * Enter password
        String password = readPassword("Enter Password: ");

        AdminService adminService = new AdminService();

        if (!adminService.loginAdmin(username, password)) {
            System.out.println("Invalid username or password!");
            return;
        }

        System.out.println("Admin Login Successful!");

        while (true) {

            System.out.println("\n===== ADMIN DASHBOARD =====");
            System.out.println("1. Manage Doctors");
            System.out.println("2. Manage Patients");
            System.out.println("3. Manage Departments");
            System.out.println("4. View All Appointments");
            System.out.println("5. Manage Reviews");
            System.out.println("6. Logout");

            System.out.print("Enter your choice: ");

            int adminChoice = input.nextInt();
            input.nextLine();

            try {
                if (adminChoice == 1) {
                    manageDoctors(input);
                }
                else if (adminChoice == 2) {
                    managePatients(input);
                }
                else if (adminChoice == 3) {
                    manageDepartments(input);
                }
                else if (adminChoice == 4) {
                    AppointmentService appointmentService = new AppointmentService();
                    appointmentService.viewAllAppointments();
                }
                else if (adminChoice == 5) {
                    ReviewService reviewService = new ReviewService();
                    reviewService.viewAllReviews();
                }
                else if (adminChoice == 6) {
                    System.out.println("Admin Logout Successful!");
                    break;
                }
                else {
                    System.out.println("Invalid Choice!");
                }
            }
            catch (Exception e) {
                System.out.println("Operation Failed!");
                System.out.println(e.getMessage());
            }
        }
    }


    public static void manageDoctors(Scanner input) throws Exception {

        DoctorService doctorService = new DoctorService();

        while (true) {

            System.out.println("\n===== MANAGE DOCTORS =====");
            System.out.println("1. Add Doctor");
            System.out.println("2. View All Doctors");
            System.out.println("3. Update Doctor");
            System.out.println("4. Delete Doctor");
            System.out.println("5. Back");

            System.out.print("Enter your choice: ");

            int doctorChoice = input.nextInt();
            input.nextLine();

            if (doctorChoice == 1) {
                doctorService.registerDoctor(input);
            }
            else if (doctorChoice == 2) {
                doctorService.viewDoctor();
            }
            else if (doctorChoice == 3) {
                doctorService.updateDoctor(input);
            }
            else if (doctorChoice == 4) {
                doctorService.deleteDoctor(input);
            }
            else if (doctorChoice == 5) {
                System.out.println("Returning to Admin Dashboard...");
                break;
            }
            else {
                System.out.println("Invalid Choice!");
            }
        }
    }


    public static void managePatients(Scanner input) throws Exception {

        PatientService patientService = new PatientService();

        while (true) {

            System.out.println("\n===== MANAGE PATIENTS =====");
            System.out.println("1. View All Patients");
            System.out.println("2. Back");

            System.out.print("Enter your choice: ");

            int patientChoice = input.nextInt();
            input.nextLine();

            if (patientChoice == 1) {
                patientService.viewAllPatients();
            }
            else if (patientChoice == 2) {
                System.out.println("Returning to Admin Dashboard...");
                break;
            }
            else {
                System.out.println("Invalid Choice!");
            }
        }
    }

 

    public static void manageDepartments(Scanner input) throws Exception {

        DepartmentService departmentService = new DepartmentService();

        while (true) {

            System.out.println("\n===== MANAGE DEPARTMENTS =====");
            System.out.println("1. Add Department");
            System.out.println("2. View All Departments");
            System.out.println("3. Back");

            System.out.print("Enter your choice: ");

            int departmentChoice = input.nextInt();
            input.nextLine();

            if (departmentChoice == 1) {
                departmentService.addDepartment(input);
            }
            else if (departmentChoice == 2) {
                departmentService.viewAllDepartments();
            }
            else if (departmentChoice == 3) {
                System.out.println("Returning to Admin Dashboard...");
                break;
            }
            else {
                System.out.println("Invalid Choice!");
            }
        }
    }
  
  
    public static String readPassword(String message) {
    Console console = System.console();

    if (console == null) {
        System.out.println("Run the program in VS Code Terminal.");
        return "";
    }

    return new String(console.readPassword(message));
}
}
