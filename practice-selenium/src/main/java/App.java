import org.openqa.selenium.firefox.FirefoxDriver;
import webdriver.FireFoxDriverFactory;

import java.util.concurrent.TimeUnit;

public class App {
    public static void main(String[] args) {
        FireFoxDriverFactory factory = new FireFoxDriverFactory();
        FirefoxDriver driver = factory.construct();
        try {
            driver.get("https://google.com");
            System.out.println(driver.getCurrentUrl());
        }finally {
            driver.close();
        }
    }
}
