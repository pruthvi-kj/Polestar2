package Polestar.Pages;

import Utils.CommonMethods;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BuyingProcess extends CommonMethods {
    private static final Logger LOG = LogManager.getLogger(BuyingProcess.class);
    private static final String learnOrSeeMoreCta = "div[class='css-ly8tcg']";
    private static final String spacesListSection = "css-1nfgff6";
    private static WebElement temp;

    private static RemoteWebDriver driver;
    private Map<String, WebElement> mapping = new HashMap<>();

    @FindBy(css = "div[class*='optanon-alert-box-wrapper']")
    private WebElement cookieBar;
    @FindBy(xpath = "//button[@class='optanon-allow-all accept-cookies-button']")
    private WebElement acceptCookies;
    @FindBy(className = "css-x9njz8")
    private WebElement tabHeadingView;
    @FindBy(className = "css-d29to2")
    private List<WebElement> tabHeadings;
    @FindBy(css = ".css-u6if8s button[class='css-47lmvx']>span")
    private WebElement sectionNavigatedTo;
    @FindBy(css = "[data-name]")
    private List<WebElement> sections;
    @FindBy(className = "css-4zfwrn")
    private WebElement modalOpen;
    @FindBy(className = "css-1qbfuld")
    private List<WebElement> spaces;
    @FindBy(css = "[class='css-yp9swi'] [href]")
    private List<WebElement> buyingProcessLinks;
    @FindBy(className = "css-kchll9")
    private WebElement closeCTA;
    @FindBy(className = "css-1nfgff6")
    private WebElement buyingProcessSpacesSection;

    public BuyingProcess(WebDriver driver) {

        this.driver = (RemoteWebDriver) driver;
        PageFactory.initElements(driver, this);
        driver.switchTo().defaultContent();
        try {
            ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0)");
            WebDriverWait wait = new WebDriverWait(driver, 3);
            if (cookieBar.getAttribute("style").equalsIgnoreCase("bottom: 0px;")) {
                if (wait.until(ExpectedConditions.visibilityOf(cookieBar)).isDisplayed())
                    wait.until(ExpectedConditions.elementToBeClickable(acceptCookies));
            while (acceptCookies.isDisplayed())
                clickOnElementJS(driver, acceptCookies);
        }
        } catch(Exception e){
            throw new RuntimeException(e);
        }
        
        mapping.put("TAB HEADINGS", tabHeadingView);
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
          new WebDriverWait(driver, 1).until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(closeCTA))).click();
        }
        catch (Exception e){
        }
        final String[] modalHeading = new String[1];
        navigateUsingJSToAnElementEnd(driver,buyingProcessSpacesSection);
        List<WebElement>ele= spaces.stream().filter(s -> s.getText().equalsIgnoreCase(spaceName)).collect(Collectors.toList());
        ele.forEach(s -> {
            new WebDriverWait(driver, 1).until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(s)));
            clickOnElementJS(driver, s);
            modalHeading[0] = modalOpen.getAttribute("textContent");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {}
        });

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
                LOG.error("error in verifyAllLinksAreValid"+e.getCause());
            }
        });
        return linksValid[0];
    }
}

