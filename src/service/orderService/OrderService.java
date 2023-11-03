package service.orderService;

import constant.FileName;
import model.order.Order;
import model.user.User;
import service.IService;
import service.Service;

import java.io.File;
import java.util.List;

public class OrderService implements IService<Order,Long> {
    private Service<Order,Long>orderService;
    private Service<User,Long>userService;

    public OrderService() {
        this.orderService = new Service<>(FileName.ORDER);
        this.userService = new Service<>(FileName.USER);
    }

    @Override
    public List<Order> findAll() {
        return orderService.findAll();
    }

    @Override
    public Long getNewId() {
        return orderService.getNewId();
    }

    @Override
    public boolean save(Order order) {
        return orderService.save(order);
    }

    @Override
    public Order findById(Long id) {
        return orderService.findById(id);
    }

    @Override
    public boolean deleteById(Long id) {
        return orderService.deleteById(id);
    }
}
