package com.ecommerce.application.model;

import javax.persistence.*;

@Entity
@Table(name = "payment_details")
public class PaymentDetail extends BaseEntity {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long user_id;
    private Long order_id;

    @Column(columnDefinition = "integer default 0")
    private Long discount;

    @Column(columnDefinition = "integer default 150")
    private Long delivery_fee;

    private String payment_type; // COD, ONLINE BANKING
    private String payment_status; // UNPAID, PAID
    private Long total_amount;

    public PaymentDetail() {
    }

    public PaymentDetail(Long user_id, Long order_id, Long discount, Long delivery_fee, String payment_type, String payment_status, Long total_amount) {
        this.user_id = user_id;
        this.order_id = order_id;
        this.discount = discount;
        this.delivery_fee = delivery_fee;
        this.payment_type = payment_type;
        this.payment_status = payment_status;
        this.total_amount = total_amount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Long getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Long order_id) {
        this.order_id = order_id;
    }

    public Long getDiscount() {
        return discount;
    }

    public void setDiscount(Long discount) {
        this.discount = discount;
    }

    public Long getDelivery_fee() {
        return delivery_fee;
    }

    public void setDelivery_fee(Long delivery_fee) {
        this.delivery_fee = delivery_fee;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }

    public String getPayment_status() {
        return payment_status;
    }

    public void setPayment_status(String payment_status) {
        this.payment_status = payment_status;
    }

    public Long getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(Long total_amount) {
        this.total_amount = total_amount;
    }

    @Override
    public String toString() {
        return "PaymentDetail{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", order_id=" + order_id +
                ", discount=" + discount +
                ", delivery_fee=" + delivery_fee +
                ", payment_type='" + payment_type + '\'' +
                ", payment_status='" + payment_status + '\'' +
                ", total_amount=" + total_amount +
                '}';
    }
}
