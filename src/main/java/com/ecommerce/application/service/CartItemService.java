package com.ecommerce.application.service;
import com.ecommerce.application.model.CartItem;
import com.ecommerce.application.model.OrderItem;
import com.ecommerce.application.model.Product;
import com.ecommerce.application.repository.OrderDetailRepository;
import com.ecommerce.application.response.CartHandler;
import com.ecommerce.application.repository.CartItemRepository;
import com.ecommerce.application.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CartItemService {

    private final CartItemRepository cartRepository;
    private final ProductRepository productRepository;
    private final CartHandler cartHandler;
    private final OrderDetailRepository orderDetailRepository;

    @Autowired
    public CartItemService(CartItemRepository cartRepository, ProductRepository productRepository, CartHandler cartHandler, OrderDetailRepository orderDetailRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.cartHandler = cartHandler;
        this.orderDetailRepository = orderDetailRepository;
    }

    public Object getCarts(){
        return cartHandler.GenerateResponse();
    }

    public Optional<CartItem> getSingleCart(Long id){
        boolean exists = cartRepository.existsById(id);
        if(!exists){
            throw new IllegalStateException("Product id: " + id + " does not exists.");
        }
        return cartRepository.findById(id);
    }

    public Object createCart(CartItem cartItem){
        // Check Product
        Product product = productRepository.findById(cartItem.getProduct_id())
                .orElseThrow(() -> new IllegalStateException("Product does not exists."));

        // Custom Query: Check if item is already in the cart list
        List<CartItem> cartItemItem = cartRepository.findByProductId(cartItem.getProduct_id());
        if(!cartItemItem.isEmpty()) {
            throw new IllegalStateException("Product is already in your cart list.");
        }

        cartItem.setPrice(product.getPrice() * cartItem.getQuantity());
        return cartRepository.save(cartItem);
    }

    public Object deleteCart(Long id){

        // Trigger for delete All
        if(id == 0){
            cartRepository.deleteAll();
            return cartHandler.GenerateResponse();
        }

        boolean exists = cartRepository.existsById(id);
        if(!exists){
            throw  new IllegalStateException("Product does not exists.");
        }
        // delete single product
        cartRepository.deleteById(id);
        return cartHandler.GenerateResponse();
    }

    public Object updateCart(CartItem cartItem, Long id) {
        CartItem cartItemToUpdate = cartRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Cart Item does not exists"));

        if(cartItem.getQuantity() == 0){
            throw  new IllegalStateException("your quantity cannot be zero value.");
        }

        // Get price
        Product product = productRepository.findById(cartItemToUpdate.getProduct_id())
                .orElseThrow(() -> new IllegalStateException("Product does not exists"));

        // Update Cart Item
        cartItemToUpdate.setQuantity(cartItem.getQuantity());
        cartItemToUpdate.setPrice(product.getPrice() * cartItem.getQuantity());

        return cartRepository.save(cartItemToUpdate);
    }


}
