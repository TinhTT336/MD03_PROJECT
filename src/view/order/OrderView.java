package view.order;

import config.StringFormatter;
import config.Validation;
import constant.FileName;
import constant.OrderStatus;
import model.category.Category;
import model.order.Order;
import model.product.Product;
import model.user.User;
import service.Service;
import view.product.ProductView;
import view.user.HomeView;

import java.util.Map;

import static config.Color.*;

public class OrderView {
    private Service<Order, Long> orderService;
    private Service<Product, Long> productService;
    private Service<Category, Long> categoryService;
    private Service<User, Long> userService;

    public OrderView() {
        this.orderService = new Service<>(FileName.ORDER);
        this.productService = new Service<>(FileName.PRODUCT);
        this.categoryService = new Service<>(FileName.CATEGORY);
        this.userService = new Service<>(FileName.USER);
    }

    public void showOrderManagement() {
        do {
            System.out.println(PURPLE + "+------------------------------------------------------------------------------------------------+");
            System.out.printf("|" + WHITE_BOLD_BRIGHT + "   TMESTICS   \uD83D\uDC8B(¯`•.¸.•´¯)\uD83D\uDC84                                   Xin chào: %-28s\n", HomeView.userLogin.getFullName() + PURPLE + "                 |");
            System.out.println("+------------------------------------------------------------------------------------------------+");
            System.out.println("|     " + WHITE_BOLD_BRIGHT + "                               \uD83D\uDCD7 QUẢN LÝ ĐƠN HÀNG                                         " + PURPLE + "|");
            System.out.println("+------------------------------------------------------------------------------------------------+");
            System.out.println("|" + RESET + "                                 1. \uD83D\uDC41️ DANH SÁCH ĐƠN HÀNG                                       " + PURPLE + "|");
            System.out.println("|" + RESET + "                                 2. ✅️ XÁC NHẬN ĐƠN HÀNG                                        " + PURPLE + "|");
            System.out.println("|" + RESET + "                                 3. ❌️ HUỶ ĐƠN HÀNG                                             " + PURPLE + "|");
            System.out.println("|" + RESET + "                                 4. \uD83D\uDD0D TÌM KIẾM ĐƠN HÀNG                                        " + PURPLE + "|");
            System.out.println("|" + RESET + "                                 5. \uD83D\uDD00 SẮP XẾP ĐƠN HÀNG THEO TỔNG TIỀN                          " + PURPLE + "|");
            System.out.println("|" + RESET + "                                 6. \uD83D\uDD3D LỌC ĐƠN HÀNG THEO TRẠNG THÁI                             " + PURPLE + "|");
            System.out.println("|" + RESET + "                                 0. ↩️ QUAY LẠI                                                 " + PURPLE + "|");
            System.out.println("+------------------------------------------------------------------------------------------------+\n" + RESET);
            System.out.println("Nhập lựa chọn: ");

            switch (Validation.validateInt()) {
                case 1:
                    showOrder();
                    showOrderDetail();
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 0:
                    return;
                default:
                    System.out.println(RED + "Không có chức năng phù hợp, vui lòng chọn lại" + RESET);
                    break;
            }
        } while (true);
    }

    private void confirmOrder(Order order) {
        order.setOrderStatus(OrderStatus.CONFIRM);
        System.out.println(PURPLE + "Đã xác nhận đơn hàng!" + RESET);
        orderService.save(order);
    }

    private void cancelOrder(Order order) {
        System.out.println("Bạn có chắc chắn muốn huỷ đơn hàng?");
        System.out.println(PURPLE + "+--------------------------------------------------------+");
        System.out.println("|" + RESET + " 1. HUỶ ĐƠN HÀNG         |    0. QUAY LẠI           " + PURPLE + "|");
        System.out.println("+--------------------------------------------------------+" + RESET);

        switch (Validation.validateInt()) {
            case 1:
                if (!(order.getOrderStatus().equals(OrderStatus.DELIVERY) && order.getOrderStatus().equals(OrderStatus.SUCCESS))) {
                    order.setOrderStatus(OrderStatus.CANCEL);
                    System.out.println(PURPLE + "Đã huỷ đơn hàng!" + RESET);
                    orderService.save(order);

                    //tru so luong da mua hang trong ProductList
                    for (Map.Entry<Product, Integer> entry : order.getOrdersDetail().entrySet()) {
                        for (Product product : productService.findAll()) {
                            if (entry.getKey().equals(product)) {
                                if (product.getStock() == 0) {
                                    product.setStatus(!product.isStatus());
                                }
                                product.setStock(product.getStock() + entry.getValue());
                                productService.save(product);
                            }
                        }
                    }
                }
                ;
                break;
            case 2:
                return;
            default:
                System.out.println(RED + "Không có chức năng phù hợp, vui lòng chọn lại" + RESET);
                break;
        }
    }

    private void showOrderDetail() {
        System.out.println("Các chức năng để lựa chọn: ");
        System.out.println(PURPLE + "+--------------------------------------------------------+");
        System.out.println("|" + RESET + " 1. XEM CHI TIẾT ĐƠN HÀNG    |    0. QUAY LẠI           " + PURPLE + "|");
        System.out.println("+--------------------------------------------------------+" + RESET);
        System.out.println("Nhập lựa chọn: ");
        switch (Validation.validateInt()) {
            case 1:
                System.out.println("Nhâp mã đơn hàng muốn xem chi tiết");
                int orderId = Validation.validateInt();

                Order orderDetail = orderService.findById((long) orderId);

                if (orderDetail == null) {
                    System.out.println(RED + "  Không có đơn hàng với mã vừa nhập!!!" + RESET);
                } else {
                    System.out.println(WHITE_BOLD_BRIGHT + "\nCHI TIẾT ĐƠN HÀNG                        " + RESET);
                    new ProductView().showTLine();
                    System.out.printf(" ID Hoá đơn               :  %-24s  \n", orderDetail.getId());
                    System.out.printf(" Người đặt hàng           :  %-24s  |  Người nhận hàng      :  %-24s  \n", userService.findById(orderDetail.getUserId()).getFullName(), orderDetail.getName());
                    System.out.printf(" Số điện thoại nhận hàng  :  %-24s  |  Địa chỉ giao hàng    :  %-24s  \n", orderDetail.getPhoneNumber(), orderDetail.getAddress());
                    System.out.printf(" Tổng giá trị đơn hàng    :  %-24s  |  Trạng thái đơn hàng  :  %-24s  \n", orderDetail.getTotal(), orderDetail.getOrderStatus());
                    System.out.printf(" Ngày đặt hàng            :  %-24s  |  Ngày giao hàng       :  %-24s  \n", StringFormatter.getCurrentYearMonth(orderDetail.getOrderAt()), StringFormatter.getCurrentYearMonth(orderDetail.getDeliverAt()));

                    System.out.println(PURPLE + "+---------------------------------------------------------------------------------------------------------------------------------+ " + RESET);
                    System.out.println(PURPLE + "|" + RESET + "  ID |                TÊN                 |  DANH MỤC  |                MÔ TẢ             | ĐƠN GIÁ(VND) | SỐ LƯỢNG | TRẠNG THÁI" + PURPLE + "|");
                    showTLine();
                    for (Product key : orderDetail.getOrdersDetail().keySet()) {
                        System.out.printf(PURPLE + "|" + RESET + " %-4d| %-35s| %-11s| %-33s|   %-10s |     %-4d | %-10s " + PURPLE + "|\n",
                                key.getId(), key.getProductName(), key.getCategory().getCategoryName(), key.getDescription(), StringFormatter.formatCurrency(key.getUnitPrice()), orderDetail.getOrdersDetail().get(key), StringFormatter.formatCurrency(key.getUnitPrice() * orderDetail.getOrdersDetail().get(key)));
                    }
                    new ProductView().showTLine();
                    System.out.printf(PURPLE + "|" + WHITE_BOLD_BRIGHT + "      TỔNG TIỀN                                                                           |   %-12s   \n", StringFormatter.formatCurrency(orderDetail.getTotal()) + PURPLE + "                            |");
                    new ProductView().showTLine();
                }
                break;
            case 0:
                return;
            default:
                System.out.println(RED + "Không có chức năng phù hợp, vui lòng chọn lại" + RESET);
                break;
        }
    }

    private void showOrder() {
        System.out.println(WHITE_BOLD_BRIGHT + "\nDANH SÁCH ĐƠN HÀNG                        " + RESET);
        showTHead();
        for (Order order : orderService.findAll()) {
            showTBody(order);
        }
        showTLine();
    }

    public void showTHead() {
        System.out.println(PURPLE + "+---------------------------------------------------------------------------------------------------------------------------------+ " + RESET);
        System.out.println(PURPLE + "|" + RESET + "  ID |  NGÀY ĐẶT  | NGƯỜI ĐẶT HÀNG | NGƯỜI NHẬN HÀNG | SỐ ĐIỆN THOẠI |  ĐỊA CHỈ GIAO HÀNG| TỔNG TIỀN  |  NGÀY NHẬN | TRẠNG THÁI  " + PURPLE + "|");
        showTLine();
    }

    public void showTBody(Order order) {
        System.out.printf(PURPLE + "|" + RESET + " %-4d| %-10s | %-14s | %-15s |  %-11s  | %-18s| %-11s| %-10s | %-12s" + PURPLE + "|\n",
                order.getId(), StringFormatter.getCurrentYearMonth(order.getOrderAt()), userService.findById(order.getUserId()).getFullName(), order.getName(), order.getPhoneNumber(), order.getAddress(), StringFormatter.formatCurrency(order.getTotal()), StringFormatter.getCurrentYearMonth(order.getDeliverAt()), order.getOrderStatus());
    }

    public void showTLine() {
        System.out.println(PURPLE + "+---------------------------------------------------------------------------------------------------------------------------------+ " + RESET);
    }
}
