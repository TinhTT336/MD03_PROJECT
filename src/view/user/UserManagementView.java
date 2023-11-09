package view.user;

import config.Validation;
import constant.FileName;
import constant.Role;
import model.user.User;
import service.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static config.Color.*;
import static config.Color.PURPLE;

public class UserManagementView {
    private final Service<User, Long> userService;

    public UserManagementView() {
        this.userService = new Service<>(FileName.USER);
    }

    public void showUserManagement() {
        do {
            System.out.println(PURPLE + "+------------------------------------------------------------------------------------------------+");
            System.out.printf("|" + WHITE_BOLD_BRIGHT + "   TMESTICS   \uD83D\uDC8B(¯`•.¸.•´¯)\uD83D\uDC84                                   Xin chào: %-28s\n", HomeView.userLogin.getFullName() + PURPLE + "                 |");
            System.out.println("+------------------------------------------------------------------------------------------------+");
            System.out.println("|" + WHITE_BOLD_BRIGHT + "                                    \uD83D\uDC64 QUẢN LÝ TÀI KHOẢN                                        " + PURPLE + "|");
            System.out.println("+------------------------------------------------------------------------------------------------+");
            System.out.println("|" + RESET + "                                 1. \uD83D\uDC41️ DANH SÁCH TÀI KHOẢN                                      " + PURPLE + "|");
            System.out.println("|" + RESET + "                                 2. ✏️ THAY ĐỔI TRẠNG THÁI                                      " + PURPLE + "|");
            System.out.println("|" + RESET + "                                 3. ✏️ SET QUYỀN                                                " + PURPLE + "|");
            System.out.println("|" + RESET + "                                 4. \uD83D\uDD0D TÌM KIẾM TÀI KHOẢN                                       " + PURPLE + "|");
            System.out.println("|" + RESET + "                                 5. \uD83D\uDD00 LỌC THEO TRẠNG THÁI TÀI KHOẢN                            " + PURPLE + "|");
            System.out.println("|" + RESET + "                                 0. ↩️ QUAY LẠI                                                 " + PURPLE + "|");
            System.out.println("+------------------------------------------------------------------------------------------------+"+RESET);
            System.out.println("Nhập lựa chọn: ");

            switch (Validation.validateInt()) {
                case 1:
                    showListUser(userService.findAll());
                    break;
                case 2:
                    changeUserStatus();
                    break;
                case 3:
                    setRole();
                    break;
                case 4:
                    findUser();
                    break;
                case 5:
                    filterUser();
                    break;
                case 0:
                    return;
                default:
                    System.out.println(RED + "Không có chức năng phù hợp, vui lòng chọn lại!!!" + RESET);
                    break;
            }
        } while (true);
    }

    private void filterUser() {
        System.out.println("Lựa chọn trạng thái tài khoản muốn lọc: ");
        System.out.println(PURPLE + "+---------------------------------------------------------------------------------+");
        System.out.println("|" + WHITE_BRIGHT + " 1. ĐANG HOẠT ĐÔNG           | 2. BỊ KHOÁ               | 0. QUAY LẠI            " + PURPLE+"|");
        System.out.println(PURPLE + "+---------------------------------------------------------------------------------+" + RESET);

        System.out.println("Nhập lựa chọn: ");
        switch (Validation.validateInt()) {
            case 1:
                List<User> trueUsers = userService.findAll().stream().filter(User::isStatus).collect(Collectors.toList());
                showListUser(trueUsers);
                break;
            case 2:
                List<User> falseUsers = userService.findAll().stream().filter(user -> !user.isStatus()).collect(Collectors.toList());
                showListUser(falseUsers);
                break;
            case 0:
                return;
            default:
                System.out.println(RED + "Không có chức năng phù hợp, vui lòng chọn lại!!!" + RESET);
                break;
        }
    }

    private void findUser() {
        System.out.println("Nhập tên người dùng muốn tìm kiếm: ");
        String name = Validation.validateString();
        int count = 0;
        showTHead();
        for (User user : userService.findAll()) {
            if (user.getUsername().toLowerCase().contains(name.toLowerCase()) || user.getFullName().toLowerCase().contains(name.toLowerCase())) {
                count++;
                showTBody(user);
            }
        }

        if (count > 0) {
            showTLine();
            System.out.println(PURPLE_BRIGHT+"Tìm thấy " + count + " tài khoản có tên " + "'" + name + "'"+RESET);
            System.out.println();
        } else {
            System.out.println("|" + RED + "  Không có tài khoản nào!!!" + RESET);
            showTLine();
            System.out.println();
        }
    }

    private void setRole() {
        System.out.println("Nhập mã người dùng muốn thay đổi Vai trò: ");
        int setRoleId = Validation.validateInt();

        User setRoleUser = userService.findById((long) setRoleId);
        if (setRoleUser == null) {
            System.out.println(RED+"Không có tài khoản với mã vừa nhập!!!" + RESET);
        } else {
            System.out.println("Thông tin người dùng muốn thay đổi Vai trò: ");
            showUser(setRoleUser);
            if (setRoleUser.getRole().equals(Role.ADMIN)) {
//                setRoleIdUser.setRole(Role.USER);
//                userService.save(setRoleIdUser);
                System.out.println(RED + "Không được quyền thay đổi tài khoản ADMIN !!!" + RESET);
            } else {
                if (!setRoleUser.isStatus()) {
                    System.out.println(RED + "Không được thay đổi vai trò tài khoản đã bị khoá!!!" + RESET);
                } else {
                    List<User> users = new ArrayList<>();
                    for (User user : userService.findAll()) {
                        if (user.getRole().equals(Role.ADMIN)) {
                            users.add(user);
                        }
                    }
                    if (users.size() >= 2) {
                        System.out.println(RED + "Số lượng tài khoản ADMIN không được vượt quá 2 tài khoản !!!" + RESET);
                    } else {
                        setRoleUser.setRole(Role.ADMIN);
                        userService.save(setRoleUser);
                        System.out.println(PURPLE_BRIGHT+"Đã thay đổi Vai trò người dùng!"+RESET);
                    }
                }
            }
        }
    }

    private void changeUserStatus() {
        System.out.println("Nhập mã người dùng muốn thay đổi trạng thái: ");
        int changeId = Validation.validateInt();

        User changeUser = userService.findById((long) changeId);
        if (changeUser == null) {
            System.out.println(RED + "Không có tài khoản với mã vừa nhập !!!" + RESET);
        } else {
            System.out.println("Thông tin người dùng muốn thay đổi trạng thái: ");
            showUser(changeUser);

            if (changeUser.getRole().equals(Role.ADMIN)) {
                System.out.println(RED + "Không được quyền thay đổi trạng thái tài khoản ADMIN!!!" + RESET);
            } else {
                changeUser.setStatus(!changeUser.isStatus());
                userService.save(changeUser);
                System.out.println(PURPLE_BRIGHT+"Đã thay đổi trạng thái người dùng!"+RESET);
            }
        }
    }

    private void showListUser(List<User> userList) {
        System.out.println(WHITE_BOLD_BRIGHT + "\nDANH SÁCH TÀI KHOẢN                        ");
        showTHead();
        if (userList.isEmpty()) {
            System.out.println("|" + RED + "  Không có tài khoản nào!!!" + RESET);
            showTLine();
        } else {
            List<User> users = userList.stream().sorted((u1, u2) -> (int) (-u1.getId() - u2.getId())).collect(Collectors.toList());
            for (User user : users) {
                showTBody(user);
            }
            showTLine();
        }
    }

    private void showTHead() {
        showTLine();
        System.out.println("|" + WHITE_BRIGHT + "  ID  |       TÊN NGƯỜI DÙNG       |    TÊN TÀI KHOẢN    |        EMAIL        |  SỐ ĐIỆN THOẠI  |    VAI TRÒ    |    TRẠNG THÁI " + PURPLE + "|");
        showTLine();
    }

    private void showTBody(User user) {
        System.out.printf( PURPLE+ "|"+RESET+" %-4d | %-25s  |    %-15s  | %-19s |    %-11s  | %-14s|    %-10s "+PURPLE+"|\n",
                user.getId(), user.getFullName(), user.getUsername(), user.getEmail(), user.getPhone(), user.getRole().getRoleName(), (user.isStatus() ? "Hoạt động" : "Bị khoá"));
    }

    private void showTLine() {
        System.out.println(PURPLE+"+---------------------------------------------------------------------------------------------------------------------------------+" + RESET);
    }

    private void showUser(User user) {
        showTHead();
        showTBody(user);
        showTLine();
    }
}
