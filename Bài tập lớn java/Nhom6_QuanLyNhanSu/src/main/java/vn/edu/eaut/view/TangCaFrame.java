package vn.edu.eaut.view;

import vn.edu.eaut.connection.DatabaseConnection;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class TangCaFrame extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtMaNV, txtNgayTangCa, txtSoGio, txtHeSo;

    public TangCaFrame() {
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel lblHeader = new JLabel("QUẢN LÝ THÔNG TIN TĂNG CA NHÂN VIÊN", JLabel.CENTER);
        lblHeader.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblHeader.setForeground(new Color(41, 128, 185));
        lblHeader.setBorder(new EmptyBorder(0, 0, 10, 0));
        add(lblHeader, BorderLayout.NORTH);

        // --- Form nhập liệu bên trái ---
        JPanel panelForm = new JPanel(new GridBagLayout());
        panelForm.setBorder(BorderFactory.createTitledBorder("Thông tin tăng ca"));
        panelForm.setPreferredSize(new Dimension(340, 0));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0; panelForm.add(new JLabel("Mã Nhân Viên:"), gbc);
        gbc.gridx = 1; txtMaNV = new JTextField(15); panelForm.add(txtMaNV, gbc);

        gbc.gridx = 0; gbc.gridy = 1; panelForm.add(new JLabel("Ngày (YYYY-MM-DD):"), gbc);
        gbc.gridx = 1; txtNgayTangCa = new JTextField(15); panelForm.add(txtNgayTangCa, gbc);

        gbc.gridx = 0; gbc.gridy = 2; panelForm.add(new JLabel("Số giờ tăng ca:"), gbc);
        gbc.gridx = 1; txtSoGio = new JTextField(15); panelForm.add(txtSoGio, gbc);

        gbc.gridx = 0; gbc.gridy = 3; panelForm.add(new JLabel("Hệ số (VD: 1.5):"), gbc);
        gbc.gridx = 1; txtHeSo = new JTextField("1.5", 15); panelForm.add(txtHeSo, gbc);

        // Nút chức năng
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

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        panelForm.add(panelButtons, gbc);

        add(panelForm, BorderLayout.WEST);

        // --- Bảng danh sách bên phải ---
        tableModel = new DefaultTableModel(new String[]{"ID", "Mã NV", "Ngày Tăng Ca", "Số Giờ", "Hệ Số"}, 0);
        table = new JTable(tableModel);
        table.setRowHeight(25);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Danh sách tăng ca"));
        add(scrollPane, BorderLayout.CENTER);

        loadDataToTable();

        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    txtMaNV.setText(tableModel.getValueAt(row, 1).toString());
                    txtNgayTangCa.setText(tableModel.getValueAt(row, 2).toString());
                    txtSoGio.setText(tableModel.getValueAt(row, 3).toString());
                    txtHeSo.setText(tableModel.getValueAt(row, 4).toString());
                }
            }
        });

        btnThem.addActionListener(e -> {
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement("INSERT INTO tang_ca (ma_nhan_vien, ngay_tang_ca, so_gio, he_so) VALUES (?, ?, ?, ?)")) {
                pstmt.setString(1, txtMaNV.getText().trim());
                pstmt.setString(2, txtNgayTangCa.getText().trim());
                pstmt.setDouble(3, Double.parseDouble(txtSoGio.getText().trim()));
                pstmt.setDouble(4, Double.parseDouble(txtHeSo.getText().trim()));
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
             ResultSet rs = stmt.executeQuery("SELECT * FROM tang_ca")) {
            while (rs.next()) {
                tableModel.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("ma_nhan_vien"),
                        rs.getDate("ngay_tang_ca"),
                        rs.getDouble("so_gio"),
                        rs.getDouble("he_so")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clearForm() {
        txtMaNV.setText("");
        txtNgayTangCa.setText("");
        txtSoGio.setText("");
        txtHeSo.setText("1.5");
        table.clearSelection();
    }
}