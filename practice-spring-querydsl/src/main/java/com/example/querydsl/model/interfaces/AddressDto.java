package com.example.querydsl.model.interfaces;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class AddressDto {
    private long userId;
    private long addressId;
    private String username;

    @QueryProjection
    public AddressDto(long userId, long addressId, String username, String address) {
        this.userId = userId;
        this.addressId = addressId;
        this.username = username;
        this.address = address;
    }

    private String address;
}
