# practice-quarkus Project

다음 명령어로 생성됨

```
quarkus create com.github.penguin418:practice-quarkus
```

다음 명령어로 의존성을 추가함

```
quarkus ext add \
'spring-web' \
'spring-data-jpa' \
'jdbc-h2'
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
