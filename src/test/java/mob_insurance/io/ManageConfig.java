package mob_insurance.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ManageConfig {

static Properties configProperty = null; 
	
	public static boolean loadConfigProperties(){
		boolean result=true;
		try{
			    System.out.println("Info: Loading config.properties file.");
				FileInputStream configFileObj=null;
				configFileObj=new FileInputStream(new File("src//test//resources//config//config.properties"));
				configProperty=new Properties();
				configProperty.load(configFileObj);
		}catch(IOException ioExceptionObj){		  	   
			System.out.println("Error occured while reading configuration file.");
			return false;
		}			
		return result;		
	}
	
	public static String getTestDataFileName(String key){
		if(configProperty == null){
			loadConfigProperties();	
		}
		return configProperty.getProperty(key);
	}
	
	public static String getValue(String key){
		if(configProperty == null){
			loadConfigProperties();	
		}
		return configProperty.getProperty(key);
	}
    
	public static String[] getLocator(String key){
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
