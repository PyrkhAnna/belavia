package by.htp.belavia.steps;

//import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

import by.htp.belavia.driver.DriverSingleton;
//import by.htp.belavia.entity.PricesMatrix;
import by.htp.belavia.pages.CalendarPricesPage;
import by.htp.belavia.pages.HomePage;

public class Steps {
	private WebDriver driver;
	private final Logger logger = LogManager.getLogger();

	public void initBrowser() {
		driver = DriverSingleton.getDriver();
	}
	public void closeDriver() {
		driver.quit();
	}
	public void fillFormBelavia()
	{
		HomePage homePage = new HomePage(driver);
		homePage.openPage();
		homePage.fillForm();
		logger.info("FormBelavia filled");
	}
	public void readCalendarPrices()
	{
		CalendarPricesPage calendarPricesPage = new CalendarPricesPage(driver);
		calendarPricesPage.openPage();
		calendarPricesPage.catchSeveralWeeks(4);
		System.out.println(calendarPricesPage.getListOfMinPrice());
		logger.info("calendarPricesPage filled ");
	}
}
