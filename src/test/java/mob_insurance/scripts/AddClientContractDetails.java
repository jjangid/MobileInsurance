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
				//Open URL and Login will only be called when Login needs to be done					
					String loginText = (String) testData[tcNo][2];
					String[] loginVariable = coreFunc.GetLoginRole(loginText);					
					if(String.valueOf(loginVariable[0].trim()).equalsIgnoreCase("TRUE")){						
						//Login to application with specified credentials
						if(!coreFunc.Login(loginVariable[1])){
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
						TestResult.addTestResult("CreateClientAndContract Test Case : "+String.valueOf(testData[tcNo][0]),"Passed");
						System.out.println("Test Case"+testData[tcNo][0]+" is passed.");
					}
					
					boolean needToLogOut=Boolean.valueOf(String.valueOf(testData[tcNo][95]));
					if(needToLogOut){
					  coreFunc.Logout();					  
					}
					getDriver().navigate().refresh();
					waitForLoad();
				}
				else
				{
					TestResult.addTestResult("Test Case : "+String.valueOf(testData[tcNo][0]),"Skipped");
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
			TestResult.addTestResult("CreateClientAndContract failed due to error occured","Failed");
			TestResult.appendTestResult();
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
			List<WebElement> salutationelement  = coreFunc.findElements("xpath",LoadProperty.getVar("salutation", "element"));
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
			
			WebElement titleTb = coreFunc.findElement("name",LoadProperty.getVar("title", "element"));
			titleTb.sendKeys(title);
			TestResult.addTestResult("Value '"+title+"' selected in 'Title' dropdown" ,"Passed");
			Reporter.log("Value '"+title+"' selected in 'Title' dropdown  ==>  Step Pass");
			
			WebElement firstName = coreFunc.findElement("id",LoadProperty.getVar("firstName", "element"));
			firstName.clear();
			firstName.sendKeys(namefirst);
			TestResult.addTestResult("Entered value '"+namefirst+"' in 'First Name' field" ,"Passed");
			Reporter.log("Entered value '"+namefirst+"' in 'First Name' field  ==>  Step Pass");
			
			WebElement lastName = coreFunc.findElement("id",LoadProperty.getVar("lastName", "element"));
			lastName.clear();
			lastName.sendKeys(namelast);
			TestResult.addTestResult("Entered value '"+namelast+"' in 'LastName' field" ,"Passed");
			Reporter.log("Entered value '"+namelast+"' in 'LastName' field  ==>  Step Pass");
			
			WebElement dob_dd = coreFunc.findElement("id",LoadProperty.getVar("dob", "element"));
			dob_dd.clear();
			dob_dd.sendKeys(dob);
			TestResult.addTestResult("Entered value '"+dob+"' in 'BirthDate' field" ,"Passed");
			Reporter.log("Entered value '"+dob+"' in 'BirthDate' field  ==>  Step Pass");
			
			WebDriverWait wait = new WebDriverWait(getDriver(), 30);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(LoadProperty.getVar("birthName", "element"))));
			WebElement birthNameTb = coreFunc.findElement("name",LoadProperty.getVar("birthName", "element"));
			birthNameTb.clear();
			birthNameTb.sendKeys(birthname);
			TestResult.addTestResult("Entered value '"+birthname+"' in 'Birth Name' field" ,"Passed");
			Reporter.log("Entered value '"+birthname+"' in 'Birth Name' field  ==>  Step Pass");
			
			WebElement birthPlaceTb = coreFunc.findElement("name",LoadProperty.getVar("birthPlace", "element"));
			birthPlaceTb.clear();
			birthPlaceTb.sendKeys(birthplace);
			TestResult.addTestResult("Entered value '"+birthplace+"' in 'BirthPlace' field" ,"Passed");
			Reporter.log("Entered value '"+birthplace+"' in 'BirthPlace' field  ==>  Step Pass");
			
			WebElement street = coreFunc.findElement("name",LoadProperty.getVar("street", "element"));
			street.clear();
			street.sendKeys(streetName);
			TestResult.addTestResult("Entered value '"+streetName+"' in 'StreetName' field" ,"Passed");
			Reporter.log("Entered value '"+streetName+"' in 'StreetName' field  ==>  Step Pass");
			
			WebElement streetNumTb = coreFunc.findElement("name",LoadProperty.getVar("streetNum", "element"));
			streetNumTb.clear();
			streetNumTb.sendKeys(streetNum);
			TestResult.addTestResult("Entered value '"+streetNum+"' in 'Street/No' field" ,"Passed");
			Reporter.log("Entered value '"+streetNum+"' in 'Street/No' field  ==>  Step Pass");
			
			WebElement ziptb = coreFunc.findElement("name",LoadProperty.getVar("zip", "element"));
			ziptb.clear();
			ziptb.sendKeys(zip);
			TestResult.addTestResult("Entered value '"+zip+"' in 'PLZ' field" ,"Passed");
			Reporter.log("Entered value '"+zip+"' in 'PLZ' field  ==>  Step Pass");
			
			WebElement cityTb = coreFunc.findElement("name",LoadProperty.getVar("city", "element"));
			cityTb.clear();
			cityTb.sendKeys(city);
			TestResult.addTestResult("Entered value '"+city+"' in 'City' field" ,"Passed");
			Reporter.log("Entered value '"+city+"' in 'City' field  ==>  Step Pass");
			
			WebElement phoneTb = coreFunc.findElement("name",LoadProperty.getVar("phone", "element"));
			phoneTb.clear();
			phoneTb.sendKeys(phone);
			TestResult.addTestResult("Entered value '"+phone+"' in 'TelePhone' field" ,"Passed");
			Reporter.log("Entered value '"+phone+"' in 'TelePhone' field  ==>  Step Pass");
			
			WebElement emailTb = coreFunc.findElement("name",LoadProperty.getVar("email", "element"));
			emailTb.clear();
			emailTb.sendKeys(email);
			TestResult.addTestResult("Entered value '"+email+"' in 'Email' field" ,"Passed");
			Reporter.log("Entered value '"+email+"' in 'Email' field  ==>  Step Pass");
			
			WebElement mobileTb = coreFunc.findElement("name",LoadProperty.getVar("mobile", "element"));
			mobileTb.clear();
			mobileTb.sendKeys(mobile);
			TestResult.addTestResult("Entered value '"+mobile+"' in 'Mobile' field" ,"Passed");
			Reporter.log("Entered value '"+mobile+"' in 'Mobile' field  ==>  Step Pass");
						
			//Click on save client button
			coreFunc.ClickOnActionButton("Save");
			TestResult.addTestResult("Clicked on 'Save' client button" ,"Passed");
			Reporter.log("Clicked on 'Save' client button  ==>  Step Pass");
						
			coreFunc.waitForWhile(5);

			clientWait.until(ExpectedConditions.invisibilityOfElementLocated(By.className(LoadProperty.getVar("clientGridWait", "element"))));
			//System.out.println("Debug log : After client saved");
			Reporter.log("Search client by Email Id :  "+email);
			coreFunc.waitForWhile(5);
			WebElement clientSearchBox = coreFunc.findElement("name",LoadProperty.getVar("clientSearchBox", "element"));
			clientSearchBox.clear();
			clientSearchBox.sendKeys(email);
			TestResult.addTestResult("Entered value '"+email+"' in 'Client Search' field" ,"Passed");
			Reporter.log("Entered value '"+email+"' in 'Client Search' field  ==>  Step Pass");
			
			clientWait.until(ExpectedConditions.invisibilityOfElementLocated(By.className(LoadProperty.getVar("clientGridWait", "element"))));
			coreFunc.waitForWhile(5);
			//select first record
			WebElement firstRecord = coreFunc.findElement("xpath",LoadProperty.getVar("firstRecord", "element"));
			firstRecord.click();			
					
			//Verify client found or not with specified name in grid
			WebElement recordcount = coreFunc.findElement("xpath",LoadProperty.getVar("recordCount", "element"));
			if(recordcount.getText().equalsIgnoreCase(coreFunc.GetDELanguageText("No data to display"))){
				TestResult.addTestResult("Client not Created with Email ID '"+email ,"Failed");
				coreFunc.logData("CreateClientAndContract",testID,"Failed","Error: No Client created with Email ID : "+email+"  [ TCID: "+testID+"]",assertEnabled);	    		
	    		return false;
			}
			
			coreFunc.waitForWhile(3);
			WebDriverWait clientGridWait = new WebDriverWait(getDriver(), 45);			
			clientGridWait.until(ExpectedConditions.visibilityOfElementLocated(By.name(LoadProperty.getVar("email", "element"))));
			List <WebElement> emailExists = getDriver().findElements(By.xpath("//li[@data-email='"+email+"']"));
			System.out.println("Email created  : "+emailExists.size());
			for (int j = 0; j <emailExists.size() ; j++) {
		        WebElement item = emailExists.get(j);
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
       		coreFunc.waitForWhile(3);
       		
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
    		System.out.println("Debug log : Entering in CreateClientDetails 1");
    		//click on relatives tab
    		WebElement eleProviderName=coreFunc.findElement("linkText",LoadProperty.getVar("relatives", "element"));
			eleProviderName.click();
    		TestResult.addTestResult("Clicked on 'Relative' Tab" ,"Passed");
			Reporter.log("Clicked on 'Relative' Tab  ==>  Step Pass");
    		
    		//click on add relative button    		
    		getDriver().findElement(By.xpath(LoadProperty.getVar("addRelative", "element"))).click();
    		TestResult.addTestResult("Clicked on 'Add Relative' Button" ,"Passed");
			Reporter.log("Clicked on 'Add Relative' Button  ==>  Step Pass");
			
    		//select value in relation dropdown
    		WebElement relationDD =  getDriver().findElement(By.cssSelector(LoadProperty.getVar("relationsDD", "element")));
			relationDD.click();  
    		coreFunc.SelectValueInDropdown(fd_relation);
			TestResult.addTestResult("Value '"+fd_relation+"' selected in 'Relation' dropdown" ,"Passed");
			Reporter.log("Value '"+fd_relation+"' selected in 'Relation' dropdown  ==>  Step Pass");

    		//select value in salutation dropdown
			List<WebElement> salutationelement = coreFunc.findElements("xpath",LoadProperty.getVar("salutationDD", "element"));
			coreFunc.waitForWhile(2);
			for (int i = 0; i <salutationelement.size() ; i++) {
		        WebElement sitem = salutationelement.get(i);
		        if(sitem.isDisplayed()){
		        	sitem.click();
		        	break;
		        }	        
		    }			
			coreFunc.SelectValueInDropdown(fd_salutation);
			TestResult.addTestResult("Value '"+fd_salutation+"' selected in 'Salutation' dropdown" ,"Passed");
			Reporter.log("Value '"+fd_salutation+"' selected in 'Salutation' dropdown  ==>  Step Pass");
			
			//select value in title dropdown
			List<WebElement> titleelement =coreFunc.findElements("xpath",LoadProperty.getVar("titleElement", "element")); 
			for (int i = 0; i <titleelement.size() ; i++) {
		        WebElement titem = titleelement.get(i);
		        if(titem.isDisplayed()){		        	
		        	titem.sendKeys(fd_title);
		        	break;
		        }	        
		    }
			TestResult.addTestResult("Value '"+fd_title+"' selected in 'Title' dropdown" ,"Passed");
			Reporter.log("Value '"+fd_title+"' selected in 'Title' dropdown  ==>  Step Pass");			
					
			//Enter value in First name text box
			List<WebElement> firstname =coreFunc.findElements("xpath",LoadProperty.getVar("first_Name", "element")); 
			for (int i = 0; i <firstname.size() ; i++) {
		        WebElement fnitem = firstname.get(i);
		        if(fnitem.isDisplayed()){
		        	fnitem.sendKeys(fd_firstname);
		        	break;
		        }	        
		    }			
	    	TestResult.addTestResult("Entered value '"+fd_firstname+"' in 'First Name' field" ,"Passed");
			Reporter.log("Entered value '"+fd_firstname+"' in 'First Name' field  ==>  Step Pass");
			
			//Enter value in Last name text box
			List<WebElement> lastname = coreFunc.findElements("xpath",LoadProperty.getVar("last_Name", "element"));
			for (int i = 0; i <lastname.size() ; i++) {
		        WebElement lnitem = lastname.get(i);
		        if(lnitem.isDisplayed()){
		        	lnitem.sendKeys(fd_lastname);
		        	break;
		        }	        
		    }	
			TestResult.addTestResult("Entered value '"+fd_lastname+"' in 'Last Name' field" ,"Passed");
			Reporter.log("Entered value '"+fd_lastname+"' in 'Last Name' field  ==>  Step Pass");
			
			
			//Enter value in DOB  box
			List<WebElement> dob = coreFunc.findElements("xpath",LoadProperty.getVar("birth_Date", "element"));
			for (int i = 0; i <dob.size() ; i++) {
		        WebElement ditem = dob.get(i);
		        if(ditem.isDisplayed()){
		        	ditem.sendKeys(fd_dob);
		        	break;
		        }	        
		    }		
			TestResult.addTestResult("Entered value '"+fd_dob+"' in 'DOB' field" ,"Passed");
			Reporter.log("Entered value '"+fd_dob+"' in 'DOB' field  ==>  Step Pass");
			
			//Enter value in communication data text box
			List<WebElement> woc = coreFunc.findElements("xpath",LoadProperty.getVar("communication", "element")); 
			for (int i = 0; i <woc.size() ; i++) {
		        WebElement witem = woc.get(i);
		        if(witem.isDisplayed()){
		        	witem.click();
		        	break;
		        }	        
		    }		
			coreFunc.SelectValueInDropdown(fd_woc);
			TestResult.addTestResult("Entered value '"+fd_woc+"' in 'Way Of Communication' field" ,"Passed");
			Reporter.log("Entered value '"+fd_woc+"' in 'Way Of Communication' field  ==>  Step Pass");
			
			//Enter value in communication data text box
			List<WebElement> comdata = coreFunc.findElements("xpath",LoadProperty.getVar("communicationData", "element")); 
			for (int i = 0; i <comdata.size() ; i++) {
		        WebElement cditem = comdata.get(i);
		        if(cditem.isDisplayed()){
		        	cditem.sendKeys(fd_comdata);
		        	break;
		        }	        
		    }				
			TestResult.addTestResult("Entered value '"+fd_woc+"' in 'Communication Data' field" ,"Passed");
			Reporter.log("Entered value '"+fd_woc+"' in 'Communication Data' field  ==>  Step Pass");
			
			List<WebElement> savebtn = coreFunc.findElements("xpath",LoadProperty.getVar("saveBtnRelative", "element")); 
			for (int i = 0; i <=savebtn.size() ; i++) {
		        WebElement item = savebtn.get(i);
		        if (item.getText().equals(coreFunc.GetDELanguageText("Save"))) {
		            item.click();
		            break;
		        }
		    }
			System.out.println("Debug log : Exiting from CreateClientDetails ");
			coreFunc.waitForWhile(2);
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
    		WebElement professionTab = coreFunc.findElement("linkText",LoadProperty.getVar("professionTab", "element"));
    		professionTab.click();
    		TestResult.addTestResult("Clicked on 'Profession' Tab" ,"Passed");
			Reporter.log("Clicked on 'Profession' Tab  ==>  Step Pass");
			
    		//click on Edit button
    		coreFunc.ClickOnActionButton("Edit");
    		TestResult.addTestResult("Clicked on 'Edit' button" ,"Passed");
			Reporter.log("Clicked on 'Edit' button  ==>  Step Pass");
			
    		WebDriverWait clientWait = new WebDriverWait(getDriver(), 45);
    		clientWait.until(ExpectedConditions.visibilityOfElementLocated(By.name(LoadProperty.getVar("profession", "element"))));
    		
    		WebElement profession = coreFunc.findElement("name",LoadProperty.getVar("profession", "element"));
    		profession.clear();
    		profession.sendKeys(pd_profession);
			TestResult.addTestResult("Entered value '"+pd_profession+"' in 'Profession' field" ,"Passed");
			Reporter.log("Entered value '"+pd_profession+"' in 'Profession' field  ==>  Step Pass");
			
			WebElement jobType = coreFunc.findElement("id",LoadProperty.getVar("jobType", "element"));
			jobType.click();
    		coreFunc.SelectValueInDropdown(pd_jobtype);
			TestResult.addTestResult("Value '"+pd_jobtype+"' selected in 'Job Type' dropdown" ,"Passed");
			Reporter.log("Value '"+pd_jobtype+"' selected in 'Job Type' dropdown  ==>  Step Pass");
						
			WebElement workPercentagePhysical = coreFunc.findElement("name",LoadProperty.getVar("workPercentagePhysical", "element"));
			workPercentagePhysical.clear();
			workPercentagePhysical.sendKeys(pd_physicalperc);    		
			TestResult.addTestResult("Entered value '"+pd_physicalperc+"' in 'Physical %' field" ,"Passed");
			Reporter.log("Entered value '"+pd_physicalperc+"' in 'Physical %' field  ==>  Step Pass");
			
			WebElement incomeYearlyBurutto = coreFunc.findElement("name",LoadProperty.getVar("incomeYearlyBurutto", "element"));
			incomeYearlyBurutto.clear();
			incomeYearlyBurutto.sendKeys(pd_yearBru);    	
			TestResult.addTestResult("Entered value '"+pd_yearBru+"' in 'Yearly Brutto' field" ,"Passed");
			Reporter.log("Entered value '"+pd_yearBru+"' in 'Yearly Brutto' field  ==>  Step Pass");
			
			WebElement incomeYearlyNetto = coreFunc.findElement("name",LoadProperty.getVar("incomeYearlyNetto", "element"));
			incomeYearlyNetto.clear();
			incomeYearlyNetto.sendKeys(pd_yerlynetto);  			
			TestResult.addTestResult("Entered value '"+pd_yerlynetto+"' in 'Yearly Netto' field" ,"Passed");
			Reporter.log("Entered value '"+pd_yerlynetto+"' in 'Yearly Netto' field  ==>  Step Pass");
			
			WebElement incomeYearlySalaries = coreFunc.findElement("name",LoadProperty.getVar("incomeYearlySalaries", "element"));
			incomeYearlySalaries.clear();
			incomeYearlySalaries.sendKeys(pd_yearlysal); 
			TestResult.addTestResult("Entered value '"+pd_yearlysal+"' in 'Yearly Salaries' field" ,"Passed");
			Reporter.log("Entered value '"+pd_yearlysal+"' in 'Yearly Salaries' field  ==>  Step Pass");
			
			WebElement retirementSavingSince = coreFunc.findElement("name",LoadProperty.getVar("retirementSavingSince", "element"));
			retirementSavingSince.clear();
			retirementSavingSince.sendKeys(pd_savingSince); 
			TestResult.addTestResult("Entered value '"+pd_savingSince+"' in 'Saving Since' field" ,"Passed");
			Reporter.log("Entered value '"+pd_savingSince+"' in 'Saving Since' field  ==>  Step Pass");
			
			WebElement capitalFormingPayments = coreFunc.findElement("name",LoadProperty.getVar("capitalFormingPayments", "element"));
			capitalFormingPayments.clear();
			capitalFormingPayments.sendKeys(pd_capfrom); 
			TestResult.addTestResult("Entered value '"+pd_capfrom+"' in 'Capital Forming Payments' field" ,"Passed");
			Reporter.log("Entered value '"+pd_capfrom+"' in 'Capital Forming Payments' field  ==>  Step Pass");
			
			WebElement taxOfficeName = coreFunc.findElement("name",LoadProperty.getVar("taxOfficeName", "element"));
			taxOfficeName.clear();
			taxOfficeName.sendKeys(pd_taxoffice); 
			TestResult.addTestResult("Entered value '"+pd_taxoffice+"' in 'Tax Office' field" ,"Passed");
			Reporter.log("Entered value '"+pd_taxoffice+"' in 'Tax Office' field  ==>  Step Pass");
			
			WebElement taxNumber = coreFunc.findElement("name",LoadProperty.getVar("taxNumber", "element"));
			taxNumber.clear();
			taxNumber.sendKeys(pd_taxnum); 
			TestResult.addTestResult("Entered value '"+pd_taxnum+"' in 'Tax Number' field" ,"Passed");
			Reporter.log("Entered value '"+pd_taxnum+"' in 'Tax Number' field  ==>  Step Pass");
			
			WebElement taxId = coreFunc.findElement("name",LoadProperty.getVar("taxId", "element"));
			taxId.clear();
			taxId.sendKeys(pd_taxid); 
			TestResult.addTestResult("Entered value '"+pd_taxid+"' in 'Tax Id' field" ,"Passed");
			Reporter.log("Entered value '"+pd_taxid+"' in 'Tax Id' field  ==>  Step Pass");
			
			WebElement taxClass = coreFunc.findElement("name",LoadProperty.getVar("taxClass", "element"));
			taxClass.clear();
			taxClass.sendKeys(pd_taxclass); 
			TestResult.addTestResult("Entered value '"+pd_taxclass+"' in 'Tax Class' field" ,"Passed");
			Reporter.log("Entered value '"+pd_taxclass+"' in 'Tax Class' field  ==>  Step Pass");
			
			WebElement churchTaxPercentage = coreFunc.findElement("name",LoadProperty.getVar("churchTaxPercentage", "element"));
			churchTaxPercentage.clear();
			churchTaxPercentage.sendKeys(pd_churchtax); 
			TestResult.addTestResult("Entered value '"+pd_churchtax+"' in 'Church Tax Percentage' field" ,"Passed");
			Reporter.log("Entered value '"+pd_churchtax+"' in 'Church Tax Percentage' field  ==>  Step Pass");
			
			WebElement socialInsuranceNumber = coreFunc.findElement("name",LoadProperty.getVar("socialInsuranceNumber", "element"));
			socialInsuranceNumber.clear();
			socialInsuranceNumber.sendKeys(pd_sinsnumber);
			TestResult.addTestResult("Entered value '"+pd_sinsnumber+"' in 'Social Insurance Number' field" ,"Passed");
			Reporter.log("Entered value '"+pd_sinsnumber+"' in 'Social Insurance Number' field  ==>  Step Pass");
			
			WebElement healthInsurance = coreFunc.findElement("name",LoadProperty.getVar("healthInsurance", "element"));
			healthInsurance.clear();
			healthInsurance.sendKeys(pd_healthInsu);
			TestResult.addTestResult("Entered value '"+pd_healthInsu+"' in 'Health Insurance' field" ,"Passed");
			Reporter.log("Entered value '"+pd_healthInsu+"' in 'Health Insurance' field  ==>  Step Pass");
			
			//click on Save button
    		coreFunc.ClickOnActionButton("Save");
    		TestResult.addTestResult("Clicked on 'Save' button" ,"Passed");
			Reporter.log("Clicked on 'Save' button  ==>  Step Pass");
    		clientWait.until(ExpectedConditions.invisibilityOfElementLocated(By.className(LoadProperty.getVar("clientGridWait", "element"))));
		  
			System.out.println("Debug log : Exiting from AddClientProfession ");
			coreFunc.waitForWhile(2);
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
    		String jd_employer =String.valueOf(testData[68]);
			String jd_contact = String.valueOf(testData[69]);
    		String jd_street =String.valueOf(testData[70]);
    		String jd_number =String.valueOf(testData[71]);
    		String jd_zip =String.valueOf(testData[72]);
    		//String jd_city =String.valueOf(testData[73]);
    		//String jd_country =String.valueOf(testData[74]);
    		
    		//Enter client Job data			
			//click on Job tab
    		WebElement jobTab = coreFunc.findElement("linkText",LoadProperty.getVar("jobTab", "element"));
    		jobTab.click();
			TestResult.addTestResult("Clicked on 'Job' Tab" ,"Passed");
			Reporter.log("Clicked on 'Job' Tab  ==>  Step Pass");
			//click on Edit button
			coreFunc.ClickOnActionButton("Edit");
			TestResult.addTestResult("Clicked on 'Edit' button" ,"Passed");
			Reporter.log("Clicked on 'Edit' button  ==>  Step Pass");
			
			WebDriverWait clientWait = new WebDriverWait(getDriver(), 45);
			clientWait.until(ExpectedConditions.visibilityOfElementLocated(By.name(LoadProperty.getVar("employerName", "element"))));
						
			WebElement employerName = coreFunc.findElement("name",LoadProperty.getVar("employerName", "element"));
			employerName.clear();
			employerName.sendKeys(jd_employer);
			TestResult.addTestResult("Entered value '"+jd_employer+"' in 'Employer' field" ,"Passed");
			Reporter.log("Entered value '"+jd_employer+"' in 'Employer' field  ==>  Step Pass");
			
			WebElement employerContact = coreFunc.findElement("name",LoadProperty.getVar("employerContact", "element"));
			employerContact.clear();
			employerContact.sendKeys(jd_contact);
			TestResult.addTestResult("Entered value '"+jd_contact+"' in 'Contact' field" ,"Passed");
			Reporter.log("Entered value '"+jd_contact+"' in 'Contact' field  ==>  Step Pass");
			
			WebElement employerStreet = coreFunc.findElement("name",LoadProperty.getVar("employerStreet", "element"));
			employerStreet.clear();
			employerStreet.sendKeys(jd_street);
			TestResult.addTestResult("Entered value '"+jd_street+"' in 'Street' field" ,"Passed");
			Reporter.log("Entered value '"+jd_street+"' in 'Street' field  ==>  Step Pass");
			
			WebElement employerStreet_num = coreFunc.findElement("name",LoadProperty.getVar("employerStreet_num", "element"));
			employerStreet_num.clear();
			employerStreet_num.sendKeys(jd_number);
			TestResult.addTestResult("Entered value '"+jd_number+"' in 'Number' field" ,"Passed");
			Reporter.log("Entered value '"+jd_number+"' in 'Number' field  ==>  Step Pass");
			
			WebElement employerZip = coreFunc.findElement("name",LoadProperty.getVar("employerZip", "element"));
			employerZip.clear();
			employerZip.sendKeys(jd_zip);
			TestResult.addTestResult("Entered value '"+jd_zip+"' in 'ZIP' field" ,"Passed");
			Reporter.log("Entered value '"+jd_zip+"' in 'ZIP' field  ==>  Step Pass");
			
			//WebElement employerCity = coreFunc.findElement("xpath",LoadProperty.getVar("employerCity", "element"));
			//employerCity.click();
			//new Actions(getDriver()).sendKeys(Keys.ARROW_DOWN).perform();
			//coreFunc.SelectValueInDropdown(jd_city);
			//TestResult.addTestResult("Value '"+jd_city+"' selected in 'City' dropdown" ,"Passed");
			//Reporter.log("Value '"+jd_city+"' selected in 'City' dropdown  ==>  Step Pass");
			
			//WebElement employerCountry = coreFunc.findElement("name",LoadProperty.getVar("employerCountry", "element"));
			//employerCountry.click();
			//new Actions(getDriver()).sendKeys(Keys.ARROW_DOWN).perform();			
			//coreFunc.SelectValueInDropdown(jd_country);
			//TestResult.addTestResult("Value '"+jd_country+"' selected in 'Country' dropdown" ,"Passed");
			//Reporter.log("Value '"+jd_country+"' selected in 'Country' dropdown  ==>  Step Pass");
										
			//click on Save button
			coreFunc.ClickOnActionButton("Save");			
			TestResult.addTestResult("Clicked on 'Save' button" ,"Passed");
			Reporter.log("Clicked on 'Save' button  ==>  Step Pass");
			
			clientWait.until(ExpectedConditions.invisibilityOfElementLocated(By.className(LoadProperty.getVar("clientGridWait", "element"))));
			System.out.println("Debug log : Exiting from AddClientJob ");
			coreFunc.waitForWhile(2);
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
    		String rd_weight =String.valueOf(testData[76]);
    		String rd_height =String.valueOf(testData[77]);
    		String rd_smoker =String.valueOf(testData[78]);
    		String rd_healthinfo =String.valueOf(testData[79]);
			
			//click on Risk tab
    		WebElement riskTab = coreFunc.findElement("linkText",LoadProperty.getVar("riskTab", "element"));
    		riskTab.click();
			TestResult.addTestResult("Clicked on 'Risk' Tab" ,"Passed");
			Reporter.log("Clicked on 'Risk' Tab  ==>  Step Pass");
			
	   		//click on Edit button
    		coreFunc.ClickOnActionButton("Edit");
    		TestResult.addTestResult("Clicked on 'Edit' button" ,"Passed");
			Reporter.log("Clicked on 'Edit' button  ==>  Step Pass");
			
			WebDriverWait clientWait = new WebDriverWait(getDriver(), 45);
			clientWait.until(ExpectedConditions.visibilityOfElementLocated(By.name(LoadProperty.getVar("weight", "element"))));
    		
    		//Enter client risk data
			WebElement weight = coreFunc.findElement("name",LoadProperty.getVar("weight", "element"));
			weight.clear();
			weight.sendKeys(rd_weight);
			TestResult.addTestResult("Entered value '"+rd_weight+"' in 'Weight' field" ,"Passed");
			Reporter.log("Entered value '"+rd_weight+"' in 'Weight' field  ==>  Step Pass");
			
			WebElement height = coreFunc.findElement("name",LoadProperty.getVar("height", "element"));
			height.clear();
			height.sendKeys(rd_height);
			TestResult.addTestResult("Entered value '"+rd_height+"' in 'Height' field" ,"Passed");
			Reporter.log("Entered value '"+rd_height+"' in 'Height' field  ==>  Step Pass");
			
			WebElement smoker = coreFunc.findElement("name",LoadProperty.getVar("smoker", "element"));
			smoker.click();
			new Actions(getDriver()).sendKeys(Keys.ARROW_DOWN).perform();			
			coreFunc.SelectValueInDropdown(rd_smoker);
			TestResult.addTestResult("Value '"+rd_smoker+"' selected in 'Smoker' dropdown" ,"Passed");
			Reporter.log("Value '"+rd_smoker+"' selected in 'Smoker' dropdown  ==>  Step Pass");
			
			WebElement healthInfo = coreFunc.findElement("name",LoadProperty.getVar("healthInfo", "element"));
			healthInfo.clear();
			healthInfo.sendKeys(rd_healthinfo);
			TestResult.addTestResult("Entered value '"+rd_healthinfo+"' in 'Health Info' field" ,"Passed");
			Reporter.log("Entered value '"+rd_healthinfo+"' in 'Health Info' field  ==>  Step Pass");
			
			//click on Save button
			coreFunc.ClickOnActionButton("Save");
			TestResult.addTestResult("Clicked on 'Save' button" ,"Passed");
			Reporter.log("Clicked on 'Save' button  ==>  Step Pass");
			
    		System.out.println("Debug log : Exiting from AddClientRisk ");
    		coreFunc.waitForWhile(2);
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
    		coreFunc.waitForWhile(2);
    		//click on Bank tab
    		WebElement bankTab = coreFunc.findElement("linkText",LoadProperty.getVar("bankTab", "element"));
    		bankTab.click();
			TestResult.addTestResult("Clicked on 'Bank' Tab" ,"Passed");
			Reporter.log("Clicked on 'Bank' Tab  ==>  Step Pass");
			
			//click on add bank details icon 
			WebElement addBank = coreFunc.findElement("xpath",LoadProperty.getVar("addBank", "element"));
			addBank.click();
    		TestResult.addTestResult("Clicked on 'Add Bank Detail' button" ,"Passed");
			Reporter.log("Clicked on 'Add Bank Detail' Tab  ==>  Step Pass");
			
    		//select value in type dropdown
    		getDriver().findElement(By.cssSelector(LoadProperty.getVar("type", "element"))).click();  
    		coreFunc.SelectValueInDropdown(bd_type);
			TestResult.addTestResult("Value '"+bd_type+"' selected in 'Type' dropdown" ,"Passed");
			Reporter.log("Value '"+bd_type+"' selected in 'Type' dropdown  ==>  Step Pass");
			
			WebElement holderName = coreFunc.findElement("name",LoadProperty.getVar("holderName", "element"));
			holderName.clear();
			holderName.sendKeys(bd_holdername);
    		TestResult.addTestResult("Entered value '"+bd_holdername+"' in 'Holder Name' field" ,"Passed");
			Reporter.log("Entered value '"+bd_holdername+"' in 'Holder Name' field  ==>  Step Pass");
			
			if(bd_type.equals(coreFunc.GetDELanguageText("Account")))
			{
				WebElement bankName = coreFunc.findElement("xpath",LoadProperty.getVar("bankName", "element"));
				bankName.click();				
				//getDriver().findElement(By.xpath(LoadProperty.getVar("bankName", "element"))).click();  
				new Actions(getDriver()).sendKeys(Keys.ARROW_DOWN).perform();
				coreFunc.SelectValueInDropdown(bd_bankname);
				TestResult.addTestResult("Value '"+bd_bankname+"' selected in 'Bank Name' dropdown" ,"Passed");
				Reporter.log("Value '"+bd_bankname+"' selected in 'Bank Name' dropdown  ==>  Step Pass");
				
				WebElement bankNumber = coreFunc.findElement("name",LoadProperty.getVar("bankNumber", "element"));
				bankNumber.clear();
				bankNumber.sendKeys(bd_banknumber);
				TestResult.addTestResult("Entered value '"+bd_banknumber+"' in 'Bank Number' field" ,"Passed");
				Reporter.log("Entered value '"+bd_banknumber+"' in 'Bank Number' field  ==>  Step Pass");	
				
				WebElement accountNo = coreFunc.findElement("name",LoadProperty.getVar("accountNo", "element"));
				accountNo.clear();
				accountNo.sendKeys(bd_accountnum);
				TestResult.addTestResult("Entered value '"+bd_accountnum+"' in 'Account No' field" ,"Passed");
				Reporter.log("Entered value '"+bd_accountnum+"' in 'Account No' field  ==>  Step Pass");
			
				WebElement iban = coreFunc.findElement("name",LoadProperty.getVar("iban", "element"));
				iban.clear();
				iban.sendKeys(bd_iban);
				TestResult.addTestResult("Entered value '"+bd_iban+"' in 'IBAN' field" ,"Passed");
				Reporter.log("Entered value '"+bd_iban+"' in 'IBAN' field  ==>  Step Pass");
				
				WebElement bic = coreFunc.findElement("name",LoadProperty.getVar("bic", "element"));
				bic.clear();
				bic.sendKeys(bd_bic);
				TestResult.addTestResult("Entered value '"+bd_bic+"' in 'BIC' field" ,"Passed");
				Reporter.log("Entered value '"+bd_bic+"' in 'BIC' field  ==>  Step Pass");
			}
			else
			{
				WebElement cardName = coreFunc.findElement("name",LoadProperty.getVar("cardName", "element"));
				cardName.clear();
				cardName.sendKeys(bd_cardName);
				TestResult.addTestResult("Entered value '"+bd_cardName+"' in 'Card Name' field" ,"Passed");
				Reporter.log("Entered value '"+bd_cardName+"' in 'Card Name' field  ==>  Step Pass");
				
				WebElement cardNumber = coreFunc.findElement("name",LoadProperty.getVar("cardNumber", "element"));
				cardNumber.clear();
				cardNumber.sendKeys(bd_cardNum);
				TestResult.addTestResult("Entered value '"+bd_cardNum+"' in 'Card Number' field" ,"Passed");
				Reporter.log("Entered value '"+bd_cardNum+"' in 'Card Number' field  ==>  Step Pass");
				
				WebElement validTo = coreFunc.findElement("name",LoadProperty.getVar("validTo", "element"));
				validTo.clear();
				validTo.sendKeys(bd_validUpto);
				TestResult.addTestResult("Entered value '"+bd_validUpto+"' in 'Valid To' field" ,"Passed");
				Reporter.log("Entered value '"+bd_validUpto+"' in 'Valid To' field  ==>  Step Pass");
			}	
			//click on Save button
			List<WebElement> savebtn = coreFunc.findElements("xpath",LoadProperty.getVar("saveBtnRelative", "element"));
			for (int i = 0; i <=savebtn.size() ; i++) {
		        WebElement item = savebtn.get(i);
		        if (item.getText().equals(coreFunc.GetDELanguageText("Save"))) {
		            item.click();
		            break;
		        }
		   
			}
			TestResult.addTestResult("Clicked on 'Save' button" ,"Passed");
			Reporter.log("Clicked on 'Save' button  ==>  Step Pass");
			System.out.println("Debug log : Exiting from AddClientBank");
			coreFunc.waitForWhile(5);//Thread.sleep(5000);
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
    		coreFunc.waitForWhile(3);
    		clientWait.until(ExpectedConditions.invisibilityOfElementLocated(By.className(LoadProperty.getVar("clientGridWait", "element"))));
    		//Get data for contract   		
    		
    		Reporter.log("Start Create Contract for client First Name :  "+namefirst+" Last Name :  "+namelast+"  Email Id :  "+email);
    		Reporter.log("Search client by Email Id :  "+email);    		
    		WebElement clientSearchBox = coreFunc.findElement("name",LoadProperty.getVar("clientSearchBox", "element"));
			clientSearchBox.clear();
			clientSearchBox.sendKeys(email);
			clientWait.until(ExpectedConditions.invisibilityOfElementLocated(By.className(LoadProperty.getVar("clientGridWait", "element"))));
			
			coreFunc.waitForWhile(3);
			//Verify client found or not with specified name in grid 
			WebElement recordcount =coreFunc.findElement("xpath",LoadProperty.getVar("recordCount", "element")); 
			System.out.println("Debug log : Client Count :  "+ recordcount.getText());
			if(recordcount.getText().equalsIgnoreCase(coreFunc.GetDELanguageText("No data to display"))){
				Reporter.log("Unable to add contract due to Client not found with details First Name :  "+namefirst+" Last Name :  "+namelast+"  Email Id :  "+email);
				TestResult.addTestResult("Unable to add contract due to Client not found with details First Name :  "+namefirst+" Last Name :  "+namelast+"  Email Id :  "+email,"Failed");
				coreFunc.logData("CreateClientAndContract",testID,"Failed","Error: Unable to add contract due to Client not found with Email ID : "+email+"  [ TCID: "+testID+"]",assertEnabled);					
	    		return false;
			}
			else{
				Reporter.log("Client found with details First Name :  "+namefirst+" Last Name :  "+namelast+"  Email Id :  "+email);
			}
			coreFunc.waitForWhile(3);
			WebElement contractcountB =coreFunc.findElement("xpath",LoadProperty.getVar("contractCount", "element"));  
			Reporter.log("Verify existing contract count");
			System.out.println("Debug log : Contract count before adding :  "+ contractcountB.getText());
			int contractCountbefore = Integer.parseInt(contractcountB.getText());
			
			coreFunc.waitForWhile(3);
			//select first record
			WebElement firstRecord = coreFunc.findElement("xpath",LoadProperty.getVar("firstRecord", "element"));
			firstRecord.click();	
			
			coreFunc.waitForWhile(3);
			
    			 testStep="Click Contract Tab.";
				 String[] locator=ManageLocator.getLocator("Tab.Clients.Contract");
				 WebElement eleContractTab=coreFunc.findElement(locator[0],locator[1]);
				 eleContractTab.click();
				 TestResult.addTestResult(testStep,"Passed");
				 
				 coreFunc.waitForWhile(5);
				 testStep="Click Create Contract button.";
				 //locator=ManageLocator.getLocator("Tab.Client.Contract.Button.Create");
				 coreFunc.ClickOnActionButton("Create");
				 TestResult.addTestResult(testStep,"Passed");
				 
				 coreFunc.waitForWhile(5);
				 
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
				 
				 coreFunc.waitForWhile(2);
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
								 
				 coreFunc.waitForWhile(4);
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
				 WebElement paymentTB = coreFunc.findElement("name",LoadProperty.getVar("payment", "element"));
				 paymentTB.clear();
				 paymentTB.sendKeys(payment);
				 TestResult.addTestResult(testStep,"Passed");
				 
				 testStep="Select Duration Time as "+duration_months;
				 WebElement durationMonthsTB = coreFunc.findElement("name",LoadProperty.getVar("durationMonths", "element"));
				 durationMonthsTB.clear();
				 durationMonthsTB.sendKeys(duration_months);
				 TestResult.addTestResult(testStep,"Passed");
    	
			//Click on save contract button
			coreFunc.ClickOnActionButton("Save");
			
			AddContractFeatures(testData);	
			
			testStep="Click Save button.";
			//locator=ManageLocator.getLocator("Tab.Client.Contract.Create.Button.Save");
			coreFunc.ClickOnActionButton("Save");
			TestResult.addTestResult(testStep,"Passed");
			
			clientWait.until(ExpectedConditions.invisibilityOfElementLocated(By.className(LoadProperty.getVar("clientGridWait", "element"))));
			//click on refresh button 
			WebElement refreshBtn = coreFunc.findElement("xpath",LoadProperty.getVar("refreshBtn", "element"));
			refreshBtn.click();
			clientWait.until(ExpectedConditions.invisibilityOfElementLocated(By.className(LoadProperty.getVar("clientGridWait", "element"))));
			coreFunc.waitForWhile(5);
			
			WebElement contractcountA =coreFunc.findElement("xpath",LoadProperty.getVar("contractCount", "element")); 
			Reporter.log("Verify Contract count or not with specified last name in clients grid");
			System.out.println("Debug log : Contract count after adding :  "+ contractcountA.getText());
			
			int contractCountAfter = Integer.parseInt(contractcountA.getText());
			if(contractCountAfter > contractCountbefore)
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
			String insuranceType=String.valueOf(testData[93]);
			
			String testDataReference=String.valueOf(testData[94]); 
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
						//WebElement eleClientTab=coreFunc.findElement(locator[0],locator[1]);
						//eleClientTab.click(); 
						//TestResult.addTestResult(testStep,"Passed");
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