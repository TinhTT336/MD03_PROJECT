package view.user;

import config.Config;
import config.StringFormatter;
import config.Validation;
import constant.FileName;
import model.cart.Cart;
import model.category.Category;
import model.product.Product;
import model.user.User;
import service.Service;
import service.cartService.CartService;
import view.category.CategoryView;
import view.product.ProductView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static config.Color.*;

public class UserView {
    private Service<Product, Long> productService;
    private Service<Category, Long> categoryService;
    private Service<User, Long> userLoginService;
    private Service<Cart, Long> cartService;
    static Map<Product, Integer> cart = new HashMap<>();

    public static Cart userCart = new CartService().getCurrentCartUser();

    static {
        if (userCart == null) {
            userCart = new Cart(HomeView.userLogin.getId(), cart);
        }
    }


    public UserView() {
        this.productService = new Service<>(FileName.PRODUCT);
        this.cartService = new Service<>(FileName.CART);
        this.userLoginService = new Service<>(FileName.LOGIN);
        this.categoryService = new Service<>(FileName.CATEGORY);
    }

    public void showMenuUser() {
        do {
            System.out.println(PURPLE + ".------------------------------------------------------------------------------------------------.");
            System.out.printf("|" + WHITE_BOLD_BRIGHT + "   TMESTICS   \uD83D\uDC8B(¯`•.¸.•´¯)\uD83D\uDC84                                   Xin chào: %-28s\n", HomeView.userLogin.getFullName() + PURPLE + "                 |");
            System.out.println("|------------------------------------------------------------------------------------------------|");
            System.out.println("|" + RESET + "                                 1. \uD83D\uDCE6 DANH SÁCH SẢN PHẨM                                       " + PURPLE + "|");
            System.out.println("|" + RESET + "                                 2. \uD83D\uDDC2️ SẢN PHẨM NỔI BẬT                                         " + PURPLE + "|");
            System.out.println("|" + RESET + "                                 3. \uD83D\uDDC2️ DANH SÁCH DANH MỤC SẢN PHẨM                              " + PURPLE + "|");
            System.out.println("|" + RESET + "                                 4. \uD83D\uDD0D TÌM KIẾM SẢN PHẨM                                        " + PURPLE + "|");
            System.out.println("|" + RESET + "                                 5. \uD83D\uDD0D TÌM KIẾM DANH MỤC SẢN PHẨM                               " + PURPLE + "|");
            System.out.println("|" + RESET + "                                 6. \uD83D\uDD00 SẮP XẾP SẢN PHẨM THEO GIÁ                                " + PURPLE + "|");
            System.out.println("|" + RESET + "                                 7. \uD83D\uDED2 MUA HÀNG                                                 " + PURPLE + "|");
            System.out.println("|" + RESET + "                                 8. \uD83D\uDECD️ GIỎ HÀNG CỦA TÔI                                         " + PURPLE + "|");
            System.out.println("|" + RESET + "                                 9. \uD83D\uDCBC THÔNG TIN TÀI KHOẢN                                      " + PURPLE + "|");
            System.out.println("|" + RESET + "                                 0. \uD83D\uDCF4 ĐĂNG XUẤT                                                " + PURPLE + "|");
            System.out.println("'------------------------------------------------------------------------------------------------'" + RESET);
            System.out.println("Nhập lựa chọn: ");

            switch (Validation.validateInt()) {
                case 1:
                    new ProductView().showTrueProduct(productService.findAll());
                    if(!productService.findAll().isEmpty()){
                        new ProductView().showProductDetail();
                    }
                    break;
                case 2:
                    showFeaturedProduct();
                    break;
                case 3:
                    new CategoryView().showCategoryListForPro();
                    if(!cartService.findAll().isEmpty()){
                        new ProductView().showCategoryDetail();
                    }
                    break;
                case 4:
                    new ProductView().searchTrueProduct();
                    break;
                case 5:
                    new ProductView().searchTrueProductByCat();
                    break;
                case 6:
                    new ProductView().sortProductByPrice();
                    break;
                case 7:
//                    buyProducts();
                    break;
                case 8:
                    showCartUser();
                    break;
                case 9:
                    new AccountView().showAccountManagement();
                    break;
                case 0:
                    userLoginService.saveOne(null);
//                    new Config<User>().writeFile(FileName.LOGIN, null);
                    new HomeView().showMenuHome();
                default:
                    System.out.println(" Không có chức năng phù hợp, vui lòng chọn lại");
                    break;
            }
        } while (true);
    }

    private void showCartUser() {
        for (Cart cart : cartService.findAll()) {
            System.out.println(cart.toString());
        }
        System.out.println(PURPLE + ".---------------------------------------------------------------------------------------------------------------------------------. " + RESET);
        System.out.println(PURPLE + "|" + RESET + "  ID |                TÊN                 |  DANH MỤC  |               MÔ TẢ              | ĐƠN GIÁ(VND) | SỐ LƯỢNG | THÀNH TIỀN" + PURPLE + "|");
        new ProductView().showTLine();
        if(userCart.getCartUsers().isEmpty()){
            System.out.println(PURPLE + "|" +RED+" Chưa có sản phẩm nào !!!"+RESET);
        }else{
            for (Product key : userCart.getCartUsers().keySet()) {
                System.out.printf(PURPLE + "|" + RESET + " %-4d| %-35s| %-11s| %-33s|   %-10s |     %-4d| %-10s" + PURPLE + "|\n",
                        key.getId(), key.getProductName(), key.getCategory().getCategoryName(), key.getDescription(), StringFormatter.formatCurrency(key.getUnitPrice()), userCart.getCartUsers().get(key), StringFormatter.formatCurrency(key.getUnitPrice() * userCart.getCartUsers().get(key)));
            }
        }
        new ProductView().showTLine();
    }

    public void showFeaturedProduct() {
//        List<Product> featuredProduct = productService.findAll().stream().sorted(Comparator.comparing(Product::getUnitPrice).reversed()).limit(10).collect(Collectors.toList());
        List<Product> featuredProduct = productService.findAll().stream().sorted((p1, p2) -> (int) -(p1.getUnitPrice() - p2.getUnitPrice())).limit(10).collect(Collectors.toList());
        new ProductView().showTrueProduct(featuredProduct);
    }

    public void buyProducts() {
        new ProductView().showTrueProduct(productService.findAll());
        System.out.println("Nhập mã sản phẩm muốn mua: ");
        int id = Validation.validateInt();
        Product buyProduct = productService.findById((long) id);
        if (buyProduct == null) {
            System.out.println(RED + "Không tìm thấy sản phẩm nào với mã vừa nhập" + RESET);
            return;
        }

        if (!buyProduct.isStatus()) {
            System.out.println(RED + "Sản phẩm đang hết hàng, vui lòng chọn sản phẩm khác" + RESET);
        } else {
            buyOneProduct(buyProduct);
        }
    }

    public void buyOneProduct(Product product) {
        System.out.println("Các chức năng để lựa chọn: ");
        System.out.println(PURPLE + ".--------------------------------------------------------.");
        System.out.println("|" + RESET + " 1. THÊM VÀO GIỎ HÀNG        |    0. QUAY LẠI           " + PURPLE + "|");
        System.out.println("'--------------------------------------------------------'" + RESET);
        System.out.println("Nhập lựa chọn: ");

        switch (Validation.validateInt()) {
            case 1:
                if (!product.isStatus() || !product.getCategory().isStatus() || product.getStock() == 0) {
                    System.out.println(RED + "Sản phẩm đang hết hàng, vui lòng chọn sản phẩm khác" + RESET);
                    return;
                }
                Map<Product, Integer> cart = new HashMap<>();
                if (userCart == null || userCart.getCartUsers().isEmpty() || userCart.getCartUsers() == null) {
                    cart.put(product, 1);
                    userCart.setCartUsers(cart);
                } else {
                    boolean productExistsInCart = false;
                    for (Map.Entry<Product, Integer> entry : userCart.getCartUsers().entrySet()) {
                        if (entry.getKey().equals(product)) {
                            entry.setValue(entry.getValue() + 1);
                            productExistsInCart = true;
                            break;
                        }
                    }
                    if (!productExistsInCart) {
                        userCart.getCartUsers().put(product, 1);
                    }
                }
                cartService.saveOne(userCart);
                break;
            case 2:
                break;
            default:
                System.out.println(" Không có chức năng phù hợp, vui lòng chọn lại");
                break;
        }
    }

    public boolean equals(Object obj) {
        return (this == obj);
    }
}
