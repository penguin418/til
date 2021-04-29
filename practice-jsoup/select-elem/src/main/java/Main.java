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

        // html 파일의 구조
        // <!DOCTYPE ... >
        // <html> 전체 html 파일을 감싼다
        String html = doc.html(); // 전체 html
        // <!doctype html>
        // <html style="font-size: 10px;font-family: Roboto, Arial, sans-serif;" lang="ko-KR">
        //  <head> ...

        // <head> 헤드
        Element headElem = doc.head(); // 헤드 전체를 가진 element 를 반환한다

        // ......<meta> 메타 정보
        // meta 데이터는 meta 태그를 가지고 있다
        // <meta name="description" content="~~">
        String description = headElem.select("meta[name=description]").first().attr("content");
        // YouTube에서 마음에 드는 동영상과 음악을 감상하고, 직접 만든 콘텐츠를 업로드하여 친구, 가족뿐 아니라 전 세계 사람들과 콘텐츠를 공유할 수 있습니다.
        String keywords = headElem.select("meta[name=keywords]").first().attr("content");
        // 동영상, 공유, 카메라폰, 동영상폰, 무료, 올리기

        // ......<link> 보통은 css
        // ......<script> 보통은 js
        // 보통의 경우 css, js 는 사용할 이유가 없다. 삭제는 아래처럼 한다
        doc.select("link,script").remove();

        // ......<title> 제목
        String title = doc.title();
        // YouTube

        // <body>
        // body 전체를 가진 element 를 출력해 낼 수 있다
        Element bodyElem = doc.body();

        // ......<br> 띄어쓰기
        // 띄어쓰기로 대체하려면 append 를 사용한다
        // 이때, prettyPrint 설정을 종료해야 한다
        doc.outputSettings().prettyPrint(false);
        doc.select("br").prepend("\\n");
        // ......<i>, <hr>, select // 안 다룸

        // ......<p> 단락
        // 단락 안의 띄어쓰기를 유지하려면 false 옵션을 사용해야 한다
        doc.outputSettings().prettyPrint(false);

        // ......<h1> 제목
        // 태그를 고르기 위해선 plainText 로 태그를 적으면 된다
        Elements h1Elems = doc.select("h1");
        // 이때, 모든 요소가 선택되며 아래 방법으로 순환할 수 있다
        for(Element h1Elem: h1Elems);
        // 순환 할 필요 없으면 첫번째 것을 사용한다
        Element h1Elem = h1Elems.first();

        // ......<div> 상자
        Elements divElems = doc.select("div");

        // ......<span> 디자인
        Elements spanElems = doc.select("span");

        // ......<img> 이미지
        Elements imgElems = doc.select("img");

        // ......<input> 입력란
        Elements inputElems = doc.select("input");

        // ......<button> 버튼
        Elements buttonElems = doc.select("button");

        // ......<form> 폼
        Elements formElems = doc.select("form");

        // ......<a> 링크
        Elements linkElems = doc.select("a");
    }
}
