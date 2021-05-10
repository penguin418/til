import builder.AnsiTxtBuilder;
import builder.Utf8TxtBuilder;
import director.Director;
import org.junit.jupiter.api.Assertions;
import product.Txt;

public class Test {
    public static void main(String[] args){
        Director product1Director = new Director();
        Director product2Director = new Director();
        product1Director.setBuilder(new AnsiTxtBuilder());
        product2Director.setBuilder(new Utf8TxtBuilder());

        Txt product1 = product1Director.construct("Text");
        Txt anotherProduct1 = product1Director.construct("Text");
        Assertions.assertEquals(product1, anotherProduct1);

        Txt product2 = product2Director.construct("Text");
        Assertions.assertNotEquals(product1, product2);
    }
}
