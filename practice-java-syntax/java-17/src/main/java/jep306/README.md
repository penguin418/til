### 요약
strictfp 키워드 사라짐.

### 짧은 설명
이제부터 부동 소수점 연산에 언제나 IEEE-754 규칙이 적용됨.

### 긴 설명
strictfp 가 없어진 이야기

* 처음 Java가 개발되었을 때는 모든 부동 소수점 연산이 표준(IEEE-754) 처리되었지만, 
일부 CPU에는 이 기능이 큰 부담이 갔음.
* 그래서 Java 1.2부터는 추가된 strictfp 키워드는 엄격한 float 연산을 활성화하였고,
strictfp 키워드를 사용하지 않는 경우엔 부동 소수점 연산이 각 플랫폼에 맞게 최적화되어 처리되었음.
  
* 이제 시간이 흘러, 모든 CPU가 IEEE-754 를 무리없이 지원가능하므로, 
strictfp 키워드를 삭제하고, 부동 소수점 연산이 항상 표준에 맞게 처리되도록 수정됨.

strictfp 회고

* strictfp를 사용한 코드는 컴파일러 최적화가 적용되지 않음
  ``` java
  strictfp double strictMultiply(double a, double b){
  return a * b;
  }
  ```
* strictfp를 사용하지 않으면 다른 언어에서 계산한 값이랑 결과가 달랐음.