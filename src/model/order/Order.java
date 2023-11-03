package model.order;

import constant.OrderStatus;
import model.Entity;
import model.product.Product;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class Order extends Entity<Long>implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long userId;
    private String name;
    private String phoneNumber;
    private String address;
    private double total;
    private OrderStatus orderStatus;
    private Map<Product,Integer> ordersDetail;
    private LocalDateTime orderAt;
    private LocalDateTime deliverAt;


    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
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




    public LocalDateTime getOrderAt() {
        return orderAt;
    }

    public void setOrderAt(LocalDateTime orderAt) {
        this.orderAt = orderAt;
    }

    public LocalDateTime getDeliverAt() {
        return deliverAt;
    }

    public void setDeliverAt(LocalDateTime deliverAt) {
        this.deliverAt = deliverAt;
    }

    public Map<Product, Integer> getOrdersDetail() {
        return ordersDetail;
    }

    public void setOrdersDetail(Map<Product, Integer> ordersDetail) {
        this.ordersDetail = ordersDetail;
    }
}
