package vn.edu.eaut.view;

import vn.edu.eaut.connection.DatabaseConnection;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class TaiKhoanFrame extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtUsername, txtPassword;
    private JComboBox<String> cbRole;

    public TaiKhoanFrame() {
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel lblHeader = new JLabel("QUẢN LÝ TÀI KHOẢN HỆ THỐNG", JLabel.CENTER);
        lblHeader.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblHeader.setForeground(new Color(41, 128, 185));
        lblHeader.setBorder(new EmptyBorder(0, 0, 10, 0));
        add(lblHeader, BorderLayout.NORTH);

        // --- Form nhập liệu bên trái ---
        JPanel panelForm = new JPanel(new GridBagLayout());
        panelForm.setBorder(BorderFactory.createTitledBorder("Thông tin tài khoản"));
        panelForm.setPreferredSize(new Dimension(340, 0));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0; panelForm.add(new JLabel("Tên đăng nhập:"), gbc);
        gbc.gridx = 1; txtUsername = new JTextField(15); panelForm.add(txtUsername, gbc);

        gbc.gridx = 0; gbc.gridy = 1; panelForm.add(new JLabel("Mật khẩu:"), gbc);
        gbc.gridx = 1; txtPassword = new JTextField(15); panelForm.add(txtPassword, gbc);

        gbc.gridx = 0; gbc.gridy = 2; panelForm.add(new JLabel("Vai trò (Role):"), gbc);
        gbc.gridx = 1; cbRole = new JComboBox<>(new String[]{"ADMIN", "USER", "MANAGER"}); panelForm.add(cbRole, gbc);

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

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        panelForm.add(panelButtons, gbc);

        add(panelForm, BorderLayout.WEST);

        // --- Bảng danh sách bên phải ---
        tableModel = new DefaultTableModel(new String[]{"ID", "Tên Đăng Nhập", "Mật Khẩu", "Vai Trò"}, 0);
        table = new JTable(tableModel);
        table.setRowHeight(25);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Danh sách tài khoản"));
        add(scrollPane, BorderLayout.CENTER);

        loadDataToTable();

        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    txtUsername.setText(tableModel.getValueAt(row, 1).toString());
                    txtPassword.setText(tableModel.getValueAt(row, 2).toString());
                    cbRole.setSelectedItem(tableModel.getValueAt(row, 3).toString());
                }
            }
        });

        btnThem.addActionListener(e -> {
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement("INSERT INTO tai_khoan (username, password, role) VALUES (?, ?, ?)")) {
                pstmt.setString(1, txtUsername.getText().trim());
                pstmt.setString(2, txtPassword.getText().trim());
                pstmt.setString(3, cbRole.getSelectedItem().toString());
                pstmt.executeUpdate();

                JOptionPane.showMessageDialog(this, "Thêm tài khoản thành công!");
                loadDataToTable();
                clearForm();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Lỗi thêm dữ liệu (Trùng tên đăng nhập?)", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnXoa.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn tài khoản cần xóa trên bảng!");
                return;
            }
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa tài khoản này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try (Connection conn = DatabaseConnection.getConnection();
                     PreparedStatement pstmt = conn.prepareStatement("DELETE FROM tai_khoan WHERE id = ?")) {
                    pstmt.setInt(1, Integer.parseInt(tableModel.getValueAt(row, 0).toString()));
                    pstmt.executeUpdate();
                    JOptionPane.showMessageDialog(this, "Xóa thành công!");
                    loadDataToTable();
                    clearForm();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
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
             ResultSet rs = stmt.executeQuery("SELECT * FROM tai_khoan")) {
            while (rs.next()) {
                tableModel.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("role")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clearForm() {
        txtUsername.setText("");
        txtPassword.setText("");
        cbRole.setSelectedIndex(0);
        table.clearSelection();
    }
}