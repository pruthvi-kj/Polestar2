package Steps;

import io.cucumber.java.Before;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import resources.Utils;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class hooks extends Utils {
    private static final ThreadLocal<RemoteWebDriver> localDrivers = new ThreadLocal<>();
    private static RemoteWebDriver driver, desktopDriver, mobileDriver;

    @Before("@Mobile")
    public static void openMobileBrowser() throws IOException {
        if (mobileDriver == null) {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            String kobitonServerUrl = getDeviceProperty("kobitonServerUrl");
            String platform;
            platform = (System.getProperty("mobilePlatform") == null) ? "android" : System.getProperty("mobilePlatform");
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
            mobileDriver = new RemoteWebDriver(new URL(kobitonServerUrl), capabilities);
            mobileDriver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        }
        driver = mobileDriver;
        localDrivers.set(driver);
    }

    public static RemoteWebDriver getDriver() {
        return localDrivers.get();
    }

//    @After("@Footer,@Header")
//    public static void AfterMethod(Scenario scenario) {
//        driver.get("https://google.com");
////        if (scenario.isFailed()) {
////            // Take a screenshot...
////            final byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
////            // embed it in the report.
////            scenario.attach(screenshot, "image/png", "failure");
////        }
//    }

//    @AfterStep
//    public static void doSomethingAfterStep(Scenario scenario) {
////        byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
////        // embed it in the report.
////        if (scenario.isFailed()) {
////            scenario.attach(screenshot, "image/png", "Failure");
////        } else {
////            scenario.attach(screenshot, "image/png", "Success");
////        }
//    }

    public static void closeDriver() {
        desktopDriver = null;
        mobileDriver = null;
        try {
            localDrivers.get().close();
            localDrivers.get().quit();
        } catch (Exception e) {
        }

    }

    public static void formURL() {
        String URL = "https://yml.dev.devhouse.digital/us/yml/";

    }

    @Before("@Desktop")
    public void openWebBrowser() {
        if (desktopDriver == null) {
            String platform;
            platform = (System.getProperty("desktopPlatform") == null) ? "chrome" : System.getProperty("desktopPlatform");
            System.out.println(System.getProperty("desktopPlatform"));
            switch (platform) {
                case "chrome":
                    WebDriverManager.chromedriver().setup();
                    ChromeOptions options = new ChromeOptions();
//                    options.addArguments("--headless");
//                    options.addArguments("window-size=1920,1080");
                    options.addArguments("user-data-dir=target/ChromeData");
                    desktopDriver = new ChromeDriver(options);
                    break;
                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    FirefoxBinary firefoxBinary = new FirefoxBinary();
                    firefoxBinary.addCommandLineOptions("--headless");
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    firefoxOptions.setBinary(firefoxBinary);
                    desktopDriver = new FirefoxDriver(firefoxOptions);
                    break;
                case "safari":
                    desktopDriver = new SafariDriver();
                    break;
                //                  *ipad using chrome emulation(to be added if needed)*
//            case "ipad":
//                Map<String, String> mobileEmulation = new HashMap<>();
//                mobileEmulation.put("deviceName", "Nexus 5");
//
//                ChromeOptions chromeOptions = new ChromeOptions();
//                chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);
//
//                desktopDriver = new ChromeDriver(chromeOptions);
            }
            desktopDriver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
            desktopDriver.manage().window().maximize();
        }
        driver = desktopDriver;
        localDrivers.set(driver);
    }
}
