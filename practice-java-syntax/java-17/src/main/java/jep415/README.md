### 요약
컨텍스트 별 역직렬화 필터 추가됨


### 짧은 설명


### 긴 설명
역직렬화는 유명한 취약점 중 하나로, Remote Command Execution 공격의 대상. 2017년부터 OWASP 10에서 내려오지 않는 중. (현시점 2023년)

공격은 역직렬화가 완료되기 전에 끝나므로, 역직렬화가 아예 되지 않도록 필터해주는 것이 매우 중요.

역직렬화 필터
* JEP 290에서 추가된 직렬화 필터 
  ``` bash
  -Djdk.serialFilter=<허용할 패키지>;!<허용하지 않을 패키지> 
  ```
  ``` java
  ObjectInputFilter filter = ObjectInputFilter.Config.createFilter("com.github.penguin418.*;!*");
  ObjectInputFilter.Config.setSerialFilter(filter);
  ```
* JEP415
  JEP415는 JEP290의 직렬화 필터를 좀 더 발전 시켜서 복잡한 필터를 만들 수 있음.
  `SerialFilterFactory`를 사용할 수 있음. 
  ``` java
  ObjectInputFilter.Config.setSerialFilterFactory(
           // BinaryOperator<ObjectInputFilter>를 직접 구현하면 더 좋은 필터를 만들 수 있음.
           (f1, f2) -> ObjectInputFilter.merge(f2,f1)
  );
  ```

필터 문법
* 나열: 각 대상은 ;로 구분
  ` package1.*;package2.* `
* 거부: 거부할 패키지는 !로 표시
  ` allowed.*;!rejected.* `
* 와일드카드
  * `*` : 모두
  * `package1.*` : package1 하위의 전체 클래스
  * `package1.**`: package1 하위의 전체 클래스와 패키지
  * `class1*`  : class1로 시작하는 클래스
* 리소스 제한
  * `maxarray=1` : 배열의 최대 길이 1
  * `maxdepth=1` : 그래프의 최대 깊이 1
  * `maxrefs=1`  : 객체간 참조가 최대 1
  * `maxbytes=1` : 스트림의 크기가 최대 1