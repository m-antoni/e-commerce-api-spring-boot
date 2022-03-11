package com.ecommerce.application.repository;

import com.ecommerce.application.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query(value = "SELECT * FROM cart WHERE product_id = ?1", nativeQuery = true)
    List<Cart> findByProductId(Long product_id);

}
