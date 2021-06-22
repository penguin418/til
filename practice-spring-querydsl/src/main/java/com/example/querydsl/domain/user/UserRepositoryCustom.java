package com.example.querydsl.domain.user;


import com.querydsl.core.BooleanBuilder;
import org.springframework.util.StringUtils;

import java.util.List;

public interface UserRepositoryCustom {
    public List<User> findByConditions(String usernameLike, Integer powerBound);
}
