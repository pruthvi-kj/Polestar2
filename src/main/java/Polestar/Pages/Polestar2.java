package Polestar.Pages;

import Polestar.Utils.commonMethods;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Polestar2 extends commonMethods {
    WebDriver driver;

    public Polestar2(WebDriver driver) throws InterruptedException {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        driver.switchTo().defaultContent();
        Thread.sleep(2000);
        try {
            if (acceptCookies.isDisplayed()) {
                WebDriverWait wait = new WebDriverWait(driver, 3);
                wait.until(ExpectedConditions.elementToBeClickable(acceptCookies));
                clickOnElement(acceptCookies);}
            }catch(Exception e){}
    }

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

    @FindBy(xpath = "//button[@class='css-1pqb5ae'][1]")
    private WebElement polestarDotComFooterMobile;

    @FindBy(xpath = "//button[@class='css-1pqb5ae'][2]")
    private WebElement polestarFooterMobile;

    @FindBy(xpath = "//button[@class='css-1pqb5ae'][3]")
    private WebElement discoverFooterMobile;

    @FindBy(xpath = "//button[@class='css-1pqb5ae'][4]")
    private WebElement socialFooterMobile;


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
                } catch (Exception e) {
                }
            default:
                ;
        }
        return isDisplayed;
    }

    public void clickOnTheLink(String linkText) {
        List<String> a = new ArrayList<String>();
        a.add("addsfg");
        a.add("sdfsdfg");
        a.add("dfdsfd");
        switch (linkText) {
            case "Subscribe":
                clickOnElement(SubscribeBtn);
                break;
            case "Change Location":
                clickOnElement(changeLocation);
                break;
            case "Close":
                clickOnElement(closeBtn);
                break;
            case "Header Menu":
                ((JavascriptExecutor) driver)
                        .executeScript("window.scrollTo(0,0)");
                clickOnElement(headerMenu);
                break;
            case "Polestar 1 Explore":
                clickOnElement(polestar1Explore);
                break;
            case "Polestar 1 Configure":
                Actions ac = new Actions(driver);
                ac.moveToElement(polestar1Configure).click().build().perform();
                break;
            case "Polestar 2 Configure":
                clickOnElement(polestar2Configure);
                break;
            case "Polestar 2 Explore":
                clickOnElement(polestar2Explore);
                break;
            case "Polestar 2 Test Drive":
                clickOnElement(polestar2TestDrive);
                break;
            case "polestar.com":
                clickOnElement(polestarDotComFooterMobile);
                break;
            case "Polestar":
                clickOnElement(polestarFooterMobile);
                break;
            case "Discover":
                clickOnElement(discoverFooterMobile);
                break;
            case "Social":
                clickOnElement(socialFooterMobile);
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

    public static WebElement checkElementExists(Iterator<WebElement> i, String linktext) {
        while (i.hasNext()) {
            WebElement he = i.next();
            String hText = he.getText();
            if (hText != null && hText.equalsIgnoreCase(linktext)) {
                return he;
            }
        }
        return null;
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
