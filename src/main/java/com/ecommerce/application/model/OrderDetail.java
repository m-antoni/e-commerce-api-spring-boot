package com.ecommerce.application.model;

import javax.persistence.*;

@Entity
@Table(name = "order_details")
public class OrderDetail {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long product_id;
    private String sku;
    private Integer quantity;
    private Long price;

    public OrderDetail() {
    }

    public OrderDetail(Long product_id, String sku, Long price, Integer quantity) {
        this.product_id = product_id;
        this.sku = sku;
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

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
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
                ", sku='" + sku + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }
}
