package com.ecommerce.application.handler;

import com.ecommerce.application.model.CartItem;
import com.ecommerce.application.model.OrderItem;
import com.ecommerce.application.model.Product;
import com.ecommerce.application.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StockHandler {

    @Autowired
    private final ProductRepository productRepository;

    public StockHandler(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // This will pull update the product stock by subtracting the quantity given input
    public void PullStock(CartItem cartItem){
        Integer quantity = cartItem.getQuantity();
        Product product = productRepository.findById(cartItem.getProduct_id()).orElseThrow(() -> new IllegalStateException("Product does not exists"));
        product.setStocks(product.getStocks() - quantity);
        productRepository.save(product);
    }

    // This means returning the item to stocks
    public void ReturnStock(OrderItem orderItem){
        Integer quantity = orderItem.getQuantity();
        Product productStock = productRepository.findById(orderItem.getProduct_id()).orElseThrow(() -> new IllegalStateException("Product does not exists"));
        productStock.setStocks(productStock.getStocks() + quantity);
        productRepository.save(productStock);
    }

}
