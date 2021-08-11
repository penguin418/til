import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import webdriver.FireFoxDriverFactory;

public class InteractionTest {
    @Test
    public void 입력() {
        FireFoxDriverFactory factory = new FireFoxDriverFactory();
        FirefoxDriver driver = factory.construct();
        try {
            final String NAVER_HOME = "https://www.naver.com";
            driver.get(NAVER_HOME);
            WebElement searchBox = driver.findElement(By.xpath("//input[@id='query']"));


            Assertions.assertEquals("", searchBox.getAttribute("value"));

            // sendKeys 로 입력할 수 있다
            searchBox.sendKeys("검색어");
            Assertions.assertNotEquals("", searchBox.getAttribute("value"));

            // click 으로 버튼을 클릭 할 수 있다
            driver.findElement(By.xpath("//button[@id='search_btn']"))
                    .click();
            Assertions.assertNotEquals(NAVER_HOME, driver.getCurrentUrl());

            // clear 로 지울 수 있다
            searchBox = driver.findElement(By.xpath("//input[@id='nx_query']"));
            searchBox.clear();
            Assertions.assertEquals("", searchBox.getAttribute("value"));
        } finally {
            driver.close();
        }
    }
}
