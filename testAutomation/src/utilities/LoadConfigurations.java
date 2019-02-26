package utilities;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class LoadConfigurations {

	private Properties properties;

	public LoadConfigurations(){
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream("main/resources/Configurations.properties");
		properties = new Properties();
		try {
			properties.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}

	public String getConfiguration(String conf) throws Exception {
		String value = null;
		try {
			value = properties.getProperty(conf);
		} catch (Exception e) {
			throw e;
		}
		if(value != null) return value;
		return value;
	}

	public String getImplicitWait() throws Exception {
		String conf = "IMPLICIT_WAIT_TIMEOUT";
		String value = properties.getProperty(conf);
		if(value != null) return value;

		else {
			Log4jLogger.error("CONFIGURATION: '"+conf+"' not found in the 'Configurations.properties' file.");
			throw new RuntimeException("CONFIGURATION: '"+conf+"' not found in the 'Configurations.properties' file.");
		}
	}
}