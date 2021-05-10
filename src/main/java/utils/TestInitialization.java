package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class TestInitialization {
    private static final Logger LOG = LogManager.getLogger(TestInitialization.class);
    private static TestReport testReport;
    private static boolean isInit;

    static {
        isInit = false;
    }

    private TestInitialization() {

    }

    public static synchronized void init() {
        if (!isInit) {

            TestReport.init();
            testReport = new TestReport();
            isInit = true;
        }
    }

    public static TestReport getInstance() {
        return testReport;
    }

}