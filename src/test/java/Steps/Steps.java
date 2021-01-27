package Steps;

import Polestar.Pages.Polestar2;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import resources.Utils;

public class Steps extends Utils {
    WebDriver driver;
    Polestar2 p;
    static String polestar2PageTitle = "Polestar 2 – The 100% electric car | Polestar US";


    @Given("User lands on {string} page")
    public void user_lands_on_page(String url) throws InterruptedException {
        driver = hooks.getDriver();
        driver.get(url);
        p = new Polestar2(driver);
    }

    @Given("User is in {string} page")
    public void user_is_in_page(String page) {
        waitUntilPageTitle(driver, polestar2PageTitle);
        Assert.assertEquals(polestar2PageTitle, driver.getTitle());
    }

    @When("user navigates to footer")
    public void user_navigates_to_footer() throws InterruptedException {
        p.navigateToFooter();
    }

    @When("clicks on {string}")
    public void clicks_on(String linkName) {
        p.clickOnTheLink(linkName);

    }

    @Then("Verify the user lands on {string}")
    public void verify_the_user_lands_on(String userPageTitle) throws InterruptedException {
        boolean isVisible;
        waitUntilPageTitle(driver, userPageTitle.replace("*", "|"));
        switch (userPageTitle) {
            case "Select your region":
                isVisible = p.isElementVisible(userPageTitle);
                System.out.println(isVisible);
                Assert.assertEquals(true, isVisible);
                break;
            default:
                Assert.assertEquals(userPageTitle.replace("*", "|"), driver.getTitle());
        }
    }

    @Then("when user clicks on back verify that back user lands on Polestar {int} homepage")
    public void when_user_clicks_on_back_verify_that_back_user_lands_on_polestar_homepage(Integer int1) throws InterruptedException {
        driver.navigate().back();
        waitUntilPageTitle(driver, polestar2PageTitle);
        Assert.assertEquals(polestar2PageTitle, driver.getTitle());
    }

    @Then("when user clicks on close user lands on Polestar {int} homepage")
    public void when_user_clicks_on_close_user_lands_on_polestar_homepage(Integer int1) throws InterruptedException {
        p.clickOnTheLink("Close");
        boolean isVisible;
        Thread.sleep(2000);
        isVisible = p.isElementVisible("Select your region");
        Assert.assertEquals(false, isVisible);
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
