
package com.ecommerce.application.repository;

import com.ecommerce.application.model.CartItem;
import com.ecommerce.application.model.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VoucherRepository extends JpaRepository<Voucher, Long> {
    @Query(value = "SELECT * FROM vouchers WHERE voucher_code = ?1", nativeQuery = true)
    List<Voucher> findByVoucherCode(String voucher_code);
}





