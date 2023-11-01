package constant;

public enum OrderStatus {
    WAITING("waiting"), CONFIRM("confirm"), DELIVERY("delivery"),SUCCESS("success"), CANCEL("cancel");
    private String orderStatusName;

    OrderStatus(String orderStatusName) {
        this.orderStatusName = orderStatusName;
    }

    public String getOrderStatusName() {
        return orderStatusName;
    }

    public void setOrderStatusName(String orderStatusName) {
        this.orderStatusName = orderStatusName;
    }
}
