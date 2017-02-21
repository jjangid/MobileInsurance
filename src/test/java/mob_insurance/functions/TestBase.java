package mob_insurance.functions;

/**
 * @author IT Technocrats
 *
 */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.URL;
import java.sql.Date;
import java.text.SimpleDateFormat;

import java.util.concurrent.TimeUnit;
import mob_insurance.functions.CoreRepository;
import jxl.write.WriteException;
import mob_insurance.io.InsuranceField;
import mob_insurance.io.LoadProperty;
import mob_insurance.io.ManageLocator;

import org.apache.http.HttpHost;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHttpEntityEnclosingRequest;
import org.json.JSONException;
import org.json.JSONObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.HttpCommandExecutor;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Duration;
import org.openqa.selenium.support.ui.Sleeper;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

//import mob_insurance.common.teststep.InsuranceField;
//import mob_insurance.io.ManageLocator;

public class TestBase extends Bean {
	private WebDriver driver = null;

	private DesiredCapabilities capability = null;
	String test_name = null;
	String username = System.getProperty("user.name");
	String project_path = System.getProperty("user.dir");
	String reportPath = null;
	FileWriter fWriter = null;
    BufferedWriter writer = null;
	//String generate_html_report;
	static int sheet_pointer = 0;
	
	private static String SELENIUM_HUB_URL;
	private static String TARGET_SERVER_URL;

	@BeforeSuite
	public void BeforeExecution(){
		try {
			String generate_html_report = LoadProperty.getVar("generate_html_report", "config").trim();
			System.out.println("Generate_html_report : '"+generate_html_report+"'");
			if(generate_html_report.equalsIgnoreCase("true")) 			
			{			
				System.out.println("Html Report created");
				reportPath = project_path	+ "\\src\\test\\resources\\Report";	
				//*******************Start: Rename Existing Test Result file *********
				  renameFile(reportPath);			
				//*******************End: Rename Existing Test Result file *********
	        	fWriter = new FileWriter(reportPath+"//MI_Results.html");
	            //creating a buffered writer for the file object
	            writer = new BufferedWriter(fWriter);
	            writer.write("<!DOCTYPE HTML PUBLIC '-//W3C//DTD HTML 4.01 Transitional//EN' 'http://www.w3.org/TR/html4/loose.dtd'>" + "<html>" +
				"<head>" +
				"<meta http-equiv='content-type' content='text/html; charset=ISO-8859-1'>" +
				"<meta http-equiv='Content-Script-Type' content='text/javascript'>" +
				"<meta http-equiv='Content-Style-Type' content='text/css'>" +
				"<title>Automation Execution Report</title>" +
					"<script type = 'text/javascript'>" +
						"function count(){" +
							"var all = document.getElementsByTagName('*');" +
							"var passCount = 0;" +
							"var failCount = 0;" +
							"for (var i=0, max=all.length; i < max; i++){" +
								"if(all[i].className == 'passColor') passCount++;" +
								"else if(all[i].className == 'failColor') failCount++;" +							
							"}" +
							"document.getElementById('passSteps').innerText = passCount;" +
							"document.getElementById('failSteps').innerText = failCount;" +						
							"document.getElementById('totalSteps').innerText = passCount + failCount;" +
						"}" +	
					"</script>" +
					"<style>" +
						".table{border-color: #000000;width : 100%;}" +
						".infoColor{background-color : #E3E3E3;}" +
						".passColor{background-color : #D4EBD8;}" +
						".failColor{background-color : #ECC2C2;}" +					
					"</style>" +
				"</head>" +
				"<body onLoad='count();'>"+
					"<table>" +
						"<tr>" +
							"<table border='3px' bgcolor='#C0C0C0' class = 'table' width='100%'>" +
								"<tr>" +
									"<td colspan = '11' height='30px' align='center'>" +
										"<font face='georgia'><b><u>Mobile Insurance Automation Execution Report</u></b></font>" +
									"</td>" +
								"</tr>" +
							"</table>" +
							"<table border='3px' bgcolor='#C0C0C0' width = '100%'>" +
								"<tr>" +
									"<td colspan = '11' height='30px' align='center' style = 'font-style : bold'>" +
										"<font face='georgia'><b>Total Test Cases :&nbsp; <label id = 'totalSteps'>0</label>&nbsp;&nbsp;&nbsp;&nbsp; [Passed :&nbsp; <label id = 'passSteps'>0</label>&nbsp;&nbsp;&nbsp;&nbsp;						Failed :&nbsp; <label id = 'failSteps'>0</label>&nbsp;&nbsp;&nbsp;&nbsp;]</b></font>" +
									"</td>" +
								"</tr>" +
							"</table>" +
							"<table border='3px' bgcolor='#C0C0C0' cellpadding='10' class = 'table' width='100%' style='table-layout: auto; word-wrap: break-word;'>" +
								"<tr>" +
									"<td><font face='georgia'><b><u>S.No.</u></b></font></td>" +
									"<td><font face='georgia'><b><u>Test Case Name</u></b></font></td>" +
									"<td><font face='georgia'><b><u>Test Case ID</u></b></font></td>" +
									"<td><font face='georgia'><b><u>Pass/Fail</u></b></font></td>" +
									"<td><font face='georgia'><b><u>Date / Time</u></b></font></td>" +
									"<td><font face='georgia'><b><u>Comments</u></b></font></td>" +
								"</tr>");
	            writer.close();				
			} 
			else
			{
				System.out.println("Html Report not created");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();			
		}
	}
	
	@Parameters({"browser"})	
	@BeforeClass
	public WebDriver init(String browser ) 
	{
		try {
			
			String baseUrl = LoadProperty.getVar("baseUrl","data");
			System.out.println("baseUrl: '" + baseUrl + "'");
			//Hub 
			String hubUrl = LoadProperty.getVar("hub_url", "config");
			SELENIUM_HUB_URL = getConfigurationProperty("SELENIUM_HUB_URL","test.selenium.hub.url", hubUrl);
			System.out.println("Using Selenium hub at: " + SELENIUM_HUB_URL);
			//Target 
			TARGET_SERVER_URL = getConfigurationProperty("TARGET_SERVER_URL","test.target.server.url", baseUrl);
			//System.out.println("using target server at: " + TARGET_SERVER_URL);
			
			test_name = this.getClass().getSimpleName();
			System.out.println("Starting Test: '" + test_name + "'");			
			System.out.println("browser name: " + browser);			
			System.out.println("Running Grid : "+LoadProperty.getVar("use_grid", "config"));
			
			if(browser.equalsIgnoreCase("firefox")) 
			{
				System.out.println("Executing test on Firefox browser....");
				System.setProperty("webdriver.gecko.driver",project_path+"\\src\\test\\resources\\driver\\wires.exe");	
				DesiredCapabilities capabilities=DesiredCapabilities.firefox();
				capabilities.setCapability("marionette", true);
				capability.setBrowserName(browser);
				capability.setPlatform(org.openqa.selenium.Platform.ANY);
				if (LoadProperty.getVar("use_grid", "config").equalsIgnoreCase("true")) 
				{
					//place holder for grid implementation
					System.out.println("Test not executed because grid functionality not implemented yet");
				} 
				else
				{ 
					setDriver(new FirefoxDriver(capabilities));
				}	
				
			}
			else if (browser.equalsIgnoreCase("iexplore")) 
			{
				System.out.println("Executing test on Internet Explorer browser....");
				DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
		    	capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
		    	capabilities.setCapability("requireWindowFocus", true);
		    	System.setProperty("webdriver.ie.driver", project_path+"\\src\\test\\resources\\driver\\IEDriverServer.exe");
				capability.setBrowserName(browser);
				capability.setPlatform(org.openqa.selenium.Platform.WINDOWS);
				if (LoadProperty.getVar("use_grid", "config").equalsIgnoreCase("true")) 
				{
					//place holder for grid implementation
					System.out.println("Test not executed because grid functionality not implemented yet");
				} 
				else
				{ 
				   setDriver(new InternetExplorerDriver(capabilities));		   
				}
			}
			else if (browser.equalsIgnoreCase("chrome")) 
			{
				System.out.println("Executing test on Chrome browser....");
				ChromeOptions options = new ChromeOptions();
				options.addArguments("--disable-extensions");
				options.addArguments("test-type");
				System.setProperty("webdriver.chrome.driver",project_path+"\\src\\test\\resources\\driver\\chromedriver.exe");
				if (LoadProperty.getVar("use_grid", "config").equalsIgnoreCase("true")) 
				{
					//place holder for grid implementation
					System.out.println("Test not executed because grid functionality not implemented yet");
				} 
				else
				{ 
					setDriver(new ChromeDriver(options));  
				}
			}
			
			if(getDriver()==null)
				   throw new Exception("Error: You can run test script only on Firefox and Chrome browser. Support is unavailable for other browsers. ");
				
			getDriver().manage().window().maximize();
			//getDriver().manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);	
			return getDriver();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}

	@AfterClass
	public void tearDown() throws IOException, WriteException {
		try {
			Sleeper.SYSTEM_SLEEPER.sleep(new Duration(5, TimeUnit.SECONDS));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Ending Test Name: '" + test_name + "'");
		System.out.println("Shuting down driver of Test Name: '" + test_name
				+ "'");
		driver.close();
		driver.quit();
    	driver=null;
		
		System.out.println("Driver quit for Test: '" + test_name + "'");
		System.out.println("\n");
	}
	
	public void waitForWhile(int seconds){
		try {
			Sleeper.SYSTEM_SLEEPER.sleep(new Duration(seconds, TimeUnit.SECONDS));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public WebDriver getDriver() {
		return driver;
	}

	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}

	@AfterSuite(alwaysRun = true)
	public void tearDownAfterSuit() {
		if (driver != null) {
			System.out
					.println("Driver of Test name: "
							+ test_name
							+ " is closing at the end of suit, means this script did not worked properly");
			driver.quit();
		}
		// send email code
	}

	private static String getConfigurationProperty(String envKey,String sysKey, String defValue) {
		
		String retValue = defValue;
		String envValue = System.getenv(envKey);
		String sysValue = System.getProperty(sysKey);
		// system property prevails over environment variable
		if (sysValue != null) {
			retValue = sysValue;
		} else if (envValue != null) {
			retValue = envValue;
		}
		return retValue;
	}

    private void renameFile(String filePath){
    	
    	File eTestResult=new File(filePath+"\\MI_Results.html");
    	new File("loc/xyz1.mp3").renameTo(new File("loc/xyz.mp3"));
    	if(eTestResult.exists()){ 
    	   String fileName=String.format("MI_Results_"+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date(System.currentTimeMillis())));
    	   File nFile=new File(filePath+"\\"+fileName);
    	   System.out.println("Renamed ReportFileName "+fileName);
    	   eTestResult.renameTo(nFile);    	  
    	   nFile=null;
    	}
    	eTestResult=null;
    }
    
    public String getIPOfNode(RemoteWebDriver remoteDriver){

    	  String hostFound = null; 
    	  try { 
    	     HttpCommandExecutor ce = (HttpCommandExecutor) remoteDriver.getCommandExecutor(); 
    	     String hostName = ce.getAddressOfRemoteServer().getHost(); 
    	     int port = ce.getAddressOfRemoteServer().getPort(); 
    	     HttpHost host = new HttpHost(hostName, port); 
    	     DefaultHttpClient client = new DefaultHttpClient(); 
    	     URL sessionURL = new URL("http://" + hostName + ":" + port + "/grid/api/testsession?session=" + remoteDriver.getSessionId()); 
    	     BasicHttpEntityEnclosingRequest r = new BasicHttpEntityEnclosingRequest( "POST", sessionURL.toExternalForm()); 
    	     HttpResponse response = client.execute(host, r); 
    	     JSONObject object = extractObject(response); 
    	     URL myURL = new URL(object.getString("proxyId")); 
    	     if ((myURL.getHost() != null) && (myURL.getPort() != -1)) { 
    	     // hostFound = myURL.getHost(); 
    	      hostFound = object.getString("proxyId");
    	      
    	     } 
    	    } catch (Exception e) { 
    	     System.err.println(e); 
    	    }
    	  return hostFound; 
    	} 
    
    private JSONObject extractObject(HttpResponse resp) throws IOException, JSONException { 
    	InputStream contents = resp.getEntity().getContent(); 
    	StringWriter writer = new StringWriter(); 
    	IOUtils.copy(contents, writer, "UTF8"); 
    	JSONObject objToReturn = new JSONObject(writer.toString()); 
    	return objToReturn; 
  }

//  public String[] interactWithElementBasedOnType(String elementType,String fieldTitle,String elementName,String locatorKey,Object eleValue){
	public String[] interactWithElementBasedOnType(InsuranceField objInsuranceField){
  	String[] testResult=new String[3];
  	CoreRepository coreFunc = new CoreRepository();
  	testResult[0]="Enter "+objInsuranceField.fieldTitle+" as "+objInsuranceField.testDataValue+".";
  	
  	try{
	    	String[] locator=ManageLocator.getLocator((objInsuranceField.insuranceType+"."+objInsuranceField.fieldName).toUpperCase().trim());
	    	if(locator == null){
	    		testResult[1]="Failed";
		    	testResult[2]="Unable to find element locator for "+objInsuranceField.fieldName+" field.";	    	  
		    	System.out.println("Info: Unable to find element locator for "+objInsuranceField.fieldName+" field.");
		    	return testResult;	
	    	}
	    	WebElement wElement=null;
	    	
	    	System.out.println("Debug "+objInsuranceField.fieldType.toLowerCase());
	    	switch(objInsuranceField.fieldType.toLowerCase()){
	    	  case "combobox":
	    		              wElement=coreFunc.findElement(locator[0],locator[1]);
	    		              testResult[0]="Choose "+objInsuranceField.fieldTitle+" as "+objInsuranceField.testDataValue+".";
	    		              locator=ManageLocator.getLocator("Create.Contract.ContractFeature.Dropdown.Option");
	    		              wElement.click();
	    		              locator[1]=locator[1].replaceAll("@optionText", objInsuranceField.testDataValue);    		              
	    		              WebElement eleDWOption=coreFunc.findElement(locator[0],locator[1]);
	    		              eleDWOption.click();
	    		              testResult[1]="Passed";
	    		              break;
	    		
	    	  case "textarea":
	    	  case "datefield":
	    	  case "textfield":
	    		              wElement=coreFunc.findElement(locator[0],locator[1]);
	    		              testResult[0]="Enter "+objInsuranceField.fieldTitle+" as "+objInsuranceField.testDataValue+".";
	    		              wElement.clear();
		    		          wElement.sendKeys(objInsuranceField.testDataValue);
		    		          testResult[1]="Passed";
		    		          break;
	    	  case "checkbox":
	    		              wElement=coreFunc.findElement(locator[0],locator[1]);
	    		              testResult[0]="Select "+objInsuranceField.fieldTitle+" as "+objInsuranceField.testDataValue+".";
	    		              if(wElement.isSelected() != Boolean.valueOf(objInsuranceField.testDataValue)){
	    		            	  wElement.click();
	    		              } 
	    		              testResult[1]="Passed";
	    		              break;
	    	  case "radiogroup":
	    		              locator[1]=locator[1].replaceAll("@buttonLabel",objInsuranceField.testDataValue);
	    		              wElement=coreFunc.findElement(locator[0],locator[1]);
				              testResult[0]="Select "+objInsuranceField.fieldTitle+" as "+objInsuranceField.testDataValue+".";
				              wElement.click();
				              testResult[1]="Passed";
				              break;
//	    	  case "click":
//	    		              wElement=this.findElement(locator[0],locator[1]);
//	    		              testResult[0]="Click "+fieldName+".";
//	    		              wElement.click();
//	    		              testResult[1]="Passed";
//	    		              break;
	    	  default:
				    		  testResult[0]="Enter "+objInsuranceField.fieldTitle+" as "+objInsuranceField.testDataValue+".";
					    	  testResult[1]="Failed";
					    	  testResult[2]="Method supports only combobox/textarea/datefield/textfield/checkbox/radiogroup type element.";	
	    		              System.out.println("Error: Method supports only combobox/textarea/datefield/textfield/checkbox/radiogroup type element.");
	    		              return testResult;
	    	}
  	}catch(Exception e){
  		testResult[1]="Failed";
	    	testResult[2]="Error occured while interacting with field on UI.";
  		return testResult;
  	}
  	return testResult;
  }
    
}