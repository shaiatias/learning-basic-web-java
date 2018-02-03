/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Manage Your Trip
 */
public class DBConnection {

    private static Connection connection = null;

    public static Connection getConnection() {
        
        if (connection == null) {
            try {
                Class.forName("org.apache.derby.jdbc.ClientDriver");
                connection = DriverManager.getConnection("jdbc:derby://localhost:1527/my_db", "root", "root");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    
        return connection;
    }
}
