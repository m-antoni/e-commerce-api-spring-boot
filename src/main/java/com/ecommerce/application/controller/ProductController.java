package com.ecommerce.application.controller;

import com.ecommerce.application.model.Product;
import com.ecommerce.application.handler.ResponseHandler;
import com.ecommerce.application.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<Object> getProducts(){
        try
        {
           List<Product> result = productService.getProducts();
           return ResponseHandler.GenerateResponse("Success", HttpStatus.OK, result);
        }
        catch(Exception e)
        {
            return ResponseHandler.GenerateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<Object> getSingleProduct(@PathVariable("id") Long id){
        try
        {
            Optional<Product> result = productService.getSingleProduct(id);
            return ResponseHandler.GenerateResponse("Success", HttpStatus.OK, result);
        }
        catch(Exception e)
        {
            return ResponseHandler.GenerateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    @PostMapping
    public ResponseEntity<Object> createProduct(@RequestBody Product product){
        try
        {
            Product result = productService.createProduct(product);
            return ResponseHandler.GenerateResponse("Product created successfully", HttpStatus.CREATED, result);
        }
        catch(Exception e)
        {
            return ResponseHandler.GenerateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }

    }

    @PutMapping(path = "{id}")
    public ResponseEntity<Object> updateProduct(@RequestBody Product product, @PathVariable("id") Long id){
        try
        {
            Product result = productService.updateProduct(product, id);
            return ResponseHandler.GenerateResponse("Updated successfully", HttpStatus.OK, result);
        }
        catch(Exception e)
        {
            return ResponseHandler.GenerateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }

    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable("id") Long id){
       try
       {
           productService.deleteProduct(id);
           return ResponseHandler.GenerateResponse("Deleted successfully", HttpStatus.OK, null);
       }
       catch(Exception e)
       {
           return ResponseHandler.GenerateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
       }
    }

}
