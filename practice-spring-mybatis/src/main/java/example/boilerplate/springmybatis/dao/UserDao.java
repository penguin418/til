package example.boilerplate.springmybatis.dao;

import example.boilerplate.springmybatis.entity.User;
import lombok.AllArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
@AllArgsConstructor
public class UserDao {
    private final static String namespace = "USERS";
    private final SqlSession sqlSession;

    public int insertUserWithId(User user) {
        return this.sqlSession.insert(
                namespace + ".insertUserWithId",
                new HashMap<String, Object>() {{
                    put("id", user.getId());
                    put("username", user.getUsername());
                    put("password", user.getPassword());
                }}
        );
    }

    public int insertUserWithoutId(User user) {
        return this.sqlSession.insert(
                namespace + ".insertUserWithoutId",
                new HashMap<String, Object>() {{
                    put("username", user.getUsername());
                    put("password", user.getPassword());
                }}
        );
    }

    public void insertUserReturningId(User user) {
        this.sqlSession.insert(
                namespace + ".insertUserReturningId",
                user
        );
    }


    public User selectUser(int id) {
        return this.sqlSession.selectOne(
                namespace + ".selectUser",
                new HashMap<String, Object>() {{
                    put("id", id);
                }}
        );
    }

    public List<User> selectUsers() {
        return this.sqlSession.selectList(namespace + ".selectUsers");
    }

    public int updateUser(User user) {
        return this.sqlSession.update(namespace + ".updateUser", user);
    }

    public int deleteUser(int id) {
        return this.sqlSession.delete(namespace + ".deleteUser", id);
    }
}
