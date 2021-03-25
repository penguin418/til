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

## Validator (Runtime Annotation 예제)

필드 타입에 대한 검증 로직을 담은 annotation 입니다

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
