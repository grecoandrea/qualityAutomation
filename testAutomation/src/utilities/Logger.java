package utilities;

import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class Logger {

	static ExtentTest test;
	static ExtentReports report;
	static DateCalculator dateCalculator = new utilities.DateCalculator();
	public static WebDriver driver;
	public static Exception e;
	public static utilities.LoadConfigurations loadConfigurations = new utilities.LoadConfigurations();
		
	public Logger() {
	}

	public static void startTestSuiteLogs(String testSuite) throws Exception {
		Log4jLogger.startTestSuite(testSuite);
	}

	public static void endTestSuiteLogs(String testSuite) throws Exception {
		Log4jLogger.endTestSuite(testSuite);
	}

	public static void startTestCaseLogs() throws Exception {
		test.log(LogStatus.INFO, "Test Case: "+Thread.currentThread().getStackTrace()[2].getMethodName());
		Log4jLogger.startTestCase(Thread.currentThread().getStackTrace()[2].getMethodName());
	}
	
	public static void successLogs() throws Exception {
		test.log(LogStatus.PASS, "Test Case Passed: "+Thread.currentThread().getStackTrace()[2].getMethodName());
		Log4jLogger.successTestCase(Thread.currentThread().getStackTrace()[2].getMethodName());
	}
	
	public static void failureLogs(Exception e) throws Exception {
		e.printStackTrace();
	}
	
	public static void startCommandLogs(String key, String value) throws Exception {
		Log4jLogger.info("Execute: '"+Thread.currentThread().getStackTrace()[2].getMethodName()+"' with Key: '"+key+"' and Value: '"+value+"'. ");
		test.log(LogStatus.INFO, "Execute: '"+Thread.currentThread().getStackTrace()[2].getMethodName()+"' with Key: '"+key+"' and Value: '"+value+"'. ");
	}
	
	public static void startMultipleCommandLogs(String keyOne, String keyTwo) throws Exception {
		Log4jLogger.info("Execute: '"+Thread.currentThread().getStackTrace()[2].getMethodName()+"' with first Key: '"+keyOne+"' and second Key: '"+keyTwo+"'. ");
		test.log(LogStatus.INFO, "Execute: '"+Thread.currentThread().getStackTrace()[2].getMethodName()+"' with first Key: '"+keyOne+"' and second Key: '"+keyTwo+"'. ");
	}
		
	public static void errorCommandLogs(String key, String value, WebDriver driver, Exception e) throws Exception {
		Log4jLogger.error("ERROR in: '"+Thread.currentThread().getStackTrace()[2].getMethodName()+"' with Key: '"+key+"' and Value: '"+value+"'. ");
		test.log(LogStatus.FAIL, "ERROR in: '"+Thread.currentThread().getStackTrace()[2].getMethodName()+"' with Key: '"+key+"' and Value: '"+value+"'. ");
		test.log(LogStatus.INFO,test.addScreenCapture(Screenshot.capture(driver))+"Screenshot" );
		Screenshot.captureScreenShot(driver);
		failureLogs(e);
	}
	
	public static void warningCommandLogs(String key, String value, WebDriver driver) throws Exception {
		Log4jLogger.warn("WARNING in: '"+Thread.currentThread().getStackTrace()[2].getMethodName()+"' with Key: '"+key+"' and Value: '"+value+"'. ");
		test.log(LogStatus.WARNING, "WARNING in: '"+Thread.currentThread().getStackTrace()[2].getMethodName()+"' with Key: '"+key+"' and Value: '"+value+"'. ");
		test.log(LogStatus.WARNING,test.addScreenCapture(Screenshot.capture(driver))+"Screenshot" );
		Screenshot.captureScreenShot(driver);
		//failureLogs(e);
	}
	
	public static void errorMultipleCommandLogs(String keyOne, String keyTwo, WebDriver driver, Exception e) throws Exception {
		Log4jLogger.error("ERROR in: '"+Thread.currentThread().getStackTrace()[2].getMethodName()+"' with first Key: '"+keyOne+"' and second Key: '"+keyTwo+"'. ");
		test.log(LogStatus.FAIL, "ERROR in: '"+Thread.currentThread().getStackTrace()[2].getMethodName()+"' with first Key: '"+keyOne+"' and second Key: '"+keyTwo+"'. ");
		test.log(LogStatus.INFO,test.addScreenCapture(Screenshot.capture(driver))+"Screenshot" );
		Screenshot.captureScreenShot(driver);
		failureLogs(e);
	}
	
	public static void createScreenshot(WebDriver driver) throws Exception {
		//Log4jLogger.error("ERROR in: '"+Thread.currentThread().getStackTrace()[2].getMethodName()+"' with Key: '"+key+"' and Value: '"+value+"'. ");
		//test.log(LogStatus.FAIL, "ERROR in: '"+Thread.currentThread().getStackTrace()[2].getMethodName()+"' with Key: '"+key+"' and Value: '"+value+"'. ");
		test.log(LogStatus.INFO,test.addScreenCapture(Screenshot.capture(driver))+"Screenshot" );
		Screenshot.captureScreenShot(driver);
		//failureLogs(e);
	}
	
	public static void createExtentReport(String testSuite) throws Exception {
		String loggerName = System.getProperty("LOGGER_NAME", "Results");
		report = new ExtentReports(System.getProperty("user.dir")+"/Report/ExtentReport"+loggerName+"_"+dateCalculator.dateActual()+".html", false);
		test = report.startTest("Test Suite: "+testSuite);
	}
	
	public static void closeExtentReport() throws Exception {
		report.endTest(test);
		report.flush();
		report.close();
	}
	
	public static void assignCategoryToSuite(String macroCategory, String microCategory) {
		String micro = microCategory.substring(0, 7);
		test.assignCategory(macroCategory, micro);
	}
}
