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
				TestResult.resetListTestResult();
				if((testData[tcNo][0])==null){					
					Reporter.log("Test case not found in test data sheet");
					Assert.assertEquals(false, true, "Test case not found in test data sheet");					
				}
				String testname = (String) testData[tcNo][0];
				System.out.println("testname : "+testname);
				TestResult.addTestResult(testname,"");
				Reporter.log("S.No: " +tcNo+ " Test execution started for testID: "+testData[tcNo][0]);
				
				boolean DeleteContract=false;
				boolean DeleteClient=false;				
						
				System.out.println("Test Case ID : "+testData[tcNo][0]);
				//Verify 'ExecuteTest' column value to identify whether test case need to execute or not
				if(String.valueOf(testData[tcNo][1]).equalsIgnoreCase("TRUE")){
					
		
//					Open URL and Login will only be called when Login needs to be done					
					String loginText = (String) testData[tcNo][2];
					String[] loginVariable = coreFunc.GetLoginRole(loginText);
					//Open URL and Login will only be called when Login needs to be done					
					if(String.valueOf(loginVariable[0].trim()).equalsIgnoreCase("TRUE")){						
						//Login to application with specified credentials
						if(!coreFunc.Login(loginVariable[1])){
							TestResult.appendTestResult();
							continue;
						}
					}
				
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
						TestResult.addTestResult("DeleteClientContract Test Case : "+String.valueOf(testData[tcNo][0]),"Passed");
						coreFunc.logData("DeleteClientContract",String.valueOf(testData[tcNo][0]),"Passed","",assertEnabled);
						System.out.println("Test Case"+testData[tcNo][0]+" is passed.");
					}
					boolean needToLogOut=Boolean.valueOf(String.valueOf(testData[tcNo][6]));
					if(needToLogOut){
					  coreFunc.Logout();					  
					}						
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
			TestResult.addTestResult("DeleteClientContract failed due to error occured","Failed");
			TestResult.appendTestResult();
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
				TestResult.addTestResult("Unable to delete contract due to Client not found with Email Id :  "+email,"Failed");
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
				TestResult.addTestResult("Unable to delete contract due to no contract found for Email Id :  "+email,"Failed");
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
			TestResult.addTestResult("First contract selected for Client "+email,"Passed");
			//wait till contract details has been loading
			WebDriverWait contractWait = new WebDriverWait(getDriver(), 60);
			contractWait.until(ExpectedConditions.visibilityOfElementLocated(By.name("category")));
			
			//click on delete contract button
			coreFunc.ClickOnActionButton("Delete");
			TestResult.addTestResult("Click on 'Delete' button","Passed");
			
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
				Reporter.log("Contract deleted successfully for client Email Id :  "+email);
				TestResult.addTestResult("Contract deleted successfully for client Email Id : "+email,"Passed");
			}
			else
			{
				TestResult.addTestResult("Contract not deleted successfully for client Email Id : "+email,"Failed");
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
					
					TestResult.addTestResult("Client not found for deletion Email ID : "+email ,"Failed");
					coreFunc.logData("DeleteClient",testID,"Failed","Error: No Client found with Email ID : "+email+"  [ TCID: "+testID+"]",assertEnabled);					
		    		return false;
				}
				else{
					Reporter.log("Client found with Email ID : "+email+" ==>  Step Pass");
					TestResult.addTestResult("Client found for deletion with Email ID : "+email ,"Passed");
				}
				//select first record
				getDriver().findElement(By.xpath(LoadProperty.getVar("firstRecord", "element"))).click();
				
				Thread.sleep(5000);
				
				WebDriverWait clientGridWait = new WebDriverWait(getDriver(), 45);
				clientGridWait.until(ExpectedConditions.visibilityOfElementLocated(By.name(LoadProperty.getVar("email", "element"))));
					
				Thread.sleep(5000);
				
				//click on delete button
				coreFunc.ClickOnActionButton("Delete");
				Reporter.log("Clicked on Delete button. ==>  Step Pass");
				TestResult.addTestResult("Clicked on Delete button. ","Passed");
				
				//click on ok button
				getDriver().manage().timeouts().implicitlyWait(10000, TimeUnit.SECONDS);
				getDriver().findElement(By.id(LoadProperty.getVar("okPopup", "element"))).click();
				Reporter.log("Clicked on 'OK' button. ==>  Step Pass");
				TestResult.addTestResult("Clicked on 'Ok' button. ","Passed");
				
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
		    		TestResult.addTestResult("Client Deleted for ==> Email Id :  "+email,"Passed");
				}
				else
				{
					TestResult.addTestResult("Client not Deleted for ==> Email Id :  "+email,"Failed");
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