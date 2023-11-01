package model.cart;

import model.Entity;
import model.product.Product;

import java.io.Serializable;
import java.util.Map;

public class Cart extends Entity<Long> implements Serializable {
    private Long userId;
//    private Map<Long, Integer>cartUser;
    private Map<Product, Integer>cartUsers;

    public Cart() {
    }

    public Cart(Long userId, Map<Product, Integer> cartUsers) {
        this.userId = userId;
        this.cartUsers = cartUsers;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Map<Product, Integer> getCartUsers() {
        return cartUsers;
    }

    public void setCartUsers(Map<Product, Integer> cartUsers) {
        this.cartUsers = cartUsers;
    }


}
