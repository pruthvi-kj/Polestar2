package Polestar.Pages;

import Polestar.DataMembers.RangeData;
import Polestar.Utils.commonMethods;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
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
import java.util.*;
import java.util.function.Function;


public class Polestar2 extends commonMethods{
    private static final Logger LOG = LogManager.getLogger(Polestar2.class);

    private static XSSFSheet sheet;
    private static WebDriver driver;

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
    @FindBy(className = "css-j07xvw")
    private WebElement orderNowCta;
    @FindBy(className = "css-zgmw7k")
    private WebElement heroUnit;
    @FindBy(className = "css-1bs17j1")
    private WebElement bookATestDriveHU;
    @FindBy(css ="[class='css-1k4t3n2']>div:nth-child(3)")
    private WebElement rangeSlider;
    @FindBy(css="[class='css-1k4t3n2']>div:nth-child(3) span[class='css-yv1aru']")
    private WebElement rangeChargePercentage;
    @FindBy(css="[class='css-1k4t3n2']>div:nth-child(3) p[class='css-1algwbp']")
    private WebElement rangeMiles;
    @FindBy(css="[class='css-1k4t3n2']>div:nth-child(3) div[class='css-aaonyv']>span[class='css-1algwbp']")
    private WebElement rangeCharge;
    @FindBy(className = "css-bchlgi")
    private WebElement rangeCalcComp;
    @FindBy(className = "css-1k4t3n2")
    private WebElement range;
    @FindBy(css = "div[data-name], section[data-name]")
    private List<WebElement> sections;
    @FindBy(css = "div[class*='optanon-alert-box-wrapper']")
    private WebElement cookieBar;
    @FindBy(css="div[class='css-1kvol9r']")
    private WebElement modalOpen;
    @FindBy(className = "css-5eui9h")
    private List<WebElement> modalSections;
    @FindBy(css="[class='css-2dq1z6'] [class='css-113edzk']")
    private List<WebElement> designIntroFeatureList;
    @FindBy(css = "div[data-name='Public charging'] [class='css-yv1aru']")
    private List<WebElement> percentageCharge;
    @FindBy(css = "div[data-name='Home charging'] [class='css-8deao8'] div:nth-child(2)>p")
    private  List<WebElement> chargerType;
    @FindBy(css = "div[data-name]")
    private List<WebElement> chargingModalSections;
    @FindBy(className = "css-5eui9h")
    private List<WebElement> chargingModalHeadings;
    //time="div[data-name='Home charging'] [class='css-15bk8jn']"
    private static TestReport testReport;
    private static WebElement temp;


    public Polestar2(WebDriver driver) throws InterruptedException  {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        driver.switchTo().defaultContent();
        try {
            try {
                Wait<WebDriver> waitF = new FluentWait<WebDriver>(driver)
                        .withTimeout(Duration.ofSeconds(10))
                        .pollingEvery(Duration.ofSeconds(1))
                        .ignoring(NoSuchElementException.class);
                WebElement foo = waitF.until(new Function<WebDriver, WebElement>() {
                    public WebElement apply(WebDriver driver) {
                        return driver.findElement(By.className("css-113edzk"));
                    }
                });
                ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0)");
                WebDriverWait wait = new WebDriverWait(driver, 15);
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
        mapping.put("EXTERIOR PDP", exteriorView);
        mapping.put("PANORAMIC GLASS ROOF SEE MORE", panoramicGlassRoofSeeMore);
        mapping.put("ORDER NOW", orderNowCta);
        mapping.put("BOOK A TEST DRIVE", bookATestDriveHU);
        mapping.put("RANGE CALCULATOR PDP", rangeCalcComp);
        testReport= TestInitialization.getInstance();
    }

    public void getChargingModalSection(String chargingSectionName) throws InterruptedException {
        chargingModalHeadings.stream().filter(s-> s.getAttribute("title").equalsIgnoreCase(chargingSectionName) )
                .forEach(s-> clickOnElementJS(driver,s));
        Thread.sleep(1000);

    }

    public void updateSliderPosition(int startChargePercentage, int endChargePercentage) throws InterruptedException {
        int getWidth=temp.findElement(By.cssSelector("[class='css-1nqf9b0']")).getRect().getWidth();
        List<WebElement> slider=temp.findElements(By.cssSelector("[class='css-1nqf9b0']>div>div"));
        Actions a= new Actions(driver);
        if((endChargePercentage <= getWidth) && (startChargePercentage < endChargePercentage)){
            a.moveToElement(slider.get(0),-startChargePercentage,0).click().build().perform();
            a.moveToElement(slider.get(1),-endChargePercentage,0).click().build().perform();
        }
        Thread.sleep(1000);
    }

    public HashMap<String,String> getSavingsValue(String chargingSectionName) throws InterruptedException {
        HashMap<String,String> savingsValue= new HashMap<>();
        List<String> tempA= new ArrayList<>();
        List<String> tempB= new ArrayList<>();

        temp= getSectionToNavigate(chargingModalSections,chargingSectionName).findElement(By.cssSelector("[class='css-klf5ss']"));
        temp.findElements(By.className("css-u6if8s")).stream().forEach(s-> {
            clickOnElement(s);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            temp.findElements(By.cssSelector("[class='css-185z86n']>span")).stream().forEach(p-> tempA.add("Actual "+s.getText()+" "+p.getText()));
            temp.findElements(By.cssSelector("[class='css-185z86n']>label")).stream().forEach(p-> tempB.add(p.getText()));
        });
        savingsValue.put("Actual Estimated fuel savings",temp.findElement(By.className("css-xjcxgn")).getText());
        for (int i = 0; i < tempA.size(); i++) {
            savingsValue.put(tempA.get(i),tempB.get(i));
        }
        return savingsValue;
    }

    public List<String> getChargeDuration(){
        List<String> chargeData = new ArrayList<>();
        chargeData.add(temp.findElement(By.className("css-15bk8jn")).getText());
         temp.findElements(By.className("css-yv1aru")).stream().forEach(s-> chargeData.add(s.getText()));
         return chargeData;
    }

        public void clickOnChargerType(String chargingSectionName,double chargerType) throws InterruptedException {
        temp= getSectionToNavigate(chargingModalSections,chargingSectionName);
        navigateUsingJSToAnElement(driver,temp.findElement(By.cssSelector("[class='css-9lvjku']")));
        temp.findElements(By.cssSelector("[data-testid]")).stream()
                .filter(s->s.findElement(By.cssSelector("div:nth-child(2)>p:nth-child(1)")).getText()
                        .contains(Double.toString(chargerType))).forEach(s-> clickOnElementJS(driver,s));
        Thread.sleep(1000);
    }

    public void clickOnLearnMore() throws InterruptedException {
        clickOnElement(temp.findElement(By.cssSelector("div[class='css-ly8tcg']")));
    }
    public void clickOnSeeMore(String feature) {
        designIntroFeatureList.stream().filter(s-> s.findElement(By.cssSelector("p")).getText().equalsIgnoreCase(feature)).
                forEach(s->clickOnElement(s.findElement(By.cssSelector("div[class='css-ly8tcg']"))));
    }

    public void clickOnTheLink(String linkText) {
        clickOnElement(mapping.get(linkText.toUpperCase()));
    }

    public void navigateToView(String view) throws InterruptedException {
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofSeconds(15))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoring(NoSuchElementException.class);
        WebElement foo = wait.until(new Function<WebDriver, WebElement>() {
            public WebElement apply(WebDriver driver) {
                return sections.get(10);
            }
        });
        temp=getSectionToNavigate(sections,view);
        navigateUsingJSToAnElement(driver,temp);
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
            WebElement elementToVerify = null;
            try {
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

    public void updateSliderPosition(String slideX) throws InterruptedException {
        Actions a= new Actions(driver);
        navigateUsingJSToAnElement(driver,rangeCalcComp);
        int width= range.getRect().getWidth();
        if(Integer.parseInt(slideX)<=width){
        a.clickAndHold(rangeSlider).dragAndDropBy(rangeSlider,Integer.parseInt(slideX),0).build().perform();}
        Thread.sleep(1000);
    }

    public RangeData calculateMiles(){
        return new RangeData(Integer.parseInt(rangeCharge.getText()),Integer.parseInt(rangeChargePercentage.getText()),
                Integer.parseInt(rangeMiles.getText()));

    }

    public boolean onModal(){
        if(new WebDriverWait(driver, 3).until(ExpectedConditions.visibilityOf(modalOpen)).isDisplayed())
        return true;
        return false;
    }

    public boolean ifSectionClickable(){
        for (int i=(modalSections.size()-1);i>=0;i--){
            try {
                new WebDriverWait(driver, 2).until(ExpectedConditions.elementToBeClickable(modalSections.get(i)));
                modalSections.get(i).click();
            }
            catch (Exception e){
                return false;
            }
        }
        return true;
    }
}

