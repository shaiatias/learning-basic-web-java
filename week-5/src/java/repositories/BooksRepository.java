package repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import models.Book;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by shai on 11/7/2017.
 */
public class BooksRepository extends DBRepository<Book, String> {

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

    public List<Book> findBookByName(String name) {
        return name.equals("*") ?
                getAll() :
                getAll()
                    .stream()
                    .filter(book -> book.name.toLowerCase().contains(name.toLowerCase()))
                    .collect(Collectors.toList());
    }

    public Optional<Book> findBookByIsbn(String isbn) {

        return getAll()
                .stream()
                .filter(book -> book.isbn.equalsIgnoreCase(isbn))
                .findFirst();
    }

    public void addBook(String name, String category, String isbn, String author, String year) {
        Book book = new Book(name, category, isbn, author, year);
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

    @Override
    protected Book resultSetToEntity(ResultSet rs) throws SQLException{
        
        String name = rs.getString("name");
        String category = rs.getString("category");
        String isbn = rs.getString("isbn");
        String author = rs.getString("author");
        String publicationYear = rs.getString("publicationYear");
        
        return new Book(name, category, isbn, author, publicationYear);
    }
}
