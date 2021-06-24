package com.example.querydsl.model.domain.product;

import com.example.querydsl.model.interfaces.ProductDto;
import com.example.querydsl.model.interfaces.QProductDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;

import java.util.List;

import static com.example.querydsl.model.domain.product.QProduct.product;
//import com.example.querydsl.model.interfaces.QProductDto;

@AllArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryCustom {
    JPAQueryFactory jpaQueryFactory;

    @Override
    public List<ProductDto> getAllOrderBy(ProductDto.Field field, Boolean ascending) {
        return jpaQueryFactory.select(
                new QProductDto(
                        product.id,
                        product.name,
                        product.price,
                        product.discountedPrice
                )).from(product)
                .orderBy(field.ascending(ascending)).fetch();
    }
}
