package com.example.querydsl.model.domain.address;

import com.example.querydsl.model.interfaces.AddressDto;

public interface AddressRepositoryCustom {

    public AddressDto findDtoById(Long id);
}
