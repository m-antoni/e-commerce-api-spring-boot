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
    private final ProductRepository productRepository;

    @Autowired
    public OrderDetailService(
            OrderDetailRepository orderDetailRepository,
            OrderItemRepository orderItemRepository,
            CartItemRepository cartItemRepository,
            DeliveryAddressRepository deliveryAddressRepository,
            OrderHandler orderHandler,
            PaymentDetailRepository paymentDetailRepository,
            ProductRepository productRepository) {

        this.orderDetailRepository = orderDetailRepository;
        this.orderItemRepository = orderItemRepository;
        this.cartItemRepository = cartItemRepository;
        this.deliveryAddressRepository = deliveryAddressRepository;
        this.orderHandler = orderHandler;
        this.paymentDetailRepository = paymentDetailRepository;
        this.productRepository = productRepository;
    }

    public List<OrderDetail> getOrderDetails(){
        List<OrderDetail> orderDetails = new ArrayList<>();
        orderDetailRepository.findAll().forEach(orderDetails::add);
        return orderDetails;
    }

    public Optional<OrderDetail> getSingleOrderDetail(Long id){
        boolean exists = orderDetailRepository.existsById(id);
        if(!exists){
            throw new IllegalStateException("Order does not exists.");
        }
        return orderDetailRepository.findById(id);
    }

    public Object createSingleOrderDetail(Map<String, String> REQUEST_PAYLOAD){

        Long cart_item_id = Long.valueOf(REQUEST_PAYLOAD.get("cart_item_id"));

        Optional<CartItem> cartItem = cartItemRepository.findById(cart_item_id);

        if(!cartItem.isPresent()){
            throw new IllegalStateException("Product does not exists in your cart");
        }

        // Save the OrderDetail
        OrderDetail createOrderDetail = new OrderDetail(1L, cartItem.get().getPrice(), orderHandler.generateOrderNo());
        OrderDetail orderDetail = orderDetailRepository.save(createOrderDetail);

        // Save OrderItem
        OrderItem createOrderItem = new OrderItem(cartItem.get().getProduct_id(), orderDetail.getId(), cartItem.get().getQuantity());
        orderItemRepository.save(createOrderItem);

        // Update Product Stocks base on the quantity
        Integer quantity = cartItem.get().getQuantity();
        Product product = productRepository.findById(cartItem.get().getProduct_id()).orElseThrow(() -> new IllegalStateException("Product does not exists"));
        product.setStocks(product.getStocks() - quantity);
        productRepository.save(product);

        // Remove the checkout item from cart
        cartItemRepository.deleteById(cart_item_id);

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

        // Update the Product Stocks base on cart quantity
        for(CartItem cartItem: cartItems)
        {
            Integer quantity = cartItem.getQuantity();
            Product product = productRepository.findById(cartItem.getProduct_id()).orElseThrow(() -> new IllegalStateException("Product does not exists"));
            product.setStocks(product.getStocks() - quantity);
            productRepository.save(product);
        }

        // Remove the checkout item(s) from cart
        cartItemRepository.deleteAll();

        return orderHandler.GenerateResponse(createOrderDetail, listOfOrderItems, orderDetail.getTotal_amount(), REQUEST_PAYLOAD);
    }

    // Update Order Status
    public Object updateOrderStatus(Long orderDetailId, Map<String, String> REQUEST_PAYLOAD){

        String order_status = REQUEST_PAYLOAD.get("order_status");

        OrderDetail orderDetail = orderDetailRepository.findById(orderDetailId)
                .orElseThrow(() -> new IllegalStateException("Order does not exists"));

        orderDetail.setOrder_status(order_status);

        /* Update the Payment Details: Check the order_status if "DELIVERED" or "RETURNED" */
        if(order_status == "DELIVERED")
        {
            orderDetail.getPaymentDetail().setPayment_status("PAID");
        }
        else
        {
            orderDetail.getPaymentDetail().setPayment_status("CANCELLED");

            // This means the item is unsuccessful so we need to return the quantity we have
            for(OrderItem orderItem: orderDetail.getOrder_items())
            {
                Integer quantity = orderItem.getQuantity();
                Product productStock = productRepository.findById(orderItem.getProduct_id()).orElseThrow(() -> new IllegalStateException("Product does not exists"));
                productStock.setStocks(productStock.getStocks() + quantity);
                productRepository.save(productStock);
            }
        }

        return orderDetailRepository.save(orderDetail);
    }

    // Delete Single Order
    public void deleteSingleOrderDetail(Long id){
        boolean exists = orderDetailRepository.existsById(id);
        if(!exists){
            throw  new IllegalStateException("Order does not exist.");
        }
        orderDetailRepository.deleteById(id);
    }

    // Delete All Order
    public void deleteAllOrderDetails(){
        orderDetailRepository.deleteAll();
    }

}
