package UtilsMain;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import static UtilsMain.InitiateDriver.getDriver;

public class CommonMethods {
    private static final Logger LOG = LogManager.getLogger(CommonMethods.class);
    private static XSSFWorkbook workbook;
    private static XSSFSheet sheet;
    private static FileInputStream fis;


    public static void clickOnElement(WebElement element) {
        element.click();
    }

    public static void clickOnElementJS(WebDriver driver, WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);

    }

    public static XSSFSheet getSheet(String excelLocation, String sheetName) {
        try {
            fis = new FileInputStream(excelLocation);
            workbook = new XSSFWorkbook(fis);
        } catch (IOException e) {
            System.out.println("Unable to find/access excel" + e);
        }

        int countSheets = workbook.getNumberOfSheets();
        for (int i = 0; i < countSheets; i++) {
            if (workbook.getSheetName(i).equalsIgnoreCase(sheetName)) {
                sheet = workbook.getSheetAt(i);
                return sheet;
            }
        }
        return null;
    }

    public static void closeInputStream() throws IOException {
        fis.close();
    }

    public static ArrayList<String> getRowData(Row r, int count) {
        ArrayList<String> a = new ArrayList<String>();
        Iterator<Cell> cv = r.cellIterator();
        while (cv.hasNext()) {
            Cell c = cv.next();
            if (c.getColumnIndex() < count) {
                if (c.getCellType() == CellType.STRING) {
                    a.add(c.getStringCellValue());
                } else {
                    a.add(NumberToTextConverter.toText(c.getNumericCellValue()));
                }
            }
        }
        return a;
    }

    public static String getCellValue(Row r, int index) {
        String value;
        try {
            if (r.getCell(index).getCellType() == CellType.STRING) {
                value = r.getCell(index).getStringCellValue();
            } else {
                value = NumberToTextConverter.toText(r.getCell(index).getNumericCellValue());
            }
            return value;
        } catch (Exception e) {
            System.out.println("Underlying exception: " + e.getCause());
        }
        return null;
    }


    public static void writeToExcel(String path) throws IOException {
        FileOutputStream fos = new FileOutputStream(path);
        //write data in the excel file
        workbook.write(fos);
        //close output stream
        fos.close();

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

    public static WebElement getSectionToNavigate(List<WebElement> sections, String view, String attribute) {
//        List<WebElement>e= sections.stream().filter(s-> s.getAttribute("data-name").equalsIgnoreCase(view)).
//                collect(Collectors.toList());
        for (WebElement e : sections) {
            if (e.getAttribute(attribute).equalsIgnoreCase(view)) {
                return e;
            }
        }
        return null;
    }

    public static int makeUrlConnection(WebElement element) throws IOException {
        String attName = "href";
        HttpURLConnection connection = (HttpURLConnection) new URL(element.getAttribute(attName)).openConnection();
        connection.setRequestMethod("HEAD");
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.93 Safari/537.36");
        connection.connect();
        LOG.info("CTA- " + element.getAttribute("textContent") + ", URL- " + element.getAttribute(attName) + ", Response Code- " + connection.getResponseCode() + connection.getResponseMessage());
        return connection.getResponseCode();
    }

    public static int makeUrlConnection(String element) throws IOException {
        String attName = "href";
        HttpURLConnection connection = (HttpURLConnection) new URL(element).openConnection();
        connection.setRequestMethod("HEAD");
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.93 Safari/537.36");
        connection.connect();
        return connection.getResponseCode();
    }

    public static int getColumnIndex(Row firstRow, String a) {
        Iterator<Cell> cell = firstRow.cellIterator();
        int k = 0;
        while (cell.hasNext()) {
            while (cell.hasNext()) {
                Cell value = cell.next();
                if (value.getStringCellValue().equalsIgnoreCase(a)) {
                    return k;
                }
                k++;
            }
        }
        return 0;
    }

    public static void writeToCell(Row r, int index, String text) {
        r.createCell(index).setCellValue(text);
    }

    public static void navigateUsingJSToAnElementEnd(WebDriver driver, WebElement element) throws InterruptedException {
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView(false);", element);
        Thread.sleep(1000);
    }

    public static void navigateUsingJSToAnElementStart(WebDriver driver, WebElement element) throws InterruptedException {
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView(true);", element);
        Thread.sleep(1000);
    }

    public static void navigateUsingJSToAnElementEnd(WebDriver driver, WebElement element, int xOffset, int yOffset) throws InterruptedException {
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView(false); ", element);
        Thread.sleep(2000);
        String arg = "window.scrollBy(" + xOffset + "," + yOffset + ")";
        System.out.println(arg);
        ((JavascriptExecutor) driver)
                .executeScript(arg);
        Thread.sleep(2000);
    }

    public static void scrollToElementUsingActionClass(WebDriver driver, WebElement element) {
        new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(element));
        Actions ac = new Actions(driver);
        ac.moveToElement(element).build().perform();
    }

    public static void scrollToElementUsingActionClass(WebElement element, int xOffset, int yOffset) {
        new WebDriverWait(getDriver(), 5).until(ExpectedConditions.visibilityOf(element));
        Actions ac = new Actions(getDriver());
        ac.moveToElement(element, xOffset, yOffset).build().perform();
    }

    public static void handleCookie(WebElement acceptCookies, WebElement cookieBar) {
        LOG.info("Identifying if the cookie bar to be handled");
        WebDriver driver = getDriver();
        driver.switchTo().defaultContent();
        WebDriverWait wait = new WebDriverWait(driver, 5);
        LOG.info(cookieBar.getAttribute("style"));
        if (!cookieBar.getAttribute("style").contains("display: none;")) {
            LOG.info("handling cookies bar");
                wait.until(ExpectedConditions.visibilityOf(cookieBar)).isDisplayed();
                wait.until(ExpectedConditions.elementToBeClickable(acceptCookies));
            while (acceptCookies.isDisplayed())
                clickOnElement(acceptCookies);
        }
    }

    public static String getDeviceProperty(String key) throws IOException {
        Properties prop = new Properties();
        FileInputStream fis = new FileInputStream("src/test/resources/device.properties");
        prop.load(fis);
        return prop.getProperty(key);
    }

    public static void explicitWait(WebElement element) {
        WebDriverWait wait = new WebDriverWait(getDriver(), 3, 1000);
    }

}
