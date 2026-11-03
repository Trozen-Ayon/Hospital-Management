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
        System.out.println("6. Doctor Registration");

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
        int patientId = patientService.loginPatient(username, password);
        if (patientId != -1) {

            System.out.println("<---------------------->");
            System.out.println("Patient Login Successful");
            System.out.println("Patient Dashboard");
            System.out.println("1. View Doctors");
            System.out.println("2. Book Appointment");
            System.out.println("3. View My Appointments");
            System.out.println("4. Cancel My Appointments");
            System.out.println("5. View My Prescriptions");
            System.out.println("6. Give Review & Rating");

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
                PatientService patientService2 = new PatientService();

               try{
                   patientService2.bookAppointment(patientId, input);
                } catch (Exception e) {
                    System.out.println("Appointment Booking Failed!");
                System.out.println(e.getMessage());
    }
                

            } else if (patient_choice == 3) {
                System.out.println("View My Appointments Selected");
                  try {
                patientService.viewMyAppointments(patientId);
                } catch (Exception e) {
                 System.out.println("Unable to load appointments.");
                System.out.println(e.getMessage());
            }

            } else if (patient_choice == 4) {
      
                  try {
             patientService.cancelAppointment(patientId, input);
                 } catch (Exception e) {
             System.out.println("Cancellation Failed!");
                System.out.println(e.getMessage());
    }

            } else if(patient_choice == 5) {
               try {
        patientService.viewPrescriptions(patientId);
    } catch (Exception e) {
        System.out.println("Unable to load prescriptions.");
        System.out.println(e.getMessage());
    }
            }
            else if(patient_choice == 6){

    try {
        patientService.giveReview(patientId, input);
    } catch (Exception e) {
        System.out.println("Review Submission Failed!");
        System.out.println(e.getMessage());
    }
            }

        } else {
            System.out.println("Invalid username or password");
        }

    } catch (Exception e) {
        System.out.println("Login Failed!");
        System.out.println(e.getMessage());
    }

} else if (choice == 2) {
 
     System.out.println("Doctor Login");

    System.out.print("Enter username: ");
    String username = input.nextLine();

    System.out.print("Enter password: ");
    String password = input.nextLine();

    DoctorService doctorService = new DoctorService();

    try {

        int doctorId = doctorService.loginDoctor(username, password);

        if (doctorId != -1) {

            System.out.println("Doctor Login Successful!");

            System.out.println("\nDoctor Dashboard");
            System.out.println("1. View My Appointments");
            System.out.println("2. Accept Appointment");
            System.out.println("3. Complete Appointment");
            System.out.println("4. View My Reviews");
            System.out.println("5. Logout");

            System.out.print("Enter your choice: ");
            int doctorChoice = input.nextInt();
            input.nextLine();

            if (doctorChoice == 1) {
               try {
           doctorService.viewMyAppointments(doctorId);
              } catch (Exception e) {
              System.out.println("Unable to load appointments.");
            System.out.println(e.getMessage());
    }
            } else if (doctorChoice == 2) {
                try {
             doctorService.acceptAppointment(doctorId, input);
          } catch (Exception e) {
          System.out.println("Unable to accept appointment.");
          System.out.println(e.getMessage());
    }
            } else if (doctorChoice == 3) {
                try {
          doctorService.completeAppointment(doctorId, input);
             } catch (Exception e) {
           System.out.println("Unable to complete appointment.");
          System.out.println(e.getMessage());
    }
            } else if (doctorChoice == 4) {
                  try {
        doctorService.viewMyReviews(doctorId);
    } catch (Exception e) {
        System.out.println("Unable to load reviews.");
        System.out.println(e.getMessage());
    }
                
            } else {
                System.out.println("Invalid Choice!");
            }

        } else {
            System.out.println("Invalid username or password!");
        }

    } catch (Exception e) {
        System.out.println("Doctor Login Failed!");
        System.out.println(e.getMessage());
    }

} else if (choice == 3) {
    System.out.println("Admin Login Selected");
    System.out.println("\nAdmin Login");

    System.out.print("Enter username: ");
    String username = input.nextLine();

    System.out.print("Enter password: ");
    String password = input.nextLine();

    AdminService adminService = new AdminService();

    try {

        if (adminService.loginAdmin(username, password)) {

            System.out.println("\nAdmin Login Successful!");

            System.out.println("\nAdmin Dashboard");
            System.out.println("1. Manage Doctors");
            System.out.println("2. Manage Patients");
            System.out.println("3. Manage Departments");
            System.out.println("4. View All Appointments");
            System.out.println("5. Manage Reviews");
            System.out.println("6. Logout");

            System.out.print("Enter your choice: ");
            int adminChoice = input.nextInt();
            input.nextLine();

            if (adminChoice == 1) {
                System.out.println("Manage Doctors Selected");
            } else if (adminChoice == 2) {
                System.out.println("Manage Patients Selected");
            } else if (adminChoice == 3) {
                System.out.println("Manage Departments Selected");
            } else if (adminChoice == 4) {
                System.out.println("View All Appointments Selected");
            } else if (adminChoice == 5) {
                System.out.println("Manage Reviews Selected");
            } else if (adminChoice == 6) {
                System.out.println("Logout Successful!");
            } else {
                System.out.println("Invalid Choice!");
            }

        } else {
            System.out.println("Invalid username or password!");
        }

    } catch (Exception e) {
        System.out.println("Admin Login Failed!");
        System.out.println(e.getMessage());
    }

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

}  else if (choice == 6) {

    DoctorService doctorService = new DoctorService();

    try {
        doctorService.registerDoctor(input);
    } catch (Exception e) {
        System.out.println(e.getMessage());
    }
}else{
    System.out.println("Invalid Choice!!");
}

    }
}