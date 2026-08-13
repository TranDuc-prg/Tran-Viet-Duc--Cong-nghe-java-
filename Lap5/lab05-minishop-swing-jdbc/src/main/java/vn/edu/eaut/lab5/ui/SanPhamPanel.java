package vn.edu.eaut.lab5.ui;

import vn.edu.eaut.lab5.bus.SanPhamBUS;
import vn.edu.eaut.lab5.model.SanPham;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class SanPhamPanel extends JPanel {
    private JTable tblSanPham;
    private DefaultTableModel tableModel;
    private JTextField txtMaSp, txtTenSp, txtDonGia, txtSoLuong, txtTimKiem;
    private final SanPhamBUS sanPhamBUS = new SanPhamBUS();

    public SanPhamPanel() {
        setLayout(new BorderLayout(10, 10));
        initComponents();
        loadDataToTable("");
    }

    private void initComponents() {
        // Panel nhập liệu và tìm kiếm phía trên
        JPanel pnlNorth = new JPanel(new BorderLayout(5, 5));

        JPanel pnlInput = new JPanel(new GridLayout(2, 4, 10, 10));
        pnlInput.setBorder(BorderFactory.createTitledBorder("Thông tin sản phẩm"));

        txtMaSp = new JTextField();
        txtMaSp.setEditable(false);
        txtTenSp = new JTextField();
        txtDonGia = new JTextField();
        txtSoLuong = new JTextField();

        pnlInput.add(new JLabel("Mã SP:"));
        pnlInput.add(txtMaSp);
        pnlInput.add(new JLabel("Tên sản phẩm:"));
        pnlInput.add(txtTenSp);
        pnlInput.add(new JLabel("Đơn giá:"));
        pnlInput.add(txtDonGia);
        pnlInput.add(new JLabel("Số lượng:"));
        pnlInput.add(txtSoLuong);

        JPanel pnlSearch = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        txtTimKiem = new JTextField(20);
        JButton btnTimKiem = new JButton("Tìm kiếm");
        pnlSearch.add(new JLabel("Nhập tên cần tìm:"));
        pnlSearch.add(txtTimKiem);
        pnlSearch.add(btnTimKiem);

        pnlNorth.add(pnlInput, BorderLayout.CENTER);
        pnlNorth.add(pnlSearch, BorderLayout.SOUTH);
        add(pnlNorth, BorderLayout.NORTH);

        // Bảng hiển thị sản phẩm ở giữa
        tableModel = new DefaultTableModel(new String[]{"Mã SP", "Tên sản phẩm", "Đơn giá", "Số lượng"}, 0);
        tblSanPham = new JTable(tableModel);
        add(new JScrollPane(tblSanPham), BorderLayout.CENTER);

        // Panel nút chức năng phía dưới
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

        // Bắt sự kiện chọn bảng
        tblSanPham.getSelectionModel().addListSelectionListener(e -> {
            int row = tblSanPham.getSelectedRow();
            if (row >= 0) {
                txtMaSp.setText(tableModel.getValueAt(row, 0).toString());
                txtTenSp.setText(tableModel.getValueAt(row, 1).toString());
                txtDonGia.setText(tableModel.getValueAt(row, 2).toString());
                txtSoLuong.setText(tableModel.getValueAt(row, 3).toString());
            }
        });

        // Bắt sự kiện nút bấm
        btnThem.addActionListener(e -> xuLyThemHoacSua(true));
        btnSua.addActionListener(e -> xuLyThemHoacSua(false));
        btnXoa.addActionListener(e -> xuLyXoa());
        btnLamMoi.addActionListener(e -> xoaForm());
        btnTimKiem.addActionListener(e -> loadDataToTable(txtTimKiem.getText().trim()));
    }

    private void loadDataToTable(String keyword) {
        tableModel.setRowCount(0);
        try {
            List<SanPham> list = keyword.isEmpty() ? sanPhamBUS.findAll() : sanPhamBUS.searchByName(keyword);
            for (SanPham sp : list) {
                tableModel.addRow(new Object[]{
                        sp.getMaSp(), sp.getTenSp(), sp.getDonGia(), sp.getSoLuong()
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi tải dữ liệu: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void xuLyThemHoacSua(boolean isAdd) {
        try {
            SanPham sp = new SanPham();
            if (!isAdd) {
                if (txtMaSp.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm cần sửa", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                sp.setMaSp(Integer.parseInt(txtMaSp.getText()));
            }
            sp.setTenSp(txtTenSp.getText().trim());
            // Chuyển đổi đơn giá từ text sang double chính xác
            sp.setDonGia(Double.parseDouble(txtDonGia.getText().trim()));
            sp.setSoLuong(Integer.parseInt(txtSoLuong.getText().trim()));

            if (sanPhamBUS.save(sp)) {
                JOptionPane.showMessageDialog(this, "Lưu sản phẩm thành công!");
                loadDataToTable("");
                xoaForm();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Đơn giá và số lượng phải là định dạng số hợp lệ!", "Lỗi nhập liệu", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void xuLyXoa() {
        String maSpStr = txtMaSp.getText();
        if (maSpStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm cần xóa", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa sản phẩm này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                if (sanPhamBUS.delete(Integer.parseInt(maSpStr))) {
                    JOptionPane.showMessageDialog(this, "Xóa sản phẩm thành công!");
                    loadDataToTable("");
                    xoaForm();
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void xoaForm() {
        txtMaSp.setText("");
        txtTenSp.setText("");
        txtDonGia.setText("");
        txtSoLuong.setText("");
        tblSanPham.clearSelection();
    }
}