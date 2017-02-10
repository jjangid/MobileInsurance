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

public class TarifCalculator extends TestBase{
	CoreRepository coreFunc=null;
	Boolean assertEnabled = false;
	String locatorType=null;
	String locator=null;
	@Test
	public void TarifCalculatorTest() throws Exception{
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
			testData=coreFunc.getTestData("TarifCalculator.xls");
			if (testData == null) {
				System.out.println("File Name is Incorrect");
				Reporter.log("File Name is Incorrect");
				coreFunc.logData("TarifCalculator", "", "Failed","File Name is Incorrect", assertEnabled);
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
						coreFunc.logData("TarifCalculatorTest",String.valueOf(testData[tcNo][0]),"Passed","",assertEnabled);
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
			coreFunc.logData("TarifCalculatorTest","","Failed","Error: Refer output logs for more information.",assertEnabled);
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
			
			//click on composit tab
			getDriver().manage().timeouts().implicitlyWait(10000, TimeUnit.SECONDS);
			getDriver().findElement(By.xpath(LoadProperty.getVar("composit", "element"))).click();
			Reporter.log("Clicked on tab :  "+LoadProperty.getVar("composit", "element"));
			
			Thread.sleep(5000);
			
			//click on horseholderliability tab
			getDriver().manage().timeouts().implicitlyWait(10000, TimeUnit.SECONDS);
			getDriver().findElement(By.xpath(LoadProperty.getVar("horseholderliability", "element"))).click();
			Reporter.log("Clicked on tab :  "+LoadProperty.getVar("horseholderliability", "element"));
			
			Thread.sleep(5000);
			
			clientWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(LoadProperty.getVar("desiredselfparticipation", "element"))));
			
			//click on send button
			coreFunc.ClickOnActionButton("Send");
			
			Reporter.log("Clicked on '"+ coreFunc.GetDELanguageText("Send")+"' button");
			
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