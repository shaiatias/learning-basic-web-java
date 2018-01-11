package repositories;

import models.Student;

/**
 * Created by shai on 11/7/2017.
 */
public class StudentsRepository extends FileRepository<Student, String> {

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
}
