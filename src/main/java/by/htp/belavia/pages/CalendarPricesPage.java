package by.htp.belavia.pages;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import by.htp.belavia.entity.PricesMatrix;


public class CalendarPricesPage extends AbstractPage{
	private static final Logger logger = LogManager.getLogger();
	private static final String LINK_TO_NEXT_PRICES_MATRIX_1 = "#matrix div.h-outbound.hidden-xs.clear div.nav-right a";
	private static final String LINK_TO_NEXT_PRICES_MATRIX_2 = "#matrix div.h-inbound.hidden-xs.clear div.nav-left a";
	
	private List<PricesMatrix> calendarPrices = new ArrayList<PricesMatrix>();
	private List<String> outbound = new ArrayList<String>(7);
	private List<String> inbound = new ArrayList<String>(7);
	private List<String> prices = new ArrayList<String>();
	
	
//	@FindBy(css = "#matrix div.h-outbound.hidden-xs.clear>div")
	private List<WebElement> outboundDate;
	@FindBy(css = "#matrix div.h-inbound.hidden-xs.clear>div")
	private List<WebElement> inboundDate;
	@FindAll
	({
		@FindBy(xpath =".//*[@id='matrix']/div[3]/div/div[2]"),
		@FindBy(xpath =".//*[@id='matrix']/div[4]/div/div[3]"),
		@FindBy(xpath =".//*[@id='matrix']/div[5]/div/div[4]"),
		@FindBy(xpath =".//*[@id='matrix']/div[6]/div/div[5]"),
		@FindBy(xpath =".//*[@id='matrix']/div[7]/div/div[6]"),
		@FindBy(xpath =".//*[@id='matrix']/div[8]/div/div[7]"),
		@FindBy(xpath =".//*[@id='matrix']/div[9]/div/div[8]")
	})
	//@FindBy(xpath ="//div[contains(@class,'price')]")
	public List<WebElement> pricesMatrix;
	
//	@FindBy(css = "#matrix div.h-outbound.hidden-xs.clear div.nav-right a")
	//private WebElement linkToNextPricesMatrix;
	
	public CalendarPricesPage (WebDriver driver) {
		super(driver);
		PageFactory.initElements(AbstractPage.driver, this);
	}

	@Override
	public void openPage() {
		driver.navigate().to(HomePage.getLinkToNextPage());
		//driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
		//driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		logger.info("CalendarPricesPage opened");
	}
	private void readPricesMatrixPage() {
		outbound=getOutboundDates();
		inbound =getInboundDates();
		prices=getPrices();
		for (int i=0; i<7; i++) {
			if (!prices.get(i).equals("0.0 EUR")) {
				PricesMatrix oneField = new PricesMatrix(prices.get(i), outbound.get(i),inbound.get(i));
				System.out.println(oneField);
				calendarPrices.add(oneField);
			}
		}
		logger.info("PricesMatrix read");
	}
	private void clickNextPricesMatrixPage() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(LINK_TO_NEXT_PRICES_MATRIX_1)));
		
		driver.findElement(By.cssSelector(LINK_TO_NEXT_PRICES_MATRIX_1)).click();
		logger.info("Click to next PricesMatrix nav-right");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(LINK_TO_NEXT_PRICES_MATRIX_2)));
		driver.findElement(By.cssSelector(LINK_TO_NEXT_PRICES_MATRIX_2)).click();
	
		logger.info("Click to next PricesMatrix nav-left");
	}
	public void catchSeveralWeeks(int amountOfWeeks) {
		try{
			
			for (int i=1; i<amountOfWeeks;i++){
				driver.manage().timeouts().implicitlyWait(6, TimeUnit.SECONDS);
				(new WebDriverWait(driver, 20)).until(new ExpectedCondition<WebElement>(){
					@Override
					public WebElement apply(WebDriver d) {
						return d.findElement(By.xpath(".//*[@id='matrix']/div[9]/div/div[8]"));
					}});
						//ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@id='matrix']/div[9]/div/div[8]")));
				readPricesMatrixPage();
				if (i!=(amountOfWeeks-1)) clickNextPricesMatrixPage();
			}
				
		}catch (StaleElementReferenceException e) {
			System.out.println("Element is not attached to the page document.Error at weeks" + e.getStackTrace());
		}catch (WebDriverException e) {
			System.out.println("WebDriverException" + e.getStackTrace());
		}	
		
	}
	public List<PricesMatrix> getListOfMinPrice() {
		Collections.sort(calendarPrices, new Comparator<PricesMatrix>(){
			public int compare(PricesMatrix o1, PricesMatrix o2) {
                return o1.toString().compareTo(o2.toString());
			}
		});
		List<PricesMatrix> minPrice = new ArrayList<PricesMatrix>();
		PricesMatrix min = calendarPrices.get(0);
		int i=0;
		do{
			minPrice.add(calendarPrices.get(i));
			i++;
			//min=calendarPrices.get(i);
		}while (calendarPrices.get(i).getPrice().equals(min.getPrice())&&i<calendarPrices.size()-1);
		logger.info("minPrice add");
		return minPrice;
	}
	private List<String> getOutboundDates () {
		//даты вылета из Минска 
		String s;
		int i = 0;
		outbound.clear();
		outboundDate= driver.findElements(By.cssSelector("#matrix div.h-outbound.hidden-xs.clear>div"));
		for (WebElement div : outboundDate) {
			if (div.isDisplayed()) {
			s=div.getAttribute("id");
			if (!s.isEmpty()) {
				outbound.add(i, "20"+s.substring(9));
				i++;
			}
			}
		}
		logger.info("OutboundDates read");
		System.out.println(outbound);
		return outbound;
	}
	private List<String> getInboundDates () {
		//даты вылета из Сочи 
		String s;
		int i=0;
		inbound.clear();
		for (WebElement div : inboundDate) {
			s=div.getAttribute("id");
			if (!s.isEmpty()){
				inbound.add(i, "20"+s.substring(8));
				i++;
			}
		}
		logger.info("InboundDates read");
		System.out.println(inbound);
		return inbound;
	}
	private List<String> getPrices () {
		// цены считываем
		String s;
		prices.clear();
		int i=0;
		for (WebElement price : pricesMatrix) {
			if (price.isDisplayed()) {
			if (price.getAttribute("class").startsWith("price")){
				WebElement label = price.findElement(By.tagName("label"));
				s=label.getText();
				prices.add(i, s);
				i++;
				
			} else {
				prices.add(i, "0.0 EUR");
				i++;
				}
		}}
		System.out.println(prices);
		logger.info("Prices read");
		return prices;
	}
	
}
