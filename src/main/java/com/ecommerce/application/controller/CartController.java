package com.ecommerce.application.controller;

import com.ecommerce.application.model.Cart;
import com.ecommerce.application.reponse.ResponseHandler;
import com.ecommerce.application.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/cart")
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<Object> getOrderDetails(){
        try
        {
            Object result = cartService.getCarts();
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
            Optional<Cart> result = cartService.getSingleCart(id);
            return ResponseHandler.GenerateResponse("Success", HttpStatus.OK, result);
        }
        catch (Exception e)
        {
            return ResponseHandler.GenerateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    @PostMapping
    public ResponseEntity<Object>  createOrderDetail(@RequestBody Cart cart){
        try
        {
            Object result = cartService.createCart(cart);
            return ResponseHandler.GenerateResponse("Success", HttpStatus.OK, result);
        }
        catch (Exception e)
        {
            return ResponseHandler.GenerateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<Object> updateOrderDetail(@RequestBody Cart cart, @PathVariable("id") Long id){
        try
        {
            Object result = cartService.updateCart(cart, id);
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
            Object result = cartService.deleteCart(id);
            return ResponseHandler.GenerateResponse("Deleted successfully", HttpStatus.OK, result);
        }
        catch(Exception e)
        {
            return ResponseHandler.GenerateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

}
