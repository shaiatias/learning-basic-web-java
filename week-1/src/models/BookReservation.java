package models;

import java.io.Serializable;

/**
 * Created by shai on 11/8/2017.
 */
public class BookReservation implements Serializable {

    public long from;
    public long to;
    public String bookIsbn;
    public String studentId;

    public BookReservation(long from, long to, String bookIsbn, String studentId) {
        this.from = from;
        this.to = to;
        this.bookIsbn = bookIsbn;
        this.studentId = studentId;
    }

    @Override
    public String toString() {
        return "BookReservation{" +
                "from=" + from +
                ", to=" + to +
                ", bookIsbn='" + bookIsbn + '\'' +
                ", studentId='" + studentId + '\'' +
                '}';
    }
}
