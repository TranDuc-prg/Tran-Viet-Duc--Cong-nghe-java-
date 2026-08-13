package vn.edu.eaut.lab5.model;

import java.math.BigDecimal;

public class ChiTietHoaDon {
    private int maCt;
    private int maHd;
    private int maSp;
    private int soLuong;
    private BigDecimal donGia;

    public int getMaCt() { return maCt; }
    public void setMaCt(int maCt) { this.maCt = maCt; }

    public int getMaHd() { return maHd; }
    public void setMaHd(int maHd) { this.maHd = maHd; }

    public int getMaSp() { return maSp; }
    public void setMaSp(int maSp) { this.maSp = maSp; }

    public int getSoLuong() { return soLuong; }
    public void setSoLuong(int soLuong) { this.soLuong = soLuong; }

    public BigDecimal getDonGia() { return donGia; }
    public void setDonGia(BigDecimal donGia) { this.donGia = donGia; }
}