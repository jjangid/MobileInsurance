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

public class DeleteClientAndContract extends TestBase{
	CoreRepository coreFunc=null;
	Boolean assertEnabled = false;
	String locatorType=null;
	String locator=null;
	@Test
	public void DeleteClientContract() throws Exception{
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
			testData=coreFunc.getTestData("DeleteClientContract.xls");
			if (testData == null) {
				System.out.println("File Name is Incorrect");
				Reporter.log("File Name is Incorrect");
				coreFunc.logData("DeleteClientContract", "", "Failed","File Name is Incorrect", assertEnabled);
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
				boolean DeleteContract=false;
				boolean DeleteClient=false;				
						
				System.out.println("Test Case ID : "+testData[tcNo][0]);
				//Verify 'ExecuteTest' column value to identify whether test case need to execute or not
				if(String.valueOf(testData[tcNo][1]).equalsIgnoreCase("TRUE")){
					
					//Verify 'DeleteContract' column value to identify whether client need to create or not
					if(String.valueOf(testData[tcNo][4]).equalsIgnoreCase("TRUE")){
						DeleteContract= DeleteContract(testData[tcNo]);	
						DeleteContract=true;
					}
					else{
						DeleteContract=true;
						Reporter.log("Contract deletion skipped for test case "+testData[tcNo][0]+".");
						System.out.println("Contract deletion skipped for test case "+testData[tcNo][0]+".");
					}
					
					//Verify 'DeleteClient' column value to identify whether client need to create or not
					if(String.valueOf(testData[tcNo][3]).equalsIgnoreCase("TRUE")){
						DeleteClient= DeleteClient(testData[tcNo]);						
					}
					else{
						DeleteClient=true;
						Reporter.log("Client deletion skipped for test case "+testData[tcNo][0]+".");
						System.out.println("Client deletion skipped for test case "+testData[tcNo][0]+".");
					}					
					if(DeleteContract && DeleteClient)
					{
						coreFunc.logData("DeleteClientContract",String.valueOf(testData[tcNo][0]),"Passed","",assertEnabled);
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
			coreFunc.logData("DeleteClientContract","","Failed","Error: Refer output logs for more information.",assertEnabled);
			e.printStackTrace();
		}		
		
	}
	
	private boolean DeleteContract(Object[] testData)
	{
    	System.out.println("Debug log : Entering in DeleteContract ");
       	String testID=String.valueOf(testData[0]);
    	try
		{
    		String email =String.valueOf(testData[5]);	    	
    		Reporter.log("Delete Contract for ==> Email Id :  "+email);
    		
    		WebDriverWait clientWait = new WebDriverWait(getDriver(), 45);
    		
    		Reporter.log("Search client by Email Id :  "+email);  
    		Thread.sleep(10000);
			getDriver().findElement(By.name(LoadProperty.getVar("clientSearchBox", "element"))).clear();
			getDriver().findElement(By.name(LoadProperty.getVar("clientSearchBox", "element"))).sendKeys(email);
			
			clientWait.until(ExpectedConditions.invisibilityOfElementLocated(By.className(LoadProperty.getVar("clientGridWait", "element"))));
			
			//Verify client found or not with specified name in grid 
			WebElement recordcount =  getDriver().findElement(By.xpath(LoadProperty.getVar("recordCount", "element")));
			System.out.println("Debug log :  DeleteContract "+ recordcount.getText());
			if(recordcount.getText().equalsIgnoreCase(coreFunc.GetDELanguageText("No data to display"))){
				Reporter.log("Unable to delete contract due to Client not found with Email Id :  "+email);
				coreFunc.logData("DeleteContract",testID,"Failed","Unable to delete contract due to Client not found with Email Id :  "+email+"  [ TCID: "+testID+"]",assertEnabled);					
	    		return false;
			}
			else{
				Reporter.log("Client found with details Email Id :  "+email);
			}
			
    		WebElement contractcount =  getDriver().findElement(By.xpath(LoadProperty.getVar("contractCount", "element")));
			Reporter.log("Verify Contract found or not with specified Email ID in clients grid");
			System.out.println("Debug log : Case verify text :  "+ contractcount.getText());
			if(contractcount.getText().equalsIgnoreCase("0"))
			{
				Reporter.log("Unable to delete contract due to no contract found for Email Id :  "+email);
				coreFunc.logData("DeleteContract",testID,"Failed","Unable to delete contract due to no contract found for Email Id :  "+email+"  [ TCID: "+testID+"]",assertEnabled);					
	    		return false;			
			}
			WebElement contractcountBef =  getDriver().findElement(By.xpath(LoadProperty.getVar("contractCount", "element")));
			System.out.println("Debug log : Contract count before deletion :  "+ contractcountBef.getText());
			int contractCountbefore = Integer.parseInt(contractcountBef.getText());
			
			Reporter.log("Debug log : Contract count before deletion :  "+ contractcountBef.getText());
			getDriver().manage().timeouts().implicitlyWait(10000, TimeUnit.SECONDS);
			//select first record
			getDriver().findElement(By.xpath(LoadProperty.getVar("firstRecordContract", "element"))).click();
			
			Thread.sleep(10000);
			
			//select contract for deletion
			getDriver().findElement(By.xpath(LoadProperty.getVar("firstContract", "element"))).click();
			Thread.sleep(10000);
			
			//wait till contract details has been loading
			WebDriverWait contractWait = new WebDriverWait(getDriver(), 60);
			contractWait.until(ExpectedConditions.visibilityOfElementLocated(By.name("category")));
			
			//click on delete contract button
			coreFunc.ClickOnActionButton("Delete");
			
			//click on ok button
			getDriver().manage().timeouts().implicitlyWait(10000, TimeUnit.SECONDS);
			getDriver().findElement(By.id(LoadProperty.getVar("okPopup", "element"))).click();
			    		
    		//Get data for contract
    	
			clientWait.until(ExpectedConditions.invisibilityOfElementLocated(By.className(LoadProperty.getVar("clientGridWait", "element"))));
			//click on refresh button 
			getDriver().findElement(By.xpath(LoadProperty.getVar("refreshBtn", "element"))).click();
			clientWait.until(ExpectedConditions.invisibilityOfElementLocated(By.className(LoadProperty.getVar("clientGridWait", "element"))));
			
			Thread.sleep(5000);
			WebElement contractcountA =  getDriver().findElement(By.xpath(LoadProperty.getVar("contractCount", "element")));
			
			Reporter.log("Verify Contract count  with specified Email Id in clients grid");
			System.out.println("Debug log : Contract count after deleting :  "+ contractcountA.getText());
			
			int contractCountAfter = Integer.parseInt(contractcountA.getText());
					
			if(contractCountAfter == (contractCountbefore-1))
			{
				Reporter.log("Contract deletes successfully for client Email Id :  "+email);
			}
			else
			{
				coreFunc.logData("DeleteContract",testID,"Failed","Error: Contract not deleted successfully for Test Case [ TCID: "+testID+"]",assertEnabled);
				return false;
			}
			Reporter.log("Exiting Delete Contract ");
			System.out.println("Debug log : Exiting from DeleteContract ");
			
   		}
    	catch(NoSuchElementException eNFound)
		{
    		coreFunc.logData("DeleteContract",testID,"Failed","Error: "+eNFound.getMessage()+" element not found. [ TCID: "+testID+"]",assertEnabled);
    		System.out.println("Debug log : Error ==> "+eNFound.getMessage()+" element not found. [ TCID: "+testID+"]");
    		return false;
    	}
		catch(ElementNotVisibleException eNVisible)
		{
    		coreFunc.logData("DeleteContract",testID,"Failed","Error: "+eNVisible.getMessage()+" element is not visible. [ TCID: "+testID+"]",assertEnabled);
    		System.out.println("Debug log : Error ==> "+eNVisible.getMessage()+" element is not visible. [ TCID: "+testID+"]");
    		return false;
    	}
		catch(Exception e)
		{
    		coreFunc.logData("DeleteContract",testID,"Failed","Error: occured on "+e.getMessage()+" element.[ TCID: "+testID+"]",assertEnabled);
    		System.out.println("Debug log : Error ==> occured on  "+e.getMessage()+" element.[ TCID: "+testID+"]");
    		return false;
    	}
    	return true;
    }   
  
	private boolean DeleteClient(Object[] testData)
		{
	    	System.out.println("Debug log : Entering in DeleteClient ");
	       	String testID=String.valueOf(testData[0]);
	    	try
			{
	    		String email =String.valueOf(testData[5]);
	    	
	    		Reporter.log("Delete Client with ==> Email Id :  "+email); 
	    		System.out.println("Debug log ==>Delete Client with Email Id :  "+email);
				Thread.sleep(6000);
	    		WebDriverWait clientWait = new WebDriverWait(getDriver(), 45);
	    		clientWait.until(ExpectedConditions.invisibilityOfElementLocated(By.className(LoadProperty.getVar("clientGridWait", "element"))));
				
				Reporter.log("Before Deletion ==> Search client by Email Id :  "+email);
				
				getDriver().findElement(By.name(LoadProperty.getVar("clientSearchBox", "element"))).clear();
				getDriver().findElement(By.name(LoadProperty.getVar("clientSearchBox", "element"))).sendKeys(email);
				Thread.sleep(3000);
				clientWait.until(ExpectedConditions.invisibilityOfElementLocated(By.className(LoadProperty.getVar("clientGridWait", "element"))));
				Thread.sleep(3000);
							
				//Verify client found or not with specified name in grid
				WebElement recordcount =  getDriver().findElement(By.xpath(LoadProperty.getVar("recordCount", "element")));
				System.out.println("Debug log : Case verify text :  "+ recordcount.getText());
				if(recordcount.getText().equalsIgnoreCase(coreFunc.GetDELanguageText("No data to display"))){
		    		coreFunc.logData("DeleteClient",testID,"Failed","Error: No Client found with Email ID : "+email+"  [ TCID: "+testID+"]",assertEnabled);					
		    		return false;
				}
				else{
					Reporter.log("Client found with Email ID : "+email);
				}
				//select first record
				getDriver().findElement(By.xpath(LoadProperty.getVar("firstRecord", "element"))).click();
				
				Thread.sleep(5000);
				
				WebDriverWait clientGridWait = new WebDriverWait(getDriver(), 45);
				clientGridWait.until(ExpectedConditions.visibilityOfElementLocated(By.name(LoadProperty.getVar("email", "element"))));
					
				Thread.sleep(5000);
				
				//click on delete button
				coreFunc.ClickOnActionButton("Delete");
				
				//click on ok button
				getDriver().manage().timeouts().implicitlyWait(10000, TimeUnit.SECONDS);
				getDriver().findElement(By.id(LoadProperty.getVar("okPopup", "element"))).click();
				
				Reporter.log("After Deletion  ==> Search client by Email Id :  "+email);
				Thread.sleep(5000);
				getDriver().findElement(By.name(LoadProperty.getVar("clientSearchBox", "element"))).clear();
				getDriver().findElement(By.name(LoadProperty.getVar("clientSearchBox", "element"))).sendKeys(email);
				
				clientWait.until(ExpectedConditions.invisibilityOfElementLocated(By.className(LoadProperty.getVar("clientGridWait", "element"))));
				Thread.sleep(5000);
				WebElement recordcountADel =  getDriver().findElement(By.xpath(LoadProperty.getVar("recordCount", "element")));
				System.out.println("Debug log : Case verify text :  "+ recordcountADel.getText());
				if(recordcount.getText().equalsIgnoreCase(coreFunc.GetDELanguageText("No data to display"))){
		    		Reporter.log("Client Deleted for ==> Email Id :  "+email); 
				}
				else
				{
					coreFunc.logData("DeleteClient",testID,"Failed","Error: Client found with Email ID : "+email+"  [ TCID: "+testID+"]",assertEnabled);
					return false;
				}				
				System.out.println("Debug log : Exiting from AddClient");				
		    }
			catch(NoSuchElementException eNFound)
			{
	    		coreFunc.logData("DeleteClient",testID,"Failed","Error: "+eNFound.getMessage()+" element not found. [ TCID: "+testID+"]",assertEnabled);
	    		System.out.println("Debug log : Error ==> "+eNFound.getMessage()+" element not found. [ TCID: "+testID+"]");
	    		return false;
	    	}
			catch(ElementNotVisibleException eNVisible)
			{
	    		coreFunc.logData("DeleteClient",testID,"Failed","Error: "+eNVisible.getMessage()+" element is not visible. [ TCID: "+testID+"]",assertEnabled);
	    		System.out.println("Debug log : Error ==> "+eNVisible.getMessage()+" element is not visible. [ TCID: "+testID+"]");
	    		return false;
	    	}
			catch(Exception e)
			{
	    		coreFunc.logData("DeleteClient",testID,"Failed","Error: occured on "+e.getMessage()+" element.[ TCID: "+testID+"]",assertEnabled);
	    		System.out.println("Debug log : Error ==> occured on  "+e.getMessage()+" element.[ TCID: "+testID+"]");
	    		return false;
	    	}
	    	return true;
	    }
	   
}