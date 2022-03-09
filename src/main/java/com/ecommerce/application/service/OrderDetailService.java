package com.ecommerce.application.service;

import com.ecommerce.application.model.OrderDetail;
import com.ecommerce.application.model.Product;
import com.ecommerce.application.repository.OrderDetailRepository;
import com.ecommerce.application.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderDetailService {

    private final OrderDetailRepository orderDetailRepository;
    private final ProductRepository productRepository;

    @Autowired
    public OrderDetailService(OrderDetailRepository orderDetailRepository, ProductRepository productRepository) {
        this.orderDetailRepository = orderDetailRepository;
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
            throw new IllegalStateException("Order Detail id: " + id + " does not exists.");
        }
        return orderDetailRepository.findById(id);
    }

    public OrderDetail createOrderDetail(OrderDetail orderDetail){
        OrderDetail newOrder = orderDetailRepository.save(orderDetail);
        return newOrder;
    }

    public void deleteOrderDetail(Long id){
        boolean exists = orderDetailRepository.existsById(id);
        if(!exists){
            throw  new IllegalStateException("Order Detail id: " + id + " does not exist.");
        }
        orderDetailRepository.deleteById(id);
    }

    public OrderDetail updateOrderDetail(OrderDetail orderDetail, Long id) {
        OrderDetail orderDetailUpdate = orderDetailRepository.findById(id).orElseThrow(() -> new IllegalStateException("Order Detail id: " + id + " does not exists"));
        orderDetailUpdate.setQuantity(orderDetail.getQuantity());

        // Update the price tied to quantity
        boolean exists = productRepository.existsById(orderDetail.getProduct_id());
        if(!exists){
            throw  new IllegalStateException("Product id: " + id + " does not exist.");
        }
        Optional<Product> product = productRepository.findById(orderDetail.getProduct_id());

        // Update
        orderDetailUpdate.setSku(orderDetail.getSku());
        orderDetailUpdate.setProduct_id(orderDetail.getProduct_id());
        orderDetailUpdate.setPrice(product.get().getPrice() * orderDetail.getQuantity());

        return orderDetailRepository.save(orderDetailUpdate);
    }

}
