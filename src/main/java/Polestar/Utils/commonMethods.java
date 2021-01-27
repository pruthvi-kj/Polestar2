package Polestar.Utils;

import org.openqa.selenium.WebElement;

public class commonMethods {

    public static void clickOnElement(WebElement element) {
        try {
            element.click();
        } catch (Exception e) {
            throw e;

        }
    }
}
