package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class BasePage{

	
	/// This class only contains constructor which will be used in every page object class
	// This is parent of all object classes
	WebDriver driver;
	
	public BasePage(WebDriver driver) {
		
		this.driver= driver;
		PageFactory.initElements(driver, this);
	}
}
