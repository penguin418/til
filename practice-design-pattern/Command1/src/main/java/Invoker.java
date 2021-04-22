import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

// Invoker
public class Invoker {

    public void execute(ICommand command) {
        command.execute();
    }
}
