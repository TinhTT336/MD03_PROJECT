package service.categoryService;

import constant.FileName;
import model.category.Category;
import service.IService;
import service.Service;

import java.util.List;

public class CategoryService implements IService<Category, Long> {
    private Service<Category, Long> categoryService;

    public CategoryService() {
        this.categoryService = new Service<>(FileName.CATEGORY);
    }

    @Override
    public List<Category> findAll() {
        return categoryService.findAll();
    }

    @Override
    public Long getNewId() {
        return categoryService.getNewId();
    }

    @Override
    public boolean save(Category category) {
        return categoryService.save(category);
    }

    @Override
    public Category findById(Long id) {
        return categoryService.findById(id);
    }

    @Override
    public boolean deleteById(Long id) {
        return categoryService.deleteById(id);
    }

    public List<Category> sortByName() {
        List<Category>categoryList=categoryService.findAll();
        categoryList.sort((c1, c2) -> c1.getCategoryName().compareTo(c2.getCategoryName()));
        return categoryList;
    }


}
