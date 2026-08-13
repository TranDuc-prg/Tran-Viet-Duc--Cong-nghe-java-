package vn.edu.eaut.lab5.bus;

import vn.edu.eaut.lab5.dal.KhachHangDAL;
import vn.edu.eaut.lab5.model.KhachHang;
import java.sql.SQLException;
import java.util.List;

public class KhachHangBUS {
    private final KhachHangDAL khachHangDao = new KhachHangDAL();

    public List<KhachHang> findAll() throws SQLException {
        return khachHangDao.getAll();
    }

    public boolean save(KhachHang kh) throws SQLException {
        validate(kh);
        if (kh.getMaKh() == 0) {
            return khachHangDao.insert(kh);
        }
        return khachHangDao.update(kh);
    }

    public boolean delete(int maKh) throws SQLException {
        if (maKh <= 0) {
            throw new IllegalArgumentException("Mã khách hàng không hợp lệ");
        }
        return khachHangDao.delete(maKh);
    }

    private void validate(KhachHang kh) {
        if (kh.getTenKh() == null || kh.getTenKh().trim().isEmpty()) {
            throw new IllegalArgumentException("Tên khách hàng không được rỗng");
        }
        if (kh.getSdt() == null || !kh.getSdt().matches("\\d{1,10}")) {
            throw new IllegalArgumentException("Số điện thoại chỉ gồm số và tối đa 10 ký tự");
        }
    }
}