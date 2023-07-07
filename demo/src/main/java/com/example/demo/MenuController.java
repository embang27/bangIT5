package com.example.demo;

import  javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuController implements Initializable {

    @FXML
    private Button dangxuat;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dangxuat.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScence(event, "Log in!", "hello-view.fxml", null);
            }
        });

    }
    private Stage stage;
    private Scene scene;
    private Parent parent;


    public void changetoNhanVien(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("nhanvien.fxml"));
        stage = (Stage) ((Node) event.getSource ()). getScene (). getWindow ();
        scene = new Scene (root);
        stage.setTitle("Nhân viên");
        stage.setScene (scene);
        stage.show();
    }

    public void changetoQuanLy(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("quanly.fxml"));
        stage = (Stage) ((Node) event.getSource ()). getScene (). getWindow ();
        scene = new Scene (root);
        stage.setTitle("Quản lý");
        stage.setScene (scene);
        stage.show();
    }

    public void changetoChamCong(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("CHAMCONG.fxml"));
        stage = (Stage) ((Node) event.getSource ()). getScene (). getWindow ();
        scene = new Scene (root);
        stage.setTitle("Chấm công");
        stage.setScene (scene);
        stage.show();
    }


}