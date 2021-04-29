import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        String youtubeUrl = "https://www.youtube.com";

        Document doc = Jsoup.connect(youtubeUrl).get();
        // 띄어쓰기를 사용하여 요소A 의 하위 요소B 를 찾을 수 있다

        // 예를 들어 유튜브의 html 파일의 내용이 있다
        // <div class="video-details">
        // <div class="rich-thumbnail skeleton-bg-color"></div>
        // <div class="details"> ...

        // div 요소 중 video-detail 클래스 안의 div 들을 파싱하고 싶다면,
        Elements elems = doc.select("div.video-details div");
        // <div class="rich-thumbnail skeleton-bg-color"></div>
        // <div class="details"> ...
        
        // jsoup 문서에 따르면 이외에도 아래의 옵션들이 있다
        //    el#id: id를 아이디로 하는 el 요소, e.g. div#logo
        //    el.class: class 클래스를 가진 el 요소, e.g. div.masthead
        //    el[attr]: attr 속성을 가진 el 요소, e.g. a[href]
        //    ancestor child: ancester 요소 아래의 child 요소, e.g. .body p finds p elements anywhere under a block with class "body"
        //    parent > child: parent 요소 바로아래 있는 child 요소, e.g. div.content > p finds p elements; and body > * finds the direct children of the body tag
        //    siblingA + siblingB: siblingA 요소 바로 뒤에오는 siblingB 요소, e.g. div.head + div
        //    siblingA ~ siblingX: siblingX 요소 바로 뒤에오는 siblingB 요소, e.g. h1 ~ p
        //    el, el, el: 그룹 셀렉터, e.g. div.masthead, div.logo
        //    https://jsoup.org/cookbook/extracting-data/selector-syntax
    }
}
