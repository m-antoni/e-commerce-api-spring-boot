package com.ecommerce.application.model;

public class Order extends BaseEntity{

    private Long id;
    private Long cart_id;
    private Long order_no;
    private Long amount;
    private String shipping_address;
    private String order_status;

    public Order(Long cart_id, Long order_no, Long amount, String shipping_address, String order_status) {
        this.cart_id = cart_id;
        this.order_no = order_no;
        this.amount = amount;
        this.shipping_address = shipping_address;
        this.order_status = order_status;
    }

    public Order() {
    }

    public Long getCart_id() {
        return cart_id;
    }

    public void setCart_id(Long cart_id) {
        this.cart_id = cart_id;
    }

    public Long getOrder_no() {
        return order_no;
    }

    public void setOrder_no(Long order_no) {
        this.order_no = order_no;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getShipping_address() {
        return shipping_address;
    }

    public void setShipping_address(String shipping_address) {
        this.shipping_address = shipping_address;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", product_id=" + cart_id +
                ", order_no=" + order_no +
                ", amount=" + amount +
                ", shipping_address='" + shipping_address + '\'' +
                ", order_status='" + order_status + '\'' +
                '}';
    }
}
