package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import utilities.DataProviders;

/*Data is valid  - login success - test pass  - logout
	login failed - test fail

Data is invalid - login success - test fail  - logout
	login failed - test pass
*/

public class TC003_LoginDDT extends BaseClass {
	
	@Test(dataProvider="LoginData", dataProviderClass=DataProviders.class, groups="Datadriven") // if dataprovider class is in another package then we have add this extra parameter
	public void verify_loginDDT(String email, String pwd, String exp) 
	{
		logger.info("Starting TC003_LoginDDT");
		
		try
		{
			
		HomePage hp = new HomePage(driver);
		hp.clickMyAccount();
		hp.clickLogin();
		
		LoginPage login = new LoginPage(driver);
		login.setEmail(email);
		login.setPassword(pwd);
		login.clickLogin();
		
		MyAccountPage macc = new MyAccountPage(driver); 
		boolean targetPage = macc.isMyAccountPageExists();
		Assert.assertEquals(targetPage, true, "Login Failed");
		
		if(exp.equalsIgnoreCase("Valid")) 
		{
			if(targetPage==true) 
			{
				macc.clickLogout();
				Assert.assertTrue(true);
				
			}
			else 
			{
				Assert.assertTrue(false);
			}
		}
		
		if(exp.equalsIgnoreCase("Invalid")) 
		{
			if(targetPage==true) 
			{
				macc.clickLogout();
				Assert.assertTrue(false);
			}
			else 
			{
				Assert.assertTrue(true);
			}
		}
		} catch(Exception e) 
		{
			Assert.fail();
		}
		logger.info("Finished TC003_LoginDDT");
			
	
	}
}
