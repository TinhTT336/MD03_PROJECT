package view.order;

import config.StringFormatter;
import config.Validation;
import constant.FileName;
import constant.OrderStatus;
import constant.Role;
import model.order.Order;
import model.product.Product;
import model.user.User;
import service.Service;
import view.product.ProductView;
import view.user.HomeView;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static config.Color.*;

public class OrderView {
    private final Service<Order, Long> orderService;
    private final Service<Product, Long> productService;
    private final Service<User, Long> userService;

    public OrderView() {
        this.orderService = new Service<>(FileName.ORDER);
        this.productService = new Service<>(FileName.PRODUCT);
        this.userService = new Service<>(FileName.USER);
    }

    public void showOrderManagement() {
        do {
            System.out.println(PURPLE + "+------------------------------------------------------------------------------------------------+");
            System.out.printf("|" + WHITE_BOLD_BRIGHT + "   TMESTICS   \uD83D\uDC8B(¯`•.¸.•´¯)\uD83D\uDC84                                   Xin chào: %-49s\n", HomeView.userLogin.getFullName() + PURPLE + "                 |");
            System.out.println("+------------------------------------------------------------------------------------------------+");
            System.out.println("|     " + WHITE_BOLD_BRIGHT + "                               \uD83D\uDCD7 QUẢN LÝ ĐƠN HÀNG                                         " + PURPLE + "|");
            System.out.println("+------------------------------------------------------------------------------------------------+");
            System.out.println("|" + RESET + "                                 1. \uD83D\uDC41️ DANH SÁCH ĐƠN HÀNG                                       " + PURPLE + "|");
            System.out.println("|" + RESET + "                                 2. ✅️ XÁC NHẬN ĐƠN HÀNG                                        " + PURPLE + "|");
            System.out.println("|" + RESET + "                                 3. ❌️ HUỶ ĐƠN HÀNG                                             " + PURPLE + "|");
            System.out.println("|" + RESET + "                                 4. \uD83D\uDCDD CẬP NHẬT ĐƠN HÀNG ĐANG GIAO                              " + PURPLE + "|");
            System.out.println("|" + RESET + "                                 5. \uD83D\uDD00 SẮP XẾP ĐƠN HÀNG THEO TỔNG TIỀN                          " + PURPLE + "|");
            System.out.println("|" + RESET + "                                 6. \uD83D\uDD3D LỌC ĐƠN HÀNG THEO TRẠNG THÁI                             " + PURPLE + "|");
            System.out.println("|" + RESET + "                                 7. \uD83D\uDD0E TÌM KIẾM ĐƠN HÀNG THEO TÊN                               " + PURPLE + "|");
            System.out.println("|" + RESET + "                                 0. ↩️ QUAY LẠI                                                 " + PURPLE + "|");
            System.out.println("+------------------------------------------------------------------------------------------------+\n" + RESET);
            System.out.println("Nhập lựa chọn: ");

            switch (Validation.validateInt()) {
                case 1:
                    List<Order> orders = orderService.findAll().stream().sorted((o1, o2) -> -o1.getOrderAt().compareTo(o2.getOrderAt())).collect(Collectors.toList());
                    System.out.println(WHITE_BOLD_BRIGHT + "\nDANH SÁCH ĐƠN HÀNG                        " + RESET);
                    showOrderList(orders);
                    if (!orderService.findAll().isEmpty()) {
                        showOrderDetail();
                    }
                    break;
                case 2:
                    confirmOrder();
                    break;
                case 3:
                    cancelOrder();
                    break;
                case 4:
                    updateOrderToDelivery();
                    break;
                case 5:
                    sortOrderByTotal();
                    break;
                case 6:
                    sortOrderByStatus();
                    break;
                case 7:
                    searchOrderByUserName();
                    break;
                case 0:
                    return;
                default:
                    System.out.println(RED + "Không có chức năng phù hợp, vui lòng chọn lại" + RESET);
                    break;
            }
        } while (true);
    }

    private void searchOrderByUserName() {
        List<User> userList = new ArrayList<>();
        System.out.println("Nhập tên tài khoản muốn tìm kiếm: ");
        String name = Validation.validateString();
        for (User user : userService.findAll()) {
            if (user.getUsername().toLowerCase().contains(name.toLowerCase())&&user.getRole().equals(Role.USER)) {
                userList.add(user);
            }
        }
        if (userList.isEmpty()) {
            System.out.println(RED + "Không có đơn hàng với tên tài khoản " + "'" + name + "'" + " vừa nhập!!!" + RESET);
        } else {
            List<Order>orderList=new ArrayList<>();
            System.out.println(WHITE_BOLD_BRIGHT + "DANH SÁCH ĐƠN HÀNG TÌM KIẾM " + RESET);
            showTHead();
            for (Order order : orderService.findAll()) {
                for (User user : userList) {
                    if (order.getUserId().equals(user.getId())) {
                        showTBody(order);
                        orderList.add(order);
                    }
                }
            }
            new ProductView().showTLine();
            if(orderList.isEmpty()){
                System.out.println(PURPLE + "|" + RED + " Không có đơn hàng nào!!!" + RESET);
                new ProductView().showTLine();
            }else{
                System.out.println(PURPLE_BRIGHT + " Tìm được " + orderList.size() + " đơn hàng với tên tài khoản " + "'" + name + "'" + RESET);
            }
        }
    }

    private void updateOrderToDelivery() {
        System.out.println(WHITE_BOLD_BRIGHT + "DANH SÁCH ĐƠN HÀNG ĐÃ XÁC NHẬN: " + RESET);
        showOrderList(getOrdersByStatus(OrderStatus.CONFIRM));

//        System.out.println(PURPLE + "                                              +--------------------+" + RESET);
//        System.out.println("Nhập mã đơn hàng muốn kiểm tra/ hoặc chọn:    " + PURPLE + "|" + WHITE_BRIGHT + "    0. QUAY LẠI     " + PURPLE + "|");
//        System.out.println(PURPLE + "                                              +--------------------+" + RESET);
//
//        int confirmOrderId = Validation.validateInt();
//        Order order = orderService.findById((long) confirmOrderId);
//        if (order == null) {
//            System.out.println(RED + "Không có đơn hàng với mã vừa nhập!!!" + RESET);
//            return;
//        }
//
//        if (findOrder(order, getOrdersByStatus(OrderStatus.CONFIRM))) {
//            System.out.println(RED + "Không có đơn hàng với mã vừa nhập!!!" + RESET);
//            return;
//        }
//        if (order.getDeliverAt().isEqual(LocalDateTime.now()) || LocalDateTime.now().isAfter(order.getDeliverAt())) {
//            order.setOrderStatus(OrderStatus.DELIVERY);
//            orderService.save(order);
//            System.out.println(PURPLE_BRIGHT + "Đơn hàng " + order.getId() + " đang được giao!" + RESET);
//        } else {
//            System.out.println(RED + "Trạng thái đơn hàng " + order.getId() + " và thời gian giao hàng không phù hợp, vui lòng kiểm tra lại!!!" + RESET);
//        }

        for (Order order1 : getOrdersByStatus(OrderStatus.CONFIRM)) {
            if (order1.getDeliverAt().isEqual(LocalDateTime.now()) || LocalDateTime.now().isAfter(order1.getDeliverAt())) {
                order1.setOrderStatus(OrderStatus.DELIVERY);
                orderService.save(order1);
                System.out.println(PURPLE_BRIGHT + "Đơn hàng " + order1.getId() + " đang được giao!" + RESET);
            } else {
                System.out.println(RED + "Trạng thái đơn hàng hoặc thời gian giao hàng " + order1.getId() + " không phù hợp, vui lòng kiểm tra lại!!!" + RESET);
            }
        }
    }

    private int calculateOrderByStatus(OrderStatus orderStatus) {
        return (int) orderService.findAll().stream().filter(o -> o.getOrderStatus().equals(orderStatus)).count();
    }

    private List<Order> getOrdersByStatus(OrderStatus orderStatus) {
        return orderService.findAll().stream().filter(o -> o.getOrderStatus().equals(orderStatus)).collect(Collectors.toList());
    }

    private void sortOrderByStatus() {
        System.out.println(WHITE_BOLD_BRIGHT + "\nSỐ LƯỢNG ĐƠN HÀNG THEO TRẠNG THÁI                        " + RESET);
        System.out.println(PURPLE + "+---------------------------------------------------------+");
        System.out.println("|" + WHITE_BRIGHT + "       TRẠNG THÁI ĐƠN HÀNG        |  SỐ LƯỢNG ĐƠN HÀNG   " + PURPLE + "|");
        System.out.println(PURPLE + "+---------------------------------------------------------+");
        System.out.printf("|" + RESET + "             1. %-18s|          %-18s                       \n", OrderStatus.WAITING, calculateOrderByStatus(OrderStatus.WAITING) + PURPLE + "           |");
        System.out.printf("|" + RESET + "             2. %-18s|          %-18s                       \n", OrderStatus.CONFIRM, calculateOrderByStatus(OrderStatus.CONFIRM) + PURPLE + "           |");
        System.out.printf("|" + RESET + "             3. %-18s|          %-18s                       \n", OrderStatus.DELIVERY, calculateOrderByStatus(OrderStatus.DELIVERY) + PURPLE + "           |");
        System.out.printf("|" + RESET + "             4. %-18s|          %-18s                       \n", OrderStatus.SUCCESS, calculateOrderByStatus(OrderStatus.SUCCESS) + PURPLE + "           |");
        System.out.printf("|" + RESET + "             5. %-18s|          %-18s                       \n", OrderStatus.CANCEL, calculateOrderByStatus(OrderStatus.CANCEL) + PURPLE + "           |");
        System.out.println(PURPLE + "+---------------------------------------------------------+" + RESET);

        System.out.println(PURPLE + "                                                                 +--------------------+" + RESET);
        System.out.println("Nhập lựa chọn để hiển thị danh sách đơn hàng theo trạng thái:    " + PURPLE + "|" + WHITE_BRIGHT + "    0. QUAY LẠI     " + PURPLE + "|");
        System.out.println(PURPLE + "                                                                 +--------------------+" + RESET);
        switch (Validation.validateInt()) {
            case 1:
                System.out.println(WHITE_BOLD_BRIGHT + "DANH SÁCH ĐƠN HÀNG CHỜ XÁC NHẬN: " + RESET);
                showOrderList(getOrdersByStatus(OrderStatus.WAITING));
                break;
            case 2:
                System.out.println(WHITE_BOLD_BRIGHT + "DANH SÁCH ĐƠN HÀNG ĐÃ XÁC NHẬN: " + RESET);
                showOrderList(getOrdersByStatus(OrderStatus.CONFIRM));
                break;
            case 3:
                System.out.println(WHITE_BOLD_BRIGHT + "DANH SÁCH ĐƠN HÀNG ĐANG GIAO HÀNG: " + RESET);
                showOrderList(getOrdersByStatus(OrderStatus.DELIVERY));
                break;
            case 4:
                System.out.println(WHITE_BOLD_BRIGHT + "DANH SÁCH ĐƠN HÀNG ĐÃ GIAO HÀNG: " + RESET);
                showOrderList(getOrdersByStatus(OrderStatus.SUCCESS));
                break;
            case 5:
                System.out.println(WHITE_BOLD_BRIGHT + "DANH SÁCH ĐƠN HÀNG ĐÃ HUỶ: " + RESET);
                showOrderList(getOrdersByStatus(OrderStatus.CANCEL));
                break;
            case 0:
                return;
            default:
                System.out.println(RED + "Không có chức năng phù hợp, vui lòng chọn lại" + RESET);
                break;
        }
    }

    private void sortOrderByTotal() {
        System.out.println("Lựa chọn cách sắp xếp đơn hàng theo giá: ");
        System.out.println(PURPLE + "+---------------------------------------------------------------------------------+");
        System.out.println("|" + WHITE_BRIGHT + "   1. GIÁ TĂNG DẦN          |    2. GIÁ GIẢM DẦN        |    0. QUAY LẠI         " + PURPLE + "|");
        System.out.println(PURPLE + "+---------------------------------------------------------------------------------+" + RESET);

        System.out.println("Nhập lựa chọn: ");
        switch (Validation.validateInt()) {
            case 1:
                List<Order> orderListIncreasePrice = orderService.findAll().stream().sorted((o1, o2) -> (int) (o1.getTotal() - o2.getTotal())).collect(Collectors.toList());
                showOrderList(orderListIncreasePrice);
                break;
            case 2:
                List<Order> orderListDecreasePrice = orderService.findAll().stream().sorted((o1, o2) -> (int) -(o1.getTotal() - o2.getTotal())).collect(Collectors.toList());
                showOrderList(orderListDecreasePrice);
                break;
            case 0:
                return;
            default:
                System.out.println(RED + "Không có chức năng phù hợp, vui lòng chọn lại" + RESET);
                break;
        }
    }

    private boolean findOrder(Order order, List<Order> orderList) {
        for (Order order1 : orderList) {
            if (order.getId().equals(order1.getId())) {
                return false;
            }
        }
        return true;
    }

    private void confirmOrder() {
        List<Order> waitingOrder = getOrdersByStatus(OrderStatus.WAITING);
        System.out.println(WHITE_BOLD_BRIGHT + "DANH SÁCH ĐƠN HÀNG CHỜ XÁC NHẬN: " + RESET);
        showOrderList(waitingOrder);
        System.out.println(PURPLE + "                                              +--------------------+" + RESET);
        System.out.println("Nhập mã đơn hàng muốn xác nhận/ hoặc chọn:    " + PURPLE + "|" + WHITE_BRIGHT + "    0. QUAY LẠI     " + PURPLE + "|");
        System.out.println(PURPLE + "                                              +--------------------+" + RESET);

        int confirmOrderId = Validation.validateInt();
        if (confirmOrderId == 0) {
            return;
        }
        Order confirmOrder = orderService.findById((long) confirmOrderId);
        if (confirmOrder == null) {
            System.out.println(RED + "Không có đơn hàng với mã vừa nhập!!!" + RESET);
            return;
        }

        if (findOrder(confirmOrder, waitingOrder)) {
            System.out.println(RED + "Đơn hàng không có trạng thái đang chờ xác nhận!!!" + RESET);
        } else {
            confirmOrder.setOrderStatus(OrderStatus.CONFIRM);
            System.out.println(PURPLE_BRIGHT + "Đã xác nhận đơn hàng!" + RESET);
            orderService.save(confirmOrder);
        }
    }

    private void cancelOrder() {
        List<Order> cancelOrders = getOrdersByStatus(OrderStatus.WAITING);
        List<Order> orders2 = getOrdersByStatus(OrderStatus.CONFIRM);
        cancelOrders.addAll(orders2);
        System.out.println(WHITE_BOLD_BRIGHT + "DANH SÁCH ĐƠN HÀNG CÓ THỂ HUỶ: " + RESET);
        showOrderList(cancelOrders);

        System.out.println(PURPLE + "                                              +--------------------+" + RESET);
        System.out.println("Nhập mã đơn hàng muốn huỷ/ hoặc chọn:         " + PURPLE + "|" + WHITE_BRIGHT + "    0. QUAY LẠI     " + PURPLE + "|");
        System.out.println(PURPLE + "                                              +--------------------+" + RESET);

        int calOrderId = Validation.validateInt();
        if (calOrderId == 0) {
            return;
        }
        Order calOrder = orderService.findById((long) calOrderId);

        if (calOrder == null) {
            System.out.println(RED + "Không có đơn hàng với mã vừa nhập!!!" + RESET);
            return;
        }
        if (findOrder(calOrder, cancelOrders)) {
            System.out.println(RED + "Đơn hàng không thể huỷ!!!" + RESET);
        } else {
            System.out.println("Bạn có chắc chắn muốn huỷ đơn hàng?");
            System.out.println(PURPLE + "+--------------------------------------------------------+");
            System.out.println("|" + WHITE_BRIGHT + "     1. HUỶ ĐƠN HÀNG         |    0. QUAY LẠI           " + PURPLE + "|");
            System.out.println("+--------------------------------------------------------+" + RESET);

            switch (Validation.validateInt()) {
                case 1:
                    calOrder.setOrderStatus(OrderStatus.CANCEL);
                    System.out.println(PURPLE_BRIGHT + "Đã huỷ đơn hàng!" + RESET);
                    orderService.save(calOrder);

                    //cong so luong da mua hang trong ProductList
                    for (Map.Entry<Product, Integer> entry : calOrder.getOrdersDetail().entrySet()) {
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
                    break;
                case 2:
                    return;
                default:
                    System.out.println(RED + "Không có chức năng phù hợp, vui lòng chọn lại!!!" + RESET);
                    break;
            }
        }
    }

    public void showOrderDetail() {
        System.out.println("Các chức năng để lựa chọn: ");
        System.out.println(PURPLE + "+--------------------------------------------------------+");
        System.out.println("|" + WHITE_BRIGHT + " 1. XEM CHI TIẾT ĐƠN HÀNG    |    0. QUAY LẠI           " + PURPLE + "|");
        System.out.println("+--------------------------------------------------------+" + RESET);
        System.out.println("Nhập lựa chọn: ");
        switch (Validation.validateInt()) {
            case 1:
                System.out.println("Nhâp mã đơn hàng muốn xem chi tiết");
                int orderId = Validation.validateInt();
                Order orderDetail = orderService.findById((long) orderId);

                if (orderDetail == null) {
                    System.out.println(RED + "Không có đơn hàng với mã vừa nhập!!!" + RESET);
                } else {
                    System.out.println(WHITE_BOLD_BRIGHT + "\nCHI TIẾT ĐƠN HÀNG                        " + RESET);
                    new ProductView().showTLine();
                    System.out.printf(" ID Hoá đơn               :  %-24s  \n", orderDetail.getId());
                    System.out.printf(" Người đặt hàng           :  %-24s  |  Người nhận hàng      :  %-24s  \n", userService.findById(orderDetail.getUserId()).getFullName(), orderDetail.getName());
                    System.out.printf(" Số điện thoại nhận hàng  :  %-24s  |  Địa chỉ giao hàng    :  %-24s  \n", orderDetail.getPhoneNumber(), orderDetail.getAddress());
                    System.out.printf(" Tổng giá trị đơn hàng    :  %-24s  |  Trạng thái đơn hàng  :  %-24s  \n", StringFormatter.formatCurrency(orderDetail.getTotal()), changeOrderStatusColor(orderDetail.getOrderStatus()));
                    System.out.printf(" Ngày đặt hàng            :  %-24s  |  Ngày giao hàng       :  %-24s  \n", StringFormatter.getCurrentYearMonth(orderDetail.getOrderAt()), StringFormatter.getCurrentYearMonth(orderDetail.getDeliverAt()));
                    new ProductView().showTLine();
                    System.out.println(PURPLE + "|" + WHITE_BRIGHT + "  ID |                TÊN                 |  DANH MỤC  |              MÔ TẢ           | ĐƠN GIÁ(VND) | SỐ LƯỢNG |  THÀNH TIỀN   " + PURPLE + " |");
                    new ProductView().showTLine();
                    for (Product key : orderDetail.getOrdersDetail().keySet()) {
                        System.out.printf(PURPLE + "|" + RESET + " %-4d| %-35s| %-11s| %-29s|   %-10s |     %-4d | %-14s " + PURPLE + "|\n", key.getId(), key.getProductName(), key.getCategory().getCategoryName(), key.getDescription(), StringFormatter.formatCurrency(key.getUnitPrice()), orderDetail.getOrdersDetail().get(key), StringFormatter.formatCurrency(key.getUnitPrice() * orderDetail.getOrdersDetail().get(key)));
                    }
                    new ProductView().showTLine();
                    System.out.printf(PURPLE + "|" + WHITE_BOLD_BRIGHT + "      TỔNG TIỀN                                                                       |   %-14s     \n", StringFormatter.formatCurrency(orderDetail.getTotal()) + PURPLE + "                              |");
                    new ProductView().showTLine();
//                    if (HomeView.userLogin.getRole().equals(Role.USER) &&orderDetail.getOrderStatus().equals(OrderStatus.CONFIRM) && orderDetail.getDeliverAt().isEqual(LocalDateTime.now()) ||HomeView.userLogin.getRole().equals(Role.USER) &&orderDetail.getOrderStatus().equals(OrderStatus.CONFIRM) && LocalDateTime.now().isBefore(orderDetail.getDeliverAt())) {
//                        orderDetail.setOrderStatus(OrderStatus.DELIVERY);
//                        orderService.save(orderDetail);
//                        System.out.println(PURPLE_BRIGHT + "Đơn hàng " + orderDetail.getId() + " đang được giao!" + RESET);
//                    }
                    if (HomeView.userLogin.getRole().equals(Role.USER) && orderDetail.getOrderStatus().equals(OrderStatus.DELIVERY)) {
                        confirmOrderDelivered(orderDetail);
                        return;
                    }
                }
                break;
            case 0:
                return;
            default:
                System.out.println(RED + "Không có chức năng phù hợp, vui lòng chọn lại!!!" + RESET);
                break;
        }
    }

    private void confirmOrderDelivered(Order order) {
        System.out.println("Bạn đã nhận được đơn hàng? ");
        System.out.println(PURPLE + "+--------------------------------------------------------+");
        System.out.println("|" + WHITE_BRIGHT + "      1. ĐÃ NHẬN HÀNG        |    0. QUAY LẠI           " + PURPLE + "|");
        System.out.println("+--------------------------------------------------------+" + RESET);
        System.out.println("Nhập lựa chọn: ");
        switch (Validation.validateInt()) {
            case 1:
                order.setOrderStatus(OrderStatus.SUCCESS);
                System.out.println(PURPLE_BRIGHT + "Cảm ơn bạn đã xác nhận đơn hàng!" + RESET);
                orderService.save(order);
                break;
            case 0:
                return;
            default:
                System.out.println(RED + "Không có chức năng phù hợp, vui lòng chọn lại!!!" + RESET);
                break;
        }
    }

    public void showOrderList(List<Order> orderList) {
//        System.out.println(WHITE_BOLD_BRIGHT + "\nDANH SÁCH ĐƠN HÀNG                        " + RESET);
        showTHead();
        if (orderList.isEmpty()) {
            System.out.println(PURPLE + "|" + RED + " Không có đơn hàng nào!!!" + RESET);
        } else {
            for (Order order : orderList) {
                showTBody(order);
            }
        }
        new ProductView().showTLine();

    }

    public String changeOrderStatusColor(OrderStatus orderStatus) {
        if (orderStatus.equals(OrderStatus.WAITING)) {
            return WHITE + "CHỜ XÁC NHẬN" + RESET;
        } else if (orderStatus.equals(OrderStatus.CONFIRM)) {
            return YELLOW_BRIGHT + "ĐÃ XÁC NHẬN" + RESET;
        } else if (orderStatus.equals(OrderStatus.CANCEL)) {
            return RED + "BỊ HUỶ" + RESET;
        } else if (orderStatus.equals(OrderStatus.DELIVERY)) {
            return CYAN_BRIGHT + "ĐANG GIAO" + RESET;
        } else if (orderStatus.equals(OrderStatus.SUCCESS)) {
            return PURPLE_BRIGHT + "THÀNH CÔNG" + RESET;
        }
        return null;
    }

    public void showTHead() {
        new ProductView().showTLine();
        System.out.println(PURPLE + "|" + WHITE_BRIGHT + " ID  |  NGÀY ĐẶT  | NGƯỜI ĐẶT HÀNG | NGƯỜI NHẬN HÀNG | SỐ ĐIỆN THOẠI | ĐỊA CHỈ GIAO HÀNG | TỔNG TIỀN  | NGÀY GIAO  | TRẠNG THÁI  " + PURPLE + "|");
        new ProductView().showTLine();
    }

    public void showTBody(Order order) {
        System.out.printf(PURPLE + "|" + RESET + " %-4d| %-10s | %-15s| %-15s |  %-11s  | %-18s| %-11s| %-10s | %-23s" + PURPLE + "|\n", order.getId(), StringFormatter.getCurrentYearMonth(order.getOrderAt()), userService.findById(order.getUserId()).getFullName(), order.getName(), order.getPhoneNumber(), order.getAddress(), StringFormatter.formatCurrency(order.getTotal()), StringFormatter.getCurrentYearMonth(order.getDeliverAt()), changeOrderStatusColor(order.getOrderStatus()));
    }
}
