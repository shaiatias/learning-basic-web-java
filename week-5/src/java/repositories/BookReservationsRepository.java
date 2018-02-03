package repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import models.Book;
import models.BookReservation;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import models.BookInstance;
import models.Fine;

/**
 * Created by shai on 11/7/2017.
 */
public class BookReservationsRepository extends DBRepository<BookReservation, Long> {

    private static BookReservationsRepository instance = null;

    public static BookReservationsRepository getInstance() {

        if (instance != null) {
            return instance;
        } else {
            instance = new BookReservationsRepository();
            return instance;
        }
    }

    private BookReservationsRepository() {
        super("bookReservations");
    }

    public boolean isBookAvailable(int bookCopies, Predicate<BookReservation> filter) {

        return getAll()
                .stream()
                .filter(bookReservation -> bookReservation.toDate != -1)
                .collect(Collectors.counting()) != bookCopies;
    }

    public Optional<BookInstance> getAvailableBook(String isnb) {

        List<Integer> takenBooks = getAll().stream()
                .filter(bookReservation -> bookReservation.toDate != -1)
                .map(b -> b.bookCopy)
                .collect(Collectors.toList());

        return BookInstanceRepository
                .getInstance().getAll().stream()
                .filter(b -> b.isbn.equals(isnb))
                .filter(b -> !takenBooks.contains(b.copyNum))
                .findFirst();
    }

    public Optional<BookReservation> returnBook(String isbn, String studentId) {

        Optional<BookReservation> reservation = getAll()
                .stream()
                .filter(bookReservation
                        -> bookReservation.bookIsbn.equalsIgnoreCase(isbn) && bookReservation.studentId.equalsIgnoreCase(studentId))
                .findFirst();

        reservation.ifPresent(bookReservation -> {
            bookReservation.toDate = System.currentTimeMillis();
            
            long remainingDays = bookReservation.getRemainingDays();
            
            if (remainingDays < 0) {
                FinesRepository.getInstance().save(new Fine(studentId, System.currentTimeMillis(), (int) remainingDays, false));
            }
            
            save(bookReservation);
        });

        return reservation;
    }

    public void reserveBook(String studentId, String bookIsbn) {

        // find available book
        BookInstance bookInstance = getAvailableBook(bookIsbn).orElseThrow(() -> new RuntimeException("this book is fully booked"));
        
        // save reservation
        BookReservation bookReservation = new BookReservation(-1l, System.currentTimeMillis(), -1l, bookIsbn, bookInstance.copyNum, studentId);
        save(bookReservation);
    }

    @Override
    protected BookReservation resultSetToEntity(ResultSet rs) throws SQLException {

        Long id = rs.getLong("id");
        Long fromDate = rs.getLong("fromDate");
        Long toDate = rs.getLong("toDate");
        String bookIsbn = rs.getString("bookIsbn");
        int bookCopy = rs.getInt("bookCopy");
        String studentId = rs.getString("studentId");

        return new BookReservation(id, fromDate, toDate, bookIsbn, bookCopy, studentId);
    }
}
