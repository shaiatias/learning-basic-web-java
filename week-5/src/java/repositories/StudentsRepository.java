package repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import models.Student;

/**
 * Created by shai on 11/7/2017.
 */
public class StudentsRepository extends DBRepository<Student, String> {

    private static StudentsRepository instance = null;

    public static StudentsRepository getInstance() {

        if (instance != null){
            return instance;
        }

        else {
            instance = new StudentsRepository();
            return instance;
        }
    }

    private StudentsRepository() {
        super("students");
    }

    public void addStudent(String firstname, String lastname, String id, String email) {
        Student student = new Student(firstname, lastname, id, email);
        save(student);
    }

    @Override
    protected Student resultSetToEntity(ResultSet rs) throws SQLException {
        
        String id = rs.getString("id");
        String firstname = rs.getString("firstname");
        String lastname = rs.getString("lastname");
        String email = rs.getString("email");
        
        return new Student(id, firstname, lastname, email);
    }
}
