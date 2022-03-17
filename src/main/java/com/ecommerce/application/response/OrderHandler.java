package com.ecommerce.application.response;

import com.ecommerce.application.model.DeliveryAddress;
import com.ecommerce.application.model.OrderDetail;
import com.ecommerce.application.model.PaymentDetail;
import com.ecommerce.application.repository.DeliveryAddressRepository;
import com.ecommerce.application.repository.PaymentDetailRepository;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Component
public class OrderHandler {

    private final DeliveryAddressRepository deliveryAddressRepository;
    private final PaymentDetailRepository paymentDetailRepository;

    public OrderHandler(DeliveryAddressRepository deliveryAddressRepository, PaymentDetailRepository paymentDetailRepository) {
        this.deliveryAddressRepository = deliveryAddressRepository;
        this.paymentDetailRepository = paymentDetailRepository;
    }

    public Object GenerateResponse(OrderDetail orderDetail, Object orderItems, Long totalAmount, Map<String, String> REQUEST_PAYLOAD) {

        // Save Delivery Address
        DeliveryAddress deliveryAddress = this.createDeliveryAddress(orderDetail.getId(), REQUEST_PAYLOAD);

        // Save Payment Detail
        PaymentDetail paymentDetail = this.createPaymentDetail(orderDetail.getId(), totalAmount, REQUEST_PAYLOAD);

        // GENERATING THE RESPONSE
        Map<String, Object> map = new HashMap<>();
        map.put("delivery_to", deliveryAddress.getFull_name());
        map.put("delivery_contact_no", deliveryAddress.getContact_no());
        map.put("delivery_address", deliveryAddress.getAddress());
        map.put("order_no", orderDetail.getOrder_no());
        map.put("order_items", orderItems);// get the total items
        map.put("order_status", orderDetail.getOrder_status());
        map.put("payment_type", paymentDetail.getPayment_type());
        map.put("payment_status", paymentDetail.getPayment_status());
        map.put("total_amount", paymentDetail.getTotal_amount());

        return map;
    }

    public DeliveryAddress createDeliveryAddress(Long orderDetailId, Map<String, String>  REQUEST_PAYLOAD){
        DeliveryAddress deliveryAddress = new DeliveryAddress();
        deliveryAddress.setOrder_id(orderDetailId); // THIS IS THE ORDER DETAIL ID
        deliveryAddress.setFull_name(REQUEST_PAYLOAD.get("full_name"));
        deliveryAddress.setAddress(REQUEST_PAYLOAD.get("address"));
        deliveryAddress.setContact_no(Long.valueOf(REQUEST_PAYLOAD.get("contact_no")));

        return deliveryAddressRepository.save(deliveryAddress);
    }

    public PaymentDetail createPaymentDetail(Long orderDetailId, Long orderDetailTotalAmount, Map<String, String>  REQUEST_PAYLOAD){
        PaymentDetail paymentDetail = new PaymentDetail();
        paymentDetail.setUser_id(Long.valueOf(REQUEST_PAYLOAD.get("user_id"))); // Hardcoded for now since we do not have Auth.
        paymentDetail.setOrder_id(orderDetailId);
        paymentDetail.setDiscount(0L);
        paymentDetail.setDelivery_fee(Long.valueOf(REQUEST_PAYLOAD.get("delivery_fee")));
        paymentDetail.setPayment_type(REQUEST_PAYLOAD.get("payment_type"));

        // Check the payment type to determine the status
        String PAYMENT_STATUS = REQUEST_PAYLOAD.get("payment_type").equals("COD") ? "UNPAID" : "PAID";
        paymentDetail.setPayment_status(PAYMENT_STATUS);

        /* "Modify the total_amount from order details"
         *  - Add the delivery fee to total amount from orders
         *  - Subtract discount
        */
        Long COMPUTE_A = Long.valueOf(orderDetailTotalAmount + paymentDetail.getDelivery_fee());
        Long COMPUTE_B = COMPUTE_A - paymentDetail.getDiscount();
        paymentDetail.setTotal_amount(COMPUTE_B);

        return paymentDetailRepository.save(paymentDetail);
    }

    public Long generateOrderNo(){
        long min = 100000000000L;
        long max = 999999999999L;
        Random random = new Random();
        long randomLong = (long) (random.nextFloat() * (max - min) + min);

        return randomLong;
    }

    // This will generate random string and numbers
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

}
