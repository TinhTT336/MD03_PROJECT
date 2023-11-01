package service.cartService;

import config.Config;
import constant.FileName;
import model.cart.Cart;
import service.IService;
import service.Service;

import java.util.List;

public class CartService implements IService<Cart, Long> {
    private Service<Cart, Long> cartService;

    public CartService() {
        this.cartService = new Service<>(FileName.CART);
    }

    @Override
    public List<Cart> findAll() {
        return cartService.findAll();
    }

    @Override
    public Long getNewId() {
        return cartService.getNewId();
    }

    @Override
    public boolean save(Cart cart) {
        return cartService.save(cart);
    }

    @Override
    public Cart findById(Long id) {
        return cartService.findById(id);
    }

    @Override
    public boolean deleteById(Long id) {
        return cartService.deleteById(id);
    }
    public Cart getCurrentCartUser(){
        return new Config<Cart>().readFile(FileName.CART);
//        return cartService.getOne();
    }

}
