package vn.edu.eaut.model;

public class PhongBan {
    private int id;
    private String maPhongBan;
    private String tenPhongBan;
    private String moTa;

    public PhongBan() {}

    public PhongBan(int id, String maPhongBan, String tenPhongBan, String moTa) {
        this.id = id;
        this.maPhongBan = maPhongBan;
        this.tenPhongBan = tenPhongBan;
        this.moTa = moTa;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getMaPhongBan() { return maPhongBan; }
    public void setMaPhongBan(String maPhongBan) { this.maPhongBan = maPhongBan; }

    public String getTenPhongBan() { return tenPhongBan; }
    public void setTenPhongBan(String tenPhongBan) { this.tenPhongBan = tenPhongBan; }

    public String getMoTa() { return moTa; }
    public void setMoTa(String moTa) { this.moTa = moTa; }
}