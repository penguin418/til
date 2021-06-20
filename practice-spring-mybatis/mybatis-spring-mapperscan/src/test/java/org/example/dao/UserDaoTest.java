package org.example.dao;

import org.example.entity.User;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
public class UserDaoTest {

    @Autowired UserDao userDao;

    @org.junit.Test
    @Transactional
    public void 삽입() {
        // given: 만든 아이디 사용함
        int id = 1;
        // when: 새로운 유저 추가
        int successRows = userDao.insertUser(new User(id, "user", "pass"));
        // then: 일치
        Assertions.assertTrue(successRows > 0);
    }

    @org.junit.Test
    @Transactional
    public void 조회() {
        // given: 아이디 추가함
        User user = new User(0, "user", "pass");
        userDao.insertUser(user);
        int newId = user.getId();

        // when: 추가한 아이디 조회
        User userFromDB = userDao.selectUser(newId);

        // then: 일치
        Assertions.assertEquals(user, userFromDB);
    }

    @org.junit.Test
    @Transactional
    public void 수정() {
        // given: 유저 등록
        User user = new User(0, "user", "pass");
        userDao.insertUser(user);
        int newId = user.getId();

        // when: 수정
        String newName = "new user";
        user.setUsername(newName);
        int successRows = userDao.updateUser(user);

        // then: 일치
        Assertions.assertTrue(successRows > 0);
        Assertions.assertEquals(newName, user.getUsername());
    }

    @Test
    @Transactional
    public void 삭제() {
        // given: 유저 2명 등록
        User user = new User(0, "user", "pass");
        userDao.insertUser(user);
        int newId = user.getId();

        // when: 1개 삭제
        userDao.deleteUser(newId);

        // then: 1개 남음
        User user1 = userDao.selectUser(newId);
        Assertions.assertNull(user1);
    }
}