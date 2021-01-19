package Steps;

import Polestar.Pages.Polestar2;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;

public class Steps {
    WebDriver driver;
    Polestar2 p;
    @Given("User is in {string} page")
    public void user_is_in_page(String url) {
        driver = hooks.getDriver();
        driver.get(url);
    }

    @When("user navigates to footer")
    public void user_navigates_to_footer() throws InterruptedException {
        p= new Polestar2(driver);
        p.navigateToFooter();
    }

    @When("clicks on {string}")
    public void clicks_on(String linkname){
        p.clickOnTheLink(linkname);
    }

    @Then("Verify the user lands on {string}")
    public void verify_the_user_lands_on(String userPageTitle) throws InterruptedException {
        String driverPageTitle = p.getPageTitle();
        Assert.assertEquals(userPageTitle.replace("*","|"),driverPageTitle);
    }

}
