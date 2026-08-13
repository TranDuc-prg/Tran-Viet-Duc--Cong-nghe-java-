package vn.edu.eaut.lab5.dal;

import vn.edu.eaut.lab5.config.DBHelper;
import vn.edu.eaut.lab5.model.SanPham;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SanPhamDAL {

    public List<SanPham> getAll() throws SQLException {
        List<SanPham> list = new ArrayList<>();
        String sql = "SELECT * FROM san_pham";
        try (Connection conn = DBHelper.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                SanPham sp = new SanPham();
                sp.setMaSp(rs.getInt("ma_sp"));
                sp.setTenSp(rs.getString("ten_sp"));
                // Chuyển BigDecimal từ DB sang double cho Model
                BigDecimal gia = rs.getBigDecimal("don_gia");
                sp.setDonGia(gia != null ? gia.doubleValue() : 0.0);
                sp.setSoLuong(rs.getInt("so_luong"));
                list.add(sp);
            }
        }
        return list;
    }

    public List<SanPham> searchByName(String keyword) throws SQLException {
        List<SanPham> list = new ArrayList<>();
        String sql = "SELECT * FROM san_pham WHERE ten_sp LIKE ?";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + keyword + "%");
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    SanPham sp = new SanPham();
                    sp.setMaSp(rs.getInt("ma_sp"));
                    sp.setTenSp(rs.getString("ten_sp"));
                    BigDecimal gia = rs.getBigDecimal("don_gia");
                    sp.setDonGia(gia != null ? gia.doubleValue() : 0.0);
                    sp.setSoLuong(rs.getInt("so_luong"));
                    list.add(sp);
                }
            }
        }
        return list;
    }

    public boolean insert(SanPham sp) throws SQLException {
        String sql = "INSERT INTO san_pham (ten_sp, don_gia, so_luong) VALUES (?, ?, ?)";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, sp.getTenSp());
            pstmt.setBigDecimal(2, BigDecimal.valueOf(sp.getDonGia()));
            pstmt.setInt(3, sp.getSoLuong());
            return pstmt.executeUpdate() > 0;
        }
    }

    public boolean update(SanPham sp) throws SQLException {
        String sql = "UPDATE san_pham SET ten_sp = ?, don_gia = ?, so_luong = ? WHERE ma_sp = ?";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, sp.getTenSp());
            pstmt.setBigDecimal(2, BigDecimal.valueOf(sp.getDonGia()));
            pstmt.setInt(3, sp.getSoLuong());
            pstmt.setInt(4, sp.getMaSp());
            return pstmt.executeUpdate() > 0;
        }
    }

    public boolean delete(int maSp) throws SQLException {
        String sql = "DELETE FROM san_pham WHERE ma_sp = ?";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, maSp);
            return pstmt.executeUpdate() > 0;
        }
    }
}