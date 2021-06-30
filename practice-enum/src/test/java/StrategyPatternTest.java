import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import strategy.StrategyPattern;
import strategy.StrategyPattern2;

public class StrategyPatternTest {
    @Test
    public void test1(){
        StrategyPattern p1 = StrategyPattern.A;
        StrategyPattern2 p2 = StrategyPattern2.A;

        Assertions.assertEquals(p1.print("hello"), p2.print("hello"));
    }
}
