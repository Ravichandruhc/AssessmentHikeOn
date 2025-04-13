package Scripts;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;

import io.github.bonigarcia.wdm.WebDriverManager;

public abstract class BaseTest{
	
	public WebDriver driver;
	
	
	@BeforeClass
	public void preCondition()
	{
		
			  WebDriverManager.chromedriver().setup();
			  driver=new ChromeDriver();
		      driver.get("https://www.amazon.com/");
		      driver.manage().window().maximize();
		    

	}
		
		
			
//@AfterTest
//public void postCondition()
//{
//	driver.close();
//}
}
