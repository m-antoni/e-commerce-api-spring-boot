package com.ecommerce.application.model;

import javax.persistence.*;

@Entity
@Table(name = "cart")
public class Cart {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long product_id;
    private Integer quantity;
    private Long price;

    public Cart() {
    }

    public Cart(Long product_id, Long price, Integer quantity) {
        this.product_id = product_id;
        this.price = price;
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

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "OrderDetail{" +
                "id=" + id +
                ", product_id=" + product_id +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }
}
