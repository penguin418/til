# Annotation

annotation은 jdk5부터 제공되는 기능으로 소스코드에 meta 정보를 제공하기 위해 사용됩니다

알려진 예

- @Repeatable
- @Deprecated
- @Override
- @SupressWarning

compile, compile-time, runtime에 사용될 수 있습니다

- compiler에게 정보를 제공하는 경우

    @Deprecated, @Override, @SuppressWarning 의 3가지 `annotation`이 있습니다

- compile-time에 사용되는 경우

    빌드툴, 코드 제네레이터, xml 등을 위해 컴파일시 필요한 정보를 제공할 수 있습니다

- runtime에 사용되는 경우

    java reflection에게 정보를 제공할 수 있습니다

annotation 작성

- @interface는 이 인터페이스가 `annotation` 라는 뜻입니다

    ```java
    @interface Annotation{
    	int property();
    }
    ```

annotation을 작성할 때 다음의 annotation을 추가로 넣을 수 있습니다

- @Documented는 이 annotation 이 사용된 곳의 JavaDoc에 이 annotation 정보를 넣습니다
- @Target은 이 annotation이 적용될 수 있는 대상을 정합니다

    대상에는 다음이 들어갈 수 있습니다

    ElementType.METHOD
    ElementType.PACKAGE
    ElementType.PARAMETER
    ElementType.TYPE
    ElementType.ANNOTATION_TYPE
    ElementType.CONSTRUCTOR
    ElementType.LOCAL_VARIABLE
    ElementType.FIELD

- @Inherited은 이 annotation이 상속될 수 있다고 지시합니다

    ```java
    @Inherited
    public @interface InheritableAnnotation{ ... }

    @InheritableAnnotation
    public class Parent{ ... }

    public class Child extends Parent { ... }
    ```

- @Retention은 이 annotation의 범위를 지정합니다

    각 범위를 사용했을 때 이 annotation의 라이프사이클에 대해 설명합니다

    - RetentionPolicy.SOURCE

        이 annotation은 소스코드 상에만 존재할 것입니다

    - RetentionPolicy.CLASS

        이 annotation은 .class에도 존재할 것입니다. 하지만 runtime에는 존재하지 않습니다

    - RetentionPolicy.RUNTIME

        이 annotation은 runtime에도 존재할 것 입니다. java reflection에 의해 접근될 수 있습니다

annotation의 활용

- Source범위에서 사용시

    annotation의 사전적 의미는 주석으로 단독으로 사용될 경우 주석의 역할을 수행합니다.

- Class범위에서 사용시

    annotation-processor에 의해 처리되며 동적으로 소스코드를 생성하는데 사용될 수 있습니다

- Runtime에서 사용 시

    Reflection에 의해 처리될 수 있으며, 값, 타입 검증에 유용하게 사용될 수 있습니다

    캡슐화 저해 문제가 있고, 약간의 오버헤드가 있습니다

출처: [Geeksforgeeks.org](https://www.geeksforgeeks.org/annotations-in-java/)

## Annotation Processor

일종의 Java Compiler 플러그인으로서 AbstractProcessor를 상속받아 작성된다

- compile 이후, compile-time에 실행되며 자체적인 JVM에서 구동된다

코드를 검사하고, annotation을 스캔하여 필요한 경우, 수정하는데 사용다

- 코드를 검사하고 컴파일 후 메시지를 출력할 수 있다
- 새로운 소스코드를 생성할 수 있다
- 소스코드를 수정할 수 있다

    특정 동작 이전, 이후 등에 코드를 삽입하는 작업은 AOP를 사용하는 것이 더 쉽고 간편한다

구현하는 방법

1. annotation-processor는 AbstractProcessor를 상속받아야 한다

    AbstractProcessor 상속 받는 방법

    추상메소드, 메소드 구현

    - boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv)

        인자에 대해:

        annotations: 당연하게도 .. 제공된다

        roundEnv: 주변 환경 정보. processingOver() 메소드는 생성 된 유형이 다음 라운드의 주석 처리에 적용되지 않으면 true를 반환한다.

        리턴값에 대해:

        annotation processor의 처리는 여러 round로 이뤄진다. 

        이전 round에서 처리되지 못한 작업은 다음 round 때 다시 확인 후 처리되도록 해야 한다

        만약 true를 리턴하면 다음 라운드 때 실행되지 않는다

        ```java
        @Override
        public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnv) {
            // rootElement를 재귀적으로 탐색하거나 
            // 여러개의 Annotation을 지원하는 경우 (가령, supported annotation types에 add("*")한 경우 라던지...
            // 추가적인 처리가 필요합니다
            if (!roundEnv.processingOver()) {
                processAnnotations(roundEnv);
            }
            return true;
        }
        ```

    - synchronized void init(ProcessingEnvironment env)

        기본 생성자와 별개로 실행되며 ProcessingEnvironment라는 유용한 파라미터를 제공한다

        ProcessingEnvironment는 Element, Type 등의 util class를 제공하며 여기서 프로세싱에 필요한 정보들을 가져온다

    - Set<String> getSupportedAnnotationTypes()

        annotation processor를 등록할 때 호출되는 메소드디.

        프로세서가 어떤 Annotation을 처리하는 지 제공해야 한다

        ```java
        @Override
        public Set<String> getSupportedAnnotationTypes() {
                return new HashSet<String>(){
                    {
                        add(원하는어노테이션.class.getCanonicalName());
                    }
                };
            }
        ```

    - SourceVersion getSupportedSourceVersion()

        annotation processor를 등록할 때 호출된다

        지원하는 Annotation을 반환하면 된다

        ```java
        @Override
        public SourceVersion getSupportedSourceVersion() {
            return SourceVersion.latestSupported();
        }
        ```

2. annotation processor로 등록해야 한다

    이론적인 방법

    - resources/META-INF/services/javax.annotation.processing.Processor 파일을 만든다
    - 파일 안에 `나의 processor 클래스`의 cannonical name을 넣어준다

    쉬운 방법 (클래스 이동이나 이름 변경이 덜 번거로워짐)

    - google의 auto service를 dependency에 추가한다

        ```
        annotationProcessor 'com.google.auto.service:auto-service:1.0-rc5'
        implementation 'com.google.auto.service:auto-service:1.0-rc5'
        ```

    - 클래스 상단에 @AutoService(Processor.class)를 추가한다

참고: [Geeksforgeeks.org](https://www.javacodegeeks.com/2015/09/java-annotation-processors.html)