package Test;

import utilities.*;

import org.testng.annotations.*;
import org.junit.*;

public class TestSuiteExample extends PageObjectGlobal {

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
    public void testCaseExample() throws Exception {
        Logger.startTestCaseLogs();
        try {
            openBrowser("DEFAULT_BROWSER");
            openPage("DEFAULT_URL");
            typeText("SEARCH_FORM", "pizza margherita");
            click("SEARCH_BUTTON");
            verifyPresence("Pizza Margherita");
            Logger.successLogs();
        } catch (Exception e) {
            Logger.failureLogs(e);
        }
    }
    
    @org.junit.Test @org.testng.annotations.Test
    public void testCaseExampleRandomic() throws Exception {
        Logger.startTestCaseLogs();
        try {
            String randomParam = randomizer.getRandomSearch();
            openBrowser("DEFAULT_BROWSER");
            openPage("DEFAULT_URL");
            typeText("SEARCH_FORM", randomParam);
            click("SEARCH_BUTTON");
            click("FIRST_SEARCH_RESULT");
            Logger.successLogs();
        } catch (Exception e) {
            Logger.failureLogs(e);
        }
    }
    
    @org.junit.Test @org.testng.annotations.Test
    public void testCaseExampleFromInterface() throws Exception {
        Logger.startTestCaseLogs();
        try {
            String objectValue = loadInterfacesMap.getInterface("OBJECT_OF_SEARCH");
            openBrowser("DEFAULT_BROWSER");
            openPage("DEFAULT_URL");
            typeText("SEARCH_FORM", objectValue);
            click("SEARCH_BUTTON");
            click("FIRST_SEARCH_RESULT");
            Logger.successLogs();
        } catch (Exception e) {
            Logger.failureLogs(e);
        }
    }
    
    
    @org.junit.Test @org.testng.annotations.Test
    public void testCaseExampleWithFailure() throws Exception {
        Logger.startTestCaseLogs();
        try {
            String objectValue = loadInterfacesMap.getInterface("OBJECT_OF_SEARCH");
            openBrowser("DEFAULT_BROWSER");
            openPage("DEFAULT_URL");
            typeText("SEARCH_FORM", objectValue);
            click("SEARCH_BUTTON");
            click("FIRST_SEARCH_RESULT");
            verifyPresence("tortino di mele con zuppa di farro e pesce spada");
            Logger.successLogs();
        } catch (Exception e) {
            Logger.failureLogs(e);
        }
    }
}
