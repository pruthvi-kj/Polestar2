package Polestar.Utils;

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

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class commonMethods {
    private static XSSFWorkbook workbook;
    private static XSSFSheet sheet;
    private static FileInputStream fis;

    public static void clickOnElement(WebElement element) {
        try {
            element.click();
        } catch (Exception e) {
            throw e;
        }
    }

    public static void clickOnElementJS(WebDriver driver, WebElement element)
    {
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

    public int getColumnIndex(Row firstRow, String a) {
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

    public void writeToCell(Row r, int index, String text) {
        r.createCell(index).setCellValue(text);
    }

    public void navigateUsingJSToAnElement(WebDriver driver, WebElement element) throws InterruptedException {
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView(true);", element);
        Thread.sleep(1000);
    }

    public void scrollToElementUsingActionClass(WebDriver driver, WebElement element) {
        Actions ac = new Actions(driver);
        ac.moveToElement(element).build().perform();
    }
    public static WebElement getSectionToNavigate(List<WebElement> sections, String view){
//        List<WebElement>e= sections.stream().filter(s-> s.getAttribute("data-name").equalsIgnoreCase(view)).
//                collect(Collectors.toList());
        for(WebElement e: sections){
            if(e.getAttribute("data-name").equalsIgnoreCase(view)){
                return e;
            }
        }
         return null;
    }

}
