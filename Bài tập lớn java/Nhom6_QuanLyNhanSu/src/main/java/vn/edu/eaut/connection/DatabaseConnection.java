package vn.edu.eaut.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // Sửa lại thành quan_ly_nhan_su cho khớp với phpMyAdmin và IntelliJ
    private static final String URL = "jdbc:mysql://localhost:3306/quan_ly_nhan_su?useUnicode=true&characterEncoding=UTF-8";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Kết nối CSDL thành công!");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Kết nối CSDL thất bại: " + e.getMessage());
        }
        return conn;
    }
}