package models;

import helpers.Constants;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.Calendar;
import repositories.Entity;

/**
 * Created by shai on 11/8/2017.
 */
public class BookReservation implements Entity<Long> {

    public Long id;
    public Long fromDate;
    public Long toDate;
    public String bookIsbn;
    public Integer bookCopy;
    public String studentId;

    @Override
    public Long getIdValue() {
        return this.id;
    }

    @Override
    public String getIdKey() {
        return "id";
    }

    public BookReservation(Long id, Long fromDate, Long toDate, String bookIsbn, Integer bookCopy, String studentId) {
        this.id = id;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.bookIsbn = bookIsbn;
        this.bookCopy = bookCopy;
        this.studentId = studentId;
    }

    @Override
    public String toString() {
        return "BookReservation{" + "id=" + id + ", fromDate=" + fromDate + ", toDate=" + toDate + ", bookIsbn=" + bookIsbn + ", bookCopy=" + bookCopy + ", studentId=" + studentId + '}';
    }

    public static String format(long date) {
        DateTimeFormatter formatter
                = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)
                        .withZone(ZoneId.systemDefault());

        Instant instant = Instant.ofEpochMilli(date);
        String output = formatter.format( instant );
        
        return output;
    }

    public long getRemainingDays() {
        
        long expectedReturn = this.fromDate + (Constants.MAX_RESERVATION_IN_DAYS * 1000 * 60 * 60 * 24);
        
        long days = ChronoUnit.DAYS.between(Instant.ofEpochMilli(expectedReturn), Instant.now());
        return days;
    }
}
