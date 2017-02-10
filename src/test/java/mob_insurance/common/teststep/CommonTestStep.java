package mob_insurance.common.teststep;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import mob_insurance.io.ManageLocator;
import mob_insurance.processor.Browser;
import mob_insurance.processor.TestResult;

public class CommonTestStep {

	public static void logIn(String userName,String pwd,Browser webBrowser, String languageCode) throws Exception{
		 try{
			 
	         	// Login Page Locators
				// Index 0: Locator Type and Index 1: Locator value
				String[] locatorUserName=ManageLocator.getLocator("Username");
				String[] locatorPWD=ManageLocator.getLocator("Password");
				String[] locatorLoginButton=ManageLocator.getLocator("LoginButton");

				boolean result=selectLanguage(webBrowser,languageCode);
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
				elePassword.sendKeys(pwd);
				TestResult.addTestResult("Enter Password as "+pwd,"Passed");
				
				webBrowser.waitUntilElementEnabledWithCSS(btnLogin);
				btnLogin.click();
				TestResult.addTestResult("Click Login button","Passed");
				
		    }catch(Exception e){
		    	TestResult.addTestResult("Login into application","Failed","- Error occured while login into app.");
		    	throw e;
		    }
		
		
	}
	
	private static boolean selectLanguage(Browser webBrowser, String languageCode) {		 
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
	
	
}
