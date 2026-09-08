package vn.edu.eaut.view;

import vn.edu.eaut.connection.DatabaseConnection;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class HopDongFrame extends JPanel { // Đổi từ JFrame sang JPanel
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtMaHD, txtLuong, txtNgayBD, txtNgayKT;
    private JComboBox<String> cbNhanVien, cbLoaiHD, cbTrangThai;

    public HopDongFrame() { // Constructor cùng tên với class
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(10, 10, 10, 10));

        // Tiêu đề
        JLabel lblHeader = new JLabel("QUẢN LÝ THÔNG TIN HỢP ĐỒNG LAO ĐỘNG", JLabel.CENTER);
        lblHeader.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblHeader.setForeground(new Color(41, 128, 185));
        lblHeader.setBorder(new EmptyBorder(0, 0, 10, 0));
        add(lblHeader, BorderLayout.NORTH);

        // --- Panel bên trái: Form nhập liệu ---
        JPanel panelForm = new JPanel(new GridBagLayout());
        panelForm.setBorder(BorderFactory.createTitledBorder("Chi tiết hợp đồng"));
        panelForm.setPreferredSize(new Dimension(350, 0));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0; panelForm.add(new JLabel("Mã hợp đồng:"), gbc);
        gbc.gridx = 1; txtMaHD = new JTextField(15); panelForm.add(txtMaHD, gbc);

        gbc.gridx = 0; gbc.gridy = 1; panelForm.add(new JLabel("Nhân viên:"), gbc);
        gbc.gridx = 1; cbNhanVien = new JComboBox<>(); loadNhanVienCombobox(); panelForm.add(cbNhanVien, gbc);

        gbc.gridx = 0; gbc.gridy = 2; panelForm.add(new JLabel("Loại hợp đồng:"), gbc);
        gbc.gridx = 1; cbLoaiHD = new JComboBox<>(new String[]{"Thử việc", "Xác định thời hạn", "Không xác định thời hạn"}); panelForm.add(cbLoaiHD, gbc);

        gbc.gridx = 0; gbc.gridy = 3; panelForm.add(new JLabel("Ngày bắt đầu (YYYY-MM-DD):"), gbc);
        gbc.gridx = 1; txtNgayBD = new JTextField(15); panelForm.add(txtNgayBD, gbc);

        gbc.gridx = 0; gbc.gridy = 4; panelForm.add(new JLabel("Ngày kết thúc (YYYY-MM-DD):"), gbc);
        gbc.gridx = 1; txtNgayKT = new JTextField(15); panelForm.add(txtNgayKT, gbc);

        gbc.gridx = 0; gbc.gridy = 5; panelForm.add(new JLabel("Mức lương HĐ:"), gbc);
        gbc.gridx = 1; txtLuong = new JTextField(15); panelForm.add(txtLuong, gbc);

        gbc.gridx = 0; gbc.gridy = 6; panelForm.add(new JLabel("Trạng thái:"), gbc);
        gbc.gridx = 1; cbTrangThai = new JComboBox<>(new String[]{"Hiệu lực", "Hết hiệu lực", "Đã thanh lý"}); panelForm.add(cbTrangThai, gbc);

        // Panel nút chức năng
        JPanel panelButtons = new JPanel(new GridLayout(2, 2, 8, 8));
        JButton btnThem = new JButton("Thêm");
        JButton btnSua = new JButton("Sửa");
        JButton btnXoa = new JButton("Xóa");
        JButton btnLamMoi = new JButton("Làm mới");

        styleButton(btnThem, new Color(46, 204, 113));
        styleButton(btnSua, new Color(241, 196, 15));
        styleButton(btnXoa, new Color(231, 76, 60));
        styleButton(btnLamMoi, new Color(52, 152, 219));

        panelButtons.add(btnThem);
        panelButtons.add(btnSua);
        panelButtons.add(btnXoa);
        panelButtons.add(btnLamMoi);

        gbc.gridx = 0; gbc.gridy = 7; gbc.gridwidth = 2;
        panelForm.add(panelButtons, gbc);

        add(panelForm, BorderLayout.WEST);

        // --- Panel bên phải: Bảng hiển thị danh sách ---
        tableModel = new DefaultTableModel(new String[]{"ID", "Mã HĐ", "Tên Nhân Viên", "Loại HĐ", "Ngày BĐ", "Ngày KT", "Lương", "Trạng Thái"}, 0);
        table = new JTable(tableModel);
        table.setRowHeight(25);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Danh sách hợp đồng lao động"));
        add(scrollPane, BorderLayout.CENTER);

        loadDataToTable();

        // Xử lý sự kiện nút Thêm
        btnThem.addActionListener(e -> {
            try {
                String maHD = txtMaHD.getText().trim();
                String selectedNV = (String) cbNhanVien.getSelectedItem();
                if (selectedNV == null) {
                    JOptionPane.showMessageDialog(this, "Chưa chọn nhân viên!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                int nvId = Integer.parseInt(selectedNV.split(" - ")[0]);
                String loaiHD = (String) cbLoaiHD.getSelectedItem();
                String ngayBD = txtNgayBD.getText().trim();
                String ngayKT = txtNgayKT.getText().trim();
                double mucLuong = Double.parseDouble(txtLuong.getText().trim());
                String trangThai = (String) cbTrangThai.getSelectedItem();

                if (maHD.isEmpty() || ngayBD.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Vui lòng nhập mã hợp đồng và ngày bắt đầu!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                try (Connection conn = DatabaseConnection.getConnection();
                     PreparedStatement pstmt = conn.prepareStatement("INSERT INTO hop_dong (ma_hop_dong, nhan_vien_id, loai_hop_dong, ngay_bat_dau, ngay_ket_thuc, muc_luong, trang_thai) VALUES (?, ?, ?, ?, ?, ?, ?)")) {
                    pstmt.setString(1, maHD);
                    pstmt.setInt(2, nvId);
                    pstmt.setString(3, loaiHD);
                    pstmt.setDate(4, Date.valueOf(ngayBD));
                    if (ngayKT.isEmpty()) {
                        pstmt.setNull(5, Types.DATE);
                    } else {
                        pstmt.setDate(5, Date.valueOf(ngayKT));
                    }
                    pstmt.setDouble(6, mucLuong);
                    pstmt.setString(7, trangThai);
                    pstmt.executeUpdate();

                    JOptionPane.showMessageDialog(this, "Thêm hợp đồng thành công!");
                    loadDataToTable();
                    clearForm();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Lỗi dữ liệu đầu vào (Kiểm tra định dạng ngày YYYY-MM-DD hoặc số tiền)!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnLamMoi.addActionListener(e -> {
            clearForm();
            loadDataToTable();
        });
    }

    private void loadNhanVienCombobox() {
        cbNhanVien.removeAllItems();
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT id, ma_nhan_vien, ho_ten FROM nhan_vien")) {
            while (rs.next()) {
                cbNhanVien.addItem(rs.getInt("id") + " - " + rs.getString("ho_ten") + " (" + rs.getString("ma_nhan_vien") + ")");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadDataToTable() {
        tableModel.setRowCount(0);
        String sql = "SELECT h.*, n.ho_ten FROM hop_dong h JOIN nhan_vien n ON h.nhan_vien_id = n.id";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                tableModel.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("ma_hop_dong"),
                        rs.getString("ho_ten"),
                        rs.getString("loai_hop_dong"),
                        rs.getDate("ngay_bat_dau"),
                        rs.getDate("ngay_ket_thuc"),
                        String.format("%,.0f", rs.getDouble("muc_luong")),
                        rs.getString("trang_thai")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void styleButton(JButton btn, Color color) {
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
    }

    private void clearForm() {
        txtMaHD.setText("");
        txtLuong.setText("");
        txtNgayBD.setText("");
        txtNgayKT.setText("");
        table.clearSelection();
    }
}