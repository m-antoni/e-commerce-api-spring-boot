package com.ecommerce.application.service;
import com.ecommerce.application.model.CartItem;
import com.ecommerce.application.model.Product;
import com.ecommerce.application.repository.OrderDetailRepository;
import com.ecommerce.application.handler.CartHandler;
import com.ecommerce.application.repository.CartItemRepository;
import com.ecommerce.application.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CartItemService {

    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final CartHandler cartHandler;
    private final OrderDetailRepository orderDetailRepository;

    @Autowired
    public CartItemService(CartItemRepository cartRepository, ProductRepository productRepository, CartHandler cartHandler, OrderDetailRepository orderDetailRepository) {
        this.cartItemRepository = cartRepository;
        this.productRepository = productRepository;
        this.cartHandler = cartHandler;
        this.orderDetailRepository = orderDetailRepository;
    }

    public Object getCarts(){
        return cartHandler.GenerateResponse();
    }

    public Optional<CartItem> getSingleCart(Long id){
        boolean exists = cartItemRepository.existsById(id);
        if(!exists){
            throw new IllegalStateException("Product id: " + id + " does not exists.");
        }
        return cartItemRepository.findById(id);
    }

    public Object createCart(CartItem cartItem){
        // Check Product
        Product product = productRepository.findById(cartItem.getProduct_id())
                .orElseThrow(() -> new IllegalStateException("Product does not exists."));

        // Custom Query: Check if item is already in the cart list
        List<CartItem> cartItemItem = cartItemRepository.findByProductId(cartItem.getProduct_id());
        if(!cartItemItem.isEmpty())
            throw new IllegalStateException("Product is already in your cart list.");

        // Check if the quantity is zero or less than the cart quantity
        Product productStocks = productRepository.findById(cartItem.getProduct_id()).orElseThrow(() -> new IllegalStateException("Product does not exists"));
        if(productStocks.getStocks() == 0)
            throw new IllegalStateException("Sorry this product is out of stock.");

        // Check if Cart quantity given is greater than the current product stocks
        if(cartItem.getQuantity() > productStocks.getStocks())
            throw new IllegalStateException("Product stock(s) is only " + productStocks.getStocks() + ", Please update your quantity");

        cartItem.setPrice(product.getPrice() * cartItem.getQuantity());
        return cartItemRepository.save(cartItem);
    }

    public Object deleteCart(Long id){

        // Trigger for delete All
        if(id == 0){
            cartItemRepository.deleteAll();
            return cartHandler.GenerateResponse();
        }

        boolean exists = cartItemRepository.existsById(id);
        if(!exists){
            throw  new IllegalStateException("Product does not exists.");
        }
        // delete single product
        cartItemRepository.deleteById(id);
        return cartHandler.GenerateResponse();
    }

    public Object updateCart(CartItem cartItem, Long id) {
        CartItem cartItemToUpdate = cartItemRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Cart Item does not exists"));

        // Check the quantity field
        if(cartItem.getQuantity() == 0)
            throw  new IllegalStateException("your quantity cannot be zero value.");

        // Check if the quantity is zero or less than the cart quantity
        Product productStocks = productRepository.findById(cartItem.getProduct_id()).orElseThrow(() -> new IllegalStateException("Product does not exists"));
        if(productStocks.getStocks() == 0)
            throw new IllegalStateException("Sorry this product is out of stock.");

        // Check if Cart quantity given is greater than the current product stocks
        if(cartItem.getQuantity() > productStocks.getStocks())
            throw new IllegalStateException("Product stock(s) is only " + productStocks.getStocks() + ", Please update your quantity");

        // Get price
        Product product = productRepository.findById(cartItemToUpdate.getProduct_id())
                .orElseThrow(() -> new IllegalStateException("Product does not exists"));

        // Update Cart Item
        cartItemToUpdate.setQuantity(cartItem.getQuantity());
        cartItemToUpdate.setPrice(product.getPrice() * cartItem.getQuantity());

        return cartItemRepository.save(cartItemToUpdate);
    }


}
