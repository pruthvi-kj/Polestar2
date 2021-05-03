package Polestar.Pages;

import Polestar.Utils.commonMethods;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServiceAndAssistance extends commonMethods {
    private static WebDriver driver;
    private static final Logger LOG = LogManager.getLogger(ServiceAndAssistance.class);
    public static WebElement temp;
    Map<String, WebElement> mapping = new HashMap<>();

    private static final String learnOrSeeMoreCta ="div[class='css-ly8tcg']";
    private static final String servicePointsListSection="css-1nb4a83";


    @FindBy(css = "div[class*='optanon-alert-box-wrapper']")
    private WebElement cookieBar;
    @FindBy(xpath = "//button[@class='optanon-allow-all accept-cookies-button']")
    private WebElement acceptCookies;
    @FindBy(css = "div[class='css-13j3osp']")
    private WebElement tabHeadingView;
    @FindBy(className = "css-d29to2")
    private List<WebElement> tabHeadings;
    @FindBy(css = ".css-13j3osp button[class='css-47lmvx']")
    private WebElement sectionNavigatedTo;
    @FindBy(css = "[data-name]")
    private List<WebElement> sections;
    @FindBy(className="css-t8cify")
    private WebElement modalOpen;
    @FindBy(className="css-5x9gvo")
    private List<WebElement> servicePoints;



    public ServiceAndAssistance(WebDriver driver) throws InterruptedException {

        this.driver = driver;
        PageFactory.initElements(driver, this);
        driver.switchTo().defaultContent();
        try {
            ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0)");
            WebDriverWait wait = new WebDriverWait(driver, 5);
            wait.until(ExpectedConditions.visibilityOf(cookieBar));
            wait.until(ExpectedConditions.elementToBeClickable(acceptCookies));
        } catch (Exception e) {
            LOG.error(e.getMessage());
            LOG.error(e.getStackTrace().toString());
        }
        while (acceptCookies.isDisplayed())
            clickOnElementJS(driver, acceptCookies);
        mapping.put("TAB HEADINGS", tabHeadingView);
    }

    public void navigateToView(String view) throws InterruptedException {
        temp=mapping.containsKey(view.toUpperCase()) ? mapping.get(view.toUpperCase()):getSectionToNavigate(sections,view,"data-name");
        navigateUsingJSToAnElement(driver,temp);
    }

    public String navigateToSectionUsingTabHeading(String view) throws InterruptedException {
        clickOnElementJS(driver,getSectionToNavigate(tabHeadings,view,"title"));
        new WebDriverWait(driver,3).until(ExpectedConditions.textToBePresentInElement(sectionNavigatedTo,view));
        return sectionNavigatedTo.getText();

    }

    public void clickOnLearnMore() throws InterruptedException {
        clickOnElement(temp.findElement(By.cssSelector(learnOrSeeMoreCta)));
    }

    public void clickOnServicePoint(String servicePointName) throws InterruptedException {
        scrollToElementUsingActionClass(driver,temp.findElement(By.className(servicePointsListSection)));
        servicePoints.stream().filter(s->s.getText().equalsIgnoreCase(servicePointName)).forEach(s->clickOnElement(s));
    }

    public String onModal(){
        return new WebDriverWait(driver, 3).until(ExpectedConditions.visibilityOf(modalOpen)).getText();
    }


}
