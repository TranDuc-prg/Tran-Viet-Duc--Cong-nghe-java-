package vn.edu.eaut.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

/**
 * Bộ giao diện dùng chung cho toàn bộ hệ thống.
 * Không sử dụng thư viện bên ngoài.
 */
public final class UITheme {

    private UITheme() {}

    public static final Color NAVY = new Color(15, 23, 42);
    public static final Color NAVY_2 = new Color(30, 41, 59);
    public static final Color GREEN = new Color(16, 185, 129);
    public static final Color GREEN_DARK = new Color(5, 150, 105);
    public static final Color GREEN_LIGHT = new Color(236, 253, 245);
    public static final Color BG = new Color(241, 245, 249);
    public static final Color CARD = Color.WHITE;
    public static final Color TEXT = new Color(15, 23, 42);
    public static final Color MUTED = new Color(100, 116, 139);
    public static final Color BORDER = new Color(226, 232, 240);
    public static final Color DANGER = new Color(220, 38, 38);

    public static Font font(int style, int size) {
        return new Font("Segoe UI", style, size);
    }

    public static void install() {
        UIManager.put("Button.font", font(Font.PLAIN, 12));
        UIManager.put("Label.font", font(Font.PLAIN, 12));
        UIManager.put("TextField.font", font(Font.PLAIN, 13));
        UIManager.put("PasswordField.font", font(Font.PLAIN, 13));
        UIManager.put("ComboBox.font", font(Font.PLAIN, 12));
        UIManager.put("Table.font", font(Font.PLAIN, 12));
        UIManager.put("TableHeader.font", font(Font.BOLD, 12));
        UIManager.put("OptionPane.messageFont", font(Font.PLAIN, 13));
        UIManager.put("OptionPane.buttonFont", font(Font.BOLD, 12));
    }

    public static JButton button(String text, Color bg, Color fg) {
        JButton b = new JButton(text);
        b.setFont(font(Font.BOLD, 12));
        b.setForeground(fg);
        b.setBackground(bg);
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setOpaque(true);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setMargin(new Insets(0, 14, 0, 14));
        return b;
    }

    public static void styleTable(JTable table) {
        table.setFont(font(Font.PLAIN, 12));
        table.setForeground(TEXT);
        table.setBackground(Color.WHITE);
        table.setGridColor(new Color(226, 232, 240));
        table.setRowHeight(32);
        table.setShowVerticalLines(false);
        table.setIntercellSpacing(new Dimension(0, 1));
        table.setSelectionBackground(new Color(209, 250, 229));
        table.setSelectionForeground(TEXT);
        table.setFillsViewportHeight(true);
        table.setAutoCreateRowSorter(true);

        JTableHeader h = table.getTableHeader();
        h.setFont(font(Font.BOLD, 12));
        h.setForeground(Color.WHITE);
        h.setBackground(NAVY_2);
        h.setPreferredSize(new Dimension(0, 38));
        h.setReorderingAllowed(false);
    }

    public static void styleTextField(JTextField field) {
        field.setFont(font(Font.PLAIN, 13));
        field.setForeground(TEXT);
        field.setBackground(new Color(248, 250, 252));
        field.setCaretColor(GREEN);
        field.setBorder(new LineBorder(BORDER, 1, true));
        field.setPreferredSize(new Dimension(220, 38));

        field.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override public void focusGained(java.awt.event.FocusEvent e) {
                field.setBorder(new LineBorder(GREEN, 2, true));
            }
            @Override public void focusLost(java.awt.event.FocusEvent e) {
                field.setBorder(new LineBorder(BORDER, 1, true));
            }
        });
    }

    public static JPanel card(int radius) {
        return new RoundedPanel(CARD, radius);
    }

    public static JPanel createSidebarHeader(String title, String subtitle) {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(NAVY_2);
        p.setBorder(new EmptyBorder(15, 15, 15, 15));
        p.setMaximumSize(new Dimension(Integer.MAX_VALUE, 92));

        JLabel t = new JLabel(title);
        t.setFont(font(Font.BOLD, 13));
        t.setForeground(Color.WHITE);

        JLabel s = new JLabel(subtitle);
        s.setFont(font(Font.PLAIN, 10));
        s.setForeground(new Color(148, 163, 184));

        p.add(t);
        p.add(Box.createVerticalStrut(5));
        p.add(s);
        return p;
    }

    public static JButton navButton(String code, String text) {
        JButton b = new JButton();
        b.setLayout(new BorderLayout());
        b.setMaximumSize(new Dimension(Integer.MAX_VALUE, 48));
        b.setPreferredSize(new Dimension(230, 48));
        b.setAlignmentX(Component.LEFT_ALIGNMENT);
        b.setBackground(NAVY);
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setOpaque(true);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JLabel icon = new JLabel(code, SwingConstants.CENTER);
        icon.setPreferredSize(new Dimension(44, 48));
        icon.setFont(font(Font.BOLD, 10));
        icon.setForeground(new Color(148, 163, 184));

        JLabel label = new JLabel(text);
        label.setFont(font(Font.BOLD, 12));
        label.setForeground(new Color(226, 232, 240));

        JLabel arrow = new JLabel("›", SwingConstants.CENTER);
        arrow.setPreferredSize(new Dimension(28, 48));
        arrow.setFont(font(Font.BOLD, 20));
        arrow.setForeground(new Color(100, 116, 139));

        b.add(icon, BorderLayout.WEST);
        b.add(label, BorderLayout.CENTER);
        b.add(arrow, BorderLayout.EAST);

        b.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) {
                if (!UITheme.isActive(b)) {
                    b.setBackground(NAVY_2);
                    icon.setForeground(GREEN);
                    arrow.setForeground(GREEN);
                }
            }
            @Override public void mouseExited(MouseEvent e) {
                if (!UITheme.isActive(b)) {
                    b.setBackground(NAVY);
                    icon.setForeground(new Color(148, 163, 184));
                    arrow.setForeground(new Color(100, 116, 139));
                }
            }
        });

        return b;
    }

    public static void setNavActive(JButton b, boolean active) {
        b.putClientProperty("active", active);
        b.setBackground(active ? NAVY_2 : NAVY);

        for (Component c : b.getComponents()) {
            if (c instanceof JLabel l) {
                if (active) {
                    l.setForeground(l.getText().equals("›") ? GREEN : Color.WHITE);
                } else {
                    if (l.getText().equals("›")) l.setForeground(new Color(100,116,139));
                    else if (!l.getText().equals("›")) l.setForeground(new Color(226,232,240));
                }
            }
        }
    }

    public static boolean isActive(JButton b) {
        Object value = b.getClientProperty("active");
        return Boolean.TRUE.equals(value);
    }

    public static JButton logoutButton() {
        JButton b = new JButton("⇥   Đăng xuất hệ thống");
        b.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        b.setPreferredSize(new Dimension(230, 44));
        b.setAlignmentX(Component.LEFT_ALIGNMENT);
        b.setFont(font(Font.BOLD, 12));
        b.setForeground(new Color(248, 113, 113));
        b.setBackground(NAVY);
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setOpaque(true);
        b.setHorizontalAlignment(SwingConstants.LEFT);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        b.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) {
                b.setBackground(new Color(127, 29, 29));
                b.setForeground(Color.WHITE);
            }
            @Override public void mouseExited(MouseEvent e) {
                b.setBackground(NAVY);
                b.setForeground(new Color(248, 113, 113));
            }
        });
        return b;
    }

    public static JPanel contentBackground() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(BG);
        p.setBorder(new EmptyBorder(14, 14, 14, 14));
        return p;
    }

    public static JLabel sectionTitle(String title) {
        JLabel l = new JLabel(title);
        l.setFont(font(Font.BOLD, 20));
        l.setForeground(TEXT);
        return l;
    }

    public static JLabel sectionSubtitle(String title) {
        JLabel l = new JLabel(title);
        l.setFont(font(Font.PLAIN, 11));
        l.setForeground(MUTED);
        return l;
    }

    private static class RoundedPanel extends JPanel {
        private final Color bg;
        private final int radius;

        RoundedPanel(Color bg, int radius) {
            this.bg = bg;
            this.radius = radius;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(bg);
            g2.fill(new RoundRectangle2D.Double(0, 0, getWidth()-1, getHeight()-1, radius, radius));
            g2.dispose();
            super.paintComponent(g);
        }
    }
}
