package models;

import java.io.Serializable;

/**
 * Created by shai on 11/7/2017.
 */
public class Book implements Serializable {

    public Book(String name, String category, String isbn, String author, String year, int copies) {
        this.name = name;
        this.category = category;
        this.isbn = isbn;
        this.author = author;
        this.year = year;
        this.copies = copies;
    }

    public String name;
    public String category;
    public String isbn;
    public String author;
    public String year;
    public int copies;

    @Override
    public String toString() {
        return "Book{" +
                "name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", isbn='" + isbn + '\'' +
                ", author='" + author + '\'' +
                ", year='" + year + '\'' +
                ", copies=" + copies +
                '}';
    }
}
