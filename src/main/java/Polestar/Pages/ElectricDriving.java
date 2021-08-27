package Polestar.Pages;

import Polestar.DataMembers.ChargeData;
import Polestar.DataMembers.RangeData;
import UtilsMain.CommonMethods;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import static UtilsMain.InitiateDriver.getWebDriverWait;

public class ElectricDriving extends CommonMethods {
    private static final Logger LOG = LogManager.getLogger(ElectricDriving.class);
    private static final String learnOrSeeMoreCta = "div[class='css-ly8tcg']";
    private static final String chargingSliderComponent = "[class='css-9lvjku']";
    private static final String chargerTypeId = "[data-testid]";
    private static final String chargerTypeIdText = "div:nth-child(2)>p:nth-child(1)";
    private static final String estimatedChargeTime = "css-111c08i";
    private static final String startEndChangePercentage = "css-yv1aru";
    private static final String sliderComponent = "[class='css-1nqf9b0']";
    private static final String startEndSliderId = "[class='css-1nqf9b0']>div>div";

    public static WebElement temp;
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
    @FindBy(css = "button[class='css-47lmvx']>span")
    private WebElement sectionNavigatedTo;
    @FindBy(css = "[data-name]")
    private List<WebElement> sections;
    @FindBy(css = "[class='css-wkb1an'] h1,[class='css-bfldvu']>h1")
    private WebElement modalOpen;
    @FindBy(className = "css-1qbfuld")
    private List<WebElement> spaces;
    @FindBy(css = "[class='css-yp9swi'] [href]")
    private List<WebElement> electricDrivingLinks;
    @FindBy(css = ".css-kchll9 ,.css-12lf4sx")
    private WebElement closeCTA;
    @FindBy(className = "css-5eui9h")
    private List<WebElement> chargingModalHeadings;
    @FindBy(css = "div[data-name]")
    private List<WebElement> chargingModalSections;
    @FindBy(className = "css-bchlgi")
    private WebElement rangeCalcComp;
    @FindBy(className = "css-1k4t3n2")
    private WebElement range;
    @FindBy(className = "css-cssveg")
    private WebElement heroImage;
    @FindBy(css = "[class='css-12qn6e6']>div:nth-child(3)")
    private WebElement rangeSlider;
    @FindBy(css = "[class='css-12qn6e6']>div:nth-child(3) p[class='css-1algwbp']")
    private WebElement rangeMiles;
    @FindBy(css = ".css-weza6i>div:nth-child(1) .css-692aln")
    private WebElement savingsAmount;
    @FindBy(css = "[class='css-12qn6e6']>div:nth-child(3) span[class='css-1408r7f']")
    private WebElement rangeChargePercentage;
    @FindBy(css = "[class='css-12qn6e6']>div:nth-child(3) div[class='css-15ym8zq']>span[class='css-1algwbp']")
    private WebElement rangeCharge;
    @FindBy(className = "css-1crvpkm")
    private WebElement stateNameId;
    @FindBy(className = "css-18rtpmq")
    private WebElement stateSelectionClearBtn;
    @FindBy(css = "[class='css-1nlu2c6']")
    private WebElement headerMenu;

    public ElectricDriving(WebDriver driver) {

        this.driver = (RemoteWebDriver) driver;
        PageFactory.initElements(driver, this);
        handleCookie(acceptCookies, cookieBar);
        mapping.put("TAB HEADINGS", tabHeadingView);
        mapping.put("HEADER MENU", headerMenu);

    }

    public void navigateToView(String view) throws InterruptedException {
        try {
            getWebDriverWait().until(ExpectedConditions.elementToBeClickable(closeCTA));
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

    public String onModal() {
        return getWebDriverWait().until(ExpectedConditions.visibilityOf(modalOpen)).getText();
    }

    public void getChargingModalSection(String chargingSectionName) throws InterruptedException {
        chargingModalHeadings.stream().filter(s -> s.getAttribute("title").equalsIgnoreCase(chargingSectionName))
                .forEach(s -> clickOnElementJS(driver, s));
        Thread.sleep(1000);

    }

    public void clickOnChargerType(String chargingSectionName, double chargerType) throws InterruptedException {
        temp = getSectionToNavigate(chargingModalSections, chargingSectionName, "data-name");
        assert temp != null;
        navigateUsingJSToAnElementEnd(driver, temp.findElement(By.cssSelector(chargingSliderComponent)));
        temp.findElements(By.cssSelector(chargerTypeId)).stream()
                .filter(s -> s.findElement(By.cssSelector(chargerTypeIdText)).getText()
                        .contains(Double.toString(chargerType))).forEach(s -> clickOnElementJS(driver, s));
        Thread.sleep(1000);
    }

    public ChargeData getChargeDuration() {
        List<String> chargeData = new ArrayList<>();
        ChargeData cd = new ChargeData();

        cd.estimatedChargeTime = temp.findElement(By.className(estimatedChargeTime)).getText();
        temp.findElements(By.className(startEndChangePercentage)).forEach(s -> chargeData.add(s.getText()));
        cd.startChargePercentage = Integer.parseInt(chargeData.get(0));
        cd.endChargePercentage = Integer.parseInt(chargeData.get(1));
        return cd;
    }

    public void updateSliderPosition(int startChargePercentage, int endChargePercentage) throws InterruptedException {
        int getWidth = temp.findElement(By.cssSelector(sliderComponent)).getRect().getWidth();
        List<WebElement> slider = temp.findElements(By.cssSelector(startEndSliderId));
        Actions a = new Actions(driver);
        a.moveToElement(slider.get(0), startChargePercentage, 0).click().build().perform();
        a.moveToElement(slider.get(1), endChargePercentage, 0).click().build().perform();
        Thread.sleep(1000);
    }

    public void updateSliderPosition(int slideX) throws InterruptedException {
        Actions a = new Actions(driver);
        navigateUsingJSToAnElementEnd(driver, rangeCalcComp);
        a.clickAndHold(rangeSlider).dragAndDropBy(rangeSlider, slideX, 0).build().perform();
    }

    public RangeData calculateMiles() {
        return new RangeData(Integer.parseInt(rangeCharge.getText()), Integer.parseInt(rangeChargePercentage.getText()),
                Integer.parseInt(rangeMiles.getText()));
    }

    public void selectState(String stateName) {
        stateNameId.sendKeys(stateName);
        stateNameId.sendKeys(Keys.ENTER);
        new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(stateSelectionClearBtn));
    }

    public Long getSavingsValue() {
        return Long.parseLong(savingsAmount.getAttribute("textContent").replaceAll("[^0-9]", ""));
    }

    public boolean verifyAllLinksAreValid() {
        AtomicBoolean linksValid = new AtomicBoolean();
        linksValid.set(true);
        String attName = "href";
        electricDrivingLinks.stream().filter(s -> !s.getAttribute(attName).toLowerCase().contains("google") &&
                !s.getAttribute(attName).toLowerCase().contains("tel")).forEach(s -> {
            try {
                linksValid.set(linksValid.get() && makeUrlConnection(s) == (s.getAttribute(attName).contains("instagram") ? 405 : 200));
            } catch (IOException e) {
                LOG.error(e.getCause());
            }
        });
        return linksValid.get();
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

