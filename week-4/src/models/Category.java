package models;

import repositories.Entity;

/**
 * Created by shai on 11/15/2017.
 */
public class Category implements Entity<Long> {

    public Long id;
    public String name;

    @Override
    public Long id() {
        return this.id;
    }

    public Category(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
