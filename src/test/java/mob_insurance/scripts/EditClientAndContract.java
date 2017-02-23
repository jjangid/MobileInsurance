/*Common Test*/
package mob_insurance.scripts;

import java.util.concurrent.TimeUnit;
import mob_insurance.functions.TestBase;
import mob_insurance.io.LoadProperty;
import mob_insurance.functions.CoreRepository;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;


public class EditClientAndContract extends TestBase{
	CoreRepository coreFunc = null;
	Boolean assertEnabled = false;
	String locatorType=null;
	String locator=null;
	@Test
	public void ExecuteTest() throws Exception{
	try
		{
			Reporter.log("EditClientAndContract Test execution has started...");
			int pageTimeOut = Integer.valueOf(LoadProperty.getVar("PageLoadImplicitTimeOutFactor", "config"));
			int elementTimeOut = Integer.valueOf(LoadProperty.getVar("ElementImplicitTimeOutFactor", "config"));
			getDriver().manage().timeouts().pageLoadTimeout(pageTimeOut, TimeUnit.SECONDS);
			getDriver().manage().timeouts().implicitlyWait(elementTimeOut, TimeUnit.SECONDS);
			
			//create instance of class core			
			coreFunc = new CoreRepository();
			//Assign webdriver driver to class core
			coreFunc.setDriver(this.getDriver());	
			Reporter.log("Navigate to specified URL");
		
			//Call login function to login in mobile insurance application
			Reporter.log("Performed Login on Application");
			//coreFunc.Login();
			
			Reporter.log("Edit Client details started...");			
			Reporter.log("Search client by Last Name ");
			getDriver().findElement(By.name("clientsgridsearchfield-inputEl")).clear();
			getDriver().findElement(By.name("clientsgridsearchfield-inputEl")).sendKeys("Buterner");
	
			getDriver().manage().timeouts().implicitlyWait(elementTimeOut, TimeUnit.SECONDS);
			Thread.sleep(3000);
			//Verify client found or not with specified name in grid
			WebElement recordcount =  getDriver().findElement(By.xpath("html/body/div[2]/div[2]/div/div/div/div/div[1]/div[2]/div/div/div[2]/div[4]/div/div/div[8]"));
			Reporter.log("Verify client found or not with specified last name in clients grid");
			System.out.println("Debug log : Case verify text :  "+ recordcount.getText());
			if(recordcount.getText().equalsIgnoreCase("No data to display"))
			{
				Assert.assertEquals(false, true, "No record found for editing");					
			}
			Reporter.log("Client Record found for editing");
			//select first record
			getDriver().findElement(By.xpath("html/body/div[2]/div[2]/div/div/div/div/div[1]/div[2]/div/div/div[2]/div[3]/div[1]/div/table[1]")).click();
			
			//wait till client details loading
			WebDriverWait clientLoadingWait = new WebDriverWait(getDriver(), 60);
			clientLoadingWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("html/body/div[2]/div[2]/div/div/div/div/div[3]/div[2]/div/div/div[2]/div[2]/div/div[2]/div/div[2]/div/div/a[5]/span/span/span[2]")));
			
			//click on edit Client button
			getDriver().findElement(By.xpath("html/body/div[2]/div[2]/div/div/div/div/div[3]/div[2]/div/div/div[2]/div[2]/div/div[2]/div/div[2]/div/div/a[5]/span/span/span[2]")).click();
			getDriver().manage().timeouts().implicitlyWait(elementTimeOut, TimeUnit.SECONDS);
			Reporter.log("Enter Client Data");
			//Enter client data
			/*String name =  coreFunc.generateName();
			System.out.println(name);
			String email = name+"@ittechnocrats.com";
			System.out.println(email);		
			getDriver().findElement(By.id("first_name-inputEl")).clear();
			getDriver().findElement(By.id("first_name-inputEl")).sendKeys(name);
			getDriver().findElement(By.id("last_name-inputEl")).clear();
			getDriver().findElement(By.id("last_name-inputEl")).sendKeys("Buterner");
			getDriver().findElement(By.id("birthdate-inputEl")).clear();
			getDriver().findElement(By.id("birthdate-inputEl")).sendKeys("10/12/87");
			WebDriverWait wait = new WebDriverWait(getDriver(), 30);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("birth_name")));
			getDriver().findElement(By.name("birth_name")).clear();
			getDriver().findElement(By.name("birth_name")).sendKeys(coreFunc.generateName());
			getDriver().findElement(By.name("birth_place")).clear();
			getDriver().findElement(By.name("birth_place")).sendKeys(coreFunc.generateName());
			getDriver().findElement(By.name("street")).clear();
			getDriver().findElement(By.name("street")).sendKeys(coreFunc.generateNumber());
			getDriver().findElement(By.name("zip")).clear();
			getDriver().findElement(By.name("zip")).sendKeys(coreFunc.generateNumber());
			getDriver().findElement(By.name("city")).clear();
			getDriver().findElement(By.name("city")).sendKeys("Absberg");
			getDriver().findElement(By.name("phone")).clear();
			getDriver().findElement(By.name("phone")).sendKeys(coreFunc.generateNumber());
			getDriver().findElement(By.name("email")).clear();
			getDriver().findElement(By.name("email")).sendKeys(email);
			getDriver().findElement(By.name("mobile")).clear();
			getDriver().findElement(By.name("mobile")).sendKeys(coreFunc.generateNumber());*/
			
			//Click on save client button
			getDriver().findElement(By.xpath("html/body/div[2]/div[2]/div/div/div/div/div[3]/div[2]/div/div/div[2]/div[2]/div/div[2]/div/div[2]/div/div/a[3]")).click();
			Reporter.log("Client Data Edited completed ...");
			
			//Verify contract is available for selected client
			Reporter.log("Edit Contract details started ...");
			WebElement contractcount =  getDriver().findElement(By.xpath("html/body/div[2]/div[2]/div/div/div/div/div[1]/div[2]/div/div/div[2]/div[3]/div[1]/div/table/tbody/tr[1]/td[4]/div"));
			Reporter.log("Verify Contract found or not with specified last name in clients grid");
			System.out.println("Debug log : Case verify text :  "+ contractcount.getText());
			if(contractcount.getText().equalsIgnoreCase("0"))
			{
				Assert.assertEquals(false, true, "No contract found for editing");					
			}
			Reporter.log("Contract found for editing");
			getDriver().manage().timeouts().implicitlyWait(elementTimeOut, TimeUnit.SECONDS);
			Thread.sleep(3000);
			//select client
			getDriver().findElement(By.xpath("html/body/div[2]/div[2]/div[1]/div/div/div/div[1]/div[2]/div/div/div[2]/div[3]/div[1]/div/table[1]/tbody/tr[1]/td[1]/div/div")).click();
			getDriver().manage().timeouts().implicitlyWait(elementTimeOut, TimeUnit.SECONDS);
			//select contract for editing
			getDriver().findElement(By.xpath("html/body/div[2]/div[2]/div[1]/div/div/div/div[1]/div[2]/div/div/div[2]/div[3]/div[1]/div/table[1]/tbody/tr[2]/td/div/div/div/div[2]/div[1]/div/table[1]")).click();
			getDriver().manage().timeouts().implicitlyWait(elementTimeOut, TimeUnit.SECONDS);
			//click on edit contract button			
			getDriver().findElement(By.xpath("html/body/div[2]/div[2]/div[1]/div/div/div/div[3]/div[2]/div/div/div[2]/div[2]/div[2]/div[2]/div/div[2]/div/div/a[5]/span/span/span[2]")).click();
			//wait till contract details has been loading
			WebDriverWait contractWait = new WebDriverWait(getDriver(), 60);
			contractWait.until(ExpectedConditions.visibilityOfElementLocated(By.name("category")));
			Reporter.log("Enter Contract details");
		 	getDriver().findElement(By.name("provider_id")).clear();
			getDriver().findElement(By.name("provider_id")).sendKeys("ADLER Versicherung AG");
			getDriver().manage().timeouts().implicitlyWait(elementTimeOut, TimeUnit.SECONDS);
			getDriver().findElement(By.name("provider_id")).sendKeys(Keys.ENTER);
			getDriver().manage().timeouts().implicitlyWait(elementTimeOut, TimeUnit.SECONDS);
			//getDriver().findElement(By.name("insurance_number")).clear();
			//getDriver().findElement(By.name("insurance_number")).sendKeys(coreFunc.generateNumber());
			getDriver().findElement(By.name("payment")).clear();
			getDriver().findElement(By.name("payment")).sendKeys("1222");
			getDriver().findElement(By.name("duration_months")).clear();
			getDriver().findElement(By.name("duration_months")).sendKeys("15");
			//Click on save contract button
			getDriver().findElement(By.xpath("html/body/div[2]/div[2]/div/div/div/div/div[3]/div[2]/div/div/div[2]/div[2]/div[2]/div[2]/div[1]/div[2]/div/div/a[3]/span/span/span[2]")).click();
			getDriver().manage().timeouts().implicitlyWait(elementTimeOut, TimeUnit.SECONDS);
			Reporter.log("Contract Data Edited completed ...");
			Reporter.log("EditClientAndContract Test execution has Ended...");
		}
		catch(Exception e){
			//new SkipException("Auto UUID ID is Missing."); 
			Reporter.log("Error: Test Case failed due to error occured");
			coreFunc.logData("Common Test","","Failed","Error: Refer output logs for more information.",assertEnabled);
			e.printStackTrace();
		}		
		
	}


   
}