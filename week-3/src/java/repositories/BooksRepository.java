package repositories;

import models.Book;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by shai on 11/7/2017.
 */
public class BooksRepository extends FileRepository<Book, String> {

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
        super("books");
    }

    public Stream<Book> findBookByName(String name) {

        return name.equals("*") ?
                getAll().stream() :
                getAll()
                    .stream()
                    .filter(book -> book.name.toLowerCase().contains(name.toLowerCase()));
    }

    public Optional<Book> findBookByIsbn(String isbn) {

        return getAll()
                .stream()
                .filter(book -> book.isbn.equalsIgnoreCase(isbn))
                .findFirst();
    }

    public void addBook(String name, String category, String isbn, String author, String year) {

        Optional<Integer> previousAmount =
                getOne(book -> book.isbn.equalsIgnoreCase(isbn))
                .map(book -> book.copies);

        int amount = 1 + previousAmount.orElse(0);

        Book book = new Book(name, category, isbn, author, year, amount);
        save(book);
    }

    public void addBook(String name, String category, String isbn, String author, String year, int copies) {
        Book book = new Book(name, category, isbn, author, year, copies);
        save(book);
    }

    public void removeByIsbn(String isbn) {
        delete(book -> book.isbn.equalsIgnoreCase(isbn));
    }

    public Optional<Book> findBookByCategory(String category) {
        return findBooksByCategory(category).stream().findFirst();
    }

    public List<Book> findBooksByCategory(String category) {

        return getAll()
                .stream()
                .filter(book -> book.category.equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }
}
