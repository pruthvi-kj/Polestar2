package Steps;

import Polestar.Pages.Polestar2;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Steps {
    WebDriver driver;
    static String menu;
    Polestar2 p;

//    @Given("User initilize desktop {string} driver")
//    public void user_initilize_desktop_driver(String whichDriver) {
//        System.out.println(whichDriver);
//        switch (whichDriver){
//            case "Chrome":
//                WebDriverManager.chromedriver().setup();
//                ChromeOptions options = new ChromeOptions();
//                options.addArguments("--headless");
//                options.addArguments("window-size=1920,1080");
//                options.addArguments("user-data-dir=target/ChromeData");
//                driver=new ChromeDriver(options);
//                break;
//            case "Firefox":
//                WebDriverManager.firefoxdriver().setup();
//                driver=new FirefoxDriver();
//                break;
//
//        }
//
//        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
//        driver.manage().window().maximize();
//        System.out.println(System.getProperty("browserName"));
//
//    }

    @Given("User lands on {string} page")
    public void user_lands_on_page(String url) throws InterruptedException {
        driver=hooks.getDriver();
        driver.get(url);
        p= new Polestar2(driver);
    }
    @Given("User is in {string} page")
    public void user_is_in_page(String page) throws InterruptedException {
        System.out.println("do nothing");
    }

    @When("user navigates to footer")
    public void user_navigates_to_footer() throws InterruptedException {
        p.navigateToFooter();
    }

    @When("clicks on {string}")
    public void clicks_on(String linkname){
        p.clickOnTheLink(linkname);

    }

    @Then("Verify the user lands on {string}")
    public void verify_the_user_lands_on(String userPageTitle) throws InterruptedException {
        boolean isVisible;
        WebDriverWait wait= new WebDriverWait(driver,3);
        try {
            wait.until(ExpectedConditions.titleContains(userPageTitle.replace("*", "|")));
        }catch (Exception e){}
        switch (userPageTitle) {
            case "Select your region":
                isVisible = p.isElementVisible(userPageTitle);
                System.out.println(isVisible);
                Assert.assertEquals(true, isVisible);
                break;
            default:
                String driverPageTitle = driver.getTitle();
                Assert.assertEquals(userPageTitle.replace("*", "|"), driverPageTitle);

        }
    }
    @Then("when user clicks on back verify that back user lands on Polestar {int} homepage")
    public void when_user_clicks_on_back_verify_that_back_user_lands_on_polestar_homepage(Integer int1) throws InterruptedException {
        driver.navigate().back();
        String userPageTitle="Polestar 2 – The 100% electric car | Polestar US";
        WebDriverWait wait= new WebDriverWait(driver,3);
        try {
            wait.until(ExpectedConditions.titleContains(userPageTitle.replace("*", "|")));
        }catch (Exception e){
        }
        Assert.assertEquals(userPageTitle,driver.getTitle());

    }

    @Then("when user clicks on close user lands on Polestar {int} homepage")
    public void when_user_clicks_on_close_user_lands_on_polestar_homepage(Integer int1) throws InterruptedException {
        p.clickOnTheLink("Close");
        boolean isVisible;
        Thread.sleep(2000);
        isVisible=p.isElementVisible("Select your region");
        Assert.assertEquals(false,isVisible);
    }

    @When("user navigates to header")
    public void user_navigates_to_header() {
        p.clickOnTheLink("Header Menu");
    }

    @When("user moves the mouse to {string}")
    public void user_moves_the_mouse_to(String Menu) throws InterruptedException {
        p.moveCursorTo(Menu);
    }



}
