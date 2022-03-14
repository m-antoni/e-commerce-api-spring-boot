package com.ecommerce.application.model;

import javax.persistence.*;

@Entity
@Table(name = "order_details")
public class OrderDetail extends BaseEntity{

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long user_id;
    private Long total_amount;

    public OrderDetail() {
    }

    public OrderDetail(Long user_id, Long total_amount) {
        this.user_id = user_id;
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

    public Long getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(Long total_amount) {
        this.total_amount = total_amount;
    }

    @Override
    public String toString() {
        return "OrderDetail{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", total_amount=" + total_amount +
                '}';
    }
}
