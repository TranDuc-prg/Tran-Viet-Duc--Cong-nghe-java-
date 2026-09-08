package vn.edu.eaut.model;

public class BangLuong {
    private int id;
    private int nhanVienId;
    private int thang;
    private int nam;
    private double luongCoBan;
    private double phuCap;
    private double tienTangCa;
    private double khauTru;
    private double tongLuong;
    private String trangThai;

    public BangLuong() {}

    public BangLuong(int id, int nhanVienId, int thang, int nam, double luongCoBan, double phuCap, double tienTangCa, double khauTru, double tongLuong, String trangThai) {
        this.id = id;
        this.nhanVienId = nhanVienId;
        this.thang = thang;
        this.nam = nam;
        this.luongCoBan = luongCoBan;
        this.phuCap = phuCap;
        this.tienTangCa = tienTangCa;
        this.khauTru = khauTru;
        this.tongLuong = tongLuong;
        this.trangThai = trangThai;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getNhanVienId() { return nhanVienId; }
    public void setNhanVienId(int nhanVienId) { this.nhanVienId = nhanVienId; }

    public int getThang() { return thang; }
    public void setThang(int thang) { this.thang = thang; }

    public int getNam() { return nam; }
    public void setNam(int nam) { this.nam = nam; }

    public double getLuongCoBan() { return luongCoBan; }
    public void setLuongCoBan(double luongCoBan) { this.luongCoBan = luongCoBan; }

    public double getPhuCap() { return phuCap; }
    public void setPhuCap(double phuCap) { this.phuCap = phuCap; }

    public double getTienTangCa() { return tienTangCa; }
    public void setTienTangCa(double tienTangCa) { this.tienTangCa = tienTangCa; }

    public double getKhauTru() { return khauTru; }
    public void setKhauTru(double khauTru) { this.khauTru = khauTru; }

    public double getTongLuong() { return tongLuong; }
    public void setTongLuong(double tongLuong) { this.tongLuong = tongLuong; }

    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }
}