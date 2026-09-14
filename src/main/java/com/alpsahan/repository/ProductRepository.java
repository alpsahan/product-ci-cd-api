package com.alpsahan.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alpsahan.entity.Product;


public interface ProductRepository extends JpaRepository<Product, Long>{

}
