package Steps;

import Polestar.DataMembers.ChargeData;
import Polestar.DataMembers.FuelPrices;
import Polestar.DataMembers.RangeData;
import UtilsMain.InitiateDriver;
import UtilsMain.TestInitialization;
import UtilsMain.TestReport;
import UtilsTest.ApiCall;
import UtilsTest.Utils;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalTime;
import java.util.*;

import static UtilsMain.InitiateDriver.getDriver;
import static org.apache.commons.lang.builder.EqualsBuilder.reflectionEquals;
import static org.testng.Assert.*;


public class StepDefinition extends Utils {
    final private static String polestar2PageTitle = "Polestar 2 – The 100% electric car | Polestar US";
    private final static int batterySize = 78;
    private static final Logger LOG = LogManager.getLogger(StepDefinition.class);
    private ArrayList<String> expectedCallout, actualCallout = new ArrayList<>();
    private String globalChargingSectionName;
    private static TestReport testReport;
    private List<String> globalExpectedServicePointHeadings;
    private List<String> globalActualServicePointHeadings;
    private List<String> globalExpectedSpacesHeadings;
    private List<String> globalActualSpacesHeadings;
    private List<String> globalActualSectionName;
    private List<String> globalExpectedSectionName;
    private List<String> globalActualModals;
    private List<String> globalExpectedModals;
    private List<LocalTime> expectedTimes;
    private List<LocalTime> actualTimes;
    private String globalPageName;
    private String stateName = null;
    private ChargeData chargeData;
    private List<RangeData> rangeData;
    private static final float singleMotorRange=2.65f;
    private static final float dualMotorRange=2.49f;
    private static final int defaultMiles=233;
    private String motorType;
    private static final ThreadLocal< Class<?>> clsThread = new ThreadLocal<Class<?>>();
    private static final ThreadLocal<Object> objThread = new ThreadLocal<>();
    private static final ThreadLocal<RemoteWebDriver> driverThread = new ThreadLocal<>();
    private static final List<String> pagesToVerify =Arrays.asList("BuyingProcess","ElectricDriving","Polestar2", "ServiceAndAssistance");
    private String globalRoute;


    @Given("User is in {string} page for {string} route")
    public void user_is_in_page(String page, String route) {
        try {
            testReport = TestInitialization.getInstance();
            driverThread.set(getDriver());
            globalRoute= route;
            String env=(System.getProperty("environment") == null ? InitiateDriver.defaultEnv : System.getProperty("environment")).toUpperCase();
            LOG.info("Launching URL"+getURL(page,route));
            if(env.equalsIgnoreCase("QA"))
                driverThread.get().get(getValue("AuthUrl"));
            driverThread.get().get(getURL(page,route));
            clsThread.set(Class.forName("Polestar.Pages." + page));
            globalPageName = page;
            Constructor<?> ct = clsThread.get().getConstructor(WebDriver.class);
            objThread.set(ct.newInstance( driverThread.get()));
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException | ClassNotFoundException | NoSuchMethodException | IOException e) {
            LOG.error(getLog(e).toString());
            throw new RuntimeException(e);
        }
    }

    @When("user navigates to footer")
    public void user_navigates_to_footer() {
        try {
            LOG.info("Calling method navigateToFooter for class  "+ clsThread.get().getName()+" with params"  );
            callMethod(clsThread.get(), objThread.get(), "navigateToFooter");
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            LOG.error(getLog(e).toString());
            throw new RuntimeException(e);
        }
    }

    @When("clicks on {string}")
    public void clicks_on(String linkName){
        motorType=linkName;
        try {
            LOG.info("Calling method clickOnTheLink for class "+ clsThread.get().getName() + " with params " + linkName );
            callMethod(clsThread.get(), objThread.get(), "clickOnTheLink", linkName);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            LOG.error(getLog(e).toString());
            throw new RuntimeException(e);
        }
    }

    @Then("Verify the user navigates to {string}")
    public void verify_the_user_navigates_to(String userPageTitle) {
        boolean isVisible;
        switch (userPageTitle) {
            case "Select your region":
                try {
                    LOG.info("Calling method isElementVisible for class "+ clsThread.get().getName()+ " with params " + userPageTitle );
                    isVisible = (boolean) callMethod(clsThread.get(), objThread.get(), "isElementVisible", userPageTitle);
                } catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
                    LOG.error(getLog(e).toString());
                    throw new RuntimeException(e);
                }
                assertTrue(isVisible);
                break;
            case "Careers":
                waitUntilPageTitle( driverThread.get(), userPageTitle.replace("*", "|"));
                Set<String> a =  driverThread.get().getWindowHandles();
                int count = 0;
                for (String handle : a) {
                    driverThread.get().switchTo().window(handle);
                    if ( driverThread.get().getTitle().equalsIgnoreCase(userPageTitle.replace("*", "|"))) {
                        count++;
                    }
                }
                assertEquals(count, 1, "Title mismatch");
            default:
                waitUntilPageTitle( driverThread.get(), userPageTitle.replace("*", "|"));
                assertEquals( driverThread.get().getTitle(), userPageTitle.replace("*", "|"), "Title mismatch");
        }
    }

    @Then("when user clicks on back verify that back user lands on Polestar2 homepage")
    public void when_user_clicks_on_back_verify_that_back_user_lands_on_polestar_homepage() {
        driverThread.get().navigate().back();
        waitUntilPageTitle( driverThread.get(), polestar2PageTitle);
        assertEquals( driverThread.get().getTitle(), polestar2PageTitle, "Title mismatch");
    }

    @Then("when user clicks on close user lands on Polestar2 homepage")
    public void when_user_clicks_on_close_user_lands_on_polestar_homepage(){
        try {
            LOG.info("Calling method clickOnTheLink for class "+ clsThread.get().getName()+ " with params " + "Close" );
            callMethod(clsThread.get(), objThread.get(), "clickOnTheLink", "Close");
            boolean isVisible;
            Thread.sleep(2000);
            LOG.info("Calling method isElementVisible for class "+ clsThread.get().getName()+ " with params " + "Select your region" );
            isVisible = (boolean) callMethod(clsThread.get(), objThread.get(), "isElementVisible", "Select your region");
            assertFalse(isVisible, "Elemenst is still visible");
        } catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException | InterruptedException e) {
            LOG.error(getLog(e).toString());
            throw new RuntimeException(e);
        }
    }

    @When("user navigates to header")
    public void user_navigates_to_header(){
        try {
            LOG.info("Calling method clickOnTheLink for class "+ clsThread.get().getName() + " with params " + "Header Menu" );
            callMethod(clsThread.get(), objThread.get(), "clickOnTheLink", "Header Menu");
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            LOG.error(getLog(e).toString());
            throw new RuntimeException(e);
        }
    }

    @When("user moves the mouse to {string}")
    public void user_moves_the_mouse_to(String Menu){
        try {
            LOG.info("Calling method moveCursorTo for class "+ clsThread.get().getName() + " with params " + Menu);
            callMethod(clsThread.get(), objThread.get(), "moveCursorTo", Menu);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            LOG.error(getLog(e).toString());
            throw new RuntimeException(e);
        }
    }

    @When("we read the Web Elements data from excel {string} and sheet {string}")
    public void weReadTheWebElementsDataFromExcelAndSheet(String path, String sheetName){
        try {
            LOG.info("Calling method readData for class "+ clsThread.get().getName()+ " with params " + path+", "+ sheetName );
            callMethod(clsThread.get(), objThread.get(), "readData", path, sheetName);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            LOG.error(getLog(e).toString());
            throw new RuntimeException(e);
        }
    }

    @And("extract the required data")
    public void extractTheRequiredData(){
        try {
            LOG.info("Calling method extractDataOfElements for class "+ clsThread.get().getName() + " with params " );
            callMethod(clsThread.get(), objThread.get(), "extractDataOfElements");
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            LOG.error(getLog(e).toString());
            throw new RuntimeException(e);
        }
    }

    @Then("write extracted data to excel {string}")
    public void writeExtractedDataToExcel(String path){
        try {
            LOG.info("Calling method writeData for class "+ clsThread.get().getName()+ " with params " + path );
            callMethod(clsThread.get(), objThread.get(), "writeData", path);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            LOG.error(getLog(e).toString());
            throw new RuntimeException(e);
        }
    }

    @And("clicks on {string} option for {string}")
    public void clicksOnOptionFor(String button, String category) {
        try {
            LOG.info("Calling method clickOnButton for class "+ clsThread.get().getName()+ " with params " + category + " " + button );
            callMethod(clsThread.get(), objThread.get(), "clickOnButton", category + " " + button);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            LOG.error(getLog(e).toString());
            throw new RuntimeException(e);
        }
    }

    @When("user navigates to {string}")
    public void userNavigatesTo(String view){
        try {
            LOG.info("Calling method navigateToView for class "+ clsThread.get().getName()+ " with params " + view );
            callMethod(clsThread.get(), objThread.get(), "navigateToView", view);
            Thread.sleep(1000);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | InterruptedException e) {
            LOG.error(getLog(e).toString());
            throw new RuntimeException(e);
        }
        LOG.info("User is in section " + view);
        testReport.log("User is in section " + view);
        testReport.logImage( driverThread.get().getScreenshotAs(OutputType.BASE64));
    }

    @Then("Verify the user lands on {string}")
    public void verifyTheUserLandsOn(String section){
        try {
            LOG.info("Calling method getViewName for class "+ clsThread.get().getName()+ " with params " + section );
            assertEquals((String) callMethod(clsThread.get(), objThread.get(), "getViewName", section), section, "Not in the selected view");
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            LOG.error(getLog(e).toString());
            throw new RuntimeException(e);
        }
    }

    @And("extract all the callout for the section {string}")
    public void extractAllTheCalloutForTheSection(String section){
        try {
            LOG.info("Calling method readData for class "+ clsThread.get().getName()+ " with params " +"src/test/resources/excel.xlsx," + section  );
            callMethod(clsThread.get(), objThread.get(), "readData", "src/test/resources/excel.xlsx", section);
            LOG.info("Calling method extractCalloutFromExcel for class "+ clsThread.get().getName() + " with params");
            expectedCallout = (ArrayList<String>) callMethod(clsThread.get(), objThread.get(), "extractCalloutFromExcel");
            LOG.info("Calling method getTextOfElements for class "+ clsThread.get().getName()+ " with params " + section );
            actualCallout = (ArrayList<String>) callMethod(clsThread.get(), objThread.get(), "getTextOfElements", section);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            LOG.error(getLog(e).toString());
            throw new RuntimeException(e);
        }
    }

    @Then("if all the callouts are matching")
    public void ifAllTheCalloutsAreMatching() {
        assertEquals(actualCallout, expectedCallout, "Callouts are not matching");
    }

    @And("verify that all the links are valid")
    public void verifyLinkValid() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        try {
            LOG.info("Calling method verifyAllLinksAreValid for class "+ clsThread.get().getName()+ " with params " );
            assertTrue((Boolean) callMethod(clsThread.get(), objThread.get(), "verifyAllLinksAreValid"));
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            LOG.error(getLog(e).toString());
            throw new RuntimeException(e);
        }
    }


    @When("clicks on Learn More under section")
    public void clicksOnUnderSection(DataTable sectionName) {
        globalExpectedModals = sectionName.asList(String.class);
        globalActualModals = new ArrayList<>();
        globalExpectedModals.forEach(s -> {
            try {
                LOG.info("Calling method navigateToView for class "+ clsThread.get().getName() + " with params " + s);
                callMethod(clsThread.get(), objThread.get(), "navigateToView", s);
                LOG.info("User is in section " + s);
                testReport.log("User is in section " + s);
                testReport.logImage( driverThread.get().getScreenshotAs(OutputType.BASE64));
                LOG.info("Calling method clickOnLearnMore for class "+ clsThread.get().getName()+ " with params " );
                callMethod(clsThread.get(), objThread.get(), "clickOnLearnMore");
                LOG.info("Calling method onModal for class "+ clsThread.get().getName()+ " with params " );
                globalActualModals.add((String) callMethod(clsThread.get(), objThread.get(), "onModal"));
                LOG.info("User is in modal " + globalActualModals.get(globalActualModals.size() - 1));
                testReport.log("User is in modal " + globalActualModals.get(globalActualModals.size() - 1));
                testReport.logImage( driverThread.get().getScreenshotAs(OutputType.BASE64));
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                LOG.error(getLog(e).toString());
                throw new RuntimeException(e);
            }
        });
    }

    @When("clicks on Learn More under {string} section")
    public void clicksOnUnderSection(String sectionName){
        globalExpectedModals = new ArrayList<>();
        globalActualModals = new ArrayList<>();
        globalExpectedModals.add(sectionName);
        try {
            LOG.info("Calling method navigateToView for class "+ clsThread.get().getName() + " with params " + sectionName);
            callMethod(clsThread.get(), objThread.get(), "navigateToView", sectionName);
            LOG.info("User is in section " + sectionName);
            testReport.log("User is in section " + sectionName);
            testReport.logImage( driverThread.get().getScreenshotAs(OutputType.BASE64));
            LOG.info("Calling method clickOnLearnMore for class "+ clsThread.get().getName()+ " with params " );
            callMethod(clsThread.get(), objThread.get(), "clickOnLearnMore");
            Thread.sleep(1000);
            LOG.info("User is in modal of " + sectionName);
            testReport.log("User is in modal of " + sectionName);
            testReport.logImage( driverThread.get().getScreenshotAs(OutputType.BASE64));
            LOG.info("Calling method onModal for class "+ clsThread.get().getName()+ " with params "  );
            globalActualModals.add((String) callMethod(clsThread.get(), objThread.get(), "onModal"));
        }catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException | InterruptedException e){
            LOG.error(getLog(e).toString());
            throw new RuntimeException(e);
        }
    }

    @Then("Verify the user lands on modal")
    public void verifyTheUserLandsOnModal() {
        assertEquals(globalActualModals.size(), globalExpectedModals.size(), "Unable to open modal");
    }

    @Then("Verify that all the tab headings are clickable")
    public void verifyThatAllTheTabHeadingsAreClickable(){
        try {
            LOG.info("Calling method ifSectionClickable for class "+ clsThread.get().getName()+ " with params " );
            assertTrue((Boolean) callMethod(clsThread.get(), objThread.get(), "ifSectionClickable"), "Unable to click on all the modals");
        }catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException e){
            LOG.error(getLog(e).toString());
            throw new RuntimeException(e);
        }
    }

    @When("clicks on See More under feature")
    public void clicksOnSeeMoreUnderFeature(DataTable feature){
        globalExpectedModals = feature.asList(String.class);
        globalActualModals = new ArrayList<>();
        try {
            LOG.info("Calling method navigateToView for class "+ clsThread.get().getName()+ " with params " + "Exterior" );
            callMethod(clsThread.get(), objThread.get(), "navigateToView", "Exterior");
        }catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException e){
            LOG.error(getLog(e).toString());
            throw new RuntimeException(e);
        }
        globalExpectedModals.forEach(s -> {
            try {
                LOG.info("Calling method clickOnSeeMore for class "+ clsThread.get().getName() + " with params " + s );
                callMethod(clsThread.get(), objThread.get(), "clickOnSeeMore", s);
                LOG.info("User clicks on See More under " + s);
                testReport.log("User clicks on See More under " + s);
                testReport.logImage( driverThread.get().getScreenshotAs(OutputType.BASE64));
                Thread.sleep(1000);
                LOG.info("Calling method onModal for class "+ clsThread.get().getName()  + " with params " );
                globalActualModals.add((String) callMethod(clsThread.get(), objThread.get(), "onModal"));
                LOG.info("User is on modal of " + s);
                testReport.log("User is on modal of " + s);
                testReport.logImage( driverThread.get().getScreenshotAs(OutputType.BASE64));
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | InterruptedException e) {
                LOG.error(getLog(e).toString());
                throw new RuntimeException(e);            }
        });


    }

    @And("clicks on {string} section")
    public void clicksOnSection(String chargingSectionName){
        globalChargingSectionName = chargingSectionName;
        try {
            LOG.info("Calling method getChargingModalSection for class "+ clsThread.get().getName() + " with params " + chargingSectionName  );
            callMethod(clsThread.get(), objThread.get(), "getChargingModalSection", chargingSectionName);
        }catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException e){
            LOG.error(getLog(e).toString());
            throw new RuntimeException(e);
        }
    }

    @And("clicks on {double} kW charger")
    public void clicksOnKWCharger(double powerOutput) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        try {
            LOG.info("Calling method clickOnChargerType for class "+ clsThread.get().getName() + " with params " + powerOutput );
            callMethod(clsThread.get(), objThread.get(), "clickOnChargerType", globalChargingSectionName, powerOutput);
        }catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException e){
            LOG.error(getLog(e).toString());
            throw new RuntimeException(e);
        }
    }

    @And("slider positions are set at {int} px and {int} px from current position")
    public void sliderPositionsAreSetAtPxAndPx(int startChargePercentage, int endChargePercentage) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        try {
            LOG.info("Calling method updateSliderPosition for class "+ clsThread.get().getName()  + " with params " + startChargePercentage+", "+endChargePercentage);
            callMethod(clsThread.get(), objThread.get(), "updateSliderPosition", startChargePercentage, endChargePercentage);
        }catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException e){
            LOG.error(getLog(e).toString());
            throw new RuntimeException(e);
        }
    }

    @Then("verify the charge time calculated")
    public void verifyTheChargeTimeCalculated() {
        SoftAssert softAssert = new SoftAssert();
        for (int i = 0; i < actualTimes.size(); i++) {
            softAssert.assertEquals(actualTimes.get(i), expectedTimes.get(i), "Time for charging do not match");
        }
        softAssert.assertAll();
    }

    @And("clicks on {string} tab")
    public void clicksOnTab(String tabName) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        LOG.info("Calling method clickOnTabInSavings for class "+ clsThread.get().getName() + " with params " + tabName+", "+ globalChargingSectionName );
        callMethod(clsThread.get(), objThread.get(), "clickOnTabInSavings", tabName, globalChargingSectionName);
    }

    @And("user update the slider position")
    public void userSlidesUpto(DataTable slideX) {
        List<Integer> slide = slideX.asList(int.class);
        rangeData = new ArrayList<>();
        slide.forEach(s -> {
            try {
                LOG.info("Calling method updateSliderPosition for class "+ clsThread.get().getName() + " with params " + s  );
                callMethod(clsThread.get(), objThread.get(), "updateSliderPosition", s);
                LOG.info("Calling method calculateMiles for class "+ clsThread.get().getName()  + " with params ");
                rangeData.add((RangeData) callMethod(clsThread.get(), objThread.get(), "calculateMiles"));
                testReport.log("screenshot for "+motorType+" miles travelled " + rangeData.get(rangeData.size() - 1).miles);
                testReport.logImage( driverThread.get().getScreenshotAs(OutputType.BASE64));
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                LOG.error(getLog(e).toString());
                throw new RuntimeException(e);
            }
        });
    }

    @Then("verify the miles calculated")
    public void verifyTheMilesCalculated() {
        SoftAssert softAssert = new SoftAssert();
        float rangeInMiles= motorType.equalsIgnoreCase("Single Motor")? singleMotorRange:dualMotorRange;
        rangeData.forEach(s -> {
            float expectedValue;
            if (s.rangePercentage == 100) {
                expectedValue = ((s.numberOfCharge) * 100 * rangeInMiles);
            } else expectedValue = (((s.numberOfCharge - 1) * 100 * rangeInMiles) + ((100 - s.rangePercentage) * rangeInMiles));
            int actualValue = s.miles;
            int expectedRoundValue = (int) (Math.ceil(expectedValue) == actualValue ? Math.ceil(expectedValue) : Math.floor(expectedValue));
            softAssert.assertEquals(actualValue, expectedRoundValue, "Miles do not match");
            LOG.info("Calculated Miles for " + s.numberOfCharge + " charge(s) and " + s.rangePercentage + "% is " + expectedRoundValue + " miles");
            LOG.info("Actual Miles for " + s.numberOfCharge + " charge(s) and " + s.rangePercentage + "% is " + actualValue + " miles");
            testReport.log("Calculated Miles for " + s.numberOfCharge + " charge(s) and " + s.rangePercentage + "% is " + expectedRoundValue + " miles");
            testReport.log("Actual Miles for " + s.numberOfCharge + " charge(s) and " + s.rangePercentage + "% is " + actualValue + " miles");
        });
        softAssert.assertAll();
    }


    @Then("verify the savings")
    public void verifyTheSavings() {
        SoftAssert softAssert = new SoftAssert();
        if (rangeData == null) {
            rangeData = new ArrayList<>();
            rangeData.add(new RangeData());
        }
        rangeData.forEach(rangeData -> {
            String stateCode = getStateCode(stateName);
            FuelPrices actualSavingsValue = null;
            FuelPrices expectedSavingsValue;
            Long actualSaving = null;
            double milesToKM = (rangeData.miles == 0 ? defaultMiles : rangeData.miles);
            double km = milesToKM % 10 > 5 ? Math.ceil(milesToKM * 1.609) : Math.floor(milesToKM * 1.609);
            try {
                if (globalPageName.equalsIgnoreCase("Polestar2")) {
                    if (stateName == null) {
                        if (rangeData.miles != 0) {
                            LOG.info("Calling method navigateToView for class " + clsThread.get().getName() + " with params " + "Range" );
                            callMethod(clsThread.get(), objThread.get(), "navigateToView", "Range");
                        }
                        LOG.info("Calling method clickOnLearnMore for class "+ clsThread.get().getName() + " with params " );
                        callMethod(clsThread.get(), objThread.get(), "clickOnLearnMore");
                        LOG.info("Calling method getChargingModalSection for class "+ clsThread.get().getName() + " with params " + "Savings" );
                        callMethod(clsThread.get(), objThread.get(), "getChargingModalSection", "Savings");
                    }

                    LOG.info("Calling method getSavingsValue for class "+ clsThread.get().getName()  + " with params " + "Savings" );
                    actualSavingsValue = (FuelPrices) callMethod(clsThread.get(), objThread.get(), "getSavingsValue", "Savings");
                    for (Field field : actualSavingsValue.getClass().getDeclaredFields()) {
                        field.setAccessible(true);
                        LOG.info("Actual " + field.getName() + ": " + field.get(actualSavingsValue));
                        testReport.log("Actual " + field.getName() + ": " + field.get(actualSavingsValue));
                    }
                } else if (globalPageName.equalsIgnoreCase("ElectricDriving")) {
                    LOG.info("Calling method getSavingsValue for class "+ clsThread.get().getName()  + " with params ");
                    actualSaving = (Long) (callMethod(clsThread.get(), objThread.get(), "getSavingsValue"));
                    LOG.info("Actual Savings: " + actualSaving);
                    testReport.log("Actual Savings: " + actualSaving);
                }
                testReport.log("Savings value calculated");
                testReport.logImage( driverThread.get().getScreenshotAs(OutputType.BASE64));

                //getting state code and fuel price
                FuelPrices price = ApiCall.getFuelPrice(stateCode == null ? "US" : "US_" + stateCode);

                assert price != null;
                expectedSavingsValue = calculateExpenses(km, price);
                if (globalPageName.equalsIgnoreCase("Polestar2")) {
                    for (Field field : expectedSavingsValue.getClass().getDeclaredFields()) {
                        field.setAccessible(true);
                        LOG.info("Expected " + field.getName() + ": " + field.get(expectedSavingsValue));
                        testReport.log("Expected " + field.getName() + ": " + field.get(expectedSavingsValue));
                    }
                    assertTrue(reflectionEquals(expectedSavingsValue, actualSavingsValue), "miles calculated do not match");
                } else if (globalPageName.equalsIgnoreCase("ElectricDriving")) {
                    LOG.info("Expected Savings: " + expectedSavingsValue.yearEstimatedFuelSavings);
                    testReport.log("Expected Savings: " + expectedSavingsValue.yearEstimatedFuelSavings);
                    assertTrue(reflectionEquals(expectedSavingsValue.yearEstimatedFuelSavings, actualSaving), "miles calculated do not match");
                }
            } catch (IOException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
                LOG.error(getLog(e).toString());
                throw new RuntimeException(e);
            }
        });
        softAssert.assertAll();
    }

    @And("selects {string} as the state")
    public void selectsAsTheState(String state){
        this.stateName = state;
        try {
            if (!globalPageName.equalsIgnoreCase("ElectricDriving")) {
                LOG.info("Calling method navigateToView for class "+ clsThread.get().getName()  + " with params " + "Range");
                callMethod(clsThread.get(), objThread.get(), "navigateToView", "Range");
                LOG.info("Calling method clickOnLearnMore for class "+ clsThread.get().getName()  + " with params " );
                callMethod(clsThread.get(), objThread.get(), "clickOnLearnMore");
                LOG.info("Calling method getChargingModalSection for class "+ clsThread.get().getName()  + " with params " + "Savings" );
                callMethod(clsThread.get(), objThread.get(), "getChargingModalSection", "Savings");
            }
            LOG.info("Calling method selectState for class "+ clsThread.get().getName() + " with params " + state );
            callMethod(clsThread.get(), objThread.get(), "selectState", state);
        }catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException e){
            LOG.error(getLog(e).toString());
            throw new RuntimeException(e);
        }
    }

    @When("user clicks on section in nav bar")
    public void userClicksOnSectionInNavBar(DataTable dataTable) {
        globalActualSectionName = dataTable.asList(String.class);
        globalExpectedSectionName = new ArrayList<>();
        globalActualSectionName.forEach(s -> {
            try {
                LOG.info("Calling method navigateToSectionUsingNavBar for class "+ clsThread.get().getName() + " with params " + s );
                globalExpectedSectionName.add((String) callMethod(clsThread.get(), objThread.get(), "navigateToSectionUsingNavBar", s));
                LOG.info("user is in section " + s);
                testReport.log("user is in section " + s);
                testReport.logImage( driverThread.get().getScreenshotAs(OutputType.BASE64));

            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                LOG.error(getLog(e).toString());
                throw new RuntimeException(e);
            }
        });

    }

    @Then("verify that user lands on the selected section")
    public void verifyThatUserLandsOnTheSelectedSection() {
        SoftAssert softAssert = new SoftAssert();
        for (int i = 0; i < globalActualSectionName.size(); i++) {
            softAssert.assertEquals(globalActualSectionName.get(i), globalExpectedSectionName.get(i));
        }
        softAssert.assertAll();
    }

    @When("user clicks on tab")
    public void userClicksOnTab(DataTable tabName) {
        globalActualSectionName = tabName.asList(String.class);
        globalExpectedSectionName = new ArrayList<>();
        globalActualSectionName.forEach(s -> {
            try {
                LOG.info("Calling method navigateToSectionUsingTabHeading for class "+ clsThread.get().getName() + " with params " + s  );
                globalExpectedSectionName.add((String) callMethod(clsThread.get(), objThread.get(), "navigateToSectionUsingTabHeading", s));
                LOG.info("user is in section " + s);
                testReport.log("user is in section " + s);
                testReport.logImage( driverThread.get().getScreenshotAs(OutputType.BASE64));
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                LOG.error(getLog(e).toString());
                throw new RuntimeException(e);
            }
        });
    }

    @When("clicks on service point under {string} section")
    public void clicksOnServicePointUnderSection(String sectionName, DataTable servicePoints){
        globalExpectedServicePointHeadings = servicePoints.asList(String.class);
        globalActualServicePointHeadings = new ArrayList<>();
        try {
            LOG.info("Calling method navigateToView for class "+ clsThread.get().getName() + " with params " + sectionName );
            callMethod(clsThread.get(), objThread.get(), "navigateToView", sectionName);
        }catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e){
            LOG.error(getLog(e).toString());
            throw new RuntimeException(e);
        }
        globalExpectedServicePointHeadings.forEach(s -> {
            try {
                LOG.info("Calling method clickOnServicePoint for class "+ clsThread.get().getName() + " with params " + s  );
                globalActualServicePointHeadings.add((String) callMethod(clsThread.get(), objThread.get(), "clickOnServicePoint", s));
                LOG.info("user is in modal for service point " + s);
                testReport.log("user is in modal for service point " + s);
                testReport.logImage( driverThread.get().getScreenshotAs(OutputType.BASE64));
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                LOG.error(getLog(e).toString());
                throw new RuntimeException(e);
            }
        });
    }

    @Then("Verify the user lands on selected service point modal")
    public void verifyTheUserLandsServicePointModal() {
        SoftAssert softAssert = new SoftAssert();
        for (int i = 0; i < globalActualServicePointHeadings.size(); i++) {
            assertEquals(globalActualServicePointHeadings.get(i), globalExpectedServicePointHeadings.get(i), "Unable to open modal");
        }
        softAssert.assertAll();
    }

    @When("clicks on Spaces under {string} section")
    public void clicksOnSpacesUnderSection(String sectionName, DataTable spaces){
        globalExpectedSpacesHeadings = spaces.asList(String.class);
        globalActualSpacesHeadings = new ArrayList<>();
        globalExpectedSpacesHeadings.forEach(s -> {
            try {
                LOG.info("Calling method clickOnSpace for class "+ clsThread.get().getName() + " with params " + s  );
                globalActualSpacesHeadings.add((String) callMethod(clsThread.get(), objThread.get(), "clickOnSpace", s));
                LOG.info("user is in modal for space " + s);
                testReport.log("user is in modal for space " + s);
                testReport.logImage( driverThread.get().getScreenshotAs(OutputType.BASE64));
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                LOG.error(getLog(e).toString() + " " + e.getClass());
                throw new RuntimeException(e);
            }
        });
    }

    @Then("Verify the user lands on selected Spaces modal")
    public void verifyTheUserLandsOnSelectedSpacesModal() {
        SoftAssert softAssert = new SoftAssert();
        for (int i = 0; i < globalActualSpacesHeadings.size(); i++) {
            assertEquals(globalActualSpacesHeadings.get(i), globalExpectedSpacesHeadings.get(i), "Unable to open modal");
        }
        softAssert.assertAll();
    }

    @And("the configuration to set as below")
    public void theConfigurationToSetAsBelow(DataTable data) {
        List<Map<String, String>> chargingInfo = data.asMaps();
        expectedTimes = new ArrayList<>();
        actualTimes = new ArrayList<>();
        chargingInfo.forEach(s -> {
            try {
                LOG.info("Calling method getChargingModalSection for class "+ clsThread.get().getName() + " with params " + s.get("Charger Type") );
                callMethod(clsThread.get(), objThread.get(), "getChargingModalSection", s.get("ChargerType"));
                LOG.info("user is on section" + s.get("ChargerType"));
                testReport.log("user is on section" + s.get("ChargerType"));
                testReport.logImage( driverThread.get().getScreenshotAs(OutputType.BASE64));
                LOG.info("Calling method clickOnChargerType for class "+ clsThread.get().getName()  + " with params " + s.get("ChargerType")+", " +Double.parseDouble(s.get("PowerOutput")));
                callMethod(clsThread.get(), objThread.get(), "clickOnChargerType", s.get("ChargerType"), Double.parseDouble(s.get("PowerOutput")));
                LOG.info("Calling method updateSliderPosition for class "+ clsThread.get().getName()+ " with params " + Integer.parseInt(s.get("Start%"))+", " +Integer.parseInt(s.get("End%")) );
                callMethod(clsThread.get(), objThread.get(), "updateSliderPosition", Integer.parseInt(s.get("Start%")), Integer.parseInt(s.get("End%")));
                Thread.sleep(1000);
                LOG.info("Output for " + s.get("ChargerType"));
                testReport.log("Output for " + s.get("ChargerType"));
                testReport.logImage( driverThread.get().getScreenshotAs(OutputType.BASE64));
                LOG.info("Calling method getChargeDuration for class "+ clsThread.get().getName() );
                chargeData = (ChargeData) callMethod(clsThread.get(), objThread.get(), "getChargeDuration"+ " with params ");
                double duration = ((batterySize * 0.01) * (chargeData.endChargePercentage - chargeData.startChargePercentage))
                        / (Double.parseDouble(s.get("PowerOutput")) * 0.9);
                int expectedHours = (int) Math.floor(duration);
                int expectedMinutes = (int) Math.floor((duration - expectedHours) * 60);
                String time = (expectedHours > 9) ? (expectedHours + ":" + expectedMinutes) : ("0" + expectedHours + ":" + expectedMinutes);
                expectedTimes.add(LocalTime.parse(time));
                actualTimes.add(LocalTime.parse(chargeData.estimatedChargeTime.contains(":") ? chargeData.estimatedChargeTime
                        : "00:".concat(chargeData.estimatedChargeTime)));
                for (LocalTime t : actualTimes) {
                    LOG.info("Calculated Duration- " + expectedHours + ":" + expectedMinutes + " hours");
                    testReport.log("Calculated Duration- " + expectedHours + ":" + expectedMinutes + " hours");
                }
                LOG.info("Actual Duration- " + chargeData.estimatedChargeTime);
                testReport.log("Actual Duration- " + chargeData.estimatedChargeTime);
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | InterruptedException e) {
                LOG.error(getLog(e).toString());
                throw new RuntimeException(e);
            }
        });
    }

    @And("user slides upto {int} px")
    public void userSlidesUptoRangePx(int slideX) {
        rangeData = new ArrayList<>();
        try {
            LOG.info("Calling method updateSliderPosition for class "+ clsThread.get().getName() );
            callMethod(clsThread.get(), objThread.get(), "updateSliderPosition", slideX);
            LOG.info("Calling method calculateMiles for class "+ clsThread.get().getName() );
            rangeData.add((RangeData) callMethod(clsThread.get(), objThread.get(), "calculateMiles"));
            testReport.log("screenshot for " + rangeData.get(0).miles + " miles");
            testReport.logImage( driverThread.get().getScreenshotAs(OutputType.BASE64));
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            LOG.error(getLog(e).toString());
            throw new RuntimeException(e);        }
    }

    @Given("User navigates to {string} page")
    public void userNavigatesToPage(String pageName) {
        user_navigates_to_header();
        clicks_on("Sign in");
        try {
            clsThread.set(Class.forName("Polestar.Pages." + pageName));
            Constructor<?> ct = clsThread.get().getConstructor(WebDriver.class);
            objThread.set(ct.newInstance( driverThread.get()));
        } catch (NoSuchMethodException | ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            LOG.error(getLog(e).toString());
            throw new RuntimeException(e);        }
    }

    @Then("user should be signed in")
    public void userShouldBeSignedIn() {
        new WebDriverWait(driverThread.get(),15).until(ExpectedConditions.visibilityOf(driverThread.get()
                .findElement(By.className("css-yp9swi"))));
        pagesToVerify.forEach(s->{
            try {
                driverThread.get().get(getURL(s,globalRoute));
                user_navigates_to_header();

//                driverThread.get().findElement("sds")
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    @When("User enters {string}")
    public void userEnters(String key) {
        LOG.info("Calling method enterValues for class "+ clsThread.get().getName() );
        try {
            LOG.info(key+"- "+ getValue(key.replaceAll("\\s+","")));
            callMethod(clsThread.get(), objThread.get(), "enterValues", key,getValue(key.replaceAll("\\s+","")));
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | IOException e) {
            LOG.error(getLog(e).toString());
            throw new RuntimeException(e);
        }
    }
}
