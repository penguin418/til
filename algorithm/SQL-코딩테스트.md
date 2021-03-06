# 코딩테스트-SQL

1. GROUP BY

    [코딩테스트 연습 - 고양이와 개는 몇 마리 있을까](https://programmers.co.kr/learn/courses/30/lessons/59040)

    ```sql
    # 고양이, 개 몇마리인지 구하기
    SELECT animal_type, count(*) from animal_ins 
    where animal_type in ("Cat", "Dog")
    group by animal_type 
    order by field(animal_type,"Cat", "Dog")  # 필드순서 선택
    ```

    [코딩테스트 연습 - 중복 제거하기](https://programmers.co.kr/learn/courses/30/lessons/59408)

    ```sql
    # 중복 제거하기
    SELECT count(distinct name) # count(distinct)로 중복 제거하고 세기
    from animal_ins where not isnull(name)
    ```

    [코딩테스트 연습 - 동명 동물 수 찾기](https://programmers.co.kr/learn/courses/30/lessons/59041)

    ```sql
    # 같은 이름의 동물 세기
    SELECT name, count(*) from animal_ins 
    where not isnull(name)
    group by name having count(*) > 1 # having으로 집계 조건 주기
    order by name
    ```

    [코딩테스트 연습 - 입양 시각 구하기(1)](https://programmers.co.kr/learn/courses/30/lessons/59412)

    ```sql
    # 시간 대 별로 입양 횟수 구하기
    SELECT hour(datetime) h, count(*) from animal_outs
    group by h
    having h between 9 and 19
    order by h
    # where을 사용하는 경우, h를 직접 쓸 수 없다
    # where hour(datetime) between 9 and 19 
    ```

    [코딩테스트 연습 - 입양 시각 구하기(2)](https://programmers.co.kr/learn/courses/30/lessons/59413)

    ```sql
    # 0시 부터 23시 시간 대 별로 입양 횟수 구하기
    set @hour = -1;                          # @이름 으로 변수 사용하기!
    select (@hour := @hour +1) as HOUR,
    			 (select count(*) from animal_outs 
    			  where hour(datetime) = @hour) as b
    from animal_outs                         
    where @hour < 23;
    ```

2. 집합연산

    [코딩테스트 연습 - 우유와 요거트가 담긴 장바구니](https://programmers.co.kr/learn/courses/30/lessons/62284)

    ```sql
    # 우유랑 요거트를 모두 담은 장바구니만 고르기
    SELECT CART_ID FROM CART_PRODUCTS WHERE NAME='MILK'  # 우유 목록
    INTERSECT
    SELECT CART_ID FROM CART_PRODUCTS WHERE NAME='YOGULT' # 요거트 목록
    ```

3. SUM,MAX,MIN

    [코딩테스트 연습 - 최댓값 구하기](https://programmers.co.kr/learn/courses/30/lessons/59415)

    ```sql
    # 최대값 단 하나!
    SELECT datetime from animal_ins 
    order by datetime desc # 내림차순으로 정렬
    limit 1                # 하나만 출력
    ```

4.  ISNULL

    [코딩테스트 연습 - 이름이 없는 동물의 아이디](https://programmers.co.kr/learn/courses/30/lessons/59039)

    ```sql
    # name이 null인 것 고르기
    SELECT animal_id from animal_ins where isnull(name)
    ```

    [코딩테스트 연습 - 이름이 있는 동물의 아이디](https://programmers.co.kr/learn/courses/30/lessons/59407)

    ```sql
    # 이름이 null이 아닌 동물 출력하기
    SELECT ANIMAL_ID from animal_ins 
    where NAME <> "NULL" # varchar의 null은 "NULL"로 검사 가능하다
    ```

5. JOIN

    [코딩테스트 연습 - 있었는데요 없었습니다](https://programmers.co.kr/learn/courses/30/lessons/59043)

    ```sql
    # 기록이 잘못된 레코드 찾기
    SELECT a.animal_id, a.name 
    from animal_ins as a 
    join animal_outs as b on a.animal_id = b.animal_id
    where a.datetime > b.datetime
    order by a.datetime
    ```

    [코딩테스트 연습 - 오랜 기간 보호한 동물(1)](https://programmers.co.kr/learn/courses/30/lessons/59044)

    ```sql
    # 가장 오래된 동물 3마리 구하기
    SELECT name, datetime from animal_ins
    # 다른 테이블에 없는 동물 찾기
    where animal_id not in (select animal_id from animal_outs)
    order by datetime
    limit 3
    ```

    [코딩테스트 연습 - 보호소에서 중성화한 동물](https://programmers.co.kr/learn/courses/30/lessons/59045)

    ```sql
    # 보호소에서 중성화한 동물 찾기
    SELECT animal_id, animal_type, name
    from animal_ins
    where sex_upon_intake not in ('Spayed Female', 'Neutered Male') 
    and animal_id in (
        select animal_id from animal_outs 
        where sex_upon_outcome 
        in ('Spayed Female', 'Neutered Male')
    )
    ```

6. String, Date

    [코딩테스트 연습 - 이름에 el이 들어가는 동물 찾기](https://programmers.co.kr/learn/courses/30/lessons/59047)

    ```sql
    # 이름에 'el'이 들어가는 개 찾기
    SELECT animal_id, name
    from animal_ins
    where animal_type = 'Dog'
    and name like '%el%'  # like 함수로 검색 가능, %는 제한없이, -는 1글자를 의미
    order by name
    ```

    [코딩테스트 연습 - 중성화 여부 파악하기](https://programmers.co.kr/learn/courses/30/lessons/59409)

    ```sql
    # Neutered' 또는 'Spayed'라는 단어가 들어가면 O 아니면 X
    SELECT animal_id, name, 
    # mysql에서는
    # if(조건, true결과, false결과)문을 사용한다
    # regexp는 조건에 존재하는지 확인한다
    if(sex_upon_intake regexp 'Neutered|Spayed', 'O', 'X') 
    from animal_ins
    ```

    [코딩테스트 연습 - 오랜 기간 보호한 동물(2)](https://programmers.co.kr/learn/courses/30/lessons/59411)

    ```sql
    # 오래 보호한 2마리
    SELECT a.animal_id, a.name
    from animal_outs as a join animal_ins as b
    on a.animal_id = b.animal_id
    order by datediff(a.datetime, b.datetime) desc # datediff 함수
    limit 2
    ```

    [코딩테스트 연습 - DATETIME에서 DATE로 형 변환](https://programmers.co.kr/learn/courses/30/lessons/59414)

    ```sql
    # 출력 지정
    SELECT animal_id, name, 
    date_format(datetime, "%Y-%m-%d") dateonly # date_format 함수⭐
    from animal_ins
    order by animal_id
    ```
