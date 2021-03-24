# 디자인패턴 연습

콘텍스트, 문제, 해결 방법을 다룹니다

참고: GoF의 디자인 패턴, Effective Java

생성, 구조, 행위 패턴이 있습니다

* 생성 패턴

  객체 생성/조합을 캡슐화합니다

  객체의 생성은 결합도를 강하게 만들며, 강한 결합도는 유지보수를 어렵게 만듧니다

* 구조 패턴

  클래스나 객체를 조합해 더 큰 구조를 만드는 패턴입니다

* 행위 패턴

  객체나 클래스 사이의 알고리즘이나 책임 분배에 관한 패턴입니다

## Singleton (생성 패턴, GoF)
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

## Thread Safe Singleton (생성 패턴, GoF)
쓰레드 환경에서 사용하는 싱글턴 패턴
* 객체 생성 시 instance == null을 검사하는 과정에서 두가지 객체를 생성할 수 있는 상황을 피한다
``` java
...
    public synchronized static Singleton getInstance(){
      ...
```

## Builder (생성 패턴)
* 목적
  1. 생성하는 매커니즘(Builder)과 표현(내용) 주체(Directory)을 분리한다
  2. 동일 절차에서 다른 표현결과를 만들 수 있다
* 동기
  
  내가 만든 편집기가 여러 출력 포맷(.txt, .rtf등)을 지원하면서, 다른 포맷도 추가할 수 있어야 한다
* 유용성
  
  빌더 패턴만 구현하면 새로운 포맷을 지원할 때, concreteBuilder만 새로 구현해 주면 된다

* 사례

  포맷 변환기
  
## Builder (Effective Java)
객체를 유연하게 생성하는 방법

(Lombok 등의 라이브러리를 사용하면 쉽게 구현할 수 있습니다)
* 객체를 한번에 생성해서 객체 일관성 유지가 가능합니다
* setter가 필요없으므로 immutable 클래스를 만들 수 있습니다
* 빌더에 검증 로직을 넣어도 됩니다
  ``` java
  public Coffee{
    ...
    public Coffee(Integer shot, Integer water, Integer syrup, Boolean decaf){
      this.shot = shot;
      this.water = water;
      this.syrup = syrup;
      this.decaf = decaf;
    }
    ...
    public static class Builder{
      private Integer shot = 1;
      private Integer water = 7;
      private Integer syrup = 0;
      private Boolean decaf = false;
      ...
      public Builder decaf(Boolean decaf) {
        this.decaf = decaf;
        return this;
      }
      
      public Coffee build(){
        return new Coffee(shot, water, syrup, decaf);
      }
    }
  }
  ```
  ``` java
    public static void main(String[] args){
      Coffee espresso = new Coffee.Builder()
                                 .shot(1).build();
      Coffee americano = new Coffee.Builder()
                                 .shot(1).water(7).build();
      Assertions.assertNotEquals(espresso, americano);
    }
  ```

## 팩토리 메소드

* 목적

  팩토리 클래스 객체에 분기에 따라 객체를 다르게 생성하는 역할을 위임합니다
* 동기
  
  분기에 따라 객체를 다르게 생성해야 하는 작업이 있습니다

  이 작업을 여러번 반복하는 경우, 생성하는 작업을 다른 객체에 위임하여 결합도를 낮춥니다.

* 추가적으로 Factory 메소드는 각 객체를 생성하는 작업으로 인해 클래스가 큰 경우가 많습니다.
  
  이때, Singleton 을 사용하면 객체 생성 시간을 절약할 수 있습니다 
  ``` java
  public class Test {
      static class Window1 {
          private final List<Shape> shapes = new ArrayList<>();
  
          public Window1(List<ShapeType> shapeTypes){
              for (ShapeType shapeType : shapeTypes){
                  Shape shape = ShapeFactory
                                  .getInstance()
                                  .getShape(shapeType);
                  this.shapes.add(shape);
              }
          }
          ...
  ```