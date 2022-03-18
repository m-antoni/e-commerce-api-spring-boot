package com.ecommerce.application.controller;

import com.ecommerce.application.model.DeliveryAddress;
import com.ecommerce.application.response.ResponseHandler;
import com.ecommerce.application.service.OrderDetailService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/v1/orders")
public class OrderDetailController {

    private final OrderDetailService orderDetailService;

    @Autowired
    public OrderDetailController(OrderDetailService orderDetailService) {
        this.orderDetailService = orderDetailService;
    }

    @PutMapping(path = "{cartItemId}")
    public ResponseEntity<Object> createSingleOrderDetail(@RequestBody Map<String, String> REQUEST_PAYLOAD, @PathVariable("cartItemId") Long cartItemId){
        try
        {
            Object result = orderDetailService.createSingleOrderDetail(cartItemId, REQUEST_PAYLOAD);
            return ResponseHandler.GenerateResponse("Success", HttpStatus.CREATED, result);
        }
        catch (Exception e)
        {
            return ResponseHandler.GenerateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    @PostMapping(path = "/all")
    public ResponseEntity<Object> createAllOrderDetail(@RequestBody Map<String, String> REQUEST_PAYLOAD){
        try
        {
            Object result = orderDetailService.creatAllOrderDetail(REQUEST_PAYLOAD);
            return ResponseHandler.GenerateResponse("Success", HttpStatus.CREATED, result);
        }
        catch (Exception e)
        {
            return ResponseHandler.GenerateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    @GetMapping
    public ResponseEntity<Object> getOrderDetails(){
        try
        {
            Object result = orderDetailService.getOrderDetails();
            return ResponseHandler.GenerateResponse("Success", HttpStatus.CREATED, result);
        }
        catch(Exception e)
        {
            return ResponseHandler.GenerateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

}
