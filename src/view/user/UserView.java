package view.user;

import config.StringFormatter;
import config.Validation;
import constant.FileName;
import constant.OrderStatus;
import model.cart.Cart;
import model.category.Category;
import model.order.Order;
import model.product.Product;
import model.user.User;
import service.Service;
import service.cartService.CartService;
import view.category.CategoryView;
import view.product.ProductView;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static config.Color.*;

public class UserView {
    private Service<Product, Long> productService;
    private Service<Category, Long> categoryService;
    private Service<User, Long> userLoginService;
    private static Service<Cart, Long> cartService;
    private Service<User, Long> userService;
    private Service<Order, Long> orderService;
    public static Map<Product, Integer> cart = new HashMap<>();
    public static Map<Long, Integer> cart1 = new HashMap<>();

//    public static Cart currentCart = new CartService().getCurrentCartUser();

    static Cart userCart = (new CartService().getCurrentCartUser() != null && new CartService().getCurrentCartUser().getUserId().equals(HomeView.userLogin.getId()) ? new CartService().getCurrentCartUser() : new Cart(HomeView.userLogin.getId(), cart));


    public UserView() {
        this.productService = new Service<>(FileName.PRODUCT);
        this.cartService = new Service<>(FileName.CART);
        this.userLoginService = new Service<>(FileName.LOGIN);
        this.categoryService = new Service<>(FileName.CATEGORY);
        this.orderService = new Service<>(FileName.ORDER);
    }

    public void showMenuUser() {
        do {
            System.out.println(PURPLE + "+------------------------------------------------------------------------------------------------+");
            System.out.printf("|" + WHITE_BOLD_BRIGHT + "   TMESTICS   \uD83D\uDC8B(¯`•.¸.•´¯)\uD83D\uDC84                                   Xin chào: %30s\n", HomeView.userLogin.getFullName() + PURPLE + "                |");
            System.out.println("|------------------------------------------------------------------------------------------------|");
            System.out.println("|" + RESET + "                                 1. \uD83D\uDCE6 DANH SÁCH SẢN PHẨM                                       " + PURPLE + "|");
            System.out.println("|" + RESET + "                                 2. \uD83D\uDDC2️ SẢN PHẨM NỔI BẬT                                         " + PURPLE + "|");
            System.out.println("|" + RESET + "                                 3. \uD83D\uDDC2️ DANH SÁCH DANH MỤC SẢN PHẨM                              " + PURPLE + "|");
            System.out.println("|" + RESET + "                                 4. \uD83D\uDD0D TÌM KIẾM SẢN PHẨM                                        " + PURPLE + "|");
            System.out.println("|" + RESET + "                                 5. \uD83D\uDD0D TÌM KIẾM DANH MỤC SẢN PHẨM                               " + PURPLE + "|");
            System.out.println("|" + RESET + "                                 6. \uD83D\uDD00 SẮP XẾP SẢN PHẨM THEO GIÁ                                " + PURPLE + "|");
            System.out.println("|" + RESET + "                                 7. \uD83D\uDED2 ĐẶT HÀNG                                                 " + PURPLE + "|");
            System.out.println("|" + RESET + "                                 8. \uD83D\uDECD️ GIỎ HÀNG CỦA TÔI                                         " + PURPLE + "|");
            System.out.println("|" + RESET + "                                 9. \uD83D\uDCBC THÔNG TIN TÀI KHOẢN                                      " + PURPLE + "|");
            System.out.println("|" + RESET + "                                 0. \uD83D\uDCF4 ĐĂNG XUẤT                                                " + PURPLE + "|");
            System.out.println("+------------------------------------------------------------------------------------------------+" + RESET);
            System.out.println("Nhập lựa chọn: ");

            switch (Validation.validateInt()) {
                case 1:
                    new ProductView().showTrueProduct(productService.findAll());
                    if (!productService.findAll().isEmpty()) {
                        new ProductView().showProductDetail();
                    }
                    break;
                case 2:
                    showFeaturedProduct();
                    break;
                case 3:
                    new CategoryView().showCategoryListForPro();
                    if (!cartService.findAll().isEmpty()) {
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
                    buyProducts();
                    break;
                case 8:
                    showCartUser();
                    if (userCart != null && userCart.getCartUsers() != null && !userCart.getCartUsers().isEmpty()) {
                        handlePayment();
                    }
                    break;
                case 9:
                    new AccountView().showAccountManagement();
                    break;
                case 0:
                    userLoginService.saveOne(null);
//                    cartService.saveOne(null);
                    userCart = null;
//                    new Config<User>().writeFile(FileName.LOGIN, null);
                    new HomeView().showMenuHome();
                default:
                    System.out.println(" Không có chức năng phù hợp, vui lòng chọn lại");
                    break;
            }
        } while (true);
    }

    private void handlePayment() {
        System.out.println("Các chức năng để lựa chọn: ");
        System.out.println(PURPLE + "+---------------------------------------------------------------------------------------------------------------------+");
        System.out.println("|" + RESET + "  1. TIẾP TỤC MUA HÀNG  |  2. THAY ĐỔI SỐ LƯỢNG SẢN PHẨM   |  3. XOÁ SẢN PHẨM    |  4. THANH TOÁN    | 0. QUAY LẠI   " + PURPLE + "|");
        System.out.println(PURPLE + "+---------------------------------------------------------------------------------------------------------------------+" + RESET);
        System.out.println("Nhập lựa chọn: ");

        switch (Validation.validateInt()) {
            case 1:
                buyProducts();
                break;
            case 2:
                changeProQuantity();
                break;
            case 3:
                deleteProduct();
                break;
            case 4:
                Order order = new Order();
                order.setId(orderService.getNewId());
                order.setUserId(HomeView.userLogin.getId());
                System.out.println("Nhập tên người nhận hàng: ");
                order.setName(Validation.validateString());
                System.out.println("Nhập số điện thoại người nhận hàng: ");
                order.setPhoneNumber(Validation.validatePhone());
                System.out.println("Nhập địa chỉ giao hàng: ");
                order.setAddress(Validation.validateString());
                order.setOrderStatus(OrderStatus.WAITING);
                order.setOrderAt(LocalDateTime.now());
                order.setDeliverAt(order.getOrderAt().plusDays(4));
                order.setOrdersDetail(userCart.getCartUsers());
                order.setTotal(getTotal());

                //tru so luong da mua hang trong ProductList
                for (Map.Entry<Product, Integer> entry : userCart.getCartUsers().entrySet()) {
                    for (Product product : productService.findAll()) {
                        if (entry.getKey().equals(product)) {
                            product.setStock(product.getStock() - entry.getValue());
                            if (product.getStock() == 0) {
                                product.setStatus(!product.isStatus());
                            }
                            productService.save(product);
                        }
                    }
                }

                orderService.save(order);
                cartService.saveOne(null);
                userCart = null;
                break;
            case 0:
                return;
            default:
                System.out.println(" Không có chức năng phù hợp, vui lòng chọn lại");
                break;
        }
    }

    private void changeProQuantity() {
        System.out.println("Nhập mã sản phẩm muốn thay đổi số lượng");
        int idPro = Validation.validateInt();
        Product changedPro = productService.findById((long) idPro);
        if (changedPro == null) {
            System.out.println(RED + "Không có sản phẩm với mã vừa nhập !" + RESET);
            return;
        }
        while (true) {
            System.out.println("Nhập số lượng sản phẩm muốn thay đổi: ");
            int quantity = Validation.validateInt();
            if (quantity > changedPro.getStock()) {
                System.out.println(RED + "Số lượng vượt quá tồn kho, vui lòng kiểm tra lại" + RESET);
            } else if (quantity <= 0) {
                userCart.getCartUsers().remove(changedPro);
                break;
            } else {
                for (Map.Entry<Product, Integer> entry : userCart.getCartUsers().entrySet()) {
                    if (entry.getKey().equals(changedPro)) {
                        entry.setValue(quantity);
                        System.out.println(PURPLE + "Đã thay đổi số lượng sản phẩm " + changedPro.getProductName() + " !" + RESET);
                        cartService.saveOne(userCart);
                        return;
                    }
                }
            }
        }
    }

    private void deleteProduct() {
        System.out.println("Nhập mã sản phẩm muốn xoá khỏi giỏ hàng: ");
        int deleteProId = Validation.validateInt();
        Product deletePro = productService.findById((long) deleteProId);
        if (deletePro == null) {
            System.out.println(RED + "Không có sản phẩm với mã vừa nhập !" + RESET);
        } else {
            userCart.getCartUsers().remove(deletePro);
            cartService.saveOne(userCart);
            System.out.println(PURPLE + "Đã xoá sản phẩm " + deletePro.getProductName() + " !" + RESET);
        }
    }

    private double showCartUser() {
        double total = 0;
        System.out.println(PURPLE + "+---------------------------------------------------------------------------------------------------------------------------------+ " + RESET);
        System.out.println(PURPLE + "|" + RESET + "  ID |                TÊN                 |  DANH MỤC  |               MÔ TẢ              | ĐƠN GIÁ(VND) | SỐ LƯỢNG | THÀNH TIỀN " + PURPLE + "|");
        new ProductView().showTLine();
        if (userCart == null || userCart.getCartUsers() == null || userCart.getCartUsers().isEmpty()) {
            System.out.println(PURPLE + "|" + RED + " Chưa có sản phẩm nào !!!" + RESET);
        } else {
            for (Product key : userCart.getCartUsers().keySet()) {
                System.out.printf(PURPLE + "|" + RESET + " %-4d| %-35s| %-11s| %-33s|   %-10s |     %-4d | %-10s " + PURPLE + "|\n",
                        key.getId(), key.getProductName(), key.getCategory().getCategoryName(), key.getDescription(), StringFormatter.formatCurrency(key.getUnitPrice()), userCart.getCartUsers().get(key), StringFormatter.formatCurrency(key.getUnitPrice() * userCart.getCartUsers().get(key)));
                total += key.getUnitPrice() * userCart.getCartUsers().get(key);
            }
            new ProductView().showTLine();
            System.out.printf(PURPLE + "|" + WHITE_BOLD_BRIGHT + "      TỔNG TIỀN                                                                           |   %-12s   \n", StringFormatter.formatCurrency(total) + PURPLE + "                            |");
        }
        new ProductView().showTLine();
        return total;
    }

    private double getTotal() {
        double total = 0;
        if (userCart == null || userCart.getCartUsers() == null || userCart.getCartUsers().isEmpty()) {
//            System.out.println(PURPLE + "|" + RED + " Chưa có sản phẩm nào !!!" + RESET);
            System.out.println();
        } else {
            for (Product key : userCart.getCartUsers().keySet()) {
                total += key.getUnitPrice() * userCart.getCartUsers().get(key);
            }
        }
        return total;
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

        if (!buyProduct.isStatus() && !buyProduct.getCategory().isStatus()) {
            System.out.println(RED + "Sản phẩm đang hết hàng, vui lòng chọn sản phẩm khác" + RESET);
        } else {
            buyOneProduct(buyProduct);
        }
    }

    public void buyOneProduct(Product product) {
        System.out.println("Các chức năng để lựa chọn: ");
        System.out.println(PURPLE + "+--------------------------------------------------------+");
        System.out.println("|" + RESET + " 1. THÊM VÀO GIỎ HÀNG        |    0. QUAY LẠI           " + PURPLE + "|");
        System.out.println("+--------------------------------------------------------+" + RESET);
        System.out.println("Nhập lựa chọn: ");

        switch (Validation.validateInt()) {
            case 1:
                if (!product.isStatus() || !product.getCategory().isStatus() || product.getStock() == 0) {
                    System.out.println(RED + "Sản phẩm đang hết hàng, vui lòng chọn sản phẩm khác" + RESET);
                    return;
                }
                if (userCart == null || userCart.getCartUsers() == null || userCart.getCartUsers().isEmpty()) {
                    cart.put(product, 1);
                    userCart.getCartUsers().put(product, 1);
                    System.out.println(PURPLE + "Đã thêm sản phẩm " + product.getProductName() + " thành công!" + RESET);
                    return;
                }

                boolean productExistsInCart = false;
                for (Map.Entry<Product, Integer> entry : userCart.getCartUsers().entrySet()) {
                    if (entry.getKey().equals(product)) {
                        if (entry.getValue() < product.getStock()) {
                            entry.setValue(entry.getValue() + 1);
                            productExistsInCart = true;
                            System.out.println(PURPLE + "Đã thêm sản phẩm " + product.getProductName() + " thành công!" + RESET);
                            break;
                        } else {
                            System.out.println(RED + "Số lượng vượt quá tồn kho, vui lòng kiểm tra lại" + RESET);
                        }
                        break;
                    }
                }
                if (!productExistsInCart) {
                    userCart.getCartUsers().put(product, 1);
                    System.out.println(PURPLE + "Đã thêm sản phẩm " + product.getProductName() + " thành công!" + RESET);
                }
                cartService.saveOne(userCart);
                break;
            case 2:
                return;
            default:
                System.out.println(" Không có chức năng phù hợp, vui lòng chọn lại");
                break;
        }
    }

}
