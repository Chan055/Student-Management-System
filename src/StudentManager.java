import java.util.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.ArrayList;
import java.io.FileReader;
import java.io.FileWriter;

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