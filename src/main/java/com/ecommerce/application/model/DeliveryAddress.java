package com.ecommerce.application.model;

import javax.persistence.*;

@Entity
@Table(name = "delivery_addresses" )
public class DeliveryAddress extends BaseEntity{

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long order_id;
    private String full_name;
    private String address;
    private Long contact_no;

    public DeliveryAddress() {
    }

    public DeliveryAddress(Long order_id, String full_name, String address, Long contact_no) {
        this.order_id = order_id;
        this.full_name = full_name;
        this.address = address;
        this.contact_no = contact_no;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Long order_id) {
        this.order_id = order_id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getContact_no() {
        return contact_no;
    }

    public void setContact_no(Long contact_no) {
        this.contact_no = contact_no;
    }

    @Override
    public String toString() {
        return "DeliveryAddress{" +
                "id=" + id +
                ", order_id=" + order_id +
                ", full_name='" + full_name + '\'' +
                ", address='" + address + '\'' +
                ", contact_no=" + contact_no +
                '}';
    }

}
