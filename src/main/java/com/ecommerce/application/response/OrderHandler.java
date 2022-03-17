package com.ecommerce.application.response;

import com.ecommerce.application.model.DeliveryAddress;
import com.ecommerce.application.model.OrderItem;
import com.ecommerce.application.repository.DeliveryAddressRepository;
import org.springframework.stereotype.Component;

import javax.swing.text.html.Option;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class OrderHandler {

    private final DeliveryAddressRepository deliveryAddressRepository;

    public OrderHandler(DeliveryAddressRepository deliveryAddressRepository) {
        this.deliveryAddressRepository = deliveryAddressRepository;
    }

    public Object GenerateResponse(Long order_no, Object orderItems, Long totalAmount, DeliveryAddress deliveryAddress) {
        Map<String, Object> map = new HashMap<>();
        map.put("order_no", order_no);
        map.put("receiver_name", deliveryAddress.getFull_name());
        map.put("receiver_contact_no", deliveryAddress.getContact_no());
        map.put("receiver_address", deliveryAddress.getAddress());
        map.put("order_items", orderItems);// get the total items
        map.put("total_amount", totalAmount);

        return map;
    }
}
