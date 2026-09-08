package vn.edu.eaut.model.dao;

import vn.edu.eaut.connection.DatabaseConnection;
import vn.edu.eaut.model.ChucVu;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChucVuDAO {
    public List<ChucVu> getAllChucVu() {
        List<ChucVu> list = new ArrayList<>();
        String sql = "SELECT * FROM chuc_vu";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ChucVu cv = new ChucVu(
                        rs.getInt("id"),
                        rs.getString("ma_chuc_vu"),
                        rs.getString("ten_chuc_vu"),
                        rs.getDouble("phu_cap"),
                        rs.getString("mo_ta")
                );
                list.add(cv);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}