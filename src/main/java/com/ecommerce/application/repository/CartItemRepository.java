package com.ecommerce.application.repository;

import com.ecommerce.application.model.CartItem;
import com.ecommerce.application.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    @Query(value = "SELECT * FROM cart_items WHERE product_id = ?1", nativeQuery = true)
    List<CartItem> findByProductId(Long product_id);
}
