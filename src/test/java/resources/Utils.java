package resources;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

public class Utils {

    public static String getDeviceProperty(String key) throws IOException {
        Properties prop = new Properties();
        FileInputStream fis = new FileInputStream("src/test/resources/device.properties");
        prop.load(fis);
        return prop.getProperty(key);
    }

    public static String getURL(String key) throws IOException {
        Properties prop = new Properties();
        String readEnv=(System.getProperty("environment")==null?"PROD":System.getProperty("environment")).toUpperCase();
        FileInputStream fis = new FileInputStream("src/test/resources/"+readEnv+".properties");
        prop.load(fis);
        return prop.getProperty(key);
    }

    public static void waitUntilPageTitle(WebDriver driver, String userPageTitle) {
        WebDriverWait wait = new WebDriverWait(driver, 3);
        try {
            wait.until(ExpectedConditions.titleContains(userPageTitle));
        } catch (Exception e) {
        }
    }

    public static Object callMethod(Class cls, Object obj, String methodName) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = cls.getDeclaredMethod(methodName);
        Object o = method.invoke(obj);
        return o;
    }

    public static Object callMethod(Class cls, Object obj, String methodName, String arg1) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = cls.getDeclaredMethod(methodName, String.class);
        Object o = method.invoke(obj, arg1);
        return o;
    }

    public static Object callMethod(Class cls, Object obj, String methodName, String arg1, String arg2) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = cls.getDeclaredMethod(methodName, String.class, String.class);
        Object o = method.invoke(obj, arg1, arg2);
        return o;
    }

    public static Object callMethod(Class cls, Object obj, String methodName,String arg1, int arg2) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = cls.getDeclaredMethod(methodName, String.class, int.class);
        Object o = method.invoke(obj, arg1, arg2);
        return o;
    }
    public static Object callMethod(Class cls, Object obj, String methodName,int arg1, int arg2) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = cls.getDeclaredMethod(methodName, int.class, int.class);
        Object o = method.invoke(obj, arg1, arg2);
        return o;
    }

    public static Object callMethod(Class cls, Object obj, String methodName,String arg1, double arg2) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = cls.getDeclaredMethod(methodName, String.class, double.class);
        Object o = method.invoke(obj, arg1, arg2);
        return o;
    }
    public static String getStateCode(String stateName) {
        XSSFWorkbook workbook = null;
        XSSFSheet sheet = null;
        Map<String, String> stateCodeList= new HashMap<>();
        FileInputStream fis;
        try {
            fis = new FileInputStream("src/test/resources/excel.xlsx");
            workbook = new XSSFWorkbook(fis);
        } catch (IOException e) {
            System.out.println("Unable to find/access excel" + e);
        }
        int countSheets = workbook.getNumberOfSheets();
        for (int i = 0; i < countSheets; i++) {
            if (workbook.getSheetName(i).equalsIgnoreCase("StateCode")) {
                sheet = workbook.getSheetAt(i);
                break;
            }
        }
        Iterator<Row> rows = sheet.iterator();

//        ignore the first row
        rows.next();
        while (rows.hasNext()){
            Iterator<Cell> cells= rows.next().cellIterator();
            if(cells.next().getStringCellValue().equalsIgnoreCase(stateName))
            return cells.next().getStringCellValue();
        }
        return null;
    }

}
