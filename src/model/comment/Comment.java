package model.comment;

import model.Entity;

import java.io.Serializable;

public class Comment extends Entity<Long> implements Serializable {
    private static final long serialVersionUID = 1L;

private Long userId;
private Long productId;
private String comment;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
