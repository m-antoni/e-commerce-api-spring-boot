package com.ecommerce.application.model;

import javax.persistence.*;

@Entity
@Table(name = "vouchers")
public class Voucher extends BaseEntity{

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String voucher_code;
    private Long discount;
    private Long available;

    public Voucher() {
    }

    public Voucher(String voucher_code, Long discount, Long available) {
        this.voucher_code = voucher_code;
        this.discount = discount;
        this.available = available;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVoucher_code() {
        return voucher_code;
    }

    public void setVoucher_code(String voucher_code) {
        this.voucher_code = voucher_code;
    }

    public Long getDiscount() {
        return discount;
    }

    public void setDiscount(Long discount) {
        this.discount = discount;
    }

    public Long getAvailable() {
        return available;
    }

    public void setAvailable(Long available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return "Vouchers{" +
                "id=" + id +
                ", voucher_code='" + voucher_code + '\'' +
                ", discount=" + discount +
                ", available=" + available +
                '}';
    }
}
