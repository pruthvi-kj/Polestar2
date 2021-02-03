package Polestar.Pages;

import Polestar.Utils.commonMethods;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Polestar2 extends commonMethods {
    ArrayList<String> a = new ArrayList<String>();
    XSSFSheet sheet;
    WebDriver driver;
    @FindBy(xpath = "//button[@class='optanon-allow-all accept-cookies-button']")
    private WebElement acceptCookies;
    @FindBy(className = "css-1ink1h8")
    private WebElement keyStatsHighlightsAPR;
    @FindBy(className = "css-1ezcku6")
    private List<WebElement> keyStatsHighlights;
    @FindBy(className = "css-3ycsxw")
    private List<WebElement> keyStatsHighlightsLease;

    public Polestar2(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        driver.switchTo().defaultContent();
        try {
            Thread.sleep(3000);
//                WebDriverWait wait = new WebDriverWait(driver, 3);
//                wait.until(ExpectedConditions.visibilityOf(acceptCookies));
//                wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath
//                        ("//button[@class='optanon-allow-all accept-cookies-button']")));
            clickOnElement(acceptCookies);
        } catch (Exception e) {
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

    public void navigateToFooter() throws InterruptedException {
        ((JavascriptExecutor) driver)
                .executeScript("window.scrollTo(0, document.body.scrollHeight)");
        Thread.sleep(1000);
    }


//    public void findAttributes(){
//        System.out.println(dummy.getText());
//        System.out.println(dummy.getCssValue("font-family"));
//        System.out.println(dummy.getCssValue("font-style"));
//        System.out.println(dummy.getCssValue("color"));
//        System.out.println(dummy.getCssValue("font-size"));
//    }
//
//    public void scollToElement(){
//        Actions ac = new Actions(driver);
//        ac.moveToElement(dummy).build().perform();
//    }

    public void readData(String path, String sheetName) throws IOException {
        sheet = getSheet(path, sheetName);
    }

        public void extractData() throws IOException {
        Iterator<Row> rows = sheet.iterator();
        Row firstRow = rows.next();
        //getting the index for writing data
        int textIndex = getColumnIndex(firstRow, "Actual Text");
        int fontIndex = getColumnIndex(firstRow, "Actual Font Family");
        int colourIndex = getColumnIndex(firstRow, "Actual Font Colour");
        int sizeIndex = getColumnIndex(firstRow, "Actual Font Size");

        while (rows.hasNext()) {
            Row r = rows.next();
            System.out.println("seperator");
            a = getExcelData(r,textIndex);

            WebElement elementToVerify = driver.findElement(By.xpath(a.get(0)));
            Actions ac = new Actions(driver);
            ac.moveToElement(elementToVerify).build().perform();
            if (elementToVerify.getText().equalsIgnoreCase(a.get(1))) {
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
}

