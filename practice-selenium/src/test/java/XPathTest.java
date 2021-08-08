import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import webdriver.FireFoxDriverFactory;

public class XPathTest {
    private final FireFoxDriverFactory factory = new FireFoxDriverFactory();

    @Test
    public void 아이디(){
        FirefoxDriver driver = factory.construct();
        try {
            driver.get("https://www.naver.com");
            WebElement footer = driver.findElement(By.xpath("//div[@id='footer']"));
            System.out.println(footer.getAttribute("innerHTML").replace(">", ">\n"));
            Assertions.assertNotNull(footer);
        }finally {
            driver.quit();
        }
    }

    @Test
    public void 클래스(){
        FirefoxDriver driver = factory.construct();
        try {
            driver.get("https://www.naver.com");
            WebElement footer = driver.findElement(By.xpath("//div[@class='footer_inner']"));
            System.out.println(footer.getAttribute("innerHTML").replace(">", ">\n"));
            Assertions.assertNotNull(footer);
        }finally {
            driver.quit();
        }
    }

    @Test
    public void 속성(){
        FirefoxDriver driver = factory.construct();
        try {
            driver.get("https://www.naver.com");
            WebElement footer = driver.findElement(By.xpath("//div[@role='contentinfo']"));
            System.out.println(footer.getAttribute("innerHTML").replace(">", ">\n"));
            Assertions.assertNotNull(footer);
        }finally {
            driver.quit();
        }
    }
}
