# 디자인패턴 연습

콘텍스트, 문제, 해결 방법을 다룹니다

참고: GoF의 디자인 패턴

생성, 구조, 행위 패턴이 있습니다

* 생성 패턴

  객체 생성/조합을 캡슐화합니다

* 구조 패턴

  클래스나 객체를 조합해 더 큰 구조를 만드는 패턴입니다

* 행위 패턴

  객체나 클래스 사이의 알고리즘이나 책임 분배에 관한 패턴입니다

## Singleton (생성 패턴)
하나의 인스턴스만 생성하는 패턴 

* 객체가 하나만 생성되도록 하여 전역변수 없이 어디서나 사용할 수 있게 합니다
* 하나의 객체를 공유하거나, 생성 비용이 비싸 공유가 유용한 경우 유용합니다
    ``` java
    public class Singleton {
        private static Singleton instance;
    
        private Singleton(){ }
    
        public static Singleton getInstance(){
            if (instance == null)
                instance = new Singleton();
            return instance;
        }
    }
    ```
    * 생성자는 하나의 인스턴스만 생성하는 책임을 지닙니다

    * getInstance()는 항상 동일한 인스턴스를 반환하는 작업을 수행합니다

* 정적 클래스와의 차이점:

  객체 생성과정이 존재합니다

  인터페이스를 구현할 수 있습니다

* 쓰레드를 사용하는 경우
``` java
...
    public synchronized static Singleton getInstance(){
        ...
```