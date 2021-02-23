package Steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import resources.Utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Set;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class Steps extends Utils {
    final private static String polestar2PageTitle = "Polestar 2 – The 100% electric car | Polestar US";
    private static ArrayList<String> expectedCallout, actualCallout = new ArrayList<>();
    private static RemoteWebDriver driver;
    private static Class<?> cls;
    private static Object obj;

    @Given("User lands on {string} page and has to work on {string}")
    public void user_lands_on_page(String url, String page) throws ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException {
        driver = hooks.getDriver();
        driver.get(url);
        cls = Class.forName("Polestar.Pages." + page);
        Constructor<?> ct = cls.getConstructor(WebDriver.class);
        obj = ct.newInstance(driver);
    }

    @Given("User is in {string} page")
    public void user_is_in_page(String page) {
        waitUntilPageTitle(driver, polestar2PageTitle);
        assertEquals(driver.getTitle(), polestar2PageTitle, "Title mismatch");
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

    @Then("Verify the user navigates to {string}")
    public void verify_the_user_navigates_to(String userPageTitle) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        boolean isVisible;
        switch (userPageTitle) {
            case "Select your region":
                isVisible = (boolean) callMethod(cls, obj, "isElementVisible", userPageTitle);
//                isVisible = p.isElementVisible(userPageTitle);
                assertTrue(isVisible);
                break;
            case "Careers":
                waitUntilPageTitle(driver, userPageTitle.replace("*", "|"));
                Set<String> a = driver.getWindowHandles();
                int count = 0;
                for (String handle : a) {
                    driver.switchTo().window(handle);
                    if (driver.getTitle().equalsIgnoreCase(userPageTitle.replace("*", "|"))) {
                        count++;
                    }
                }
                assertEquals(count, 1, "Title mismatch");
            default:
                waitUntilPageTitle(driver, userPageTitle.replace("*", "|"));
                assertEquals(driver.getTitle(), userPageTitle.replace("*", "|"), "Title mismatch");
        }
    }

    @Then("when user clicks on back verify that back user lands on Polestar2 homepage")
    public void when_user_clicks_on_back_verify_that_back_user_lands_on_polestar_homepage() {
        driver.navigate().back();
        waitUntilPageTitle(driver, polestar2PageTitle);
        assertEquals(driver.getTitle(), polestar2PageTitle, "Title mismatch");
    }

    @Then("when user clicks on close user lands on Polestar2 homepage")
    public void when_user_clicks_on_close_user_lands_on_polestar_homepage() throws InterruptedException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
//        p.clickOnTheLink("Close");
        callMethod(cls, obj, "clickOnTheLink", "Close");
        boolean isVisible;
        Thread.sleep(2000);
        isVisible = (boolean) callMethod(cls, obj, "isElementVisible", "Select your region");
//        isVisible = p.isElementVisible("Select your region");
        assertTrue(isVisible == false, "Elemenst is still visible");
    }

    @When("user navigates to header")
    public void user_navigates_to_header() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        callMethod(cls, obj, "clickOnTheLink", "Header Menu");
//        p.clickOnTheLink("Header Menu");
    }

    @When("user moves the mouse to {string}")
    public void user_moves_the_mouse_to(String Menu) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        callMethod(cls, obj, "moveCursorTo", Menu);
//        p.moveCursorTo(Menu);
    }

    @When("we read the Web Elements data from excel {string} and sheet {string}")
    public void weReadTheWebElementsDataFromExcelAndSheet(String path, String sheetName)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        callMethod(cls, obj, "readData", path, sheetName);
    }

    @And("extract the required data")
    public void extractTheRequiredData() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        callMethod(cls, obj, "extractDataOfElements");
    }

    @Then("write extracted data to excel {string}")
    public void writeExtractedDataToExcel(String path) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        callMethod(cls, obj, "writeData", path);
    }

    @And("clicks on {string} option for {string}")
    public void clicksOnOptionFor(String button, String category) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        callMethod(cls, obj, "clickOnButton", category + " " + button);
    }

    @When("user navigates to {string}")
    public void userNavigatesTo(String view) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        callMethod(cls, obj, "navigateToView", view);
    }

    @Then("Verify the user lands on {string}")
    public void verifyTheUserLandsOn(String section) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        assertEquals((String) callMethod(cls, obj, "getViewName", section), section, "Not in the selected view");
    }

    @And("extract all the callout for the section {string}")
    public void extractAllTheCalloutForTheSection(String section) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        callMethod(cls, obj, "readData", "src/main/resources/excel.xlsx", section);
        expectedCallout = (ArrayList<String>) callMethod(cls, obj, "extractCalloutFromExcel");
        actualCallout = (ArrayList<String>) callMethod(cls, obj, "getTextOfElements", section);

    }

    @Then("if all the callouts are matching")
    public void ifAllTheCalloutsAreMatching() {
        assertEquals(actualCallout, expectedCallout, "Callouts are not matching");
    }
}
