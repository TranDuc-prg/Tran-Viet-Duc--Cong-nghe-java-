-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th9 08, 2026 lúc 03:18 PM
-- Phiên bản máy phục vụ: 10.4.32-MariaDB
-- Phiên bản PHP: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `quan_ly_nhan_su`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `bang_luong`
--

CREATE TABLE `bang_luong` (
  `id` int(11) NOT NULL,
  `nhan_vien_id` int(11) NOT NULL,
  `thang` int(11) NOT NULL,
  `nam` int(11) NOT NULL,
  `luong_co_ban` decimal(10,2) DEFAULT NULL,
  `trang_thai` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `bang_luong`
--

INSERT INTO `bang_luong` (`id`, `nhan_vien_id`, `thang`, `nam`, `luong_co_ban`, `trang_thai`) VALUES
(1, 1, 9, 2025, NULL, 'Đã chốt'),
(2, 2, 9, 2025, NULL, 'Đã chốt'),
(3, 3, 9, 2025, NULL, 'Đã chốt'),
(4, 4, 9, 2025, NULL, 'Đã chốt'),
(5, 5, 9, 2025, NULL, 'Đã chốt'),
(6, 6, 9, 2025, NULL, 'Đã chốt'),
(7, 7, 9, 2025, NULL, 'Đã chốt'),
(8, 8, 9, 2025, NULL, 'Đã chốt'),
(9, 9, 9, 2025, NULL, 'Đã chốt'),
(10, 10, 9, 2025, NULL, 'Đã chốt'),
(11, 11, 9, 2025, NULL, 'Đã chốt'),
(12, 1, 9, 2026, NULL, 'Đã chốt'),
(13, 2, 9, 2026, NULL, 'Đã chốt'),
(14, 3, 9, 2026, NULL, 'Đã chốt'),
(15, 4, 9, 2026, NULL, 'Đã chốt'),
(16, 5, 9, 2026, NULL, 'Đã chốt'),
(17, 6, 9, 2026, NULL, 'Đã chốt'),
(18, 7, 9, 2026, NULL, 'Đã chốt'),
(19, 8, 9, 2026, NULL, 'Đã chốt'),
(20, 9, 9, 2026, NULL, 'Đã chốt'),
(21, 10, 9, 2026, NULL, 'Đã chốt'),
(22, 11, 9, 2026, NULL, 'Đã chốt');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `cham_cong`
--

CREATE TABLE `cham_cong` (
  `id` int(11) NOT NULL,
  `nhan_vien_id` int(11) NOT NULL,
  `ngay_gio` datetime NOT NULL,
  `trang_thai` varchar(50) NOT NULL COMMENT 'Ví dụ: Vào ca, Kết thúc ca, Đúng giờ, Đi muộn'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `cham_cong`
--

INSERT INTO `cham_cong` (`id`, `nhan_vien_id`, `ngay_gio`, `trang_thai`) VALUES
(1, 2, '2026-08-17 08:00:00', 'Vào ca'),
(2, 2, '2026-08-17 17:00:00', 'Kết thúc ca'),
(3, 3, '2026-08-17 08:05:20', 'Vào ca'),
(4, 3, '2026-08-17 17:10:15', 'Kết thúc ca'),
(5, 2, '2026-08-23 21:42:36', 'Đúng giờ'),
(6, 1, '2026-08-23 21:47:02', 'Đúng giờ');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `chuc_vu`
--

CREATE TABLE `chuc_vu` (
  `id` int(11) NOT NULL,
  `ma_chuc_vu` varchar(50) NOT NULL,
  `ten_chuc_vu` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `hop_dong`
--

CREATE TABLE `hop_dong` (
  `id` int(11) NOT NULL,
  `ma_hop_dong` varchar(50) NOT NULL,
  `nhan_vien_id` int(11) NOT NULL,
  `loai_hop_dong` varchar(100) NOT NULL,
  `ngay_bat_dau` date NOT NULL,
  `ngay_ket_thuc` date DEFAULT NULL,
  `muc_luong` decimal(10,2) NOT NULL,
  `noi_dung` text DEFAULT NULL,
  `trang_thai` varchar(50) DEFAULT 'Hiệu lực'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `khau_tru`
--

CREATE TABLE `khau_tru` (
  `id` int(11) NOT NULL,
  `nhan_vien_id` int(11) NOT NULL,
  `loai_khau_tru` varchar(100) NOT NULL,
  `so_tien` decimal(10,2) NOT NULL,
  `thang_ap_dung` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `nghi_phep`
--

CREATE TABLE `nghi_phep` (
  `id` int(11) NOT NULL,
  `nhan_vien_id` int(11) NOT NULL,
  `ngay_bat_dau` date NOT NULL,
  `ngay_ket_thuc` date NOT NULL,
  `so_ngay_nghi` int(11) NOT NULL,
  `ly_do` text NOT NULL,
  `trang_thai` varchar(50) DEFAULT 'Chờ duyệt'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `nhan_vien`
--

CREATE TABLE `nhan_vien` (
  `id` int(11) NOT NULL,
  `ma_nv` varchar(50) NOT NULL,
  `ho_ten` varchar(100) NOT NULL,
  `gioi_tinh` varchar(10) DEFAULT NULL,
  `ngay_sinh` date DEFAULT NULL,
  `dien_thoai` varchar(15) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `phong_ban_id` int(11) DEFAULT NULL,
  `luong_co_ban` decimal(10,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `nhan_vien`
--

INSERT INTO `nhan_vien` (`id`, `ma_nv`, `ho_ten`, `gioi_tinh`, `ngay_sinh`, `dien_thoai`, `email`, `phong_ban_id`, `luong_co_ban`) VALUES
(1, 'NV01', 'Nguyễn Văn Đức', 'Nam', '2001-05-12', '0912345601', 'ducnv@eaut.edu.vn', 1, 15000000.00),
(2, 'NV02', 'Trần Thị Mai', 'Nữ', '2000-08-20', '0912345602', 'maitt@eaut.edu.vn', 2, 10000000.00),
(3, 'NV03', 'Lê Hoàng Long', 'Nam', '1999-11-15', '0912345603', 'longlh@eaut.edu.vn', 3, 9500000.00),
(4, 'NV04', 'Phạm Minh Tuấn', 'Nam', '2002-02-10', '0912345604', 'tuanpm@eaut.edu.vn', 4, 12000000.00),
(5, 'NV05', 'Vũ Thị Hương', 'Nữ', '2001-12-05', '0912345605', 'huongvt@eaut.edu.vn', 5, 11000000.00),
(6, 'NV06', 'Đỗ Văn Nam', 'Nam', '2000-04-25', '0912345606', 'namdv@eaut.edu.vn', 6, 10500000.00),
(7, 'NV07', 'Bùi Thanh Hằng', 'Nữ', '2003-07-30', '0912345607', 'hangbt@eaut.edu.vn', 7, 11500000.00),
(8, 'NV08', 'Ngô Quang Huy', 'Nam', '1998-09-18', '0912345608', 'huynq@eaut.edu.vn', 8, 9000000.00),
(9, 'NV09', 'Dương Thị Lan', 'Nữ', '2001-03-22', '0912345609', 'landt@eaut.edu.vn', 9, 13000000.00),
(10, 'NV10', 'Hoàng Văn Hải', 'Nam', '2000-10-11', '0912345610', 'haihv@eaut.edu.vn', 10, 9800000.00),
(11, '20220111', 'saưq', 'Nam', '2000-01-01', '0366388104', 'duc@gmail.com', 1, 10000000.00);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `phong_ban`
--

CREATE TABLE `phong_ban` (
  `id` int(11) NOT NULL,
  `ten_phong_ban` varchar(100) NOT NULL,
  `ma_phong_ban` varchar(50) DEFAULT NULL,
  `mo_ta` text DEFAULT NULL,
  `trang_thai` varchar(50) DEFAULT 'Hoạt động'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `phong_ban`
--

INSERT INTO `phong_ban` (`id`, `ten_phong_ban`, `ma_phong_ban`, `mo_ta`, `trang_thai`) VALUES
(1, 'Phòng Giám Đốc', 'GD', 'Quản lý toàn bộ công ty', 'Hoạt động'),
(2, 'Phòng Kế Toán', 'KT', 'Quản lý tài chính, kế toán', 'Hoạt động'),
(3, 'Phòng Nhân Sự', 'NS', 'Quản lý tuyển dụng và nhân sự', 'Hoạt động'),
(4, 'Phòng IT', 'IT', 'Quản lý hệ thống mạng và phần mềm', 'Hoạt động'),
(5, 'Phòng Kinh Doanh', 'KD', 'Thực hiện kinh doanh, tìm kiếm khách hàng', 'Hoạt động'),
(6, 'Phòng Marketing', 'MKT', 'Quảng bá thương hiệu và sản phẩm', 'Hoạt động'),
(7, 'Phòng Kỹ Thuật', 'KTHT', 'Hỗ trợ kỹ thuật và vận hành', 'Hoạt động'),
(8, 'Phòng Hành Chính', 'HC', 'Công tác hành chính văn phòng', 'Hoạt động'),
(9, 'Phòng Dự Án', 'DA', 'Quản lý các dự án của công ty', 'Ngừng hoạt động'),
(10, 'Phòng Chăm Sóc Khách Hàng', 'CSKH', 'Chăm sóc và giải đáp thắc mắc khách hàng', 'Hoạt động'),
(12, 'ewg', 'dgs', 'ewg', 'Hoạt động');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `phu_cap`
--

CREATE TABLE `phu_cap` (
  `id` int(11) NOT NULL,
  `nhan_vien_id` int(11) NOT NULL,
  `ten_phu_cap` varchar(100) NOT NULL,
  `so_tien` decimal(10,2) NOT NULL,
  `thang_ap_dung` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `phu_cap_khau_tru`
--

CREATE TABLE `phu_cap_khau_tru` (
  `id` int(11) NOT NULL,
  `ma_nhan_vien` varchar(50) NOT NULL,
  `loai` varchar(50) NOT NULL,
  `ten_khoan` varchar(100) NOT NULL,
  `so_tien` decimal(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `phu_cap_khau_tru`
--

INSERT INTO `phu_cap_khau_tru` (`id`, `ma_nhan_vien`, `loai`, `ten_khoan`, `so_tien`) VALUES
(1, 'NV01', 'Phụ cấp', 'Phụ cấp trách nhiệm', 1500000.00),
(2, 'NV02', 'Phụ cấp', 'Phụ cấp xăng xe', 500000.00),
(3, 'NV03', 'Khấu trừ', 'Khấu trừ đi muộn', 200000.00),
(4, 'NV04', 'Phụ cấp', 'Phụ cấp ăn trưa', 730000.00),
(5, 'NV05', 'Khấu trừ', 'Ứng lương tháng', 1000000.00),
(6, 'NV06', 'Phụ cấp', 'Phụ cấp điện thoại', 400000.00),
(7, 'NV07', 'Phụ cấp', 'Thưởng chuyên cần', 500000.00),
(8, 'NV08', 'Khấu trừ', 'Phạt vi phạm nội quy', 300000.00),
(9, 'NV09', 'Phụ cấp', 'Phụ cấp độc hại', 1200000.00),
(10, 'NV10', 'Phụ cấp', 'Đóng quỹ công đoàn', 100000.00),
(12, 'NV11', 'Khấu trừ', 'Đóng quỹ công đoàn', 100000.00);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `tai_khoan`
--

CREATE TABLE `tai_khoan` (
  `id` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `role` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `tai_khoan`
--

INSERT INTO `tai_khoan` (`id`, `username`, `password`, `role`) VALUES
(1, 'admin', '123456', 'ADMIN'),
(2, 'ketoan01', '123456', 'KETOAN'),
(3, 'nhansu01', '123456', 'NHANSU'),
(4, 'it01', '123456', 'USER'),
(5, 'kd01', '123456', 'USER'),
(6, 'mkt01', '123456', 'USER'),
(7, 'kythuat01', '123456', 'USER'),
(8, 'hc01', '123456', 'USER'),
(9, 'duan01', '123456', 'USER'),
(10, 'cskh01', '123456', 'USER'),
(11, '', '', 'ADMIN');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `tang_ca`
--

CREATE TABLE `tang_ca` (
  `id` int(11) NOT NULL,
  `nhan_vien_id` int(11) NOT NULL,
  `ngay_tang_ca` date NOT NULL,
  `so_gio` decimal(5,2) NOT NULL,
  `he_so` decimal(3,2) DEFAULT 1.50,
  `trang_thai` varchar(50) DEFAULT 'Chờ duyệt'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `bang_luong`
--
ALTER TABLE `bang_luong`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_nhan_vien_luong` (`nhan_vien_id`);

--
-- Chỉ mục cho bảng `cham_cong`
--
ALTER TABLE `cham_cong`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `chuc_vu`
--
ALTER TABLE `chuc_vu`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `hop_dong`
--
ALTER TABLE `hop_dong`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `ma_hop_dong` (`ma_hop_dong`),
  ADD KEY `fk_nv_hopdong` (`nhan_vien_id`);

--
-- Chỉ mục cho bảng `khau_tru`
--
ALTER TABLE `khau_tru`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_nv_khautru` (`nhan_vien_id`);

--
-- Chỉ mục cho bảng `nghi_phep`
--
ALTER TABLE `nghi_phep`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_nv_nghiphep` (`nhan_vien_id`);

--
-- Chỉ mục cho bảng `nhan_vien`
--
ALTER TABLE `nhan_vien`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `ma_nv` (`ma_nv`),
  ADD KEY `phong_ban_id` (`phong_ban_id`);

--
-- Chỉ mục cho bảng `phong_ban`
--
ALTER TABLE `phong_ban`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `phu_cap`
--
ALTER TABLE `phu_cap`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_nv_phucap` (`nhan_vien_id`);

--
-- Chỉ mục cho bảng `phu_cap_khau_tru`
--
ALTER TABLE `phu_cap_khau_tru`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `tai_khoan`
--
ALTER TABLE `tai_khoan`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`);

--
-- Chỉ mục cho bảng `tang_ca`
--
ALTER TABLE `tang_ca`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_nv_tangca` (`nhan_vien_id`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `bang_luong`
--
ALTER TABLE `bang_luong`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

--
-- AUTO_INCREMENT cho bảng `cham_cong`
--
ALTER TABLE `cham_cong`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT cho bảng `chuc_vu`
--
ALTER TABLE `chuc_vu`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `hop_dong`
--
ALTER TABLE `hop_dong`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `khau_tru`
--
ALTER TABLE `khau_tru`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `nghi_phep`
--
ALTER TABLE `nghi_phep`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `nhan_vien`
--
ALTER TABLE `nhan_vien`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT cho bảng `phong_ban`
--
ALTER TABLE `phong_ban`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT cho bảng `phu_cap`
--
ALTER TABLE `phu_cap`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `phu_cap_khau_tru`
--
ALTER TABLE `phu_cap_khau_tru`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT cho bảng `tai_khoan`
--
ALTER TABLE `tai_khoan`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT cho bảng `tang_ca`
--
ALTER TABLE `tang_ca`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `bang_luong`
--
ALTER TABLE `bang_luong`
  ADD CONSTRAINT `fk_nhan_vien_luong` FOREIGN KEY (`nhan_vien_id`) REFERENCES `nhan_vien` (`id`);

--
-- Các ràng buộc cho bảng `hop_dong`
--
ALTER TABLE `hop_dong`
  ADD CONSTRAINT `fk_nv_hopdong` FOREIGN KEY (`nhan_vien_id`) REFERENCES `nhan_vien` (`id`);

--
-- Các ràng buộc cho bảng `khau_tru`
--
ALTER TABLE `khau_tru`
  ADD CONSTRAINT `fk_nv_khautru` FOREIGN KEY (`nhan_vien_id`) REFERENCES `nhan_vien` (`id`);

--
-- Các ràng buộc cho bảng `nghi_phep`
--
ALTER TABLE `nghi_phep`
  ADD CONSTRAINT `fk_nv_nghiphep` FOREIGN KEY (`nhan_vien_id`) REFERENCES `nhan_vien` (`id`);

--
-- Các ràng buộc cho bảng `nhan_vien`
--
ALTER TABLE `nhan_vien`
  ADD CONSTRAINT `nhan_vien_ibfk_1` FOREIGN KEY (`phong_ban_id`) REFERENCES `phong_ban` (`id`);

--
-- Các ràng buộc cho bảng `phu_cap`
--
ALTER TABLE `phu_cap`
  ADD CONSTRAINT `fk_nv_phucap` FOREIGN KEY (`nhan_vien_id`) REFERENCES `nhan_vien` (`id`);

--
-- Các ràng buộc cho bảng `tang_ca`
--
ALTER TABLE `tang_ca`
  ADD CONSTRAINT `fk_nv_tangca` FOREIGN KEY (`nhan_vien_id`) REFERENCES `nhan_vien` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
