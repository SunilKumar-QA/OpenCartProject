package testCases;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;



import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;


public class BaseClass {
	
public static WebDriver driver; // This class is parent of all "TEST" classes
public Logger logger;
public Properties p;
	
	@BeforeClass(groups= {"Sanity","Regression","Master"})
	@Parameters({"os","browser"})
	public void setup(String os, String br) throws IOException, Exception
	{
		//Loading config.properties file
		FileReader file = new FileReader("./src//test//resources//config.properties");
		p = new Properties();
		p.load(file);
		
		logger = LogManager.getLogger(this.getClass());  //generating logs
		
		if(p.getProperty("execution_env").equalsIgnoreCase("remote")) {
			
			DesiredCapabilities cap = new DesiredCapabilities();
			
			if(os.equalsIgnoreCase("windows")) {
				
				cap.setPlatform(Platform.WIN10);
			}
			else if(os.equalsIgnoreCase("linux"))
			{
				cap.setPlatform(Platform.LINUX);
				
			}
			else if(os.equalsIgnoreCase("mac"))
			{
				cap.setPlatform(Platform.MAC);
			}
			else 
			{
				System.out.println("No Matching Os");
				return;
			}
			
			switch(br.toLowerCase())
			{
			case "chrome": cap.setBrowserName("chrome"); break;
			case "edge": cap.setBrowserName("MicrosoftEdge"); break;
			case "firefox": cap.setBrowserName("firefox"); break;
			default: System.out.println("No matching browser"); return;
			}
			//driver=new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"),cap);
			
			URI uri = new URI("http://localhost:4444/wd/hub");
			URL gridUrl = uri.toURL();

			driver = new RemoteWebDriver(gridUrl, cap);
		}
		
		if(p.getProperty("execution_env").equalsIgnoreCase("local")) {
			
			switch(br.toLowerCase()) 
			{
			case "chrome" : driver = new ChromeDriver(); break;
			case "edge" : driver = new EdgeDriver(); break;
			case "firefox" : driver = new FirefoxDriver(); break;
			default : System.out.println("Invalid Browser Name.."); return;
			}
		}
		
		
		// Automatically download and set up ChromeDriver
        //WebDriverManager.chromedriver().setup();
        // Launch Chrome browser
       // driver = new ChromeDriver();
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10)); 
        driver.get(p.getProperty("appURL")); //Reading URL from properties file
        driver.manage().window().maximize();
	}
	
	@AfterClass(groups= {"Sanity","Regression","Master"})
	public void tearDown()
	{
		if (driver != null) {
            driver.quit();
        }
	}
	
	public String randomString() {
		
		String generatedstring = RandomStringUtils.randomAlphabetic(5);
		return generatedstring;
		
		}

	public String randomNumber() {
		
		String generatednumber = RandomStringUtils.randomNumeric(10);
		return generatednumber;
		
		}
	
	public String randomAlphaNumeric() {
		
		String generatedstring = RandomStringUtils.randomAlphabetic(3);
		String generatednumber = RandomStringUtils.randomNumeric(3);
		return (generatedstring+"@"+generatednumber);
		
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
