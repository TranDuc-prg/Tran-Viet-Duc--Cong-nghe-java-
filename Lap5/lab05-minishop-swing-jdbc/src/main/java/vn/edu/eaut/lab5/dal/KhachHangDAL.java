package vn.edu.eaut.lab5.dal;

import vn.edu.eaut.lab5.config.DBHelper;
import vn.edu.eaut.lab5.model.KhachHang;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KhachHangDAL {

    public List<KhachHang> getAll() throws SQLException {
        List<KhachHang> list = new ArrayList<>();
        String sql = "SELECT * FROM khach_hang";
        try (Connection conn = DBHelper.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                KhachHang kh = new KhachHang();
                kh.setMaKh(rs.getInt("ma_kh"));
                kh.setTenKh(rs.getString("ten_kh"));
                kh.setDienThoai(rs.getString("dien_thoai"));
                kh.setDiaChi(rs.getString("dia_chi"));
                list.add(kh);
            }
        }
        return list;
    }

    public boolean insert(KhachHang kh) throws SQLException {
        String sql = "INSERT INTO khach_hang (ten_kh, dien_thoai, dia_chi) VALUES (?, ?, ?)";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, kh.getTenKh());
            pstmt.setString(2, kh.getDienThoai());
            pstmt.setString(3, kh.getDiaChi());
            return pstmt.executeUpdate() > 0;
        }
    }

    public boolean update(KhachHang kh) throws SQLException {
        String sql = "UPDATE khach_hang SET ten_kh = ?, dien_thoai = ?, dia_chi = ? WHERE ma_kh = ?";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, kh.getTenKh());
            pstmt.setString(2, kh.getDienThoai());
            pstmt.setString(3, kh.getDiaChi());
            pstmt.setInt(4, kh.getMaKh());
            return pstmt.executeUpdate() > 0;
        }
    }

    public boolean delete(int maKh) throws SQLException {
        String sql = "DELETE FROM khach_hang WHERE ma_kh = ?";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, maKh);
            return pstmt.executeUpdate() > 0;
        }
    }
}