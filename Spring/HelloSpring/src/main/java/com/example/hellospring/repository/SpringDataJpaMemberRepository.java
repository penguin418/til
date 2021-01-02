package com.example.hellospring.repository;

import com.example.hellospring.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataJpaMemberRepository extends
        JpaRepository<Member, Long>, // 테이블, 아이디타입
        MemberRepository // 리포지토리
{
    @Override
    Optional<Member> findByName(String name);
}
