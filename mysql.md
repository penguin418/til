# MySQL

# 1. 시작하기

```sql
mysql -u root -p
Enter password: ****
```

# 2. 계정 관리

## 2.1 계정 생성

## 2.2 계정 조회

## 2.3 계정 삭제

```sql
drop user '삭제할계정@localhost'
```

## 2.1. root비밀번호 변경

```sql
# mysql 테이블
use mysql;
# 업데이트
update user set password=password('새로운비번') where user='root';
# 확인
select host, user, password from user;
```

# 3. 스키마

## 3.1 스키마란

### 3.1.1 스키마의 정의

스키마는 데이터베이스의 전반적인 명세를 포함하는 메타데이터 집합으로  Data Dictionay에 저장된다

스키마는 데이터개체(Entity), 속성(Attribute), 관계(Relationship)및 제약조건등을 정의한다

### 3.1.2 스키마의 특징

스키마는 특정 데이터 모델을 이용하여 현실 세계의 특정 개념을 표현한다

스키마는 시간에 따라 불변이다

스키마는 데이터의 구조적 특성을 의미하며 인스턴스에 의해 규정된다

### 3.1.2 스키마 분류

스키마는 사용자의 관점에 따라 외부, 개념, 내부 스키마로 나뉜다

3.1.2.1 외부스키마

외부스키마는 사용자 혹은 응용프로그램에서 필요로 하는 데이터베이스의 논리적 구조를 정의한 것이다

하나의 데이터베이스에는 여러 외부스키마가 존재할 수 있다

SQL혹은 JDBC등의 API를 통해 접근할 수 있다

3.1.2.2 개념스키마

개념스키마는 데이터베이스의 전체적인 논리적 구조로 데이터베이스에 하나만 존재한다

개념스키마는 개체간 관계, 데이터베이스 접근권한, 무결성 규칙에 관한 명세를 정의한다

3.1.2.3 내부스키마

물리적 저장장치 입장에서 본 데이터베이스 구조이다

## 3.2 스키마 관리

### 3.2.1 스키마 생성

```sql
# MySQL에서 위 아래는 동일한 기능이다, 아래를 더 많이 쓴다
# charset은 데이터 인코딩 방식이고
# collate는 데이터 베이스내 문자를 인코딩방식이다
create schema 'new_db' default character set utf8 collate utf8_bin;
create database 'new_db' default charset=utf8 collate=utf8_bin;
```

### 3.2.2 스키마 조회

```sql
show database;
```

```sql
+------------------+
|new_db            |
|mysql             |
|information_schema|
|performance_schema|
+------------------+
```

### 3.2.3 스키마 사용

선택한 데이터베이스를 사용하는 명령

```sql
use new_db
```

### 3.2.4 스키마 삭제

```sql
drop database new_db
```

# 4. 테이블

## 4.2 테이블 관리

### 4.2.1 테이블 생성

```sql
create table new_table(
	id INT(16) PRIMARY KEY,
	name VARCHAR(10)
);
```

### 4.2.2 테이블 조회

4.2.2.1 모든 테이블 조회

```sql
show tables
```

```sql
+------------------+
|tables_in_new_db  |
+------------------+
|new_table         |
+------------------+
```

4.2.2.2 테이블 상세 조회

```sql
desc new_table;
```

```sql
+------+-------------+------+-----+---------+-------+
|Field | Type        | Null | Key | Default | Extra |
+------+-------------+------+-----+---------+-------+
|id    | int(16)     | No   | PRI | NULL    |       |
|name  | varchar(10) | Yes  |     | NULL    |       |
+------+-------------+------+-----+---------+-------+
```

### 4.2.2 테이블 변경

4.2.2.1 이름변경

```sql
# 단일 테이블
alter table old_table to new_table;
rename table old_table to new_table;
# 복수 테이블
rename table old1 to new1,
						 old2 to new2;
```

4.2.2.2 컬럼 추가

```sql
alter table new_table add column new_column varchar(10) not null;
```

4.2.2.3 컬럼 변경

```sql
alter table new_table modify column new_column varchar(16) not null;
```

4.2.2.4 컬럼 이름 변셩

```sql
alter table new_table change column new_column new_new_column varchar(16) not null;
```

4.2.2.5 컬럼 삭제

```sql
alter table new_table drop column new_column
```

### 4.2.2 테이블 삭제

```sql
drop table new_table;
```

# 5. 컬럼

## 5.2.1 추가

5.2.1.1 전체 값 추가

```sql
insert into new_table values (값, 값, ... 전체 값)
```

5.2.1.2 여러 줄 추가

```sql
insert into new_table values (전체 값), (전체 값)
```

5.2.1.3 일부 추가

```sql
insert into new_table 컬럼, 컬럼, 컬럼 values (값, 값, 값)
```

## 5.2.2 갱신

```sql
update new_table set 필드 = 값
```

## 5.2.3 삭제

5.2.3.1 전체 삭제

```sql
delete from new_table
```

5.2.3.2 조건 부 삭제

```sql
delete from new_table where 조건
```

## 5.3 조회

### 5.3.1 전체 조회

```sql
select * from 테이블
```

### 5.3.2 일부 조회

```sql
select id, name from 테이블
```

### 5.3.3 별칭으로 조회

```sql
select id, name as n from 테이블
```

### 5.3.4 조건에 따라 다른 이름으로 조회

```sql
select *, ( 
	case 
		when id = 1 or id = 2 then 'admin'
		when id < 1000 then 'group1'
		else 'other'
) from 테이블
```

### 5.3.5 조회 결과 수정

5.3.5.1 문자열 붙이기

```sql
select concat (id, ' ', name) as 컬럼이름 from table
```

주의) null과 합쳐지면 null임

```sql
select concat (null, 1)
결과: null
```

5.3.5.2 문자열 자르기 (왼쪽)

```sql
'왼쪽'
select left(name, 1) from ~
'중간'
select mid(name, 1, 2) from ~
오른쪽'
select right(name, 1) from ~
```

5.3.5.3 대문자, 소문자

```sql
select upper(name), lower(name) from ~
```

5.3.5.6 공백제거

```sql
'좌우 공백 제거'
select trim('  공백있는문자열 ') from ...
select trim(BOTH ' ' from ' 공백있는문자열 ') from ...

'왼쪽 공백 제거'

select ltrim('  공백있는문자열 ') from ...
select trim(LEADING' ' from ' 공백있는문자열 ') from ...
'오른쪽 공백 제거'

select rtrim('  공백있는문자열 ') from ...
select trim(TRAILING' ' from ' 공백있는문자열 ') from ...
```

[https://extbrain.tistory.com/65?category=270532](https://extbrain.tistory.com/65?category=270532)