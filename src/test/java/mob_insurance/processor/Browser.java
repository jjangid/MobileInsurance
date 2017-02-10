package mob_insurance.processor;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Duration;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Sleeper;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Function;
import mob_insurance.common.teststep.InsuranceField;
import mob_insurance.io.ManageLocator;

public class Browser {
	private WebDriver wDriver=null;
	String project_path = System.getProperty("user.dir");
	
	
	public WebDriver getDriver(){
		return wDriver;
	}
	
   public Browser(String browserType) throws Exception{
		
		  System.out.println("Info: Opening "+browserType+" browser.");
		   // Initializing browser
		   String standardBrowserType;
			if (browserType == null || browserType.trim().length() == 0) {
				standardBrowserType="FIREFOX";
	       }else 
	    	   standardBrowserType = browserType.trim().toUpperCase();
			if (standardBrowserType.equals("FIREFOX")) {
			   System.setProperty("webdriver.gecko.driver",project_path+"\\src\\test\\resources\\driver\\wires.exe");	
			   DesiredCapabilities capabilities=DesiredCapabilities.firefox();
			   capabilities.setCapability("marionette", true);
	           wDriver = new FirefoxDriver(capabilities);
	       }else if (standardBrowserType.equals("CHROME")) 
			{
				String webdriver_chrome_driver=project_path+"\\src\\test\\resources\\driver\\chromedriver.exe";
				if(webdriver_chrome_driver.trim().length() == 0)
					throw new Exception("Error: Driver path has not specified.");							
				
				ChromeOptions options = new ChromeOptions();
				options.addArguments("--disable-extensions");
				options.addArguments("test-type");
				System.setProperty("webdriver.chrome.driver",webdriver_chrome_driver);
				wDriver=new ChromeDriver(options);
	       }else if(standardBrowserType.equals("IE")){
	    	   DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
	    	   capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
	    	   capabilities.setCapability("requireWindowFocus", true);
	    	   String webdriver_IE_driver=project_path+"\\src\\test\\resources\\driver\\IEDriverServer.exe";
	    	   if(webdriver_IE_driver.trim().length() == 0)
					throw new Exception("Error: Driver path has not specified.");
	    	   System.setProperty("webdriver.ie.driver", webdriver_IE_driver);
	    	   wDriver=new InternetExplorerDriver(capabilities);
	       }
			if(wDriver==null)
			   throw new Exception("Error: You can run test script only on Firefox and Chrome browser. Support is unavailable for other browsers. ");
			
			wDriver.manage().window().maximize();
			wDriver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);	
		
	}
	
	public void tearDown(){
		try {
			Sleeper.SYSTEM_SLEEPER.sleep(new Duration(5, TimeUnit.SECONDS));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    	wDriver.close();
    	wDriver.quit();
    	wDriver=null;
    }
	
	public void waitForWhile(int seconds){
		try {
			Sleeper.SYSTEM_SLEEPER.sleep(new Duration(seconds, TimeUnit.SECONDS));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public WebElement findElement(String locatorType,String locatorValue){
		  WebElement element=null;
		  try{
		     element=waitUnit(getByReference(locatorType, locatorValue));
		  }catch(NoSuchElementException | ElementNotVisibleException e){
			  throw e;
		  }
		  return element;		   
	}
	
	public WebElement findElement(String locatorType,String locatorValue, WebElement wElement){
		WebElement element=null;
		  try{
		     element=wElement.findElement(getByReference(locatorType, locatorValue));
		  }catch(NoSuchElementException | ElementNotVisibleException e){
			  throw e;
		  }
		  return element;
	} 
	
	public List<WebElement> findElements(String locatorType,String locatorValue){
    	List<WebElement> lsElement=null;
		lsElement=waitForElements(getByReference(locatorType, locatorValue));
		return lsElement;
    }
	
	public WebElement waitUntilElementToBeVisible(String locatorType,String locatorValue){
		WebElement wElement=null;
		wElement=(new WebDriverWait(wDriver, 30)).until(ExpectedConditions.visibilityOfElementLocated(getByReference(locatorType, locatorValue)));
		return wElement;		
	}
	
	private WebElement waitUnit(By locator){
		   Wait<WebDriver> wait = new FluentWait<WebDriver>(wDriver)
				                  .withTimeout(30, TimeUnit.SECONDS)
				                  .pollingEvery(5, TimeUnit.SECONDS)
				                  .ignoring(NoSuchElementException.class)
		                          .ignoring(ElementNotVisibleException.class);

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
	 
	public void getURL(String URL){
		  wDriver.get(URL);
	  }
	 
    public void waitForPageLoad(){
	    	WebDriverWait wait=new WebDriverWait(wDriver,20);
//	    	/ wait for Javascript to load
	        ExpectedCondition<Boolean> isPageLoaded = new ExpectedCondition<Boolean>() {
	          @Override
	          public Boolean apply(WebDriver driver) {
	            return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString().equals("complete");
	          }
	        };
	        
	        wait.until(isPageLoaded);
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
	    
	public void waitForElementToBeInvisible(String locatorType,String locatorValue){
	    	By locator=getByReference(locatorType,locatorValue);
	    	new WebDriverWait(wDriver, 40).until(ExpectedConditions.invisibilityOfElementLocated(locator));
	}
	    
	public void waitForElementToBeInvisible(WebElement webElement){	    	
         	WebDriverWait wait =new WebDriverWait(wDriver, 60);
	    	wait.until(new ExpectedCondition<Boolean>(){public Boolean apply(WebDriver webDriver) {return !webDriver.switchTo().activeElement().getText().equalsIgnoreCase(webElement.getText());}
			});
	}
	    
	public boolean isClickable(WebElement wElement)      
	{
		    try
		    {
		       WebDriverWait wait = new WebDriverWait(wDriver, 10);
		       wait.until(ExpectedConditions.elementToBeClickable(wElement));
		       return true;
		    }
		    catch (Exception e)
		    {
		      return false;
		    }
	    }

	public boolean waitUntilElementEnabledWithCSS(WebElement wElement){
	    	WebDriverWait wait =new WebDriverWait(wDriver, 30);
	    	return wait.until(new ExpectedCondition<Boolean>(){public Boolean apply(WebDriver webDriver) {
	    	       String eleClass=wElement.getAttribute("class");
	    	       if(!eleClass.toLowerCase().contains("disabled")){
	    	    	  return true;
	    	       }
	    	        return false;
	    	    }
			});
	    }

//    public String[] interactWithElementBasedOnType(String elementType,String fieldTitle,String elementName,String locatorKey,Object eleValue){
	public String[] interactWithElementBasedOnType(InsuranceField objInsuranceField){
    	String[] testResult=new String[3];
    	testResult[0]="Enter "+objInsuranceField.fieldTitle+" as "+objInsuranceField.testDataValue+".";
    	
    	try{
	    	String[] locator=ManageLocator.getLocator((objInsuranceField.insuranceType+"."+objInsuranceField.fieldName).toUpperCase().trim());
	    	if(locator == null){
	    		testResult[1]="Failed";
		    	testResult[2]="Unable to find element locator for "+objInsuranceField.fieldName+" field.";	    	  
		    	System.out.println("Info: Unable to find element locator for "+objInsuranceField.fieldName+" field.");
		    	return testResult;	
	    	}
	    	
//	    	if(locator.length < 3){	    	  
//	    	  testResult[1]="Failed";
//	    	  testResult[2]="Method can only be used if type has specified as a part of locator in property file.";	    	  
//	    	  System.out.println("Method can only be used if type has specified as a part of locator in property file.");
//	    	  return testResult;
//	    	}
	    	
	    	WebElement wElement=null;
	    	
	    	
	    	switch(objInsuranceField.fieldType.toLowerCase()){
	    	  case "combobox":
	    		              wElement=this.findElement(locator[0],locator[1]);
	    		              testResult[0]="Choose "+objInsuranceField.fieldTitle+" as "+objInsuranceField.testDataValue+".";
	    		              locator=ManageLocator.getLocator("Create.Contract.ContractFeature.Dropdown.Option");
	    		              wElement.click();
	    		              locator[1]=locator[1].replaceAll("@optionText", objInsuranceField.testDataValue);    		              
	    		              WebElement eleDWOption=this.findElement(locator[0],locator[1]);
	    		              eleDWOption.click();
	    		              testResult[1]="Passed";
	    		              break;
	    		
	    	  case "textarea":
	    	  case "datefield":
	    	  case "textfield":
	    		              wElement=this.findElement(locator[0],locator[1]);
	    		              testResult[0]="Enter "+objInsuranceField.fieldTitle+" as "+objInsuranceField.testDataValue+".";
	    		              wElement.clear();
		    		          wElement.sendKeys(objInsuranceField.testDataValue);
		    		          testResult[1]="Passed";
		    		          break;
	    	  case "checkbox":
	    		              wElement=this.findElement(locator[0],locator[1]);
	    		              testResult[0]="Select "+objInsuranceField.fieldTitle+" as "+objInsuranceField.testDataValue+".";
	    		              if(wElement.isSelected() != Boolean.valueOf(objInsuranceField.testDataValue)){
	    		            	  wElement.click();
	    		              } 
	    		              testResult[1]="Passed";
	    		              break;
	    	  case "radiogroup":
	    		              locator[1]=locator[1].replaceAll("@buttonLabel",objInsuranceField.testDataValue);
	    		              wElement=this.findElement(locator[0],locator[1]);
				              testResult[0]="Select "+objInsuranceField.fieldTitle+" as "+objInsuranceField.testDataValue+".";
				              wElement.click();
				              testResult[1]="Passed";
				              break;
//	    	  case "click":
//	    		              wElement=this.findElement(locator[0],locator[1]);
//	    		              testResult[0]="Click "+fieldName+".";
//	    		              wElement.click();
//	    		              testResult[1]="Passed";
//	    		              break;
	    	  default:
				    		  testResult[0]="Enter "+objInsuranceField.fieldTitle+" as "+objInsuranceField.testDataValue+".";
					    	  testResult[1]="Failed";
					    	  testResult[2]="Method supports only combobox/textarea/datefield/textfield/checkbox/radiogroup type element.";	
	    		              System.out.println("Error: Method supports only combobox/textarea/datefield/textfield/checkbox/radiogroup type element.");
	    		              return testResult;
	    	}
    	}catch(Exception e){
    		testResult[1]="Failed";
	    	testResult[2]="Error occured while interacting with field on UI.";
    		return testResult;
    	}
    	return testResult;
    }

}
