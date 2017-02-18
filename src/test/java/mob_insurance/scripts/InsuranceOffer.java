/*DeleteClientAndContract*/
package mob_insurance.scripts;
import java.io.File;
import java.util.Random;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import mob_insurance.common_lib.LoadProperty;
import mob_insurance.functions.TestBase;
import mob_insurance.functions.CoreRepository;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Point;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import org.testng.annotations.Parameters;
import org.apache.commons.lang.StringUtils;

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
			int elementTimeOut = Integer.valueOf(LoadProperty.getVar("ElementImplicitTimeOutFactor", "config"));
			getDriver().manage().timeouts().pageLoadTimeout(pageTimeOut, TimeUnit.SECONDS);
			getDriver().manage().timeouts().implicitlyWait(elementTimeOut, TimeUnit.SECONDS);
			//create instance of class core
			coreFunc = new CoreRepository();
			//Assign webdriver driver to class core
			coreFunc.setDriver(this.getDriver());
			//navigate to specified URL
			coreFunc.openURL();
			//Login to application with specified credentials
			coreFunc.Login();
			//Prepare test data
			Object[][] testData=null;
			testData=coreFunc.getTestData("InsuranceOffer.xls");
			if (testData == null) {
				System.out.println("File Name is Incorrect");
				Reporter.log("File Name is Incorrect");
				coreFunc.logData("InsuranceOffer", "", "Failed","File Name is Incorrect", assertEnabled);
				Assert.assertEquals(testData, "null");
			}
			System.out.println("TestCaseCount : "+testData.length);
			Reporter.log("TestCaseCount : "+testData.length);
			for(int tcNo=0;tcNo<testData.length;tcNo++)
			{
				Reporter.log("*******************************************************************************************");
				if((testData[tcNo][0])==null){					
					Reporter.log("Test case not found in test data sheet");
					Assert.assertEquals(false, true, "Test case not found in test data sheet");					
				}				
				Reporter.log("S.No: " +tcNo+ " Test execution started for testID: "+testData[tcNo][0]);
				boolean TarifCalc=false;
							
				System.out.println("Test Case ID : "+testData[tcNo][0]);
				//Verify 'ExecuteTest' column value to identify whether test case need to execute or not
				if(String.valueOf(testData[tcNo][1]).equalsIgnoreCase("TRUE")){
					
					TarifCalc= TarifCalcTest(testData[tcNo]);
					
					if(TarifCalc)
					{
						coreFunc.logData("InsuranceOfferTest",String.valueOf(testData[tcNo][0]),"Passed","",assertEnabled);
						System.out.println("Test Case"+testData[tcNo][0]+" is passed.");
					}
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
		}		
		
	}

	private boolean TarifCalcTest(Object[] testData)
	{
    	System.out.println("Debug log : Entering in TarifCalcTest ");
       	String testID=String.valueOf(testData[0]);
    	try
		{
    		String email =String.valueOf(testData[3]);	    	
    		Reporter.log("Calculate tarrif for Client Email Id :  "+email);
    		
    		WebDriverWait clientWait = new WebDriverWait(getDriver(), 45);
    		
    		Reporter.log("Search client by Email Id :  "+email);  
    		Thread.sleep(10000);
			getDriver().findElement(By.name(LoadProperty.getVar("clientSearchBox", "element"))).clear();
			getDriver().findElement(By.name(LoadProperty.getVar("clientSearchBox", "element"))).sendKeys(email);
			
			clientWait.until(ExpectedConditions.invisibilityOfElementLocated(By.className(LoadProperty.getVar("clientGridWait", "element"))));
			
			//Verify client found or not with specified name in grid 
			WebElement recordcount =  getDriver().findElement(By.xpath(LoadProperty.getVar("recordCount", "element")));
			System.out.println("Debug log :  Client count "+ recordcount.getText());
			if(recordcount.getText().equalsIgnoreCase(coreFunc.GetDELanguageText("No data to display"))){
				Reporter.log("Unable to verify Tarif Calculation Test due to Client not found with Email Id :  "+email);
				coreFunc.logData("TarifCalcTest",testID,"Failed","Unable to verify Tarif Calculation Test due to Client not found with Email Id :  "+email+"  [ TCID: "+testID+"]",assertEnabled);					
	    		return false;
			}
			else{
				Reporter.log("Client found with details Email Id :  "+email);
			}
			Thread.sleep(5000);
			//select first record
			getDriver().findElement(By.xpath(LoadProperty.getVar("firstRecord", "element"))).click();
			
				
			WebDriverWait clientGridWait = new WebDriverWait(getDriver(), 45);
			clientGridWait.until(ExpectedConditions.visibilityOfElementLocated(By.name(LoadProperty.getVar("email", "element"))));
				
			Thread.sleep(5000);
			
			//click on tarif calc tab
			System.out.println("Debug log 1 ");
			getDriver().manage().timeouts().implicitlyWait(10000, TimeUnit.SECONDS);
			getDriver().findElement(By.xpath(LoadProperty.getVar("tarifcalculator", "element"))).click();
			Reporter.log("Clicked on tab :  "+LoadProperty.getVar("tarifcalculator", "element"));
			
			Thread.sleep(5000);
			
			
			
			//Click Insurance Category tab
			String insuranceCategory=String.valueOf(testData[4]);
			String insCategoryLocator=LoadProperty.getVar("Menu.TarifCalculator.InsuranceCategory", "element");
			insCategoryLocator=insCategoryLocator.replaceAll("@category", insuranceCategory);
			getDriver().findElement(By.xpath(insCategoryLocator)).click();
						
			//click on Insurance Type tab
			getDriver().manage().timeouts().implicitlyWait(10000, TimeUnit.SECONDS);
			String insuranceType=String.valueOf(testData[5]);
			String insTypeLocator=LoadProperty.getVar("Menu.TarifCalculator.InsuranceType", "element");
			insTypeLocator=insTypeLocator.replaceAll("@type", insuranceType);
			getDriver().findElement(By.xpath(insTypeLocator)).click();
			Reporter.log("Clicked on tab :  "+LoadProperty.getVar("composit", "element"));
			
			Thread.sleep(5000);
			
			//click on send button
			coreFunc.ClickOnActionButton("Send");
			//Wait till Results tab gets appeared on page	
			String resultTab=LoadProperty.getVar("Menu.TarifCalculator.ResultTab", "element");
			coreFunc.waitUntilElementToBeVisible("xpath", resultTab,180);
			Reporter.log("Clicked on '"+ coreFunc.GetDELanguageText("Send")+"' button");
			
//			Click 1st Order button
			String orderButtonLocator=LoadProperty.getVar("TarifCalculator.ResultTab.Button.Order", "element");
			WebElement eleOrderButton=coreFunc.findElement("xpath", orderButtonLocator);
			eleOrderButton.click();
			
//			Wait until Product details pop-up comes-up
			String productDetailsPopUpLocator=LoadProperty.getVar("TarifCalculator.Popup.ProductDetails", "element");
			coreFunc.waitUntilElementToBeVisible("xpath", productDetailsPopUpLocator,180);
			
//			Accept the Terms
			String termAcceptLocator=LoadProperty.getVar("TarifCalculator.Pop-up.ProductDetails.Acceptterm", "element");
			WebElement eleTermAccept=coreFunc.findElement("xpath", termAcceptLocator);
			eleTermAccept.click();
			
//			Click Offer button
			String offerButtonLocator=LoadProperty.getVar("TarifCalculator.Popup.ProductDetails.OfferButton", "element");
			WebElement eleOfferButton=coreFunc.findElement("xpath", offerButtonLocator);
			eleOfferButton.click();
			
//			Wait for Confirmation message box
			String confirmBoxLocator=LoadProperty.getVar("TarifCalculator.Popup.ProductDetails.ConfirmBox", "element");
			coreFunc.waitUntilElementToBeVisible("xpath", confirmBoxLocator,180);

//			Click Yes button
			String YesButtonLocator=LoadProperty.getVar("TarifCalculator.Popup.ProductDetails.ConfirmBox.YesButton", "element");
			WebElement eleYesButton=coreFunc.findElement("xpath", YesButtonLocator);
			eleYesButton.click();

//			Wait for success message 
			String successMessageLocator=LoadProperty.getVar("TarifCalculator.Popup.ProductDetails.successmessage", "element");
			coreFunc.waitUntilElementToBeVisible("xpath", successMessageLocator,180);
			
//			Close the Product Details
			String closeButtonLocator=LoadProperty.getVar("TarifCalculator.Popup.ProductDetails.closebutton", "element");
			WebElement eleCloseButton=coreFunc.findElement("xpath", closeButtonLocator);
			eleCloseButton.click();
			
//			Click Clients tab
			String clientTabLocator=LoadProperty.getVar("Menu.Client", "element");
			WebElement eleClientTab=coreFunc.findElement("xpath", clientTabLocator);
			eleClientTab.click();
			
//			Search client again
			Thread.sleep(10000);
			getDriver().findElement(By.name(LoadProperty.getVar("clientSearchBox", "element"))).clear();
			getDriver().findElement(By.name(LoadProperty.getVar("clientSearchBox", "element"))).sendKeys(email);
			
			clientWait.until(ExpectedConditions.invisibilityOfElementLocated(By.className(LoadProperty.getVar("clientGridWait", "element"))));

//			Get Contract count
			String contractCountLocator=LoadProperty.getVar("Client.Contract.count", "element");
			WebElement eleContractCount=coreFunc.findElement("xpath", contractCountLocator);
			String count=eleContractCount.getText();
			System.out.println("Contract count: "+count);
			
			System.out.println("Debug log : Exiting from TarifCalcTest");				
	    }
		catch(NoSuchElementException eNFound)
		{
    		coreFunc.logData("TarifCalcTest",testID,"Failed","Error: "+eNFound.getMessage()+" element not found. [ TCID: "+testID+"]",assertEnabled);
    		System.out.println("Debug log : Error ==> "+eNFound.getMessage()+" element not found. [ TCID: "+testID+"]");
    		return false;
    	}
		catch(ElementNotVisibleException eNVisible)
		{
    		coreFunc.logData("TarifCalcTest",testID,"Failed","Error: "+eNVisible.getMessage()+" element is not visible. [ TCID: "+testID+"]",assertEnabled);
    		System.out.println("Debug log : Error ==> "+eNVisible.getMessage()+" element is not visible. [ TCID: "+testID+"]");
    		return false;
    	}
		catch(Exception e)
		{
    		coreFunc.logData("TarifCalcTest",testID,"Failed","Error: occured on "+e.getMessage()+" element.[ TCID: "+testID+"]",assertEnabled);
    		System.out.println("Debug log : Error ==> occured on  "+e.getMessage()+" element.[ TCID: "+testID+"]");
    		return false;
    	}
    	return true;
    }
	   
}