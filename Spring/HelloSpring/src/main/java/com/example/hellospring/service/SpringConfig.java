package com.example.hellospring.service;

import com.example.hellospring.repository.JPAMemberRepository;
import com.example.hellospring.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class SpringConfig
{
//    private DataSource dataSource;
//    @PersistenceContext
//    private EntityManager em;
    private final MemberRepository memberRepository;


    @Autowired
    public SpringConfig(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }
    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository);
    }

//    @Bean
//    public MemberService memberService(){
//        return new MemberService(memberRepository());
//    }
//
//
//    @Bean
//    public MemberRepository memberRepository(){
//        // return new MemoryMemberRepository();
////        return new JdbcTemplateMemberRepository(dataSource);
////        return new JPAMemberRepository(em);
//    }
}