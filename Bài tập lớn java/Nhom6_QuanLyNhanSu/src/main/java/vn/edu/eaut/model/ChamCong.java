package vn.edu.eaut.model;

import java.util.Date;

public class ChamCong {
    private int id;
    private int nhanVienId;
    private Date ngayGio;
    private String trangThai;

    public ChamCong() {}

    public ChamCong(int id, int nhanVienId, Date ngayGio, String trangThai) {
        this.id = id;
        this.nhanVienId = nhanVienId;
        this.ngayGio = ngayGio;
        this.trangThai = trangThai;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getNhanVienId() { return nhanVienId; }
    public void setNhanVienId(int nhanVienId) { this.nhanVienId = nhanVienId; }
    public Date getNgayGio() { return ngayGio; }
    public void setNgayGio(Date ngayGio) { this.ngayGio = ngayGio; }
    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }
}