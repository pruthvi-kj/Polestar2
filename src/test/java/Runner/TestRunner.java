package Runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/java/Features",
        glue = "Steps",
        plugin = {"pretty", "html:target/Destination", "de.monochromata.cucumber.report.PrettyReports:target/cucumber",
                "json:target/jsonReport/cucumber.json", "listener.ListenerPlugin"}
)
public class TestRunner extends AbstractTestNGCucumberTests {

}
