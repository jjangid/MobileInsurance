package mob_insurance.common_lib;

/**
 * @author IT Technocrats
 *
 */
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class LoadProperty {

	static String var = null;
	private static Properties prpMappingModule=null;

	public static String getVar(String key,String file) {
		Properties props = new Properties();
		String path = System.getProperty("user.dir");
		try {
			// load a properties file
			path = path + "\\src\\test\\resources\\config\\";
			if(file=="data")
			{
				props.load(new FileInputStream(path + "data.properties"));			
			}
			else if(file=="element")
			{
				props.load(new FileInputStream(path + "data.properties"));
				String lang =  props.getProperty("loginLanguage");
				key= lang.equalsIgnoreCase("DE") ? key+"_DE" :key;
				props.load(new FileInputStream(path + "element.properties"));
			}
			else if(file=="config")
			{
				props.load(new FileInputStream(path + "config.properties"));
			}else if(file.equalsIgnoreCase("MappingModule")){
				loadMappingModule(path+"MappingModule.properties");
				return props.getProperty(key);
			}
			if (key != null) {
				var = props.getProperty(key);
			}
			else
			{
				System.out.println("Got null value in key, you may provide a null value while calling getVar(String key)");
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return var;
	}
	
	private static void loadMappingModule(String path){
		if(prpMappingModule==null){
			prpMappingModule = new Properties();
			try {
				prpMappingModule.load(new FileInputStream(path));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
