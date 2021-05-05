package Steps;

import Polestar.DataMembers.ChargeData;
import Polestar.DataMembers.FuelPrices;
import Polestar.DataMembers.RangeData;
import UtilsTest.Utils;
import UtilsTest.apiCall;
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
import utils.TestInitialization;
import utils.TestReport;

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




public class Steps extends Utils {
    final private static String polestar2PageTitle = "Polestar 2 – The 100% electric car | Polestar US";
    private static ArrayList<String> expectedCallout, actualCallout = new ArrayList<>();
    private static RemoteWebDriver driver;
    private static Class<?> cls;
    private static List<FuelPrices> fuelPrices;
    private static Object obj;
    private static String globalChargingSectionName;
    private final static int batterySize=78;
    private static double globalPowerOutput;
    private static TestReport testReport;
    private static final double polestar2EnergyConsumption=0.193;
    private static final double fuelVehicleEnergyConsumption=0.083;
    private static final double weeksInYear=52.1775;
    private  String stateName=null;
    private ChargeData chargeData;
    private static List<Map<String, String>> chargingInfo;
    private static String globalActualSectionName;
    private static String globalExpectedSectionName;
    private static String globalServicePointName;
    private static String globalSpaceName;
    private static List<String> globalExpectedServicePointHeadings;
    private static List<String> globalActualServicePointHeadings;
    private static List<String> globalExpectedSpacesHeadings;
    private static List<String> globalActualSpacesHeadings;
    private static List<String> globalActualSectionNamex;
    private static List<String> globalExpectedSectionNamex;
    private static List<String> globalActualModals;
    private static List<String> globalExpectedModals;
    private List<RangeData> rangeData1;

    private static List<LocalTime> expectedTimes;
    private static List<LocalTime> actualTimes;
    private static final Logger LOG = LogManager.getLogger(Steps.class);




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
        callMethod(cls, obj, "readData", "src/test/resources/excel.xlsx", section);
        expectedCallout = (ArrayList<String>) callMethod(cls, obj, "extractCalloutFromExcel");
        actualCallout = (ArrayList<String>) callMethod(cls, obj, "getTextOfElements", section);
    }

    @Then("if all the callouts are matching")
    public void ifAllTheCalloutsAreMatching() {
        assertEquals(actualCallout, expectedCallout, "Callouts are not matching");
    }

    @And("verify that all the links are valid")
    public void verifyLinkValid() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        assertTrue((Boolean) callMethod(cls,obj,"verifyAllLinksAreValid"));
    }



    @Then("nav bar highlight is on {string}")
    public void navBarHighlightIsOn(String section) {

    }

    @When("clicks on Learn More under section")
    public void clicksOnUnderSection(DataTable sectionName) {
        globalExpectedModals=sectionName.asList(String.class);
        globalActualModals=new ArrayList<>();
        globalExpectedModals.stream().forEach(s->{
            try {
                callMethod(cls,obj,"navigateToView",s);
                callMethod(cls, obj, "clickOnLearnMore");
                globalActualModals.add((String) callMethod(cls,obj,"onModal"));
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }});
    }

    @When("clicks on Learn More under {string} section")
    public void clicksOnUnderSection(String sectionName) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        callMethod(cls,obj,"navigateToView",sectionName);
        callMethod(cls, obj, "clickOnLearnMore");
    }

    @Then("Verify the user lands on modal")
    public void verifyTheUserLandsOnModal() {
        assertEquals(globalActualModals.size(),globalExpectedModals.size(),"Unable to open modal");
    }

    @Then("Verify that all the tab headings are clickable")
    public void verifyThatAllTheTabHeadingsAreClickable() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        assertTrue((Boolean) callMethod(cls,obj,"ifSectionClickable"),"Unable to click on all the modals");
    }

    @When("clicks on See More under feature")
    public void clicksOnSeeMoreUnderSection(DataTable feature) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        globalExpectedModals=feature.asList(String.class);
        globalActualModals= new ArrayList<>();
        callMethod(cls,obj,"navigateToView","Exterior");
        globalExpectedModals.forEach(s->{
            try {
                callMethod(cls,obj,"clickOnSeeMore",s);
                testReport.log("User clicks on See More under "+s);
                testReport.logImage(driver.getScreenshotAs(OutputType.BASE64));
                Thread.sleep(1000);
                globalActualModals.add((String) callMethod(cls,obj,"onModal"));
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | InterruptedException e) {
                e.printStackTrace();
            }
        });


    }

    @And("clicks on {string} section")
    public void clicksOnSection(String chargingSectionName) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        globalChargingSectionName=chargingSectionName;
        callMethod(cls,obj,"getChargingModalSection",chargingSectionName);
    }

    @And("clicks on {double} kW charger")
    public void clicksOnKWCharger(double powerOutput) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
//        globalPowerOutput=powerOutput;
        callMethod(cls,obj,"clickOnChargerType",globalChargingSectionName, powerOutput);

    }

    @And("slider positions are set at {int} px and {int} px from current position")
    public void sliderPositionsAreSetAtPxAndPx(int startChargePercentage, int endChargePercentage) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        callMethod(cls,obj,"updateSliderPosition",startChargePercentage, endChargePercentage);

    }

    @Then("verify the charge time calculated")
    public void verifyTheChargeTimeCalculated()  {
        SoftAssert softAssert= new SoftAssert();
        for (int i = 0; i <actualTimes.size() ; i++) {
            softAssert.assertEquals(actualTimes.get(i),expectedTimes.get(i),"Time for charging do not match");
        }
        softAssert.assertAll();
    }

    @And("clicks on {string} tab")
    public void clicksOnTab(String tabName) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        callMethod(cls,obj,"clickOnTabInSavings",tabName,globalChargingSectionName);
    }

    @And("user update the slider position")
    public void userSlidesUpto(DataTable slideX) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        List<Integer> slide=slideX.asList(int.class);
        rangeData1= new ArrayList<>();
        slide.stream().forEach(s->{
            try {
                callMethod(cls,obj,"updateSliderPosition", s);
                rangeData1.add((RangeData) callMethod(cls,obj,"calculateMiles"));
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }});
    }

    @Then("verify the miles calculated")
    public void verifyTheMilesCalculated(){
        SoftAssert softAssert= new SoftAssert();
        rangeData1.forEach(s->{
            float expectedValue=0;
            if(s.rangePercentage==100){
                expectedValue= ((s.numberOfCharge)*100*2.33f);
            }
            else expectedValue=  (((s.numberOfCharge-1)*100*2.33f)+((100-s.rangePercentage)*2.33f));
            int actualValue= s.miles;
            int expectedRoundValue= (int) (Math.ceil(expectedValue)==actualValue? Math.ceil(expectedValue):Math.floor(expectedValue));
            softAssert.assertEquals(actualValue,expectedRoundValue,"Miles do not match");
            testReport.log("Calculated Miles for "+s.numberOfCharge+" charge(s) and "+s.rangePercentage +"% is "+ expectedRoundValue + " miles");
            testReport.log("Actual Miles for "+s.numberOfCharge+" charge(s) and "+s.rangePercentage +"% is "+ actualValue+" miles");
        });
        softAssert.assertAll();
    }



    @Then("verify the savings")
    public void verifyTheSavings(){
        SoftAssert softAssert= new SoftAssert();
        if(rangeData1==null){
            rangeData1= new ArrayList<>();
            rangeData1.add(new RangeData());
        }
        rangeData1.stream().forEach(rangeData->{
            FuelPrices actualSavingsValue=null;
            FuelPrices expectedSavingsValue= new FuelPrices();
            double milesToKM=(rangeData.miles==0?233:rangeData.miles)*1;
            try {
                if(stateName==null){
                    if(rangeData!=null)
                        callMethod(cls,obj,"navigateToView","Range");

                        callMethod(cls, obj, "clickOnLearnMore");
                        callMethod(cls,obj,"getChargingModalSection","Savings");
                    }
                actualSavingsValue= (FuelPrices) callMethod(cls,obj,"getSavingsValue", "Savings");
                for (Field field : actualSavingsValue.getClass().getDeclaredFields()) {
                        field.setAccessible(true);
                        testReport.log("Actual "+field.getName()+": "+field.get(actualSavingsValue));
                    }


                    //getting state code and fuel price
                    String stateCode=getStateCode(stateName);
                    FuelPrices price= null;
                        price = apiCall.getFuelPrice(stateCode==null? "US":"US_"+stateCode);


                    expectedSavingsValue.yearCostForPolestar2=Math.round((polestar2EnergyConsumption * weeksInYear * milesToKM) * price.electricityPrice);
                    expectedSavingsValue.yearCostForFuelCar=Math.round((fuelVehicleEnergyConsumption * weeksInYear * milesToKM) * price.fuelPrice);
                    expectedSavingsValue.yearEstimatedFuelSavings= expectedSavingsValue.yearCostForFuelCar-expectedSavingsValue.yearCostForPolestar2;
                    expectedSavingsValue.monthCostForPolestar2=Math.round(((polestar2EnergyConsumption * weeksInYear * milesToKM)/12)*price.electricityPrice);
                    expectedSavingsValue.monthCostForFuelCar=Math.round(((fuelVehicleEnergyConsumption * weeksInYear * milesToKM)/12)*price.fuelPrice);
                    expectedSavingsValue.monthEstimatedFuelSavings=expectedSavingsValue.monthCostForFuelCar-expectedSavingsValue.monthCostForPolestar2;
                    for (Field field : expectedSavingsValue.getClass().getDeclaredFields()) {
                        field.setAccessible(true);
                        testReport.log("Expected "+field.getName()+": "+field.get(expectedSavingsValue));
                    }
                } catch (IOException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
                e.printStackTrace();
            }
            assertTrue(reflectionEquals(expectedSavingsValue, actualSavingsValue),"miles calculated do not match");
        });
    softAssert.assertAll();
    }

    @And("selects {string} as the state")
    public void selectsAsTheState(String state) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        this.stateName=state;
        callMethod(cls,obj,"navigateToView","Range");
        callMethod(cls, obj, "clickOnLearnMore");
        callMethod(cls,obj,"getChargingModalSection","Savings");
        callMethod(cls,obj,"selectState",state);
    }

    @When("user clicks on {string} section in nav bar")
    public void userClicksOnSectionInNavBar(String sectionName) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        globalExpectedSectionName=sectionName;
        globalActualSectionName= (String) callMethod(cls,obj,"navigateToSectionUsingNavBar",sectionName);
    }

    @Then("verify that user lands on the selected section")
    public void verifyThatUserLandsOnTheSelectedSection() {
        SoftAssert softAssert= new SoftAssert();
        for (int i = 0; i < globalActualSectionNamex.size(); i++) {
            softAssert.assertEquals(globalActualSectionNamex.get(i),globalExpectedSectionNamex.get(i));
        }
        softAssert.assertAll();
    }

    @When("user clicks on tab")
    public void userClicksOnTab(DataTable tabName) {
        globalActualSectionNamex=tabName.asList(String.class);
        globalExpectedSectionNamex= new ArrayList<>();
        globalActualSectionNamex.stream().forEach(s->{
            try {
                globalExpectedSectionNamex.add((String) callMethod(cls,obj,"navigateToSectionUsingTabHeading",s));
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }

    @When("clicks on service point under {string} section")
    public void clicksOnServicePointUnderSection(String sectionName, DataTable servicePoints) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        globalExpectedServicePointHeadings= servicePoints.asList(String.class);
        globalActualServicePointHeadings= new ArrayList<>();
        callMethod(cls,obj,"navigateToView",sectionName);
        globalExpectedServicePointHeadings.stream().forEach(s->{
            try {
                globalActualServicePointHeadings.add((String) callMethod(cls, obj, "clickOnServicePoint",s));
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                LOG.error(e);
                try {
                    throw e;
                } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException noSuchMethodException) {
                    noSuchMethodException.printStackTrace();
                }}});
    }

    @Then("Verify the user lands on selected service point modal")
    public void verifyTheUserLandsServicePointModal() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        SoftAssert softAssert= new SoftAssert();
        for (int i = 0; i < globalActualServicePointHeadings.size(); i++) {
            assertEquals(globalActualServicePointHeadings.get(i),globalExpectedServicePointHeadings.get(i),"Unable to open modal");
        }
        softAssert.assertAll();
    }

    @When("clicks on Spaces under {string} section")
    public void clicksOnSpacesUnderSection(String sectionName,DataTable spaces) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        globalExpectedSpacesHeadings= spaces.asList(String.class);
        globalActualSpacesHeadings= new ArrayList<>();
        callMethod(cls,obj,"navigateToView",sectionName);
        globalExpectedSpacesHeadings.stream().forEach(s->{
            try {
                globalActualSpacesHeadings.add((String) callMethod(cls, obj, "clickOnSpace",s));
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                LOG.error(e);
                try {
                    throw e;
                } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException noSuchMethodException) {
                    noSuchMethodException.printStackTrace();
                }}});

    }

    @Then("Verify the user lands on selected Spaces modal")
    public void verifyTheUserLandsOnSelectedSpacesModal() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        SoftAssert softAssert= new SoftAssert();
        for (int i = 0; i < globalActualSpacesHeadings.size(); i++) {
            assertEquals(globalActualSpacesHeadings.get(i),globalExpectedSpacesHeadings.get(i),"Unable to open modal");
        }
        softAssert.assertAll();
    }

    @And("the configuration to set as below")
    public void theConfigurationToSetAsBelow(DataTable data) {
        chargingInfo = data.asMaps();
        expectedTimes= new ArrayList<>();
        actualTimes= new ArrayList<>();
        chargingInfo.stream().forEach(s->{
            try {
                callMethod(cls,obj,"getChargingModalSection",s.get("ChargerType"));
                callMethod(cls,obj,"clickOnChargerType",s.get("ChargerType"), Double.parseDouble(s.get("PowerOutput")));
                callMethod(cls,obj,"updateSliderPosition",Integer.parseInt(s.get("Start%")), Integer.parseInt(s.get("End%")));
                Thread.sleep(1000);
                testReport.log("Output for "+s.get("ChargerType"));
                testReport.logImage(driver.getScreenshotAs(OutputType.BASE64));
                chargeData=(ChargeData) callMethod(cls,obj,"getChargeDuration");
                double duration=((batterySize*0.01)*(chargeData.endChargePercentage- chargeData.startChargePercentage))
                        /(Double.parseDouble(s.get("PowerOutput"))*0.9);
                int expectedHours = (int) Math.floor(duration);
                int expectedMinutes = (int) Math.floor((duration- expectedHours)*60);
                String time=(expectedHours>9)? (expectedHours +":"+expectedMinutes):(0+Integer.toString(expectedHours)+":"+expectedMinutes);
                expectedTimes.add(LocalTime.parse(time));
                actualTimes.add(LocalTime.parse(chargeData.estimatedChargeTime.split(" ")[1].contains("hour")? chargeData.estimatedChargeTime.split(" ")[0]
                        :"00:".concat(chargeData.estimatedChargeTime.split(" ")[0])));
                String[] split = chargeData.estimatedChargeTime.split(":");

                testReport.log("Calculated Duration- "+expectedHours+":"+expectedMinutes+" hours");
                testReport.log("Actual Duration- "+ chargeData.estimatedChargeTime);
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | InterruptedException e) {
                e.printStackTrace();
            }
        });
    }


    @Given("some shit")
    public void someShit() {
        String str = "18:22";
        LocalTime time = LocalTime.parse(str);
        System.out.println(time);
    }

    @And("user slides upto {int} px")
    public void userSlidesUptoRangePx(int slideX) {
        rangeData1= new ArrayList<>();
            try {
                callMethod(cls,obj,"updateSliderPosition", slideX);
                rangeData1.add((RangeData) callMethod(cls,obj,"calculateMiles"));
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
    }

//    @And("user update the slider position for Savings calculation")
//    public void userUpdateTheSliderPositionForSavingsCalculation(DataTable slideX) {
//        List<Integer> slide= slideX.asList(int.class);
//        fuelPrices= new ArrayList<>();
//        AtomicInteger i= new AtomicInteger();
//        slide.stream().forEach(s->{
//            try {
//                callMethod(cls,obj,"updateSliderPosition", s);
//                int miles =((RangeData) callMethod(cls,obj,"calculateMiles")).miles;
//                callMethod(cls,obj,"navigateToView","Range");
//                callMethod(cls, obj, "clickOnLearnMore");
//                callMethod(cls,obj,"getChargingModalSection","Savings");
//                fuelPrices.add((FuelPrices) callMethod(cls,obj,"getSavingsValue", "Savings"));
//                for (Field field : fuelPrices.get(i.get()).getClass().getDeclaredFields()) {
//                    field.setAccessible(true);
//                    testReport.log("Actual "+field.getName()+": "+fuelPrices.get(i.get()));
//                }
//                fuelPrices.get(i.get()).miles=miles;
//
//
//
//
//                i.getAndIncrement();
//            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
//                e.printStackTrace();
//            }
//
//        });
//    }

//
//    @Then("verify the savings")
//    public void verifyTheSavings1() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, IOException {
//        if(!fuelPrices.isEmpty()){
//            fuelPrices.forEach(actualFuelPrices->{
//                double milesToKM=(actualFuelPrices.miles)*1;
//
//                FuelPrices price= null;
//                    try {
//                        price = apiCall.getFuelPrice("US");
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                FuelPrices expectedSavingsValue= calculateExpenses(milesToKM,price);
//                for (Field field : expectedSavingsValue.getClass().getDeclaredFields()) {
//                    field.setAccessible(true);
//                    try {
//                        testReport.log("Expected "+field.getName()+": "+field.get(expectedSavingsValue));
//                    } catch (IllegalAccessException e) {
//                        e.printStackTrace();
//                    }
//                }
//                assertTrue(reflectionEquals(actualFuelPrices, expectedSavingsValue),"miles calculated do not match");
//            });
//        }
//
//        else {
//            double milesToKM=(233)*1;
//            FuelPrices actualSavingsValue=null;
//            callMethod(cls,obj,"getChargingModalSection","Savings");
//            actualSavingsValue= (FuelPrices) callMethod(cls,obj,"getSavingsValue", "Savings");
//            for (Field field : actualSavingsValue.getClass().getDeclaredFields()) {
//                field.setAccessible(true);
//                testReport.log("Actual " + field.getName() + ": " + field.get(actualSavingsValue));
//            }
//                FuelPrices price= apiCall.getFuelPrice("US");
//
//                FuelPrices expectedSavingsValue= calculateExpenses(milesToKM,price);
//                for (Field field : expectedSavingsValue.getClass().getDeclaredFields()) {
//                    field.setAccessible(true);
//                    testReport.log("Expected "+field.getName()+": "+field.get(expectedSavingsValue));
//            }
//
//        }
//
//        if(rangeData1==null){
//            rangeData1= new ArrayList<>();
//            rangeData1.add(new RangeData());
//        }
//        rangeData1.stream().forEach(rangeData->{
//            FuelPrices actualSavingsValue=null;
//            double milesToKM=(rangeData.miles==0?233:rangeData.miles)*1;
//            try {
//                if(stateName==null){
//                    if(rangeData!=null) {
//                        callMethod(cls,obj,"navigateToView","Range");
//
//                        callMethod(cls, obj, "clickOnLearnMore");
//                        callMethod(cls,obj,"getChargingModalSection","Savings");
//                        actualSavingsValue= (FuelPrices) callMethod(cls,obj,"getSavingsValue", "Savings");
//                    }
//                    for (Field field : actualSavingsValue.getClass().getDeclaredFields()) {
//                        field.setAccessible(true);
//                        testReport.log("Actual "+field.getName()+": "+field.get(actualSavingsValue));
//                    }
//
//                    FuelPrices expectedSavingsValue= new FuelPrices();
//
//                    //getting state code and fuel price
//                    String stateCode=getStateCode(stateName);
//                    FuelPrices price= null;
//                    try {
//                        price = apiCall.getFuelPrice(stateCode==null? "US":"US_"+stateCode);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//
//                    expectedSavingsValue.yearCostForPolestar2=Math.round((polestar2EnergyConsumption * weeksInYear * milesToKM) * price.electricityPrice);
//                    expectedSavingsValue.yearCostForFuelCar=Math.round((fuelVehicleEnergyConsumption * weeksInYear * milesToKM) * price.fuelPrice);
//                    expectedSavingsValue.yearEstimatedFuelSavings= expectedSavingsValue.yearCostForFuelCar-expectedSavingsValue.yearCostForPolestar2;
//                    expectedSavingsValue.monthCostForPolestar2=Math.round(((polestar2EnergyConsumption * weeksInYear * milesToKM)/12)*price.electricityPrice);
//                    expectedSavingsValue.monthCostForFuelCar=Math.round(((fuelVehicleEnergyConsumption * weeksInYear * milesToKM)/12)*price.fuelPrice);
//                    expectedSavingsValue.monthEstimatedFuelSavings=expectedSavingsValue.monthCostForFuelCar-expectedSavingsValue.monthCostForPolestar2;
//                    for (Field field : expectedSavingsValue.getClass().getDeclaredFields()) {
//                        field.setAccessible(true);
//                        testReport.log("Expected "+field.getName()+": "+field.get(expectedSavingsValue));
//                    }
//                }
//            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
//                e.printStackTrace();
//            }
//        });

    }
