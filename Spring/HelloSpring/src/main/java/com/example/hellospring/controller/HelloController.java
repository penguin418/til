<<<<<<< HEAD
package com.example.hellospring.controller;

import com.example.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


public class HelloController {
    @GetMapping("hello") // @Notation을 통해 hello 리소스에 대한 GET을 처리함을 알린다
    public String hello(Model model) { // MVC 중 모델을 받는다
        model.addAttribute("data", "world"); // 모델에 attribute를 추가한다
        return "hello"; // resources/template의 <viewname>.html이 열림 (hello)
    }

    @GetMapping("hello-para")
    public String helloPara(@RequestParam("para") String para, Model model) {
        model.addAttribute("para", para);
        return "hello-para";
    }

    @GetMapping("hello-string")
    @ResponseBody
    public String helloString(Model model){
        return "string";
    }
    @GetMapping("hello-info")
    @ResponseBody
    public Info helloInfo(Model model){
        return new Info();
    }
    static class Info{
        private String name;
        // Alt + Insert로 Getter,Setter를 자동완성하자
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
    }
=======
package com.example.hellospring.controller;

import com.example.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


public class HelloController {
    @GetMapping("hello") // @Notation을 통해 hello 리소스에 대한 GET을 처리함을 알린다
    public String hello(Model model) { // MVC 중 모델을 받는다
        model.addAttribute("data", "world"); // 모델에 attribute를 추가한다
        return "hello"; // resources/template의 <viewname>.html이 열림 (hello)
    }

    @GetMapping("hello-para")
    public String helloPara(@RequestParam("para") String para, Model model) {
        model.addAttribute("para", para);
        return "hello-para";
    }

    @GetMapping("hello-string")
    @ResponseBody
    public String helloString(Model model){
        return "string";
    }
    @GetMapping("hello-info")
    @ResponseBody
    public Info helloInfo(Model model){
        return new Info();
    }
    static class Info{
        private String name;
        // Alt + Insert로 Getter,Setter를 자동완성하자
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
    }
>>>>>>> 9e30e7704ba0cf0457cc8358de06c509ee717597
}