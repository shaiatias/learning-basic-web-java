package repositories;

import models.Book;
import models.BookReservation;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by shai on 11/7/2017.
 */
public class BookReservationsRepository extends FileRepository<BookReservation, Long> {

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
        super("book-reservations");
    }

    public boolean isBookAvailable(int bookCopies, Predicate<BookReservation> filter) {

        return getAll()
                .stream()
                .filter(bookReservation -> bookReservation.to != -1)
                .collect(Collectors.counting()) != bookCopies;
    }

    public Optional<BookReservation> returnBook(String isbn, String studentId) {

        Optional<BookReservation> reservation = getAll()
                .stream()
                .filter(bookReservation ->
                        bookReservation.bookIsbn.equalsIgnoreCase(isbn) && bookReservation.studentId.equalsIgnoreCase(studentId))
                .findFirst();

        reservation.ifPresent(bookReservation -> {
            bookReservation.to = System.currentTimeMillis();
            save(bookReservation);
        });

        return reservation;
    }

    public void reserveBook(String studentId, String bookIsbn) {

        Book book = BooksRepository.getInstance().getOne(b -> b.isbn.equals(bookIsbn)).orElseThrow(() -> new RuntimeException("book not found"));


        if (!isBookAvailable(book.copies, bookReservation -> bookReservation.bookIsbn.equalsIgnoreCase(bookIsbn))) {
            throw new RuntimeException("this book is fully booked");
        }

        BookReservation bookReservation = new BookReservation(-1, System.currentTimeMillis(), -1, bookIsbn, studentId);
        save(bookReservation);
    }
}
