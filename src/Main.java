import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

interface Student {
    void showStudentDetails();
}

class Undergraduate implements Student {
    String name;
    String major;
    long id;

    Undergraduate(String name, long id, String major) {
        this.name = name;
        this.id = id;
        this.major = major;
    }

    @Override
    public void showStudentDetails() {
        System.out.println("Undergraduate");
        System.out.println("Name : " + name);
        System.out.println("Id : " + id);
        System.out.println("Major : " + major);
    }
}

class Graduate implements Student {
    String name;
    String job;
    long id;

    Graduate(String name, long id, String job) {
        this.name = name;
        this.id = id;
        this.job = job;
    }

    @Override
    public void showStudentDetails() {
        System.out.println("Graduate");
        System.out.println("Name: " + name);
        System.out.println("ID: " + id);
        System.out.println("Job: " + job);
    }
}

class StudentManager {
    private List<Student> studentList;

    StudentManager() {
        studentList = new ArrayList<>();
    }

    public void showStudent() {
        if(studentList.isEmpty()){
            System.out.println("There is no student list");
        } else {
            for (Student student : studentList) {
                student.showStudentDetails();
                System.out.println("_________");
            }
        }
    }

    public void addStudent(Student student) {
        studentList.add(student);
    }

    void addStudentFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String type = parts[0];
                if (type.equalsIgnoreCase("Undergraduate")) {
                    String name = parts[1];
                    long id = Long.parseLong(parts[2]);
                    String major = parts[3];
                    studentList.add(new Undergraduate(name, id, major));
                } else if (type.equalsIgnoreCase("Graduate")) {
                    String name = parts[1];
                    long id = Long.parseLong(parts[2]);
                    String job = parts[3];
                    studentList.add(new Graduate(name, id, job));
                }
            }
        } catch (Exception e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    public void deleteStudentById(String type, long id) {
        boolean found = false;
        for (int i = 0; i < studentList.size(); i++) {
            Student student = studentList.get(i);
            if (type.equalsIgnoreCase("Undergraduate") && student instanceof Undergraduate && ((Undergraduate) student).id == id) {
                studentList.remove(i);
                System.out.println("Undergraduate student with ID " + id + " deleted.");
                found = true;
                break;
            } else if (type.equalsIgnoreCase("Graduate") && student instanceof Graduate && ((Graduate) student).id == id) {
                studentList.remove(i);
                System.out.println("Graduate student with ID " + id + " deleted.");
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Student with ID " + id + " not found.");
        }
    }

    public void searchStudentByName(String name) {
        boolean found = false;
        for (Student student : studentList) {
            if (student instanceof Undergraduate && ((Undergraduate) student).name.equalsIgnoreCase(name)) {
                student.showStudentDetails();
                found = true;
            } else if (student instanceof Graduate && ((Graduate) student).name.equalsIgnoreCase(name)) {
                student.showStudentDetails();
                found = true;
            }
        }
        if (!found) {
            System.out.println("No student found with the name: " + name);
        }
    }

    void saveStudentToFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Student student : studentList) {
                if (student instanceof Undergraduate) {
                    writer.write("Undergraduate," + ((Undergraduate) student).name + "," + ((Undergraduate) student).id + "," + ((Undergraduate) student).major);
                } else if (student instanceof Graduate) {
                    writer.write("Graduate," + ((Graduate) student).name + "," + ((Graduate) student).id + "," + ((Graduate) student).job);
                }
                writer.newLine();
            }
        } catch (Exception e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }
}


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
                                sc.nextLine(); 
                                break; 
                            }

                            if (typeOfStudent.equalsIgnoreCase("Undergraduate")) {
                                System.out.print("Enter your major: ");
                                String major = sc.nextLine();

                                if (!major.matches("[a-zA-Z\\s]+")) {  // Allows spaces in majors
                                    System.out.println("Invalid input. Please enter only alphabetic characters for the major.");
                                    break; // Exit the current case to prevent further execution
                                }

                                studentManager.addStudent(new Undergraduate(name, id, major));
                            } else if (typeOfStudent.equalsIgnoreCase("Graduate")) {
                                System.out.print("Enter your job: ");
                                String job = sc.nextLine();

                                if (!job.matches("[a-zA-Z\\s]+")) {  // Allows spaces in jobs
                                    System.out.println("Invalid input. Please enter only alphabetic characters for the job.");
                                    break; // Exit the current case to prevent further execution
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
                                sc.nextLine(); // Consume newline character that nextLong() doesn't read
                                break;  // Exit the loop if input is valid
                            } catch (InputMismatchException e) {
                                System.out.println("Invalid input. Please enter a valid integer ID. Try again.");
                                sc.nextLine(); // Clear the scanner's buffer to handle next inputs correctly
                            }
                        }

                        studentManager.deleteStudentById(type, ID);
                        break;

                    case 5:
                        System.out.print("Enter name to search for a student: ");
                        String nameKeyword = sc.nextLine();

                        if (!nameKeyword.matches("[a-zA-Z\\s]+")) {  // Allows spaces in names
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
