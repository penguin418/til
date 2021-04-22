public class Main {
    public static void main(String[] args){
        Invoker manager = new Invoker();
        manager.execute(new HtmlHelloCommand());
        manager.execute(new PlainHelloCommand());
        manager.execute(new HtmlHelloCommand());
    }
}
