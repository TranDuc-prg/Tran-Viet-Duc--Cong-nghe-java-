package vn.edu.eaut.lab5.model;

public class SanPham {
    private int maSp;
    private String tenSp;
    private double donGia; // Đổi thành double
    private int soLuong;

    public int getMaSp() { return maSp; }
    public void setMaSp(int maSp) { this.maSp = maSp; }

    public String getTenSp() { return tenSp; }
    public void setTenSp(String tenSp) { this.tenSp = tenSp; }

    public double getDonGia() { return donGia; }
    public void setDonGia(double donGia) { this.donGia = donGia; }

    public int getSoLuong() { return soLuong; }
    public void setSoLuong(int soLuong) { this.soLuong = soLuong; }
}