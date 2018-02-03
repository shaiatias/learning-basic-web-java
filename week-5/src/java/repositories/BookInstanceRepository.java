/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import models.BookInstance;

/**
 *
 * @author shai
 */
public class BookInstanceRepository extends DBRepository<BookInstance, String> {

    private static BookInstanceRepository instance = null;

    public static BookInstanceRepository getInstance() {

        if (instance != null) {
            return instance;
        } else {
            instance = new BookInstanceRepository();
            return instance;
        }
    }
    
    private BookInstanceRepository() {
        super("bookInstances");
    }

    @Override
    protected BookInstance resultSetToEntity(ResultSet rs) throws SQLException {
        
        String id = rs.getString("id");
        String isbn = rs.getString("isbn");
        Integer copyNum = rs.getInt("copyNum");
        
        return new BookInstance(id, isbn, copyNum);
    }
}
