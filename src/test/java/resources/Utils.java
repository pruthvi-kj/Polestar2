package resources;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Properties;

public class Utils {

    public static String getDeviceProperty(String key) throws IOException {
        Properties prop = new Properties();
        FileInputStream fis = new FileInputStream("src/test/java/resources/device.properties");
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
    public static Object callMethod(Class cls,Object obj, String methodName) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method=cls.getDeclaredMethod(methodName);
        Object o=method.invoke(obj);
        return o;
    }

    public static Object callMethod(Class cls,Object obj, String methodName, String arg1) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method=cls.getDeclaredMethod(methodName, String.class);
        Object o=method.invoke(obj, arg1);
        return o;
    }

    public static Object callMethod(Class cls,Object obj, String methodName, String arg1,String arg2) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method=cls.getDeclaredMethod(methodName, String.class,String.class);
        Object o=method.invoke(obj, arg1,arg2);
        return o;
    }

}
