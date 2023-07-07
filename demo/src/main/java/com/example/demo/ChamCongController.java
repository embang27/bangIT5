package com.example.demo;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChamCongController implements Initializable {
    @FXML
    private TableColumn<chamcongtable, String> col_hoten;

    @FXML
    private TableColumn<chamcongtable, Integer> col_luong;

    @FXML
    private TableColumn<chamcongtable, Integer> col_msnv;

    @FXML
    private TableColumn<chamcongtable, Date> col_ngaythangnam;

    @FXML
    private TableColumn<chamcongtable, Integer> col_tongsogio;

    @FXML
    private TableView<chamcongtable> tableuser;

    @FXML
    private TextField chamcongSearch;

    @FXML
    private TextField textHoten;

    @FXML
    private TextField textMSNV;

    @FXML
    private TextField textSogiolam;

    @FXML
    private TextField textNgaythangnam;

    @FXML
    private TextField textLuong;


    ObservableList<chamcongtable> chamcongtableObservableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mysql connetNow = new mysql();
        Connection connectDB = connetNow.getDBConnection();

        String chamcongView ="SELECT msnv, hoten, tongsogio, ngaythangnam, luong FROM nhanvien";

        try {
            Statement statement = connectDB.createStatement();
            ResultSet Output = statement.executeQuery(chamcongView);

            while (Output.next()){

                Integer querymsnv = Output.getInt("msnv");
                String queryhoten = Output.getString("hoten");
                Integer querytongsogio = Output.getInt("tongsogio");
                Date queryngaythangnam = Output.getDate("ngaythangnam");
                Integer queryluong = Output.getInt("luong");
                chamcongtableObservableList.add(new chamcongtable(querymsnv, querytongsogio,queryluong, queryhoten, queryngaythangnam));

            }
            col_msnv.setCellValueFactory(new PropertyValueFactory<>("msnv"));
            col_hoten.setCellValueFactory(new PropertyValueFactory<>("hoten"));
            col_tongsogio.setCellValueFactory(new PropertyValueFactory<>("tongsogio"));
            col_ngaythangnam.setCellValueFactory(new PropertyValueFactory<>("ngaythangnam"));
            col_luong.setCellValueFactory(new PropertyValueFactory<>("luong"));
            tableuser.setItems(chamcongtableObservableList);

            FilteredList<chamcongtable> filterData = new FilteredList<>(chamcongtableObservableList, b -> true);

            chamcongSearch.textProperty().addListener((observable, oldValue, newValue) -> {
                filterData.setPredicate(chamcongtable -> {
                    if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
                        return true;

                    }

                    String searchKeyword = newValue.toLowerCase();

                    if (chamcongtable.getHoten().toLowerCase().indexOf(searchKeyword) > - 1) {
                        return true;

                    }else
                        return false;
                });
            });
            SortedList<chamcongtable> sortedData = new SortedList<>(filterData);

            tableuser.setItems(sortedData);

        } catch (SQLException e){
            Logger.getLogger(ChamCongController.class.getName()).log(Level.SEVERE,null, e);
            e.printStackTrace();
        }


    }

    Alert alert;
    public void SuaNhanVien() {
        try {

            String sql = "UPDATE nhanvien SET hoten = ?, tongsogio = ?, ngaythangnam = ?, luong = ? WHERE msnv = ?";
            Connection connect = DBConnection.ConnectDB();
            PreparedStatement prepare = connect.prepareStatement(sql);

            String NoiDungConThieu = "";
            if (textMSNV.getText().isEmpty()) {
                NoiDungConThieu += "Thiếu Mã Nhân Viên Để Sửa.\n";
            }
            if (textHoten.getText().isEmpty()) {
                NoiDungConThieu += "Thiếu Họ Tên Để Sửa.\n";
            }
            if (textSogiolam.getText() == null) {
                NoiDungConThieu += "Thiếu Số Giờ Làm Sửa.\n";
            }
            if (textNgaythangnam.getText() == null) {
                NoiDungConThieu += "Thiếu Ngày Tháng Năm Để Sửa.\n";
            }
            if (textLuong.getText() == null) {
                NoiDungConThieu += "Thiếu Lương Để Sửa.\n";
            }
            if (!NoiDungConThieu.isEmpty()) {
                alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Thông Báo Cho Bạn");
                alert.setHeaderText(null);
                alert.setContentText(NoiDungConThieu);
                alert.showAndWait();
            }
            else {
                prepare = connect.prepareStatement(sql);
                prepare.setString(5, textMSNV.getText());
                prepare.setString(1, textHoten.getText());
                prepare.setString(2, textSogiolam.getText());
                prepare.setString(3, textNgaythangnam.getText());
                prepare.setString(4, textLuong.getText());
            }
            //---------------------------
            int rowsAffected = prepare.executeUpdate();
            if (rowsAffected > 0) {
                // Xóa thành công
                System.out.println("Đã Sửa Nhân Viên Có Mã: " + textMSNV.getText());
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Thông Báo Cho Bạn");
                alert.setHeaderText(null);
                alert.setContentText("Đã Sửa Nhân Viên Có Mã: " + textMSNV.getText());
                alert.showAndWait();
            } else {
                // Không tìm thấy Nhân Viên để xóa
                System.out.println("Không tìm thấy Nhân Viên có mã : " + textMSNV.getText());
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Thông Báo Cho Bạn");
                alert.setHeaderText(null);
                alert.setContentText("Không tìm thấy Nhân Viên có mã: " + textMSNV.getText());
                alert.showAndWait();
            }
            //------------------------------
            refreshTable();
        }catch(
                SQLException e)

        {
            e.printStackTrace();
        }

    }

    public ObservableList<chamcongtable> DuLieuNhanVien() {
        ObservableList<chamcongtable> CacNhanVien = FXCollections.observableArrayList();
        String sql = "SELECT MSNV, HoTen, tongsogio, ngaythangnam, luong FROM NhanVien";
        Connection connection = DBConnection.ConnectDB();
        DBConnection connectionDB = new DBConnection();
        try {
            chamcongtable nhanVien;
            PreparedStatement prepare = connectionDB.prepareStatement(sql);
            ResultSet result = prepare.executeQuery();
            while (result.next()) {
                nhanVien = new chamcongtable(result.getInt("MSNV"),
                        result.getInt("tongsogio"),
                        result.getInt("luong"),
                        result.getString("HoTen"),
                        result.getDate("ngaythangnam")


                );
                CacNhanVien.add(nhanVien);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return CacNhanVien;
    }
    private ObservableList<chamcongtable> Danhsachnhanvien;

    public void refreshTable(){
        Danhsachnhanvien = DuLieuNhanVien();
        col_msnv.setCellValueFactory(new PropertyValueFactory<>("msnv"));
        col_hoten.setCellValueFactory(new PropertyValueFactory<>("hoten"));
        col_tongsogio.setCellValueFactory(new PropertyValueFactory<>("tongsogio"));
        col_ngaythangnam.setCellValueFactory(new PropertyValueFactory<>("ngaythangnam"));
        col_luong.setCellValueFactory(new PropertyValueFactory<>("luong"));
        tableuser.setItems(Danhsachnhanvien);


    }
    public void ThemNhanVien(ActionEvent event) throws SQLException {
        String hoten = textHoten.getText();
        String tongsogio = textSogiolam.getText();
        String msnv = textMSNV.getText();
        String ngayvaolam = textNgaythangnam.getText();
        String luong = textLuong.getText();

        try {
            Connection connect = DBConnection.ConnectDB();
            String insertQuery = "INSERT INTO NhanVien (msnv, hoten, tongsogio,ngayvaolam, luong) " +
                    "VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstmt = connect.prepareStatement(insertQuery);
            pstmt.setString(1, msnv);
            pstmt.setString(2, hoten);
            pstmt.setString(3, tongsogio);
            pstmt.setString(4, ngayvaolam);
            pstmt.setString(5, luong);
            pstmt.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.ERROR.INFORMATION);
            alert.setTitle("Thành Công");
            alert.setHeaderText(null);
            alert.setContentText("Thêm nhân viên thành công!");
            alert.showAndWait();
            refreshTable();


            textHoten.clear();
            textMSNV.clear();
            textSogiolam.clear();
            textNgaythangnam.clear();
            textLuong.clear();
        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR.ERROR);
            alert.setTitle("Lỗi");
            alert.setHeaderText(null);
            alert.setContentText("Thêm nhân viên không thành công!");
            alert.showAndWait();
        }
    }

        

    private Stage stage;
    private Scene scene;
    private Parent parent;


    public void changetoMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("menu.fxml"));
        stage = (Stage) ((Node) event.getSource ()). getScene (). getWindow ();
        scene = new Scene (root);
        stage.setScene (scene);
        stage.show();
    }
    public void Logout(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("Bạn đang đăng xuất!");
        alert.setContentText("Bạn có chắc muốn đăng xuất?");
        if (alert.showAndWait().get() == ButtonType.OK) {

            Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }


}
