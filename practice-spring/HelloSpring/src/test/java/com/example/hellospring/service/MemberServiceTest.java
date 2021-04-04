package com.example.hellospring.service;

import com.example.hellospring.domain.Member;
import com.example.hellospring.repository.MemberRepository;
import com.example.hellospring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
//    @BeforeEach
//    public void beforeEach(){
//        repository = new MemoryMemberRepository();
//        memberService = new MemberService(repository); // DI,의존성주입
//    }

// @Transactional 사용하면 필요없음
//    @AfterEach
//    public void afterEach(){
//        memberRepository.clearStore();
//    }

    @Test
    void join() {
        // given
        Member member = new Member();
        member.setName("hello");
        // when : 검증하는 대상
        Long saveId = memberService.join(member);
        // then
        Member findMember = memberService.findOne(saveId).get();
        Assertions.assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    void findMembers() {
        // given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");
        // when
        memberService.join(member1);
        assertThrows(IllegalStateException.class, ()->memberService.join(member2));
        // then
    }

    @Test
    void findOne() {
    }
}