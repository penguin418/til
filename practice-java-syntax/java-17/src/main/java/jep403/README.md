### 요약
`--illegal-access`를 더이상 허용하지 않음
### 짧은 설명
java 9부터 JDK 내부에 접근하지 않도록 경고했으나 이번부터 막음
### 긴 설명
* sun.*, jdk.*, org.* 등은 내부적으로 표준없이 구현되었으며 지속적인 지원에 대한 보증이 없었으나, 외부에서의 사용으로 관리 부담이 커짐.
* java 9부터 JDK 내부에 reflection 등으로 접근하지 않도록 경고했음.
* 이제 `--illegal-access=warn` 옵션 써도 실행 못함.
* jdk 내부 변수에 reflection 등으로 접근 불가
  * sun.misc.Unsafe 같은 중요 패키지는 아직까지는 접근 허용
  * 사실 이것도 안쓰는 게 맞다.
    ``` text
    // 대신 사용가능한 패키지
    java.lang.invoke.VarHandle @since 9
    java.lang.invoke.MethodHandles.Lookup::defineClass @since 9
    java.lang.invoke.MethodHandles.Lookup::defineHiddenClass @since 15
    java.lang.invoke.MethodHandles.Lookup::ensureInitialized @since 15
    ```

com.sun API 

com.sun.* 패키지는 내부적 용도이나, 일부 기능이 외부에서 사용 할 수 있게 제공되고 있었음.
해당 기능들은 JDK 9에서 외부로 지원되었는데, 이들에 대한 지원은 계속됨.
* jdk.compiler
* jdk.httpserver
* jdk.sctp
* com.sun.nio.file
### 출처
* http://web.archive.org/web/19980215011039/http://java.sun.com/products/jdk/faq/faq-sun-packages.html
* https://wiki.openjdk.org/display/JDK8/Java+Dependency+Analysis+Tool#JavaDependencyAnalysisTool-ReplaceusesoftheJDK'sinternalAPIs