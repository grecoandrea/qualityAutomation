package utilities;

import org.apache.log4j.Logger;

public class Log4jLogger {


	private static Logger Log = Logger.getLogger(Log4jLogger.class.getName());
	
	public static void startTestSuite(String testSuiteName){
		Log.info("██ ██ ██ ██ ██ ██ ██ ██ ██ ██ ██ ██ ██ ██ ██ ██ ██ ██ ██ ██ ██ ██ ██ ██ ██ ██ ██ ██ ██");
		Log.info("------------------------ START Test suite: "+testSuiteName+" ------------------------");
	}

	public static void endTestSuite(String testSuiteName){

		Log.info("------------------------ END Test suite: "+testSuiteName+" ------------------------");
		Log.info("-----------------------------------------------------------------------------------");
		Log.info("-----------------------------------------------------------------------------------");
	}

	public static void startTestCase(String testCaseName){

		Log.info("------ START Test case: "+testCaseName+" ------");
	}


	public static void endTestCase(String testCaseName){

		Log.info("------ END Test case: "+testCaseName+" ------");
	}

	public static void successTestCase(String testCaseName){

		Log.info("------ SUCCESS Test case: "+testCaseName+" ------");
	}


	public static void failedTestCase(String testCaseName){

		Log.error("------ FAILED Test case: "+testCaseName+" ------");
	}


	public static void info(String message) {

		Log.info(message);

	}

	public static void warn(String message) {

		Log.warn(message);

	}

	public static void error(String message) {
		
		Log.error("████████ ERROR ████████ "+message);
		
	}

	public static void fatal(String message) {

		Log.fatal(message);

	}

	public static void debug(String message) {

		Log.debug(message);

	}
	
	

}