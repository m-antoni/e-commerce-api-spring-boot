package com.ecommerce.application.service;

import com.ecommerce.application.model.CartItem;
import com.ecommerce.application.model.DeliveryAddress;
import com.ecommerce.application.model.OrderDetail;
import com.ecommerce.application.model.OrderItem;
import com.ecommerce.application.repository.CartItemRepository;
import com.ecommerce.application.repository.DeliveryAddressRepository;
import com.ecommerce.application.repository.OrderDetailRepository;
import com.ecommerce.application.repository.OrderItemRepository;
import com.ecommerce.application.response.OrderHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderDetailService {

    private final OrderDetailRepository orderDetailRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartItemRepository cartItemRepository;
    private final DeliveryAddressRepository deliveryAddressRepository;
    private final OrderHandler orderHandler;

    @Autowired
    public OrderDetailService(OrderDetailRepository orderDetailRepository, OrderItemRepository orderItemRepository, CartItemRepository cartItemRepository, DeliveryAddressRepository deliveryAddressRepository, OrderHandler orderHandler) {
        this.orderDetailRepository = orderDetailRepository;
        this.orderItemRepository = orderItemRepository;
        this.cartItemRepository = cartItemRepository;
        this.deliveryAddressRepository = deliveryAddressRepository;
        this.orderHandler = orderHandler;
    }

    public Object createSingleOrderDetail(Long cartItemId, DeliveryAddress deliveryAddress){

        Optional<CartItem> cartItem = cartItemRepository.findById(cartItemId);

        if(!cartItem.isPresent()){
            throw new IllegalStateException("Product does not exists in your cart");
        }

        // save the order details
        OrderDetail createOrderDetail = new OrderDetail(1L, cartItem.get().getPrice(), this.generateOrderNo());
        OrderDetail orderDetail = orderDetailRepository.save(createOrderDetail);
        DeliveryAddress createDeliveryAddress = new DeliveryAddress();

        // save the order items
        OrderItem createOrderItem = new OrderItem(cartItem.get().getProduct_id(), orderDetail.getId(), cartItem.get().getQuantity());
        orderItemRepository.save(createOrderItem);

        // save delivery address
        deliveryAddress.setOrder_id(createOrderDetail.getId());
        DeliveryAddress saveDeliveryAddress = deliveryAddressRepository.save(deliveryAddress);

        // Remove the cart item
        cartItemRepository.deleteById(cartItemId);

        return orderHandler.GenerateResponse(createOrderDetail.getOrder_no(), createOrderItem, orderDetail.getTotal_amount(), saveDeliveryAddress);
    }

    public Object creatAllOrderDetail(DeliveryAddress deliveryAddress){
        List<CartItem> cartItems = cartItemRepository.findAll();

        if(cartItems.size() == 0){
            throw new IllegalStateException("You have 0 products in your cart, please add at least one product");
        }

        // save the order details
        Long total_amount = Long.valueOf(cartItems.stream().mapToInt(x -> Math.toIntExact(x.getPrice())).sum());

        OrderDetail createOrderDetail = new OrderDetail(1L, total_amount, this.generateOrderNo());
        OrderDetail orderDetail = orderDetailRepository.save(createOrderDetail);

        // save delivery address
        deliveryAddress.setOrder_id(orderDetail.getId()); // get the order_id before saving
        DeliveryAddress saveDeliveryAddress = deliveryAddressRepository.save(deliveryAddress);

        // save order items
        List<OrderItem> listOfOrderItems = new ArrayList<>();
        for (CartItem cartItem: cartItems){
            OrderItem createOrderItem = new OrderItem(cartItem.getProduct_id(), orderDetail.getId(), cartItem.getQuantity());
            listOfOrderItems.add(createOrderItem);
        }
        orderItemRepository.saveAll(listOfOrderItems);

        // Remove the cart all the cart items base on id
        cartItemRepository.deleteAll();

        return orderHandler.GenerateResponse(createOrderDetail.getOrder_no(), listOfOrderItems, orderDetail.getTotal_amount(), saveDeliveryAddress);
    }

    // This will generate string and numbers
    private String generateRandomChars() {
        String salt_chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 15) { // length of the random string.
            int index = (int) (rnd.nextFloat() * salt_chars.length());
            salt.append(salt_chars.charAt(index));
        }
        String saltStr = salt.toString().toUpperCase();
        return saltStr;
    }

    // This will generate order no
    private Long generateOrderNo(){
        long min = 100000000000L;
        long max = 999999999999L;
        Random random = new Random();
        long randomLong = (long) (random.nextFloat() * (max - min) + min);

        return randomLong;
    }
}
