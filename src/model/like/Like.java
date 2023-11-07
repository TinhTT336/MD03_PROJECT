package model.like;

import model.Entity;

import java.io.Serializable;

public class Like extends Entity<Long>implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long userId;
    private Long productId;
    private boolean likeStatus;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public boolean isLikeStatus() {
        return likeStatus;
    }

    public void setLikeStatus(boolean likeStatus) {
        this.likeStatus = likeStatus;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
