package com.example.demo;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private Button login;

    @FXML
    private TextField taikhoan;

    @FXML
    private TextField matkhau;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        login.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.LoginUser(event, taikhoan.getText(), matkhau.getText());
            }
        });

    }
}
