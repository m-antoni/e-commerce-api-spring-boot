package com.ecommerce.application.model;

import javax.persistence.*;

@Entity
@Table(name = "order_items")
public class OrderItem extends BaseEntity{

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long product_id;
    private Long order_id;
    private Integer quantity;





    public OrderItem() {
    }

    public OrderItem(Long product_id, Long order_id, Integer quantity) {
        this.product_id = product_id;
        this.order_id = order_id;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Long product_id) {
        this.product_id = product_id;
    }

    public Long getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Long order_id) {
        this.order_id = order_id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }


    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", product_id=" + product_id +
                ", order_id=" + order_id +
                ", quantity=" + quantity +
                '}';
    }
}
