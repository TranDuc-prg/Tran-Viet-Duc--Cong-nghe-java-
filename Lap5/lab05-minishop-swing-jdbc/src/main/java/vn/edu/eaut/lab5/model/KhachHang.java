package vn.edu.eaut.lab5.model;

public class KhachHang {
    private int maKh;
    private String tenKh;
    private String dienThoai;
    private String diaChi;
    private String email;

    // Getters và Setters
    public int getMaKh() { return maKh; }
    public void setMaKh(int maKh) { this.maKh = maKh; }

    public String getTenKh() { return tenKh; }
    public void setTenKh(String tenKh) { this.tenKh = tenKh; }

    public String getDienThoai() { return dienThoai; }
    public void setDienThoai(String dienThoai) { this.dienThoai = dienThoai; }

    // Alias hỗ trợ nếu code gọi sdt
    public String getSdt() { return dienThoai; }
    public void setSdt(String sdt) { this.dienThoai = sdt; }

    public String getDiaChi() { return diaChi; }
    public void setDiaChi(String diaChi) { this.diaChi = diaChi; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}