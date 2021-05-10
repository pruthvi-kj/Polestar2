package Polestar.Pages;

import Polestar.Utils.commonMethods;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.TestInitialization;
import utils.TestReport;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BuyingProcess extends commonMethods {
    private static final Logger LOG = LogManager.getLogger(BuyingProcess.class);
    private static final String learnOrSeeMoreCta = "div[class='css-ly8tcg']";
    private static final String spacesListSection = "css-1nfgff6";
    public static WebElement temp;
    private static RemoteWebDriver driver;
    private static TestReport testReport;

    Map<String, WebElement> mapping = new HashMap<>();
    @FindBy(css = "div[class*='optanon-alert-box-wrapper']")
    private WebElement cookieBar;
    @FindBy(xpath = "//button[@class='optanon-allow-all accept-cookies-button']")
    private WebElement acceptCookies;
    @FindBy(css = "div[class='css-1eh5vff']")
    private WebElement tabHeadingView;
    @FindBy(className = "css-d29to2")
    private List<WebElement> tabHeadings;
    @FindBy(css = ".css-1eh5vff button[class='css-47lmvx']>span")
    private WebElement sectionNavigatedTo;
    @FindBy(css = "[data-name]")
    private List<WebElement> sections;
    @FindBy(className = "css-t8cify")
    private WebElement modalOpen;
    @FindBy(className = "css-1qbfuld")
    private List<WebElement> spaces;
    @FindBy(css = "[class='css-yp9swi'] [href]")
    private List<WebElement> buyingProcessLinks;
    @FindBy(className = "css-1xuj1yo")
    private WebElement closeCTA;

    public BuyingProcess(WebDriver driver) {

        this.driver = (RemoteWebDriver) driver;
        PageFactory.initElements(driver, this);
        driver.switchTo().defaultContent();
        try {
            ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0)");
            WebDriverWait wait = new WebDriverWait(driver, 5);
            wait.until(ExpectedConditions.visibilityOf(cookieBar));
            wait.until(ExpectedConditions.elementToBeClickable(acceptCookies));
        } catch (Exception e) {
            LOG.error(e.getMessage());
            LOG.error(e.getStackTrace());
        }
        while (acceptCookies.isDisplayed())
            clickOnElementJS(driver, acceptCookies);
        mapping.put("TAB HEADINGS", tabHeadingView);
        testReport = TestInitialization.getInstance();
    }

    public void navigateToView(String view) throws InterruptedException {
        try{
            new WebDriverWait(driver, 1).until(ExpectedConditions.elementToBeClickable(closeCTA));
            clickOnElement(closeCTA);}
        catch (Exception e){}
        temp = mapping.containsKey(view.toUpperCase()) ? mapping.get(view.toUpperCase()) : getSectionToNavigate(sections, view, "data-name");
        navigateUsingJSToAnElementStart(driver, temp);
    }

    public String navigateToSectionUsingTabHeading(String view) {
        new WebDriverWait(driver, 3).until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOfAllElements(tabHeadings)));
        clickOnElementJS(driver, getSectionToNavigate(tabHeadings, view, "title"));
        new WebDriverWait(driver, 3).until(ExpectedConditions.textToBePresentInElement(sectionNavigatedTo, view));
        return sectionNavigatedTo.getAttribute("textContent");
    }

    public void clickOnLearnMore() {
        clickOnElement(temp.findElement(By.cssSelector(learnOrSeeMoreCta)));
    }

    public String clickOnSpace(String spaceName) throws InterruptedException {
        try{
            new WebDriverWait(driver, 1).until(ExpectedConditions.elementToBeClickable(closeCTA));
            clickOnElement(closeCTA);}
        catch (Exception e){}
        final String[] modalHeading = new String[1];
        navigateUsingJSToAnElementEnd(driver, temp.findElement(By.className(spacesListSection)));
        spaces.stream().filter(s -> s.getText().equalsIgnoreCase(spaceName)).forEach(s -> {
            new WebDriverWait(driver, 3).until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(s)));
            clickOnElementJS(driver, s);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            modalHeading[0] = modalOpen.getAttribute("textContent");
        });
        clickOnElement(closeCTA);
        return modalHeading[0];
    }

    public String onModal() {
        return new WebDriverWait(driver, 3).until(ExpectedConditions.visibilityOf(modalOpen)).getText();
    }

    public boolean verifyAllLinksAreValid() throws Exception {
        final boolean[] linksValid = {true};
        String attName = "href";
        buyingProcessLinks.stream().filter(s -> !s.getAttribute(attName).toLowerCase().contains("google") &&
                !s.getAttribute(attName).toLowerCase().contains("tel")).forEach(s -> {
            try {
                linksValid[0] = linksValid[0] && makeUrlConnection(s) == (s.getAttribute(attName).contains("instagram") ? 405 : 200);
            } catch (IOException e) {
                LOG.error(e);
                e.printStackTrace();
            }
        });
        return linksValid[0];
    }
}

