package vn.edu.eaut.model;

import java.util.Date;

public class NhanVien {
    private int id;
    private String maNv;
    private String hoTen;
    private String gioiTinh;
    private Date ngaySinh;
    private String dienThoai;
    private String email;
    private int phongBanId;
    private double luongCoBan;

    public NhanVien() {
    }

    public NhanVien(int id, String maNv, String hoTen, String gioiTinh, Date ngaySinh, String dienThoai, String email, int phongBanId, double luongCoBan) {
        this.id = id;
        this.maNv = maNv;
        this.hoTen = hoTen;
        this.gioiTinh = gioiTinh;
        this.ngaySinh = ngaySinh;
        this.dienThoai = dienThoai;
        this.email = email;
        this.phongBanId = phongBanId;
        this.luongCoBan = luongCoBan;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getMaNv() { return maNv; }
    public void setMaNv(String maNv) { this.maNv = maNv; }

    public String getHoTen() { return hoTen; }
    public void setHoTen(String hoTen) { this.hoTen = hoTen; }

    public String getGioiTinh() { return gioiTinh; }
    public void setGioiTinh(String gioiTinh) { this.gioiTinh = gioiTinh; }

    public Date getNgaySinh() { return ngaySinh; }
    public void setNgaySinh(Date ngaySinh) { this.ngaySinh = ngaySinh; }

    public String getDienThoai() { return dienThoai; }
    public void setDienThoai(String dienThoai) { this.dienThoai = dienThoai; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public int getPhongBanId() { return phongBanId; }
    public void setPhongBanId(int phongBanId) { this.phongBanId = phongBanId; }

    public double getLuongCoBan() { return luongCoBan; }
    public void setLuongCoBan(double luongCoBan) { this.luongCoBan = luongCoBan; }
}