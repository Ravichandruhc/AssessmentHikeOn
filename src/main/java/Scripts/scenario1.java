package Scripts;



import java.util.ArrayList;

import org.testng.annotations.Test;

import POM.AmazonHomePage;

public class scenario1 extends BaseTest
{
	
	@Test
	public void validateTheProduct() throws InterruptedException{
		AmazonHomePage ahp = new AmazonHomePage(driver);
		ahp.clickOnDismiss();
		ahp.setValueToSearchProduct("Wireless Headphones");
		ahp.clickOnSearchBtn();
		ahp.clickOnBtnFourStartAndAbove();
		ahp.handleSlider("50", "200");
		ArrayList<String> a = ahp.productDetails();
		ahp.moveToCSV(a);
		ahp.validateResult(a);
		
    }

}
