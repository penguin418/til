package com.example.querydsl.model.domain.address;

import com.example.querydsl.model.interfaces.AddressDto;
import com.example.querydsl.model.interfaces.QAddressDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;

import static com.example.querydsl.model.domain.address.QAddress.address;

@AllArgsConstructor
public class AddressRepositoryImpl implements AddressRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public AddressDto findDtoById(Long id) {
        return jpaQueryFactory.select(
                new QAddressDto(
                        address.user.userId,
                        address.id,
                        address.user.username,
                        address.adress))
                .from(address)
                .where(address.id.eq(id))
                .fetchOne();
    }
}
