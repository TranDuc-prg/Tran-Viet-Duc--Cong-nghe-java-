package vn.edu.eaut.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class AdminMainFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel panelCenter;
    private JButton activeButton;
    private JButton firstButton;

    public AdminMainFrame() {
        UITheme.install();

        setTitle("Quản Lý Nhân Sự | Quản trị hệ thống");
        setSize(1280, 800);
        setMinimumSize(new Dimension(1150, 720));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        add(createHeader(), BorderLayout.NORTH);
        add(createSidebar(), BorderLayout.WEST);

        cardLayout = new CardLayout();
        panelCenter = new JPanel(cardLayout);
        panelCenter.setBackground(UITheme.BG);
        panelCenter.setBorder(new EmptyBorder(14, 14, 14, 14));

        panelCenter.add(new NhanVienFrame("ADMIN"), "NHAN_VIEN");
        panelCenter.add(new PhongBanFrame(), "PHONG_BAN");
        panelCenter.add(new ChucVuFrame(), "CHUC_VU");
        panelCenter.add(new HopDongFrame(), "HOP_DONG");
        panelCenter.add(new ChamCongFrame("ADMIN"), "CHAM_CONG");
        panelCenter.add(new NghiPhepFrame(), "NGHI_PHEP");
        panelCenter.add(new TangCaFrame(), "TANG_CA");
        panelCenter.add(new PhuCapFrame(), "PHU_CAP");
        panelCenter.add(new BangLuongFrame(), "BANG_LUONG");
        panelCenter.add(new TaiKhoanFrame(), "TAI_KHOAN");

        add(panelCenter, BorderLayout.CENTER);
        add(createFooter(), BorderLayout.SOUTH);

        showPage(firstButton, "NHAN_VIEN");
    }

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setPreferredSize(new Dimension(0, 72));
        header.setBackground(UITheme.GREEN);
        header.setBorder(new EmptyBorder(0, 24, 0, 24));

        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 14));
        left.setOpaque(false);

        JLabel logo = new JLabel("AD", SwingConstants.CENTER);
        logo.setPreferredSize(new Dimension(44, 44));
        logo.setFont(UITheme.font(Font.BOLD, 15));
        logo.setForeground(UITheme.GREEN);
        logo.setBackground(Color.WHITE);
        logo.setOpaque(true);

        JPanel titles = new JPanel();
        titles.setOpaque(false);
        titles.setLayout(new BoxLayout(titles, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("HỆ THỐNG QUẢN LÝ NHÂN SỰ");
        title.setFont(UITheme.font(Font.BOLD, 16));
        title.setForeground(Color.WHITE);

        JLabel sub = new JLabel("QUẢN TRỊ HỆ THỐNG • ADMIN");
        sub.setFont(UITheme.font(Font.PLAIN, 10));
        sub.setForeground(new Color(209, 250, 229));

        titles.add(title);
        titles.add(Box.createVerticalStrut(3));
        titles.add(sub);
        left.add(logo);
        left.add(titles);

        JPanel user = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 14));
        user.setOpaque(false);

        JLabel info = new JLabel("<html><b>Quản trị viên</b><br><font size='2'>ADMIN • Đang hoạt động</font></html>");
        info.setForeground(Color.WHITE);
        info.setHorizontalAlignment(SwingConstants.RIGHT);

        JLabel avatar = new JLabel("A", SwingConstants.CENTER);
        avatar.setPreferredSize(new Dimension(40, 40));
        avatar.setFont(UITheme.font(Font.BOLD, 15));
        avatar.setForeground(UITheme.GREEN);
        avatar.setBackground(Color.WHITE);
        avatar.setOpaque(true);

        user.add(info);
        user.add(avatar);

        header.add(left, BorderLayout.WEST);
        header.add(user, BorderLayout.EAST);
        return header;
    }

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(UITheme.NAVY);
        sidebar.setBorder(new EmptyBorder(18, 14, 16, 14));
        sidebar.setPreferredSize(new Dimension(260, 0));

        sidebar.add(UITheme.createSidebarHeader(
                "QUẢN TRỊ HỆ THỐNG",
                "Quản lý toàn bộ nghiệp vụ"
        ));
        sidebar.add(Box.createVerticalStrut(18));

        JLabel menu = new JLabel("  CHỨC NĂNG QUẢN TRỊ");
        menu.setFont(UITheme.font(Font.BOLD, 10));
        menu.setForeground(new Color(148, 163, 184));
        menu.setAlignmentX(Component.LEFT_ALIGNMENT);
        sidebar.add(menu);
        sidebar.add(Box.createVerticalStrut(8));

        JButton btnNhanVien = UITheme.navButton("NV", "Quản lý Nhân Viên");
        firstButton = btnNhanVien;
        JButton btnPhongBan = UITheme.navButton("PB", "Quản lý Phòng Ban");
        JButton btnChucVu = UITheme.navButton("CV", "Quản lý Chức Vụ");
        JButton btnHopDong = UITheme.navButton("HD", "Quản lý Hợp Đồng");
        JButton btnChamCong = UITheme.navButton("CC", "Quản lý Chấm Công");
        JButton btnNghiPhep = UITheme.navButton("NP", "Quản lý Nghỉ Phép");
        JButton btnTangCa = UITheme.navButton("TC", "Quản lý Tăng Ca");
        JButton btnPhuCap = UITheme.navButton("PC", "Phụ Cấp & Khấu Trừ");
        JButton btnBangLuong = UITheme.navButton("BL", "Tính Lương & Bảng Lương");
        JButton btnTaiKhoan = UITheme.navButton("TK", "Quản lý Tài Khoản");

        JButton[] buttons = {btnNhanVien, btnPhongBan, btnChucVu, btnHopDong,
                btnChamCong, btnNghiPhep, btnTangCa, btnPhuCap, btnBangLuong, btnTaiKhoan};

        for (JButton b : buttons) {
            sidebar.add(b);
            sidebar.add(Box.createVerticalStrut(4));
        }

        sidebar.add(Box.createVerticalGlue());

        JButton logout = UITheme.logoutButton();
        sidebar.add(logout);

        btnNhanVien.addActionListener(e -> showPage(btnNhanVien, "NHAN_VIEN"));
        btnPhongBan.addActionListener(e -> showPage(btnPhongBan, "PHONG_BAN"));
        btnChucVu.addActionListener(e -> showPage(btnChucVu, "CHUC_VU"));
        btnHopDong.addActionListener(e -> showPage(btnHopDong, "HOP_DONG"));
        btnChamCong.addActionListener(e -> showPage(btnChamCong, "CHAM_CONG"));
        btnNghiPhep.addActionListener(e -> showPage(btnNghiPhep, "NGHI_PHEP"));
        btnTangCa.addActionListener(e -> showPage(btnTangCa, "TANG_CA"));
        btnPhuCap.addActionListener(e -> showPage(btnPhuCap, "PHU_CAP"));
        btnBangLuong.addActionListener(e -> showPage(btnBangLuong, "BANG_LUONG"));
        btnTaiKhoan.addActionListener(e -> showPage(btnTaiKhoan, "TAI_KHOAN"));
        logout.addActionListener(e -> logout());

        return sidebar;
    }

    private void showPage(JButton button, String page) {
        if (activeButton != null) UITheme.setNavActive(activeButton, false);
        activeButton = button;
        UITheme.setNavActive(button, true);
        cardLayout.show(panelCenter, page);
    }

    private JPanel createFooter() {
        JPanel footer = new JPanel(new BorderLayout());
        footer.setPreferredSize(new Dimension(0, 30));
        footer.setBackground(Color.WHITE);
        footer.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, UITheme.BORDER));

        JLabel left = new JLabel("  © 2026 EAUT • Hệ thống quản lý nhân sự");
        left.setFont(UITheme.font(Font.PLAIN, 9));
        left.setForeground(UITheme.MUTED);

        JLabel right = new JLabel("ADMIN • TOÀN QUYỀN QUẢN TRỊ  ");
        right.setFont(UITheme.font(Font.BOLD, 9));
        right.setForeground(UITheme.GREEN_DARK);

        footer.add(left, BorderLayout.WEST);
        footer.add(right, BorderLayout.EAST);
        return footer;
    }

    private void logout() {
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Bạn có chắc chắn muốn đăng xuất khỏi hệ thống?",
                "Xác nhận đăng xuất",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (confirm == JOptionPane.YES_OPTION) {
            dispose();
            new LoginFrame().setVisible(true);
        }
    }
}
