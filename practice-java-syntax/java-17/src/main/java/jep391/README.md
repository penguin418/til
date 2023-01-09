### 요약
macOS에서 Rosetta 2 안써도 되도록 포팅
### 짧은 설명
성능 개선됨
### 긴 설명
* 이미 기존에 AArch64 시스템 기반 리눅스, 윈도우에서 jdk를 실행할 수 있도록 포팅 완료함(jep 388).
* Java 1.2부터 도입된 JIT 컴파일러는 자바 코드를 수행하면서 최적화하는 과정이 들어있는데, AArch64 기반 macOS에서는 메모리 세그먼트에서 실행 가능하면서 쓰기 가능한 영역을 허용하지 않아서 최적화를 할 수 없게 됨. 
* 그래서 Java 17는 해당 기능(write-xor-execute)을 구현함.
### 출처
* https://openjdk.org/jeps/391
* https://en.wikipedia.org/wiki/W%5EX