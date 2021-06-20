package org.example.dao;

import org.apache.ibatis.annotations.*;
import org.example.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao {

    @Insert("INSERT INTO users(id, username, password) values (#{id}, #{username}, #{password})")
    public int insertUser(User user);

    @Select("SELECT * FROM users WHERE id = #{id}")
    User selectUser(@Param("id") int id);

    @Update("UPDATE users SET username = #{username}, password=#{password}" +
            "WHERE id = #{id}")
    int updateUser(User user);

    @Delete("DELETE FROM users WHERE id = #{id}")
    int deleteUser(@Param("id") int id);
}