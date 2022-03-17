package com.ecommerce.application.response;

import com.ecommerce.application.model.DeliveryAddress;
import com.ecommerce.application.model.OrderDetail;
import com.ecommerce.application.model.PaymentDetail;
import com.ecommerce.application.repository.DeliveryAddressRepository;
import com.ecommerce.application.repository.PaymentDetailRepository;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class OrderHandler {

    private final DeliveryAddressRepository deliveryAddressRepository;

    public OrderHandler(DeliveryAddressRepository deliveryAddressRepository) {
        this.deliveryAddressRepository = deliveryAddressRepository;
    }

    public Object GenerateResponse(OrderDetail orderDetail, Object orderItems, Long totalAmount, DeliveryAddress deliveryAddress) {

        Map<String, Object> map = new HashMap<>();

        map.put("order_no", orderDetail.getOrder_no());
        map.put("receiver_name", deliveryAddress.getFull_name());
        map.put("receiver_contact_no", deliveryAddress.getContact_no());
        map.put("receiver_address", deliveryAddress.getAddress());
        map.put("order_items", orderItems);// get the total items
        map.put("order_status", orderDetail.getOrder_status());
        map.put("total_amount", totalAmount);

        return map;
    }

}
