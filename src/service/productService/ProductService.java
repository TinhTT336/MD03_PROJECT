package service.productService;

import constant.FileName;
import model.product.Product;
import service.IService;
import service.Service;

import java.util.ArrayList;
import java.util.List;

public class ProductService implements IService<Product, Long> {

    private final Service<Product, Long> productService;

    public ProductService() {
        productService = new Service<>(FileName.PRODUCT);
    }

    @Override
    public List<Product> findAll() {
        return productService.findAll();
    }

    @Override
    public Long getNewId() {
        return productService.getNewId();
    }

    @Override
    public boolean save(Product product) {
        return productService.save(product);
    }

    @Override
    public Product findById(Long id) {
        return productService.findById(id);
    }

    @Override
    public boolean deleteById(Long id) {
        return productService.deleteById(id);
    }

    public List<Product> searchProduct(String productName) {
        List<Product> productList = new ArrayList<>();
        for (Product product : productService.findAll()) {
            if (product.getProductName().toLowerCase().contains(productName.toLowerCase())) {
                productList.add(product);
            }
        }
        return productList;
    }

    public List<Product> searchTrueProductByName(String productName) {
        List<Product> productList = new ArrayList<>();
        for (Product product : productService.findAll()) {
            if (product.isStatus() && product.getCategory().isStatus()&&product.getStock()>0) {
                if (product.getProductName().toLowerCase().contains(productName.toLowerCase())) {
                    productList.add(product);
                }
            }
        }
        return productList;
    }

    public List<Product> searchTrueProductByCatID(Long id) {
        List<Product> productList = new ArrayList<>();
        for (Product product : productService.findAll()) {
            if (product.isStatus() && product.getCategory().isStatus()&&product.getStock()>0) {
                if (product.getCategory().getId().equals(id)) {
                    productList.add(product);
                }
            }
        }
        return productList;
    }

}
