package Polestar.Pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.Iterator;
import java.util.List;

public class Polestar2 {
    WebDriver driver;
    Iterator<WebElement> w;

    public Polestar2(WebDriver driver) throws InterruptedException {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        Thread.sleep(1000);
        try {
            acceptCookies.click();
        } catch (Exception e) {}
    }

    @FindBy(xpath = "//div/ul/li/a[@href]")
    private List<WebElement> footerLinks;

    @FindBy(xpath = "//a[@href='/us/sign-up-newsletter/?redirect-url=/us/polestar-2/']")
    private WebElement SubscribeBtn;

    @FindBy(xpath = "//div[text()='United States']")
    private WebElement changeLocation;

    @FindBy(xpath = "//h1[text()='Select your region']")
    private WebElement regionSelect;

    @FindBy(xpath = "//button[@class='optanon-allow-all accept-cookies-button']")
    private WebElement acceptCookies;

    @FindBy(xpath = "//button[@class='css-1y07xyn']")
    private WebElement closeBtn;

    @FindBy(xpath = "//button[@title='menu']")
    private WebElement headerMenu;

    public void navigateToFooter() throws InterruptedException {
        ((JavascriptExecutor) driver)
                .executeScript("window.scrollTo(0, document.body.scrollHeight)");
        Thread.sleep(1000);
    }


    public boolean isElementVisible(String userPageTitle) {
        boolean isDisplayed = false;
        switch (userPageTitle) {
            case "Select your region":
                try {
                    isDisplayed = regionSelect.isDisplayed();
                    break;
                } catch (Exception e) {}
            default:
                ;
        }
        return isDisplayed;
    }

    public void clickOnTheLink(String linktext) {
        switch (linktext) {
            case "Subscribe":
                try {
                    SubscribeBtn.click();
                    break;
                } catch (Exception e) {}
            case "Change Location":
                try {
                    changeLocation.click();
                    break;
                } catch (Exception e) {}
            case "Close":
                try {
                    closeBtn.click();
                    break;
                } catch (Exception e) {}
            default:
                try {
                    w = footerLinks.iterator();
                    while (w.hasNext()) {
                        WebElement e = w.next();
                        String text = e.getText();
                        if (text != null && text.equalsIgnoreCase(linktext)) {
                            e.click();
                            break;
                        }
                    }
                } catch (Exception e) {}

        }
    }

    public String getPageTitle() throws InterruptedException {
        Thread.sleep(1000);
        return driver.getTitle();
    }


}
