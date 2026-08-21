-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th8 21, 2026 lúc 09:48 AM
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
-- Cơ sở dữ liệu: `lab09_jpa`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `diem`
--

CREATE TABLE `diem` (
  `id` int(11) NOT NULL,
  `sinh_vien_id` int(11) DEFAULT NULL,
  `mon_hoc_id` int(11) DEFAULT NULL,
  `diem_chuyen_can` double DEFAULT 0,
  `diem_thi` double DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `lop_hoc`
--

CREATE TABLE `lop_hoc` (
  `id` int(11) NOT NULL,
  `ma_lop` varchar(20) NOT NULL,
  `ten_lop` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `lop_hoc`
--

INSERT INTO `lop_hoc` (`id`, `ma_lop`, `ten_lop`) VALUES
(1, 'IT01', 'Công Nghệ Thông Tin 1'),
(2, 'IT02', 'Công Nghệ Thông Tin 2');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `mon_hoc`
--

CREATE TABLE `mon_hoc` (
  `id` int(11) NOT NULL,
  `ma_mon` varchar(20) NOT NULL,
  `ten_mon` varchar(100) NOT NULL,
  `so_tin_chi` int(11) DEFAULT 3
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `mon_hoc`
--

INSERT INTO `mon_hoc` (`id`, `ma_mon`, `ten_mon`, `so_tin_chi`) VALUES
(1, 'MH01', 'Lập Trình Java Web', 3),
(2, 'MH02', 'Cơ Sở Dữ Liệu', 3);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `sach`
--

CREATE TABLE `sach` (
  `id` int(11) NOT NULL,
  `gia` double DEFAULT NULL,
  `tenSach` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `san_pham`
--

CREATE TABLE `san_pham` (
  `id` int(11) NOT NULL,
  `soLuong` int(11) DEFAULT NULL,
  `tenSp` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `sinh_vien`
--

CREATE TABLE `sinh_vien` (
  `id` int(11) NOT NULL,
  `ma_sv` varchar(20) NOT NULL,
  `ho_ten` varchar(100) NOT NULL,
  `email` varchar(100) DEFAULT NULL,
  `so_dien_thoai` varchar(15) DEFAULT NULL,
  `lop_hoc_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `sinh_vien`
--

INSERT INTO `sinh_vien` (`id`, `ma_sv`, `ho_ten`, `email`, `so_dien_thoai`, `lop_hoc_id`) VALUES
(1, 'SV001', 'Nguyễn Văn A', 'anguyen@gmail.com', '0912345678', 1),
(2, 'SV002', 'Trần Thị B', 'btran@gmail.com', '0987654321', 1),
(3, 'SV003', 'Lê Văn C', 'cle@gmail.com', '0901122334', 2),
(4, '20220111', 'Trần Việt Đức', 'tduc45310@gmail.com', '0366388105', NULL);

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `diem`
--
ALTER TABLE `diem`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_diem_sinhvien` (`sinh_vien_id`),
  ADD KEY `fk_diem_monhoc` (`mon_hoc_id`);

--
-- Chỉ mục cho bảng `lop_hoc`
--
ALTER TABLE `lop_hoc`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `ma_lop` (`ma_lop`);

--
-- Chỉ mục cho bảng `mon_hoc`
--
ALTER TABLE `mon_hoc`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `ma_mon` (`ma_mon`);

--
-- Chỉ mục cho bảng `sach`
--
ALTER TABLE `sach`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `san_pham`
--
ALTER TABLE `san_pham`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `sinh_vien`
--
ALTER TABLE `sinh_vien`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `ma_sv` (`ma_sv`),
  ADD KEY `fk_sinhvien_lophoc` (`lop_hoc_id`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `diem`
--
ALTER TABLE `diem`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `lop_hoc`
--
ALTER TABLE `lop_hoc`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT cho bảng `mon_hoc`
--
ALTER TABLE `mon_hoc`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT cho bảng `sach`
--
ALTER TABLE `sach`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `san_pham`
--
ALTER TABLE `san_pham`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `sinh_vien`
--
ALTER TABLE `sinh_vien`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `diem`
--
ALTER TABLE `diem`
  ADD CONSTRAINT `fk_diem_monhoc` FOREIGN KEY (`mon_hoc_id`) REFERENCES `mon_hoc` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `fk_diem_sinhvien` FOREIGN KEY (`sinh_vien_id`) REFERENCES `sinh_vien` (`id`) ON DELETE CASCADE;

--
-- Các ràng buộc cho bảng `sinh_vien`
--
ALTER TABLE `sinh_vien`
  ADD CONSTRAINT `fk_sinhvien_lophoc` FOREIGN KEY (`lop_hoc_id`) REFERENCES `lop_hoc` (`id`) ON DELETE SET NULL;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
