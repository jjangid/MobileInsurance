/*CreateClientAndContract*/
package mob_insurance.scripts;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import mob_insurance.functions.TestBase;
import mob_insurance.io.InsuranceField;
import mob_insurance.io.LoadProperty;
import mob_insurance.io.ManageLocator;
import mob_insurance.listeners.TestData;
import mob_insurance.listeners.TestResult;
import mob_insurance.functions.CoreRepository;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import com.google.common.base.Predicate;

public class AddClientContractDetails extends TestBase{
	CoreRepository coreFunc=null;
	Boolean assertEnabled = false;
	
	@Test
	public void AddClientAndContract() throws Exception{
	try
		{
			Reporter.log("Test execution has started...");
			int pageTimeOut = Integer.valueOf(LoadProperty.getVar("PageLoadImplicitTimeOutFactor", "config"));
			int elementTimeOut = Integer.valueOf(LoadProperty.getVar("ElementImplicitTimeOutFactor", "config"));
			getDriver().manage().timeouts().pageLoadTimeout(pageTimeOut, TimeUnit.SECONDS);
			getDriver().manage().timeouts().implicitlyWait(elementTimeOut, TimeUnit.SECONDS);
			ManageLocator.loadConfigProperties(LoadProperty.getVar("loginLanguage", "data"));
			//create instance of class core
			coreFunc = new CoreRepository();
			//Assign webdriver driver to class core
			coreFunc.setDriver(this.getDriver());			
			
			//Prepare test data
			Object[][] testData=null;
			testData=coreFunc.getTestData("AddClientContract.xls");
			if (testData == null) {
				System.out.println("File Name is Incorrect");
				Reporter.log("File Name is Incorrect");
				coreFunc.logData("AddClientAndContract", "", "Failed","File Name is Incorrect", assertEnabled);
				Assert.assertEquals(testData, "null");
			}
			System.out.println("TestCaseCount : "+testData.length);
			Reporter.log("TestCaseCount : "+testData.length);
			for(int tcNo=0;tcNo<testData.length;tcNo++)
			{
				Reporter.log("*******************************************************************************************");
				TestResult.resetListTestResult();
				if((testData[tcNo][0])==null){					
					Reporter.log("Test case not found in test data sheet");
					Assert.assertEquals(false, true, "Test case not found in test data sheet");					
				}
				String testname = (String) testData[tcNo][0];
				System.out.println("testname : "+testname);
				TestResult.addTestResult(testname,"");
								
				Reporter.log("S.No: " +tcNo+ " Test execution started for testID: "+testData[tcNo][0]);
				boolean AddClientResult=false;
				boolean AddClientContractResult=false;
				boolean AddClientRelativesResult=false;
				boolean AddClientProfessionResult=false;
				boolean AddClientJobResult=false;
				boolean AddClientRiskResult=false;
				boolean AddClientBankResult=false;
			
				System.out.println("Test Case ID : "+testData[tcNo][0]);
				//Verify 'ExecuteTest' column value to identify whether test case need to execute or not
				if(String.valueOf(testData[tcNo][1]).equalsIgnoreCase("TRUE")){
					
//					Open URL and Login will only be called when Login needs to be done							
					if(String.valueOf(testData[tcNo][2]).equalsIgnoreCase("TRUE")){						
						//navigate to specified URL				
						coreFunc.openURL();
						//Login to application with specified credentials
						if(!coreFunc.Login()){
							TestResult.appendTestResult();
							continue;
						}
					}					
					//Verify 'CreateClient' column value to identify whether client need to create or not
					if(String.valueOf(testData[tcNo][3]).equalsIgnoreCase("TRUE")){
						AddClientResult= AddClient(testData[tcNo]);						
					}
					else{
						AddClientResult=true;
						Reporter.log("Client creation skipped for test case "+testData[tcNo][0]+".");
						System.out.println("Client creation skipped for test case "+testData[tcNo][0]+".");
					}
					
					//Verify 'FamilyDetails' column value to identify whether client need to create or not
					if(String.valueOf(testData[tcNo][41]).equalsIgnoreCase("TRUE")){
						AddClientRelativesResult= AddClientRelatives(testData[tcNo]);						
					}
					else{
						AddClientRelativesResult=true;
						Reporter.log("Client FamilyDetails creation skipped for test case "+testData[tcNo][0]+".");
						System.out.println("Client FamilyDetails creation skipped for test case "+testData[tcNo][0]+".");
					}
					
					//Verify 'ProfessionDetails' column value to identify whether client need to create or not
					if(String.valueOf(testData[tcNo][50]).equalsIgnoreCase("TRUE")){
						AddClientProfessionResult= AddClientProfession(testData[tcNo]);						
					}
					else{
						AddClientProfessionResult=true;
						Reporter.log("Client ProfessionDetails creation skipped for test case "+testData[tcNo][0]+".");
						System.out.println("Client ProfessionDetails creation skipped for test case "+testData[tcNo][0]+".");
					}
					
					//Verify 'JobDetails' column value to identify whether client need to create or not
					if(String.valueOf(testData[tcNo][67]).equalsIgnoreCase("TRUE")){
						AddClientJobResult= AddClientJob(testData[tcNo]);						
					}
					else{
						AddClientJobResult=true;
						Reporter.log("Client JobDetails creation skipped for test case "+testData[tcNo][0]+".");
						System.out.println("Client JobDetails creation skipped for test case "+testData[tcNo][0]+".");
					}
							
					//Verify 'RiskDetails' column value to identify whether client need to create or not
					if(String.valueOf(testData[tcNo][75]).equalsIgnoreCase("TRUE")){
						AddClientRiskResult= AddClientRisk(testData[tcNo]);						
					}
					else{
						AddClientRiskResult=true;
						Reporter.log("Client RiskDetails creation skipped for test case "+testData[tcNo][0]+".");
						System.out.println("Client RiskDetails creation skipped for test case "+testData[tcNo][0]+".");
					}
					
					//Verify 'BankDetails' column value to identify whether client need to create or not
					if(String.valueOf(testData[tcNo][82]).equalsIgnoreCase("TRUE")){
						AddClientBankResult= AddClientBank(testData[tcNo]);						
					}
					else{
						AddClientBankResult=true;
						Reporter.log("Client BankDetails creation skipped for test case "+testData[tcNo][0]+".");
						System.out.println("Client BankDetails creation skipped for test case "+testData[tcNo][0]+".");
					}
					
					//Verify 'CreateContract' column value to identify whether client need to create or not
					if(String.valueOf(testData[tcNo][28]).equalsIgnoreCase("TRUE")){
						AddClientContractResult= AddContract(testData[tcNo]);						
					}
					else{
						AddClientContractResult=true;
						Reporter.log("Client Contract creation skipped for test case "+testData[tcNo][0]+".");
						System.out.println("Client Contract creation skipped for test case "+testData[tcNo][0]+".");
					}			
					
					if(AddClientResult && AddClientContractResult && AddClientRelativesResult && 
							AddClientProfessionResult && AddClientJobResult && AddClientRiskResult  && AddClientBankResult)
					{
						coreFunc.logData("CreateClientAndContract",String.valueOf(testData[tcNo][0]),"Passed","",assertEnabled);
						System.out.println("Test Case"+testData[tcNo][0]+" is passed.");
					}
					
					boolean needToLogOut=Boolean.valueOf(String.valueOf(testData[tcNo][98]));
					if(needToLogOut){
					  coreFunc.Logout();					  
					}
					getDriver().navigate().refresh();
					waitForLoad();
				}
				else
				{
					
					Reporter.log("Test execution of test case "+testData[tcNo][0]+" is skipped");
					System.out.println("Test execution of test case "+testData[tcNo][0]+" is skipped");
					
				}		  			    	
			    Reporter.log("Test execution completed for testID: "+testData[tcNo][0]);
			    Reporter.log("*******************************************************************************************");
			    TestResult.appendTestResult();
			}
			
			
		}
		catch(Exception e){
			//new SkipException("Auto UUID ID is Missing."); 
			Reporter.log("Error: Test Case failed due to error occured");
			coreFunc.logData("CreateClientAndContract","","Failed","Error: Refer output logs for more information.",assertEnabled);
			e.printStackTrace();
		}		
		
	}
    
	void waitForLoad() {
	    new WebDriverWait(getDriver(), 60).until((Predicate<WebDriver>) wd ->
	            ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
	}
	
    private boolean AddClient(Object[] testData)
	{
    	System.out.println("Debug log : Entering in AddClient ");
       	String testID=String.valueOf(testData[0]);
    	try
		{
    		String salutation= String.valueOf(testData[4]);
    		String title = String.valueOf(testData[5]);
    		String namefirst =String.valueOf(testData[7]);
    		String namelast =String.valueOf(testData[8]);
    		String dob =String.valueOf(testData[9]);
    		String birthname =String.valueOf(testData[11]);
    		String birthplace =String.valueOf(testData[14]);
    		String streetName =String.valueOf(testData[15]);
    		String streetNum =String.valueOf(testData[16]);
    		String zip =String.valueOf(testData[17]);
    		String city =String.valueOf(testData[18]);
    		String phone =String.valueOf(testData[19]);
    		String email =String.valueOf(testData[20]);
    		String mobile =String.valueOf(testData[21]);
    		Reporter.log("Create Client with ==> First Name :  "+namefirst+" Last Name :  "+namelast+"  Email Id :  "+email); 
    		System.out.println("Debug log ==> Create Client with First Name :  "+namefirst+" Last Name :  "+namelast+"  Email Id :  "+email);
    		WebDriverWait clientWait = new WebDriverWait(getDriver(), 45);
    		
    		//click on create button
			coreFunc.ClickOnActionButton("Create");
			TestResult.addTestResult("Clicked on 'Create' client button" ,"Passed");
			Reporter.log("Clicked on 'Create' client button  ==>  Step Pass");
			clientWait.until(ExpectedConditions.invisibilityOfElementLocated(By.className(LoadProperty.getVar("clientGridWait", "element"))));
			
			//Enter client data
			//select value in salutation dropdown
			List<WebElement> salutationelement = getDriver().findElements(By.xpath(LoadProperty.getVar("salutation", "element")));
			for (int i = 0; i <salutationelement.size() ; i++) {
		        WebElement sitem = salutationelement.get(i);
		        if(sitem.isDisplayed()){
		        	sitem.click();
		        	break;
		        }	        
		    }			
			coreFunc.SelectValueInDropdown(salutation);
			TestResult.addTestResult("Value '"+salutation+"' selected in salutation dropdown" ,"Passed");
			Reporter.log("Value '"+salutation+"' selected in salutation dropdown  ==>  Step Pass");
			
			getDriver().findElement(By.name(LoadProperty.getVar("title", "element"))).sendKeys(title);
			TestResult.addTestResult("Value '"+title+"' selected in 'Title' dropdown" ,"Passed");
			Reporter.log("Value '"+title+"' selected in 'Title' dropdown  ==>  Step Pass");
			
			getDriver().findElement(By.id(LoadProperty.getVar("firstName", "element"))).clear();
			getDriver().findElement(By.id(LoadProperty.getVar("firstName", "element"))).sendKeys(namefirst);
			TestResult.addTestResult("Entered value '"+namefirst+"' in 'First Name' field" ,"Passed");
			Reporter.log("Entered value '"+namefirst+"' in 'First Name' field  ==>  Step Pass");
			
			getDriver().findElement(By.id(LoadProperty.getVar("lastName", "element"))).clear();
			getDriver().findElement(By.id(LoadProperty.getVar("lastName", "element"))).sendKeys(namelast);
			TestResult.addTestResult("Entered value '"+namefirst+"' in 'LastName' field" ,"Passed");
			Reporter.log("Entered value '"+namefirst+"' in 'LastName' field  ==>  Step Pass");
			
			getDriver().findElement(By.id(LoadProperty.getVar("dob", "element"))).clear();
			getDriver().findElement(By.id(LoadProperty.getVar("dob", "element"))).sendKeys(dob);
			TestResult.addTestResult("Entered value '"+dob+"' in 'BirthDate' field" ,"Passed");
			Reporter.log("Entered value '"+dob+"' in 'BirthDate' field  ==>  Step Pass");
			
			WebDriverWait wait = new WebDriverWait(getDriver(), 30);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(LoadProperty.getVar("birthName", "element"))));
			getDriver().findElement(By.name(LoadProperty.getVar("birthName", "element"))).clear();
			getDriver().findElement(By.name(LoadProperty.getVar("birthName", "element"))).sendKeys(birthname);
			TestResult.addTestResult("Entered value '"+birthname+"' in 'Birth Name' field" ,"Passed");
			Reporter.log("Entered value '"+birthname+"' in 'Birth Name' field  ==>  Step Pass");
			
			getDriver().findElement(By.name(LoadProperty.getVar("birthPlace", "element"))).clear();
			getDriver().findElement(By.name(LoadProperty.getVar("birthPlace", "element"))).sendKeys(birthplace);
			TestResult.addTestResult("Entered value '"+birthplace+"' in 'BirthPlace' field" ,"Passed");
			Reporter.log("Entered value '"+birthplace+"' in 'BirthPlace' field  ==>  Step Pass");
			
			getDriver().findElement(By.name(LoadProperty.getVar("street", "element"))).clear();
			getDriver().findElement(By.name(LoadProperty.getVar("street", "element"))).sendKeys(streetName);
			TestResult.addTestResult("Entered value '"+streetName+"' in 'StreetName' field" ,"Passed");
			Reporter.log("Entered value '"+streetName+"' in 'StreetName' field  ==>  Step Pass");
			
			getDriver().findElement(By.name(LoadProperty.getVar("streetNum", "element"))).clear();
			getDriver().findElement(By.name(LoadProperty.getVar("streetNum", "element"))).sendKeys(streetNum);
			TestResult.addTestResult("Entered value '"+streetNum+"' in 'Street/No' field" ,"Passed");
			Reporter.log("Entered value '"+streetNum+"' in 'Street/No' field  ==>  Step Pass");
			
			getDriver().findElement(By.name(LoadProperty.getVar("zip", "element"))).clear();
			getDriver().findElement(By.name(LoadProperty.getVar("zip", "element"))).sendKeys(zip);
			TestResult.addTestResult("Entered value '"+zip+"' in 'PLZ' field" ,"Passed");
			Reporter.log("Entered value '"+zip+"' in 'PLZ' field  ==>  Step Pass");
			
			getDriver().findElement(By.name(LoadProperty.getVar("city", "element"))).clear();
			getDriver().findElement(By.name(LoadProperty.getVar("city", "element"))).sendKeys(city);
			TestResult.addTestResult("Entered value '"+city+"' in 'City' field" ,"Passed");
			Reporter.log("Entered value '"+city+"' in 'City' field  ==>  Step Pass");
			
			getDriver().findElement(By.name(LoadProperty.getVar("phone", "element"))).clear();
			getDriver().findElement(By.name(LoadProperty.getVar("phone", "element"))).sendKeys(phone);
			TestResult.addTestResult("Entered value '"+phone+"' in 'TelePhone' field" ,"Passed");
			Reporter.log("Entered value '"+phone+"' in 'TelePhone' field  ==>  Step Pass");
			
			getDriver().findElement(By.name(LoadProperty.getVar("email", "element"))).clear();
			getDriver().findElement(By.name(LoadProperty.getVar("email", "element"))).sendKeys(email);
			TestResult.addTestResult("Entered value '"+email+"' in 'Email' field" ,"Passed");
			Reporter.log("Entered value '"+email+"' in 'Email' field  ==>  Step Pass");
			
			getDriver().findElement(By.name(LoadProperty.getVar("mobile", "element"))).clear();
			getDriver().findElement(By.name(LoadProperty.getVar("mobile", "element"))).sendKeys(mobile);
			TestResult.addTestResult("Entered value '"+mobile+"' in 'Mobile' field" ,"Passed");
			Reporter.log("Entered value '"+mobile+"' in 'Mobile' field  ==>  Step Pass");
			
			getDriver().manage().timeouts().implicitlyWait(10000, TimeUnit.SECONDS);
			
			//Click on save client button
			coreFunc.ClickOnActionButton("Save");
			TestResult.addTestResult("Clicked on 'Save' client button" ,"Passed");
			Reporter.log("Clicked on 'Save' client button  ==>  Step Pass");
						
			getDriver().manage().timeouts().implicitlyWait(10000, TimeUnit.SECONDS);
			Thread.sleep(6000);
			//getDriver().findElement(By.id(LoadProperty.getVar("okPopup", "element"))).click();
			clientWait.until(ExpectedConditions.invisibilityOfElementLocated(By.className(LoadProperty.getVar("clientGridWait", "element"))));
			//System.out.println("Debug log : After client saved");
			Reporter.log("Search client by Email Id :  "+email);
			Thread.sleep(5000);
			getDriver().findElement(By.name(LoadProperty.getVar("clientSearchBox", "element"))).clear();
			getDriver().findElement(By.name(LoadProperty.getVar("clientSearchBox", "element"))).sendKeys(email);
			TestResult.addTestResult("Entered value '"+email+"' in 'Client Search' field" ,"Passed");
			Reporter.log("Entered value '"+email+"' in 'Client Search' field  ==>  Step Pass");
			
			clientWait.until(ExpectedConditions.invisibilityOfElementLocated(By.className(LoadProperty.getVar("clientGridWait", "element"))));
			Thread.sleep(5000);
			//select first record
			getDriver().findElement(By.xpath(LoadProperty.getVar("firstRecord", "element"))).click();
		
			//Verify client found or not with specified name in grid
			WebElement recordcount =  getDriver().findElement(By.xpath(LoadProperty.getVar("recordCount", "element")));
			System.out.println("Debug log : Case verify text :  "+ recordcount.getText());
			System.out.println("Debug log : Case verify text :2  "+ coreFunc.GetDELanguageText("No data to display"));
			if(recordcount.getText().equalsIgnoreCase(coreFunc.GetDELanguageText("No data to display"))){
				TestResult.addTestResult("Client not Created with Email ID '"+email ,"Failed");
				coreFunc.logData("CreateClientAndContract",testID,"Failed","Error: No Client created with Email ID : "+email+"  [ TCID: "+testID+"]",assertEnabled);	    		
	    		return false;
			}
			
			Thread.sleep(3000);
			WebDriverWait clientGridWait = new WebDriverWait(getDriver(), 45);
			
			clientGridWait.until(ExpectedConditions.visibilityOfElementLocated(By.name(LoadProperty.getVar("email", "element"))));
			
			List <WebElement> emailExists = getDriver().findElements(By.xpath("//li[@data-email='"+email+"']"));
			
			System.out.println("Email created  : "+emailExists.size());
			for (int j = 0; j <emailExists.size() ; j++) {
		        WebElement item = emailExists.get(j);
		        System.out.println("Debug log Size2==>"+item.getText());
		        if (item.getText().contains(email)) {
		        	TestResult.addTestResult("Client Created with Email ID '"+email ,"Passed");
					Reporter.log("Client Created with Email ID '"+email+"' ==>  Step Pass");
		            break;
		        }
		    }
			Reporter.log("Client created with ==> First Name :  "+namefirst+" Last Name :  "+namelast+"  Email Id :  "+email); 
			System.out.println("Debug log : Exiting from AddClient");
			
	    }
		catch(NoSuchElementException eNFound)
		{
			TestResult.addTestResult("Client not Created due to Error : "+ eNFound.getMessage() ,"Failed");
    		coreFunc.logData("CreateClientAndContract",testID,"Failed","Error: "+eNFound.getMessage()+" element not found. [ TCID: "+testID+"]",assertEnabled);
    		System.out.println("Debug log : Error ==> "+eNFound.getMessage()+" element not found. [ TCID: "+testID+"]");
    		return false;
    	}
		catch(ElementNotVisibleException eNVisible)
		{
			TestResult.addTestResult("Client not Created due to Error : "+ eNVisible.getMessage() ,"Failed");
    		coreFunc.logData("CreateClientAndContract",testID,"Failed","Error: "+eNVisible.getMessage()+" element is not visible. [ TCID: "+testID+"]",assertEnabled);
    		System.out.println("Debug log : Error ==> "+eNVisible.getMessage()+" element is not visible. [ TCID: "+testID+"]");
    		return false;
    	}
		catch(Exception e)
		{
			TestResult.addTestResult("Client not Created due to Error : "+ e.getMessage() ,"Failed");
    		coreFunc.logData("CreateClientAndContract",testID,"Failed","Error: occured on "+e.getMessage()+" element.[ TCID: "+testID+"]",assertEnabled);
    		System.out.println("Debug log : Error ==> occured on  "+e.getMessage()+" element.[ TCID: "+testID+"]");
    		return false;
    	}
    	return true;
    }
    
    private boolean AddClientRelatives(Object[] testData)
   	{
       	System.out.println("Debug log : Entering in CreateClientDetails ");
        String testID=String.valueOf(testData[0]);
       	try
   		{
//       	String namefirst =String.valueOf(testData[7]);
//    		String namelast =String.valueOf(testData[8]);
//    		String email =String.valueOf(testData[20]);
    		String fd_relation =String.valueOf(testData[42]);
    		String fd_salutation =String.valueOf(testData[43]);
    		String fd_title =String.valueOf(testData[44]);
    		String fd_firstname =String.valueOf(testData[45]);
    		String fd_lastname =String.valueOf(testData[46]);
    		String fd_dob =String.valueOf(testData[47]);
    		String fd_woc =String.valueOf(testData[48]);
    		String fd_comdata =String.valueOf(testData[49]);
    		
    		WebDriverWait clientWait = new WebDriverWait(getDriver(), 45);
    		clientWait.until(ExpectedConditions.invisibilityOfElementLocated(By.className(LoadProperty.getVar("clientGridWait", "element"))));
    		//Enter client family data

    		//click on relatives tab
    		getDriver().findElement(By.linkText(LoadProperty.getVar("relatives", "element"))).click();

    		
    		//click on add relative button    		
    		getDriver().findElement(By.xpath(LoadProperty.getVar("addRelative", "element"))).click();
    		
    		//select value in relation dropdown
    		WebElement relationDD =  getDriver().findElement(By.cssSelector(LoadProperty.getVar("relationsDD", "element")));
    		relationDD.click();  
    		coreFunc.SelectValueInDropdown(fd_relation);
    		    		
    		//select value in salutation dropdown
			List<WebElement> salutationelement = getDriver().findElements(By.xpath(LoadProperty.getVar("salutationDD", "element")));
			for (int i = 0; i <salutationelement.size() ; i++) {
		        WebElement sitem = salutationelement.get(i);
		        if(sitem.isDisplayed()){
		        	sitem.click();
		        	break;
		        }	        
		    }			
			coreFunc.SelectValueInDropdown(fd_salutation);
			
			//select value in title dropdown
			List<WebElement> titleelement = getDriver().findElements(By.xpath(LoadProperty.getVar("titleElement", "element")));
			for (int i = 0; i <titleelement.size() ; i++) {
		        WebElement titem = titleelement.get(i);
		        if(titem.isDisplayed()){		        	
		        	titem.sendKeys(fd_title);
		        	break;
		        }	        
		    }			
					
			//Enter value in First name text box
			List<WebElement> firstname = getDriver().findElements(By.xpath(LoadProperty.getVar("first_Name", "element")));
			for (int i = 0; i <firstname.size() ; i++) {
		        WebElement fnitem = firstname.get(i);
		        if(fnitem.isDisplayed()){
		        	fnitem.sendKeys(fd_firstname);
		        	break;
		        }	        
		    }			
	    	
			//Enter value in Last name text box
			List<WebElement> lastname = getDriver().findElements(By.xpath(LoadProperty.getVar("last_Name", "element")));
			for (int i = 0; i <lastname.size() ; i++) {
		        WebElement lnitem = lastname.get(i);
		        if(lnitem.isDisplayed()){
		        	lnitem.sendKeys(fd_lastname);
		        	break;
		        }	        
		    }		
			
			//Enter value in DOB  box
			List<WebElement> dob = getDriver().findElements(By.xpath(LoadProperty.getVar("birth_Date", "element")));
			for (int i = 0; i <dob.size() ; i++) {
		        WebElement ditem = dob.get(i);
		        if(ditem.isDisplayed()){
		        	ditem.sendKeys(fd_dob);
		        	break;
		        }	        
		    }		
			
			//Enter value in communication data text box
			List<WebElement> woc = getDriver().findElements(By.xpath(LoadProperty.getVar("communication", "element")));
			for (int i = 0; i <woc.size() ; i++) {
		        WebElement witem = woc.get(i);
		        if(witem.isDisplayed()){
		        	witem.click();
		        	break;
		        }	        
		    }		
			coreFunc.SelectValueInDropdown(fd_woc);
			
			//Enter value in communication data text box
			List<WebElement> comdata = getDriver().findElements(By.xpath(LoadProperty.getVar("communicationData", "element")));
			for (int i = 0; i <comdata.size() ; i++) {
		        WebElement cditem = comdata.get(i);
		        if(cditem.isDisplayed()){
		        	cditem.sendKeys(fd_comdata);
		        	break;
		        }	        
		    }				
	
			List<WebElement> savebtn = getDriver().findElements(By.xpath(LoadProperty.getVar("saveBtnRelative", "element")));
			System.out.println("Debug log Size==>"+savebtn.size());
			for (int i = 0; i <=savebtn.size() ; i++) {
		        WebElement item = savebtn.get(i);
		        System.out.println("Debug log Size2==>"+item.getText());
		        if (item.getText().equals(coreFunc.GetDELanguageText("Save"))) {
		            item.click();
		            break;
		        }
		    }
			System.out.println("Debug log : Exiting from CreateClientDetails ");
			Thread.sleep(5000);
   		}
		catch(NoSuchElementException eNFound)
		{
    		coreFunc.logData("CreateClientAndContract",testID,"Failed","Error: "+eNFound.getMessage()+" element not found. [ TCID: "+testID+"]",assertEnabled);
    		System.out.println("Debug log : Error ==> "+eNFound.getMessage()+" element not found. [ TCID: "+testID+"]");
    		return false;
    	}
		catch(ElementNotVisibleException eNVisible)
		{
    		coreFunc.logData("CreateClientAndContract",testID,"Failed","Error: "+eNVisible.getMessage()+" element is not visible. [ TCID: "+testID+"]",assertEnabled);
    		System.out.println("Debug log : Error ==> "+eNVisible.getMessage()+" element is not visible. [ TCID: "+testID+"]");
    		return false;
    	}
		catch(Exception e)
		{
    		coreFunc.logData("CreateClientAndContract",testID,"Failed","Error: occured on "+e.getMessage()+" element.[ TCID: "+testID+"]",assertEnabled);
    		System.out.println("Debug log : Error ==> occured on  "+e.getMessage()+" element.[ TCID: "+testID+"]");
    		return false;
    	}
    	return true;
   	  }
   	
	private boolean AddClientProfession(Object[] testData)
   	{
       	System.out.println("Debug log : Entering in AddClientProfession ");
        String testID=String.valueOf(testData[0]);
       	try
   		{
//       	String namefirst =String.valueOf(testData[7]);
//    		String namelast =String.valueOf(testData[8]);
//    		String email =String.valueOf(testData[20]);
    		String pd_profession =String.valueOf(testData[51]);
    		String pd_jobtype =String.valueOf(testData[52]);
			String pd_physicalperc =String.valueOf(testData[54]);
			String pd_yearBru = String.valueOf(testData[55]);
    		String pd_yerlynetto =String.valueOf(testData[56]);
    		String pd_yearlysal =String.valueOf(testData[57]);
    		String pd_savingSince =String.valueOf(testData[58]);
    		String pd_capfrom =String.valueOf(testData[59]);
    		String pd_taxoffice =String.valueOf(testData[60]);
    		String pd_taxnum =String.valueOf(testData[61]);
    		String pd_taxid =String.valueOf(testData[62]);
    		String pd_taxclass =String.valueOf(testData[63]);
    		String pd_churchtax =String.valueOf(testData[64]);
    		String pd_sinsnumber =String.valueOf(testData[65]);
    		String pd_healthInsu =String.valueOf(testData[66]);
    		
    		//Enter client profession data

    		//click on Profession tab
    		getDriver().findElement(By.linkText(LoadProperty.getVar("professionTab", "element"))).click();

    		//click on Edit button
    		coreFunc.ClickOnActionButton("Edit");
    		
    		WebDriverWait clientWait = new WebDriverWait(getDriver(), 45);
    		clientWait.until(ExpectedConditions.visibilityOfElementLocated(By.name(LoadProperty.getVar("profession", "element"))));
    		
    		
			getDriver().findElement(By.name(LoadProperty.getVar("profession", "element"))).clear();
			getDriver().findElement(By.name(LoadProperty.getVar("profession", "element"))).sendKeys(pd_profession);
			
			getDriver().findElement(By.id(LoadProperty.getVar("jobType", "element"))).click();
			coreFunc.SelectValueInDropdown(pd_jobtype);
						
			getDriver().findElement(By.name(LoadProperty.getVar("workPercentagePhysical", "element"))).clear();
			getDriver().findElement(By.name(LoadProperty.getVar("workPercentagePhysical", "element"))).sendKeys(pd_physicalperc);
			
			getDriver().findElement(By.name(LoadProperty.getVar("incomeYearlyBurutto", "element"))).clear();
			getDriver().findElement(By.name(LoadProperty.getVar("incomeYearlyBurutto", "element"))).sendKeys(pd_yearBru);
			getDriver().findElement(By.name(LoadProperty.getVar("incomeYearlyNetto", "element"))).clear();
			getDriver().findElement(By.name(LoadProperty.getVar("incomeYearlyNetto", "element"))).sendKeys(pd_yerlynetto);
			getDriver().findElement(By.name(LoadProperty.getVar("incomeYearlySalaries", "element"))).clear();
			getDriver().findElement(By.name(LoadProperty.getVar("incomeYearlySalaries", "element"))).sendKeys(pd_yearlysal);
			getDriver().findElement(By.name(LoadProperty.getVar("retirementSavingSince", "element"))).clear();
			getDriver().findElement(By.name(LoadProperty.getVar("retirementSavingSince", "element"))).sendKeys(pd_savingSince);			
			getDriver().findElement(By.name(LoadProperty.getVar("capitalFormingPayments", "element"))).clear();
			getDriver().findElement(By.name(LoadProperty.getVar("capitalFormingPayments", "element"))).sendKeys(pd_capfrom);
			getDriver().findElement(By.name(LoadProperty.getVar("taxOfficeName", "element"))).clear();
			getDriver().findElement(By.name(LoadProperty.getVar("taxOfficeName", "element"))).sendKeys(pd_taxoffice);
			getDriver().findElement(By.name(LoadProperty.getVar("taxNumber", "element"))).clear();
			getDriver().findElement(By.name(LoadProperty.getVar("taxNumber", "element"))).sendKeys(pd_taxnum);
			getDriver().findElement(By.name(LoadProperty.getVar("taxId", "element"))).clear();
			getDriver().findElement(By.name(LoadProperty.getVar("taxId", "element"))).sendKeys(pd_taxid);
			getDriver().findElement(By.name(LoadProperty.getVar("taxClass", "element"))).clear();
			getDriver().findElement(By.name(LoadProperty.getVar("taxClass", "element"))).sendKeys(pd_taxclass);
			getDriver().findElement(By.name(LoadProperty.getVar("churchTaxPercentage", "element"))).clear();
			getDriver().findElement(By.name(LoadProperty.getVar("churchTaxPercentage", "element"))).sendKeys(pd_churchtax);
			getDriver().findElement(By.name(LoadProperty.getVar("socialInsuranceNumber", "element"))).clear();
			getDriver().findElement(By.name(LoadProperty.getVar("socialInsuranceNumber", "element"))).sendKeys(pd_sinsnumber);
			getDriver().findElement(By.name(LoadProperty.getVar("healthInsurance", "element"))).clear();
			getDriver().findElement(By.name(LoadProperty.getVar("healthInsurance", "element"))).sendKeys(pd_healthInsu);
			getDriver().manage().timeouts().implicitlyWait(10000, TimeUnit.SECONDS);
					
			//click on Save button
    		coreFunc.ClickOnActionButton("Save");
    		
    		clientWait.until(ExpectedConditions.invisibilityOfElementLocated(By.className(LoadProperty.getVar("clientGridWait", "element"))));
		  
			System.out.println("Debug log : Exiting from AddClientProfession ");
			Thread.sleep(5000);
   		}
		catch(NoSuchElementException eNFound)
		{
    		coreFunc.logData("CreateClientAndContract",testID,"Failed","Error: "+eNFound.getMessage()+" element not found. [ TCID: "+testID+"]",assertEnabled);
    		System.out.println("Debug log : Error ==> "+eNFound.getMessage()+" element not found. [ TCID: "+testID+"]");
    		return false;
    	}
		catch(ElementNotVisibleException eNVisible)
		{
    		coreFunc.logData("CreateClientAndContract",testID,"Failed","Error: "+eNVisible.getMessage()+" element is not visible. [ TCID: "+testID+"]",assertEnabled);
    		System.out.println("Debug log : Error ==> "+eNVisible.getMessage()+" element is not visible. [ TCID: "+testID+"]");
    		return false;
    	}
		catch(Exception e)
		{
    		coreFunc.logData("CreateClientAndContract",testID,"Failed","Error: occured on "+e.getMessage()+" element.[ TCID: "+testID+"]",assertEnabled);
    		System.out.println("Debug log : Error ==> occured on  "+e.getMessage()+" element.[ TCID: "+testID+"]");
    		return false;
    	}
    	return true;
   	  }
   		
	private boolean AddClientJob(Object[] testData)
   	{
       	System.out.println("Debug log : Entering in AddClientJob ");
        String testID=String.valueOf(testData[0]);
       	try
   		{
//       	String namefirst =String.valueOf(testData[7]);
//    		String namelast =String.valueOf(testData[8]);
//    		String email =String.valueOf(testData[20]);
    		String jd_employer =String.valueOf(testData[68]);
			String jd_contact = String.valueOf(testData[69]);
    		String jd_street =String.valueOf(testData[70]);
    		String jd_number =String.valueOf(testData[71]);
    		String jd_zip =String.valueOf(testData[72]);
    		String jd_city =String.valueOf(testData[73]);
    		String jd_country =String.valueOf(testData[74]);
    		
    		//Enter client Job data
			
			//click on Job tab
			getDriver().findElement(By.linkText(LoadProperty.getVar("jobTab", "element"))).click();
			
			//click on Edit button
			coreFunc.ClickOnActionButton("Edit");
			
			WebDriverWait clientWait = new WebDriverWait(getDriver(), 45);
			clientWait.until(ExpectedConditions.visibilityOfElementLocated(By.name(LoadProperty.getVar("employerName", "element"))));
			
			
			getDriver().findElement(By.name(LoadProperty.getVar("employerName", "element"))).clear();
			getDriver().findElement(By.name(LoadProperty.getVar("employerName", "element"))).sendKeys(jd_employer);
								
			getDriver().findElement(By.name(LoadProperty.getVar("employerContact", "element"))).clear();
			getDriver().findElement(By.name(LoadProperty.getVar("employerContact", "element"))).sendKeys(jd_contact);	
			
			getDriver().findElement(By.name(LoadProperty.getVar("employerStreet", "element"))).clear();
			getDriver().findElement(By.name(LoadProperty.getVar("employerStreet", "element"))).sendKeys(jd_street);
			
			getDriver().findElement(By.name(LoadProperty.getVar("employerStreet_num", "element"))).clear();
			getDriver().findElement(By.name(LoadProperty.getVar("employerStreet_num", "element"))).sendKeys(jd_number);
			
			getDriver().findElement(By.name(LoadProperty.getVar("employerZip", "element"))).clear();
			getDriver().findElement(By.name(LoadProperty.getVar("employerZip", "element"))).sendKeys(jd_zip);
					
			getDriver().findElement(By.xpath(LoadProperty.getVar("employerCity", "element"))).click();
			new Actions(getDriver()).sendKeys(Keys.ARROW_DOWN).perform();
			//Thread.sleep(2000);
			coreFunc.SelectValueInDropdown(jd_city);
			
			getDriver().findElement(By.xpath(LoadProperty.getVar("employerCountry", "element"))).click();
			new Actions(getDriver()).sendKeys(Keys.ARROW_DOWN).perform();			
			coreFunc.SelectValueInDropdown(jd_country);
					
			getDriver().manage().timeouts().implicitlyWait(10000, TimeUnit.SECONDS);
					
			//click on Save button
			coreFunc.ClickOnActionButton("Save");
			
			clientWait.until(ExpectedConditions.invisibilityOfElementLocated(By.className(LoadProperty.getVar("clientGridWait", "element"))));
			
			System.out.println("Debug log : Exiting from AddClientJob ");
			Thread.sleep(5000);
    	}
		catch(NoSuchElementException eNFound)
		{
    		coreFunc.logData("CreateClientAndContract",testID,"Failed","Error: "+eNFound.getMessage()+" element not found. [ TCID: "+testID+"]",assertEnabled);
    		System.out.println("Debug log : Error ==> "+eNFound.getMessage()+" element not found. [ TCID: "+testID+"]");
    		return false;
    	}
		catch(ElementNotVisibleException eNVisible)
		{
    		coreFunc.logData("CreateClientAndContract",testID,"Failed","Error: "+eNVisible.getMessage()+" element is not visible. [ TCID: "+testID+"]",assertEnabled);
    		System.out.println("Debug log : Error ==> "+eNVisible.getMessage()+" element is not visible. [ TCID: "+testID+"]");
    		return false;
    	}
		catch(Exception e)
		{
    		coreFunc.logData("CreateClientAndContract",testID,"Failed","Error: occured on "+e.getMessage()+" element.[ TCID: "+testID+"]",assertEnabled);
    		System.out.println("Debug log : Error ==> occured on  "+e.getMessage()+" element.[ TCID: "+testID+"]");
    		return false;
    	}
    	return true;
   	  }
		
	private boolean AddClientRisk(Object[] testData)
   	{
       	System.out.println("Debug log : Entering in AddClientRisk ");
        String testID=String.valueOf(testData[0]);
       	try
   		{
//       	String namefirst =String.valueOf(testData[7]);
//    		String namelast =String.valueOf(testData[8]);
//    		String email =String.valueOf(testData[20]);
    		String rd_weight =String.valueOf(testData[76]);
    		String rd_height =String.valueOf(testData[77]);
    		String rd_smoker =String.valueOf(testData[78]);
    		String rd_healthinfo =String.valueOf(testData[79]);
//    		String rd_type =String.valueOf(testData[80]);
//			String rd_info =String.valueOf(testData[81]);
			
			//click on Risk tab
			getDriver().findElement(By.linkText(LoadProperty.getVar("riskTab", "element"))).click();
			
			//click on Edit button
			coreFunc.ClickOnActionButton("Edit");
			
			WebDriverWait clientWait = new WebDriverWait(getDriver(), 45);
			clientWait.until(ExpectedConditions.visibilityOfElementLocated(By.name(LoadProperty.getVar("weight", "element"))));
    		
    		//Enter client risk data
			
			getDriver().findElement(By.name(LoadProperty.getVar("weight", "element"))).clear();
			getDriver().findElement(By.name(LoadProperty.getVar("weight", "element"))).sendKeys(rd_weight);
								
			getDriver().findElement(By.name(LoadProperty.getVar("height", "element"))).clear();
			getDriver().findElement(By.name(LoadProperty.getVar("height", "element"))).sendKeys(rd_height);	
			
			getDriver().findElement(By.name(LoadProperty.getVar("smoker", "element"))).click();
			new Actions(getDriver()).sendKeys(Keys.ARROW_DOWN).perform();			
			coreFunc.SelectValueInDropdown(rd_smoker);
			
			getDriver().findElement(By.name(LoadProperty.getVar("healthInfo", "element"))).clear();
			getDriver().findElement(By.name(LoadProperty.getVar("healthInfo", "element"))).sendKeys(rd_healthinfo);
			
			//click on add relative button    		
    		//getDriver().findElement(By.cssSelector(LoadProperty.getVar("addRelative", "element"))).click();
			
			//click on Save button
			coreFunc.ClickOnActionButton("Save");
			
    		System.out.println("Debug log : Exiting from AddClientRisk ");
			Thread.sleep(5000);
   		}
		catch(NoSuchElementException eNFound)
		{
    		coreFunc.logData("CreateClientAndContract",testID,"Failed","Error: "+eNFound.getMessage()+" element not found. [ TCID: "+testID+"]",assertEnabled);
    		System.out.println("Debug log : Error ==> "+eNFound.getMessage()+" element not found. [ TCID: "+testID+"]");
    		return false;
    	}
		catch(ElementNotVisibleException eNVisible)
		{
    		coreFunc.logData("CreateClientAndContract",testID,"Failed","Error: "+eNVisible.getMessage()+" element is not visible. [ TCID: "+testID+"]",assertEnabled);
    		System.out.println("Debug log : Error ==> "+eNVisible.getMessage()+" element is not visible. [ TCID: "+testID+"]");
    		return false;
    	}
		catch(Exception e)
		{
    		coreFunc.logData("CreateClientAndContract",testID,"Failed","Error: occured on "+e.getMessage()+" element.[ TCID: "+testID+"]",assertEnabled);
    		System.out.println("Debug log : Error ==> occured on  "+e.getMessage()+" element.[ TCID: "+testID+"]");
    		return false;
    	}
    	return true;
   	}
   	
	private boolean AddClientBank(Object[] testData)
   	{
       	System.out.println("Debug log : Entering in AddClientBank ");
        String testID=String.valueOf(testData[0]);
       	try
   		{
//       	String namefirst =String.valueOf(testData[7]);
//    		String namelast =String.valueOf(testData[8]);
//    		String email =String.valueOf(testData[20]);
    		String bd_type =String.valueOf(testData[83]);
    		String bd_holdername =String.valueOf(testData[84]);
			String bd_bankname =String.valueOf(testData[85]);
    		String bd_banknumber =String.valueOf(testData[86]);
			String bd_accountnum =String.valueOf(testData[87]);
			String bd_iban =String.valueOf(testData[88]);
    		String bd_bic =String.valueOf(testData[89]);
    		String bd_cardName =String.valueOf(testData[90]);
    		String bd_cardNum =String.valueOf(testData[91]);
    		String bd_validUpto =String.valueOf(testData[92]);
    		
    		//click on Bank tab
			getDriver().findElement(By.linkText(LoadProperty.getVar("bankTab", "element"))).click();
			
			WebDriverWait clientWait = new WebDriverWait(getDriver(), 45);
			clientWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(LoadProperty.getVar("addBank", "element"))));
			
    		//click on add bank details icon    		
    		getDriver().findElement(By.xpath(LoadProperty.getVar("addBank", "element"))).click();
    		
    		//select value in type dropdown
    		getDriver().findElement(By.cssSelector(LoadProperty.getVar("type", "element"))).click();  
    		coreFunc.SelectValueInDropdown(bd_type);

    		getDriver().findElement(By.name(LoadProperty.getVar("holderName", "element"))).clear();
			getDriver().findElement(By.name(LoadProperty.getVar("holderName", "element"))).sendKeys(bd_holdername);
			
			
			if(bd_type.equals(coreFunc.GetDELanguageText("Account")))
			{
				getDriver().findElement(By.xpath(LoadProperty.getVar("bankName", "element"))).click();  
				new Actions(getDriver()).sendKeys(Keys.ARROW_DOWN).perform();
				coreFunc.SelectValueInDropdown(bd_bankname);
				
				getDriver().findElement(By.name(LoadProperty.getVar("bankNumber", "element"))).clear();
				getDriver().findElement(By.name(LoadProperty.getVar("bankNumber", "element"))).sendKeys(bd_banknumber);	
				
				getDriver().findElement(By.name(LoadProperty.getVar("accountNo", "element"))).clear();
				getDriver().findElement(By.name(LoadProperty.getVar("accountNo", "element"))).sendKeys(bd_accountnum);
				
				getDriver().findElement(By.name(LoadProperty.getVar("iban", "element"))).clear();
				getDriver().findElement(By.name(LoadProperty.getVar("iban", "element"))).sendKeys(bd_iban);
				
				getDriver().findElement(By.name(LoadProperty.getVar("bic", "element"))).clear();
				getDriver().findElement(By.name(LoadProperty.getVar("bic", "element"))).sendKeys(bd_bic);
			}
			else
			{
				getDriver().findElement(By.name(LoadProperty.getVar("cardName", "element"))).clear();
				getDriver().findElement(By.name(LoadProperty.getVar("cardName", "element"))).sendKeys(bd_cardName);
				
				getDriver().findElement(By.name(LoadProperty.getVar("cardNumber", "element"))).clear();
				getDriver().findElement(By.name(LoadProperty.getVar("cardNumber", "element"))).sendKeys(bd_cardNum);
				
				getDriver().findElement(By.name(LoadProperty.getVar("validTo", "element"))).clear();
				getDriver().findElement(By.name(LoadProperty.getVar("validTo", "element"))).sendKeys(bd_validUpto);
			}	
			//click on Save button
			List<WebElement> savebtn = getDriver().findElements(By.xpath(LoadProperty.getVar("saveBtnRelative", "element")));
			System.out.println("Debug log Size==>"+savebtn.size());
			for (int i = 0; i <=savebtn.size() ; i++) {
		        WebElement item = savebtn.get(i);
		        System.out.println("Debug log Size2==>"+item.getText());
		        if (item.getText().equals(coreFunc.GetDELanguageText("Save"))) {
		            item.click();
		            break;
		        }
		   
			}
			System.out.println("Debug log : Exiting from AddClientBank");
			Thread.sleep(5000);
   		}
		catch(NoSuchElementException eNFound)
		{
    		coreFunc.logData("CreateClientAndContract",testID,"Failed","Error: "+eNFound.getMessage()+" element not found. [ TCID: "+testID+"]",assertEnabled);
    		System.out.println("Debug log : Error ==> "+eNFound.getMessage()+" element not found. [ TCID: "+testID+"]");
    		return false;
    	}
		catch(ElementNotVisibleException eNVisible)
		{
    		coreFunc.logData("CreateClientAndContract",testID,"Failed","Error: "+eNVisible.getMessage()+" element is not visible. [ TCID: "+testID+"]",assertEnabled);
    		System.out.println("Debug log : Error ==> "+eNVisible.getMessage()+" element is not visible. [ TCID: "+testID+"]");
    		return false;
    	}
		catch(Exception e)
		{
    		coreFunc.logData("CreateClientAndContract",testID,"Failed","Error: occured on "+e.getMessage()+" element.[ TCID: "+testID+"]",assertEnabled);
    		System.out.println("Debug log : Error ==> occured on  "+e.getMessage()+" element.[ TCID: "+testID+"]");
    		return false;
    	}
    	return true;
   	}
			
    private boolean AddContract(Object[] testData)
	{
    	System.out.println("Debug log : Entering in AddContract ");
    	String testStep="";
    	String testID=String.valueOf(testData[0]);
    	try
		{
    		String namefirst =String.valueOf(testData[7]);
    		String namelast =String.valueOf(testData[8]);
    		String email =String.valueOf(testData[20]);
			String categoryOption = String.valueOf(testData[29]);
    		String providerName =String.valueOf(testData[30]);
    		String productName =String.valueOf(testData[31]);
    		String insuranceNumber =String.valueOf(testData[33]);
    		String payment =String.valueOf(testData[34]);
    		String duration_months =String.valueOf(testData[37]);  	
    		
    		WebDriverWait clientWait = new WebDriverWait(getDriver(), 45);
    		getDriver().manage().timeouts().implicitlyWait(10000, TimeUnit.SECONDS);
    		Thread.sleep(5000);
    		clientWait.until(ExpectedConditions.invisibilityOfElementLocated(By.className(LoadProperty.getVar("clientGridWait", "element"))));
    		//Get data for contract
    		
    		
    		Reporter.log("Start Create Contract for client First Name :  "+namefirst+" Last Name :  "+namelast+"  Email Id :  "+email);
    		Reporter.log("Search client by Email Id :  "+email);    		
			getDriver().findElement(By.name(LoadProperty.getVar("clientSearchBox", "element"))).clear();
			getDriver().findElement(By.name(LoadProperty.getVar("clientSearchBox", "element"))).sendKeys(email);
			clientWait.until(ExpectedConditions.invisibilityOfElementLocated(By.className(LoadProperty.getVar("clientGridWait", "element"))));
			//Verify client found or not with specified name in grid 
			WebElement recordcount =  getDriver().findElement(By.xpath(LoadProperty.getVar("recordCount", "element")));
			System.out.println("Debug log : Case verify text :  "+ recordcount.getText());
			System.out.println("Debug log : Case verify text :  "+ coreFunc.GetDELanguageText("No data to display"));
			if(recordcount.getText().equalsIgnoreCase(coreFunc.GetDELanguageText("No data to display"))){
				Reporter.log("Unable to add contract due to Client not found with details First Name :  "+namefirst+" Last Name :  "+namelast+"  Email Id :  "+email);
				coreFunc.logData("CreateClientAndContract",testID,"Failed","Error: Unable to add contract due to Client not found with Email ID : "+email+"  [ TCID: "+testID+"]",assertEnabled);					
	    		return false;
			}
			else{
				Reporter.log("Client found with details First Name :  "+namefirst+" Last Name :  "+namelast+"  Email Id :  "+email);
			}
			
			WebElement contractcountB =  getDriver().findElement(By.xpath(LoadProperty.getVar("contractCount", "element")));
			Reporter.log("Verify existing contract count");
			System.out.println("Debug log : Contract count before adding :  "+ contractcountB.getText());
			int contractCountbefore = Integer.parseInt(contractcountB.getText());
			//System.out.println("Debug log : 1 ");			
			Thread.sleep(3000);
			//select first record
			getDriver().findElement(By.xpath(LoadProperty.getVar("firstRecord", "element"))).click();
			//System.out.println("Debug log : 2 ");			
			
			Thread.sleep(5000);
			
    			 testStep="Click Contract Tab.";
				 String[] locator=ManageLocator.getLocator("Tab.Clients.Contract");
				 WebElement eleContractTab=coreFunc.findElement(locator[0],locator[1]);
				 eleContractTab.click();
				 TestResult.addTestResult(testStep,"Passed");
				 
				 coreFunc.waitForWhile(7);
				 testStep="Click Create Contract button.";
				 locator=ManageLocator.getLocator("Tab.Client.Contract.Button.Create");
				// WebElement eleCreateButton=coreFunc.findElement(locator[0],locator[1]);
				 //eleCreateButton.click();
				 coreFunc.ClickOnActionButton("Create");
				 TestResult.addTestResult(testStep,"Passed");
				 
				 coreFunc.waitForWhile(10);
				 
				 locator=ManageLocator.getLocator("Create.Contract.Category");
				 WebElement eleCategory=coreFunc.findElement(locator[0],locator[1]);
				 eleCategory.click(); 
				 				 
				 testStep="Select Category as "+categoryOption+".";
				 while(categoryOption.contains(">")){
					 String parentNodeText=categoryOption.substring(0, categoryOption.indexOf(">")).trim();
					 locator=ManageLocator.getLocator("Create.Contract.Category.ParentNode");
					 locator[1]=locator[1].replaceAll("@parentNodeText", parentNodeText);
					 WebElement eleParentNode=coreFunc.findElement(locator[0],locator[1]);
					 eleParentNode.click();			 
					 categoryOption=categoryOption.substring(categoryOption.indexOf(">")+1).trim();
				 }
				 
				 coreFunc.waitForWhile(2);
				 locator=ManageLocator.getLocator("Create.Contract.Category.TextNode");
				 locator[1]=locator[1].replaceAll("@textNode", categoryOption);
				 WebElement eleCategoryOption=coreFunc.findElement(locator[0],locator[1]);
				 eleCategoryOption.click();	
				 TestResult.addTestResult(testStep,"Passed");
				 
				 coreFunc.waitForWhile(5);
				 testStep="Select Provider as "+providerName+" .";
				 locator=ManageLocator.getLocator("Create.Contract.ProviderName");
				 WebElement eleProviderName=coreFunc.findElement(locator[0],locator[1]);
				 eleProviderName.click();
				 
				 locator=ManageLocator.getLocator("Create.Contract.ProviderOption");
				 locator[1]=locator[1].replaceAll("@optionText", providerName.trim());
				 WebElement eleProviderOption=coreFunc.findElement(locator[0],locator[1]);
//				 ((JavascriptExecutor)coreFunc.getDriver()).executeScript("window.scrollTo(0,"+eleProviderOption.getLocation().y+")");
				 eleProviderOption.click();

				 TestResult.addTestResult(testStep,"Passed");
								 
				 coreFunc.waitForWhile(10);
				 testStep="Select Product as "+productName+" .";
				 locator=ManageLocator.getLocator("Create.Contract.ProductName");
				 WebElement eleProductName=coreFunc.findElement(locator[0],locator[1]);
				 eleProductName.click();
				 
				 
				 locator=ManageLocator.getLocator("Create.Contract.ProductOption");
				 locator[1]=locator[1].replaceAll("@optionText", productName.trim());
				 WebElement eleProductOption=coreFunc.findElement(locator[0],locator[1]);
				 eleProductOption.click();
				 TestResult.addTestResult(testStep,"Passed");
				 
				 
				 testStep="Enter Ins Number as "+insuranceNumber+" .";
				 locator=ManageLocator.getLocator("Create.Contract.InsName");
				 WebElement eleInsname=coreFunc.findElement(locator[0],locator[1]);
				 eleInsname.sendKeys(insuranceNumber);
				 TestResult.addTestResult(testStep,"Passed");
				 
				 /*testStep="Select Period as "+mapTestData.get("Period")+" .";
				 locator=ManageLocator.getLocator("Create.Contract.Period");
				 WebElement elePeriod=coreFunc.findElement(locator[0],locator[1]);
				 elePeriod.click();
				 				 
				 locator=ManageLocator.getLocator("Create.Contract.Period.Option");
				 locator[1]=locator[1].replaceAll("@periodOption", mapTestData.get("Period"));
				 WebElement elePeriodOption=coreFunc.findElement(locator[0],locator[1]);
				 elePeriodOption.click();
				 TestResult.addTestResult(testStep,"Passed");*/
				 
				 
				 testStep="Select Payment as "+payment;
				 getDriver().findElement(By.name(LoadProperty.getVar("payment", "element"))).clear();
				 getDriver().findElement(By.name(LoadProperty.getVar("payment", "element"))).sendKeys(payment);
				 TestResult.addTestResult(testStep,"Passed");
				 
				 testStep="Select Duration Time as "+duration_months;
				 getDriver().findElement(By.name(LoadProperty.getVar("durationMonths", "element"))).clear();
				 getDriver().findElement(By.name(LoadProperty.getVar("durationMonths", "element"))).sendKeys(duration_months);
				 TestResult.addTestResult(testStep,"Passed");
    	
    		/*
			//Click on contract tab			
			getDriver().findElement(By.linkText(LoadProperty.getVar("contractTab", "element"))).click();
			System.out.println("Debug log : 3 ");			
			Thread.sleep(5000);
			
			//Click on create contract button
			coreFunc.ClickOnActionButton("Create");
			
			System.out.println("Debug log : 4 ");
			Thread.sleep(5000);
			WebDriverWait contractWait = new WebDriverWait(getDriver(), 60);
			contractWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(LoadProperty.getVar("category", "element"))));
			System.out.println("Debug log : 5 ");	
			
			//Enter contract data
			//select value in category dropdown
			List<WebElement> categoryelement = getDriver().findElements(By.xpath(LoadProperty.getVar("category", "element")));
			for (int i = 0; i <categoryelement.size() ; i++) {
		        WebElement citem = categoryelement.get(i);
		        if(citem.isDisplayed()){
		        	citem.click();
		        	break;
		        }	        
		    }
			List<WebElement> catItem=  getDriver().findElements(By.xpath("//span[@class ='x-tree-node-text '][text() = '"+category+"']"));
			for (int i = 0; i <catItem.size() ; i++) {
		        WebElement citem1 = catItem.get(i);
		        if(citem1.isDisplayed()){
		        	citem1.click();
		        	break;
		        }	        
		    }
			//span[@class ='x-tree-node-text '][text() = 'KFZ']
			//coreFunc.SelectValueInDropdown(category);			
			
			//getDriver().findElement(By.name(LoadProperty.getVar("category", "element"))).sendKeys(category);
		 	getDriver().findElement(By.name(LoadProperty.getVar("providerId", "element"))).click();
		 	coreFunc.SelectValueInDropdown(providerName);
			//getDriver().findElement(By.name(LoadProperty.getVar("providerId", "element"))).sendKeys(providerName);
			getDriver().findElement(By.name(LoadProperty.getVar("productId", "element"))).click();
			coreFunc.SelectValueInDropdown(productName);
			//getDriver().findElement(By.name(LoadProperty.getVar("productId", "element"))).sendKeys(productName);
			getDriver().findElement(By.name(LoadProperty.getVar("insuranceNumber", "element"))).clear();
			getDriver().findElement(By.name(LoadProperty.getVar("insuranceNumber", "element"))).sendKeys(insuranceNumber);
			getDriver().findElement(By.name(LoadProperty.getVar("payment", "element"))).clear();
			getDriver().findElement(By.name(LoadProperty.getVar("payment", "element"))).sendKeys(payment);
			getDriver().findElement(By.name(LoadProperty.getVar("durationMonths", "element"))).clear();
			getDriver().findElement(By.name(LoadProperty.getVar("durationMonths", "element"))).sendKeys(duration_months);
			*/
			//Click on save contract button
			coreFunc.ClickOnActionButton("Save");
			
			AddContractFeatures(testData);	
			
			testStep="Click Save button.";
			locator=ManageLocator.getLocator("Tab.Client.Contract.Create.Button.Save");
			WebElement eleSaveButton=coreFunc.findElement(locator[0],locator[1]);
			 //eleSaveButton.click();
			eleSaveButton.click();
			TestResult.addTestResult(testStep,"Passed");
			
			clientWait.until(ExpectedConditions.invisibilityOfElementLocated(By.className(LoadProperty.getVar("clientGridWait", "element"))));
			//click on refresh button 
			getDriver().findElement(By.xpath(LoadProperty.getVar("refreshBtn", "element"))).click();
			clientWait.until(ExpectedConditions.invisibilityOfElementLocated(By.className(LoadProperty.getVar("clientGridWait", "element"))));
			
			WebElement contractcountA =  getDriver().findElement(By.xpath(LoadProperty.getVar("contractCount", "element")));
			
			Reporter.log("Verify Contract count or not with specified last name in clients grid");
			System.out.println("Debug log : Contract count after adding :  "+ contractcountA.getText());
			
			int contractCountAfter = Integer.parseInt(contractcountA.getText());
			
			Thread.sleep(5000);
			
			if(contractCountAfter == (contractCountbefore+1))
			{
				Reporter.log("Contract added successfully for client Named :  "+namefirst+" "+namelast+"  Email Id :  "+email);
				TestResult.addTestResult("Contract added successfully for client Named :  "+namefirst+" "+namelast+"  Email Id :  "+email,"Passed");
			}
			else
			{
				TestResult.addTestResult("Contract not added for Email Id :  "+email,"Failed");
				coreFunc.logData("CreateClientAndContract",testID,"Failed","Error: Contract not added successfully for Test Case [ TCID: "+testID+"]",assertEnabled);
				return false;
			}
			Reporter.log("Exiting Create Contract ");
			System.out.println("Debug log : Exiting from AddContract ");
			
   		}
    	catch(NoSuchElementException eNFound)
		{
    		TestResult.addTestResult("Contract not added due to Error :  "+eNFound.getMessage(),"Failed");
    		coreFunc.logData("CreateClientAndContract",testID,"Failed","Error: "+eNFound.getMessage()+" element not found. [ TCID: "+testID+"]",assertEnabled);
    		System.out.println("Debug log : Error ==> "+eNFound.getMessage()+" element not found. [ TCID: "+testID+"]");
    		return false;
    	}
		catch(ElementNotVisibleException eNVisible)
		{
			TestResult.addTestResult("Contract not added due to Error :  "+eNVisible.getMessage(),"Failed");
			coreFunc.logData("CreateClientAndContract",testID,"Failed","Error: "+eNVisible.getMessage()+" element is not visible. [ TCID: "+testID+"]",assertEnabled);
    		System.out.println("Debug log : Error ==> "+eNVisible.getMessage()+" element is not visible. [ TCID: "+testID+"]");
    		return false;
    	}
		catch(Exception e)
		{
			TestResult.addTestResult("Contract not added due to Error :  "+e.getMessage(),"Failed");
			coreFunc.logData("CreateClientAndContract",testID,"Failed","Error: occured on "+e.getMessage()+" element.[ TCID: "+testID+"]",assertEnabled);
    		System.out.println("Debug log : Error ==> occured on  "+e.getMessage()+" element.[ TCID: "+testID+"]");
    		return false;
    	}
    	return true;
    }   
    
    private void AddContractFeatures(Object[] testData)
	{
    	System.out.println("Debug log : Entering in AddContractFeatures ");
    	String testID=String.valueOf(testData[0]);
       	try
		{	    					
			String insuranceType=String.valueOf(testData[95]);
			
			String testDataReference=String.valueOf(testData[97]); 
			String testStep="";
			if(insuranceType.length() > 0 && testDataReference.length() > 0){
			   // Go ahead to choose insurance category and fill insurance detail on page 
					testStep="Click Contract Feature tab."; 
					String[] locator =ManageLocator.getLocator("Tab.Client.Contract.Create.ContractFeature");
					WebElement eleContractFeatureTab=coreFunc.findElement(locator[0],locator[1]);
					eleContractFeatureTab.click(); 
					TestResult.addTestResult(testStep,"Passed");
					
					coreFunc.waitForWhile(2);
					testStep="Select Insurance Type as "+insuranceType+"."; 
					locator=ManageLocator.getLocator("Create.Contract.ContractFeature.Module");
					WebElement eleModuleDropDown=coreFunc.findElement(locator[0],locator[1]);
					eleModuleDropDown.click(); 
							
					coreFunc.waitForWhile(2);
					locator=ManageLocator.getLocator("Create.Contract.ContractFeature.Module.Option");
					locator[1]=locator[1].replaceAll("@optionText", insuranceType);
					WebElement eleModule=coreFunc.findElement(locator[0],locator[1]);
					if(eleModule != null){
					   eleModule.click();
					   TestResult.addTestResult(testStep,"Passed");
					}else{
					   TestResult.addTestResult(testStep,"Failed","Unable to select on designated insurance type.");	
					   return;
					}
					String moduleName=LoadProperty.getVar(insuranceType, "MappingModule");
					System.out.println("Debug log : moduleName "+moduleName);
					if(moduleName != null && !moduleName.trim().isEmpty()){
						fillInsuranceFields(testDataReference,moduleName);	
						coreFunc.waitForWhile(5);
						
						testStep="Click Contract Data Tab.";
						locator=ManageLocator.getLocator("Tab.Client.Contract.Create.ContractData");
						WebElement eleClientTab=coreFunc.findElement(locator[0],locator[1]);
						eleClientTab.click(); 
						TestResult.addTestResult(testStep,"Passed");
					}else{
						testStep="Fill insurance field after selecting module";
						TestResult.addTestResult(testStep,"Failed","Unable to find mapping module in property file hence aborted the process.");
					}
			 }				
   		}
    	catch(NoSuchElementException eNFound)
		{
    		coreFunc.logData("CreateClientAndContract",testID,"Failed","Error: "+eNFound.getMessage()+" element not found. [ TCID: "+testID+"]",assertEnabled);
    		System.out.println("Debug log : Error ==> "+eNFound.getMessage()+" element not found. [ TCID: "+testID+"]");
    		 //TestResult.addTestResult(testStep,"Failed","Error occured on this step.");
			 throw eNFound;
    	}
		catch(ElementNotVisibleException eNVisible)
		{
    		coreFunc.logData("CreateClientAndContract",testID,"Failed","Error: "+eNVisible.getMessage()+" element is not visible. [ TCID: "+testID+"]",assertEnabled);
    		System.out.println("Debug log : Error ==> "+eNVisible.getMessage()+" element is not visible. [ TCID: "+testID+"]");
    		throw eNVisible;
    	}
		catch(Exception e)
		{
    		coreFunc.logData("CreateClientAndContract",testID,"Failed","Error: occured on "+e.getMessage()+" element.[ TCID: "+testID+"]",assertEnabled);
    		System.out.println("Debug log : Error ==> occured on  "+e.getMessage()+" element.[ TCID: "+testID+"]");
    		throw e;
    	}    	
    }     
    
    private void fillInsuranceFields(String testDataReference, String insuranceType){
      	 TestData objTestData=new TestData();
      	 List<String> lsInteractedFields=new ArrayList<String>();
      	 Map<Integer,List<InsuranceField>> mapInsFieldTestData=objTestData.readInsuranceTypeTestData(testDataReference,insuranceType);
      	 
      	 Iterator<Integer> itrKey=mapInsFieldTestData.keySet().iterator();
      	 while(itrKey.hasNext()){
      		 coreFunc.waitForWhile(2);
      		 Integer key=itrKey.next();
      		 List<InsuranceField> lsInsuranceField=mapInsFieldTestData.get(key);
      		 int index=0;
       		 while(lsInsuranceField.size()>index){
      			InsuranceField objInsuranceField=lsInsuranceField.get(index);
      			if(lsInteractedFields.contains(objInsuranceField.fieldName)){
      			   index++;	
           		   continue;
      			}
      		   //Just add field in log if test data is not specified for the field.
         		if(objInsuranceField.testDataValue.isEmpty()){
         			TestResult.addTestResult("Enter "+objInsuranceField.fieldTitle+" field","Passed","Execution skipped for this field since test data is not specified in TestData sheet for this field.");
         			index++;	
         			continue;
         		}
         		
         		 String[] testResult=null;
          		 if(!objInsuranceField.dependOn.isEmpty()){
          			 InsuranceField dependsOnItem=getDependsOnItem(mapInsFieldTestData,objInsuranceField.dependOn);
          		     if(dependsOnItem==null){
          		       TestResult.addTestResult("Input Data into"+objInsuranceField.fieldTitle,"Failed","This field is depended on "+objInsuranceField.dependOn+" field and test data should be present for depends on field.");
          		       lsInteractedFields.add(objInsuranceField.fieldName);
          		       index++;
          		       continue;
          		     }
          		     
          			 testResult=interactDependsOnFirst(dependsOnItem);
          			 lsInteractedFields.add(dependsOnItem.fieldName);
          			 TestResult.addTestResult(testResult[0],testResult[1],testResult[2]);
          		 }
          		 testResult=coreFunc.interactWithElementBasedOnType(objInsuranceField);
          		 lsInteractedFields.add(objInsuranceField.fieldName);
          		 TestResult.addTestResult(testResult[0],testResult[1],testResult[2]);
          		 index++;
          	 }
         }
    }
       
    private String[] interactDependsOnFirst(InsuranceField objInsuranceField){
      	 String[] testResult=coreFunc.interactWithElementBasedOnType(objInsuranceField);    	 
      	 return testResult;
    } 
       
    private InsuranceField getDependsOnItem(Map<Integer,List<InsuranceField>> mapInsFieldTestData,String title){
      	 Iterator<Integer> itrKey=mapInsFieldTestData.keySet().iterator();
      	 InsuranceField dependsOnField=null;
      	 while(itrKey.hasNext() && dependsOnField==null){
      		 Integer key=itrKey.next();
      		 List<InsuranceField> lsInsuranceField=mapInsFieldTestData.get(key);
      		 int index=0;
      		 while(lsInsuranceField.size() > index && dependsOnField==null){
      			InsuranceField objInsuranceField=lsInsuranceField.get(index);
         		 if(title.equalsIgnoreCase(objInsuranceField.fieldTitle)){
         			 dependsOnField=objInsuranceField;
         			 break;
         		 }	
         		index++;
      		 }
      		 
      	 }
      	 
      	 return dependsOnField;
    }
       
        
}

