package vn.edu.eaut.model;

import java.util.Date;

public class HopDong {
    private int id;
    private String maHopDong;
    private int nhanVienId;
    private String loaiHopDong;
    private Date ngayBatDau;
    private Date ngayKetThuc;
    private double mucLuong;
    private String trangThai;

    public HopDong() {}

    public HopDong(int id, String maHopDong, int nhanVienId, String loaiHopDong, Date ngayBatDau, Date ngayKetThuc, double mucLuong, String trangThai) {
        this.id = id;
        this.maHopDong = maHopDong;
        this.nhanVienId = nhanVienId;
        this.loaiHopDong = loaiHopDong;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.mucLuong = mucLuong;
        this.trangThai = trangThai;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getMaHopDong() { return maHopDong; }
    public void setMaHopDong(String maHopDong) { this.maHopDong = maHopDong; }

    public int getNhanVienId() { return nhanVienId; }
    public void setNhanVienId(int nhanVienId) { this.nhanVienId = nhanVienId; }

    public String getLoaiHopDong() { return loaiHopDong; }
    public void setLoaiHopDong(String loaiHopDong) { this.loaiHopDong = loaiHopDong; }

    public Date getNgayBatDau() { return ngayBatDau; }
    public void setNgayBatDau(Date ngayBatDau) { this.ngayBatDau = ngayBatDau; }

    public Date getNgayKetThuc() { return ngayKetThuc; }
    public void setNgayKetThuc(Date ngayKetThuc) { this.ngayKetThuc = ngayKetThuc; }

    public double getMucLuong() { return mucLuong; }
    public void setMucLuong(double mucLuong) { this.mucLuong = mucLuong; }

    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }
}