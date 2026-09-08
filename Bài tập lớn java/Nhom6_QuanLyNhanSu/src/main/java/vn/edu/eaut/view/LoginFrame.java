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
 * ============================================================
 * LOGIN FRAME
 * HỆ THỐNG QUẢN LÝ NHÂN SỰ
 *
 * Giao diện:
 * - Pastel xanh mint + hồng
 * - Dễ thương nhưng vẫn chuyên nghiệp
 * - Không phụ thuộc UITheme
 * - Không sử dụng thư viện ngoài
 * ============================================================
 */
public class LoginFrame extends JFrame {

    // =========================================================
    // MÀU SẮC
    // =========================================================

    private static final Color MINT = new Color(52, 211, 153);
    private static final Color MINT_DARK = new Color(16, 163, 127);
    private static final Color MINT_LIGHT = new Color(220, 252, 231);

    private static final Color PINK = new Color(244, 114, 182);
    private static final Color PINK_LIGHT = new Color(252, 231, 243);

    private static final Color NAVY = new Color(30, 41, 59);
    private static final Color NAVY_LIGHT = new Color(51, 65, 85);

    private static final Color BACKGROUND = new Color(246, 248, 252);
    private static final Color CARD = Color.WHITE;

    private static final Color TEXT = new Color(30, 41, 59);
    private static final Color TEXT_LIGHT = new Color(100, 116, 139);

    private static final Color BORDER = new Color(226, 232, 240);

    // =========================================================
    // COMPONENT
    // =========================================================

    private JTextField txtUsername;
    private JPasswordField txtPassword;

    private JButton btnLogin;
    private JButton btnRegister;

    private JCheckBox chkShowPassword;

    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public LoginFrame() {

        setTitle("Đăng nhập ♡ | Hệ thống quản lý nhân sự");

        setSize(1050, 650);

        setMinimumSize(new Dimension(950, 600));

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

        setResizable(false);

        // -----------------------------------------------------
        // ROOT
        // -----------------------------------------------------

        JPanel root = new JPanel(new BorderLayout());

        root.setBackground(BACKGROUND);

        setContentPane(root);

        // -----------------------------------------------------
        // LEFT
        // -----------------------------------------------------

        root.add(createLeftPanel(), BorderLayout.WEST);

        // -----------------------------------------------------
        // RIGHT
        // -----------------------------------------------------

        root.add(createRightPanel(), BorderLayout.CENTER);

        // -----------------------------------------------------
        // ENTER = LOGIN
        // -----------------------------------------------------

        getRootPane().setDefaultButton(btnLogin);

        // -----------------------------------------------------
        // EVENTS
        // -----------------------------------------------------

        btnLogin.addActionListener(e -> handleLogin());

        btnRegister.addActionListener(e -> {

            new RegisterFrame().setVisible(true);

            dispose();

        });

        chkShowPassword.addActionListener(e -> {

            if (chkShowPassword.isSelected()) {

                txtPassword.setEchoChar((char) 0);

            } else {

                txtPassword.setEchoChar('•');

            }

        });

        // Focus mặc định

        SwingUtilities.invokeLater(() -> txtUsername.requestFocusInWindow());
    }

    // =========================================================
    // LEFT PANEL
    // =========================================================

    private JPanel createLeftPanel() {

        JPanel panel = new JPanel();

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.setBackground(NAVY);

        panel.setBorder(
                new EmptyBorder(
                        45,
                        45,
                        35,
                        40
                )
        );

        panel.setPreferredSize(new Dimension(430, 650));

        // -----------------------------------------------------
        // LOGO
        // -----------------------------------------------------

        JPanel logoPanel = new JPanel(new GridBagLayout());

        logoPanel.setBackground(Color.WHITE);

        logoPanel.setPreferredSize(new Dimension(82, 82));

        logoPanel.setMaximumSize(new Dimension(82, 82));

        logoPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel logo = new JLabel("EAUT");

        logo.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        22
                )
        );

        logo.setForeground(MINT_DARK);

        logoPanel.add(logo);

        panel.add(logoPanel);

        // -----------------------------------------------------
        // khoảng cách
        // -----------------------------------------------------

        panel.add(Box.createVerticalStrut(38));

        // -----------------------------------------------------
        // HELLO
        // -----------------------------------------------------

        JLabel hello = new JLabel("Xin chào bạn ♡");

        hello.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        14
                )
        );

        hello.setForeground(
                new Color(167, 243, 208)
        );

        hello.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(hello);

        panel.add(Box.createVerticalStrut(12));

        // -----------------------------------------------------
        // TITLE
        // -----------------------------------------------------

        JLabel title = new JLabel(
                "<html>" +
                        "HỆ THỐNG<br>" +
                        "QUẢN LÝ NHÂN SỰ" +
                        "</html>"
        );

        title.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        30
                )
        );

        title.setForeground(Color.WHITE);

        title.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(title);

        // -----------------------------------------------------
        // DESCRIPTION
        // -----------------------------------------------------

        panel.add(Box.createVerticalStrut(18));

        JLabel description = new JLabel(
                "<html>" +
                        "Quản lý nhân sự thật đơn giản,<br>" +
                        "dễ dàng và thân thiện ♡" +
                        "</html>"
        );

        description.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
                )
        );

        description.setForeground(
                new Color(203, 213, 225)
        );

        description.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(description);

        // -----------------------------------------------------
        // FEATURES
        // -----------------------------------------------------

        panel.add(Box.createVerticalStrut(35));

        panel.add(
                createFeature(
                        "01",
                        "Quản lý thông tin nhân viên"
                )
        );

        panel.add(Box.createVerticalStrut(13));

        panel.add(
                createFeature(
                        "02",
                        "Quản lý phòng ban & chức vụ"
                )
        );

        panel.add(Box.createVerticalStrut(13));

        panel.add(
                createFeature(
                        "03",
                        "Quản lý hợp đồng & chấm công"
                )
        );

        panel.add(Box.createVerticalStrut(13));

        panel.add(
                createFeature(
                        "04",
                        "Tính lương & phụ cấp"
                )
        );

        // -----------------------------------------------------
        // DECORATION
        // -----------------------------------------------------

        panel.add(Box.createVerticalGlue());

        JLabel heart = new JLabel("♡  ♡  ♡");

        heart.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        18
                )
        );

        heart.setForeground(PINK);

        heart.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(heart);

        panel.add(Box.createVerticalStrut(8));

        JLabel footer = new JLabel(
                "© 2026 EAUT • Human Resource Management"
        );

        footer.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        10
                )
        );

        footer.setForeground(
                new Color(100, 116, 139)
        );

        footer.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(footer);

        return panel;
    }

    // =========================================================
    // FEATURE
    // =========================================================

    private JPanel createFeature(
            String number,
            String text
    ) {

        JPanel panel = new JPanel(
                new BorderLayout(
                        14,
                        0
                )
        );

        panel.setOpaque(false);

        panel.setMaximumSize(
                new Dimension(
                        330,
                        36
                )
        );

        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // -----------------------------------------------------
        // NUMBER
        // -----------------------------------------------------

        JLabel numberLabel = new JLabel(
                number,
                SwingConstants.CENTER
        );

        numberLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        10
                )
        );

        numberLabel.setForeground(
                new Color(110, 231, 183)
        );

        numberLabel.setBorder(
                new LineBorder(
                        new Color(52, 211, 153, 150),
                        1,
                        true
                )
        );

        numberLabel.setPreferredSize(
                new Dimension(
                        36,
                        30
                )
        );

        // -----------------------------------------------------
        // TEXT
        // -----------------------------------------------------

        JLabel textLabel = new JLabel(text);

        textLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        12
                )
        );

        textLabel.setForeground(
                new Color(203, 213, 225)
        );

        panel.add(
                numberLabel,
                BorderLayout.WEST
        );

        panel.add(
                textLabel,
                BorderLayout.CENTER
        );

        return panel;
    }

    // =========================================================
    // RIGHT PANEL
    // =========================================================

    private JPanel createRightPanel() {

        JPanel background = new JPanel(
                new GridBagLayout()
        );

        background.setBackground(BACKGROUND);

        background.setBorder(
                new EmptyBorder(
                        30,
                        45,
                        30,
                        45
                )
        );

        // -----------------------------------------------------
        // CARD
        // -----------------------------------------------------

        RoundedPanel card =
                new RoundedPanel(
                        CARD,
                        28
                );

        card.setLayout(
                new BoxLayout(
                        card,
                        BoxLayout.Y_AXIS
                )
        );

        card.setBorder(
                new EmptyBorder(
                        38,
                        45,
                        32,
                        45
                )
        );

        card.setPreferredSize(
                new Dimension(
                        470,
                        520
                )
        );

        // -----------------------------------------------------
        // TITLE
        // -----------------------------------------------------

        JLabel title = new JLabel(
                "Đăng nhập ♡"
        );

        title.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        28
                )
        );

        title.setForeground(TEXT);

        title.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        card.add(title);

        // -----------------------------------------------------
        // SUBTITLE
        // -----------------------------------------------------

        card.add(
                Box.createVerticalStrut(6)
        );

        JLabel subtitle = new JLabel(
                "Rất vui được gặp lại bạn!"
        );

        subtitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        13
                )
        );

        subtitle.setForeground(TEXT_LIGHT);

        subtitle.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        card.add(subtitle);

        // -----------------------------------------------------
        // SPACE
        // -----------------------------------------------------

        card.add(
                Box.createVerticalStrut(28)
        );

        // -----------------------------------------------------
        // USERNAME
        // -----------------------------------------------------

        card.add(
                createLabel("TÀI KHOẢN")
        );

        card.add(
                Box.createVerticalStrut(7)
        );

        txtUsername = createTextField();

        card.add(txtUsername);

        // -----------------------------------------------------
        // PASSWORD
        // -----------------------------------------------------

        card.add(
                Box.createVerticalStrut(17)
        );

        card.add(
                createLabel("MẬT KHẨU")
        );

        card.add(
                Box.createVerticalStrut(7)
        );

        txtPassword = createPasswordField();

        card.add(txtPassword);

        // -----------------------------------------------------
        // SHOW PASSWORD
        // -----------------------------------------------------

        card.add(
                Box.createVerticalStrut(8)
        );

        chkShowPassword =
                new JCheckBox(
                        "Hiện mật khẩu"
                );

        chkShowPassword.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        11
                )
        );

        chkShowPassword.setForeground(TEXT_LIGHT);

        chkShowPassword.setBackground(Color.WHITE);

        chkShowPassword.setFocusPainted(false);

        chkShowPassword.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        card.add(chkShowPassword);

        // -----------------------------------------------------
        // LOGIN
        // -----------------------------------------------------

        card.add(
                Box.createVerticalStrut(22)
        );

        btnLogin =
                createPrimaryButton(
                        "♡  ĐĂNG NHẬP  ♡"
                );

        card.add(btnLogin);

        // -----------------------------------------------------
        // REGISTER
        // -----------------------------------------------------

        card.add(
                Box.createVerticalStrut(12)
        );

        btnRegister =
                createSecondaryButton(
                        "TẠO TÀI KHOẢN MỚI"
                );

        card.add(btnRegister);

        // -----------------------------------------------------
        // FOOTER
        // -----------------------------------------------------

        card.add(
                Box.createVerticalGlue()
        );

        JLabel info =
                new JLabel(
                        "♡  Bảo mật   •   Chính xác   •   Thân thiện  ♡"
                );

        info.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        10
                )
        );

        info.setForeground(
                new Color(148, 163, 184)
        );

        info.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        card.add(info);

        background.add(card);

        return background;
    }

    // =========================================================
    // LABEL
    // =========================================================

    private JLabel createLabel(
            String text
    ) {

        JLabel label =
                new JLabel(text);

        label.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        11
                )
        );

        label.setForeground(TEXT);

        label.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        return label;
    }

    // =========================================================
    // TEXT FIELD
    // =========================================================

    private JTextField createTextField() {

        JTextField field =
                new JTextField();

        field.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
                )
        );

        field.setForeground(TEXT);

        field.setBackground(
                new Color(
                        248,
                        250,
                        252
                )
        );

        field.setCaretColor(
                MINT_DARK
        );

        field.setBorder(
                new LineBorder(
                        BORDER,
                        1,
                        true
                )
        );

        field.setPreferredSize(
                new Dimension(
                        370,
                        45
                )
        );

        field.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        45
                )
        );

        field.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        addFocusEffect(field);

        return field;
    }

    // =========================================================
    // PASSWORD FIELD
    // =========================================================

    private JPasswordField createPasswordField() {

        JPasswordField field =
                new JPasswordField();

        field.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
                )
        );

        field.setForeground(TEXT);

        field.setBackground(
                new Color(
                        248,
                        250,
                        252
                )
        );

        field.setCaretColor(
                MINT_DARK
        );

        field.setEchoChar('•');

        field.setBorder(
                new LineBorder(
                        BORDER,
                        1,
                        true
                )
        );

        field.setPreferredSize(
                new Dimension(
                        370,
                        45
                )
        );

        field.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        45
                )
        );

        field.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        addFocusEffect(field);

        return field;
    }

    // =========================================================
    // FOCUS EFFECT
    // =========================================================

    private void addFocusEffect(
            JComponent component
    ) {

        component.addFocusListener(
                new FocusAdapter() {

                    @Override
                    public void focusGained(
                            FocusEvent e
                    ) {

                        component.setBorder(
                                new LineBorder(
                                        MINT,
                                        2,
                                        true
                                )
                        );

                        component.setBackground(
                                Color.WHITE
                        );
                    }

                    @Override
                    public void focusLost(
                            FocusEvent e
                    ) {

                        component.setBorder(
                                new LineBorder(
                                        BORDER,
                                        1,
                                        true
                                )
                        );

                        component.setBackground(
                                new Color(
                                        248,
                                        250,
                                        252
                                )
                        );
                    }
                }
        );
    }

    // =========================================================
    // PRIMARY BUTTON
    // =========================================================

    private JButton createPrimaryButton(
            String text
    ) {

        JButton button =
                new JButton(text);

        button.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        12
                )
        );

        button.setForeground(Color.WHITE);

        button.setBackground(MINT_DARK);

        button.setFocusPainted(false);

        button.setBorderPainted(false);

        button.setOpaque(true);

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        button.setPreferredSize(
                new Dimension(
                        370,
                        45
                )
        );

        button.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        45
                )
        );

        button.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        button.addMouseListener(
                new java.awt.event.MouseAdapter() {

                    @Override
                    public void mouseEntered(
                            java.awt.event.MouseEvent e
                    ) {

                        button.setBackground(
                                MINT
                        );
                    }

                    @Override
                    public void mouseExited(
                            java.awt.event.MouseEvent e
                    ) {

                        button.setBackground(
                                MINT_DARK
                        );
                    }
                }
        );

        return button;
    }

    // =========================================================
    // SECONDARY BUTTON
    // =========================================================

    private JButton createSecondaryButton(
            String text
    ) {

        JButton button =
                new JButton(text);

        button.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        11
                )
        );

        button.setForeground(
                MINT_DARK
        );

        button.setBackground(
                Color.WHITE
        );

        button.setFocusPainted(false);

        button.setOpaque(true);

        button.setBorder(
                new LineBorder(
                        new Color(
                                167,
                                243,
                                208
                        ),
                        1,
                        true
                )
        );

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        button.setPreferredSize(
                new Dimension(
                        370,
                        42
                )
        );

        button.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        42
                )
        );

        button.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        button.addMouseListener(
                new java.awt.event.MouseAdapter() {

                    @Override
                    public void mouseEntered(
                            java.awt.event.MouseEvent e
                    ) {

                        button.setBackground(
                                MINT_LIGHT
                        );
                    }

                    @Override
                    public void mouseExited(
                            java.awt.event.MouseEvent e
                    ) {

                        button.setBackground(
                                Color.WHITE
                        );
                    }
                }
        );

        return button;
    }

    // =========================================================
    // HANDLE LOGIN
    // =========================================================

    private void handleLogin() {

        String username =
                txtUsername
                        .getText()
                        .trim();

        String password =
                new String(
                        txtPassword.getPassword()
                ).trim();

        // -----------------------------------------------------
        // CHECK EMPTY
        // -----------------------------------------------------

        if (
                username.isEmpty()
                        ||
                        password.isEmpty()
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "♡ Vui lòng nhập đầy đủ tài khoản và mật khẩu!",
                    "Thiếu thông tin",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        // -----------------------------------------------------
        // LOGIN
        // -----------------------------------------------------

        try {

            NhanVienDAO dao =
                    new NhanVienDAO();

            String role =
                    dao.checkLogin(
                            username,
                            password
                    );

            // -------------------------------------------------
            // SUCCESS
            // -------------------------------------------------

            if (role != null) {

                role =
                        role
                                .trim()
                                .toUpperCase();

                JOptionPane.showMessageDialog(
                        this,
                        "♡ Đăng nhập thành công!\n\n" +
                                "Tài khoản: " +
                                username +
                                "\nQuyền: " +
                                role,
                        "Chào mừng bạn!",
                        JOptionPane.INFORMATION_MESSAGE
                );

                // ---------------------------------------------
                // ADMIN
                // ---------------------------------------------

                if ("ADMIN".equals(role)) {

                    new AdminMainFrame()
                            .setVisible(true);

                }

                // ---------------------------------------------
                // NHÂN SỰ
                // ---------------------------------------------

                else if (
                        "NHANSU".equals(role)
                ) {

                    new NhanSuMainFrame()
                            .setVisible(true);

                }

                // ---------------------------------------------
                // KẾ TOÁN
                // ---------------------------------------------

                else if (
                        "KETOAN".equals(role)
                ) {

                    new KeToanMainFrame()
                            .setVisible(true);

                }

                // ---------------------------------------------
                // ROLE KHÁC
                // ---------------------------------------------

                else {

                    new AdminMainFrame()
                            .setVisible(true);
                }

                dispose();

            }

            // -------------------------------------------------
            // FAIL
            // -------------------------------------------------

            else {

                JOptionPane.showMessageDialog(
                        this,
                        "Tài khoản hoặc mật khẩu không đúng.\n" +
                                "Hoặc tài khoản hiện không hoạt động.",
                        "Đăng nhập thất bại",
                        JOptionPane.ERROR_MESSAGE
                );

                txtPassword.setText("");

                txtPassword.requestFocus();

            }

        }

        // -----------------------------------------------------
        // DATABASE ERROR
        // -----------------------------------------------------

        catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Không thể kết nối đến hệ thống.\n\n" +
                            "Chi tiết:\n" +
                            ex.getMessage(),
                    "Lỗi hệ thống",
                    JOptionPane.ERROR_MESSAGE
            );

            ex.printStackTrace();
        }
    }

    // =========================================================
    // ROUNDED PANEL
    // =========================================================

    private static class RoundedPanel
            extends JPanel {

        private final Color backgroundColor;

        private final int radius;

        public RoundedPanel(
                Color backgroundColor,
                int radius
        ) {

            this.backgroundColor =
                    backgroundColor;

            this.radius =
                    radius;

            setOpaque(false);
        }

        @Override
        protected void paintComponent(
                Graphics g
        ) {

            Graphics2D g2 =
                    (Graphics2D)
                            g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            g2.setColor(
                    backgroundColor
            );

            g2.fill(
                    new RoundRectangle2D.Double(
                            0,
                            0,
                            getWidth() - 1,
                            getHeight() - 1,
                            radius,
                            radius
                    )
            );

            g2.dispose();

            super.paintComponent(g);
        }
    }

    // =========================================================
    // MAIN
    // =========================================================

    public static void main(
            String[] args
    ) {

        try {

            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName()
            );

        } catch (Exception ignored) {
        }

        SwingUtilities.invokeLater(
                () -> {

                    LoginFrame frame =
                            new LoginFrame();

                    frame.setVisible(true);
                }
        );
    }
}