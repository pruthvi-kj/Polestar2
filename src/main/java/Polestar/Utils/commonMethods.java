package Polestar.Utils;

import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.openqa.selenium.interactions.Actions;

import java.util.Iterator;

public class commonMethods {
    private static XSSFWorkbook workbook;
    private static FileInputStream fis;

    public static void clickOnElement(WebElement element) {
        try {
            element.click();
        } catch (Exception e) {
            throw e;
        }
    }

    public static XSSFSheet getSheet(String excelLocation, String sheetName) {
        XSSFSheet sheet;
        try {
            fis = new FileInputStream(excelLocation);
            workbook = new XSSFWorkbook(fis);
        }catch (IOException e){
            System.out.println("Unable to find/access excel"+ e);
        }

        int countSheets = workbook.getNumberOfSheets();
        for (int i = 0; i < countSheets; i++) {
            if (workbook.getSheetName(i).equalsIgnoreCase(sheetName)) {
                sheet = workbook.getSheetAt(i);
                return sheet;
//                Iterator<Row> rows = sheet.iterator();
//                Row firstRow = rows.next();
//                Iterator<Cell> cell = firstRow.cellIterator();
//                int k = 0;
//
//                while (cell.hasNext()) {
//                    Cell value = cell.next();
//                    if (value.getStringCellValue().equalsIgnoreCase("TestCase")) {
//                        coloumn = k;
//                        break;
//                    }
//                    k++;
//                }
//
//                while (rows.hasNext()) {
//                    Row r = rows.next();
//                    if (r.getCell(coloumn).getStringCellValue().equalsIgnoreCase(TestCaseID)) {
//                        //after you grab purchase testcase row = pull all the data of that row and feed into test
//                        Iterator<Cell> cv = r.cellIterator();
//                        while (cv.hasNext()) {
//                            Cell c = cv.next();
//                            if (c.getCellType() == CellType.STRING) {
//                                a.add(c.getStringCellValue());
//                            } else {
//                                a.add(NumberToTextConverter.toText(c.getNumericCellValue()));
//                            }
//                        }
//                    }
//
//                }
            }
        }
        return null;
    }
    public static void closeInputStream() throws IOException {
        fis.close();
    }

    public static ArrayList<String> getExcelData(Row r, int count) {
        ArrayList<String> a = new ArrayList<String>();
        Iterator<Cell> cv = r.cellIterator();
        while (cv.hasNext()) {
            Cell c = cv.next();
            if (c.getColumnIndex() < count) {
                if (c.getCellType() == CellType.STRING) {
                    a.add(c.getStringCellValue());
                    System.out.println(c.getStringCellValue());
                } else {
                    a.add(NumberToTextConverter.toText(c.getNumericCellValue()));
                }
            }
        }
        return a;
    }

    public static void writeToExcel(String path) throws IOException {
        FileOutputStream fos = new FileOutputStream(path);
        //write data in the excel file
        workbook.write(fos);
        //close output stream
        fos.close();

    }

    public int getColumnIndex(Row firstRow,String a) {
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

    public void writeToCell(Row r, int index, String text){
        r.createCell(index).setCellValue(text);
    }

    public void navigateUsingJSToAnElement(WebDriver driver, WebElement element) throws InterruptedException {
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView(true);", element);
        Thread.sleep(1000);
    }

    public void scrollToElementUsingActionClass(WebDriver driver, WebElement element){
        Actions ac = new Actions(driver);
        ac.moveToElement(element).build().perform();
    }
}
