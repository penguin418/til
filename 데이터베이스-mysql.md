# MySQL 특성

primary, foreign key 지원

index 15개 컬럼까지

commit → rollback

- 락 테이블 → 트랜잭션 → 언락 테이블

케이스 센시티브

- 케이스 센시티브 한 것: 키워드, 함수, 컬럼, 인덱스
- 그렇지 않은 것: 데이터베이스, 테이블, alias

# 1. 시작하기

```sql
mysql -u root -p 사용할 스키마 이름
Enter password: ****
```

## 1.1 설정

### 설정 조회

```sql
show variables [조건];
```

```sql
MariaDB [shop]> show variables like 'lower_case%';
+------------------------+-------+
| Variable_name          | Value |
+------------------------+-------+
| lower_case_file_system | ON    |
| lower_case_table_names | 1     |
+------------------------+-------+
```

### 설정 수정

```jsx
set variables lower_case_table_names=1;
```

- lower_case_table_names: 오브젝트를 소문자로 저장하는 옵션
    
    1로 지정시 윈도우(케이스센시티브x), 유닉스(케이스 센시티브o)간 호환성 
    

# 2. 계정

## 계정 관리

grant table을 사용한다

### 계정 생성

- 특정 장소에서만 접속하게 하려면 @붙인 이름으로 만든다

```sql
create user '계정';
create user '계정@localhost' identified by '비밀번호';
```

### 계정 조회

```sql
use mysql;
```

### 계정 삭제

- 테이블 삭제

```sql
drop user '삭제할계정@~';
```

- 컬럼 삭제,
    
    insert, delete, update 후, `flush privileges` 필요함
    
    출처: [https://velog.io/@taelee/mysql-user확인-및-추가](https://velog.io/@taelee/mysql-user%ED%99%95%EC%9D%B8-%EB%B0%8F-%EC%B6%94%EA%B0%80)
    

```sql
delete from user where user='계정'@'허용주소';
flush privileges;
```

### 계정 관리

- 권한 부여

```sql
grant all privileges on 테이블 to '계정'@'허용주소';
grant all privileges on 테이블.* to '계정'@'허용주소';
```

- 원격 접속 권한 수정

```jsx
rename user '계정'@'허용주소' to '계정'@'바뀐주소'
```

- 권한 삭제

```sql

revoke select,insert,delete,update,drop,alter privileges from 스키마.테이블 to 계정@~;
```

- 비밀번호(5.6버전)

```sql
update user set password=PASSWORD('비밀번호') where user='계정@~'
```

- 비밀번호(5.7버전)

```sql
update user set
```

```sql
mysqladmin -u 계정 -p password;
	현재 비밀번호
	새로운 비밀번호
```

## root 계정

### root비밀번호 변경

```sql
# mysql 테이블
use mysql;
# 업데이트
update user set password=password('새로운비번') where user='root';
# 확인
select host, user, password from user;
```

# 3. 스키마

## 스키마란

### 스키마의 정의

스키마는 데이터베이스의 전반적인 명세를 포함하는 메타데이터 집합으로  Data Dictionay에 저장된다

스키마는 데이터개체(Entity), 속성(Attribute), 관계(Relationship)및 제약조건등을 정의한다

### 스키마의 특징

스키마는 특정 데이터 모델을 이용하여 현실 세계의 특정 개념을 표현한다

스키마는 시간에 따라 불변이다

스키마는 데이터의 구조적 특성을 의미하며 인스턴스에 의해 규정된다

### 스키마 분류

스키마는 사용자의 관점에 따라 외부, 개념, 내부 스키마로 나뉜다

- 외부스키마
    
    외부스키마는 사용자 혹은 응용프로그램에서 필요로 하는 데이터베이스의 논리적 구조를 정의한 것이다
    
    하나의 데이터베이스에는 여러 외부스키마가 존재할 수 있다
    
    SQL혹은 JDBC등의 API를 통해 접근할 수 있다
    
- 개념스키마
    
    개념스키마는 데이터베이스의 전체적인 논리적 구조로 데이터베이스에 하나만 존재한다
    
    개념스키마는 개체간 관계, 데이터베이스 접근권한, 무결성 규칙에 관한 명세를 정의한다
    
- 내부스키마
    
    물리적 저장장치 입장에서 본 데이터베이스 구조이다
    

## 스키마 관리

### 스키마 생성

```sql
# MySQL에서 위 아래는 동일한 기능이다, 아래를 더 많이 쓴다
# charset은 데이터 인코딩 방식이고
# collate는 데이터 베이스내 문자를 인코딩방식이다
create schema `new_db` default character set utf8 collate utf8_bin;
create database `new_db` default charset=utf8 collate=utf8_bin;
```

### 스키마 조회

```sql
show databases;
+------------------+
|new_db            |
|mysql             |
|information_schema|
|performance_schema|
+------------------+
```

### 스키마 사용

선택한 데이터베이스를 사용하는 명령

```sql
use new_db
```

### 스키마 삭제

```sql
drop database new_db
```

# 4. 테이블

## 스토리지 엔진

테이블은 스토리지 엔진에 따라 데이터와 인덱스를 저장하는 방식이 다르다

### MyISAM

- 오래된 규칙
    
    전문 검색, 압축 지원
    
- Transaction 미지원
    
    빠르지만 동시성 제어가 어렵다
    
    쓰기 작업 시 테이블 전체에 락을 건다
    
- 수동 복구만 가능

### InnoDB

- Oracle과 유사한 아키텍쳐
    
    트랜잭션을 위해 고안되었다
    
    자동복구를 지원한다
    
- 데이터 저장방식
    
    기본키를 기준으로 클러스터링 되어 저장된다. 기본키 조회가 매우 빠르다
    
- Multi Version Concurrency Control
    
    읽기 작업시 락을 걸지 않는다
    
- 외래키
    
    엔진레벨에서 외래키 제약사항을 지원한다
    

### Memory

- 메모리에 데이터를 저장한다
    
    속도가 매우 빠르지만 데이터가 사라질 수 있다
    
- 용도
    
    쿼리 중간결과 저장용으로 사용된다
    

## 테이블 속성

### 테이블 타입

```sql
인덱스 특성

```

## 테이블 관리

### 테이블 생성

```sql
create table new_table(
	id INT(16) PRIMARY KEY,
	name VARCHAR(10)
);
```

### 컬럼 설계

- Primary Key
    
    AI(Auto Increment)에 최적화 되어있다 별일 없으면 이것을 사용하자
    
    AI 사용시 장점
    
    - 고속 병행 Insert가능
    - 
    

### 데이터베이스 조회

### 테이블 조회

- 모든 테이블 조회

```sql
show tables
+------------------+
|tables_in_new_db  |
+------------------+
|new_table         |
+------------------+
```

- 테이블 존재 확인

```sql
select * from 테이블이름 where 0;
```

- 테이블 필드 조회

```sql
desc new_table;
+------+-------------+------+-----+---------+-------+
|Field | Type        | Null | Key | Default | Extra |
+------+-------------+------+-----+---------+-------+
|id    | int(16)     | No   | PRI | NULL    |       |
|name  | varchar(10) | Yes  |     | NULL    |       |
+------+-------------+------+-----+---------+-------+
```

- 테이블 정보 조회

```sql
show table status like 'account';
+---------+--------+---------+-------... +---------------
| Name    | Engine | Version | Row_fo... | Auto_increment
+---------+--------+---------+-------... +---------------
| account | InnoDB |      10 | Compac... |              4
+---------+--------+---------+-------... +---------------
```

engine→ 스토리지 엔진으로 innoDB를 사용하고 있다

Row_format→ 레코드 포맷으로 Compact를 사용하고 있다

Auto_increment→ 다음 Auto_increment값

### 테이블 변경

- 이름변경

```sql
# 단일 테이블
alter table old_table to new_table;
rename table old_table to new_table;
# 복수 테이블
rename table old1 to new1,
						 old2 to new2;
```

- 컬럼 추가

```sql
alter table new_table add column new_column varchar(10) not null;
```

- 컬럼 변경

```sql
alter table new_table modify column new_column varchar(16) not null;
```

- 컬럼 이름 변셩

```sql
alter table new_table change column new_column new_new_column varchar(16) not null;
```

- 컬럼 삭제

```sql
alter table new_table drop column new_column
```

- 테이블 삭제

```sql
drop table new_table;
```

# 5. 컬럼

## 컬럼 관리

### 컬럼 추가

- 전체 값 추가

```sql
insert into new_table values (값, 값, ... 전체 값)
```

- 여러 줄 추가

```sql
insert into new_table values (전체 값), (전체 값)
```

- 일부 추가

```sql
insert into new_table 컬럼, 컬럼, 컬럼 values (값, 값, 값)
```

### 컬럼 갱신

```sql
update new_table set 필드 = 값
```

### 컬럼 삭제

- 전체 삭제

```sql
delete from new_table
```

- 조건 부 삭제

```sql
delete from new_table where 조건
```

### 컬럼 조회

- 전체 조회

```sql
select * from 테이블
```

- 일부 조회

```sql
select id, name from 테이블
```

- 별칭으로 조회

```sql
select id, name as n from 테이블
```

- 조건에 따라 다른 이름으로 조회

```sql
select *, ( 
	case 
		when id = 1 or id = 2 then 'admin'
		when id < 1000 then 'group1'
		else 'other'
) from 테이블
```

### 조회 결과 수정

- 문자열 붙이기

```sql
select concat (id, ' ', name) as 컬럼이름 from table
```

주의) null과 합쳐지면 null임

```sql
select concat (null, 1)
결과: null
```

- 문자열 자르기 (왼쪽)

```sql
-- 왼쪽
select left(name, 1) from ~
-- 중간
select mid(name, 1, 2) from ~
-- 오른쪽
select right(name, 1) from ~
```

- 대문자, 소문자

```sql
select upper(name), lower(name) from ~
```

- 공백제거

```sql
-- 좌우 공백 제거
select trim('  공백있는문자열 ') from ...
select trim(BOTH ' ' from ' 공백있는문자열 ') from ...

-- 왼쪽 공백 제거
select ltrim('  공백있는문자열 ') from ...
select trim(LEADING' ' from ' 공백있는문자열 ') from ...

-- 오른쪽 공백 제거
select rtrim('  공백있는문자열 ') from ...
select trim(TRAILING' ' from ' 공백있는문자열 ') from ...
```

[https://extbrain.tistory.com/65?category=270532](https://extbrain.tistory.com/65?category=270532)

# 조회 전략

## 실행계획

질의문이 어떻게 실행되는지 확인

```sql
explain select * from account;
```

- id: 쿼리 아이디
- table: 사용된 테이블
- select_type: 쿼리 종류
    
    simple: 심플한 쿼리
    
    primary: 서브 쿼리에서 가장 바깥 쿼리
    
    union: union쿼리이 사용된 쿼리
    
    union result: union쿼리로 생성된 임시 테이블
    
    dependent union: 외부 쿼리의 결과에 의존하는 union 쿼리
    
    derived: from쿼리에서 사용된 쿼리
    
    subquery: from쿼리 제외 사용된 쿼리
    
    dependent subquery: 외부 쿼리의 결과에 의존하는 서브쿼리
    
    uncacheable subquery: 캐시가 안되는 서브쿼리
    
- type: 옵티마이저에 의한 조회 방식
    
    system: 데이터가 하나인 테이블을 조회시
    
    const: unique조건이 걸린 컬럼(테이블당 하나)을 조회시
    
    eq_ref: 
    

### 옵티마이저

질의에 대한 최적 실행방법을 결정

- 규칙 기반 옵티마이저 (거의 사장됨)
    
    규칙의 우선순위를 따라서 조인 순서를 결정함
    
    주요 규칙
    
    - 1 Single Row by row
        
        rowid를 통한 액세스, 1번 규칙, 하나의 행을 액세스하는 가장 빠른 방법
        
    - 4 Single Row by unique or primary key
        
        unique index를 사용해 액세스
        
    - 8 Composite Index
        
        = 조건 많을수록 우선 처리, e.g. a=~ and b=~ 조건이랑 , c=~ and d=~ and e=~ 중 후자가 먼저
        
    - 9 Single Column Index
        
        단일 컬럼 인덱스 일치(=) 조회 (8번 조건에도 일치, 1개면 가장 적으므로)
        
    - 10 Bounded range search on indexed columns
        
        양쪽범위 한정 (a between b and c)
        
    - Unbounded range search on indexed columns
        
        한쪽범위 한정
        
    - Full scan
        
        전체테이블스캔 (느릴수밖에 없음)
        
- 비용기반 옵티마이저
    
    통계정보를 따름, 정확한 통계정보가 중요
    

[https://mozi.tistory.com/220](https://mozi.tistory.com/220)

드라이빙 드리븐

join에서 먼저 액세스 되는 테이블을 드라이빙 테이블이라고 부름

행이 적은 수를 드라이빙 하는 것이 좋음

## 임시테이블

임시테이블을 사용한 조회

```sql
-- tmp라는 이름의 임시테이블 생성
-- 메모리에 생성할 경우, heap옵션
create temporary table tmp (id BIGINT) [type=heap]

-- 조회할 데이터 옮김
insert into tmp (id)
select account_id as id from account
```

# 인덱스

## 인덱스 관리

### 인덱스 생성

- 테이블과 함께 생성

```sql
create table account {
  account_id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
  email CHAR(16),

  UNIQUE INDEX `uq_email` (`email` ASC)
}
```

- 테이블에 대해 생성(alter table사용)

```sql
ALTER TABLE account ADD CONSTRAINT uq_email UNIQUE(email);
```

- 테이블에 대해 생성(create index 사용)

```sql
create index uq_email on account(email);
```

### 인덱스 확인

- 테이블에 대해 확인

```sql
show index from account;
```

# 삽입전략

## 채번방법

1. 채번 테이블
    
    ```sql
    # 테이블 이름과 해당 테이블의 sequence만을 담은 테이블을 생성
    create table sequences ( 
    	seq_type char(4) primary key, 
    	seq_num decimal
    )
    # 채번 테이블 초기화
    insert into sequences values('user', 1);
    # 호출 메서드
    DELIMITER $$
    create function `new_seq`( table_type varchar(4) ) returns INT
    begin
    	declare result INTEGER;
    	update sequences set seq_num = seq_num+1 where seq_type=table_type;
    	select seq_num into result from sequences where seq_type=table_type;
    	return result;
    end $$
    DELIMITER ;
    # 사용방법: 'user'테이블의 다음 PK를 생성
    start transaction;
    		select new_seq('user')
    commit;
    ```
    
    - 장점: DUP(중복에러) 안생김
    - 단점: lock 발생, 성능저하
2. 태이블 최댓값
    
    ```sql
    # 데이터가 없는 경우 null이므로 ifnull을 사용하면 좀더 편하다
    select ifnull(max('pk')+1, 1) from uesr
    ```
    
    - 장점: 빠른 성능
    - 단점: DUP 발생 가능
3. Sequence 객체
    - 장점: 빠른 성능, DUP없음, LOCK없음
    - 단점: 오라클에만 있음. 위처럼 직접 구현필요하며 결국 lock을 쓰게 됨
4. auto_increment
    
    ```sql
    create table sample(
    	id int auto_increment not null primary key,
      ...
    ) auto_increment = 1;
    insert into sample values(null, ...)
    ```
    
    - 장점: mysql에 가장 최적화 됨. lock없음
    - 단점: int만 가능

# 기타

join이 subselect보다 빠름