package Polestar.Pages;

import Polestar.Utils.commonMethods;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.util.*;
import java.util.function.Function;

public class HeaderAndFooter extends commonMethods {

    private static Map<String, WebElement> mapping = new HashMap<String, WebElement>();
    private static WebDriver driver;
    @FindBy(xpath = "//a[@href and @class='css-1p608u1' or @class='css-nofjbs' or @class='css-stpt7n']")
    private List<WebElement> headerLinks;
    @FindBy(css = "a[class='css-1asux84']")
    private WebElement SubscribeBtn;
    @FindBy(css = "div[class*='optanon-alert-box-wrapper']")
    private WebElement cookieBar;
    @FindBy(xpath = "//div[text()='United States']")
    private WebElement changeLocation;
    @FindBy(xpath = "//h1[text()='Select your region']")
    private WebElement regionSelect;
    @FindBy(xpath = "//button[@class='optanon-allow-all accept-cookies-button']")
    private WebElement acceptCookies;
    @FindBy(css = "button[class='css-1y07xyn']>div")
    private WebElement closeBtn;
    @FindBy(css = "[class='css-1nlu2c6']")
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
    @FindBy(css = "[class='css-yof1vp']>div:nth-child(1)")
    private WebElement polestarDotComFooterMobile;
    @FindBy(css = "[class='css-yof1vp']>div:nth-child(2)")
    private WebElement polestarFooterMobile;
    @FindBy(css = "[class='css-yof1vp']>div:nth-child(3)")
    private WebElement discoverFooterMobile;
    @FindBy(css = "[class='css-yof1vp']>div:nth-child(4)")
    private WebElement socialFooterMobile;
    @FindBy(how= How.CSS , using= "section[class='css-10n9r42'] a[href]")
    private List<WebElement> footerLinks;
    private static final Logger LOG = LogManager.getLogger(HeaderAndFooter.class);

    public HeaderAndFooter(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        driver.switchTo().defaultContent();
        try {
            try {
                Wait<WebDriver> waitF = new FluentWait<WebDriver>(driver)
                        .withTimeout(Duration.ofSeconds(5))
                        .pollingEvery(Duration.ofSeconds(1))
                        .ignoring(NoSuchElementException.class);
                WebElement foo = waitF.until(new Function<WebDriver, WebElement>() {
                    public WebElement apply(WebDriver driver) {
                        return driver.findElement(By.className("css-113edzk"));
                    }
                });
                ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0)");
                WebDriverWait wait = new WebDriverWait(driver, 5);
                wait.until(ExpectedConditions.visibilityOf(cookieBar));
                wait.until(ExpectedConditions.elementToBeClickable(acceptCookies));
            } catch (Exception e) {
                LOG.error(e.getMessage());
                LOG.error(e.getStackTrace().toString());
            }
            while (acceptCookies.isDisplayed()){
                clickOnElementJS(driver,acceptCookies);
            }
        } catch (Exception e) {
            LOG.error(e.getMessage());
            LOG.error(e.getStackTrace().toString());
        }
        mapping.put("SUBSCRIBE", SubscribeBtn);
        mapping.put("CHANGE LOCATION", changeLocation);
        mapping.put("CLOSE", closeBtn);
        mapping.put("POLESTAR 1 EXPLORE", polestar1Explore);
        mapping.put("POLESTAR 1 CONFIGURE", polestar1Configure);
        mapping.put("POLESTAR 2 CONFIGURE", polestar2Configure);
        mapping.put("POLESTAR 2 EXPLORE", polestar2Explore);
        mapping.put("POLESTAR 2 TEST DRIVE", polestar2TestDrive);
        mapping.put("POLESTAR.COM", polestarDotComFooterMobile);
        mapping.put("POLESTAR", polestarFooterMobile);
        mapping.put("DISCOVER", discoverFooterMobile);
        mapping.put("SOCIAL", socialFooterMobile);
        mapping.put("SELECT YOUR REGION", regionSelect);
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
        } catch (Exception e) {
        }
        return isDisplayed;
    }

    public void clickOnTheLink(String linkText) {
        WebElement ele = mapping.get(linkText.toUpperCase());
        if (ele != null & linkText != "Header Menu") {
            clickOnElement(mapping.get(linkText.toUpperCase()));
        } else {
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

    public boolean verifyAllLinksAreValid(String attName) throws Exception {
        System.out.println(footerLinks.size());
        final boolean[] footerLinksValid = {true};
        footerLinks.stream().filter(s->!s.getAttribute(attName).contains("https://developer.polestar.com")).forEach(s->{
            try {
                HttpURLConnection connection = (HttpURLConnection)new URL(s.getAttribute(attName)).openConnection();
                connection.setRequestMethod("HEAD");
                connection.connect();
                LOG.info(s.getAttribute(attName)+"-"+connection.getResponseCode());
                footerLinksValid[0] = footerLinksValid[0] && connection.getResponseCode()==(s.getAttribute(attName).contains("instagram")?405:200);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return footerLinksValid[0];
    }
}
