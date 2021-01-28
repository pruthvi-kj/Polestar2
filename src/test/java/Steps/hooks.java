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
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import resources.Utils;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class hooks extends Utils {
    private static WebDriver driver;

    @Before("@Mobile")
    public static void openMobileBrowser() throws IOException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        String kobitonServerUrl = getDeviceProperty("kobitonServerUrl");
        String platform;
        platform = (System.getProperty("mobilePlatform") == null) ? "ios" : System.getProperty("mobilePlatform");
        System.out.println(platform);
        switch (platform) {
            case "android":
                capabilities.setCapability("browserName", "chrome");
                // For deviceName, platformVersion Kobiton supports wildcard
                // character *, with 3 formats: *text, text* and *text*
                // If there is no *, Kobiton will match the exact text provided
                capabilities.setCapability("deviceName", getDeviceProperty("deviceNameAndroid"));
                capabilities.setCapability("platformVersion", getDeviceProperty("platformVersionAndroid"));
                capabilities.setCapability("platformName", "Android");
                break;

            case "ios":
                capabilities.setCapability("browserName", "safari");
                // For deviceName, platformVersion Kobiton supports wildcard
                // character *, with 3 formats: *text, text* and *text*
                // If there is no *, Kobiton will match the exact text provided
                capabilities.setCapability("deviceName", getDeviceProperty("deviceNameIos"));
                capabilities.setCapability("platformVersion", getDeviceProperty("platformVersionIos"));
                capabilities.setCapability("platformName", "iOS");
                break;
        }

            capabilities.setCapability("sessionName", "Automation test session");
            capabilities.setCapability("sessionDescription", "");
            capabilities.setCapability("deviceOrientation", "portrait");
            capabilities.setCapability("captureScreenshots", true);
            // The given group is used for finding devices and the created session will be visible for all members
            // within the group.
            capabilities.setCapability("groupId", 2016); // Group: Team-Polestar
            capabilities.setCapability("deviceGroup", "KOBITON");
            driver = new RemoteWebDriver(new URL(kobitonServerUrl), capabilities);
    }

    @Before("@Desktop")
    public void openWebBrowser(){
        String platform;
        platform = (System.getProperty("desktopPlatform") == null) ? "firefox" : System.getProperty("desktopPlatform");
        System.out.println(System.getProperty("desktopPlatform"));
        switch (platform) {
            case "Chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--headless");
                options.addArguments("window-size=1920,1080");
                options.addArguments("user-data-dir=target/ChromeData");
                driver = new ChromeDriver(options);
                break;
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxBinary firefoxBinary = new FirefoxBinary();
                firefoxBinary.addCommandLineOptions("--headless");
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.setBinary(firefoxBinary);
                driver = new FirefoxDriver(firefoxOptions);
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
        driver.close();
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
