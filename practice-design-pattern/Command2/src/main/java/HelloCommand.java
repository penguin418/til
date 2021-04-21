// Command
public class HelloCommand implements ICommand{

    @Override
    public void execute() {
        // 복잡한 행동 1
        System.out.println("hello");
    }

    @Override
    public void undo() {
        // 복잡한 행동 1
        System.out.println("undo hello");
    }
}
