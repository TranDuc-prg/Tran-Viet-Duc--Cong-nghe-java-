package vn.edu.eaut.view;

import vn.edu.eaut.model.dao.NhanVienDAO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.geom.RoundRectangle2D;

/**
 * Màn hình đăng ký tài khoản.
 */
public class RegisterFrame extends JFrame {

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JPasswordField txtConfirmPassword;
    private JButton btnRegister;
    private JButton btnBack;

    public RegisterFrame() {
        UITheme.install();

        setTitle("Đăng ký | Hệ thống quản lý nhân sự");
        setSize(760, 540);
        setMinimumSize(new Dimension(700, 500));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(UITheme.BG);
        setContentPane(root);

        root.add(createBrandPanel(), BorderLayout.WEST);
        root.add(createFormPanel(), BorderLayout.CENTER);

        getRootPane().setDefaultButton(btnRegister);

        btnRegister.addActionListener(e -> handleRegister());

        btnBack.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            dispose();
        });
    }

    private JPanel createBrandPanel() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(UITheme.NAVY);
        p.setBorder(new EmptyBorder(45, 40, 35, 40));
        p.setPreferredSize(new Dimension(280, 540));

        JLabel logo = new JLabel("EAUT", SwingConstants.CENTER);
        logo.setPreferredSize(new Dimension(72, 72));
        logo.setMaximumSize(new Dimension(72, 72));
        logo.setFont(UITheme.font(Font.BOLD, 21));
        logo.setForeground(UITheme.GREEN);
        logo.setBackground(Color.WHITE);
        logo.setOpaque(true);
        logo.setAlignmentX(Component.LEFT_ALIGNMENT);
        p.add(logo);

        p.add(Box.createVerticalStrut(30));

        JLabel title = new JLabel(
                "<html>TẠO TÀI KHOẢN<br>HỆ THỐNG</html>"
        );
        title.setFont(UITheme.font(Font.BOLD, 23));
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        p.add(title);

        p.add(Box.createVerticalStrut(15));

        JLabel desc = new JLabel(
                "<html>Đăng ký tài khoản để<br>" +
                "truy cập hệ thống quản lý.</html>"
        );
        desc.setFont(UITheme.font(Font.PLAIN, 12));
        desc.setForeground(new Color(148, 163, 184));
        desc.setAlignmentX(Component.LEFT_ALIGNMENT);
        p.add(desc);

        p.add(Box.createVerticalGlue());

        JLabel footer = new JLabel("EAUT • HR Management");
        footer.setFont(UITheme.font(Font.PLAIN, 10));
        footer.setForeground(new Color(100, 116, 139));
        footer.setAlignmentX(Component.LEFT_ALIGNMENT);
        p.add(footer);

        return p;
    }

    private JPanel createFormPanel() {
        JPanel bg = new JPanel(new GridBagLayout());
        bg.setBackground(UITheme.BG);
        bg.setBorder(new EmptyBorder(25, 30, 25, 30));

        RoundedPanel card = new RoundedPanel(Color.WHITE, 22);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(35, 42, 30, 42));
        card.setPreferredSize(new Dimension(405, 455));

        JLabel title = new JLabel("Đăng ký tài khoản");
        title.setFont(UITheme.font(Font.BOLD, 23));
        title.setForeground(UITheme.TEXT);
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(title);

        card.add(Box.createVerticalStrut(6));

        JLabel subtitle = new JLabel("Tạo tài khoản mới cho hệ thống");
        subtitle.setFont(UITheme.font(Font.PLAIN, 12));
        subtitle.setForeground(UITheme.MUTED);
        subtitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(subtitle);

        card.add(Box.createVerticalStrut(24));

        card.add(label("Tài khoản"));
        card.add(Box.createVerticalStrut(6));
        txtUsername = textField();
        card.add(txtUsername);

        card.add(Box.createVerticalStrut(14));

        card.add(label("Mật khẩu"));
        card.add(Box.createVerticalStrut(6));
        txtPassword = passwordField();
        card.add(txtPassword);

        card.add(Box.createVerticalStrut(14));

        card.add(label("Nhập lại mật khẩu"));
        card.add(Box.createVerticalStrut(6));
        txtConfirmPassword = passwordField();
        card.add(txtConfirmPassword);

        card.add(Box.createVerticalStrut(22));

        btnRegister = UITheme.button("ĐĂNG KÝ TÀI KHOẢN", UITheme.GREEN, Color.WHITE);
        btnRegister.setPreferredSize(new Dimension(320, 42));
        btnRegister.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        btnRegister.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnRegister.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override public void mouseEntered(java.awt.event.MouseEvent e) {
                btnRegister.setBackground(UITheme.GREEN_DARK);
            }
            @Override public void mouseExited(java.awt.event.MouseEvent e) {
                btnRegister.setBackground(UITheme.GREEN);
            }
        });
        card.add(btnRegister);

        card.add(Box.createVerticalStrut(10));

        btnBack = UITheme.button("←  QUAY LẠI ĐĂNG NHẬP", Color.WHITE, UITheme.MUTED);
        btnBack.setPreferredSize(new Dimension(320, 38));
        btnBack.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        btnBack.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnBack.setBorder(new LineBorder(UITheme.BORDER, 1, true));
        card.add(btnBack);

        bg.add(card);
        return bg;
    }

    private JLabel label(String text) {
        JLabel l = new JLabel(text);
        l.setFont(UITheme.font(Font.BOLD, 12));
        l.setForeground(UITheme.TEXT);
        l.setAlignmentX(Component.LEFT_ALIGNMENT);
        return l;
    }

    private JTextField textField() {
        JTextField f = new JTextField();
        f.setFont(UITheme.font(Font.PLAIN, 13));
        f.setForeground(UITheme.TEXT);
        f.setBackground(new Color(248, 250, 252));
        f.setCaretColor(UITheme.GREEN);
        f.setBorder(new LineBorder(UITheme.BORDER, 1, true));
        f.setPreferredSize(new Dimension(320, 40));
        f.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        f.setAlignmentX(Component.LEFT_ALIGNMENT);
        addFocus(f);
        return f;
    }

    private JPasswordField passwordField() {
        JPasswordField f = new JPasswordField();
        f.setFont(UITheme.font(Font.PLAIN, 13));
        f.setForeground(UITheme.TEXT);
        f.setBackground(new Color(248, 250, 252));
        f.setCaretColor(UITheme.GREEN);
        f.setBorder(new LineBorder(UITheme.BORDER, 1, true));
        f.setPreferredSize(new Dimension(320, 40));
        f.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        f.setAlignmentX(Component.LEFT_ALIGNMENT);
        addFocus(f);
        return f;
    }

    private void addFocus(JComponent c) {
        c.addFocusListener(new FocusAdapter() {
            @Override public void focusGained(FocusEvent e) {
                c.setBorder(new LineBorder(UITheme.GREEN, 2, true));
            }
            @Override public void focusLost(FocusEvent e) {
                c.setBorder(new LineBorder(UITheme.BORDER, 1, true));
            }
        });
    }

    private void handleRegister() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();
        String confirmPassword = new String(txtConfirmPassword.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Vui lòng nhập đầy đủ thông tin!",
                    "Thiếu thông tin",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(
                    this,
                    "Mật khẩu nhập lại không khớp!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE
            );
            txtConfirmPassword.setText("");
            txtConfirmPassword.requestFocus();
            return;
        }

        try {
            NhanVienDAO dao = new NhanVienDAO();
            boolean success = dao.registerAccount(username, password);

            if (success) {
                JOptionPane.showMessageDialog(
                        this,
                        "Đăng ký thành công!\nVui lòng đăng nhập để tiếp tục.",
                        "Đăng ký thành công",
                        JOptionPane.INFORMATION_MESSAGE
                );

                new LoginFrame().setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Tên tài khoản đã tồn tại,\nvui lòng chọn tên khác!",
                        "Đăng ký thất bại",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Không thể kết nối đến hệ thống.\n\nChi tiết: " + ex.getMessage(),
                    "Lỗi hệ thống",
                    JOptionPane.ERROR_MESSAGE
            );
            ex.printStackTrace();
        }
    }

    private static class RoundedPanel extends JPanel {
        private final Color background;
        private final int radius;

        RoundedPanel(Color background, int radius) {
            this.background = background;
            this.radius = radius;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );
            g2.setColor(background);
            g2.fill(new RoundRectangle2D.Double(
                    0, 0, getWidth() - 1, getHeight() - 1, radius, radius
            ));
            g2.dispose();
            super.paintComponent(g);
        }
    }
}
