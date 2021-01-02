package com.example.hellospring.service;

import com.example.hellospring.repository.JPAMemberRepository;
import com.example.hellospring.repository.JdbcTemplateMemberRepository;
import com.example.hellospring.repository.MemberRepository;
import com.example.hellospring.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

@Configuration
public class SpringConfig
{
    private DataSource dataSource;
    @Autowired
    public SpringConfig(DataSource dataSource){
        this.dataSource = dataSource;
    }
    @Bean
    public MemberService memberService(){
        return new MemberService(memberRepository());
    }

    @PersistenceContext
    private EntityManager em;

    @Bean
    public MemberRepository memberRepository(){
        // return new MemoryMemberRepository();
//        return new JdbcTemplateMemberRepository(dataSource);
        return new JPAMemberRepository(em);
    }
}