package Steps;

import Polestar.DataMembers.ChargeData;
import Polestar.DataMembers.FuelPrices;
import Polestar.DataMembers.RangeData;
import UtilsTest.Utils;
import UtilsTest.ApiCall;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.asserts.SoftAssert;
import Utils.TestInitialization;
import Utils.TestReport;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.apache.commons.lang.builder.EqualsBuilder.reflectionEquals;
import static org.testng.Assert.*;


public class StepDefinition extends Utils {
    final private static String polestar2PageTitle = "Polestar 2 – The 100% electric car | Polestar US";
    private final static int batterySize = 78;
    private static final Logger LOG = LogManager.getLogger(StepDefinition.class);
    private ArrayList<String> expectedCallout, actualCallout = new ArrayList<>();
    private static RemoteWebDriver driver;
    private static Class<?> cls;
    private static Object obj;
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
    private List<RangeData> rangeData1;

    @Given("User is in {string} page")
    public void user_is_in_page(String page) {
        try {
            testReport = TestInitialization.getInstance();
            driver = Hooks.getDriver();
            driver.get(getURL(page));
            cls = Class.forName("Polestar.Pages." + page);
            globalPageName = page;
            Constructor<?> ct = cls.getConstructor(WebDriver.class);
            obj = ct.newInstance(driver);
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException | ClassNotFoundException | NoSuchMethodException | IOException e) {
            LOG.error(getLog(e).toString());
            throw new RuntimeException(e);
        }
    }

    @When("user navigates to footer")
    public void user_navigates_to_footer() {
        try {
            callMethod(cls, obj, "navigateToFooter");
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            LOG.error(getLog(e).toString());
            throw new RuntimeException(e);
        }
    }

    @When("clicks on {string}")
    public void clicks_on(String linkName) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        try {
            callMethod(cls, obj, "clickOnTheLink", linkName);
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
                    isVisible = (boolean) callMethod(cls, obj, "isElementVisible", userPageTitle);
                } catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
                    LOG.error(getLog(e).toString());
                    throw new RuntimeException(e);
                }
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
    public void when_user_clicks_on_close_user_lands_on_polestar_homepage(){
        try {
            callMethod(cls, obj, "clickOnTheLink", "Close");
            boolean isVisible;
            Thread.sleep(2000);
            isVisible = (boolean) callMethod(cls, obj, "isElementVisible", "Select your region");
            assertFalse(isVisible, "Elemenst is still visible");
        } catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException | InterruptedException e) {
            LOG.error(getLog(e).toString());
            throw new RuntimeException(e);
        }
    }

    @When("user navigates to header")
    public void user_navigates_to_header(){
        try {
            callMethod(cls, obj, "clickOnTheLink", "Header Menu");
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            LOG.error(getLog(e).toString());
            throw new RuntimeException(e);
        }
    }

    @When("user moves the mouse to {string}")
    public void user_moves_the_mouse_to(String Menu){
        try {
            callMethod(cls, obj, "moveCursorTo", Menu);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            LOG.error(getLog(e).toString());
            throw new RuntimeException(e);
        }
    }

    @When("we read the Web Elements data from excel {string} and sheet {string}")
    public void weReadTheWebElementsDataFromExcelAndSheet(String path, String sheetName){
        try {
            callMethod(cls, obj, "readData", path, sheetName);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            LOG.error(getLog(e).toString());
            throw new RuntimeException(e);
        }
    }

    @And("extract the required data")
    public void extractTheRequiredData(){
        try {
            callMethod(cls, obj, "extractDataOfElements");
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            LOG.error(getLog(e).toString());
            throw new RuntimeException(e);
        }
    }

    @Then("write extracted data to excel {string}")
    public void writeExtractedDataToExcel(String path){
        try {
            callMethod(cls, obj, "writeData", path);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            LOG.error(getLog(e).toString());
            throw new RuntimeException(e);
        }
    }

    @And("clicks on {string} option for {string}")
    public void clicksOnOptionFor(String button, String category) {
        try {
            callMethod(cls, obj, "clickOnButton", category + " " + button);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            LOG.error(getLog(e).toString());
            throw new RuntimeException(e);
        }
    }

    @When("user navigates to {string}")
    public void userNavigatesTo(String view){
        try {
            callMethod(cls, obj, "navigateToView", view);
            Thread.sleep(1000);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | InterruptedException e) {
            LOG.error(getLog(e).toString());
            throw new RuntimeException(e);
        }
        LOG.info("User is in section " + view);
        testReport.log("User is in section " + view);
        testReport.logImage(driver.getScreenshotAs(OutputType.BASE64));
    }

    @Then("Verify the user lands on {string}")
    public void verifyTheUserLandsOn(String section){
        try {
            assertEquals((String) callMethod(cls, obj, "getViewName", section), section, "Not in the selected view");
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            LOG.error(getLog(e).toString());
            throw new RuntimeException(e);
        }
    }

    @And("extract all the callout for the section {string}")
    public void extractAllTheCalloutForTheSection(String section){
        try {
            callMethod(cls, obj, "readData", "src/test/resources/excel.xlsx", section);
            expectedCallout = (ArrayList<String>) callMethod(cls, obj, "extractCalloutFromExcel");
            actualCallout = (ArrayList<String>) callMethod(cls, obj, "getTextOfElements", section);
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
            assertTrue((Boolean) callMethod(cls, obj, "verifyAllLinksAreValid"));
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
                callMethod(cls, obj, "navigateToView", s);
                LOG.info("User is in section " + s);
                testReport.log("User is in section " + s);
                testReport.logImage(driver.getScreenshotAs(OutputType.BASE64));
                callMethod(cls, obj, "clickOnLearnMore");
                globalActualModals.add((String) callMethod(cls, obj, "onModal"));
                LOG.info("User is in modal " + globalActualModals.get(globalActualModals.size() - 1));
                testReport.log("User is in modal " + globalActualModals.get(globalActualModals.size() - 1));
                testReport.logImage(driver.getScreenshotAs(OutputType.BASE64));
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
            callMethod(cls, obj, "navigateToView", sectionName);
            LOG.info("User is in section " + sectionName);
            testReport.log("User is in section " + sectionName);
            testReport.logImage(driver.getScreenshotAs(OutputType.BASE64));
            callMethod(cls, obj, "clickOnLearnMore");
            Thread.sleep(1000);
            LOG.info("User is in modal of " + sectionName);
            testReport.log("User is in modal of " + sectionName);
            testReport.logImage(driver.getScreenshotAs(OutputType.BASE64));
            globalActualModals.add((String) callMethod(cls, obj, "onModal"));
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
            assertTrue((Boolean) callMethod(cls, obj, "ifSectionClickable"), "Unable to click on all the modals");
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
            callMethod(cls, obj, "navigateToView", "Exterior");
        }catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException e){
            LOG.error(getLog(e).toString());
            throw new RuntimeException(e);
        }
        globalExpectedModals.forEach(s -> {
            try {
                callMethod(cls, obj, "clickOnSeeMore", s);
                LOG.info("User clicks on See More under " + s);
                testReport.log("User clicks on See More under " + s);
                testReport.logImage(driver.getScreenshotAs(OutputType.BASE64));
                Thread.sleep(1000);
                globalActualModals.add((String) callMethod(cls, obj, "onModal"));
                LOG.info("User is on modal of " + s);
                testReport.log("User is on modal of " + s);
                testReport.logImage(driver.getScreenshotAs(OutputType.BASE64));
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | InterruptedException e) {
                LOG.error(getLog(e).toString());
                throw new RuntimeException(e);            }
        });


    }

    @And("clicks on {string} section")
    public void clicksOnSection(String chargingSectionName){
        globalChargingSectionName = chargingSectionName;
        try {
            callMethod(cls, obj, "getChargingModalSection", chargingSectionName);
        }catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException e){
            LOG.error(getLog(e).toString());
            throw new RuntimeException(e);
        }
    }

    @And("clicks on {double} kW charger")
    public void clicksOnKWCharger(double powerOutput) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        try {
            callMethod(cls, obj, "clickOnChargerType", globalChargingSectionName, powerOutput);
        }catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException e){
            LOG.error(getLog(e).toString());
            throw new RuntimeException(e);
        }
    }

    @And("slider positions are set at {int} px and {int} px from current position")
    public void sliderPositionsAreSetAtPxAndPx(int startChargePercentage, int endChargePercentage) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        try {
            callMethod(cls, obj, "updateSliderPosition", startChargePercentage, endChargePercentage);
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
        callMethod(cls, obj, "clickOnTabInSavings", tabName, globalChargingSectionName);
    }

    @And("user update the slider position")
    public void userSlidesUpto(DataTable slideX) {
        List<Integer> slide = slideX.asList(int.class);
        rangeData1 = new ArrayList<>();
        slide.forEach(s -> {
            try {
                callMethod(cls, obj, "updateSliderPosition", s);
                rangeData1.add((RangeData) callMethod(cls, obj, "calculateMiles"));
                testReport.log("screenshot for miles " + rangeData1.get(rangeData1.size() - 1).miles);
                testReport.logImage(driver.getScreenshotAs(OutputType.BASE64));
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                LOG.error(getLog(e).toString());
                throw new RuntimeException(e);
            }
        });
    }

    @Then("verify the miles calculated")
    public void verifyTheMilesCalculated() {
        SoftAssert softAssert = new SoftAssert();
        rangeData1.forEach(s -> {
            float expectedValue;
            if (s.rangePercentage == 100) {
                expectedValue = ((s.numberOfCharge) * 100 * 2.49f);
            } else expectedValue = (((s.numberOfCharge - 1) * 100 * 2.49f) + ((100 - s.rangePercentage) * 2.49f));
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
        if (rangeData1 == null) {
            rangeData1 = new ArrayList<>();
            rangeData1.add(new RangeData());
        }
        rangeData1.forEach(rangeData -> {
            String stateCode = getStateCode(stateName);
            FuelPrices actualSavingsValue = null;
            FuelPrices expectedSavingsValue;
            Long actualSaving = null;
            double milesToKM = (rangeData.miles == 0 ? 233 : rangeData.miles);
            double km = milesToKM % 10 > 5 ? Math.ceil(milesToKM * 1.609) : Math.floor(milesToKM * 1.609);
            try {
                if (globalPageName.equalsIgnoreCase("Polestar2")) {
                    if (stateName == null) {
                        if (rangeData.miles != 0)
                            callMethod(cls, obj, "navigateToView", "Range");

                        callMethod(cls, obj, "clickOnLearnMore");
                        callMethod(cls, obj, "getChargingModalSection", "Savings");
                    }

                    actualSavingsValue = (FuelPrices) callMethod(cls, obj, "getSavingsValue", "Savings");
                    for (Field field : actualSavingsValue.getClass().getDeclaredFields()) {
                        field.setAccessible(true);
                        LOG.info("Actual " + field.getName() + ": " + field.get(actualSavingsValue));
                        testReport.log("Actual " + field.getName() + ": " + field.get(actualSavingsValue));
                    }
                } else if (globalPageName.equalsIgnoreCase("ElectricDriving")) {
                    actualSaving = (Long) (callMethod(cls, obj, "getSavingsValue"));
                    LOG.info("Actual Savings: " + actualSaving);
                    testReport.log("Actual Savings: " + actualSaving);
                }
                testReport.log("Savings value calculated");
                testReport.logImage(driver.getScreenshotAs(OutputType.BASE64));

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
                callMethod(cls, obj, "navigateToView", "Range");
                callMethod(cls, obj, "clickOnLearnMore");
                callMethod(cls, obj, "getChargingModalSection", "Savings");
            }
            callMethod(cls, obj, "selectState", state);
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
                globalExpectedSectionName.add((String) callMethod(cls, obj, "navigateToSectionUsingNavBar", s));
                LOG.info("user is in section " + s);
                testReport.log("user is in section " + s);
                testReport.logImage(driver.getScreenshotAs(OutputType.BASE64));

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
                globalExpectedSectionName.add((String) callMethod(cls, obj, "navigateToSectionUsingTabHeading", s));
                LOG.info("user is in section " + s);
                testReport.log("user is in section " + s);
                testReport.logImage(driver.getScreenshotAs(OutputType.BASE64));
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                LOG.error(getLog(e).toString());
                throw new RuntimeException(e);
            }
        });
    }

    @When("clicks on service point under {string} section")
    public void clicksOnServicePointUnderSection(String sectionName, DataTable servicePoints) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        globalExpectedServicePointHeadings = servicePoints.asList(String.class);
        globalActualServicePointHeadings = new ArrayList<>();
        try {
            callMethod(cls, obj, "navigateToView", sectionName);
        }catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e){
            LOG.error(getLog(e).toString());
            throw new RuntimeException(e);
        }
        globalExpectedServicePointHeadings.forEach(s -> {
            try {
                globalActualServicePointHeadings.add((String) callMethod(cls, obj, "clickOnServicePoint", s));
                LOG.info("user is in modal for service point " + s);
                testReport.log("user is in modal for service point " + s);
                testReport.logImage(driver.getScreenshotAs(OutputType.BASE64));
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
                globalActualSpacesHeadings.add((String) callMethod(cls, obj, "clickOnSpace", s));
                LOG.info("user is in modal for space " + s);
                testReport.log("user is in modal for space " + s);
                testReport.logImage(driver.getScreenshotAs(OutputType.BASE64));
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
                callMethod(cls, obj, "getChargingModalSection", s.get("ChargerType"));
                LOG.info("user is on section" + s.get("ChargerType"));
                testReport.log("user is on section" + s.get("ChargerType"));
                testReport.logImage(driver.getScreenshotAs(OutputType.BASE64));
                callMethod(cls, obj, "clickOnChargerType", s.get("ChargerType"), Double.parseDouble(s.get("PowerOutput")));
                callMethod(cls, obj, "updateSliderPosition", Integer.parseInt(s.get("Start%")), Integer.parseInt(s.get("End%")));
                Thread.sleep(1000);
                LOG.info("Output for " + s.get("ChargerType"));
                testReport.log("Output for " + s.get("ChargerType"));
                testReport.logImage(driver.getScreenshotAs(OutputType.BASE64));
                chargeData = (ChargeData) callMethod(cls, obj, "getChargeDuration");
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
        rangeData1 = new ArrayList<>();
        try {
            callMethod(cls, obj, "updateSliderPosition", slideX);
            rangeData1.add((RangeData) callMethod(cls, obj, "calculateMiles"));
            testReport.log("screenshot for " + rangeData1.get(0).miles + " miles");
            testReport.logImage(driver.getScreenshotAs(OutputType.BASE64));
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            LOG.error(getLog(e).toString());
            throw new RuntimeException(e);        }
    }
}
