package vn.edu.eaut.model;

public class ChucVu {
    private int id;
    private String maChucVu;
    private String tenChucVu;
    private double phuCap;
    private String moTa;

    public ChucVu() {}

    public ChucVu(int id, String maChucVu, String tenChucVu, double phuCap, String moTa) {
        this.id = id;
        this.maChucVu = maChucVu;
        this.tenChucVu = tenChucVu;
        this.phuCap = phuCap;
        this.moTa = moTa;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getMaChucVu() { return maChucVu; }
    public void setMaChucVu(String maChucVu) { this.maChucVu = maChucVu; }
    public String getTenChucVu() { return tenChucVu; }
    public void setTenChucVu(String tenChucVu) { this.tenChucVu = tenChucVu; }
    public double getPhuCap() { return phuCap; }
    public void setPhuCap(double phuCap) { this.phuCap = phuCap; }
    public String getMoTa() { return moTa; }
    public void setMoTa(String moTa) { this.moTa = moTa; }
}