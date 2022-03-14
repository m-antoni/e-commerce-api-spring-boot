package com.ecommerce.application.controller;

import com.ecommerce.application.model.Category;
import com.ecommerce.application.response.ResponseHandler;
import com.ecommerce.application.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/products/category")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<Object> getCategories(){
        try
        {
            List<Category> result = categoryService.getCategories();
            return ResponseHandler.GenerateResponse("Success", HttpStatus.OK, result);
        }
        catch(Exception e)
        {
            return ResponseHandler.GenerateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<Object> getSingleCategory(@PathVariable("id") Long id){
        try
        {
            Optional<Category> result = categoryService.getSingleCategory(id);
            return ResponseHandler.GenerateResponse("Success", HttpStatus.OK, result);
        }
        catch(Exception e)
        {
            return ResponseHandler.GenerateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    @PostMapping
    public ResponseEntity<Object> createCategory(@RequestBody Category category){
        try
        {
            Category result = categoryService.createCategory(category);
            return ResponseHandler.GenerateResponse("Category created successfully", HttpStatus.CREATED, result);
        }
        catch (Exception e)
        {
            return ResponseHandler.GenerateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<Object> updateProduct(@RequestBody Category category, @PathVariable("id") Long id){
        try
        {
            Category result = categoryService.updateCategory(category, id);
            return ResponseHandler.GenerateResponse("Category updated successfully", HttpStatus.OK, result);
        }
        catch(Exception e)
        {
            return ResponseHandler.GenerateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<Object> deleteCategory(@PathVariable("id") Long id){
        try
        {
            categoryService.deleteCategory(id);
            return ResponseHandler.GenerateResponse("Category deleted successfully", HttpStatus.OK, null);
        }
        catch(Exception e)
        {
            return ResponseHandler.GenerateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

}
