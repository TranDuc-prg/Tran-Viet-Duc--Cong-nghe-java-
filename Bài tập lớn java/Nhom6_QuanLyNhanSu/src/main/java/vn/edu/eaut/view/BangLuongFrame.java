package vn.edu.eaut.view;

import vn.edu.eaut.connection.DatabaseConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;

public class BangLuongFrame extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnTinhLuong, btnChotLuong, btnLamMoi;
    private DecimalFormat currencyFormat = new DecimalFormat("#,###");

    public BangLuongFrame() {
        setLayout(new BorderLayout(0, 12));
        setBackground(UITheme.BG);
        setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));

        // Tiêu đề
        JPanel header = new JPanel();
        header.setOpaque(false);
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        JLabel lblTitle = UITheme.sectionTitle("Bảng lương nhân viên");
        JLabel sub = UITheme.sectionSubtitle("Theo dõi lương cơ bản, phụ cấp, khấu trừ và thực nhận");
        header.add(lblTitle);
        header.add(Box.createVerticalStrut(3));
        header.add(sub);
        add(header, BorderLayout.NORTH);

        // Bảng dữ liệu
        tableModel = new DefaultTableModel(new String[]{"ID", "Mã NV", "Họ Tên", "Lương Cơ Bản", "Phụ Cấp", "Khấu Trừ", "Thực Nhận", "Trạng Thái"}, 0);
        table = new JTable(tableModel);
        UITheme.styleTable(table);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createLineBorder(UITheme.BORDER));
        add(scroll, BorderLayout.CENTER);

        // Panel chức năng
        JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 5));
        panelButtons.setOpaque(false);
        btnTinhLuong = UITheme.button("Tính Lương", UITheme.GREEN, Color.WHITE);
        btnChotLuong = UITheme.button("Chốt Bảng Lương", new Color(59, 130, 246), Color.WHITE);
        btnLamMoi = UITheme.button("Làm Mới", UITheme.NAVY_2, Color.WHITE);

        panelButtons.add(btnTinhLuong);
        panelButtons.add(btnChotLuong);
        panelButtons.add(btnLamMoi);
        add(panelButtons, BorderLayout.SOUTH);

        // Load dữ liệu ban đầu
        loadData();

        // Sự kiện nút tính lương
        btnTinhLuong.addActionListener(e -> {
            loadData();
            JOptionPane.showMessageDialog(this, "Đã tính toán lại toàn bộ bảng lương thành công!");
        });

        // Sự kiện nút làm mới
        btnLamMoi.addActionListener(e -> loadData());

        // Sự kiện nút chốt lương
        btnChotLuong.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                tableModel.setValueAt("Đã chốt", selectedRow, 7);
                JOptionPane.showMessageDialog(this, "Đã chốt bảng lương cho nhân viên được chọn!");
            } else {
                int confirm = JOptionPane.showConfirmDialog(this, "Bạn có muốn chốt bảng lương cho toàn bộ danh sách?", "Xác nhận", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    for (int i = 0; i < tableModel.getRowCount(); i++) {
                        tableModel.setValueAt("Đã chốt", i, 7);
                    }
                    JOptionPane.showMessageDialog(this, "Đã chốt toàn bộ bảng lương thành công!");
                }
            }
        });
    }

    public void loadData() {
        tableModel.setRowCount(0);

        // Truy vấn lấy nhân viên và tính tổng phụ cấp, khấu trừ tương ứng từ cơ sở dữ liệu
        String sql = "SELECT n.id, n.ma_nv, n.ho_ten, n.luong_co_ban, " +
                "(SELECT COALESCE(SUM(so_tien), 0) FROM phu_cap pc WHERE pc.nhan_vien_id = n.id) AS tong_phu_cap, " +
                "(SELECT COALESCE(SUM(so_tien), 0) FROM khau_tru kt WHERE kt.nhan_vien_id = n.id) AS tong_khau_tru " +
                "FROM nhan_vien n";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                double luongCoBan = rs.getDouble("luong_co_ban");
                double phuCap = rs.getDouble("tong_phu_cap");
                double khauTru = rs.getDouble("tong_khau_tru");
                double thucNhan = luongCoBan + phuCap - khauTru;

                Object[] row = {
                        rs.getInt("id"),
                        rs.getString("ma_nv"),
                        rs.getString("ho_ten"),
                        currencyFormat.format(luongCoBan) + " VNĐ",
                        currencyFormat.format(phuCap) + " VNĐ",
                        currencyFormat.format(khauTru) + " VNĐ",
                        currencyFormat.format(thucNhan) + " VNĐ",
                        "Chưa chốt"
                };
                tableModel.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}