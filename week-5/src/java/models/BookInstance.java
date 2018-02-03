/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import repositories.Entity;

/**
 *
 * @author shai
 */
public class BookInstance implements Entity<String>{
    
    public String id;
    public String isbn;
    public int copyNum;

    public BookInstance(String isbn, int copyNum) {
        this.isbn = isbn;
        this.copyNum = copyNum;
        this.id = this.isbn + "-" + this.copyNum;
    }
    
    public BookInstance(String id, String isbn, int copyNum) {
        this.id = id;
        this.isbn = isbn;
        this.copyNum = copyNum;
    }

    @Override
    public String getIdValue() {
        return this.isbn + "-" + this.copyNum;
    }

    @Override
    public String getIdKey() {
        return "id";
    }

    @Override
    public String toString() {
        return "BookInstance{" + "id=" + id + ", isbn=" + isbn + ", copyNum=" + copyNum + '}';
    }    
}
