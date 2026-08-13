package vn.edu.eaut.lab5.dal;

import vn.edu.eaut.lab5.config.DBHelper;
import vn.edu.eaut.lab5.model.ChiTietHoaDon;
import java.sql.*;
import java.util.List;

public class HoaDonDAL {
    public int insertHoaDon(int maKh, List<ChiTietHoaDon> chiTietList) throws SQLException {
        String sqlHoaDon = "INSERT INTO hoa_don (ngay_lap, ma_kh, tong_tien) VALUES (NOW(), ?, ?)";
        String sqlChiTiet = "INSERT INTO chi_tiet_hoa_don (ma_hd, ma_sp, so_luong, don_gia) VALUES (?, ?, ?, ?)";
        String sqlUpdateStock = "UPDATE san_pham SET so_luong = so_luong - ? WHERE ma_sp = ?";

        Connection conn = null;
        try {
            conn = DBHelper.getConnection();
            conn.setAutoCommit(false); // Bật giao dịch (Transaction)

            // 1. Tính tổng tiền hóa đơn
            java.math.BigDecimal tongTien = java.math.BigDecimal.ZERO;
            for (ChiTietHoaDon ct : chiTietList) {
                java.math.BigDecimal thanhTien = ct.getDonGia().multiply(java.math.BigDecimal.valueOf(ct.getSoLuong()));
                tongTien = tongTien.add(thanhTien);
            }

            // 2. Thêm hóa đơn
            int maHd = -1;
            try (PreparedStatement psHd = conn.prepareStatement(sqlHoaDon, Statement.RETURN_GENERATED_KEYS)) {
                psHd.setInt(1, maKh);
                psHd.setBigDecimal(2, tongTien);
                psHd.executeUpdate();

                try (ResultSet rs = psHd.getGeneratedKeys()) {
                    if (rs.next()) {
                        maHd = rs.getInt(1);
                    }
                }
            }

            if (maHd == -1) {
                conn.rollback();
                return -1;
            }

            // 3. Thêm chi tiết và trừ tồn kho
            try (PreparedStatement psCt = conn.prepareStatement(sqlChiTiet);
                 PreparedStatement psStock = conn.prepareStatement(sqlUpdateStock)) {
                for (ChiTietHoaDon ct : chiTietList) {
                    psCt.setInt(1, maHd);
                    psCt.setInt(2, ct.getMaSp());
                    psCt.setInt(3, ct.getSoLuong());
                    psCt.setBigDecimal(4, ct.getDonGia());
                    psCt.executeUpdate();

                    psStock.setInt(1, ct.getSoLuong());
                    psStock.setInt(2, ct.getMaSp());
                    psStock.executeUpdate();
                }
            }

            conn.commit(); // Xác nhận giao dịch thành công
            return maHd;
        } catch (SQLException e) {
            if (conn != null) conn.rollback();
            throw e;
        } finally {
            if (conn != null) conn.setAutoCommit(true);
        }
    }
}