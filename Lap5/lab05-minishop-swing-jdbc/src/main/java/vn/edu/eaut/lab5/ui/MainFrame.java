package vn.edu.eaut.lab5.ui;

import javax.swing.*;

public class MainFrame extends JFrame {
    private JTabbedPane tabbedPane;

    public MainFrame() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Quản Lý MiniShop - Lab 5");
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        tabbedPane = new JTabbedPane();

        // Thêm các panel chức năng vào tab đúng chuẩn yêu cầu
        tabbedPane.addTab("Quản Lý Sản Phẩm", new SanPhamPanel());
        tabbedPane.addTab("Quản Lý Khách Hàng", new KhachHangPanel());
        tabbedPane.addTab("Lập Hóa Đơn", new HoaDonPanel());
        tabbedPane.addTab("Thống Kê", new ThongKePanel());

        add(tabbedPane);
    }
}