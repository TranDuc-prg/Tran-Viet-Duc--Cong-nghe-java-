package vn.edu.eaut.view;

import vn.edu.eaut.connection.DatabaseConnection;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class PhuCapFrame extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtMaNV, txtTenKhoan, txtSoTien;
    private JComboBox<String> cbLoai;

    public PhuCapFrame() {
        setLayout(new BorderLayout(12, 12));
        setBackground(UITheme.BG);
        setBorder(new EmptyBorder(4, 4, 4, 4));

        JPanel header = new JPanel();
        header.setOpaque(false);
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        JLabel lblHeader = UITheme.sectionTitle("Phụ cấp & khấu trừ");
        JLabel sub = UITheme.sectionSubtitle("Quản lý các khoản cộng thêm và khấu trừ vào thu nhập");
        header.add(lblHeader);
        header.add(Box.createVerticalStrut(3));
        header.add(sub);
        add(header, BorderLayout.NORTH);

        // --- Form nhập liệu bên trái ---
        JPanel panelForm = new JPanel(new GridBagLayout());
        panelForm.setBackground(Color.WHITE);
        panelForm.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UITheme.BORDER),
                new EmptyBorder(12, 12, 12, 12)
        ));
        panelForm.setPreferredSize(new Dimension(340, 0));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0; panelForm.add(new JLabel("Mã Nhân Viên:"), gbc);
        gbc.gridx = 1; txtMaNV = new JTextField(15); panelForm.add(txtMaNV, gbc);

        gbc.gridx = 0; gbc.gridy = 1; panelForm.add(new JLabel("Loại khoản:"), gbc);
        gbc.gridx = 1; cbLoai = new JComboBox<>(new String[]{"Phụ cấp", "Khấu trừ"}); panelForm.add(cbLoai, gbc);

        gbc.gridx = 0; gbc.gridy = 2; panelForm.add(new JLabel("Tên khoản:"), gbc);
        gbc.gridx = 1; txtTenKhoan = new JTextField(15); panelForm.add(txtTenKhoan, gbc);

        gbc.gridx = 0; gbc.gridy = 3; panelForm.add(new JLabel("Số tiền (VNĐ):"), gbc);
        gbc.gridx = 1; txtSoTien = new JTextField(15); panelForm.add(txtSoTien, gbc);

        JPanel panelButtons = new JPanel(new GridLayout(2, 2, 8, 8));
        JButton btnThem = new JButton("Thêm");
        JButton btnSua = new JButton("Sửa");
        JButton btnXoa = new JButton("Xóa");
        JButton btnLamMoi = new JButton("Làm mới");



        panelButtons.add(btnThem);
        panelButtons.add(btnSua);
        panelButtons.add(btnXoa);
        panelButtons.add(btnLamMoi);

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        panelForm.add(panelButtons, gbc);

        add(panelForm, BorderLayout.WEST);

        // --- Bảng danh sách bên phải ---
        tableModel = new DefaultTableModel(new String[]{"ID", "Mã NV", "Loại", "Tên Khoản", "Số Tiền"}, 0);
        table = new JTable(tableModel);
        UITheme.styleTable(table);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(UITheme.BORDER));
        add(scrollPane, BorderLayout.CENTER);

        loadDataToTable();

        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    txtMaNV.setText(tableModel.getValueAt(row, 1).toString());
                    cbLoai.setSelectedItem(tableModel.getValueAt(row, 2).toString());
                    txtTenKhoan.setText(tableModel.getValueAt(row, 3).toString());
                    txtSoTien.setText(tableModel.getValueAt(row, 4).toString());
                }
            }
        });

        btnThem.addActionListener(e -> {
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement("INSERT INTO phu_cap_khau_tru (ma_nhan_vien, loai, ten_khoan, so_tien) VALUES (?, ?, ?, ?)")) {
                pstmt.setString(1, txtMaNV.getText().trim());
                pstmt.setString(2, cbLoai.getSelectedItem().toString());
                pstmt.setString(3, txtTenKhoan.getText().trim());
                pstmt.setDouble(4, Double.parseDouble(txtSoTien.getText().trim()));
                pstmt.executeUpdate();

                JOptionPane.showMessageDialog(this, "Thêm thành công!");
                loadDataToTable();
                clearForm();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Lỗi thêm dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnLamMoi.addActionListener(e -> {
            clearForm();
            loadDataToTable();
        });
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
             ResultSet rs = stmt.executeQuery("SELECT * FROM phu_cap_khau_tru")) {
            while (rs.next()) {
                tableModel.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("ma_nhan_vien"),
                        rs.getString("loai"),
                        rs.getString("ten_khoan"),
                        rs.getDouble("so_tien")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clearForm() {
        txtMaNV.setText("");
        cbLoai.setSelectedIndex(0);
        txtTenKhoan.setText("");
        txtSoTien.setText("");
        table.clearSelection();
    }
}