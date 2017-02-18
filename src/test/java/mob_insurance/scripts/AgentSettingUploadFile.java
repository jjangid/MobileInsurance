package mob_insurance.scripts;

import java.util.Iterator;
import java.util.Map;

import mob_insurance.functions.TestBase;
import mob_insurance.io.ManageConfig;
import mob_insurance.io.ManageLocator;
import mob_insurance.io.TestData;
import mob_insurance.processor.Browser;
import mob_insurance.processor.TestResult;
import mob_insurance.common_lib.LoadProperty;
import mob_insurance.functions.CoreRepository;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Reporter;
import org.testng.annotations.Test;


public class AgentSettingUploadFile extends TestBase{
	CoreRepository webBrowser=null;
	Boolean assertEnabled = false;
	String locatorType=null;
	String locator=null;
	private Map<String,String> mapTestData=null;
	private String languageCode="";
		 	
	@Test
	public void AgentSettingUploadFileTest() throws Exception{
	try
		{
			Reporter.log("Test execution has started...");
			ManageConfig.loadConfigProperties();
			TestData objTestData=new TestData();
			Map<Integer,Map<String,String>> mapTestData=null;
			Iterator<Integer> itrTestRow=null;
			System.out.println("Info: Started running Test Suite for AgentSettingUploadFile");
			System.out.println("Info: Reading Test Data from "+ManageConfig.getTestDataFileName("TestData.AgentUploadFileTest")+" file.");
			mapTestData=objTestData.readTestData(ManageConfig.getTestDataFileName("TestData.AgentUploadFileTest"));
			itrTestRow=mapTestData.keySet().iterator();
			if(!mapTestData.isEmpty()){				
				while(itrTestRow.hasNext()){
					TestResult.resetListTestResult();
					int rowNo=itrTestRow.next();
					Map<String,String> mapTestdataRow=mapTestData.get(rowNo);
					AgentSettingTestSuite(mapTestdataRow);
					runTestSuite();									
					TestResult.appendTestResult();			
				}
			}
			else
			{
				System.out.println("Info: There is no data available for running create contract test. Hence aborted the test.");
			}			
		}
		catch(Exception e){
			Reporter.log("Error: Test Case failed due to error occured");
			webBrowser.logData("AgentSettingUploadFileTest","","Failed","Error: Refer output logs for more information.",assertEnabled);
			e.printStackTrace();
		}		
	}
	
	public void AgentSettingTestSuite(Map<String,String> mapTestdataRow) throws Exception{
		
		 mapTestData=mapTestdataRow;
	    	  
	    if(mapTestData.isEmpty()){
	      System.out.println("Info: Test Data is empty hence aborted the test execution.");
	      return; 
	    }
	    
	    String languageCode = LoadProperty.getVar("loginLanguage", "data");
	 	    
	    if(!languageCode.equalsIgnoreCase("EN") && !languageCode.equalsIgnoreCase("DE")){
	    	languageCode="EN";	
	    }
	    
	    if(ManageLocator.loadConfigProperties(languageCode)){
	      
	       TestResult.addTestResult(mapTestData.get("Test Name"),"");
	       webBrowser = new CoreRepository();
	       webBrowser.setDriver(this.getDriver());
	    }
	      
	 }
	 
	 public void runTestSuite(){
		 try{
		    			   
		    	System.out.println("Info: Loging into application with credential specified in Test Data file.");
			    logIn();
			    System.out.println("Info: Navigating to Import page.");
			    clickAgentSettingMenu();
			    clickDataImportTab();
			    
			    System.out.println("Info: Importing excel file.");    	
			    browseExcelFile();
			    clickImportButton();
			    System.out.println("Info: Verify Import Status."); 
			    verifyImportStatus();
			    webBrowser.logData("AgentSettingUploadFileTest","","Pass","Error: Refer output logs for more information.",assertEnabled);
		  }catch(Exception e){
			 System.out.println("Info: "+e.getMessage());	
			 webBrowser.logData("AgentSettingUploadFileTest","","Failed","Error: Refer output logs for more information.",assertEnabled);
		 }finally{
			 //code for logout instead of closing browser
			 //webBrowser.tearDown();
			 //System.out.println("Info: closing browser instance.");  
		 }	
	 }
	 
	 private void logIn() throws Exception{
		 
		    try{
		 
	         	String userName=LoadProperty.getVar("loginName", "data");//mapTestData.get("Username");
				String password=LoadProperty.getVar("loginPassword", "data");//mapTestData.get("Password");
				String URL=LoadProperty.getVar("baseUrl", "data");//mapTestData.get("URL");
				
				webBrowser.getURL(URL);
				
				// Login Page Locators
				// Index 0: Locator Type and Index 1: Locator value
		
				String[] locatorUserName=ManageLocator.getLocator("Uname");
				String[] locatorPWD=ManageLocator.getLocator("Pword");
				String[] locatorLoginButton=ManageLocator.getLocator("LoginButton");
				
				
				boolean result=true;//selectLanguage();
				if(!result){
					throw new Exception("Error ocurred while choosing language code.");
				}
				
				WebElement eleUserName=webBrowser.findElement(locatorUserName[0],locatorUserName[1]);
				WebElement elePassword=webBrowser.findElement(locatorPWD[0],locatorPWD[1]);
				WebElement btnLogin=webBrowser.findElement(locatorLoginButton[0], locatorLoginButton[1]);
				
				eleUserName.clear();
				eleUserName.sendKeys(userName);
				TestResult.addTestResult("Enter username as "+userName,"Passed");
				elePassword.clear();
				elePassword.sendKeys(password);
				TestResult.addTestResult("Enter Password as "+password,"Passed");
				
				webBrowser.waitUntilElementEnabledWithCSS(btnLogin);
				btnLogin.click();
				TestResult.addTestResult("Click Login button","Passed");
				
		    }catch(Exception e){
		    	TestResult.addTestResult("Login into application","Failed","- Error occured while login into app.");
		    	throw e;
		    }
	    }
		
	 private boolean selectLanguage() {		 
		 try{
			 
			 String[] locatorSelectedLng=ManageLocator.getLocator("Login.Language.dropdown.selected");
			 WebElement eleSelectedLanguage=webBrowser.findElement(locatorSelectedLng[0],locatorSelectedLng[1]);
			 			 
			 if(!eleSelectedLanguage.getText().trim().equalsIgnoreCase(languageCode)){			 
				 String[] locatorLang=ManageLocator.getLocator("Login.Language.dropdown");
				 WebElement dropdownLanguage=webBrowser.findElement(locatorLang[0], locatorLang[1]);
				 int offSetHeight=dropdownLanguage.getSize().getHeight()/2;
				 int offSetWidth=dropdownLanguage.getSize().getWidth()-10;
				 
				 Actions build = new Actions(webBrowser.getDriver());
				 build.moveToElement(dropdownLanguage, offSetWidth, offSetHeight).click().build().perform();
				 
				 
				 String[] locatorSelectLang=ManageLocator.getLocator("Login.Language.dropdown.select");
				 locatorSelectLang[1]=locatorSelectLang[1].replaceAll("@language", languageCode);
				 WebElement selectLanguage=webBrowser.findElement(locatorSelectLang[0], locatorSelectLang[1]);
				 selectLanguage.click();
				 TestResult.addTestResult("Choose Language code as "+languageCode,"Passed");				 
			 }else{
				 TestResult.addTestResult("Choose Language code as "+languageCode,"Passed","- Defaulted language was same as specified lanaguage code in test data. Hence language code has not choosen again.");
			 }
		 }catch(Exception e){
			 TestResult.addTestResult("Choose Language code as "+languageCode,"Failed","- Error occured while choosing another language code.");
		     return false;
		 }
		 
		 return true;
	 }
	 
	 private void clickAgentSettingMenu(){
		 try{
			 String[] locator=ManageLocator.getLocator("Menu.AgentSetting");
			 WebElement eleMenuAgentSetting=webBrowser.findElement(locator[0], locator[1]);
			 eleMenuAgentSetting.click();
			 TestResult.addTestResult("Click Agent Setting menu option","Passed");		
		 }catch(Exception e){
			 TestResult.addTestResult("Click Agent Setting menu option","Failed","- Error occured while clicking on Agent Setting menu");
		     throw e;
		 }
	 }
	 
	 private void clickDataImportTab(){
		 try{
			 String[] locator=ManageLocator.getLocator("Menu.AgentSetting.DataImport");
			 WebElement eleDataImport=webBrowser.waitUntilElementToBeVisible(locator[0], locator[1]);
			 eleDataImport.click();		 
			 TestResult.addTestResult("Click Data Import menu option","Passed");	
		 }catch(Exception e){
			 TestResult.addTestResult("Click Data Import menu option","Failed","- Error occured while clicking on Data Import menu");
		     throw e;
		 }
	 }
	 
	 private void browseExcelFile(){
		 try{
			 String[] locator=ManageLocator.getLocator("AgentSetting.ImportFile.FileInput");
			 String filePath=mapTestData.get("FilePath");		 
			 
			 WebElement eleUploadFile=webBrowser.findElement(locator[0], locator[1]);
			 eleUploadFile.sendKeys(filePath);			 
			
			 WebElement eleUploadLoader = webBrowser.getDriver().switchTo().activeElement();
			 webBrowser.waitForElementToBeInvisible(eleUploadLoader);
			 
			 WebElement eleBadErrorPop=webBrowser.getDriver().switchTo().activeElement();
			 if(eleBadErrorPop.getText().toLowerCase().contains("ok")){
				 TestResult.addTestResult("Upload File","Failed","- There is some problem with browsed file.");
				 System.out.println("Info: There is some problem with browsed file.");
				 return;
			 }
			 TestResult.addTestResult("Upload file","Passed");
			 webBrowser.waitForElementToBeInvisible(locator[0], locator[1]);
		 }catch(Exception e){
			 TestResult.addTestResult("Upload file","Failed","- Error occured while uploading the file.");
		     throw e;
		 }
	 }
	 
	 private void clickImportButton(){
		 try{
	          String[] locator=ManageLocator.getLocator("AgentSetting.DataImport.ImportButon");
			  WebElement eleImportButton=webBrowser.waitUntilElementToBeVisible(locator[0], locator[1]);
			  eleImportButton.click();	
			  TestResult.addTestResult("Click Import button","Passed");
		 }catch(Exception e){
			 TestResult.addTestResult("Click Import button","Failed","- Error occured while clicking on import button.");
		     throw e;
		 }
	 }
	 
	 private void verifyImportStatus(){
         try{
				 String[] locator=ManageLocator.getLocator("AgentSetting.DataImport.Status.Checked");
				 webBrowser.waitForElementToBeInvisible(locator[0], locator[1]);		 
				 
				 locator=ManageLocator.getLocator("AgentSetting.DataImport.Status");
				 WebElement eleImportStatus=webBrowser.findElement(locator[0], locator[1]);
				 
				 String status=eleImportStatus.getText();
				
				 if((status.equalsIgnoreCase(mapTestData.get("Expected: Import Status").trim()))){
					 TestResult.addTestResult("Verify Import Status","Passed");	 
				 }else{
					 TestResult.addTestResult("Verify Import Status","Failed","- Current import status: "+status+" and Expected status: "+mapTestData.get("Expected: Import Status").trim()+".");
				 }
         }catch(Exception e){
        	 TestResult.addTestResult("Verify Import Status","Failed","- Error occured in verification import status.");
		     throw e;
		 }
	 }
}
	

