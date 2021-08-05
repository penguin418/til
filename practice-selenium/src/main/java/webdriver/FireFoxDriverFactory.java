package webdriver;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.nio.file.Paths;

public class FireFoxDriverFactory extends WebDriverFactory<FirefoxDriver> {
    /**
     *
     */
    private static final String WEBDRIVER_ID = "webdriver.gecko.driver";
    private static final String WEBDRIVER_PATH =
            Paths.get( "src","main", "resources","geckodriver.exe")
                    .toAbsolutePath().toString();

    @Override
    public FirefoxDriver construct() {
        System.setProperty(WEBDRIVER_ID, WEBDRIVER_PATH);
        FirefoxOptions options = new FirefoxOptions();
        options.setHeadless(true);
        return new FirefoxDriver(options);
    }
}
