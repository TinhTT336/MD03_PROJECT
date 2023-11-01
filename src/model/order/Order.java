package model.order;

import model.Entity;
import model.orderDetail.OrderDetail;

import java.util.List;

public class Order extends Entity<Long> {
    private int userId;
    private String name;
    private String phoneNumber;
    private String address;
    private double total;
    private List<OrderDetail> orderDetail;
    private String orderAt;
    private String deliverAt;

    public Order() {
    }

    public Order(int userId, String name, String phoneNumber, String address, double total, List<OrderDetail> orderDetail, String orderAt, String deliverAt) {
        this.userId = userId;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.total = total;
        this.orderDetail = orderDetail;
        this.orderAt = orderAt;
        this.deliverAt = deliverAt;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public List<OrderDetail> getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(List<OrderDetail> orderDetail) {
        this.orderDetail = orderDetail;
    }

    public String getOrderAt() {
        return orderAt;
    }

    public void setOrderAt(String orderAt) {
        this.orderAt = orderAt;
    }

    public String getDeliverAt() {
        return deliverAt;
    }

    public void setDeliverAt(String deliverAt) {
        this.deliverAt = deliverAt;
    }
}
