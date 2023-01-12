** preview 기능

### 요약
switch문 개선함

### 짧은 설명
switch문의 case가 상수 외에도 패턴들을 허용하도록 수정함
가드 패턴과 중괄호 패턴이 추가됨

### 긴 설명
패턴 switch 문이 추가되었음. 일반 switch 문에 패턴 사용가능. 패턴이 추가된 case문은 동등성(=) 검사가 아니라, 패턴으로 검사함.
``` java
public static String getType(Object o) {
    return switch (o) {
        case Integer i -> "integer";
        // 2가지 한번에 검사 가능
        case String s && s.length() > 0 -> "string";
        // 클래스 또는 array 만 사용 가능
        // case char c -> "char";
        case char[] c -> "char[]";
        case Character c -> "character";
        // null도 됨!
        case null -> "null";
        // default 타입 반드시 필요함 !
        default -> throw new IllegalStateException("Unexpected value: " + o);
    };
}
```
이로 인해 4가지 디자인 특징이 생김
* Enhanced type checking
  
  타입 체크가 강화되어, 컴파일 중에 case 문에 중복된 타입이 없는지, 우세한 패턴이 먼저 작성(Dominance of pattern labels)되지 않았는지 검사함.
* Completeness of switch expressions and statements

  패턴은 모든 케이스에 대응해야 함. (default 가 반드시 있어야 함)
* Scope of pattern variable declarations

  패턴에서 선언된 변수의 범위는 해당 case 를 벗어날 수 없음
* Dealing with null

  null 을 처리할 수 있게 됨. && 처리해야 함

``` java
public static String getType2(Object o) {
    return switch (o) {
        // case 1:
        // [Dominance of pattern labels] 패턴 우세성도 검사됨.
        // 앞선 레이블인 Integer가 뒤 레이블인 Integer i && i에 비해 우세하면 컴파일 에러 발생
        // case Integer i -> "integer";

        case Integer i && i > 10 -> "integer, > 10";
        case Integer i -> "integer";
        // [Completeness of pattern labels in switch expressions and statements] 
        // 패턴은 모든 케이스에 대응해야 함. 그렇지 않으면 컴파일 에러 발생
        default -> throw new IllegalStateException("Unexpected value: " + o);
    };
}
```

``` java
public static void getType3(Object o) {
    switch (o) {
        // [Matching null] 드디어 switch에서 null 처리를 지원! 아래 줄은 있으나 없으나 같은 의미.
        // case null: throw new NullPointerException();
        case Character c:
            System.out.println("str: " + c);
        // [Scope of pattern variable declarations] break 없이 패턴 스위치를 이어나갈 수 없음
        // case Integer i:
        //    ...

        default:
            throw new IllegalStateException("Unexpected value: " + o);
    }
}
```