package Steps;

import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;

import java.util.concurrent.TimeUnit;

public class hooks {
    private static WebDriver driver;
    private static String browserName;

    @Before
    public static void openBrowser() {
        browserName = (System.getProperty("browserName")==null)? "Chrome":System.getProperty("browserName");
            switch (browserName) {
                case "Chrome":
                    WebDriverManager.chromedriver().setup();
                    ChromeOptions options = new ChromeOptions();
//                  options.addArguments("--headless");
//                  options.addArguments("window-size=1920,1080");
                    options.addArguments("user-data-dir=target/ChromeData");
                    driver = new ChromeDriver(options);
                    break;
                case "Firefox":
                    WebDriverManager.firefoxdriver().setup();
                    driver = new FirefoxDriver();
                    break;
            }
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }


    public static WebDriver getDriver() {
        return driver;
    }

    @After
    public static void AfterMethod(Scenario scenario) {
        if (scenario.isFailed()) {
            // Take a screenshot...
            final byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            // embed it in the report.
            scenario.attach(screenshot, "image/png", "failure");
        }
        driver.quit();
    }

    @AfterStep
    public static void doSomethingAfterStep(Scenario scenario) {
        byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        // embed it in the report.
        if (scenario.isFailed()) {
            scenario.attach(screenshot, "image/png", "Failure");
        } else {
            scenario.attach(screenshot, "image/png", "Success");

        }

    }
}
