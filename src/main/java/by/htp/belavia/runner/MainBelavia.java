package by.htp.belavia.runner;

import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.htp.belavia.steps.Steps;

public class MainBelavia {
	private static final Logger logger = LogManager.getLogger();
	public static void main(String[] args) {
		ResourceBundle.getBundle("log4j2");
		Steps steps = new Steps();
		steps.initBrowser();
		steps.fillFormBelavia();
		steps.readCalendarPrices();
		
		logger.info("Finished");
		//steps.closeDriver();
	}
}
