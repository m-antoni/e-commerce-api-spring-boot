package com.ecommerce.application.service;

import com.ecommerce.application.model.Category;
import com.ecommerce.application.model.Product;
import com.ecommerce.application.repository.CategoryRepository;
import com.ecommerce.application.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    public final ProductRepository productRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    public List<Category> getCategories(){
        List<Category> categories = new ArrayList<>();
        categoryRepository.findAll().forEach(categories::add);
        return categories;
    }

    public Optional<Category> getSingleCategory(Long id){
        categoryRepository.findById(id).orElseThrow(() -> new IllegalStateException("Category does not exist"));
        return categoryRepository.findById(id);
    }

    public Category createCategory(Category category){
        return categoryRepository.save(category);
    }

    public void deleteCategory(Long id){
        categoryRepository.findById(id).orElseThrow(() -> new IllegalStateException("Category does not exist"));
        categoryRepository.deleteById(id);
    }

    public Category updateCategory(Category category, Long id){
        Category categoryExists = categoryRepository.findById(id).orElseThrow(() -> new IllegalStateException("Category does not exist"));
        categoryExists.setName(category.getName());
        categoryExists.setDescription(category.getDescription());
        return categoryRepository.save(categoryExists);
    }
}
