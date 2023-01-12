package jep406;

public class PatternMatching {
    public static void main(String[] args) {
        System.out.println("getType 1 = " + getType(1));
        System.out.println("getType \"hello\" = " + getType("hello"));
        System.out.println("getType new char[]{'a','b'} = " + getType(new char[]{'a', 'b'}));
        System.out.println("getType 'a' = " + getType('a'));
        System.out.println("getType null = " + getType(null));
        getType3("hell");
    }

    public static String getType(Object o) {
        return switch (o) {
            case Integer i -> "integer";
            case String s && s.length() > 0-> "string";
            // 클래스 또는 array 만 사용 가능
            // case char c -> "char";
            case char[] c -> "char[]";
            case Character c -> "character";
            // null도 됨!
            case null -> "null";
            // default 타입 반드시 필요함 !
            default -> throw new IllegalStateException("Unexpected value: " + o);
        };
    }

    public static String getType2(Object o) {
        return switch (o) {
            // case 1:
            // [Dominance of pattern labels] 앞선 레이블인 Integer 가 뒤 레이블인 Integer i && i 에 비해 우세한 경우 컴파일 에러 발생
            // case Integer i -> "integer";

            // 2가지 한번에 검사 가능
            case Integer i && i > 10 -> "integer, > 10";
            case Integer i -> "integer";
            // [Completeness of pattern labels in switch expressions and statements] 패턴은 모든 케이스에 대응해야 함. 그렇지 않으면 컴파일 에러 발생
            default -> throw new IllegalStateException("Unexpected value: " + o);
        };
    }

    public static void getType3(Object o) {
        switch (o) {
            // [Matching null] 드디어 switch에서 null 처리를 지원! 아래 줄은 있으나 없으나 같은 의미.
            // case null: throw new NullPointerException();
            case Character c:
                System.out.println("str: " + c);
            // [Scope of pattern variable declarations] break 없이 패턴 스위치를 이어나갈 수 없음
            // case Integer i:
            //    ...

            default:
                throw new IllegalStateException("Unexpected value: " + o);
        }
    }
}
