### 요약
extend와 implement를 막는 sealed 클래스 등장!

### 짧은 설명
클래스 또는 인터페이스의 개발자는 어떤 코드가 해당 클래스를 구현, 상속할 수 있는 지 <b>선언</b>할 수 있음.

### 긴 설명
상속
* 상속구조는 IS-A 관계를 만족해야 하지만, 지금까지는 상속 받는 클래스를 지정할 수 없었음.
* sealed 키워드는 개발자가 상속 관계를 허용할 클래스를 정의할 수 있음.

기존의 한계
* final 키워드는 아예 상속을 막아버렸음.
* package 한정자는 상속 받는 클래스를 같은 패키지 내의 클래스로만 제한할 수 있었음.  

추가된 문법
``` java
sealed class SealedClassA permits PermittedClassA, PermittedClassB {
}

non-sealed class PermittedClassA extends SealedClassA {
}

final class PermittedClassB extends SealedClassA {
}


```
* sealed 키워드
  * 상속 대상을 한정함
  * 클래스, 추상 클래스, 인터페이스 모두에 사용 가능.
  * 반드시 상속할 수 있는 클래스를 가르키는 permitted 키워드와 함께 쓰여야 함
* non-sealed 키워드
  * 상속 대상을 한정하지 않음

상속을 구현하는 클래스
* sealed, non-sealed 또는, final 클래스여야 함
* 같은 모듈 또는 같은 패키지에 속해야 함

활용
* 본래의 목적, 상속 받는 클래스의 제한
* 패턴 매칭 시 도움
  * 부모 클래스를 상속받는 클래스가 이미 정해져 있으므로, if-else 에서 마지막 else 가 가리키는 대상이 확정됨.
