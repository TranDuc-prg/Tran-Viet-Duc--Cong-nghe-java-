package vn.edu.eaut.lab5.ui;

import vn.edu.eaut.lab5.bus.HoaDonBUS;
import vn.edu.eaut.lab5.bus.KhachHangBUS;
import vn.edu.eaut.lab5.bus.SanPhamBUS;
import vn.edu.eaut.lab5.model.ChiTietHoaDon;
import vn.edu.eaut.lab5.model.KhachHang;
import vn.edu.eaut.lab5.model.SanPham;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HoaDonPanel extends JPanel {
    private JComboBox<KhachHang> cbKhachHang;
    private JComboBox<SanPham> cbSanPham;
    private JTextField txtSoLuong;
    private JTable tblGioHang;
    private DefaultTableModel tableModel;
    private JLabel lblTongTien;

    private final HoaDonBUS hoaDonBUS = new HoaDonBUS();
    private final KhachHangBUS khachHangBUS = new KhachHangBUS();
    private final SanPhamBUS sanPhamBUS = new SanPhamBUS();

    private final List<ChiTietHoaDon> gioHang = new ArrayList<>();

    public HoaDonPanel() {
        setLayout(new BorderLayout(10, 10));
        initComponents();
        loadDataCombobox();
    }

    private void initComponents() {
        // Panel phía trên: Chọn khách hàng, sản phẩm và số lượng
        JPanel pnlTop = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));

        cbKhachHang = new JComboBox<>();
        cbSanPham = new JComboBox<>();
        txtSoLuong = new JTextField("1", 5);
        JButton btnThem = new JButton("Thêm vào giỏ");

        pnlTop.add(new JLabel("Khách hàng:"));
        pnlTop.add(cbKhachHang);
        pnlTop.add(new JLabel("Sản phẩm:"));
        pnlTop.add(cbSanPham);
        pnlTop.add(new JLabel("Số lượng:"));
        pnlTop.add(txtSoLuong);
        pnlTop.add(btnThem);

        add(pnlTop, BorderLayout.NORTH);

        // Bảng giỏ hàng ở giữa
        tableModel = new DefaultTableModel(new String[]{"Mã SP", "Tên sản phẩm", "Đơn giá", "Số lượng", "Thành tiền"}, 0);
        tblGioHang = new JTable(tableModel);
        add(new JScrollPane(tblGioHang), BorderLayout.CENTER);

        // Panel phía dưới: Tổng tiền và nút thanh toán
        JPanel pnlBottom = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        lblTongTien = new JLabel("Tổng tiền: 0 VNĐ");
        lblTongTien.setFont(new Font("Arial", Font.BOLD, 14));
        JButton btnThanhToan = new JButton("Lưu Hóa Đơn");
        btnThanhToan.setBackground(new Color(40, 167, 69));
        btnThanhToan.setForeground(Color.WHITE);

        pnlBottom.add(lblTongTien);
        pnlBottom.add(btnThanhToan);
        add(pnlBottom, BorderLayout.SOUTH);

        // Xử lý sự kiện nút Thêm vào giỏ
        btnThem.addActionListener(e -> themVaoGioHang());

        // Xử lý sự kiện nút Lưu Hóa Đơn
        btnThanhToan.addActionListener(e -> thanhToan());
    }

    private void loadDataCombobox() {
        try {
            List<KhachHang> dsKh = khachHangBUS.findAll();
            cbKhachHang.removeAllItems();
            for (KhachHang kh : dsKh) {
                cbKhachHang.addItem(kh);
            }

            List<SanPham> dsSp = sanPhamBUS.findAll();
            cbSanPham.removeAllItems();
            for (SanPham sp : dsSp) {
                cbSanPham.addItem(sp);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi tải dữ liệu: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void themVaoGioHang() {
        SanPham sp = (SanPham) cbSanPham.getSelectedItem();
        if (sp == null) return;

        int soLuong;
        try {
            soLuong = Integer.parseInt(txtSoLuong.getText().trim());
            if (soLuong <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Số lượng phải là số nguyên lớn hơn 0", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (soLuong > sp.getSoLuong()) {
            JOptionPane.showMessageDialog(this, "Số lượng trong kho không đủ!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Kiểm tra sản phẩm đã có trong giỏ hàng chưa
        boolean found = false;
        for (ChiTietHoaDon ct : gioHang) {
            if (ct.getMaSp() == sp.getMaSp()) {
                ct.setSoLuong(ct.getSoLuong() + soLuong);
                found = true;
                break;
            }
        }

        if (!found) {
            ChiTietHoaDon ct = new ChiTietHoaDon();
            ct.setMaSp(sp.getMaSp());
            ct.setSoLuong(soLuong);
            // Chuyển đổi double từ SanPham sang BigDecimal an toàn
            ct.setDonGia(BigDecimal.valueOf(sp.getDonGia()));
            gioHang.add(ct);
        }

        capNhatBangGioHang();
    }

    private void capNhatBangGioHang() {
        tableModel.setRowCount(0);
        BigDecimal tongTien = BigDecimal.ZERO;

        try {
            for (ChiTietHoaDon ct : gioHang) {
                SanPham sp = laySanPhamTheoId(ct.getMaSp());
                String tenSp = (sp != null) ? sp.getTenSp() : "Unknown";
                BigDecimal thanhTien = ct.getDonGia().multiply(BigDecimal.valueOf(ct.getSoLuong()));
                tongTien = tongTien.add(thanhTien);

                tableModel.addRow(new Object[]{
                        ct.getMaSp(), tenSp, ct.getDonGia(), ct.getSoLuong(), thanhTien
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        lblTongTien.setText("Tổng tiền: " + tongTien + " VNĐ");
    }

    private SanPham laySanPhamTheoId(int maSp) throws SQLException {
        List<SanPham> dsSp = sanPhamBUS.findAll();
        for (SanPham sp : dsSp) {
            if (sp.getMaSp() == maSp) return sp;
        }
        return null;
    }

    private void thanhToan() {
        KhachHang kh = (KhachHang) cbKhachHang.getSelectedItem();
        if (kh == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (gioHang.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Giỏ hàng đang trống", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int maHd = hoaDonBUS.insertHoaDon(kh.getMaKh(), gioHang);
            if (maHd > 0) {
                JOptionPane.showMessageDialog(this, "Lập hóa đơn thành công! Mã HD: " + maHd);
                gioHang.clear();
                capNhatBangGioHang();
                loadDataCombobox(); // Cập nhật lại tồn kho sản phẩm
            } else {
                JOptionPane.showMessageDialog(this, "Lập hóa đơn thất bại", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi giao dịch: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}