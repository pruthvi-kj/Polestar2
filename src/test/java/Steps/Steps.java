package Steps;

import Polestar.Pages.Polestar2;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

import java.util.Set;

public class Steps {
    WebDriver driver;
    static String menu;
    Polestar2 p;
    @Given("User is in {string} page")
    public void user_is_in_page(String url) throws InterruptedException {
        driver = hooks.getDriver();
        driver.get(url);
        p= new Polestar2(driver);
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
        Thread.sleep(1000);
        switch (userPageTitle)
        {
            case "Select your region":
                isVisible=p.isElementVisible(userPageTitle);
                Assert.assertEquals(true,isVisible);

            break;
            default: String driverPageTitle = driver.getTitle();
            Assert.assertEquals(userPageTitle.replace("*","|"),driverPageTitle);

        }
    }
    @Then("when user clicks on back verify that back user lands on Polestar {int} homepage")
    public void when_user_clicks_on_back_verify_that_back_user_lands_on_polestar_homepage(Integer int1) {
        driver.navigate().back();
        String userPageTitle="Polestar 2 – The 100% electric car | Polestar US";
        Assert.assertEquals(userPageTitle,driver.getTitle());
    }

    @Then("when user clicks on close user lands on Polestar {int} homepage")
    public void when_user_clicks_on_close_user_lands_on_polestar_homepage(Integer int1) {
        p.clickOnTheLink("Close");
        boolean isVisible;
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
