package mob_insurance.functions;
/**
 * @author IT Technocrats
 *
 */
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Duration;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Sleeper;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import com.google.common.base.Function;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import mob_insurance.io.InsuranceField;
import mob_insurance.io.LoadProperty;
import mob_insurance.io.ManageLocator;
import mob_insurance.listeners.TestResult;

public class CoreRepository extends TestBase {

	private WebDriver driver = null;
	private int flag = 0; // Indicator of pass/ fail i.e. if flag=1
	private Long splittedText;

	/* This method is used to Login in system */
	public boolean Login() throws IOException {
		boolean isLogin = false;
		String testStep="Login into the application.";
		try {
			waitForWhile(5);
			String languageCode=LoadProperty.getVar("loginLanguage", "data");
			selectLanguage(languageCode);
			testStep="Select Language '"+languageCode+"' on Language dropdown.";
			TestResult.addTestResult(testStep,"Passed");

			//enter username
			WebElement userName = findElement("name",LoadProperty.getVar("username", "element"));
			userName.clear();
			userName.sendKeys(LoadProperty.getVar("loginName", "data"));
			testStep="Enter UserName '"+LoadProperty.getVar("loginName", "data")+"' on UserName textbox.";
			TestResult.addTestResult(testStep,"Passed");
			
			//enter password
			WebElement password = findElement("name",LoadProperty.getVar("password", "element"));
			password.clear();
			password.sendKeys(LoadProperty.getVar("loginPassword", "data"));
			testStep="Enter Password '"+LoadProperty.getVar("loginPassword", "data")+"' on Password textbox.";
			TestResult.addTestResult(testStep,"Passed");
			
			//click on login btn
			WebElement loginBtn = findElement("linkText",LoadProperty.getVar("login_Btn", "element"));
			loginBtn.click();
			testStep="Click on Login Button";
			
			TestResult.addTestResult(testStep,"Passed");
		    waitForWhile(5);	
		    
			isLogin=true;
			testStep="Login into the application.";
			TestResult.addTestResult(testStep,"Passed");
						
		} catch (Exception e) {
			e.printStackTrace();
			TestResult.addTestResult(testStep,"Failed","Error occured while login into app.");
		}
		return isLogin;

	}
	
	public boolean Logout(){
		try{
			  WebElement logOutButton = findElement("xpath",LoadProperty.getVar("Button.Logout", "element"));
			  logOutButton.click();
			  TestResult.addTestResult("Logout from application.","Passed");
		}catch(Exception e){
			TestResult.addTestResult("Logout into application","Failed","- Error: Refer output logs for more information.");
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	private boolean selectLanguage(String languageCode) {		 
		 try{
			 
//			 String[] locatorSelectedLng=ManageLocator.getLocator("Login.Language.dropdown.selected");
			 String locatorSelectedLng=LoadProperty.getVar("Login.Language.dropdown.selected","element");
			 WebElement eleSelectedLanguage=findElement("xpath",locatorSelectedLng);
			 			 
			 if(!eleSelectedLanguage.getText().trim().equalsIgnoreCase(languageCode)){			 
				 String locatorLang=LoadProperty.getVar("Login.Language.dropdown","element");
				 WebElement dropdownLanguage=findElement("xpath", locatorLang);
				 int offSetHeight=dropdownLanguage.getSize().getHeight()/2;
				 int offSetWidth=dropdownLanguage.getSize().getWidth()-10;
				 
				 Actions build = new Actions(getDriver());
				 build.moveToElement(dropdownLanguage, offSetWidth, offSetHeight).click().build().perform();
				 
				 
				 String locatorSelectLang=LoadProperty.getVar("Login.Language.dropdown.select","element");
				 locatorSelectLang=locatorSelectLang.replaceAll("@language", languageCode);
				 WebElement selectLanguage=findElement("xpath", locatorSelectLang);
				 selectLanguage.click();
				 System.out.println("Selected language..");
//				 TestResult.addTestResult("Choose Language code as "+languageCode,"Passed");				 
			 }else{
				 System.out.println("Deafualted language is same as sepcified language");
//				 TestResult.addTestResult("Choose Language code as "+languageCode,"Passed","- Defaulted language was same as specified lanaguage code in test data. Hence language code has not choosen again.");
			 }
		 }catch(Exception e){
			 System.out.println("error in language selection");
//			 TestResult.addTestResult("Choose Language code as "+languageCode,"Failed","- Error occured while choosing another language code.");
		     return false;
		 }
		 
		 return true;
	 }
	
	
	/* This method is used to select values from dropdown */
	public boolean SelectValueInDropdown(String value) throws IOException {
		boolean isValueSelected = false;
		try 
		{	new Actions(getDriver()).sendKeys(Keys.ARROW_DOWN).perform();
			WebElement item = getDriver().findElement(By.xpath("//div[contains(@class, 'boundlist')]//ul//li[text() = '" +value +"']"));
		     if (item.isEnabled() && item.isDisplayed()){
		          item.click();
		          isValueSelected=true;
		      }  
			
		} catch (Exception e) {
			e.printStackTrace();
			isValueSelected = false;
		}
		return isValueSelected;
	}
	
	/* This method is used to click on Add/Edit and Save button */
	public boolean ClickOnActionButton(String value) throws IOException {
		boolean isValueClicked = false;
		try 
		{
			value = GetDELanguageText(value);			
			List<WebElement> buttonObj = getDriver().findElements(By.xpath(LoadProperty.getVar("saveBtnList", "element")));
//			System.out.println("Debug log ClickOnActionButton Size==>"+buttonObj.size());
			for (int i = 0; i <buttonObj.size() ; i++) {
			    WebElement item = buttonObj.get(i);
//			    System.out.println("Debug ClickOnActionButton text==>"+item.getText());
				if (item.isDisplayed() && item.isEnabled() && item.getText().equals(value)) {
						item.click();
				        isValueClicked =true;
				        break;
				    }
			}
		  }
		 catch (Exception e) {
			e.printStackTrace();
			isValueClicked = false;
		}
		return isValueClicked;
	}
	
	/* This method is used to GetDELanguageText */
	public String GetDELanguageText(String value) throws IOException {
		try 
		{
			String lang = LoadProperty.getVar("loginLanguage", "data");
			switch(value)
			{
				case "Create": 
					value = lang.equalsIgnoreCase("DE") ? "Anlegen" :"Create";
					break;
				case "Edit":
					value = lang.equalsIgnoreCase("DE") ? "�ndern" :"Edit";
					break;
				case "Save":
					value = lang.equalsIgnoreCase("DE") ? "Speichern" :"Save";
					break;
				case "Account":
					value = lang.equalsIgnoreCase("DE") ? "Konto" :"Account";
					break;
				case "No data to display":
					value = lang.equalsIgnoreCase("DE") ? "Keine Daten vorhanden" :"No data to display";
					break;
				case "Delete":
					value = lang.equalsIgnoreCase("DE") ? "L�schen" :"Delete";
					break;
				case "Send":
					value = lang.equalsIgnoreCase("DE") ? "Senden" :"Send";
					break;
				case "Results":
					value = lang.equalsIgnoreCase("DE") ? "Ergebnisse" :"Results";
					break;
					
				default: 
					 System.out.println("No Keyword found for conversion");
			}		
		  }
		 catch (Exception e) {
			e.printStackTrace();			
		}
		return value;
	}
	
	/*
	 * This method is used for fetching fields in the table. And returns the
	 * result in the form of List
	 * 
	 * 
	 * @return cells_text
	 */
	public List<String> getTableRow(String table_id, int row_number,
			int column_start, int column_end) {
		// System.out.println(table_id);
		List<String> cells_text = new ArrayList<String>();
		WebElement baseTable = getDriver().findElement(By.id(table_id));
		List<WebElement> tableRows = baseTable.findElements(By.tagName("tr"));
		// System.out.println("size " + tableRows.size());
		WebElement row = tableRows.get(row_number);
		for (int column_value = column_start; column_value <= column_end; column_value++) {
			cells_text.add(row
					.findElement(By.xpath("td[" + column_value + "]"))
					.getText());
		}
		System.out.println(cells_text);
		return cells_text;
	}

	/* This method is used to read PDF file */
	public String readPDFData(String pdf_path, int page_num) throws IOException {
		String pdfData = null;
		try {
			PdfReader reader = new PdfReader(pdf_path);
			int n = reader.getNumberOfPages();
			System.out.println("No of pages in PDF" + n);
			pdfData = PdfTextExtractor.getTextFromPage(reader, page_num);
			// Extracting the content from a particular page.
			System.out.println(pdfData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pdfData;

	}

	/*
	 * Function for closing a window Created by: -
	 */
	public int close() {
		try {
			getDriver().close();
			flag = 1;
			return flag;
		} catch (Exception ex) {
			return 0;
		}
	}
	
	/*
	 * Function for verification of value from drop down list or combo box
	 * Created by: -
	 */
	public int verifyValuebyid(String dropDownName, String dropDownValue) {
		try {
			// Thread.sleep(Constants.DeltaConstants.time);
			if (dropDownName != null && dropDownValue != null) {

				if (dropDownValue.indexOf(getDriver().findElement(
						By.name(dropDownName)).getAttribute("value")) != -1) {
					flag = 1;
				} else {
					flag = 0;

				}

			} else
				flag = 0;
			return flag;
		} catch (Exception ex) {
			return 0;
		}
	}
	
	public Long findText(String objectclicked, int location) throws Exception {

		try {
			// Thread.sleep(Constants.DeltaConstants.time);
			String mMiles = getDriver().findElement(By.name(objectclicked))
					.getText();
			String[] arr = mMiles.split(" ");
			String[] arr1 = arr[location].split(",");
			mMiles = "";
			for (int i = 0; i < arr1.length; i++) {
				mMiles = mMiles + arr1[i];
			}
			splittedText = Long.parseLong(mMiles);
		} catch (Exception err) {
			System.out.println("in error" + err.getMessage());
		}
		return splittedText;
	}

	public Long findxpathText(String objectclicked, int location)
			throws Exception {

		try {
			// Thread.sleep(Constants.DeltaConstants.time);
			String mMiles = getDriver().findElement(By.xpath(objectclicked))
					.getText();
			String[] arr = mMiles.split(" ");
			// String[] arr1 = arr[location].split(",");
			String[] arr2 = arr[location].split(":");

			mMiles = "";
			for (int i = 0; i < arr2.length; i++) {
				mMiles = mMiles + arr2[i];
			}
			String nMiles = mMiles.replace(",", "");
			splittedText = Long.parseLong(nMiles);
		} catch (Exception err) {
			System.out.println("in error" + err.getMessage());
		}
		return splittedText;
	}

	public String getElementAttribute(String objectclicked, String type,
			String value) throws Exception {
		String ss = null;
		if (objectclicked != null) {
			if ("css".equalsIgnoreCase(type)) {
				if (getDriver().findElement(By.cssSelector(objectclicked))
						.isEnabled()) {
					ss = getDriver().findElement(By.cssSelector(objectclicked))
							.getAttribute(value);
				}
			} else if ("link".equalsIgnoreCase(type)) {
				if (getDriver().findElement(By.linkText(objectclicked))
						.isEnabled()) {
					ss = getDriver().findElement(By.linkText(objectclicked))
							.getAttribute(value);
				}
			} else if ("name".equalsIgnoreCase(type)) {
				if (getDriver().findElement(By.name(objectclicked)).isEnabled()) {
					ss = getDriver().findElement(By.name(objectclicked))
							.getAttribute(value);
				}
			} else if ("xpath".equalsIgnoreCase(type)) {
				if (getDriver().findElement(By.xpath(objectclicked))
						.isEnabled()) {
					ss = getDriver().findElement(By.xpath(objectclicked))
							.getAttribute(value);
				}
			} else if ("id".equalsIgnoreCase(type)) {
				if (getDriver().findElement(By.id(objectclicked)).isEnabled()) {
					ss = getDriver().findElement(By.id(objectclicked))
							.getAttribute(value);
				}
			}
		}
		return ss;
	}

	/*
	 * Function for checking the existence of an object used as css element by
	 * calling methods isElementPresent() and isVisible() Created by: - Date:-
	 */
	public int isCSSElementPresent(String elementverify) {
		try {
			// Thread.sleep(Constants.DeltaConstants.time);
			if (elementverify != null) {
				if (getDriver().findElement(By.cssSelector(elementverify))
						.isDisplayed()) {
					flag = 1;
				} else {
					flag = 0;
				}
				return flag;
			} else
				flag = 0;
			return flag;
		} catch (Exception ex) {
			return 0;
		}

	}

	/*
	 * Function for handling alert box Created by: -
	 */
	public int assertEquals(String message) {
		try {
			// Thread.sleep(Constants.DeltaConstants.time);
			if (message != null) {
				Alert alert = getDriver().switchTo().alert();
				if (message.equalsIgnoreCase(alert.getText())) {
					flag = 1;
				} else {
					flag = 0;
				}
			} else {
				flag = 0;
			}
			return flag;
		} catch (Exception ex) {
			return 0;
		}
	}

	public int assertaccept() {
		try {
			// Thread.sleep(Constants.DeltaConstants.time);
			getDriver().switchTo().alert().accept();
			return flag = 1;
		} catch (Exception ex) {
			return 0;
		}
	}

	public String getToolTipText(String elementXPath, WebDriver wDriver) {
		Actions action = new Actions(wDriver);
		action.keyDown(Keys.CONTROL).sendKeys(Keys.END).perform();
		action.moveToElement(wDriver.findElement(By.xpath(elementXPath)))
				.build().perform();

		return wDriver.findElement(By.xpath(elementXPath)).getText();

	}

	public void clearField(String objectclicked, String type) {
		if (objectclicked != null) {
			if ("id".equalsIgnoreCase(type)) {
				if (getDriver().findElement(By.id(objectclicked)).isEnabled()) {
					getDriver().findElement(By.id(objectclicked)).click();
					WebElement toClear = getDriver().findElement(
							By.id(objectclicked));
					toClear.sendKeys(Keys.CONTROL + "a");
					toClear.sendKeys(Keys.DELETE);
					flag = 1;
				} else {
					flag = 0;
				}

			}
		}

	}
	
	public void logData(String testcaseName,
			 String testCaseID, String Status, String Comment,Boolean assertEnabled)  {

			 try{
				 				 
				 Reporter.log("Test Case ID : "+testCaseID	+ "  Status : "+Status+ "  Comment : "+	Comment);				 
				 reportPath = project_path	+ "\\src\\test\\resources\\Report";
				 System.out.println("reportPath: '" + reportPath + "'");
				 fWriter = new FileWriter(reportPath+"//MI_Results.html",true);
		            //creating a buffered writer for the file object
	             writer = new BufferedWriter(fWriter);
	             DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
				 // Get the date today using Calendar object.
				 Date today = Calendar.getInstance().getTime();        
				 String reportDate = df.format(today);	
				 sheet_pointer++;
				 if (Status == "Passed")
	             {
		             writer.write("<tr class = 'passColor'>"
		        			+ "<td>" +sheet_pointer + "</td>"//Counter
		        			+ "<td>" +testcaseName + "</td>"//Test Case Name
		        			+ "<td>" + testCaseID + "</td>"//Test Case Id
		        			+ "<td>" + Status + "</td>"//status
		        			+ "<td>" + reportDate  + "</td>"//DateTime
		        			+ "<td>" + Comment  + "</td>");//Comment
	             }
	             else
	             {
		             writer.write("<tr class = 'failColor'>"
		            		+ "<td>" +sheet_pointer + "</td>"//Counter
		        			+ "<td>" +testcaseName + "</td>"//Test Case Name
		        			+ "<td>" + testCaseID + "</td>"//Test Case Id
		        			+ "<td>" + Status + "</td>"//status
		        			+ "<td>" + reportDate  + "</td>"//DateTime
		        			+ "<td>" + Comment  + "</td>");//Comment
		          }
	             writer.close();
	             
	             if(assertEnabled.equals(true))
				 {	
					 if(Status.equalsIgnoreCase("Failed"))
					 {
					 	 Assert.assertEquals("Actual : Failed","Expected : Passed","Reason : "+Comment);
					 }
					 else
					 {
						 Assert.assertEquals("Test Case Passed","Test Case Passed",Comment);						
					 }
				 }				 
			 
			 }
			 catch(Exception e)
			 {
			 e.printStackTrace();
			 }
		}
	
	public boolean isAlertPresent() {
		try {
			getDriver().switchTo().alert();
			return true;
		} // try
		catch (NoAlertPresentException Ex) {
			return false;
		}
	}
	
	public WebDriver getDriver() {
		return driver;
	}

	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}

	public void openURL() {
		String baseUrl = LoadProperty.getVar("baseUrl","data");
		
		getDriver().get(baseUrl);
		TestResult.addTestResult("Open URL as "+baseUrl,"Passed");
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
		wElement=(new WebDriverWait(getDriver(), 30)).until(ExpectedConditions.visibilityOfElementLocated(getByReference(locatorType, locatorValue)));
		return wElement;		
	}
	
	public WebElement waitUntilElementToBeVisible(String locatorType,String locatorValue,int waitTimeinSec){
		WebElement wElement=null;
		wElement=(new WebDriverWait(getDriver(), waitTimeinSec)).until(ExpectedConditions.visibilityOfElementLocated(getByReference(locatorType, locatorValue)));
		return wElement;		
	}
	
	private WebElement waitUnit(By locator){
		   Wait<WebDriver> wait = new FluentWait<WebDriver>(getDriver())
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
		   Wait<WebDriver> wait = new FluentWait<WebDriver>(getDriver())
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
	 
	public void waitForPageLoad(){
	    	WebDriverWait wait=new WebDriverWait(getDriver(),20);
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
		System.out.println("Debug log : locatorType --> "+ locatorType +"  locatorValue  --> "+locatorValue);
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
	    	new WebDriverWait(getDriver(), 40).until(ExpectedConditions.invisibilityOfElementLocated(locator));
	}
	    
	public void waitForElementToBeInvisible(WebElement webElement){	    	
     	WebDriverWait wait =new WebDriverWait(getDriver(), 60);
	    	wait.until(new ExpectedCondition<Boolean>(){public Boolean apply(WebDriver webDriver) {return !webDriver.switchTo().activeElement().getText().equalsIgnoreCase(webElement.getText());}
			});
	}
	    
	public boolean isClickable(WebElement wElement)      
	{
		    try
		    {
		       WebDriverWait wait = new WebDriverWait(getDriver(), 10);
		       wait.until(ExpectedConditions.elementToBeClickable(wElement));
		       return true;
		    }
		    catch (Exception e)
		    {
		      return false;
		    }
	    }

	public boolean waitUntilElementEnabledWithCSS(WebElement wElement){
	    	WebDriverWait wait =new WebDriverWait(getDriver(), 30);
	    	return wait.until(new ExpectedCondition<Boolean>(){public Boolean apply(WebDriver webDriver) {
	    	       String eleClass=wElement.getAttribute("class");
	    	       if(!eleClass.toLowerCase().contains("disabled")){
	    	    	  return true;
	    	       }
	    	        return false;
	    	    }
			});
	    }
	
	public void getURL(String URL){
		getDriver().get(URL);
	}

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
	
	public Object[][] getTestData(String fileName) throws Exception {
		
		// No changed would require as per new structure.
		
		int lastRow = 0; 
		int lastColumn = 0;
		Workbook readbook = null;
		String path=System.getProperty("user.dir");		
		path=path+"\\src\\test\\resources\\TestCases\\"+fileName;
		Reporter.log(path);
		// excel reading with given path
		System.out.println("Test case file path  : " + path);
		try{
		readbook = Workbook.getWorkbook(new File(path));	 	
	 	Sheet testDataSheet = readbook.getSheet(0);
	 	Object[][] tData=null;
	 	
	 	lastRow = testDataSheet.getRows();
	 	System.out.println("lastRow  : " + lastRow);
	 	//Code to avoid reading of blank rows from excel sheet
	 	//Now code will verify whether test case name is exists
	 	//if test case name is blank it will consider it a last 
		for (int trNumber = 1; trNumber < lastRow-1; trNumber++) 
		{
			Cell[] tcRow=testDataSheet.getRow(trNumber+1);
			if((tcRow[0].getContents()=="")||tcRow[0].getContents()==null)
			{
				lastRow=trNumber+1;
				break;
			}	 				
		}
		lastColumn = testDataSheet.getColumns();
	    tData=new Object[lastRow-2][lastColumn];
	    for (int rNumber = 0; rNumber < (lastRow-2); rNumber++) 
	    {
	    	Cell[] cRow=testDataSheet.getRow(rNumber+2);
	    	for (int cNumber = 0; cNumber < cRow.length; cNumber++) 
			{		        	
		    	tData[rNumber][cNumber]=cRow[cNumber].getContents();	 				
	 		}
	     }
	 	readbook.close();
	 	readbook=null;
		return tData;
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	} 
	
	private static String[] Beginning = { "Kr", "Ca", "Ra", "Mrok", "Cru",
		         "Ray", "Bre", "Zed", "Drak", "Mor", "Jag", "Mer", "Jar", "Mjol",
		         "Zork", "Mad", "Cry", "Zur", "Creo", "Azak", "Azur", "Rei", "Cro",
		         "Mar", "Luk" };
	
	private static String[] Middle = { "air", "ir", "mi", "sor", "mee", "clo",
		         "red", "cra", "ark", "arc", "miri", "lori", "cres", "mur", "zer",
		         "marac", "zoir", "slamar", "salmar", "urak" };
	
	private static String[] End = { "d", "ed", "ark", "arc", "es", "er", "der",
		         "tron", "med", "ure", "zur", "cred", "mur" };
	
	private static Random rand = new Random();
	
	public static String generateName() {
		   String name = Beginning[rand.nextInt(Beginning.length)] + 
		            Middle[rand.nextInt(Middle.length)]+
		            End[rand.nextInt(End.length)];
		   
	      return name;
	 }
	   
	public static String generateNumber() {
		   int num1 = (rand.nextInt(7) + 1) * 100 + (rand.nextInt(8) * 10) + rand.nextInt(8);
		   int num2 = rand.nextInt(743);
	       int num3 = rand.nextInt(10000);

	       DecimalFormat df3 = new DecimalFormat("000"); // 3 zeros
	       DecimalFormat df4 = new DecimalFormat("0000"); // 4 zeros

	       String phoneNumber = df3.format(num1) + "" + df3.format(num2) + "" + df4.format(num3);

	       System.out.println(phoneNumber);
		   return phoneNumber;
	   }
	
	public void tearDown() {
		try {
			Sleeper.SYSTEM_SLEEPER.sleep(new Duration(5, TimeUnit.SECONDS));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Ending Test Name: '" + test_name + "'");
		System.out.println("Shuting down driver of Test Name: '" + test_name
				+ "'");
		driver.close();
		driver.quit();
    	driver=null;
		
		System.out.println("Driver quit for Test: '" + test_name + "'");
		System.out.println("\n");
	}
}
