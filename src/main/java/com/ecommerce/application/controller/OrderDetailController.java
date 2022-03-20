package com.ecommerce.application.controller;

import com.ecommerce.application.handler.ResponseHandler;
import com.ecommerce.application.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(path = "/api/v1/orders")
public class OrderDetailController {

    private final OrderDetailService orderDetailService;

    @Autowired
    public OrderDetailController(OrderDetailService orderDetailService) {
        this.orderDetailService = orderDetailService;
    }

    @GetMapping
    public ResponseEntity<Object> getOrderDetails(){
        try
        {
            Object result = orderDetailService.getOrderDetails();
            return ResponseHandler.GenerateResponse("Success", HttpStatus.OK, result);
        }
        catch(Exception e)
        {
            return ResponseHandler.GenerateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<Object> getSingleOrderDetail(@PathVariable("id") Long id){
        try
        {
            Object result = orderDetailService.getSingleOrderDetail(id);
            return ResponseHandler.GenerateResponse("Success", HttpStatus.OK, result);
        }
        catch(Exception e)
        {
            return ResponseHandler.GenerateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    @PostMapping
    public ResponseEntity<Object> createSingleOrderDetail(@RequestBody Map<String, String> REQUEST_PAYLOAD){
        try
        {
            Object result = orderDetailService.createSingleOrderDetail(REQUEST_PAYLOAD);
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
            Object result = orderDetailService.createAllOrderDetail(REQUEST_PAYLOAD);
            return ResponseHandler.GenerateResponse("Success", HttpStatus.CREATED, result);
        }
        catch (Exception e)
        {
            return ResponseHandler.GenerateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }


    @PutMapping(path = "{orderDetailId}")
    public ResponseEntity<Object> updateOrderStatus(@PathVariable("orderDetailId") Long orderDetailId,  @RequestBody Map<String, String> REQUEST_PAYLOAD){
        try
        {
            Object result = orderDetailService.updateOrderStatus(orderDetailId, REQUEST_PAYLOAD);
            return ResponseHandler.GenerateResponse("Update Order with Id " + orderDetailId + " successfully", HttpStatus.CREATED, result);
        }
        catch (Exception e)
        {
            return ResponseHandler.GenerateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    @DeleteMapping(path = "{orderDetailId}")
    public ResponseEntity<Object> deleteSingleOrderDetail(@PathVariable("orderDetailId") Long orderDetailId){
        try
        {
            orderDetailService.deleteSingleOrderDetail(orderDetailId);
            return ResponseHandler.GenerateResponse("Order deleted successfully", HttpStatus.OK, null);
        }
        catch (Exception e)
        {
            return ResponseHandler.GenerateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    @DeleteMapping
    public ResponseEntity<Object> deleteAllOrderDetail(){
        try
        {
            orderDetailService.deleteAllOrderDetails();
            return ResponseHandler.GenerateResponse("All Orders deleted successfully", HttpStatus.OK, null);
        }
        catch (Exception e)
        {
            return ResponseHandler.GenerateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }


}
