package vn.edu.eaut.lab5.ui;

import vn.edu.eaut.lab5.bus.ThongKeBUS;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class ThongKePanel extends JPanel {
    private JTextField txtTuNgay, txtDenNgay;
    private JTextArea txtKetQua;
    private final ThongKeBUS thongKeBUS = new ThongKeBUS();

    public ThongKePanel() {
        setLayout(new BorderLayout(10, 10));
        initComponents();
    }

    private void initComponents() {
        // Panel chọn ngày tháng phía trên
        JPanel pnlTop = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        pnlTop.setBorder(BorderFactory.createTitledBorder("Bộ lọc thời gian (Định dạng: yyyy-MM-dd)"));

        txtTuNgay = new JTextField(LocalDate.now().minusMonths(1).toString(), 10);
        txtDenNgay = new JTextField(LocalDate.now().toString(), 10);
        JButton btnThongKe = new JButton("Xem Thống Kê");

        pnlTop.add(new JLabel("Từ ngày:"));
        pnlTop.add(txtTuNgay);
        pnlTop.add(new JLabel("Đến ngày:"));
        pnlTop.add(txtDenNgay);
        pnlTop.add(btnThongKe);

        add(pnlTop, BorderLayout.NORTH);

        // Khu vực hiển thị kết quả thống kê ở giữa
        txtKetQua = new JTextArea();
        txtKetQua.setEditable(false);
        txtKetQua.setFont(new Font("Monospaced", Font.PLAIN, 14));
        add(new JScrollPane(txtKetQua), BorderLayout.CENTER);

        // Sự kiện nút Thống kê
        btnThongKe.addActionListener(e -> thucHienThongKe());
    }

    private void thucHienThongKe() {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate tuNgay = LocalDate.parse(txtTuNgay.getText().trim(), formatter);
            LocalDate denNgay = LocalDate.parse(txtDenNgay.getText().trim(), formatter);

            BigDecimal doanhThu = thongKeBUS.tinhDoanhThu(tuNgay, denNgay);
            Map<String, Object> hoaDonMax = thongKeBUS.getHoaDonCaoNhat();
            Map<String, Object> spBanChay = thongKeBUS.getSanPhamBanChayNhat();

            StringBuilder sb = new StringBuilder();
            sb.append("==================== BÁO CÁO THỐNG KÊ ====================\n\n");
            sb.append("1. Doanh thu từ ").append(tuNgay).append(" đến ").append(denNgay).append(":\n");
            sb.append("   -> ").append(doanhThu).append(" VNĐ\n\n");

            sb.append("2. Hóa đơn có giá trị cao nhất:\n");
            if (!hoaDonMax.isEmpty()) {
                sb.append("   - Mã HD: ").append(hoaDonMax.get("ma_hd")).append("\n");
                sb.append("   - Ngày lập: ").append(hoaDonMax.get("ngay_lap")).append("\n");
                sb.append("   - Mã KH: ").append(hoaDonMax.get("ma_kh")).append("\n");
                sb.append("   - Tổng tiền: ").append(hoaDonMax.get("tong_tien")).append(" VNĐ\n\n");
            } else {
                sb.append("   - Không có dữ liệu hóa đơn.\n\n");
            }

            sb.append("3. Sản phẩm bán chạy nhất:\n");
            if (!spBanChay.isEmpty()) {
                sb.append("   - Mã SP: ").append(spBanChay.get("ma_sp")).append("\n");
                sb.append("   - Tên sản phẩm: ").append(spBanChay.get("ten_sp")).append("\n");
                sb.append("   - Tổng số lượng bán: ").append(spBanChay.get("tong_so_luong")).append("\n");
            } else {
                sb.append("   - Không có dữ liệu bán hàng.\n");
            }

            txtKetQua.setText(sb.toString());

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi thống kê (Kiểm tra lại định dạng ngày yyyy-MM-dd): " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}