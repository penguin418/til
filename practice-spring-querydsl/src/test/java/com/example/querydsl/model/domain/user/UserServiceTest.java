package com.example.querydsl.model.domain.user;

import com.example.querydsl.model.domain.user.User;
import com.example.querydsl.model.domain.user.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class UserServiceTest {

    @Autowired
    UserService userService;

    @Test
    @Transactional
    public void createUser() {
        User user = new User(1L, "name", "pass", 10);
        User newUser = userService.createUser(user);

        Assertions.assertEquals(user, newUser);
    }

    @Test
    @Transactional
    public void findUserMatchConditions() {
        User user = new User(0L, "name", "pass", 10);
        userService.createUser(user);
        user.setUsername("nameLike"); user.setPower(100);
        userService.createUser(user);
        user.setUsername("likeName"); user.setPower(200);
        userService.createUser(user);
        user.setUsername("nanaLike"); user.setPower(300);
        userService.createUser(user);

        List<User> allUsers = userService.findAll();
        System.out.println(allUsers);

        List<User> userList1 = userService.findUserMatchConditions("user", null);
        System.out.println(userList1);
        userList1.forEach(user1 -> Assertions.assertTrue(user1.getUsername().contains("user")));


        List<User> userList2 = userService.findUserMatchConditions(null, 100);
        System.out.println(userList2);
        userList2.forEach(user2 -> Assertions.assertTrue(user2.getPower() >= 100));



    }

}