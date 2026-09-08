package vn.edu.eaut.view;

import vn.edu.eaut.model.dao.NhanVienDAO;
import vn.edu.eaut.model.NhanVien;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Date;
import java.text.DecimalFormat;
import java.util.List;

public class NhanVienFrame extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private String userRole;
    private JButton btnAdd, btnEdit, btnDelete, btnRefresh;
    private DecimalFormat currencyFormat = new DecimalFormat("#,###");

    public NhanVienFrame() {
        this("ADMIN");
    }

    public NhanVienFrame(String role) {
        this.userRole = role;
        setLayout(new BorderLayout(0, 12));
        setBackground(UITheme.BG);
        setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));

        // Tiêu đề module
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setOpaque(false);
        JLabel lblTitle = UITheme.sectionTitle("Quản lý thông tin nhân viên");
        JLabel lblSub = UITheme.sectionSubtitle("Danh sách nhân viên và thông tin lương cơ bản");
        JPanel texts = new JPanel();
        texts.setOpaque(false);
        texts.setLayout(new BoxLayout(texts, BoxLayout.Y_AXIS));
        texts.add(lblTitle);
        texts.add(Box.createVerticalStrut(3));
        texts.add(lblSub);
        titlePanel.add(texts, BorderLayout.WEST);
        add(titlePanel, BorderLayout.NORTH);

        // Khởi tạo bảng và tiêu đề cột
        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new String[]{
                "ID", "Mã NV", "Họ Tên", "Giới Tính", "Ngày Sinh", "Điện Thoại", "Email", "Phòng Ban ID", "Lương Cơ Bản"
        });

        table = new JTable(tableModel);
        UITheme.styleTable(table);
        loadDataToTable();

        JScrollPane tableScroll = new JScrollPane(table);
        tableScroll.setBorder(BorderFactory.createLineBorder(UITheme.BORDER));
        add(tableScroll, BorderLayout.CENTER);

        // --- THANH CÔNG CỤ CRUD ---
        JPanel panelAction = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 5));
        panelAction.setOpaque(false);

        btnAdd = UITheme.button("+  Thêm Nhân Viên", UITheme.GREEN, Color.WHITE);
        btnEdit = UITheme.button("Sửa Thông Tin", new Color(59, 130, 246), Color.WHITE);
        btnDelete = UITheme.button("Xóa Nhân Viên", UITheme.DANGER, Color.WHITE);
        btnRefresh = UITheme.button("Làm Mới", UITheme.NAVY_2, Color.WHITE);

        panelAction.add(btnAdd);
        panelAction.add(btnEdit);
        panelAction.add(btnDelete);
        panelAction.add(btnRefresh);

        if ("KETOAN".equalsIgnoreCase(userRole)) {
            btnAdd.setEnabled(false);
            btnEdit.setEnabled(false);
            btnDelete.setEnabled(false);
        }

        add(panelAction, BorderLayout.SOUTH);

        // --- SỰ KIỆN NÚT BẤM ---
        btnAdd.addActionListener(e -> showAddDialog());
        btnEdit.addActionListener(e -> showEditDialog());
        btnDelete.addActionListener(e -> handleDelete());
        btnRefresh.addActionListener(e -> loadDataToTable());
    }

    private void loadDataToTable() {
        tableModel.setRowCount(0);
        NhanVienDAO dao = new NhanVienDAO();
        List<NhanVien> list = dao.getAllNhanVien();

        for (NhanVien nv : list) {
            String formattedLuong = currencyFormat.format(nv.getLuongCoBan()) + " VNĐ";

            tableModel.addRow(new Object[]{
                    nv.getId(),
                    nv.getMaNv(),
                    nv.getHoTen(),
                    nv.getGioiTinh(),
                    nv.getNgaySinh(),
                    nv.getDienThoai(),
                    nv.getEmail(),
                    nv.getPhongBanId(),
                    formattedLuong
            });
        }
    }

    private void showAddDialog() {
        JTextField txtMaNv = new JTextField();
        JTextField txtHoTen = new JTextField();
        JComboBox<String> cbGioiTinh = new JComboBox<>(new String[]{"Nam", "Nữ", "Khác"});
        JTextField txtNgaySinh = new JTextField("2000-01-01");
        JTextField txtDienThoai = new JTextField();
        JTextField txtEmail = new JTextField();
        JTextField txtPhongBanId = new JTextField("1");
        JTextField txtLuongCoBan = new JTextField("10000000");

        Object[] message = {
                "Mã NV:", txtMaNv,
                "Họ Tên:", txtHoTen,
                "Giới Tính:", cbGioiTinh,
                "Ngày Sinh (YYYY-MM-DD):", txtNgaySinh,
                "Điện Thoại:", txtDienThoai,
                "Email:", txtEmail,
                "Phòng Ban ID:", txtPhongBanId,
                "Lương Cơ Bản:", txtLuongCoBan
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Thêm Nhân Viên Mới", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                NhanVien nv = new NhanVien();
                nv.setMaNv(txtMaNv.getText().trim());
                nv.setHoTen(txtHoTen.getText().trim());
                nv.setGioiTinh(cbGioiTinh.getSelectedItem().toString());
                nv.setNgaySinh(Date.valueOf(txtNgaySinh.getText().trim()));
                nv.setDienThoai(txtDienThoai.getText().trim());
                nv.setEmail(txtEmail.getText().trim());
                nv.setPhongBanId(Integer.parseInt(txtPhongBanId.getText().trim()));
                nv.setLuongCoBan(Double.parseDouble(txtLuongCoBan.getText().trim()));

                NhanVienDAO dao = new NhanVienDAO();
                boolean success = dao.insertNhanVien(nv);

                if (success) {
                    JOptionPane.showMessageDialog(this, "Thêm nhân viên thành công!");
                    loadDataToTable();
                } else {
                    JOptionPane.showMessageDialog(this, "Thêm thất bại, vui lòng kiểm tra lại DAO!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi định dạng dữ liệu: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void showEditDialog() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên cần sửa trên bảng!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
        String currentMaNv = tableModel.getValueAt(selectedRow, 1).toString();
        String currentHoTen = tableModel.getValueAt(selectedRow, 2).toString();
        String currentGioiTinh = tableModel.getValueAt(selectedRow, 3).toString();
        String currentNgaySinh = tableModel.getValueAt(selectedRow, 4) != null ? tableModel.getValueAt(selectedRow, 4).toString() : "2000-01-01";
        String currentDienThoai = tableModel.getValueAt(selectedRow, 5) != null ? tableModel.getValueAt(selectedRow, 5).toString() : "";
        String currentEmail = tableModel.getValueAt(selectedRow, 6) != null ? tableModel.getValueAt(selectedRow, 6).toString() : "";
        String currentPbId = tableModel.getValueAt(selectedRow, 7).toString();

        NhanVienDAO daoCheck = new NhanVienDAO();
        List<NhanVien> list = daoCheck.getAllNhanVien();
        double currentLuongValue = 0;
        for(NhanVien nv : list) {
            if(nv.getId() == id) {
                currentLuongValue = nv.getLuongCoBan();
                break;
            }
        }

        JTextField txtMaNv = new JTextField(currentMaNv);
        JTextField txtHoTen = new JTextField(currentHoTen);
        JComboBox<String> cbGioiTinh = new JComboBox<>(new String[]{"Nam", "Nữ", "Khác"});
        cbGioiTinh.setSelectedItem(currentGioiTinh);
        JTextField txtNgaySinh = new JTextField(currentNgaySinh);
        JTextField txtDienThoai = new JTextField(currentDienThoai);
        JTextField txtEmail = new JTextField(currentEmail);
        JTextField txtPhongBanId = new JTextField(currentPbId);
        JTextField txtLuongCoBan = new JTextField(String.valueOf(currentLuongValue));

        Object[] message = {
                "Mã NV:", txtMaNv,
                "Họ Tên:", txtHoTen,
                "Giới Tính:", cbGioiTinh,
                "Ngày Sinh (YYYY-MM-DD):", txtNgaySinh,
                "Điện Thoại:", txtDienThoai,
                "Email:", txtEmail,
                "Phòng Ban ID:", txtPhongBanId,
                "Lương Cơ Bản:", txtLuongCoBan
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Sửa Thông Tin Nhân Viên", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                NhanVien nv = new NhanVien();
                nv.setId(id);
                nv.setMaNv(txtMaNv.getText().trim());
                nv.setHoTen(txtHoTen.getText().trim());
                nv.setGioiTinh(cbGioiTinh.getSelectedItem().toString());
                nv.setNgaySinh(Date.valueOf(txtNgaySinh.getText().trim()));
                nv.setDienThoai(txtDienThoai.getText().trim());
                nv.setEmail(txtEmail.getText().trim());
                nv.setPhongBanId(Integer.parseInt(txtPhongBanId.getText().trim()));
                nv.setLuongCoBan(Double.parseDouble(txtLuongCoBan.getText().trim()));

                NhanVienDAO dao = new NhanVienDAO();
                boolean success = dao.updateNhanVien(nv);

                if (success) {
                    JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
                    loadDataToTable();
                } else {
                    JOptionPane.showMessageDialog(this, "Cập nhật thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi định dạng dữ liệu: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void handleDelete() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên cần xóa trên bảng!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
        String hoTen = tableModel.getValueAt(selectedRow, 2).toString();

        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa nhân viên: " + hoTen + "?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            NhanVienDAO daoDel = new NhanVienDAO();
            boolean success = daoDel.deleteNhanVien(id);

            if (success) {
                JOptionPane.showMessageDialog(this, "Đã xóa nhân viên thành công!");
                loadDataToTable();
            } else {
                JOptionPane.showMessageDialog(this, "Xóa thất bại (có thể vướng khóa ngoại dữ liệu)!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}