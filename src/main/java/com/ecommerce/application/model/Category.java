package com.ecommerce.application.model;

import javax.persistence.*;
import java.util.*;

@Entity()
@Table(name = "categories")
public class Category extends BaseEntity{

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true, targetEntity = Product.class)
    private Set<Product> product_list = new HashSet<>();

    public Category() { }

    public Category(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Set<Product> getProduct_list() {
        return product_list;
    }

    public void setProduct_list(Set<Product> product_list) {
        this.product_list = product_list;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Categories{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
