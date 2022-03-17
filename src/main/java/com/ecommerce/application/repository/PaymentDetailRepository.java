package com.ecommerce.application.repository;

import com.ecommerce.application.model.PaymentDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentDetailRepository extends JpaRepository<PaymentDetail, Long> {
}
