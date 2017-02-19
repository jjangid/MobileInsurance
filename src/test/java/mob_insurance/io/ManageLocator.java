package mob_insurance.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


public class ManageLocator {
	static Properties configProperty = null; 
	
	public static boolean loadConfigProperties(String language){
		boolean result=true;
		try{
				FileInputStream configFileObj=null;
				configFileObj=new FileInputStream(new File("src//test//resources//config//element.properties"));
				configProperty=new Properties();
				configProperty.load(configFileObj);
		}catch(IOException ioExceptionObj){		  	   
			System.out.println("Error occured while reading locator property file.");
			return false;
		}			
		return result;		
	}
	
	public static String[] getLocator(String key){
	 
	 String lang =LoadProperty.getVar("loginLanguage", "data");
	 key = lang.equalsIgnoreCase("DE") ? key+"_DE" :key;
	 System.out.println("After key  "+key);	
	 String keyValue=configProperty.getProperty(key);
	 String[] arLocator =null;
	 if(keyValue.lastIndexOf(")::") == -1){
		 arLocator=new String[2];		 
	 }else{
		 arLocator=new String[3];
		 arLocator[2]=keyValue.substring(keyValue.lastIndexOf(")::")+3);	 
	 }
	 
	 arLocator[0]=keyValue.substring(0, keyValue.indexOf("("));
	 arLocator[1]=keyValue.substring(keyValue.indexOf("(")+1, keyValue.lastIndexOf(")"));
	 
	 return arLocator;
		
	}
}
