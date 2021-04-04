# Spring 연습

## Hello Spring

Hello Spring 예제입니다

- 원하는 repository를 주입할 수 있습니다
```java
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

//    @Bean // 대신 @Component 로 등록 가능
//    public TimeTraceAOP timeTraceAOP(){
//        return new TimeTraceAOP();
//    }

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
```

출처: [인프런](https://www.inflearn.com/course/%EC%8A%A4%ED%94%84%EB%A7%81-%EC%9E%85%EB%AC%B8-%EC%8A%A4%ED%94%84%EB%A7%81%EB%B6%80%ED%8A%B8)

## Wordbook

단어장

Controller에서 Model과 Form 사용법을 연습했습니다

- front에서 단어장을 만들어 업로드합니다
    ```javascript
    $("#add-wordbook").on('click', function (e) {
        let form = new FormData()
        form.append("name", $("#name").val())
        form.append("description", $("#description").val())
        var counter = 0
        $.each($(".qa"), function (index, qa) {
            form.append("qaList[" + index + "].question", $(qa).find(".qa-question").text())
            form.append("qaList[" + index + "].answer", $(qa).find(".qa-answer").text())
            counter += 1
        })

        {
            name: ~,
            description : "!",
            qaList[0].question = ~!@

        }
        if (counter < 1){
            alert("1개 이상의 문제 or 단어를 추가해야 합니다")
            return
        }
        axios.post('/wordbook/create', form)
            .then(function (res) {
                window.location = "/wordbook/list"
            })
            .catch(function (err) {
                alert('서버오류', err)
            });
    })
    ```
- Spring에선 Model을 사용하여 단어장을 입력받습니다
    ```java
    @Controller
    public class WordbookController {
        ...
        @PostMapping("/wordbook/update")
        public String PostWordbookUpdate(Wordbook wordbook) throws JsonProcessingException {
            ObjectMapper objectMapper = new ObjectMapper();
            System.out.println(objectMapper.writeValueAsString(wordbook));


            wordbookService.update(wordbook);
            return "redirect:/wordbook/list";
        }
        ...
    ```