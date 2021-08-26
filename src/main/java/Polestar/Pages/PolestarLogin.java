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

public class PolestarLogin {
    private static RemoteWebDriver driver;
    private Map<String, WebElement> mapping= new HashMap<>();

    public PolestarLogin(WebDriver driver) throws InterruptedException {
        this.driver = (RemoteWebDriver) driver;
        PageFactory.initElements(driver, this);
        new WebDriverWait(driver,10,1).until(ExpectedConditions.visibilityOf(loginForm));
        mapping.put("EMAIL ADDRESS", emailAddressInput);
        mapping.put("PASSWORD", passwordInput);
        mapping.put("LOGIN",loginBtn);


    }

    @FindBy(xpath = "//button[@class='optanon-allow-all accept-cookies-button']")
    private WebElement acceptCookies;
    @FindBy(css = "div[class*='optanon-alert-box-wrapper']")
    private WebElement cookieBar;
    @FindBy(className = "main-container")
    private WebElement loginForm;
    @FindBy(id = "email-username-field")
    private WebElement emailAddressInput;
    @FindBy(id = "password-field")
    private WebElement passwordInput;
    @FindBy(id = "login-btn")
    private WebElement loginBtn;

    public void enterValues(String key, String value) throws InterruptedException {
        mapping.get(key.toUpperCase()).sendKeys(value);
    }

    public void clickOnTheLink(String element) throws InterruptedException {
            clickOnElement(mapping.get(element.toUpperCase()));
    }

}
