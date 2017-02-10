package mob_insurance.testsuite;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;

import mob_insurance.common.teststep.CommonTestStep;
import mob_insurance.common.teststep.InsuranceField;
import mob_insurance.io.ManageLocator;
import mob_insurance.io.TestData;
import mob_insurance.processor.Browser;
import mob_insurance.processor.TestResult;

public class CreateContractTestSuite {

	private Map<String,String> mapTestData=null;
	 private Browser webBrowser=null;
	 private String languageCode="";

	 public CreateContractTestSuite(Map<String,String> mapTestdataRow, boolean isLoggedIn) throws Exception{
		
		mapTestData=mapTestdataRow;
 	  
	    if(mapTestData.isEmpty()){
	      System.out.println("Info: Test Data is empty hence aborted the test execution.");
	      return; 
	    }	    
	   
	    languageCode=mapTestData.get("Language Code")!=null?mapTestData.get("Language Code").toUpperCase():"EN";
	    String browserType=mapTestData.get("Browser");
	    
	    if(!languageCode.equalsIgnoreCase("EN") && !languageCode.equalsIgnoreCase("DE")){
	    	languageCode="EN";	
	    }
	    
	    if(ManageLocator.loadConfigProperties(languageCode)){
	      
	       TestResult.addTestResult(mapTestData.get("Test Name"),"");
	       webBrowser=new Browser(browserType);
	       TestResult.addTestResult("Open "+browserType+" browser","Passed");
	       if(!isLoggedIn){
	    	   String userName=mapTestData.get("Username");
				String password=mapTestData.get("Password");
				String URL=mapTestData.get("URL");				
				webBrowser.getURL(URL);
				CommonTestStep.logIn(userName, password, webBrowser, languageCode);
	       }
	    }
	}

	 public void runTestSuite(){
		 try{		    
			 
			 System.out.println("Info: Creating client record.");
			 closeHelpBox();
			 createClient();
			 System.out.println("Info: Started creating contract record.");
			 createContract();
			 System.out.println("Info: Loacting newly created client with email address.");
			 webBrowser.waitForWhile(5);			 
			 searchClientWithEmail();
			 System.out.println("Info: Locating newly created contract record and verying it's product.");
			 chooseContract();
		  }catch(Exception e){
			 System.out.println("Info: "+e.getMessage());			 
		 }finally{
			 webBrowser.tearDown();
			 System.out.println("Info: closing browser instance.");  
		 }	
	 }
	
	 public void createClient(){
		 
		 try{
			     
			     String[] locator=ManageLocator.getLocator("Tab.Selected");
				 WebElement eleSelectedTab=webBrowser.findElement(locator[0],locator[1]);
				 String tabName=eleSelectedTab.getText();
				 if(!tabName.equalsIgnoreCase("Clients")){
				   locator=ManageLocator.getLocator("Tab.Clients");
				   WebElement eleTabClient=webBrowser.findElement(locator[0],locator[1]); 
				   eleTabClient.click();
				   TestResult.addTestResult("Click Clients Tab. ","Passed");
				 }		 
				 
				 webBrowser.waitForWhile(5);
				 locator=ManageLocator.getLocator("Tab.Client.Button.Create");
				 WebElement eleBtnCreateClient=webBrowser.findElement(locator[0],locator[1]);
				 eleBtnCreateClient.click();
				 TestResult.addTestResult("Click Create button. ","Passed");
				 
				 locator=ManageLocator.getLocator("Create.Client.FirstName");
				 WebElement eleFirstName=webBrowser.findElement(locator[0],locator[1]);
				 eleFirstName.sendKeys(mapTestData.get("FirstName"));
				 TestResult.addTestResult("Enter First Name as "+mapTestData.get("FirstName"),"Passed");
				 
				 locator=ManageLocator.getLocator("Create.Client.LastName");
				 WebElement eleLastName=webBrowser.findElement(locator[0],locator[1]);
				 eleLastName.sendKeys(mapTestData.get("LastName"));
				 TestResult.addTestResult("Enter Last Name as "+mapTestData.get("LastName"),"Passed");
				 
				 locator=ManageLocator.getLocator("Create.Client.Email");
				 WebElement eleEmail=webBrowser.findElement(locator[0],locator[1]);
				 eleEmail.sendKeys(mapTestData.get("Email"));
				 TestResult.addTestResult("Enter Email as "+mapTestData.get("Email"),"Passed");
				 
				 locator=ManageLocator.getLocator("Create.Client.Button.Save");
				 WebElement eleSaveButton=webBrowser.findElement(locator[0],locator[1]);
				 eleSaveButton.click();		 
				 TestResult.addTestResult("Click Save button.","Passed");
				 
				 locator=ManageLocator.getLocator("Create.Client.Details.Header.ClientName");
				 locator[1]=locator[1].replaceAll("@clientName", mapTestData.get("FirstName")+" "+mapTestData.get("LastName"));
				 webBrowser.waitUntilElementToBeVisible(locator[0],locator[1]);
				 TestResult.addTestResult("Newly created client should be loaded in clients tab automatically.","Passed");		 
		 }catch(Exception e){
			 TestResult.addTestResult("Create Client record.","Failed","- Intermidiate step for creating client record got failed. Please troubleshoot issue based on above recorded steps in Test Result.");	    
		 }
	 }
	
	 public void createContract(){
		 String testStep="";
		 try{
			     testStep="Click Contract Tab.";
				 String[] locator=ManageLocator.getLocator("Tab.Clients.Contract");
				 WebElement eleContractTab=webBrowser.findElement(locator[0],locator[1]);
				 eleContractTab.click();
				 TestResult.addTestResult(testStep,"Passed");
				 
				 testStep="Click Create Contract button.";
				 locator=ManageLocator.getLocator("Tab.Client.Contract.Button.Create");
				 WebElement eleCreateButton=webBrowser.findElement(locator[0],locator[1]);
				 eleCreateButton.click();
				 TestResult.addTestResult(testStep,"Passed");
				 webBrowser.waitForWhile(5);
				 locator=ManageLocator.getLocator("Create.Contract.Category");
				 WebElement eleCategory=webBrowser.findElement(locator[0],locator[1]);
				 eleCategory.click();
				 				 
				 
				 testStep="Select Category";
				 String categoryOption=mapTestData.get("Category");
				 testStep="Select Category as "+categoryOption+".";
				 while(categoryOption.contains(">")){
					 String parentNodeText=categoryOption.substring(0, categoryOption.indexOf(">")).trim();
					 locator=ManageLocator.getLocator("Create.Contract.Category.ParentNode");
					 locator[1]=locator[1].replaceAll("@parentNodeText", parentNodeText);
					 WebElement eleParentNode=webBrowser.findElement(locator[0],locator[1]);
					 eleParentNode.click();			 
					 categoryOption=categoryOption.substring(categoryOption.indexOf(">")+1).trim();
				 }
				 
				 webBrowser.waitForWhile(2);
				 locator=ManageLocator.getLocator("Create.Contract.Category.TextNode");
				 locator[1]=locator[1].replaceAll("@textNode", categoryOption);
				 WebElement eleCategoryOption=webBrowser.findElement(locator[0],locator[1]);
				 eleCategoryOption.click();	
				 TestResult.addTestResult(testStep,"Passed");
				 
				 webBrowser.waitForWhile(5);
				 testStep="Select Provider as "+mapTestData.get("Provider")+" .";
				 locator=ManageLocator.getLocator("Create.Contract.ProviderName");
				 WebElement eleProviderName=webBrowser.findElement(locator[0],locator[1]);
				 eleProviderName.click();
				 
				 locator=ManageLocator.getLocator("Create.Contract.ProviderOption");
				 locator[1]=locator[1].replaceAll("@optionText", mapTestData.get("Provider").trim());
				 WebElement eleProviderOption=webBrowser.findElement(locator[0],locator[1]);
//				 ((JavascriptExecutor)webBrowser.getDriver()).executeScript("window.scrollTo(0,"+eleProviderOption.getLocation().y+")");
				 eleProviderOption.click();

				 TestResult.addTestResult(testStep,"Passed");
								 
				 webBrowser.waitForWhile(10);
				 testStep="Select Product as "+mapTestData.get("Product")+" .";
				 locator=ManageLocator.getLocator("Create.Contract.ProductName");
				 WebElement eleProductName=webBrowser.findElement(locator[0],locator[1]);
				 eleProductName.click();
				 
				 
				 locator=ManageLocator.getLocator("Create.Contract.ProductOption");
				 locator[1]=locator[1].replaceAll("@optionText", mapTestData.get("Product").trim());
				 WebElement eleProductOption=webBrowser.findElement(locator[0],locator[1]);
				 eleProductOption.click();
				 TestResult.addTestResult(testStep,"Passed");
				 
				 
				 testStep="Enter Ins Number as "+mapTestData.get("Ins Number")+" .";
				 locator=ManageLocator.getLocator("Create.Contract.InsName");
				 WebElement eleInsname=webBrowser.findElement(locator[0],locator[1]);
				 eleInsname.sendKeys(mapTestData.get("Ins Number"));
				 TestResult.addTestResult(testStep,"Passed");
				 
				 testStep="Select Period as "+mapTestData.get("Period")+" .";
				 locator=ManageLocator.getLocator("Create.Contract.Period");
				 WebElement elePeriod=webBrowser.findElement(locator[0],locator[1]);
				 elePeriod.click();
				 				 
				 locator=ManageLocator.getLocator("Create.Contract.Period.Option");
				 locator[1]=locator[1].replaceAll("@periodOption", mapTestData.get("Period"));
				 WebElement elePeriodOption=webBrowser.findElement(locator[0],locator[1]);
				 elePeriodOption.click();
				 TestResult.addTestResult(testStep,"Passed");
				 
				 String insuranceType=mapTestData.get("Insurance Type");
//				 String insuranceCategory=mapTestData.get("Insurance Category");
				 String testDataReference=mapTestData.get("Insurance Form Data File");
				 if(insuranceType.length() > 0 && testDataReference.length() > 0){
				   // Go ahead to choose insurance category and fill insurance detail on page 
					testStep="Click Contract Feature tab."; 
					locator=ManageLocator.getLocator("Tab.Client.Contract.Create.ContractFeature");
					WebElement eleContractFeatureTab=webBrowser.findElement(locator[0],locator[1]);
					eleContractFeatureTab.click(); 
					TestResult.addTestResult(testStep,"Passed");
					
					webBrowser.waitForWhile(2);
					testStep="Select Insurance Type as "+mapTestData.get("Insurance Type")+"."; 
					locator=ManageLocator.getLocator("Create.Contract.ContractFeature.Module");
					WebElement eleModuleDropDown=webBrowser.findElement(locator[0],locator[1]);
					eleModuleDropDown.click(); 
							
					webBrowser.waitForWhile(2);
					locator=ManageLocator.getLocator("Create.Contract.ContractFeature.Module.Option");
					locator[1]=locator[1].replaceAll("@optionText", mapTestData.get("Insurance Type"));
					WebElement eleModule=webBrowser.findElement(locator[0],locator[1]);
					if(eleModule != null){
					   eleModule.click();
					   TestResult.addTestResult(testStep,"Passed");
					}else{
					   TestResult.addTestResult(testStep,"Failed","Unable to select on designated insurance type.");	
					   return;
					}
					fillInsuranceFields(testDataReference,insuranceType);	
					webBrowser.waitForWhile(5);
					
					testStep="Click Contract Data Tab.";
					locator=ManageLocator.getLocator("Tab.Client.Contract.Create.ContractData");
					WebElement eleClientTab=webBrowser.findElement(locator[0],locator[1]);
					eleClientTab.click(); 
					TestResult.addTestResult(testStep,"Passed");
				 }		 
				 
				 testStep="Click Save button.";
				 locator=ManageLocator.getLocator("Tab.Client.Contract.Create.Button.Save");
				 WebElement eleSaveButton=webBrowser.findElement(locator[0],locator[1]);
				 //eleSaveButton.click();
				 eleSaveButton.click();
				 TestResult.addTestResult(testStep,"Passed");
		 }catch(Exception e){
			 TestResult.addTestResult(testStep,"Failed","Error occured on this step.");
			 throw e;
		 }
	 }

    private void fillInsuranceFields(String testDataReference, String insuranceType){
   	 TestData objTestData=new TestData();
   	 List<String> lsInteractedFields=new ArrayList<String>();
   	 Map<String,InsuranceField> mapInsFieldTestData=objTestData.readInsuranceTypeTestData(testDataReference,insuranceType);
   	 
   	 Iterator<String> itrKey=mapInsFieldTestData.keySet().iterator();
   	 while(itrKey.hasNext()){
   		 webBrowser.waitForWhile(1);
   		 String key=itrKey.next();
   		 InsuranceField objInsuranceField=mapInsFieldTestData.get(key);
   		 if(lsInteractedFields.contains(key))
   		   continue;
   		 String[] testResult=null;
   		 if(!objInsuranceField.dependOn.isEmpty()){
   		     String dependsOnItemKey=getDependsOnItemKey(mapInsFieldTestData,objInsuranceField.dependOn);
   		     if(dependsOnItemKey.isEmpty()){
   		       TestResult.addTestResult("Input Data into"+objInsuranceField.fieldTitle,"Failed","This field is depended on "+objInsuranceField.dependOn+" field and test data should be present for depends on field.");
   		       lsInteractedFields.add(key);
   		       continue;
   		     }
   			 testResult=interactDependsOnFirst(mapInsFieldTestData.get(dependsOnItemKey));
   			 lsInteractedFields.add(dependsOnItemKey);
   			 TestResult.addTestResult(testResult[0],testResult[1],testResult[2]);
   		 }
//   		 objInsuranceField.fieldName;
//   		 objInsuranceField.fieldType;
   		 testResult=webBrowser.interactWithElementBasedOnType(objInsuranceField);
   		 lsInteractedFields.add(key);
   		 TestResult.addTestResult(testResult[0],testResult[1],testResult[2]);
   	 }
    }
    
    private String[] interactDependsOnFirst(InsuranceField objInsuranceField){
   	 String[] testResult=webBrowser.interactWithElementBasedOnType(objInsuranceField);    	 
   	 return testResult;
    } 
    
    private String getDependsOnItemKey(Map<String,InsuranceField> mapInsFieldTestData,String title){
   	 Iterator<String> itrKey=mapInsFieldTestData.keySet().iterator();
   	 String dependsOnItemKey="";
   	 while(itrKey.hasNext()){
   		 String key=itrKey.next();
   		 InsuranceField objInsuranceField=mapInsFieldTestData.get(key);
   		 if(title.equalsIgnoreCase(objInsuranceField.fieldTitle)){
   			 dependsOnItemKey=key;
   			 break;
   		 }
   	 }
   	 
   	 return dependsOnItemKey;
    }
    
    
//    private String getFieldNameBasedOnTitle(Map<String,InsuranceField> mapInsFieldTestData,String title){
//   	 Iterator<String> itrKey=mapInsFieldTestData.keySet().iterator();
//   	 String fieldName="";
//   	 while(itrKey.hasNext()){
//   		 String key=itrKey.next();
//   		 InsuranceField objInsuranceField=mapInsFieldTestData.get(key);
//   		 if(title.equalsIgnoreCase(objInsuranceField.fieldTitle)){
//   			 fieldName= objInsuranceField.fieldName;
//   			 break;
//   		 }
//   	 }
//   	 
//   	 return fieldName;
//    }
  
    private void searchClientWithEmail(){
    	String email=mapTestData.get("Email");
    	
    	//Search Box locator
    	// Index 0: Locator Type and Index 1: Locator value
    	String[] locatorSearchBox=ManageLocator.getLocator("SearchBox");
    	
    	WebElement eleSearchBox=webBrowser.findElement(locatorSearchBox[0],locatorSearchBox[1]);
    	
    	eleSearchBox.sendKeys(email);
    	eleSearchBox.sendKeys(Keys.ENTER);
    	
    	TestResult.addTestResult("Enter email address as "+email+" .","Passed");
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
    		webBrowser.findElement(locatorSearchResult[0],locatorSearchResult[1]);
    	}catch(NoSuchElementException exception){
    		TestResult.addTestResult("Click recently added Contract record.","Failed","- Unable to locate client with email used during client creation.");
    		System.out.println("Error: Searched client has not found with specified email address. Further process has been aborted.");
    		throw exception;
    	}catch(TimeoutException exception){
    		TestResult.addTestResult("Click recently added Contract record.","Failed","- Unable to locate client with email used during client creation.");
    		System.out.println("Error: Searched client has not found with specified email address. Further process has been aborted.");
    		throw exception;
    	}
    	try{
    		eleExpandClient=webBrowser.findElement(locatorSearchResultClient[0],locatorSearchResultClient[1]);
    	}catch(NoSuchElementException exception){
    		TestResult.addTestResult("Click recently added Contract record.","Failed","- Unable to locate added contract.");
    		System.out.println("Error: No contract attached with this serached client. Further process has been aborted.");
    		throw exception;
    	}catch(TimeoutException exception){
    		TestResult.addTestResult("Click recently added Contract record.","Failed","- Unable to locate added contract.");
    		System.out.println("Error: No contract attached with this serached client. Further process has been aborted.");
    		throw exception;
    	}
    	webBrowser.waitForWhile(5);
    	try{
    		eleExpandClient.click();
    	}catch(org.openqa.selenium.StaleElementReferenceException ex){
    		eleExpandClient=webBrowser.findElement(locatorSearchResultClient[0],locatorSearchResultClient[1]);
    		eleExpandClient.click();
    	}
    	webBrowser.waitForElementToBeInvisible(locatorLoader[0],locatorLoader[1]);
    	webBrowser.waitForWhile(2); 		
    	
    	List<WebElement> lsContract=webBrowser.findElements(locatorSearchResultContract[0], locatorSearchResultContract[1]);
    	String contractProductName=mapTestData.get("Product");
    	WebElement eleExpectedContract=null;
    	
    	for(WebElement eleContract:lsContract){
    		WebElement eleProductName=webBrowser.findElement(locatorSearchResultContractProduct[0],locatorSearchResultContractProduct[1], eleContract);
    		String productName=eleProductName.getText();
    		if(contractProductName.equalsIgnoreCase(productName)){
    			eleExpectedContract=eleContract;
    			break;
    		}
    	}
    	if(eleExpectedContract != null){
    		eleExpectedContract.click();
    		TestResult.addTestResult("Click recently added Contract record.","Passed");
    		TestResult.addTestResult("Verify Product Name as"+contractProductName+" .","Passed");
			System.out.println("Message: Successfully able to access and click specified contract("+contractProductName+").");
		}else{
			TestResult.addTestResult("Click recently added Contract record.","Failed","Unable to locate recently created contract record.");
			System.out.println("Info: Specified contract has not found. Hence unable to view contract details.");
		}     		
    }
    
    private void closeHelpBox(){
   	try{
	    	 String[] locator=ManageLocator.getLocator("HelpBox.Button.Close");
			 WebElement eleContractTab=webBrowser.findElement(locator[0],locator[1]);
			 eleContractTab.click();
			 TestResult.addTestResult("Click Contract Tab.","Passed");
   	}catch(Exception e){
   		
   	}
    }

}
