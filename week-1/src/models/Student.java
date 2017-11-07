package models;

import java.io.Serializable;

/**
 * Created by shai on 11/7/2017.
 */
public class Student implements Serializable {

    public Student(String firstname, String lastname, String id, String email) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.id = id;
        this.email = email;
    }

    String firstname;
    String lastname;
    String id;
    String email;

    @Override
    public String toString() {
        return "models.Student{" +
                "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", id='" + id + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
