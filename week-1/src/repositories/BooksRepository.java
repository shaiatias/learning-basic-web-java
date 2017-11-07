package repositories;

import models.Book;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by shai on 11/7/2017.
 */
public class BooksRepository extends CrudRepository<Book> {

    private static BooksRepository instance = null;

    public static BooksRepository getInstance() {

        if (instance != null){
            return instance;
        }

         else {
            instance = new BooksRepository();
            return instance;
        }
    }

    private BooksRepository() {
        super("books.dat");
    }

    public List<Book> findBookByName(String name) {
        return getAll()
                .stream()
                .filter(book -> book.name.toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }

    public void addBook(String name, String isbn, String author, String year, int copies) {
        Book book = new Book(name, isbn, author, year, copies);
        save(book);
    }

    public void removeByIsbn(String isbn) {
        delete(book -> book.isbn.equalsIgnoreCase(isbn));
    }
}
