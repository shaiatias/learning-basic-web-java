package models;

import repositories.Entity;

/**
 * Created by shai on 11/7/2017.
 */
public class Book implements Entity<String> {

    public String name;
    public String category;
    public String isbn;
    public String author;
    public String publicationYear;

    @Override
    public String getIdValue() {
        return this.isbn;
    }

    @Override
    public String getIdKey() {
        return "isbn";
    }

    public Book(String name, String category, String isbn, String author, String publicationYear) {
        this.name = name;
        this.category = category;
        this.isbn = isbn;
        this.author = author;
        this.publicationYear = publicationYear;
    }

    @Override
    public String toString() {
        return "Book{" +
                "name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", isbn='" + isbn + '\'' +
                ", author='" + author + '\'' +
                ", publicationYear=" + publicationYear +
                '}';
    }
}
