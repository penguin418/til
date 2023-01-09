### 요약
java2D 관련 macOS 렌더링 파이프라인이 추가됨

### 짧은 설명
macOS 10.14 부터 swing 쓸 거면 java17 및 `-Dsun.java2d.metal=true` 옵션 사용

### 긴 설명
* macOS 10.14 부터 opengl 을 deprecated 시켰고, Metal API를 제공하였지만 아직은 openGL 제공중.
* Java 17에선 코드 인터페이스는 바뀌는 부분없이 Metal API를 사용할 수 있는 렌더링 파이프라인이 추가됨.
* 실행 시 다음 옵션 필요
  ``` bash
  -Dsun.java2d.metal=true
  ```
* Java 19부터는 macOS에서 metal이 기본 파이프라인임. openGL을 쓰려면 다음 옵션 추가 필요함 
  ``` bash
  -Dsun.java2d.opengl=true
  ```
  
### 출처
* https://openjdk.org/jeps/382