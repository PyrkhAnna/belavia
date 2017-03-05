package by.htp.belavia.pages;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage extends AbstractPage {
	private static final Logger logger = LogManager.getLogger();
	private static final String URL = "https://belavia.by/home/";
	private static final String FLIGHT_FROM_VALUE = "Минск (MSQ), BY";
	private static final String FLIGHT_TO_VALUE = "Сочи (AER), RU";
	private static final String HIDDEN_FIELD_FLIGHT_FROM_ID = "OriginLocation";
	private static final String HIDDEN_FIELD_FLIGHT_TO_ID = "DestinationLocation";
	private static final String HIDDEN_FIELD_FLIGHT_FROM_VALUE = "MSQ";
	private static final String HIDDEN_FIELD_FLIGHT_TO_VALUE = "AER";
	private static final String DEPARTURE_DAY = "10";
	private static final String ARRIVAL_DAY = "17";
	
	@FindBy(id = "OriginLocation_Combobox")
	private WebElement flightFrom;
	@FindBy(id = "DestinationLocation_Combobox")
	private WebElement flightTo;
	@FindBy(id = "JourneySpan_Rt")
	private WebElement journeySpanRt;
	@FindBy(id = "JourneySpan_Ow")
	private WebElement journeySpanOw;
	@FindBy(id = "DepartureDate_Datepicker")
	private WebElement clickDepartureDate;
	@FindBy(id = "calendar")
	private WebElement dates;
	@FindBy(css = "#calendar td[data-month='1'] a")
	private List<WebElement> departureDate;
	@FindBy(css = "#calendar td[data-month='1'] a")
	private List<WebElement> arrivalDate;
	@FindBy(css = "#step-2 div.col-mb-12.col-4.col-offset-8 button.button.btn-large.btn.btn-b2-green.ui-corner-all")
	private WebElement submitButton;
	@FindBy(css = "#outbound div.hdr a")
	private static WebElement linkToNextPage;
	
	public HomePage (WebDriver driver) {
		super(driver);
		PageFactory.initElements(AbstractPage.driver, this);
	}
		
	@Override
	public void openPage() {
			driver.navigate().to(URL);
			logger.info("HomePage opened");
	}
	public void fillForm() {
		flightFrom.sendKeys(FLIGHT_FROM_VALUE);
		flightFrom.click();
		flightTo.sendKeys(FLIGHT_TO_VALUE);
		flightTo.click();
		journeySpanRt.click();
		clickDepartureDate.click();
		setDate(departureDate, DEPARTURE_DAY);
		setDate(arrivalDate, ARRIVAL_DAY);
		inputInHiddenFieldJScript(HIDDEN_FIELD_FLIGHT_FROM_ID, HIDDEN_FIELD_FLIGHT_FROM_VALUE);
		inputInHiddenFieldJScript(HIDDEN_FIELD_FLIGHT_TO_ID, HIDDEN_FIELD_FLIGHT_TO_VALUE);
		submitButton.click();
		//linkToNextPage.click();
		logger.info("Form filled");	
	}
	
	public static String getLinkToNextPage() {
		String s = linkToNextPage.getAttribute("href");
		return s;
	}
	
	private void setDate (List<WebElement> listDates, String day) {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOfAllElements(listDates));
		for (WebElement date : listDates) {
			try {
				if (date.isDisplayed()&&date.getText().equals(day))  date.click();
			} catch (StaleElementReferenceException e) {
					System.out.println("Element is not attached to the page document" + e.getStackTrace());
			}
		}
		logger.info("Date set");	
	}
	
	public void inputInHiddenFieldJScript(String id, String value) {
		try {
			String jS = " var s= document.getElementById('"+id+"');s.type = 'visible';s.value = '"+value+"';";
			((JavascriptExecutor) driver).executeScript(jS, driver.findElement(By.id(id)));
		
		} catch (StaleElementReferenceException e) {
			System.out.println("Element with " + id + "is not attached to the page document" + e.getStackTrace());
			logger.debug("Element is not attached to the page document");
		} catch (NoSuchElementException e) {
			System.out.println("Element " + id + " was not found in DOM" + e.getStackTrace());
			logger.debug("Element was not found in DOM");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error occurred while filling in" + e.getStackTrace());
			logger.debug("Error occurred while filling in");
		}
		logger.info("Hidden field filled");
	}
}
