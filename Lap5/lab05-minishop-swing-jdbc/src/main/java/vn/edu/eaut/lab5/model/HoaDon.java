package vn.edu.eaut.lab5.model;

import java.math.BigDecimal;
import java.sql.Date;

public class HoaDon {
    private int maHd;
    private Date ngayLap;
    private int maKh;
    private BigDecimal tongTien;

    public int getMaHd() { return maHd; }
    public void setMaHd(int maHd) { this.maHd = maHd; }

    public Date getNgayLap() { return ngayLap; }
    public void setNgayLap(Date ngayLap) { this.ngayLap = ngayLap; }

    public int getMaKh() { return maKh; }
    public void setMaKh(int maKh) { this.maKh = maKh; }

    public BigDecimal getTongTien() { return tongTien; }
    public void setTongTien(BigDecimal tongTien) { this.tongTien = tongTien; }
}