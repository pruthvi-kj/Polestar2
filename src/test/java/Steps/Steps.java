package Steps;

import Polestar.DataMembers.ChargeData;
import Polestar.DataMembers.FuelPrices;
import Polestar.DataMembers.RangeData;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import resources.Utils;
import resources.apiCall;
import utils.TestInitialization;
import utils.TestReport;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Set;

import static org.apache.commons.lang.builder.EqualsBuilder.reflectionEquals;
import static org.testng.Assert.*;




public class Steps extends Utils {
    final private static String polestar2PageTitle = "Polestar 2 – The 100% electric car | Polestar US";
    private static ArrayList<String> expectedCallout, actualCallout = new ArrayList<>();
    private static RemoteWebDriver driver;
    private static Class<?> cls;
    private static Object obj;
    private static String globalChargingSectionName;
    private final static int batterySize=78;
    private static double globalChargerType;
    private static TestReport testReport;
    private static final double polestar2EnergyConsumption=0.193;
    private static final double fuelVehicleEnergyConsumption=0.083;
    private static final double weeksInYear=52.1775;
    private static String stateName=null;
    private RangeData rangeData;
    private static String globalActualSectionName;
    private static String globalExpectedSectionName;



    @Given("User is in {string} page")
    public void user_is_in_page(String page) throws InvocationTargetException, InstantiationException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, IOException {
        testReport= TestInitialization.getInstance();
        driver = hooks.getDriver();
        driver.get(getURL(page));
        cls = Class.forName("Polestar.Pages." + page);
        Constructor<?> ct = cls.getConstructor(WebDriver.class);
        obj = ct.newInstance(driver);
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
        assertFalse(isVisible, "Elemenst is still visible");
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

    @And("verify that all the links are valid")
    public void verifyLinkValid() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        callMethod(cls,obj,"getAttributes","href");

    }

    @And("user slides upto {int} px")
    public void userSlidesUpto(int slideX) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        callMethod(cls,obj,"updateSliderPosition", Integer.toString(slideX));
        rangeData= (RangeData) callMethod(cls,obj,"calculateMiles");

    }

    @Then("verify the miles calculated")
    public void verifyTheMilesCalculated() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        int expectedValue=0;
        if(rangeData.rangePercentage==100){
            expectedValue= (int) ((rangeData.numberOfCharge)*100*2.33f);
        }
        else expectedValue= (int) (((rangeData.numberOfCharge-1)*100*2.33f)+((100-rangeData.rangePercentage)*2.33f));
        int actualValue= rangeData.miles;
        if(Math.ceil(expectedValue)==actualValue) {
            assertEquals(Math.ceil(expectedValue), actualValue, "Miles do not match");
            testReport.log("Calculated Miles- " + Math.ceil(expectedValue) + " miles");
        }else if(Math.floor(expectedValue)==actualValue) {
            assertEquals(Math.ceil(expectedValue), actualValue, "Miles do not match");
            testReport.log("Calculated Miles- "+Math.floor(expectedValue)+" miles" );
        }else {
        testReport.log("Calculated Miles- "+expectedValue+" miles" );
        assertEquals(expectedValue,actualValue,"Miles do not match");
        }
        testReport.log("Actual Miles- "+ actualValue+" miles");
    }

    @Then("nav bar highlight is on {string}")
    public void navBarHighlightIsOn(String section) {

    }

    @When("clicks on Learn More under {string} section")
    public void clicksOnUnderSection(String sectionName) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        callMethod(cls,obj,"navigateToView",sectionName);
        callMethod(cls, obj, "clickOnLearnMore");
    }

    @Then("Verify the user lands on modal")
    public void verifyTheUserLandsOnModal() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        assertTrue((Boolean) callMethod(cls,obj,"onModal"),"Unable to open modal");
    }

    @Then("Verify that all the tab headings are clickable")
    public void verifyThatAllTheTabHeadingsAreClickable() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        assertTrue((Boolean) callMethod(cls,obj,"ifSectionClickable"));
    }

    @When("clicks on See More under {string} feature")
    public void clicksOnSeeMoreUnderSection(String feature) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        callMethod(cls,obj,"navigateToView","Exterior");
        callMethod(cls,obj,"clickOnSeeMore",feature);

    }

    @And("clicks on {string} section")
    public void clicksOnSection(String chargingSectionName) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        globalChargingSectionName=chargingSectionName;
        callMethod(cls,obj,"getChargingModalSection",chargingSectionName);
    }

    @And("clicks on {double} kW charger")
    public void clicksOnKWCharger(double chargerType) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        globalChargerType=chargerType;
        callMethod(cls,obj,"clickOnChargerType",globalChargingSectionName, chargerType);

    }

    @And("slider positions are set at {int} px and {int} px from current position")
    public void sliderPositionsAreSetAtPxAndPx(int startChargePercentage, int endChargePercentage) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        callMethod(cls,obj,"updateSliderPosition",startChargePercentage, endChargePercentage);

    }

    @Then("verify the charge time calculated")
    public void verifyTheChargeTimeCalculated() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        ChargeData chargeData= (ChargeData) callMethod(cls,obj,"getChargeDuration");
        double duration=((batterySize*0.01)*(chargeData.endChargePercentage-chargeData.startChargePercentage))
                /(globalChargerType*0.9);
        int expectedHours = (int) Math.floor(duration);
        int expectedMinutes = (int) Math.floor((duration- expectedHours)*60);
        String[] split = chargeData.estimatedChargeTime.split(":");
        int actualHours=Integer.parseInt(split[0]);
        int actualMinutes= Integer.parseInt(split[1].split(" ")[0]);
        boolean hoursValidation= expectedHours == actualHours;
        boolean minutesValidation= expectedMinutes == actualMinutes;
        testReport.log("Calculated Duration- "+expectedHours+":"+expectedMinutes+" hours");
        testReport.log("Actual Duration- "+chargeData.estimatedChargeTime);

        assertTrue(hoursValidation && minutesValidation, "Hours mismatch");

    }

    @And("clicks on {string} tab")
    public void clicksOnTab(String tabName) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        callMethod(cls,obj,"clickOnTabInSavings",tabName,globalChargingSectionName);

    }

    @Then("verify the savings")
    public void verifyTheSavings() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, FileNotFoundException {
        double milesToKM=(rangeData==null?233:rangeData.miles)*1;
        if(stateName==null){
        callMethod(cls,obj,"navigateToView","Range");
        callMethod(cls, obj, "clickOnLearnMore");
        callMethod(cls,obj,"getChargingModalSection","Savings");
        }
        FuelPrices actualSavingsValue= (FuelPrices) callMethod(cls,obj,"getSavingsValue", "Savings");
        for (Field field : actualSavingsValue.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            testReport.log(field.getName()+": "+field.get(actualSavingsValue));
        }

        FuelPrices expectedSavingsValue= new FuelPrices();

        //getting state code and fuel price
        String stateCode=getStateCode(stateName);
        FuelPrices price=apiCall.getFuelPrice(stateCode==null? "US":"US_"+stateCode);

        expectedSavingsValue.yearCostForPolestar2=Math.round((polestar2EnergyConsumption * weeksInYear * milesToKM) * price.electricityPrice);
        expectedSavingsValue.yearCostForFuelCar=Math.round((fuelVehicleEnergyConsumption * weeksInYear * milesToKM) * price.fuelPrice);
        expectedSavingsValue.yearEstimatedFuelSavings= expectedSavingsValue.yearCostForFuelCar-expectedSavingsValue.yearCostForPolestar2;
        expectedSavingsValue.monthCostForPolestar2=Math.round(((polestar2EnergyConsumption * weeksInYear * milesToKM)/12)*price.electricityPrice);
        expectedSavingsValue.monthCostForFuelCar=Math.round(((fuelVehicleEnergyConsumption * weeksInYear * milesToKM)/12)*price.fuelPrice);
        expectedSavingsValue.monthEstimatedFuelSavings=expectedSavingsValue.monthCostForFuelCar-expectedSavingsValue.monthCostForPolestar2;
        for (Field field : expectedSavingsValue.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            testReport.log(field.getName()+": "+field.get(expectedSavingsValue));
        }


        assertTrue(reflectionEquals(expectedSavingsValue, actualSavingsValue),"miles calculated do not match");
    }

    @And("selects {string} as the state")
    public void selectsAsTheState(String stateName) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        this.stateName=stateName;
        callMethod(cls,obj,"navigateToView","Range");
        callMethod(cls, obj, "clickOnLearnMore");
        callMethod(cls,obj,"getChargingModalSection","Savings");
        callMethod(cls,obj,"selectState",stateName);

    }

    @When("user clicks on {string} section in nav bar")
    public void userClicksOnSectionInNavBar(String sectionName) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        globalExpectedSectionName=sectionName;
        globalActualSectionName= (String) callMethod(cls,obj,"navigateToSectionUsingNavBar",sectionName);
    }

    @Then("verify that user lands on the same section")
    public void verifyThatUserLandsOnTheSameSection() {
        assertEquals(globalActualSectionName,globalExpectedSectionName);

    }
}
