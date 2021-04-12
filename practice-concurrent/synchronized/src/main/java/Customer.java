public class Customer extends Thread{

    private final Bank cafe;

    public Customer(Bank cafe){
        this.cafe = cafe;
    }

    public void run(){
        cafe.use();
    }
}
