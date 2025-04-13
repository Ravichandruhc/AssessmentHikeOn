package POM;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
//import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;



public class AmazonHomePage extends BasePage
{
	
	@FindBy(id="twotabsearchtextbox")
	private WebElement txtBoxSearchProduct;
	
	@FindBy(xpath="//input[@data-action-type='DISMISS']")
	private WebElement btnDismiss;
	
	@FindBy(id="nav-search-submit-button")
	private WebElement btnSearch;
	
	@FindBy(xpath="//i[@class=\"a-icon a-icon-star-medium a-star-medium-4\"]")
	private WebElement btnFourStartAndAbove;
	
	@FindBy(id="p_36/range-slider_slider-item_lower-bound-slider")
	private WebElement lowerSlider;
	
	@FindBy(id="p_36/range-slider_slider-item_upper-bound-slider")
	private WebElement upperSlider;
	
	@FindBy(xpath="//input[@class=\"a-button-input\"]")
	private WebElement btnGo;
	
	@FindBy(xpath="//div[@role=\"listitem\"]//h2/span")
	private WebElement productTitle;
	
	@FindBy(xpath="//div[@role=\"listitem\"]//span[@class=\"a-price-whole\"]")
	private WebElement price;
	
	@FindBy(xpath="//span[@class=\"a-button-inner\"]/a")
	private WebElement btbSeeOptions;
	
	@FindBy(xpath="//span[@class=\"a-button-inner\"]/a")
	private WebElement addToCart;
	
	@FindBy(xpath="//a[contains(@class,'s-pagination-next')]")
	private WebElement nextBtn;
	
	@FindBy(xpath="//span[@id='productTitle']")
	private WebElement title;
	
	@FindBy(xpath="//span[@id='buybox-see-all-buying-choices']//a")
	private WebElement fprice;
	
	@FindBy(xpath="(//span[contains(@title,'out of 5 stars')])[1]")
	private WebElement rating;
	
	@FindBy(xpath="(//span[@id='acrCustomerReviewText'])[1]")
	private WebElement reviews;
	

	public AmazonHomePage(WebDriver driver){
   	 
    	super(driver);
	 	PageFactory.initElements(driver, this);
    }
    
     	
     public void setValueToSearchProduct(String ProductName){
    	 
    	 txtBoxSearchProduct.sendKeys(ProductName);
     		
    }
     
     public void clickOnDismiss(){
    	 
    	 btnDismiss.click();     		
    }
     
      public void clickOnSearchBtn(){
    	 
    	  btnSearch.click();     		
    }
      
      public void clickOnBtnFourStartAndAbove(){
     	 
    	  btnFourStartAndAbove.click();     		
    }
      
      
      
      
      
      public void handleSlider(String startRange, String endRange) throws InterruptedException {
    	  JavascriptExecutor js = (JavascriptExecutor) driver;
          setSliderToPrice(js, lowerSlider, startRange, true); 
          setSliderToPrice(js, upperSlider, endRange, false); 
          }
      
      public static void setSliderToPrice(JavascriptExecutor js, WebElement slider, String targetPrice, boolean increase) throws InterruptedException {
          int step = increase ? 1 : -1;
          for (int i = 0; i < 100; i++) {
              String valueText = slider.getAttribute("aria-valuetext");
              String numericValue = valueText.replaceAll("[^0-9]", "");

             if (numericValue.equals(targetPrice)) {
                  break;
              }
              js.executeScript(
                  "arguments[0].value = parseInt(arguments[0].value) + (" + step + "); arguments[0].dispatchEvent(new Event('change'));",
                  slider
              );
             
          }
      }
      
      public void clickOnBtnGo(){
    	  btnGo.click();    
    	  }
      
      public ArrayList<String> productDetails() throws InterruptedException {
    	  String s = "Product Title, Price, Ratings, Number of reviews";
    	  ArrayList<String> data = new ArrayList<String>();
    	  data.add(s);
		  s=null;
    	  for(int i=1; i<=16; i++) {
    		    WebElement productTitle = driver.findElement(By.xpath("(//div[@role='listitem'])["+i+"]//a[contains(@class,'a-text-normal')]"));
    		    s = productTitle.getText();
    		    s = s.replace(",","|");
    		    
    		    try {
   		    	 WebElement seeOptionsBtn = driver.findElement(By.xpath("(//div[@role='listitem'])["+i+"]//a[text()='See options']"));
	    		    if(seeOptionsBtn.isDisplayed()) {
	    		    	s = s+",Not available as no stock,";
	    		    }
   		    } catch(Exception e) {
   		    	try {
   		    	WebElement price = driver.findElement(By.xpath("((//div[@role='listitem'])["+i+"]//span[@class='a-offscreen'])[1]"));
   		    	String p = price.getAttribute("textContent").trim();
   		    	s = s+","+p+",";
   		    	}
   		    	catch(Exception f) {
   		    		//System.out.println("\n Price: Add to cart button is displayed but no price available \n \n");
   		    		s = s+",Add to cart button available but no price available,";
   		    	}
   		    }
    		    
    		    try {
	    		    WebElement rating = driver.findElement(By.xpath("(//div[@role='listitem'])["+i+"]//a[contains(@class, 'a-popover-trigger') and contains(@aria-label, 'out of 5 stars')]"));
	    		    String r = rating.getAttribute("aria-label");
	    		    String[] rate = r.split(",");
	    		    s = s+rate[0]+",";
    		    } catch(Exception e) {
    		    	s = s+"Not Available,";
    		    }
	    		    
    		    try {
    		    	WebElement reviews = driver.findElement(By.xpath("(//div[@role='listitem'])["+i+"]//span[contains(@class,'s-underline-text')]"));    		    
	    		    //System.out.println("\n Reviews: "+reviews.getText());
    		    	String review =  reviews.getText();
    		    	if (review.contains(",")) {	
    		    	    review = review.replace(",", "");
    		    	}
    		    	
	    		    s = s+review;
    		    } catch(Exception e) {
    		    	s = s+"Not Available";
    		    }	  
	    		   
    		    data.add(s);
    	   s = null;
    	  } 
    	  return data;
    }
      
      public void moveToCSV(ArrayList<String> a) {
    	  String fileName = "output.csv";
    	  try (FileWriter writer = new FileWriter(fileName)) {
              for (String line : a) {
                  writer.write(line + "\n");
              }
              System.out.println("Data written to CSV: " + fileName);
          } catch (IOException e) {
              e.printStackTrace();
          }
    	  
      }
      
      public void validateResult(ArrayList<String> a) throws InterruptedException {
    	  WebElement productTitle = driver.findElement(By.xpath("(//div[@role='listitem'])[1]//a[contains(@class,'a-text-normal')]"));
    	  productTitle.click();
    	  Thread.sleep(3000);
    	  
    	  String flux = title.getText();
    	  flux = flux.replace(",", "|");
    	  
    	  String price = fprice.getText().trim();
    	  System.out.println(price);
    	  if(price.contains("See All Buying Options")) {
    		  flux = flux + ",Not available as no stock,";
    	  }
    	  
    	  String rate = rating.getAttribute("title");;
    	  System.out.println(rate);
    	  flux = flux + rate+",";
    	  
    	  String rev = reviews.getText();
    	  rev = rev.replace(",", "");
    	  String[] rev1 = rev.split(" ");
    	  flux = flux+rev1[0];  	  
    	  
    	  String extractedData = a.get(1);
    	  
    	  System.out.println(flux+"\n"+extractedData+"\n");
    	  
    	  if(extractedData.contains(flux)) {
    		  System.out.println("Extracted CSV data and the product data in site are matching");
    	  }
    	     	  
      }
}
