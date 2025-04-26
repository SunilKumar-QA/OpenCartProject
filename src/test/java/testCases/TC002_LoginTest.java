package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;

public class TC002_LoginTest extends BaseClass {
	
	
	@Test(groups={"Sanity", "Master"})
	public void verify_login() {
		
		logger.info("Starting TC002_LoginTest");
		
		try 
		{
		HomePage hp = new HomePage(driver);
		hp.clickMyAccount();
		hp.clickLogin();
		
		LoginPage login = new LoginPage(driver);
		login.setEmail(p.getProperty("email"));
		login.setPassword(p.getProperty("password"));
		login.clickLogin();
		
		MyAccountPage macc = new MyAccountPage(driver); 
		boolean targetPage = macc.isMyAccountPageExists();
		Assert.assertEquals(targetPage, true, "Login Failed");
		}
		catch (Exception e) {
			Assert.fail();
		}
		logger.info("Finished TC002_LoginTest");
		}
	
}
