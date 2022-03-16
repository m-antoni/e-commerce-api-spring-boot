package com.ecommerce.application.controller;

import com.ecommerce.application.model.DeliveryAddress;
import com.ecommerce.application.response.ResponseHandler;
import com.ecommerce.application.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/orders")
public class OrderDetailController {

    private final OrderDetailService orderDetailService;

    @Autowired
    public OrderDetailController(OrderDetailService orderDetailService) {
        this.orderDetailService = orderDetailService;
    }

    @PostMapping(path = "/all")
    public ResponseEntity<Object> createAllOrderDetail(@RequestBody DeliveryAddress deliveryAddress){
        try
        {
            Object result = orderDetailService.creatAllOrderDetail(deliveryAddress);
            return ResponseHandler.GenerateResponse("Success", HttpStatus.CREATED, result);
        }
        catch (Exception e)
        {
            return ResponseHandler.GenerateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    @GetMapping(path = "{cartId}")
    public ResponseEntity<Object> createSingleOrderDetail(@PathVariable Long cartId){
        try
        {
            Object result = orderDetailService.createSingleOrderDetail(cartId);
            return ResponseHandler.GenerateResponse("Success", HttpStatus.CREATED, result);
        }
        catch (Exception e)
        {
            return ResponseHandler.GenerateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }


}
