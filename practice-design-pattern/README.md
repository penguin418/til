# 디자인패턴 연습

콘텍스트, 문제, 해결 방법을 다룹니다

참고: GoF의 디자인 패턴, Effective Java

생성, 구조, 행위 패턴이 있습니다

- 생성 패턴

  객체 생성/조합을 캡슐화합니다

  객체의 생성은 결합도를 강하게 만들며, 강한 결합도는 유지보수를 어렵게 만듧니다

- 구조 패턴

  클래스나 객체를 조합해 더 큰 구조를 만드는 패턴입니다

- 행위 패턴

  객체나 클래스 사이의 알고리즘이나 책임 분배에 관한 패턴입니다

## Singleton (생성 패턴, GoF)

하나의 인스턴스만 생성하는 패턴

- 객체가 하나만 생성되도록 하여 전역변수 없이 어디서나 사용할 수 있게 합니다
- 하나의 객체를 공유하거나, 생성 비용이 비싸 공유가 유용한 경우 유용합니다

    ```
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

  - 생성자는 하나의 인스턴스만 생성하는 책임을 지닙니다
  - getInstance()는 항상 동일한 인스턴스를 반환하는 작업을 수행합니다
- 정적 클래스와의 차이점:

  객체 생성과정이 존재합니다

  인터페이스를 구현할 수 있습니다

## Thread Safe Singleton (생성 패턴, GoF)

쓰레드 환경에서 사용하는 싱글턴 패턴

- 객체 생성 시 instance == null을 검사하는 과정에서 두가지 객체를 생성할 수 있는 상황을 피한다

    ```
    ...
      public synchronized static Singleton getInstance(){
        ...

    ```

## Builder (생성 패턴)

- 목적
  1. 생성하는 매커니즘(Builder)과 표현(내용) 주체(Directory)을 분리한다
  2. 동일 절차에서 다른 표현결과를 만들 수 있다
- 동기

  내가 만든 편집기가 여러 출력 포맷(.txt, .rtf등)을 지원하면서, 다른 포맷도 추가할 수 있어야 한다

- 유용성

  빌더 패턴만 구현하면 새로운 포맷을 지원할 때, concreteBuilder만 새로 구현해 주면 된다

- 사례

  포맷 변환기

## Builder (Effective Java)

객체를 유연하게 생성하는 방법

(Lombok 등의 라이브러리를 사용하면 쉽게 구현할 수 있습니다)

- 객체를 한번에 생성해서 객체 일관성 유지가 가능합니다
- setter가 필요없으므로 immutable 클래스를 만들 수 있습니다
- 빌더에 검증 로직을 넣어도 됩니다

    ```
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

    ```
      public static void main(String[] args){
        Coffee espresso = new Coffee.Builder()
                                   .shot(1).build();
        Coffee americano = new Coffee.Builder()
                                   .shot(1).water(7).build();
        Assertions.assertNotEquals(espresso, americano);
      }

    ```

## 팩토리 메소드

- 목적

  팩토리 클래스 객체에 분기에 따라 객체를 다르게 생성하는 역할을 위임합니다

- 동기

  분기에 따라 객체를 다르게 생성해야 하는 작업이 있습니다

  이 작업을 여러번 반복하는 경우, 생성하는 작업을 다른 객체에 위임하여 결합도를 낮춥니다.

- 추가적으로 Factory 메소드는 각 객체를 생성하는 작업으로 인해 클래스가 큰 경우가 많습니다.

  이때, Singleton 을 사용하면 객체 생성 시간을 절약할 수 있습니다

    ```
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

## 프로토타입

- 목적

  초기화 동작 없이 객체를 빠르게 생성하고 싶을 때 사용합니다

- 동기

  각 클래스의 동작이 거의 차이가 없고, 속성만 차이난다면 서로 다른 클래스보다 같은 클래스에서 값만 다르게 주는 방법이 좋습니다.

  반복해서 생성해야 하는 경우, 그때 그때 생성하는 것보다 미리 생성해두고 복사하는 것이 좋은 경우가 있습니다.

  서브 클래스 횟수를 줄이기 위해 추상 팩토리 패턴 대신 주로 사용됩니다

  DB 처럼 생성에 오랜 시간이 걸리는 경우, 반복 생성 대신 복사해서 사용하기 위해 사용됩니다

- 자바에선 Clonable 인터페이스를 통해 프로토타입을 빠르게 구현할 수 있도록 지원합니다

  Primitive 타입만 사용된 경우, Clone 메소드에서 super.clone()을 수행하는 것만으로 복사 메소드를 만들 수 있습니다.

# TemplateMethod 패턴 (행위 패턴)

작업의 일부분을 서브 클래스로 캡슐화해서 서브 클래스에서 구현합니다. 특정 단계의 작업을 바꿀 수 있습니다

- 전체적 알고리즘은 상위 클래스에서 구현되며, 일부분만 하위 클래스에서 구현됩니다 `프레임워크`에서 주로 사용되는 설계 기법입니다

구성

- abstract class : 템플릿 메서드를 정의

  템플릿 메서드는 프로그램의 전체 흐름을 정의한 메서드로, 보통은 final 키워드로 오버라이드를 막습니다.

- concrete class : 실제 로직을 구현하는 클래스
- AbstractNewsParser (abstract class)

  프레임 워크의 전체적인 형상을 정의한다

    ```
    // abstract class
    public abstract class AbstractNewsParser {
    private String title;
    private String body;

        public abstract String findTitle(Document doc);

        public abstract String findBody(Document doc);

        public final void parse(String url) {
            try {
                Document doc = Jsoup.connect(url).get();
                this.title = findTitle(doc);
                this.body = findBody(doc);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        ...

    ```

- DaumNewParser (concrete class)

  실제 동작을 정의한다

    ```
    import org.jsoup.nodes.Document;

    public class DaumNewParser extends AbstractNewsParser {
        @Override
        public String findTitle(Document doc) {
            return doc.body()
                    .select("#head_view").text();
        }

        @Override
        public String findBody(Document doc) {
            return doc.body()
                    .select("#harmonyContainer").text();
        }
    }
    ...

    ```

# Command 패턴 (행위 패턴)

- 목적

  실행될 기능(메서드)을 객체로 캡슐화하여 여러 기능을 실행할 수 있는 클래스를 생성합니다

  실행될 기능 변경에도 호출자 클래스를 변경없이 사용하고 싶을 때 사용됩니다

### Command 패턴 1

객체화된 메서드를 사용하여 실제(추가) 구현이 완성되었을 때, 기존 클래스의 변경없이 기능 추가를 쉽게 할 수 있다

- GoF에서 다룬 command 패턴
- 실행할 메서드를 객체화해서 보관한다

    ```
    public class Invoker {

        public void execute(ICommand command) {
            command.execute();
        }
    }

    ```

- 실제 구현 계획이나, 추가 구성 이전의 쉬운 구현

    ```
    public class PlainHelloCommand implements ICommand{

        @Override
        public void execute() {
            // 복잡한 행동 1
            System.out.println("hello");
        }
    }

    ```

- 추가 기능 혹은 실제 구현

    ```
    public class HtmlPrinter {
        private String head;
        private String body;

        public HtmlPrinter init(){
            this.head = "";
            this.body = "";
            return this;
        }

        ...

        public HtmlPrinter div(String msg){
            return this.div(msg, null);
        }
        public HtmlPrinter div(String msg, HtmlPrinter htmlPrinter){
            this.body = "<div "+msg+">" + (htmlPrinter != null ? htmlPrinter.getBody() : "") +"</div>";
            return this;
        }
        public HtmlPrinter p(String msg){
            return this.p(msg, null);
        }

        ...

        public String getBody(){
            return this.body;
        }

        public String getFullHtml(){
            return "<html><head>"
                    + this.head
                    + "</head>"
                    + "<body>"
                    + this.body
                    + "</body>";
        }
    }

    ```

### Command 패턴 2

command 패턴을 사용한 undo 기능

- Headfirst 책에서 다룬 command 패턴

command 패턴은 기능을 객체화하였기 때문에 자료구조를 통해 목록을 관리할 수 있습니다. 메멘토 패턴은 과거의 특정 상태를 복구하는 기능인 반면, 반면 커맨드 패턴은 모든 과거 이력을 순서대로 기록하기 때문에, 데이터베이스와 관련된 작업에서 적합합니다.

- Command는 execute와 그것을 되돌리는 undo를 구현해야 합니다

    ```
    public interface ICommand {
        public void execute();
        public void undo();
    }

    ```

- CommandManager는 실행과 동시에 상태를 기록합니다 ```java public class CommandManager {

    ```
    private Stack<ICommand> undos = new Stack<>();
    private Stack<ICommand> redos = new Stack<>();

    public void execute(ICommand command){
        command.execute();
        undos.add(command);
    }

    public void undo(){
        ICommand command = undos.pop();
        command.undo();
        redos.add(command);
    }
    ...

    ```

## Producer-Consumer 패턴

생산 소비 패턴

- 동시에 여러 프로세스를 빠르게 처리할 수 있다
-

### One Producer Many Consumer 패턴

어떤 사이트에서 시작해서 모든 링크를 누르고,  스택에 담아서, 탐색하며, 아무 링크도 없는 페이지에 도달하면 멈춘다. consumer들은 여기서 타이틀을 출력한다

- 하나의 생산자와 다수의 소비자는 빠른 생산과 느린 소비가 이뤄지는 경우이다
- 쓰기보다 읽기가 주로되는 큐가 이득이다
- concurrentLinkedQueue 는 CAS기반의 락없는 큐이므로 이것을 사용하는 것이 유리하다

    ```java
    // Main.java 의 main 메소드
    public static void main(String[] args) {
            Queue<Document> bq = new ConcurrentLinkedQueue<>();
            List<Generator> generatorList = Stream.of(new Generator(bq, "https://google.com"))
                    .collect(Collectors.toList());
            List<Consumer> consumerList = Stream.of(new Consumer(bq), new Consumer(bq), new Consumer(bq))
                    .collect(Collectors.toList());
            ExecutorService es1 = Executors.newFixedThreadPool(1);
            generatorList.forEach(es1::submit);
            ExecutorService es2 = Executors.newFixedThreadPool(3);
            consumerList.forEach(es2::submit);
        }
    ```

### Many Producer One Consumer 패턴

위 예제와 동일하지만, 생산자 수와 소비자 수가 다르다

- 하나의 소비자와 다수의 생산자는 생산이 느리게 이뤄지는 구조에서 사용된다
- 쓰기 시, thread-safe함이 매우 중요하다.
- mutax가 있는 blockingQueue를 사용하는 것이 바람직하다

  이때, url또한 나눠서 쓰므로, url을 위한 queue도 준비한다

    ```java
    public static void main(String[] args) {
            Queue<Document> bq = new LinkedBlockingDeque<>();
            Queue<String> urls = new LinkedBlockingDeque<>();
            urls.add("https://google.com");
            List<Generator> generatorList = Stream.of(new Generator(bq, urls),new Generator(bq, urls),new Generator(bq, urls),new Generator(bq, urls))
                    .collect(Collectors.toList());
            List<Consumer> consumerList = Stream.of(new Consumer(bq))
                    .collect(Collectors.toList());
            ExecutorService es1 = Executors.newFixedThreadPool(4);
            generatorList.forEach(es1::submit);
            ExecutorService es2 = Executors.newFixedThreadPool(1);
            consumerList.forEach(es2::submit);
        }
    ```

### strategy pattern

전략 패턴은 새로운 전략의 추가를 컨텍스트 코드 변경없이 가능

policy나 strategy용어 사용됨

장점:

- 요구사항 변경 시 기존 코드 유지
- 사용자가 몰라도 되는 데이터를 사용 시 , 해당 데이털르 ㄹ

단점: 요구사항이

```
public static void main(String[] args) {
    Sam sam = new Sam();
    sam.setHelloPolicy(new HelloHelloPolicy());
    sam.hello();

    sam.setHelloPolicy(new HiPolicy());
    sam.hello();
}
```