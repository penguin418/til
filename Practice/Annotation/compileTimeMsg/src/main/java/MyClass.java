// package ...                         // PackageElement

import java.util.List;
import java.util.stream.Collectors;

public class MyClass {   // TypeElement

    private List<Character> randomCharacters; // VariableElement
    @XXX("length가 작동하지 않는데")
    private Integer length;            // VariableElement

    @Todo("randomString을 list에서 final String으로 리팩토링 예정")
    public MyClass(List<Character> randomCharacters, Integer length) {
        this.randomCharacters = randomCharacters;
//        this.length = length;
    }

    @FixMe("스트링으로 구현하는 것이 더 낫습니다")
    public String getRandomCharacters() {
        StringBuilder sb = new StringBuilder();
        for (Character ch : this.randomCharacters){
            sb.append(ch);
        }
        return sb.toString();
    }
    // ...
}
