package com.ecommerce.application.controller;

import com.ecommerce.application.response.ResponseHandler;
import com.ecommerce.application.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/orders")
public class OrderDetailController {

    private final OrderDetailService orderDetailService;

    @Autowired
    public OrderDetailController(OrderDetailService orderDetailService) {
        this.orderDetailService = orderDetailService;
    }

    @GetMapping()
    public ResponseEntity<Object> createAllOrderDetail(){
        try
        {
            Object result = orderDetailService.creatAllOrderDetail();
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
