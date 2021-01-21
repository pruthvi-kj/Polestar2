package Polestar.Utils;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

public class commonMethods {

    public static void clickOnElement(WebElement element) {
        try {
            element.click();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;

        }
    }
}
