package utilities;

import java.io.File;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import com.gargoylesoftware.htmlunit.BrowserVersion;

public class PageObjectGlobal {

	public WebDriver driver;
	protected utilities.LoadConfigurations loadConfigurations;
	protected utilities.LoadInterfacesMap loadInterfacesMap;
	protected Screenshot screenshot;
	Logger logger;
	
	public PageObjectGlobal() {
		super(); 
	}

	protected void openBrowser(String browser) throws Exception {
		String browserToOpen = loadConfigurations.getConfiguration(browser);
		String waitImplicit = loadConfigurations.getImplicitWait();
		Logger.startCommandLogs(browser, browserToOpen);
		if (browserToOpen.equals("InternetExplorer")) {
			System.setProperty("webdriver.ie.driver", System.getProperty("user.dir")+"/BrowsersConfig/IEDriverServer.exe");
			DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
			capabilities.setCapability(driver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			capabilities.setCapability(driver.NATIVE_EVENTS, false);
			capabilities.setCapability(driver.REQUIRE_WINDOW_FOCUS, true);
			capabilities.setCapability(driver.ENABLE_ELEMENT_CACHE_CLEANUP, true);
			driver = new InternetExplorerDriver(capabilities);
			driver.manage().timeouts().pageLoadTimeout(Long.valueOf(waitImplicit).longValue(), TimeUnit.SECONDS);
			driver.manage().timeouts().implicitlyWait(Long.valueOf(waitImplicit).longValue(), TimeUnit.SECONDS);  
		}
		else if (browserToOpen.equals("Firefox")) {
			System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")+"/BrowsersConfig/geckodriver.exe");
			DesiredCapabilities capabilities = DesiredCapabilities.firefox();
			capabilities.setCapability("setAcceptUntrustedCertificates", true); 
			capabilities.setCapability("setAssumeUntrustedCertificateIssuer", false);
			driver = new FirefoxDriver();
			driver.manage().timeouts().pageLoadTimeout(Long.valueOf(waitImplicit).longValue(), TimeUnit.SECONDS);
			driver.manage().timeouts().implicitlyWait(Long.valueOf(waitImplicit).longValue(), TimeUnit.SECONDS);  
		}
		else if (browserToOpen.equals("Chrome")) {
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/BrowsersConfig/chromedriver.exe");
			DesiredCapabilities capabilities = DesiredCapabilities.chrome();
			capabilities.setCapability("requireWindowFocus", true);
			capabilities.setCapability("applicationCacheEnabled", false);
			driver = new ChromeDriver();
			driver.manage().timeouts().pageLoadTimeout(Long.valueOf(waitImplicit).longValue(), TimeUnit.SECONDS);
			driver.manage().timeouts().implicitlyWait(Long.valueOf(waitImplicit).longValue(), TimeUnit.SECONDS);  
		}
		else if (browserToOpen.equals("ChromeLinux")) {
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/BrowsersConfig/chromedriver");
			DesiredCapabilities capabilities = DesiredCapabilities.chrome();
			capabilities.setCapability("requireWindowFocus", true);
			driver = new ChromeDriver();
		}
		else if (browserToOpen.equals("Phantom")) {
			File file = new File(System.getProperty("user.dir")+"/BrowsersConfig/phantomjs");				
			System.setProperty("phantomjs.binary.path", file.getAbsolutePath());		
			driver = new PhantomJSDriver();
		}
		else if (browserToOpen.equals("HTMLUnit")) {

			DesiredCapabilities capabilities = DesiredCapabilities.htmlUnit();
			capabilities.setJavascriptEnabled(true);
			capabilities.setBrowserName("htmlunit");
			capabilities.setVersion("internet explorer");
			capabilities.setPlatform(org.openqa.selenium.Platform.ANY);			
			capabilities.setJavascriptEnabled(true);
			driver = new HtmlUnitDriver(BrowserVersion.INTERNET_EXPLORER);
			driver.manage().timeouts().pageLoadTimeout(Long.valueOf(waitImplicit).longValue(), TimeUnit.SECONDS);
			driver.manage().timeouts().implicitlyWait(Long.valueOf(waitImplicit).longValue(), TimeUnit.SECONDS);  
		}
		else {
			Exception e = null; 
			Logger.errorCommandLogs(browser, browserToOpen, driver, e);
		}
	}

	protected void openPage(String url) throws Exception {
		String UrlToOpen = loadConfigurations.getConfiguration(url);
		Logger.startCommandLogs(url, UrlToOpen);
		try {
			driver.get(UrlToOpen);
			driver.manage().window().maximize();
		} 
		catch (Exception e) {
			Logger.errorCommandLogs(url, UrlToOpen, driver, e);
			throw e;
		}
	}

	protected void click(String element) throws Exception {
		String elementToClick = loadInterfacesMap.getInterface(element);
		Logger.startCommandLogs(element, elementToClick);
		try {
			if (elementToClick.contains("//")) {
				WebElement myDynamicElement = (new WebDriverWait(driver, 10))
						.until(ExpectedConditions.elementToBeClickable(By.xpath(elementToClick)));
				myDynamicElement.click();
				waitForInteraction();
			}
			else {
				WebElement myDynamicElement = (new WebDriverWait(driver, 10))
						.until(ExpectedConditions.elementToBeClickable(By.id(elementToClick)));
				myDynamicElement.click();
				waitForInteraction();
			}
			waitForInteraction();

		} catch (Exception e) {
			Logger.errorCommandLogs(element, elementToClick, driver, e);
			throw e;
		}
	}
	
	protected void clickByIdProcessed(String element) throws Exception {
		//String elementToClick = loadInterfacesMap.getInterface(element);
		Logger.startCommandLogs(element, "Undefined");
		try {
			WebElement myDynamicElement = (new WebDriverWait(driver, 10))
					.until(ExpectedConditions.elementToBeClickable(By.id(element)));
			myDynamicElement.click();
			waitForInteraction();
		} catch (Exception e) {
			Logger.errorCommandLogs(element, "Undefined", driver, e);
			throw e;
		}
	}

	protected void clickByVisibleText(String element) throws Exception {
		String message = loadInterfacesMap.getInterface(element);
		Logger.startCommandLogs(element, message);
		boolean check = false ;
		try {
			String elementToSearch = "//*[contains(text(), '"+message+"')]";
			WebElement myDynamicElement = (new WebDriverWait(driver, 10))
					.until(ExpectedConditions.elementToBeClickable(By.xpath(elementToSearch)));
			myDynamicElement.isDisplayed();
			String textFound = myDynamicElement.getText();
			check = textFound.equals(message);
			myDynamicElement.click(); 
			waitForInteraction();
		} catch (Exception e) {
			Logger.errorCommandLogs(element, message, driver, e);
			throw e;
		}
	}
	
	protected void clickText(String element) throws Exception {
		Logger.startCommandLogs("Undefined", element);
		boolean check = false ;
		try {
			String elementToSearch = "//*[contains(text(), '"+element+"')]";
			WebElement myDynamicElement = (new WebDriverWait(driver, 10))
					.until(ExpectedConditions.elementToBeClickable(By.xpath(elementToSearch)));
			myDynamicElement.isDisplayed();
			String textFound = myDynamicElement.getText();
			check = textFound.equals(element);
			myDynamicElement.click(); 
			waitForInteraction();
		} catch (Exception e) {
			Logger.errorCommandLogs("Undefined", element, driver, e);
			throw e;
		}
	}

	protected void selectRandomValueIntoATable(String element, String values) throws Exception {
		String elementToClick = loadInterfacesMap.getInterface(element);
		String allvalues = loadInterfacesMap.getInterface(values);
		Logger.startMultipleCommandLogs(element, values);
		try {
			Random random = new Random();
			String[] optionList = allvalues.split(",");
			int randomIndex = random.nextInt(optionList.length); 
			String optionToChoice = optionList[randomIndex];
			String elementToSearch = "//table[@id='"+elementToClick+"']//tbody/tr/td[2]/div [contains(text(), '"+optionToChoice+"')]"; 												
			WebElement myDynamicElement = (new WebDriverWait(driver, 10))
					.until(ExpectedConditions.elementToBeClickable(By.xpath(elementToSearch)));
			myDynamicElement.click();
			waitForInteraction();
		} catch (Exception e) {
			Logger.errorMultipleCommandLogs(element, values, driver, e);
			throw e;
		}
	}
		
	protected void comboboxValueByXpath(String elementId, String option) throws Exception {
		String comboId = loadInterfacesMap.getInterface(elementId);
		String comboValue = loadInterfacesMap.getInterface(option);
		Logger.startMultipleCommandLogs(elementId, option);
		try {
			WebElement myDynamicElementPosition = (new WebDriverWait(driver, 10))
					.until(ExpectedConditions.elementToBeClickable(By.id(comboId)));
			myDynamicElementPosition.click();
			waitForInteraction();

			WebElement myDynamicElementSelection = (new WebDriverWait(driver, 10))
					.until(ExpectedConditions.elementToBeClickable(By.xpath(comboValue)));
			myDynamicElementSelection.click();
			waitForInteraction();
		} catch (Exception e) {
			Logger.errorMultipleCommandLogs(elementId, option, driver, e);
			throw e;
		}
	}

	protected void dropdownSelectionRandom(String combobox, String dropdown) throws Exception {
		String comboboxButtonId = loadInterfacesMap.getInterface(combobox);
		String dropdownButtonId = loadInterfacesMap.getInterface(dropdown);
		Logger.startMultipleCommandLogs(combobox, dropdown);
		try {
			WebElement myDynamicElement = (new WebDriverWait(driver, 10))
					.until(ExpectedConditions.elementToBeClickable(By.id(comboboxButtonId)));
			myDynamicElement.click();
			waitForInteraction();
			Random random = new Random();
			List<WebElement> optionCount= driver.findElements(By.xpath("//*[@id='"+dropdownButtonId+"']/li"));
			int locatorElementSize = optionCount.size();
			

			int randomElement = random.nextInt(locatorElementSize);
			if (randomElement == 0) {
				randomElement = randomElement + 1;
			}
			else if(randomElement == locatorElementSize)
			{
				randomElement= randomElement-1;
			}		
			WebElement myDynamicElementSelection = (new WebDriverWait(driver, 10))
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='"+dropdownButtonId+"']/li["+randomElement+"]")));
			myDynamicElementSelection.click();

		} catch (Exception e) {
			Logger.errorMultipleCommandLogs(combobox, dropdown, driver, e);
			throw e;
		}
	}

	protected void dropdownSelection(String combobox, String element) throws Exception {
		String comboboxButton = loadInterfacesMap.getInterface(combobox);
		String elementToSelect = loadInterfacesMap.getInterface(element);
		Logger.startMultipleCommandLogs(combobox, element);
		try {
			if (comboboxButton.contains("//"))
			{
				WebElement myDynamicElement = (new WebDriverWait(driver, 10))
						.until(ExpectedConditions.elementToBeClickable(By.xpath(comboboxButton)));
				myDynamicElement.click();
			}
			else
			{
			WebElement myDynamicElement = (new WebDriverWait(driver, 10))
					.until(ExpectedConditions.elementToBeClickable(By.id(comboboxButton)));
			myDynamicElement.click();
			}
			WebElement myDynamicElementSelection = (new WebDriverWait(driver, 10))
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[contains(text(), '"+elementToSelect+"')]")));

			myDynamicElementSelection.click();
			waitForInteraction();

		} catch (Exception e) {
			Logger.errorMultipleCommandLogs(combobox, element, driver, e);
			throw e;
		}
	}

	protected void typeText(String element, String text) throws Exception
	{
		String keyToSearch = loadInterfacesMap.getInterface(element);
		Logger.startMultipleCommandLogs(element, text);
		WebElement myDynamicElement;
		try {
			if(keyToSearch.contains("//"))
			{
				myDynamicElement = (new WebDriverWait(driver, 10))
						.until(ExpectedConditions.elementToBeClickable(By.xpath(keyToSearch)));
			}
			else
			{
				myDynamicElement = (new WebDriverWait(driver, 10))
						.until(ExpectedConditions.elementToBeClickable(By.id(keyToSearch)));
			}
			myDynamicElement.click();
			waitForInteraction();
			myDynamicElement.isEnabled();
			myDynamicElement.isSelected();
			myDynamicElement.sendKeys(text);

		} catch (Exception e) {
			Logger.errorMultipleCommandLogs(element, text, driver, e);
			throw e;
		}
	}

	protected void runValidSearch(String searchButton, String resultPosition, String searchedText) throws Exception {
		String startSearchButton = loadInterfacesMap.getInterface(searchButton);
		String resultPositionTable = loadInterfacesMap.getInterface(resultPosition);
		String resultToSearch = "//div[@title='"+searchedText+"']";
		Logger.startMultipleCommandLogs(searchButton, searchedText);
		try {
			WebElement myDynamicElement = (new WebDriverWait(driver, 10))
					.until(ExpectedConditions.elementToBeClickable(By.id(startSearchButton)));
			myDynamicElement.click();
			waitForInteraction();
			
			WebElement myDynamicElementTable = (new WebDriverWait(driver, 10))
					.until(ExpectedConditions.elementToBeClickable(By.id(resultPositionTable)));
			myDynamicElementTable.isDisplayed();

			WebElement myDynamicElementResults = (new WebDriverWait(driver, 10))
					.until(ExpectedConditions.elementToBeClickable(By.xpath(resultToSearch)));
			myDynamicElementResults.isDisplayed();
			myDynamicElementResults.click();
			waitForInteraction();
		} catch (Exception e) {
			Logger.errorMultipleCommandLogs(searchButton, searchedText, driver, e);
			throw e;
		}
	}

	protected void runNotValidSearch(String searchButton, String resultPosition, String searchedText) throws Exception {
		String startSearchButton = loadInterfacesMap.getInterface(searchButton);
		String resultPositionTable = loadInterfacesMap.getInterface(resultPosition);
		Logger.startMultipleCommandLogs(searchButton, searchedText);
		try {
			WebElement myDynamicElement = (new WebDriverWait(driver, 10))
					.until(ExpectedConditions.elementToBeClickable(By.id(startSearchButton)));
			myDynamicElement.click();
			waitForInteraction();
			Boolean myDynamicElementTable = (new WebDriverWait(driver, 10))
					.until(ExpectedConditions.not(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id(resultPositionTable))));
			Assert.assertTrue(myDynamicElementTable);
		} catch (Exception e) {
			Logger.errorMultipleCommandLogs(searchButton, searchedText, driver, e);
			throw e;
		}
	}

	protected void waitForInteraction() throws Exception {
		ExpectedCondition<Boolean> expectation = new
				ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString().equals("complete");
			}
		};
		try {
			Thread.sleep(3000);
			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(expectation);
		} catch (Exception e) {
			throw e;
		}
	}

	protected boolean verifyTextPresentIntoElement(String element, String text) throws Exception {
		String elementIntoSearch = loadInterfacesMap.getInterface(element);
		Logger.startMultipleCommandLogs(element, text);
		boolean check = false ;
		try {
			String elementToSearch = "//*[@id='"+elementIntoSearch+"' and contains(text(), '"+text+"')]";
			WebElement myDynamicElement = (new WebDriverWait(driver, 10))
					.until(ExpectedConditions.elementToBeClickable(By.xpath(elementToSearch)));
			myDynamicElement.isDisplayed();
			String textFound = myDynamicElement.getText();
			check = textFound.equals(text);
				check=true;
			
		} catch (Exception e) {
			Logger.errorMultipleCommandLogs(element, text, driver, e);
			check=false;
			throw e;
		}
		return check;
	}
	
	protected void verifyTextNotPresentIntoElement(String element, String text) throws Exception {
		String elementIntoSearch = loadInterfacesMap.getInterface(element);
		Logger.startMultipleCommandLogs(element, text);
		try {
			String elementToSearch = "//*[@id='"+elementIntoSearch+"' and contains(text(), '"+text+"')]";
			Boolean myDynamicElement = (new WebDriverWait(driver, 10))
					.until(ExpectedConditions.not(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(elementToSearch))));
			Assert.assertTrue(myDynamicElement);
		} catch (Exception e) {
			Logger.errorMultipleCommandLogs(element, text, driver, e);
			throw e;
		}
	}
	
	protected boolean verifyTextPresent(String element) throws Exception {
		String message = loadInterfacesMap.getInterface(element);
		Logger.startCommandLogs(element, message);
		boolean check = false ;
		try {
			String elementToSearch = "//div[contains(text(), '"+message+"')]";
			WebElement myDynamicElement = (new WebDriverWait(driver, 10))
					.until(ExpectedConditions.elementToBeClickable(By.xpath(elementToSearch)));
			myDynamicElement.isDisplayed();
			String textFound = myDynamicElement.getText();
			check = textFound.equals(message);
			check=true;
		} catch (Exception e) {
			Logger.errorCommandLogs(element, message, driver, e);
			check=false;
			throw e;
		}
		return check;
	}

	protected void verifyTextNotPresent(String element) throws Exception {
		String message = loadInterfacesMap.getInterface(element);
		Logger.startCommandLogs(element, message);
		try {
			String elementToSearch = "//div[contains(text(), '"+message+"')]";
			Boolean myDynamicElement = (new WebDriverWait(driver, 10))
					.until(ExpectedConditions.not(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(elementToSearch))));
			Assert.assertTrue(myDynamicElement);
		} catch (Exception e) {
			Logger.errorCommandLogs(element, message, driver, e);
			throw e;
		}
	}

	protected void verifyPresence(String element) throws Exception {
		Logger.startCommandLogs("Undefined", element);
		boolean check = false ;
		try {
			String elementToSearch = "//div[contains(text(), '"+element+"')]";
			WebElement myDynamicElement = (new WebDriverWait(driver, 10))
					.until(ExpectedConditions.elementToBeClickable(By.xpath(elementToSearch)));
			myDynamicElement.isDisplayed();
			String textFound = myDynamicElement.getText();
			check = textFound.equals(element);
		} catch (Exception e) {
			Logger.errorCommandLogs("Undefined", element, driver, e);
			throw e;
		}
	}

	protected void verifyNotPresence(String element) throws Exception {
		Logger.startCommandLogs("Undefined", element);
		try {
			String elementToSearch = "//div[contains(text(), '"+element+"')]";
			Boolean myDynamicElement = (new WebDriverWait(driver, 10))
					.until(ExpectedConditions.not(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(elementToSearch))));
			Assert.assertTrue(myDynamicElement);
		} catch (Exception e) {
			Logger.errorCommandLogs("Undefined", element, driver, e);
			throw e;
		}
	}

	protected void verifyElementPresent(String element) throws Exception {
		String value = loadInterfacesMap.getInterface(element);
		Logger.startCommandLogs(element, value);
		try {
			if (value.contains("//")) {
				WebElement myDynamicElement = (new WebDriverWait(driver, 10))
						.until(ExpectedConditions.elementToBeClickable(By.xpath(value)));
				myDynamicElement.isDisplayed();
			}
			else {

				WebElement myDynamicElement = (new WebDriverWait(driver, 10))
						.until(ExpectedConditions.elementToBeClickable(By.id(value)));
				myDynamicElement.isDisplayed();
			}
		} catch (Exception e) {
			Logger.errorCommandLogs(element, value, driver, e);
			throw e;
		}
	}
	
	protected void verifyElementNotPresent(String element) throws Exception {
		String value = loadInterfacesMap.getInterface(element);
		Logger.startCommandLogs(element, value);
		try {
			if (value.contains("//")) {
				Boolean myDynamicElement = (new WebDriverWait(driver, 10))
						.until(ExpectedConditions.not(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(value))));
				Assert.assertTrue(myDynamicElement);
			}
			else {
				Boolean myDynamicElement = (new WebDriverWait(driver, 10))
						.until(ExpectedConditions.not(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id(value))));
				Assert.assertTrue(myDynamicElement);
			}
		} catch (Exception e) {
			Logger.errorCommandLogs(element, value, driver, e);
			throw e;
		}
	}

	protected void setDate(String position, String date) throws Exception {
		String datePosition = loadInterfacesMap.getInterface(position);
		Logger.startMultipleCommandLogs(position, date);
		try {
			WebElement myDynamicElement = (new WebDriverWait(driver, 10))
					.until(ExpectedConditions.elementToBeClickable(By.id(datePosition)));
			myDynamicElement.click();
			myDynamicElement.getText();
			waitForInteraction();
			myDynamicElement.isEnabled();
			myDynamicElement.isDisplayed();
			myDynamicElement.sendKeys(date);
		} catch (Exception e) {
			Logger.errorCommandLogs(position, date, driver, e);
			throw e;
		}
	}

	protected void deleteContent(String element) throws Exception {
		String keyToSearch = loadInterfacesMap.getInterface(element);
		Logger.startCommandLogs(element, keyToSearch);
		try {
			WebElement myDynamicElement = (new WebDriverWait(driver, 10))
					.until(ExpectedConditions.elementToBeClickable(By.id(keyToSearch)));
			myDynamicElement.click();
			waitForInteraction();
			myDynamicElement.isEnabled();
			myDynamicElement.isSelected();
			myDynamicElement.clear();
		} catch (Exception e) {
			Logger.errorCommandLogs(element, keyToSearch, driver, e);
			throw e;
		}
	}
		
	protected String storeText(String element) throws Exception
	{
		WebElement myDynamicElement;
		String textToSave= null;

		String elementToClick = loadInterfacesMap.getInterface(element);
		Logger.startCommandLogs(element, elementToClick);
		try {
			if (elementToClick.contains("//")) {
				myDynamicElement = (new WebDriverWait(driver, 10))
						.until(ExpectedConditions.elementToBeClickable(By.xpath(elementToClick)));
				waitForInteraction();
			}
			else {
				myDynamicElement = (new WebDriverWait(driver, 10))
						.until(ExpectedConditions.elementToBeClickable(By.id(elementToClick)));
				waitForInteraction();	
			}	
			myDynamicElement.click();
			textToSave= myDynamicElement.getText();
			waitForInteraction();
		} catch (Exception e) {
			Logger.errorCommandLogs(element, elementToClick, driver, e);
			throw e;
		}

		return textToSave;
	}
	
	protected void retrieveTextStored(String element) throws Exception
	{
		WebElement myDynamicElement;		
		String message="//div[contains(text(),'"+element+"')]";
		Logger.startCommandLogs("Undefined", element);
		try {
				myDynamicElement = (new WebDriverWait(driver, 10))
						.until(ExpectedConditions.elementToBeClickable(By.xpath(message)));	
				myDynamicElement.click();
				waitForInteraction();		
			}
		 catch (Exception e) {
			Logger.errorCommandLogs("Undefined", element, driver, e);
			throw e;
		}

	}
	
	protected boolean checkStringPresent(String element) throws Exception {
		String message = loadInterfacesMap.getInterface(element);
		Logger.startCommandLogs(element, message);
		boolean check = false ;
		try {
			String elementToSearch = "//div[contains(text(), '"+message+"')]";
			WebElement myDynamicElement = (new WebDriverWait(driver, 10))
					.until(ExpectedConditions.elementToBeClickable(By.xpath(elementToSearch)));
			myDynamicElement.isDisplayed();
			String textFound = myDynamicElement.getText();
			check = textFound.equals(message);
			check=true;
		} 
		catch (Exception e) {
			Logger.warningCommandLogs(element, message, driver);
			check=false;
		}
		return check;
	}
	
	
	
	protected boolean checkButtonDisabled(String element) throws Exception {
		String message = loadInterfacesMap.getInterface(element);
		Logger.startCommandLogs(element, message);
		boolean check = false ;
		try {
			String elementToSearch = "//button[@id='"+message+"'] and //button[@disabled]";	
			WebElement myDynamicElement = (new WebDriverWait(driver, 10))
					.until(ExpectedConditions.elementToBeClickable(By.xpath(elementToSearch)));
			myDynamicElement.isDisplayed();
			check = myDynamicElement.isEnabled();
			if(check = myDynamicElement.isEnabled())
			check=true;
		} 
		catch (Exception e) {
			Logger.warningCommandLogs(element, message, driver);	
			check=false;
		}
		return check;
	}
	
	protected String retrieveLoggedUser() throws Exception
	{
		WebElement myDynamicElement;
		String message="//tr/td[contains(text(),'CE Branch')]";
		Logger.startCommandLogs("Undefined", message);
		String textFound;
		try
		{
			 myDynamicElement = (new WebDriverWait(driver, 10))
					.until(ExpectedConditions.elementToBeClickable(By.xpath(message)));
		 
			 myDynamicElement.isDisplayed();
				 textFound = myDynamicElement.getText().substring(0, 4);
			waitForInteraction();	
		}
		
		catch (Exception e) {
			Logger.errorCommandLogs("Undefined", message, driver, e);
			throw e;
		}
		return textFound;
	}
	
	
	protected int getRowsNumberInTable(String tableName, String firstCellName, String pairCellName, String notPairCellName) throws Exception
	{
		int rowsNumber=0;
		Logger.startMultipleCommandLogs(tableName, pairCellName);
		try {
			String tableNameId = loadInterfacesMap.getInterface(tableName);
			String firstCellNameId=loadInterfacesMap.getInterface(firstCellName);
			String cellNamePairId = loadInterfacesMap.getInterface(pairCellName);
			String cellNameNotPairId = loadInterfacesMap.getInterface(notPairCellName);
			
			List<WebElement> rows = driver.findElements(By.xpath("//*[@*='"+tableNameId+"']//tr[@*='"+firstCellNameId+"' or @*='"+cellNamePairId+"' or @*='"+cellNameNotPairId+"']"));
			rowsNumber = rows.size();
			waitForInteraction();
		}
		catch (Exception e) {
			Logger.errorMultipleCommandLogs(tableName, pairCellName, driver, e);
			throw e;
		}
		
		return rowsNumber;
	}
		
	protected String selectRandomRowInTable(String tableName, String firstCellName, String pairCellName, String notPairCellName , String field) throws Exception
	{
		String text=null;
		Logger.startMultipleCommandLogs(tableName, field);
		try {
			String tableNameId = loadInterfacesMap.getInterface(tableName);
//			String cellNamePairId = loadInterfacesMap.getInterface(pairCellName);
//			String cellNameNotPairId = loadInterfacesMap.getInterface(notPairCellName);
			String fieldId=loadInterfacesMap.getInterface(field);
			Random random = new Random();
	//		List<WebElement> rows = driver.findElements(By.xpath("//*[@class='"+tableNameId+"']//tr[@class='"+cellNamePairId+"' or @class='"+cellNameNotPairId+"']"));
			//int locatorElementSize = rows.size();
			int locatorElementSize = getRowsNumberInTable(tableName, firstCellName, pairCellName,notPairCellName);
									 
			//System.out.println("rows number: "+ locatorElementSize);
			int randomElement = random.nextInt(locatorElementSize);
			if (randomElement == 0) {
				randomElement = randomElement + 1;
			}
			else if(randomElement == locatorElementSize)
			{
				randomElement= randomElement-1;
			}		
			//System.out.println("row selected number: "+ randomElement);			
			WebElement myDynamicElementSelection = (new WebDriverWait(driver, 10))
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@*='"+tableNameId+"']//*[@id='"+fieldId+"."+randomElement+"']")))	;
			 myDynamicElementSelection.click();
			 text = myDynamicElementSelection.getText();
			 waitForInteraction();
			
		} 
		catch (Exception e) {
			Logger.errorMultipleCommandLogs(tableName, field, driver, e);
			throw e;
		}
		
		return text;
	}
	
	protected void logoutCCMS() throws Exception {
		waitForInteraction();
		clickByVisibleText("LOG_OFF");
		//verifyElementPresentById("USER_FIELD");
		//verifyElementPresentById("PASSWORD_FIELD");
		//verifyTextPresent("CORNER_CARD_EUROPE");
		verifyTextPresent("LOGGED_OUT_TEXT");
	}

	protected void loginCCMS() throws Exception {
		openBrowser("DEFAULT_BROWSER");
		openPage("DEFAULT_URL");
	//	login("DEFAULT_USERNAME", "DEFAULT_PASSWORD");
		clickByVisibleText("CORNER_CARD_EUROPE");
		verifyElementPresent("BUTTON_CONFIRM");
		click("BUTTON_CONFIRM");
	}
	
}

