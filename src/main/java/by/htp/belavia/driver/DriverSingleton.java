package by.htp.belavia.driver;

import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DriverSingleton {
	private static WebDriver driver;
	private static final Logger logger = LogManager.getLogger();
    private static final String GECKODRIVER = "webdriver.gecko.driver";
    private static final String GECKODRIVER_PATH = "d:\\selenium\\drivers\\geckodriver.exe";
   
    
    private DriverSingleton(){};

	public static WebDriver getDriver(){
		if (driver==null){
			System.setProperty(GECKODRIVER, GECKODRIVER_PATH);
			driver =  new FirefoxDriver();
			driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
			driver.manage().timeouts().setScriptTimeout(20, TimeUnit.SECONDS);
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.manage().window().maximize();
			logger.info("Browser started");
		}
		return driver;
	}
	
	public static void closeDriver(){
		driver.quit();
		driver=null;
		logger.info("Browser closed");
	}
}
