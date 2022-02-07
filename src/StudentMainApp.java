/*
 * Programmer Duy Vo
 * CIT 285
 */

import java.util.Scanner;
import java.util.ArrayList;

public class StudentMainApp implements ProductConstants {

    private static StudentDAO studentDAO = null;
    private static Scanner sc = null;

    public static void main(String args[]) {

        System.out.println("Welcome to the Student Maintenance application\n");

        studentDAO = DAOFactory.getStudentDAO();
        sc = new Scanner(System.in);

        displayMenu();

        String action = "";
        // Stop is user enter exit
        while (!action.equalsIgnoreCase("exit")) {
            action = Validator.getString(sc,
                    "Enter a command: ");
            System.out.println();

            if (action.equalsIgnoreCase("list")) {
                displayAllStudents();
            } else if (action.equalsIgnoreCase("add")) {
                addStudent();
            } else if (action.equalsIgnoreCase("del") || action.equalsIgnoreCase("delete")) {
                deleteStudent();
            } else if (action.equalsIgnoreCase("update")) {
                updateStudent();
            } else if (action.equalsIgnoreCase("help") || action.equalsIgnoreCase("menu")) {
                displayMenu();
            } else if (action.equalsIgnoreCase("exit")) {
                System.out.println("Bye.\n");
            } else {
                System.out.println("Error! Not a valid command.\n");
            }
        }

    }

    public static void displayMenu() {
        System.out.println("COMMAND MENU");
        System.out.println("list    - List all students");
        System.out.println("add     - Add a student");
        System.out.println("del     - Delete a student");
        System.out.println("update  - update a student");
        System.out.println("help    - Show this menu");
        System.out.println("exit    - Exit this application\n");
    }

    public static void displayAllStudents() {

        System.out.println("STUDENT LIST:");
        ArrayList<Student> students = studentDAO.getStudent();
        if (students == null) {
            System.out.println("Error! Unable to get students.\n");
        } else {

            Student s = null;
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < students.size(); i++) {
                s = students.get(i);
                sb.append(
                        StringUtils.padWithSpaces(
                                s.getName(), CODE_SIZE + 4)
                        + s.getGpa() + "\t"
                        + s.getGender() + "\t"
                        + s.getStudentNumString() + "\n"
                );
            }
            System.out.println(sb.toString());
        }
        if (students.isEmpty()) {
            System.out.println("Empty list \n");
        }
    }

    public static void addStudent() {

        String name = Validator.getString(
                sc, "Enter student name: ");
        double gpa = Validator.getDouble(
                sc, "Enter gpa: ");
        char gender = Validator.getChar(
                sc, "Enter gender: ");
        int studentNum = Validator.getInt(
                sc, "Enter student number: ");

        Student student = new Student();

        student.setName(name);
        student.setGpa(gpa);
        student.setGender(gender);
        student.setStudentNum(studentNum);
        boolean success = studentDAO.addStudent(student);

        System.out.println();
        if (success) {
            System.out.println(name
                    + " was added to the database.\n");
        } else {
            System.out.println("Error! Unable to add Student\n");
        }
    }

    public static void deleteStudent() {
        String name = Validator.getString(sc,
                "Enter Student name to delete: ");

        Student s = studentDAO.getStudent(name);

        System.out.println();
        if (s != null) {
            boolean success = studentDAO.deleteStudent(s);
            if (success) {
                System.out.println(s.getName()
                        + " was deleted from the database.\n");
            } else {
                System.out.println("Error! Unable to add student\n");
            }
        } else {
            System.out.println("No student matches that name.\n");
        }
    }

    public static void updateStudent() {

        String name = Validator.getString(
                sc, "Enter student name to update: ");

        Student s = studentDAO.getStudent(name);
        Student student = new Student();

        if (s != null) {
            boolean delSuccess = studentDAO.deleteStudent(s);
            if (delSuccess) {
                student.setName(name);
                double gpa = Validator.getDouble(
                        sc, "Enter gpa: ");
                char gender = Validator.getChar(
                        sc, "Enter gender: ");
                int studentNum = Validator.getInt(
                        sc, "Enter student number: ");

                student.setName(name);
                student.setGpa(gpa);
                student.setGender(gender);
                student.setStudentNum(studentNum);

                boolean addSuccess = studentDAO.addStudent(student);

                if (addSuccess) {
                    System.out.println(name
                            + " was updated to the database.\n");
                } else {
                    System.out.println("Error! Unable to update student\n");
                }

            }
        } else {
            System.out.println("No student matches that name.\n");
        }

        System.out.println();

    }
}
