/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import models.Fine;
import models.Student;

/**
 *
 * @author shai
 */
public class FinesRepository extends DBRepository<Fine, String>  {
    private static FinesRepository instance = null;

    public static FinesRepository getInstance() {

        if (instance != null){
            return instance;
        }

        else {
            instance = new FinesRepository();
            return instance;
        }
    }

    private FinesRepository() {
        super("fines");
    }

    @Override
    protected Fine resultSetToEntity(ResultSet rs) throws SQLException {
        
        String id = rs.getString("id");
        String studentId = rs.getString("studentId");
        long createdDate = rs.getLong("createdDate");
        int lateInDays = rs.getInt("lateInDays");
        boolean paid = rs.getBoolean("paid");
        
        return new Fine(id, studentId, createdDate, lateInDays, paid);
    }
}
