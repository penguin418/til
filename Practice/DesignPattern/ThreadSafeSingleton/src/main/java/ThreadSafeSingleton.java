public class ThreadSafeSingleton {
    private static ThreadSafeSingleton instance;
    private int counter;


    private ThreadSafeSingleton(){ }

    public synchronized static ThreadSafeSingleton getInstance(){
        if (instance == null)
            instance = new ThreadSafeSingleton();
        return instance;
    }
    public synchronized int getCounter(){
        return counter;
    }

    public synchronized void increaseCounter(){
        this.counter += 1;
        System.out.println("counter: " + this.counter);
    }
}
