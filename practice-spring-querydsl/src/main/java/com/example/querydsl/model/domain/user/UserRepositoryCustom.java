package com.example.querydsl.model.domain.user;


import java.util.List;

public interface UserRepositoryCustom {
    public List<User> findByConditions(String usernameLike, Integer powerBound);
}
