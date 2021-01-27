package resources;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Utils {

    public static String getDeviceProperty(String key) throws IOException {
        Properties prop = new Properties();
        FileInputStream fis = new FileInputStream("src/test/java/resources/device.properties");
        prop.load(fis);
        return prop.getProperty(key);
    }

    public static void waitUntilPageTitle(WebDriver driver, String userPageTitle) {
        WebDriverWait wait = new WebDriverWait(driver, 3);
        try {
            wait.until(ExpectedConditions.titleContains(userPageTitle));
        } catch (Exception e) {
        }
    }

}
