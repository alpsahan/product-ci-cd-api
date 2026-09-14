package com.alpsahan.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.alpsahan.entity.Product;
import com.alpsahan.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    

    public Product createProduct(Product product) {
    	
        return productRepository.save(product);
    }


    public List<Product> getAllProducts() {
    	
        return productRepository.findAll();
    }

    
    public Product getProductById(Long id) {
    	
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
    }
    

    public Product updateProduct(Long id, Product product) {

        Product existingProduct = getProductById(id);

        existingProduct.setName(product.getName());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setStock(product.getStock());

        return productRepository.save(existingProduct);
    }
    

    public void deleteProduct(Long id) {

        Product product = getProductById(id);

        productRepository.delete(product);
    }
}