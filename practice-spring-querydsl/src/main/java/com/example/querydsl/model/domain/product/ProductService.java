package com.example.querydsl.model.domain.product;

import com.example.querydsl.model.interfaces.ProductDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Product createProduct(Product product){
        productRepository.save(product);
        return product;
    }

    public List<ProductDto> getAllOrderBy(ProductDto.Field field, Boolean ascending) {
        return productRepository.getAllOrderBy(field, ascending);
    }
}
