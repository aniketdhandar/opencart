package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class BasePage {
	
	WebDriver driver;
	public BasePage(WebDriver driver) {   //its a constructor
		
		this.driver=driver;
		PageFactory.initElements(driver, this);   //PageFactory for testNG
    }

}
