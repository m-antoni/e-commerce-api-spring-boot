package com.ecommerce.application.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "order_details")
public class OrderDetail extends BaseEntity{

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long user_id;
    private Long order_no;
    private Long total_amount;
    private String order_status = "PENDING";

    // One to Many
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "orderDetail", cascade = CascadeType.ALL, orphanRemoval = true, targetEntity = OrderItem.class)
    private Set<OrderItem> order_items = new HashSet<>();

    // One to one
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "orderDetail", cascade = CascadeType.ALL)
    private DeliveryAddress delivery_address;

    // One to one
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "paymentDetail", cascade = CascadeType.ALL)
    private PaymentDetail paymentDetail;

    public OrderDetail() {
    }

    public OrderDetail(Long user_id, Long total_amount, Long order_no) {
        this.user_id = user_id;
        this.total_amount = total_amount;
        this.order_no = order_no;
    }

    public PaymentDetail getPaymentDetail() {
        return paymentDetail;
    }

    public void setPaymentDetail(PaymentDetail paymentDetail) {
        this.paymentDetail = paymentDetail;
    }

    public Set<OrderItem> getOrder_items() {
        return order_items;
    }

    public void setOrder_items(Set<OrderItem> order_items) {
        this.order_items = order_items;
    }

    public DeliveryAddress getDelivery_address() {
        return delivery_address;
    }

    public void setDelivery_address(DeliveryAddress delivery_address) {
        this.delivery_address = delivery_address;
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

    public Long getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(Long total_amount) {
        this.total_amount = total_amount;
    }

    public Long getOrder_no() {
        return order_no;
    }

    public void setOrder_no(Long order_no) {
        this.order_no = order_no;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    @Override
    public String toString() {
        return "OrderDetail{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", order_no=" + order_no +
                ", total_amount=" + total_amount +
                '}';
    }
}
