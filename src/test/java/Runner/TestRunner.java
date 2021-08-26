package Runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features = "src/test/java/Features",
        tags = "@Login",
        glue = "Steps",
        plugin = {"pretty", "html:target/Destination", "de.monochromata.cucumber.report.PrettyReports:target/cucumber",
                "json:target/jsonReport/cucumber.json", "Listener.ListenerPlugin"}
)


public class TestRunner extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }

}
