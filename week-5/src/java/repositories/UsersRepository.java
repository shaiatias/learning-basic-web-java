/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import models.User;

/**
 *
 * @author Manage Your Trip
 */
public class UsersRepository extends DBRepository<User, String> {

    private static UsersRepository instance = null;

    public static UsersRepository getInstance() {

        if (instance != null) {
            return instance;
        } else {
            instance = new UsersRepository();
            return instance;
        }
    }

    private UsersRepository() {
        super("users");
    }

    @Override
    protected User resultSetToEntity(ResultSet rs) throws SQLException {
        
        String username = rs.getString("username");
        String password = rs.getString("password");
        Long lastLoginTime = rs.getLong("lastLoginTime");
        String role = rs.getString("role");
        String email = rs.getString("email");
        String studentId = rs.getString("studentId");

        return new User(username, password, lastLoginTime, role, email, studentId);
    }
}
