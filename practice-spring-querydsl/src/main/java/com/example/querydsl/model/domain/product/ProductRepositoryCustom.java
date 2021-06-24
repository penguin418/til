package com.example.querydsl.model.domain.product;

import com.example.querydsl.model.interfaces.ProductDto;

import java.util.List;

public interface ProductRepositoryCustom {
    List<ProductDto> getAllOrderBy(ProductDto.Field field, Boolean ascending);
}
