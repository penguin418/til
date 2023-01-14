### 요약
x64, AArch64에 최적화된 빠른 vector 연산을 지원하는 api 추가예정

### 짧은 설명
Vector 연산을 지원하는 api 추가예정

### 긴 설명
기존에도 Vector 연산을 지원하기 위한 자동 벡터화 알고리즘이 있었으나, 개발자가 코드를 작성하는 방식에 따라서
컴파일러에게 벡터 연산으로 인식되지 않아 최적화되지 않는 경우가 많았음.

개발 당시에 Vector 연산의 의도를 더 쉽게 담을 수 있도록, Vector<E> 타입의 클래스가 도입됨.
* 타입으로는 Byte, Short, Integer, Long, Float 및 Double 를 지원
* 모양으로는 64, 128, 256, 512, 또는 플랫폼에서 지원하는 최대 비트의 길이를 지원함
* 타입과 모양은 조합되어 VectorSpecies<E> 타입의 클래스로 표현됨
  ``` java
    static final VectorSpecies<Byte> ByteVector.SPECIES_128
  ```
  * 각각의 조합은 이런 식으로 상수로 제공됨

아래 두 코드는 같은 의미임.
``` java
void scalarComputation(float[] a, float[] b, float[] c) {
   for (int i = 0; i < a.length; i++) {
        c[i] = (a[i] * a[i] + b[i] * b[i]) * -1.0f;
   }
}
```

``` java
static final VectorSpecies<Float> SPECIES = FloatVector.SPECIES_PREFERRED;

void vectorComputation(float[] a, float[] b, float[] c) {
    int i = 0;
    int upperBound = SPECIES.loopBound(a.length);
    for (; i < upperBound; i += SPECIES.length()) {
        // FloatVector va, vb, vc;
        var va = FloatVector.fromArray(SPECIES, a, i);
        var vb = FloatVector.fromArray(SPECIES, b, i);
        var vc = va.mul(va)
                   .add(vb.mul(vb))
                   .neg();
        vc.intoArray(c, i);
    }
    for (; i < a.length; i++) {
        c[i] = (a[i] * a[i] + b[i] * b[i]) * -1.0f;
    }
}
```