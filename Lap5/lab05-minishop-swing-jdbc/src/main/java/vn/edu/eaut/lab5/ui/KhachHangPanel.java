package vn.edu.eaut.lab5.ui;

import vn.edu.eaut.lab5.bus.KhachHangBUS;
import vn.edu.eaut.lab5.model.KhachHang;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class KhachHangPanel extends JPanel {
    private JTable tblKhachHang;
    private DefaultTableModel tableModel;
    private JTextField txtMaKh, txtTenKh, txtSdt, txtEmail, txtDiaChi;
    private final KhachHangBUS khachHangBUS = new KhachHangBUS();

    public KhachHangPanel() {
        setLayout(new BorderLayout(10, 10));
        initComponents();
        loadDataToTable();
    }

    private void initComponents() {
        // Panel nhập liệu phía trên
        JPanel pnlInput = new JPanel(new GridLayout(3, 4, 10, 10));
        pnlInput.setBorder(BorderFactory.createTitledBorder("Thông tin khách hàng"));

        txtMaKh = new JTextField();
        txtMaKh.setEditable(false);
        txtTenKh = new JTextField();
        txtSdt = new JTextField();
        txtEmail = new JTextField();
        txtDiaChi = new JTextField();

        pnlInput.add(new JLabel("Mã KH:"));
        pnlInput.add(txtMaKh);
        pnlInput.add(new JLabel("Tên khách hàng:"));
        pnlInput.add(txtTenKh);
        pnlInput.add(new JLabel("Số điện thoại:"));
        pnlInput.add(txtSdt);
        pnlInput.add(new JLabel("Email:"));
        pnlInput.add(txtEmail);
        pnlInput.add(new JLabel("Địa chỉ:"));
        pnlInput.add(txtDiaChi);

        add(pnlInput, BorderLayout.NORTH);

        // Bảng hiển thị danh sách khách hàng ở giữa
        tableModel = new DefaultTableModel(new String[]{"Mã KH", "Tên KH", "Số điện thoại", "Email", "Địa chỉ"}, 0);
        tblKhachHang = new JTable(tableModel);
        add(new JScrollPane(tblKhachHang), BorderLayout.CENTER);

        // Panel chứa các nút chức năng phía dưới
        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        JButton btnThem = new JButton("Thêm mới");
        JButton btnSua = new JButton("Cập nhật");
        JButton btnXoa = new JButton("Xóa");
        JButton btnLamMoi = new JButton("Làm mới");

        pnlButtons.add(btnThem);
        pnlButtons.add(btnSua);
        pnlButtons.add(btnXoa);
        pnlButtons.add(btnLamMoi);

        add(pnlButtons, BorderLayout.SOUTH);

        // Bắt sự kiện chọn dòng trên bảng
        tblKhachHang.getSelectionModel().addListSelectionListener(e -> {
            int row = tblKhachHang.getSelectedRow();
            if (row >= 0) {
                txtMaKh.setText(tableModel.getValueAt(row, 0).toString());
                txtTenKh.setText(tableModel.getValueAt(row, 1).toString());
                txtSdt.setText(tableModel.getValueAt(row, 2).toString());
                txtEmail.setText(tableModel.getValueAt(row, 3) != null ? tableModel.getValueAt(row, 3).toString() : "");
                txtDiaChi.setText(tableModel.getValueAt(row, 4) != null ? tableModel.getValueAt(row, 4).toString() : "");
            }
        });

        // Xử lý sự kiện nút
        btnThem.addActionListener(e -> xuLyThemHoacSua(true));
        btnSua.addActionListener(e -> xuLyThemHoacSua(false));
        btnXoa.addActionListener(e -> xuLyXoa());
        btnLamMoi.addActionListener(e -> xoaForm());
    }

    private void loadDataToTable() {
        tableModel.setRowCount(0);
        try {
            List<KhachHang> list = khachHangBUS.findAll();
            for (KhachHang kh : list) {
                tableModel.addRow(new Object[]{
                        kh.getMaKh(),
                        kh.getTenKh(),
                        kh.getSdt(),
                        kh.getEmail(),
                        kh.getDiaChi()
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi tải dữ liệu: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void xuLyThemHoacSua(boolean isAdd) {
        try {
            KhachHang kh = new KhachHang();
            if (!isAdd) {
                if (txtMaKh.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng cần sửa", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                kh.setMaKh(Integer.parseInt(txtMaKh.getText()));
            }
            kh.setTenKh(txtTenKh.getText().trim());
            kh.setSdt(txtSdt.getText().trim());
            kh.setEmail(txtEmail.getText().trim());
            kh.setDiaChi(txtDiaChi.getText().trim());

            if (khachHangBUS.save(kh)) {
                JOptionPane.showMessageDialog(this, "Lưu thông tin khách hàng thành công!");
                loadDataToTable();
                xoaForm();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void xuLyXoa() {
        String maKhStr = txtMaKh.getText();
        if (maKhStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng cần xóa", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa khách hàng này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                int maKh = Integer.parseInt(maKhStr);
                if (khachHangBUS.delete(maKh)) {
                    JOptionPane.showMessageDialog(this, "Xóa khách hàng thành công!");
                    loadDataToTable();
                    xoaForm();
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void xoaForm() {
        txtMaKh.setText("");
        txtTenKh.setText("");
        txtSdt.setText("");
        txtEmail.setText("");
        txtDiaChi.setText("");
        tblKhachHang.clearSelection();
    }
}