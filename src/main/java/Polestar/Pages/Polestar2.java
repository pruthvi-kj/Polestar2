package Polestar.Pages;

import Polestar.Utils.commonMethods;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.util.*;

public class Polestar2 extends commonMethods {
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


    public Polestar2(WebDriver driver) throws InterruptedException {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        driver.switchTo().defaultContent();
        try {
            try {
                WebDriverWait wait = new WebDriverWait(driver, 15);
                wait.until(ExpectedConditions.visibilityOfElementLocated(
                        (By.xpath("//button[@class='optanon-allow-all accept-cookies-button']"))));
                wait.until(ExpectedConditions.elementToBeClickable
                        (acceptCookies));
                wait.until(ExpectedConditions.visibilityOf(acceptCookies));
            } catch (Exception e) {
                LOG.error(e.getMessage());
            }
            Thread.sleep(2000);
            clickOnElement(acceptCookies);
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
        mapping.put("EXTERIOR PDP", exteriorView);
        mapping.put("PANORAMIC GLASS ROOF SEE MORE", panoramicGlassRoofSeeMore);
        mapping.put("ORDER NOW", orderNowCta);
        mapping.put("BOOK A TEST DRIVE", bookATestDriveHU);

    }

    public void clickOnButton(String category) {
        clickOnElement(mapping.get(category.toUpperCase()));
    }

    public void clickOnTheLink(String linkText) {
        clickOnElement(mapping.get(linkText.toUpperCase()));
    }

    public void navigateToView(String view) throws InterruptedException {
        navigateUsingJSToAnElement(driver, mapping.get(view.toUpperCase()));
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
            throw e;
        }
        scrollToElementUsingActionClass(driver, elementToVerify.get(0));
        for (WebElement e : elementToVerify) {
            calloutActual.add(e.getText());
        }
        return calloutActual;
    }
}

