// Command
public class PlainHelloCommand implements ICommand{

    @Override
    public void execute() {
        // 복잡한 행동 1
        System.out.println("hello");
    }
}
