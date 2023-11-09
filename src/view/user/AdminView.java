package view.user;

import config.Config;
import config.Validation;
import constant.FileName;
import model.user.User;
import view.category.CategoryView;
import view.order.OrderView;
import view.product.ProductView;

import static config.Color.*;

public class AdminView {
    public void showMenuAdmin() {
        do {
            System.out.println(PURPLE + "+------------------------------------------------------------------------------------------------+");
            System.out.printf("|" + WHITE_BOLD_BRIGHT + "   TMESTICS   \uD83D\uDC8B(¯`•.¸.•´¯)\uD83D\uDC84                                   Xin chào: %-28s\n", HomeView.userLogin.getFullName() + PURPLE + "                 |");
            System.out.println("+------------------------------------------------------------------------------------------------+");
            System.out.println("|" + RESET + "                                 1. \uD83D\uDCE6 QUẢN LÝ SẢN PHẨM                                         " + PURPLE + "|");
            System.out.println("|" + RESET + "                                 2. \uD83D\uDCC2 QUẢN LÝ DANH MỤC SẢN PHẨM                                " + PURPLE + "|");
            System.out.println("|" + RESET + "                                 3. \uD83D\uDCD7 QUẢN LÝ ĐƠN HÀNG                                         " + PURPLE + "|");
            System.out.println("|" + RESET + "                                 4. \uD83D\uDC64 QUẢN LÝ TÀI KHOẢN                                        " + PURPLE + "|");
            System.out.println("|" + RESET + "                                 5. \uD83D\uDCBC THÔNG TIN TÀI KHOẢN                                      " + PURPLE + "|");
            System.out.println("|" + RESET + "                                 0. \uD83D\uDCF4 ĐĂNG XUẤT                                                " + PURPLE + "|");
            System.out.println("+------------------------------------------------------------------------------------------------+" + RESET);
            System.out.println("Nhập lựa chọn: ");

            switch (Validation.validateInt()) {
                case 1:
                    new ProductView().showProductManagement();
                    break;
                case 2:
                    new CategoryView().showCategoryManagement();
                    break;
                case 3:
                    new OrderView().showOrderManagement();
                    break;
                case 4:
                    new UserManagementView().showUserManagement();
                    break;
                case 5:
                    new AccountView().showAccountManagement();
                    break;
                case 0:
                    new Config<User>().writeFile(FileName.LOGIN, null);
                    HomeView.userLogin = null;
                    new HomeView().showMenuHome();
                default:
                    System.out.println(RED + "Không có chức năng phù hợp, vui lòng chọn lại!!!" + RESET);
                    break;
            }
        } while (true);
    }
}
