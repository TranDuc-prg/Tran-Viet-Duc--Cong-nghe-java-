package vn.edu.eaut.view;

import vn.edu.eaut.connection.DatabaseConnection;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class ChamCongFrame extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtMaNV, txtNgayCC, txtTrangThai;
    private JComboBox<String> cbCa;

    public ChamCongFrame(String role) {
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(10, 10, 10, 10));

        // Tiêu đề
        JLabel lblHeader = new JLabel("QUẢN LÝ CHẤM CÔNG HỆ THỐNG", JLabel.CENTER);
        lblHeader.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblHeader.setForeground(new Color(41, 128, 185));
        lblHeader.setBorder(new EmptyBorder(0, 0, 10, 0));
        add(lblHeader, BorderLayout.NORTH);

        // --- 1. Form nhập liệu / Thao tác bên trái ---
        JPanel panelForm = new JPanel(new GridBagLayout());
        panelForm.setBorder(BorderFactory.createTitledBorder("Thông tin chấm công"));
        panelForm.setPreferredSize(new Dimension(340, 0));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0; panelForm.add(new JLabel("Mã Nhân Viên:"), gbc);
        gbc.gridx = 1; txtMaNV = new JTextField(15); panelForm.add(txtMaNV, gbc);

        gbc.gridx = 0; gbc.gridy = 1; panelForm.add(new JLabel("Ngày (YYYY-MM-DD):"), gbc);
        gbc.gridx = 1; txtNgayCC = new JTextField(15); panelForm.add(txtNgayCC, gbc);

        gbc.gridx = 0; gbc.gridy = 2; panelForm.add(new JLabel("Ca làm việc:"), gbc);
        gbc.gridx = 1; cbCa = new JComboBox<>(new String[]{"Ca Sáng", "Ca Chiều", "Ca Hành Chính"}); panelForm.add(cbCa, gbc);

        gbc.gridx = 0; gbc.gridy = 3; panelForm.add(new JLabel("Trạng thái:"), gbc);
        gbc.gridx = 1; txtTrangThai = new JTextField("Có mặt", 15); panelForm.add(txtTrangThai, gbc);

        // Các nút chức năng
        JPanel panelButtons = new JPanel(new GridLayout(2, 2, 8, 8));
        JButton btnThem = new JButton("Thêm mới");
        JButton btnSua = new JButton("Cập nhật");
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

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        panelForm.add(panelButtons, gbc);

        add(panelForm, BorderLayout.WEST);

        // --- 2. Bảng hiển thị danh sách chấm công bên phải ---
        tableModel = new DefaultTableModel(new String[]{"ID", "Mã NV", "Ngày Chấm Công", "Ca Làm", "Trạng Thái"}, 0);
        table = new JTable(tableModel);
        table.setRowHeight(25);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Bảng dữ liệu chấm công nhân viên"));
        add(scrollPane, BorderLayout.CENTER);

        // Load dữ liệu lên bảng
        loadDataToTable();

        // Sự kiện click chọn dòng trên bảng
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    txtMaNV.setText(tableModel.getValueAt(row, 1).toString());
                    txtNgayCC.setText(tableModel.getValueAt(row, 2).toString());
                    cbCa.setSelectedItem(tableModel.getValueAt(row, 3).toString());
                    txtTrangThai.setText(tableModel.getValueAt(row, 4).toString());
                }
            }
        });

        // Xử lý sự kiện Làm mới
        btnLamMoi.addActionListener(e -> {
            clearForm();
            loadDataToTable();
        });

        // Nút thêm/sửa/xóa có thể kết nối trực tiếp với DAO hoặc câu lệnh SQL tương ứng của bạn
        btnThem.addActionListener(e -> JOptionPane.showMessageDialog(this, "Chức năng thêm bản ghi chấm công."));
        btnSua.addActionListener(e -> JOptionPane.showMessageDialog(this, "Chức năng cập nhật chấm công."));
        btnXoa.addActionListener(e -> JOptionPane.showMessageDialog(this, "Chức năng xóa chấm công."));
    }

    private void styleButton(JButton btn, Color color) {
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
    }

    private void loadDataToTable() {
        tableModel.setRowCount(0);
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM cham_cong")) {
            while (rs.next()) {
                tableModel.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getInt("nhan_vien_id"),
                        rs.getString("ngay_cham_cong"),
                        rs.getString("ca_lam"),
                        rs.getString("trang_thai")
                });
            }
        } catch (Exception e) {
            // Trường hợp bảng trong DB chưa khớp tên cột, hiển thị danh sách trống để không lỗi giao diện
            e.printStackTrace();
        }
    }

    private void clearForm() {
        txtMaNV.setText("");
        txtNgayCC.setText("");
        cbCa.setSelectedIndex(0);
        txtTrangThai.setText("Có mặt");
        table.clearSelection();
    }
}