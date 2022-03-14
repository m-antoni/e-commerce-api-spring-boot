package com.ecommerce.application.controller;

import com.ecommerce.application.model.CartItem;
import com.ecommerce.application.response.ResponseHandler;
import com.ecommerce.application.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/cart-items")
public class CartItemController {

    private final CartItemService cartService;

    @Autowired
    public CartItemController(CartItemService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<Object> getCarts(){
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
    public ResponseEntity<Object> getSingleCart(@PathVariable("id") Long id)
    {
        try
        {
            Optional<CartItem> result = cartService.getSingleCart(id);
            return ResponseHandler.GenerateResponse("Success", HttpStatus.OK, result);
        }
        catch (Exception e)
        {
            return ResponseHandler.GenerateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    @PostMapping
    public ResponseEntity<Object> createCart(@RequestBody CartItem cartItem){
        try
        {
            Object result = cartService.createCart(cartItem);
            return ResponseHandler.GenerateResponse("Success", HttpStatus.OK, result);
        }
        catch (Exception e)
        {
            return ResponseHandler.GenerateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<Object> updateItem(@RequestBody CartItem cartItem, @PathVariable("id") Long id){
        try
        {
            Object result = cartService.updateCart(cartItem, id);
            return ResponseHandler.GenerateResponse("Updated successfully", HttpStatus.OK, result);
        }
        catch(Exception e)
        {
            return ResponseHandler.GenerateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }

    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<Object> deleteItem(@PathVariable("id") Long id){
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
