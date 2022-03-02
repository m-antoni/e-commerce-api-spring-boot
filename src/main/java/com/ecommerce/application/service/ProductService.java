package com.ecommerce.application.service;

import com.ecommerce.application.model.Product;
import com.ecommerce.application.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getProducts(){
        List<Product> products = new ArrayList<>();
        productRepository.findAll().forEach(products::add);
        return products;
    }

    public Optional<Product> getSingleProduct(Long id){
        boolean exists = productRepository.existsById(id);
        if(!exists){
            throw  new IllegalStateException("Product id: " + id + " does not exist.");
        }
        return productRepository.findById(id);
    }

    public Product createProduct(Product product){
        return productRepository.save(product);
    }

    public void deleteProduct(Long id){
        boolean exists = productRepository.existsById(id);
        if(!exists){
            throw  new IllegalStateException("Product id: " + id + " does not exist.");
        }
        productRepository.deleteById(id);
    }

    public Product updateProduct(Product product, Long id){
        Product productExist = productRepository.findById(id).orElseThrow(() -> new IllegalStateException("Product id: " + id + " does not exists"));

        productExist.setCategory_id(product.getCategory_id());
        productExist.setSku(product.getSku());
        productExist.setName(product.getName());
        productExist.setDescription(product.getDescription());
        productExist.setPrice(product.getPrice());
        productExist.setStocks(product.getStocks());

        return productRepository.save(productExist);
    }


}
