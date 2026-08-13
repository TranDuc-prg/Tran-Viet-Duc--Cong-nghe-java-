package vn.edu.eaut.lab5;

import vn.edu.eaut.lab5.ui.MainFrame;

public class Main {
    public static void main(String[] args) {
        // Khởi chạy giao diện chính trên luồng sự kiện của Swing
        java.awt.EventQueue.invokeLater(() -> {
            new MainFrame().setVisible(true);
        });
    }
}