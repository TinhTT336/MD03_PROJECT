package service.orderDetailService;

import constant.FileName;
import model.order.Order;
import model.orderDetail.OrderDetail;
import model.product.Product;
import model.user.User;
import service.IService;
import service.Service;

import java.util.List;

public class OrderDetailService implements IService<OrderDetail,Long> {
    private Service<OrderDetail,Long>orderDetailService;
    private Service<Order,Long>orderService;
    private Service<User,Long>userService;
    private Service<Product,Long>productService;

    public OrderDetailService() {
        this.orderDetailService=new Service<>(FileName.ORDER);
        this.productService=new Service<>(FileName.PRODUCT);
        this.userService=new Service<>(FileName.USER);
        this.orderService=new Service<>(FileName.ORDER);
    }

    @Override
    public List<OrderDetail> findAll() {
        return orderDetailService.findAll();
    }

    @Override
    public Long getNewId() {
        return orderDetailService.getNewId();
    }

    @Override
    public boolean save(OrderDetail orderDetail) {
        return orderDetailService.save(orderDetail);
    }

    @Override
    public OrderDetail findById(Long id) {
        return orderDetailService.findById(id);
    }

    @Override
    public boolean deleteById(Long id) {
        return orderDetailService.deleteById(id);
    }
}
