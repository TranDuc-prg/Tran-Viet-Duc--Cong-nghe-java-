package vn.edu.eaut.model.dao;

import vn.edu.eaut.connection.DatabaseConnection;
import vn.edu.eaut.model.PhongBan;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PhongBanDAO {
    public List<PhongBan> getAllPhongBan() {
        List<PhongBan> list = new ArrayList<>();
        String sql = "SELECT * FROM phong_ban";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                PhongBan pb = new PhongBan(
                        rs.getInt("id"),
                        rs.getString("ma_phong_ban"),
                        rs.getString("ten_phong_ban"),
                        rs.getString("mo_ta")
                );
                list.add(pb);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean addPhongBan(PhongBan pb) {
        String sql = "INSERT INTO phong_ban (ma_phong_ban, ten_phong_ban, mo_ta) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, pb.getMaPhongBan());
            ps.setString(2, pb.getTenPhongBan());
            ps.setString(3, pb.getMoTa());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}