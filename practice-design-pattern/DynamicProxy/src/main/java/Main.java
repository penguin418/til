import java.lang.reflect.Proxy;

public class Main {
    public static void main(String[] args) {

        Target target = (Target) Proxy.newProxyInstance(
                Thread.currentThread().getContextClassLoader(),
                new Class<?>[]{Target.class},
                (proxy, method, args1) -> {
                    return method.invoke(Target.class, args1);
                }
        );

        target.doSomething();;
    }
}
