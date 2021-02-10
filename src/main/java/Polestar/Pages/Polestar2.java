package Polestar.Pages;

import Polestar.Utils.commonMethods;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.io.IOException;
import java.util.*;

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
    @FindBy(xpath = "//p[@class='css-92kwuh' and text()='Panoramic glass roof']/..//div[text()='See More']")
    private WebElement panoramicGlassRoofSeeMore;
    @FindBy(xpath = "//p[@class='css-92kwuh' and text()='Frameless mirrors']/..//div[text()='See More']")
    private WebElement seeMoreFrameless;
    @FindBy(xpath = "//p[@class='css-92kwuh' and text()='Pixel LED lights']/..//div[text()='See More']")
    private WebElement seeMoreLEDLights;
    @FindBy(className = "css-138i4qw")
    private WebElement exteriorView;

    Map<String, WebElement> mapping = new HashMap<String, WebElement>();



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
//            clickOnElement(acceptCookies);
            ((JavascriptExecutor) driver).executeScript("window.scrollTo(0,0)");
        } catch (Exception e) {
        }
        mapping.put("EXTERIOR PDP",exteriorView);
        mapping.put("PANORAMIC GLASS ROOF SEE MORE", panoramicGlassRoofSeeMore);
    }

    public void clickOnButton(String category){
        clickOnElement(mapping.get(category.toUpperCase()));
    }

    public void navigateToView(String view) throws InterruptedException {
        navigateUsingJSToAnElement(driver,mapping.get(view.toUpperCase()));
//        switch (view){
//            case "Exterior PDP":
//                navigateUsingJSToAnElement(driver,exteriorView);
//        }
    }

    public String getViewName(String view){
        System.out.println(view);
        System.out.println(mapping.get(view.toUpperCase()));
        return mapping.get(view.toUpperCase()).getText();
    }


    public void readData(String path, String sheetName) {
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
            scrollToElementUsingActionClass(driver,elementToVerify);
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

