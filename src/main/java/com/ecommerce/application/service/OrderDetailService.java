package com.ecommerce.application.service;

import com.ecommerce.application.model.*;
import com.ecommerce.application.repository.*;
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
    private final PaymentDetailRepository paymentDetailRepository;

    @Autowired
    public OrderDetailService(
            OrderDetailRepository orderDetailRepository,
            OrderItemRepository orderItemRepository,
            CartItemRepository cartItemRepository,
            DeliveryAddressRepository deliveryAddressRepository,
            OrderHandler orderHandler,
            PaymentDetailRepository paymentDetailRepository) {

        this.orderDetailRepository = orderDetailRepository;
        this.orderItemRepository = orderItemRepository;
        this.cartItemRepository = cartItemRepository;
        this.deliveryAddressRepository = deliveryAddressRepository;
        this.orderHandler = orderHandler;
        this.paymentDetailRepository = paymentDetailRepository;
    }

    public Object createSingleOrderDetail(Long cartItemId, Map<String, String> REQUEST_PAYLOAD){

        Optional<CartItem> cartItem = cartItemRepository.findById(cartItemId);

        if(!cartItem.isPresent()){
            throw new IllegalStateException("Product does not exists in your cart");
        }

        // Save the OrderDetail
        OrderDetail createOrderDetail = new OrderDetail(1L, cartItem.get().getPrice(), orderHandler.generateOrderNo());
        OrderDetail orderDetail = orderDetailRepository.save(createOrderDetail);

        // Save OrderItem
        OrderItem createOrderItem = new OrderItem(cartItem.get().getProduct_id(), orderDetail.getId(), cartItem.get().getQuantity());
        orderItemRepository.save(createOrderItem);

        // Remove the checkout item from cart
        cartItemRepository.deleteById(cartItemId);

        //return orderHandler.GenerateResponse(createOrderDetail, createOrderItem, orderDetail.getTotal_amount(), saveDeliveryAddress);
        return orderHandler.GenerateResponse(createOrderDetail, createOrderItem, orderDetail.getTotal_amount(), REQUEST_PAYLOAD);
    }

    public Object creatAllOrderDetail(Map<String, String> REQUEST_PAYLOAD){
        List<CartItem> cartItems = cartItemRepository.findAll();

        if(cartItems.size() == 0){
            throw new IllegalStateException("You have 0 products in your cart, please add at least one product");
        }

        // Save the OrderDetail
        Long total_amount = Long.valueOf(cartItems.stream().mapToInt(x -> Math.toIntExact(x.getPrice())).sum());

        OrderDetail createOrderDetail = new OrderDetail(1L, total_amount, orderHandler.generateOrderNo());
        OrderDetail orderDetail = orderDetailRepository.save(createOrderDetail);

        // Save OrderItem
        List<OrderItem> listOfOrderItems = new ArrayList<>();
        for (CartItem cartItem: cartItems)
        {
            OrderItem createOrderItem = new OrderItem(cartItem.getProduct_id(), orderDetail.getId(), cartItem.getQuantity());
            listOfOrderItems.add(createOrderItem);
        }

        orderItemRepository.saveAll(listOfOrderItems);

        // Remove the checkout item(s) from cart
        cartItemRepository.deleteAll();

        return orderHandler.GenerateResponse(createOrderDetail, listOfOrderItems, orderDetail.getTotal_amount(), REQUEST_PAYLOAD);
    }

}
