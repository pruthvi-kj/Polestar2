package Steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import resources.Utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;

public class Steps extends Utils {
    WebDriver driver;
    Object p;
    Class cls;
    Object obj;
    static String polestar2PageTitle = "Polestar 2 – The 100% electric car | Polestar US";


    @Given("User lands on {string} page and has to work on {string}")
    public void user_lands_on_page(String url, String page) throws ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException {
        driver = hooks.getDriver();
        driver.get(url);
        cls = Class.forName("Polestar.Pages." + page + "");
        System.out.println(cls.getConstructor(WebDriver.class));
        Constructor ct = cls.getConstructor(WebDriver.class);
        obj = ct.newInstance(driver);
    }

    @Given("User is in {string} page")
    public void user_is_in_page(String page) {
        waitUntilPageTitle(driver, polestar2PageTitle);
        Assert.assertEquals(polestar2PageTitle, driver.getTitle());
    }

    @When("user navigates to footer")
    public void user_navigates_to_footer() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        callMethod(cls, obj, "navigateToFooter");
//        p.navigateToFooter();
    }

    @When("clicks on {string}")
    public void clicks_on(String linkName) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        callMethod(cls, obj, "clickOnTheLink", linkName);
//        p.clickOnTheLink(linkName);

    }

    @Then("Verify the user lands on {string}")
    public void verify_the_user_lands_on(String userPageTitle) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        boolean isVisible;
        waitUntilPageTitle(driver, userPageTitle.replace("*", "|"));
        switch (userPageTitle) {
            case "Select your region":
                isVisible = (boolean) callMethod(cls, obj, "isElementVisible", userPageTitle);
//                isVisible = p.isElementVisible(userPageTitle);
                Assert.assertEquals(true, isVisible);
                break;
            case "Careers":
                Set<String> a = driver.getWindowHandles();
                int count = 0;
                for (String handle : a) {
                    driver.switchTo().window(handle);
                    if (driver.getTitle().equalsIgnoreCase(userPageTitle.replace("*", "|"))) {
                        count++;
                    }
                }
                Assert.assertEquals(1, count);
            default:
                Assert.assertEquals(userPageTitle.replace("*", "|"), driver.getTitle());
        }
    }

    @Then("when user clicks on back verify that back user lands on Polestar {int} homepage")
    public void when_user_clicks_on_back_verify_that_back_user_lands_on_polestar_homepage(Integer int1) {
        driver.navigate().back();
        waitUntilPageTitle(driver, polestar2PageTitle);
        Assert.assertEquals(polestar2PageTitle, driver.getTitle());
    }

    @Then("when user clicks on close user lands on Polestar {int} homepage")
    public void when_user_clicks_on_close_user_lands_on_polestar_homepage(Integer int1) throws InterruptedException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
//        p.clickOnTheLink("Close");
        callMethod(cls, obj, "clickOnTheLink", "Close");
        boolean isVisible;
        Thread.sleep(2000);
        isVisible = (boolean) callMethod(cls, obj, "isElementVisible", "Select your region");
//        isVisible = p.isElementVisible("Select your region");
        Assert.assertEquals(false, isVisible);
    }

    @When("user navigates to header")
    public void user_navigates_to_header() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        callMethod(cls,obj,"clickOnTheLink","Header Menu");
//        p.clickOnTheLink("Header Menu");
    }

    @When("user moves the mouse to {string}")
    public void user_moves_the_mouse_to(String Menu) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        callMethod(cls,obj,"moveCursorTo",Menu);
//        p.moveCursorTo(Menu);
    }

    @When("we read the Web Elements data from excel {string} and sheet {string}")
    public void weReadTheWebElementsDataFromExcelAndSheet(String path, String sheetName)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        callMethod(cls,obj,"readData",path,sheetName);
    }

    @And("extract the required data")
    public void extractTheRequiredData() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        callMethod(cls,obj,"extractData");
    }

    @Then("write extracted data to excel {string}")
    public void writeExtractedDataToExcel(String path) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        callMethod(cls,obj,"writeData", path);
    }



}
