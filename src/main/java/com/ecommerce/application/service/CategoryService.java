package com.ecommerce.application.service;

import com.ecommerce.application.model.Category;
import com.ecommerce.application.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getCategories(){
        List<Category> categories = new ArrayList<>();
        categoryRepository.findAll().forEach(categories::add);
        return categories;
    }

    public Optional<Category> getSingleCategory(Long id){
        boolean exists = categoryRepository.existsById(id);
        if(!exists){
            throw new IllegalStateException("Category id: " + id + " does not exists");
        }
        return categoryRepository.findById(id);
    }

    public Category createCategory(Category category){
        return categoryRepository.save(category);
    }

    public void deleteCategory(Long id){
        boolean exists = categoryRepository.existsById(id);
        if(!exists){
            throw new IllegalStateException("Category id: " + id + " does not exists");
        }
        categoryRepository.deleteById(id);
    }

    public Category updateCategory(Category category, Long id){
        Category categoryExists = categoryRepository.findById(id).orElseThrow(() -> new IllegalStateException("Category id: " + id + " does not exist"));

        categoryExists.setName(category.getName());
        categoryExists.setDescription(category.getDescription());

        return categoryRepository.save(categoryExists);
    }

}
