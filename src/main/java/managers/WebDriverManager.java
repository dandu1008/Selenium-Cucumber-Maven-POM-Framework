package managers;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import enums.DriverType;
import enums.EnvironmentType;

public class WebDriverManager {
	private WebDriver driver;
	private static DriverType driverType;
	private static EnvironmentType environmentType;
	
	public WebDriverManager()
	{
		driverType = FileReaderManager.getInstance().getConfigReader().getBrowser();
		environmentType = FileReaderManager.getInstance().getConfigReader().getEnvironment();
	}
	
	public WebDriver getDriver(){
		if(driver == null) driver = createDriver();
		return driver;
	}
	
	public WebDriver createDriver(){
		switch(environmentType){
		case LOCAL : driver = createLocalDriver();
		    break;
		case REMOTE : driver = createRemoteDriver();
		    break;
		}
		return driver;
	}
	
	private WebDriver createLocalDriver() {
		switch(driverType){
		case FIREFOX : driver = new FirefoxDriver();
		    break;
		case CHROME : driver = new ChromeDriver();
		    break;
		case INTERNETEXPLORER : driver = new InternetExplorerDriver();
		}
		
		if(FileReaderManager.getInstance().getConfigReader().getBrowserWindowSize()) 
			driver.manage().window().maximize();
		    driver.manage().timeouts().implicitlyWait(FileReaderManager.getInstance().getConfigReader().getImplicitlyWait(), TimeUnit.SECONDS);
		return driver;
	}

	private WebDriver createRemoteDriver(){
		throw new RuntimeException("RemoteWebDriver is not yet implemented");
	}
	
	public void quitDriver() {
		driver.close();
		driver.quit();
	}

}
