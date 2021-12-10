package Polestar.Pages;

import UtilsMain.CommonMethods;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
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

import static UtilsMain.InitiateDriver.getWebDriverWait;

public class BuyingProcess extends CommonMethods {
    private static final Logger LOG = LogManager.getLogger(BuyingProcess.class);
    private static final String learnOrSeeMoreCta = "div[class='css-ly8tcg']";
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
    @FindBy(css = "[class='css-1nlu2c6']")
    private WebElement headerMenu;

    public BuyingProcess(WebDriver driver) {

        this.driver = (RemoteWebDriver) driver;
        PageFactory.initElements(driver, this);
        handleCookie(acceptCookies, cookieBar);
        mapping.put("TAB HEADINGS", tabHeadingView);
        mapping.put("HEADER MENU", headerMenu);

    }

    public void navigateToView(String view) throws InterruptedException {
        try {
            new WebDriverWait(driver, 1).until(ExpectedConditions.elementToBeClickable(closeCTA));
            clickOnElement(closeCTA);
        } catch (Exception e) {
        }
        temp = mapping.containsKey(view.toUpperCase()) ? mapping.get(view.toUpperCase()) : getSectionToNavigate(sections, view, "data-name");
        navigateUsingJSToAnElementStart(driver, temp);
    }

    public String navigateToSectionUsingTabHeading(String view) {
        getWebDriverWait().until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOfAllElements(tabHeadings)));
        clickOnElementJS(driver, getSectionToNavigate(tabHeadings, view, "title"));
        getWebDriverWait().until(ExpectedConditions.textToBePresentInElement(sectionNavigatedTo, view));
        return sectionNavigatedTo.getAttribute("textContent");
    }

    public void clickOnLearnMore() {
        clickOnElement(temp.findElement(By.cssSelector(learnOrSeeMoreCta)));
    }

    public String clickOnSpace(String spaceName) throws InterruptedException {
        try {
            getWebDriverWait().until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(closeCTA))).click();
        } catch (Exception e) {
        }
        final String[] modalHeading = new String[1];
        getWebDriverWait().until(ExpectedConditions.visibilityOf(buyingProcessSpacesSection));
        navigateUsingJSToAnElementEnd(driver, buyingProcessSpacesSection);
        List<WebElement> ele = spaces.stream().filter(s -> s.getText().equalsIgnoreCase(spaceName)).collect(Collectors.toList());
        ele.forEach(s -> {
            getWebDriverWait().until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(s)));
            clickOnElementJS(driver, s);
            modalHeading[0] = modalOpen.getAttribute("textContent");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
        });

        return modalHeading[0];
    }

    public String onModal() {
        return getWebDriverWait().until(ExpectedConditions.visibilityOf(modalOpen)).getText();
    }

    public boolean verifyAllLinksAreValid() throws Exception {
        final boolean[] linksValid = {true};
        String attName = "href";
        buyingProcessLinks.stream().filter(s -> !s.getAttribute(attName).toLowerCase().contains("google") &&
                !s.getAttribute(attName).toLowerCase().contains("tel")).forEach(s -> {
            try {
                linksValid[0] = linksValid[0] && makeUrlConnection(s) == (s.getAttribute(attName).contains("instagram") ? 405 : 200);
            } catch (IOException e) {
                LOG.error("error in verifyAllLinksAreValid" + e.getCause());
            }
        });
        return linksValid[0];
    }

    public void clickOnTheLink(String linkText) throws InterruptedException {
        try {
            clickOnElement(mapping.get(linkText.toUpperCase()));
        } catch (Exception e) {
            navigateUsingJSToAnElementEnd(driver, mapping.get(linkText.toUpperCase()), 0, 100);
            clickOnElement(mapping.get(linkText.toUpperCase()));
        }
    }
}

