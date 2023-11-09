package view.user;

import config.Config;
import config.Validation;
import constant.FileName;
import constant.Role;
import model.cart.Cart;
import model.product.Product;
import model.user.User;
import service.Service;
import service.cartService.CartService;
import service.userService.UserService;
import view.category.CategoryView;
import view.product.ProductView;

import java.util.HashMap;

import static config.Color.*;


public class HomeView {
    private final Service<Product, Long> productService;

    public HomeView() {
        this.productService = new Service<>(FileName.PRODUCT);
    }

    UserService userService = new UserService();
    public static User userLogin;
    public static Cart userCart;


    public void showMenuHome() {
        do {
            System.out.println(PURPLE + "+------------------------------------------------------------------------------------------------+");
            System.out.println("|" + WHITE_BOLD_BRIGHT + "            \uD83D\uDC8B(¯`•.¸.•´¯)\uD83D\uDC84               TMESTICS               \uD83D\uDC8B(¯`•.¸.•´¯)\uD83D\uDC84               " + PURPLE + "|");
            System.out.println("+------------------------------------------------------------------------------------------------+");
            System.out.println("|" + RESET + "                                 1. \uD83D\uDD11 ĐĂNG NHẬP                                                " + PURPLE + "|");
            System.out.println("|" + RESET + "                                 2. \uD83D\uDD12 ĐĂNG KÝ                                                  " + PURPLE + "|");
            System.out.println("|" + RESET + "                                 3. \uD83D\uDCE6 DANH SÁCH SẢN PHẨM                                       " + PURPLE + "|");
            System.out.println("|" + RESET + "                                 4. \uD83D\uDDC2️ SẢN PHẨM NỔI BẬT                                         " + PURPLE + "|");
            System.out.println("|" + RESET + "                                 5. \uD83D\uDDC2️ DANH SÁCH DANH MỤC SẢN PHẨM                              " + PURPLE + "|");
            System.out.println("|" + RESET + "                                 6. \uD83D\uDD0D TÌM KIẾM SẢN PHẨM                                        " + PURPLE + "|");
            System.out.println("|" + RESET + "                                 7. \uD83D\uDD0D TÌM KIẾM DANH MỤC SẢN PHẨM                               " + PURPLE + "|");
            System.out.println("|" + RESET + "                                 8. \uD83D\uDD00 SẮP XẾP SẢN PHẨM THEO GIÁ                                " + PURPLE + "|");
            System.out.println("|" + RESET + "                                 9. \uD83D\uDED2 ĐẶT HÀNG                                                 " + PURPLE + "|");
            System.out.println("|" + RESET + "                                 0. ❌ THOÁT                                                    " + PURPLE + "|");
            System.out.println("+------------------------------------------------------------------------------------------------+" + RESET);
            System.out.println("Nhập lựa chọn: ");
            switch (Validation.validateInt()) {
                case 1:
                    login();
                    break;
                case 2:
                    register();
                    break;
                case 3:
                    new ProductView().showTrueProduct(productService.findAll());
                    if (!productService.findAll().isEmpty()) {
                        new ProductView().showProductDetailForUser();
                    }
                    break;
                case 4:
                    new UserView().showFeaturedProduct();
                    break;
                case 5:
                    new CategoryView().showCategoryListForPro();
                    if (!productService.findAll().isEmpty()) {
                        new ProductView().showCategoryDetail();
                    }
                    break;
                case 6:
                    new ProductView().searchTrueProduct();
                    break;
                case 7:
                    new ProductView().searchTrueProductByCat();
                    break;
                case 8:
                    new ProductView().sortProductByPrice();
                    break;
                case 9:
                    if (HomeView.userLogin == null) {
                        System.out.println(RED + "Vui lòng đăng nhập để mua hàng!!!" + RESET);
                        System.out.println();
                        login();
                    }
                    break;
                case 0:
                    System.exit(0);
                default:
                    System.out.println(RED + "Không có chức năng phù hợp, vui lòng chọn lại!!!" + RESET);
                    break;
            }
        } while (true);
    }

    public void register() {
        System.out.println(WHITE_BOLD_BRIGHT + "\uD83D\uDC8B(¯`•.¸.•´¯)\uD83D\uDC84--------- ĐĂNG KÝ--------- \uD83D\uDC8B(¯`•.¸.•´¯)\uD83D\uDC84" + RESET);
        System.out.println();
        User user = new User();
        user.setId(userService.getNewId());
        System.out.println("Nhập họ và tên: ");
        user.setFullName(Validation.validateString());

        System.out.println("Nhập tên tài khoản: ");
        while (true) {
            String username = Validation.validateString();
            if (userService.checkUsernameExist(username)) {
                System.out.println(RED + "Tên tài khoản đã tồn tại, vui lòng nhập lại!!!" + RESET);
            } else {
                user.setUsername(username);
                break;
            }
        }

        System.out.println("Nhập email: ");
        while (true) {
            String email = Validation.validateEmail();
            if (userService.checkEmailExist(email)) {
                System.out.println(RED + "Email đã tồn tại, vui lòng nhập lại!!!" + RESET);
            } else {
                user.setEmail(email);
                break;
            }
        }

        System.out.println("Nhập số điện thoại: ");
        user.setPhone(Validation.validatePhone());

        System.out.println("Nhập mật khẩu: ");
        user.setPassword(Validation.validatePassword());

        System.out.println("Xác nhận lại mât khẩu: ");
        while (true) {
            String rePass = Validation.validatePassword();
            if (user.getPassword().equals(rePass)) {
                break;
            } else {
                System.out.println(RED + "Mật khẩu không khớp, vui lòng nhập lại!!!" + RESET);
            }
        }
        userService.save(user);
        System.out.println(PURPLE_BRIGHT + "Đăng ký tài khoản thành công!" + RESET);
        System.out.println();
        login();
    }

    public void login() {
        System.out.println(WHITE_BOLD_BRIGHT + "\uD83D\uDC8B(¯`•.¸.•´¯)\uD83D\uDC84-------- ĐĂNG NHẬP-------- \uD83D\uDC8B(¯`•.¸.•´¯)\uD83D\uDC84" + RESET);
        System.out.println();
        System.out.println("Nhập tên tài khoản: ");
        String usernameLogin = Validation.validateString();
        System.out.println("Nhập mật khẩu: ");
        String passwordLogin = Validation.validateString();

        User user = userService.checkLogin(usernameLogin, passwordLogin);
        if (user == null) {
            System.out.println(RED + "Tên tài khoản hoặc mật khẩu không đúng, vui lòng kiểm tra lại!!!" + RESET);
        } else {
            checkRole(user);
        }
    }

    public void checkRole(User user) {
        if (!user.isStatus()) {
            System.out.println(RED + "Tài khoản bị khoá, vui lòng liên hệ ADMIN!!!" + RESET);
            return;
        }
        if (user.getRole().equals(Role.ADMIN)) {
            userLogin = user;
            new Config<User>().writeFile(FileName.LOGIN, userLogin);
//            System.out.println(PURPLE_BRIGHT + "Đăng nhập thành công!" + RESET);
            new AdminView().showMenuAdmin();
        } else {
            if (user.getRole().equals(Role.USER)) {
                userLogin = user;
                userCart = new CartService().getCartByUserLogin(userLogin);
                if (userCart == null) {
                    userCart = new Cart(new CartService().getNewId(), HomeView.userLogin.getId(), new HashMap<>());
                }
                new Config<User>().writeFile(FileName.LOGIN, userLogin);
//                System.out.println(PURPLE_BRIGHT + "Đăng nhập thành công!" + RESET);
                new UserView().showMenuUser();
            }
        }
    }
}

