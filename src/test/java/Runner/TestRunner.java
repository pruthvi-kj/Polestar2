package Runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features ="src/test/java/Features" ,
        glue ="Steps",
        plugin = {"pretty", "html:target/Destination", "de.monochromata.cucumber.report.PrettyReports:target/cucumber","json:target/jsonReport/cucumber.json"},
        tags = "@Footer"
        )
public class TestRunner {

}
