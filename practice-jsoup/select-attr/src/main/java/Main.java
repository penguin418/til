import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException {
        String youtubeUrl = "https://www.youtube.com";

        Document doc = Jsoup.connect(youtubeUrl).get();

        // 속성은 [] 안에서 검색할 수 있다

        // 예를 들어, 유튜브에는 다음과 같은 링크가 아주 많다
        // <a slot="guide-links-primary" ... >

        // a 요소이고, slot 속성이 guide-links-primary 일 때,
        Elements linkElems = doc.select("a[slot=guide-links-primary]");

        String[] hrefs = linkElems.stream().map(elem->elem.attr("href")).toArray(String[]::new);
        System.out.println(Arrays.toString(hrefs));
        // [https://www.youtube.com/about/, https://www.youtube.com/about/press/, ...

        // 정규식을 이용해서도 검색이 가능하다
        Elements detailElems = doc.select("div[class^=details]");
        System.out.println(detailElems);
        // <div class="details"><div class="channel-ava

        // jsoup 문서에 따르면 이외에도 아래의 옵션들이 있다
        //    el[attr]: attr 속성을 가진 el 요소, e.g. a[href]
        //    [attr]: attr 속성을 가진 요소, e.g. [href]
        //    [^attr]: attr 로 시작하는 속성을 가진 요소, e.g. [^data-] finds elements with HTML5 dataset attributes
        //    [attr=value]: attr 의 속성이 value 인 요소, e.g. [width=500] (also quotable, like [data-name='launch sequence'])
        //    [attr^=value], attr 의 속성이 value 로 시작하는 요소
        //    [attr$=value], attr 의 속성이 value 로 끝나는 요소
        //    [attr*=value]: attr 의 속성이 value 를 포함하는 요소, e.g. [href*=/path/]
        //    [attr~=regex]: attr 의 속성이 정규식 regexp 를 포함하는 요소 e.g. img[src~=(?i)\.(png|jpe?g)]
        //    *: 모든 것, e.g. *
        //    https://jsoup.org/cookbook/extracting-data/selector-syntax
    }
}
