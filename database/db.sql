DROP database IF EXISTS data_book;
create database data_book;
use data_book;

CREATE TABLE Sach(
    ID varchar(8) PRIMARY KEY,
    Ten VARCHAR(255),
    HinhAnh VARCHAR(255),
    TacGia VARCHAR(255),
    NhaXuatBan VARCHAR(255),
    Gia INT,
    DaBan int,
    TonKho INT,
    TrongLuong decimal (4,1),
    KichThuoc varchar(255),
    GioiThieu TEXT
);

CREATE TABLE TaiKhoan (
    TenTaiKhoan VARCHAR(255) PRIMARY KEY,
    HoTen VARCHAR(255),
    SoDienThoai VARCHAR(15),
    Email VARCHAR(255),
    DiaChi VARCHAR(255),
    isAdmin bool,
    MatKhau VARCHAR(255)
);

CREATE TABLE DonHang (
    MaDonHang varchar(8) PRIMARY KEY,
    IDNguoiDat VARCHAR(255),
    ThoiGianDat datetime,
    TrangThai int,
    ThanhTien int,
    IDSachDau varchar(8),
    SoSanPham int,
	SoDienThoai VARCHAR(15),
    DiaChi VARCHAR(255),
    FOREIGN KEY (idSachDau) REFERENCES Sach(ID),
    FOREIGN KEY (IDNguoiDat) REFERENCES TaiKhoan(TenTaiKhoan)
);

CREATE TABLE ChiTietDonHang (
    MaDonHang varchar(8),
    IDSach varchar(8),
    SoLuong INT,
    FOREIGN KEY (MaDonHang) REFERENCES DonHang(MaDonHang),
    FOREIGN KEY (IDSach) REFERENCES Sach(ID)
);

INSERT INTO Sach (ID, Ten, HinhAnh, Gia, TonKho, TacGia, NhaXuatBan, GioiThieu, DaBan)
VALUES
('BLYVL195' ,'The Clean Coder: A Code of Conduct for Professional Programmers', '/img/product/CleanCode.jpg', 60000, 100, 'Tác giả 1', 'Nhà xuất bản 1', 'Giới thiệu cho cuốn sách 1', 10),
('BXFLU747' ,'Code Complete: A Practical Handbook of Software Construction, Second Edition', '/img/product/CodeComplete.jpg', 60000, 100, 'Tác giả 2', 'Nhà xuất bản 2', 'Giới thiệu cho cuốn sách 2', 0),

('BEVKS715' ,'Computer Networking: Principles, Protocols and Practice', '/img/product/ComputerNetworking.jpg', 60000, 100, 'Tác giả 3', 'Nhà xuất bản 3', 'Giới thiệu cho cuốn sách 3', 10),
('BHKKX073' ,'Network Warrior: Everything You Need to Know That Wasn''t on the CCNA Exam', '/img/product/NetworkWarrior.jpg', 60000, 100, 'Tác giả 4', 'Nhà xuất bản 4', 'Giới thiệu cho cuốn sách 4', 0),

('BPNOW492' ,'The Web Application Hacker''s Handbook: Finding and Exploiting Security Flaws', '/img/product/HackerHandbook.jpg', 60000, 100, 'Tác giả 5', 'Nhà xuất bản 5', 'Giới thiệu cho cuốn sách 5', 10),
('BGRMC730' ,'Hacking: The Art of Exploitation, 2nd Edition', '/img/product/Hacking.jpg', 60000, 100, 'Tác giả 6', 'Nhà xuất bản 6', 'Giới thiệu cho cuốn sách 6', 0),

('BGEHN704' ,'Data Science for Business: What You Need to Know about Data Mining and Data-Analytic Thinking', '/img/product/DataScience.jpg', 60000, 100, 'Tác giả 7', 'Nhà xuất bản 7', 'Giới thiệu cho cuốn sách 7', 10),
('BWNPW735' ,'Python for Data Analysis: Data Wrangling with Pandas, NumPy, and IPython', '/img/product/DataAnalysis.jpg', 60000, 100, 'Tác giả 8', 'Nhà xuất bản 8', 'Giới thiệu cho cuốn sách 8', 0),

('BHXGU988' ,'Artificial Intelligence: A Modern Approach, Global Edition', '/img/product/ArtificialIntelligence.jpg', 60000, 100, 'Tác giả 9', 'Nhà xuất bản 9', 'Giới thiệu cho cuốn sách 9', 10),
('BLSVL608' ,'Life 3.0: Being Human in the Age of Artificial Intelligence', '/img/product/Life3.jpg', 60000, 100, 'Tác giả 10', 'Nhà xuất bản 10', 'Giới thiệu cho cuốn sách 10', 0);

INSERT INTO TaiKhoan (TenTaiKhoan, isAdmin, MatKhau)
VALUES ('admin',  true, 'admin');
INSERT INTO TaiKhoan (TenTaiKhoan, HoTen, SoDienThoai, DiaChi, isAdmin, MatKhau)
VALUES
('user1', 'Tester1', '0123456789', '371 Nguyễn Kiệm, P.3, Q.Gò Vấp, TP.HCM', false, 'user1'),
('user2', 'Tester2', '0987654321', '371 Nguyễn Kiệm, P.3, Q.Gò Vấp, TP.HCM', false, 'user2');

INSERT INTO DonHang (MaDonHang, IDNguoiDat, ThoiGianDat, TrangThai, ThanhTien, IDSachDau, SoSanPham, SoDienThoai, DiaChi)
VALUES ('OCFEI004', 'user1', '2023-03-01', 0, 360000, 'BLYVL195', 3, '0123456789', '371 Nguyễn Kiệm, P.3, Q.Gò Vấp, TP.HCM');
INSERT INTO ChiTietDonHang (MaDonHang, IDSach, SoLuong) 
VALUES 
('OCFEI004',  'BLYVL195', 2),
('OCFEI004', 'BXFLU747' , 1),
('OCFEI004', 'BHXGU988', 3);

INSERT INTO DonHang (MaDonHang, IDNguoiDat, ThoiGianDat, TrangThai, ThanhTien, IDSachDau, SoSanPham, SoDienThoai, DiaChi)
VALUES ('ORCYN789', 'user1', '2023-03-02', 1, 900000, 'BXFLU747', 3, '0123456789', '371 Nguyễn Kiệm, P.3, Q.Gò Vấp, TP.HCM');
INSERT INTO ChiTietDonHang (MaDonHang, IDSach, SoLuong)
VALUES
('ORCYN789', 'BXFLU747' , 2),
('ORCYN789', 'BHXGU988' , 3),
('ORCYN789', 'BHKKX073' , 1);

INSERT INTO DonHang (MaDonHang, IDNguoiDat, ThoiGianDat, TrangThai, ThanhTien, IDSachDau, SoSanPham, SoDienThoai, DiaChi)
VALUES ('ORCYN729', 'user1', '2023-03-03', 3, 900000, 'BEVKS715', 3, '0123456789', '371 Nguyễn Kiệm, P.3, Q.Gò Vấp, TP.HCM');
INSERT INTO ChiTietDonHang (MaDonHang, IDSach, SoLuong)
VALUES
('ORCYN729', 'BEVKS715' , 2),
('ORCYN729', 'BHKKX073' , 3),
('ORCYN729', 'BPNOW492' , 1);

INSERT INTO DonHang (MaDonHang, IDNguoiDat, ThoiGianDat, TrangThai, ThanhTien, IDSachDau, SoSanPham, SoDienThoai, DiaChi)
VALUES ('OCFEI084', 'user1', '2023-03-04', 3, 360000, 'BHKKX073', 3, '0123456789', '371 Nguyễn Kiệm, P.3, Q.Gò Vấp, TP.HCM');
INSERT INTO ChiTietDonHang (MaDonHang, IDSach, SoLuong) 
VALUES 
('OCFEI084',  'BHKKX073', 2),
('OCFEI084', 'BPNOW492' , 1),
('OCFEI084', 'BGRMC730', 3);

INSERT INTO DonHang (MaDonHang, IDNguoiDat, ThoiGianDat, TrangThai, ThanhTien, IDSachDau, SoSanPham, SoDienThoai, DiaChi)
VALUES ('OCFEI584', 'user1', '2023-03-05', 0, 360000, 'BPNOW492', 3, '0123456789', '371 Nguyễn Kiệm, P.3, Q.Gò Vấp, TP.HCM');
INSERT INTO ChiTietDonHang (MaDonHang, IDSach, SoLuong) 
VALUES 
('OCFEI584',  'BPNOW492', 2),
('OCFEI584', 'BGRMC730' , 1),
('OCFEI584', 'BGEHN704', 3);

INSERT INTO DonHang (MaDonHang, IDNguoiDat, ThoiGianDat, TrangThai, ThanhTien, IDSachDau, SoSanPham, SoDienThoai, DiaChi)
VALUES ('OCFGI084', 'user1', '2023-03-06', 1, 360000, 'BGRMC730', 3, '0123456789', '371 Nguyễn Kiệm, P.3, Q.Gò Vấp, TP.HCM');
INSERT INTO ChiTietDonHang (MaDonHang, IDSach, SoLuong) 
VALUES 
('OCFGI084',  'BGRMC730', 2),
('OCFGI084', 'BGEHN704' , 1),
('OCFGI084', 'BWNPW735', 3);

INSERT INTO DonHang (MaDonHang, IDNguoiDat, ThoiGianDat, TrangThai, ThanhTien, IDSachDau, SoSanPham, SoDienThoai, DiaChi)
VALUES ('OCBEI084', 'user1', '2023-03-07', 10, 360000, 'BGEHN704', 3, '0123456789', '371 Nguyễn Kiệm, P.3, Q.Gò Vấp, TP.HCM');
INSERT INTO ChiTietDonHang (MaDonHang, IDSach, SoLuong) 
VALUES 
('OCBEI084',  'BGEHN704', 2),
('OCBEI084', 'BWNPW735' , 1),
('OCBEI084', 'BHXGU988', 3);