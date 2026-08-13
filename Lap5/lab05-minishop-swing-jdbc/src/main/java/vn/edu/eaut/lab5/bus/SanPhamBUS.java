package vn.edu.eaut.lab5.bus;

import vn.edu.eaut.lab5.dal.SanPhamDAL;
import vn.edu.eaut.lab5.model.SanPham;

import java.sql.SQLException;
import java.util.List;

public class SanPhamBUS {
    private final SanPhamDAL sanPhamDao = new SanPhamDAL();

    public List<SanPham> findAll() throws SQLException {
        return sanPhamDao.getAll();
    }

    public List<SanPham> searchByName(String keyword) throws SQLException {
        return sanPhamDao.searchByName(keyword);
    }

    public boolean save(@org.jetbrains.annotations.NotNull SanPham sp) throws SQLException {
        if (sp.getMaSp() == 0) {
            return sanPhamDao.insert(sp);
        } else {
            return sanPhamDao.update(sp);
        }
    }

    public boolean delete(int maSp) throws SQLException {
        return sanPhamDao.delete(maSp);
    }
}