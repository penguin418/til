import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Main {
    private static String base = "https://www.google.com/search?";

    public static void main(String[] args) throws IOException {
//        AbstractNewsParser newsParser;
//        newsParser = new NaverNewParser();
//        newsParser.parse("https://news.naver.com/main/read.nhn?mode=LSD&mid=shm&sid1=100&oid=001&aid=0012350318");
//        System.out.println(newsParser.toString());
//
//        newsParser = new DaumNewParser();
//        newsParser.parse("https://news.v.daum.net/v/20210423182429595");
//        System.out.println(newsParser.toString());
        Document doc = Jsoup.connect(base + "q=" + "디베이스앤").get();
        doc.select("script,style,path,input").remove();
//        System.out.println(doc);
        Elements elems = doc.select("div[class=g]");
        int i = 0;
        for (Element elem : elems){
            i += 1;
            System.out.println(i);
            System.out.println("URL  :" + elem.select(".iUh30").get(0).ownText());
            System.out.println("타이틀:" + elem.select(".LC20lb").text());
            System.out.println("본문  :" + elem.select(".aCOpRe").text());
            System.out.println("===============================================");
        }
    }
}
