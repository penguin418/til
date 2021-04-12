import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String args[]) throws InterruptedException {
        scenario1();
        scenario2();
    }

    public static void scenario1() throws InterruptedException {
        logger.info("scenario1");
        Bank bank = new Bank();
        ArrayList<Customer> customers = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            customers.add(new Customer(bank));
            customers.get(i).start();
        }
        for (int i = 0; i < 3; i++) {
            customers.get(i).join();
        }
    }

    public static void scenario2() throws InterruptedException {
        GoodBank goodBank = new GoodBank();
        ArrayList<Customer> customers = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            customers.add(new Customer(goodBank));
            customers.get(i).start();
        }
        for (int i = 0; i < 3; i++) {
            customers.get(i).join();
        }
    }

}
