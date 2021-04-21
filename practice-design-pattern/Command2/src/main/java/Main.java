public class Main {
    public static void main(String[] args){
        CommandManager manager = new CommandManager();
        manager.execute(new HelloCommand());
        manager.execute(new WorldCommand());
        manager.execute(new HelloCommand());

        manager.undo();
        manager.undo();
        manager.redo();
    }
}
