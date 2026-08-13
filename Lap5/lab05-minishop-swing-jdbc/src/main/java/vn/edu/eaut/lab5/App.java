package vn.edu.eaut.lab5;

import vn.edu.eaut.lab5.config.DBHelper;
import vn.edu.eaut.lab5.ui.MainFrame;
import javax.swing.SwingUtilities;

public class App {
    public static void main(String[] args) {
        // 1. Kiểm tra kết nối CSDL trước khi khởi chạy
        DBHelper.testConnection();

        // 2. Khởi tạo và hiển thị giao diện chính trên luồng Swing
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setTitle("Quản Lý MiniShop - Lab 5");
            frame.setSize(900, 600);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(MainFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}