package mob_insurance.scripts;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Function;
import mob_insurance.io.ManageLocator;

public class Demo {
	WebDriver wDriver=null;
	Map<String,String> mapTestData=null;
	
	public Demo(String browserType,Map<String,String> mapTestDataRow) throws Exception{
		
//		  TestData objTestData=new TestData();
//    	  System.out.println("Info: Reading Test Data from "+testDataFile+" file.");
    	  mapTestData=mapTestDataRow;
    	  
    	  if(mapTestData.isEmpty()){
    		  System.out.println("Info: Test Data is empty hence aborted the test execution.");
    		  return; 
    	  }
    	  
    	  String language=mapTestData.get("language")!=null?"EN":mapTestData.get("language");
    	  
		  if(ManageLocator.loadConfigProperties(language)){		   
			   System.out.println("Info: Opening "+browserType+" browser.");
			   // Initializing browser
			   String standardBrowserType;
				if (browserType == null || browserType.trim().length() == 0) {
					standardBrowserType="FIREFOX";
		       }
				else standardBrowserType = browserType.trim().toUpperCase();
				if (standardBrowserType.equals("FIREFOX")) {
		           wDriver = new FirefoxDriver();
		       }
				else if (standardBrowserType.equals("CHROME")) 
				{
					String webdriver_chrome_driver="drivers\\chromedriver.exe";
					if(webdriver_chrome_driver.trim().length() == 0)
						throw new Exception("Error: Driver path has not specified.");							
					
					ChromeOptions options = new ChromeOptions();
					options.addArguments("--disable-extensions");
					options.addArguments("test-type");
					System.setProperty("webdriver.chrome.driver",webdriver_chrome_driver);
					wDriver=new ChromeDriver(options);
		       }
				if(wDriver==null)
				   throw new Exception("Error: You can run test script only on Firefox and Chrome browser. Support is unavailable for other browsers. ");
				
				wDriver.manage().window().maximize();
				wDriver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);		
		  }		   
	   }
	
    public void runTestSuite(){
    	if(wDriver!=null){
	    	System.out.println("Info: Loging into application with credential spcified in Test Data file.");
	    	logIn();
	    	System.out.println("Info: Searching client with specified client email.");
	    	searchClientWithEmail();
	    	System.out.println("Info: Choose contract based on product name.");    	
	    	chooseContract();
    	}
    }
    
    private void logIn(){
    	
//    	String userName="QSAgent@plids.com";
//		String password="Kbn$w42P";
//		String URL="https://mvp-stage.mobilversichert.de";
    	
    	String userName=mapTestData.get("Username");
		String password=mapTestData.get("Password");
		String URL=mapTestData.get("URL");
    	
		wDriver.get(URL);
		
		// Login Page Locators
		// Index 0: Locator Type and Index 1: Locator value
		String[] locatorUserName=ManageLocator.getLocator("Username");
		String[] locatorPWD=ManageLocator.getLocator("Password");
		String[] locatorLoginButton=ManageLocator.getLocator("LoginButton");
		
//		WebElement eleUserName=findElement("xpath", "//label[*/text()='Username:']/following-sibling::*/descendant::input[@name='username']");
//		WebElement elePassword=findElement("xpath", "//label[*/text()='Password:']/following-sibling::*/descendant::input[@name='password']");
//		WebElement btnLogin=findElement("xpath", "//*[text()='Login']");
		
		WebElement eleUserName=findElement(locatorUserName[0],locatorUserName[1]);
		WebElement elePassword=findElement(locatorPWD[0],locatorPWD[1]);
		WebElement btnLogin=findElement(locatorLoginButton[0], locatorLoginButton[1]);
		
		eleUserName.sendKeys(userName);
		elePassword.sendKeys(password);
		btnLogin.click();
		
    	
    }
    
    private void searchClientWithEmail(){
//    	String email="z@gmail.com";
    	String email=mapTestData.get("Client Email");
    	
    	//Search Box locator
    	// Index 0: Locator Type and Index 1: Locator value
    	String[] locatorSearchBox=ManageLocator.getLocator("SearchBox");
    	
//    	WebElement eleSearchBox=findElement("xpath", "//input[@componentid='clientsgridsearchfield']");
    	WebElement eleSearchBox=findElement(locatorSearchBox[0],locatorSearchBox[1]);
    	
    	eleSearchBox.sendKeys(email);
    	eleSearchBox.sendKeys(Keys.ENTER);
    } 
    
    private void chooseContract(){
        // Get all locator required for this method
    	String[] locatorSearchResult=ManageLocator.getLocator("SearchResult");
    	String[] locatorSearchResultClient=ManageLocator.getLocator("SearchResult.Client.ExpandButton");
    	String[] locatorLoader=ManageLocator.getLocator("Loader");
    	String[] locatorSearchResultContract=ManageLocator.getLocator("SearchResult.Client.Contract");
    	String[] locatorSearchResultContractProduct=ManageLocator.getLocator("SearchResult.Client.Contract.Product");
    	
    	WebElement eleExpandClient=null;
    	try{
//    		findElement("xpath", "//input[@componentid='clientsgridsearchfield']/following::table[contains(@data-boundview,'gridview')]");
    		findElement(locatorSearchResult[0],locatorSearchResult[1]);
    	}catch(NoSuchElementException exception){
    		System.out.println("Error: Searched client has not found with specified email address. Further process has been aborted.");
    		tearDown();
    		return;
    	}catch(TimeoutException exception){
    		System.out.println("Error: Searched client has not found with specified email address. Further process has been aborted.");
    		tearDown();
    		return;
    	}
    	try{
//    		eleExpandClient=findElement("xpath", "//input[@componentid='clientsgridsearchfield']/following::table[contains(@data-boundview,'gridview')]/descendant::div[@role='presentation']");
    		eleExpandClient=findElement(locatorSearchResultClient[0],locatorSearchResultClient[1]);
    	}catch(NoSuchElementException exception){
    		System.out.println("Error: No contract attached with this serached client. Further process has been aborted.");
    		tearDown();
    		return;
    	}catch(TimeoutException exception){
    		System.out.println("Error: No contract attached with this serached client. Further process has been aborted.");
    		tearDown();
    		return;
    	}
    	
    	try{
    		eleExpandClient.click();
    	}catch(org.openqa.selenium.StaleElementReferenceException ex){
    		eleExpandClient=findElement(locatorSearchResultClient[0],locatorSearchResultClient[1]);
    		eleExpandClient.click();
    	}
//    	waitForElementToBeInvisible(By.xpath("//div[text()='Loading...']"));
    	waitForElementToBeInvisible(getByReference(locatorLoader[0],locatorLoader[1]));
    	try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//    	List<WebElement> lsContract=findElements("xpath", "//input[@componentid='clientsgridsearchfield']/following::table[contains(@data-boundview,'gridview')]/descendant::table[contains(@data-boundview,'gridview')]");
    	List<WebElement> lsContract=findElements(locatorSearchResultContract[0], locatorSearchResultContract[1]);
//    	String contractProductName="n.n.";
    	String contractProductName=mapTestData.get("Contract Product");
    	WebElement eleExpectedContract=null;
    	
    	for(WebElement eleContract:lsContract){
//    		WebElement eleProductName=eleContract.findElement(By.xpath("./descendant::td[3]/div"));
    		WebElement eleProductName=eleContract.findElement(getByReference(locatorSearchResultContractProduct[0],locatorSearchResultContractProduct[1]));
    		
    		String productName=eleProductName.getText();
    		if(contractProductName.equalsIgnoreCase(productName)){
    			eleExpectedContract=eleContract;
    			break;
    		}
    	}
    	if(eleExpectedContract != null){
    		eleExpectedContract.click();
			System.out.println("Message: Successfully able to access and click specified contract("+contractProductName+").");
		}else{
			System.out.println("Info: Specified contract has not found. Hence unable to view contract details.");
		}
    	
        tearDown();	
    }
    
    public void tearDown(){
    	wDriver.close();
    	wDriver.quit();
    	wDriver=null;
    }
    
    private WebElement findElement(String locatorType,String locatorValue){
		   WebElement element=null;
		  switch(locatorType.toLowerCase()){
			  case "xpath":
				         element=waitUnit(By.xpath(locatorValue));
				         break;
			  case "id":
				         element=waitUnit(By.id(locatorValue));
				         break;
			  case "linktext":
				         element=waitUnit(By.linkText(locatorValue));
				         break;
			  case "name":
				         element=waitUnit(By.name(locatorValue));
				         break;
				          
		  }
		  return element;
		   
	   }
    
    private List<WebElement> findElements(String locatorType,String locatorValue){
    	List<WebElement> lsElement=null;
		  switch(locatorType.toLowerCase()){
			  case "xpath":
				  lsElement=waitForElements(By.xpath(locatorValue));
				         break;
			  case "id":
				  lsElement=waitForElements(By.id(locatorValue));
				         break;
			  case "linktext":
				  lsElement=waitForElements(By.linkText(locatorValue));
				         break;
			  case "name":
				  lsElement=waitForElements(By.name(locatorValue));
				         break;
				          
		  }
		  return lsElement;
    }
    
    
    
    
    private WebElement waitUnit(By locator){
		   Wait<WebDriver> wait = new FluentWait<WebDriver>(wDriver)
				                  .withTimeout(30, TimeUnit.SECONDS)
				                  .pollingEvery(5, TimeUnit.SECONDS)
				                  .ignoring(NoSuchElementException.class);

		    WebElement foo =wait.until(new Function<WebDriver, WebElement>(){
		    	public WebElement apply(WebDriver driver) {
		    		 
		    	    return driver.findElement(locator);
		    	 
		    	    }
		    }) ;
	     return foo;
	   }

    private List<WebElement> waitForElements(By locator){
		   Wait<WebDriver> wait = new FluentWait<WebDriver>(wDriver)
				                  .withTimeout(30, TimeUnit.SECONDS)
				                  .pollingEvery(5, TimeUnit.SECONDS)
				                  .ignoring(NoSuchElementException.class);

		    List<WebElement> foo =wait.until(new Function<WebDriver, List<WebElement>>(){
		    	public List<WebElement> apply(WebDriver driver) {
		    		 
		    	    return driver.findElements(locator);
		    	 
		    	    }
		    }) ;
	     return foo;
	   }

    private By getByReference(String locatorType,String locatorValue){
    	switch(locatorType.toLowerCase()){
		  case "xpath":
			             return By.xpath(locatorValue);
			         
		  case "id":
			             return By.id(locatorValue);
			        
		  case "linktext":
			             return By.linkText(locatorValue);
			         
		  case "name":
			             return By.name(locatorValue);
			         
	      default : return null;          
	  }
    }
    
    private void waitForElementToBeInvisible(By locator){
    	new WebDriverWait(wDriver, 10).until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }
//    private void waitForPageLoad(){
//    	WebDriverWait wait=new WebDriverWait(wDriver,20);
////    	/ wait for Javascript to load
//        ExpectedCondition<Boolean> isPageLoaded = new ExpectedCondition<Boolean>() {
//          @Override
//          public Boolean apply(WebDriver driver) {
//            return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString().equals("complete");
//          }
//        };
//        
//        wait.until(isPageLoaded);
//    }
//    
    

}
