package com.nhom1.bookstore.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.nhom1.bookstore.entity.Order;
import com.nhom1.bookstore.entity.OrderDetail;
import com.nhom1.bookstore.entity.OrderDetail.OrderItem;
import com.nhom1.bookstore.services.ConverterCurrency;

@Repository
public class OrderDAOImpl implements OrderDAO {
    private Connection conn;

    public OrderDAOImpl() {
        this.conn = JDBC.getConnection();
        if (conn != null) {
            System.out.println("Order connect success");
        }
    }

    @Override
    public void editStatusOrder(String currentID, int newStatus) {
        String sql = "UPDATE DonHang SET TrangThai = ? WHERE MaDonHang = ?;";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, newStatus);
            statement.setString(2, currentID);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Order getOrder(String id) {
        String sql = "SELECT * FROM DonHang where MaDonHang = ?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String maDonHang = resultSet.getString("MaDonHang");
                    String idNguoiDat = resultSet.getString("IDNguoiDat");
                    Date thoiGianDat = resultSet.getTimestamp("ThoiGianDat");
                    int trangThai = resultSet.getInt("TrangThai");
                    int thanhTienRaw = resultSet.getInt("ThanhTien");
                    String thanhTien = ConverterCurrency.numberToCurrency(thanhTienRaw);
                    String idSachDau = resultSet.getString("IDSachDau");
                    int soSanPham = resultSet.getInt("SoSanPham");
                    String soDienThoai = resultSet.getString("SoDienThoai");
                    String diaChi = resultSet.getString("DiaChi");
                    return new Order(maDonHang, idNguoiDat, thoiGianDat, trangThai, thanhTien, idSachDau, soSanPham, soDienThoai, diaChi);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public OrderDetail getOrderDetail(String id) {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrderID(id);
        orderDetail.setOrderItemList(new ArrayList<>());
        String sql = "SELECT * FROM ChiTietDonHang where MaDonHang = ?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String idSach = resultSet.getString("IDSach");
                    int soLuong = resultSet.getInt("SoLuong");
                    OrderDetail.OrderItem bookInOrder = orderDetail.new OrderItem(idSach, soLuong);
                    orderDetail.getOrderItemList().add(bookInOrder);
                }
                return orderDetail;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Order> getOrderList() {
        List<Order> orderList = new ArrayList<>();
        String sql = "SELECT * FROM DonHang";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String maDonHang = resultSet.getString("MaDonHang");
                    String idNguoiDat = resultSet.getString("IDNguoiDat");
                    Date thoiGianDat = resultSet.getTimestamp("ThoiGianDat");
                    int trangThai = resultSet.getInt("TrangThai");
                    int thanhTienRaw = resultSet.getInt("ThanhTien");
                    String thanhTien = ConverterCurrency.numberToCurrency(thanhTienRaw);
                    String idSachDau = resultSet.getString("IDSachDau");
                    int soSanPham = resultSet.getInt("SoSanPham");
                    String soDienThoai = resultSet.getString("SoDienThoai");
                    String diaChi = resultSet.getString("DiaChi");
                    orderList.add(
                        new Order(maDonHang, idNguoiDat, thoiGianDat, trangThai, thanhTien, idSachDau, soSanPham, soDienThoai, diaChi));
                    }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderList;
    }

    @Override
    public List<Order> search(String tuKhoa) {
        List<Order> result = searchDetails(tuKhoa);
        return result;
    }

    private List<Order> searchGeneral(String tuKhoa) {
        List<Order> result = new ArrayList<>();
        
        String sql = 
        "SELECT * FROM DonHang " +
        "WHERE " + 
            "LOWER(MaDonHang) LIKE LOWER(?) OR " +
            "LOWER(IDNguoiDat) LIKE LOWER(?) OR " +
            "LOWER(ThoiGianDat) LIKE LOWER(?) OR " +
            "LOWER(IDSachDau) LIKE LOWER(?) OR " +
            "LOWER(SoSanPham) LIKE LOWER(?) OR " +
            "LOWER(SoDienThoai) LIKE LOWER(?) OR " +
            "LOWER(DiaChi) LIKE LOWER(?)";

        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, "%" + tuKhoa + "%");
            preparedStatement.setString(2, "%" + tuKhoa + "%");
            preparedStatement.setString(3, "%" + tuKhoa + "%");
            preparedStatement.setString(4, "%" + tuKhoa + "%");
            preparedStatement.setString(5, "%" + tuKhoa + "%");
            preparedStatement.setString(6, "%" + tuKhoa + "%");
            preparedStatement.setString(7, "%" + tuKhoa + "%");

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String maDonHang = resultSet.getString("MaDonHang");
                    String idNguoiDat = resultSet.getString("IDNguoiDat");
                    Date thoiGianDat = resultSet.getTimestamp("ThoiGianDat");
                    int trangThai = resultSet.getInt("TrangThai");

                    int thanhTienRaw = resultSet.getInt("ThanhTien");
                    String thanhTien = ConverterCurrency.numberToCurrency(thanhTienRaw);

                    String idSachDau = resultSet.getString("IDSachDau");
                    int soSanPham = resultSet.getInt("SoSanPham");
                    String soDienThoai = resultSet.getString("SoDienThoai");
                    String diaChi = resultSet.getString("DiaChi");
                    result.add(
                            new Order(maDonHang, idNguoiDat, thoiGianDat, trangThai, thanhTien, idSachDau, soSanPham, soDienThoai, diaChi));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    private List<Order> searchDetails(String tuKhoa) {
        List<Order> result = searchGeneral(tuKhoa);

        String sql = 
        "SELECT * FROM ChiTietDonHang " +
        "WHERE " + 
            "LOWER(MaDonHang) LIKE LOWER(?) OR " +
            "LOWER(IDSach) LIKE LOWER(?)";

        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, "%" + tuKhoa + "%");
            preparedStatement.setString(2, "%" + tuKhoa + "%");

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String maDonHang = resultSet.getString("MaDonHang");
                    Boolean isMatch = false;
                    for(Order order : result) {
                        if(order.getOrderID() == maDonHang) {
                            isMatch = true;
                            break;
                        }
                    }
                    if(!isMatch) {
                        result.add(getOrder(maDonHang));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return result;
    }

    @Override
    public void createOrder(Order newOrder) {
        String sql = "INSERT INTO DonHang (MaDonHang, IDNguoiDat, ThoiGianDat, TrangThai, ThanhTien, IDSachDau, SoSanPham, SoDienThoai, DiaChi) "
                +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, newOrder.getOrderID());
            statement.setString(2, newOrder.getUserID());
            statement.setObject(3, newOrder.getOrderTime());
            statement.setInt(4, newOrder.convertStatusString(newOrder.getOrderStatus()));

            int thanhTien = ConverterCurrency.currencyToNumber(newOrder.getOrderPrice());
            statement.setInt(5, thanhTien);
            statement.setString(6, newOrder.getOrderFirstBookID());
            statement.setInt(7, newOrder.getOrderItemQuantity());
            statement.setString(8, newOrder.getOrderPhone());
            statement.setString(9, newOrder.getOrderAddress());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createOrderDetail(OrderDetail newOrderDetail) {
        String sql = "INSERT INTO chitietdonhang (MaDonHang, IDSach, SoLuong) VALUES (?, ?, ?)";
        List<OrderDetail.OrderItem> bookList = newOrderDetail.getOrderItemList();

        for (OrderItem bookInOrder : bookList) {
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setString(1, newOrderDetail.getOrderID());

                statement.setString(2, bookInOrder.getBookID());
                statement.setInt(3, bookInOrder.getQuantity());
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void deleteOrder(String id) {
        String sql = "Delete from ChiTietDonHang WHERE MaDonHang  = ?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, id);

            statement.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }

        sql = "Delete from DonHang WHERE MaDonHang = ?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, id);

            statement.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Order> getPOrderList(String userid) {
        List<Order> result = new ArrayList<>();
        
        String sql = "SELECT * FROM DonHang WHERE LOWER(IDNguoiDat) = LOWER(?)";

        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, userid);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String maDonHang = resultSet.getString("MaDonHang");
                    String idNguoiDat = resultSet.getString("IDNguoiDat");
                    Date thoiGianDat = resultSet.getTimestamp("ThoiGianDat");
                    int trangThai = resultSet.getInt("TrangThai");

                    int thanhTienRaw = resultSet.getInt("ThanhTien");
                    String thanhTien = ConverterCurrency.numberToCurrency(thanhTienRaw);

                    String idSachDau = resultSet.getString("IDSachDau");
                    int soSanPham = resultSet.getInt("SoSanPham");
                    String soDienThoai = resultSet.getString("SoDienThoai");
                    String diaChi = resultSet.getString("DiaChi");
                    result.add(
                            new Order(maDonHang, idNguoiDat, thoiGianDat, trangThai, thanhTien, idSachDau, soSanPham, soDienThoai, diaChi));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }
}