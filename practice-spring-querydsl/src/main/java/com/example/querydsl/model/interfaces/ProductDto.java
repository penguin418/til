package com.example.querydsl.model.interfaces;

import com.querydsl.core.annotations.QueryProjection;
import com.querydsl.core.types.OrderSpecifier;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.function.Function;

import static com.example.querydsl.model.domain.product.QProduct.product;

@Data
public class ProductDto {
    private Long id;
    private String name;
    private Integer price;
    private Integer discountedPrice;

    @QueryProjection
    public ProductDto(Long id, String name, Integer price, Integer discountedPrice) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.discountedPrice = discountedPrice;
    }

    @SuppressWarnings("rawtypes")
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static enum Field {
        ID((asc) -> asc ? product.id.asc() : product.id.desc()),
        NAME((asc) -> asc ? product.name.asc() : product.name.desc()),
        PRICE((asc) -> asc ? product.price.asc() : product.price.desc()),
        DISCOUNTED_PRICE((asc) -> asc ? product.discountedPrice.asc() : product.discountedPrice.desc());

        private final Function<Boolean, OrderSpecifier> expression;

        public OrderSpecifier ascending(Boolean asc) {
            return expression.apply(asc);
        }
    }
}
