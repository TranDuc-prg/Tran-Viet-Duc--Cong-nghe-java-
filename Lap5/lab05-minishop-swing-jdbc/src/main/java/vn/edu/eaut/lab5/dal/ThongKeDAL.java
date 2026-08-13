package vn.edu.eaut.lab5.dal;

import vn.edu.eaut.lab5.config.DBHelper;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class ThongKeDAL {

    // 1. Tính doanh thu theo khoảng ngày
    public BigDecimal tinhDoanhThu(LocalDate tuNgay, LocalDate denNgay) throws SQLException {
        String sql = "SELECT COALESCE(SUM(tong_tien), 0) AS doanh_thu FROM hoa_don WHERE ngay_lap BETWEEN ? AND ?";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(tuNgay));
            ps.setDate(2, Date.valueOf(denNgay));
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getBigDecimal("doanh_thu");
                }
            }
        }
        return BigDecimal.ZERO;
    }

    // 2. Lấy hóa đơn có giá trị cao nhất
    public Map<String, Object> getHoaDonCaoNhat() throws SQLException {
        String sql = "SELECT ma_hd, ngay_lap, ma_kh, tong_tien FROM hoa_don ORDER BY tong_tien DESC LIMIT 1";
        Map<String, Object> result = new HashMap<>();
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                result.put("ma_hd", rs.getInt("ma_hd"));
                result.put("ngay_lap", rs.getDate("ngay_lap"));
                result.put("ma_kh", rs.getInt("ma_kh"));
                result.put("tong_tien", rs.getBigDecimal("tong_tien"));
            }
        }
        return result;
    }

    // 3. Lấy sản phẩm bán chạy nhất
    public Map<String, Object> getSanPhamBanChayNhat() throws SQLException {
        String sql = "SELECT sp.ma_sp, sp.ten_sp, SUM(ct.so_luong) AS tong_so_luong " +
                "FROM chi_tiet_hoa_don ct " +
                "JOIN san_pham sp ON ct.ma_sp = sp.ma_sp " +
                "GROUP BY sp.ma_sp, sp.ten_sp " +
                "ORDER BY tong_so_luong DESC LIMIT 1";
        Map<String, Object> result = new HashMap<>();
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                result.put("ma_sp", rs.getInt("ma_sp"));
                result.put("ten_sp", rs.getString("ten_sp"));
                result.put("tong_so_luong", rs.getInt("tong_so_luong"));
            }
        }
        return result;
    }
}