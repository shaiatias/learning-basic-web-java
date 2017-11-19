package models;

import repositories.Entity;

/**
 * Created by shai on 11/8/2017.
 */
public class BookReservation implements Entity<Long> {

    public long id;
    public long from;
    public long to;
    public String bookIsbn;
    public String studentId;

    @Override
    public Long id() {
        return this.id;
    }

    public BookReservation(long id, long from, long to, String bookIsbn, String studentId) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.bookIsbn = bookIsbn;
        this.studentId = studentId;
    }

    @Override
    public String toString() {
        return "BookReservation{" +
                "id=" + id +
                ", from=" + from +
                ", to=" + to +
                ", bookIsbn='" + bookIsbn + '\'' +
                ", studentId='" + studentId + '\'' +
                '}';
    }
}
