// Command
public class HtmlHelloCommand implements ICommand{
    private final HtmlPrinter htmlPrinter = new HtmlPrinter();
    @Override
    public void execute() {
        String html = this.htmlPrinter
                .init()
                .appendScriptUrl("http://hello.com/hello.js")
                .div("class=\"hello\"", new HtmlPrinter().innerHTML("hello"))
                .p("hello")
                .p("world").getFullHtml();
        System.out.println(html);
    }
}
