package testCases;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import testBase.BaseClass;

import java.io.File;
import java.io.FileReader;

public class TC_001_AccountRegistrationTest extends BaseClass {
	
	static public WebDriver driver;
	public Logger logger;
	public Properties p;
	
	@BeforeClass(groups= {"santiy","regression","master"})
	@Parameters({"os", "browser"})
	public void setup(String os, String br) throws IOException
	
	{
	     //loading properties file
	    FileReader file=new FileReader(".//src//test//resources//config.properties");
		p =new Properties();
		p.load(file);
		
		//loading log4j file
		logger=LogManager.getLogger(this.getClass());//Log4j
		
		
		//launching browser switch case
		switch(br.toLowerCase())
		{
		case "chrome": driver=new ChromeDriver(); break;
		case "edge": driver=new EdgeDriver(); break;
		default: System.out.println("No matching browser..");
					return;
		}
		
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
		driver.get(p.getProperty("appURL")); //read from config properties
		driver.manage().window().maximize();
	}
	
   @AfterClass(groups= {"sanity","regression","master"})
	public void tearDown() {
		
		driver.close();
	}
   
	@Test(groups= {"regression","master"})
	public void verify_account_registration() throws InterruptedException {
		logger.info("***  TC_001 TestStart  ****");
		logger.debug("Application log Started....");
		
		try {
		HomePage hp= new HomePage(driver);
		Thread.sleep(3000);
		hp.clickMyAccount();
		logger.info("Clicked on MyAccount link");

		hp.clickRegister();
		logger.info("Clicked on ClickeRegister link");

	
		AccountRegistrationPage regpage =new AccountRegistrationPage(driver);
		regpage.setFirstName(randomeString().toUpperCase());
		regpage.setLastName(randomeString().toUpperCase());
		regpage.setEmail(randomeString()+"@gmail.com");  //randomly generate email
	//	regpage.setTelephone(randomeNumber());
	     
		String password=randomAlphaNumeric();
		
		regpage.setPassword(password);
	//	regpage.setConfirmPassword(password);
		regpage.setPrivacyPolicy();
		regpage.clickContinue();
		logger.info("clicked on continue..");

		
		String confmsg=regpage.getConfirmationMsg();
        logger.info("Validating expected message..");
		
		Assert.assertEquals(confmsg, "Your Account Has Been Created!","Account creation failed");

		}
		catch(Exception e){
			
			logger.error("test failed..");
			logger.debug("debug logs....");
			Assert.fail();
		}
		
		logger.debug("application logs end.......");
		logger.info("**** finished TC_001_AccountRegistrationTest  *****");
	}
	public String captureScreen(String tname) throws IOException {

		String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
				
		TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
		File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
		
		String targetFilePath=System.getProperty("user.dir")+"\\screenshots\\" + tname + "_" + timeStamp + ".png";
		File targetFile=new File(targetFilePath);
		
		sourceFile.renameTo(targetFile);
			
		return targetFilePath;

	}

}
