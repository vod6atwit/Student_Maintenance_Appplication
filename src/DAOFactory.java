/*
 * Programmer Duy Vo
 * CIT 285
 */

public class DAOFactory {
    // this method maps the StudentDAO interface
    // to the appropriate data storage mechanism
    // public static ProductDAO getStudentDAO()

    public static StudentDAO getStudentDAO() {
        StudentDAO sDAO = new StudentXMLFile();
        return sDAO;
    }
}
