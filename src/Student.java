/*
 * Programmer Duy Vo
 * CIT 285
 */

public class Student {

    private String student_name;
    private double gpa;
    private char gender;
    private int student_Number;

    public Student() {
        this("", 0, ' ', 0);
    }

    public Student(String name, double gpa, char gender, int studentNum) {
        this.student_name = name;
        this.gpa = gpa;
        this.gender = gender;
        this.student_Number = studentNum;
    }

    public void setName(String name) {
        this.student_name = name;
    }

    public String getName() {
        return student_name;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }

    public double getGpa() {
        return gpa;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public char getGender() {
        return gender;
    }

    public void setStudentNum(int studentNum) {
        this.student_Number = studentNum;
    }

    public int getStudentNum() {
        return student_Number;
    }

    public String getStudentNumString() {
        String studentNumString = Integer.toString(student_Number);
        return studentNumString;

    }

    public boolean equals(Object object) {
        if (object instanceof Student) {
            Student student2 = (Student) object;
            if (student_name.equals(student2.getName())
                    && gpa == student2.getGpa()
                    && gender == student2.getGender()
                    && student_Number == student2.getStudentNum()) {
                return true;
            }
        }
        return false;
    }

    public String toString() {
        return "Name:        " + getName() + "\n"
                + "Gpa:            " + getGpa() + "\n"
                + "gender: " + getGender() + "\n"
                + "Student Number:   " + getStudentNum() + "\n";
    }
}
