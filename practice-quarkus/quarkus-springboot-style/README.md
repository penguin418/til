# practice-quarkus Project



quarkus 기반 spring-web 스타일의 블로그 REST API


다음 명령어로 생성됨

```
quarkus create com.github.penguin418:practice-quarkus
```

다음 의존성을 추가함

```
    implementation 'io.quarkus:quarkus-spring-di'
    implementation 'io.quarkus:quarkus-hibernate-orm'
    compileOnly "org.projectlombok:lombok:${lombokVersion}"
    annotationProcessor "org.projectlombok:lombok:${lombokVersion}"
    implementation "org.jboss.logging:commons-logging-jboss-logging"

    implementation 'io.quarkus:quarkus-jdbc-h2'
    implementation 'io.quarkus:quarkus-spring-data-jpa'
    implementation 'io.quarkus:quarkus-resteasy-reactive-jackson'
    implementation 'io.quarkus:quarkus-spring-web'
```

다음 명령어로 개발

```
quarkus dev
```

다음 명령어로 빌드

```
./gradlew build
```

다음 명령어로 실행

```
java -jar build/quarkus-app/quarkus-run.jar
```
