package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class DBUtils {

    public static void changeScence(ActionEvent event, String title, String fxmlFile, String username) {
        Parent root = null;
            try {
                root = FXMLLoader.load(DBUtils.class.getResource(fxmlFile));
            } catch (IOException e) {
                e.printStackTrace();
            }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(title);
        stage.setScene(new Scene(root));
        stage.show();

    }

    public static void LoginUser(ActionEvent event, String username, String password){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/qlns", "root", "123456");
            preparedStatement = connection.prepareStatement("SELECT password FROM login WHERE username = ?");
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()){
                System.out.println("Tài khoản không tồn tại!");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Thông tin đăng nhập sai!");
                alert.show();
            } else {
                while (resultSet.next()) {
                    String getPassword = resultSet.getString("password");
                    if (getPassword.equals(password)) {
                        changeScence(event, "Select!", "menu.fxml", username);
                    }else  {
                        System.out.println("Mật khẩu không chính xác!");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Thông tin đăng nhập sai!");
                        alert.show();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null){
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}

