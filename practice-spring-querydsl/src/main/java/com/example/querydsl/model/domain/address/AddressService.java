package com.example.querydsl.model.domain.address;

import com.example.querydsl.model.interfaces.AddressDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;

    public Address createAddress(Address address){
        addressRepository.save(address);
        return address;
    }

    public AddressDto getAddressInfo(Long id){
        return addressRepository.findDtoById(id);
    }
}
