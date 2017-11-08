import models.Book;
import models.BookReservation;
import models.Student;
import repositories.BookReservationsRepository;
import repositories.BooksRepository;
import repositories.StudentsRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shai on 11/8/2017.
 */
public class DataFiller {

    public static void main(String[] args) {

        List<Book> books = new ArrayList<>();
        books.add(new Book("ddads", "fantasy", "233231", "fwfe", "1991", 1));
        books.add(new Book("ddads", "fantasy", "233231", "fwfe", "1991", 1));
        books.add(new Book("ddads", "fantasy", "233231", "fwfe", "1991", 1));
        BooksRepository.getInstance().save(books);

        List<Student> students = new ArrayList<>();
        StudentsRepository.getInstance().save(students);


        List<BookReservation> bookReservations = new ArrayList<>();
        BookReservationsRepository.getInstance().save(bookReservations);
    }
}
