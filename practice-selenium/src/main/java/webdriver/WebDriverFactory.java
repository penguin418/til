package webdriver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public abstract class WebDriverFactory<T extends WebDriver>{

    public abstract T construct();
}
