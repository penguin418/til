package com.example.hellospring.service;

import com.example.hellospring.domain.Member;
import com.example.hellospring.repository.MemberRepository;
import com.example.hellospring.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

// @Service // 스트링빈에 등록, 컴포넌트 스캔방식 <-> 자바코드로 직접 넣기
// @Componenet 어노테이션을 사용해서 스프링빈으로 자동 등록, 이 방법이 더 좋다
// 자동 싱글턴
public class MemberService {
    private MemberRepository memberRepository;

    // @Autowired
    public MemberService(MemberRepository repository){memberRepository = repository;}

    public Long join(Member member){
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
        .ifPresent(
                m->{
                    throw new IllegalStateException("이미 존재하는 회원입니다");
                }
        );
    }

    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
