# 스프링 마이바티스

# Mybatis

마이바티스는 JDBC 프레임워크로 JDBC 연결과정을 상당부분 대신해 주어 (1) 커넥션 풀 관리가 쉽고, (2) JDBC연결 과정과 객체 매핑과정을 생략해 비지니스 로직에 집중할 수 있고, (3) SQL을 분리해서 확인할 수 있다 

# Mybatis-Spring

Mybatis-Spring연결 모듈은 마이바티스와 스프링을 간단하게 연동하도록 해준다. 얼마나 간단하게 연결해주냐면, 

참고: 다음 문서는 아주 자세하게 설명해주고 있다

[https://mybatis.org/spring/ko/getting-started.html](https://mybatis.org/spring/ko/getting-started.html)

# 사용방법

## 1. 의존성

maven을 사용하는 경우

```groovy
<dependency>
  <groupId>org.mybatis</groupId>
  <artifactId>mybatis-spring</artifactId>
  <version>2.0.6</version>
</dependency>
```

하지만 gradle을 사용하기 때문에 다음과 같이 설정하였다

```groovy
implementation 'org.mybatis.spring.boot:mybatis-spring-boot-starter:2.2.0'
```

## 2. Configuration

mybatis 사용을 위해서는 2가지가 필요하다 (1) SqlSessionFactory과 (2) Mapper이다.

- SqlSessionFactory는 dataSource로부터 SqlSession을 생성한다 SqlSession은 RDB와의 논리적인 연결상태로, 자동으로 커밋, 롤백을 수행하고 닫아주는 Thread-Safe한 객체이다
- Mapper는 SQL분리와 쿼리 결과를 객체로 매핑해 주는 과정을 수행한다.

위의 두가지를 설정하는 방법은 여러가지가 있지만, 원래 프레임워크를 사용하는 것은 비지니스 로직에 더 집중하기 위함이므로 가장 쉬운 세팅 2가지를 다뤄본다

### Java 코드만으로 구성하기

자바 코드로만 구성하는 방법은 @MapperScan을 사용한다. 이 방법의 장점은 간단한 @Annotation만으로 귀찮은 트랜잭션과 매핑을 피할 수 있다는 점이다.  단점은 데이터 영속화 이전에 어떤 처리를 해주고 싶은 경우에 Repository 계층을 추가로 구성해야 한다는 것이다

1. SqlSessionFactory 등록

    Configuration을 하나 만들어서 SqlSessionFactory를 등록한다

    ```java
    @Configuration
    @MapperScan("or.example.dao")
    public class MyBatisConfig {

      @Bean
      public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        return sessionFactory.getObject();
      }
    }
    ```

2. Dao 생성

    ```java
    package org.example.dao;

    @Repository
    public interface UserDao {
    	@Insert("INSERT INTO users(id, username, password) values (#{id}, #{username}, #{password})")
    	public int insertUser(User user);
    	
    	@Select("SELECT * FROM users WHERE id = #{id}")
    	User selectUser(@Param("id") int id);
    	
    	@Update("UPDATE users SET username = #{username}, password=#{password}" +
    	        "WHERE id = #{id}")
    	int updateUser(User user);
    	
    	@Delete("DELETE FROM users WHERE id = #{id}")
    	int deleteUser(@Param("id") int id);
    }
    ```

    인터페이스에 주의하자

### SqlSessionTemplate 사용하여 구성하기

SqlSessionTemplate는 Mybatis-Spring연결모듈의 핵심으로 SqlSessio의 기본 구현체인 DefaultSqlSession을 대신하여 사용된다 

1. SqlSessionFactory + SqlSessionTemplate등록

    ```java
    @Configuration
    @AllArgsConstructor
    public class MybatisConfig {
      private final ApplicationContext context;

      @Bean
      public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setMapperLocations(context.getResources("classpath:/mapper/*.xml"));
        return sqlSessionFactoryBean.getObject();
      }

      @Bean
      public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
      }
    }
    ```

2. Mapper 생성

    Mapper는 SQL문을 담고 있다. 다음과 같이 DAO에 들어갈 sql을 작성한다

    ```xml
    <?xml version="1.0" encoding="UTF-8" ?>
    <!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "mybatis-3-mapper.dtd">

    <mapper namespace="USERS">
      <!--매핑-->
      <resultMap id="userMapper" type="example.boilerplate.springmybatis.entity.User">
        <id column="id" javaType="int" property="id"/>
        <id column="username" javaType="String" property="username"/>
        <id column="password" javaType="String" property="password"/>
      </resultMap>

      <!--직접 아이디로 추가하는 쿼리문, 이미 존재하는 아이디로 추가하지 않으면 괜찮은 것 같음-->
      <insert id="insertUserWithId" parameterType="java.util.HashMap">
        INSERT INTO users (id, username, password)
        VALUES( #{id}, #{username}, #{password} )
      </insert>
    ```

    namespace를 설정하지 않는 경우, 패키지 경로를 대신 사용해도 된다. 다만 많이 귀찮다

3. Dao생성

    dao에서는 sqlSessionTemplate을 통해 mapper를 사용하여 아래처럼  추가적인 코드를 작성할 수 있다

    ```java
    @Repository
    @AllArgsConstructor
    public class UserDao {
      private final static String namespace = "USERS";
      private final SqlSession sqlSession;

      public int insertUserWithId(User user) {
        return this.sqlSession.insert(
              namespace + ".insertUserWithId",
              new HashMap<String, Object>() {{
                  put("id", user.getId());
                  put("username", user.getUsername());
                  put("password", user.getPassword());
              }}
        );
      }
    ```# 스프링 마이바티스

# Mybatis

마이바티스는 JDBC 프레임워크로 JDBC 연결과정을 상당부분 대신해 주어 (1) 커넥션 풀 관리가 쉽고, (2) JDBC연결 과정과 객체 매핑과정을 생략해 비지니스 로직에 집중할 수 있고, (3) SQL을 분리해서 확인할 수 있다 

# Mybatis-Spring

Mybatis-Spring연결 모듈은 마이바티스와 스프링을 간단하게 연동하도록 해준다. 얼마나 간단하게 연결해주냐면, 

참고: 다음 문서는 아주 자세하게 설명해주고 있다

[https://mybatis.org/spring/ko/getting-started.html](https://mybatis.org/spring/ko/getting-started.html)

# 사용방법

## 1. 의존성

maven을 사용하는 경우

```groovy
<dependency>
  <groupId>org.mybatis</groupId>
  <artifactId>mybatis-spring</artifactId>
  <version>2.0.6</version>
</dependency>
```

하지만 gradle을 사용하기 때문에 다음과 같이 설정하였다

```groovy
implementation 'org.mybatis.spring.boot:mybatis-spring-boot-starter:2.2.0'
```

## 2. Configuration

mybatis 사용을 위해서는 2가지가 필요하다 (1) SqlSessionFactory과 (2) Mapper이다.

- SqlSessionFactory는 dataSource로부터 SqlSession을 생성한다 SqlSession은 RDB와의 논리적인 연결상태로, 자동으로 커밋, 롤백을 수행하고 닫아주는 Thread-Safe한 객체이다
- Mapper는 SQL분리와 쿼리 결과를 객체로 매핑해 주는 과정을 수행한다.

위의 두가지를 설정하는 방법은 여러가지가 있지만, 원래 프레임워크를 사용하는 것은 비지니스 로직에 더 집중하기 위함이므로 가장 쉬운 세팅 2가지를 다뤄본다

### Java 코드만으로 구성하기

자바 코드로만 구성하는 방법은 @MapperScan을 사용한다. 이 방법의 장점은 간단한 @Annotation만으로 귀찮은 트랜잭션과 매핑을 피할 수 있다는 점이다.  단점은 데이터 영속화 이전에 어떤 처리를 해주고 싶은 경우에 Repository 계층을 추가로 구성해야 한다는 것이다

1. SqlSessionFactory 등록

    Configuration을 하나 만들어서 SqlSessionFactory를 등록한다

    ```java
    @Configuration
    @MapperScan("or.example.dao")
    public class MyBatisConfig {

      @Bean
      public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        return sessionFactory.getObject();
      }
    }
    ```

2. Dao 생성

    ```java
    package org.example.dao;

    @Repository
    public interface UserDao {
    	@Insert("INSERT INTO users(id, username, password) values (#{id}, #{username}, #{password})")
    	public int insertUser(User user);
    	
    	@Select("SELECT * FROM users WHERE id = #{id}")
    	User selectUser(@Param("id") int id);
    	
    	@Update("UPDATE users SET username = #{username}, password=#{password}" +
    	        "WHERE id = #{id}")
    	int updateUser(User user);
    	
    	@Delete("DELETE FROM users WHERE id = #{id}")
    	int deleteUser(@Param("id") int id);
    }
    ```

    인터페이스에 주의하자

### SqlSessionTemplate 사용하여 구성하기

SqlSessionTemplate는 Mybatis-Spring연결모듈의 핵심으로 SqlSessio의 기본 구현체인 DefaultSqlSession을 대신하여 사용된다 

1. SqlSessionFactory + SqlSessionTemplate등록

    ```java
    @Configuration
    @AllArgsConstructor
    public class MybatisConfig {
      private final ApplicationContext context;

      @Bean
      public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setMapperLocations(context.getResources("classpath:/mapper/*.xml"));
        return sqlSessionFactoryBean.getObject();
      }

      @Bean
      public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
      }
    }
    ```

2. Mapper 생성

    Mapper는 SQL문을 담고 있다. 다음과 같이 DAO에 들어갈 sql을 작성한다

    ```xml
    <?xml version="1.0" encoding="UTF-8" ?>
    <!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "mybatis-3-mapper.dtd">

    <mapper namespace="USERS">
      <!--매핑-->
      <resultMap id="userMapper" type="example.boilerplate.springmybatis.entity.User">
        <id column="id" javaType="int" property="id"/>
        <id column="username" javaType="String" property="username"/>
        <id column="password" javaType="String" property="password"/>
      </resultMap>

      <!--직접 아이디로 추가하는 쿼리문, 이미 존재하는 아이디로 추가하지 않으면 괜찮은 것 같음-->
      <insert id="insertUserWithId" parameterType="java.util.HashMap">
        INSERT INTO users (id, username, password)
        VALUES( #{id}, #{username}, #{password} )
      </insert>
    ```

    namespace를 설정하지 않는 경우, 패키지 경로를 대신 사용해도 된다. 다만 많이 귀찮다

3. Dao생성

    dao에서는 sqlSessionTemplate을 통해 mapper를 사용하여 아래처럼  추가적인 코드를 작성할 수 있다

    ```java
    @Repository
    @AllArgsConstructor
    public class UserDao {
      private final static String namespace = "USERS";
      private final SqlSession sqlSession;

      public int insertUserWithId(User user) {
        return this.sqlSession.insert(
              namespace + ".insertUserWithId",
              new HashMap<String, Object>() {{
                  put("id", user.getId());
                  put("username", user.getUsername());
                  put("password", user.getPassword());
              }}
        );
      }
    ```