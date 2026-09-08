package vn.edu.eaut.view;

import vn.edu.eaut.connection.DatabaseConnection;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.sql.*;

public class PhongBanFrame extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtMaPB, txtTenPB, txtMoTa, txtTimKiem;
    private JComboBox<String> cbTrangThai, cbLocTrangThai;
    private JButton btnThem, btnSua, btnXoa, btnLamMoi, btnTimKiem, btnXuatExcel;
    private JLabel lblThongKe;

    public PhongBanFrame() {
        setLayout(new BorderLayout(15, 15));
        setBorder(new EmptyBorder(15, 15, 15, 15));
        setBackground(new Color(245, 247, 250));

        // Tiêu đề
        JLabel lblHeader = new JLabel("QUẢN LÝ THÔNG TIN PHÒNG BAN", JLabel.CENTER);
        lblHeader.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblHeader.setForeground(new Color(41, 128, 185));
        lblHeader.setBorder(new EmptyBorder(0, 0, 5, 0));
        add(lblHeader, BorderLayout.NORTH);

        // --- Panel bên trái: Form nhập liệu phòng ban ---
        JPanel panelForm = new JPanel(new GridBagLayout());
        panelForm.setBackground(Color.WHITE);
        panelForm.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(new Color(200, 200, 200)),
                        " Thông tin phòng ban ",
                        0, 0, new Font("Segoe UI", Font.BOLD, 13), new Color(41, 128, 185)
                ),
                new EmptyBorder(10, 10, 10, 10)
        ));
        panelForm.setPreferredSize(new Dimension(360, 0));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 8, 10, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0; panelForm.add(createLabel("Mã phòng ban:"), gbc);
        gbc.gridx = 1; txtMaPB = new JTextField(15); styleTextField(txtMaPB); panelForm.add(txtMaPB, gbc);

        gbc.gridx = 0; gbc.gridy = 1; panelForm.add(createLabel("Tên phòng ban:"), gbc);
        gbc.gridx = 1; txtTenPB = new JTextField(15); styleTextField(txtTenPB); panelForm.add(txtTenPB, gbc);

        gbc.gridx = 0; gbc.gridy = 2; panelForm.add(createLabel("Mô tả:"), gbc);
        gbc.gridx = 1; txtMoTa = new JTextField(15); styleTextField(txtMoTa); panelForm.add(txtMoTa, gbc);

        gbc.gridx = 0; gbc.gridy = 3; panelForm.add(createLabel("Trạng thái:"), gbc);
        gbc.gridx = 1; cbTrangThai = new JComboBox<>(new String[]{"Hoạt động", "Ngừng hoạt động"}); styleComboBox(cbTrangThai); panelForm.add(cbTrangThai, gbc);

        // Panel chứa các nút chức năng chính
        JPanel panelButtons = new JPanel(new GridLayout(3, 2, 10, 10));
        panelButtons.setBackground(Color.WHITE);
        btnThem = new JButton("Thêm mới");
        btnSua = new JButton("Cập nhật");
        btnXoa = new JButton("Xóa bỏ");
        btnLamMoi = new JButton("Làm mới");
        btnXuatExcel = new JButton("Xuất Excel");

        styleButton(btnThem, new Color(46, 204, 113));
        styleButton(btnSua, new Color(230, 126, 34));
        styleButton(btnXoa, new Color(231, 76, 60));
        styleButton(btnLamMoi, new Color(52, 152, 219));
        styleButton(btnXuatExcel, new Color(39, 174, 96));

        panelButtons.add(btnThem);
        panelButtons.add(btnSua);
        panelButtons.add(btnXoa);
        panelButtons.add(btnLamMoi);
        panelButtons.add(btnXuatExcel);

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 8, 8, 8);
        panelForm.add(panelButtons, gbc);

        add(panelForm, BorderLayout.WEST);

        // --- Panel bên phải: Tìm kiếm, Bộ lọc, Bảng hiển thị và Thống kê ---
        JPanel panelRight = new JPanel(new BorderLayout(10, 10));
        panelRight.setOpaque(false);

        JPanel panelSearchContainer = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panelSearchContainer.setBackground(Color.WHITE);
        panelSearchContainer.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 224, 230)),
                new EmptyBorder(8, 8, 8, 8)
        ));

        panelSearchContainer.add(new JLabel("Từ khóa:"));
        txtTimKiem = new JTextField(15);
        styleTextField(txtTimKiem);
        panelSearchContainer.add(txtTimKiem);

        panelSearchContainer.add(new JLabel("Lọc trạng thái:"));
        cbLocTrangThai = new JComboBox<>(new String[]{"Tất cả", "Hoạt động", "Ngừng hoạt động"});
        styleComboBox(cbLocTrangThai);
        panelSearchContainer.add(cbLocTrangThai);

        btnTimKiem = new JButton("Tìm kiếm");
        styleButton(btnTimKiem, new Color(41, 128, 185));
        panelSearchContainer.add(btnTimKiem);

        panelRight.add(panelSearchContainer, BorderLayout.NORTH);

        // Bảng dữ liệu phòng ban
        tableModel = new DefaultTableModel(new String[]{"ID", "Mã PB", "Tên Phòng Ban", "Mô Tả", "Trạng Thái"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        table.setRowHeight(28);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        table.getTableHeader().setBackground(new Color(52, 152, 219));
        table.getTableHeader().setForeground(Color.WHITE);
        table.setSelectionBackground(new Color(210, 230, 245));
        table.setSelectionForeground(Color.BLACK);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.getColumnModel().getColumn(0).setPreferredWidth(50);
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(1).setPreferredWidth(90);
        table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                " Danh sách phòng ban (Nhấp đúp dòng để quản lý nhân sự trực thuộc) ",
                0, 0, new Font("Segoe UI", Font.BOLD, 13), new Color(41, 128, 185)
        ));
        panelRight.add(scrollPane, BorderLayout.CENTER);

        JPanel panelBottom = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panelBottom.setBackground(Color.WHITE);
        panelBottom.setBorder(BorderFactory.createLineBorder(new Color(220, 224, 230)));
        lblThongKe = new JLabel("Tổng số: 0 phòng ban");
        lblThongKe.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblThongKe.setForeground(new Color(41, 128, 185));
        panelBottom.add(lblThongKe);
        panelRight.add(panelBottom, BorderLayout.SOUTH);

        add(panelRight, BorderLayout.CENTER);

        loadDataToTable("SELECT * FROM phong_ban");

        // Sự kiện click chọn dòng / nhấp đúp mở quản lý nhân sự
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    txtMaPB.setText(tableModel.getValueAt(row, 1).toString());
                    txtTenPB.setText(tableModel.getValueAt(row, 2).toString());
                    txtMoTa.setText(tableModel.getValueAt(row, 3) != null ? tableModel.getValueAt(row, 3).toString() : "");
                    cbTrangThai.setSelectedItem(tableModel.getValueAt(row, 4).toString());
                    txtMaPB.setEditable(false);

                    if (evt.getClickCount() == 2) {
                        int idPB = Integer.parseInt(tableModel.getValueAt(row, 0).toString());
                        String tenPB = tableModel.getValueAt(row, 2).toString();
                        showEmployeeManagementDialog(idPB, tenPB);
                    }
                }
            }
        });

        btnThem.addActionListener(e -> handleAdd());
        btnSua.addActionListener(e -> handleUpdate());
        btnXoa.addActionListener(e -> handleDelete());
        btnLamMoi.addActionListener(e -> {
            clearForm();
            txtTimKiem.setText("");
            cbLocTrangThai.setSelectedIndex(0);
            loadDataToTable("SELECT * FROM phong_ban");
        });
        btnTimKiem.addActionListener(e -> handleSearchAndFilter());
        txtTimKiem.addActionListener(e -> handleSearchAndFilter());
        cbLocTrangThai.addActionListener(e -> handleSearchAndFilter());
        btnXuatExcel.addActionListener(e -> handleExportExcel());
    }

    private JLabel createLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
        return lbl;
    }

    private void styleTextField(JTextField tf) {
        tf.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tf.setPreferredSize(new Dimension(0, 28));
    }

    private void styleComboBox(JComboBox<String> cb) {
        cb.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        cb.setBackground(Color.WHITE);
        cb.setPreferredSize(new Dimension(0, 28));
    }

    private void styleButton(JButton btn, Color bgColor) {
        btn.setBackground(bgColor);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setFocusPainted(false);
        btn.setOpaque(true);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void loadDataToTable(String sqlQuery) {
        tableModel.setRowCount(0);
        int total = 0, active = 0, inactive = 0;

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sqlQuery)) {
            while (rs.next()) {
                total++;
                String trangThai = rs.getString("trang_thai") != null ? rs.getString("trang_thai") : "Hoạt động";
                if ("Hoạt động".equalsIgnoreCase(trangThai)) active++;
                else inactive++;

                tableModel.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("ma_phong_ban") != null ? rs.getString("ma_phong_ban") : "",
                        rs.getString("ten_phong_ban"),
                        rs.getString("mo_ta") != null ? rs.getString("mo_ta") : "",
                        trangThai
                });
            }
            lblThongKe.setText("<html><b>Tổng số:</b> " + total + " phòng ban &nbsp;|&nbsp; <font color='green'><b>Hoạt động:</b> " + active + "</font> &nbsp;|&nbsp; <font color='red'><b>Ngừng hoạt động:</b> " + inactive + "</font></html>");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleSearchAndFilter() {
        String keyword = txtTimKiem.getText().trim();
        String selectedStatus = cbLocTrangThai.getSelectedItem().toString();

        StringBuilder sql = new StringBuilder("SELECT * FROM phong_ban WHERE (ma_phong_ban LIKE ? OR ten_phong_ban LIKE ?)");
        if (!"Tất cả".equals(selectedStatus)) sql.append(" AND trang_thai = ?");

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {
            pstmt.setString(1, "%" + keyword + "%");
            pstmt.setString(2, "%" + keyword + "%");
            if (!"Tất cả".equals(selectedStatus)) pstmt.setString(3, selectedStatus);

            tableModel.setRowCount(0);
            int total = 0;
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    total++;
                    tableModel.addRow(new Object[]{
                            rs.getInt("id"),
                            rs.getString("ma_phong_ban"),
                            rs.getString("ten_phong_ban"),
                            rs.getString("mo_ta"),
                            rs.getString("trang_thai")
                    });
                }
            }
            lblThongKe.setText("Kết quả tìm kiếm: Tìm thấy " + total + " phòng ban phù hợp.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showEmployeeManagementDialog(int idPB, String tenPB) {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Quản lý nhân sự - " + tenPB, true);
        dialog.setSize(950, 500);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout(10, 10));

        // Bảng danh sách nhân viên bên phải
        DefaultTableModel empModel = new DefaultTableModel(new String[]{"ID", "Mã NV", "Họ Tên", "Giới Tính", "Email", "Điện Thoại", "Lương Cơ Bản"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        JTable empTable = new JTable(empModel);
        empTable.setRowHeight(26);
        empTable.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        empTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));

        // Panel form nhập liệu bên trái (Dùng BoxLayout xếp dọc để không bị đè giao diện)
        JPanel pnlEmpForm = new JPanel();
        pnlEmpForm.setLayout(new BoxLayout(pnlEmpForm, BoxLayout.Y_AXIS));
        pnlEmpForm.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(new Color(200, 200, 200)),
                        " Thông tin nhân viên ",
                        0, 0, new Font("Segoe UI", Font.BOLD, 12), new Color(41, 128, 185)
                ),
                new EmptyBorder(10, 10, 10, 10)
        ));
        pnlEmpForm.setPreferredSize(new Dimension(300, 0));
        pnlEmpForm.setBackground(Color.WHITE);

        JTextField txtMaNV = new JTextField();
        JTextField txtHoTen = new JTextField();
        JComboBox<String> cbGioiTinh = new JComboBox<>(new String[]{"Nam", "Nữ", "Khác"});
        JTextField txtEmail = new JTextField();
        JTextField txtDienThoai = new JTextField();
        JTextField txtLuong = new JTextField();

        // Hàm hỗ trợ thêm dòng vào form dọc
        Runnable addFormRow = () -> {}; // Dùng helper trực tiếp bên dưới

        pnlEmpForm.add(createLabel("Mã NV:"));
        pnlEmpForm.add(Box.createVerticalStrut(3));
        styleTextField(txtMaNV); pnlEmpForm.add(txtMaNV);
        pnlEmpForm.add(Box.createVerticalStrut(8));

        pnlEmpForm.add(createLabel("Họ tên:"));
        pnlEmpForm.add(Box.createVerticalStrut(3));
        styleTextField(txtHoTen); pnlEmpForm.add(txtHoTen);
        pnlEmpForm.add(Box.createVerticalStrut(8));

        pnlEmpForm.add(createLabel("Giới tính:"));
        pnlEmpForm.add(Box.createVerticalStrut(3));
        styleComboBox(cbGioiTinh); pnlEmpForm.add(cbGioiTinh);
        pnlEmpForm.add(Box.createVerticalStrut(8));

        pnlEmpForm.add(createLabel("Email:"));
        pnlEmpForm.add(Box.createVerticalStrut(3));
        styleTextField(txtEmail); pnlEmpForm.add(txtEmail);
        pnlEmpForm.add(Box.createVerticalStrut(8));

        pnlEmpForm.add(createLabel("Điện thoại:"));
        pnlEmpForm.add(Box.createVerticalStrut(3));
        styleTextField(txtDienThoai); pnlEmpForm.add(txtDienThoai);
        pnlEmpForm.add(Box.createVerticalStrut(8));

        pnlEmpForm.add(createLabel("Lương cơ bản:"));
        pnlEmpForm.add(Box.createVerticalStrut(3));
        styleTextField(txtLuong); pnlEmpForm.add(txtLuong);
        pnlEmpForm.add(Box.createVerticalStrut(15));

        // Hàm load nhân viên vào bảng Dialog
        Runnable loadEmpData = () -> {
            empModel.setRowCount(0);
            String sql = "SELECT * FROM nhan_vien WHERE phong_ban_id = ?";
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, idPB);
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        empModel.addRow(new Object[]{
                                rs.getInt("id"),
                                rs.getString("ma_nv"),
                                rs.getString("ho_ten"),
                                rs.getString("gioi_tinh"),
                                rs.getString("email"),
                                rs.getString("dien_thoai"),
                                rs.getBigDecimal("luong_co_ban")
                        });
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        loadEmpData.run();

        // Click dòng nhân viên để đẩy lên form
        empTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = empTable.getSelectedRow();
                if (row >= 0) {
                    txtMaNV.setText(empModel.getValueAt(row, 1).toString());
                    txtHoTen.setText(empModel.getValueAt(row, 2).toString());
                    cbGioiTinh.setSelectedItem(empModel.getValueAt(row, 3).toString());
                    txtEmail.setText(empModel.getValueAt(row, 4) != null ? empModel.getValueAt(row, 4).toString() : "");
                    txtDienThoai.setText(empModel.getValueAt(row, 5) != null ? empModel.getValueAt(row, 5).toString() : "");
                    txtLuong.setText(empModel.getValueAt(row, 6) != null ? empModel.getValueAt(row, 6).toString() : "");
                    txtMaNV.setEditable(false);
                }
            }
        });

        // Panel chứa các nút thao tác CRUD Nhân viên (chia 2 hàng cho thoải mái nút bấm)
        JPanel pnlEmpButtons = new JPanel(new GridLayout(2, 2, 8, 8));
        pnlEmpButtons.setBackground(Color.WHITE);
        JButton btnAddEmp = new JButton("Thêm mới");
        JButton btnUpdateEmp = new JButton("Cập nhật");
        JButton btnDeleteEmp = new JButton("Xóa bỏ");
        JButton btnClearEmp = new JButton("Làm mới");

        styleButton(btnAddEmp, new Color(46, 204, 113));
        styleButton(btnUpdateEmp, new Color(230, 126, 34));
        styleButton(btnDeleteEmp, new Color(231, 76, 60));
        styleButton(btnClearEmp, new Color(52, 152, 219));

        pnlEmpButtons.add(btnAddEmp);
        pnlEmpButtons.add(btnUpdateEmp);
        pnlEmpButtons.add(btnDeleteEmp);
        pnlEmpButtons.add(btnClearEmp);

        pnlEmpForm.add(pnlEmpButtons);

        // Sự kiện Thêm nhân viên
        btnAddEmp.addActionListener(e -> {
            String maNV = txtMaNV.getText().trim();
            String hoTen = txtHoTen.getText().trim();
            if (maNV.isEmpty() || hoTen.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Vui lòng nhập Mã NV và Họ tên!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement("INSERT INTO nhan_vien (ma_nv, ho_ten, gioi_tinh, email, dien_thoai, luong_co_ban, phong_ban_id) VALUES (?, ?, ?, ?, ?, ?, ?)")) {
                pstmt.setString(1, maNV);
                pstmt.setString(2, hoTen);
                pstmt.setString(3, cbGioiTinh.getSelectedItem().toString());
                pstmt.setString(4, txtEmail.getText().trim());
                pstmt.setString(5, txtDienThoai.getText().trim());
                pstmt.setBigDecimal(6, txtLuong.getText().trim().isEmpty() ? BigDecimal.ZERO : new BigDecimal(txtLuong.getText().trim()));
                pstmt.setInt(7, idPB);
                pstmt.executeUpdate();
                JOptionPane.showMessageDialog(dialog, "Thêm nhân viên thành công!");
                loadEmpData.run();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(dialog, "Lỗi thêm nhân viên (Trùng mã NV)!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Sự kiện Sửa nhân viên
        btnUpdateEmp.addActionListener(e -> {
            int row = empTable.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(dialog, "Chọn nhân viên cần sửa trên bảng!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int idNV = Integer.parseInt(empModel.getValueAt(row, 0).toString());
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement("UPDATE nhan_vien SET ho_ten = ?, gioi_tinh = ?, email = ?, dien_thoai = ?, luong_co_ban = ? WHERE id = ?")) {
                pstmt.setString(1, txtHoTen.getText().trim());
                pstmt.setString(2, cbGioiTinh.getSelectedItem().toString());
                pstmt.setString(3, txtEmail.getText().trim());
                pstmt.setString(4, txtDienThoai.getText().trim());
                pstmt.setBigDecimal(5, txtLuong.getText().trim().isEmpty() ? BigDecimal.ZERO : new BigDecimal(txtLuong.getText().trim()));
                pstmt.setInt(6, idNV);
                pstmt.executeUpdate();
                JOptionPane.showMessageDialog(dialog, "Cập nhật nhân viên thành công!");
                loadEmpData.run();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(dialog, "Lỗi cập nhật nhân viên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Sự kiện Xóa nhân viên
        btnDeleteEmp.addActionListener(e -> {
            int row = empTable.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(dialog, "Chọn nhân viên cần xóa trên bảng!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int idNV = Integer.parseInt(empModel.getValueAt(row, 0).toString());
            int confirm = JOptionPane.showConfirmDialog(dialog, "Bạn có chắc muốn xóa nhân viên này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try (Connection conn = DatabaseConnection.getConnection();
                     PreparedStatement pstmt = conn.prepareStatement("DELETE FROM nhan_vien WHERE id = ?")) {
                    pstmt.setInt(1, idNV);
                    pstmt.executeUpdate();
                    JOptionPane.showMessageDialog(dialog, "Xóa nhân viên thành công!");
                    loadEmpData.run();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        btnClearEmp.addActionListener(e -> {
            txtMaNV.setText("");
            txtHoTen.setText("");
            txtEmail.setText("");
            txtDienThoai.setText("");
            txtLuong.setText("");
            txtMaNV.setEditable(true);
            empTable.clearSelection();
        });

        dialog.add(pnlEmpForm, BorderLayout.WEST);
        dialog.add(new JScrollPane(empTable), BorderLayout.CENTER);

        JButton btnClose = new JButton("Đóng");
        styleButton(btnClose, new Color(108, 117, 125));
        JPanel pnlBottomDialog = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pnlBottomDialog.add(btnClose);
        btnClose.addActionListener(e -> dialog.dispose());
        dialog.add(pnlBottomDialog, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }
    private void handleExportExcel() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu file Excel danh sách phòng ban");
        fileChooser.setSelectedFile(new File("DanhSachPhongBan.csv"));

        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            try (FileWriter fw = new FileWriter(fileToSave + (fileToSave.getName().endsWith(".csv") ? "" : ".csv"))) {
                fw.write("ID,Ma PB,Ten Phong Ban,Mo Ta,Trang Thai\n");
                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    fw.write('"' + tableModel.getValueAt(i, 0).toString() + "\"," +
                            '"' + tableModel.getValueAt(i, 1).toString() + "\"," +
                            '"' + tableModel.getValueAt(i, 2).toString() + "\"," +
                            '"' + tableModel.getValueAt(i, 3).toString() + "\"," +
                            '"' + tableModel.getValueAt(i, 4).toString() + "\"\n");
                }
                JOptionPane.showMessageDialog(this, "Xuất file thành công tại: " + fileToSave.getAbsolutePath());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void handleAdd() {
        String ma = txtMaPB.getText().trim();
        String ten = txtTenPB.getText().trim();
        if (ma.isEmpty() || ten.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ Mã và Tên phòng ban!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("INSERT INTO phong_ban (ma_phong_ban, ten_phong_ban, mo_ta, trang_thai) VALUES (?, ?, ?, ?)")) {
            pstmt.setString(1, ma);
            pstmt.setString(2, ten);
            pstmt.setString(3, txtMoTa.getText().trim());
            pstmt.setString(4, cbTrangThai.getSelectedItem().toString());
            pstmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "Thêm phòng ban thành công!");
            loadDataToTable("SELECT * FROM phong_ban");
            clearForm();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi thêm dữ liệu (Có thể trùng mã phòng ban)!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleUpdate() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn phòng ban cần sửa trên bảng!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int id = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
        String ten = txtTenPB.getText().trim();
        if (ten.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tên phòng ban không được để trống!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("UPDATE phong_ban SET ten_phong_ban = ?, mo_ta = ?, trang_thai = ? WHERE id = ?")) {
            pstmt.setString(1, ten);
            pstmt.setString(2, txtMoTa.getText().trim());
            pstmt.setString(3, cbTrangThai.getSelectedItem().toString());
            pstmt.setInt(4, id);
            pstmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "Cập nhật phòng ban thành công!");
            loadDataToTable("SELECT * FROM phong_ban");
            clearForm();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void handleDelete() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn phòng ban cần xóa trên bảng!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int id = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
        String tenPB = tableModel.getValueAt(selectedRow, 2).toString();

        try (Connection conn = DatabaseConnection.getConnection()) {
            String countSql = "SELECT COUNT(*) FROM nhan_vien WHERE phong_ban_id = ?";
            try (PreparedStatement countStmt = conn.prepareStatement(countSql)) {
                countStmt.setInt(1, id);
                try (ResultSet rs = countStmt.executeQuery()) {
                    if (rs.next() && rs.getInt(1) > 0) {
                        JOptionPane.showMessageDialog(this, "Không thể xóa phòng ban này vì vẫn còn " + rs.getInt(1) + " nhân viên trực thuộc!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                }
            }

            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa phòng ban: " + tenPB + "?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try (PreparedStatement delStmt = conn.prepareStatement("DELETE FROM phong_ban WHERE id = ?")) {
                    delStmt.setInt(1, id);
                    delStmt.executeUpdate();
                    JOptionPane.showMessageDialog(this, "Xóa phòng ban thành công!");
                    loadDataToTable("SELECT * FROM phong_ban");
                    clearForm();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void clearForm() {
        txtMaPB.setText("");
        txtTenPB.setText("");
        txtMoTa.setText("");
        cbTrangThai.setSelectedIndex(0);
        txtMaPB.setEditable(true);
        table.clearSelection();
    }
}