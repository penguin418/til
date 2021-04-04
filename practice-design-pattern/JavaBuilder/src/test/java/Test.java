import org.junit.jupiter.api.Assertions;

public class Test {
    public static void main(String[] args){
        Coffee espresso = new Coffee.Builder()
                .shot(1).build();
        Coffee americano = new Coffee.Builder()
                .shot(1).water(7).build();
        Assertions.assertNotEquals(espresso, americano);
    }
}
