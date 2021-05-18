import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Queue<HeavyWork> heavyWorks = new ArrayDeque<>()
        {{
            add(new SomeHeavyWork("1"));
            add(new SomeHeavyWork("2"));
            add(new SomeHeavyWorkVirtualProxy("3"));
            add(new SomeHeavyWorkVirtualProxy("4"));
        }};

        System.out.println(heavyWorks.remove().getValue());
        System.out.println(heavyWorks.remove().getValue());
        System.out.println(heavyWorks.remove().getValue());
        System.out.println(heavyWorks.remove().getValue());
    }
}
