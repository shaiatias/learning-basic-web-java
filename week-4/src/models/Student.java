package models;

import repositories.Entity;

/**
 * Created by shai on 11/7/2017.
 */
public class Student implements Entity<String> {

    public String id;
    public String firstname;
    public String lastname;
    public String email;

    @Override
    public String id() {
        return this.id;
    }

    public Student(String id, String firstname, String lastname, String email) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
