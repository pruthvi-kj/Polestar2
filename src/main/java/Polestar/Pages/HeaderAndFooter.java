package Polestar.Pages;

import Polestar.Utils.commonMethods;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.*;

public class HeaderAndFooter extends commonMethods {

    Map<String, WebElement> mapping = new HashMap<String, WebElement>();
    WebDriver driver;
    @FindBy(xpath = "//a[@href and @class='css-10wxmov' or @class='css-1asux84']")
    private List<WebElement> footerLinks;
    @FindBy(xpath = "//a[@href and @class='css-1p608u1' or @class='css-nofjbs' or @class='css-stpt7n']")
    private List<WebElement> headerLinks;
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
    @FindBy(xpath = "//a[@href='/us/polestar-1/' and @class='css-1p608u1']")
    private WebElement polestar1Explore;
    @FindBy(xpath = "//a[@href='/us/polestar-1/configurator/' and @class='css-nofjbs']")
    private WebElement polestar1Configure;
    @FindBy(xpath = "//a[@href='/us/polestar-2/' and @class='css-1p608u1']")
    private WebElement polestar2Explore;
    @FindBy(xpath = "//a[@href='/us/polestar-2/configurator/' and @class='css-1p608u1']")
    private WebElement polestar2Configure;
    @FindBy(xpath = "//a[@href='/us/test-drive/booking/select-location?location-type&model=ps2/'] and @class='css-nofjbs']")
    private WebElement polestar2TestDrive;
    @FindBy(xpath = "//button[@aria-controls='wusj1esvciq8fo5c-0']")
    private WebElement polestarDotComFooterMobile;
    @FindBy(xpath = "//button[@aria-controls='wusj1esvciq8fo5c-1']")
    private WebElement polestarFooterMobile;
    @FindBy(xpath = "//button[@aria-controls='wusj1esvciq8fo5c-2']")
    private WebElement discoverFooterMobile;
    @FindBy(xpath = "//button[@aria-controls='wusj1esvciq8fo5c-3']")
    private WebElement socialFooterMobile;

    public HeaderAndFooter(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        driver.switchTo().defaultContent();
        try {
            Thread.sleep(6000);
//                WebDriverWait wait = new WebDriverWait(driver, 3);
//                wait.until(ExpectedConditions.visibilityOf(acceptCookies));
//                wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath
//                        ("//button[@class='optanon-allow-all accept-cookies-button']")));
            clickOnElement(acceptCookies);
        } catch (Exception e) {
        }
        mapping.put("SUBSCRIBE",SubscribeBtn);
        mapping.put("CHANGE LOCATION",changeLocation);
        mapping.put("CLOSE",closeBtn);
        mapping.put("POLESTAR 1 EXPLORE",polestar1Explore);
        mapping.put("POLESTAR 1 CONFIGURE",polestar1Configure);
        mapping.put("POLESTAR 2 CONFIGURE",polestar2Configure);
        mapping.put("POLESTAR 2 EXPLORE",polestar2Explore);
        mapping.put("POLESTAR 2 TEST DRIVE",polestar2TestDrive);
        mapping.put("POLESTAR.COM",polestarDotComFooterMobile);
        mapping.put("POLESTAR",polestarFooterMobile);
        mapping.put("DISCOVER",discoverFooterMobile);
        mapping.put("SOCIAL",socialFooterMobile);
        mapping.put("SELECT YOUR REGION",regionSelect);
    }


    public void navigateToFooter() throws InterruptedException {
        ((JavascriptExecutor) driver)
                .executeScript("window.scrollTo(0, document.body.scrollHeight)");
        Thread.sleep(1000);
    }

    public boolean isElementVisible(String userPageTitle) {
        boolean isDisplayed = false;
                try {
                    isDisplayed = mapping.get(userPageTitle.toUpperCase()).isDisplayed();
                } catch (Exception e) {}
        return isDisplayed;
    }

    public void clickOnTheLink(String linkText) {
        WebElement ele = mapping.get(linkText.toUpperCase());
        if(ele!=null & linkText!="Header Menu"){
            clickOnElement(mapping.get(linkText.toUpperCase()));
        }
        else {
            switch (linkText) {
                case "Header Menu":
                    ((JavascriptExecutor) driver)
                            .executeScript("window.scrollTo(0,0)");
                    clickOnElement(headerMenu);
                    break;
                default:
                    Iterator<WebElement> h = headerLinks.iterator();
                    //to check if there are header elements are present
                    if (!h.hasNext()) {
                        clickOnElement(checkElementExists(footerLinks.iterator(), linkText));
                    } else if (h.hasNext()) {
                        clickOnElement(checkElementExists(h, linkText));
                    }
            }
        }
    }

    public String getPageTitle() throws InterruptedException {
        Thread.sleep(1000);
        return driver.getTitle();
    }


    public void moveCursorTo(String Menu) throws InterruptedException {
        Thread.sleep(1000);
        WebElement menuElement = checkElementExists(headerLinks.iterator(), Menu);
        Actions action = new Actions(driver);
        try {
            action.moveToElement(menuElement).build().perform();
        } catch (Exception e) {
            throw e;
        }

    }
}
