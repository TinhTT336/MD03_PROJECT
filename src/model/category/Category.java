package model.category;

import model.Entity;

import java.io.Serializable;

public class Category extends Entity<Long>{
    private static final long serialVersionUID = 1L;
    private String categoryName;
    private String description;
    private boolean status=true;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

}
