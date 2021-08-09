import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import webdriver.FireFoxDriverFactory;

import java.util.concurrent.TimeUnit;

public class WaitTest {
    private FireFoxDriverFactory factory = new FireFoxDriverFactory();

    @Test
    public void 암시적_기다림() {
        FirefoxDriver driver = factory.construct();
        // 기본적으로 selenium 은 시간내 동작에 실패할 경우 TimeOut 에러가 발생한다
        // 하지만 WAIT 시간을 사용함으로서 timeout 조금 늦춘다
        try {
            // 페이지가 완전히 그려지기 까지의 최대 기다림 시간을 정의한다
            // python 등에서 사용하는 waitForAngularEnabled() 대신 사용할 수 있음
            driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
            driver.get("https://www.naver.com");

            // 특정 dom element 그려짐을 최대 기다림 시간을 정의한다
            // 페이지가 완전히 그려지는 시간을 사용하지 않는 경우, 사용되는 옵션으로
            // 현재는 페이지가 완전히 그려진 후이므로 0으로 지정해도 된다
            driver.manage().timeouts().implicitlyWait(0, TimeUnit.NANOSECONDS);
            WebElement queryElem = driver.findElementById("query");
            System.out.println(queryElem.toString());

            // 자바스크립트를 얼마나 기다릴 지 정의한다
            driver.manage().timeouts().setScriptTimeout(1000, TimeUnit.MILLISECONDS);
            // 검색어 입력 ( search term )
            ((JavascriptExecutor) driver).executeScript("document.querySelector('#query').value=\"search term\"");
            // 검색 버튼 클릭
            ((JavascriptExecutor) driver).executeAsyncScript("window.nclick(this,'sch.action','','', document.querySelector('#search_btn').click())");

            System.out.println(driver.getCurrentUrl());


        } catch (TimeoutException e) {
            System.out.println("예외발생");

            e.printStackTrace();
        } finally {
            driver.close();
        }
    }

    @Test
    public void 명시적_기다림() throws InterruptedException {
        FirefoxDriver driver = factory.construct();

        try {
            // Thread sleep 을 통해 기다리는 방법, 무조건 10초를 기다린다
            // 쓰레드 자체가 멈춰버린다
            driver.get("https://www.naver.com");
            Thread.sleep(10_000);

            // 명시적 대기 - 조건
            driver.get("https://www.naver.com");


            // 명시적 기다림을 사용하는 방법: 조건이 만족되길 기다린다
            WebDriverWait wait = new WebDriverWait(driver, 10);
            // iframe 이 존재하는 경우 해당 iframe 으로 이동하는 조건
            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("da_iframe_time"));
            // 출력
            System.out.println("===== ===== ===== ===== ===== ===== ===== ===== ===== =====\n" + driver.getPageSource());

            // 광고 img 가 존재하는 경우, 해당 img 를 선택하는 조건
            WebElement mainAd = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='addiv']//a//img")));
            String imgTitle = mainAd.getAttribute("alt");
            System.out.println("===== ===== ===== ===== ===== ===== ===== ===== ===== =====\n" + driver.getPageSource());

            // 무조건 3초를 기다리는 조건
            wait.wait(3_000);

        } catch (TimeoutException e) {
            System.out.println("예외발생");

            e.printStackTrace();
        } finally {
            driver.close();
        }
    }
}
