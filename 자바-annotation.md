# Annotation

annotation은 jdk5부터 제공되는 기능으로 소스코드에 meta 정보를 제공하기 위해 사용됩니다

알려진 예

- @Override
- @Deprecated
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