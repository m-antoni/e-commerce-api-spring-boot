package com.ecommerce.application.service;

import com.ecommerce.application.model.CartItem;
import com.ecommerce.application.model.OrderDetail;
import com.ecommerce.application.model.OrderItem;
import com.ecommerce.application.repository.CartItemRepository;
import com.ecommerce.application.repository.OrderDetailRepository;
import com.ecommerce.application.repository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderDetailService {

    private final OrderDetailRepository orderDetailRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartItemRepository cartItemRepository;

    @Autowired
    public OrderDetailService(OrderDetailRepository orderDetailRepository, OrderItemRepository orderItemRepository, CartItemRepository cartItemRepository) {
        this.orderDetailRepository = orderDetailRepository;
        this.orderItemRepository = orderItemRepository;
        this.cartItemRepository = cartItemRepository;
    }

    public Object createSingleOrderDetail(Long cartItemId){

        Optional<CartItem> cartItem = cartItemRepository.findById(cartItemId);

        if(!cartItem.isPresent()){
            throw new IllegalStateException("Product does not exists in your cart");
        }

        // save the order details
        OrderDetail createOrderDetail = new OrderDetail(1L, cartItem.get().getPrice());
        OrderDetail orderDetail = orderDetailRepository.save(createOrderDetail);

        // save the order items
        OrderItem createOrderItem = new OrderItem(cartItem.get().getProduct_id(), orderDetail.getId(), cartItem.get().getQuantity());
        orderItemRepository.save(createOrderItem);

        // Remove the cart item
        cartItemRepository.deleteById(cartItemId);

        Map<String, Object> map = new HashMap<>();
        map.put("order_items", createOrderItem);// get the total items
        map.put("total_amount", orderDetail.getTotal_amount());

        return map;
    }


    public Object creatAllOrderDetail(){
        List<CartItem> cartItems = cartItemRepository.findAll();

        if(cartItems.size() == 0){
            throw new IllegalStateException("You have 0 products in your cart, please add at least one product");
        }

        // save the order details
        Long total_amount = Long.valueOf(cartItems.stream().mapToInt(x -> Math.toIntExact(x.getPrice())).sum());
        OrderDetail createOrderDetail = new OrderDetail(1L, total_amount);
        OrderDetail orderDetail = orderDetailRepository.save(createOrderDetail);

        // save order items
        List<OrderItem> listOfOrderItems = new ArrayList<>();
        for (CartItem cartItem: cartItems){
            OrderItem createOrderItem = new OrderItem(cartItem.getProduct_id(), orderDetail.getId(), cartItem.getQuantity());
            listOfOrderItems.add(createOrderItem);
        }
        orderItemRepository.saveAll(listOfOrderItems);

        // Remove the cart all the cart items base on id
        cartItemRepository.deleteAll();

        Map<String, Object> map = new HashMap<>();
        map.put("order_items", listOfOrderItems);// get the total items
        map.put("total_amount", createOrderDetail.getTotal_amount());

        return map;
    }
}
