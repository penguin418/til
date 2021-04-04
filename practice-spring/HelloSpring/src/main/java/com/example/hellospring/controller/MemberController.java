package com.example.hellospring.controller;

import com.example.hellospring.domain.Member;
import com.example.hellospring.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller // @Notation 을 통해 컨트롤러임을 알린다
// 자동이든 수동이든, 컨트롤러에서는 넣어주어야함
// xml 방식은 거의 안씀
// di 방법: 필드주입, 생성자주입, setter주입
public class MemberController {
    // @Autowired // 필드주입
    private final MemberService memberService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

//    @Autowired // Setter주입, 아무나 부를 수 있어 위험
//    public void setMemberService(MemberService memberService){
//        this.memberService = memberService;
//    }

    // 생성자 주입
    @Autowired // 스프링 빈에 등록된 것을 넣어줌, DI
    public MemberController(MemberService memberService){
        this.memberService = memberService;
    }

    @GetMapping("/")
    public String Home(Model model){
        return "home";
    }

    @GetMapping("/members/new")
    public String CreateForm(){
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(MemberForm form){
        Member member = new Member();
        member.setName(form.getName());
        memberService.join(member);
        logger.debug("member added"+member.toString());
        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model){
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }

}
