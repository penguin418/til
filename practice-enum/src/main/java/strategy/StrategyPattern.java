package strategy;

import java.util.List;

public enum StrategyPattern implements Runnable{
    A{
        @Override
        public String print(String string) {
            return "A"+string.toString()+"A";
        }
    },
    B{
        @Override
        public String print(String string) {
            return "B"+string.toString()+"B";
        }
    }
    ;

    public abstract String print(String string);

    @Override
    public void run() {

    }
}
