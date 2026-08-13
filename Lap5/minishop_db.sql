-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th8 12, 2026 lúc 10:29 AM
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
-- Cơ sở dữ liệu: `minishop_db`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `chi_tiet_hoa_don`
--

CREATE TABLE `chi_tiet_hoa_don` (
  `ma_hd` int(11) NOT NULL,
  `ma_sp` int(11) NOT NULL,
  `so_luong` int(11) NOT NULL,
  `don_gia` decimal(12,2) NOT NULL,
  `thanh_tien` decimal(12,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `chi_tiet_hoa_don`
--

INSERT INTO `chi_tiet_hoa_don` (`ma_hd`, `ma_sp`, `so_luong`, `don_gia`, `thanh_tien`) VALUES
(1, 5, 1, 120000.00, 120000.00);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `hoa_don`
--

CREATE TABLE `hoa_don` (
  `ma_hd` int(11) NOT NULL,
  `ngay_lap` date NOT NULL,
  `ma_kh` int(11) NOT NULL,
  `tong_tien` decimal(12,2) DEFAULT 0.00
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `hoa_don`
--

INSERT INTO `hoa_don` (`ma_hd`, `ngay_lap`, `ma_kh`, `tong_tien`) VALUES
(1, '2026-08-12', 3, 120000.00);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `khach_hang`
--

CREATE TABLE `khach_hang` (
  `ma_kh` int(11) NOT NULL,
  `ten_kh` varchar(100) NOT NULL,
  `dien_thoai` varchar(10) DEFAULT NULL,
  `dia_chi` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `khach_hang`
--

INSERT INTO `khach_hang` (`ma_kh`, `ten_kh`, `dien_thoai`, `dia_chi`) VALUES
(1, 'Nguyen Van An', '0912345678', 'Ha Noi'),
(2, 'Tran Thi Binh', '0987654321', 'Bac Ninh'),
(3, 'Le Van Cuong', '0901111222', 'Hai Duong');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `san_pham`
--

CREATE TABLE `san_pham` (
  `ma_sp` int(11) NOT NULL,
  `ten_sp` varchar(100) NOT NULL,
  `don_gia` decimal(12,2) NOT NULL,
  `so_luong` int(11) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `san_pham`
--

INSERT INTO `san_pham` (`ma_sp`, `ten_sp`, `don_gia`, `so_luong`) VALUES
(1, 'Ban phim Logitech K120', 180000.00, 50),
(2, 'Chuot khong day Rapoo', 220000.00, 40),
(3, 'USB Kingston 32GB', 150000.00, 100),
(4, 'Tai nghe Sony Basic', 350000.00, 30),
(5, 'Áo thun nam basic', 120000.00, 49),
(6, 'Quần jean nam slimfit', 350000.00, 30),
(7, 'Áo sơ mi công sở', 250000.00, 40),
(8, 'Áo khoác gió thể thao', 450000.00, 25),
(9, 'Quần đùi mặc nhà', 60000.00, 100),
(10, 'Giày sneaker thể thao', 550000.00, 20),
(11, 'Dép quai ngang nam nữ', 80000.00, 70),
(12, 'Túi xách da nữ', 400000.00, 15),
(13, 'Balo laptop chống thấm', 320000.00, 35),
(14, 'Ví da nam cầm tay', 150000.00, 45),
(15, 'Mũ lưỡi trai thời trang', 70000.00, 60),
(16, 'Kính mát chống UV', 130000.00, 40),
(17, 'Thắt lưng da nam', 180000.00, 50),
(18, 'Vớ/Tất thể thao (Đôi)', 15000.00, 200),
(19, 'Đồng hồ đeo tay quartz', 650000.00, 10),
(20, 'Sạc dự phòng 10000mAh', 280000.00, 30),
(21, 'Tai nghe Bluetooth TWS', 350000.00, 25),
(22, 'Cáp sạc Type-C nhanh', 50000.00, 90),
(23, 'Củ sạc nhanh 20W', 120000.00, 50),
(24, 'Giá đỡ điện thoại bàn', 45000.00, 80),
(25, 'Chuột không dâyร Logitech', 220000.00, 40),
(26, 'Bàn phím cơ gaming', 850000.00, 20),
(27, 'Lót chuột cỡ lớn', 60000.00, 60),
(28, 'Quạt mini cầm tay', 90000.00, 45),
(29, 'Đèn bàn học LED', 180000.00, 25),
(30, 'Bình giữ nhiệt inox', 110000.00, 50),
(31, 'Ly giữ nhiệt kèm ống hút', 140000.00, 35),
(32, 'Hộp đựng cơm văn phòng', 95000.00, 40),
(33, 'Túi giữ nhiệt đựng hộp cơm', 50000.00, 60),
(34, 'Gối cao su non văn phòng', 160000.00, 30),
(35, 'Thảm tập yoga đệm dày', 200000.00, 25),
(36, 'Con lăn tập bụng', 150000.00, 20),
(37, 'Dây nhảy thể dục đếm số', 70000.00, 50),
(38, 'Bóng tập yoga', 220000.00, 15),
(39, 'Găng tay tập gym', 80000.00, 40),
(40, 'Sữa rửa mặt nam nữ', 110000.00, 50),
(41, 'Kem chống nắng dưỡng da', 180000.00, 40),
(42, 'Son dưỡng môi', 60000.00, 70),
(43, 'Xịt thơm quần áo', 90000.00, 60),
(44, 'Nước hoa mini 20ml', 150000.00, 35),
(45, 'Khăn giấy ướt (Gói)', 15000.00, 150),
(46, 'Dầu gội khô tiện lợi', 120000.00, 30),
(47, 'Sữa tắm hương nước hoa', 130000.00, 45),
(48, 'Bàn chải đánh răng điện', 250000.00, 20),
(49, 'Kem đánh răng thảo dược', 45000.00, 100),
(50, 'Nước súc miệng diệt khuẩn', 75000.00, 60),
(51, 'Giấy vệ sinh cuộn lớn', 35000.00, 90),
(52, 'Nước rửa tay khô', 40000.00, 80),
(53, 'Khẩu trang kháng khuẩn (Hộp)', 30000.00, 200),
(54, 'Bút bi thiên long (Cây)', 5000.00, 500);

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `chi_tiet_hoa_don`
--
ALTER TABLE `chi_tiet_hoa_don`
  ADD PRIMARY KEY (`ma_hd`,`ma_sp`),
  ADD KEY `ma_sp` (`ma_sp`);

--
-- Chỉ mục cho bảng `hoa_don`
--
ALTER TABLE `hoa_don`
  ADD PRIMARY KEY (`ma_hd`),
  ADD KEY `ma_kh` (`ma_kh`);

--
-- Chỉ mục cho bảng `khach_hang`
--
ALTER TABLE `khach_hang`
  ADD PRIMARY KEY (`ma_kh`);

--
-- Chỉ mục cho bảng `san_pham`
--
ALTER TABLE `san_pham`
  ADD PRIMARY KEY (`ma_sp`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `hoa_don`
--
ALTER TABLE `hoa_don`
  MODIFY `ma_hd` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT cho bảng `khach_hang`
--
ALTER TABLE `khach_hang`
  MODIFY `ma_kh` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT cho bảng `san_pham`
--
ALTER TABLE `san_pham`
  MODIFY `ma_sp` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=55;

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `chi_tiet_hoa_don`
--
ALTER TABLE `chi_tiet_hoa_don`
  ADD CONSTRAINT `chi_tiet_hoa_don_ibfk_1` FOREIGN KEY (`ma_hd`) REFERENCES `hoa_don` (`ma_hd`),
  ADD CONSTRAINT `chi_tiet_hoa_don_ibfk_2` FOREIGN KEY (`ma_sp`) REFERENCES `san_pham` (`ma_sp`);

--
-- Các ràng buộc cho bảng `hoa_don`
--
ALTER TABLE `hoa_don`
  ADD CONSTRAINT `hoa_don_ibfk_1` FOREIGN KEY (`ma_kh`) REFERENCES `khach_hang` (`ma_kh`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
