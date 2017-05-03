/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.cmu.db;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tarun
 */
public class DbConnection {
    
    public static Connection connectDB() {
        try {
            Driver driver = new oracle.jdbc.driver.OracleDriver();
            DriverManager.registerDriver(driver);
            String url = "jdbc:oracle:thin:@128.237.206.120:1521:xe";
            System.out.println("Driver loaded.");
            String username = "advdb";
            String password = "advdb";
            System.out.println("Attempting a connection");
            Properties prop = new Properties();
            prop.put("oracle.jdbc.timezoneAsRegion", "false");
            prop.put("user", username);
            prop.put("password", password);
            Connection conn = DriverManager.getConnection(url, prop);
            System.out.println("Connection established.");
            return conn;
        } catch (SQLException ex) {
            System.out.println("Error in creating connection");
            Logger.getLogger(DbConnection.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public static void closeConnection(Connection conn) {
        try {
            if(conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DbConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
