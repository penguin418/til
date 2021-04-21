import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

// Invoker
public class CommandManager {
    private Stack<ICommand> undos = new Stack<>();
    private Stack<ICommand> redos = new Stack<>();

    public void execute(ICommand command){
        command.execute();
        undos.add(command);
    }

    public void undo(){
        ICommand command = undos.pop();
        command.undo();
        redos.add(command);
    }

    public void redo(){
        ICommand command = redos.pop();
        command.execute();
        undos.add(command);
    }
}
