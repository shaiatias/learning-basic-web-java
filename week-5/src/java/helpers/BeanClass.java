/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import java.io.Serializable;
import java.util.List;
import models.Book;
import models.BookReservation;
import models.Fine;
import models.User;

/**
 *
 * @author shai
 */
public class BeanClass implements Serializable{
    public List<Book> searchResults;
    public List<BookReservation> reservationsResults;
    public List<Fine> finesResults;
    public User user;
}
