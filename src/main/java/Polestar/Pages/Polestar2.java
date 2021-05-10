package Polestar.Pages;

import Polestar.DataMembers.ChargeData;
import Polestar.DataMembers.FuelPrices;
import Polestar.DataMembers.RangeData;
import Polestar.Utils.commonMethods;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.TestInitialization;
import utils.TestReport;

import java.io.IOException;
import java.time.Duration;
import java.util.NoSuchElementException;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;


public class Polestar2 extends commonMethods {
    private static final Logger LOG = LogManager.getLogger(Polestar2.class);
    private static final String sliderComponent = "[class='css-1nqf9b0']";
    private static final String startEndSliderId = "[class='css-1nqf9b0']>div>div";
    private static final String savingsValueComponent = "[class='css-klf5ss']";
    private static final String yearMonthSelector = "css-u6if8s";
    private static final String costForMiles = "[class='css-185z86n']>label";
    private static final String estimatedFuelSavings = "css-xjcxgn";
    private static final String estimatedChargeTime = "css-15bk8jn";
    private static final String startEndChangePercentage = "css-yv1aru";
    private static final String chargingSliderComponent = "[class='css-9lvjku']";
    private static final String chargerTypeId = "[data-testid]";
    private static final String chargerTypeIdText = "div:nth-child(2)>p:nth-child(1)";
    private static final String learnOrSeeMoreCta = "div[class='css-ly8tcg']";
    private static XSSFSheet sheet;
    private static RemoteWebDriver driver;
    private static TestReport testReport;
    private static WebElement temp;
    Map<String, WebElement> mapping = new HashMap<>();
    @FindBy(xpath = "//button[@class='optanon-allow-all accept-cookies-button']")
    private WebElement acceptCookies;
    @FindBy(className = "css-1ink1h8")
    private WebElement keyStatsHighlightsAPR;
    @FindBy(className = "css-1ezcku6")
    private List<WebElement> keyStatsHighlights;
    @FindBy(className = "css-3ycsxw")
    private List<WebElement> keyStatsHighlightsLease;
    @FindBy(xpath = "//p[@class='css-92kwuh' and text()='Panoramic glass roof']/..//div[text()='See More']")
    private WebElement panoramicGlassRoofSeeMore;
    @FindBy(xpath = "//p[@class='css-92kwuh' and text()='Frameless mirrors']/..//div[text()='See More']")
    private WebElement seeMoreFrameless;
    @FindBy(xpath = "//p[@class='css-92kwuh' and text()='Pixel LED lights']/..//div[text()='See More']")
    private WebElement seeMoreLEDLights;
    @FindBy(className = "css-138i4qw")
    private WebElement exteriorView;
    @FindBy(className = "css-1sb6ikf")
    private WebElement closeCTA;
    @FindBy(className = "css-j07xvw")
    private WebElement orderNowCta;
    @FindBy(className = "css-zgmw7k")
    private WebElement heroUnit;
    @FindBy(className = "css-azo2pw")
    private WebElement bookATestDriveHU;
    @FindBy(css = "[class='css-1k4t3n2']>div:nth-child(3)")
    private WebElement rangeSlider;
    @FindBy(css = "[class='css-1k4t3n2']>div:nth-child(3) span[class='css-yv1aru']")
    private WebElement rangeChargePercentage;
    @FindBy(css = "[class='css-1k4t3n2']>div:nth-child(3) div[class='css-aaonyv']>span[class='css-1algwbp']")
    private WebElement rangeCharge;
    @FindBy(css = "[class='css-1k4t3n2']>div:nth-child(3) p[class='css-1algwbp']")
    private WebElement rangeMiles;
    @FindBy(className = "css-bchlgi")
    private WebElement rangeCalcComp;
    @FindBy(className = "css-1k4t3n2")
    private WebElement range;
    @FindBy(css = "div[data-name], section[data-name]")
    private List<WebElement> sections;
    @FindBy(css = "div[class*='optanon-alert-box-wrapper']")
    private WebElement cookieBar;
    @FindBy(css = "div[class='css-1kvol9r']")
    private WebElement modalOpen;
    @FindBy(className = "css-5eui9h")
    private List<WebElement> modalSections;
    @FindBy(css = "[class='css-2dq1z6'] [class='css-113edzk']")
    private List<WebElement> designIntroFeatureList;
    @FindBy(css = "div[data-name]")
    private List<WebElement> chargingModalSections;
    @FindBy(className = "css-5eui9h")
    private List<WebElement> chargingModalHeadings;
    @FindBy(className = "css-1crvpkm")
    private WebElement stateNameId;
    @FindBy(className = "css-18rtpmq")
    private WebElement stateSelectionClearBtn;
    @FindBy(className = "css-147hag1")
    private List<WebElement> navBarIds;
    @FindBy(className = "css-113edzk")
    private WebElement navBar;
    @FindBy(className = "css-1gprnpu")
    private WebElement selectedNavBar;
    @FindBy(css = ".css-u6if8s>button")
    private List<WebElement> selectedTabBar;
    @FindBy(css = "[class='css-yp9swi'] [href]")
    private List<WebElement> polestar2Links;


    public Polestar2(WebDriver driver) throws InterruptedException {
        this.driver = (RemoteWebDriver) driver;
        PageFactory.initElements(driver, this);
        driver.switchTo().defaultContent();
        try {
            try {
                Wait<WebDriver> waitF = new FluentWait<>(driver)
                        .withTimeout(Duration.ofSeconds(5))
                        .pollingEvery(Duration.ofSeconds(1))
                        .ignoring(NoSuchElementException.class);
                waitF.until(localDriver -> localDriver.findElement(By.className("css-113edzk")));
                ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0)");
                WebDriverWait wait = new WebDriverWait(driver, 5);
                wait.until(ExpectedConditions.visibilityOf(cookieBar));
                wait.until(ExpectedConditions.elementToBeClickable(acceptCookies));
            } catch (Exception e) {
                LOG.error(e.getMessage());
                LOG.error(e.getStackTrace());
            }
            while (acceptCookies.isDisplayed()) {
                clickOnElementJS(driver, acceptCookies);
            }
        } catch (Exception e) {
            LOG.error(e.getMessage());
            LOG.error(e.getStackTrace());
        }
        mapping.put("EXTERIOR PDP", exteriorView);
        mapping.put("PANORAMIC GLASS ROOF SEE MORE", panoramicGlassRoofSeeMore);
        mapping.put("ORDER NOW", orderNowCta);
        mapping.put("BOOK A TEST DRIVE", bookATestDriveHU);
        mapping.put("RANGE CALCULATOR PDP", rangeCalcComp);
        testReport = TestInitialization.getInstance();
    }

    public void getChargingModalSection(String chargingSectionName) throws InterruptedException {
        chargingModalHeadings.stream().filter(s -> s.getAttribute("title").equalsIgnoreCase(chargingSectionName))
                .forEach(s -> clickOnElementJS(driver, s));
        Thread.sleep(1000);

    }

    public void updateSliderPosition(int startChargePercentage, int endChargePercentage) throws InterruptedException {
        int getWidth = temp.findElement(By.cssSelector(sliderComponent)).getRect().getWidth();
        List<WebElement> slider = temp.findElements(By.cssSelector(startEndSliderId));
        Actions a = new Actions(driver);
        a.moveToElement(slider.get(0), startChargePercentage, 0).click().build().perform();
        a.moveToElement(slider.get(1), endChargePercentage, 0).click().build().perform();
        Thread.sleep(1000);
    }

    public FuelPrices getSavingsValue(String chargingSectionName) throws InterruptedException {
        Thread.sleep(3000);
        List<Long> values = new ArrayList<>();
        temp = getSectionToNavigate(chargingModalSections, chargingSectionName, "data-name").findElement(By.cssSelector(savingsValueComponent));
        temp.findElements(By.className(yearMonthSelector)).forEach(s -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            temp.findElements(By.cssSelector(costForMiles)).forEach(p -> {
                scrollToElementUsingActionClass(driver, p);
                values.add(Long.parseLong(p.getText().split(" ")[1]));
            });
            values.add(Long.parseLong(temp.findElement(By.className(estimatedFuelSavings)).getText().split(" ")[1]));
            clickOnElement(s);
        });

        return new FuelPrices(values.get(0), values.get(1), values.get(2), values.get(3), values.get(4), values.get(5));
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

    public void clickOnChargerType(String chargingSectionName, double chargerType) throws InterruptedException {
        temp = getSectionToNavigate(chargingModalSections, chargingSectionName, "data-name");
        assert temp != null;
        navigateUsingJSToAnElementEnd(driver, temp.findElement(By.cssSelector(chargingSliderComponent)));
        temp.findElements(By.cssSelector(chargerTypeId)).stream()
                .filter(s -> s.findElement(By.cssSelector(chargerTypeIdText)).getText()
                        .contains(Double.toString(chargerType))).forEach(s -> clickOnElementJS(driver, s));
        Thread.sleep(1000);
    }

    public void clickOnLearnMore() {
        clickOnElement(temp.findElement(By.cssSelector(learnOrSeeMoreCta)));
    }

    public void clickOnSeeMore(String feature) {
        designIntroFeatureList.stream().filter(s -> s.findElement(By.cssSelector("p")).getText().equalsIgnoreCase(feature)).
                forEach(s -> clickOnElement(s.findElement(By.cssSelector(learnOrSeeMoreCta))));
    }

    public void clickOnTheLink(String linkText) {
        clickOnElement(mapping.get(linkText.toUpperCase()));
    }

    public void navigateToView(String view) throws InterruptedException {
        try{
        new WebDriverWait(driver, 1).until(ExpectedConditions.elementToBeClickable(closeCTA));
        clickOnElement(closeCTA);}
        catch (Exception e){}
        Wait<RemoteWebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(5))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoring(NoSuchElementException.class);
        wait.until((Function<WebDriver, WebElement>) driver -> sections.get(10));
        temp = getSectionToNavigate(sections, view, "data-name");
        navigateUsingJSToAnElementStart(driver, temp);
    }

    public String getViewName(String view) {
        return mapping.get(view.toUpperCase()).getText();
    }


    public void readData(String path, String sheetName) {
        sheet = getSheet(path, sheetName);
    }

    public void extractDataOfElements() throws IOException {
        Iterator<Row> rows = sheet.iterator();
        Row firstRow = rows.next();
        //getting the index for writing data
        int textIndex = getColumnIndex(firstRow, "Actual Text");
        int fontIndex = getColumnIndex(firstRow, "Actual Font Family");
        int colourIndex = getColumnIndex(firstRow, "Actual Font Colour");
        int sizeIndex = getColumnIndex(firstRow, "Actual Font Size");
        int xpathIndex = getColumnIndex(firstRow, "className");

        while (rows.hasNext()) {
            Row r = rows.next();
            String a = getCellValue(r, xpathIndex);
            WebElement elementToVerify;
            try {
                assert a != null;
                elementToVerify = driver.findElement(By.xpath(a));
            } catch (Exception e) {
                LOG.error(e.getCause());
                throw e;
            }
            scrollToElementUsingActionClass(driver, elementToVerify);
            if (elementToVerify.getText().equalsIgnoreCase(a)) {
                r.createCell(textIndex).setCellValue(elementToVerify.getText());
                r.createCell(fontIndex).setCellValue(elementToVerify.getCssValue("font-family"));
                r.createCell(sizeIndex).setCellValue(elementToVerify.getCssValue("font-size"));
                r.createCell(colourIndex).setCellValue(Color.fromString(elementToVerify.getCssValue("color")).asHex());
            } else {
                r.createCell(textIndex).setCellValue("Element Not Found");
            }
        }
        closeInputStream();
    }

    public void writeData(String path) throws IOException {
        writeToExcel(path);
    }

    public ArrayList<String> extractCalloutFromExcel() throws IOException {
        ArrayList<String> calloutExpected = new ArrayList<>();
        Iterator<Row> rows = sheet.iterator();
        Row firstRow = rows.next();
        int index = getColumnIndex(firstRow, "Callouts");
        //read data from excel and put it in an array list

        while (rows.hasNext()) {
            Row r = rows.next();
            calloutExpected.add(getCellValue(r, index));
        }
        closeInputStream();
        return calloutExpected;
    }

    public ArrayList<String> getTextOfElements(String section) {
        ArrayList<String> calloutActual = new ArrayList<>();
        List<WebElement> elementToVerify;
        //read data from UI and put it in an array list
        try {
            elementToVerify = driver.findElements(By.xpath("//p[text()='" +
                    section + "']/../..//p[@data-testid]"));
        } catch (Exception e) {
            LOG.error(e.getStackTrace());
            throw e;
        }
        scrollToElementUsingActionClass(driver, elementToVerify.get(0));
        for (WebElement e : elementToVerify) {
            calloutActual.add(e.getText());
        }
        return calloutActual;
    }

    public void updateSliderPosition(int slideX) throws InterruptedException {
        Actions a = new Actions(driver);
        navigateUsingJSToAnElementEnd(driver, rangeCalcComp);
        int width = range.getRect().getWidth();
        if (slideX <= width) {
            a.clickAndHold(rangeSlider).dragAndDropBy(rangeSlider, slideX, 0).build().perform();
        }
    }

    public RangeData calculateMiles() {
        return new RangeData(Integer.parseInt(rangeCharge.getText()), Integer.parseInt(rangeChargePercentage.getText()),
                Integer.parseInt(rangeMiles.getText()));
    }

    public String onModal() {
        String modalName = new WebDriverWait(driver, 3).until(ExpectedConditions.visibilityOf(modalOpen)).getText();
        return modalName;
    }

    public boolean ifSectionClickable() {
        for (int i = (modalSections.size() - 1); i >= 0; i--) {
            try {
                new WebDriverWait(driver, 2).until(ExpectedConditions.elementToBeClickable(modalSections.get(i)));
                modalSections.get(i).click();
                System.out.println(modalSections.get(i).getAttribute("textContent") + "section");
                int finalI = i;
                selectedTabBar.stream().filter(s -> s.findElement(By.cssSelector("span")).getAttribute("textContent")
                        .equalsIgnoreCase(modalSections.get(finalI).getAttribute("textContent"))).forEach(s ->
                        new WebDriverWait(driver, 3).until(ExpectedConditions.attributeContains(s, "aria-selected", "true")));
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }

    public void selectState(String stateName) {
        stateNameId.sendKeys(stateName);
        stateNameId.sendKeys(Keys.ENTER);
        new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(stateSelectionClearBtn));
    }

    public String navigateToSectionUsingNavBar(String sectionName) throws InterruptedException {
        navBarIds.stream().filter(s -> s.getAttribute("data-label").equalsIgnoreCase(sectionName)).forEach(s -> {
            new Actions(driver).moveToElement(navBar).build().perform();
            new Actions(driver).moveToElement(s).click().build().perform();
        });
        Thread.sleep(2000);
        return selectedNavBar.getText();
    }

    public boolean verifyAllLinksAreValid() {
        AtomicBoolean linksValid = new AtomicBoolean();
        linksValid.set(true);
        String attName = "href";
        polestar2Links.stream().filter(s -> !s.getAttribute(attName).toLowerCase().contains("google") &&
                !s.getAttribute(attName).toLowerCase().contains("tel")).forEach(s -> {
            try {
                linksValid.set(linksValid.get() && makeUrlConnection(s) == (s.getAttribute(attName).contains("instagram") ? 405 : 200));
            } catch (IOException e) {
                LOG.error(e);
                e.printStackTrace();
            }
        });
        return linksValid.get();
    }
}

