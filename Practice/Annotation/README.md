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