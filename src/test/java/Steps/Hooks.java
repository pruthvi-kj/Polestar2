package Steps;

import UtilsTest.Utils;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static UtilsMain.InitiateDriver.*;


public class Hooks extends Utils {

    private static final Logger LOG = LogManager.getLogger(Hooks.class);


    @Before
    public static void beforeMethod(Scenario scenario) {
        List<String> tags = new ArrayList<>(scenario.getSourceTagNames());
        try {
            if (tags.contains("@Desktop")) {
                openWebBrowser();
            } else {
                openMobileBrowser();
            }
        } catch (IOException e) {
            LOG.error(e.getCause());
            throw new RuntimeException(e);
        }
        explicitWait();
    }

}
