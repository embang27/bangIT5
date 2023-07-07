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
import org.w3c.dom.events.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NhanVienController implements Initializable {
    @FXML
    private Button addButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button editButton;

    @FXML
    private Label welcomeText;

    @FXML
    private TableColumn<nhanvientable, String> col_hoten;

    @FXML
    private TableColumn<nhanvientable, String> col_diachi;

    @FXML
    private TableColumn<nhanvientable, String> col_sdt;

    @FXML
    private TableColumn<nhanvientable, Integer> col_msnv;

    @FXML
    private TableColumn<nhanvientable, Date> col_ngayvaolam;

    @FXML
    private TableColumn<nhanvientable, Integer> col_sogiolam1tuan;

    @FXML
    private TableColumn<nhanvientable, Integer> col_tongsogio;

    @FXML
    private TableView<nhanvientable> tableuser;

    @FXML
    private TextField nhavienSearch;

    @FXML
    private TextField textHoten;

    @FXML
    private TextField textDiachi;

    @FXML
    private TextField textMSNV;

    @FXML
    private TextField textSogiolam1tuan;

    @FXML
    private TextField textTongsogio;

    @FXML
    private TextField textSdt;

    @FXML
    private TextField textNgayvaolam;

    ObservableList<nhanvientable> nhanvientableObservableList = FXCollections.observableArrayList();




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mysql connetNow = new mysql();
        Connection connectDB = connetNow.getDBConnection();

        String nhanvienView ="SELECT msnv, hoten, diachi, sdt, ngayvaolam, sogiolam1tuan, tongsogio FROM nhanvien";

        try {
            Statement statement = connectDB.createStatement();
            ResultSet Output = statement.executeQuery(nhanvienView);

            while (Output.next()){

                Integer querymsnv = Output.getInt("msnv");
                String queryhoten = Output.getString("hoten");
                String querydiachi = Output.getString("diachi");
                Integer querysogiolam1tuan = Output.getInt("sogiolam1tuan");
                Integer querytongsogio = Output.getInt("tongsogio");
                Date queryngayvaolam = Output.getDate("ngayvaolam");
                String querysdt = Output.getString("sdt");
                nhanvientableObservableList.add(new nhanvientable(querymsnv, queryhoten, querysdt, querydiachi, queryngayvaolam, querysogiolam1tuan, querytongsogio));

            }
            col_msnv.setCellValueFactory(new PropertyValueFactory<>("msnv"));
            col_hoten.setCellValueFactory(new PropertyValueFactory<>("hoten"));
            col_diachi.setCellValueFactory(new PropertyValueFactory<>("diachi"));
            col_sogiolam1tuan.setCellValueFactory(new PropertyValueFactory<>("sogiolam1tuan"));
            col_tongsogio.setCellValueFactory(new PropertyValueFactory<>("tongsogio"));
            col_ngayvaolam.setCellValueFactory(new PropertyValueFactory<>("ngayvaolam"));
            col_sdt.setCellValueFactory(new PropertyValueFactory<>("sdt"));
            tableuser.setItems(nhanvientableObservableList);

            FilteredList<nhanvientable> filterData = new FilteredList<>(nhanvientableObservableList, b -> true);

            nhavienSearch.textProperty().addListener((observable, oldValue, newValue) -> {
                filterData.setPredicate(nhanvientable -> {
                    if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
                        return true;

                    }

                    String searchKeyword = newValue.toLowerCase();

                    if (nhanvientable.getHoten().toLowerCase().indexOf(searchKeyword) > - 1) {
                        return true;

                    }else
                        return false;
                });
            });
            SortedList<nhanvientable> sortedData = new SortedList<>(filterData);

            tableuser.setItems(sortedData);

        } catch (SQLException e){
            Logger.getLogger(ChamCongController.class.getName()).log(Level.SEVERE,null, e);
            e.printStackTrace();
        }


    }

    Alert alert;
    public void SuaNhanVien() {
        try {

            String sql = "UPDATE nhanvien SET hoten = ?, diachi = ?, sdt = ?, ngayvaolam = ? WHERE msnv = ?";
            Connection connect = DBConnection.ConnectDB();
            PreparedStatement prepare = connect.prepareStatement(sql);

            String NoiDungConThieu = "";
            if (textMSNV.getText().isEmpty()) {
                NoiDungConThieu += "Thiếu Mã Nhân Viên Để Sửa.\n";
            }
            if (textHoten.getText().isEmpty()) {
                NoiDungConThieu += "Thiếu Họ Tên Để Sửa.\n";
            }
            if (textDiachi.getText() == null) {
                NoiDungConThieu += "Thiếu Địa Chỉ Để Sửa.\n";
            }
            if (textSdt.getText() == null) {
                NoiDungConThieu += "Thiếu Số Điện Thoại Để Sửa.\n";
            }
            if (textNgayvaolam.getText() == null) {
                NoiDungConThieu += "Thiếu Ngày Vào Làm Để Sửa.\n";
            }
            if (!NoiDungConThieu.isEmpty()) {
                alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Thông Báo Cho Bạn");
                alert.setHeaderText(null);
                alert.setContentText(NoiDungConThieu);
                alert.showAndWait();
            } else if (!textSdt.getText().matches("\\d+") || Integer.parseInt(textSdt.getText()) < 0) {
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Thông Báo Cho Bạn");
                    alert.setContentText("Số Điện Thoại Không Hợp Lệ");
                    alert.initOwner(textSdt.getScene().getWindow()); // Set the parent window
                    alert.showAndWait();
                });
                return;
            }
            else {
                prepare = connect.prepareStatement(sql);
                prepare.setString(5, textMSNV.getText());
                prepare.setString(1, textHoten.getText());
                prepare.setString(2, textDiachi.getText());
                prepare.setString(3, textSdt.getText());
                prepare.setString(4, textNgayvaolam.getText());
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
    public ObservableList<nhanvientable> DuLieuNhanVien() {
        ObservableList<nhanvientable> CacNhanVien = FXCollections.observableArrayList();
        String sql = "SELECT MSNV, HoTen, DiaChi, SDT, NgayVaoLam, sogiolam1tuan, tongsogio FROM NhanVien";
        Connection connection = DBConnection.ConnectDB();
        DBConnection connectionDB = new DBConnection();
        try {
            nhanvientable nhanVien;
            PreparedStatement prepare = connectionDB.prepareStatement(sql);
            ResultSet result = prepare.executeQuery();
            while (result.next()) {
                nhanVien = new nhanvientable(result.getInt("MSNV"),
                        result.getString("HoTen"),
                        result.getString("DiaChi"),
                        result.getString("SDT"),
                        result.getDate("NgayVaoLam"),
                        result.getInt("sogiolam1tuan"),
                        result.getInt("tongsogio")
                );
                CacNhanVien.add(nhanVien);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return CacNhanVien;
    }
    private ObservableList<nhanvientable> Danhsachnhanvien;

    public void refreshTable(){
        Danhsachnhanvien = DuLieuNhanVien();
        col_msnv.setCellValueFactory(new PropertyValueFactory<>("msnv"));
        col_hoten.setCellValueFactory(new PropertyValueFactory<>("hoten"));
        col_diachi.setCellValueFactory(new PropertyValueFactory<>("diachi"));
        col_sogiolam1tuan.setCellValueFactory(new PropertyValueFactory<>("sogiolam1tuan"));
        col_tongsogio.setCellValueFactory(new PropertyValueFactory<>("tongsogio"));
        col_ngayvaolam.setCellValueFactory(new PropertyValueFactory<>("ngayvaolam"));
        col_sdt.setCellValueFactory(new PropertyValueFactory<>("sdt"));
        tableuser.setItems(Danhsachnhanvien);


    }
    public void ThemNhanVien(ActionEvent event) throws SQLException {
        String hoten = textHoten.getText();
        String diachi = textDiachi.getText();
        String msnv = textMSNV.getText();
        String ngayvaolam = textNgayvaolam.getText();
        String sdt = textSdt.getText();

        try {
            Connection connect = DBConnection.ConnectDB();
            String insertQuery = "INSERT INTO NhanVien (msnv, hoten, diachi,sdt,  ngayvaolam, sogiolam1tuan, tongsogio) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = connect.prepareStatement(insertQuery);
            pstmt.setString(1, msnv);
            pstmt.setString(2, hoten);
            pstmt.setString(3, diachi);
            pstmt.setString(4, sdt);
            pstmt.setString(5, ngayvaolam);
            pstmt.setString(6, "0");
            pstmt.setString(7, "0");
            pstmt.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.ERROR.INFORMATION);
            alert.setTitle("Thành Công");
            alert.setHeaderText(null);
            alert.setContentText("Thêm nhân viên thành công!");
            alert.showAndWait();
            refreshTable();


            textHoten.clear();
            textDiachi.clear();
            textMSNV.clear();
            textNgayvaolam.clear();
            textSdt.clear();
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
