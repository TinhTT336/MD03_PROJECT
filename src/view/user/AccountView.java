package view.user;

import config.Validation;
import constant.FileName;
import model.user.User;
import service.Service;
import service.userService.UserService;


import static config.Color.*;

public class AccountView {
    private Service<User, Long> userService;
    private Service<User, Long> loginService;


    public AccountView() {
        this.userService = new Service<>(FileName.USER);
        this.loginService = new Service<>(FileName.LOGIN);
    }

    public void showAccountManagement() {
        do {
            System.out.println(PURPLE + ".------------------------------------------------------------------------------------------------.");
            System.out.printf("|" + WHITE_BOLD_BRIGHT + "   TMESTICS   \uD83D\uDC8B(¯`•.¸.•´¯)\uD83D\uDC84                                   Xin chào: %-28s\n", HomeView.userLogin.getFullName() + PURPLE + "                 |");
            System.out.println("|------------------------------------------------------------------------------------------------|");
            System.out.println("|"+WHITE_BOLD_BRIGHT+"                                    \uD83D\uDCBC THÔNG TIN TÀI KHOẢN                                      "+PURPLE+"|");
            System.out.println("|------------------------------------------------------------------------------------------------|");
            System.out.println("|"+RESET+"                                 1. \uD83D\uDC41️ CHI TIẾT TÀI KHOẢN                                       "+PURPLE+"|");
            System.out.println("|"+RESET+"                                 2. ✏️ THAY ĐỔI THÔNG TIN                                       "+PURPLE+"|");
            System.out.println("|"+RESET+"                                 3. ✏️ THAY ĐỔI MẬT KHẨU                                        "+PURPLE+"|");
            System.out.println("|"+RESET+"                                 4. \uD83D\uDCC5 LỊCH SỬ MUA HÀNG                                         "+PURPLE+"|");
            System.out.println("|"+RESET+"                                 0. ↩️ QUAY LẠI                                                 "+PURPLE+"|");
            System.out.println("'------------------------------------------------------------------------------------------------'"+RESET);
            System.out.println("Nhập lựa chọn: ");

            switch (Validation.validateInt()) {
                case 1:
                    showDetailAccount();
                    break;
                case 2:
                    changeAccount();
                    break;
                case 3:
                    changePassword();
                    break;
                case 4:
                    break;
                case 0:
                    return;
                default:
                    System.out.println(RED + "Không có chức năng phù hợp, vui lòng chọn lại" + RESET);
                    break;
            }
        } while (true);
    }

    private void changePassword() {
        while (true) {
            System.out.println("Nhập mật khẩu hiện tại: ");
            String oldPass = Validation.validatePassword();
            if (!oldPass.equals(HomeView.userLogin.getPassword())) {
                System.out.println("Mật khẩu không chính xác, vui lòng kiểm tra lại");
            } else {
                System.out.println("Nhập mật khẩu muốn thay đổi: ");
                String newPass = Validation.validatePassword();
                if (newPass.equals(oldPass)) {
                    System.out.println("Mật khẩu mới không được trùng với mật khẩu trước đó");
                } else {
                    System.out.println("Xác nhận lại mật khẩu: ");
                    String rePass = Validation.validatePassword();
                    if (!newPass.equals(rePass)) {
                        System.out.println("Mật khẩu không khớp, vui lòng kiểm tra lại");
                    } else {
                        HomeView.userLogin.setPassword(newPass);
                        break;
                    }
                }
            }

//            System.out.println("Nhập mật khẩu tài khoản ");
//            String oldPass = Validation.validatePassword();
//
//            System.out.println("Nhập mật khẩu muốn thay đổi: ");
//            String newPass = Validation.validatePassword();
//
//            System.out.println("Xác nhận lại mật khẩu: ");
//            String rePass = Validation.validatePassword();
//
//            if (!oldPass.equals(HomeView.userLogin.getPassword())) {
//                System.out.println("Mật khẩu không chính xác, vui lòng kiểm tra lại");
//            } else if (newPass.equals(oldPass)) {
//                System.out.println("Mật khẩu mới không được trùng với mật khẩu trước đó");
//            } else {
//                if (!newPass.equals(rePass)) {
//                    System.out.println("Mật khẩu không khớp, vui lòng kiểm tra lại");
//                } else {
//                    HomeView.userLogin.setPassword(newPass);
//                    break;
//                }
//            }
        }
        loginService.saveOne(HomeView.userLogin);
        userService.save(HomeView.userLogin);
        System.out.println("Thay đổi mật khẩu thành công !");
    }

    private void changeAccount() {
        System.out.println("Chọn thông tin muốn thay đổi: ");
        System.out.println(PURPLE + ".---------------------------------------------------------------------------------------------------------------------------------.");
        System.out.println("|" + RESET + "     1. TÊN NGƯỜI DÙNG     |      2. TÊN TÀI KHOẢN      |           3. EMAIL           |            4. SỐ ĐIỆN THOẠI             |" + PURPLE);
        System.out.println("|---------------------------------------------------------------------------------------------------------------------------------|" + RESET);

        System.out.println("Nhập lựa chọn: ");
        switch (Validation.validateInt()) {
            case 1:
                System.out.println("Nhập họ và tên muốn thay đổi: ");
                HomeView.userLogin.setFullName(Validation.validateString());
                break;
            case 2:
                while (true) {
                    System.out.println("Nhập tên tài khoản muốn thay đổi: ");
                    String username = Validation.validateString();
                    if (new UserService().checkUsernameExist(username)) {
                        System.out.println("Tên tài khoản đã tồn tại, vui lòng nhập lại");
                    } else {
                        HomeView.userLogin.setUsername(username);
                        break;
                    }
                }
                break;
            case 3:
                while (true) {
                    System.out.println("Nhập email muốn thay đổi: ");
                    String email = Validation.validateEmail();
                    if (new UserService().checkEmailExist(email)) {
                        System.out.println("Email đã tồn tại, vui lòng nhập lại");
                    } else {
                        HomeView.userLogin.setEmail(email);
                        break;
                    }
                }
                break;
            case 4:
                System.out.println("Nhập số điện thoại muốn thay đổi: ");
                HomeView.userLogin.setPhone(Validation.validatePhone());
                break;
            default:
                System.out.println(RED + "Không có chức năng phù hợp, vui lòng chọn lại" + RESET);
                break;
        }
        userService.save(HomeView.userLogin);
        loginService.saveOne(HomeView.userLogin);

        System.out.println("Thay đổi thông tin tài khoản thành công !");
    }

    private void showDetailAccount() {
        System.out.println("\uD83D\uDC41️ THÔNG TIN CHI TIẾT TÀI KHOẢN   ");
        System.out.println(PURPLE + ".---------------------------------------------------------------------------------------------------------------------------------.");
        System.out.println("|" + RESET + "  ID  |       TÊN NGƯỜI DÙNG       |    TÊN TÀI KHOẢN    |        EMAIL        |  SỐ ĐIỆN THOẠI  |    VAI TRÒ    |    TRẠNG THÁI " + PURPLE+"|");
        System.out.println("|---------------------------------------------------------------------------------------------------------------------------------|");
        System.out.printf("|"+RESET+" %-4d | %-25s  |    %-15s  | %-19s |    %-11s  | %-14s|    %-10s "+PURPLE+"|\n",
                HomeView.userLogin.getId(), HomeView.userLogin.getFullName(), HomeView.userLogin.getUsername(), HomeView.userLogin.getEmail(), HomeView.userLogin.getPhone(), HomeView.userLogin.getRole().getRoleName(), (HomeView.userLogin.isStatus() ? "Hoạt động" : "Bị khoá"));
        System.out.println(PURPLE + "'---------------------------------------------------------------------------------------------------------------------------------|' " + RESET);
    }
}
