package mob_insurance.scripts;

import java.util.Iterator;
import java.util.Map;
import mob_insurance.functions.TestBase;
import mob_insurance.io.LoadProperty;
import mob_insurance.io.ManageConfig;
import mob_insurance.io.ManageLocator;
import mob_insurance.listeners.TestData;
import mob_insurance.listeners.TestResult;
import mob_insurance.functions.CoreRepository;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;
import org.testng.annotations.Test;


public class AgentSettingUploadFile extends TestBase{
	CoreRepository coreFunc=null;
	Boolean assertEnabled = false;
	String locatorType=null;
	String locator=null;
	private Map<String,String> mapTestData=null;
		 	
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
			coreFunc.logData("AgentSettingUploadFileTest","","Failed","Error: Refer output logs for more information.",assertEnabled);
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
	       coreFunc = new CoreRepository();
	       coreFunc.setDriver(this.getDriver());
	    }
	      
	 }
	 
	 public void runTestSuite(){
		 try{
		    			   
		    	System.out.println("Info: Loging into application with credential specified in Test Data file.");
		    	boolean needToLogin=Boolean.valueOf(String.valueOf(mapTestData.get("Login").trim()));
				if(needToLogin){
				  coreFunc.Login();					  
				}		    	
			    System.out.println("Info: Navigating to Import page.");
			    clickAgentSettingMenu();
			    clickDataImportTab();			    
			    System.out.println("Info: Importing excel file.");    	
			    browseExcelFile();
			    clickImportButton();
			    System.out.println("Info: Verify Import Status."); 
			    verifyImportStatus();
			    coreFunc.logData("AgentSettingUploadFileTest","","Pass","",assertEnabled);
			    boolean needToLogOut=Boolean.valueOf(String.valueOf(mapTestData.get("Logout").trim()));
				if(needToLogOut){
				  coreFunc.Logout();					  
				}
		  }catch(Exception e){
			 System.out.println("Info: "+e.getMessage());	
			 coreFunc.logData("AgentSettingUploadFileTest","","Failed","Error: Refer output logs for more information.",assertEnabled);
		 }finally{
			 //code for logout instead of closing browser
			 //coreFunc.tearDown();
			 //System.out.println("Info: closing browser instance.");  
		 }	
	 }
	 
	  
	 private void clickAgentSettingMenu(){
		 try{
			 String[] locator=ManageLocator.getLocator("Menu.AgentSetting");
			 WebElement eleMenuAgentSetting=coreFunc.findElement(locator[0], locator[1]);
			 eleMenuAgentSetting.click();
			 TestResult.addTestResult("Click Agent Setting menu option","Passed");
			 Reporter.log("Click Agent Setting menu option ==> Step Passed");
		 }catch(Exception e){
			 TestResult.addTestResult("Click Agent Setting menu option","Failed","- Error occured while clicking on Agent Setting menu");
		     throw e;
		 }
	 }
	 
	 private void clickDataImportTab(){
		 try{
			 String[] locator=ManageLocator.getLocator("Menu.AgentSetting.DataImport");
			 WebElement eleDataImport=coreFunc.waitUntilElementToBeVisible(locator[0], locator[1]);
			 eleDataImport.click();		 
			 TestResult.addTestResult("Click Data Import menu option","Passed");
			 Reporter.log("Click Data Import menu option ==> Step Passed");
		 }catch(Exception e){
			 TestResult.addTestResult("Click Data Import menu option","Failed","- Error occured while clicking on Data Import menu");
		     throw e;
		 }
	 }
	 
	 private void browseExcelFile(){
		 try{
			 String[] locator=ManageLocator.getLocator("AgentSetting.ImportFile.FileInput");
			 String filePath=mapTestData.get("FilePath");		 
			 
			 WebElement eleUploadFile=coreFunc.findElement(locator[0], locator[1]);
			 eleUploadFile.sendKeys(filePath);			 
			
			 WebElement eleUploadLoader = coreFunc.getDriver().switchTo().activeElement();
			 coreFunc.waitForElementToBeInvisible(eleUploadLoader);
			 
			 WebElement eleBadErrorPop=coreFunc.getDriver().switchTo().activeElement();
			 if(eleBadErrorPop.getText().toLowerCase().contains("ok")){
				 TestResult.addTestResult("Upload File","Failed","- There is some problem with browsed file.");
				 Reporter.log("Upload File - There is some problem with browsed file ==> Step Failed");
				 System.out.println("Info: There is some problem with browsed file.");
				 return;
			 }
			 TestResult.addTestResult("Upload file","Passed");
			 Reporter.log("Upload file ==> Step Passed");
			 coreFunc.waitForElementToBeInvisible(locator[0], locator[1]);
		 }catch(Exception e){
			 TestResult.addTestResult("Upload file","Failed","- Error occured while uploading the file.");
		     throw e;
		 }
	 }
	 
	 private void clickImportButton(){
		 try{
	          String[] locator=ManageLocator.getLocator("AgentSetting.DataImport.ImportButon");
			  WebElement eleImportButton=coreFunc.waitUntilElementToBeVisible(locator[0], locator[1]);
			  eleImportButton.click();	
			  TestResult.addTestResult("Click Import button","Passed");
			  Reporter.log("Click Import button ==> Step Passed");
		 }catch(Exception e){
			 TestResult.addTestResult("Click Import button","Failed","- Error occured while clicking on import button.");
		     throw e;
		 }
	 }
	 
	 private void verifyImportStatus(){
         try{
				 String[] locator=ManageLocator.getLocator("AgentSetting.DataImport.Status.Checked");
				 coreFunc.waitForElementToBeInvisible(locator[0], locator[1]);		 
				 
				 locator=ManageLocator.getLocator("AgentSetting.DataImport.Status");
				 WebElement eleImportStatus=coreFunc.findElement(locator[0], locator[1]);
				 
				 String status=eleImportStatus.getText();
				
				 if((status.equalsIgnoreCase(mapTestData.get("Expected: Import Status").trim()))){
					 TestResult.addTestResult("Verify Import Status","Passed");	 
					 Reporter.log("Verify Import Status ==> Step Passed");
				 }else{
					 TestResult.addTestResult("Verify Import Status","Failed","- Current import status: "+status+" and Expected status: "+mapTestData.get("Expected: Import Status").trim()+".");
				 }
         }catch(Exception e){
        	 Reporter.log("Verify Import Status - Error occured in verification import status. ==> Step Failed");
        	 TestResult.addTestResult("Verify Import Status","Failed","- Error occured in verification import status.");
		     throw e;
		 }
	 }
}
	

