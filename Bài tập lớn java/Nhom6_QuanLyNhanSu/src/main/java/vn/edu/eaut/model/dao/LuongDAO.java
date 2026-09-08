package vn.edu.eaut.model.dao;

import vn.edu.eaut.connection.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LuongDAO {
    // Chức năng chốt bảng lương sử dụng Transaction đảm bảo toàn vẹn dữ liệu
    public boolean chotBangLuongThang(int thang, int nam) {
        String sql = "INSERT INTO bang_luong (nhan_vien_id, thang, nam, luong_co_ban, trang_thai) " +
                "SELECT id, ?, ?, luong_co_ban, 'Đã chốt' FROM nhan_vien";
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false); // Bắt đầu Transaction

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, thang);
                ps.setInt(2, nam);
                ps.executeUpdate();
            }

            conn.commit(); // Xác nhận giao dịch thành công
            return true;
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback(); // Hoàn tác nếu có lỗi xảy ra
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }
}