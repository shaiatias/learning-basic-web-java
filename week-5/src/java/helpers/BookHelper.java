/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import java.util.Optional;
import models.Book;
import models.BookInstance;
import models.BookReservation;
import repositories.BookInstanceRepository;
import repositories.BookReservationsRepository;
import repositories.BooksRepository;

public class BookHelper {
    
    public static void addBook(String name,String category,String isbn,String author,String publicationYear,int copies){
        
        if (BooksRepository.getInstance().isEntryExist(new Book(name, category, isbn,author, publicationYear))) {
            throw new RuntimeException("book already exists");
        }
        
        BooksRepository.getInstance().save(new Book(name, category, isbn,author, publicationYear));
        
        for (int i = 0; i < copies; i++) {
            BookInstanceRepository.getInstance().save(new BookInstance(isbn, i + 1));
        }
    }
    
    public static void removeBook(String isbn) {
        
        Optional<BookReservation> findFirst = BookReservationsRepository.getInstance()
                .getAll().stream()
                .filter(res -> res.bookIsbn.equals(isbn))
                .filter(res -> res.toDate == null)
                .findFirst();
        
        if (findFirst.isPresent()) {
            throw new RuntimeException("cannot delete book while copies are reserved");
        }
        
        BooksRepository.getInstance().delete(b -> b.isbn.equals(isbn));
        BookInstanceRepository.getInstance().delete(b -> b.isbn.equals(isbn));
    }
}
