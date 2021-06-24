package com.example.querydsl.model.domain.product;

import com.example.querydsl.model.interfaces.ProductDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
class ProductServiceTest {
    @Autowired private ProductService productService;

    @Test
    void createProduct() {
        Product product = new Product(null, "pro", 100, 70);
        productService.createProduct(product);

        Assertions.assertNotNull(product.getId());
    }

    @Test
    void getAllOrderBy() {
        productService.createProduct(new Product(null, "pro1", 700, 70));
        productService.createProduct(new Product(null, "pro2", 600, 60));
        productService.createProduct(new Product(null, "pro3", 500, 10));
        productService.createProduct(new Product(null, "pro4", 400, 20));
        productService.createProduct(new Product(null, "pro5", 300, 30));
        productService.createProduct(new Product(null, "pro6", 200, 40));
        productService.createProduct(new Product(null, "pro7", 100, 50));

        System.out.println("order 1");
        productService.getAllOrderBy(ProductDto.Field.NAME, true).forEach(System.out::println);
        System.out.println("order 2");
        productService.getAllOrderBy(ProductDto.Field.PRICE, false).forEach(System.out::println);
        System.out.println("order 3");
        productService.getAllOrderBy(ProductDto.Field.DISCOUNTED_PRICE, true).forEach(System.out::println);


    }
}