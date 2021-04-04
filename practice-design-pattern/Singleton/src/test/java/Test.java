import static org.junit.jupiter.api.Assertions.*;

public class Test {
    public static void main(String[] args){
        Singleton instance1 = Singleton.getInstance();
        Singleton instance2 = Singleton.getInstance();

        assertEquals(instance1, instance2);
    }
}
