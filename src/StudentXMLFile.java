/*
 * Programmer Duy Vo
 * CIT 285
 */

import java.util.*;
import java.io.*;
import javax.xml.stream.*;

public class StudentXMLFile implements StudentDAO {

    private String studentsFilename = "Student.xml";
    private File studentsFile = null;

    public StudentXMLFile() {
        studentsFile = new File(studentsFilename);
    }

    private void checkFile() throws IOException {
        if (!studentsFile.exists()) {
            studentsFile.createNewFile();
        }
    }

    private boolean saveStudents(ArrayList<Student> students) {

        XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();

        try {
            this.checkFile();

            FileWriter fileWriter = new FileWriter(studentsFilename);
            XMLStreamWriter writer = outputFactory.createXMLStreamWriter(fileWriter);

            writer.writeStartDocument("1.0");
            writer.writeStartElement("Students");

            for (Student s : students) {

                writer.writeStartElement("Student");
                writer.writeAttribute("Name", s.getName());

                writer.writeStartElement("GPA");
                double gpa = s.getGpa();
                writer.writeCharacters(Double.toString(gpa));
                writer.writeEndElement();

                writer.writeStartElement("Gender");
                String gender = "";
                if (s.getGender() == 'm' || s.getGender() == 'M') {
                    gender = "male";
                }
                if (s.getGender() == 'f' || s.getGender() == 'F') {
                    gender = "female";
                }
                writer.writeCharacters(gender);
                writer.writeEndElement();

                writer.writeStartElement("studentNumber");
                int num = s.getStudentNum();
                writer.writeCharacters(Integer.toString(num));
                writer.writeEndElement();

                writer.writeEndElement();
            }
            writer.writeEndElement();
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (XMLStreamException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public ArrayList<Student> getStudent() {
        ArrayList<Student> students = new ArrayList<Student>();
        Student s = null;

        // create the XMLInputFactory object
        XMLInputFactory inputFactory = XMLInputFactory.newInstance();
        try {
            // check the file to make sure it exists
            this.checkFile();

            // create a XMLStreamReader object
            FileReader fileReader
                    = new FileReader(studentsFilename);
            XMLStreamReader reader
                    = inputFactory.createXMLStreamReader(fileReader);

            // read the products from the file
            while (reader.hasNext()) {
                int eventType = reader.getEventType();
                
                // Switch between elements
                switch (eventType) {
                    case XMLStreamConstants.START_ELEMENT:
                        String elementName = reader.getLocalName();
                        if (elementName.equals("Student")) {
                            s = new Student();

                            String name = reader.getAttributeValue(0);
                            s.setName(name);
                        }
                        if (elementName.equals("GPA")) {
                            String gpaString = reader.getElementText();
                            double gpa = Double.parseDouble(gpaString);
                            s.setGpa(gpa);
                        }

                        if (elementName.equals("Gender")) {
                            String genderString = reader.getElementText();
                            char gender = genderString.charAt(0);
                            s.setGender(gender);
                        }
                        if (elementName.equals("studentNumber")) {
                            String studentNumString = reader.getElementText();
                            int studentNum = Integer.parseInt(studentNumString);
                            s.setStudentNum(studentNum);
                        }

                        break;
                    case XMLStreamConstants.END_ELEMENT:
                        elementName = reader.getLocalName();
                        if (elementName.equals("Student")) {
                            students.add(s);
                        }
                        break;
                    default:
                        break;
                }
                reader.next();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (XMLStreamException e) {
            e.printStackTrace();
            return null;
        }
        return students;
    }

    // compare to delete Student
    public Student getStudent(String name) {
        ArrayList<Student> students = this.getStudent();
        for (Student s : students) {
            if (s.getName().equals(name)) {
                return s;
            }
        }
        return null;
    }

    public boolean addStudent(Student s) {
        ArrayList<Student> students = this.getStudent();
        students.add(s);
        return this.saveStudents(students);
    }

    public boolean deleteStudent(Student s) {
        ArrayList<Student> students = this.getStudent();
        students.remove(s);
        return this.saveStudents(students);
    }

    public boolean updateStudent(Student newStudent) {
        ArrayList<Student> students = this.getStudent();

        // get the old student and remove it
        Student oldStudent = this.getStudent(newStudent.getName());
        int i = students.indexOf(oldStudent);
        students.remove(i);

        // add the updated product
        students.add(i, newStudent);

        return this.saveStudents(students);
    }
}
