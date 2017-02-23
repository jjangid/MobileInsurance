package mob_insurance.scripts;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import mob_insurance.functions.CoreRepository;
import mob_insurance.functions.TestBase;
import mob_insurance.io.LoadProperty;
import mob_insurance.listeners.TestResult;

public class AddAgent extends TestBase{
	CoreRepository coreFunc=null;
	Boolean assertEnabled = false;
	
	@Test
	public void AddAgentTest() throws Exception{
		
		try
		{
			Reporter.log("Test execution has started...");
			int pageTimeOut = Integer.valueOf(LoadProperty.getVar("PageLoadImplicitTimeOutFactor", "config"));
			getDriver().manage().timeouts().pageLoadTimeout(pageTimeOut, TimeUnit.SECONDS);

			//create instance of class core
			coreFunc = new CoreRepository();
			//Assign webdriver driver to class core
			coreFunc.setDriver(this.getDriver());
			
			//Prepare test data
			Object[][] testData=null;
			testData=coreFunc.getTestData("AddAgent.xls");
			if (testData == null) {
				System.out.println("File Name is Incorrect");
				Reporter.log("File Name is Incorrect");
				coreFunc.logData("Add Agent", "", "Failed","Incorrect file name", assertEnabled);
				Assert.assertEquals(testData, "null");
			}
			Reporter.log("TestCaseCount : "+testData.length);
			for(int tcNo=0;tcNo<testData.length;tcNo++)
			{
				Reporter.log("*******************************************************************************************");
				TestResult.resetListTestResult();
				if((testData[tcNo][0])==null){					
					Reporter.log("Test case not found in test data sheet");
					Assert.assertEquals(false, true, "Test case not found in test data sheet");					
				}				
				Reporter.log("S.No: " +tcNo+ " Test execution started for testID: "+testData[tcNo][0]);
				boolean executionResult=true;
				String testname = (String) testData[tcNo][0];
				TestResult.addTestResult(testname,"");
								
				//Verify 'ExecuteTest' column value to identify whether test case need to execute or not
				if(String.valueOf(testData[tcNo][1]).equalsIgnoreCase("TRUE")){				
					String loginText = (String) testData[tcNo][2];
					String[] loginVariable = coreFunc.GetLoginRole(loginText);
					//Open URL and Login will only be called when Login needs to be done					
					if(Boolean.valueOf(String.valueOf(loginVariable[0].trim()))){						
						//Login to application with specified credentials
						if(!coreFunc.Login(loginVariable[1])){
							TestResult.appendTestResult();
							continue;
						}
					}
					
					executionResult= addAgentTestSuite(testData[tcNo]);
					
					if(executionResult)
					{
						coreFunc.logData("Add Agent Test Suite",String.valueOf(testData[tcNo][0]),"Passed","",assertEnabled);
						System.out.println("Test Case"+testData[tcNo][0]+" is passed.");
					}
					TestResult.appendTestResult();
				}
				else
				{					
					Reporter.log("Test execution of test case "+testData[tcNo][0]+" is skipped");
					System.out.println("Test execution of test case "+testData[tcNo][0]+" is skipped");					
				}
				
			    Reporter.log("Test execution completed for testID: "+testData[tcNo][0]);
			    Reporter.log("*******************************************************************************************");
			}
			
			
		}
		catch(Exception e){
			Reporter.log("Error: Test Case failed due to error occured");
			coreFunc.logData("Add Agent Test Suite","","Failed","Error: Refer output logs for more information.",assertEnabled);
			e.printStackTrace();
			TestResult.appendTestResult();
		}
	}
	
	private boolean addAgentTestSuite(Object testData[]){
		String testStep="";
		String locatorHolder="";
		//click on System Admin tab
		try{
				testStep="Click on 'System Admin' Tab";
				System.out.println(testStep);
				locatorHolder=LoadProperty.getVar("Tab.SystemAdmin", "element");
				interactWithElement("click","xpath", locatorHolder, "");
				TestResult.addTestResult(testStep,"Passed");
				Reporter.log("Clicked on 'System Admin' Tab  ==>  Step Pass");
		
				
				if(createAgencyTest(testData)){
					testStep="Search recently added Agency with respect to name";
					searchAgencyWithName(String.valueOf(testData[3]));
					locatorHolder=LoadProperty.getVar("FirstAgencyRecord", "element");
					locatorHolder=locatorHolder.replaceAll("@agencyname", String.valueOf(testData[3]));
					coreFunc.findElement("xpath", locatorHolder).click();
					TestResult.addTestResult(testStep,"Passed");
					Reporter.log(testStep +"  ==>  Step Pass");
					
					testStep="Click Agent Tab.";
					locatorHolder=LoadProperty.getVar("Tab.Agent", "element");
					coreFunc.findElement("xpath", locatorHolder).click();
					TestResult.addTestResult(testStep,"Passed");
					Reporter.log(testStep +"  ==>  Step Pass");
					
					testStep="Click Create Button.";
					locatorHolder=LoadProperty.getVar("Tab.Agent.Button.Create", "element");
					coreFunc.findElement("xpath", locatorHolder).click();
					TestResult.addTestResult(testStep,"Passed");
					Reporter.log(testStep +"  ==>  Step Pass");
					
					if(createAgentTestSuite(testData)){
					    testStep="Search recently added Agency with respect to name";
						searchAgencyWithName(String.valueOf(testData[3]));
						TestResult.addTestResult(testStep,"Passed");
						Reporter.log(testStep +"  ==>  Step Pass");
						
						testStep="Click Row Expender";
						locatorHolder=LoadProperty.getVar("SerachAgency.RowExpender", "element");
						coreFunc.findElement("xpath", locatorHolder).click();
						TestResult.addTestResult(testStep,"Passed");
						Reporter.log(testStep +"  ==>  Step Pass");
						
						//No need to click on Agent name. User will automatically redirected on Agent tab on click of Row Expander	
						
						testStep="Click Agent Authorization Tab.";
						locatorHolder=LoadProperty.getVar("Tab.AgentAuthorization", "element");
						coreFunc.findElement("xpath", locatorHolder).click();
						TestResult.addTestResult(testStep,"Passed");				
						Reporter.log(testStep +"  ==>  Step Pass");
						updateAgentAuthorisation(testData);
					}
				}
		}catch(Exception e){
			TestResult.addTestResult(testStep,"Failed");
			Reporter.log(testStep +"  ==>  Step Failed");
			return false;
		}
		return true;
	}
	
	private void searchAgencyWithName(String Name) {
				
		getDriver().findElement(By.name(LoadProperty.getVar("AgencySearchBox", "element"))).clear();
		getDriver().findElement(By.name(LoadProperty.getVar("AgencySearchBox", "element"))).sendKeys(Name);
		
		WebDriverWait clientWait = new WebDriverWait(getDriver(), 45);
		clientWait.until(ExpectedConditions.invisibilityOfElementLocated(By.className(LoadProperty.getVar("clientGridWait", "element"))));
	}
	
	private boolean createAgencyTest(Object testData[]){
		String testStep="";
		String testDataHolder="";
		String locatorHolder="";
		 coreFunc.waitForWhile(5);
		try{
	//		Click Create button on Agency tab
			testStep="Click Create button on Agency tab.";
			System.out.println(testStep);
			locatorHolder=LoadProperty.getVar("Tab.Agency.Button.Create", "element");
			interactWithElement("click","xpath", locatorHolder, "");
			TestResult.addTestResult(testStep,"Passed");
			Reporter.log(testStep +"  ==>  Step Pass");
			coreFunc.waitForWhile(5);
			
	//		Enter Agency Name name
			testDataHolder=String.valueOf(testData[3]);
			testStep="Enter Agency Name as "+testDataHolder+".";
			System.out.println(testStep);
			locatorHolder=LoadProperty.getVar("Tab.Agency.AgencyName", "element");
			interactWithElement("input","xpath",locatorHolder,testDataHolder);
			TestResult.addTestResult(testStep,"Passed");
			Reporter.log(testStep +"  ==>  Step Pass");
			
	//		Select Type
			testDataHolder=String.valueOf(testData[5]);
			testStep="Select Type  as "+testDataHolder+".";
			System.out.println(testStep);
			locatorHolder=LoadProperty.getVar("Tab.Agency.TypePicker", "element");
			interactWithElement("dropdown","xpath",locatorHolder,testDataHolder);
			TestResult.addTestResult(testStep,"Passed");
			Reporter.log(testStep +"  ==>  Step Pass");
			
	//		Enter Agency Name 2
			testDataHolder=String.valueOf(testData[6]);
			testStep="Enter Agency Name as "+testDataHolder+".";
			System.out.println(testStep);
			locatorHolder=LoadProperty.getVar("Tab.Agency.AgencyName2", "element");
			interactWithElement("input","xpath",locatorHolder,testDataHolder);
			TestResult.addTestResult(testStep,"Passed");
			Reporter.log(testStep +"  ==>  Step Pass");
			
	//		Select Status
			testDataHolder=String.valueOf(testData[7]);
			testStep="Select Status  as "+testDataHolder+".";
			System.out.println(testStep);
			locatorHolder=LoadProperty.getVar("Tab.Agency.StatusPicker", "element");
			interactWithElement("dropdown","xpath",locatorHolder,testDataHolder);
			TestResult.addTestResult(testStep,"Passed");
			Reporter.log(testStep +"  ==>  Step Pass");
			
	//		Enter Email
	//		testDataHolder=String.valueOf(testData[8]);
	//		testStep="Enter Email as "+testDataHolder+".";
	//		System.out.println(testStep);
	//		locatorHolder=LoadProperty.getVar("Tab.Agency.Email", "element");
	//		interactWithElement("input","xpath",locatorHolder,testDataHolder);
	//		TestResult.addTestResult(testStep,"Passed");
			
	//		Enter Street
			testDataHolder=String.valueOf(testData[9]);
			testStep="Enter Street as "+testDataHolder+".";
			System.out.println(testStep);
			locatorHolder=LoadProperty.getVar("Tab.Agency.Street", "element");
			interactWithElement("input","xpath",locatorHolder,testDataHolder);
			TestResult.addTestResult(testStep,"Passed");
			Reporter.log(testStep +"  ==>  Step Pass");
			
	//		Enter Street Number
			testDataHolder=String.valueOf(testData[10]);
			testStep="Enter Street Number as "+testDataHolder+".";
			System.out.println(testStep);
			locatorHolder=LoadProperty.getVar("Tab.Agency.StreetNumber", "element");
			interactWithElement("input","xpath",locatorHolder,testDataHolder);
			TestResult.addTestResult(testStep,"Passed");
			Reporter.log(testStep +"  ==>  Step Pass");
			
	//		Enter Telephone
			testDataHolder=String.valueOf(testData[11]);
			testStep="Enter Telephone as "+testDataHolder+".";
			System.out.println(testStep);
			locatorHolder=LoadProperty.getVar("Tab.Agency.phone", "element");
			interactWithElement("input","xpath",locatorHolder,testDataHolder);
			TestResult.addTestResult(testStep,"Passed");
			Reporter.log(testStep +"  ==>  Step Pass");
	
	//		Enter Zip
			testDataHolder=String.valueOf(testData[12]);
			testStep="Enter Zip as "+testDataHolder+".";
			System.out.println(testStep);
			locatorHolder=LoadProperty.getVar("Tab.Agency.zip", "element");
			interactWithElement("input","xpath",locatorHolder,testDataHolder);
			TestResult.addTestResult(testStep,"Passed");
			Reporter.log(testStep +"  ==>  Step Pass");
	
	//		Select City
			testDataHolder=String.valueOf(testData[13]);
			testStep="Select City as "+testDataHolder+".";
			System.out.println(testStep);
			locatorHolder=LoadProperty.getVar("Tab.Agency.CityPicker", "element");
			interactWithElement("dropdown","xpath",locatorHolder,testDataHolder);
			TestResult.addTestResult(testStep,"Passed");
			Reporter.log(testStep +"  ==>  Step Pass");
			
	//		Enter FAX
			testDataHolder=String.valueOf(testData[14]);
			testStep="Enter FAX as "+testDataHolder+".";
			System.out.println(testStep);
			locatorHolder=LoadProperty.getVar("Tab.Agency.Fax", "element");
			interactWithElement("input","xpath",locatorHolder,testDataHolder);
			TestResult.addTestResult(testStep,"Passed");
			Reporter.log(testStep +"  ==>  Step Pass");
			
	//		Select Country
			testDataHolder=String.valueOf(testData[15]);
			testStep="Select Country as "+testDataHolder+".";
			System.out.println(testStep);
			locatorHolder=LoadProperty.getVar("Tab.Agency.CountryPicker", "element");
			interactWithElement("dropdown","xpath",locatorHolder,testDataHolder);
			TestResult.addTestResult(testStep,"Passed");
			Reporter.log(testStep +"  ==>  Step Pass");
	//		Select Currency
			testDataHolder=String.valueOf(testData[16]);
			testStep="Select Currency as "+testDataHolder+".";
			System.out.println(testStep);
			locatorHolder=LoadProperty.getVar("Tab.Agency.CurrencyPicker", "element");
			interactWithElement("dropdown","xpath",locatorHolder,testDataHolder);
			TestResult.addTestResult(testStep,"Passed");
			Reporter.log(testStep +"  ==>  Step Pass");
   //		Click Save button
			testStep="Click Save button";
			System.out.println(testStep);
			locatorHolder=LoadProperty.getVar("Tab.Agency.SaveButton", "element");
			interactWithElement("click","xpath",locatorHolder,"");
			TestResult.addTestResult(testStep,"Passed");
			Reporter.log(testStep +"  ==>  Step Pass");
			
		}catch(Exception e){
			TestResult.addTestResult(testStep,"Failed","Error has been occured while executing this test step. Please have look into log for more info.");
			Reporter.log(testStep +"  ==>  Step Failed");
			return false;
		}
		
		return true;
		
	}	
	
    private boolean createAgentTestSuite(Object testData[]){
    	String testStep="";
		String testDataHolder="";
		String locatorHolder="";
		try{
//			Select Salutation
			testDataHolder=String.valueOf(testData[17]);
			testStep="Select Salutation as "+testDataHolder+".";
			System.out.println(testStep);
			locatorHolder=LoadProperty.getVar("Tab.Agent.Salutation", "element");
			interactWithElement("dropdown","xpath",locatorHolder,testDataHolder);
			TestResult.addTestResult(testStep,"Passed");
			Reporter.log(testStep +"  ==>  Step Pass");
			
//			Select Title
			testDataHolder=String.valueOf(testData[18]);
			testStep="Select Title as "+testDataHolder+".";
			System.out.println(testStep);
			locatorHolder=LoadProperty.getVar("Tab.Agent.Title", "element");
			interactWithElement("dropdown","xpath",locatorHolder,testDataHolder);
			TestResult.addTestResult(testStep,"Passed");
			Reporter.log(testStep +"  ==>  Step Pass");
			
//			Enter IHK
			testDataHolder=String.valueOf(testData[19]);
			testStep="Enter IHK as "+testDataHolder+".";
			System.out.println(testStep);
			locatorHolder=LoadProperty.getVar("Tab.Agent.IHK", "element");
			interactWithElement("input","xpath",locatorHolder,testDataHolder);
			TestResult.addTestResult(testStep,"Passed");
			Reporter.log(testStep +"  ==>  Step Pass");	
			
//			Enter First Name
			testDataHolder=String.valueOf(testData[20]);
			testStep="Enter First name as "+testDataHolder+".";
			System.out.println(testStep);
			locatorHolder=LoadProperty.getVar("Tab.Agent.FirstName", "element");
			interactWithElement("input","xpath",locatorHolder,testDataHolder);
			TestResult.addTestResult(testStep,"Passed");
			Reporter.log(testStep +"  ==>  Step Pass");
			
//			Enter Last Name
			testDataHolder=String.valueOf(testData[21]);
			testStep="Enter Last name as "+testDataHolder+".";
			System.out.println(testStep);
			locatorHolder=LoadProperty.getVar("Tab.Agent.LastName", "element");
			interactWithElement("input","xpath",locatorHolder,testDataHolder);
			TestResult.addTestResult(testStep,"Passed");
			Reporter.log(testStep +"  ==>  Step Pass");
			
//			Enter DOB
			testDataHolder=String.valueOf(testData[22]);
			testStep="Enter DOB as "+testDataHolder+".";
			System.out.println(testStep);
			locatorHolder=LoadProperty.getVar("Tab.Agent.DOB", "element");
			interactWithElement("input","xpath",locatorHolder,testDataHolder);
			TestResult.addTestResult(testStep,"Passed");
			Reporter.log(testStep +"  ==>  Step Pass");
			
//			Enter Password
			testDataHolder=String.valueOf(testData[23]);
			testStep="Enter Password as "+testDataHolder+".";
			System.out.println(testStep);
			locatorHolder=LoadProperty.getVar("Tab.Agent.PWD", "element");
			interactWithElement("input","xpath",locatorHolder,testDataHolder);
			TestResult.addTestResult(testStep,"Passed");
			Reporter.log(testStep +"  ==>  Step Pass");
			
//			Enter Confirm Password
			testDataHolder=String.valueOf(testData[24]);
			testStep="Enter Confirm Password as "+testDataHolder+".";
			System.out.println(testStep);
			locatorHolder=LoadProperty.getVar("Tab.Agent.ConfirmPWD", "element");
			interactWithElement("input","xpath",locatorHolder,testDataHolder);
			TestResult.addTestResult(testStep,"Passed");
			Reporter.log(testStep +"  ==>  Step Pass");
			
//			Select Family Status
			testDataHolder=String.valueOf(testData[25]);
			testStep="Select Family Status as "+testDataHolder+".";
			System.out.println(testStep);
			locatorHolder=LoadProperty.getVar("Tab.Agent.FamilyStatus", "element");
			interactWithElement("dropdown","xpath",locatorHolder,testDataHolder);
			TestResult.addTestResult(testStep,"Passed");
			Reporter.log(testStep +"  ==>  Step Pass");
			
//			Select Birth Name
			testDataHolder=String.valueOf(testData[26]);
			testStep="Enter Birth Name as "+testDataHolder+".";
			System.out.println(testStep);
			locatorHolder=LoadProperty.getVar("Tab.Agent.BirthName", "element");
			interactWithElement("input","xpath",locatorHolder,testDataHolder);
			TestResult.addTestResult(testStep,"Passed");
			Reporter.log(testStep +"  ==>  Step Pass");
			
//			Select Birth Place
			testDataHolder=String.valueOf(testData[29]);
			testStep="Enter Birth Place as "+testDataHolder+".";
			System.out.println(testStep);
			locatorHolder=LoadProperty.getVar("Tab.Agent.BirthPlace", "element");
			interactWithElement("input","xpath",locatorHolder,testDataHolder);
			TestResult.addTestResult(testStep,"Passed");
			Reporter.log(testStep +"  ==>  Step Pass");
			
//			Select Nationality
			testDataHolder=String.valueOf(testData[27]);
			testStep="Select Nationality as "+testDataHolder+".";
			System.out.println(testStep);
			locatorHolder=LoadProperty.getVar("Tab.Agent.Nationality", "element");
			interactWithElement("dropdown","xpath",locatorHolder,testDataHolder);
			TestResult.addTestResult(testStep,"Passed");
			Reporter.log(testStep +"  ==>  Step Pass");
			
//			Select Country
			testDataHolder=String.valueOf(testData[28]);
			testStep="Select Country as "+testDataHolder+".";
			System.out.println(testStep);
			locatorHolder=LoadProperty.getVar("Tab.Agent.Country", "element");
			interactWithElement("dropdown","xpath",locatorHolder,testDataHolder);
			TestResult.addTestResult(testStep,"Passed");
			Reporter.log(testStep +"  ==>  Step Pass");
			
//			Enter Street
			testDataHolder=String.valueOf(testData[30]);
			testStep="Enter Street as "+testDataHolder+".";
			System.out.println(testStep);
			locatorHolder=LoadProperty.getVar("Tab.Agent.Street", "element");
			interactWithElement("input","xpath",locatorHolder,testDataHolder);
			TestResult.addTestResult(testStep,"Passed");
			Reporter.log(testStep +"  ==>  Step Pass");
			
//			Enter Street Number
			testDataHolder=String.valueOf(testData[31]);
			testStep="Enter Street Number as "+testDataHolder+".";
			System.out.println(testStep);
			locatorHolder=LoadProperty.getVar("Tab.Agent.StreetNumber", "element");
			interactWithElement("input","xpath",locatorHolder,testDataHolder);
			TestResult.addTestResult(testStep,"Passed");
			Reporter.log(testStep +"  ==>  Step Pass");
			
//			Enter ZIP
			testDataHolder=String.valueOf(testData[32]);
			testStep="Enter PLZ as "+testDataHolder+".";
			System.out.println(testStep);
			locatorHolder=LoadProperty.getVar("Tab.Agent.PLZ", "element");
			interactWithElement("input","xpath",locatorHolder,testDataHolder);
			TestResult.addTestResult(testStep,"Passed");
			Reporter.log(testStep +"  ==>  Step Pass");
			
//			Select City
			testDataHolder=String.valueOf(testData[33]);
			testStep="Select City as "+testDataHolder+".";
			System.out.println(testStep);
			locatorHolder=LoadProperty.getVar("Tab.Agent.City", "element");
			interactWithElement("dropdown","xpath",locatorHolder,testDataHolder);
			TestResult.addTestResult(testStep,"Passed");
			Reporter.log(testStep +"  ==>  Step Pass");
			
//			Enter Telephone
			testDataHolder=String.valueOf(testData[34]);
			testStep="Enter Telephone as "+testDataHolder+".";
			System.out.println(testStep);
			locatorHolder=LoadProperty.getVar("Tab.Agent.telephone", "element");
			interactWithElement("input","xpath",locatorHolder,testDataHolder);
			TestResult.addTestResult(testStep,"Passed");
			Reporter.log(testStep +"  ==>  Step Pass");
			
//			Enter Mobile
			testDataHolder=String.valueOf(testData[36]);
			testStep="Enter Mobile as "+testDataHolder+".";
			System.out.println(testStep);
			locatorHolder=LoadProperty.getVar("Tab.Agent.Mobile", "element");
			interactWithElement("input","xpath",locatorHolder,testDataHolder);
			TestResult.addTestResult(testStep,"Passed");			
			Reporter.log(testStep +"  ==>  Step Pass");
			
//			Enter Email
			testDataHolder=String.valueOf(testData[35]);
			testStep="Enter Email as "+testDataHolder+".";
			System.out.println(testStep);
			locatorHolder=LoadProperty.getVar("Tab.Agent.Email", "element");
			interactWithElement("input","xpath",locatorHolder,testDataHolder);
			TestResult.addTestResult(testStep,"Passed");			
			Reporter.log(testStep +"  ==>  Step Pass");
			
//			Enter External URL
			testDataHolder=String.valueOf(testData[37]);
			testStep="Enter External URL as "+testDataHolder+".";
			System.out.println(testStep);
			locatorHolder=LoadProperty.getVar("Tab.Agent.EURL", "element");
			interactWithElement("input","xpath",locatorHolder,testDataHolder);
			TestResult.addTestResult(testStep,"Passed");			
			Reporter.log(testStep +"  ==>  Step Pass");
			
//			Enter External Title
			testDataHolder=String.valueOf(testData[38]);
			testStep="Enter External Title as "+testDataHolder+".";
			System.out.println(testStep);
			locatorHolder=LoadProperty.getVar("Tab.Agent.ETitle", "element");
			interactWithElement("input","xpath",locatorHolder,testDataHolder);
			TestResult.addTestResult(testStep,"Passed");	
			Reporter.log(testStep +"  ==>  Step Pass");
			
//			Enter External User
			testDataHolder=String.valueOf(testData[39]);
			testStep="Enter External User as "+testDataHolder+".";
			System.out.println(testStep);
			locatorHolder=LoadProperty.getVar("Tab.Agent.EUser", "element");
			interactWithElement("input","xpath",locatorHolder,testDataHolder);
			TestResult.addTestResult(testStep,"Passed");			
			Reporter.log(testStep +"  ==>  Step Pass");
			
//			Select Role
			testDataHolder=String.valueOf(testData[40]);
			testStep="Select Role as "+testDataHolder+".";
			System.out.println(testStep);
			locatorHolder=LoadProperty.getVar("Tab.Agent.Role", "element");
			interactWithElement("dropdown","xpath",locatorHolder,testDataHolder);
			TestResult.addTestResult(testStep,"Passed");
			Reporter.log(testStep +"  ==>  Step Pass");
			
//			Select Status
			testDataHolder=String.valueOf(testData[41]);
			testStep="Select Status as "+testDataHolder+".";
			System.out.println(testStep);
			locatorHolder=LoadProperty.getVar("Tab.Agent.Status", "element");
			interactWithElement("dropdown","xpath",locatorHolder,testDataHolder);
			TestResult.addTestResult(testStep,"Passed");
			Reporter.log(testStep +"  ==>  Step Pass");
			
//			Click Save button
			testStep="Click Save button.";
			System.out.println(testStep);
			locatorHolder=LoadProperty.getVar("Tab.Agent.SaveButton", "element");
			interactWithElement("click","xpath",locatorHolder,"");
			TestResult.addTestResult(testStep,"Passed");
			
		}catch(Exception e){
			TestResult.addTestResult(testStep,"Failed","Error appeared while executing test step.");
			Reporter.log(testStep +"  ==>  Step Failed");
			return false;
		}
		return true;
    }
    
    private boolean updateAgentAuthorisation(Object testData[]){
    	String testStep="";
		String testDataHolder="";
		String locatorHolder="";
		try{
//			Price Calculater
			testDataHolder=String.valueOf(testData[43]);
			testStep="Set Price Calculater as "+testDataHolder+".";
			System.out.println(testStep);
			locatorHolder=LoadProperty.getVar("Tab.AgentAuthorisation.PriceCalculater.ON", "element");
			interactWithElement("toggle","xpath",locatorHolder,testDataHolder);
			TestResult.addTestResult(testStep,"Passed");
			Reporter.log(testStep +"  ==>  Step Pass");
			
//			PHV
			testDataHolder=String.valueOf(testData[44]);
			testStep="Set PHV as "+testDataHolder+".";
			System.out.println(testStep);
			locatorHolder=LoadProperty.getVar("Tab.AgentAuthorisation.PHV.ON", "element");
			interactWithElement("toggle","xpath",locatorHolder,testDataHolder);
			TestResult.addTestResult(testStep,"Passed");
			Reporter.log(testStep +"  ==>  Step Pass");
			
//			HAU
			testDataHolder=String.valueOf(testData[45]);
			testStep="Set HAU as "+testDataHolder+".";
			System.out.println(testStep);
			locatorHolder=LoadProperty.getVar("Tab.AgentAuthorisation.HAU.ON", "element");
			interactWithElement("toggle","xpath",locatorHolder,testDataHolder);
			TestResult.addTestResult(testStep,"Passed");
			Reporter.log(testStep +"  ==>  Step Pass");
			
//			UNF
			testDataHolder=String.valueOf(testData[46]);
			testStep="Set UNF as "+testDataHolder+".";
			System.out.println(testStep);
			locatorHolder=LoadProperty.getVar("Tab.AgentAuthorisation.UNF.ON", "element");
			interactWithElement("toggle","xpath",locatorHolder,testDataHolder);
			TestResult.addTestResult(testStep,"Passed");
			Reporter.log(testStep +"  ==>  Step Pass");
			
//			BAU
			testDataHolder=String.valueOf(testData[47]);
			testStep="Set BAU as "+testDataHolder+".";
			System.out.println(testStep);
			locatorHolder=LoadProperty.getVar("Tab.AgentAuthorisation.BAU.ON", "element");
			interactWithElement("toggle","xpath",locatorHolder,testDataHolder);
			TestResult.addTestResult(testStep,"Passed");
			Reporter.log(testStep +"  ==>  Step Pass");
			
//			RES
			testDataHolder=String.valueOf(testData[48]);
			testStep="Set RES as "+testDataHolder+".";
			System.out.println(testStep);
			locatorHolder=LoadProperty.getVar("Tab.AgentAuthorisation.RES.ON", "element");
			interactWithElement("toggle","xpath",locatorHolder,testDataHolder);
			TestResult.addTestResult(testStep,"Passed");
			Reporter.log(testStep +"  ==>  Step Pass");
			
//			GEW
			testDataHolder=String.valueOf(testData[49]);
			testStep="Set GEW as "+testDataHolder+".";
			System.out.println(testStep);
			locatorHolder=LoadProperty.getVar("Tab.AgentAuthorisation.GEW.ON", "element");
			interactWithElement("toggle","xpath",locatorHolder,testDataHolder);
			TestResult.addTestResult(testStep,"Passed");
			Reporter.log(testStep +"  ==>  Step Pass");
			
//			HUN
			testDataHolder=String.valueOf(testData[50]);
			testStep="Set HUN as "+testDataHolder+".";
			System.out.println(testStep);
			locatorHolder=LoadProperty.getVar("Tab.AgentAuthorisation.HUN.ON", "element");
			interactWithElement("toggle","xpath",locatorHolder,testDataHolder);
			TestResult.addTestResult(testStep,"Passed");
			Reporter.log(testStep +"  ==>  Step Pass");
			
//			HUG
			testDataHolder=String.valueOf(testData[51]);
			testStep="Set HUG as "+testDataHolder+".";
			System.out.println(testStep);
			locatorHolder=LoadProperty.getVar("Tab.AgentAuthorisation.HUG.ON", "element");
			interactWithElement("toggle","xpath",locatorHolder,testDataHolder);
			TestResult.addTestResult(testStep,"Passed");
			Reporter.log(testStep +"  ==>  Step Pass");
			
//			PFE
			testDataHolder=String.valueOf(testData[52]);
			testStep="Set PFE as "+testDataHolder+".";
			System.out.println(testStep);
			locatorHolder=LoadProperty.getVar("Tab.AgentAuthorisation.PFE.ON", "element");
			interactWithElement("toggle","xpath",locatorHolder,testDataHolder);
			TestResult.addTestResult(testStep,"Passed");
			Reporter.log(testStep +"  ==>  Step Pass");
			
//			WHO
			testDataHolder=String.valueOf(testData[53]);
			testStep="Set WHO as "+testDataHolder+".";
			System.out.println(testStep);
			locatorHolder=LoadProperty.getVar("Tab.AgentAuthorisation.WHO.ON", "element");
			interactWithElement("toggle","xpath",locatorHolder,testDataHolder);
			TestResult.addTestResult(testStep,"Passed");
			Reporter.log(testStep +"  ==>  Step Pass");
			
//			PKW
			testDataHolder=String.valueOf(testData[54]);
			testStep="Set PKW as "+testDataHolder+".";
			System.out.println(testStep);
			locatorHolder=LoadProperty.getVar("Tab.AgentAuthorisation.PKW.ON", "element");
			interactWithElement("toggle","xpath",locatorHolder,testDataHolder);
			TestResult.addTestResult(testStep,"Passed");
			Reporter.log(testStep +"  ==>  Step Pass");
			
//			KRAD
			testDataHolder=String.valueOf(testData[55]);
			testStep="Set KRAD as "+testDataHolder+".";
			System.out.println(testStep);
			locatorHolder=LoadProperty.getVar("Tab.AgentAuthorisation.KRAD.ON", "element");
			interactWithElement("toggle","xpath",locatorHolder,testDataHolder);
			TestResult.addTestResult(testStep,"Passed");
			Reporter.log(testStep +"  ==>  Step Pass");
			
//			LKWL
			testDataHolder=String.valueOf(testData[56]);
			testStep="Set LKWL as "+testDataHolder+".";
			System.out.println(testStep);
			locatorHolder=LoadProperty.getVar("Tab.AgentAuthorisation.LKWL.ON", "element");
			interactWithElement("toggle","xpath",locatorHolder,testDataHolder);
			TestResult.addTestResult(testStep,"Passed");
			Reporter.log(testStep +"  ==>  Step Pass");
			
//			Invitation
			testDataHolder=String.valueOf(testData[57]);
			testStep="Set Invitation as "+testDataHolder+".";
			System.out.println(testStep);
			locatorHolder=LoadProperty.getVar("Tab.AgentAuthorisation.Invitation.ON", "element");
			interactWithElement("toggle","xpath",locatorHolder,testDataHolder);
			TestResult.addTestResult(testStep,"Passed");
			Reporter.log(testStep +"  ==>  Step Pass");
			
//			Chat
			testDataHolder=String.valueOf(testData[58]);
			testStep="Set Chat as "+testDataHolder+".";
			System.out.println(testStep);
			locatorHolder=LoadProperty.getVar("Tab.AgentAuthorisation.Chat.ON", "element");
			interactWithElement("toggle","xpath",locatorHolder,testDataHolder);
			TestResult.addTestResult(testStep,"Passed");
			Reporter.log(testStep +"  ==>  Step Pass");
			
//			Customizing
			testDataHolder=String.valueOf(testData[59]);
			testStep="Set Customizing as "+testDataHolder+".";
			System.out.println(testStep);
			locatorHolder=LoadProperty.getVar("Tab.AgentAuthorisation.Customizing.ON", "element");
			interactWithElement("toggle","xpath",locatorHolder,testDataHolder);
			TestResult.addTestResult(testStep,"Passed");
			Reporter.log(testStep +"  ==>  Step Pass");
			
//			Display Error
			testDataHolder=String.valueOf(testData[60]);
			testStep="Set Display Error as "+testDataHolder+".";
			System.out.println(testStep);
			locatorHolder=LoadProperty.getVar("Tab.AgentAuthorisation.DisplayError.ON", "element");
			interactWithElement("toggle","xpath",locatorHolder,testDataHolder);
			TestResult.addTestResult(testStep,"Passed");
			Reporter.log(testStep +"  ==>  Step Pass");
			
//			Import Data
			testDataHolder=String.valueOf(testData[61]);
			testStep="Set Import Data as "+testDataHolder+".";
			System.out.println(testStep);
			locatorHolder=LoadProperty.getVar("Tab.AgentAuthorisation.ImportData.ON", "element");
			interactWithElement("toggle","xpath",locatorHolder,testDataHolder);
			TestResult.addTestResult(testStep,"Passed");
			Reporter.log(testStep +"  ==>  Step Pass");
			
//			Translation
			testDataHolder=String.valueOf(testData[62]);
			testStep="Set Translation as "+testDataHolder+".";
			System.out.println(testStep);
			locatorHolder=LoadProperty.getVar("Tab.AgentAuthorisation.Translation.ON", "element");
			interactWithElement("toggle","xpath",locatorHolder,testDataHolder);
			TestResult.addTestResult(testStep,"Passed");
			Reporter.log(testStep +"  ==>  Step Pass");
			
//			Optimise
			testDataHolder=String.valueOf(testData[63]);
			testStep="Set Optimise as "+testDataHolder+".";
			System.out.println(testStep);
			locatorHolder=LoadProperty.getVar("Tab.AgentAuthorisation.Optimise.ON", "element");
			interactWithElement("toggle","xpath",locatorHolder,testDataHolder);
			TestResult.addTestResult(testStep,"Passed");
			Reporter.log(testStep +"  ==>  Step Pass");
			
//			Feeling
			testDataHolder=String.valueOf(testData[64]);
			testStep="Set Feeling as "+testDataHolder+".";
			System.out.println(testStep);
			locatorHolder=LoadProperty.getVar("Tab.AgentAuthorisation.Feeling.ON", "element");
			interactWithElement("toggle","xpath",locatorHolder,testDataHolder);
			TestResult.addTestResult(testStep,"Passed");
			Reporter.log(testStep +"  ==>  Step Pass");
			
//			Sessions
			testDataHolder=String.valueOf(testData[65]);
			testStep="Set Sessions as "+testDataHolder+".";
			System.out.println(testStep);
			locatorHolder=LoadProperty.getVar("Tab.AgentAuthorisation.Sessions.ON", "element");
			interactWithElement("toggle","xpath",locatorHolder,testDataHolder);
			TestResult.addTestResult(testStep,"Passed");
			Reporter.log(testStep +"  ==>  Step Pass");
			
//			Click Save button
			testStep="Click Save button.";
			System.out.println(testStep);
			locatorHolder=LoadProperty.getVar("Tab.AgentAuthorisation.SaveButton", "element");
			interactWithElement("click","xpath",locatorHolder,"");
			TestResult.addTestResult(testStep,"Passed");
			Reporter.log(testStep +"  ==>  Step Pass");
			
		}catch(Exception e){
			TestResult.addTestResult(testStep,"Failed","Error appeared while executing test step.");
			Reporter.log(testStep +"  ==>  Step Failed");
			return false;
		}
		return true;
    }
	
	
	private void interactWithElement(String elementType,String locatorType,String locatorValue,String testData) throws Exception{
		WebElement pageElement=null;
		
		switch(elementType.toLowerCase()){
		  case "input":
			           pageElement=coreFunc.findElement(locatorType, locatorValue);
				       pageElement.clear();
				       pageElement.sendKeys(testData);
				       break;
//		  case "date":
//			           pageElement=coreFunc.findElement(locatorType, locatorValue);
//				       pageElement.clear();
//				       pageElement.sendKeys(testData);
//				       break;
		  case "dropdown":
			           pageElement=coreFunc.findElement(locatorType, locatorValue);
			           pageElement.click();
			           String locatorHolder=LoadProperty.getVar("Tab.Agency.Option", "element");
			   		   locatorHolder=locatorHolder.replaceAll("@option", testData);
			   		   pageElement=coreFunc.findElement("xpath", locatorHolder);
			   		   pageElement.click();
			   		   break;
		  case "click":
					   pageElement=coreFunc.findElement(locatorType, locatorValue);
				       pageElement.click();
				       break;
		  case "toggle":
			            if(!"true".equalsIgnoreCase(testData) && !"false".equalsIgnoreCase(testData)){
			            	return;
			            }
			           pageElement=coreFunc.findElement(locatorType, locatorValue); 
			           boolean selected=true;
			           String style=pageElement.getAttribute("style");
			           String marginValue=style.substring(style.indexOf("margin-left:")+12, style.lastIndexOf(";")).trim();
			           if(marginValue.equalsIgnoreCase("0px")){
			        	   selected=false; 
			           }
			           if(selected && Boolean.valueOf(testData)==false){
			        	 locatorValue=locatorValue.substring(0,locatorValue.length()-1)+"FF";  
			        	 pageElement=coreFunc.findElement(locatorType, locatorValue); 
			        	 pageElement.click();
			           }else if(selected==false && Boolean.valueOf(testData)){
			        	 pageElement.click();  
			           }
			           break;
		}
		
	}	
		
		
		
		
		
		
		
	
}