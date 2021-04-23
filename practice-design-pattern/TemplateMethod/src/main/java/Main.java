public class Main {
    public static void main(String[] args){
        AbstractNewsParser newsParser;
        newsParser = new NaverNewParser();
        newsParser.parse("https://news.naver.com/main/read.nhn?mode=LSD&mid=shm&sid1=100&oid=001&aid=0012350318");
        System.out.println(newsParser.toString());

        newsParser = new DaumNewParser();
        newsParser.parse("https://news.v.daum.net/v/20210423182429595");
        System.out.println(newsParser.toString());
    }
}
