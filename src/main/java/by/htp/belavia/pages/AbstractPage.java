package by.htp.belavia.pages;

import org.openqa.selenium.WebDriver;

public abstract class AbstractPage {
	protected static WebDriver driver;
	
	public abstract void openPage();
		
	@SuppressWarnings("static-access")
	public AbstractPage(WebDriver driver) {
		this.driver = driver;
	}
}
