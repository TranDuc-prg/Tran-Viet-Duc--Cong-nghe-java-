package vn.edu.eaut.model.dao;

import vn.edu.eaut.connection.DatabaseConnection;
import vn.edu.eaut.model.ChamCong;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChamCongDAO {

    public List<ChamCong> getAllChamCong() {
        List<ChamCong> list = new ArrayList<>();
        String sql = "SELECT * FROM cham_cong";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ChamCong cc = new ChamCong(
                        rs.getInt("id"),
                        rs.getInt("nhan_vien_id"),
                        rs.getTimestamp("ngay_gio"),
                        rs.getString("trang_thai")
                );
                list.add(cc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Phương thức lưu chấm công phù hợp với cấu trúc bảng `cham_cong`
    public boolean saveChamCong(int nhanVienId) {
        String sql = "INSERT INTO cham_cong (nhan_vien_id, ngay_gio, trang_thai) VALUES (?, NOW(), 'Vào ca')";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, nhanVienId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}