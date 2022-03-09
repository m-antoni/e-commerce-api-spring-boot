package com.ecommerce.application.controller;

import com.ecommerce.application.model.Category;
import com.ecommerce.application.model.OrderDetail;
import com.ecommerce.application.model.Product;
import com.ecommerce.application.reponse.ResponseHandler;
import com.ecommerce.application.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping(path = "/api/v1/order-details")
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
            List<OrderDetail> result = orderDetailService.getOrderDetails();
            return ResponseHandler.GenerateResponse("Success", HttpStatus.OK, result);
        }
        catch(Exception e)
        {
            return ResponseHandler.GenerateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<Object> getSingleOrderDetail(@PathVariable("id") Long id)
    {
        try
        {
            Optional<OrderDetail> result = orderDetailService.getSingleOrderDetail(id);
            return ResponseHandler.GenerateResponse("Success", HttpStatus.OK, result);

        }
        catch (Exception e)
        {
            return ResponseHandler.GenerateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    @PostMapping
    public ResponseEntity<Object>  createOrderDetail(@RequestBody  OrderDetail orderDetail){
        try
        {
            OrderDetail result = orderDetailService.createOrderDetail(orderDetail);
            return ResponseHandler.GenerateResponse("Success", HttpStatus.OK, result);
        }
        catch (Exception e)
        {
            return ResponseHandler.GenerateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<Object> updateOrderDetail(@RequestBody OrderDetail orderDetail, @PathVariable("id") Long id){
        try
        {
            OrderDetail result = orderDetailService.updateOrderDetail(orderDetail, id);
            return ResponseHandler.GenerateResponse("Updated successfully", HttpStatus.OK, result);
        }
        catch(Exception e)
        {
            return ResponseHandler.GenerateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }

    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<Object> deleteOrderDetail(@PathVariable("id") Long id){
        try
        {
            orderDetailService.deleteOrderDetail(id);
            return ResponseHandler.GenerateResponse("Deleted successfully", HttpStatus.OK, null);
        }
        catch(Exception e)
        {
            return ResponseHandler.GenerateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

}
