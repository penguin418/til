### 요약
JVM 외부 코드 실행을 도와주는 Foreign Function과 JVM 외부 메모리에 접근 가능한 Memory API 추가될 예정

### 짧은 설명
JNI 말고 안전하고 쉽게 외부 의존성을 사용할 수 있는 Foreign Function 추가예정

### 긴 설명
시작에 앞서
* 프리뷰 기능이라서 `--add-modules jdk.incubator.foreign` 컴파일 옵션 추가 필요

기존 기술의 한계
* JNI는 외부 코드를 실행할 수 있게 도와주지만, JIT 컴파일러의 도움을 못 받으므로 최적화되지 않음. 
  또한 c, c++는 자바 객체를 못읽고 자바도 c, c++의 구조체를 못읽어서 변환과정에 많은 시간을 쓰거나, 구조체로 적고 주소로 참조하는 위험한 방식을 사용했었음.
* ByteBuffer API로 OffHeap 영역을 접근할 수는 있었으나 비효율적인 방식이었음.
* Unsafe API로 OffHeap 영역을 접근할 수는 있었으나 안전하지 않은 방식이었음.

FFM(Foreign Function & Memory) API 
* JNI를 대체하는 새로운 외부 함수 사용방법:
  * 새롭게 추가된 jextract 도구가 CLinker 를 통해 구현된 java 인터페이스를 만들어 줌.  
  * 내부 변수에 접근하는 등 위험한 시도가 이뤄지면 IllegalAccessException 발생
* ByteBuffer, Unsafe API를 대체하는 더 안전한 off heap 메모리 사용방법:
  * 메모리를 확실하게 할당하고 해제 가능.

CLinker
* Java 와 네이티브 코드를 이어주는 인터페이스.
* JNI와 다르게 호출 규칙이 Java로 구현됨
* downcall(java -> foreign function)과 upcall(foreign function->java) 모두 지원
  * upcall 시 c용 인터페이스를 생성하기 위해 사용
* 구조체 타입을 파라미터로 사용가능
* java 내부를 보호하며, 위험한 호출 시에는 IllegalAccessException 발생

jextract 도구
* CLI 도구로, 현재는 프리뷰 버전(2023.01.15)
* c 헤더 파일(*.h)에서 CLinker 기반의 Java API 파일을 생성해주며, 자바 파일인것 처럼 접근해서 사용 가능함.
* 예제: [jextract 도구로 java 에서 Tensorflow 사용하기](https://hg.openjdk.java.net/panama/dev/raw-file/4810a7de75cb/doc/panama_foreign.html#using-tensorflow-c-api-mac-os)

MemorySegment
* off heap 메모리 영역을 모델링 하는 추상화로, 쓰레드에 종속됨
* 크기 또는 자바 타입을 기준으로 메모리 할당 가능
  ``` java
  // 0(base)부터 99바이트까지의 메모리를 할당
  MemorySegment segment = MemorySegment.allocateNative(100, newImplicitScope());
  // 0(base)부터 99*long 만큼 메모리를 할당
  MemorySegment segment2 = MemorySegment.ofArray(new long[100]);
  ```
MemoryHandle
* 메모리를 접근할 때 사용
  ``` java
  VarHandle intHandle = MemoryHandle.varHandle(int.class, ByteOrder.nativeOrder());
  // segment1에 1로 초기화
  for(int i=0; i<25; i++)
      intHandle.set(segment, i*4, 1);
  ```
MemoryAccess
* 메모리를 더 빠르게 접근하는 방법
  ``` java
  // segment1을 2로 초기화
  for(int i=0; i<25; i++)
      MemoryAccess.setIntAtOffset(segment1, i*4, 2);
  ```
MemoryAddress
* 주소값
  ``` java
  MemoryAddress address = segment1.baseAddress();
  ```
MemoryLayout
* `+i*4` 등의 offset 계산을 예쁘게 하는 방법
  ``` java
  // 메모리 구성
  ValueLayout longMemoryLayout = MemoryLayout.valueLayout(32, ByteOrder.nativeOrder());
  SequenceLayout intArrayLayout = MemoryLayout.sequenceLayout(25, longMemoryLayout);
  // 메모리 할당
  MemorySegment segment = MemorySegment.allocateNative(intArrayLayout, newImplicitScope());
  ```
* 인덱스만 가지고 접근 가능
  ``` java
  VarHandle indexedElementHandle = intArrayLayout.varHandle(int.class, PathElement.sequenceElement());
  for (int i = 0; i < intArrayLayout.elementCount().getAsLong(); i++) {
      indexedElementHandle.set(segment, (long) i, i);
  }
  ```
리소스 스코프
* off-heap 메모리는 기본적으로 쓰레드 종속임.
* confined 와 shared 스코프가 있으며
* 수동 메모리 해제를 원하는 경우 다음 방법처럼 confined 리소스 스코프를 선언하면 됨
  ``` java
  // 리소스 스코프를 선언함. 스코프를 떠날 때 강제로 메모리 해제
  try (ResourceScope scope = ResourceScope.newConfinedScope()) {
      MemorySegment s1 = MemorySegment.map(Path.of("someFile"), 0, 100000, MapMode.READ_WRITE, scope);
      MemorySegment s2 = MemorySegment.allocateNative(100, scope);
      ...
  } // both segments released here
  ```
* 다른 쓰레드와 메모리를 공유하고 싶다면 shared 리소스 스코프를 선언해야 함.
  * 이때는 close 메서드 또는 더이상 쓰이지 않을 때 가비지 컬렉터의 일정을 따라 메모리가 해제됨.

참고
* https://openjdk.org/jeps/412
* https://developer.okta.com/blog/2022/04/08/state-of-ffi-java