package Polestar.Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.HashMap;
import java.util.Map;

import static UtilsMain.CommonMethods.clickOnElement;

public class PolestarProfile {

    private static RemoteWebDriver driver;
    private Map<String, WebElement> mapping = new HashMap<>();

    public PolestarProfile(WebDriver driver) throws InterruptedException {
        this.driver = (RemoteWebDriver) driver;
        PageFactory.initElements(driver, this);
        new WebDriverWait(driver, 15, 1).until(ExpectedConditions.visibilityOf(loggedInHomeScreen));
        mapping.put("LOG OUT",logoutBtn);
    }

    @FindBy(xpath = "//button[@class='optanon-allow-all accept-cookies-button']")
    private WebElement acceptCookies;
    @FindBy(css = "div[class*='optanon-alert-box-wrapper']")
    private WebElement cookieBar;

    @FindBy(className = "css-yp9swi")
    private WebElement loggedInHomeScreen;
    @FindBy(className = "css-1dfa8nz")
    private WebElement logoutBtn;

    public void enterValues(String key, String value) throws InterruptedException {
        mapping.get(key.toUpperCase()).sendKeys(value);
    }

    public void clickOnTheLink(String element) throws InterruptedException {
        clickOnElement(mapping.get(element.toUpperCase()));
    }

    public boolean ifUserLoggedIn() {
        try {
            new WebDriverWait(driver, 15).until(ExpectedConditions.visibilityOf(loggedInHomeScreen));
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
