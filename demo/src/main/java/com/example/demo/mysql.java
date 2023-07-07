package com.example.demo;

import java.sql.Connection;
import java.sql.DriverManager;


public class mysql {

    public Connection databaselink;

    public Connection getDBConnection() {

        String databaseUser = "root";
        String databasePassword = "123456";
        String url = "jdbc:mysql://localhost:3306/qlns";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            databaselink = DriverManager.getConnection(url, databaseUser, databasePassword);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return databaselink;
    }
}



