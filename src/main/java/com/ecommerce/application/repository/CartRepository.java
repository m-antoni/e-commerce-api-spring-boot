package com.ecommerce.application.repository;

import com.ecommerce.application.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
