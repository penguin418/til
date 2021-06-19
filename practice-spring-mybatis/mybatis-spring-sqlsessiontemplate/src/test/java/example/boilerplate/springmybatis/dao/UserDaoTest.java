package example.boilerplate.springmybatis.dao;

import example.boilerplate.springmybatis.entity.User;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class UserDaoTest {
    @Autowired
    private UserDao userDao;

    @Test
    @Transactional
    public void 삽입_아이디사용() {
        // given: 만든 아이디 사용함
        int id = 1;
        // when: 새로운 유저 추가
        int successRows = userDao.insertUserWithId(new User(id, "user", "pass"));
        // then: 일치
        Assertions.assertTrue(successRows > 0);
    }

    @Test
    @Transactional
    public void 삽입_아이디_자동() {
        // given: 만든 아이디 사용함
        // when: 새로운 유저 추가
        int successRows = userDao.insertUserWithoutId(new User("user", "pass"));
        // then: 일치
        Assertions.assertTrue( successRows > 0);
    }

    @Test
    @Transactional
    public void 조회() {
        // given: 아이디 추가함
        User user = new User("user", "pass");
        userDao.insertUserReturningId(user);
        int newId = user.getId();

        // when: 추가한 아이디 조회
        User userFromDB = userDao.selectUser(newId);

        // then: 일치
        Assertions.assertEquals(user, userFromDB);
    }

    @Test
    @Transactional
    public void 조회_전체() {
        // given: 유저 3개 등록
        User user = new User("user", "pass");
        userDao.insertUserReturningId(user);
        userDao.insertUserReturningId(user);
        userDao.insertUserReturningId(user);

        // when: 전체 조회
        List<User> users = userDao.selectUsers();

        // then: 3명
        Assertions.assertEquals(3, users.size());
    }

    @Test
    @Transactional
    public void 수정() {
        // given: 유저 등록
        User user = new User("user", "pass");
        userDao.insertUserReturningId(user);
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
        User user = new User("user", "pass");
        userDao.insertUserReturningId(user);
        userDao.insertUserReturningId(user);
        int newId = user.getId();

        // when: 1개 삭제
        userDao.deleteUser(newId);

        // then: 1개 남음
        List<User> users = userDao.selectUsers();
        System.out.println(users);
        Assertions.assertEquals(1, users.size());
    }


}