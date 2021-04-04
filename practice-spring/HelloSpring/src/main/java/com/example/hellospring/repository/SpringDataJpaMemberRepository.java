package com.example.hellospring.repository;

import com.example.hellospring.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataJpaMemberRepository extends
        JpaRepository<Member, Long>, // 테이블, 아이디타입
        MemberRepository // 리포지토리
{
    // save, findOne, exists, count, delete 모두 자동 생성

    // ByNameAndId 도 만들 수 있음
    @Override
    Optional<Member> findByName(String name);

    // 복잡한 쿼리는 나중에 따로 만들 수 있음
}
