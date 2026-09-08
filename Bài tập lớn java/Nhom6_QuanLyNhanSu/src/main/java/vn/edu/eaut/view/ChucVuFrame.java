package vn.edu.eaut.view;

import vn.edu.eaut.connection.DatabaseConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ChucVuFrame extends JPanel { // Đổi từ JFrame sang JPanel
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtMaChucVu, txtTenChucVu, txtPhuCap;
    private JButton btnThem, btnSua, btnXoa, btnLamMoi;

    public ChucVuFrame() { // Constructor cùng tên với class
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Tiêu đề
        JLabel lblTitle = new JLabel("QUẢN LÝ CHỨC VỤ", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitle.setForeground(new Color(41, 128, 185));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        add(lblTitle, BorderLayout.NORTH);

        // Bảng hiển thị danh sách chức vụ
        tableModel = new DefaultTableModel(new String[]{"ID", "Mã Chức Vụ", "Tên Chức Vụ", "Mức Phụ Cấp", "Mô Tả"}, 0);
        table = new JTable(tableModel);
        table.setRowHeight(25);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Panel nhập liệu và nút bấm ở phía dưới
        JPanel panelBottom = new JPanel(new BorderLayout());

        JPanel panelInput = new JPanel(new GridLayout(2, 4, 5, 5));
        panelInput.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        panelInput.add(new JLabel("Mã Chức Vụ:"));
        txtMaChucVu = new JTextField();
        panelInput.add(txtMaChucVu);

        panelInput.add(new JLabel("Tên Chức Vụ:"));
        txtTenChucVu = new JTextField();
        panelInput.add(txtTenChucVu);

        panelInput.add(new JLabel("Phụ Cấp:"));
        txtPhuCap = new JTextField();
        panelInput.add(txtPhuCap);

        panelBottom.add(panelInput, BorderLayout.CENTER);

        // Panel chứa các nút chức năng
        JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        btnThem = new JButton("Thêm");
        btnSua = new JButton("Sửa");
        btnXoa = new JButton("Xóa");
        btnLamMoi = new JButton("Làm Mới");

        panelButtons.add(btnThem);
        panelButtons.add(btnSua);
        panelButtons.add(btnXoa);
        panelButtons.add(btnLamMoi);

        panelBottom.add(panelButtons, BorderLayout.SOUTH);
        add(panelBottom, BorderLayout.SOUTH);

        // Load dữ liệu ban đầu từ CSDL
        loadData();

        // Xử lý sự kiện click vào bảng để đưa dữ liệu lên ô nhập
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    txtMaChucVu.setText(tableModel.getValueAt(row, 1).toString());
                    txtTenChucVu.setText(tableModel.getValueAt(row, 2).toString());
                    txtPhuCap.setText(tableModel.getValueAt(row, 3).toString());
                }
            }
        });

        // Nút làm mới
        btnLamMoi.addActionListener(e -> {
            txtMaChucVu.setText("");
            txtTenChucVu.setText("");
            txtPhuCap.setText("");
            table.clearSelection();
            loadData();
        });
    }

    public void loadData() {
        tableModel.setRowCount(0);
        String sql = "SELECT * FROM chuc_vu";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Object[] row = {
                        rs.getInt("id"),
                        rs.getString("ma_chuc_vu"),
                        rs.getString("ten_chuc_vu"),
                        rs.getDouble("phu_cap"),
                        rs.getString("mo_ta")
                };
                tableModel.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}