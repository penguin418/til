public class HtmlPrinter {
    private String head;
    private String body;

    public HtmlPrinter init(){
        this.head = "";
        this.body = "";
        return this;
    }

    public HtmlPrinter appendScriptUrl(String url){
        this.head += "<script type=\"text/javascript\" src=\"" + url + "\"></script>";
        return this;
    }

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
    public HtmlPrinter p(String msg, HtmlPrinter htmlPrinter){
        this.body += "<p "+msg+">" + (htmlPrinter != null ? htmlPrinter.getBody() : "") + "</p>";
        return this;
    };

    public HtmlPrinter innerHTML(String msg){
        this.body = msg;
        return this;
    }

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
