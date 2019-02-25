package test;

import utilities.*;
import org.testng.annotations.*;

import org.junit.*;

public class TestExample extends PageObjectGlobal {

    Randomizer randomizer;
    DateCalculator dateCalculator;
    Logger logger;

    private void loaderAllConfigurations() throws Exception {
        loadConfigurations = new utilities.LoadConfigurations();
        loadInterfacesMap = new utilities.LoadInterfacesMap();
        randomizer = new utilities.Randomizer();
        dateCalculator = new utilities.DateCalculator();
    }

    @BeforeMethod @Before
    public void beforeMethod() throws Exception {
        loaderAllConfigurations();
        Logger.createExtentReport(this.getClass().getSimpleName());
        Logger.startTestSuiteLogs(this.getClass().getSimpleName());
        Logger.assignCategoryToSuite("Regression", this.getClass().getSimpleName());
    }

    @AfterMethod @After
    public void afterMethod() throws Exception {
        Logger.closeExtentReport();
        Logger.endTestSuiteLogs(this.getClass().getSimpleName());
        driver.quit();
    }

    @org.junit.Test @org.testng.annotations.Test
    public void testCaseTemplate_Success() throws Exception {
        Logger.startTestCaseLogs();
        try {
            String objectValue = loadInterfacesMap.getInterface("OBJECT_KEY");
            String resultRandom = randomizer.getRandomResultInTable();
            String date = dateCalculator.retDateSixMonthsEarly();
            loginCCMS();
            //TEST ACTIONS
            logoutCCMS();
            Logger.successLogs();
        } catch (Exception e) {
            Logger.failureLogs(e);
        }
    }
}
