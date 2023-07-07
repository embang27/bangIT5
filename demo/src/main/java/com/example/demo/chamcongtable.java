package com.example.demo;

import java.util.Date;

public class chamcongtable {
    int msnv, tongsogio, luong;
    String hoten;
    Date ngaythangnam;

    public chamcongtable(int msnv, int tongsogio, int luong, String hoten, Date ngaythangnam) {
        this.msnv = msnv;
        this.tongsogio = tongsogio;
        this.luong = luong;
        this.hoten = hoten;
        this.ngaythangnam = ngaythangnam;
    }

    public int getMsnv() {
        return msnv;
    }

    public int getTongsogio() {
        return tongsogio;
    }

    public int getLuong() {
        return luong;
    }

    public String getHoten() {
        return hoten;
    }

    public Date getNgaythangnam() {
        return ngaythangnam;
    }

    public void setMsnv(int msnv) {
        this.msnv = msnv;
    }

    public void setTongsogio(int tongsogio) {
        this.tongsogio = tongsogio;
    }

    public void setLuong(int luong) {
        this.luong = luong;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public void setNgaythangnam(Date ngaythangnam) {
        this.ngaythangnam = ngaythangnam;
    }
}
