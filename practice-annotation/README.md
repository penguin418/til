# Annotation 연습

## CommentAnnotation

간단한 어노테이션입니다

- 클레스 범위에서 사용되므로 컴파일 이후 JVM에서는 없어집니다

    ```java
    @Documented
    @Retention(RetentionPolicy.CLASS)
    @Target({FIELD, METHOD})
    public @interface CommentAnnotation {
        String value(); // 필드가 하나인 경우 value로 이름 짓는 convention이 있습니다
    }
    ```

- 단순 주석용으로 적합합니다

    ```java
    public class Account {
        // value = 의 경우, 생략가능합니다
        @CommentAnnotation("must be longer than 3 characters")
        private String username;

        public Account(String username) {
            this.username = username;
        }
        
        // 물론 넣어도 상관 없습니다
        @CommentAnnotation(value = "new username should be checked in advance")
        public void updateUsername(String username){
            if (username.length() < 3){
                throw new IllegalArgumentException();
            }
            this.username = username;
        }
    }

    ```

- @Documented은 method의 javaDoc에도 이 어노테이션을 넣어줍니다

  ### Method Detail

  - updateUsername

      ```java
      @CommentAnnotation("new username should be checked in advance")
      public void updateUsername(java.lang.String username)
      ```

## Validator (Runtime Annotation 연습)

필드 타입에 대한 검증 로직을 구현한 annotation 입니다

Email, Password, PhoneNumber에 대해 검증합니다

- annotation, annotation을 검증하는 클래스, annotation을 사용하는 클래스로 구성됩니다
    ```java
    // @Valid: annotation입니다
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public @interface Valid {
        FieldType value();
    }
    ```

    ```java
    // Validtor: 실제 검증 로직을 담고 있는 프로세서입니다
    public class Validator {

        public <T> void validate(Object classObj
        ) throws IllegalAccessException, IllegalArgumentException {
            for (Field field : classObj.getClass().getDeclaredFields()) {
                if (field.getAnnotation(Valid.class) == null)
                    continue;
                FieldType fieldType = field.getAnnotation(Valid.class).value();
                field.setAccessible(true); 
                Object value = field.get(classObj); 
                validateField(fieldType, value);
            }
        }
        
        private <T> void validateField(FieldType fieldType, Object value
        ) throws IllegalArgumentException {
            switch (fieldType) {
                case EMAIL:
                    validateEmail(value);
                    break;
    	    ...
            }
        }

        protected void validateEmail(Object emailObj
        ) throws IllegalArgumentException {
            String email = (String)emailObj;
            if (!email.matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]+$")) {
                throw new IllegalArgumentException("email is not valid. " +
                        "should be like example@email.com");
            }
        }
        ...
    }
    ```
    * reflection이 사용되었습니다
      
      따라서 annotation의 Retention은 RUNTIME이 되어야 합니다

    ```java
    // RegisterForm: annotation을 사용하는 클래스입니다
    public class RegisterForm {
        @Valid(EMAIL)
        String email;
        @Valid(PASSWORD)
        String password;
        @Valid(PHONE_NUMBER)
        String phoneNumber;

        public RegisterForm(String email, String password, String phoneNumber) throws IllegalAccessException {
            this.email = email;
            this.password =password;
            this.phoneNumber = phoneNumber;
    	
    	// annotaion을 처리하기 위해 validate 메소드를 호출이 필요합니다
            Validator validator = new Validator();
            validator.validate(this);
        }
    }
    ```
  * 현재 예제는 spring 등의 프레임워크가 존재하지 않으므로, annotation의 적용 라이프 사이클을 관리해주는 외부 container가 없습니다.

    따라서 annotation을 사용하는 클래스에서 validator를 직접 호출해주어야 합니다.

## compileTimeMsg (Compile-time Annotation 연습)

컴파일 타임에 todo, fixme annotation을 검사하여 그 내용을 출력합니다

* compile annotation의 첫번째 기능인 컴파일 타임 메시지 출력 기능을 연습해봤습니다

- compileTimeMsg, compileTimeMsgAnnotation, compileTimeMsgAnnotationProcessor로 구성했습니다
- compileTimeMsg 모듈에는 MyClass가 있으며 수정이 필요한 부분에 대해 annotation으로 표시했습니다

    ```java
    public class MyClass {   // TypeElement

        private List<Character> randomCharacters; // VariableElement
        @XXX("length가 작동하지 않아요")
        private Integer length;            // VariableElement

        @Todo("randomString을 list에서 final String으로 리팩토링 예정입니다")
        public MyClass(List<Character> randomCharacters, Integer length) {
            this.randomCharacters = randomCharacters;
    //        this.length = length;
        }

        @FixMe("스트링으로 구현하는 것이 더 낫습니다")
        public String getRandomCharacters() {
            StringBuilder sb = new StringBuilder();
    ```

- compileTimeMsgAnnotation모듈에는 Todo, FixMe, XXX 3가지의 annotation이 있으며 각 annotation은 주석의 키워드에서 모티프를 얻었습니다

    ```
    (컴파일 직후 출력됨)
    ...
    > Task :compileTimeMsg:classes
    C:\Users\niug2\Development\til2\Practice\Annotation\compileTimeMsg\src\main\java\MyClass.java:13: warning: [Todo] randomString을 list에서 final String으로 리팩토링 예정입니다
        public MyClass(List<Character> randomCharacters, Integer length) {
               ^
    C:\Users\niug2\Development\til2\Practice\Annotation\compileTimeMsg\src\main\java\MyClass.java:10: warning: [XXX] length가 작동하지 않아요[unassigned]
        private Integer length;            // VariableElement
                        ^
    C:\Users\niug2\Development\til2\Practice\Annotation\compileTimeMsg\src\main\java\MyClass.java:19: warning: [FixMe] 스트링으로 구현하는 것이 더 낫습니다[unassigned]
        public String getRandomCharacters() {
                      ^
    3 warnings
    ...
    ```

- compileTimeMsgAnnotationProcessor모듈에는 anntotationProcessor가 있고 각각의 annotation을 다르게 처리합니다

    ```java
    // messager, typeUtils, elementUtils 등 유용한 툴들을 미리 등록합니다
    public class KeywordProcessor extends AbstractProcessor {

        private Messager messager;
    		...		

    		@Override
        public synchronized void init(ProcessingEnvironment processingEnv) {
            super.init(processingEnv);
            messager = processingEnv.getMessager();
    				...
        }
    ```

    ```java
    // supportedAnnotations() 를 만들면, 지원하는 annotation들을 쉽게 관리할 수 있습니다
    public class KeywordProcessor extends AbstractProcessor {
    		...
    		private Set<Class<? extends Annotation>> supportedAnnotations() {
            // 지원하는 어노테이션들
            return new HashSet<>(Arrays.asList(
                    Todo.class,
                    FixMe.class,
                    XXX.class
            ));
        }
    	
    		@Override
        public Set<String> getSupportedAnnotationTypes() {
            // 지원하는 어노테이션을 반환합니다
            return supportedAnnotations().stream().map(Class::getCanonicalName).collect(Collectors.toSet());
        }
    ```

    ```java
    // 이전 라운드를 겪었는지를 확인하여 그에 따라 처리할 수 있습니다
    public class KeywordProcessor extends AbstractProcessor {
    		...
        @Override
        public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnv) {
            if (!roundEnv.processingOver()) { // 처리되지 않음
                for (Class<? extends Annotation> annotation : supportedAnnotations()) {
                    processAnnotations(roundEnv, annotation);
                }
            }
            return true;
        }

    		private void processAnnotations(RoundEnvironment roundEnv, Class<? extends Annotation> annotation) {
            final Set<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith(annotation);
            for (Element annotatedElement : annotatedElements) {
                String msg = "";
                if (annotation == Todo.class) {
                    handleTodoMsg(annotatedElement);
    						...
    		}
    		
    		private void handleTodoMsg(Element element) {
    				String msg = "[TODO]" + element.getAnnotation(Todo.class).value();
            messager.printMessage(Diagnostic.Kind.WARNING, msg, element);
        }
    ```