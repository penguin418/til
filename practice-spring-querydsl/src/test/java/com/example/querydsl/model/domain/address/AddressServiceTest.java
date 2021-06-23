package com.example.querydsl.model.domain.address;

import com.example.querydsl.model.domain.user.User;
import com.example.querydsl.model.domain.user.UserService;
import com.example.querydsl.model.interfaces.AddressDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class AddressServiceTest {

    @Autowired
    UserService userService;
    @Autowired
    AddressService addressService;

    private User user;

    @BeforeEach
    public void beforeEach(){
        this.user = new User(null, "username", "password", 250);
        this.userService.createUser(user);
    }

    @Test
    public  void insertAddress() {
        Address address = new Address(null, "1 Hello Street, World Village", this.user);
        address = this.addressService.createAddress(address);
        Assertions.assertNotNull(address.getId());
    }

    @Test
    public void getAddressInfo() {
        Address address = new Address(null, "1 Hello Street, World Village", this.user);
        this.addressService.createAddress(address);

        AddressDto addressDto = this.addressService.getAddressInfo(address.getId());

        Assertions.assertEquals(addressDto.getUsername(), address.getUser().getUsername());
        Assertions.assertEquals(addressDto.getAddress(), address.getAdress());
    }
}