package vn.edu.eaut.lab5.bus;

import vn.edu.eaut.lab5.dal.ThongKeDAL;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class ThongKeBUS {
    private final ThongKeDAL thongKeDao = new ThongKeDAL();

    public BigDecimal tinhDoanhThu(LocalDate tuNgay, LocalDate denNgay) {
        try {
            return thongKeDao.tinhDoanhThu(tuNgay, denNgay);
        } catch (Exception e) {
            e.printStackTrace();
            return BigDecimal.ZERO;
        }
    }

    public Map<String, Object> getHoaDonCaoNhat() {
        try {
            return thongKeDao.getHoaDonCaoNhat();
        } catch (Exception e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }

    public Map<String, Object> getSanPhamBanChayNhat() {
        try {
            return thongKeDao.getSanPhamBanChayNhat();
        } catch (Exception e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }
}