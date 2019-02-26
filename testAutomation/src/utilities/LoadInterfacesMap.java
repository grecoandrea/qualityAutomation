package utilities;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.openqa.selenium.WebDriver;

public class LoadInterfacesMap {

	private Properties properties;
	Logger logger;
	WebDriver driver;

	public LoadInterfacesMap() throws Exception{
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream("main/resources/InterfacesMap.properties");
		properties = new Properties();
		try {
			properties.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}

	public String getInterface(String key) throws Exception {

		String value = null;
		try {
			value = properties.getProperty(key);
		} catch (Exception e) {
			throw e;
		}
		if(value != null) return value;
		return value;

	}
}

