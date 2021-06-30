package strategy;

import java.util.function.Function;

public enum StrategyPattern2{
    A((string)->"A"+string+"A"),
    B((string)->"B"+string+"B")
    ;

    private final Function<String, String> expression;

    public String print(String string){
        return expression.apply(string);
    }

    StrategyPattern2(Function<String, String> expression) {
        this.expression = expression;
    }
}
