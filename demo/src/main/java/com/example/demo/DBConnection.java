package com.example.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBConnection {
//    public static void main(String[] args) {
//        String url = "jdbc:mysql://localhost:3306/QuanLy";
//        String username = "root";
//        String password = "PhamQuan2003";
//
//        System.out.println("Connecting to database...");
//
//        try {
//            Connection connection = DriverManager.getConnection(url, username, password);
//            System.out.println("Database connected!");
//        } catch (SQLException e) {
//            System.out.println("Cannot connect to the database!");
//        } finally {
//            System.out.println("Finally block executed.");
//        }
//    }

    private static Connection connection;
    public static Connection ConnectDB(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/qlns";
            String user = "root";
            String password = "123456";
            connection = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
        return connection;
    }

    public PreparedStatement prepareStatement(String query) {
        try {
            return connection.prepareStatement(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void disconnect() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            ConnectDB();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
