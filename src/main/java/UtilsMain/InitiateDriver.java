package UtilsMain;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.net.URL;
import java.time.Duration;

import static UtilsMain.CommonMethods.getDeviceProperty;


public class InitiateDriver {
    private static final ThreadLocal<RemoteWebDriver> localDrivers = new ThreadLocal<>();
    private static RemoteWebDriver driver, desktopDriver, mobileDriver;
    private static WebDriverWait wait;
    public static final String defaultEnv = "QA";


    public static void openMobileBrowser() throws IOException {
        if (mobileDriver == null) {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            String kobitonServerUrl = getDeviceProperty("kobitonServerUrl");
            String platform;
            platform = (System.getProperty("mobilePlatform") == null) ? "android" : System.getProperty("mobilePlatform");
            switch (platform) {
                case "android":
                    capabilities.setCapability("browserName", "chrome");
                    // For deviceName, platformVersion Kobiton supports wildcard
                    // character *, with 3 formats: *text, text* and *text*
                    // If there is no *, Kobiton will match the exact text provided
                    capabilities.setCapability("deviceName", getDeviceProperty("deviceNameAndroid"));
                    capabilities.setCapability("platformVersion", getDeviceProperty("platformVersionAndroid"));
                    capabilities.setCapability("platformName", "Android");
                    capabilities.setCapability("automationName", "uiautomator2");
                    ChromeOptions chromeOptions = new ChromeOptions();
                    chromeOptions.setExperimentalOption("w3c", true);
                    capabilities.setCapability("chromeOptions", chromeOptions);
                    break;

                case "ios":
                    capabilities.setCapability("browserName", "safari");
                    // For deviceName, platformVersion Kobiton supports wildcard
                    // character *, with 3 formats: *text, text* and *text*
                    // If there is no *, Kobiton will match the exact text provided
                    capabilities.setCapability("deviceName", getDeviceProperty("deviceNameIos"));
                    capabilities.setCapability("platformVersion", getDeviceProperty("platformVersionIos"));
                    capabilities.setCapability("platformName", "iOS");
                    capabilities.setCapability("automationName", "uiautomator2");
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
            mobileDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        }
        driver = mobileDriver;
        localDrivers.set(driver);
    }

    public static void openWebBrowser() {
        if (desktopDriver == null) {
            String platform;
            platform = (System.getProperty("desktopPlatform") == null) ? "chrome" : System.getProperty("desktopPlatform");
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
            desktopDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
            desktopDriver.manage().window().maximize();
        }
        driver = desktopDriver;
        localDrivers.set(driver);
    }

    public static RemoteWebDriver getDriver() {
        return localDrivers.get();
    }

    public static void closeDriver() {
        localDrivers.remove();
        if (desktopDriver != null) {
            desktopDriver.close();
            desktopDriver.quit();
        } else {
            mobileDriver.close();
            mobileDriver.quit();
        }
        desktopDriver = null;
        mobileDriver = null;
    }

    public static void explicitWait() {
        wait = new WebDriverWait(getDriver(), 2, 1000);
    }

    public static WebDriverWait getWebDriverWait() {
        return wait;
    }

}
