package com.ecommerce.application.repository;

import com.ecommerce.application.model.PaymentDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PaymentDetailRepository extends JpaRepository<PaymentDetail, Long> {
    @Query(value = "SELECT * FROM payment_details WHERE order_id = ?1", nativeQuery = true)
    List<PaymentDetail> findByOrderId(Long order_id);
}
