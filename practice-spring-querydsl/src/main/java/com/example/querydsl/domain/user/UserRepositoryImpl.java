package com.example.querydsl.domain.user;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.List;
import static com.example.querydsl.domain.user.QUser.user;

@AllArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<User> findByConditions(String usernameLike, Integer minimumPower) {

        // 빌더 패턴 1
        BooleanBuilder builder = new BooleanBuilder();
        if (StringUtils.hasText(usernameLike))
            builder.and(user.username.like("%"+usernameLike+"%"));

        if (minimumPower != null)
            builder.and(user.power.goe(minimumPower));

        return jpaQueryFactory
                .selectFrom(user)
                .where(builder)
                .fetch();

    }
}
