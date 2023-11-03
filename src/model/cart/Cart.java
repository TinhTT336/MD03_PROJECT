package model.cart;

import model.Entity;
import model.product.Product;

import java.io.Serializable;
import java.util.Map;

public class Cart extends Entity<Long> implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long userId;
    private Map<Product, Integer> cartUsers;

    public Cart() {
    }

    public Cart(Long id,Long userId, Map<Product, Integer> cartUsers) {
        this.id=id;
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
