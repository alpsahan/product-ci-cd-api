package com.alpsahan.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.alpsahan.entity.Product;

@SpringBootTest
@Testcontainers
public class ProductRepositoryIntegrationTest {
	
	@Container
	static PostgreSQLContainer<?> postgres = 
			new PostgreSQLContainer<>("postgres:16-alpine")
			.withDatabaseName("testdb")
			.withUsername("test")
			.withPassword("test");
	
	
	@DynamicPropertySource
	static void configureProperties(DynamicPropertyRegistry registry) {
		
		registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
	}
	
	
	@Autowired
	private ProductRepository productRepository;
	
	@BeforeEach
	void cleanDatabase() {
	    productRepository.deleteAll();
	}
	
	
	@Test
	void shouldSaveAndFindProduct() {
		
		Product product = new Product(
				null,
				"Test Laptop",
				new BigDecimal("35000.00"),
				10);
		
		Product savedProduct = productRepository.save(product);
		
		Optional<Product> foundProduct = productRepository.findById(savedProduct.getId());
		
		
		assertThat(foundProduct).isPresent();
        assertThat(foundProduct.get().getName()).isEqualTo("Test Laptop");
	}
	
	
	@Test
	void shouldFindAllProducts() {

	    Product product1 = new Product(
	            null,
	            "Mouse",
	            new BigDecimal("1500.00"),
	            10
	    );

	    Product product2 = new Product(
	            null,
	            "Keyboard",
	            new BigDecimal("2500.00"),
	            5
	    );

	    productRepository.save(product1);
	    productRepository.save(product2);

	    var products = productRepository.findAll();

	    assertThat(products).hasSize(2);
	}
	
	
	@Test
	void shouldDeleteProduct() {

	    Product product = new Product(
	            null,
	            "Monitor",
	            new BigDecimal("8000.00"),
	            4
	    );

	    Product savedProduct = productRepository.save(product);

	    productRepository.deleteById(savedProduct.getId());

	    Optional<Product> deletedProduct =productRepository.findById(savedProduct.getId());

	    assertThat(deletedProduct).isEmpty();
	}
	
}
