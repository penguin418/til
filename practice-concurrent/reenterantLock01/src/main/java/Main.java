import java.util.List;

public class Main {

    public static void main(String args[]) throws InterruptedException {
        List<Thread> threads = List.of(
                new ReentrantLockTest(),
                new ReentrantLockTest(),
                new ReentrantLockTest());
        for(Thread t : threads){
            t.start();
        }
        for(Thread t : threads){
            t.join();
        }
    }
}
