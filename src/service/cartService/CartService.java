package service.cartService;

import config.Config;
import constant.FileName;
import model.cart.Cart;
import model.user.User;
import service.IService;
import service.Service;
import service.userService.UserService;

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

    public Cart getCurrentCartUser() {
        User userLogin = new UserService().getCurrentUser();
//        return new Config<Cart>().readFile(FileName.CART);
        if (!cartService.findAll().isEmpty() && cartService.findAll() != null) {
            for (Cart cart : cartService.findAll()) {
                if (cart.getUserId().equals(userLogin.getId())) {
                    return cart;
                }
            }
        }
        return null;
    }

    public Cart getCartByUserLogin(User userLogin) {
//        User userLogin = new UserService().getCurrentUser();
        for (Cart cart : cartService.findAll()) {
            if (cart.getUserId().equals(userLogin.getId())) {
                return cart;
            }
        }
        return null;
    }


}
