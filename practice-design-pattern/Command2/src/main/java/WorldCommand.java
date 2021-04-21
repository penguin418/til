// Command
public class WorldCommand implements ICommand{

    @Override
    public void execute() {
        // 복잡한 행동 1
        System.out.println("world");
    }

    @Override
    public void undo() {
        // 복잡한 행동 1
        System.out.println("undo world");
    }
}
