package vn.edu.eaut.lab5.bus;

import vn.edu.eaut.lab5.dal.HoaDonDAL;
import vn.edu.eaut.lab5.model.ChiTietHoaDon;
import java.sql.SQLException;
import java.util.List;

public class HoaDonBUS {
    private final HoaDonDAL hoaDonDao = new HoaDonDAL();

    public int insertHoaDon(int maKh, List<ChiTietHoaDon> chiTietList) throws SQLException {
        if (maKh <= 0) {
            throw new IllegalArgumentException("Vui lòng chọn khách hàng hợp lệ");
        }
        if (chiTietList == null || chiTietList.isEmpty()) {
            throw new IllegalArgumentException("Giỏ hàng không được trống");
        }
        return hoaDonDao.insertHoaDon(maKh, chiTietList);
    }
}