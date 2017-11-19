import models.Book;
import models.BookReservation;
import models.Category;
import models.Student;
import repositories.BookReservationsRepository;
import repositories.BooksRepository;
import repositories.CategoriesRepository;
import repositories.StudentsRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shai on 11/8/2017.
 */
public class DataFiller {

    public static void main(String[] args) {

        List<Category> categories = new ArrayList<>();
        categories.add(new Category(1L, "fantasy"));
        categories.add(new Category(2L, "horror"));
        CategoriesRepository.getInstance().truncateData().save(categories);

        List<Book> books = new ArrayList<>();
        books.add(new Book("ddads", "fantasy", "233231", "fwfe", "1991", 1));
        books.add(new Book("ddads", "fantasy", "233231", "fwfe", "1991", 1));
        books.add(new Book("ddads", "fantasy", "233231", "fwfe", "1991", 1));
        BooksRepository.getInstance().truncateData().save(books);

        List<Student> students = new ArrayList<>();
        students.add(new Student("123", "john", "wick", "john@mail.com"));
        students.add(new Student("127", "ron", "yam", "ron@mail.com"));
        students.add(new Student("124", "tal", "bar", "tal@mail.com"));
        StudentsRepository.getInstance().truncateData().save(students);

        BookReservationsRepository.getInstance().truncateData();
    }
}
