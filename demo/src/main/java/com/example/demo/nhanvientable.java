package com.example.demo;

import java.util.Date;

public class nhanvientable {
        int msnv, sogiolam1tuan, tongsogio;
    String hoten, diachi, sdt;
    Date ngayvaolam;

    public nhanvientable(int msnv, String hoten, String sdt, String diachi, Date ngayvaolam, int sogiolam1tuan, int tongsogio) {
        this.msnv = msnv;
        this.sdt = sdt;
        this.sogiolam1tuan = sogiolam1tuan;
        this.tongsogio = tongsogio;
        this.hoten = hoten;
        this.diachi = diachi;
        this.ngayvaolam = ngayvaolam;
    }



    public int getMsnv() {
        return msnv;
    }

    public void setMsnv(int msnv) {
        this.msnv = msnv;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public int getSogiolam1tuan() {
        return sogiolam1tuan;
    }

    public void setSogiolam1tuan(int sogiolam1tuan) {
        this.sogiolam1tuan = sogiolam1tuan;
    }

    public int getTongsogio() {
        return tongsogio;
    }

    public void setTongsogio(int tongsogio) {
        this.tongsogio = tongsogio;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public Date getNgayvaolam() {
        return ngayvaolam;
    }

    public void setNgayvaolam(Date ngayvaolam) {
        this.ngayvaolam = ngayvaolam;
    }
}
