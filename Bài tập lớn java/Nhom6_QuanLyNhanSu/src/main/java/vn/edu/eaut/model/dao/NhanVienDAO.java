package vn.edu.eaut.model.dao;

import vn.edu.eaut.connection.DatabaseConnection;
import vn.edu.eaut.model.NhanVien;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NhanVienDAO {

    // Lấy danh sách nhân viên
    public List<NhanVien> getAllNhanVien() {
        List<NhanVien> list = new ArrayList<>();
        String sql = "SELECT * FROM nhan_vien";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                NhanVien nv = new NhanVien();
                nv.setId(rs.getInt("id"));
                nv.setMaNv(rs.getString("ma_nv"));
                nv.setHoTen(rs.getString("ho_ten"));
                nv.setGioiTinh(rs.getString("gioi_tinh"));
                nv.setNgaySinh(rs.getDate("ngay_sinh"));
                nv.setDienThoai(rs.getString("dien_thoai"));
                nv.setEmail(rs.getString("email"));
                nv.setPhongBanId(rs.getInt("phong_ban_id"));
                nv.setLuongCoBan(rs.getDouble("luong_co_ban"));

                list.add(nv);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Thêm nhân viên mới vào cơ sở dữ liệu
    public boolean insertNhanVien(NhanVien nv) {
        String sql = "INSERT INTO nhan_vien (ma_nv, ho_ten, gioi_tinh, ngay_sinh, dien_thoai, email, phong_ban_id, luong_co_ban) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nv.getMaNv());
            ps.setString(2, nv.getHoTen());
            ps.setString(3, nv.getGioiTinh());

            // Ép kiểu chuẩn java.sql.Date
            if (nv.getNgaySinh() != null) {
                ps.setDate(4, new java.sql.Date(nv.getNgaySinh().getTime()));
            } else {
                ps.setNull(4, java.sql.Types.DATE);
            }

            ps.setString(5, nv.getDienThoai());
            ps.setString(6, nv.getEmail());
            ps.setInt(7, nv.getPhongBanId());
            ps.setDouble(8, nv.getLuongCoBan());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Cập nhật thông tin nhân viên
    public boolean updateNhanVien(NhanVien nv) {
        String sql = "UPDATE nhan_vien SET ma_nv = ?, ho_ten = ?, gioi_tinh = ?, ngay_sinh = ?, dien_thoai = ?, email = ?, phong_ban_id = ?, luong_co_ban = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nv.getMaNv());
            ps.setString(2, nv.getHoTen());
            ps.setString(3, nv.getGioiTinh());

            // Ép kiểu chuẩn java.sql.Date
            if (nv.getNgaySinh() != null) {
                ps.setDate(4, new java.sql.Date(nv.getNgaySinh().getTime()));
            } else {
                ps.setNull(4, java.sql.Types.DATE);
            }

            ps.setString(5, nv.getDienThoai());
            ps.setString(6, nv.getEmail());
            ps.setInt(7, nv.getPhongBanId());
            ps.setDouble(8, nv.getLuongCoBan());
            ps.setInt(9, nv.getId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Xóa nhân viên theo ID
    public boolean deleteNhanVien(int id) {
        String sql = "DELETE FROM nhan_vien WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Kiểm tra đăng nhập và trả về quyền (role)
    public String checkLogin(String username, String password) {
        String sql = "SELECT role FROM tai_khoan WHERE username = ? AND password = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("role");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Đăng ký tài khoản mới
    public boolean registerAccount(String username, String password) {
        String checkSql = "SELECT * FROM tai_khoan WHERE username = ?";
        String insertSql = "INSERT INTO tai_khoan (username, password, role) VALUES (?, ?, 'USER')";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement checkPs = conn.prepareStatement(checkSql)) {

            checkPs.setString(1, username);
            try (ResultSet rs = checkPs.executeQuery()) {
                if (rs.next()) {
                    return false;
                }
            }

            try (PreparedStatement insertPs = conn.prepareStatement(insertSql)) {
                insertPs.setString(1, username);
                insertPs.setString(2, password);
                int rowsAffected = insertPs.executeUpdate();
                return rowsAffected > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Lưu thông tin chấm công chi tiết thời gian thực
    public boolean saveChamCong(int nhanVienId) {
        String sql = "INSERT INTO cham_cong (nhan_vien_id, ngay_gio, trang_thai) VALUES (?, NOW(), 'Đúng giờ')";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, nhanVienId);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}