package com.ecommerce.application.repository;

import com.ecommerce.application.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    // Order By ID DESC
    List<OrderDetail> findAllByOrderByIdDesc();
}
