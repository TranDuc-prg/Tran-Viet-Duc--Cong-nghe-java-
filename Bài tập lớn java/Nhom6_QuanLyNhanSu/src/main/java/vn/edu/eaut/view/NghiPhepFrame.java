package vn.edu.eaut.view;

import vn.edu.eaut.connection.DatabaseConnection;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class NghiPhepFrame extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtMaNV, txtTuNgay, txtDenNgay, txtLyDo;
    private JComboBox<String> cbTrangThai;

    public NghiPhepFrame() {
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(10, 10, 10, 10));

        // Tiêu đề
        JLabel lblHeader = new JLabel("QUẢN LÝ THÔNG TIN NHÂN VIÊN NGHỈ PHÉP", JLabel.CENTER);
        lblHeader.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblHeader.setForeground(new Color(41, 128, 185));
        lblHeader.setBorder(new EmptyBorder(0, 0, 10, 0));
        add(lblHeader, BorderLayout.NORTH);

        // --- Panel bên trái: Form nhập liệu ---
        JPanel panelForm = new JPanel(new GridBagLayout());
        panelForm.setBorder(BorderFactory.createTitledBorder("Thông tin nghỉ phép"));
        panelForm.setPreferredSize(new Dimension(340, 0));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0; panelForm.add(new JLabel("Mã Nhân Viên:"), gbc);
        gbc.gridx = 1; txtMaNV = new JTextField(15); panelForm.add(txtMaNV, gbc);

        gbc.gridx = 0; gbc.gridy = 1; panelForm.add(new JLabel("Từ ngày (YYYY-MM-DD):"), gbc);
        gbc.gridx = 1; txtTuNgay = new JTextField(15); panelForm.add(txtTuNgay, gbc);

        gbc.gridx = 0; gbc.gridy = 2; panelForm.add(new JLabel("Đến ngày (YYYY-MM-DD):"), gbc);
        gbc.gridx = 1; txtDenNgay = new JTextField(15); panelForm.add(txtDenNgay, gbc);

        gbc.gridx = 0; gbc.gridy = 3; panelForm.add(new JLabel("Lý do:"), gbc);
        gbc.gridx = 1; txtLyDo = new JTextField(15); panelForm.add(txtLyDo, gbc);

        gbc.gridx = 0; gbc.gridy = 4; panelForm.add(new JLabel("Trạng thái:"), gbc);
        gbc.gridx = 1; cbTrangThai = new JComboBox<>(new String[]{"Chờ duyệt", "Đã duyệt", "Từ chối"}); panelForm.add(cbTrangThai, gbc);

        // Panel chứa các nút chức năng
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

        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        panelForm.add(panelButtons, gbc);

        add(panelForm, BorderLayout.WEST);

        // --- Panel bên phải: Bảng hiển thị danh sách ---
        tableModel = new DefaultTableModel(new String[]{"ID", "Mã NV", "Từ Ngày", "Đến Ngày", "Lý Do", "Trạng Thái"}, 0);
        table = new JTable(tableModel);
        table.setRowHeight(25);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Danh sách nghỉ phép"));
        add(scrollPane, BorderLayout.CENTER);

        // Load dữ liệu
        loadDataToTable();

        // Sự kiện click dòng trên bảng
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    txtMaNV.setText(tableModel.getValueAt(row, 1).toString());
                    txtTuNgay.setText(tableModel.getValueAt(row, 2).toString());
                    txtDenNgay.setText(tableModel.getValueAt(row, 3).toString());
                    txtLyDo.setText(tableModel.getValueAt(row, 4) != null ? tableModel.getValueAt(row, 4).toString() : "");
                    cbTrangThai.setSelectedItem(tableModel.getValueAt(row, 5).toString());
                }
            }
        });

        // Xử lý nút Thêm
        btnThem.addActionListener(e -> {
            String maNV = txtMaNV.getText().trim();
            String tuNgay = txtTuNgay.getText().trim();
            String denNgay = txtDenNgay.getText().trim();
            String lyDo = txtLyDo.getText().trim();
            String trangThai = cbTrangThai.getSelectedItem().toString();

            if (maNV.isEmpty() || tuNgay.isEmpty() || denNgay.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập Mã NV, Từ ngày và Đến ngày!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement("INSERT INTO nghi_phep (ma_nhan_vien, tu_ngay, den_ngay, ly_do, trang_thai) VALUES (?, ?, ?, ?, ?)")) {
                pstmt.setString(1, maNV);
                pstmt.setString(2, tuNgay);
                pstmt.setString(3, denNgay);
                pstmt.setString(4, lyDo);
                pstmt.setString(5, trangThai);
                pstmt.executeUpdate();

                JOptionPane.showMessageDialog(this, "Thêm đơn nghỉ phép thành công!");
                loadDataToTable();
                clearForm();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Lỗi thêm dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Xử lý nút Làm mới
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
             ResultSet rs = stmt.executeQuery("SELECT * FROM nghi_phep")) {
            while (rs.next()) {
                tableModel.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("ma_nhan_vien"),
                        rs.getDate("tu_ngay"),
                        rs.getDate("den_ngay"),
                        rs.getString("ly_do"),
                        rs.getString("trang_thai")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clearForm() {
        txtMaNV.setText("");
        txtTuNgay.setText("");
        txtDenNgay.setText("");
        txtLyDo.setText("");
        cbTrangThai.setSelectedIndex(0);
        table.clearSelection();
    }
}