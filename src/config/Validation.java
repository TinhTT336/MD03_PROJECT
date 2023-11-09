package config;

import static config.Color.*;

public class Validation {
    public static int validateInt() {
        int n;
        while (true) {
            try {
                n = Integer.parseInt(Config.scanner().nextLine());
                if (n < 0) {
                    System.out.println(RED + "Sai định dạng, vui lòng nhập lại: " + RESET);
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println(RED + "Sai định dạng, vui lòng nhập lại: " + RESET);
            }
        }
        return n;
    }


    public static String validateString() {
        String s;
        while (true) {
            s = Config.scanner().nextLine();
            if (s.trim().isEmpty()) {
                System.out.println(RED + "Độ dài nhập vào cần chứa ít nhất 1 ký tự, vui lòng nhập lại: " + RESET);
            } else {
                break;
            }
        }
        return s;
    }


    public static String validateEmail() {
        String email;
        while (true) {
            email = validateString();
            if (email.matches("[a-zA-Z0-9]+(\\.[a-zA-Z0-9]+)*@[a-z]+(\\.[a-z]+){1,2}")) {
                break;
            } else {
                System.out.println(RED + "Email không đúng định dạng, vui lòng nhập lại: " + RESET);
            }
        }
        return email;
    }

    public static String validatePhone() {
        String phone;
        while (true) {
            phone = validateString();
            //"^0\\d{9,10}$"
            if (phone.matches("(84|0[3|5|7|8|9])+([0-9]{8})\\b")) {
                break;
            } else {
                System.out.println(RED + "Số điện thoại bắt đầu bằng 84/03/05/07/08/09 và có độ dài 10 hoặc 11 số, vui lòng nhập lại: " + RESET);
            }
        }
        return phone;
    }

    public static String validatePassword() {
        String password;
        while (true) {
            password = validateString();
            if (password.length() < 4 || password.length() > 10) {
                System.out.println(RED + "Mật khẩu có độ dài từ 4-10 ký tự, vui lòng nhập lại" + RESET);
            } else {
                break;
            }
        }
        return password;
    }
}
