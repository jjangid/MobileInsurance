/*DeleteClientAndContract*/
package mob_insurance.scripts;
import java.util.concurrent.TimeUnit;

import mob_insurance.functions.TestBase;
import mob_insurance.io.LoadProperty;
import mob_insurance.listeners.TestResult;
import mob_insurance.functions.CoreRepository;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

public class InsuranceOffer extends TestBase{
	CoreRepository coreFunc=null;
	Boolean assertEnabled = false;
	String locatorType=null;
	String locator=null;
	@Test
	public void InsuranceOfferTest() throws Exception{
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
			testData=coreFunc.getTestData("InsuranceOffer.xls");
			if (testData == null) {
				System.out.println("File Name is Incorrect");
				Reporter.log("File Name is Incorrect");
				coreFunc.logData("InsuranceOffer", "", "Failed","File Name is Incorrect", assertEnabled);
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
				boolean TarifCalc=false;
				String testname = (String) testData[tcNo][0];
				TestResult.addTestResult(testname,"");
								
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
					
					TarifCalc= TarifCalcTest(testData[tcNo]);
					
					if(TarifCalc)
					{
						coreFunc.logData("InsuranceOfferTest",String.valueOf(testData[tcNo][0]),"Passed","",assertEnabled);
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
			coreFunc.logData("InsuranceOfferTest","","Failed","Error: Refer output logs for more information.",assertEnabled);
			e.printStackTrace();
			TestResult.appendTestResult();
		}		
		
	}

	private boolean TarifCalcTest(Object[] testData)
	{
    	System.out.println("Debug log : Entering in TarifCalcTest ");
       	String testID=String.valueOf(testData[0]);
       	String testStep="";
    	try
		{

    		String email =String.valueOf(testData[3]);
    		testStep="Search client with "+email+" email.";
    		Reporter.log("Calculate tarrif for Client Email Id :  "+email);
    		
    		WebDriverWait clientWait = new WebDriverWait(getDriver(), 45);
    		
    		Reporter.log("Search client by Email Id :  "+email); 
    		System.out.println("Search client by Email Id :  "+email);
    		Thread.sleep(10000);
			getDriver().findElement(By.name(LoadProperty.getVar("clientSearchBox", "element"))).clear();
			getDriver().findElement(By.name(LoadProperty.getVar("clientSearchBox", "element"))).sendKeys(email);
			
			clientWait.until(ExpectedConditions.invisibilityOfElementLocated(By.className(LoadProperty.getVar("clientGridWait", "element"))));
			
			//Verify client found or not with specified name in grid 
			WebElement recordcount =  getDriver().findElement(By.xpath(LoadProperty.getVar("recordCount", "element")));
			if(recordcount.getText().equalsIgnoreCase(coreFunc.GetDELanguageText("No data to display"))){
				Reporter.log("Unable to verify Tarif Calculation Test due to Client not found with Email Id :  "+email);
				TestResult.addTestResult(testStep,"Failed","Unable to find client with specified email address.");
				coreFunc.logData("TarifCalcTest",testID,"Failed","Unable to verify Tarif Calculation Test due to Client not found with Email Id :  "+email+"  [ TCID: "+testID+"]",assertEnabled);					
	    		return false;
			}
			
			TestResult.addTestResult(testStep,"Passed");
//			Save current contract count
            
			String contractCountLocator=LoadProperty.getVar("Client.Contract.count", "element");
			WebElement eleContractCount=coreFunc.findElement("xpath", contractCountLocator);
			int contractCount=Integer.valueOf(eleContractCount.getText());
			System.out.println("Client contract count: "+contractCount);
			Reporter.log("Client found with details Email Id :  "+email);
			
			Thread.sleep(5000);
			//select first record
			testStep="Click on client record";
			System.out.println("Click on client record");
			getDriver().findElement(By.xpath(LoadProperty.getVar("firstRecord", "element"))).click();
			TestResult.addTestResult(testStep,"Passed");
				
			WebDriverWait clientGridWait = new WebDriverWait(getDriver(), 45);
			clientGridWait.until(ExpectedConditions.visibilityOfElementLocated(By.name(LoadProperty.getVar("email", "element"))));
				
			Thread.sleep(5000);
			
			//click on tarif calc tab
			testStep="Click Tarif Calculator Tab";
			System.out.println("Click Tarif Calculator Tab");
//			getDriver().manage().timeouts().implicitlyWait(10000, TimeUnit.SECONDS);
			getDriver().findElement(By.xpath(LoadProperty.getVar("tarifcalculator", "element"))).click();
			TestResult.addTestResult(testStep,"Passed");
			Reporter.log("Clicked on tab :  "+LoadProperty.getVar("tarifcalculator", "element"));
			
			Thread.sleep(5000);
			
			
			
			//Click Insurance Category tab
			
			String insuranceCategory=String.valueOf(testData[4]);
			testStep="Click "+insuranceCategory+" insurance category.";
			System.out.println(testStep);
			String insCategoryLocator=LoadProperty.getVar("Menu.TarifCalculator.InsuranceCategory", "element");
			insCategoryLocator=insCategoryLocator.replaceAll("@category", insuranceCategory);
			TestResult.addTestResult(testStep,"Passed");
			getDriver().findElement(By.xpath(insCategoryLocator)).click();
						
			//click on Insurance Type tab
			String insuranceType=String.valueOf(testData[5]);
			testStep="Click "+insuranceType+" insurance type.";
			System.out.println(testStep);
			String insTypeLocator=LoadProperty.getVar("Menu.TarifCalculator.InsuranceType", "element");
			insTypeLocator=insTypeLocator.replaceAll("@type", insuranceType);
			WebElement eleInsType=coreFunc.findElement("xpath",insTypeLocator);
		    eleInsType.click();
			TestResult.addTestResult(testStep,"Passed");
			Reporter.log("Clicked on tab :  "+LoadProperty.getVar("composit", "element"));
			
			Thread.sleep(5000);
			
			//click on send button
			testStep="Click Send button";
			System.out.println(testStep);
			String sendButtonLocator=LoadProperty.getVar("TarifCalculator.SendButton", "element");
			WebElement eleSendButton=coreFunc.findElement("xpath", sendButtonLocator);
			eleSendButton.click();
			TestResult.addTestResult(testStep,"Passed");
			Reporter.log("Clicked on '"+ coreFunc.GetDELanguageText("Send")+"' button");
			
			//Wait till Results tab gets appeared on page	
			String resultTab=LoadProperty.getVar("Menu.TarifCalculator.ResultTab", "element");
			testStep="Hold for while till Results tab gets appeared.";
			System.out.println(testStep);
			try{
			    coreFunc.waitUntilElementToBeVisible("xpath", resultTab,180);
			    TestResult.addTestResult(testStep,"Passed");
			}catch(Exception e){
				TestResult.addTestResult(testStep,"Failed","Results tab is taking too much time to load.");
				return false;
			}
			
//			Click 1st Order button
			testStep="Click Order button";
			System.out.println(testStep);
			String orderButtonLocator=LoadProperty.getVar("TarifCalculator.ResultTab.Button.Order", "element");
			WebElement eleOrderButton=coreFunc.findElement("xpath", orderButtonLocator);
			eleOrderButton.click();
			TestResult.addTestResult(testStep,"Passed");
			
//			Wait until Product details pop-up comes-up
			String productDetailsPopUpLocator=LoadProperty.getVar("TarifCalculator.Popup.ProductDetails", "element");
			testStep="Hold for while till pop-up window comes up";
			System.out.println(testStep);
			try{
			    coreFunc.waitUntilElementToBeVisible("xpath", productDetailsPopUpLocator,180);
			    TestResult.addTestResult(testStep,"Passed");
			}catch(Exception e){
				TestResult.addTestResult(testStep,"Failed","Pop-up is taking too much time to come up.");
				return false;
			}
			
			
//			Accept the Terms
			String termAcceptLocator=LoadProperty.getVar("TarifCalculator.Pop-up.ProductDetails.Acceptterm", "element");
			testStep="Accept the terms & conditions.";
			System.out.println(testStep);
			WebElement eleTermAccept=coreFunc.findElement("xpath", termAcceptLocator);
			eleTermAccept.click();
			TestResult.addTestResult(testStep,"Passed");
			
//			Click Offer button
			testStep="Cllick Offer button.";
			System.out.println(testStep);
			String offerButtonLocator=LoadProperty.getVar("TarifCalculator.Popup.ProductDetails.OfferButton", "element");
			WebElement eleOfferButton=coreFunc.findElement("xpath", offerButtonLocator);
			eleOfferButton.click();
			TestResult.addTestResult(testStep,"Passed");
			
//			Wait for Confirmation message box
			
			String confirmBoxLocator=LoadProperty.getVar("TarifCalculator.Popup.ProductDetails.ConfirmBox", "element");
			testStep="Hold for while till confirmation message comes up.";
			System.out.println(testStep);
			try{
			    coreFunc.findElement("xpath", confirmBoxLocator);
			    TestResult.addTestResult(testStep,"Passed");
			}catch(Exception e){
				TestResult.addTestResult(testStep,"Failed","Confirmation message is taking too much time appear.");
				return false;
			}

//			Click Yes button
			testStep="Click Yes button of Confirmation message box.";
			System.out.println(testStep);
			String YesButtonLocator=LoadProperty.getVar("TarifCalculator.Popup.ProductDetails.ConfirmBox.YesButton", "element");
			WebElement eleYesButton=coreFunc.findElement("xpath", YesButtonLocator);
			eleYesButton.click();
			TestResult.addTestResult(testStep,"Passed");

//			Wait for success message 
			String successMessageLocator=LoadProperty.getVar("TarifCalculator.Popup.ProductDetails.successmessage", "element");
			testStep="Hold for while till success message comes up.";
			System.out.println(testStep);
			try{
			    coreFunc.waitUntilElementToBeVisible("xpath", successMessageLocator,180);
			    TestResult.addTestResult(testStep,"Passed");
			}catch(Exception e){
				TestResult.addTestResult(testStep,"Failed","success message is taking too much time appear.");
				return false;
			}
			
//			Close the Product Details
			String closeButtonLocator=LoadProperty.getVar("TarifCalculator.Popup.ProductDetails.closebutton", "element");
			testStep="Close Product pop-up window.";
			System.out.println(testStep);
			WebElement eleCloseButton=coreFunc.findElement("xpath", closeButtonLocator);
			eleCloseButton.click();
			TestResult.addTestResult(testStep,"Passed");
			
//			Click Clients tab
			String clientTabLocator=LoadProperty.getVar("Menu.Client", "element");
			testStep="Move back to Clients tab.";
			System.out.println(testStep);
			WebElement eleClientTab=coreFunc.findElement("xpath", clientTabLocator);
			eleClientTab.click();
			TestResult.addTestResult(testStep,"Passed");
			
//			Search client again
			testStep="Verify no of contract attached with client assocated with "+email+".";
			System.out.println(testStep);
			Thread.sleep(10000);
			getDriver().findElement(By.name(LoadProperty.getVar("clientSearchBox", "element"))).clear();
			getDriver().findElement(By.name(LoadProperty.getVar("clientSearchBox", "element"))).sendKeys(email);
			
			clientWait.until(ExpectedConditions.invisibilityOfElementLocated(By.className(LoadProperty.getVar("clientGridWait", "element"))));

//			Get Contract count
			eleContractCount=coreFunc.findElement("xpath", contractCountLocator);
			int cCount=Integer.valueOf(eleContractCount.getText());
			System.out.println("Current client contract count: "+cCount);
			if(cCount == (contractCount+1)){
				TestResult.addTestResult(testStep,"Passed");	
			}else{
				TestResult.addTestResult(testStep,"Failed","Current contract count: "+cCount+" and Initial contract count: "+contractCount);
			}
			
			boolean needToLogOut=Boolean.valueOf(String.valueOf(testData[6]));
			if(needToLogOut){
			  String logOutLocator=LoadProperty.getVar("Button.Logout", "element");
			  testStep="Logout from application.";
			  WebElement eleLogOut=coreFunc.findElement("xpath", logOutLocator);
			  eleLogOut.click();
			  TestResult.addTestResult(testStep,"Passed");
			}
			
			System.out.println("Debug log : Exiting from TarifCalcTest");				
	    }
		catch(NoSuchElementException eNFound)
		{
			TestResult.addTestResult(testStep,"Failed","Error: element not found. [ TCID: "+testID+"]");
    		coreFunc.logData("TarifCalcTest",testID,"Failed","Error: "+eNFound.getMessage()+" element not found. [ TCID: "+testID+"]",assertEnabled);
    		System.out.println("Debug log : Error ==> "+eNFound.getMessage()+" element not found. [ TCID: "+testID+"]");
    		return false;
    	}
		catch(ElementNotVisibleException eNVisible)
		{
			TestResult.addTestResult(testStep,"Failed","Error: element is not visible. [ TCID: "+testID+"]");
    		coreFunc.logData("TarifCalcTest",testID,"Failed","Error: "+eNVisible.getMessage()+" element is not visible. [ TCID: "+testID+"]",assertEnabled);
    		System.out.println("Debug log : Error ==> "+eNVisible.getMessage()+" element is not visible. [ TCID: "+testID+"]");
    		return false;
    	}
		catch(Exception e)
		{
			TestResult.addTestResult(testStep,"Failed","Error: occured on element.[ TCID: "+testID+"]");
			coreFunc.logData("TarifCalcTest",testID,"Failed","Error: occured on "+e.getMessage()+" element.[ TCID: "+testID+"]",assertEnabled);
    		System.out.println("Debug log : Error ==> occured on  "+e.getMessage()+" element.[ TCID: "+testID+"]");
    		return false;
    	}
    	return true;
    }
	   
}