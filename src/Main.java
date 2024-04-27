


import java.util.InputMismatchException;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StudentManager studentManager = new StudentManager();
        studentManager.addStudentFile("Student.txt");

        while (true) {
            System.out.println("1. Show student details");
            System.out.println("2. Add Student");
            System.out.println("3. Save Student");
            System.out.println("4. Delete Student");
            System.out.println("5. Search Student by Name");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            try {
                int choices = sc.nextInt();
                sc.nextLine();

                switch (choices) {
                    case 1:
                        studentManager.showStudent();
                        break;

                    case 2:
                        try {
                            String typeOfStudent;
                            boolean validType;
                            do {
                                System.out.print("Undergraduate or Graduate : ");
                                typeOfStudent = sc.nextLine();

                                validType = typeOfStudent.equalsIgnoreCase("Undergraduate") || typeOfStudent.equalsIgnoreCase("Graduate");

                                if (!validType) {
                                    System.out.println("Invalid input. Please enter either 'Undergraduate' or 'Graduate'.");
                                }
                            } while (!validType);

                            System.out.print("Name: ");
                            String name = sc.nextLine();

                            if (!name.matches("[a-zA-Z\\s]+")) {  
                                System.out.println("Invalid input. Please enter only alphabetic characters for the name.");
                                break; 
                            }

                            System.out.print("Id : ");
                            long id;
                            try {
                                id = sc.nextLong();
                                sc.nextLine(); // Consume newline
                            } catch (InputMismatchException e) {
                                System.out.println("Invalid input. Please enter a valid integer ID.");
                                sc.nextLine(); // Clear input buffer
                                break; // Exit the current case to prevent further execution
                            }

                            if (typeOfStudent.equalsIgnoreCase("Undergraduate")) {
                                System.out.print("Enter your major: ");
                                String major = sc.nextLine();

                                if (!major.matches("[a-zA-Z\\s]+")) { 
                                    System.out.println("Invalid input. Please enter only alphabetic characters for the major.");
                                    break; 
                                }

                                studentManager.addStudent(new Undergraduate(name, id, major));
                            } else if (typeOfStudent.equalsIgnoreCase("Graduate")) {
                                System.out.print("Enter your job: ");
                                String job = sc.nextLine();

                                if (!job.matches("[a-zA-Z\\s]+")) { 
                                    System.out.println("Invalid input. Please enter only alphabetic characters for the job.");
                                    break;
                                }

                                studentManager.addStudent(new Graduate(name, id, job));
                            }
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid input. Please enter valid data.");
                            sc.nextLine(); // Clear input buffer
                        }
                        break;


                    case 3:
                        studentManager.saveStudentToFile("Student.txt");
                        System.out.println("Students data saved successfully.");
                        break;

                    case 4:
                        String type;
                        boolean validType;
                        do {
                            System.out.print("Enter 'Undergraduate' or 'Graduate' to specify the type of student you want to delete: ");
                            type = sc.nextLine();
                            validType = type.equalsIgnoreCase("Undergraduate") || type.equalsIgnoreCase("Graduate");
                            if (!validType) {
                                System.out.println("Invalid input. Please enter either 'Undergraduate' or 'Graduate'. Try again.");
                            }
                        } while (!validType);

                        System.out.print("Enter the ID of the student you want to delete: ");
                        long ID;
                        while (true) {
                            try {
                                ID = sc.nextLong();
                                sc.nextLine(); 
                                break;  
                            } catch (InputMismatchException e) {
                                System.out.println("Invalid input. Please enter a valid integer ID. Try again.");
                                sc.nextLine(); 
                            }
                        }

                        studentManager.deleteStudentById(type, ID);
                        break;

                    case 5:
                        System.out.print("Enter name to search for a student: ");
                        String nameKeyword = sc.nextLine();

                        if (!nameKeyword.matches("[a-zA-Z\\s]+")) {  
                            System.out.println("Invalid input. Please enter only alphabetic characters.");
                            break;
                        }

                        studentManager.searchStudentByName(nameKeyword);
                        break;
                    case 6:    
                        System.out.println("Exiting...");
                        System.exit(0);
                        break;    


                    default:
                        System.out.println("Invalid choice");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input");
                sc.nextLine();                                       
            }
        }
    }

    private static void deleteStudent(Scanner sc, StudentManager studentManager) {
        System.out.print("Enter 'Undergraduate' or 'Graduate' to specify the type of student you want to delete: ");
        String type = sc.nextLine();
        System.out.print("Enter the ID of the student you want to delete: ");
        int id = sc.nextInt();
        sc.nextLine();

        studentManager.deleteStudentById(type, id);
    }
}
